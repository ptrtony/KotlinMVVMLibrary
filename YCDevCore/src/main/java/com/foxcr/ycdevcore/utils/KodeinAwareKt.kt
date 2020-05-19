package com.foxcr.ycdevcore.utils

import com.foxcr.ycdevcore.base.delegate.AppDelegate
import org.kodein.di.KodeinAware


fun Any.obtainAppKodeinAware(): KodeinAware {
    return (AppDelegate.instance)
}