package com.foxcr.ycdevcomponent.base.statusbar

import android.app.Activity
import androidx.fragment.app.Fragment

interface IStatusBarDelegate {
    fun onCreate(activity: Activity,configBean: StatusBarConfigBean)
    fun onCreate(fragment: Fragment,configBean: StatusBarConfigBean)
    fun onDestroy(activity: Activity)
    fun onDestroy(fragment: Fragment)
}