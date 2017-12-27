package com.mhm.xq.bll.global

import com.mhm.xq.bll.greendao.UserManager
import com.mhm.xq.dal.greendao.gen.DaoSession


class ManagerSession {

    var mDaoSession: DaoSession? = null

    var userManager: UserManager? = null

    constructor(daoSession: DaoSession) {
        mDaoSession = daoSession
        userManager = UserManager(this, daoSession.userDao)
    }

    fun getDaoSession(): DaoSession {
        return mDaoSession!!
    }
}