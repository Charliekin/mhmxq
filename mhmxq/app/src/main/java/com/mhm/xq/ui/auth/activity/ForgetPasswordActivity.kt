package com.mhm.xq.ui.auth.activity

import android.os.Bundle
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity

class ForgetPasswordActivity : BaseActivity() {

    @BindView(R.id.btAuth)
    @JvmField
    var mBtAuth: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_base_auth)
        ButterKnife.bind(this)
        init()
    }

    private fun init() {
        setTitleBarTitle(R.string.forget_password, true)
        initVariable()
    }

    private fun initVariable() {
        mBtAuth!!.setText(R.string.confirm)
    }
}