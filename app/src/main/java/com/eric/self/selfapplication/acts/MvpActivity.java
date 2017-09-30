package com.eric.self.selfapplication.acts;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.eric.self.baselibrary.adapter.recycler.ComAdapter;
import com.eric.self.baselibrary.base.BaseActivity;
import com.eric.self.baselibrary.holder.recycler.ViewHolder;
import com.eric.self.selfapplication.R;
import com.eric.self.selfapplication.bean.Repository;
import com.eric.self.selfapplication.presenter.GitHubPresenter;
import com.eric.self.selfapplication.presenter.IGitHubPresenter;
import com.eric.self.selfapplication.view.IGithubView;

import java.util.List;

public class MvpActivity extends BaseActivity implements IGithubView<List<Repository>> {

    private RecyclerView mvpList;
    private IGitHubPresenter mGitHubPresenter = new GitHubPresenter(this);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initialize();
    }

    private void initialize() {
        mvpList = (RecyclerView) findViewById(R.id.mvpList);
        mvpList.setLayoutManager(new LinearLayoutManager(this));
        mGitHubPresenter.getGitHubJavaRepos();
    }

    @Override
    public void requestSuccess(List<Repository> repositories) {
        mvpList.setAdapter(new ComAdapter<Repository>(this, repositories, R.layout.item_list_repos) {
            @Override
            public void covert(ViewHolder holder, Repository repos) {
                holder.setImage(R.id.headImg, repos.getOwner().getAvatar_url());
                TextView tv1 = (TextView) holder.getView(R.id.desc);
                tv1.setText(tv1.getText().toString().replace("$", repos.getDescription()));
                TextView tv2 = (TextView) holder.getView(R.id.star);
                tv2.setText(tv2.getText().toString().replace("$", repos.getForks_count() + ""));
            }
        });
    }
}
