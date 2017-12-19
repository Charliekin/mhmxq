package com.mhm.xq.ui.common.adapter

import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.mhm.xq.R
import com.mhm.xq.ui.base.adapter.BaseRcvAdapter
import com.mhm.xq.ui.base.adapter.BaseRcvViewHolder

class BottomDialogAdapter : BaseRcvAdapter<String> {

    var mFragment: BottomSheetDialogFragment? = null

    constructor(fragment: BottomSheetDialogFragment) {
        mFragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
            ViewHolder(parent)

    inner class ViewHolder : BaseRcvViewHolder<String> {

        @BindView(R.id.tvDialogItem)
        @JvmField
        var mTvDialogItem: TextView? = null

        constructor(parent: ViewGroup?) : super(R.layout.my_item_bottom_dialog, parent)

        override fun onBindViews() {
            ButterKnife.bind(this, itemView)
        }

        override fun onSetViews() {
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener!!.onItemClick(p0, sPosition)
                        mFragment!!.dismiss()
                    }
                }
            })
        }

        override fun onUpdateViews(model: String, position: Int) {
            mTvDialogItem!!.text = model
        }

    }

}