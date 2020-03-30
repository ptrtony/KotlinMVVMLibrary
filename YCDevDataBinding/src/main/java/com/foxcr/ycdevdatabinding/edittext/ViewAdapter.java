package com.foxcr.ycdevdatabinding.edittext;

import android.widget.EditText;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent;
import com.jakewharton.rxbinding3.widget.TextViewBeforeTextChangeEvent;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class ViewAdapter {
    /**
     * EditText输入文字改变的监听
     */
    @BindingAdapter(value = {"app:textChanges"}, requireAll = false)
    public static void addTextChangedListener(EditText editText, Consumer<CharSequence> onNext) {
        if (editText.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) editText.getContext();
            RxTextView.textChanges(editText)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(onNext);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    /**
     * EditText输入文字改变的监听
     */
    @BindingAdapter(value = {"app:afterTextChanges"}, requireAll = false)
    public static void addAfterTextChangedListener(EditText editText, Consumer<TextViewAfterTextChangeEvent> onNext) {
        if (editText.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) editText.getContext();
            RxTextView.afterTextChangeEvents(editText)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(onNext);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }

    /**
     * EditText输入文字改变的监听
     */
    @BindingAdapter(value = {"app:beforeTextChanges"}, requireAll = false)
    public static void addBeforeTextChangedListener(EditText editText, Consumer<TextViewBeforeTextChangeEvent> onNext) {
        if (editText.getContext() instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) editText.getContext();
            RxTextView.beforeTextChangeEvents(editText)
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(onNext);
        } else {
            throw new AssertionError("activity is not extends FragmentActivity");
        }
    }
}
