package com.foxcr.ycdevcore.integration.http

import okhttp3.HttpUrl

interface BaseUrl {
    fun url(): HttpUrl?
}