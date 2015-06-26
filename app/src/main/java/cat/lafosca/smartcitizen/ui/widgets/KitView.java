package cat.lafosca.smartcitizen.ui.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
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

    private static final int BLUE_KIT_COLOR = Color.parseColor("#35C2E5");

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

    public void setKitsData(String name, String location, Drawable drawable) {
        mKitName.setText(name);
        mKitLocation.setText(location);
        mKitName.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        //mSensorName.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
    }

    public void updateTitleColor(String kitType) {

        if (kitType.toLowerCase().contains("sck")) {
            mKitName.setTextColor(BLUE_KIT_COLOR);

            Drawable[] drawables = mKitName.getCompoundDrawables();
            drawables[0].setColorFilter(new PorterDuffColorFilter(BLUE_KIT_COLOR, PorterDuff.Mode.MULTIPLY));

        }//todo else if .... another type of kit
    }

    public void updateLocationText(String location) {
        mKitLocation.setText(location);
    }
}
