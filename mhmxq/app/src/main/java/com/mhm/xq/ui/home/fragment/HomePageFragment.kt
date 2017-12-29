package com.mhm.xq.ui.home.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.mhm.xq.R
import com.mhm.xq.dal.Db
import com.mhm.xq.entity.greendao.NewsColumn
import com.mhm.xq.net.http.rest.MyApi
import com.mhm.xq.ui.base.fragment.BaseRetryLoadNetDataFragment
import com.mhm.xq.ui.home.adapter.NewsAdapter
import com.mhm.xq.ui.home.view.NewsTabLayout
import com.mhm.xq.utils.LogUtil
import io.reactivex.Observable

class HomePageFragment : BaseRetryLoadNetDataFragment<ArrayList<NewsColumn>>() {

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
    }

    override fun onFragmentResume(type: Int) {
        super.onFragmentResume(type)
        getLoadingLayout().showContent()
    }

    override fun onLazyLoadingData() {
        super.onLazyLoadingData()
        initVariables()
    }

    private fun initVariables() {
        sColumns = ArrayList()
        mAdapter = NewsAdapter(this.childFragmentManager)
        mVpNews!!.adapter = mAdapter
        mTlNews!!.setupWithViewPager(mVpNews)
        mNewsTabLayout = NewsTabLayout(this.context!!, mTlNews!!)
    }

    override fun getLocalData(): ArrayList<NewsColumn>? =
            Db.getManagerSession().newsColumnManager!!.getDao().loadAll() as ArrayList<NewsColumn>

    override fun getNetDataObservable(): Observable<ArrayList<NewsColumn>> = MyApi.getNewsColumn()

    override fun refreshUI(data: ArrayList<NewsColumn>) {
        sColumns!!.clear()
        sColumns!!.addAll(data)
        var list = ArrayList<String>()
        data.mapTo(list) { it.name }
        mAdapter!!.count = list.size
        mNewsTabLayout!!.setTabData(list)
    }

    override fun handlerError(throwable: Throwable, isImportant: Boolean) {
        LogUtil.i(throwable.message)
    }

    override fun onDetach() {
        super.onDetach()
        mNewsTabLayout = null
    }
}