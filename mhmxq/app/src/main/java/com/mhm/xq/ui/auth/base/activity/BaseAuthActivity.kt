package com.mhm.xq.ui.auth.base.activity

import android.os.Bundle
import android.support.annotation.StringRes
import android.view.View
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mhm.xq.R
import com.mhm.xq.entity.greendao.User
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.utils.StringUtil
import com.mhm.xq.utils.ToastUtil
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

abstract class BaseAuthActivity : BaseActivity() {
    @BindView(R.id.etAuthUser)
    @JvmField
    var mEtAuthUser: EditText? = null
    @BindView(R.id.etAuthPwd)
    @JvmField
    var mEtAuthPwd: EditText? = null
    @BindView(R.id.btAuth)
    @JvmField
    var mBtAuth: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_base_auth)
        ButterKnife.bind(this)
        init()
    }

    open fun init() {}

    fun loadAuthUser() {
        if (getNetData() == null) {
            return
        }
        if (StringUtil.isBlank(getMobile())) {
            ToastUtil.show(this, R.string.mobile_hint)
            return
        }
        if (StringUtil.isBlank(getPassword())) {
            ToastUtil.show(this, R.string.password_hint)
            return
        }
        startProgressBar()
        getNetData()!!.compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterNext(Consumer {
                    closeProgressBar()
                })
                .subscribe({ user ->
                    loadComplete(user)
                },
                        { t ->
                            loadThrowable(t)
                        })
    }

    fun setButtonTxt(@StringRes resId: Int) {
        mBtAuth!!.setText(resId)
    }

    @OnClick(R.id.btAuth)
    fun OnBtLoginClick(view: View) {
        loadAuthUser()
    }

    abstract fun getNetData(): Observable<User>?

    fun getMobile(): String {
        return mEtAuthUser!!.text.toString().trim()
    }

    fun getPassword(): String {
        return mEtAuthPwd!!.text.toString().trim()
    }

    open fun loadComplete(user: User) {

    }

    open fun loadThrowable(throwable: Throwable) {
        ToastUtil.show(this, R.string.network_visit_error)
    }

}