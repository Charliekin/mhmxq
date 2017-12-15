package com.mhm.xq.ui.common.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.ui.common.adapter.ChoosePhotoAdapter

class ChoosePhotoFromAlbumActivity : BaseActivity() {

    @BindView(R.id.rvChoosePhoto)
    @JvmField
    var mRvChoosePhoto: RecyclerView? = null

    var mAdapter: ChoosePhotoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_choose_photo)
        ButterKnife.bind(this)
        init()
    }

    private fun init() {
        setTitleBarTitleLeft(R.string.picture, true)
        var manager = GridLayoutManager(this, 3)
        mRvChoosePhoto!!.layoutManager = manager
        mAdapter = ChoosePhotoAdapter()
        mRvChoosePhoto!!.adapter = mAdapter
        var list: ArrayList<String>? = ArrayList()
        for (i in 0..100) {
            list!!.add("" + i)
        }
        mAdapter!!.addDataList(list!!)
    }

    @OnClick(R.id.tvChoosePhoto)
    fun onClick(view: View) {
        when (view.id) {
            R.id.tvChoosePhoto -> {

            }
        }
    }
}