package com.foxcr.ycdevdatabinding.smartrefresh;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

public class ViewAdapter {
    @BindingAdapter(value = {"app:refreshLayout"})
    public static void setRefreshLayoutChange(SmartRefreshLayout smartRefreshLayout, MutableLiveData<RefreshEvent> refreshUIChange) {
        if (refreshUIChange.hasObservers()) return;
        Observer<RefreshEvent> refreshUIObserver = refreshEvent -> {
            switch (refreshEvent.action) {
                case RefreshEvent.AUTO_REFRESH:
                    smartRefreshLayout.autoRefresh();
                    break;
                case RefreshEvent.AUTO_LOAD_MORE:
                    smartRefreshLayout.autoLoadMore();
                    break;
                case RefreshEvent.STOP_REFRESH:
                    smartRefreshLayout.finishRefresh(refreshEvent.success);
                    break;
                case RefreshEvent.STOP_LOAD_MORE:
                    if (refreshEvent.success) {
                        if (refreshEvent.noMoreData) {
                            smartRefreshLayout.setNoMoreData(true);
                        } else {
                            smartRefreshLayout.finishLoadMore(true);
                        }
                    } else {
                        smartRefreshLayout.finishLoadMore(false);
                    }
                    break;
            }
        };
        if (smartRefreshLayout.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) smartRefreshLayout.getContext();
            refreshUIChange.observe(activity, refreshUIObserver);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }

    }

    @BindingAdapter(value = {"app:refreshObserver", "app:loadMoreObserver"}, requireAll = false)
    public static void setOnRefreshLoadMoreListener(SmartRefreshLayout smartRefreshLayout, MutableLiveData<RefreshLayout> onRefreshObserver, MutableLiveData<RefreshLayout> onLoadMoreObserver) {
        if (onRefreshObserver != null)
            smartRefreshLayout.setOnRefreshListener(onRefreshObserver::setValue);
        if (onLoadMoreObserver != null)
            smartRefreshLayout.setOnLoadMoreListener(onLoadMoreObserver::setValue);
    }

    @BindingAdapter(value = {"app:refreshLoadMoreListener"})
    public static void setOnRefreshLoadMoreListener(SmartRefreshLayout smartRefreshLayout, OnRefreshLoadMoreListener onRefreshLoadMoreListener) {
        if (onRefreshLoadMoreListener != null)
            smartRefreshLayout.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener);
    }

}
