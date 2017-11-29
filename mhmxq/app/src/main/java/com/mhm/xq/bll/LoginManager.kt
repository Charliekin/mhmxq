package com.mhm.xq.bll

import android.content.Intent
import android.os.Bundle
import com.mhm.xq.ui.auth.activity.SignActivity
import com.mhm.xq.ui.base.activity.BaseActivity

class LoginManager {
    companion object {
        fun startActivityForRequest(baseActivity: BaseActivity, bundle: Bundle?, request: Int) {
            val intent: Intent = Intent(baseActivity, SignActivity::class.java)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            baseActivity.startActivityForResult(intent, request)
        }
    }
}