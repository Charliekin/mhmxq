package com.mhm.xq.bll.base

import com.mhm.xq.bll.global.ManagerSession

open class BaseSessionManager {
    var mManagerSession: ManagerSession? = null

    constructor() {
        throw UnsupportedOperationException("BaseSessionManager cannot instantiated")
    }

    constructor(managerSession: ManagerSession) {
        mManagerSession = managerSession
    }

    public fun getManagerSession(): ManagerSession {
        return mManagerSession!!
    }
}