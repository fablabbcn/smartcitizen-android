package com.smartcitizen.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.smartcitizen.R;

public class CustomViewPager extends ViewPager {

    private boolean swipeable;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomViewPager);
        try {
            swipeable = a.getBoolean(R.styleable.CustomViewPager_swipeable, false);
        } finally {
            a.recycle();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swipeable ? super.onInterceptTouchEvent(event) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swipeable ? super.onTouchEvent(event) : false;
    }

    public boolean isSwipeable() {
        return swipeable;
    }

    public void setSwipeable(boolean swipeable) {
        this.swipeable = swipeable;
    }

}
