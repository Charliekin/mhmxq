package com.mhm.xq.bll.global

import com.mhm.xq.bll.greendao.NewsColumnManager
import com.mhm.xq.bll.greendao.UserManager
import com.mhm.xq.dal.greendao.gen.DaoSession


class ManagerSession {

    var mDaoSession: DaoSession? = null

    var userManager: UserManager? = null
    var newsColumnManager: NewsColumnManager? = null

    constructor(daoSession: DaoSession) {
        mDaoSession = daoSession
        userManager = UserManager(this, daoSession.userDao)
        newsColumnManager = NewsColumnManager(this, daoSession.newsColumnDao)
    }

    fun getDaoSession(): DaoSession {
        return mDaoSession!!
    }
}