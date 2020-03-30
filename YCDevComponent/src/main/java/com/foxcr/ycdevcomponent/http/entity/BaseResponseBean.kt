package com.foxcr.ycdevcomponent.http.entity

class BaseResponseBean<T>:ResponseBean<T>() {
    var code: Int=0
    var message: String=""
    var success: Boolean=false
}