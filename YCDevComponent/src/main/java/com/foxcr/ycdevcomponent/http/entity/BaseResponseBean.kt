package com.foxcr.ycdevcomponent.http.entity

class BaseResponseBean<T>:ResponseBean<T>() {
    var errorCode: Int=0
    var errorMsg: String=""
    var success: Boolean=false
}