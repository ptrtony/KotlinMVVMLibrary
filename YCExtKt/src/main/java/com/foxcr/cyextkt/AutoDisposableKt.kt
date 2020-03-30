package com.foxcr.cyextkt

import androidx.lifecycle.Lifecycle
import com.uber.autodispose.*
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.*
import io.reactivex.parallel.ParallelFlowable
import org.reactivestreams.Subscriber

fun <T> Observable<T>.autoDisposable(lifecycle: Lifecycle, untilEvent : Lifecycle.Event): ObservableSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycle,untilEvent)))
}

fun <T> Flowable<T>.autoDisposable(lifecycle: Lifecycle, untilEvent : Lifecycle.Event): FlowableSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycle,untilEvent)))
}

fun <T> ParallelFlowable<T>.autoDisposable(lifecycle: Lifecycle, untilEvent : Lifecycle.Event): ParallelFlowableSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycle,untilEvent)))
}

fun <T> Maybe<T>.autoDisposable(lifecycle: Lifecycle, untilEvent : Lifecycle.Event): MaybeSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycle,untilEvent)))
}

fun <T> Single<T>.autoDisposable(lifecycle: Lifecycle, untilEvent : Lifecycle.Event): SingleSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycle,untilEvent)))
}

fun <T> Completable.autoDisposable(lifecycle: Lifecycle, untilEvent : Lifecycle.Event): CompletableSubscribeProxy {
    return this.`as`(AutoDispose.autoDisposable<T>(AndroidLifecycleScopeProvider.from(lifecycle,untilEvent)))
}


fun <T> Observable<T>.autoDisposable(scopeProvider :ScopeProvider): ObservableSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(scopeProvider))
}

fun <T> Flowable<T>.autoDisposable(scopeProvider :ScopeProvider): FlowableSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(scopeProvider))
}

fun <T> ParallelFlowable<T>.autoDisposable(scopeProvider :ScopeProvider): ParallelFlowableSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(scopeProvider))
}

fun <T> Maybe<T>.autoDisposable(scopeProvider :ScopeProvider): MaybeSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(scopeProvider))
}

fun <T> Single<T>.autoDisposable(scopeProvider :ScopeProvider): SingleSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(scopeProvider))
}

fun <T> Completable.autoDisposable(scopeProvider :ScopeProvider): CompletableSubscribeProxy {
    return this.`as`(AutoDispose.autoDisposable<T>(scopeProvider))
}

inline fun <T> Observable<T>.subscribeEx(scopeProvider :ScopeProvider,block:()->Observer<T>){
    this.`as`(AutoDispose.autoDisposable(scopeProvider)).subscribe(block())
}

inline fun <T> Flowable<T>.subscribeEx(scopeProvider :ScopeProvider,block:()-> Subscriber<T>){
    this.`as`(AutoDispose.autoDisposable(scopeProvider)).subscribe(block())
}

inline fun <T> ParallelFlowable<T>.subscribeEx(scopeProvider :ScopeProvider,block:()->Array<Subscriber<T>>){
    this.`as`(AutoDispose.autoDisposable(scopeProvider)).subscribe(block())
}

inline fun <T> Maybe<T>.subscribeEx(scopeProvider :ScopeProvider,block:()->MaybeObserver<T>){
    this.`as`(AutoDispose.autoDisposable(scopeProvider)).subscribe(block())
}

inline fun <T> Single<T>.subscribeEx(scopeProvider :ScopeProvider,block:()->SingleObserver<T>){
    this.`as`(AutoDispose.autoDisposable(scopeProvider)).subscribe(block())
}

inline fun <T> Completable.subscribeEx(scopeProvider :ScopeProvider,block:()->CompletableObserver){
    this.`as`(AutoDispose.autoDisposable<T>(scopeProvider)).subscribe(block())
}





