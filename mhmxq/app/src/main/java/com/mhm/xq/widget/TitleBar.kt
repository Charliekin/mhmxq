package com.mhm.xq.widget

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mhm.xq.R
import com.mhm.xq.utils.ColorUtil
import com.mhm.xq.utils.DensityUtil
import com.mhm.xq.utils.StringUtil

open class TitleBar : ViewGroup, View.OnClickListener {

    protected var mLlLeft: LinearLayout? = null
    protected var mLlCenter: LinearLayout? = null
    protected var mLlRight: LinearLayout? = null

    protected var mTvLeft: TextView? = null
    protected var mIvLeft: ImageView? = null
    protected var mTvTitle: TextView? = null
    protected var mTvSubTitle: TextView? = null

    //左部LinearLayout的paddlingLeft
    private var mIntLeftPaddingLeft: Int = 0
    //左部LinearLayout的paddingRight
    private var mIntLeftPaddingRight: Int = 0

    //返回按钮的图片
    private var mIconLeft: Drawable? = null
    private var mIntLeftIconWidth: Int = DensityUtil.dip2px(context, 40F)
    private var mIntLeftIconHeight: Int = DensityUtil.dip2px(context, 40F)

    //放回按钮文本
    private var mStrLeftText: String? = null
    private var mIntLeftTextSize: Int = DensityUtil.dip2px(context, 14F)
    private var mColLeftTextColor: ColorStateList = ColorUtil.createColorStateList(Color.BLACK)
    //文本距离返回图片的距离
    private var mIntLeftTextMarginLeft: Int = 0

    private var mIsShowLeftLayout: Boolean = false

    //标题
    private var mStrCenterTitle: String? = null
    private var mStrCenterSubtitle: String? = null
    private var mIntCenterTitleTextSize = DensityUtil.dip2px(context, 14F)
    private var mColCenterTitleTextColor = ColorUtil.createColorStateList(Color.BLACK)
    private var mIntCenterSubtitleTextSize = DensityUtil.dip2px(context, 14F)
    private var mColCenterSubtitleTextColor = ColorUtil.createColorStateList(Color.BLACK)
    private var mIntCenterSubTitleMarginTop = DensityUtil.dip2px(context, 5F)
    private var mBlnCenterMaxSpace = false

