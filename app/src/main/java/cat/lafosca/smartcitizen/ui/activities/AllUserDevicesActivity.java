package cat.lafosca.smartcitizen.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.commons.Utils;
import cat.lafosca.smartcitizen.model.rest.Device;
import cat.lafosca.smartcitizen.ui.widgets.KitView;

public class AllUserDevicesActivity extends AppCompatActivity {

    public static Intent getCallingIntent(Context context, String userName, ArrayList<Device> devicesInfoMap) {
        Intent intent = new Intent(context, AllUserDevicesActivity.class);
        intent.putParcelableArrayListExtra("devicesInfo", devicesInfoMap);
        intent.putExtra("userName", userName);
        return intent;
    }

    //private CurrentUser mUserData;

    @InjectView(R.id.devices_container)
    LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_devices);

        ArrayList<Device> devicesData = getIntent().getParcelableArrayListExtra("devicesInfo");

        ButterKnife.inject(this);

        String toolbarTitle = getString(R.string.device_owner_label, getIntent().getStringExtra("userName"));
        toolbarTitle.toUpperCase();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(toolbarTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int devicesSize = devicesData.size();
        for (int i = 0; i < devicesSize; i++) {
            KitView kitView = new KitView(this);

            Device device = devicesData.get(i);

            String name = device.getName();
            String location = devicesData.get(i).getDeviceData().getLocation().getPrettyLocation();

            Drawable drawable = Utils.getDrawable(this, R.drawable.device_icon);//do it outside the foor loop?
            kitView.setKitsData(name, location, drawable);
            kitView.updateTitleColor(devicesData.get(i).getKit().getSlug());

            /*if (i == devicesSize - 1) {
                kitView.findViewById(R.id.kit_row_separator).setVisibility(View.GONE);
            }*/

            mContainer.addView(kitView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
