package cat.lafosca.smartcitizen.ui.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.commons.Constants;
import cat.lafosca.smartcitizen.commons.SmartCitizenApp;
import cat.lafosca.smartcitizen.commons.Utils;
import cat.lafosca.smartcitizen.model.rest.Device;
import cat.lafosca.smartcitizen.ui.adapters.MainPagerAdapter;
import cat.lafosca.smartcitizen.ui.fragments.AccountFragment;
import cat.lafosca.smartcitizen.ui.fragments.AccountPlaceholderFragment;
import cat.lafosca.smartcitizen.ui.widgets.CustomViewPager;



public class MainActivity extends AppCompatActivity {

    //private SlidingTabLayout tabs;
    @InjectView(R.id.tabs)
    TabLayout tabs;

    private MainPagerAdapter mTabAdapter;

    @InjectView(R.id.pager)
    CustomViewPager mViewPager;

    private Map<Integer, Device> mMapDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initMixPanel();
        /*mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);*/
        mMapDevices = new HashMap<>();

        mTabAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);

        mViewPager.setAdapter(mTabAdapter);

        initTabs();

    }

    private void initMixPanel() {
        MixpanelAPI mixpanelAPI = MixpanelAPI.getInstance(this.getApplicationContext(), Constants.MIXPANEL_TOKEN);

        SmartCitizenApp.getInstance().setMixpanelInstance(mixpanelAPI);
    }

    @Override
    protected void onDestroy() {
        SmartCitizenApp.getInstance().getMixpanelInstance().flush();
        super.onDestroy();
    }

    public Device getDevice(Integer deviceId) {
        return mMapDevices.get(deviceId);
    }

    public void addDevice(Device device) {
        mMapDevices.put(device.getId(), device);
    }

    private void initTabs() {
        //SlidingTabLayout
        /*tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        //tabs.setCustomTabView(R.layout.tab, R.id.tab_title);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(mViewPager);*/
        //SlidingTabLayout

        //TabLayout (android dessign support)
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setBackgroundColor(getResources().getColor(R.color.ColorPrimary));
        //tabs.setupWithViewPager(mViewPager); //this create tabs from the viewpager adapter
        //int tabCount = tabs.getTabCount();
        for (int i = 0; i<mTabAdapter.getCount(); i++) {
            TabLayout.Tab tab = tabs.newTab();
            View tabInflated = getLayoutInflater().inflate(R.layout.tab, null);
            tab.setCustomView(tabInflated);
            ((TextView)tabInflated.findViewById(R.id.tab_title)).setText(mTabAdapter.getPageTitle(i));

            int resourceId = (i == 0) ? R.drawable.drawable_map_tab_title_selector : R.drawable.drawable_account_tab_title_selector;

            Drawable textDrawable = Utils.getDrawable(this, resourceId);

            ((TextView) tabInflated.findViewById(R.id.tab_title)).setCompoundDrawablesWithIntrinsicBounds(textDrawable, null, null, null);

            tabs.addTab(tab);

            //if the tablayout already has tabs
            //TabLayout.Tab tab = tabs.getTabAt(i);
            //tab.setIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            //tab.setText(mTabAdapter.getPageTitle(i));

        }
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //tabs.setTabTextColors(getResources().getColor(android.R.color.black), getResources().getColor(android.R.color.white));
        //TabLayout (android dessign support)
    }

    public void refreshAccountView(boolean isUserLoggedIn) {

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
				/*
				 * IMPORTANT: We use the "root frame" defined in
				 * "fragment_root_account.xml" as the reference to replace fragment
				 */
        if (isUserLoggedIn) {
            trans.replace(R.id.root_frame, AccountFragment.newInstance());

        } else {
            trans.replace(R.id.root_frame, AccountPlaceholderFragment.newInstance());
        }

				/*
				 * IMPORTANT: The following lines allow us to add the fragment
				 * to the stack and return to it later, by pressing back
				 */
        /*trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(null);*/

        trans.commit();
    }
}
