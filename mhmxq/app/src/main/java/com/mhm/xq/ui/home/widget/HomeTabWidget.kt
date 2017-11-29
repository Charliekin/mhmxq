package com.mhm.xq.ui.home.widget

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mhm.xq.R
import com.mhm.xq.utils.StringUtil

class HomeTabWidget : ConstraintLayout {

    private var mTvUnread: TextView? = null
    private var mTvHomeTab: TextView? = null
    private var mIvHomeTab: ImageView? = null

    private var mTabName: String? = null
    private var mTabNameSize: Int? = null
    private var mTabNameColor: ColorStateList? = null
    private var mTabIconNameMargin: Int? = null
    private var mTabIcon: Drawable? = null
    private var mTabIconWidth: Int? = null
    private var mTabIconHeight: Int? = null
    private var mUnread: String? = null
    private var mUnreadSize: Int? = null
    private var mUnreadBg: Drawable? = null
    private var mUnreadColor: ColorStateList? = null

    constructor(context: Context) : this(context, null) {
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
        val typeArray: TypedArray = getContext().obtainStyledAttributes(R.styleable.HomeTabWidget)
        initAttrs(typeArray)
    }

    private fun initAttrs(typeArray: TypedArray) {
        if (typeArray == null) {
            return
        }
        mTabName = typeArray.getString(R.styleable.HomeTabWidget_tabName)
        if (typeArray.hasValue(R.styleable.HomeTabWidget_tabNameSize)) {
            mTabNameSize = typeArray.getInt(R.styleable.HomeTabWidget_tabNameSize, 12)
        }
        mTabNameColor = typeArray.getColorStateList(R.styleable.HomeTabWidget_tabNameColor)
        if (typeArray.hasValue(R.styleable.HomeTabWidget_tabIconNameMargin)) {
            mTabIconNameMargin = typeArray.getInt(R.styleable.HomeTabWidget_tabIconNameMargin, 0)
        }
        mTabIcon = typeArray.getDrawable(R.styleable.HomeTabWidget_tabIcon)
        if (typeArray.hasValue(R.styleable.HomeTabWidget_tabIconWidth)) {
            mTabIconWidth = typeArray.getInt(R.styleable.HomeTabWidget_tabIconWidth, -1)
        }
        if (typeArray.hasValue(R.styleable.HomeTabWidget_tabIconHeight)) {
            mTabIconHeight = typeArray.getInt(R.styleable.HomeTabWidget_tabIconHeight, -1)
        }
        mUnread = typeArray.getString(R.styleable.HomeTabWidget_unread)
        if (typeArray.hasValue(R.styleable.HomeTabWidget_unreadSize)) {
            mUnreadSize = typeArray.getInt(R.styleable.HomeTabWidget_unreadSize, 4)
        }
        mUnreadBg = typeArray.getDrawable(R.styleable.HomeTabWidget_unreadBg)
        mUnreadColor = typeArray.getColorStateList(R.styleable.HomeTabWidget_unreadColor)
        typeArray.recycle()
        initViewValues()
    }

    private fun initView() {
        val view: View = LayoutInflater.from(context).inflate(R.layout.common_view_home_tab_item, this, true)
        mIvHomeTab = view.findViewById<ImageView>(R.id.ivHomeTab)
        mTvHomeTab = view.findViewById<TextView>(R.id.tvHomeTab)
        mTvUnread = view.findViewById<TextView>(R.id.tvUnread)
    }

    private fun initViewValues() {
        mIvHomeTab!!.setImageDrawable(mTabIcon)
        mTvHomeTab!!.text = mTabName
        mTvUnread!!.text = mUnread
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mTvUnread!!.background = mUnreadBg
        }
        if (StringUtil.isBlank(mTabName)) {
            mTvHomeTab!!.visibility = View.GONE
        } else {
            mTvHomeTab!!.visibility = View.VISIBLE
        }
        if (StringUtil.isBlank(mUnread)) {
            mTvUnread!!.visibility = View.GONE
        } else {
            mTvUnread!!.visibility = View.VISIBLE
        }
        val iconLayoutParams: LayoutParams = mIvHomeTab!!.layoutParams as LayoutParams
        if (mTabIconWidth != null) {
            iconLayoutParams.width = mTabIconWidth as Int
        }
        if (mTabIconHeight != null) {
            iconLayoutParams.height = mTabIconHeight as Int
        }
        if (mTabNameSize != null) {
            mTvHomeTab!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabNameSize as Float)
        }
        if (mTabNameColor != null) {
            mTvHomeTab!!.setTextColor(mTabNameColor)
        }
        if (mTabIconNameMargin != null) {
            iconLayoutParams.bottomMargin = mTabIconNameMargin as Int
        }
        if (mUnreadSize != null) {
            mTvUnread!!.textSize = mUnreadSize as Float
        }
        if (mUnreadColor != null) {
            mTvUnread!!.setTextColor(mUnreadColor)
        }
        mIvHomeTab!!.layoutParams = iconLayoutParams
    }

    public fun setTabNameText(strText: String) {
        mTvHomeTab!!.text = strText
        mTabName = strText
        if (StringUtil.isBlank(mTabName)) {
            mTvHomeTab!!.visibility = View.GONE
        } else {
            mTvHomeTab!!.visibility = View.VISIBLE
        }
    }

    public fun setUnreadText(unread: String) {
        mTvUnread!!.text = unread
        mUnread = unread
        if (StringUtil.isBlank(mUnread)) {
            mTvUnread!!.visibility = View.GONE
        } else {
            mTvUnread!!.visibility = View.VISIBLE
        }
    }

    public fun setUnreadTextSize(size: Int) {
        mUnreadSize = size
        mTvUnread!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mUnreadSize as Float)
    }

    public fun setTabNameSize(size: Int) {
        mTabNameSize = size
        mTvHomeTab!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabNameSize as Float)
    }

    public fun setTabNameMarginTopBottom(top: Int, bottom: Int) {
        val layoutParams: LayoutParams = mTvHomeTab!!.layoutParams as LayoutParams
        layoutParams.bottomToBottom = bottom
        layoutParams.topToBottom = top
        mTvHomeTab!!.layoutParams = layoutParams
    }

    public fun setTabNameColor(color: ColorStateList) {
        mTabNameColor = color
        mTvHomeTab!!.setTextColor(mTabNameColor)
    }

    public fun setUnreadColor(color: ColorStateList) {
        mUnreadColor = color
        mTvUnread!!.setTextColor(mUnreadColor)
    }

    public fun setUnreadBg(bg: Drawable) {
        mUnreadBg = bg
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mTvUnread!!.background = mUnreadBg
        }
    }

    public fun setIcon(icon: Drawable) {
        mTabIcon = icon
        mIvHomeTab!!.setImageDrawable(mTabIcon)
    }

    public fun setIconSize(width: Int, height: Int, bottomMargin: Int) {
        mTabIconWidth = width
        mTabIconHeight = height
        val layoutParams: LayoutParams = mIvHomeTab!!.layoutParams as LayoutParams
        layoutParams.width = mTabIconWidth as Int
        layoutParams.height = mTabIconHeight as Int
        layoutParams.bottomMargin = bottomMargin
        mIvHomeTab!!.layoutParams = layoutParams
    }

}