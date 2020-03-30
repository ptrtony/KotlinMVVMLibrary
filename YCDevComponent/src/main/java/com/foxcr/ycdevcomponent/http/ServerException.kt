package com.foxcr.ycdevcomponent.http

class ServerException(msg: String,val code:Int=0) : Exception(msg)