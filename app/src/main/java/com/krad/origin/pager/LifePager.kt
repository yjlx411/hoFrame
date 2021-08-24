package com.krad.origin.pager

import android.view.View
import com.krad.origin.R
import com.krad.origin.bean.NewsData
import com.krad.origin.hoframe.index.NetApi
import com.krad.origin.hoframe.net.ObserverImpl
import com.krad.origin.hoframe.pager.Pager
import com.krad.origin.bean.OilPrice
import com.krad.origin.databinding.PagerLifeBinding
import com.krad.origin.hoframe.net.BaseResponse
import com.krad.origin.hoframe.net.DefaultErrorHandler
import com.krad.origin.hoframe.net.SuccessObserver

class LifePager : Pager<PagerLifeBinding>() {

    val province = "江苏"
    val city = "苏州"
    var oilPrice: OilPrice? = null

    init {
        addNetUnit(NetApi.QueryOilPrice.make(object : SuccessObserver<Array<OilPrice>>() {

            override fun onSuccess(flag: Int, data: Array<OilPrice>?) {
                uIndex.net_success()
                data?.forEach {
                    if (province == it.city) {
                        oilPrice = it
                        it.let {
                            uIndex.viewBind?.tvPrice92?.text = it.price_92h
                            uIndex.viewBind?.tvPrice95?.text = it.price_95h
                            uIndex.viewBind?.tvPrice98?.text = it.price_98h
                        }
                        return
                    }
                }
            }

        }).setErrorHandler(object : DefaultErrorHandler<Array<OilPrice>>(null) {
            override fun onCodeIntercept(response: BaseResponse<Array<OilPrice>>): Boolean {
                uIndex.net_err()
                return true
            }

            override fun onFailIntercept(response: BaseResponse<Array<OilPrice>>): Boolean {
                uIndex.net_err()
                return true
            }
        }))
    }

    override fun getLayoutID(): Int {
        return R.layout.pager_life
    }

    override fun initView() {
        uIndex.netStateVS?.onRetry = Runnable {
            netAct(NetApi.QueryOilPrice).getMode().start()
        }
        uIndex.initTitlebar("$province$city")
        uIndex.titleBarVs?.hideBackButton()
    }

    override fun initData() {
        netAct(NetApi.QueryOilPrice).start()
    }


}