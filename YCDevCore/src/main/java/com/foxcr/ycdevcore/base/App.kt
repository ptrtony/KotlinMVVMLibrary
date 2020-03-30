package com.foxcr.ycdevcore.base

import com.foxcr.ycdevcore.di.controller.AppController


interface App {
    fun getAppController(): AppController
}