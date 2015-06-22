package cat.lafosca.smartcitizen.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.controllers.SharedPreferencesController;
import cat.lafosca.smartcitizen.controllers.UserController;
import cat.lafosca.smartcitizen.model.rest.CurrentUser;
import retrofit.RetrofitError;


public class AccountFragment extends Fragment implements UserController.UserControllerListener{

    private static final String TAG = AccountFragment.class.getSimpleName();

    @InjectView(R.id.account_text_info)
    TextView mTextInfo;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (SharedPreferencesController.getInstance().isUserLoggedIn()) {
            mTextInfo.setText("User loged in\n"+"acces token "+SharedPreferencesController.getInstance().getUserToken());

        } else {
            mTextInfo.setText("User not logged in, but I shouldn't see it anyways...");
        }
    }

    @Override
    public void onGetUserData(CurrentUser currentUser) {
        Log.i(TAG, currentUser.toString());
    }

    @Override
    public void onErrorGetUserData(RetrofitError error) {
        Log.e(TAG, error.toString());
    }
}
