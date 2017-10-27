package com.eric.self.selfapplication.acts;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.eric.self.baselibrary.adapter.list.ComAdapter;
import com.eric.self.baselibrary.dialog.AlertDialogUtil;
import com.eric.self.baselibrary.dialog.ProgressDialogUtil;
import com.eric.self.baselibrary.holder.list.ViewHolder;
import com.eric.self.baselibrary.http.retrofit.RetrofitManager;
import com.eric.self.baselibrary.util.SPUtils;
import com.eric.self.baselibrary.util.SnackbarUtil;
import com.eric.self.baselibrary.util.swipeback.SwipeBackActivity;
import com.eric.self.photolibrary.choose.DialogUtil;
import com.eric.self.photolibrary.ui.ScanActivity;
import com.eric.self.selfapplication.R;
import com.eric.self.selfapplication.service.GitHubService;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class MainActivity extends SwipeBackActivity {
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private ListView listView;
    private DialogUtil dialogUtil;
    private FloatingActionButton fab;
    private Uri imageUri;

    FloatingActionButton netFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.listView);
        netFab = (FloatingActionButton) findViewById(R.id.netFabB);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //定义与事件相关的属性信息
                    JSONObject eventObject = new JSONObject();
                    eventObject.put("商品分类", "手机");
                    eventObject.put("商品名称", "iPhone7 64g");
                    eventObject.put("数量", 2);
                    //记录事件,以购买为例
                    ZhugeSDK.getInstance().track(getApplicationContext(), "购买商品", eventObject);
                } catch (Exception e) {

                }
                int permission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, DialogUtil.PERMISSION_HEAD);
                } else {
                    if (dialogUtil == null || !dialogUtil.isShowing()) {
                        dialogUtil = DialogUtil.newInstance(MainActivity.this).authorities("com.eric.self.selfapplication.fileprovider");
                        dialogUtil.showDialog(DialogUtil.CHOOSE_PHOTO_DIALOG);
                    }
                }

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int permission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_QRCODE_PERMISSIONS);
                } else {
                    Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        netFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                GitHubService gitHubService = RetrofitManager.newInstance(MainActivity.this).getService(GitHubService.class);
                final ProgressDialog progressDialog = ProgressDialogUtil.createProgressDialog(MainActivity.this);
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
                                SnackbarUtil.showLong(throwable.getMessage(), view);
                            }

                            @Override
                            public void onNext(final JSONArray jsonArray) {
                                AlertDialogUtil.showConfirm(MainActivity.this, null, jsonArray.toJSONString(), "确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SnackbarUtil.showLong("====" + i, view);
                                    }
                                }, "取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SnackbarUtil.showLong("====" + i, view);
                                    }
                                });

                            }
                        });

                //SnackbarUtil.showLong(jsonObject.toString(), view);
            }
        });
        List<TestItem> items = new ArrayList<>();
        items.add(new TestItem("item1", "http://bpic.588ku.com/back_pic/05/11/08/37598fd214cc4a8.jpg!ww800"));
        items.add(new TestItem("item2", "http://bpic.588ku.com/back_pic/05/11/08/37598fd214cc4a8.jpg!ww800"));
        items.add(new TestItem("item3", "http://bpic.588ku.com/back_pic/05/11/08/37598fd214cc4a8.jpg!ww800"));
        items.add(new TestItem("item4", "http://bpic.588ku.com/back_pic/05/11/08/37598fd214cc4a8.jpg!ww800"));
        items.add(new TestItem("item5", "http://bpic.588ku.com/back_pic/05/11/08/37598fd214cc4a8.jpg!ww800"));
        listView.setAdapter(new ComAdapter<TestItem>(items, this, R.layout.test_adapter_item) {
            @Override
            public void convert(ViewHolder holder, TestItem item) {
                holder.setTextView(R.id.text, item.getTitle());
                holder.setImage(R.id.imageView, item.getUrl());
                holder.getItemView(R.id.imageView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DialogUtil.CAMERA_REQUEST && resultCode == RESULT_OK) {
            File file = new File((String) SPUtils.get(this, DialogUtil.TMP_IMG_PATH, ""));
            imageUri = FileProvider.getUriForFile(this, "com.eric.self.selfapplication.fileprovider", file);
            if (imageUri != null) {
                imageUri = dialogUtil.startPhotoCrop(imageUri, fab);
            }
        } else if (requestCode == DialogUtil.IMAGE_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imageUri = dialogUtil.startPhotoCrop(imageUri, fab);
        } else if (requestCode == DialogUtil.IMAGE_CROP_REQUEST && resultCode == RESULT_OK) {
            //Glide.with(this).load(imageUri).into(imageview);
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
                    SnackbarUtil.showLong("要使用该功能，必须允许或者在应用访问授权中打开存储空间权限", fab);
                }
                break;
            case REQUEST_CODE_QRCODE_PERMISSIONS:
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZhugeSDK.getInstance().flush(getApplicationContext());
    }

    private class TestItem {
        private String title, url;

        public TestItem(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
