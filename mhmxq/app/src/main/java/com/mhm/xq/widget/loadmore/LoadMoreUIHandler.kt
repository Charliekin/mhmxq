package com.mhm.xq.widget.loadmore

interface LoadMoreUIHandler {
    abstract fun onLoading(container: LoadMoreContainer)

    abstract fun onLoadFinish(container: LoadMoreContainer, empty: Boolean, hasMore: Boolean)

    abstract fun onWaitToLoadMore(container: LoadMoreContainer)

    abstract fun onLoadError(container: LoadMoreContainer, errorCode: Int, errorMessage: String?)
}