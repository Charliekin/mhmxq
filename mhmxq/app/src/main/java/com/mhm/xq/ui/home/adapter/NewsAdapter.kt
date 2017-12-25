package com.mhm.xq.ui.home.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.mhm.xq.constacts.Extras
import com.mhm.xq.ui.home.fragment.ChannelFragment

class NewsAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private var mCount: Int = 0

    override fun getItem(position: Int): Fragment {
        var fragment = ChannelFragment()
        var bundle = Bundle()
        bundle.putInt(Extras.TAB_INDEX, position)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int = mCount

    fun setCount(count: Int) {
        mCount = count
        notifyDataSetChanged()
    }

}