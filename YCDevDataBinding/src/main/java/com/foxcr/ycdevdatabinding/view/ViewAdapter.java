package com.foxcr.ycdevdatabinding.view;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import com.jakewharton.rxbinding3.view.RxView;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import kotlin.Unit;

public class ViewAdapter {

    @BindingAdapter(value = {"app:onRxClick", "app:throttle"}, requireAll = false)
    public static void onRxClick(View view, Consumer<Unit> onNext, Integer duration) {
        if (view.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) view.getContext();
            if (duration == null) {
                duration = 2;
            } else if (duration == 0) {
                RxView.clicks(view)
                        .observeOn(AndroidSchedulers.mainThread())
                        .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                        .subscribe(onNext);
                return;
            }
            RxView.clicks(view)
                    .throttleFirst(duration, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(onNext);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    @BindingAdapter({"app:onRxLongClicks"})
    public static void onRxLongClicks(View view, Consumer<Unit> onNext) {
        if (view.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) view.getContext();
            RxView.longClicks(view)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(onNext);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    @BindingAdapter({"app:onRxFocusChanges"})
    public static void onRxFocusChanges(View view, Consumer<Boolean> onNext) {
        if (view.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) view.getContext();
            RxView.focusChanges(view)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(onNext);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    @BindingAdapter({"app:onRxLayoutChanges"})
    public static void onRxLayoutChanges(View view, Consumer<Unit> onNext) {
        if (view.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) view.getContext();
            RxView.layoutChanges(view)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(onNext);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    @BindingAdapter(value = {"app:rxVisibility"}, requireAll = false)
    public static void setRxVisibility(View view, Observable<Boolean> observable) {
        if (observable == null) return;
        if (view.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) view.getContext();
            observable.subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(aBoolean ->
                            view.setVisibility(aBoolean ? View.VISIBLE : View.GONE)
                    );
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    @BindingAdapter(value = {"app:rxSelected"}, requireAll = false)
    public static void setRxSelected(View view, Observable<Boolean> observable) {
        if (observable == null) return;
        if (view.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) view.getContext();
            observable.subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(view::setSelected);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    @BindingAdapter(value = {"app:rxBackgroundResource"}, requireAll = false)
    public static void setRxBackgroundResource(View view, Observable<Integer> observable) {
        if (observable == null) return;
        if (view.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) view.getContext();
            observable.subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(view::setBackgroundResource);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    @BindingAdapter(value = {"app:rxBackground"}, requireAll = false)
    public static void setRxBackground(View view, Observable<Drawable> observable) {
        if (observable == null) return;
        if (view.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) view.getContext();
            observable.subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(view::setBackground);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    @BindingAdapter(value = {"app:rxAlpha"}, requireAll = false)
    public static void setRxAlpha(View view, Observable<Float> observable) {
        if (observable == null) return;
        if (view.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) view.getContext();
            observable.subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(view::setAlpha);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }


    @BindingAdapter(value = {"app:rxEnabled"}, requireAll = false)
    public static void setRxEnabled(View view, Observable<Boolean> observable) {
        if (observable == null) return;
        if (view.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) view.getContext();
            observable.subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(view::setEnabled);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }


}
