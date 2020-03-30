package com.foxcr.ycdevcomponent.core

import okhttp3.HttpUrl

interface OnUrlParserListener {
    fun parseUrl(domainUrl: HttpUrl?, url: HttpUrl): HttpUrl?
}