package com.mhm.xq.ui.common.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.mhm.xq.R
import com.mhm.xq.ui.base.dialog.FixStatusBarBottomSheetDialog

class BottomDialogFragment : BottomSheetDialogFragment() {

    var mUnbinder: Unbinder? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: BottomSheetDialog = FixStatusBarBottomSheetDialog(context!!, theme)
        dialog.setContentView(R.layout.my_dialog_general)
        mUnbinder = ButterKnife.bind(this, dialog)
        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        mUnbinder!!.unbind()
    }

}