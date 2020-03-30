package com.foxcr.ycdevdatabinding.textview;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ViewAdapter {

    @BindingAdapter(value = {"app:rxText"}, requireAll = false)
    public static void setRxText(TextView textView, Observable<String> observable) {
        if (textView.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) textView.getContext();
            observable.subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(textView::setText);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

}
