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
import com.mhm.xq.R
import com.mhm.xq.bll.ConfigManager
import com.mhm.xq.net.http.rest.MyApi
import com.mhm.xq.net.http.rest.MyRetrofit
import com.mhm.xq.ui.auth.activity.SignActivity
import com.mhm.xq.ui.base.fragment.LazyFragment
import com.mhm.xq.ui.me.activity.*
import com.mhm.xq.utils.ToastUtil
import com.mhm.xq.widget.CategoryNavigationBar
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MeFragment : LazyFragment() {
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

    override fun onFragmentCreateView(savedInstanceState: Bundle?) {
        super.onFragmentCreateView(savedInstanceState)
        setFragmentContentView(R.layout.my_fragment_me)
        ButterKnife.bind(this, rootView!!)
//        GlideUtil.loadImage(this, BuildConfig.HTTP_HOST + "/files/1.png", R.mipmap.user_icon_default, mIvUserIcon)
    }

    override fun onFragmentResume(type: Int) {
        super.onFragmentResume(type)
        getLoadingLayout().showContent()
    }

    @OnClick(R.id.llMe, R.id.ivQrCode, R.id.cnbAttention, R.id.cnbCollect, R.id.cnbFeedback, R.id.cnbSet)
    fun onViewClick(view: View) {
        var intent = Intent()
        when (view.id) {
            R.id.llMe -> {
                intent.setClass(context, MyInfoActivity::class.java)
            }
            R.id.ivQrCode -> {
                intent.setClass(context, MyQrActivity::class.java)
            }
            R.id.cnbAttention -> {
                intent.setClass(context, MyAttention::class.java)
            }
            R.id.cnbCollect -> {
                intent.setClass(context, MyCollection::class.java)
            }
            R.id.cnbFeedback -> {
                intent.setClass(context, FeedbackActivity::class.java)
            }
            R.id.cnbSet -> {
                intent.setClass(context, MySetActivity::class.java)
            }
        }
        if (!ConfigManager.getInstance()!!.checkIsAuthLogin()) {
            var signIntent = Intent(context, SignActivity::class.java)
            startActivity(signIntent)
            return
        }
        startActivity(intent)
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

    private fun downloadUserIcon() {
        MyApi.downloadUserIcon().compose(bindUntilEvent(FragmentEvent.DETACH))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseBody ->
                    var ios: InputStream = responseBody.byteStream()
                    var file = File(Environment.getExternalStorageDirectory(), "2.jpg")
                    var fos = FileOutputStream(file)
                    var buf = ByteArray(1024)
                    while (len(ios, buf) != -1) {
                        fos.write(buf, 0, len(ios, buf))
                    }
                    fos.flush()
                    fos.close()
                    ios.close()
                    ToastUtil.show(this.context, "下载完了。。。")
                }, { t ->
                    ToastUtil.show(this.context, t.message.toString())
                })
    }

    private fun len(ios: InputStream, buf: ByteArray): Int {
        var len: Int = ios.read(buf)
        return len
    }
}