package com.krad.origin.pager

import android.graphics.Color
import com.krad.origin.R
import com.krad.origin.databinding.PagerHomeBinding
import com.krad.origin.hoframe.pager.Pager
import android.view.View
import android.widget.ImageView

import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import com.krad.origin.hoframe.view.widget.tabView.TabViewChild

import com.google.gson.reflect.TypeToken
import com.krad.origin.hoframe.index.PagerIndex
import com.krad.origin.hoframe.util.DimensionUtil
import com.next.easynavigation.view.EasyNavigationBar
import java.lang.Exception


class HomePager : Pager<PagerHomeBinding>() {

    override fun getLayoutID(): Int {
        return R.layout.pager_home
    }

    override fun initData() {

    }

    override fun initView() {
        if (VB()?.navigationBar != null) {
            val fragments: ArrayList<Fragment> = ArrayList()
            val newsFragment = PagerIndex.News.prepare().toFragment(true)
            val lifeFragment = PagerIndex.Life.prepare().toFragment(true)
            fragments.add(newsFragment)
            fragments.add(lifeFragment)

            with(VB()?.navigationBar!!) {
                defaultSetting()  //恢复默认配置、可用于重绘导航栏
                titleItems(arrayOf("新闻", "生活"))      //  Tab文字集合  只传文字则只显示文字
                normalIconItems(intArrayOf(R.drawable.ic_news_normal,
                    R.drawable.ic_news_normal))   //  Tab未选中图标集合
                selectIconItems(intArrayOf(R.drawable.ic_news_select,
                    R.drawable.ic_news_select))   //  Tab选中图标集合
                fragmentList(fragments)       //  fragment集合
                fragmentManager(this@HomePager.fragmentManager)
                iconSize(25F)     //Tab图标大小
                tabTextSize(12)   //Tab文字大小
                tabTextTop(2)     //Tab文字距Tab图标的距离
                normalTextColor(Color.parseColor("#999999"))   //Tab未选中时字体颜色
                selectTextColor(Color.parseColor("#49A5FB"))   //Tab选中时字体颜色
                navigationBackground(Color.parseColor("#ffffff"))   //导航栏背景色
                setOnTabClickListener(object : EasyNavigationBar.OnTabClickListener {
                    override fun onTabSelectEvent(view: View?, position: Int): Boolean {
                        //Tab点击事件  return true 页面不会切换
                        return false;
                    }

                    override fun onTabReSelectEvent(view: View?, position: Int): Boolean {
                        //Tab重复点击事件
                        return false;
                    }
                })
                smoothScroll(false)  //点击Tab  Viewpager切换是否有动画
                canScroll(false)    //Viewpager能否左右滑动
                mode(EasyNavigationBar.NavigationMode.MODE_NORMAL)   //默认MODE_NORMAL 普通模式  //MODE_ADD 带加号模式
                navigationHeight(50)  //导航栏高度
                setOnTabLoadListener {
                    object : EasyNavigationBar.OnTabLoadListener {
                        //Tab加载完毕回调
                        override fun onTabLoadCompleteEvent() {
                            //navigationBar.setMsgPointCount(0, 7);
                            //navigationBar.setMsgPointCount(1, 109);
                            //navigationBar.setHintPoint(4, true);
                        }
                    }
                }
                //.setupWithViewPager() //ViewPager或ViewPager2
                build()

                navigationLayout.elevation = DimensionUtil.dpToPx(2).toFloat()
            }
        }
    }
}