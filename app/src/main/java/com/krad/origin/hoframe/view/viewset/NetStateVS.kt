package com.krad.origin.hoframe.view.viewset

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import com.krad.origin.R
import com.krad.origin.databinding.LayoutNetStateBinding

class NetStateVS(rootView: View?) : ViewSet<LayoutNetStateBinding>(rootView) {

    @ColorRes
    var bgColorRes: Int = R.color.bg_gray_f5

    @LayoutRes
    var voidLayoutRes: Int = R.layout.layout_state_void

    @LayoutRes
    var failLayoutRes: Int = R.layout.layout_state_fail

    @LayoutRes
    var loadLayoutRes: Int = R.layout.layout_state_load

    var onRetry: Runnable? = null

    init {
        setBgColor(bgColorRes)
        binder?.root?.visibility = View.GONE
        binder?.vsVoid?.viewStub?.layoutResource = voidLayoutRes
        binder?.vsLoad?.viewStub?.layoutResource = loadLayoutRes
        binder?.vsFail?.viewStub?.layoutResource = failLayoutRes
    }

    var state: Int = 0 //0 成功 1 加载 2失败 3空页
        set(value) {
            field = value
            binder?.root?.visibility = if (value == 0) View.GONE else View.VISIBLE

            if (value == 1 && false == binder?.vsLoad?.isInflated) {
                binder?.vsLoad?.viewStub?.visibility = View.VISIBLE
            }

            if (value == 2 && false == binder?.vsFail?.isInflated) {
                binder?.vsFail?.viewStub?.visibility = View.VISIBLE
                val tvRetry = binder?.vsFail?.root?.findViewById<View>(R.id.tv_fail_retry)
                tvRetry?.setOnClickListener { onRetry?.run() }
            }

            if (value == 3 && false == binder?.vsVoid?.isInflated) {
                binder?.vsVoid?.viewStub?.visibility = View.VISIBLE
            }

            binder?.vsLoad?.root?.visibility = if (value == 1) View.VISIBLE else View.GONE
            binder?.vsFail?.root?.visibility = if (value == 2) View.VISIBLE else View.GONE
            binder?.vsVoid?.root?.visibility = if (value == 3) View.VISIBLE else View.GONE
            if (value == 2) {
                val tvRetry = binder?.vsFail?.root?.findViewById<View>(R.id.tv_fail_retry)
                tvRetry?.visibility = if (onRetry == null) View.GONE else View.VISIBLE
            }
        }

    fun setBgColor(@ColorRes colorRes: Int) {
        binder?.flStateNet?.setBackgroundResource(colorRes)
    }
}