package com.eric.self.selfapplication.service;

import com.alibaba.fastjson.JSONArray;
import com.eric.self.baselibrary.http.retrofit.RetrofitManager;
import com.eric.self.selfapplication.bean.GitHubRepos;
import com.eric.self.selfapplication.bean.Repository;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

/**
 * Created by eric on 2017/9/25.
 */

public interface GitHubService {

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("orgs/octokit/repos")
    Observable<JSONArray> getOctokitRepos();

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("search/repositories?q=android&sort=stars&order=desc")
    Observable<GitHubRepos> getGitJavaRepos();

}
