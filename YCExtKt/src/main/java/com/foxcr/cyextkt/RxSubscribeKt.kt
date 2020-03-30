package com.foxcr.cyextkt

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun <T> Observable<T>.observeOnUI(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.observeOnUI(): Flowable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}


fun <T> Maybe<T>.observeOnUI(): Maybe<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.observeOnUI(): Single<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}

fun <T> Completable.observeOnUI(): Completable {
    return this.observeOn(AndroidSchedulers.mainThread())
}


fun <T> Observable<T>.ioToUI(): Observable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.ioToUI(): Flowable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}


fun <T> Maybe<T>.ioToUI(): Maybe<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.ioToUI(): Single<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Completable.ioToUI(): Completable {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}