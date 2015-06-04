package cat.lafosca.smartcitizen.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.geometry.BoundingBox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.util.GeoUtils;
import com.mapbox.mapboxsdk.views.MapView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.controllers.KitsController;
import cat.lafosca.smartcitizen.model.rest.Device;

public class MapFragment extends Fragment implements KitsController.KitsControllerListenr {

    @InjectView(R.id.mapview) MapView mMapView;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.inject(this, view);

        KitsController.getKits(this);//call in onCreate ?

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*mMapView.setMinZoomLevel(mMapView.getTileProvider().getMinimumZoomLevel());
        mMapView.setMaxZoomLevel(mMapView.getTileProvider().getMaximumZoomLevel());
        mMapView.setCenter(mMapView.getTileProvider().getCenterCoordinate());
        mMapView.setZoom(0);

        // Show user location (purposely not in follow mode)
        mMapView.setUserLocationEnabled(true);

        LatLng userLocation = mMapView.getUserLocation();
        boolean imVisible = mMapView.isUserLocationVisible();
        mMapView.goToUserLocation(true);
        int i = 0;*/

    }

    @Override
    public void onGetKits(List<Device> devices) {
        int numOfDevices = devices.size();
        if (numOfDevices > 0) {
            List<Marker> markers = new ArrayList<Marker>();
            List<LatLng> positions = new ArrayList<LatLng>();
            for (int i = 0; i< numOfDevices; i++) {
                Device device = devices.get(i);

                LatLng position = new LatLng(device.getData().getLocation().getLatitude(), device.getData().getLocation().getLongitude());
                positions.add(position);
                Marker marker = new Marker(mMapView, device.getName(), "", position);
                markers.add(marker);
            }
            mMapView.addMarkers(markers);
            BoundingBox bbn = GeoUtils.findBoundingBoxForGivenLocations(positions, 10.0);

            mMapView.zoomToBoundingBox(bbn, true, true);

            int i = 0;

        }
    }
}
