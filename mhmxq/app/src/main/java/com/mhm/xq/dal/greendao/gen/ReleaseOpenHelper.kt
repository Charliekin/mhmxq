package com.mhm.xq.dal.greendao.gen

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.greenrobot.greendao.database.Database

class ReleaseOpenHelper : DaoMaster.OpenHelper {
    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?) :
            super(context, name, factory)

    /**
     * 用于数据库升级时需要的操作
     */
    override fun onUpgrade(db: Database?, oldVersion: Int, newVersion: Int) {
        for (i in oldVersion..newVersion) {
            when (i) {
                1 -> {

                }
            }
        }
    }
}