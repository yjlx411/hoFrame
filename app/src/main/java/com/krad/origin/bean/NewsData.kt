package com.krad.origin.bean

import com.krad.origin.bean.NewsData.News
import java.util.ArrayList

class NewsData {
    var stat: String? = null
    var data: ArrayList<News>? = null
    var page: String? = null
    var pageSize: String? = null

    class News {
        var uniquekey: String? = null
        var title: String? = null
        var date: String? = null
        var category: String? = null
        var author_name: String? = null
        var url: String? = null
        var thumbnail_pic_s: String? = null
        var thumbnail_pic_s02: String? = null
        var thumbnail_pic_s03: String? = null
        var is_content: String? = null
    }
}