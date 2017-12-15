package com.mhm.xq.entity

class Photo {
    private var id: Int? = null
    private var path: String? = null

    constructor(id: Int?, path: String?) {
        this.id = id
        this.path = path
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Photo) return false

        val photo = o as Photo?

        return id == photo!!.id
    }

    override fun hashCode(): Int {
        return id!!
    }

    fun getPath(): String {
        return path!!
    }

    fun setPath(path: String) {
        this.path = path
    }

    fun getId(): Int {
        return id!!
    }

    fun setId(id: Int) {
        this.id = id
    }
}