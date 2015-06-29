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
import cat.lafosca.smartcitizen.model.rest.Device;
import cat.lafosca.smartcitizen.commons.PrettyTimeHelper;
import cat.lafosca.smartcitizen.ui.activities.KitDetailActivity;

/**
 * Created by ferran on 08/06/15.
 */
public class CustomInwoWindow extends InfoWindow {

    private WeakReference<Context> mActivity;

    private Device mDevice;

    public CustomInwoWindow(MapView mapView, Device device, Activity activity) {
        super(R.layout.infowindow_custom, mapView);

        mActivity = new WeakReference<Context>(activity);

        mDevice = device;
    }

    @Override
    public void onOpen(Marker overlayItem) {

        String name = mDevice.getName();
        String kitName = mDevice.getKit().getName();
        kitName = kitName.toUpperCase();//xml doesn't work?
        String updatedAt = "";
        try {
            updatedAt = PrettyTimeHelper.getInstance().getPrettyTime(mDevice.getUpdatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String location = mDevice.getDeviceData().getLocation().getPrettyLocation();

        //Log.i("window", name+"\n"+kitName+"\n"+updatedAt+"\n"+location);

        ((TextView) mView.findViewById(R.id.info_window_title)).setText(name);
        ((TextView) mView.findViewById(R.id.info_window_kit_type)).setText(kitName);
        ((TextView) mView.findViewById(R.id.info_window_timestamp)).setText(updatedAt);
        ((TextView) mView.findViewById(R.id.info_window_location)).setText(location);


        mView.findViewById(R.id.info_window_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.get().startActivity( KitDetailActivity.getCallingIntent(mActivity.get(), mDevice) );

                // Still close the InfoWindow though
                close();
            }
        });

    }
}
