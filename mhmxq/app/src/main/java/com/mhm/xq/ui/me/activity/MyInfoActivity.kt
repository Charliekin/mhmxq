package com.mhm.xq.ui.me.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mhm.xq.R
import com.mhm.xq.constacts.ExtraValues
import com.mhm.xq.constacts.Extras
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.ui.common.activity.ChoosePhotoFromAlbumActivity
import com.mhm.xq.ui.common.activity.GeneralViewPictureActivity
import com.mhm.xq.widget.CategoryNavigationBar

class MyInfoActivity : BaseActivity() {

    @BindView(R.id.cnbInfoIcon)
    @JvmField
    var mCnbInfoIcon: CategoryNavigationBar? = null
    @BindView(R.id.cnbInfoName)
    @JvmField
    var mCnbInfoName: CategoryNavigationBar? = null
    @BindView(R.id.cnbInfoNick)
    @JvmField
    var mCnbInfoNick: CategoryNavigationBar? = null
    @BindView(R.id.cnbInfoQr)
    @JvmField
    var mCnbInfoQr: CategoryNavigationBar? = null
    @BindView(R.id.cnbInfoGender)
    @JvmField
    var mCnbInfoGender: CategoryNavigationBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_info)
        ButterKnife.bind(this)
        init()
    }

    private fun init() {
        setTitleBarTitleLeft(R.string.my_info_title, true)
        initListener()
    }

    private fun initListener() {
        mCnbInfoIcon!!.getRightIv().setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MyInfoActivity, GeneralViewPictureActivity::class.java))
            }

        })
    }

    @OnClick(R.id.cnbInfoIcon, R.id.cnbInfoName, R.id.cnbInfoNick, R.id.cnbInfoQr, R.id.cnbInfoGender)
    fun onClick(view: View) {
        var intent = Intent()
        when (view.id) {
            R.id.cnbInfoIcon -> {
                intent.setClass(this, ChoosePhotoFromAlbumActivity::class.java)
                intent.putExtra(Extras.CHOOSE_PHOTO, ExtraValues.CHOOSE_SINGLE_PHOTO)
            }
            R.id.cnbInfoName -> {

            }
            R.id.cnbInfoNick -> {

            }
            R.id.cnbInfoQr -> {
                intent.setClass(this, MyQrActivity::class.java)
            }
            R.id.cnbInfoGender -> {

            }
        }
        startActivity(intent)
    }
}