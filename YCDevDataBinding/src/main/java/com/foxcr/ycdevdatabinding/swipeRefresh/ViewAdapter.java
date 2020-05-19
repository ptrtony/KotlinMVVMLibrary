package com.foxcr.ycdevdatabinding.swipeRefresh;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ChengJingQiang
 * @copyright:2019
 * @project kotlinMVVMLibrary
 * @date 2019-08-22
 * description:
 */

public class ViewAdapter {

    @BindingAdapter(value = "app:refreshLayout")
    public static void setRefreshLayoutChange(SwipeRefreshLayout swipeRefreshLayout, MutableLiveData<SwipeRefreshEvent> refreshUIChange) {
        if (refreshUIChange.hasObservers()) return;
        Observer<SwipeRefreshEvent> refreshUIObserver = swipeRefreshEvent -> {
            switch (swipeRefreshEvent.action) {
                case SwipeRefreshEvent.AUTO_REFRESH:
                    swipeRefreshLayout.post(() -> setRefreshing(swipeRefreshLayout));
                    break;

                case SwipeRefreshEvent.STOP_REFRESH:
                    swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
                    break;
            }
        };

        if (swipeRefreshLayout.getContext() instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) swipeRefreshLayout.getContext();
            refreshUIChange.observe(fragmentActivity, refreshUIObserver);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }

    }

    @BindingAdapter(value = "app:refreshObserver")
    public static void setOnRefreshListener(SwipeRefreshLayout swipeRefreshLayout, MutableLiveData<String> onRefreshObserver) {
        if (onRefreshObserver != null) {
            swipeRefreshLayout.setOnRefreshListener(() -> onRefreshObserver.setValue(""));
        }
    }


    private static void setRefreshing(SwipeRefreshLayout refreshLayout) {
        Class<? extends SwipeRefreshLayout> refreshLayoutClass = refreshLayout.getClass();
        try {
            Method setRefreshing = refreshLayoutClass.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(refreshLayout, true, true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
