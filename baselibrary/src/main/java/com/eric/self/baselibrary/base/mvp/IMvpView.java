package com.eric.self.baselibrary.base.mvp;

/**
 * Created by eric on 2017/9/29.
 */

public interface IMvpView {

    void showLoading();

    void stopLoading();

    void requestError(Throwable throwable);

}
