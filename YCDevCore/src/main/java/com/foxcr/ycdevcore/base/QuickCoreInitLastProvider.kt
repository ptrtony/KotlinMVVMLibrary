package com.foxcr.ycdevcore.base

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.foxcr.ycdevcore.base.delegate.AppDelegate

class QuickCoreInitLastProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        context?.apply  application@{
            AppDelegate.instance.apply {
                init(this@application)
                        .apply {
                            onCreate(this@application as Application)
                        }
            }
        }
        return true
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?) = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int  = 0

    override fun getType(uri: Uri): String?  = null
}