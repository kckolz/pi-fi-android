package pack.wolf.com.pifi.util;

import android.app.Activity;
import android.content.Context;

public class ContextUtil {

    public static void finish(Context context) {
        if(context instanceof Activity) {
            ((Activity)context).finish();
        }
    }
}
