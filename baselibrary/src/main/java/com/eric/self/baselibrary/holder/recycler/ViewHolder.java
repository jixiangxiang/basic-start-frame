package com.eric.self.baselibrary.holder.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by eric on 2017/9/13.
 */

public class ViewHolder<T> extends RecyclerView.ViewHolder {
    private Context mContext;
    private SparseArray<View> mViews;
    private View mConverView;

    public ViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        this.mConverView = itemView;
        mViews = new SparseArray<View>();
    }

    public static ViewHolder get(Context mContext, ViewGroup parent, int mLayoutId) {
        View itemView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(mContext, itemView);
        return viewHolder;
    }

    public void updatePosition(int position) {
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConverView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public void setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
    }

    public void setText(int viewId, int resId) {
        TextView tv = getView(viewId);
        tv.setText(resId);
    }

    public void setImage(int viewId, int resId) {
        ImageView iv = getView(viewId);
        Glide.with(mContext).load(resId).into(iv);
    }

    public void setImage(int viewId, String url) {
        ImageView iv = getView(viewId);
        Glide.with(mContext).load(url).into(iv);
    }

    public void setImage(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        Glide.with(mContext).load(bitmap).into(iv);
    }

    public ViewHolder setOnclickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

}
