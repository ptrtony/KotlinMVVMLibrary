package com.foxcr.ycdevcomponent.core

import me.jessyan.retrofiturlmanager.RetrofitUrlManager

class RetrofitUrlManagerServiceImpl :IRetrofitUrlManagerService{
    val urlParseMap:HashMap<String, OnUrlParserListener> = HashMap()

    override fun putDomain(domainName: String, domainUrl: String){
        RetrofitUrlManager.getInstance().putDomain(domainName,domainUrl)
    }

    override fun addUrlParserListener(domainName: String, listener: OnUrlParserListener) {
        RetrofitUrlManager.getInstance().fetchDomain(domainName)?.apply {
            urlParseMap[this.toString()] = listener
        }
    }

    override fun removeUrlParserListener(domainName: String) {
        RetrofitUrlManager.getInstance().fetchDomain(domainName)?.apply {
            urlParseMap.remove(this.toString())
        }
    }

}
