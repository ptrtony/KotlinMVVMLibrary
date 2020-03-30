package com.foxcr.ycdevvm.base

import org.kodein.di.KodeinAware

interface IActivity : KodeinAware {
    fun setActivityDelegate(delegate: ActivityDelegate)
    fun layout():Int
}