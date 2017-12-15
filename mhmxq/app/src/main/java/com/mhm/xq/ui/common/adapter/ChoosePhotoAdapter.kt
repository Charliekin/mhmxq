package com.mhm.xq.ui.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.mhm.xq.MyApp
import com.mhm.xq.R
import com.mhm.xq.ui.base.adapter.BaseRcvAdapter
import com.mhm.xq.ui.base.adapter.BaseRcvViewHolder
import com.mhm.xq.utils.ToastUtil

class ChoosePhotoAdapter : BaseRcvAdapter<String>() {

    var mSelectorPosition: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
            ViewHolder(parent)

    inner class ViewHolder : BaseRcvViewHolder<String> {

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
                    if (mSelectorPosition.size >= 9) {
                        ToastUtil.show(MyApp.getContext()!!, R.string.beyond_picture)
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

        override fun onUpdateViews(model: String, position: Int) {
        }

    }
}