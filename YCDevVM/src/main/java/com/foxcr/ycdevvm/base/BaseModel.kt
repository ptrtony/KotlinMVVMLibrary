package com.foxcr.ycdevvm.base

import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

open class BaseModel : KodeinAware {
    override val kodein: Kodein = obtainAppKodeinAware().kodein
}