package cat.lafosca.smartcitizen.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.controllers.SharedPreferencesController;
import cat.lafosca.smartcitizen.controllers.UserController;
import cat.lafosca.smartcitizen.model.rest.CurrentUser;
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
    Button bButtonViewKits;

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
        Log.i(TAG, currentUser.toString());
    }

    @Override
    public void onErrorGetUserData(RetrofitError error) {
        Log.e(TAG, error.toString());
    }

    @OnClick(R.id.button_contact_suport)
    public void contactSupportTeam() {

    }

    @OnClick(R.id.button_logout)
    public void logout() {

    }
}
