package com.foxcr.ycdevvm.base

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.foxcr.ycdevvm.base.BaseViewModel
import java.lang.AssertionError

class ActivityDelegate(activity: FragmentActivity) : LifecycleObserver {

    private lateinit var owner:LifecycleOwner
    var layoutId:Int = 0
    var binding: ViewDataBinding? = null
    var isDataBind:Boolean = false
    var viewModel: BaseViewModel? = null
    var intent:Intent? =null

    init {
        activity.javaClass.getAnnotation(DataBind::class.java)?.apply {
            this@ActivityDelegate.isDataBind = bind
        }
        intent = activity.intent
        activity.lifecycle.addObserver(this)
    }


    fun dataBinding(activity: FragmentActivity,viewModel: BaseViewModel){
        this.viewModel = viewModel
        intent?.apply {
            viewModel.saveData(BaseViewModel.INTENT,this)
        }
        if(isDataBind)
            binding = DataBindingUtil.setContentView(activity, layoutId)
        else
            activity.setContentView(layoutId)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner){
        this.owner = owner
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        viewModel?.removeData(BaseViewModel.INTENT)
        owner.lifecycle.removeObserver(this)
        binding?.apply {
            this.unbind()
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){}


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){}


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(){}

}

fun ActivityDelegate.onActivityCreate(activity: FragmentActivity, block: ActivityDelegate.(activity: FragmentActivity)->Unit){
    if(this.layoutId == 0){
        throw AssertionError("no layout id")
    }
    block(activity)
    viewModel?.apply {
        activity.lifecycle.addObserver(this)
    }
}