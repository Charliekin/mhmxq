package com.mhm.xq.entity

class User {
    private var id: String? = null
    private var name: String? = null
    private var mobile: String? = null
    private var email: String? = null
    private var nick_name: String? = null
    private var age: Int = 0
    private var gender: Int = 0
    private var icon: String? = null
    private var ct: Long = 0

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getMobile(): String? {
        return mobile
    }

    fun setMobile(mobile: String) {
        this.mobile = mobile
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getNick_name(): String? {
        return nick_name
    }

    fun setNick_name(nick_name: String) {
        this.nick_name = nick_name
    }

    fun getAge(): Int {
        return age
    }

    fun setAge(age: Int) {
        this.age = age
    }

    fun getGender(): Int {
        return gender
    }

    fun setGender(gender: Int) {
        this.gender = gender
    }

    fun getIcon(): String? {
        return icon
    }

    fun setIcon(icon: String) {
        this.icon = icon
    }

    fun getCt(): Long {
        return ct
    }

    fun setCt(ct: Long) {
        this.ct = ct
    }

}