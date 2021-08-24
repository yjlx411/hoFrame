package com.krad.origin.hoframe.util

import android.util.Log
import java.util.logging.Logger

enum class LogUtil(val tag: String) {

    NetErr("netErr"),
    NetReq("netReq"),
    NetRes("netRes");

    fun debug(s: String) {
        Log.d(tag, s)
    }
}