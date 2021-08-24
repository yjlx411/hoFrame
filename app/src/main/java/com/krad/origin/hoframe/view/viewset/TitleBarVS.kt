package com.krad.origin.hoframe.view.viewset

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.krad.origin.R
import com.krad.origin.databinding.TitleBarBind

class TitleBarVS(view: View?) : ViewSet<TitleBarBind>(view) {

    public fun setTitle(title: String) {
        binder?.tvTitle?.text = title
    }

    fun setOnBackListener(listener: View.OnClickListener) {
        binder?.ivBack?.setOnClickListener(listener)
    }

    fun hideBackButton() {
        binder?.ivBack?.visibility = View.GONE
    }

}