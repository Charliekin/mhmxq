package com.mhm.xq.ui.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.mhm.xq.MyApp
import com.mhm.xq.R
import com.mhm.xq.entity.New
import com.mhm.xq.ui.base.adapter.BaseRcvAdapter
import com.mhm.xq.ui.base.adapter.BaseRcvViewHolder
import com.mhm.xq.utils.GlideUtil

class NewsColumnAdapter : BaseRcvAdapter<New>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
            WebNewsViewHolder(parent)

    class WebNewsViewHolder : BaseRcvViewHolder<New> {

        @BindView(R.id.tvWebNewsTitle)
        @JvmField
        var mTvWebNewsTitle: TextView? = null
        @BindView(R.id.tvWebNewsTime)
        @JvmField
        var mTvWebNewsTime: TextView? = null
        @BindView(R.id.tvWebNewsAuthor)
        @JvmField
        var mTvWebNewsAuthor: TextView? = null
        @BindView(R.id.ivWebNewsIcon)
        @JvmField
        var mIvWebNewsIcon: ImageView? = null

        constructor(parent: ViewGroup?) : super(R.layout.my_item_web_news, parent)

        override fun onBindViews() {
            ButterKnife.bind(this, itemView)
        }

        override fun onSetViews() {
        }

        override fun onUpdateViews(model: New, position: Int) {
            mTvWebNewsTitle!!.text = model.getTitle()
            mTvWebNewsAuthor!!.text = model.getSource()
            mTvWebNewsTime!!.text = "" + model.getCt()
            GlideUtil.loadImage(MyApp.getContext()!!, "", R.mipmap.user_icon_default, mIvWebNewsIcon!!)
        }

    }

}