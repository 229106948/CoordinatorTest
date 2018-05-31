package test.com.coordinatorlayouttest;

import android.content.Context;

public class Uitools {
    public static int dip2px(Context context,float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }
}