    //右侧属性
    //右部LinearLayout的paddlingLeft
    private var mIntRightPaddingLeft = DensityUtil.dip2px(context, 5F)
    //右部LinearLayout的paddingRight
    private var mIntRightPaddingRight = DensityUtil.dip2px(context, 5F)
    //右部Item的默认MarginRight
    private var mIntRightItemMarginRight = 0
    //右部Item的默认MarginLeft
    private var mIntRightItemMarginLeft = DensityUtil.dip2px(context, 5F)
    //右部文本
    private var mColRightTextColor = ColorUtil.createColorStateList(Color.BLACK)
    private var mIntRightTextSize = DensityUtil.dip2px(context, 14F)
    private var mIntRightTextPaddingLeft = DensityUtil.dip2px(context, 2F)
    private var mIntRightTextPaddingRight = DensityUtil.dip2px(context, 2F)
    private var mIntRightTextWidth = ViewGroup.LayoutParams.WRAP_CONTENT
    //右部图片
    private var mIntRightIconWidth = DensityUtil.dip2px(context, 40F)
    private var mIntRightIconHeight = DensityUtil.dip2px(context, 40F)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mLlLeft!!.layout(0, 0, mLlLeft!!.measuredWidth, measuredHeight)
        mLlRight!!.layout(measuredWidth - mLlRight!!.measuredWidth, 0, measuredWidth,
                measuredHeight)
        if (mLlLeft!!.measuredWidth > mLlRight!!.measuredWidth) {
            mLlCenter!!.layout(mLlLeft!!.measuredWidth, 0,
                    if (mBlnCenterMaxSpace) measuredWidth else mLlLeft!!.measuredWidth + mLlCenter!!.measuredWidth,
                    measuredHeight)
        } else {
            mLlCenter!!.layout(
                    measuredWidth - mLlRight!!.measuredWidth - mLlCenter!!.measuredWidth,
                    0,
                    if (mBlnCenterMaxSpace) measuredWidth else measuredWidth - mLlRight!!.measuredWidth,
                    measuredHeight)
        }
    }

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }


    private var mOnLeftClickListener: OnLeftClickListener? = null


    fun setOnLeftClickListener(onLeftClickListener: OnLeftClickListener?) {
        mOnLeftClickListener = onLeftClickListener
        if (onLeftClickListener == null) {
            mLlLeft!!.setOnClickListener(null)
        } else {
            mLlLeft!!.setOnClickListener(this)
        }
    }

    private fun init(attrs: AttributeSet?) {
        initViews()
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        initAttrs(typedArray)
    }

    private fun initAttrs(typedArray: TypedArray?) {
        if (typedArray != null) {
            //left
            mIntLeftPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.TitleBar_left_padding_left,
                    mIntLeftPaddingLeft)
            mIntLeftPaddingRight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_left_padding_right,
                    mIntLeftPaddingRight)
            if (typedArray.hasValue(R.styleable.TitleBar_left_icon)) {
                mIconLeft = typedArray.getDrawable(R.styleable.TitleBar_left_icon)
            }
            mIntLeftIconWidth = typedArray.getDimensionPixelSize(R.styleable.TitleBar_left_icon_width,
                    mIntLeftIconWidth)
            mIntLeftIconHeight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_left_icon_height,
                    mIntLeftIconHeight)
            if (typedArray.hasValue(R.styleable.TitleBar_left_text)) {
                mStrLeftText = typedArray.getString(R.styleable.TitleBar_left_text)
            }
            mIntLeftTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_left_text_size, mIntLeftTextSize)
            if (typedArray.hasValue(R.styleable.TitleBar_left_text_color)) {
                mColLeftTextColor = typedArray.getColorStateList(R.styleable.TitleBar_left_text_color)
            }
            mIntLeftTextMarginLeft = typedArray.getDimensionPixelSize(R.styleable.TitleBar_left_text_margin_left, mIntLeftTextMarginLeft)

            mIsShowLeftLayout = typedArray.getBoolean(R.styleable.TitleBar_show_left_layout, true)
            //center
            if (typedArray.hasValue(R.styleable.TitleBar_center_title)) {
                mStrCenterTitle = typedArray.getString(R.styleable.TitleBar_center_title)
            }
            if (typedArray.hasValue(R.styleable.TitleBar_center_subtitle)) {
                mStrCenterSubtitle = typedArray.getString(R.styleable.TitleBar_center_subtitle)
            }
            mIntCenterTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_center_title_text_size, mIntCenterTitleTextSize)
            mIntCenterSubtitleTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_center_subtitle_text_size, mIntCenterSubtitleTextSize)
            if (typedArray.hasValue(R.styleable.TitleBar_center_title_text_color)) {
                mColCenterTitleTextColor = typedArray.getColorStateList(R.styleable.TitleBar_center_title_text_color)
            }
            if (typedArray.hasValue(R.styleable.TitleBar_center_subtitle_text_color)) {
                mColCenterSubtitleTextColor = typedArray.getColorStateList(R.styleable.TitleBar_center_subtitle_text_color)
            }
            mIntCenterSubTitleMarginTop = typedArray.getDimensionPixelSize(R.styleable.TitleBar_center_subtitle_margin_top, mIntCenterSubTitleMarginTop)
            mBlnCenterMaxSpace = typedArray.getBoolean(R.styleable.TitleBar_center_max_space, false)
            //right
            mIntRightPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.TitleBar_right_padding_left,
                    mIntRightPaddingLeft)
            mIntRightPaddingRight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_right_padding_right,
                    mIntRightPaddingRight)
            mIntRightItemMarginRight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_right_item_margin_right,
                    mIntRightItemMarginRight)
            mIntRightItemMarginLeft = typedArray.getDimensionPixelSize(R.styleable.TitleBar_right_item_margin_left,
                    mIntRightItemMarginLeft)
            if (typedArray.hasValue(R.styleable.TitleBar_right_text_color)) {
                mColRightTextColor = typedArray.getColorStateList(R.styleable.TitleBar_right_text_color)
            }
            mIntRightTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleBar_right_text_size, mIntRightTextSize)
            mIntRightTextPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.TitleBar_right_text_padding_left, mIntRightTextPaddingLeft)
            mIntRightTextPaddingRight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_right_text_padding_right, mIntRightTextPaddingRight)
            mIntRightTextWidth = typedArray.getDimensionPixelSize(R.styleable.TitleBar_right_text_width, mIntRightTextWidth)
            mIntRightIconWidth = typedArray.getDimensionPixelSize(R.styleable.TitleBar_right_icon_width, mIntRightIconWidth)
            mIntRightIconHeight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_right_icon_height, mIntRightIconHeight)
            typedArray.recycle()
        }
        initViewsValues()
    }


    private fun initViews() {
        initLeftViews()
        initCenterViews()
        initRightViews()
        addView(mLlLeft, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT))
        addView(mLlCenter, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT))
        addView(mLlRight, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    private fun initLeftViews() {
        mIvLeft = ImageView(context)
        mIvLeft!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mTvLeft = TextView(context)
        mTvLeft!!.setSingleLine(true)
        mTvLeft!!.ellipsize = TextUtils.TruncateAt.END
        mLlLeft = LinearLayout(context)
        mLlLeft!!.orientation = LinearLayout.HORIZONTAL
        mLlLeft!!.gravity = Gravity.CENTER_VERTICAL
        mLlLeft!!.addView(mIvLeft)
        mLlLeft!!.addView(mTvLeft)
    }


    private fun initCenterViews() {
        mLlCenter = LinearLayout(context)
        mLlCenter!!.orientation = LinearLayout.VERTICAL
        mLlCenter!!.gravity = Gravity.CENTER

        mTvTitle = TextView(context)
        mTvTitle!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mTvTitle!!.gravity = Gravity.CENTER
        mTvTitle!!.setSingleLine(true)
        mTvTitle!!.ellipsize = TextUtils.TruncateAt.END
        mLlCenter!!.addView(mTvTitle)

        mTvSubTitle = TextView(context)
        mTvSubTitle!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mTvSubTitle!!.gravity = Gravity.CENTER
        mTvSubTitle!!.setSingleLine(true)
        mTvSubTitle!!.ellipsize = TextUtils.TruncateAt.END
        mLlCenter!!.addView(mTvSubTitle)
    }

    private fun initRightViews() {
        mLlRight = LinearLayout(context)
        mLlRight!!.orientation = LinearLayout.HORIZONTAL
        mLlRight!!.gravity = Gravity.CENTER_VERTICAL
    }


    private fun initViewsValues() {
        initLeftViewsValues()
        initCenterViewsValues()
        initRightViewValues()
    }

    private fun initLeftViewsValues() {
        mLlLeft!!.setPadding(mIntLeftPaddingLeft, 0, mIntLeftPaddingRight, 0)

        var params = mIvLeft!!.layoutParams as LinearLayout.LayoutParams
        params.width = mIntLeftIconWidth
        params.height = mIntLeftIconHeight
        mIvLeft!!.layoutParams = params
        mIvLeft!!.setImageDrawable(mIconLeft)
        if (mIconLeft == null) {
            mIvLeft!!.visibility = View.GONE
        } else {
            mIvLeft!!.visibility = View.VISIBLE
        }

        params = mTvLeft!!.layoutParams as LinearLayout.LayoutParams
        params.leftMargin = mIntLeftTextMarginLeft
        mTvLeft!!.layoutParams = params
        mTvLeft!!.gravity = Gravity.CENTER
        mTvLeft!!.text = mStrLeftText
        mTvLeft!!.setTextSize(0, mIntLeftTextSize.toFloat())
        if (mColLeftTextColor != null) {
            mTvLeft!!.setTextColor(mColLeftTextColor)
        }
        if (StringUtil.isBlank(mStrLeftText)) {
            mTvLeft!!.visibility = View.GONE
        } else {
            mTvLeft!!.visibility = View.VISIBLE
        }

        mLlLeft!!.visibility = if (mIsShowLeftLayout) View.VISIBLE else View.GONE
    }

    private fun initCenterViewsValues() {
        mTvTitle!!.text = mStrCenterTitle
        mTvSubTitle!!.text = mStrCenterSubtitle
        mTvTitle!!.setTextSize(0, mIntCenterTitleTextSize.toFloat())
        mTvSubTitle!!.setTextSize(0, mIntCenterSubtitleTextSize.toFloat())
        if (mColCenterTitleTextColor != null) {
            mTvTitle!!.setTextColor(mColCenterTitleTextColor)
        }
        if (mColCenterSubtitleTextColor != null) {
            mTvSubTitle!!.setTextColor(mColCenterSubtitleTextColor)
        }
        if (StringUtil.isBlank(mStrCenterSubtitle)) {
            mTvSubTitle!!.visibility = View.GONE
        } else {
            mTvSubTitle!!.visibility = View.VISIBLE
        }
        val params = mTvSubTitle!!.getLayoutParams() as LinearLayout.LayoutParams
        params.topMargin = mIntCenterSubTitleMarginTop
        mTvSubTitle!!.layoutParams = params
    }

    private fun initRightViewValues() {
        mLlRight!!.setPadding(mIntRightPaddingLeft, 0, mIntRightPaddingRight, 0)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val centerWidth: Int
        if (mBlnCenterMaxSpace) {
            centerWidth = measuredWidth - mLlLeft!!.measuredWidth
        } else {
            if (mLlLeft!!.measuredWidth > mLlRight!!.measuredWidth) {
                centerWidth = measuredWidth - mLlLeft!!.measuredWidth * 2
            } else {
                centerWidth = measuredWidth - mLlRight!!.measuredWidth * 2
            }
        }
        mLlCenter!!.measure(View.MeasureSpec.makeMeasureSpec(centerWidth, View.MeasureSpec.EXACTLY),
                heightMeasureSpec)
    }

    override fun onClick(v: View) {
        if (v === mLlLeft) {
            if (mOnLeftClickListener != null) {
                mOnLeftClickListener!!.leftClick(v)
            }
        }
    }

    fun show() {
        clearAnimation()
        val aniSlide = AnimationUtils.loadAnimation(context, R.anim.menu_slide_from_top_in)
        aniSlide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {

            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        startAnimation(aniSlide)
    }

    fun hide() {
        clearAnimation()
        val aniSlide = AnimationUtils.loadAnimation(context, R.anim.menu_slide_from_top_out)
        aniSlide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {
                visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        startAnimation(aniSlide)
    }


    //<editor-fold desc="center">

    fun getCenterContainer(): LinearLayout {
        return mLlCenter!!
    }

    fun setTitle(title: String) {
        mStrCenterTitle = title
        mTvTitle!!.setText(title)
    }

    fun setTitleGravity(gravity: Int) {
        mTvTitle!!.gravity = gravity
    }

    fun setTitle(@StringRes res: Int) {
        setTitle(resources.getString(res))
    }

    fun setSubtitle(subtitle: String) {
        mStrCenterSubtitle = subtitle
        mTvSubTitle!!.text = subtitle
        mIvLeft!!.visibility = if (StringUtil.isBlank(mStrCenterSubtitle)) View.GONE else View.VISIBLE
    }

    fun setCenterRootView(view: View) {
        mLlCenter!!.removeAllViews()
        mLlCenter!!.setLayoutParams(LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT))
        mLlCenter!!.addView(view)
    }

    fun setCenterMaxSpace(centerMaxSpace: Boolean) {
        mBlnCenterMaxSpace = centerMaxSpace
        requestLayout()
    }

    fun isCenterMaxSpace(): Boolean {
        return mBlnCenterMaxSpace
    }

    //</editor-fold>

    //<editor-fold desc="left">
    fun setLeftIcon(mDrawable: Drawable?) {
        mIconLeft = mDrawable
        mIvLeft!!.setImageDrawable(mIconLeft)
        if (mIconLeft == null) {
            mIvLeft!!.setVisibility(View.GONE)
        } else {
            mIvLeft!!.setVisibility(View.VISIBLE)
        }
    }

    fun setLeftIcon(@DrawableRes resource: Int) {
        setLeftIcon(if (resource == 0) null else ContextCompat.getDrawable(context, resource))
    }

    fun setLeftText(text: String) {
        mTvLeft!!.setText(text)
        mStrLeftText = text
        mTvLeft!!.setVisibility(if (StringUtil.isBlank(text)) View.GONE else View.VISIBLE)
    }

    fun setLeftText(@StringRes resId: Int) {
        setLeftText(resources.getString(resId))
    }

    //</editor-fold>

    //<editor-fold desc="Right Menu">

    fun getRightContainer(): LinearLayout {
        return mLlRight!!
    }

    fun showRightContainer() {
        mLlRight!!.setVisibility(View.VISIBLE)
    }

    fun hideRightContainer() {
        mLlRight!!.setVisibility(View.GONE)
    }

    fun getRightItemCount(): Int {
        return mLlRight!!.getChildCount()
    }

    //cast checked by runtime
    fun <T : View> findRightItemById(@IdRes id: Int): T {
        return getRightContainer().findViewById<View>(id) as T
    }

    fun <T : View> addRightItem(viewBuilder: RightViewBuilder<T>): T {
        return viewBuilder.build(mLlRight!!)
    }

    //</editor-fold>

    //<editor-fold desc="right item builder">

    open inner class RightViewBuilder<T : View>(context: Context) {
        @Px
        private var mMarginLeft = mIntRightItemMarginLeft
        @Px
        private var mMarginRight = mIntRightItemMarginRight
        @Px
        private var mWidth = mIntRightIconWidth
        @Px
        private var mHeight = mIntRightIconHeight
        @IdRes
        private var mId = View.NO_ID
        internal var mView: T? = null
        private var mListener: View.OnClickListener? = null

        fun marginLeft(@Px margin: Int): RightViewBuilder<*> {
            this.mMarginLeft = margin
            return this
        }

        fun marginRight(@Px margin: Int): RightViewBuilder<*> {
            this.mMarginRight = margin
            return this
        }

        fun width(@Px width: Int): RightViewBuilder<*> {
            this.mWidth = width
            return this
        }

        fun height(@Px height: Int): RightViewBuilder<*> {
            this.mHeight = height
            return this
        }

        fun id(@IdRes id: Int): RightViewBuilder<*> {
            this.mId = id
            return this
        }


        fun view(view: T): RightViewBuilder<*> {
            this.mView = view
            return this
        }

        fun clickListener(listener: View.OnClickListener?): RightViewBuilder<*> {
            this.mListener = listener
            return this
        }

        internal open fun build(parent: ViewGroup): T {
            if (mView == null) {
                throw IllegalArgumentException("view is null")
            }
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            params.leftMargin = mMarginLeft
            params.rightMargin = mMarginRight
            params.width = mWidth
            params.height = mHeight
            if (mView!!.id != mId) {
                mView!!.id = mId
            }
            mView!!.setOnClickListener(mListener)
            parent.addView(mView, params)
            return mView!!
        }

    }

    inner class RightImageViewBuilder(context: Context) : RightViewBuilder<ImageView>(context) {
        @DrawableRes
        internal var mDrawableRes: Int = 0

        init {
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            view(imageView)
        }

        fun drawable(@DrawableRes drawableRes: Int): RightImageViewBuilder {
            this.mDrawableRes = drawableRes
            return this
        }

        override fun build(parent: ViewGroup): ImageView {
            if (mDrawableRes != 0) {
                mView!!.setImageResource(mDrawableRes)
            }
            return super.build(parent)
        }
    }

    inner class RightTextViewBuilder(context: Context) : RightViewBuilder<TextView>(context) {
        @Px
        private var mPaddingLeft = mIntRightTextPaddingLeft
        @Px
        private var mPaddingRight = mIntRightTextPaddingRight
        private var mText: CharSequence? = null
        private var mColorStateList = mColRightTextColor
        @Px
        private var mTextSize = mIntRightTextSize

        init {
            val textView = TextView(context)
            textView.gravity = Gravity.CENTER
            view(textView)

            width(ViewGroup.LayoutParams.WRAP_CONTENT)
            height(ViewGroup.LayoutParams.MATCH_PARENT)
        }

        fun paddingLeft(@Px paddingLeft: Int): RightTextViewBuilder {
            this.mPaddingLeft = paddingLeft
            return this
        }

        fun paddingRight(@Px paddingRight: Int): RightTextViewBuilder {
            this.mPaddingRight = paddingRight
            return this
        }

        fun textSize(@Px textSize: Int): RightTextViewBuilder {
            this.mTextSize = textSize
            return this
        }

        fun text(text: CharSequence): RightTextViewBuilder {
            this.mText = text
            return this
        }

        fun text(@StringRes textRes: Int): RightTextViewBuilder {
            this.mText = resources.getString(textRes)
            return this
        }

        fun color(colorStateList: ColorStateList): RightTextViewBuilder {
            this.mColorStateList = colorStateList
            return this
        }

        fun color(@ColorRes colorRes: Int): RightTextViewBuilder {
            this.mColorStateList = ContextCompat.getColorStateList(context, colorRes)!!
            return this
        }

        override fun build(parent: ViewGroup): TextView {
            mView!!.setPadding(mPaddingLeft, 0, mPaddingRight, 0)
            mView!!.text = mText
            mView!!.setTextColor(mColorStateList)
            mView!!.setTextSize(0, mTextSize.toFloat())
            return super.build(parent)
        }
    }

    //</editor-fold>

    interface OnLeftClickListener {
        fun leftClick(view: View)
    }
}