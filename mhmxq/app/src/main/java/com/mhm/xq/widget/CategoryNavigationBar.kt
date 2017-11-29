package com.mhm.xq.widget

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mhm.xq.R
import com.mhm.xq.utils.ColorUtil
import com.mhm.xq.utils.DensityUtil
import com.mhm.xq.utils.StringUtil

class CategoryNavigationBar : ViewGroup {

    //左侧控件
    private var mLeftTV: TextView? = null
    private var mLeftIV: ImageView? = null
    private var mLlLeft: LinearLayout? = null

    //中间控件
    private var mCenterTV: TextView? = null
    private var mCenterIV: ImageView? = null
    private var mLlCenter: LinearLayout? = null

    //右侧控件
    private var mRightTV: TextView? = null
    private var mRightIV: ImageView? = null
    private var mLlRight: LinearLayout? = null

    //左侧文本
    private var mLeftText: String? = null
    private var mLeftTextSize: Int = DensityUtil.dip2px(context, 14F)
    private var mLeftTextColor: ColorStateList = ColorUtil.createColorStateList(Color.BLACK)

    //左侧icon
    private var mLeftIcon: Drawable? = null
    private var mLeftIconWidth: Int = DensityUtil.dip2px(context, 40F)
    private var mLeftIconHeight: Int = DensityUtil.dip2px(context, 40F)

    //中间文本
    private var mCenterText: String? = null
    private var mCenterTextSize: Int = DensityUtil.dip2px(context, 12F)
    private var mCenterTextColor: ColorStateList = ColorUtil.createColorStateList(Color.BLACK)

    //中间padding
    private var mCenterPaddingLeft: Int = DensityUtil.dip2px(context, 10F)
    private var mCenterPaddingRight: Int = DensityUtil.dip2px(context, 10F)

    //中间icon
    private var mCenterIcon: Drawable? = null
    private var mCenterIconWidth: Int = DensityUtil.dip2px(context, 8F)
    private var mCenterIconHeight: Int = DensityUtil.dip2px(context, 8F)

    //右侧文本
    private var mRightText: String? = null
    private var mRightTextSize: Int = DensityUtil.dip2px(context, 14F)
    private var mRightTextColor: ColorStateList = ColorUtil.createColorStateList(Color.BLACK)

