package com.mhm.xq.ui.base.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.utils.LogUtil
import com.trello.rxlifecycle2.components.support.RxFragment

open class BaseFragment : RxFragment() {

    protected var inflater: LayoutInflater? = null
    protected var rootView: FrameLayout? = null
    protected var contentView: View? = null
    protected var container: ViewGroup? = null
    protected var isViewCreated: Boolean = false
    protected var isInitViewFromCache: Boolean = false

    //</editor-fold>


    //<editor-fold desc="默认广播接收">

    private var mInternalReceiver: InternalReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        abstractRegister()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtil.v("onCreateView")
        this.inflater = inflater
        this.container = container
        if (rootView == null) {
            isInitViewFromCache = false
            rootView = this.inflater!!.inflate(R.layout.common_fragment_base_empty_root, container, false) as FrameLayout
            onCreateView(savedInstanceState)
        } else {
            isInitViewFromCache = true
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        this.isViewCreated = false
        if (rootView != null) {
            (rootView!!.parent as ViewGroup).removeView(rootView)
        }
    }

    //从网上拷贝的， 需要这个么？
    override fun onDetach() {
        LogUtil.v("onDetach")
        super.onDetach()
        try {
            activity!!.unregisterReceiver(mInternalReceiver)
        } catch (e: Exception) {
        }

        try {
            val childFragmentManager = Fragment::class.java.getDeclaredField("mChildFragmentManager")
            childFragmentManager.isAccessible = true
            childFragmentManager.set(this, null)
        } catch (e: NoSuchFieldException) {
            Log.e(TAG, e.message, e)
        } catch (e: IllegalAccessException) {
            Log.e(TAG, e.message, e)
        }

    }

    override fun onDestroy() {
        try {
            getActivity()!!.unregisterReceiver(mInternalReceiver)
        } catch (e: Exception) {
        }

        super.onDestroy()
    }

    open fun onCreateView(savedInstanceState: Bundle?) {
        LogUtil.v("onCreateView")
    }

    fun setContentView(layoutResID: Int) {
        setContentViews(inflater!!.inflate(layoutResID, rootView, false))
    }

    fun setContentViews(view: View) {
        rootView!!.removeAllViews()
        rootView!!.addView(view)
        contentView = view
    }

    fun getContentView(): FrameLayout? {
        return rootView!!
    }

    fun findViewById(id: Int): View? {
        return if (contentView != null) contentView!!.findViewById(id) else null
    }

    //<editor-fold desc="dialog">

    fun startProgressBar() {
        if (activity == null || activity !is BaseActivity) {
            return
        }
        (activity as BaseActivity).startProgressBar()
    }

    fun closeProgressBar() {
        if (activity == null || activity !is BaseActivity) {
            return
        }
        (activity as BaseActivity).closeProgressBar()

    }

    /**
     * 子类需要重写此方法，指定需要注册的Action
     */
    fun abstractRegister() {
        registerReceiver(null)
    }

    /**
     * 注册接收者
     *
     * @param actionArray action数组, 不注册默认的action
     */
    protected fun registerReceiver(actionArray: Array<String>?) {
        val intentfilter = IntentFilter()
        if (actionArray != null) {
            for (action in actionArray) {
                intentfilter.addAction(action)
            }
        }
        if (mInternalReceiver == null) {
            mInternalReceiver = InternalReceiver()
        }
        activity!!.registerReceiver(mInternalReceiver, intentfilter)
    }

    /**
     * 子类按需重写此方法, fragment不处理默认的广播
     *
     * @param context
     * @param intent
     */
    protected fun handleReceiver(context: Context, intent: Intent) {}

    private inner class InternalReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent?) {
            if (intent == null || intent.action == null) {
                return
            }
            handleReceiver(context, intent)
        }
    }

    companion object {

        private val TAG = BaseFragment::class.java.simpleName
    }
    //</editor-fold>

}
