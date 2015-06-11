package cat.lafosca.smartcitizen.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by ferran on 11/06/15.
 */
public class InterceptorLayout extends LinearLayout {
    public InterceptorLayout(Context context) {
        super(context);
    }

    public InterceptorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
