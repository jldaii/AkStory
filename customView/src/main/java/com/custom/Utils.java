package com.custom;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import com.custom.views.R;


public class Utils {

    public static float dp2px(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics());
    }


}
