package cat.lafosca.smartcitizen.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import cat.lafosca.smartcitizen.R;

/**
 * Created by ferran on 22/06/15.
 */
public class KitView extends LinearLayout {

    private TextView mKitName, mKitLocation;

    public KitView(Context context) {
        super(context);

        init(context);
    }

    public KitView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.kit_row_view, this, true);

        mKitName = (TextView)findViewById(R.id.kit_row_name);
        mKitLocation = (TextView)findViewById(R.id.kit_row_location);
    }

    public void setKitsData(String name, String location, int drawable) {
        mKitName.setText(name);
        mKitLocation.setText(location);
        //mSensorName.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
    }

    public void updateLocationText(String location) {
        mKitLocation.setText(location);
    }
}
