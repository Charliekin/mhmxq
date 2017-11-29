package com.mhm.xq.net.http.client;


import com.google.gson.TypeAdapter;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;
    private final Type type;

    GsonResponseBodyConverter(Type type, TypeAdapter<T> adapter) {
        this.type = type;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            if ($Gson$Types.equals(type, TypeToken.get(String.class).getType())) {
                return (T) value.string();
            }
            return adapter.fromJson(value.charStream());
        } finally {
            value.close();
        }
    }
}