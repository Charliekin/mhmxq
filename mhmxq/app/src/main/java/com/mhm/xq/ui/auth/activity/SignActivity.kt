package com.mhm.xq.ui.auth.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mhm.xq.MyApp
import com.mhm.xq.R
import com.mhm.xq.entity.User
import com.mhm.xq.net.http.rest.MyApi
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.utils.StringUtil
import com.mhm.xq.utils.ToastUtil
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignActivity : BaseActivity() {

    @BindView(R.id.etLoginUser)
    @JvmField
    var mEtLoginUser: EditText? = null
    @BindView(R.id.etLoginPwd)
    @JvmField
    var mEtLoginPwd: EditText? = null
    @BindView(R.id.btLogin)
    @JvmField
    var mBtLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_sign)
        ButterKnife.bind(this)
        init()
    }

    private fun init() {
        setTitleBarTitle(R.string.login, true)
        initVariable()
    }

    private fun initVariable() {
        setTitleBarRightIcon(R.drawable.menu_share_more, View.OnClickListener {
            startActivity(Intent(MyApp.getContext(), RegisteredActivity::class.java))
        })
    }

    @OnClick(R.id.btLogin)
    fun OnBtLoginClick(view: View) {
        if (StringUtil.isBlank(getMobile())) {
            ToastUtil.show(this, R.string.mobile_hint)
            return
        }
        if (StringUtil.isBlank(getPassword())) {
            ToastUtil.show(this, R.string.password_hint)
            return
        }
        MyApi.login("", "")
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user: User? ->
                    if (user != null) {
                        ToastUtil.show(MyApp.getContext(), user.getId()!!)
                    }
                }, { t: Throwable? ->
                    ToastUtil.show(MyApp.getContext(), "网络访问错误")
                })
    }

    fun getMobile(): String {
        return mEtLoginUser!!.text.toString().trim()
    }

    fun getPassword(): String {
        return mEtLoginPwd!!.text.toString().trim()
    }
}