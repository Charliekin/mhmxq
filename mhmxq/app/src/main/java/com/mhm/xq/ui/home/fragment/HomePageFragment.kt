package com.mhm.xq.ui.home.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.mhm.xq.R
import com.mhm.xq.entity.NewsColumn
import com.mhm.xq.net.http.rest.MyApi
import com.mhm.xq.ui.base.fragment.LazyFragment
import com.mhm.xq.ui.home.adapter.NewsAdapter
import com.mhm.xq.ui.home.view.NewsTabLayout
import com.mhm.xq.utils.ToastUtil
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePageFragment : LazyFragment() {

    @BindView(R.id.tlNews)
    @JvmField
    var mTlNews: TabLayout? = null
    @BindView(R.id.vpNews)
    @JvmField
    var mVpNews: ViewPager? = null

    private var mAdapter: NewsAdapter? = null
    private var mNewsTabLayout: NewsTabLayout? = null

    var sColumns: ArrayList<NewsColumn>? = null

    override fun onFragmentCreateView(savedInstanceState: Bundle?) {
        super.onFragmentCreateView(savedInstanceState)
        setFragmentContentView(R.layout.my_fragment_home)
        ButterKnife.bind(this, rootView!!)
        init()
    }

    override fun onFragmentResume(type: Int) {
        super.onFragmentResume(type)
        getLoadingLayout().showContent()
    }

    override fun onLazyLoadingData() {
        super.onLazyLoadingData()
        loadNetNewsColumn()
    }

    private fun init() {
        initVariables()
    }

    private fun initVariables() {
        sColumns = ArrayList()
        mAdapter = NewsAdapter(this.childFragmentManager)
        mVpNews!!.adapter = mAdapter
        mTlNews!!.setupWithViewPager(mVpNews)
        mNewsTabLayout = NewsTabLayout(this.context!!, mTlNews!!)
    }

    private fun loadNetNewsColumn() {
        MyApi.getNewsColumn().compose(bindUntilEvent(FragmentEvent.DETACH))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe({ startProgressBar() })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ base ->
                    closeProgressBar()
                    sColumns!!.addAll(base.getNewsColumns())
                    dataProcessing()
                    getLoadingLayout().showContent()
                }, {
                    closeProgressBar()
                    getLoadingLayout().showContent()
                    ToastUtil.show(this.context!!, R.string.common_error)
                })
    }

    private fun dataProcessing() {
        var list = ArrayList<String>()
        sColumns!!.mapTo(list) { it.getName() }
        mAdapter!!.count = list.size
        mNewsTabLayout!!.setTabData(list)
    }

    override fun onDetach() {
        super.onDetach()
        mNewsTabLayout = null
    }
}