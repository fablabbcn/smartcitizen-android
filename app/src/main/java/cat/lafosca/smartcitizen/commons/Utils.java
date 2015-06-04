package cat.lafosca.smartcitizen.commons;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by ferran on 04/06/15.
 */
public class Utils {

    public static Drawable getDrawable(Context context, int resourceId) {

        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getDrawable(resourceId);

        } else {
            drawable = context.getResources().getDrawable(resourceId);
        }

        return drawable;
    }

}
