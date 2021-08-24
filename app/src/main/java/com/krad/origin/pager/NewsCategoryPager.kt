package com.krad.origin.pager

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.krad.origin.R
import com.krad.origin.bean.NewsData
import com.krad.origin.databinding.PagerNewsCategoryBinding
import com.krad.origin.hoframe.index.NetApi
import com.krad.origin.hoframe.index.PagerIndex
import com.krad.origin.hoframe.net.BaseResponse
import com.krad.origin.hoframe.net.DefaultErrorHandler
import com.krad.origin.hoframe.net.SuccessObserver
import com.krad.origin.hoframe.pager.Pager
import com.krad.origin.hoframe.util.DimensionUtil
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader

class NewsCategoryPager : Pager<PagerNewsCategoryBinding>() {

    var mAdapter: BaseQuickAdapter<NewsData.News, BaseViewHolder>? = null
    var mData: MutableList<NewsData.News> = ArrayList()
    var type: String? = null
    var itemHeight = 0
    var pageNum = 1

    init {
        addNetUnit(NetApi.QueryNews.make(object : SuccessObserver<NewsData>() {
            override fun onSuccess(flag: Int, data: NewsData?) {
                if (flag == 2) {
                    //加载更多
                    if (data?.data?.size!! > 0) {
                        mData.addAll(data.data!!)
                        mAdapter?.notifyDataSetChanged()
                        VB()?.srlNews?.finishLoadMore()
                    } else {
                        VB()?.srlNews?.finishLoadMoreWithNoMoreData()
                    }
                } else {
                    if (flag == 0) {
                        //初始化
                        uIndex.net_success()
                    }
                    mData.clear()
                    data?.data?.let(mData::addAll)
                    mAdapter?.notifyDataSetChanged()
                    VB()?.srlNews?.finishRefresh()
                }
            }
        }).setErrorHandler(object : DefaultErrorHandler<NewsData>(uIndex) {
            override fun onCodeIntercept(response: BaseResponse<NewsData>): Boolean {
                if (response.flag == 0) {
                    super.onCodeIntercept(response)
                    uIndex.net_err()
                    return true
                } else {
                    if (!super.onCodeIntercept(response)) {
                        uIndex.toastNetFailMsg()
                    }
                    if (response.flag == 1) {
                        VB()?.srlNews?.finishRefresh(false)
                    } else {
                        VB()?.srlNews?.finishLoadMore(false)
                    }
                }
                return true
            }

            override fun onFailIntercept(response: BaseResponse<NewsData>): Boolean {
                if (response.flag == 0) {
                    uIndex.net_err()
                    return true
                } else {
                    uIndex.toastNetFailMsg()
                    if (response.flag == 1) {
                        VB()?.srlNews?.finishRefresh(false)
                    } else {
                        VB()?.srlNews?.finishLoadMore(false)
                    }
                }
                return true
            }
        }))
    }

    override fun getLayoutID(): Int {
        return R.layout.pager_news_category
    }


    override fun initData() {

    }


    fun getData() {
        if (type == null) {
            type = getStringExtra("type")
            netAct(NetApi.QueryNews)
                .getMode()
                .setFlag(0)
                .addParams("type", type)
                .addParams("page", pageNum)
                .addParams("is_filter", 1)
                .start()
        }
    }

    override fun initView() {

        uIndex.netStateVS?.onRetry = Runnable {
            uIndex.net_load()
            netAct(NetApi.QueryNews)
                .getMode()
                .setFlag(0)
                .addParams("type", type)
                .addParams("page", pageNum)
                .addParams("is_filter", 1)
                .start()
        }

        if (VB()?.srlNews != null) {
            with(VB()?.srlNews!!) {
                setBackgroundResource(R.color.bg_gray_f5)
                val materialHeader = MaterialHeader(context)
                materialHeader.setColorSchemeColors(Color.parseColor("#45AEFF"))
                val classicsFooter = ClassicsFooter(context)
                classicsFooter.setBackgroundColor(Color.parseColor("#00000000"))
                setRefreshHeader(materialHeader)
                setRefreshFooter(classicsFooter)

                setOnRefreshListener {
                    pageNum = 1
                    netAct(NetApi.QueryNews)
                        .silentMode()
                        .setFlag(1)
                        .addParams("type", type)
                        .addParams("page", pageNum)
                        .addParams("is_filter", 1)
                        .start()
                }
                setOnLoadMoreListener {
                    netAct(NetApi.QueryNews)
                        .silentMode()
                        .setFlag(2)
                        .addParams("type", type)
                        .addParams("page", ++pageNum)
                        .addParams("is_filter", 1)
                        .start()
                }
            }
        }

        itemHeight =
            (getActivity()?.resources?.displayMetrics?.widthPixels!! - DimensionUtil.dpToPx(30)) / 2
        val layoutManager = GridLayoutManager(getContext(), 2)
        VB()?.rvNews?.layoutManager = layoutManager
        mAdapter =
            object : BaseQuickAdapter<NewsData.News, BaseViewHolder>(R.layout.item_news_category,
                mData) {
                override fun convert(holder: BaseViewHolder, item: NewsData.News) {
                    holder.setText(R.id.tv_title_category, item.title ?: "未知标题")
                        .setText(R.id.tv_author_category, item.author_name ?: "未知作者")
                    val ivNews: ImageView = holder.getView(R.id.iv_news_category)
                    Glide.with(ivNews).load(item.thumbnail_pic_s)
                        .into(holder.getView(R.id.iv_news_category))
                    holder.itemView.setOnClickListener {
                        item.url?.let {
                            if (it.startsWith("http")) {
                                PagerIndex.Web.prepare()
                                    .add("mode","1")
                                    .add("url", item.url?:"")
                                    .start(getContext())
                            }
                        }
                    }

                    (holder.itemView.layoutParams as? RecyclerView.LayoutParams)?.topMargin =
                        if (holder.adapterPosition == 0) DimensionUtil.dpToPx(12) else 0
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
                    val holder = super.onCreateViewHolder(parent, viewType)
                    val ivNews: View = holder.getView(R.id.iv_news_category)
                    ivNews.layoutParams?.height = itemHeight
                    return holder
                }
            }
        VB()?.rvNews?.adapter = mAdapter

        uIndex.net_load()
    }

}