package com.foxcr.ycdevcomponent.core


interface IRetrofitUrlManagerService {
    fun putDomain(domainName:String, domainUrl: String)
    fun addUrlParserListener(domainName: String, listener: OnUrlParserListener)
    fun removeUrlParserListener(domainName: String)
}