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
import com.mhm.xq.ui.base.adapter.BaseRcvAdapter
import com.mhm.xq.ui.common.adapter.BaseChoosePhotoAdapter
import com.mhm.xq.ui.common.adapter.ChoosePhotoAdapter
import com.mhm.xq.ui.common.adapter.ChooseSinglePhotoAdapter
import com.mhm.xq.utils.ListUtils
import com.mhm.xq.utils.MediaStoreHelper

class ChoosePhotoFromAlbumActivity : BaseActivity(), View.OnClickListener, BaseRcvAdapter.OnItemClickListener {

    @BindView(R.id.rvChoosePhoto)
    @JvmField
    var mRvChoosePhoto: RecyclerView? = null

    private var mExtra: Int? = null
    var mAdapter: BaseChoosePhotoAdapter? = null
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
        setTitleBarRightIcon(R.drawable.menu_share_more, this)
        mExtra = intent.getIntExtra(Extras.CHOOSE_PHOTO, -1)
        val manager = GridLayoutManager(this, 3)
        mRvChoosePhoto!!.layoutManager = manager
        mAdapter = if (mExtra == ExtraValues.CHOOSE_MORE_PHOTO) {
            ChoosePhotoAdapter()
        } else {
            ChooseSinglePhotoAdapter()
        }
        mRvChoosePhoto!!.adapter = mAdapter
    }

    private fun initData() {
        MediaStoreHelper.getPhotoDirs(this, object : MediaStoreHelper.PhotosResultCallback {
            override fun onResultCallback(directories: List<PhotoDirectory>) {
                mPhotoDirectories!!.clear()
                mPhotoDirectories!!.addAll(directories)
                if (mPhotoDirectories!!.isNotEmpty()) {
                    mAdapter!!.changeDataList(mPhotoDirectories!![0].getPhotos())
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

    override fun onClick(p0: View?) {
        doMore(this,
                ListUtils.convertToList(resources.getStringArray(R.array.BottomDialogPic))
                        as ArrayList<String>)
    }

    override fun onItemClick(v: View?, position: Int?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPhotoDirectories!!.clear()
        mPhotoDirectories = null
    }
}