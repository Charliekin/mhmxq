package com.mhm.xq.ui.base.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View
import com.mhm.xq.R
import com.mhm.xq.ui.base.widget.LoadingLayout
import com.mhm.xq.utils.LogUtil

open class LazyFragment : BaseFragment() {
    //一般的resume
    val RESUME_TYPE_COMMON = 0
    //已加载完成后，tab切换时触发
    val RESUME_TYPE_TAB_CHANGED = 1
    //已加载root view，第一次跳转到目标fragment时触发
    val RESUME_TYPE_FIRST = 2

    //一般的pause
    val PAUSE_TYPE_COMMON = 0
    //已加载完成后，tab切换时触发
    val PAUSE_TYPE_TAB_CHANGED = 1

    private var mSavedInstanceState: Bundle? = null
    protected var mIsLoadedData: Boolean = false
    protected var mFragmentLoadingLayout: LoadingLayout? = null

    fun isLazyLoadData(): Boolean {
        return mIsLoadedData
    }

    fun getSavedInstanceState(): Bundle? {
        return mSavedInstanceState
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        mSavedInstanceState = savedInstanceState
        LogUtil.v("onCreateView,,,,,,, " + getLayoutResId())
        setContentView(getLayoutResId())
        mFragmentLoadingLayout = rootView!!.findViewById<LoadingLayout>(R.id.loadingLayoutView)
        onFragmentCreateView(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.v("onViewCreated")
        mFragmentLoadingLayout!!.showLoading()
        if (userVisibleHint && !mIsLoadedData) {
            handleLazyLoadingData()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        LogUtil.v("setUserVisibleHint " + isVisibleToUser)
        var isOnFragmentResumeReally = false
        if (isVisibleToUser && !mIsLoadedData && contentView != null && isViewCreated) {
            handleLazyLoadingData()
            isOnFragmentResumeReally = true
        }
        if (mIsLoadedData) {
            if (isVisibleToUser) {
                onFragmentResume(if (isOnFragmentResumeReally) RESUME_TYPE_FIRST else RESUME_TYPE_TAB_CHANGED)
            } else {
                onFragmentPause(PAUSE_TYPE_TAB_CHANGED)
            }
        }
    }

    fun setFragmentContentView(layoutResID: Int) {
        val view = inflater!!.inflate(layoutResID, mFragmentLoadingLayout, false)
        setFragmentContentView(view)
    }

    fun setFragmentContentView(view: View) {
        getLoadingLayout().setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint && mIsLoadedData) {
            onFragmentResume(RESUME_TYPE_COMMON)
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint && mIsLoadedData) {
            onFragmentPause(PAUSE_TYPE_COMMON)
        }
    }

    private fun handleLazyLoadingData() {
        onBeforeLazyLoadingData()
        onLazyLoadingData()
        onAfterLazyLoadingData()
    }

    fun getLoadingLayout(): LoadingLayout {
        return mFragmentLoadingLayout!!
    }

    @LayoutRes
    open protected fun getLayoutResId(): Int {
        return R.layout.common_fragment_lazy_empty_root
    }

    /**
     * 设置布局
     *
     * @param savedInstanceState
     */
    open fun onFragmentCreateView(savedInstanceState: Bundle?) {
        LogUtil.v("onFragmentCreateView")
    }

    /**
     * onLazyLoadingData之前调用方法
     */
    open fun onBeforeLazyLoadingData() {
        LogUtil.v("onBeforeLazyLoadingData")
    }

    /**
     * 加载数据（懒加载）,只Fragment完全显示时, 才会执行， 避免了ViewPager中,Fragment不显示时,也进行加载数据的情况
     */
    open fun onLazyLoadingData() {
        mIsLoadedData = true
        LogUtil.v("onInitFragmentData")
    }

    /**
     * onLazyLoadingData之后调用的方法
     */
    open fun onAfterLazyLoadingData() {
        LogUtil.v("onAfterLazyLoadingData")
    }

    /**
     * 重新显示
     *
     * @param type
     */
    open fun onFragmentResume(type: Int) {
        LogUtil.v("onFragmentResume " + type)
    }

    /**
     * 暂停显示
     *
     * @param type
     */
    open fun onFragmentPause(type: Int) {
        LogUtil.v("onFragmentPause " + type)
    }


}