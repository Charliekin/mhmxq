package com.mhm.xq.bll.greendao

import com.mhm.xq.bll.base.BaseDaoManager
import com.mhm.xq.bll.global.ManagerSession
import com.mhm.xq.entity.greendao.User
import org.greenrobot.greendao.AbstractDao

class UserManager : BaseDaoManager<User, Long> {

    constructor(managerSession: ManagerSession, dao: AbstractDao<User, Long>) :
            super(managerSession, dao) {
    }
}