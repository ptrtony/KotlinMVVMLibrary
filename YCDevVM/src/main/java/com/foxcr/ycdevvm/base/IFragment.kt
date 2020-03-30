package com.foxcr.ycdevvm.base

import org.kodein.di.KodeinAware

interface IFragment: KodeinAware {
    fun setFragmentDelegate(delegate: FragmentDelegate)
    fun layout():Int
}