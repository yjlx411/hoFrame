package com.krad.origin.hoframe.index

import com.krad.origin.hoframe.pager.Pager
import com.krad.origin.hoframe.pager.PagerLoader
import com.krad.origin.pager.*
import kotlin.reflect.KClass

enum class PagerIndex(clz: KClass<out Pager<*>>?){

    //主页
    Home(HomePager::class),
    //新闻分类
    News_Category(NewsCategoryPager::class),
    //新闻
    News(NewsPager::class),
    //生活
    Life(LifePager::class),
    //网页
    Web(WebPager::class);

    private val pagerClz = clz

    fun prepare(): PagerLoader {
        return PagerLoader(pagerClz?.java?.newInstance())
    }
}