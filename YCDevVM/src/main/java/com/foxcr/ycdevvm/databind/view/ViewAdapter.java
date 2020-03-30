package com.foxcr.ycdevvm.databind.view;

import android.view.View;
import androidx.databinding.BindingAdapter;

import com.foxcr.ycdevcore.integration.imageloader.ImageConfig;

public class ViewAdapter {
    @BindingAdapter({"app:imageLoadConfig"})
    public static void loadImage(View view, ImageConfig imageConfig) {
        ImageLoadHelp.INSTANCE.loadImage(view,imageConfig);
    }
}
