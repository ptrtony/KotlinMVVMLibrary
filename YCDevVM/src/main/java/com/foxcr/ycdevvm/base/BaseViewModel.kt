package com.foxcr.ycdevvm.base

import android.app.Application
import android.content.res.Resources
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.scope
import org.kodein.di.Kodein

open class BaseViewModel(application: Application): AndroidViewModel(application),IBaseViewModel {
    override val kodein: Kodein = obtainAppKodeinAware().kodein
    protected lateinit var mLifecycleOwner: LifecycleOwner
    private val cacheDataMap = HashMap<String,Any>()
    internal val finishActivityLiveData:MutableLiveData<Boolean> = MutableLiveData()

    protected val resources: Resources by lazy {
        getApplication<Application>().resources
    }

    protected val scopeProvider: ScopeProvider by lazy {
        mLifecycleOwner.scope(Lifecycle.Event.ON_DESTROY)
    }

    protected fun finishActivity(){
        finishActivityLiveData.value = true
    }

    protected fun getString(@StringRes id:Int):String{
        return resources.getString(id)
    }

    protected fun getColor(@ColorRes id:Int):Int{
        return resources.getColor(id)
    }

    fun saveData(key:String,data:Any){
        cacheDataMap[key] = data
    }

    fun <T> getData(key: String):T{
        return cacheDataMap[key] as T
    }

    fun removeData(key:String){
        cacheDataMap.remove(key)
    }

    override fun onCleared() {
        super.onCleared()
        cacheDataMap.clear()
    }

    override fun onCreate(owner: LifecycleOwner) {
        mLifecycleOwner = owner
    }

    override fun onDestroy() {
        mLifecycleOwner.lifecycle.removeObserver(this)
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    companion object {
        const val ARGUMENTS = "arguments"
        const val INTENT = "intent"
    }
}