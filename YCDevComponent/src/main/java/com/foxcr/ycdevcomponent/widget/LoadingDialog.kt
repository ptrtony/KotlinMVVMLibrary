package com.foxcr.ycdevcomponent.widget
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.foxcr.ycdevcomponent.R

class LoadingDialog constructor(context: Context):Dialog(context,
    R.style.LoadingDialogStyle){
    private val mLoadingImage:ImageView
    init {
        val lp = WindowManager.LayoutParams()
        lp.dimAmount = 0.0f
        window?.let{
            it.attributes = lp
            it.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        setCanceledOnTouchOutside(false)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_loading_dialog,null)
        setContentView(view)
        mLoadingImage = view.findViewById(R.id.mLoadingIv)
    }

    fun showDialog() {
        super.show()
        val animation = AnimationUtils.loadAnimation(context,R.anim.anim_loading)
        animation.interpolator = LinearInterpolator()
        mLoadingImage.animation = animation
        animation.start()
    }


     fun cancelDialog() {
        super.cancel()
    }


}