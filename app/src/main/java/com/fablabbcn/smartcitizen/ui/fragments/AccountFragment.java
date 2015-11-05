package com.fablabbcn.smartcitizen.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fablabbcn.smartcitizen.ui.activities.DeviceDetailActivity;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.fablabbcn.smartcitizen.R;
import com.fablabbcn.smartcitizen.commons.CircleTransform;
import com.fablabbcn.smartcitizen.commons.Constants;
import com.fablabbcn.smartcitizen.commons.DeviceInfo;
import com.fablabbcn.smartcitizen.commons.SmartCitizenApp;
import com.fablabbcn.smartcitizen.commons.Utils;
import com.fablabbcn.smartcitizen.managers.SharedPreferencesManager;
import com.fablabbcn.smartcitizen.controllers.UserController;
import com.fablabbcn.smartcitizen.model.rest.CurrentUser;
import com.fablabbcn.smartcitizen.model.rest.Device;
import com.fablabbcn.smartcitizen.model.rest.UserLocation;
import com.fablabbcn.smartcitizen.ui.activities.AllUserDevicesActivity;
import com.fablabbcn.smartcitizen.ui.activities.MainActivity;
import com.fablabbcn.smartcitizen.ui.widgets.DeviceItemView;
import com.squareup.picasso.Picasso;

import retrofit.RetrofitError;


public class AccountFragment extends Fragment implements UserController.UserControllerListener/*, DeviceController.GetDeviceListener*/{

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

    private ArrayList<Device> mDevices;

    private final int MAX_DEVICES_TO_SHOW = 3;

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

        MixpanelAPI mixpanelAPI = SmartCitizenApp.getInstance().getMixpanelInstance();
        if (mixpanelAPI != null) {
            String userId = String.valueOf(mUserData.getId());
            MixpanelAPI.People people = mixpanelAPI.getPeople();
            if (userId != null){
                mixpanelAPI.identify(userId);
                people.identify(userId);
                people.initPushHandling(Constants.GC_SENDER_ID);
            }

            String username = mUserData.getUsername();
            if (username != null) people.set("Username", username);

            String email = currentUser.getEmail();
            if (email != null) people.set("Email", email);

            String city = String.valueOf(currentUser.getLocation().getCity());
            if (city != null) people.set("City", city);

            String country = String.valueOf(currentUser.getLocation().getCountry());
            if (city != null) people.set("Country", country);

            people.set("Locale", Locale.getDefault().getDisplayLanguage());
        }

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
            Picasso.with(getActivity()).load(mUserData.getAvatar()).transform(new CircleTransform()).error(R.drawable.user_avatar_placeholder).into(mAvatarView);
        }
    }

    private void setUpDevicesData() {
        mDevices = new ArrayList<>();
        mDevices.addAll(mUserData.getDevices());

        //test
        //devices = devices.subList(0, 2);

        if (mDevices == null || mDevices.size() == 0) {
            mDevicesLabel.setVisibility(View.GONE);
            mDevicesContainer.setVisibility(View.GONE);

        } else {
            String deviceLabel = getString(R.string.device_owner_label, mUserData.getUsername());
            deviceLabel.toUpperCase();
            mDevicesLabel.setText(deviceLabel);

            int maxDevices = (mDevices.size() > MAX_DEVICES_TO_SHOW) ? MAX_DEVICES_TO_SHOW : mDevices.size();

            Collections.sort(mDevices, Device.COMPARE_BY_UPDATED);

            Context ctx = getActivity();
            for (int i = maxDevices - 1; i >= 0; i--) {

                //add to the preview list
                //if (i < maxDevices) {

                DeviceItemView kitView = new DeviceItemView(ctx);

                final Device device = mDevices.get(i);

                Drawable drawable = Utils.getDrawable(getActivity(), R.drawable.device_icon);//do it outside the foor loop?
                if (device.getLocation() != null) {
                    kitView.setKitsData(device.getName(), device.getLocation().getAddress(), drawable);
                } else {
                    kitView.setKitsData(device.getName(), null, drawable);
                }
                kitView.updateTitleColor("todo");//todo no kit info from /v0/me endpoint (need slug info : sck 1.1....). How determine the title color?

                kitView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = DeviceDetailActivity.getCallingIntent(getActivity(), device.getId());
                        startActivity(intent);
                    }
                });

                mDevicesContainer.addView(kitView, 0);

            }

            if (mDevices.size() <= MAX_DEVICES_TO_SHOW) {
                mButtonViewKits.setVisibility(View.GONE);
            } else {
                mButtonViewKits.setVisibility(View.VISIBLE);
            }

        }

    }

    @OnClick(R.id.button_view_all_kits)
    public void goToAllKits() {
        if (mUserData != null && mUserData.getDevices()!= null && mUserData.getDevices().size() > 0) {

            MixpanelAPI mixpanelAPI = SmartCitizenApp.getInstance().getMixpanelInstance();
            if (mixpanelAPI != null) {
                mixpanelAPI.track("User tapped ‘view all kits’");
            }

            Intent intent = AllUserDevicesActivity.getCallingIntent(getActivity(), mUserData.getUsername(), mDevices);
            startActivity(intent);
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

        MixpanelAPI mixpanelAPI = SmartCitizenApp.getInstance().getMixpanelInstance();
        if (mixpanelAPI != null) {
            mixpanelAPI.track("User logged out");
            //need to flush before we reset the info?
            mixpanelAPI.flush();

            mixpanelAPI.reset();

        }
    }
}
