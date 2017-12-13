package com.mhm.xq.ui.home.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.ui.general.SearchActivity
import com.mhm.xq.ui.home.adapter.HomeAdapter
import com.mhm.xq.ui.home.view.HomeTabLayout
import com.mhm.xq.utils.NetUtil

class HomeActivity : BaseActivity(), View.OnClickListener {

    @BindView(R.id.tlHome)
    @JvmField
    var mTlHome: TabLayout? = null
    @BindView(R.id.vpHome)
    @JvmField
    var mVpHome: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_home)
        ButterKnife.bind(this)
        init()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        getTitleBar()!!.setOnLeftClickListener(null)
    }

    private fun init() {
        initVariables()
        initListener()
    }

    private fun initVariables() {
        setTitleBarLeftText(applicationInfo.loadLabel(packageManager).toString())
        setTitleBarRightIcon(R.drawable.my_home_search_selector, this)
        mVpHome!!.adapter = HomeAdapter(supportFragmentManager)
        mTlHome!!.setupWithViewPager(mVpHome)
        mVpHome!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mTlHome))
        mTlHome!!.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mVpHome))
        HomeTabLayout(this, mTlHome!!)
    }

    private fun initListener() {
        mTlHome!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position != 0) {
                    setTitleBarRightIcon(0, this@HomeActivity)
                } else {
                    setTitleBarRightIcon(R.drawable.my_home_search_selector, this@HomeActivity)
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (mTlHome!!.selectedTabPosition) {
            0 -> startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    override fun handleReceiver(context: Context?, intent: Intent?) {
        super.handleReceiver(context, intent)
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent!!.action)) {
            if (NetUtil.isNetEnabled()) {
                setTitleBarLeftText(applicationInfo.loadLabel(packageManager).toString())
            } else {
                setTitleBarLeftText(String.format(resources.getString(R.string.not_connected),
                        applicationInfo.loadLabel(packageManager).toString()))
            }
        }
    }
}