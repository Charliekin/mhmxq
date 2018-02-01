package com.mhm.xq.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.mhm.xq.MyApp
import com.mhm.xq.R
import com.mhm.xq.entity.CoordinateValues
import com.mhm.xq.entity.CoordinatesXY
import com.mhm.xq.utils.DensityUtil
import com.mhm.xq.utils.LogUtil

class TimeBrokenlineView : View {

    companion object {
        private var DEF_LONG_CLICK_TIME = 500
    }

    private var mContext: Context? = null

    //实际测量过的控件宽高
    private var mWidht: Int = 0
    private var mHeight: Int = 0

    //移动x轴距离
    private var mMoveX = 0f
    private var mDownX = 0f
    private var mDownY = 0f
    private var mMoveY = 0f
    private var mLongClickTime: Long = 0

    //最大范围
    private var mMaxX = 0

    //默认控件为wrap_content默认宽高
    private var DEF_WIDTH: Int = DensityUtil.dip2px(MyApp.getContext()!!, 650f)
    private var DEF_HEIGHT: Int = DensityUtil.dip2px(MyApp.getContext()!!, 400f)

    //默认view padding
    private var DEF_PADDING = 10F
    private var DEF_MARGIN_HEIGHT = DensityUtil.dip2px(MyApp.getContext()!!, 100f).toFloat()

    //背景画笔
    private var mPaintBackground: Paint? = null
    //整体布局画笔
    private var mPaintLayout: Paint? = null
    //布局中分割线画笔
    private var mPaintDivider: Paint? = null

