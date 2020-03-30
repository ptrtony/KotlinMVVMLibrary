package com.foxcr.ycdevcore.integration.rxsubscriber

import android.content.Context
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class RxProgressObservable(val msg:String,val cancelable:Boolean){
    protected val cancelObservable = PublishSubject.create<Boolean>()

    internal fun getCancelObservable():Observable<Boolean>{
        return cancelObservable
    }

    internal fun showProgress(context: Context):Observable<Boolean>{
        show(context,msg)
        return getCancelObservable()
    }

    abstract fun show(context: Context,msg:String)
    abstract fun isShowing():Boolean
    abstract fun dismiss()
}