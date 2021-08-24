package com.krad.origin.hoframe.net

import com.krad.origin.hoframe.net.NetConfig.Companion.TEXT_FAIL_TOAST
import com.krad.origin.hoframe.view.UIndex

open class DefaultErrorHandler<T>(private var uIndex: UIndex<*>?) : ErrorHandler<T> {
    override fun handleError(response: BaseResponse<T>) {
        if (response.throwable == null) {
            onCode(response)
        } else {
            onFail(response)
        }
    }

    open fun onCodeIntercept(response: BaseResponse<T>): Boolean {
        when (response.code) {
            10012 -> {
                uIndex?.toastShortMessage(response.message)
                return true
            }
        }
        return false
    }

    open fun onFailIntercept(response: BaseResponse<T>): Boolean {
        return false
    }


    fun onCode(response: BaseResponse<T>) {
        if (onCodeIntercept(response)) {
            return
        }
        when (response.stateMode) {
            StateMode.GET -> uIndex?.net_err()
            StateMode.POST, StateMode.TOAST -> uIndex?.toastShortMessage(TEXT_FAIL_TOAST)
            StateMode.SILENT -> {
            }
            else -> {
            }
        }
    }

    fun onFail(response: BaseResponse<T>) {
        if (onFailIntercept(response)) {
            return
        }
        when (response.stateMode) {
            StateMode.GET -> uIndex?.net_err()
            StateMode.POST, StateMode.TOAST -> uIndex?.toastShortMessage(TEXT_FAIL_TOAST)
            StateMode.SILENT -> {
            }
            else -> {
            }
        }
    }

    override fun onDestroy() {
        uIndex = null
    }
}