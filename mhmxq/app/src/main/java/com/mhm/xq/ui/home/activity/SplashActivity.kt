package com.mhm.xq.ui.home.activity

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import com.mhm.xq.R
import com.mhm.xq.bll.ConfigManager
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.utils.ActivityAnimationUtil
import com.mhm.xq.utils.DisposableUtil
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {
    private var mDisposable: Disposable? = null
    private val mInstance by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_splash)
        init()
    }

    private fun init() {
        ConfigManager.getInstance()!!.init()
        mDisposable = Observable.timer(2000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(
                        object : Consumer<Long> {
                            @Throws(Exception::class)
                            override fun accept(t: Long?) {
                                val intent = Intent()
                                if (ConfigManager.getInstance()!!.isFirstRun()) {
                                    intent.setClass(mInstance, GuideActivity::class.java)
                                } else {
                                    intent.setClass(mInstance, HomeActivity::class.java)
                                }
                                startActivity(intent)
                                finish()
                                ActivityAnimationUtil.fadeAnimation(mInstance)
                            }
                        }
                )
    }

    override fun getIgnoreDefaultReceiver(): Array<String>? {
        return Array<String>(1, { ConnectivityManager.CONNECTIVITY_ACTION })
    }

    override fun onBackPressed() {
    }

    override fun onDestroy() {
        super.onDestroy()
        DisposableUtil.remove(mDisposable!!)
    }
}