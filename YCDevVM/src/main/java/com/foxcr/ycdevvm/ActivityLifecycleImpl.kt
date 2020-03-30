package com.foxcr.ycdevvm

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.foxcr.ycdevvm.base.ActivityDelegate
import com.foxcr.ycdevvm.base.IActivity

class ActivityLifecycleImpl : Application.ActivityLifecycleCallbacks{

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is IActivity && activity is FragmentActivity) {
            val delegate = ActivityDelegate(activity)
            activity.setActivityDelegate(delegate)
            delegate.layoutId = activity.layout()
        }
        registerFragmentCallbacks(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    private fun registerFragmentCallbacks(activity: Activity) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycleImpl(), true)
        }
    }
}