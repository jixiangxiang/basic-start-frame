package com.eric.self.baselibrary.holder.list;

import android.content.Context;
import android.graphics.Bitmap;
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

public class ViewHolder {
    private SparseArray<View> mViews;
    private View mContentView;
    private Context mContext;

    public ViewHolder(Context context, int resLayoutId, ViewGroup parent) {
        this.mContentView = LayoutInflater.from(context).inflate(resLayoutId, parent, false);
        this.mViews = new SparseArray<>();
        this.mContentView.setTag(this);
        this.mContext = context;
    }

    //获取一个ViewHolder
    public static ViewHolder getHolder(Context context, int resLayoutId, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new ViewHolder(context, resLayoutId, parent);
        }
        return (ViewHolder) convertView.getTag();
    }

    //通过控件的id获取对应的控件，如果没有则加入mViews;记住 <T extends View> T 这种用法
    public <T extends View> T getItemView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = this.mContentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    //获得一个convertView
    public View getmConvertView() {
        return this.mContentView;
    }

    public void setTextView(int viewId, String text) {
        TextView tv = getItemView(viewId);
        tv.setText(text);
    }

    public void setTextView(int viewId, int resId) {
        TextView tv = getItemView(viewId);
        tv.setText(resId);
    }

    public void setImage(int viewId, String imgUrl) {
        ImageView iv = getItemView(viewId);
        Glide.with(this.mContext).load(imgUrl).into(iv);
    }

    public void setImage(int viewId, int resId) {
        ImageView iv = getItemView(viewId);
        Glide.with(this.mContext).load(resId).into(iv);
    }

    public void setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getItemView(viewId);
        Glide.with(this.mContext).load(bitmap).into(iv);
    }

}
