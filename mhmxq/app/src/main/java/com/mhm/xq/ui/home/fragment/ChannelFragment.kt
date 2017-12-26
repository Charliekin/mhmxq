package com.mhm.xq.ui.home.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mhm.xq.R
import com.mhm.xq.constacts.Extras
import com.mhm.xq.entity.New
import com.mhm.xq.net.http.rest.MyApi
import com.mhm.xq.ui.base.fragment.AdvancedSrlIndexPageFragment
import com.mhm.xq.ui.home.adapter.NewsColumnAdapter
import io.reactivex.Observable

class ChannelFragment : AdvancedSrlIndexPageFragment<ArrayList<New>, New>() {

    override fun initRcv() {
        adapter = NewsColumnAdapter()
        findViewById(R.id.titleBar)!!.visibility = View.GONE
        getRvView().layoutManager = LinearLayoutManager(this.context)
        getRvView().adapter = adapter
    }

    override fun getNetPageDataListObservable(pageIndex: Int, pageSize: Int): Observable<ArrayList<New>> {
        return MyApi.getNews(getNewsColumnId(), pageIndex, pageSize)
    }

    override fun getLocalFirstPageDataListObservable(): Observable<ArrayList<New>> {
        return Observable.just(ArrayList())
    }

    private fun getNewsColumnId(): String {
        var fragment = parentFragment as HomePageFragment
        if (fragment != null) {
            var columns = fragment.sColumns
            if (columns!!.isNotEmpty()) {
                return columns[arguments!!.getInt(Extras.TAB_INDEX)].getId()
            }
        }
        return ""
    }
}