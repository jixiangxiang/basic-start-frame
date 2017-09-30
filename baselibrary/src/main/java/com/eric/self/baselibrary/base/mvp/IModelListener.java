package com.eric.self.baselibrary.base.mvp;

/**
 * Created by eric on 2017/9/29.
 */

public interface IModelListener<T> {

    void onStartLoading();

    void onStopLoading();

    void onError(Throwable throwable);

    void onSuccess(T t);

}
