package com.smartcitizen.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.smartcitizen.R;
import com.smartcitizen.commons.SmartCitizenApp;
import com.smartcitizen.managers.SharedPreferencesManager;
import com.smartcitizen.rest.RestController;
import com.smartcitizen.ui.activities.LoginActivity;
import com.smartcitizen.ui.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountPlaceholderFragment extends Fragment {

    @InjectView(R.id.account_placeholder_info)
    TextView mTextInfo;

    public static AccountPlaceholderFragment newInstance() {
        AccountPlaceholderFragment fragment = new AccountPlaceholderFragment();
        return fragment;
    }

    public AccountPlaceholderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_placeholder, container, false);
        ButterKnife.inject(this, view);

        setSpannableText();

        return view;
    }

    private void setSpannableText() {


        String infoText = getString(R.string.account_placeholder_info);
        String infoTextLink = getString(R.string.account_placeholder_info_link);

        String stringFormatted = infoText +" "+ infoTextLink;

        SpannableString spannableString = SpannableString.valueOf(stringFormatted);

        spannableString.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.account_text_2)),
                stringFormatted.length() - infoTextLink.length(), //start
                stringFormatted.length(), //end
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(
                new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        MixpanelAPI mixpanelAPI = SmartCitizenApp.getInstance().getMixpanelInstance();
                        if (mixpanelAPI != null) {
                            mixpanelAPI.track("User tapped ‘create your account’");
                        }

                        String url = getString(R.string.sign_up);
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));

                        startActivity(i);
                    }
                },
                stringFormatted.length() - infoTextLink.length(), //start
                stringFormatted.length(), //end
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mTextInfo.setText(spannableString, TextView.BufferType.SPANNABLE);
        mTextInfo.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @OnClick(R.id.account_placeholder_login)
    public void login() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);

        startActivityForResult(intent, LoginActivity.LOGIN_OK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED) {
            return;

        }else {
            //if (resultCode == Activity.RESULT_OK) { //resultCode == requestCode Why?
            if (requestCode == LoginActivity.LOGIN_OK) {
                String accessToken = SharedPreferencesManager.getInstance().getUserToken();
                if (getActivity() != null && !accessToken.isEmpty()) {
                    RestController.getInstance().updateAuthRestController(accessToken);
                    ((MainActivity) getActivity()).refreshAccountView(true);
                }
            }
            //}
        }
    }
}
