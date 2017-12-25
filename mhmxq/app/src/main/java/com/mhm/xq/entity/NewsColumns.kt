package com.mhm.xq.entity

import com.mhm.xq.entity.base.BaseEntity

class NewsColumns : BaseEntity() {
    private var newsColumns: ArrayList<NewsColumn>? = null

    public fun getNewsColumns(): ArrayList<NewsColumn> {
        return newsColumns!!
    }

    public fun setNewsColumns(newsColumns: ArrayList<NewsColumn>) {
        this.newsColumns = newsColumns
    }
}