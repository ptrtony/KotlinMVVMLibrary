package com.foxcr.ycdevdatabinding.radiogroup;

import android.widget.RadioGroup;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import com.jakewharton.rxbinding3.widget.RxRadioGroup;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class ViewAdapter {
    @BindingAdapter(value = {"app:onCheckedChanges"}, requireAll = false)
    public static void setCheckedChanged(RadioGroup radioGroup, Consumer<Integer> onNext) {
        if (radioGroup.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) radioGroup.getContext();
            RxRadioGroup.checkedChanges(radioGroup)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(onNext);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }
}
