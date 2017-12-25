package com.mhm.xq.entity

import com.mhm.xq.entity.base.BaseEntity

class News : BaseEntity() {
    private var news: ArrayList<New>? = null
    public fun getNews(): ArrayList<New> {
        return news!!
    }

    public fun setNews(news: ArrayList<New>) {
        this.news = news
    }
}