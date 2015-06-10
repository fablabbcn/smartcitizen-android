package cat.lafosca.smartcitizen.ui.widgets;

import android.widget.TextView;

import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.views.InfoWindow;
import com.mapbox.mapboxsdk.views.MapView;

import java.text.ParseException;

import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.model.rest.Device;
import cat.lafosca.smartcitizen.util.PrettyTimeHelper;

/**
 * Created by ferran on 08/06/15.
 */
public class CustomInwoWindow extends InfoWindow {

    private Device mDevice;

    //public CustomInwoWindow(int layoutResId, MapView mapView, Device device) {
    public CustomInwoWindow(MapView mapView, Device device) {
        super(R.layout.infowindow_custom, mapView);

        mDevice = device;

        /*setOnTouchListener(new View.OnTouchListener() {
             @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Demonstrate custom onTouch() control
                    Toast.makeText(mView.getContext(), R.string.customInfoWindowOnTouchMessage, Toast.LENGTH_SHORT).show();

                    // Still close the InfoWindow though
                    close();
                }

                // Return true as we're done processing this event
                return true;
            }
        });*/
    }

    @Override
    public void onOpen(Marker overlayItem) {

        String name = mDevice.getName();
        String kitName= mDevice.getKit().getName();
        kitName = kitName.toUpperCase();//xml doesn't work?
        String updatedAt = "";
        try {
            updatedAt = PrettyTimeHelper.getInstance().getPrettyTime(mDevice.getUpdatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String location = mDevice.getData().getLocation().getCity();
        String country = mDevice.getData().getLocation().getCountry();
        if (country != null) {
            location += ", "+country;
        }

        //Log.i("window", name+"\n"+kitName+"\n"+updatedAt+"\n"+location);

        ((TextView) mView.findViewById(R.id.info_window_title)).setText(name);
        ((TextView) mView.findViewById(R.id.info_window_kit_type)).setText(kitName);
        ((TextView) mView.findViewById(R.id.info_window_timestamp)).setText(updatedAt);
        ((TextView) mView.findViewById(R.id.info_window_location)).setText(location);

    }
}
