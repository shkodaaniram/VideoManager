package com.example.videomanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class MyAdapter extends RecyclerView.Adapter <MyAdapter.ViewHolder> {

    private int[]  mImages;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageButton filter_type;
        public ViewHolder(View v) {
            super(v);
            filter_type = (ImageButton)v.findViewById(R.id.recycler_itemBtn);
        }
    }

    public MyAdapter(int[] images) {
        mImages = images;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.filter_type.setBackgroundResource(mImages[position]);

    }

    @Override
    public int getItemCount() {
        return mImages.length;
    }
}

