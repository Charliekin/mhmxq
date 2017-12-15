package com.mhm.xq.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet

class SquareItemLayout : ConstraintLayout {

    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec))
        var widthMeasure = measuredWidth
        widthMeasure = MeasureSpec.makeMeasureSpec(widthMeasure, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasure, widthMeasure)
    }
}