    private var mDefXShaftValue = ArrayList<Int>()
    private var mMoveXShaftValue = ArrayList<Int>()
    private var mCoordinateValue = ArrayList<CoordinateValues>()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, def: Int) : super(context, attrs, def) {
        mContext = context
    }


    private fun initXShaftValue() {
        if (mCoordinateValue.isNotEmpty()) {
            mCoordinateValue.clear()
        }
        if (mDefXShaftValue.isNotEmpty()) {
            return
        }
        var gridY = (mWidht - DEF_PADDING * 2) / 4
        for (i in 0..23) {
            mDefXShaftValue.add((DEF_PADDING + gridY * i).toInt())
        }
        mMaxX = mDefXShaftValue[mDefXShaftValue.size - 1]
        mMoveXShaftValue.addAll(mDefXShaftValue)
    }

    /**
     * 计算控制控件的长宽
     *
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEF_WIDTH, DEF_HEIGHT)
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEF_WIDTH, heightSpecSize)
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, DEF_HEIGHT)
        } else {
            setMeasuredDimension(widthSpecSize, heightSpecSize)
        }
        mWidht = measuredWidth
        mHeight = measuredHeight
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawBackgroundView(canvas)
        drawLayout(canvas)
        drawDivider(canvas)
        drawSignIn(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = event.x
                mDownY = event.y
                mLongClickTime = event.downTime
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
//                mMoveX = event.x
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.eventTime - mLongClickTime > DEF_LONG_CLICK_TIME) {
                    mMoveX = event.x - mDownX
                    LogUtil.i("---->>>     " + mMoveX)
//                    if ((mMoveX + mMoveXShaftValue[mDefXShaftValue.size - 1]) <= mWidht
//                            || (mMoveX + mMoveXShaftValue[mDefXShaftValue.size - 1]) >= mMaxX) {
//                        LogUtil.i("---->>>     差不多行了，超载了。。。" + mMoveX +
//                                "   " + mMoveXShaftValue[mDefXShaftValue.size - 1] + "    " +
//                                (mMoveX + mMoveXShaftValue[mDefXShaftValue.size - 1]))
//                        return false
//                    }
                    invalidate()
                }
            }
        }
        return true
    }

    private fun drawBackgroundView(canvas: Canvas?) {
        initPaintBackground()
        canvas!!.drawRect(0f,
                DensityUtil.dip2px(mContext!!, 100f).toFloat(),
                mWidht.toFloat(), mHeight.toFloat(), mPaintBackground)
    }

    private fun drawLayout(canvas: Canvas?) {
        initPaintLayout()
        //顶边框
        canvas!!.drawLine(DEF_PADDING, DEF_MARGIN_HEIGHT,
                mWidht.toFloat() - DEF_PADDING, DEF_MARGIN_HEIGHT, mPaintLayout)
        //左边框
        canvas.drawLine(DEF_PADDING, DEF_MARGIN_HEIGHT,
                DEF_PADDING, mHeight.toFloat(), mPaintLayout)
        //下边框
        canvas.drawLine(DEF_PADDING, mHeight.toFloat(),
                mWidht.toFloat() - DEF_PADDING, mHeight.toFloat(), mPaintLayout)
        //右边框
        canvas.drawLine(mWidht.toFloat() - DEF_PADDING, DEF_MARGIN_HEIGHT,
                mWidht.toFloat() - DEF_PADDING, mHeight.toFloat(), mPaintLayout)
    }

    private fun drawDivider(canvas: Canvas?) {
        initPaintDivider()
        initXShaftValue()
        var gridX = (mHeight - DEF_MARGIN_HEIGHT) / 3
        //x轴,平分y值
        for (i in 1..2) {
            canvas!!.drawLine(DEF_PADDING, gridX * i + DEF_MARGIN_HEIGHT,
                    mWidht - DEF_PADDING, gridX * i + DEF_MARGIN_HEIGHT, mPaintDivider)
        }
        //y轴,平分x轴
        for (i in 0..23) {
//            mMoveXShaftValue[i] = (mDefXShaftValue[i] + mMoveX).toInt()
            mMoveXShaftValue[i] = (mDefXShaftValue[i] + 1).toInt()
            canvas!!.drawLine(mMoveXShaftValue[i].toFloat() /*+ mMoveX*/, DEF_MARGIN_HEIGHT,
                    mMoveXShaftValue[i].toFloat()/* + mMoveX*/, mHeight.toFloat(), mPaintDivider)
            canvas.drawText("" + i, mMoveXShaftValue[i].toFloat(), DEF_MARGIN_HEIGHT - DEF_PADDING, mPaintLayout)
            LogUtil.i("---->>>     <<<---   " + mMoveXShaftValue[i])
            mDefXShaftValue[i] = mMoveXShaftValue[i]
            if (i >= 23) {
                break
            }
            recordSettingValue(i, gridX)
        }
    }

    private fun recordSettingValue(i: Int, gridX: Float) {
        for (k in 0..2) {
            var coordinateValues = CoordinateValues()
            var coordinateLeftOn = CoordinatesXY()
            var coordinateRightOn = CoordinatesXY()
            var coordinateLeftUnder = CoordinatesXY()
            var coordinateRightUnder = CoordinatesXY()
            coordinateLeftOn.setX(mMoveXShaftValue[i])
            coordinateLeftOn.setY((gridX * k + DEF_MARGIN_HEIGHT).toInt())
            coordinateRightOn.setX(mMoveXShaftValue[i + 1])
            coordinateRightOn.setY((gridX * k + DEF_MARGIN_HEIGHT).toInt())
            coordinateLeftUnder.setX(mMoveXShaftValue[i])
            coordinateLeftUnder.setY((gridX * (k + 1) + DEF_MARGIN_HEIGHT).toInt())
            coordinateRightUnder.setX(mMoveXShaftValue[i + 1])
            coordinateRightUnder.setY((gridX * (k + 1) + DEF_MARGIN_HEIGHT).toInt())
            coordinateValues.setLeftOnAngle(coordinateLeftOn)
            coordinateValues.setRightOnAngle(coordinateRightOn)
            coordinateValues.setLeftUnderAngle(coordinateLeftUnder)
            coordinateValues.setRightUnderAngle(coordinateRightUnder)
            mCoordinateValue.add(coordinateValues)
        }
    }

    private fun drawSignIn(canvas: Canvas?) {
        for (coordinate in mCoordinateValue) {
            if ((mDownX >= coordinate.getLeftOnAngle()!!.getX()) &&
                    mDownX < coordinate.getRightOnAngle()!!.getX() &&
                    mDownY >= coordinate.getLeftOnAngle()!!.getY() &&
                    mDownY < coordinate.getLeftUnderAngle()!!.getY()) {
                canvas!!.drawText("签到成功", (coordinate.getRightOnAngle()!!.getX() -
                        coordinate.getLeftOnAngle()!!.getX()).toFloat(),
                        (coordinate.getLeftUnderAngle()!!.getY() -
                                coordinate.getLeftOnAngle()!!.getY()).toFloat(), mPaintLayout)
            }
        }
    }

    private fun initPaintBackground() {
        mPaintBackground = Paint()
        mPaintBackground!!.color = ContextCompat.getColor(mContext!!, R.color.timeBrokenlineBg)
        mPaintBackground!!.isAntiAlias = true
    }

    private fun initPaintLayout() {
        mPaintLayout = Paint()
        mPaintLayout!!.color = ContextCompat.getColor(mContext!!, R.color.textColorSecondary)
        mPaintLayout!!.isAntiAlias = true

    }

    private fun initPaintDivider() {
        mPaintDivider = Paint()
        mPaintDivider!!.style = Paint.Style.STROKE
        mPaintDivider!!.color = ContextCompat.getColor(mContext!!, R.color.textColorTertiary)
        var array = FloatArray(2, { i -> ((i * 5) % 5 + 100).toFloat() })
        mPaintDivider!!.pathEffect = DashPathEffect(array, 0f)
        mPaintDivider!!.isAntiAlias = true
    }
}