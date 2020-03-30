package com.foxcr.ycdevcore.integration.http

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class RequestInterceptor(private val mHandler: GlobalHttpHandler): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse: Response
        try {
            originalResponse = chain.proceed(request)
        } catch (e: Exception) {
            Timber.w("Http Error: $e")
            throw e
        }
        return mHandler.onHttpResultResponse(chain, originalResponse)
    }
}
