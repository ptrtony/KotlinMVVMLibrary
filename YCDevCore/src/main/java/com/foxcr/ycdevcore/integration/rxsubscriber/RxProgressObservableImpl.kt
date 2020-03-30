package com.foxcr.ycdevcore.integration.rxsubscriber

import android.content.Context
import android.content.DialogInterface

class RxProgressObservableImpl(msg: String, cancelable: Boolean = true) : RxProgressObservable(msg, cancelable) {

    override fun show(context: Context, msg: String) {
        if (cancelable) {
            ProgressDialogUtil.showLoadingDialog(context, msg, cancelable,
                DialogInterface.OnCancelListener { cancelObservable.onNext(true) },
                DialogInterface.OnDismissListener { cancelObservable.onComplete() })
        } else {
            ProgressDialogUtil.showLoadingDialog(context, msg, cancelable,
                null,
                DialogInterface.OnDismissListener { cancelObservable.onComplete() })
        }
    }

    override fun isShowing(): Boolean {
        return ProgressDialogUtil.isShowing()
    }

    override fun dismiss() {
        ProgressDialogUtil.dismissLoadingDialog()
    }
}