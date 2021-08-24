package com.krad.origin.hoframe.pager

import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import com.krad.origin.R

class PagerConfig {

    var isNetPager = true //是否要请求网络

    var isLinear = true //根布局是否是线性布局

    var isNetBind = true //网络访问是否自动绑定生命周期

    var isSBInvasion = true //是否是沉浸式状态栏

    var isHideInputRoot = false //点击空白处隐藏键盘

    @LayoutRes
    val netStateLayoutId = R.layout.layout_net_state //网络访问状态布局的id

    @LayoutRes
    val titlebarLayoutId = R.layout.layout_titlebar //指定网络访问状态布局的id

    @ColorRes
    val pageBackground = R.color.white //页面背景
}