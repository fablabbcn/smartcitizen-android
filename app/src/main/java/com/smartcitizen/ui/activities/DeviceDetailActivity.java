package com.smartcitizen.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryfork.spanny.Spanny;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.smartcitizen.R;
import com.smartcitizen.commons.SmartCitizenApp;
import com.smartcitizen.controllers.DeviceController;
import com.smartcitizen.model.rest.Device;
import com.smartcitizen.model.rest.Sensor;
import com.smartcitizen.ui.widgets.RoundedBackgroundSpan;
import com.smartcitizen.ui.widgets.SensorView;
import retrofit.RetrofitError;

public class DeviceDetailActivity extends AppCompatActivity implements DeviceController.GetDeviceListener{

    private static final String TAG = DeviceDetailActivity.class.getName();

    private Device mDevice;

    @InjectView(R.id.kit_detail_title)
    TextView mKitTitle;

    @InjectView(R.id.kit_detail_kit_type)
    TextView mKitType;

    @InjectView(R.id.kit_detail_timestamp)
    TextView mKitTimestamp;

    @InjectView(R.id.kit_detail_user)
    TextView mKitUser;

    @InjectView(R.id.kit_detail_location)
    TextView mKitLocation;

    @InjectView(R.id.kit_detail_tags)
    TextView mTagsText;

    @InjectView(R.id.sensors_layout)
    LinearLayout mSensorsLayout;

    @InjectView(R.id.card_view)
    CardView mCardView;

    @InjectView(R.id.scrollView)
    ScrollView mScrollView;

    @InjectView(R.id.refreshLayout)
    SwipeRefreshLayout mRegreshLayout;

    @InjectView(R.id.kit_detail_header)
    LinearLayout mHeaderView;

    private boolean mIsHeaderViewHidden;

    private ViewTreeObserver.OnScrollChangedListener mScrollViewListener;

    public static Intent getCallingIntent(Context context, Device device) {

        Intent intent = new Intent(context, DeviceDetailActivity.class);
        intent.putExtra("device", device);
        return intent;
    }

    public static Intent getCallingIntent(Context context, int deviceId) {

        Intent intent = new Intent(context, DeviceDetailActivity.class);
        intent.putExtra("deviceId", deviceId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kit_detail);

        ButterKnife.inject(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegreshLayout.setColorSchemeResources(R.color.blue_smartcitizen, R.color.sensor_text_color, R.color.blue_smartcitizen_selected);

        mCardView.setVisibility(View.GONE);

        if (getIntent().hasExtra("device")) {
            Device device = getIntent().getParcelableExtra("device");
            init(device);
        } else if (getIntent().hasExtra("deviceId")) {
            int deviceId = getIntent().getIntExtra("deviceId", 0);
            mRegreshLayout.setRefreshing(true);
            DeviceController.getDevice(deviceId, new DeviceController.GetDeviceListener() {
                @Override
                public void onGetDevice(Device device) {
                    mRegreshLayout.setRefreshing(false);
                    init(device);
                }
                @Override
                public void onError(RetrofitError error) {
                    mRegreshLayout.setRefreshing(false);
                    Log.e(TAG, "onError "+error.toString());
                }
            });
        }

    }

    private void init(Device device) {
        mDevice = device;

        MixpanelAPI mixpanelAPI = SmartCitizenApp.getInstance().getMixpanelInstance();
        if (mixpanelAPI != null) {

            Map<String, Object> properties = new HashMap<>();
            properties.put("device_id", String.valueOf(mDevice.getId()));
            properties.put("device_name", mDevice.getName());

            mixpanelAPI.trackMap("User viewed kit detail", properties);
        }

        setDeviceViews();

        mRegreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mDevice != null)
                    DeviceController.getDevice(mDevice.getId(), DeviceDetailActivity.this);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDeviceViews() {
        setTextLabels();
        setSensorsView();
        setTags();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setScrollViewListener();//if device version > lollipop
    }

    @Override
    protected void onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mScrollView.getViewTreeObserver().removeOnScrollChangedListener(mScrollViewListener);

        super.onDestroy();
    }

