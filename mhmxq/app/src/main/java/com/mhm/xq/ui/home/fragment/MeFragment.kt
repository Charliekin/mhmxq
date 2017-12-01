package com.mhm.xq.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.zxing.client.android.CaptureActivity
import com.mhm.xq.R
import com.mhm.xq.bll.ConfigManager
import com.mhm.xq.ui.auth.activity.SignActivity
import com.mhm.xq.ui.base.fragment.BaseFragment
import com.mhm.xq.ui.me.activity.MyQrActivity
import com.mhm.xq.widget.CategoryNavigationBar

class MeFragment : BaseFragment() {
    @BindView(R.id.llMe)
    @JvmField
    var mLlMe: LinearLayout? = null
    @BindView(R.id.ivUserIcon)
    @JvmField
    var mIvUserIcon: ImageView? = null
    @BindView(R.id.tvUserName)
    @JvmField
    var mTvUserName: TextView? = null
    @BindView(R.id.ivQrCode)
    @JvmField
    var mIvQrCode: ImageView? = null
    @BindView(R.id.cnbAttention)
    @JvmField
    var mCnbAttention: CategoryNavigationBar? = null
    @BindView(R.id.cnbCollect)
    @JvmField
    var mCnbCollect: CategoryNavigationBar? = null
    @BindView(R.id.cnbFeedback)
    @JvmField
    var mCnbFeedback: CategoryNavigationBar? = null
    @BindView(R.id.cnbSet)
    @JvmField
    var mCnbSet: CategoryNavigationBar? = null


    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.my_fragment_me)
        ButterKnife.bind(this, rootView!!)
    }

    @OnClick(R.id.llMe, R.id.ivQrCode, R.id.cnbAttention, R.id.cnbCollect, R.id.cnbFeedback, R.id.cnbSet)
    fun onViewClick(view: View) {
        if (!ConfigManager.getInstance()!!.checkIsAuthLogin()) {
            startActivity(Intent(context, SignActivity::class.java))
            return
        }
        when (view.id) {
            R.id.llMe -> {
                startActivity(Intent(context, CaptureActivity::class.java))
            }
            R.id.ivQrCode -> {
                startActivity(Intent(context, MyQrActivity::class.java))
            }
            R.id.cnbAttention -> {

            }
            R.id.cnbCollect -> {

            }
            R.id.cnbFeedback -> {

            }
            R.id.cnbSet -> {

            }
        }
    }
}