package com.mhm.xq.entity

class New {
    private var id: String? = null
    private var title: String? = null
    private var source: String? = null
    private var content: String? = null
    private var status: Int = 0
    private var ct: Long = 0
    private var url: String? = null
    private var channelId: String? = null
    private var userId: Int = 0

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getSource(): String? {
        return source
    }

    fun setSource(source: String) {
        this.source = source
    }

    fun getContent(): String? {
        return content
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun getStatus(): Int {
        return status
    }

    fun setStatus(status: Int) {
        this.status = status
    }

    fun getCt(): Long {
        return ct
    }

    fun setCt(ct: Long) {
        this.ct = ct
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String) {
        this.url = url
    }

    fun getChannelId(): String? {
        return channelId
    }

    fun setChannelId(channelId: String) {
        this.channelId = channelId
    }

    fun getUserId(): Int {
        return userId
    }

    fun setUserId(userId: Int) {
        this.userId = userId
    }
}