    private void setScrollViewListener() {

        mIsHeaderViewHidden = false;
        mScrollViewListener = new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (mScrollView.getScrollY() > mHeaderView.getHeight()) {
                    if (!mIsHeaderViewHidden) {
                        getSupportActionBar().setElevation(12);
                        mIsHeaderViewHidden = true;
                    }

                } else {
                    if (mIsHeaderViewHidden) {
                        getSupportActionBar().setElevation(0);
                        mIsHeaderViewHidden = false;
                    }
                }
            }
        };

        mScrollView.getViewTreeObserver().addOnScrollChangedListener(mScrollViewListener);


    }

    private void setTags() {

        List<String> tags = mDevice.getSystemTags();

        if (tags == null || tags.size() == 0) {
            mTagsText.setVisibility(View.GONE);

        } else {
            int tagColor = getResources().getColor(R.color.tag_background_color);
            int tagTextColor = getResources().getColor(R.color.tag_text_color);
            mTagsText.setVisibility(View.VISIBLE);

            String space = "          ";

            Spanny spanny = new Spanny("  ");

            int numTags = tags.size();
            for (int i = 0; i<numTags; i++) {
                String tag = tags.get(i);
                if (tag.length()>0)
                    spanny.append(tag,  new RoundedBackgroundSpan(dp(12), 20, tagColor, tagTextColor));

                if (i < numTags - 1) {
                    spanny.append(space);
                }
            }

            mTagsText.setText(spanny);

        }
    }

    private int dp(int value) {
        return (int) Math.ceil(getResources().getDisplayMetrics().density * value);
    }

    private void setSensorsView() {
        if (mDevice.getDeviceData()!= null && mDevice.getDeviceData().getSensors().size() > 0) {

            mCardView.setVisibility(View.VISIBLE);

            //clean data (if update)
            int numChilds = mSensorsLayout.getChildCount();
            if (numChilds > 0) {
                mSensorsLayout.removeAllViews();
            }

            List<Sensor> sensors = mDevice.getDeviceData().getSensors();
            int numSensors = sensors.size();
            for (int i = 0; i<numSensors; i++) {
                Sensor sensor = sensors.get(i);

                SensorView sensorView = new SensorView(this);
                String prettyName = sensor.getPrettyName();
                sensorView.setSensoViewrName(sensor.getPrettyName(), sensor.getIcon(prettyName));
                float sensorValue = sensor.getValue();
                //String.format("%.2f", floatValue);
                sensorView.setSensorValue(String.format("%.1f", sensorValue) + " " + sensor.getUnit());

                if (i == numSensors - 1) {
                    sensorView.findViewById(R.id.sensor_separator).setVisibility(View.INVISIBLE);
                }

                mSensorsLayout.addView(sensorView);
            }
        }
    }

    private void setTextLabels() {
        mKitTitle.setText(mDevice.getName());
        mKitType.setText(mDevice.getKit().getName().toUpperCase());

        if (mDevice.getLastReadingAt() != null) {
            String updatedAt = "";
            updatedAt = ""+DateUtils.getRelativeTimeSpanString(mDevice.getLastReadingAt().getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            mKitTimestamp.setText(updatedAt);

        } else {
            mKitTimestamp.setText("N/A");
        }

        if (mDevice.getOwner() != null && mDevice.getOwner().getUsername() != null) {
            mKitUser.setText(mDevice.getOwner().getUsername());
        } else {
            mKitUser.setVisibility(View.GONE);
        }

        if (mDevice.getDeviceData() != null && mDevice.getDeviceData().getLocation() != null) {
            String location = mDevice.getDeviceData().getLocation().getCity();
            String country = mDevice.getDeviceData().getLocation().getCountry();
            if (country != null) {
                location += ", " + country;
            }
            mKitLocation.setText(location);
        } else {
            mKitLocation.setVisibility(View.GONE);
        }
    }

    //Update device info (refresh)
    @Override
    public void onGetDevice(Device device) {
        mRegreshLayout.setRefreshing(false);
        mDevice = device;
        setDeviceViews();
        Toast.makeText(this, "Device info updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(RetrofitError error) {
        mRegreshLayout.setRefreshing(false);
        Toast.makeText(this, "Error updating device. Error kind: "+error.getKind().name(), Toast.LENGTH_LONG).show();
    }
}
