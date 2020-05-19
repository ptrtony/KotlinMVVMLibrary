package com.foxcr.ycdevcomponent.base.statusbar

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.foxcr.cyextkt.getStatusBarHeight
import com.foxcr.cyextkt.quickConfig
import com.foxcr.ycdevcomponent.R
import com.foxcr.ycdevvm.base.EmptyActivityLifecycleCallbacks

class StatusBarUIHelp private constructor(): EmptyActivityLifecycleCallbacks(){
    private val statusBarUIFragmentHelp = StatusBarUIFragmentHelp()
    private val mThirdWindowList = ArrayList<String>()

    internal var mDelegate: IStatusBarDelegate = object : IStatusBarDelegate {

        override fun onDestroy(fragment: Fragment) {

        }

        override fun onCreate(fragment: Fragment, configBean: StatusBarConfigBean) {

        }

        override fun onCreate(activity: Activity, configBean: StatusBarConfigBean) {

        }

        override fun onDestroy(activity: Activity) {

        }

    }

    internal fun setDefaultDelegate(delegate: IStatusBarDelegate){
        mDelegate = delegate
    }

    internal fun getDefaultDelegate(): IStatusBarDelegate {
        return mDelegate
    }

    internal fun includeThirdWindow(key: String){
        mThirdWindowList.add(key)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if(activity is FragmentActivity){
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(statusBarUIFragmentHelp,true)
        }
        val activityName = activity.javaClass.name
        if(mThirdWindowList.indexOf(activityName)>=0){
            mDelegate.onCreate(activity, StatusBarConfigBean())
        }else if(activity.javaClass.isAnnotationPresent(StatusBarConfig::class.java)){
            activity.javaClass.getAnnotation(StatusBarConfig::class.java)?.apply {
                val configBean = quickConfig<StatusBarConfigBean> {
                    isDarkFont = this@apply.isDarkFont
                    backgroundResId = this@apply.backgroundResId
                    alpha = this@apply.alpha
                }
                mDelegate.onCreate(activity,configBean)
            }
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        if(activity is FragmentActivity){
            activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(statusBarUIFragmentHelp)
        }
        mDelegate.onDestroy(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        activity.findViewById<View>(R.id.v_status_proxy)?.apply {
            val params = layoutParams
            if(params.height == activity.getStatusBarHeight()){
                return@apply
            }
            params.height = activity.getStatusBarHeight()
            layoutParams = params
        }
    }

    inner class StatusBarUIFragmentHelp: FragmentManager.FragmentLifecycleCallbacks(){

        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
            super.onFragmentResumed(fm, f)
            f.view?.findViewById<View>(R.id.v_status_proxy)?.apply {
                val params = layoutParams
                if(params.height == f.requireContext().getStatusBarHeight()){
                    return@apply
                }
                params.height = f.requireContext().getStatusBarHeight()
                layoutParams = params
            }
        }

        override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
            super.onFragmentActivityCreated(fm, f, savedInstanceState)
            if(mThirdWindowList.contains(f.javaClass.name)) {
                mDelegate.onCreate(f, StatusBarConfigBean())
            }else if(f.javaClass.isAnnotationPresent(StatusBarConfig::class.java)){
                f.javaClass.getAnnotation(StatusBarConfig::class.java)?.apply {
                    val configBean = quickConfig<StatusBarConfigBean> {
                        isDarkFont = this@apply.isDarkFont
                        backgroundResId = this@apply.backgroundResId
                        alpha = this@apply.alpha
                    }
                    mDelegate.onCreate(f,configBean)
                }
            }
        }

        override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
            super.onFragmentDestroyed(fm, f)
            mDelegate.onDestroy(f)
        }
    }

    companion object {
        private val INSTANCE: StatusBarUIHelp by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            StatusBarUIHelp()
        }

        fun getInstance(): StatusBarUIHelp {
            return INSTANCE
        }

        fun setDefaultDelegate(delegate: IStatusBarDelegate){
            INSTANCE.setDefaultDelegate(delegate)
        }

        fun getDefaultDelegate(): IStatusBarDelegate {
            return INSTANCE.getDefaultDelegate()
        }

        fun includeThirdWindow(vararg clazzs:Class<*>){
            clazzs.forEach {
                getInstance().includeThirdWindow(it.name)
            }
        }

    }
}