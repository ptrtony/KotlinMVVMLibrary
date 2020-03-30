package com.foxcr.ycdevvm.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import com.foxcr.ycdevvm.base.BaseViewModel
import com.foxcr.ycdevvm.base.FragmentDelegate
import com.foxcr.ycdevvm.base.IFragment
import com.foxcr.ycdevvm.base.onFragmentCreate
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.scope
import org.kodein.di.Kodein
import java.lang.AssertionError
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM: BaseViewModel>:Fragment(), IFragment {
    protected lateinit var mFragmentDelegate: FragmentDelegate
    override val kodein: Kodein = obtainAppKodeinAware().kodein
    protected lateinit var mViewModel: VM
    protected var mContext: Context?=null
    private var baseView: View? = null
    private var firstViewCreated = false

    protected val scopeProvider: ScopeProvider by lazy {
        this.scope(Lifecycle.Event.ON_DESTROY)
    }

    override fun setFragmentDelegate(delegate: FragmentDelegate) {
        this.mFragmentDelegate = delegate
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mViewModel = initViewModel().apply {
            mFragmentDelegate.bindViewModel(this)
        }
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (baseView == null) {
            if(mFragmentDelegate.layoutId == 0){
                throw AssertionError("no layout id")
            }
            baseView = mFragmentDelegate.dataBinding(inflater,container,false)
            firstViewCreated = true
        }
        return baseView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(firstViewCreated){
            mFragmentDelegate.onFragmentCreate(this) {
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
            firstViewCreated = false
        }
    }

    open fun initParam(savedInstanceState: Bundle?){}

    open fun initView(savedInstanceState: Bundle?){}

    open fun initViewObservable(viewModel: VM) {}

    open fun initVariableId():Int = 0

    private fun initViewObservable(savedInstanceState: Bundle?){
        mViewModel.finishActivityLiveData.observe(this, Observer {
            if(it){
                activity?.finish()
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