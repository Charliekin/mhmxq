package com.mhm.xq.ui.home.fragment

import android.os.Bundle
import com.mhm.xq.R
import com.mhm.xq.net.http.rest.MyApi
import com.mhm.xq.ui.base.fragment.LazyFragment
import com.mhm.xq.utils.ToastUtil
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePageFragment : LazyFragment() {

    override fun onFragmentCreateView(savedInstanceState: Bundle?) {
        super.onFragmentCreateView(savedInstanceState)
        setFragmentContentView(R.layout.my_fragment_home)
    }

    override fun onFragmentResume(type: Int) {
        super.onFragmentResume(type)
        getLoadingLayout().showContent()
    }

    override fun onLazyLoadingData() {
        super.onLazyLoadingData()
        loadNetChannel()
    }

    private fun loadNetChannel() {
        MyApi.getChannel().compose(bindUntilEvent(FragmentEvent.DETACH))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe({ startProgressBar() })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ base ->
                    closeProgressBar()
                    getLoadingLayout().showContent()
                    ToastUtil.show(this.context, base.getMessage())
                }, {
                    closeProgressBar()
                    getLoadingLayout().showContent()
                    ToastUtil.show(this.context!!, R.string.common_error)
                })
    }
}