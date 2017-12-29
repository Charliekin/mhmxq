package com.mhm.xq.ui.base.fragment

import android.view.View
import com.mhm.xq.ui.base.widget.LoadingLayout
import com.mhm.xq.utils.LogUtil
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseRetryLoadNetDataFragment<T> : LazyFragment() {
    private var mLoadNetDataCt: Long = 0
    private var mDisposable: Disposable? = null

    public fun getLoadNetDataCt(): Long = mLoadNetDataCt

    override fun onAfterLazyLoadingData() {
        super.onAfterLazyLoadingData()
        initBaseRetry()
    }

    private fun initBaseRetry() {
        getLoadingLayout().setOnRetryClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                getLoadingLayout().showLoading()
                loadDataFromNet()
            }
        })
        var success: Boolean = loadDataFromLocal()
        if (!success || !skipWhenLoadLocalDataSuccess()) {
            loadDataFromNet()
        }
    }

    public fun loadDataFromLocal(): Boolean {
        var data: T? = getLocalData()
        if (data != null && (data is ArrayList<*>)) {
            refreshUI(data)
            return true
        } else if (data != null && (data as ArrayList<*>).isNotEmpty()) {
            refreshUI(data)
            return true
        } else {
            getLoadingLayout().showLoading()
            return false
        }
    }

    public fun loadDataFromNet() {
        if (mDisposable != null && mDisposable!!.isDisposed) {
            mDisposable = null
        }
        mLoadNetDataCt = System.currentTimeMillis()
        getNetDataObservable().compose(bindUntilEvent(FragmentEvent.DETACH))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<T> {
                    override fun onNext(t: T) {
                        LogUtil.i("-------->    " + (t as ArrayList<*>).size)
                        refreshUI(t)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        if (showNetErrorView() && getLoadingLayout().getViewType() == LoadingLayout.LOADING_VIEW) {
                            getLoadingLayout().showError()
                            handlerError(e, true)
                        } else {
                            handlerError(e, false)
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        mDisposable = d
                    }

                })
    }

    private fun showNetErrorView(): Boolean = true

    public fun skipWhenLoadLocalDataSuccess(): Boolean = false

    abstract fun getLocalData(): T?

    abstract fun getNetDataObservable(): Observable<T>

    abstract fun refreshUI(data: T)

    abstract fun handlerError(throwable: Throwable, isImportant: Boolean)
}