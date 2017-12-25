package com.mhm.xq.ui.home.view

import android.content.Context
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.mhm.xq.R

class NewsTabLayout {
    var mContext: Context? = null
    var mTabLayout: TabLayout? = null

    constructor(context: Context, tabLayout: TabLayout) {
        mContext = context
        mTabLayout = tabLayout
    }

    fun setTabData(arrayList: ArrayList<String>) {
        var tabCount = mTabLayout!!.tabCount
        for (i in 0..(tabCount - 1)) {
            var tab: TabLayout.Tab = mTabLayout!!.getTabAt(i)!!
            var view: View?
            view = if (tab.customView == null) {
                LayoutInflater.from(mContext).inflate(R.layout.common_view_news_tab_item, null)
            } else {
                tab.customView
            }
            var textView = view!!.findViewById<TextView>(R.id.tvNewsTab)
            textView!!.text = arrayList[i]
            tab.customView = view
        }
    }
}
