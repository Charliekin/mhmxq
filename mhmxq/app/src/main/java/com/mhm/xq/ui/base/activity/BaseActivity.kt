package com.mhm.xq.ui.base.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.mhm.xq.R
import com.mhm.xq.bll.ConfigManager
import com.mhm.xq.constacts.Actions
import com.mhm.xq.ui.base.dialog.CommonProgressDialog
import com.mhm.xq.ui.base.view.NoNetworkTipView
import com.mhm.xq.utils.ActivityUtil
import com.mhm.xq.utils.KeyboardUtil
import com.mhm.xq.utils.NetUtil
import com.mhm.xq.utils.StatusBarUtil
import com.mhm.xq.view.MyTitleBar
import com.mhm.xq.widget.TitleBar
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import java.util.*

open class BaseActivity : RxAppCompatActivity(), KeyboardUtil.OnKeyboardStatusChangedListener {


    private var mInternalReceiver: InternalReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        abstractRegister()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initTitleBar()
    }

    override fun onDestroy() {
        unRegisterKeyboardListener()
        try {
            if (mInternalReceiver != null) {
                unregisterReceiver(mInternalReceiver)
            }
        } catch (e: Exception) {
        }

        super.onDestroy()
    }

    override fun onBackPressed() {
        finishActivity(false)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        registerKeyboardListener()
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        registerKeyboardListener()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        registerKeyboardListener()
    }

    //<editor-fold desc="titleBar">

    protected var mTitleBar: MyTitleBar? = null

    fun getTitleBar(): MyTitleBar? {
        if (mTitleBar == null) {
            mTitleBar = findViewById(R.id.titleBar)
        }
        return mTitleBar
    }

    fun initTitleBar() {
        if (getTitleBar() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getTitleBar()!!.setBackgroundColor(resources.getColor(R.color.colorPrimary, this.theme))
            } else {
                getTitleBar()!!.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            }
            getTitleBar()!!.setOnLeftClickListener(object : TitleBar.OnLeftClickListener {
                override fun leftClick(view: View) {
                    finishActivity(true)
                }
            })
        }
    }

    fun setTitleBarLeftText(strText: String) {
        getTitleBar()!!.setLeftText(strText)
    }

    fun setTitleBarLeftText(resId: Int) {
        setTitleBarLeftText(resources.getString(resId))
    }

    fun setTitleBarTitle(strText: String, isShowUpButton: Boolean) {
        getTitleBar()!!.setTitle(strText, isShowUpButton)
    }

    fun setTitleBarTitle(resId: Int, isShowUpButton: Boolean) {
        setTitleBarTitle(resources.getString(resId), isShowUpButton)
    }

    fun setTitleBarRightIcon(@DrawableRes iconRes: Int, onClickListener: View.OnClickListener) {
        getTitleBar()!!.getRightContainer().removeAllViews()
        getTitleBar()!!.addImageMenu(iconRes, onClickListener)
    }

    fun setTitleBarRightText(text: String, onClickListener: View.OnClickListener) {
        getTitleBar()!!.getRightContainer().removeAllViews()
        getTitleBar()!!.addTextMenu(text, onClickListener)
    }

    fun setTitleBarRightText(@StringRes textRes: Int, onClickListener: View.OnClickListener) {
        getTitleBar()!!.getRightContainer().removeAllViews()
        getTitleBar()!!.addTextMenu(textRes, onClickListener)
    }
    //</editor-fold>

    //<editor-fold desc="activity">

    fun finishActivity(autoHideSoftInput: Boolean) {
        if (isKeyboardVisible) {
            KeyboardUtil.hideKeyboard(currentFocus)
            if (!autoHideSoftInput) {
                return
            }
        }
        finish()
    }

    override fun finish() {
        if (beforeFinish()) {
            super.finish()
        }
    }

    /**
     * 在finish之前调用
     *
     * @return 如果是返回True, 表示正常关闭, 如果返回false表示不关闭
     */
    protected fun beforeFinish(): Boolean {
        return true
    }

    //</editor-fold>

    private inner class InternalReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent == null || intent.action == null) {
                return
            }
            handleReceiver(context, intent)
        }
    }

    fun abstractRegister() {
        registerReceiver(null)
    }

    protected fun registerReceiver(actionArray: Array<String>?) {
        val intentFiler: IntentFilter = IntentFilter()
        var ignoreReceiverList: ArrayList<String> = ArrayList<String>()
        if (getIgnoreDefaultReceiver() != null) {
            for (items in this.getIgnoreDefaultReceiver()!!) {
                ignoreReceiverList.add(items)
            }
        }
        if (!ignoreReceiverList.contains(Actions.LOGIN)) {
            intentFiler.addAction(Actions.LOGIN)
        }
        if (!ignoreReceiverList.contains(ConnectivityManager.CONNECTIVITY_ACTION)) {
            intentFiler.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        }
        if (actionArray != null) {
            for (items in actionArray) {
                intentFiler.addAction(items)
            }
        }
        if (mInternalReceiver == null) {
            mInternalReceiver = InternalReceiver()
        }
        registerReceiver(mInternalReceiver, intentFiler)
    }

    open protected fun getIgnoreDefaultReceiver(): Array<String>? {
        return null
    }

    open protected fun handleReceiver(context: Context?, intent: Intent?) {
        if (intent == null) {
            return
        }
        if (Actions.LOGIN.equals(intent.action)) {
            recreate()
        }
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.action)) {
            if (NetUtil.isNetEnabled()) {
                hideNoNetworkTip()
            } else {
                showNoNetworkTip()
            }
        }
    }


    //<editor-fold desc="no network tip">

    private var mNoNetworkTipView: NoNetworkTipView? = null

    /**
     * 显示没有网络的错误提示
     *
     * @param marginTop 上距离
     * @return
     */
    fun showNoNetworkTip(marginTop: Int): Boolean {
        val flRootView = ActivityUtil.findRootView(this) ?: return false
        if (mNoNetworkTipView != null) {
            return true
        }
        mNoNetworkTipView = NoNetworkTipView(this)
        flRootView.addView(mNoNetworkTipView)
        mNoNetworkTipView!!.bringToFront()
        val layoutParams = mNoNetworkTipView!!.findViewById<FrameLayout>(R.id.flTipContainer).getLayoutParams() as ViewGroup.MarginLayoutParams
        layoutParams.topMargin = marginTop
        mNoNetworkTipView!!.requestLayout()
        return true
    }

    /**
     * 显示没有网络的错误提示
     *
     * @return
     */
    fun showNoNetworkTip(): Boolean {
        return showNoNetworkTip(getNoNetworkTipMarginTop())
    }

    /**
     * 隐藏没有网络的错误提示
     */
    fun hideNoNetworkTip() {
        val flRootView = ActivityUtil.findRootView(this) ?: return
        if (mNoNetworkTipView == null) {
            return
        }
        flRootView.removeView(mNoNetworkTipView)
        mNoNetworkTipView = null
    }

    /**
     * 网络提示的MarginTop
     *
     * @return
     */
    fun getNoNetworkTipMarginTop(): Int {
        var margin = resources.getDimensionPixelOffset(R.dimen.title_bar_height)
        if (ActivityUtil.isFullScreen(this) || ActivityUtil.isTranslucentStatus(this) || ActivityUtil.isLayoutFullscreen(this)) {
            margin += StatusBarUtil.getStatusBarHeight(this)
        }
        return margin
    }

    //</editor-fold>
    //<editor-fold desc="dialog">

    protected var mProgressDialog: CommonProgressDialog? = null


    fun startProgressBar() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
            return
        }
        mProgressDialog = CommonProgressDialog(this)
        mProgressDialog!!.show()
        mProgressDialog!!.setCancelable(false)
    }

    fun closeProgressBar() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }

    }

    //</editor-fold>


    //<editor-fold desc="键盘监听">

    private var mOnGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null
    var isKeyboardVisible: Boolean = false
    protected var mKeyboardHeight: Int = 0

    /**
     * 注册键盘监听事件
     */
    private fun registerKeyboardListener() {
        mOnGlobalLayoutListener = KeyboardUtil.attach(this, this)
    }

    /**
     * 取消键盘监听事件
     */
    private fun unRegisterKeyboardListener() {
        if (mOnGlobalLayoutListener != null) {
            KeyboardUtil.detach(this, mOnGlobalLayoutListener!!)
        }
    }

    override fun onKeyboardVisibleChanged(isVisible: Boolean) {
        isKeyboardVisible = isVisible
    }

    override fun onKeyboardHeightChanged(height: Int) {
        mKeyboardHeight = height
    }

    //</editor-fold>

    //<editor-fold desc="判断用户是否登录">

    public fun checkIsAuthLogin(request: Int) {
        if (!ConfigManager.getInstance()!!.checkIsAuthLogin()) {
            handleAutoDonLogin(request)
        }
    }

    open public fun handleAutoDonLogin(request: Int) {
    }

    //</editor-fold>
}