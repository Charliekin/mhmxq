package com.mhm.xq.ui.common.activity

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AbsListView
import butterknife.BindView
import butterknife.ButterKnife
import com.mhm.xq.R
import com.mhm.xq.constacts.ExtraValues
import com.mhm.xq.constacts.Extras
import com.mhm.xq.entity.PhotoDirectory
import com.mhm.xq.glide.GlideApp
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.ui.common.adapter.ChoosePhotoAdapter
import com.mhm.xq.ui.common.adapter.ChooseSinglePhotoAdapter
import com.mhm.xq.utils.MediaStoreHelper

class ChoosePhotoFromAlbumActivity : BaseActivity() {

    @BindView(R.id.rvChoosePhoto)
    @JvmField
    var mRvChoosePhoto: RecyclerView? = null

    var mExtra: Int? = null
    var mChoosePhotoAdapter: ChoosePhotoAdapter? = null
    var mChooseSinglePhotoAdapter: ChooseSinglePhotoAdapter? = null
    var mPhotoDirectories: ArrayList<PhotoDirectory>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_choose_photo)
        ButterKnife.bind(this)
        init()
    }

    private fun init() {
        initVariables()
        initData()
        initListener()
    }

    private fun initVariables() {
        setTitleBarTitleLeft(R.string.picture, true)
        mExtra = intent.getIntExtra(Extras.CHOOSE_PHOTO, -1)
        var manager = GridLayoutManager(this, 3)
        mRvChoosePhoto!!.layoutManager = manager
        if (mExtra == ExtraValues.CHOOSE_MORE_PHOTO) {
            mChoosePhotoAdapter = ChoosePhotoAdapter()
            mRvChoosePhoto!!.adapter = mChoosePhotoAdapter
        } else {
            mChooseSinglePhotoAdapter = ChooseSinglePhotoAdapter()
            mRvChoosePhoto!!.adapter = mChooseSinglePhotoAdapter
        }
    }

    private fun initData() {
        MediaStoreHelper.getPhotoDirs(this, object : MediaStoreHelper.PhotosResultCallback {
            override fun onResultCallback(directories: List<PhotoDirectory>) {
                mPhotoDirectories!!.clear()
                mPhotoDirectories!!.addAll(directories)
                if (mPhotoDirectories!!.isNotEmpty()) {
                    if (mChoosePhotoAdapter != null) {
                        mChoosePhotoAdapter!!.changeDataList(mPhotoDirectories!![0].getPhotos())
                    } else {
                        mChooseSinglePhotoAdapter!!.changeDataList(mPhotoDirectories!![0].getPhotos())
                    }
                }
            }
        })
    }

    private fun initListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mRvChoosePhoto!!.setOnScrollChangeListener(object : AbsListView.OnScrollListener, View.OnScrollChangeListener {
                override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {
                }

                override fun onScroll(p0: AbsListView?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {
                    when (p1) {
                        AbsListView.OnScrollListener.SCROLL_STATE_IDLE -> {
                            GlideApp.with(this@ChoosePhotoFromAlbumActivity).resumeRequests()
                        }
                        AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL -> {
                            GlideApp.with(this@ChoosePhotoFromAlbumActivity).pauseRequests()
                        }
                        AbsListView.OnScrollListener.SCROLL_STATE_FLING -> {
                            GlideApp.with(this@ChoosePhotoFromAlbumActivity).pauseRequests()
                        }
                    }
                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}