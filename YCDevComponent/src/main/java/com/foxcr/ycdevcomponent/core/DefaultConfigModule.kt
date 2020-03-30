package com.foxcr.ycdevcomponent.core

import com.foxcr.ycdevcomponent.http.ResponseTransformerStrategy
import com.foxcr.ycdevcomponent.http.ResponseTransformerStrategyImpl

class DefaultConfigModule private constructor(builder: Builder){
    private var enableLeakCanary: Boolean = false
    private var enableDoraemonKit: Boolean = false
    private var enableOkHttpLog: Boolean = false
    private var responseTransformerStrategy: ResponseTransformerStrategy? =null

    init {
        this.enableLeakCanary = builder.enableLeakCanary
        this.enableDoraemonKit = builder.enableDoraemonKit
        this.enableOkHttpLog = builder.enableOkHttpLog
        this.responseTransformerStrategy = builder.responseTransformerStrategy
    }

    fun enableLeakCanary(): Boolean {
        return enableLeakCanary
    }

    fun enableDoraemonKit(): Boolean {
        return enableDoraemonKit
    }

    fun enableOkHttpLog(): Boolean {
        return enableOkHttpLog
    }

    fun provideResponseTransformerStrategy():ResponseTransformerStrategy{
        return responseTransformerStrategy?: ResponseTransformerStrategyImpl()
    }

    class Builder internal constructor(){
        internal var enableLeakCanary: Boolean = false
        internal var enableDoraemonKit: Boolean = false
        internal var enableOkHttpLog: Boolean = false
        internal var responseTransformerStrategy: ResponseTransformerStrategy? =null

        fun enableLeakCanary(enableLeakCanary: Boolean): Builder {
            this.enableLeakCanary = enableLeakCanary
            return this
        }

        fun enableDoraemonKit(enableDoraemonKit: Boolean): Builder {
            this.enableDoraemonKit = enableDoraemonKit
            return this
        }

        fun enableOkHttpLog(enableOkHttpLog: Boolean): Builder {
            this.enableOkHttpLog = enableOkHttpLog
            return this
        }

        fun setResponseTransformerStrategy(responseTransformerStrategy: ResponseTransformerStrategy?): Builder {
            this.responseTransformerStrategy = responseTransformerStrategy
            return this
        }

        fun build(): DefaultConfigModule {
            return DefaultConfigModule(this)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }
}