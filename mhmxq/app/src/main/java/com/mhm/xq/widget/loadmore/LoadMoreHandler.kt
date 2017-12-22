package com.mhm.xq.widget.loadmore

interface LoadMoreHandler {
    abstract fun onReadyLoadMore(): Boolean
    abstract fun onLoadMore(loadMoreContainer: LoadMoreContainer)
}