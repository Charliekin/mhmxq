package com.mhm.xq.ui.common.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.mhm.xq.R
import com.mhm.xq.constacts.Extras
import com.mhm.xq.ui.base.adapter.BaseRcvAdapter
import com.mhm.xq.ui.base.dialog.FixStatusBarBottomSheetDialog
import com.mhm.xq.ui.common.adapter.BottomDialogAdapter

class BottomDialogFragment : BottomSheetDialogFragment {

    @BindView(R.id.rcvGeneral)
    @JvmField
    var mRcvGeneral: RecyclerView? = null

    private var mAdapter: BottomDialogAdapter? = null
    var mUnbinder: Unbinder? = null
    var mOnItemClickListener: BaseRcvAdapter.OnItemClickListener? = null

    constructor()

    @SuppressLint("ValidFragment")
    constructor(onItemClickListener: BaseRcvAdapter.OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: BottomSheetDialog = FixStatusBarBottomSheetDialog(context!!, theme)
        dialog.setContentView(R.layout.my_dialog_general)
        mUnbinder = ButterKnife.bind(this, dialog)
        init()
        return dialog
    }

    private fun init() {
        mAdapter = BottomDialogAdapter(this)
        mRcvGeneral!!.adapter = mAdapter
        mRcvGeneral!!.layoutManager = LinearLayoutManager(this.context)
        if (mOnItemClickListener != null) {
            mAdapter!!.setOnItemClickListener(mOnItemClickListener!!)
        }
        initData()
    }

    private fun initData() {
        val list = arguments!!.getStringArrayList(Extras.BOTTOM_DIALOG_ARGUMENT)
        if (list.isNotEmpty()) {
            mAdapter!!.changeDataList(list)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mUnbinder!!.unbind()
    }

}