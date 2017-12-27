package com.mhm.xq.dal

import com.mhm.xq.BuildConfig
import com.mhm.xq.MyApp
import com.mhm.xq.bll.global.ManagerSession
import com.mhm.xq.dal.greendao.gen.DaoMaster
import com.mhm.xq.dal.greendao.gen.ReleaseOpenHelper
import org.greenrobot.greendao.identityscope.IdentityScopeType

class Db {

    companion object {
        val UNKNOWN_DB_NAME = "unknown_db.db"
        private var mManagerSession: ManagerSession? = null
        private var mName: String? = null

        @Synchronized
        fun open(name: String) {
            if (mName == null || mName.equals(name)) {
                if (mManagerSession != null) {
                    mManagerSession!!.getDaoSession().clear()
                    mManagerSession!!.getDaoSession().database.close()
                    mManagerSession = null
                }
            }
            if (mManagerSession != null) {
                return
            }

            mName = name
            var openHelper: DaoMaster.OpenHelper? = null
            if (BuildConfig.DEBUG) {
                openHelper = DaoMaster.DevOpenHelper(MyApp.getContext()!!, mName, null)
            } else {
                openHelper = ReleaseOpenHelper(MyApp.getContext()!!, mName, null)
            }
            var db = openHelper.writableDatabase
            db.enableWriteAheadLogging()
            var daoMaster = DaoMaster(db)
            var daoSession = daoMaster.newSession(IdentityScopeType.Session)
            mManagerSession = ManagerSession(daoSession)
        }

        @Synchronized
        fun close() {
            if (mManagerSession == null) {
                return
            }
            mManagerSession!!.getDaoSession().clear()
            mManagerSession!!.getDaoSession().database.close()
            mManagerSession = null
            mName = null
        }

        fun getManagerSession(): ManagerSession = mManagerSession!!
    }
}