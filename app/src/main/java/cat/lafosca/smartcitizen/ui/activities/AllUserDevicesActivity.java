package cat.lafosca.smartcitizen.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.model.rest.CurrentUser;
import cat.lafosca.smartcitizen.model.rest.DeviceInfo;
import cat.lafosca.smartcitizen.ui.widgets.KitView;

public class AllUserDevicesActivity extends AppCompatActivity {

    public static Intent getCallingIntent(Context context, CurrentUser userData) {
        Intent intent = new Intent(context, AllUserDevicesActivity.class);
        intent.putExtra("userData", userData);
        return intent;
    }

    private CurrentUser mUserData;

    @InjectView(R.id.devices_container)
    LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_devices);

        mUserData = getIntent().getParcelableExtra("userData");

        ButterKnife.inject(this);

        String toolbarTitle = getString(R.string.device_owner_label, mUserData.getUsername());
        toolbarTitle.toUpperCase();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(toolbarTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int devicesSize = mUserData.getDevices().size();
        for (int i = 0; i < devicesSize; i++) {
            KitView kitView = new KitView(this);

            DeviceInfo device = mUserData.getDevices().get(i);

            kitView.setKitsData(device.getName(), "location", 0);


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
