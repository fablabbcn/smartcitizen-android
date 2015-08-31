package cat.lafosca.smartcitizen.ui.widgets;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.views.InfoWindow;
import com.mapbox.mapboxsdk.views.MapView;

import java.lang.ref.WeakReference;
import java.text.ParseException;

import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.commons.CountyCode;
import cat.lafosca.smartcitizen.model.rest.BaseDevice;
import cat.lafosca.smartcitizen.commons.PrettyTimeHelper;
import cat.lafosca.smartcitizen.ui.activities.DeviceDetailActivity;

/**
 * Created by ferran on 08/06/15.
 */
public class CustomInwoWindow extends InfoWindow {

    private WeakReference<Context> mActivity;

    private BaseDevice mDevice;

    public CustomInwoWindow(MapView mapView, BaseDevice device, Activity activity) {
        super(R.layout.infowindow_custom, mapView);

        mActivity = new WeakReference<Context>(activity);

        mDevice = device;
    }

    @Override
    public void onOpen(Marker overlayItem) {

        String name = mDevice.getName();
        //String kitName = mDevice.getKit().getName();
        //kitName = kitName.toUpperCase();//xml doesn't work?
        String updatedAt = "";
        try {
            updatedAt = PrettyTimeHelper.getInstance().getPrettyTime(mDevice.getAddedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //String location = mDevice.getDeviceData().getLocation().getPrettyLocation();
        String country = CountyCode.getInstance().getCountryNameByCode(mDevice.getCountryCode());
        String location = mDevice.getCity() + ", " + country;

        ((TextView) mView.findViewById(R.id.info_window_title)).setText(name);
        String description = mDevice.getDescription();
        TextView tvDescription = ((TextView) mView.findViewById(R.id.info_window_kit_type));
        if (description == null) {
            tvDescription.setVisibility(View.GONE);
        } else {
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText(mDevice.getDescription());
        }
        ((TextView) mView.findViewById(R.id.info_window_timestamp)).setText(updatedAt);
        ((TextView) mView.findViewById(R.id.info_window_location)).setText(location);


        mView.findViewById(R.id.info_window_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mActivity.get().startActivity( DeviceDetailActivity.getCallingIntent(mActivity.get(), mDevice) );
                mActivity.get().startActivity( DeviceDetailActivity.getCallingIntent( mActivity.get(), mDevice.getId()) );

                // Still close the InfoWindow though
                close();
            }
        });

    }
}
