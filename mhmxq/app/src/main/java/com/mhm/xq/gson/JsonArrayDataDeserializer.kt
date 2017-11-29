package com.mhm.xq.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class JsonArrayDataDeserializer : JsonDeserializer<JsonArrayData> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): JsonArrayData? {
        return if (json == null || json.isJsonNull) {
            null
        } else {
            JsonArrayData(json.asJsonArray)
        }
    }

}