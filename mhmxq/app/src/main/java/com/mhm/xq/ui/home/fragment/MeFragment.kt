package com.mhm.xq.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.zxing.client.android.CaptureActivity
import com.mhm.xq.R
import com.mhm.xq.net.http.rest.MyRetrofit
import com.mhm.xq.ui.base.fragment.BaseFragment
import com.mhm.xq.ui.me.activity.MyQrActivity
import com.mhm.xq.utils.PermissionUtil
import com.mhm.xq.utils.ToastUtil
import com.mhm.xq.widget.CategoryNavigationBar
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MeFragment : BaseFragment() {
    @BindView(R.id.llMe)
    @JvmField
    var mLlMe: LinearLayout? = null
    @BindView(R.id.ivUserIcon)
    @JvmField
    var mIvUserIcon: ImageView? = null
    @BindView(R.id.tvUserName)
    @JvmField
    var mTvUserName: TextView? = null
    @BindView(R.id.ivQrCode)
    @JvmField
    var mIvQrCode: ImageView? = null
    @BindView(R.id.cnbAttention)
    @JvmField
    var mCnbAttention: CategoryNavigationBar? = null
    @BindView(R.id.cnbCollect)
    @JvmField
    var mCnbCollect: CategoryNavigationBar? = null
    @BindView(R.id.cnbFeedback)
    @JvmField
    var mCnbFeedback: CategoryNavigationBar? = null
    @BindView(R.id.cnbSet)
    @JvmField
    var mCnbSet: CategoryNavigationBar? = null


    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.my_fragment_me)
        ButterKnife.bind(this, rootView!!)
        PermissionUtil.verifyStoragePermissions(activity)
    }

    @OnClick(R.id.ivUserIcon, R.id.llMe, R.id.ivQrCode, R.id.cnbAttention, R.id.cnbCollect, R.id.cnbFeedback, R.id.cnbSet)
    fun onViewClick(view: View) {
//        if (!ConfigManager.getInstance()!!.checkIsAuthLogin()) {
//            startActivity(Intent(context, SignActivity::class.java))
//            return
//        }
        when (view.id) {
            R.id.ivUserIcon -> {
                uploadUserIcon()
            }
            R.id.llMe -> {
                startActivity(Intent(context, CaptureActivity::class.java))
            }
            R.id.ivQrCode -> {
                startActivity(Intent(context, MyQrActivity::class.java))
            }
            R.id.cnbAttention -> {

            }
            R.id.cnbCollect -> {

            }
            R.id.cnbFeedback -> {

            }
            R.id.cnbSet -> {

            }
        }
    }

    private fun uploadUserIcon() {
        var file = File(Environment.getExternalStorageDirectory(), "icon.jpg")
        if (!file.exists()) {
            ToastUtil.show(this.context, "文件获取不到")
            return
        }
        var requestBody: RequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file)
        val part = MultipartBody.Part.create(requestBody)
        MyRetrofit.getInstance().api.uploadUserIcon(part)
                .compose(bindUntilEvent(FragmentEvent.DETACH))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ base ->
                    ToastUtil.show(this.context, base.getMessage())
                }, { t ->
                    ToastUtil.show(this.context, t.message.toString())
                })
    }
}