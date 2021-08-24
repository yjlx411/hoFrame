package com.krad.origin.pager

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.krad.origin.R
import com.krad.origin.adpater.FragmentTabAdapter
import com.krad.origin.databinding.PagerNewsBinding
import com.krad.origin.hoframe.index.PagerIndex
import com.krad.origin.hoframe.pager.HoFragment
import com.krad.origin.hoframe.pager.Pager

class NewsPager : Pager<PagerNewsBinding>() {

    val categories = arrayOf("推荐", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "健康")

    override fun getLayoutID(): Int {
        return R.layout.pager_news
    }

    override fun initData() {
    }

    override fun initView() {
        val fragments: ArrayList<Fragment> = ArrayList()
        categories.forEach {
            val toFragment = PagerIndex.News_Category.prepare().add("type", it).toFragment()
            fragments.add(toFragment)
        }
        VB()?.vpNews?.offscreenPageLimit = 3
        VB()?.vpNews?.adapter = FragmentTabAdapter(fragmentManager, fragments)
        VB()?.stlNews?.setViewPager(VB()?.vpNews, categories)
        val onPageChangeListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {
                val fragment = fragments[position]
                val coreUnit = (fragment as? HoFragment)?.getCoreUnit<NewsCategoryPager>()
                coreUnit?.getData()
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        }
        VB()?.vpNews?.addOnPageChangeListener(onPageChangeListener)
        VB()?.vpNews?.post {
            onPageChangeListener.onPageSelected(0)
        }
    }

}