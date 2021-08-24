package com.krad.origin.hoframe.pager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.lang.Exception
import kotlin.collections.HashMap

class PagerLoader(pager: Pager<*>?) {

    companion object {
        private var pagerID = 0
        const val KEY_ID = "pagerID"

        fun createID(): Int {
            return ++pagerID
        }
    }

    var mPager: Pager<*>? = pager
    var mRequestCode = -1

    var params: HashMap<String, Any>? = HashMap()

    fun add(key: String, value: Any): PagerLoader {
        params?.set(key, value)
        return this
    }

    fun start(context: Context?) {
        if (context == null) {
            // TODO: 2021/8/15 addLog
            return
        }

        if (mPager == null) {
            // TODO: 2021/8/15 addLog
            return
        }

        mPager!!.dataExtra = params
        mPager!!.initData()

        val intent = Intent()
        PagerStore.put(mPager!!.coreID, mPager!!)
        with(intent) {
            setClass(context, HoActivity().javaClass)
            putExtra(KEY_ID, mPager!!.coreID)
        }
        if (mRequestCode != -1 && context is Activity) {
            context.startActivityForResult(intent, mRequestCode)
        } else {
            context.startActivity(intent)
        }
        context.startActivity(intent)
        mPager = null
        params = null
    }

    fun toFragment(): HoFragment {
        return toFragment(true)
    }

    fun toFragment(bundleMode: Boolean): HoFragment {
        val hoFragment = HoFragment()
        try {
            if (bundleMode) {
                val bundle = Bundle()
                bundle.putSerializable("core", mPager?.javaClass)
                bundle.putSerializable("coreData", params)
                hoFragment.arguments = bundle
            } else {
                mPager?.dataExtra = params
                hoFragment.setCoreUnit(mPager)
                mPager?.initData()
            }
        } catch (e: Exception) {
            //
        }
        mPager = null
        params = null
        return hoFragment
    }

    fun forResult(requestCode: Int): PagerLoader {
        mRequestCode = requestCode
        return this
    }
}