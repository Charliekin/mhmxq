package com.mhm.xq.bll.base

import com.mhm.xq.bll.global.ManagerSession
import org.greenrobot.greendao.AbstractDao
import org.greenrobot.greendao.Property
import org.greenrobot.greendao.query.DeleteQuery
import org.greenrobot.greendao.query.Query
import org.greenrobot.greendao.query.WhereCondition
import java.util.*

open class BaseDaoManager<T, K> : BaseSessionManager {

    private var mDao: AbstractDao<T, K>? = null

    constructor(managerSession: ManagerSession, abstractDao: AbstractDao<T, K>) : super(managerSession) {
        mDao = abstractDao
    }

    public fun getDao(): AbstractDao<T, K> {
        return mDao!!
    }

    public fun fullInsertOrReplace(entity: T): Long = mDao!!.insertOrReplace(entity)

    public fun fullInsertOrReplaceInTx(vararg entities: T) {
        if (entities == null || entities.size == 0) {
            return
        }
        fullInsertOrReplaceInTx(Arrays.asList(entities) as Iterable<T>)
    }

    public fun fullInsertOrReplaceInTx(entity: Iterable<T>) {
        mDao!!.database.beginTransaction()
        try {
            for (item in entity) {
                fullInsertOrReplace(item)
            }
            mDao!!.database.setTransactionSuccessful()
        } finally {
            mDao!!.database.endTransaction()
        }
    }

    public fun fullDelete(entity: T) {
        mDao!!.delete(entity)
    }

    public fun loadByProperty(property: Property, value: Object): List<T>? {
        var queryBuilder: Query<T> = mDao!!.queryBuilder().where(property.eq(value)).build()
        return queryBuilder.list()
    }

    public fun loadByCondition(cond: WhereCondition, vararg condMore: WhereCondition): List<T> {
        var queryBuilder: Query<T> = mDao!!.queryBuilder().where(cond, *condMore).build()
        return queryBuilder.list()
    }

    public fun deleteByProperty(property: Property, value: Object) {
        var deleteQuery: DeleteQuery<T> = mDao!!.queryBuilder().where(property.eq(value)).buildDelete()
        deleteQuery.executeDeleteWithoutDetachingEntities()
    }
}