package com.mhm.xq.ui.base.activity

import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
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
        mDisposableFirstPageTimeline = getFirstPageObservable(isNeedLocal)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BaseConsumer(LoadType.firstPage),
                        BaseThrowableConsumer(LoadType.firstPage))
    }

    fun loadOlderPagerData() {
        mDisposableOlderPageTimeline = getTimelineObservable(LoadType.olderPage)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BaseConsumer(LoadType.olderPage),
                        BaseThrowableConsumer(LoadType.olderPage))
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

    inner class BaseConsumer<T> : Consumer<T> {
        var mLoadType: LoadType? = null

        constructor(loadType: LoadType) {
            mLoadType = loadType
        }

        override fun accept(t: T) {
            when (mLoadType) {
                LoadType.firstPage -> {
                    loadFirstPageDataCompleteCallback(t)
                }
                LoadType.olderPage -> {
                    loadOlderPageDataCompleteCallback(t)
                }
            }
            loadCompleteCallback(mLoadType!!, t)
        }

    }

    inner class BaseThrowableConsumer : Consumer<Throwable> {
        var mLoadType: LoadType? = null

        constructor(loadType: LoadType) {
            mLoadType = loadType
        }

        override fun accept(t: Throwable?) {
            loadErrorCallback(mLoadType!!, t)
        }
    }
}