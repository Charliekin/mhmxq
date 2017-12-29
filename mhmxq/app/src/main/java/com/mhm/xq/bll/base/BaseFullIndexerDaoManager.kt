package com.mhm.xq.bll.base

import android.content.ContentValues
import com.mhm.xq.bll.global.ManagerSession
import com.mhm.xq.engine.greendao.GreenDaoQueryTools
import org.greenrobot.greendao.AbstractDao
import org.greenrobot.greendao.Property
import org.greenrobot.greendao.query.WhereCondition

/**
 * 适用于单页数据
 *
 * @param <TlEntity>
 * @param <TlKEY>
 * @param <REntity>
 * @param <RKEY>
 */
abstract class BaseFullIndexerDaoManager<TlEntity, TlKEY, REntity, RKEY> : BaseDaoManager<TlEntity, TlKEY> {


    private var mPropertyMaualAddCt: Property? = null
    private var mRealBaseDaoManager: BaseDaoManager<REntity, RKEY>? = null

    constructor(managerSession: ManagerSession, dao: AbstractDao<TlEntity, TlKEY>,
                realBaseDaoManager: BaseDaoManager<REntity, RKEY>,
                propertyMaualAddCt: Property) :
            super(managerSession, dao) {
        mPropertyMaualAddCt = propertyMaualAddCt
        mRealBaseDaoManager = realBaseDaoManager
    }

    /**
     * 写入一组数据
     *
     * @param realDataList     直接的数据
     * @param filterConditions 过滤条件
     * @param manualAddCt      数据的添加时间.  时间值一般传入当前的时间或是0,在读取数据时,是先按时间倒排，再按row id正排读取
     * @param extra            额外值
     */
    public fun fullInsertOrReplaceInTx(realDataList: ArrayList<REntity>,
                                       manualAddCt: Long,
                                       extra: ContentValues?,
                                       filterConditions: ArrayList<WhereCondition>?) {
        getDao().database.beginTransaction()
        try {
            mRealBaseDaoManager!!.fullInsertOrReplaceInTx(realDataList)
            GreenDaoQueryTools.deleteAllData(getDao(), filterConditions!!)
            var timelineDataList = ArrayList<TlEntity>()
            if (realDataList.isNotEmpty()) {
                realDataList.mapTo(timelineDataList) { createNewPageEntity(it, manualAddCt, extra) }
            }
            if (timelineDataList.isNotEmpty()) {
                getDao().insertOrReplaceInTx(timelineDataList)
            }
            getDao().database.setTransactionSuccessful()
        } finally {
            getDao().database.endTransaction()
        }
    }

    fun fullInsertOrReplaceInTx(realDataList: ArrayList<REntity>,
                                manualAddCt: Long,
                                filterConditions: ArrayList<WhereCondition>) {
        fullInsertOrReplaceInTx(realDataList, manualAddCt, null, filterConditions)
    }

    fun fullInsertOrReplaceInTx(realDataList: ArrayList<REntity>) {
        fullInsertOrReplaceInTx(realDataList, 0, null, null)
    }

    /**
     * 读取数据
     *
     * @param filterConditions 过滤条件
     * @return
     */
    fun loadFullPageData(filterConditions: ArrayList<WhereCondition>?): ArrayList<REntity> {
        var timelineList = GreenDaoQueryTools.loadFullPageData(getDao(), mPropertyMaualAddCt, filterConditions)
        var resultList = ArrayList<REntity>()
        if (timelineList.isNotEmpty()) {
            for (item in timelineList) {
                resultList.add(getRealEntity(item))
            }
        }
        return resultList
    }

    fun loadFullPageData(): ArrayList<REntity> {
        return loadFullPageData(null)
    }

    /**
     * 写入一条数据
     *
     * @param realData    真实的数据
     * @param manualAddCt 写入时间
     * @param extra       额外值
     */
    fun manualFullInsertOrReplace(realData: REntity, manualAddCt: Long, extra: ContentValues?) {
        var pageData = createNewPageEntity(realData, manualAddCt, extra)
        getDao().delete(pageData)
        getDao().insertOrReplace(pageData)
    }

    fun manualFullInsertOrReplace(realData: REntity, extra: ContentValues?) {
        manualFullInsertOrReplace(realData, System.currentTimeMillis(), extra)
    }

    fun manualFullInsertOrReplace(realData: REntity) {
        manualFullInsertOrReplace(realData, System.currentTimeMillis(), null)
    }

    fun deleteByRealEntityKey(realEntityKey: RKEY, extra: ContentValues?) {
        getDao().deleteByKey(getFullPageEntityKey(realEntityKey, extra))
    }

    fun deleteByRealEntityKey(realEntityKey: RKEY) {
        getDao().deleteByKey(getFullPageEntityKey(realEntityKey, null))
    }

    fun deleteByRealEntityKeyInTx(realEntityKeyList: ArrayList<RKEY>, extra: ContentValues?) {
        getDao().database.beginTransaction()
        try {
            if (realEntityKeyList.isNotEmpty()) {
                for (item in realEntityKeyList) {
                    deleteByRealEntityKey(item, extra)
                }
            }
            getDao().database.setTransactionSuccessful()
        } finally {
            getDao().database.endTransaction()
        }
    }

    fun deleteByRealEntityKeyInTx(realEntityKeyList: ArrayList<RKEY>) {
        deleteByRealEntityKeyInTx(realEntityKeyList, null)
    }

    fun deleteByRealEntity(realEntity: REntity, extra: ContentValues?) {
        deleteByRealEntityKey(getRealEntityKey(realEntity), extra)
    }

    fun deleteByRealEntity(realEntity: REntity) {
        deleteByRealEntity(realEntity, null)
    }

    fun deleteByRealEntityInTx(realDataList: ArrayList<REntity>, extra: ContentValues?) {
        getDao().database.beginTransaction()
        try {
            if (realDataList.isNotEmpty()) {
                for (item in realDataList) {
                    deleteByRealEntity(item, extra)
                }
            }
            getDao().database.setTransactionSuccessful()
        } finally {
            getDao().database.endTransaction()
        }
    }

    fun deleteByRealEntityInTx(realEntityList: ArrayList<REntity>) {
        deleteByRealEntityInTx(realEntityList, null)
    }

    abstract fun getRealEntityKey(realEntity: REntity): RKEY

    abstract fun createNewPageEntity(realEntity: REntity, manualAddCt: Long, extra: ContentValues?): TlEntity

    abstract fun getFullPageEntityKey(realEntity: RKEY, extra: ContentValues?): TlKEY

    abstract fun getRealEntity(timelineEntity: TlEntity): REntity
}