package com.eric.self.selfapplication.model;

import com.eric.self.baselibrary.base.App;
import com.eric.self.baselibrary.base.mvp.CommonAction0;
import com.eric.self.baselibrary.base.mvp.CommonSubscriber;
import com.eric.self.baselibrary.base.mvp.IModelListener;
import com.eric.self.baselibrary.http.retrofit.RetrofitManager;
import com.eric.self.selfapplication.bean.GitHubRepos;
import com.eric.self.selfapplication.bean.Repository;
import com.eric.self.selfapplication.service.GitHubService;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by eric on 2017/9/29.
 */

public class GithubModel implements IGithubModel {
    private IModelListener modelListener;
    private GitHubService mGitHubService;

    public GithubModel(IModelListener modelListener) {
        this.modelListener = modelListener;
        mGitHubService = RetrofitManager.newInstance(App.getContext()).getService(GitHubService.class);
    }

    @Override
    public void getGitHubJavaRepos() {
        mGitHubService.getGitJavaRepos().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new CommonAction0(this.modelListener))
                .subscribe(new CommonSubscriber<GitHubRepos>(this.modelListener));
    }
}
