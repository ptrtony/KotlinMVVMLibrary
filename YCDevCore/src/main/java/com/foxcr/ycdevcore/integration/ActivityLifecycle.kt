package com.foxcr.ycdevcore.integration

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ActivityLifecycle(internal val application: Application): Application.ActivityLifecycleCallbacks {

    private val mAppManager = AppManager.instance

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        AppManager.instance.addActivity(activity)
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        mAppManager.setCurrentActivity(activity)
    }

    override fun onActivityStopped(activity: Activity) {
        if (mAppManager.getCurrentActivity() === activity) {
            mAppManager.setCurrentActivity(null)
        }
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        AppManager.instance.removeActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
    }

}