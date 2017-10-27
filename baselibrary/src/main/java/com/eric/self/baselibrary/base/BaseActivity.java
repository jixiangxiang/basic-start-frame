package com.eric.self.baselibrary.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.eric.self.baselibrary.R;
import com.eric.self.baselibrary.base.mvp.IMvpView;
import com.eric.self.baselibrary.dialog.AlertDialogUtil;
import com.eric.self.baselibrary.dialog.ProgressDialogUtil;
import com.eric.self.baselibrary.util.swipeback.SwipeBackActivity;
import com.eric.self.baselibrary.util.swipeback.SwipeBackLayout;


/**
 * Created by laucherish on 16/3/15.
 */
public abstract class BaseActivity extends SwipeBackActivity implements IMvpView {

    protected SwipeBackLayout mSwipeBackLayout;
    private ProgressDialog mProgressDialog;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            scrollToFinishActivity();
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        afterCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    @Override
    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialogUtil.createProgressDialog(this);
            mProgressDialog.show();
        }
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    @Override
    public void stopLoading() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    @Override
    public void requestError(Throwable throwable) {
        AlertDialogUtil.showAlert(this, null, throwable.getMessage(), getString(R.string.confirm));
    }
}
