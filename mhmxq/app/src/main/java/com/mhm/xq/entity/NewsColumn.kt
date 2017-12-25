package com.mhm.xq.entity

class NewsColumn {
    private var id: String? = null
    private var name: String? = null
    private var type: Int = 0
    private var ct: Long = 0

    public fun getId(): String {
        return id!!
    }

    public fun setId(id: String?) {
        this.id = id
    }

    public fun getName(): String {
        return name!!
    }

    public fun setName(name: String?) {
        this.name = name
    }

    public fun getType(): Int {
        return type
    }

    public fun setType(type: Int) {
        this.type = type
    }

    public fun getCt(): Long {
        return ct
    }

    public fun setCt(ct: Long) {
        this.ct = ct
    }
}