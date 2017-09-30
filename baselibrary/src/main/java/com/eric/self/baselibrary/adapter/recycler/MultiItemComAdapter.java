package com.eric.self.baselibrary.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eric.self.baselibrary.holder.recycler.ViewHolder;

import java.util.List;

/**
 * Created by eric on 2017/9/14.
 */

public abstract class MultiItemComAdapter<T> extends ComAdapter<T> {
    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemComAdapter(Context mContext, List mDatas, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(mContext, mDatas, -1);
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        ViewHolder<T> viewHolder = new ViewHolder<>(mContext, itemView);
        return viewHolder;
    }
}
