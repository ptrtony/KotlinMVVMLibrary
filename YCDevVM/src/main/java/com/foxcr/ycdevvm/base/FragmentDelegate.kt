package com.foxcr.ycdevvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.lang.AssertionError

class FragmentDelegate(fragment: Fragment): LifecycleObserver {
    private lateinit var owner:LifecycleOwner

    var layoutId:Int = 0
    var binding: ViewDataBinding? = null
    var isDataBind:Boolean = false
    var viewModel: BaseViewModel? = null
    var mArguments:Bundle? = null

    init {
        fragment.javaClass.getAnnotation(DataBind::class.java)?.apply {
            this@FragmentDelegate.isDataBind = bind
        }
        mArguments = fragment.arguments
        fragment.lifecycle.addObserver(this)
    }

    fun bindViewModel(viewModel: BaseViewModel){
        this.viewModel = viewModel
        mArguments?.apply {
            viewModel.saveData(BaseViewModel.ARGUMENTS,this)
        }
    }

    fun dataBinding(inflater: LayoutInflater, container: ViewGroup?, attachToParent:Boolean): View?{
        return if(isDataBind){
            binding = DataBindingUtil.inflate(inflater, layoutId, container, attachToParent)
            binding!!.root
        }else{
            inflater.inflate(layoutId,container,false)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner){
        this.owner = owner
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        viewModel?.removeData(BaseViewModel.ARGUMENTS)
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

fun FragmentDelegate.onFragmentCreate(fragment: Fragment,block:FragmentDelegate.(fragment: Fragment)->Unit){
    block(fragment)
    viewModel?.apply {
        fragment.lifecycle.addObserver(this)
    }
}