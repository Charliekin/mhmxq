package com.mhm.xq.entity.base

import org.greenrobot.greendao.annotation.NotNull

open class BaseEntity {

    @NotNull
    private var code: Int = 0
    private var message: String? = null

    public fun getCode(): Int {
        return code
    }

    public fun setCode(code: Int) {
        this.code = code
    }

    public fun getMessage(): String {
        return message!!
    }

    public fun setMessage(message: String) {
        this.message = message
    }
}