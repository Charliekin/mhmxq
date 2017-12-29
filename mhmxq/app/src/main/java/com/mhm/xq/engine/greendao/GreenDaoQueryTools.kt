package com.mhm.xq.engine.greendao

import com.mhm.xq.utils.LogUtil
import org.greenrobot.greendao.AbstractDao
import org.greenrobot.greendao.Property
import org.greenrobot.greendao.identityscope.IdentityScope
import org.greenrobot.greendao.query.WhereCondition

class GreenDaoQueryTools {
    companion object {
        fun <T, K> deleteAllData(dao: AbstractDao<T, K>, filterConditions: ArrayList<WhereCondition>) {
            var builder = dao.queryBuilder()
            if (filterConditions.isNotEmpty()) {
                for (item in filterConditions) {
                    builder = builder.where(item)
                }
            }
            builder.buildDelete().executeDeleteWithoutDetachingEntities()
            clearIdentityScope(dao)
        }

        private fun <T, K> clearIdentityScope(dao: AbstractDao<T, K>) {
            try {
                var f = AbstractDao::class.java.getDeclaredField("identityScope")
                f.isAccessible = true
                var o = f.get(dao)
                if (o != null) {
                    var identityScope = o as IdentityScope<*, *>
                    identityScope.clear()
                    LogUtil.i("clear identity success")
                }
            } catch (e: NoSuchFieldException) {
                throw RuntimeException("clear identity fail")
            } catch (e: IllegalAccessException) {
                throw RuntimeException("clear identity fail")
            }
        }

        /**
         * 读取AbstractDao全部数据,并按manualAddCtProperty倒序排序
         */
        fun <T, K> loadFullPageData(dao: AbstractDao<T, K>, manualAddCtProperty: Property?, filterConditions: ArrayList<WhereCondition>?): ArrayList<T> {
            var builder = dao.queryBuilder()
            if (filterConditions!!.isNotEmpty()) {
                for (item in filterConditions) {
                    builder = builder.where(item)
                }
            }
            return builder.orderDesc(manualAddCtProperty).orderRaw(" rowid asc ").list() as ArrayList<T>
        }
    }
}