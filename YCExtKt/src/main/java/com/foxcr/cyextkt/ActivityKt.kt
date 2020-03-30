package com.foxcr.cyextkt

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun <reified T : Activity> Context.getIntent(bundle: Bundle? = null): Intent{
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    return intent
}

inline fun <reified T : Activity> Fragment.getIntent(bundle: Bundle? = null): Intent{
    val intent = Intent(requireActivity(), T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    return intent
}

inline fun <reified T : Activity> Activity.startActivity(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.startActivityForResult(bundle: Bundle? = null,requestCode:Int=-1) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivityForResult(intent,requestCode)
}

inline fun <reified T : Activity> Fragment.startActivity(bundle: Bundle? = null) {
    val intent = Intent(this.requireActivity(), T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

inline fun <reified T : Activity> Fragment.startActivityForResult(bundle: Bundle? = null,requestCode:Int=-1) {
    val intent = Intent(this.requireActivity(), T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivityForResult(intent,requestCode)
}