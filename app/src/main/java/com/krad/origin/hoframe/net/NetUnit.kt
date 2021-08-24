package com.krad.origin.hoframe.net

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.krad.origin.hoframe.index.NetApi
import com.krad.origin.hoframe.net.NetConfig.Companion.CODE_SUCCESS
import com.krad.origin.hoframe.pager.Pager
import com.krad.origin.hoframe.view.UIndex
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetUnit<T>(val netApi: NetApi, observer: SuccessObserver<T>?) {
    private val mLiveData = MutableLiveData<BaseResponse<T>>()
    private var mObserver: Observer<BaseResponse<T>>? = null
    private var mSuccessObserver: SuccessObserver<T>?
    private var mErrorHandler: ErrorHandler<T>? = null
    private var withContext = true
    private var uIndex: UIndex<*>? = null
    var type: Type? = null

    init {
        try {
            type = observer?.javaClass?.genericSuperclass
            if (type is ParameterizedType) {
                type = (type as ParameterizedType).actualTypeArguments[0]
            }
        } catch (e: Exception) {
            //
        }
    }


    fun bind(context: Context?) {
        if (withContext && context is LifecycleOwner) {
            mObserver = Observer { baseResponse: BaseResponse<T>? ->
                if (baseResponse != null) {
                    if (baseResponse.code == CODE_SUCCESS) {
                        if (mSuccessObserver != null) mSuccessObserver!!.onSuccess(baseResponse.flag,baseResponse.data)
                    } else {
                        if (mErrorHandler != null) mErrorHandler!!.handleError(baseResponse)
                    }
                }
            }
            mLiveData.observe((context as LifecycleOwner?)!!, mObserver!!)
        }
    }

    fun bind(pager: Pager<*>) {
        bind(pager.getContext())
        uIndex = pager.uIndex
        if (mErrorHandler == null) {
            setErrorHandler(DefaultErrorHandler(uIndex))
        }
    }

    fun unBind() {
        if (mObserver != null) {
            mLiveData.removeObserver(mObserver!!)
            mObserver = null
            if (mSuccessObserver != null) {
                mSuccessObserver = null
            }
        }
        if (mErrorHandler != null) {
            mErrorHandler!!.onDestroy()
            mErrorHandler = null
        }
        uIndex = null
    }

    fun onNetStart(stateMode: StateMode) {
        when (stateMode) {
            StateMode.GET -> {
                uIndex?.net_load()
            }

            StateMode.POST -> {
                uIndex?.showLoadDialog()
            }
            else -> {}
        }
    }


    fun onNetFinish(stateMode: StateMode) {
        when (stateMode) {
            StateMode.POST -> {
                uIndex?.hideLoadDialog()
            }
            else -> {}
        }
    }

    fun onNetResult(response: BaseResponse<T>?) {
        if (response != null) {
            if (withContext) {
                mLiveData.postValue(response)
            } else {
                if (response.code == CODE_SUCCESS) {
                    if (mSuccessObserver != null) mSuccessObserver!!.onSuccess(response.flag,response.data)
                } else {
                    if (mErrorHandler != null) mErrorHandler!!.handleError(response)
                }
            }
        }
    }

    fun prepare(): NetAct<T> {
        return NetAct(this)
    }

    fun setErrorHandler(mErrorHandler: ErrorHandler<T>?): NetUnit<T> {
        this.mErrorHandler = mErrorHandler
        return this
    }

    fun withContext(withContext: Boolean): NetUnit<T> {
        this.withContext = withContext
        return this
    }

    init {
        requireNotNull(observer) { "observer can not be null" }
        mSuccessObserver = observer
        if (mErrorHandler == null) {
            setErrorHandler(DefaultErrorHandler(null))
        }
    }
}