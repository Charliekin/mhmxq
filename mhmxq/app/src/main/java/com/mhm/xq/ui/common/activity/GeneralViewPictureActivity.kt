package com.mhm.xq.ui.common.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.mhm.xq.R
import com.mhm.xq.bll.DownloadManager
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.ui.base.adapter.BaseRcvAdapter
import com.mhm.xq.utils.GlideUtil
import com.mhm.xq.utils.ListUtils

class GeneralViewPictureActivity : BaseActivity(), View.OnClickListener, BaseRcvAdapter.OnItemClickListener {

    @BindView(R.id.svViewPic)
    @JvmField
    var mSvViewPic: SubsamplingScaleImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_general_view_pic)
        ButterKnife.bind(this)
        init()
    }

    private fun init() {
        setTitleBarTitle("", true)
        setTitleBarRightIcon(R.drawable.menu_share_more, this)
        GlideUtil.loadImage(this, false,
                "http://172.16.20.118:8080/files/1.png", mSvViewPic as View, 0, 0)
    }

    override fun onClick(p0: View?) {
        doMore(this,
                ListUtils.convertToList(resources.getStringArray(R.array.BottomDialogAlbumPres)) as ArrayList<String>)
    }

    override fun onItemClick(v: View?, position: Int?) {
        when (position) {
            0 -> {
                startActivity(Intent(this, ChoosePhotoFromAlbumActivity::class.java))
            }
            1 -> {
                DownloadManager.saveImage(this, "http://172.16.20.118:8080/files/1.png", 0, 0)
            }
        }
    }
}