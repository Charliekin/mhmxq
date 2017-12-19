package com.mhm.xq.ui.common.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity
import com.mhm.xq.ui.base.adapter.BaseRcvAdapter
import com.mhm.xq.utils.ListUtils

class GeneralViewPictureActivity : BaseActivity(), View.OnClickListener, BaseRcvAdapter.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_general_view_pic)
        init()
    }

    private fun init() {
        setTitleBarTitle("", true)
        setTitleBarRightIcon(R.drawable.menu_share_more, this)
    }

    override fun onClick(p0: View?) {
        doMore(this,
                ListUtils.convertToList(resources.getStringArray(R.array.BottomDialogAlbumPres)) as ArrayList<String>)
    }

    override fun onItemClick(v: View?, position: Int?) {
        when (position) {
            0 -> {
                startActivity(Intent(this, ChoosePhotoFromAlbumActivity::class.java))
            }
            1 -> {

            }
        }
    }
}