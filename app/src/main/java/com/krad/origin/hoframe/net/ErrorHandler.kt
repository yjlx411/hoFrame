package com.krad.origin.hoframe.net

interface ErrorHandler<T> {
    fun handleError(response: BaseResponse<T>)
    fun onDestroy()
}