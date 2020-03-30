package com.foxcr.ycdevcomponent.utils

import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.google.gson.Gson
import com.foxcr.ycdevcomponent.core.RxPreferenceConverter
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import org.kodein.di.generic.instance
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object RxPreferences{

    fun setBoolean(key: String,value: Boolean){
        getBoolean(key).set(value)
    }

    fun setFloat(key: String,value: Float){
        getFloat(key).set(value)
    }

    fun setInteger(key: String,value: Int){
        getInteger(key).set(value)
    }

    fun setLong(key: String,value: Long){
        getLong(key).set(value)
    }

    fun setString(key: String,value: String){
        getString(key).set(value)
    }

    inline fun <reified T> setObject(key: String,value: T){
        getObject<T>(key).set(value)
    }

    inline fun <reified T> setArrayObject(key: String,value: List<T>){
        getArrayObject<T>(key).set(value)
    }

    fun getBoolean(key: String,defaultValue: Boolean=false): Preference<Boolean>{
        val rxPreferences by obtainAppKodeinAware().instance<RxSharedPreferences>()
        return rxPreferences.getBoolean(key,defaultValue)
    }

    fun getFloat(key: String,defaultValue :Float=0f): Preference<Float>{
        val rxPreferences by obtainAppKodeinAware().instance<RxSharedPreferences>()
        return rxPreferences.getFloat(key,defaultValue)
    }

    fun getInteger(key: String, defaultValue: Int=0): Preference<Int>{
        val rxPreferences by obtainAppKodeinAware().instance<RxSharedPreferences>()
        return rxPreferences.getInteger(key,defaultValue)
    }

    fun getLong(key: String, defaultValue: Long=0L): Preference<Long>{
        val rxPreferences by obtainAppKodeinAware().instance<RxSharedPreferences>()
        return rxPreferences.getLong(key,defaultValue)
    }

    fun getString(key: String, defaultValue: String=""): Preference<String>{
        val rxPreferences by obtainAppKodeinAware().instance<RxSharedPreferences>()
        return rxPreferences.getString(key,defaultValue)
    }

    inline fun <reified T> getObject(key: String): Preference<T>{
        val rxPreferences by obtainAppKodeinAware().instance<RxSharedPreferences>()
        return rxPreferences.getObjectEx(key)
    }

    inline fun <reified T> getArrayObject(key: String): Preference<List<T>>{
        val rxPreferences by obtainAppKodeinAware().instance<RxSharedPreferences>()
        return rxPreferences.getArrayObjectEx(key)
    }
}

inline fun <reified T> RxSharedPreferences.getObjectEx(key:String,defaultValue:T?=null): Preference<T>{
    val rxPreferences by obtainAppKodeinAware().instance<RxSharedPreferences>()
    val gson by obtainAppKodeinAware().instance<Gson>()
    val default = defaultValue?:gson.fromJson<T>("{}",T::class.java)
    return rxPreferences.getObject(key, default, RxPreferenceConverter(T::class.java))
}

inline fun <reified T> RxSharedPreferences.getArrayObjectEx(key:String, defaultValue:List<T>?=null): Preference<List<T>>{
    val rxPreferences by obtainAppKodeinAware().instance<RxSharedPreferences>()
    val gson by obtainAppKodeinAware().instance<Gson>()
    val type = object : ParameterizedType{
        override fun getRawType(): Type {
            return List::class.java
        }

        override fun getOwnerType(): Type? {
            return null
        }

        override fun getActualTypeArguments(): Array<Type> {
            return arrayOf(T::class.java)
        }

    }
    val default = defaultValue?:gson.fromJson<List<T>>("[]",type)
    return rxPreferences.getObject<List<T>>(key, default, RxPreferenceConverter(type))
}