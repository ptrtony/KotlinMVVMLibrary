package com.foxcr.ycdevvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.scope
import org.kodein.di.Kodein
import java.lang.AssertionError
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity(), IActivity {
    protected lateinit var mActivityDelegate: ActivityDelegate
    protected lateinit var mViewModel: VM
    override val kodein: Kodein = obtainAppKodeinAware().kodein

    protected val scopeProvider: ScopeProvider by lazy {
        this.scope(Lifecycle.Event.ON_DESTROY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityDelegate.onActivityCreate(this){ activity ->
            mViewModel = initViewModel()
            dataBinding(activity,mViewModel)
            initParam(savedInstanceState)
            initView(savedInstanceState)
            initViewObservable(savedInstanceState)
            if(isDataBind){
                if(initVariableId()==0){
                    throw AssertionError("variableId = 0")
                }
                binding?.setVariable(initVariableId(), mViewModel)
            }
        }

    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
    }

    override fun setActivityDelegate(delegate: ActivityDelegate) {
        mActivityDelegate = delegate
    }

    open fun initParam(savedInstanceState: Bundle?){}

    open fun initView(savedInstanceState: Bundle?){}

    open fun initViewObservable(viewModel: VM) {}

    open fun initVariableId():Int = 0

    private fun initViewObservable(savedInstanceState: Bundle?){
        mViewModel.finishActivityLiveData.observe(this, Observer {
            if(it){
                finish()
            }
        })
        initViewObservable(mViewModel)
    }

    private fun initViewModel():VM{
        val genericSuperclass = javaClass.genericSuperclass
        return if (genericSuperclass is ParameterizedType) {
            val actualTypeArguments = genericSuperclass.actualTypeArguments
            val genericClass = actualTypeArguments.singleOrNull {
                try {
                    AndroidViewModel::class.java.isAssignableFrom(it as Class<VM>)
                }catch (e:Exception){
                    e.printStackTrace()
                    false
                }
            }
            ViewModelProviders.of(this).get(genericClass as Class<VM>)
        }else{
            ViewModelProviders.of(this).get(BaseViewModel::class.java) as VM
        }
    }
}