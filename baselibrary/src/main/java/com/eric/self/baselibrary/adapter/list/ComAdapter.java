package com.eric.self.baselibrary.adapter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.eric.self.baselibrary.holder.list.ViewHolder;

import java.util.List;

/**
 * Created by eric on 2017/9/13.
 */

public abstract class ComAdapter<T> extends BaseAdapter {
    private List<T> mDatas;
    private Context mContext;
    private int mItemLayoutId;
    private LayoutInflater mLayoutInflater;

    public ComAdapter(List<T> mDatas, Context context, int mItemLayoutId) {
        this.mDatas = mDatas;
        this.mContext = context;
        this.mItemLayoutId = mItemLayoutId;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.mDatas == null ? 0 : this.mDatas.size();
    }

    @Override
    public T getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //实例化一个ViewHolder
        ViewHolder holder = getViewHolder(view, viewGroup);
        //使用对外公开的convert方法，通过ViewHolder把View找到，通过Item设置值
        convert(holder, getItem(i));
        return holder.getmConvertView();
    }

    private ViewHolder getViewHolder(View convertView, ViewGroup parent) {
        return ViewHolder.getHolder(mContext, mItemLayoutId, convertView, parent);
    }

    /**
     * 对外公布了一个convert方法，并且还把ViewHolder和本item对应的Bean对象给传出去
     * 现在convert方法里面需要干嘛呢？通过ViewHolder把View找到，通过Item设置值
     */
    public abstract void convert(ViewHolder holder, T item);
}
