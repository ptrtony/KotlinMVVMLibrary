package com.foxcr.ycdevcomponent.base

import android.content.Context
import android.util.TypedValue
import com.foxcr.cyextkt.dp
import com.foxcr.ycdevcomponent.R
import com.jakewharton.rxrelay2.PublishRelay
import timber.log.Timber

open class AppToolbarDelegate(context: Context) {

    private var isDarkStatusBar :Boolean = true
    private var toolbarBackground:Int = R.color.white
    private var headlerBackground:Int = R.color.white
    private var toolbarTitleText:String = ""
    private var toolbarTitleTextColor:Int = R.color.white
    private var toolbarRightText:String = ""
    private var toolbarRightTextColor:Int = R.color.white
    private var toolbarNavIcon:Int = R.mipmap.back_white
    private var toolbarRightIcon:Int = 0
    private var toolbarDivide:Boolean = false
    protected val mContext = context.applicationContext


    val rightIconClickObserver by lazy {
        PublishRelay.create<Unit>().toSerialized()
    }

    val rightTitleClickObserver by lazy {
        PublishRelay.create<Unit>().toSerialized()
    }

    open fun isDarkStatusBar():Boolean{
        return isDarkStatusBar
    }

    private fun setIsDarkStatusBar(isDarkStatusBar:Boolean){
        this.isDarkStatusBar = isDarkStatusBar
    }

    open fun provideHeaderBackground(): Int {
        return headlerBackground
    }

    private fun setProvideHeaderBackground(headlerBackground:Int){
        this.headlerBackground = headlerBackground
    }



    open fun provideToolbarBackground(): Int {
        return toolbarBackground
    }

    private fun setProvideToolbarBackground(toolbarBackground:Int){
        this.toolbarBackground = toolbarBackground
    }


    open fun provideToolbarTitleText(): String {
        return toolbarTitleText
    }

    open fun setToolbarTitleText(toolbarTitleText:String){
        this.toolbarTitleText = toolbarTitleText
    }

    open fun provideToolbarTitleTextColor(): Int {
        return mContext.resources.getColor(toolbarTitleTextColor)
    }

    private fun setToolbarTitleTextColor(toolbarTitleTextColor:Int){
        this.toolbarTitleTextColor = toolbarTitleTextColor
    }

    open fun provideToolbarRightTvColor(): Int {
        return mContext.resources.getColor(toolbarRightTextColor)
    }

    private fun setToolbarRightTvColor(toolbarRightTextColor:Int){
        this.toolbarRightTextColor = toolbarRightTextColor
    }

    open fun provideToolbarNavIcon(): Int {
        return toolbarNavIcon
    }

    private fun setToolbarNavIcon(toolbarNavIcon:Int){
        this.toolbarNavIcon = toolbarNavIcon
    }

    open fun provideToolbarRightIcon(): Int {
        return toolbarRightIcon
    }

    open fun setToolbarRightIcon(toolbarRightIcon:Int){
        this.toolbarRightIcon = toolbarRightIcon
    }

    open fun provideToolbarRightTv(): String {
        return toolbarRightText
    }

    open fun setToolbarRightTv(toolbarRightText:String){
        this.toolbarRightText = toolbarRightText
    }


    open fun provideToolBarDivide(): Boolean{
        return toolbarDivide
    }

    private fun setToolbarDivide(toolbarDivide:Boolean){
        this.toolbarDivide = toolbarDivide
    }


    open fun setToolbarBackgroundWhiteColor(isToolbarDivide:Boolean){
        setIsDarkStatusBar(true)
        setProvideHeaderBackground(R.color.white)
        setProvideToolbarBackground(R.color.white)
        setToolbarTitleTextColor(R.color.main_text_color)
        setToolbarRightTvColor(R.color.main_text_color)
        setToolbarNavIcon(R.mipmap.back_black)
        setToolbarDivide(isToolbarDivide)
    }

    open fun setToolbarOtherBackgroundColor(color:Int,isToolbarDivide:Boolean){
        setIsDarkStatusBar(false)
        setProvideHeaderBackground(color)
        setProvideToolbarBackground(color)
        setToolbarTitleTextColor(R.color.white)
        setToolbarRightTvColor(R.color.white)
        setToolbarNavIcon(R.mipmap.back_white)
        setToolbarDivide(isToolbarDivide)
    }



    open fun provideToolbarHeight():Int{
        val tv = TypedValue()
        if (mContext.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            val actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, mContext.resources.displayMetrics)
            Timber.d("actionBarHeight=$actionBarHeight")
            return actionBarHeight
        }
        return 56.dp
    }
}