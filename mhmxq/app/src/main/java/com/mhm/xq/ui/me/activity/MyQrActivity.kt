package com.mhm.xq.ui.me.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.utils.DensityUtil
import com.mhm.xq.utils.QRCodeUtil

class MyQrActivity : BaseActivity() {

    @BindView(R.id.ivQRUserIcon)
    @JvmField
    var mIvQRUserIcon: ImageView? = null
    @BindView(R.id.ivQRCode)
    @JvmField
    var mIvQRCode: ImageView? = null
    @BindView(R.id.tvQRUserName)
    @JvmField
    var mTvQRUserName: TextView? = null

    var qrBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_qr)
        ButterKnife.bind(this)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        qrBitmap!!.recycle()
        qrBitmap = null
    }

    private fun init() {
        setTitleBarTitleLeft(R.string.me_qr_text, true)
        initVariable()
    }

    private fun initVariable() {
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.user_icon_default)
        qrBitmap = QRCodeUtil.createImage("白天不懂夜的黑", DensityUtil.dip2px(this, 200f),
                DensityUtil.dip2px(this, 200f), bitmap)
        mIvQRCode!!.setImageBitmap(qrBitmap)
    }
}