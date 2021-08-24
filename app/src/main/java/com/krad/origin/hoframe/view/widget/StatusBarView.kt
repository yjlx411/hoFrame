package com.krad.origin.hoframe.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import kotlin.jvm.JvmOverloads
class StatusBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mStatusBarHeight)
    }

    companion object {
        private var mStatusBarHeight: Int = 0

        //此处代码可以放到StatusBarUtils
        fun getStatusBarHeight(context: Context): Int {
            if (mStatusBarHeight == 0) {
                val res = context.resources
                val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    mStatusBarHeight = res.getDimensionPixelSize(resourceId)
                }
            }
            return mStatusBarHeight
        }
    }

    init {
        mStatusBarHeight = getStatusBarHeight(context)
    }
}