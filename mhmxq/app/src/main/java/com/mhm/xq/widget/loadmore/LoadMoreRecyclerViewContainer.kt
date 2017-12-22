package com.mhm.xq.widget.loadmore

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.mhm.xq.widget.rcvheader.RecyclerViewWithHeaderAndFooter

class LoadMoreRecyclerViewContainer : LinearLayout, LoadMoreContainer {
    private var mLoadMoreUIHandler: LoadMoreUIHandler? = null
    private var mLoadMoreHandler: LoadMoreHandler? = null

    private var mIsLoading: Boolean = false
    private var mHasMore = false
    private var mAutoLoadMore = true
    private var mLoadError = false

    private var mListEmpty = true
    private var mShowLoadingForFirstPage = false
    private var mFooterView: View? = null
    private var mIsInitFooterView: Boolean = false
    private var mCanLoadMoreNoScroll: Boolean = false
    private var mRecyclerView: RecyclerViewWithHeaderAndFooter? = null

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }

    fun isCanLoadMoreNoScroll(): Boolean {
        return mCanLoadMoreNoScroll
    }

    fun setCanLoadMoreNoScroll(canLoadMoreNoScroll: Boolean) {
        mCanLoadMoreNoScroll = canLoadMoreNoScroll
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mRecyclerView = retrieveRecyclerView()
        init()
    }


    @Deprecated("It's totally wrong. Use {@link #useDefaultFooter} instead.")
    fun useDefaultHeader() {
        useDefaultFooter()
    }

    fun useDefaultFooter() {
        val footerView = LoadMoreDefaultFooterView(context)
        footerView.visibility = View.GONE
        setLoadMoreView(footerView)
        setLoadMoreUIHandler(footerView)
    }

    private fun init() {
        mRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = mRecyclerView!!.getLayoutManager()
                    val lastVisibleItemPosition: Int
                    if (layoutManager is GridLayoutManager) {
                        lastVisibleItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                    } else if (layoutManager is StaggeredGridLayoutManager) {
                        val into = IntArray((layoutManager as StaggeredGridLayoutManager).spanCount)
                        (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(into)
                        lastVisibleItemPosition = findMax(into)
                    } else {
                        lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    }
                    if (layoutManager.getChildCount() > 0
                            && lastVisibleItemPosition >= layoutManager.getItemCount() - 1
                            && (mCanLoadMoreNoScroll || ViewCompat.canScrollVertically(mRecyclerView!!, 1) || ViewCompat.canScrollVertically(mRecyclerView!!, -1))) {

                        if (mLoadMoreHandler != null && mLoadMoreHandler!!.onReadyLoadMore()) {
                            onReachBottom()
                        }
                    }
                } else {
                    if (isCanAddFooterView()) {
                        addFooterView(mFooterView)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun tryToPerformLoadMore() {
        if (mIsLoading) {
            return
        }

        // no more content and also not load for first page
        if (!mHasMore && !(mListEmpty && mShowLoadingForFirstPage)) {
            return
        }

        mIsLoading = true

        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler!!.onLoading(this)
        }
        if (null != mLoadMoreHandler) {
            mLoadMoreHandler!!.onLoadMore(this)
        }
    }

    private fun onReachBottom() {
        // if has error, just leave what it should be
        if (mLoadError) {
            return
        }
        if (mAutoLoadMore) {
            tryToPerformLoadMore()
        } else {
            if (mHasMore) {
                mLoadMoreUIHandler!!.onWaitToLoadMore(this)
            }
        }
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    override fun setShowLoadingForFirstPage(showLoading: Boolean) {
        mShowLoadingForFirstPage = showLoading
    }

    override fun setAutoLoadMore(autoLoadMore: Boolean) {
        mAutoLoadMore = autoLoadMore
    }

    override fun getLoadMoreView(): View {
        return mFooterView!!
    }

    override fun setLoadMoreView(view: View) {
        // has not been initialized
        if (mRecyclerView == null) {
            mFooterView = view
            return
        }
        // remove previous
        if (mFooterView != null && mFooterView !== view) {
            removeFooterView(view)
        }

        // add current
        mFooterView = view
        mFooterView!!.setOnClickListener { tryToPerformLoadMore() }
        if (mIsInitFooterView) {
            addFooterView(view)
        }
    }

    override fun removeLoadMoreView() {
        if (mFooterView != null) {
            removeFooterView(mFooterView!!)
        }
    }

    override fun setLoadMoreUIHandler(handler: LoadMoreUIHandler) {
        mLoadMoreUIHandler = handler
    }

    override fun setLoadMoreHandler(handler: LoadMoreHandler) {
        mLoadMoreHandler = handler
    }

    /**
     * page has loaded
     *
     * @param emptyResult
     * @param hasMore
     */
    override fun loadMoreFinish(emptyResult: Boolean, hasMore: Boolean) {
        mLoadError = false
        mListEmpty = emptyResult
        mIsLoading = false
        mHasMore = hasMore

        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler!!.onLoadFinish(this, emptyResult, hasMore)
        }
    }

    override fun loadMoreError(errorCode: Int, errorMessage: String?) {
        mIsLoading = false
        mLoadError = true
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler!!.onLoadError(this, errorCode, errorMessage)
        }
    }

    private fun isCanAddFooterView(): Boolean {
        return if (!mIsInitFooterView && mFooterView != null) {
            if (mCanLoadMoreNoScroll) {
                true
            } else {
                if (ViewCompat.canScrollVertically(mRecyclerView!!, 1)) {
                    true
                } else {
                    false
                }
            }
        } else {
            false
        }
    }

    fun addFooterView(view: View?) {
        mIsInitFooterView = true
        if (mFooterView != null) {
            mRecyclerView!!.removeFooterView(mFooterView!!)
        }
        mFooterView = view
        mRecyclerView!!.addFooterView(view!!)
    }

    protected fun removeFooterView(view: View) {
        mRecyclerView!!.removeFooterView(view)
    }

    fun retrieveRecyclerView(): RecyclerViewWithHeaderAndFooter? {
        val view = getChildAt(0)
        mRecyclerView = view as RecyclerViewWithHeaderAndFooter
        return mRecyclerView
    }
}