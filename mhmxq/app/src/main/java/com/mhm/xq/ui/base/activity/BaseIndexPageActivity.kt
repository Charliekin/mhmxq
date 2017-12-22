package com.mhm.xq.ui.base.activity

import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
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
        mDisposableFirstPage = getFirstPageObservable(isNeedLocal)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BaseConsumer(LoadType.firstPage),
                        BaseThrowableConsumer(LoadType.firstPage))
    }

    fun loadOlderPagerData() {
        mDisposableOlderPage = getIndexPageObservable(LoadType.olderPage)
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