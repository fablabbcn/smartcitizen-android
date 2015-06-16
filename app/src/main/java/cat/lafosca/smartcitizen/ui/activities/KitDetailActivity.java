package cat.lafosca.smartcitizen.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.commons.PrettyTimeHelper;
import cat.lafosca.smartcitizen.model.rest.Device;

public class KitDetailActivity extends AppCompatActivity {

    private Device mDevice;

    @InjectView(R.id.kit_detail_title)
    TextView mKitTitle;

    @InjectView(R.id.kit_detail_kit_type)
    TextView mKitType;

    @InjectView(R.id.kit_detail_timestamp)
    TextView mKitTimestamp;

    @InjectView(R.id.kit_detail_user)
    TextView mKitUser;

    @InjectView(R.id.kit_detail_location)
    TextView mKitLocation;

    public static Intent getCallingIntent(Context context, Device device) {

        Intent intent = new Intent(context, KitDetailActivity.class);
        intent.putExtra("Device", device);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kit_detail);

        ButterKnife.inject(this);

        mDevice = getIntent().getParcelableExtra("Device");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

    }

    private void init() {
        setTextLabels();
    }

    private void setTextLabels() {
        mKitTitle.setText(mDevice.getName());
        mKitType.setText(mDevice.getKit().getName().toUpperCase());

        if (mDevice.getUpdatedAt() != null) {
            String updatedAt = "";
            try {
                updatedAt = PrettyTimeHelper.getInstance().getPrettyTime(mDevice.getUpdatedAt());
                mKitTimestamp.setText(updatedAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            mKitTimestamp.setVisibility(View.GONE);
        }

        if (mDevice.getOwner() != null && mDevice.getOwner().getUsername() != null) {
            mKitUser.setText(mDevice.getOwner().getUsername());
        } else {
            mKitUser.setVisibility(View.GONE);
        }

        if (mDevice.getData() != null && mDevice.getData().getLocation() != null) {
            String location = mDevice.getData().getLocation().getCity();
            String country = mDevice.getData().getLocation().getCountry();
            if (country != null) {
                location += ", " + country;
            }
            mKitLocation.setText(location);
        } else {
            mKitLocation.setVisibility(View.GONE);
        }
    }
}
