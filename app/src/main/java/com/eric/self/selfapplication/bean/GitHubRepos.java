package com.eric.self.selfapplication.bean;

import java.util.List;

/**
 * Created by eric on 2017/9/30.
 */

public class GitHubRepos {
    private int total_count;
    private boolean incomplete_results;
    private List<Repository> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<Repository> getItems() {
        return items;
    }

    public void setItems(List<Repository> items) {
        this.items = items;
    }
}
