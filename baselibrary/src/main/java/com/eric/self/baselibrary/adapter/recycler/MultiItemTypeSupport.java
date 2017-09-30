package com.eric.self.baselibrary.adapter.recycler;

/**
 * Created by eric on 2017/9/14.
 */

public interface MultiItemTypeSupport<T> {

    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);
}
