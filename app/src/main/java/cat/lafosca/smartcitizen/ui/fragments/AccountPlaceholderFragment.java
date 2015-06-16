package cat.lafosca.smartcitizen.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cat.lafosca.smartcitizen.R;

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

        String infoText = getString(R.string.account_placeholder_info);
        String infoTextLink = getString(R.string.account_placeholder_info_link);

        mTextInfo.setText(infoText + infoTextLink);

        return view;
    }


}
