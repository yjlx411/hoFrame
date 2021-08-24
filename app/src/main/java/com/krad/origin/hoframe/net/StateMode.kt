package com.krad.origin.hoframe.net

enum class StateMode {
    GET,  //访问时有加载动画，出错时有错误及重新加载
    POST,  //访问时有loading弹窗，出错时显示toast
    TOAST,  //仅在访问出错时显示toast
    SILENT //无UI交互
}