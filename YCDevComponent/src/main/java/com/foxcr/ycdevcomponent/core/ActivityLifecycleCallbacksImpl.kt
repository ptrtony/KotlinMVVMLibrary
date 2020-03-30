package com.foxcr.ycdevcomponent.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class ActivityLifecycleCallbacksImpl: Application.ActivityLifecycleCallbacks{

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
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
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacksImpl(), true)
        }
    }
}