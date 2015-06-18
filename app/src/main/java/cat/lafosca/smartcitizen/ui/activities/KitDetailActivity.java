package cat.lafosca.smartcitizen.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.binaryfork.spanny.Spanny;

import java.text.ParseException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.commons.PrettyTimeHelper;
import cat.lafosca.smartcitizen.model.rest.Device;
import cat.lafosca.smartcitizen.model.rest.Sensor;
import cat.lafosca.smartcitizen.ui.widgets.RoundedBackgroundSpan;
import cat.lafosca.smartcitizen.ui.widgets.SensorView;

public class KitDetailActivity extends AppCompatActivity {

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

    @InjectView(R.id.scrollView)
    ScrollView mScrollView;

    @InjectView(R.id.kit_detail_header)
    LinearLayout mHeaderView;

    private boolean mIsHeaderViewHidden;

    private ViewTreeObserver.OnScrollChangedListener mScrollViewListener;

    public static Intent getCallingIntent(Context context, Device device) {

        Intent intent = new Intent(context, KitDetailActivity.class);
        intent.putExtra("Device", device);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kit_detail);

        ButterKnife.inject(this);

        mDevice = getIntent().getParcelableExtra("Device");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

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

    private void init() {
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
        String status = mDevice.getDeviceInfo().getStatus();
        String exposure = mDevice.getDeviceData().getLocation().getExposure();
        //moar tags?

        if (status == null && exposure == null) {
            mTagsText.setVisibility(View.GONE);

        } else {
            int tagColor = getResources().getColor(R.color.tag_background_color);
            int tagTextColor = getResources().getColor(R.color.tag_text_color);
            mTagsText.setVisibility(View.VISIBLE);

            int corners = dp(8);
            String space = "     ";

            //todo: build tags dinamically?
            Spanny spanny = new Spanny(status, new RoundedBackgroundSpan(corners, tagColor, tagTextColor))
                    .append(space)
                    .append(exposure, new RoundedBackgroundSpan(corners, tagColor, tagTextColor));

            mTagsText.setText(spanny);

        }
    }

    private int dp(int value) {
        return (int) Math.ceil(getResources().getDisplayMetrics().density * value);
    }

    private void setSensorsView() {
        if (mDevice.getDeviceData()!= null && mDevice.getDeviceData().getSensors().size() > 0) {
            List<Sensor> sensors = mDevice.getDeviceData().getSensors();
            int numSensors = sensors.size();
            for (int i = 0; i<numSensors; i++) {
                Sensor sensor = sensors.get(i);

                SensorView sensorView = new SensorView(this);
                sensorView.setSensoViewrName(sensor.getPrettyName(), sensor.getIcon());
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
        mKitTitle.setText(mDevice.getDeviceInfo().getName());
        mKitType.setText(mDevice.getKit().getName().toUpperCase());

        if (mDevice.getDeviceInfo().getUpdatedAt() != null) {
            String updatedAt = "";
            try {
                updatedAt = PrettyTimeHelper.getInstance().getPrettyTime(mDevice.getDeviceInfo().getUpdatedAt());
                mKitTimestamp.setText(updatedAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            mKitTimestamp.setVisibility(View.GONE);
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
}
