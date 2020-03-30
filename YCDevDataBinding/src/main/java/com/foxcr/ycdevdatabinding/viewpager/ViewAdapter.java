package com.foxcr.ycdevdatabinding.viewpager;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;

import com.jakewharton.rxbinding3.viewpager.RxViewPager;
import com.jakewharton.rxbinding3.viewpager.ViewPagerPageScrollEvent;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class ViewAdapter {

    @BindingAdapter(value = {"app:onPageSelections"}, requireAll = false)
    public static void addViewPageSelectListener(ViewPager viewPager, Consumer<Integer> onNext) {
        if (viewPager.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) viewPager.getContext();
            RxViewPager.pageSelections(viewPager)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(onNext);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    @BindingAdapter(value = {"app:onPageScrollStateChanges"}, requireAll = false)
    public static void addViewPageScrollStateChangeListener(ViewPager viewPager, Consumer<Integer> onNext) {
        if (viewPager.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) viewPager.getContext();
            RxViewPager.pageScrollStateChanges(viewPager)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(onNext);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    @BindingAdapter(value = {"app:onPageScrollEvents"}, requireAll = false)
    public static void addViewPageScrollListener(ViewPager viewPager, Consumer<ViewPagerPageScrollEvent> onNext) {
        if (viewPager.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) viewPager.getContext();
            RxViewPager.pageScrollEvents(viewPager)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(onNext);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }
}
