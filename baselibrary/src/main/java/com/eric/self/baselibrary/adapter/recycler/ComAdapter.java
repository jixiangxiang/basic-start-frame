package com.eric.self.baselibrary.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.eric.self.baselibrary.holder.recycler.ViewHolder;

import java.util.List;

/**
 * Created by eric on 2017/9/13.
 */

public abstract class ComAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    private LayoutInflater mLayoutInflater;
    protected List<T> mDatas;
    private int mLayoutId;

    public ComAdapter(Context mContext, List<T> mDatas, int layoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mLayoutId = layoutId;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(this.mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updatePosition(position);
        covert(holder, mDatas.get(position));
    }

    public abstract void covert(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
