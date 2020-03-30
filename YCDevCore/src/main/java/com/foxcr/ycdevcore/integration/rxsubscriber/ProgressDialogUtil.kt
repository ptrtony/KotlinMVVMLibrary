package com.foxcr.ycdevcore.integration.rxsubscriber

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.view.KeyEvent
import java.lang.ref.WeakReference

object ProgressDialogUtil {
    private var mWeakReference: WeakReference<ProgressDialog>? = null

    internal fun getInstance(context: Context): ProgressDialog {
        val dialog = ProgressDialog(context)
        mWeakReference = WeakReference(dialog)
        return dialog
    }

    fun showLoadingDialog(
        context: Context, content: String?, cancelable: Boolean,
        onCancelListener: DialogInterface.OnCancelListener? = null,
        onDismissListener: DialogInterface.OnDismissListener? = null
    ) {
        //如果已有dialog存在并正在显示，使用dismiss，使用cancel会发出取消事件，导致并不想取消的订阅被接触订阅
        dismissLoadingDialog()
        getInstance(context).apply {
            setMessage(content)
            setCancelable(cancelable)
            setOnDismissListener(onDismissListener)
            setOnCancelListener(onCancelListener)
            setOnKeyListener { dialog, keyCode, event ->
                return@setOnKeyListener if (keyCode == KeyEvent.KEYCODE_BACK) {
                    cancel()
                    true
                } else {
                    false
                }
            }
            show()
        }
    }

    fun isShowing(): Boolean {
        mWeakReference?.apply {
            return this.get()?.isShowing ?: false
        }
        return false
    }

    fun dismissLoadingDialog() {
        try {
            mWeakReference?.apply {
                val dialog = this.get()
                if (dialog != null && dialog.isShowing) {
                    dialog.dismiss()
                }
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

    }
}