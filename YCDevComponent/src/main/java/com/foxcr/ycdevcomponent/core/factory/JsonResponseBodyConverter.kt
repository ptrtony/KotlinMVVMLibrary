package com.foxcr.ycdevcomponent.core.factory

import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Converter

class JsonResponseBodyConverter<T>(private val gson : Gson, private val adapter :TypeAdapter<T>) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T {
        val string = value.string()
        val json = JSON.parseObject(string)
        val jsonReader = gson.newJsonReader(ResponseBody.create(MediaType.parse("application/json; charset=utf-8"),JSON.toJSONString(json)).charStream())
        value.use {
            val result = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            return result
        }
    }
}