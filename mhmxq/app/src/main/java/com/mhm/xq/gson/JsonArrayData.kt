package com.mhm.xq.gson

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive

class JsonArrayData {
    private val mJsonElementList: ArrayList<JsonElement> = ArrayList<JsonElement>()

    public fun getJsonElementList(): ArrayList<JsonElement> {
        return mJsonElementList
    }

    public constructor() {

    }

    public constructor(jsonArray: JsonArray) {
        if (jsonArray == null) {
            return
        }
        for (items in jsonArray) {
            mJsonElementList.add(items)
        }
    }

    public constructor(dataList: List<String>) {
        mJsonElementList.clear();
        if (dataList != null && dataList.size > 0) {
            for (items in dataList) {
                mJsonElementList.add(JsonPrimitive(items))
            }
        }
    }

    public fun getAsJsonString(): String {
        val jsonArray: JsonArray = JsonArray()
        for (items in mJsonElementList) {
            jsonArray.add(items)
        }
        return jsonArray.toString()
    }

    public fun getAsStringList(): List<String> {
        val lstData: ArrayList<String> = ArrayList<String>();
        for (items in mJsonElementList) {
            lstData.add(items.toString())
        }
        return lstData;
    }
}