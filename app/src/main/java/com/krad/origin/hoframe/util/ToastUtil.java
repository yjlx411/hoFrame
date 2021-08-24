package com.krad.origin.hoframe.util;

import android.widget.Toast;

import com.krad.origin.hoframe.pager.HoApplication;
public class ToastUtil {

    private static Toast mToast;

    public static void toastLongMessage(final String message) {
        ThreadUtil.runOnMainThread(() -> {
            if (mToast != null) {
                mToast.cancel();
                mToast = null;
            }
            mToast = Toast.makeText(HoApplication.getInstance(), message, Toast.LENGTH_LONG);
            mToast.show();
        });
    }


    public static void toastShortMessage(final String message) {
        ThreadUtil.runOnMainThread(() -> {
            if (mToast != null) {
                mToast.cancel();
                mToast = null;
            }
            mToast = Toast.makeText(HoApplication.getInstance(), message, Toast.LENGTH_SHORT);
            mToast.show();
        });
    }


}
