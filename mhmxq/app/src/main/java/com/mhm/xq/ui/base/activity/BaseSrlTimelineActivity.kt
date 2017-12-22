package com.mhm.xq.ui.base.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.mhm.xq.R
import com.mhm.xq.widget.loadmore.LoadMoreContainer
import com.mhm.xq.widget.loadmore.LoadMoreHandler
import com.mhm.xq.widget.loadmore.LoadMoreRecyclerViewContainer

abstract class BaseSrlTimelineActivity<T> : BaseTimelineActivity<T>() {

    private var mSrlView: SwipeRefreshLayout? = null
    private var mLoadMoreView: LoadMoreRecyclerViewContainer? = null
    private var mRvView: RecyclerView? = null


    fun getSrlView(): SwipeRefreshLayout {
        if (mSrlView == null) {
            mSrlView = findViewById<ViewGroup>(R.id.pullRefreshView) as SwipeRefreshLayout
        }
        return mSrlView!!
    }

    fun getLoadMoreView(): LoadMoreRecyclerViewContainer {
        if (mLoadMoreView == null) {
            mLoadMoreView = findViewById<ViewGroup>(R.id.loadMoreView) as LoadMoreRecyclerViewContainer
        }
        return mLoadMoreView!!
    }

    fun getRvView(): RecyclerView {
        if (mRvView == null) {
            mRvView = findViewById<ViewGroup>(R.id.rvView) as RecyclerView
        }
        return mRvView!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initTimeline()
    }

    protected fun getLayoutId(): Int {
        return R.layout.common_activity_base_srl
    }

    protected fun initTimeline() {
        initTimelineViews()
        initTimelineListeners()
    }

    protected fun initTimelineViews() {
        initSrlView()
        initLoadMoreView()
        initRcv()
    }

    protected fun initTimelineListeners() {
        getLoadingLayout().setOnRetryClickListener(View.OnClickListener {
            loadFirstPageData(false)
            getLoadingLayout().showLoading()
        })
    }

    protected fun initSrlView() {
        getSrlView().setColorSchemeResources(R.color.colorPrimary)
        getSrlView().setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                if (isLoadingData()) {
                    getSrlView().isRefreshing = false
                    return
                }
                loadFirstPageData(false)
            }
        })
    }

    protected fun initLoadMoreView() {
        getLoadMoreView().useDefaultFooter()
        getLoadMoreView().setAutoLoadMore(true)
        getLoadMoreView().setShowLoadingForFirstPage(true)
        getLoadMoreView().setCanLoadMoreNoScroll(true)
        getLoadMoreView().setLoadMoreHandler(object : LoadMoreHandler {
            override fun onReadyLoadMore(): Boolean {
                return !getSrlView().isRefreshing
            }

            override fun onLoadMore(loadMoreContainer: LoadMoreContainer) {
                if (isLoadingData()) {
                    getLoadMoreView().loadMoreFinish(false, true)
                    return
                }
                loadOlderPagerData()
            }

        })
    }

    abstract fun initRcv()
}