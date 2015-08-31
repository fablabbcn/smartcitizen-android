package cat.lafosca.smartcitizen.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.commons.DeviceInfo;
import cat.lafosca.smartcitizen.commons.Utils;
import cat.lafosca.smartcitizen.controllers.DeviceController;
import cat.lafosca.smartcitizen.managers.SharedPreferencesManager;
import cat.lafosca.smartcitizen.controllers.UserController;
import cat.lafosca.smartcitizen.model.rest.CurrentUser;
import cat.lafosca.smartcitizen.model.rest.Device;
import cat.lafosca.smartcitizen.model.rest.UserLocation;
import cat.lafosca.smartcitizen.ui.activities.AllUserDevicesActivity;
import cat.lafosca.smartcitizen.ui.activities.DeviceDetailActivity;
import cat.lafosca.smartcitizen.ui.activities.MainActivity;
import cat.lafosca.smartcitizen.ui.widgets.DeviceItemView;
import retrofit.RetrofitError;


public class AccountFragment extends Fragment implements UserController.UserControllerListener, DeviceController.GetDeviceListener{

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

    //this map contains full device info associated to the user ("/v0/devices/{device_id}" contains more device info than "/v0/me"
    private Map mDevicesInfo;

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
    public void onGetUserData(CurrentUser currentUser) {
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
        List<Device> devices = mUserData.getDevices();

        mDevicesInfo = new HashMap(devices.size());

        //test
        //devices = devices.subList(0, 2);

        if (devices == null || devices.size() == 0) {
            mDevicesLabel.setVisibility(View.GONE);
            mDevicesContainer.setVisibility(View.GONE);

        } else {
            String deviceLabel = getString(R.string.device_owner_label, mUserData.getUsername());
            deviceLabel.toUpperCase();
            mDevicesLabel.setText(deviceLabel);

            int maxDevices = (devices.size() > MAX_DEVICES) ? MAX_DEVICES : devices.size();

            Collections.sort(devices, Device.COMPARE_BY_UPDATED);

            Context ctx = getActivity();
            //for (int i = maxDevices - 1; i >= 0; i--) {
            for (int i = devices.size() - 1; i >= 0; i--) {

                //add to the preview list
                if (i < maxDevices) {

                    DeviceItemView kitView = new DeviceItemView(ctx);

                    Device device = devices.get(i);

                    kitView.setTag(device.getId());

                    Drawable drawable = Utils.getDrawable(getActivity(), R.drawable.device_icon);//do it outside the foor loop?
                    kitView.setKitsData(device.getName(), "loading info", drawable);

                    mDevicesContainer.addView(kitView, 0);
                }

                DeviceController.getDevice(mUserData.getDevices().get(i).getId(), this);//need to do this api call for every device to get the extra info we don't get from callig /v0/me
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
        Context ctx = getActivity();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", "support@smartcitizen.me", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, ctx.getString(R.string.support_mail_subject));

        String body = ctx.getString(R.string.support_mail_body,
                DeviceInfo.getDeviceName(),
                DeviceInfo.getAndroidVersion(),
                DeviceInfo.getAppVersion(ctx)
        );

        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(emailIntent, "Send email "));
    }

    @OnClick(R.id.button_logout)
    public void logout() {
        SharedPreferencesManager.getInstance().setUserLoggedIn("");
        ((MainActivity)getActivity()).refreshAccountView(false);
    }

    @OnClick(R.id.button_view_all_kits)
    public void goToAllKits() {
        if (mUserData != null && mUserData.getDevices()!= null && mUserData.getDevices().size() > 0) {
            ArrayList<Device> devices = new ArrayList<>(mDevicesInfo.values());
            Collections.sort(devices, Device.COMPARE_BY_UPDATED);
            Intent intent = AllUserDevicesActivity.getCallingIntent(getActivity(), mUserData.getUsername(), devices);
            startActivity(intent);
        }
    }

    @Override
    public void onGetDevice(final Device device) {

        View view = mDevicesContainer.findViewWithTag(device.getId());
        if (view instanceof DeviceItemView) {
            ((DeviceItemView)view).updateLocationText(device.getDeviceData().getLocation().getPrettyLocation());
            ((DeviceItemView)view).updateTitleColor(device.getKit().getSlug());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = DeviceDetailActivity.getCallingIntent(getActivity(), device);
                    startActivity(intent);
                }
            });

        }

        if (mDevicesInfo != null)
            mDevicesInfo.put(device.getId(), device);
    }

    @Override
    public void onError(RetrofitError error) {

    }
}
