/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.krad.origin.hoframe.util;

import android.content.res.Resources;
import android.util.TypedValue;

public class DimensionUtil {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int spToPx(int spValue) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, spValue, Resources.getSystem().getDisplayMetrics());
    }

}
