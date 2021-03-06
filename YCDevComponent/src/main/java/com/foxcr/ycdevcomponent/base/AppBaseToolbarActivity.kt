package com.foxcr.ycdevcomponent.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.lifecycle.Lifecycle
import com.foxcr.cyextkt.autoDisposable
import com.foxcr.cyextkt.hideSoftInput
import com.foxcr.cyextkt.observeOnUI
import com.foxcr.cyextkt.throttleFirstClicks
import com.foxcr.ycdevcomponent.R
import com.foxcr.ycdevvm.base.BaseViewModel
import com.uber.autodispose.android.lifecycle.scope
import java.lang.AssertionError
import java.lang.IllegalStateException

/**
@author cjq
@Date 2020/5/16
@Time 下午5:49
@Describe:
 */
abstract class AppBaseToolbarActivity<VM : BaseViewModel> : AppBaseActivity<VM>() {
    private var mToolbarTitle: TextView? = null
    override fun initView(savedInstanceState: Bundle?) {
        val rootView = findViewById<ViewGroup>(android.R.id.content)
        if(rootView.findViewById<View>(R.id.llHeader)==null){
            val parentView = rootView.getChildAt(0)
            rootView.removeView(parentView)
            val newParentView = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL}
            val headerView = LayoutInflater.from(this).inflate(R.layout.layout_base_toolbar, null)
            newParentView.addView(headerView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            newParentView.addView(parentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            rootView.addView(newParentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        }
        initToolbar()
    }

    private fun initToolbar(){
        val llHeader = findViewById<View>(R.id.llHeader) ?: throw AssertionError("layout must include layout_base_toolbar")
        findViewById<Toolbar>(R.id.baseToolbar).apply {
            val toolbarContainer = provideToolbarContainer()
            addView(toolbarContainer,Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.MATCH_PARENT)
            this.apply {
                val lp = layoutParams
                lp.height = getAppToolbarDelegate().provideToolbarHeight()
                layoutParams = lp
            }
            if(!initToolbar(this)){
                initDefaultToolbar(this,toolbarContainer,getAppToolbarDelegate())
            }
        }
        llHeader.setBackgroundResource(getAppToolbarDelegate().provideHeaderBackground())
    }

    open fun provideToolbarContainer(): View {
        return LayoutInflater.from(this).inflate(R.layout.layout_toolbar_container, null)
    }

    private fun initDefaultToolbar(toolbar:Toolbar, toolbarContainer: View, appToolbarDelegate: AppToolbarDelegate){
        toolbarContainer.apply {
            mToolbarTitle = findViewById<TextView>(R.id.toolbarTitle)?.apply {
                text = appToolbarDelegate.provideToolbarTitleText()
                setTextColor(appToolbarDelegate.provideToolbarTitleTextColor())
            }
            findViewById<ImageView>(R.id.toolbarLeftIcon)?.apply {
                if (appToolbarDelegate.provideToolbarNavIcon() == 0) {
                    visibility = View.GONE
                    setOnClickListener(null)
                }else{
                    visibility = View.VISIBLE
                    setImageResource(appToolbarDelegate.provideToolbarNavIcon())
                    throttleFirstClicks()
                        .observeOnUI()
                        .autoDisposable(this@AppBaseToolbarActivity.scope(Lifecycle.Event.ON_DESTROY))
                        .subscribe {
                            try {
                                hideSoftInput()
                                onBackPressed()
                            } catch (ex: IllegalStateException) {
                                ex.printStackTrace()
                            }
                        }
                }
            }
            findViewById<ImageView>(R.id.toolbarRightIcon)?.apply {
                if (appToolbarDelegate.provideToolbarRightIcon() != 0) {
                    visibility = View.VISIBLE
                    setImageResource(appToolbarDelegate.provideToolbarRightIcon())
                    throttleFirstClicks()
                        .observeOnUI()
                        .autoDisposable(this@AppBaseToolbarActivity.scope(Lifecycle.Event.ON_DESTROY))
                        .subscribe(appToolbarDelegate.rightIconClickObserver)
                }else{
                    visibility = View.GONE
                    setOnClickListener(null)
                }
            }
            findViewById<TextView>(R.id.toolbarRightTv)?.apply {
                if (!appToolbarDelegate.provideToolbarRightTv().isEmpty()) {
                    visibility = View.VISIBLE
                    text = appToolbarDelegate.provideToolbarRightTv()
                    setTextColor(appToolbarDelegate.provideToolbarRightTvColor())
                    throttleFirstClicks()
                        .observeOnUI()
                        .autoDisposable(this@AppBaseToolbarActivity.scope(Lifecycle.Event.ON_DESTROY))
                        .subscribe(appToolbarDelegate.rightTitleClickObserver)
                }else{
                    visibility = View.GONE
                    setOnClickListener(null)
                }
            }
            findViewById<View>(R.id.toolbar_divide)?.apply {
                visibility = if(appToolbarDelegate.provideToolBarDivide()) View.VISIBLE else View.GONE
            }
        }
        toolbar.apply {
            setBackgroundResource(appToolbarDelegate.provideToolbarBackground())
        }
    }

    abstract fun initToolbar(toolbar: Toolbar):Boolean

    fun setCenterTitle(title:String){
        mToolbarTitle?.text = title
    }
}