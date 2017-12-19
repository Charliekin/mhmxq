package com.mhm.xq.ui.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.mhm.xq.MyApp
import com.mhm.xq.R
import com.mhm.xq.entity.Photo
import com.mhm.xq.ui.base.adapter.BaseRcvAdapter
import com.mhm.xq.ui.base.adapter.BaseRcvViewHolder
import com.mhm.xq.utils.GlideUtil
import com.mhm.xq.utils.ToastUtil

class ChoosePhotoAdapter : BaseRcvAdapter<Photo>() {

    var mSelectorPosition: ArrayList<Photo> = ArrayList()
    val MAX_PIC: Int = 9

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
            ViewHolder(parent)

    inner class ViewHolder : BaseRcvViewHolder<Photo> {

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
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    if (mSelectorPosition.size >= MAX_PIC) {
                        ToastUtil.show(MyApp.getContext()!!,
                                String.format(MyApp.getContext()!!.resources.getString(R.string.beyond_picture), MAX_PIC))
                        return
                    }
                    if (mSelectorPosition.contains(getDataList()[sPosition!!])) {
                        mSelectorPosition.remove(getDataList()[sPosition!!])
                        mIvCPSelector!!.isSelected = false
                    } else {
                        mIvCPSelector!!.isSelected = true
                        mSelectorPosition.add(getDataList()[sPosition!!])
                    }
                }
            })
        }

        override fun onUpdateViews(model: Photo, position: Int) {
            mIvCPSelector!!.isSelected = mSelectorPosition.contains(model)
            GlideUtil.loadLocalFromUri(MyApp.getContext()!!, model.getPath(), mIvChoosePhoto!!)
        }

    }
}