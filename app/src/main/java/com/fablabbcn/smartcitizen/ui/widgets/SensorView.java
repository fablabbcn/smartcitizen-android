package com.fablabbcn.smartcitizen.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fablabbcn.smartcitizen.R;

/**
 * Created by ferran on 16/06/15.
 */
public class SensorView extends FrameLayout {

    private TextView mSensorName, mSensorValue;

    public SensorView(Context context) {
        super(context);

        init(context);
    }

    public SensorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.sensor_view, this, true);

        mSensorName = (TextView)findViewById(R.id.sensor_name);
        mSensorValue = (TextView)findViewById(R.id.sensor_value);
    }

    public void setSensoViewrName(String text, int drawable) {
        mSensorName.setText(text);
        mSensorName.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
    }

    public void setSensorValue(String text) {
        mSensorValue.setText(text);
    }
}
