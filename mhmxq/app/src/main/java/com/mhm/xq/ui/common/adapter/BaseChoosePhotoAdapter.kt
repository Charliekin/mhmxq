package com.mhm.xq.ui.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.mhm.xq.R
import com.mhm.xq.entity.Photo
import com.mhm.xq.ui.base.adapter.BaseRcvAdapter
import com.mhm.xq.ui.base.adapter.BaseRcvViewHolder

open class BaseChoosePhotoAdapter : BaseRcvAdapter<Photo>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return BaseViewHolder(parent)
    }

    open inner class BaseViewHolder : BaseRcvViewHolder<Photo> {

        @BindView(R.id.ivChoosePhoto)
        @JvmField
        var mIvChoosePhoto: ImageView? = null
        @BindView(R.id.ivCPSelector)
        @JvmField
        var mIvCPSelector: ImageView? = null

        constructor(parent: ViewGroup?) : super(R.layout.common_item_choose_photo, parent)

        override fun onBindViews() {
            ButterKnife.bind(this, itemView)
        }

        override fun onSetViews() {
        }

        override fun onUpdateViews(model: Photo, position: Int) {
        }

    }
}