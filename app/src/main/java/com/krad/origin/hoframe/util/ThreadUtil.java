package com.krad.origin.hoframe.util;

import android.os.Handler;
import android.os.Looper;


public class ThreadUtil {

    private static Handler sHandler;

    public static void runOnMainThread(Runnable runnable) {
        if (runnable == null) return;
        if (sHandler == null) {
            sHandler = new Handler(Looper.getMainLooper());
        }
        sHandler.post(runnable);
    }
}
