package com.mhm.xq.ui.base.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mhm.xq.R
import com.mhm.xq.widget.loadmore.LoadMoreContainer
import com.mhm.xq.widget.loadmore.LoadMoreHandler
import com.mhm.xq.widget.loadmore.LoadMoreRecyclerViewContainer

abstract class BaseSrlIndexPageFragment<T> : BaseIndexPageFragment<T>() {
    private var mSrlView: SwipeRefreshLayout? = null
    private var mLoadMoreView: LoadMoreRecyclerViewContainer? = null
    private var mRvView: RecyclerView? = null


    fun getSrlView(): SwipeRefreshLayout {
        if (mSrlView == null) {
            mSrlView = findViewById(R.id.pullRefreshView) as SwipeRefreshLayout
        }
        return mSrlView!!
    }

    fun getLoadMoreView(): LoadMoreRecyclerViewContainer {
        if (mLoadMoreView == null) {
            mLoadMoreView = findViewById(R.id.loadMoreView) as LoadMoreRecyclerViewContainer
        }
        return mLoadMoreView!!
    }

    fun getRvView(): RecyclerView {
        if (mRvView == null) {
            mRvView = findViewById(R.id.rvView) as RecyclerView
        }
        return mRvView!!
    }

    override fun onFragmentCreateView(savedInstanceState: Bundle?) {
        super.onFragmentCreateView(savedInstanceState)
        setContentView(R.layout.common_activity_base_srl)
        initIndexPage()
    }

    protected fun initIndexPage() {
        initIndexPageViews()
        initIndexPageListeners()
    }

    protected fun initIndexPageViews() {
        initSrlView()
        initLoadMoreView()
        initRcv()
    }

    protected fun initIndexPageListeners() {
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