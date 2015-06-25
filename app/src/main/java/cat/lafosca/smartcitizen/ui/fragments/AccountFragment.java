package cat.lafosca.smartcitizen.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.controllers.UserController;
import cat.lafosca.smartcitizen.model.rest.CurrentUser;
import cat.lafosca.smartcitizen.model.rest.DeviceInfo;
import cat.lafosca.smartcitizen.model.rest.UserLocation;
import cat.lafosca.smartcitizen.ui.activities.AllUserDevicesActivity;
import cat.lafosca.smartcitizen.ui.widgets.KitView;
import retrofit.RetrofitError;


public class AccountFragment extends Fragment implements UserController.UserControllerListener{

    private static final String TAG = AccountFragment.class.getSimpleName();

    @InjectView(R.id.userAvatar)
    ImageView mAvatarView;

    @InjectView(R.id.userName)
    TextView mUserName;

    @InjectView(R.id.userLocation)
    TextView mUserLocation;

    @InjectView(R.id.devices_label)
    TextView mDevicesLabel;

    @InjectView(R.id.devices_container)
    LinearLayout mDevicesContainer;

    @InjectView(R.id.button_view_all_kits)
    TextView mButtonViewKits;

    private CurrentUser mUserData;

    private final int MAX_DEVICES = 3;

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.inject(this, v);
        UserController.getCurrentUserData(this);
        return v;
    }

    @Override
    public void onGetUserData(CurrentUser currentUser) { //TODO fix this
        //Log.i(TAG, currentUser.toString());

        mUserData = currentUser;

        setUpProfileData();
        setUpDevicesData();
    }

    @Override
    public void onErrorGetUserData(RetrofitError error) {
        Log.e(TAG, error.toString());
    }

    private void setUpProfileData() {
        mUserName.setText(mUserData.getUsername());

        UserLocation location = mUserData.getLocation();
        if (location != null && location.getCity() != null && location.getCountry() != null) {
            mUserLocation.setText(location.getCity() + ", " + location.getCountry());
        } else {
            mUserLocation.setVisibility(View.GONE);
        }

        if (mUserData.getAvatar() != null) {
            //TODO Picasso
            //mAvatarView.setImage
        }
    }

    private void setUpDevicesData() {
        List<DeviceInfo> devices = mUserData.getDevices();

        if (devices == null || devices.size() == 0) {
            mDevicesLabel.setVisibility(View.GONE);
            mDevicesContainer.setVisibility(View.GONE);

        } else {
            String deviceLabel = getString(R.string.device_owner_label, mUserData.getUsername());
            deviceLabel.toUpperCase();
            mDevicesLabel.setText(deviceLabel);

            int maxDevices = (devices.size() > MAX_DEVICES) ? MAX_DEVICES : devices.size();

            Context ctx = getActivity();
            for (int i = maxDevices - 1; i >= 0; i--) {
                KitView kitView = new KitView(ctx);

                DeviceInfo device = devices.get(i);

                //not location at this moment
                /*StringBuilder sb = new StringBuilder();
                DeviceLocation location = device.getDeviceData().getLocation();
                if (location != null) {
                    sb.append(device.getDeviceData().getLocation().getCity());
                    sb.append(", "+device.getDeviceData().getLocation().getCountry());
                }

                kitView.setKitsData(devices.get(i).getKit().getName(), sb.toString(), 0);*/

                kitView.setKitsData(device.getName(), "location", 0);


                /*if (i == maxDevices - 1) {
                    kitView.findViewById(R.id.kit_row_separator).setVisibility(View.GONE);
                }*/

                mDevicesContainer.addView(kitView, 0);
            }

            if (devices.size() <= MAX_DEVICES) {
                mButtonViewKits.setVisibility(View.GONE);
            } else {
                mButtonViewKits.setVisibility(View.VISIBLE);
            }

        }

    }

    @OnClick(R.id.button_contact_suport)
    public void contactSupportTeam() {

    }

    @OnClick(R.id.button_logout)
    public void logout() {

    }

    @OnClick(R.id.button_view_all_kits)
    public void goToAllKits() {
        if (mUserData != null && mUserData.getDevices()!= null && mUserData.getDevices().size() > 0) {
            Intent intent = AllUserDevicesActivity.getCallingIntent(getActivity(), mUserData);
            startActivity(intent);
        }
    }
}
