package com.krad.origin.hoframe.net

class NetConfig {

    companion object {
        val CODE_SUCCESS = 0
        val CODE_FAIL = - 666

        val DEBUG = true

        val BASE_URL_PRODUCTION = "http://apis.juhe.cn/" //正式IP

        val BASE_URL_TEST = "http://apis.juhe.cn/" //测试IP

        val baseUrl = if (DEBUG) BASE_URL_TEST else BASE_URL_PRODUCTION

        var TOEKN: String = "" //登录token

        val TEXT_FAIL_TOAST = "请求失败，请稍后再试"
    }

}