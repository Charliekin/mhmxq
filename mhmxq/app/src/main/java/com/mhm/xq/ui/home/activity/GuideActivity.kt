package com.mhm.xq.ui.home.activity

import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.ui.base.widget.SimpleIndicatorView
import com.mhm.xq.ui.home.adapter.GuidePagerAdapter
import com.mhm.xq.utils.NavigationBarUtil
import com.mhm.xq.utils.PermissionUtil


class GuideActivity : BaseActivity() {

    @BindView(R.id.vpGuide)
    @JvmField
    var mVpGuide: ViewPager? = null
    @BindView(R.id.sivGuide)
    @JvmField
    var mSivGuide: SimpleIndicatorView? = null

    private var mDecorView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_guide)
        ButterKnife.bind(this)
        init()
    }

    private fun init() {
        mDecorView = window.decorView
        val adapter: GuidePagerAdapter = GuidePagerAdapter(this)
        mVpGuide!!.adapter = adapter
        mSivGuide!!.setViewPager(mVpGuide!!)
        val flag = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide
                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        val nBU: NavigationBarUtil = NavigationBarUtil(this)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT
                || !nBU.checkDeviceHasNavigationBar()) {
            return
        } else {
            mDecorView!!.systemUiVisibility = flag
        }
        PermissionUtil.verifyStoragePermissions(this)
    }

    override fun onBackPressed() {
    }

}