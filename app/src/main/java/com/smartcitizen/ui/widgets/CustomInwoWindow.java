package com.smartcitizen.ui.widgets;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.views.InfoWindow;
import com.mapbox.mapboxsdk.views.MapView;

import java.lang.ref.WeakReference;
import java.util.Date;

import com.smartcitizen.R;
import com.smartcitizen.commons.CountryCode;
import com.smartcitizen.controllers.DeviceController;
import com.smartcitizen.model.rest.BaseDevice;
import com.smartcitizen.model.rest.Device;
import com.smartcitizen.ui.activities.DeviceDetailActivity;
import com.smartcitizen.ui.activities.MainActivity;
import retrofit.RetrofitError;

/**
 * Created by ferran on 08/06/15.
 */
public class CustomInwoWindow extends InfoWindow {

    private WeakReference<Context> mActivity;

    private BaseDevice mBaseDevice;
    private Device mDevice;

    private TextView tvUpdated;


    public CustomInwoWindow(MapView mapView, BaseDevice device, Activity activity) {
        super(R.layout.infowindow_custom, mapView);

        mActivity = new WeakReference<Context>(activity);

        mBaseDevice = device;
    }

    @Override
    public void onOpen(Marker overlayItem) {

        if (mActivity.get() instanceof MainActivity) {
            mDevice = ((MainActivity) mActivity.get()).getDevice(mBaseDevice.getId());
        }

        //BASE DATA
        //name
        String name = mBaseDevice.getName();
        ((TextView) mView.findViewById(R.id.info_window_title)).setText(name);

        //location
        String country = CountryCode.getInstance().getCountryNameByCode(mBaseDevice.getCountryCode());
        String location = mBaseDevice.getCity() + ", " + country;
        ((TextView) mView.findViewById(R.id.info_window_location)).setText(location);

        //description
        String description = mBaseDevice.getDescription();
        TextView tvDescription = ((TextView) mView.findViewById(R.id.info_window_kit_type));
        if (description == null) {
            tvDescription.setVisibility(View.GONE);
        } else {
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText(mBaseDevice.getDescription());
        }

        tvUpdated = (TextView) mView.findViewById(R.id.info_window_timestamp);
        if (mDevice == null) {
            tvUpdated.setText("Loading");
        }

        if (mDevice != null) {

            setLastReadingData();
            mView.findViewById(R.id.info_window_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.get().startActivity( DeviceDetailActivity.getCallingIntent(mActivity.get(), mDevice) );
                    //mActivity.get().startActivity(DeviceDetailActivity.getCallingIntent(mActivity.get(), mBaseDevice.getId()));

                    // Still close the InfoWindow though
                    close();
                }
            });


        } else {
            //setup base info & call api to get full device data
            DeviceController.getDevice(mBaseDevice.getId(), new DeviceController.GetDeviceListener() {
                @Override
                public void onGetDevice(Device device) {
                    if (mActivity.get() instanceof MainActivity) {
                        ((MainActivity) mActivity.get()).addDevice(device);
                    }

                    mDevice = device;
                    setLastReadingData();

                    mView.findViewById(R.id.info_window_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mActivity.get().startActivity(DeviceDetailActivity.getCallingIntent(mActivity.get(), mDevice));
                            //mActivity.get().startActivity(DeviceDetailActivity.getCallingIntent(mActivity.get(), mBaseDevice.getId()));

                            // Still close the InfoWindow though
                            close();
                        }
                    });
                }

                @Override
                public void onError(RetrofitError error) {
                    Log.e("CustomInfoWindow", "error getting device "+error.toString());
                }
            });
        }


        mView.findViewById(R.id.info_window_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mActivity.get().startActivity( DeviceDetailActivity.getCallingIntent(mActivity.get(), mBaseDevice) );
                mActivity.get().startActivity( DeviceDetailActivity.getCallingIntent(mActivity.get(), mBaseDevice.getId()) );

                // Still close the InfoWindow though
                close();
            }
        });

    }

    private void setLastReadingData() {
        //setup full info
        String lastReading = "";
        Date lastR = mDevice.getLastReadingAt();
        if (lastR == null) {
            lastReading = "N/A";
        } else {
            //lastReading = ""+DateUtils.getRelativeDateTimeString(mActivity.get(), lastR.getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, ); // X seconds/minuts ago, hh:mm
            lastReading = ""+DateUtils.getRelativeTimeSpanString(lastR.getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        }

        tvUpdated.setText(lastReading);
    }
}
