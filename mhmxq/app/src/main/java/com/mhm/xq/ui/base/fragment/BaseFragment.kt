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
import com.mhm.xq.utils.LogUtil
import com.trello.rxlifecycle2.components.support.RxFragment
import java.lang.Exception

open class BaseFragment : RxFragment() {

    protected var inflater: LayoutInflater? = null
    protected var rootView: FrameLayout? = null
    protected var contentView: View? = null
    protected var container: ViewGroup? = null
    protected var isViewCreated: Boolean = false
    protected var isInitViewFromCache: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        abstractRegister()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

    open fun onCreateView(savedInstanceState: Bundle?) {

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isViewCreated = false
        if (rootView != null) {
            (rootView!!.parent as ViewGroup).removeView(rootView)
        }
    }

    override fun onDetach() {
        super.onDetach()
        try {
            activity.unregisterReceiver(mInternalReceiver)
        } catch (e: Exception) {
            LogUtil.e("receiver error")
        }
        try {
            val childFragmentManager = Fragment::class.java.getDeclaredField("mChildFragmentManager")
            childFragmentManager.isAccessible = true
            childFragmentManager.set(this, null)
        } catch (e: NoSuchFieldException) {
            Log.e("BaseFragment", e.message, e)
        } catch (e: IllegalAccessException) {
            Log.e("BaseFragment", e.message, e)
        }
    }

    fun setContentViews(view: View) {
        rootView!!.removeView(view)
        rootView!!.addView(view)
        contentView = rootView
    }

    fun setContentView(layoutResID: Int) {
        setContentViews(inflater!!.inflate(layoutResID, rootView, false))
    }

    fun getRootView(): View {
        return rootView!!
    }

    fun findViewById(id: Int): View? {
        return if (contentView != null) contentView!!.findViewById(id) else null
    }

    //<editor-fold desc="默认广播接收">

    private var mInternalReceiver: InternalReceiver? = null

    public fun abstractRegister() {
        registerReceiver(null)
    }

    public fun registerReceiver(array: Array<String>?) {
        val intentFilter: IntentFilter = IntentFilter()
        if (array != null) {
            for (str in array) {
                intentFilter.addAction(str)
            }
        }
        if (mInternalReceiver == null) {
            mInternalReceiver = InternalReceiver()
        }
        activity.registerReceiver(mInternalReceiver, intentFilter)
    }

    public fun handleReceiver(context: Context?, intent: Intent?) {

    }

    private inner class InternalReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent == null || intent.action == null) {
                return
            }
            handleReceiver(context, intent)
        }

    }

    //<editor-fold>
}