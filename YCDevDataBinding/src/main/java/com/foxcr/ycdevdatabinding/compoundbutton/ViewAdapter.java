package com.foxcr.ycdevdatabinding.compoundbutton;

import android.widget.CompoundButton;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import com.jakewharton.rxbinding3.widget.RxCompoundButton;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class ViewAdapter {
    @BindingAdapter(value = {"app:onCheckedChanges"}, requireAll = false)
    public static void setCheckedChangeListener(CompoundButton compoundButton, Consumer<Boolean> onNext) {
        if (compoundButton.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) compoundButton.getContext();
            RxCompoundButton.checkedChanges(compoundButton)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(onNext);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    @BindingAdapter(value = {"app:rxChecked"}, requireAll = false)
    public static void setRxChecked(CompoundButton compoundButton, Observable<Boolean> observable) {
        if (compoundButton.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) compoundButton.getContext();
            observable.subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(compoundButton::setChecked);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }
}
