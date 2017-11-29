package com.mhm.xq.ui.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.mhm.xq.MyApp
import com.mhm.xq.R
import com.mhm.xq.utils.ToastUtil

class NoNetworkTipView : LinearLayout {
    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, def: Int) : super(context, attrs, def) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.my_view_no_network_tip, this)
        findViewById<FrameLayout>(R.id.flTipContainer).setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                ToastUtil.show(MyApp.getContext(), resources.getString(R.string.have_no_network));
            }
        })
    }
}