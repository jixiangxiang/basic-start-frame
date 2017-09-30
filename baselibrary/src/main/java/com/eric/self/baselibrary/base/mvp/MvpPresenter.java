package com.eric.self.baselibrary.base.mvp;

/**
 * Created by eric on 2017/9/29.
 */

public abstract class MvpPresenter<T> implements IModelListener<T> {
    private IMvpView mMvpView;

    public MvpPresenter(IMvpView mMvpView) {
        this.mMvpView = mMvpView;
    }

    @Override
    public void onStartLoading() {
        this.mMvpView.showLoading();
    }

    @Override
    public void onStopLoading() {
        this.mMvpView.stopLoading();
    }

    @Override
    public void onError(Throwable throwable) {
        this.mMvpView.requestError(throwable);
    }
}
