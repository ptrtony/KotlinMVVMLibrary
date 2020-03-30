package com.foxcr.ycdevdatabinding.recyclerview;


import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ViewAdapter {

    @BindingAdapter("app:layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }
}
