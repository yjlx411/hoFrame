package com.krad.origin.hoframe.view

import android.app.Activity
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.krad.origin.R
import com.krad.origin.hoframe.util.ToastUtil
import com.krad.origin.hoframe.view.viewset.NetStateVS
import com.krad.origin.hoframe.view.viewset.StatusBarInvasionUtil
import com.krad.origin.hoframe.view.viewset.TitleBarVS
import com.krad.origin.hoframe.view.widget.LoadingDialog
import com.ldoublem.loadingviewlib.view.LVCircularRing


import android.widget.FrameLayout

import android.widget.LinearLayout

import android.view.ViewGroup
import com.krad.origin.hoframe.pager.PagerConfig


class UIndex<T : ViewDataBinding>(private val config: PagerConfig) {

    var mRootView: View? = null
    var context: Activity? = null

    var viewBind: T? = null
    var titleBarVs: TitleBarVS? = null
    var netStateVS: NetStateVS? = null
    var dialog: LoadingDialog? = null


    fun bindView(rootView: View, contentView: View) {
        mRootView = rootView
        context = rootView.context as Activity?
        if (context != null) {
            viewBind = DataBindingUtil.bind(contentView)!!
        }
    }

    fun showLoadDialog() {
        if (context == null) return
        if (dialog == null) {
            val view = View.inflate(context, R.layout.view_dialog_loading, null)
            val circularRing = view.findViewById<LVCircularRing>(R.id.load_view)
            circularRing.setViewColor(R.color.theme)
            dialog = LoadingDialog(context, view, R.style.DialogTheme)
            dialog?.setCancelable(true)
        }
        if (!dialog!!.isShowing) dialog?.show()
    }

    fun hideLoadDialog() {
        if (dialog != null && dialog?.isShowing == true) dialog?.dismiss()
    }

    fun toastShortMessage(string: String) {
        ToastUtil.toastShortMessage(string)
    }


    fun toastNetFailMsg() {
        toastShortMessage("请求失败，请稍后再试")
    }

    fun onDestroy() {
        mRootView = null
        context = null
        dialog = null
        titleBarVs?.destroy()
        titleBarVs = null
        netStateVS?.destroy()
        netStateVS = null
    }

    fun initTitlebar(name: String) {
        if (context == null || titleBarVs != null || mRootView == null) return
        val inflate: View = View.inflate(context, config.titlebarLayoutId, null)
        titleBarVs = TitleBarVS(inflate)
        val params = ViewGroup.LayoutParams(-1, -2)
        val container = mRootView as? ViewGroup
        if (container != null) {
            if (container is LinearLayout) {
                container.addView(inflate, 0, params)
            } else if (container is FrameLayout) {
                container.addView(inflate, container.childCount, params)
            }
        }
        titleBarVs!!.setTitle(name)
        titleBarVs!!.setOnBackListener {
            context?.onBackPressed()
        }
    }

    fun setNetStateLayout(view: View) {
        netStateVS = NetStateVS(view)
    }


    fun net_load() {
        netStateVS?.state = 1
        //
    }

    fun net_err() {
        netStateVS?.state = 2
    }


    fun net_void() {
        netStateVS?.state = 3
        //
    }

    fun net_success() {
        netStateVS?.state = 0
        //
    }
}