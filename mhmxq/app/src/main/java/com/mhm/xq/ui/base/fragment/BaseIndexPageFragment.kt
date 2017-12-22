package com.mhm.xq.ui.base.fragment

import com.mhm.xq.ui.base.activity.BaseLoadingLayoutActivity
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

abstract class BaseIndexPageFragment<T> : LazyFragment() {
    var mDisposableFirstPage: Disposable? = null
    var mDisposableOlderPage: Disposable? = null

    private fun getFirstPageObservable(isNeedLocal: Boolean): Observable<T> {
        return if (isNeedLocal && getLocalFirstPageDataListObservable() != null) {
            Observable.concat(getLocalFirstPageDataListObservable(),
                    getIndexPageObservable(BaseLoadingLayoutActivity.LoadType.firstPage))
        } else {
            getIndexPageObservable(BaseLoadingLayoutActivity.LoadType.firstPage)
        }
    }

    private fun getIndexPageObservable(loadType: BaseLoadingLayoutActivity.LoadType): Observable<T> {
        var pageIndex = 0
        var pageSize = getPageSize()
        if (loadType == BaseLoadingLayoutActivity.LoadType.olderPage) {
            pageIndex = getCurrentCount() / getPageSize()
            if (getCurrentCount() % getPageSize() != 0) {
                pageSize = pageSize * 2
            }
        }
        return getNetPageDataListObservable(pageIndex, pageSize)
    }

    fun loadFirstPageData(isNeedLocal: Boolean) {
        mDisposableFirstPage = getFirstPageObservable(isNeedLocal)
                .compose(bindUntilEvent(FragmentEvent.DETACH))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BaseConsumer(BaseLoadingLayoutActivity.LoadType.firstPage),
                        BaseThrowableConsumer(BaseLoadingLayoutActivity.LoadType.firstPage))
    }

    fun loadOlderPagerData() {
        mDisposableOlderPage = getIndexPageObservable(BaseLoadingLayoutActivity.LoadType.olderPage)
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

    abstract fun loadErrorCallback(loadType: BaseLoadingLayoutActivity.LoadType, t: Throwable?)

    abstract fun loadCompleteCallback(loadType: BaseLoadingLayoutActivity.LoadType, dataList: Any?)

    abstract fun loadFirstPageDataCompleteCallback(dataList: Any?)

    abstract fun loadOlderPageDataCompleteCallback(dataList: Any?)

    abstract fun getNetPageDataListObservable(pageIndex: Int, pageSize: Int): Observable<T>

    abstract fun getLocalFirstPageDataListObservable(): Observable<T>

    abstract fun getCurrentCount(): Int

    abstract fun getPageSize(): Int

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