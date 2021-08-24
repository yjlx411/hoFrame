package com.krad.origin.hoframe.index

import androidx.lifecycle.Observer
import com.krad.origin.hoframe.net.NetUnit
import com.krad.origin.hoframe.net.SuccessObserver

enum class NetApi constructor(val url: String, val key: String, var withToken: Boolean = true) {

    QueryOilPrice("gnyj/query", "5148977f8000db81b4a68a8067089f06", false),
    QueryNews("http://v.juhe.cn/toutiao/index", "a493789eb04cf5e989e07126d12b2701", false);

    fun <T> make(observer: SuccessObserver<T>?): NetUnit<T> {
        return NetUnit(this, observer)
    }
}