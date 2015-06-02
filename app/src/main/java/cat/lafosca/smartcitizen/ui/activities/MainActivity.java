package cat.lafosca.smartcitizen.ui.activities;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.ui.adapters.MainPagerAdapter;
import cat.lafosca.smartcitizen.ui.widgets.CustomViewPager;



public class MainActivity extends AppCompatActivity {

    //private SlidingTabLayout tabs;
    private TabLayout tabs;

    private MainPagerAdapter mTabAdapter;
    private CustomViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);*/

        mTabAdapter = new MainPagerAdapter(getSupportFragmentManager());

        mViewPager = (CustomViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mTabAdapter);

        initTabs();

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
        tabs = (TabLayout)findViewById(R.id.tabs);
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

            Drawable textDrawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textDrawable = this.getDrawable(resourceId);

            } else {
                textDrawable = this.getResources().getDrawable(resourceId);
            }

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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
