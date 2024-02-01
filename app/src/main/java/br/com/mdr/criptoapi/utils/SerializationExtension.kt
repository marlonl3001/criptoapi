package br.com.mdr.criptoapi.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import timber.log.Timber

object SerializationExtension {
    val gson: Gson = GsonBuilder().create()

    inline fun <reified T> String.jsonToObject(): T? {
        return try {
            gson.fromJson(this, T::class.java)
        } catch (e: JsonSyntaxException) {
            e.message?.let { Timber.e(it) }
            null
        }
    }

    inline fun <reified T> String.jsonToListObject(): List<T>? {
        return try {
            gson.fromJson(this, object : TypeToken<List<T>>() {}.type)
        } catch (e: JsonSyntaxException) {
            e.message?.let { Timber.e(it) }
            null
        }
    }
}
