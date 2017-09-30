package com.eric.self.selfapplication.view;

import com.eric.self.baselibrary.base.mvp.IMvpView;

/**
 * Created by eric on 2017/9/29.
 */

public interface IGithubView<T> extends IMvpView {

    void requestSuccess(T t);
}
