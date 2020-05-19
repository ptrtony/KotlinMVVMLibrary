package com.foxcr.ycdevcomponent.core

import com.foxcr.ycdevcore.integration.http.GlobalHttpHandler
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
@author cjq
@Date 2020/5/19
@Time 上午11:30
@Describe:
 */
class GloableHttpHeaderImpl : GlobalHttpHandler {
    override fun onHttpRequestBefore(chain: Interceptor.Chain?, request: Request): Request {
        return request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("charset", "utf-8")
//            .addHeader(Constants.COOKIE,"loginUserName=${loginUsername}")
//            .addHeader(Constants.COOKIE,"loginUserPassword=${loginUserPassword}")
            .build()
    }

    override fun onHttpResultResponse(chain: Interceptor.Chain?, response: Response?): Response {
        return response!!
    }
}