package com.mhm.xq.bll.greendao

import com.mhm.xq.bll.base.BaseDaoManager
import com.mhm.xq.bll.global.ManagerSession
import com.mhm.xq.dal.greendao.gen.NewsColumnDao
import com.mhm.xq.entity.NewsColumns
import com.mhm.xq.entity.greendao.NewsColumn
import org.greenrobot.greendao.AbstractDao

class NewsColumnManager : BaseDaoManager<NewsColumn, Long> {
    constructor(managerSession: ManagerSession, dao: AbstractDao<NewsColumn, Long>) :
            super(managerSession, dao)

    fun singleton(newsColumns: NewsColumns) {
        val oldNewsColumn = loadByCondition(NewsColumnDao.Properties.Id.isNotNull)
        if (oldNewsColumn != null && oldNewsColumn.size != 0) {
            getDao().deleteInTx(oldNewsColumn)
        }
        fullInsertOrReplaceInTx(newsColumns.getNewsColumns())
    }
}