package com.foxcr.ycdevcomponent.module

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
@author cjq
@Date 2020/5/19
@Time 下午2:43
@Describe:
 */

const val INITIAL_MODEL = "initial_model"

val initialModel = Kodein.Module(INITIAL_MODEL){
    bind<UserModel>() with singleton {
        UserModel()
    }
}
