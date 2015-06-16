package cat.lafosca.smartcitizen.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.model.rest.Device;

public class KitDetailActivity extends AppCompatActivity {

    private Device mDevice;

    public static Intent getCallingIntent(Context context, Device device) {

        Intent intent = new Intent(context, KitDetailActivity.class);
        intent.putExtra("Device", device);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kit_detail);

        mDevice = getIntent().getParcelableExtra("Device");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
