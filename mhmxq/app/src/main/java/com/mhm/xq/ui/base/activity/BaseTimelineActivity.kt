package com.mhm.xq.ui.base.activity

import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseTimelineActivity<T> : BaseLoadingLayoutActivity() {
    var mDisposableFirstPageTimeline: Disposable? = null
    var mDisposableOlderPageTimeline: Disposable? = null

    private fun getFirstPageObservable(isNeedLocal: Boolean): Observable<T> {
        return if (isNeedLocal && getLocalFirstPageDataListObservable() != null) {
            Observable.concat(getLocalFirstPageDataListObservable(),
                    getTimelineObservable(LoadType.firstPage))
        } else {
            getTimelineObservable(LoadType.firstPage)
        }
    }

    private fun getTimelineObservable(loadType: LoadType): Observable<T> {
        var since: Long = 0
        var until: Long = 0
        if (loadType == LoadType.olderPage) {
            until = getOldestCt()
        }
        return getNetPageDataListObservable(since, until, getPageSize())
    }

    fun loadFirstPageData(isNeedLocal: Boolean) {
        getFirstPageObservable(isNeedLocal)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BaseObserver(LoadType.firstPage))
    }

    fun loadOlderPagerData() {
        getTimelineObservable(LoadType.olderPage)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BaseObserver(LoadType.olderPage))
    }

    protected fun isLoadingData(): Boolean {
        return isLoadingFirstPageData() || isLoadingOlderPageData()
    }

    protected fun isLoadingFirstPageData(): Boolean {
        if (mDisposableFirstPageTimeline != null && !mDisposableFirstPageTimeline!!.isDisposed) {
            return true
        }
        return false
    }

    protected fun isLoadingOlderPageData(): Boolean {
        if (mDisposableOlderPageTimeline != null && !mDisposableOlderPageTimeline!!.isDisposed) {
            return true
        }
        return false
    }

    abstract fun loadErrorCallback(loadType: LoadType, t: Throwable?)

    abstract fun loadCompleteCallback(loadType: LoadType, dataList: Any?)

    abstract fun loadFirstPageDataCompleteCallback(dataList: Any?)

    abstract fun loadOlderPageDataCompleteCallback(dataList: Any?)

    abstract fun getNetPageDataListObservable(since: Long, until: Long, pageSize: Int): Observable<T>

    abstract fun getLocalFirstPageDataListObservable(): Observable<T>

    abstract fun getPageSize(): Int

    abstract fun getOldestCt(): Long

    inner class BaseObserver : Observer<T> {

        var mLoadType: BaseLoadingLayoutActivity.LoadType? = null
        var mLastResult: T? = null

        constructor(loadType: BaseLoadingLayoutActivity.LoadType?) {
            mLoadType = loadType
        }

        override fun onSubscribe(d: Disposable) {
            if (mLoadType == BaseLoadingLayoutActivity.LoadType.firstPage) {
                mDisposableFirstPageTimeline = d
            } else {
                mDisposableOlderPageTimeline = d
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