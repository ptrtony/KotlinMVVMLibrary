package com.foxcr.ycdevcomponent.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.foxcr.ycdevcomponent.BR
import com.foxcr.ycdevcomponent.R
import com.foxcr.ycdevvm.base.BaseFragment
import com.foxcr.ycdevvm.base.DataBind
import com.foxcr.ycdevvm.widget.UniClassicsFooter
import kotlinx.android.synthetic.main.layout_base_recyclerview.*

/**
@author cjq
@Date 2020/5/16
@Time 下午6:12
@Describe:
 */

@DataBind
abstract class AppBaseRecyclerViewFragment <T,VM:BaseRecyclerViewModel<T>> : BaseFragment<VM>(){
    protected lateinit var mAdapter: BaseQuickAdapter<T, BaseViewHolder>
    override fun layout() = R.layout.layout_base_recyclerview
    override fun initVariableId() = BR.viewModel

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        recyclerView.apply {
            mAdapter = provideAdapter()
            layoutManager = provideLinearLayoutManager()
            adapter = mAdapter
        }
    }
    override fun initViewObservable(viewModel: VM) {
        viewModel.dataListObservable.observe(this, Observer {
            smartRefreshLayout.refreshFooter?.apply {
                if(this is UniClassicsFooter){
                    setOnFinishListener(object : UniClassicsFooter.OnFinishListener{
                        override fun getTextFinish(textFinish: String, textNothing: String): String {
                            return if(it.data.isNullOrEmpty()){
                                textNothing
                            }else{
                                textFinish
                            }
                        }
                    })
                }
            }
            it.data?.apply {
                if(this.size < viewModel.provideLimit()){
                    smartRefreshLayout.setEnableLoadMore(false)
                }
            }
            if(it.isLoadMore){
                //上拉加载更多
                it.data?.apply {
                    mAdapter.addData(this)
                }
            }else{
                //下拉刷新
                if(it.data?.isNullOrEmpty() == true){
                    mAdapter.emptyView = provideEmptyOrErrorView(it.success)
                }else{
                    mAdapter.setNewData(it.data)
                }
            }
        })
    }

    open fun provideLinearLayoutManager(): RecyclerView.LayoutManager{
        return LinearLayoutManager(requireContext())
    }

    open fun provideEmptyOrErrorView(success:Boolean): View {
        return LayoutInflater.from(requireContext()).inflate(R.layout.layout_empty_list, null)
    }

    abstract fun provideAdapter():BaseQuickAdapter<T,BaseViewHolder>

}