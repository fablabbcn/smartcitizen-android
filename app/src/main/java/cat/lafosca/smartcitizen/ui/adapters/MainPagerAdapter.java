package cat.lafosca.smartcitizen.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.ui.fragments.AccountRootFragment;
import cat.lafosca.smartcitizen.ui.fragments.MapFragment;

/**
 * Created by ferran on 28/05/15.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        mTitles = context.getResources().getStringArray(R.array.tabs_titles);
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MapFragment.newInstance();
            case 1:
                return new AccountRootFragment();

            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
