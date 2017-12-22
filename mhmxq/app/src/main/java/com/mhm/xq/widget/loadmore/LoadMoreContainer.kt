package com.mhm.xq.widget.loadmore

import android.view.View

interface LoadMoreContainer {
    abstract fun setShowLoadingForFirstPage(showLoading: Boolean)

    abstract fun setAutoLoadMore(autoLoadMore: Boolean)

    abstract fun setLoadMoreView(view: View)

    abstract fun getLoadMoreView(): View

    abstract fun removeLoadMoreView()

    abstract fun setLoadMoreUIHandler(handler: LoadMoreUIHandler)

    abstract fun setLoadMoreHandler(handler: LoadMoreHandler)

    /**
     * When data has loaded
     *
     * @param emptyResult
     * @param hasMore
     */
    abstract fun loadMoreFinish(emptyResult: Boolean, hasMore: Boolean)

    /**
     * When something unexpected happened while loading the data
     *
     * @param errorCode
     * @param errorMessage
     */
    abstract fun loadMoreError(errorCode: Int, errorMessage: String?)
}