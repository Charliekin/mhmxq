package com.mhm.xq.ui.home.adapter

import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.mhm.xq.R
import com.mhm.xq.bll.ConfigManager
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.ui.home.activity.HomeActivity
import java.util.*

class GuidePagerAdapter(baseActivity: BaseActivity) : PagerAdapter() {

    private var mBaseActivity: BaseActivity? = baseActivity
    var mList: ArrayList<Int>? = null

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return 3
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container!!.removeView(getView(position))
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val view: View = getView(position)
        container!!.addView(view)
        return view
    }

    fun getView(position: Int): View {
        val view: View = LayoutInflater.from(mBaseActivity).inflate(R.layout.my_view_guide, null)
        val imageView: ImageView = view.findViewById(R.id.ivGuide)
        val tv: TextView = view.findViewById(R.id.tvGuide)
        imageView.setImageResource(mList!!.get(position))
        tv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                ConfigManager.getInstance()!!.updateVersionCode()
                val anim: Animation = AnimationUtils.loadAnimation(mBaseActivity, R.anim.abc_fade_out)
                anim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        tv.visibility = View.GONE
                        mBaseActivity!!.startActivity(Intent(mBaseActivity, HomeActivity::class.java))
                        mBaseActivity!!.finish()
                    }

                    override fun onAnimationStart(animation: Animation?) {
                    }

                })
                tv.startAnimation(anim)
            }

        })
        return view
    }

    init {
        mList = ArrayList()
        mList!!.add(R.mipmap.guide_a)
        mList!!.add(R.mipmap.guide_b)
        mList!!.add(R.mipmap.guide_c)
    }

}