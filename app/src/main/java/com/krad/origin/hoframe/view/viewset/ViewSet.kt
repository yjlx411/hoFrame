package com.krad.origin.hoframe.view.viewset

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.krad.origin.R
import com.krad.origin.databinding.TitleBarBind

abstract class ViewSet<T : ViewDataBinding?>(rootView: View?) : BaseObservable() {

    var binder: T? = rootView?.let { DataBindingUtil.bind<T>(it) }

    fun destroy() {
        binder?.unbind()
        binder = null
    }
}