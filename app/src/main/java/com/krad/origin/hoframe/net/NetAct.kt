package com.krad.origin.hoframe.net

import com.google.gson.reflect.TypeToken
import com.krad.origin.hoframe.net.NetConfig.Companion.TOEKN
import com.krad.origin.hoframe.util.GsonUtil
import com.krad.origin.hoframe.util.LogUtil
import com.krad.origin.hoframe.util.MD5Utils
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class NetAct<T> constructor(private var netUnit: NetUnit<T>?) {
    var stateMode: StateMode = StateMode.SILENT
    private var params: WeakHashMap<String, Any>? = null
    private var disposableObserver: DisposableObserver<*>? = null
    private var flag: Int = 0

    fun start() {
        if (netUnit == null) return
        if (disposableObserver != null && !disposableObserver!!.isDisposed)
            disposableObserver!!.dispose()

        if (params == null) params = WeakHashMap()
        params!!["key"] = netUnit?.netApi?.key

        LogUtil.NetReq.debug(netUnit?.netApi?.name + params.toString())

        netUnit?.onNetStart(stateMode)

        disposableObserver = createObservable(params!!)?.subscribeOn(Schedulers.io())
            ?.observeOn(Schedulers.io())
            ?.map(Function { s: String? ->
                val decryptRespond = GsonUtil.decryptRespond(s)
                LogUtil.NetRes.debug(netUnit?.netApi?.name + ":" + decryptRespond)
                val baseResponse: BaseResponse<T> =
                    try {
                        GsonUtil.getGson()
                            .fromJson(s, object : TypeToken<BaseResponse<T?>?>() {}.type)
                    } catch (e: Exception) {
                        BaseResponse<T>()
                    }
                if (baseResponse.result != null) {
                    var type: Type? = netUnit?.type
                    val jsonDataStr = GsonUtil.getGson().toJson(baseResponse.result)
                    if (type.toString().contains("java.lang.String")) {
                        baseResponse.data = jsonDataStr as? T
                    } else {
                        try {
                            val data: T = GsonUtil.getGson().fromJson(jsonDataStr, type)
                            baseResponse.data = data
                        } catch (e: java.lang.Exception) {
                            //
                        }
                    }
                    baseResponse.result = null
                }
                baseResponse
            })
            ?.observeOn(Schedulers.single())
            ?.subscribeWith(object : DisposableObserverImpl<BaseResponse<T>>() {

                override fun onNext(value: BaseResponse<T>) {
                    super.onNext(value)
                    if (netUnit != null) {
                        value.stateMode = stateMode
                        value.flag = flag
                        netUnit!!.onNetResult(value)
                        netUnit = null
                    }
                    onActFinish()
                }

                override fun onError(e: Throwable?) {
                    val msg = GsonUtil.getGson().toJson(e)
                    LogUtil.NetErr.debug(netUnit?.netApi?.name + ":" + msg)
                    if (netUnit != null) {
                        val value: BaseResponse<T> = BaseResponse()
                        value.code = NetConfig.CODE_FAIL
                        value.stateMode = stateMode
                        value.flag = flag
                        value.throwable = e
                        netUnit!!.onNetResult(value)
                    }
                    onActFinish()
                }
            })
    }

    fun onActFinish() {
        if (disposableObserver != null) {
            if (!disposableObserver!!.isDisposed)
                disposableObserver!!.dispose()
            disposableObserver = null
        }
        netUnit?.onNetFinish(stateMode)
        netUnit = null
    }

    fun addParams(key: String, value: Any?): NetAct<T> {
        if (params == null) params = WeakHashMap()
        params!![key] = value
        return this
    }

    init {
        requireNotNull(netUnit) { "netUnit con not be null" }
    }

    fun getMode(): NetAct<T> {
        stateMode = StateMode.GET
        return this
    }


    fun postMode(): NetAct<T> {
        stateMode = StateMode.POST
        return this
    }

    fun toastMode(): NetAct<T> {
        stateMode = StateMode.TOAST
        return this
    }

    fun silentMode(): NetAct<T> {
        stateMode = StateMode.SILENT
        return this
    }

    fun setFlag(num: Int): NetAct<T> {
        flag = num
        return this
    }

    private fun createObservable(params: WeakHashMap<String, Any>): Observable<String>? {
        val netApi = NetClient.getInstance().api
        return if (netUnit != null) {
            netApi.postWithKey(netUnit!!.netApi.url, netUnit!!.netApi.key, params)
            /*if (netUnit!!.netApi.withToken) {
                netApi.postWithToken(netUnit!!.netApi.url, TOEKN, json)
            } else {
                netApi.post(netUnit!!.netApi.url, json)
            }*/
        } else {
            null
        }
    }

    open class DisposableObserverImpl<T> : DisposableObserver<T>() {
        override fun onNext(value: T) {
        }

        override fun onError(e: Throwable?) {
        }

        override fun onComplete() {
        }
    }
}