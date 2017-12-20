package com.mhm.xq.ui.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.mhm.xq.MyApp
import com.mhm.xq.entity.Photo
import com.mhm.xq.utils.GlideUtil

class ChooseSinglePhotoAdapter : BaseChoosePhotoAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
            ViewHolder(parent)

    inner class ViewHolder(parent: ViewGroup?) : BaseViewHolder(parent) {

        override fun onSetViews() {
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    //TODO 跳转到裁剪页
                }
            })
        }

        override fun onUpdateViews(model: Photo, position: Int) {
            mIvCPSelector!!.visibility = View.GONE
            GlideUtil.loadLocalFromUri(MyApp.getContext()!!, model.getPath(), mIvChoosePhoto!!)
        }

    }

}