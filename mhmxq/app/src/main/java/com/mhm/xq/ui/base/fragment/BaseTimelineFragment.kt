package com.mhm.xq.ui.base.fragment

import com.mhm.xq.ui.base.activity.BaseLoadingLayoutActivity
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

abstract class BaseTimelineFragment<T> : LazyFragment() {
    var mDisposableFirstPageTimeline: Disposable? = null
    var mDisposableOlderPageTimeline: Disposable? = null

    private fun getFirstPageObservable(isNeedLocal: Boolean): Observable<T> {
        return if (isNeedLocal && getLocalFirstPageDataListObservable() != null) {
            Observable.concat(getLocalFirstPageDataListObservable(),
                    getTimelineObservable(BaseLoadingLayoutActivity.LoadType.firstPage))
        } else {
            getTimelineObservable(BaseLoadingLayoutActivity.LoadType.firstPage)
        }
    }

    private fun getTimelineObservable(loadType: BaseLoadingLayoutActivity.LoadType): Observable<T> {
        var since: Long = 0
        var until: Long = 0
        if (loadType == BaseLoadingLayoutActivity.LoadType.olderPage) {
            until = getOldestCt()
        }
        return getNetPageDataListObservable(since, until, getPageSize())
    }

    fun loadFirstPageData(isNeedLocal: Boolean) {
        mDisposableFirstPageTimeline = getFirstPageObservable(isNeedLocal)
                .compose(bindUntilEvent(FragmentEvent.DETACH))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BaseConsumer(BaseLoadingLayoutActivity.LoadType.firstPage),
                        BaseThrowableConsumer(BaseLoadingLayoutActivity.LoadType.firstPage))
    }

    fun loadOlderPagerData() {
        mDisposableOlderPageTimeline = getTimelineObservable(BaseLoadingLayoutActivity.LoadType.olderPage)
                .compose(bindUntilEvent(FragmentEvent.DETACH))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BaseConsumer(BaseLoadingLayoutActivity.LoadType.olderPage),
                        BaseThrowableConsumer(BaseLoadingLayoutActivity.LoadType.olderPage))
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

    abstract fun loadErrorCallback(loadType: BaseLoadingLayoutActivity.LoadType, t: Throwable?)

    abstract fun loadCompleteCallback(loadType: BaseLoadingLayoutActivity.LoadType, dataList: Any?)

    abstract fun loadFirstPageDataCompleteCallback(dataList: Any?)

    abstract fun loadOlderPageDataCompleteCallback(dataList: Any?)

    abstract fun getNetPageDataListObservable(since: Long, until: Long, pageSize: Int): Observable<T>

    abstract fun getLocalFirstPageDataListObservable(): Observable<T>

    abstract fun getPageSize(): Int

    abstract fun getOldestCt(): Long

    inner class BaseConsumer<T> : Consumer<T> {
        var mLoadType: BaseLoadingLayoutActivity.LoadType? = null

        constructor(loadType: BaseLoadingLayoutActivity.LoadType) {
            mLoadType = loadType
        }

        override fun accept(t: T) {
            when (mLoadType) {
                BaseLoadingLayoutActivity.LoadType.firstPage -> {
                    loadFirstPageDataCompleteCallback(t)
                }
                BaseLoadingLayoutActivity.LoadType.olderPage -> {
                    loadOlderPageDataCompleteCallback(t)
                }
            }
            loadCompleteCallback(mLoadType!!, t)
        }

    }

    inner class BaseThrowableConsumer : Consumer<Throwable> {
        var mLoadType: BaseLoadingLayoutActivity.LoadType? = null

        constructor(loadType: BaseLoadingLayoutActivity.LoadType) {
            mLoadType = loadType
        }

        override fun accept(t: Throwable?) {
            loadErrorCallback(mLoadType!!, t)
        }
    }
}