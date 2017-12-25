package com.mhm.xq.ui.base.activity

import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

open abstract class BaseIndexPageActivity<T> : BaseLoadingLayoutActivity() {

    var mDisposableFirstPage: Disposable? = null
    var mDisposableOlderPage: Disposable? = null

    private fun getFirstPageObservable(isNeedLocal: Boolean): Observable<T> {
        return if (isNeedLocal && getLocalFirstPageDataListObservable() != null) {
            Observable.concat(getLocalFirstPageDataListObservable(),
                    getIndexPageObservable(LoadType.firstPage))
        } else {
            getIndexPageObservable(LoadType.firstPage)
        }
    }

    private fun getIndexPageObservable(loadType: LoadType): Observable<T> {
        var pageIndex = 0
        var pageSize = getPageSize()
        if (loadType == LoadType.olderPage) {
            pageIndex = getCurrentCount() / getPageSize()
            if (getCurrentCount() % getPageSize() != 0) {
                pageSize = pageSize * 2
            }
        }
        return getNetPageDataListObservable(pageIndex, pageSize)
    }

    fun loadFirstPageData(isNeedLocal: Boolean) {
        getFirstPageObservable(isNeedLocal)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BaseObserver(LoadType.firstPage))
    }

    fun loadOlderPagerData() {
        getIndexPageObservable(LoadType.olderPage)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BaseObserver(LoadType.olderPage))
    }

    protected fun isLoadingData(): Boolean {
        return isLoadingFirstPageData() || isLoadingOlderPageData()
    }

    protected fun isLoadingFirstPageData(): Boolean {
        if (mDisposableFirstPage != null && !mDisposableFirstPage!!.isDisposed) {
            return true
        }
        return false
    }

    protected fun isLoadingOlderPageData(): Boolean {
        if (mDisposableOlderPage != null && !mDisposableOlderPage!!.isDisposed) {
            return true
        }
        return false
    }

    abstract fun loadErrorCallback(loadType: LoadType, t: Throwable?)

    abstract fun loadCompleteCallback(loadType: LoadType, dataList: Any?)

    abstract fun loadFirstPageDataCompleteCallback(dataList: Any?)

    abstract fun loadOlderPageDataCompleteCallback(dataList: Any?)

    abstract fun getNetPageDataListObservable(pageIndex: Int, pageSize: Int): Observable<T>

    abstract fun getLocalFirstPageDataListObservable(): Observable<T>

    abstract fun getCurrentCount(): Int

    abstract fun getPageSize(): Int

    inner class BaseObserver : Observer<T> {

        var mLoadType: BaseLoadingLayoutActivity.LoadType? = null
        var mLastResult: T? = null

        constructor(loadType: BaseLoadingLayoutActivity.LoadType?) {
            mLoadType = loadType
        }

        override fun onSubscribe(d: Disposable) {
            if (mLoadType == BaseLoadingLayoutActivity.LoadType.firstPage) {
                mDisposableFirstPage = d
            } else {
                mDisposableOlderPage = d
            }
        }

        override fun onComplete() {
            loadCompleteCallback(mLoadType!!, mLastResult)
        }

        override fun onNext(t: T) {
            mLastResult = t
            when (mLoadType) {
                BaseLoadingLayoutActivity.LoadType.firstPage -> {
                    loadFirstPageDataCompleteCallback(t)
                }
                BaseLoadingLayoutActivity.LoadType.olderPage -> {
                    loadOlderPageDataCompleteCallback(t)
                }
            }
        }

        override fun onError(e: Throwable) {
            loadErrorCallback(mLoadType!!, e)
        }

    }

}