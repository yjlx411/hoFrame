package com.krad.origin.hoframe.pager

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.krad.origin.hoframe.net.NetAct
import com.krad.origin.hoframe.index.NetApi
import com.krad.origin.hoframe.net.NetUnit
import com.krad.origin.hoframe.view.UIndex
import java.util.*
import kotlin.collections.HashMap
import android.content.Intent
import androidx.fragment.app.FragmentManager

abstract class Pager<T : ViewDataBinding> {

    private var context: Context? = null
    private var config: PagerConfig = PagerConfig()
    private var netMap: EnumMap<NetApi, NetUnit<*>>? = null

    val coreID = PagerLoader.createID()
    var dataExtra: HashMap<String, Any>? = null
    var uIndex: UIndex<T> = UIndex(config)

    var fragmentManager: FragmentManager? = null

    fun inject(rootView: ViewGroup) {
        context = rootView.context
        initConfig(config)

        val viewContainer: ViewGroup
        if (config.isLinear) {
            viewContainer = LinearLayout(context)
            viewContainer.orientation = LinearLayout.VERTICAL
        } else {
            viewContainer = FrameLayout(context!!)
        }
        rootView.addView(viewContainer, -1, -1)
        val viewContent: View = LayoutInflater.from(context).inflate(getLayoutID(), null)
        var netStateView: View? = null
        if (config.isNetPager) {
            val viewNetLayout = FrameLayout(context!!)
            viewContainer.addView(viewNetLayout, -1, -1)
            viewNetLayout.addView(viewContent, -1, -1)
            netStateView = View.inflate(context!!, config.netStateLayoutId, null)
            viewNetLayout.addView(netStateView, -1, -1)
        } else {
            viewContainer.addView(viewContent, -1, -1)
        }
        viewContainer.setBackgroundResource(config.pageBackground)
        uIndex.bindView(viewContainer, viewContent)
        if (netStateView != null) {
            uIndex.setNetStateLayout(netStateView)
        }

        if (config.isNetPager && netMap != null) uIndex.net_load()

        initView()

        if (netMap != null && context != null) {
            for (netUnit in netMap!!.values) {
                if (config.isNetBind) {
                    netUnit.bind(this)
                }
            }
        }
    }

    fun VB(): T? {
        return uIndex.viewBind
    }

    open fun bindNet() {
        if (netMap != null && context != null) {
            for (netUnit in netMap!!.values) {
                netUnit.bind(this)
            }
        }
    }

    open fun initConfig(config: PagerConfig) {

    }

    @LayoutRes
    abstract fun getLayoutID(): Int


    abstract fun initData()

    abstract fun initView()

    fun onStart() {

    }

    fun onResume() {

    }

    fun onPause() {

    }

    fun onStop() {

    }

    fun onDestroy() {
        if (netMap != null && context != null) {
            for (netUnit in netMap!!.values) {
                netUnit.unBind()
            }
            netMap!!.clear()
        }
        fragmentManager = null
        uIndex.onDestroy()
    }


    open fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
    }

    fun getContext(): Context? {
        return context
    }

    fun getActivity(): Activity? {
        return context as? Activity
    }

    fun addNetUnit(netUnit: NetUnit<*>) {
        if (netMap == null) {
            netMap = EnumMap(NetApi::class.java)
        }
        netMap!![netUnit.netApi] = netUnit
    }

    private fun findNetUnit(netApi: NetApi?): NetUnit<*> {
        if (netMap == null) throw RuntimeException("netUnit has not been added")
        return netMap!![netApi] ?: throw RuntimeException("netUnit has not been added")
    }

    fun netAct(netApi: NetApi?): NetAct<*> {
        return findNetUnit(netApi).prepare()
    }

    fun <T> getDataExtra(key: String): T? {
        return dataExtra?.get(key) as? T
    }

    fun getStringExtra(key: String): String {
        return getDataExtra<String>(key) ?: ""
    }

    fun getIntExtra(key: String): Int {
        return getDataExtra<Int>(key) ?: 0
    }
}