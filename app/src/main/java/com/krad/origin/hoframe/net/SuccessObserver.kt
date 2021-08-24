package com.krad.origin.hoframe.net

abstract class SuccessObserver<T> {
   abstract fun onSuccess(flag: Int, data: T?)
}