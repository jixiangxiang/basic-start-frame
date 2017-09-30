package com.eric.self.baselibrary.base.mvp;

import rx.Subscriber;

/**
 * Created by eric on 2017/9/29.
 */

public class CommonSubscriber<T> extends Subscriber<T> {
    private IModelListener<T> mModelListener;

    public CommonSubscriber(IModelListener mModelListener) {
        this.mModelListener = mModelListener;
    }

    @Override
    public void onCompleted() {
        this.mModelListener.onStopLoading();
    }

    @Override
    public void onError(Throwable throwable) {
        this.mModelListener.onError(throwable);
    }

    @Override
    public void onNext(T t) {
        this.mModelListener.onSuccess(t);
    }
}
