package com.eric.self.selfapplication.acts;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONArray;
import com.eric.self.baselibrary.dialog.AlertDialogUtil;
import com.eric.self.baselibrary.dialog.ProgressDialogUtil;
import com.eric.self.baselibrary.http.retrofit.RetrofitManager;
import com.eric.self.baselibrary.util.SPUtils;
import com.eric.self.baselibrary.util.SnackbarUtil;
import com.eric.self.photolibrary.choose.DialogUtil;
import com.eric.self.photolibrary.ui.ScanActivity;
import com.eric.self.selfapplication.R;
import com.eric.self.selfapplication.service.GitHubService;

import java.io.File;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

import static com.eric.self.photolibrary.choose.DialogUtil.REQUEST_CODE_QRCODE_PERMISSIONS;

public class SampleListActivity extends AppCompatActivity implements View.OnClickListener {

    private Button netBtn;
    private Button photoBtn;
    private Button scanBtn;
    private Button imageBtn;
    private Button mvpBtn;
    private DialogUtil dialogUtil;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);
        initialize();
    }

    private void initialize() {
        netBtn = (Button) findViewById(R.id.netBtn);
        photoBtn = (Button) findViewById(R.id.photoBtn);
        scanBtn = (Button) findViewById(R.id.scanBtn);
        imageBtn = (Button) findViewById(R.id.imageBtn);
        mvpBtn = (Button) findViewById(R.id.mvpBtn);
        netBtn.setOnClickListener(this);
        photoBtn.setOnClickListener(this);
        scanBtn.setOnClickListener(this);
        imageBtn.setOnClickListener(this);
        mvpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == netBtn) {
            GitHubService gitHubService = RetrofitManager.newInstance(SampleListActivity.this).getService(GitHubService.class);
            final ProgressDialog progressDialog = ProgressDialogUtil.createProgressDialog(SampleListActivity.this);
            gitHubService.getOctokitRepos()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            progressDialog.show();
                        }
                    })
                    .subscribe(new Subscriber<JSONArray>() {
                        @Override
                        public void onCompleted() {
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            SnackbarUtil.showLong(throwable.getMessage(), netBtn);
                        }

                        @Override
                        public void onNext(final JSONArray jsonArray) {
                            AlertDialogUtil.showConfirm(SampleListActivity.this, null, jsonArray.toJSONString(), "确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SnackbarUtil.showLong("====" + i, netBtn);
                                }
                            }, "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SnackbarUtil.showLong("====" + i, netBtn);
                                }
                            });

                        }
                    });
        } else if (view == photoBtn) {
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, DialogUtil.PERMISSION_HEAD);
            } else {
                if (dialogUtil == null || !dialogUtil.isShowing()) {
                    dialogUtil = DialogUtil.newInstance(this).authorities("com.eric.self.selfapplication.fileprovider");
                    dialogUtil.showDialog(DialogUtil.CHOOSE_PHOTO_DIALOG);
                }
            }
        } else if (view == scanBtn) {
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_QRCODE_PERMISSIONS);
            } else {
                Intent intent = new Intent(this, ScanActivity.class);
                startActivity(intent);
            }
        } else if (view == imageBtn) {
            Intent intent = new Intent(this, ListImageActivity.class);
            startActivity(intent);
        } else if (view == mvpBtn) {
            Intent intent = new Intent(this, MvpActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DialogUtil.CAMERA_REQUEST && resultCode == RESULT_OK) {
            File file = new File((String) SPUtils.get(this, DialogUtil.TMP_IMG_PATH, ""));
            imageUri = FileProvider.getUriForFile(this, "com.eric.self.selfapplication.fileprovider", file);
            if (imageUri != null) {
                imageUri = dialogUtil.startPhotoCrop(imageUri, photoBtn);
            }
        } else if (requestCode == DialogUtil.IMAGE_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imageUri = dialogUtil.startPhotoCrop(imageUri, photoBtn);
        } else if (requestCode == DialogUtil.IMAGE_CROP_REQUEST && resultCode == RESULT_OK) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case DialogUtil.PERMISSION_HEAD:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (dialogUtil == null) {
                        dialogUtil = DialogUtil.newInstance(this).authorities("com.eric.self.selfapplication.fileprovider");
                    }
                    dialogUtil.showDialog(DialogUtil.CHOOSE_PHOTO_DIALOG);
                } else {
                    SnackbarUtil.showLong("要使用该功能，必须允许或者在应用访问授权中打开存储空间权限", photoBtn);
                }
                break;
            case DialogUtil.REQUEST_CODE_QRCODE_PERMISSIONS:
                Intent intent = new Intent(this, ScanActivity.class);
                startActivity(intent);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
