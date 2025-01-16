package com.tta.core_utils.extension

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import com.google.gson.Gson

inline fun <reified T : Parcelable> Intent.customParcelize(key: String) = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableExtra(key, T::class.java)
    else -> getParcelableExtra(key)
}

inline fun <reified T : Parcelable> Intent.customArrayListParcelize(key: String) = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableArrayListExtra(key, T::class.java)
    else -> getParcelableArrayListExtra(key)
}

inline fun <reified T : Parcelable> Bundle.customParcelize(key: String) = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> getParcelable(key)
}

inline fun <reified T : Parcelable> Bundle.customArrayListParcelize(key: String) = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableArrayList(key, T::class.java)
    else -> getParcelableArrayList(key)
}

fun Any?.toJson(): String? {
    return try {
        Gson().toJson(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun <T> String?.fromJson(dataClass: Class<T>): T? {
    return try {
        Gson().fromJson(this, dataClass)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun <T> String?.fromListJson(dataClass: Class<Array<T>>): List<T> {
    return try {
        return Gson().fromJson(this, dataClass).toList()
    } catch (e: Exception) {
        e.printStackTrace()
        listOf()
    }
}

fun <T> List<T>.toArrList(): ArrayList<T> {
    val list = arrayListOf<T>()
    list.addAll(this)
    return list
}