package com.foxcr.ycdevcomponent.base

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.foxcr.cyextkt.quickConfig
import com.foxcr.ycdevcomponent.event.DataToAdapterEvent
import com.foxcr.ycdevdatabinding.smartrefresh.RefreshEvent
import com.foxcr.ycdevvm.base.BaseViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout

/**
@author cjq
@Date 2020/5/16
@Time 下午5:38
@Describe:
 */
abstract class BaseRecyclerViewModel<T>(application: Application):BaseViewModel(application){
    companion object{
        private var mPage = 1
        const val LIMIT_NUM = 10
    }
    val dataListObservable: MutableLiveData<DataToAdapterEvent<List<T>>> = MutableLiveData()
    val refreshUIChange: MutableLiveData<RefreshEvent> = MutableLiveData()
    val onRefreshObserver: MutableLiveData<RefreshLayout> = MutableLiveData()
    val onLoadMoreObserver: MutableLiveData<RefreshLayout> = MutableLiveData()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        //以dataBind的形式绑定SmartRefreshLayout的上拉刷新下拉加载事件，并进行相应的业务
        //完成网络请求后发布事件，通知Activity更新数据
        onRefreshObserver.observe(owner, Observer<RefreshLayout> {
            load()
        })
        onLoadMoreObserver.observe(owner, Observer<RefreshLayout> {
            load(true)
        })

        //通知SmartRefreshLayout自动刷新获取网络数据
        //无需在Activity层主动调用viewModel来执行网络操作，viewModel可自行执行业务操作
        if (autoRefresh())
            refreshUIChange.value = quickConfig<RefreshEvent> {
                action = RefreshEvent.AUTO_REFRESH
            }
    }

    private fun load(loadMore: Boolean = false) {
        var page = 1
        if (loadMore) {
            page = mPage
        } else {
            mPage = page
        }
        loadData(loadMore, page, LIMIT_NUM)
    }

    open fun autoRefresh() = true

    open fun provideLimit(): Int = LIMIT_NUM

    fun notifyDataSetChanged(data: List<T>, success: Boolean, loadMore: Boolean) {
        if (success)
            mPage++
        refreshUIChange.postValue(quickConfig<RefreshEvent> {
            action = if (loadMore) RefreshEvent.STOP_LOAD_MORE else RefreshEvent.STOP_REFRESH
            this.success = success
        })
        dataListObservable.value = quickConfig<DataToAdapterEvent<List<T>>> {
            this.data = data
            this.isLoadMore = loadMore
            this.success = success
        }
    }

    abstract fun loadData(loadMore: Boolean, page: Int, limit: Int)

}