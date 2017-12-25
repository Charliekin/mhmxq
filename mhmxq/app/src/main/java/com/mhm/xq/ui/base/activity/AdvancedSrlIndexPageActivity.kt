package com.mhm.xq.ui.base.activity

import android.os.Bundle
import com.mhm.xq.ui.base.adapter.BaseRcvAdapter
import com.mhm.xq.utils.ListUtils
import com.mhm.xq.widget.rcvheader.RecyclerViewWithHeaderAndFooter

abstract class AdvancedSrlIndexPageActivity<T, V> : BaseSrlIndexPageActivity<T>() {

    var adapter: BaseRcvAdapter<V>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = if (getRvView() is RecyclerViewWithHeaderAndFooter) {
            (getRvView() as RecyclerViewWithHeaderAndFooter).getInnerAdapter() as BaseRcvAdapter<V>
        } else {
            getRvView().adapter as BaseRcvAdapter<V>
        }
        loadFirstPageData(true)
    }

    override fun loadCompleteCallback(loadType: LoadType, dataList: Any?) {
        getLoadingLayout().showContent()
        when (loadType) {
            LoadType.olderPage -> {
                var list: ArrayList<V> = getDataList(dataList as T)
                if (list == null || list.size < getPageSize()) {
                    getLoadMoreView().loadMoreFinish(false, false)
                } else {
                    getLoadMoreView().loadMoreFinish(false, true)
                }
            }
            LoadType.firstPage -> {
                getSrlView().isRefreshing = false
                getLoadMoreView().loadMoreFinish(false, true)
            }
        }
    }

    override fun loadFirstPageDataCompleteCallback(dataList: Any?) {
        adapter!!.changeDataList(getDataList(dataList as T))
        if (getCurrentCount() == 0) {
            getLoadingLayout().showLoading()
        }
    }

    override fun loadOlderPageDataCompleteCallback(dataList: Any?) {
        adapter!!.dropLastWhile(object : ListUtils.OnPredicateCallback<V> {
            override fun predicate(data: V): Boolean {
                if (data != null) {
                    var list: ArrayList<V> = getDataList(dataList as T)
                    return list != null && list.contains(data)
                } else {
                    return false
                }
            }
        })
        adapter!!.addDataList(getDataList(dataList as T))
    }

    override fun loadErrorCallback(loadType: LoadType, t: Throwable?) {
        if (loadType == LoadType.firstPage) {
            getSrlView().isRefreshing = false
            if (enableRetryWhenLoadFirstPageFail() || getCurrentCount() == 0) {
                getLoadingLayout().showError()
            }
        } else {
            getLoadMoreView().loadMoreError(0, null)
        }
    }

    protected fun enableRetryWhenLoadFirstPageFail(): Boolean {
        return true
    }

    override fun getPageSize(): Int = 20

    override fun getCurrentCount(): Int = adapter!!.itemCount

    /**
     * model不是List<V>时,需要重写此方法
     *
     * @param t
     * @return
     */
    protected fun getDataList(t: T): ArrayList<V> {
        return (t as ArrayList<V>)
    }
}