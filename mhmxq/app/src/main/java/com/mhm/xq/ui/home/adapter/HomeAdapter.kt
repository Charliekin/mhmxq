package com.mhm.xq.ui.home.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.mhm.xq.ui.home.fragment.BabyFragment
import com.mhm.xq.ui.home.fragment.HelpFragment
import com.mhm.xq.ui.home.fragment.HomePageFragment
import com.mhm.xq.ui.home.fragment.MeFragment

class HomeAdapter(fg: FragmentManager) : FragmentPagerAdapter(fg) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> HomePageFragment()
        1 -> BabyFragment()
        2 -> HelpFragment()
        else -> MeFragment()
    }

    override fun getCount(): Int = 4

}