    //右侧icon
    private var mRightIcon: Drawable? = null
    private var mRightIconWidth: Int = DensityUtil.dip2px(context, 40F)
    private var mRightIconHeight: Int = DensityUtil.dip2px(context, 40F)

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defId: Int) : super(context, attrs, defId) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        setBackgroundColor(Color.WHITE)
        setPadding(DensityUtil.dip2px(context, 13F), 0, DensityUtil.dip2px(context, 13F), 0)
        initView()
        val typeArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CategoryNavigationBar)
        initAttrs(typeArray)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mLlLeft!!.layout(paddingLeft, 0, mLlLeft!!.measuredWidth + paddingLeft, measuredHeight)
        mLlRight!!.layout(measuredWidth - mLlRight!!.measuredWidth - paddingRight, 0,
                measuredWidth - paddingRight, measuredHeight)
        if (mLlLeft!!.measuredWidth > mLlRight!!.measuredWidth) {
            mLlCenter!!.layout(mLlLeft!!.measuredWidth + paddingLeft, 0,
                    mLlLeft!!.measuredWidth + mLlCenter!!.measuredWidth + paddingLeft, measuredHeight)
        } else {
            mLlCenter!!.layout(mLlLeft!!.measuredWidth + paddingLeft, 0,
                    measuredWidth - mLlRight!!.measuredWidth - paddingRight, measuredHeight)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val centerWidth: Int
        if (mLlLeft!!.measuredWidth > mLlRight!!.measuredWidth) {
            centerWidth = measuredWidth - mLlLeft!!.measuredWidth * 2
        } else {
            centerWidth = measuredWidth - mLlRight!!.measuredWidth * 2
        }
        mLlCenter!!.measure(View.MeasureSpec.makeMeasureSpec(centerWidth, View.MeasureSpec.EXACTLY),
                heightMeasureSpec)
    }

    private fun initView() {
        initLeftView()
        initCenterView()
        initRightView()
        addView(mLlLeft, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT))
        addView(mLlCenter, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT))
        addView(mLlRight, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    private fun initLeftView() {
        mLeftTV = TextView(context)
        mLeftTV!!.maxLines = 1
        mLeftTV!!.ellipsize = TextUtils.TruncateAt.END
        mLeftIV = ImageView(context)
        mLeftIV!!.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        mLlLeft = LinearLayout(context)
        mLlLeft!!.gravity = Gravity.CENTER
        mLlLeft!!.orientation = LinearLayout.HORIZONTAL
        mLlLeft!!.addView(mLeftTV)
        mLlLeft!!.addView(mLeftIV)
    }

    private fun initRightView() {
        mRightTV = TextView(context)
        mRightTV!!.ellipsize = TextUtils.TruncateAt.END
        mRightTV!!.maxLines = 1
        mRightIV = ImageView(context)
        mRightIV!!.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        mLlRight = LinearLayout(context)
        mLlRight!!.orientation = LinearLayout.HORIZONTAL
        mLlRight!!.gravity = Gravity.CENTER
        mLlRight!!.addView(mRightTV)
        mLlRight!!.addView(mRightIV)
    }

    private fun initCenterView() {
        mCenterTV = TextView(context)
        mCenterTV!!.ellipsize = TextUtils.TruncateAt.END
        mCenterTV!!.maxLines = 1
        mCenterIV = ImageView(context)
        mCenterIV!!.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        mLlCenter = LinearLayout(context)
        mLlCenter!!.gravity = Gravity.CENTER
        mLlCenter!!.orientation = LinearLayout.HORIZONTAL
        mLlCenter!!.addView(mCenterTV)
        mLlCenter!!.addView(mCenterIV)
    }

    private fun initAttrs(typeArray: TypedArray) {
        if (typeArray == null) {
            return
        }
        if (typeArray.hasValue(R.styleable.CategoryNavigationBar_cate_left_text)) {
            mLeftText = typeArray.getString(R.styleable.CategoryNavigationBar_cate_left_text)
        }
        mLeftTextSize = typeArray.getDimensionPixelSize(R.styleable.CategoryNavigationBar_cate_left_text_size, mLeftTextSize)
        if (typeArray.hasValue(R.styleable.CategoryNavigationBar_cate_left_text_color)) {
            mLeftTextColor = typeArray.getColorStateList(R.styleable.CategoryNavigationBar_cate_left_text_color)
        }
        if (typeArray.hasValue(R.styleable.CategoryNavigationBar_cate_left_icon)) {
            mLeftIcon = typeArray.getDrawable(R.styleable.CategoryNavigationBar_cate_left_icon)
        }
        mLeftIconWidth = typeArray.getDimensionPixelSize(R.styleable.CategoryNavigationBar_cate_left_icon_width, mLeftIconWidth)
        mLeftIconHeight = typeArray.getDimensionPixelSize(R.styleable.CategoryNavigationBar_cate_left_icon_height, mLeftIconHeight)
        if (typeArray.hasValue(R.styleable.CategoryNavigationBar_cate_center_text)) {
            mCenterText = typeArray.getString(R.styleable.CategoryNavigationBar_cate_center_text)
        }
        mCenterTextSize = typeArray.getDimensionPixelSize(R.styleable.CategoryNavigationBar_cate_center_text_size, mCenterTextSize)
        if (typeArray.hasValue(R.styleable.CategoryNavigationBar_cate_center_text_color)) {
            mCenterTextColor = typeArray.getColorStateList(R.styleable.CategoryNavigationBar_cate_center_text_color)
        }
        mCenterPaddingLeft = typeArray.getDimensionPixelSize(R.styleable.CategoryNavigationBar_cate_center_padding_left, mCenterPaddingLeft)
        mCenterPaddingRight = typeArray.getDimensionPixelSize(R.styleable.CategoryNavigationBar_cate_center_padding_right, mCenterPaddingRight)
        if (typeArray.hasValue(R.styleable.CategoryNavigationBar_cate_center_icon)) {
            mCenterIcon = typeArray.getDrawable(R.styleable.CategoryNavigationBar_cate_center_icon)
        }
        mCenterIconWidth = typeArray.getDimensionPixelSize(R.styleable.CategoryNavigationBar_cate_center_icon_width, mCenterIconWidth)
        mCenterIconHeight = typeArray.getDimensionPixelSize(R.styleable.CategoryNavigationBar_cate_center_icon_height, mCenterIconHeight)
        if (typeArray.hasValue(R.styleable.CategoryNavigationBar_cate_right_text)) {
            mRightText = typeArray.getString(R.styleable.CategoryNavigationBar_cate_right_text)
        }
        mRightTextSize = typeArray.getDimensionPixelSize(R.styleable.CategoryNavigationBar_cate_right_text_size, mRightTextSize)
        if (typeArray.hasValue(R.styleable.CategoryNavigationBar_cate_right_text_color)) {
            mRightTextColor = typeArray.getColorStateList(R.styleable.CategoryNavigationBar_cate_right_text_color)
        }
        if (typeArray.hasValue(R.styleable.CategoryNavigationBar_cate_right_icon)) {
            mRightIcon = typeArray.getDrawable(R.styleable.CategoryNavigationBar_cate_right_icon)
        }
        mRightIconWidth = typeArray.getDimensionPixelSize(R.styleable.CategoryNavigationBar_cate_right_icon_width, mRightIconWidth)
        mRightIconHeight = typeArray.getDimensionPixelSize(R.styleable.CategoryNavigationBar_cate_right_icon_height, mRightIconHeight)
        typeArray.recycle()
        initViewValue()
    }

    private fun initViewValue() {
        initLeftViewValue()
        initCenterViewValue()
        initRightViewValue()
    }

    private fun initLeftViewValue() {
        if (!StringUtil.isBlank(mLeftText)) {
            mLeftTV!!.text = mLeftText
            mLeftTV!!.visibility = View.VISIBLE
        } else {
            mLeftTV!!.visibility = View.GONE
        }
        if (mLeftTextColor != null) {
            mLeftTV!!.setTextColor(mLeftTextColor)
        }
        mLeftTV!!.setTextSize(0, mLeftTextSize.toFloat())
        if (mLeftIcon != null) {
            mLeftIV!!.visibility = View.VISIBLE
            mLeftIV!!.setImageDrawable(mLeftIcon)
        } else {
            mLeftIV!!.visibility = View.GONE
        }
        val lp = mLeftIV!!.layoutParams
        lp.width = mLeftIconWidth
        lp.height = mLeftIconHeight
        mLeftIV!!.layoutParams = lp
    }

    private fun initRightViewValue() {
        if (!StringUtil.isBlank(mRightText)) {
            mRightTV!!.text = mRightText
            mRightTV!!.visibility = View.VISIBLE
        } else {
            mRightTV!!.visibility = View.GONE
        }
        if (mRightTextColor != null) {
            mRightTV!!.setTextColor(mRightTextColor)
        }
        mRightTV!!.setTextSize(0, mRightTextSize.toFloat())
        if (mRightIcon != null) {
            mRightIV!!.visibility = View.VISIBLE
            mRightIV!!.setImageDrawable(mRightIcon)
        } else {
            mRightIV!!.visibility = View.GONE
        }
        val lp = mRightIV!!.layoutParams
        lp.width = mRightIconWidth
        lp.height = mRightIconHeight
        mRightIV!!.layoutParams = lp
    }

    private fun initCenterViewValue() {
        if (!StringUtil.isBlank(mCenterText)) {
            mCenterTV!!.text = mCenterText
            mCenterTV!!.visibility = View.VISIBLE
        } else {
            mCenterTV!!.visibility = View.GONE
        }
        mCenterTV!!.setTextSize(0, mCenterTextSize.toFloat())
        if (mCenterTextColor != null) {
            mCenterTV!!.setTextColor(mCenterTextColor)
        }
        if (mCenterIcon != null) {
            mCenterIV!!.setImageDrawable(mCenterIcon)
        }
        val lp = mCenterIV!!.layoutParams
        lp.width = mCenterIconWidth
        lp.height = mCenterIconHeight
        mCenterIV!!.layoutParams = lp
        mLlCenter!!.setPadding(mCenterPaddingLeft, 0, mCenterPaddingRight, 0)
    }

    public fun setLeftTv(strText: String): TextView {
        mLeftText = strText
        if (StringUtil.isBlank(mLeftText)) {
            mLeftTV!!.visibility = View.GONE
        } else {
            mLeftTV!!.visibility = View.VISIBLE
            mLeftTV!!.text = mLeftText
        }
        return mLeftTV!!
    }

    public fun setLeftTvSize(size: Int): TextView {
        mLeftTextSize = size
        mLeftTV!!.textSize = mLeftTextSize.toFloat()
        return mLeftTV!!
    }

    public fun setLeftTvColor(color: ColorStateList): TextView {
        if (color != null) {
            mLeftTextColor = color
            mLeftTV!!.setTextColor(mLeftTextColor)
        }
        return mLeftTV!!
    }

    public fun setLeftIv(drawable: Drawable): ImageView {
        if (drawable != null) {
            mLeftIcon = drawable
            mLeftIV!!.setImageDrawable(mLeftIcon)
        }
        return mLeftIV!!
    }

    public fun setLeftIvWithHeight(width: Int, height: Int): ImageView {
        val lp = mLeftIV!!.layoutParams
        lp.width = width
        lp.height = height
        mLeftIV!!.layoutParams = lp
        return mLeftIV!!
    }

    public fun setCenterTv(strText: String): TextView {
        if (StringUtil.isBlank(strText)) {
            mCenterTV!!.visibility = View.GONE
        } else {
            mCenterText = strText
            mCenterTV!!.text = mCenterText
            mCenterTV!!.visibility = View.VISIBLE
        }
        return mCenterTV!!
    }

    public fun setCenterTvSize(size: Int): TextView {
        mCenterTextSize = size
        mCenterTV!!.textSize = mCenterTextSize.toFloat()
        return mCenterTV!!
    }

    public fun setCenterTvColor(color: ColorStateList): TextView {
        if (color != null) {
            mCenterTextColor = color
            mCenterTV!!.setTextColor(mCenterTextColor)
        }
        return mCenterTV!!
    }

    public fun setCenterIv(drawable: Drawable): ImageView {
        if (drawable != null) {
            mCenterIcon = drawable
            mCenterIV!!.setImageDrawable(mCenterIcon)
        }
        return mCenterIV!!
    }

    public fun setCenterIvWithHeight(width: Int, height: Int): ImageView {
        val lp = mCenterIV!!.layoutParams
        lp.width = width
        lp.height = height
        mCenterIV!!.layoutParams = lp
        return mCenterIV!!
    }

    public fun setCenterLlPadding(left: Int, right: Int) {
        mLlCenter!!.setPadding(left, 0, right, 0)
    }

    public fun setRightTv(strText: String): TextView {
        if (StringUtil.isBlank(strText)) {
            mRightTV!!.visibility = View.GONE
        } else {
            mRightText = strText
            mRightTV!!.text = mRightText
            mRightTV!!.visibility = View.VISIBLE
        }
        return mRightTV!!
    }

    public fun setRightTvSize(size: Int): TextView {
        mRightTextSize = size
        mRightTV!!.textSize = mRightTextSize.toFloat()
        return mCenterTV!!
    }

    public fun setRightTvColor(color: ColorStateList): TextView {
        if (color != null) {
            mRightTextColor = color
            mRightTV!!.setTextColor(mRightTextColor)
        }
        return mRightTV!!
    }

    public fun setRightIv(drawable: Drawable): ImageView {
        if (drawable != null) {
            mRightIcon = drawable
            mRightIV!!.setImageDrawable(mRightIcon)
        }
        return mRightIV!!
    }

    public fun setRightIvWithHeight(width: Int, height: Int): ImageView {
        val lp = mRightIV!!.layoutParams
        lp.width = width
        lp.height = height
        mRightIV!!.layoutParams = lp
        return mRightIV!!
    }

}