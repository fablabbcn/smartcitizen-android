package com.smartcitizen.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcitizen.R;
import com.smartcitizen.managers.SharedPreferencesManager;

/**
 * Created by ferran on 19/06/15.
 */
public class AccountRootFragment extends Fragment {

    public AccountRootFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_root_account, container, false);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
		/*
		 * When this container fragment is created, we fill it with a fragment depending on user session
		 */
        Fragment accountFragment = (SharedPreferencesManager.getInstance().isUserLoggedIn()) ? AccountFragment.newInstance() : AccountPlaceholderFragment.newInstance();

        transaction.replace(R.id.root_frame, accountFragment);

        transaction.commit();

        return view;
    }
}
