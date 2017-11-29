package com.mhm.xq.ui.base.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.mhm.xq.R

class CommonProgressDialog : Dialog {

    private var mTextView: TextView? = null
    private var mImageView: View? = null
    internal var mAsyncTask: AsyncTask<*, *, *>? = null

    private val mCancelListener = DialogInterface.OnCancelListener {
        if (mAsyncTask != null) {
            mAsyncTask!!.cancel(true)
        }
    }

    constructor(context: Context) : super(context) {
        mAsyncTask = null
        setCancelable(false)
        setContentView(R.layout.common_dialog_loading)
        mTextView = findViewById(R.id.tv_text)
        mTextView!!.setText(R.string.common_wait)
        mImageView = findViewById(R.id.iv_progress)
        setOnCancelListener(mCancelListener)
    }

    constructor(context: Context, resId: Int) : this(context) {
        mTextView!!.setText(resId)
    }

    constructor(context: Context, text: CharSequence) : this(context) {
        mTextView!!.setText(text)
    }

    constructor(context: Context, text: CharSequence, asyncTask: AsyncTask<*, *, *>) : this(context, text) {
        mAsyncTask = asyncTask
    }

    /**
     * 设置对话框显示文本
     *
     * @param text
     */
    fun setProgressText(text: CharSequence) {
        mTextView!!.setText(text)
    }

    override fun dismiss() {
        super.dismiss()
        mImageView!!.clearAnimation()
    }

    override fun show() {
        super.show()
        val loadAnimation = AnimationUtils.loadAnimation(context, R.anim.common_loading)
        mImageView!!.startAnimation(loadAnimation)
    }
}