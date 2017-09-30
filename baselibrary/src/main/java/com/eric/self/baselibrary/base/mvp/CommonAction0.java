package com.eric.self.baselibrary.base.mvp;

import rx.functions.Action0;

/**
 * action0 只有一个无参无返回方法，适合作为doOnSubscribe方法
 * Created by eric on 2017/9/29.
 */

public class CommonAction0 implements Action0 {
    private IModelListener mModelListener;

    public CommonAction0(IModelListener mModelListener) {
        this.mModelListener = mModelListener;
    }

    @Override
    public void call() {
        this.mModelListener.onStartLoading();
    }
}
