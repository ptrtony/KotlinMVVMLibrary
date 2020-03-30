package com.foxcr.ycdevcomponent.core

import android.content.Context

interface AdvanceConfigModule {
    fun applyOptions(context: Context, builder: DefaultConfigModule.Builder)
}