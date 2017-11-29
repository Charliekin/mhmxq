package com.mhm.xq.view

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mhm.xq.R
import com.mhm.xq.widget.TitleBar

class MyTitleBar : TitleBar {
    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    fun setTitle(title: String, isShowUpButton: Boolean) {
        if (isShowUpButton) {
            setLeftIcon(R.mipmap.back)
        } else {
            setLeftIcon(0)
        }
        setTitle(title)
    }

    fun setTitle(@StringRes resId: Int, isShowUpButton: Boolean) {
        setTitle(context.getString(resId), isShowUpButton)
    }

    fun addImageMenu(@DrawableRes iconRes: Int, onClickListener: View.OnClickListener): ImageView {
        val imageViewBuilder = RightImageViewBuilder(context)
        imageViewBuilder.drawable(iconRes)
        imageViewBuilder.clickListener(onClickListener)
        return addRightItem(imageViewBuilder)
    }

    fun addTextMenu(text: String, onClickListener: View.OnClickListener): TextView {
        val textViewBuilder = RightTextViewBuilder(context)
        textViewBuilder.text(text)
        textViewBuilder.clickListener(onClickListener)
        return addRightItem(textViewBuilder)
    }

    fun addTextMenu(@StringRes textRes: Int, onClickListener: View.OnClickListener): TextView {
        val textViewBuilder = RightTextViewBuilder(context)
        textViewBuilder.text(textRes)
        textViewBuilder.clickListener(onClickListener)
        return addRightItem(textViewBuilder)
    }
}