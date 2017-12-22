package com.mhm.xq.widget.rcvheader

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerViewWithHeaderAndFooterDivider : RecyclerView.ItemDecoration {
    internal var mDrbItemDivider: Drawable
    internal var mColor: Int = 0
    internal var mHeight: Int = 0

    constructor(color: Int, height: Int) {
        mColor = color
        mHeight = height
        mDrbItemDivider = ColorDrawable(mColor)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val childCount = parent.childCount
        val totalCount = parent.adapter.itemCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(childView)
            if (position == RecyclerView.NO_POSITION) {
                continue
            }
            val params = childView
                    .layoutParams as RecyclerView.LayoutParams
            val left = childView.left - params.leftMargin
            val right = childView.right + params.rightMargin
            val top = childView.bottom + params.topMargin
            val bottom = top + mHeight
            if (position == totalCount - 1) {
                mDrbItemDivider.setBounds(left, top, right, top)
            } else {
                mDrbItemDivider.setBounds(left, top, right, bottom)
            }
            mDrbItemDivider.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val totalCount = parent.adapter.itemCount
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            return
        }
        val left = 0
        val top = 0
        val right = 0
        var bottom = 0
        if (position != totalCount - 1) {
            val typeCurrent = parent.adapter.getItemViewType(position)
            val typeNext = parent.adapter.getItemViewType(position + 1)
            if (typeCurrent >= 0 && typeNext >= 0) {
                bottom = mHeight
            }
        }
        outRect.set(left, top, right, bottom)
    }
}