package cat.lafosca.smartcitizen.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.BoundingBox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.UserLocationOverlay;
import com.mapbox.mapboxsdk.util.GeoUtils;
import com.mapbox.mapboxsdk.views.MapView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.commons.Utils;
import cat.lafosca.smartcitizen.controllers.DeviceController;
import cat.lafosca.smartcitizen.model.rest.BaseDevice;
import cat.lafosca.smartcitizen.ui.widgets.CustomInwoWindow;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MapFragment extends Fragment implements DeviceController.GetWorldMapDevicesListener{

    private static final String TAG = MapFragment.class.getSimpleName();

    @InjectView(R.id.mapview)   MapView mMapView;

    @InjectView(R.id.mapProgress) ProgressBar mProgress;

    private LatLng userLocationPoint = new LatLng(41.394401, 2.197694); //barcelona todo: Remove this

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.inject(this, view);

        /*final Resources res = getResources();
        final Bitmap clusterBitmpap = BitmapFactory.decodeResource(res, R.drawable.custom_marker);*/

        mMapView.setUserLocationEnabled(true);
        mMapView.setUserLocationTrackingMode(UserLocationOverlay.TrackingMode.NONE);

        mProgress.setVisibility(View.VISIBLE);
        DeviceController.getWorldMapDevices(this);//call in onCreate ?

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView.setClusteringEnabled(
                true, //enabled/disabled
                //draw cluster listener
//                new ClusterMarker.OnDrawClusterListener() {
//                    @Override
//                    public Drawable drawCluster(ClusterMarker clusterMarker) {
//                        NumberBitmapDrawable customCluster = new NumberBitmapDrawable(res, clusterBitmpap);
//                        customCluster.setCount(clusterMarker.getMarkersReadOnly().size());
//                        return customCluster;
//                        //return null;
//                    }
//                },
                null,
                0 // min zoom level
        );

    }

    @OnClick(R.id.userLocationButton)
    void GoToUserLocation() {

        if (Utils.isGPSEnabled(getActivity()) || mMapView.getUserLocation() != null) {
            if (!mMapView.isUserLocationVisible() && mMapView.getUserLocation()!=null) {
                mMapView.setUserLocationRequiredZoom(5.0F);
                mMapView.goToUserLocation(true);
            }
        } else {
            final Context context = getActivity();
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);

            dialog.setTitle(context.getResources().getString(R.string.gps_not_enabled));
            dialog.setMessage(context.getResources().getString(R.string.ask_activate_gps));

            dialog.setPositiveButton(context.getResources().getString(R.string.activate), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(context.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                }
            });
            dialog.show();
        }
    }

    //todo Remove/refactor
    @Override
    public void onGetDevices(List<BaseDevice> devices) {
        int numOfDevices = devices.size();
        if (numOfDevices > 0) {
            new MarkerTask(getActivity()).execute(devices);
        }
    }

    @Override
    public void onError(RetrofitError error) {
        mProgress.setVisibility(View.GONE);

        if (getActivity()!= null && this.isAdded())
            Toast.makeText(getActivity(), "Error getting kits. Error kind: "+error.getKind().name(), Toast.LENGTH_LONG).show();

        StringBuilder sb = new StringBuilder();
        sb.append("ERROR " + error.getUrl() + " kind: " + error.getKind().name());
        Response response = error.getResponse();
        if (response != null) {
            sb.append(response.getReason() +" "+ response.getStatus()+"\nBody: "+response.getBody().toString());
        }

        Log.e(TAG, sb.toString());
    }


    private class MarkerTask extends AsyncTask<List<BaseDevice>, Void, Boolean> {

        private Drawable customMarkerDrawableOnline;
        private Drawable customMarkerDrawableOffline;
        private List<Marker> markers;
        private List<LatLng> positions;


        public MarkerTask(Context context) {
            customMarkerDrawableOffline = Utils.getDrawable(context, R.drawable.marker_offline);
            customMarkerDrawableOnline = Utils.getDrawable(context, R.drawable.marker_online);
            markers = new ArrayList<Marker>();
            positions = new ArrayList<LatLng>();
        }

        @Override
        protected Boolean doInBackground(List<BaseDevice>... lists) {
            List<BaseDevice> list = lists[0];

            int numOfDevices = list.size();
            if (numOfDevices > 0) {
                Activity activity = getActivity();
                for (int i = 0; i< numOfDevices; i++) {
                    BaseDevice device = list.get(i);

                    if (device.getLatitude() == null || device.getLongitude() == null)
                        continue;

                    LatLng position = new LatLng(device.getLatitude(), device.getLongitude());
                    positions.add(position);
                    Marker marker = new Marker(mMapView, device.getName(), " ", position);
                    if (device.getSystemTags().contains("offline")) {
                        marker.setMarker(customMarkerDrawableOffline);
                    } else {
                        marker.setMarker(customMarkerDrawableOnline);
                    }
                    marker.setToolTip( new CustomInwoWindow(mMapView, device, activity));
                    markers.add(marker);
                }
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            mProgress.setVisibility(View.GONE);

            if (success && mMapView != null) {
                mMapView.addMarkers(markers);
                BoundingBox bbn = GeoUtils.findBoundingBoxForGivenLocations(positions, 5.0);

                mMapView.zoomToBoundingBox(bbn, true, true);
            }
        }
    }
}
