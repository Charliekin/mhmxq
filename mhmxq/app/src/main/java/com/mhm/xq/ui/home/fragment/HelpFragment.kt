package com.mhm.xq.ui.home.fragment

import android.os.Bundle
import com.mhm.xq.R
import com.mhm.xq.ui.base.fragment.LazyFragment

class HelpFragment : LazyFragment() {

    override fun onFragmentCreateView(savedInstanceState: Bundle?) {
        super.onFragmentCreateView(savedInstanceState)
        setContentView(R.layout.my_fragment_help)
    }

}