package com.eric.self.selfapplication.presenter;

import com.eric.self.baselibrary.base.mvp.IModelListener;
import com.eric.self.baselibrary.base.mvp.MvpPresenter;
import com.eric.self.selfapplication.bean.GitHubRepos;
import com.eric.self.selfapplication.model.GithubModel;
import com.eric.self.selfapplication.model.IGithubModel;
import com.eric.self.selfapplication.view.IGithubView;

/**
 * Created by eric on 2017/9/29.
 */

public class GitHubPresenter extends MvpPresenter<GitHubRepos> implements IGitHubPresenter, IModelListener<GitHubRepos> {
    private IGithubView mMvpView;
    private IGithubModel mGithubModel;

    public GitHubPresenter(IGithubView githubView) {
        super(githubView);
        mMvpView = githubView;
        mGithubModel = new GithubModel(this);
    }

    @Override
    public void getGitHubJavaRepos() {
        this.mGithubModel.getGitHubJavaRepos();
    }


    @Override
    public void onSuccess(GitHubRepos gitHubRepos) {
        this.mMvpView.requestSuccess(gitHubRepos.getItems());
    }
}
