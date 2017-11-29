package com.mhm.xq.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MyGson {

    companion object {
        public fun build(): Gson {
            val gsonBuilder: GsonBuilder = GsonBuilder()
            gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss").create()
            gsonBuilder.registerTypeAdapter(JsonArrayData::class.java, JsonArrayDataDeserializer())
            val gson: Gson = gsonBuilder.create()
            return gson
        }
    }
}