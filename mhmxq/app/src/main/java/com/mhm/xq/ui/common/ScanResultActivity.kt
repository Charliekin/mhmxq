package com.mhm.xq.ui.common

import android.os.Bundle
import com.mhm.xq.constacts.Extras
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.utils.ToastUtil

class ScanResultActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val strText = intent.getStringExtra(Extras.SCAN_RESULT)
        ToastUtil.show(this, strText)
    }
}