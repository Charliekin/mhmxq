package com.mhm.xq.ui.home.view

import android.os.Build
import android.support.design.widget.TabLayout
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.ui.home.widget.HomeTabWidget
import com.mhm.xq.utils.DensityUtil

class HomeTabLayout {

    private var mBaseActivity: BaseActivity? = null
    private var mHomeView: HomeTabWidget? = null
    private var mDisasterView: HomeTabWidget? = null
    private var mHelpView: HomeTabWidget? = null
    private var mMeView: HomeTabWidget? = null

    constructor(baseActivity: BaseActivity, tabLayout: TabLayout) {
        mBaseActivity = baseActivity
        initTabLayoutVariables(tabLayout)
    }

    private fun initTabLayoutVariables(tabLayout: TabLayout) {
        for (index in 0..(tabLayout.tabCount - 1)) {
            val tab: TabLayout.Tab = tabLayout.getTabAt(index)!!
            tab.customView = HomeTabWidget(mBaseActivity!!)
        }
        mHomeView = tabLayout.getTabAt(0)!!.customView!! as HomeTabWidget
        mDisasterView = tabLayout.getTabAt(1)!!.customView!! as HomeTabWidget
        mHelpView = tabLayout.getTabAt(2)!!.customView!! as HomeTabWidget
        mMeView = tabLayout.getTabAt(3)!!.customView!! as HomeTabWidget
        initTabValue()
    }

    private fun initTabValue() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mHomeView!!.setIcon(mBaseActivity!!.resources.getDrawable(R.drawable.home_page_selector
                    , mBaseActivity!!.theme))
        } else {
            mHomeView!!.setIcon(mBaseActivity!!.resources.getDrawable(R.drawable.home_page_selector))
        }
        mHomeView!!.setTabNameText(mBaseActivity!!.resources.getString(R.string.home_tab_page))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mHomeView!!.setTabNameColor(mBaseActivity!!.resources.getColorStateList(R.color.my_color_home_tab_text
                    , mBaseActivity!!.theme))
        } else {
            mHomeView!!.setTabNameColor(mBaseActivity!!.resources.getColorStateList(R.color.my_color_home_tab_text))
        }
        mHomeView!!.setIconSize(DensityUtil.dip2px(mBaseActivity!!, 22F),
                DensityUtil.dip2px(mBaseActivity!!, 22F), DensityUtil.dip2px(mBaseActivity!!, 3F))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDisasterView!!.setIcon(mBaseActivity!!.resources.getDrawable(R.drawable.home_disaster_selector
                    , mBaseActivity!!.theme))
        } else {
            mDisasterView!!.setIcon(mBaseActivity!!.resources.getDrawable(R.drawable.home_disaster_selector))
        }
        mDisasterView!!.setTabNameText(mBaseActivity!!.resources.getString(R.string.home_tab_disaster))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mDisasterView!!.setTabNameColor(mBaseActivity!!.resources.getColorStateList(R.color.my_color_home_tab_text
                    , mBaseActivity!!.theme))
        } else {
            mDisasterView!!.setTabNameColor(mBaseActivity!!.resources.getColorStateList(R.color.my_color_home_tab_text))
        }
        mDisasterView!!.setIconSize(DensityUtil.dip2px(mBaseActivity!!, 22F),
                DensityUtil.dip2px(mBaseActivity!!, 22F), DensityUtil.dip2px(mBaseActivity!!, 3F))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mHelpView!!.setIcon(mBaseActivity!!.resources.getDrawable(R.drawable.home_help_car_selector
                    , mBaseActivity!!.theme))
        } else {
            mHelpView!!.setIcon(mBaseActivity!!.resources.getDrawable(R.drawable.home_help_car_selector))
        }
        mHelpView!!.setTabNameText(mBaseActivity!!.resources.getString(R.string.home_tab_help))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mHelpView!!.setTabNameColor(mBaseActivity!!.resources.getColorStateList(R.color.my_color_home_tab_text
                    , mBaseActivity!!.theme))
        } else {
            mHelpView!!.setTabNameColor(mBaseActivity!!.resources.getColorStateList(R.color.my_color_home_tab_text))
        }
        mHelpView!!.setIconSize(DensityUtil.dip2px(mBaseActivity!!, 22F),
                DensityUtil.dip2px(mBaseActivity!!, 22F), DensityUtil.dip2px(mBaseActivity!!, 3F))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mMeView!!.setIcon(mBaseActivity!!.resources.getDrawable(R.drawable.home_me_selector
                    , mBaseActivity!!.theme))
        } else {
            mMeView!!.setIcon(mBaseActivity!!.resources.getDrawable(R.drawable.home_me_selector))
        }
        mMeView!!.setTabNameText(mBaseActivity!!.resources.getString(R.string.home_tab_me))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mMeView!!.setTabNameColor(mBaseActivity!!.resources.getColorStateList(R.color.my_color_home_tab_text
                    , mBaseActivity!!.theme))
        } else {
            mMeView!!.setTabNameColor(mBaseActivity!!.resources.getColorStateList(R.color.my_color_home_tab_text))
        }
        mMeView!!.setIconSize(DensityUtil.dip2px(mBaseActivity!!, 22F),
                DensityUtil.dip2px(mBaseActivity!!, 22F), DensityUtil.dip2px(mBaseActivity!!, 3F))
    }
}