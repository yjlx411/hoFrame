package com.krad.origin.pager

import com.krad.origin.R
import com.krad.origin.databinding.PagerWebBinding
import com.krad.origin.hoframe.pager.Pager

class WebPager : Pager<PagerWebBinding>() {

    private var title: String? = null
    private var url: String? = null
    private var htmLContent: String? = null
    private var mode = 0  //1.url加载 2.html加载

    private var ua: String? = null

    override fun getLayoutID(): Int {
        return R.layout.pager_web
    }

    override fun initData() {
        mode = getIntExtra("mode")
        title = getStringExtra("title")
        if (mode == 1) {
            url = getStringExtra("url")
        } else if (mode == 2) {
            htmLContent = getStringExtra("htmLContent")
        }
        ua = getStringExtra("UA")
        if (ua == null) ua = ""
        //ua += ";groupName="
    }

    override fun initView() {
        uIndex.initTitlebar(title ?: "")
        val webSetting = VB()?.webView?.settings
        val UserAnget = webSetting?.userAgentString
        webSetting?.setUserAgentString(UserAnget + ua)
        webSetting?.setAppCachePath(getActivity()?.getDir("appcache", 0)?.path) //设置应用缓存目录
        webSetting?.setDatabasePath(getActivity()?.getDir("databases", 0)?.path) //设置数据库缓存路径
        webSetting?.setGeolocationDatabasePath(getActivity()?.getDir("geolocation", 0)?.getPath()) //设置定位的数据库路径

        if (mode == 1) {
            VB()?.webView?.loadUrl(url ?: "")
        } else if (mode == 2) {
            if (htmLContent != null && !htmLContent!!.startsWith("http")) {
                htmLContent = getHtmlData(htmLContent!!)
                val mimeType = "text/html"
                val enCoding = "utf-8"
                VB()?.webView?.loadDataWithBaseURL(null, htmLContent!!, mimeType, enCoding, null)
            }
        }
    }

    private fun getHtmlData(bodyHTML: String): String? {
        val head = ("<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">"
                + "<link href=\"http://youeryuanapp.oss-cn-shanghai.aliyuncs.com/quill-editor/quill.core.css\" rel=\"stylesheet\">"
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>")
        return "<html>$head<body><div class=\"ql-editor\">$bodyHTML</div></body></html>"
    }
}