package com.eric.self.photolibrary.choose;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.eric.self.baselibrary.util.SPUtils;
import com.eric.self.baselibrary.util.SnackbarUtil;
import com.eric.self.photolibrary.R;
import com.eric.self.photolibrary.util.StorageUtil;

import java.io.File;
import java.util.List;

/**
 * Created by eric on 2017/9/5.
 */

public class DialogUtil implements DialogInterface.OnClickListener {
    private Context mContext;
    private static DialogUtil dialogUtil;
    private AlertDialog mDialog;
    private String mAuthorities;

    public static final int CHOOSE_PHOTO_DIALOG = 101;
    public static final int CAMERA_REQUEST = 1001;
    public static final int IMAGE_REQUEST = 1002;
    public static final int IMAGE_CROP_REQUEST = 1003;
    public static final int PERMISSION_HEAD = 1004;
    public static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1005;
    public static final String TMP_IMG_PATH = "filepath";

    private DialogUtil(Context context) {
        mContext = context;
    }

    public static DialogUtil newInstance(Context context) {
        if (dialogUtil == null) {
            dialogUtil = new DialogUtil(context);
        }
        return dialogUtil;
    }

    public DialogUtil authorities(String authorities) {
        this.mAuthorities = authorities;
        return dialogUtil;
    }

    public DialogUtil showDialog(int type, Integer bgColor, Integer itemColor, Integer wordColor, Float wordSize) {

        switch (type) {
            case CHOOSE_PHOTO_DIALOG:
                View view = dialogUtil.createChoosePhotoDialog(this.mAuthorities);
                if (bgColor != null)
                    view.setBackgroundColor(bgColor);
                if (itemColor != null) {
                    view.findViewById(R.id.takePhotoBtn).setBackgroundColor(itemColor);
                    view.findViewById(R.id.albumBtn).setBackgroundColor(itemColor);
                    view.findViewById(R.id.dissBtn).setBackgroundColor(itemColor);
                }
                if (wordColor != null) {
                    ((Button) view.findViewById(R.id.takePhotoBtn)).setTextColor(wordColor);
                    ((Button) view.findViewById(R.id.albumBtn)).setTextColor(wordColor);
                    ((Button) view.findViewById(R.id.dissBtn)).setTextColor(wordColor);
                }
                if (wordSize != null) {
                    ((Button) view.findViewById(R.id.takePhotoBtn)).setTextSize(wordSize);
                    ((Button) view.findViewById(R.id.albumBtn)).setTextSize(wordSize);
                    ((Button) view.findViewById(R.id.dissBtn)).setTextSize(wordSize);
                }
                dialogUtil.show(view);
                break;
        }
        return dialogUtil;
    }

    public DialogUtil showDialog(int type, Integer bgColor, Integer itemColor, Integer wordColor) {
        return showDialog(type, bgColor, itemColor, wordColor, null);
    }


    public DialogUtil showDialog(int type, Integer bgColor, Integer itemColor) {
        return showDialog(type, bgColor, itemColor, null);
    }

    public DialogUtil showDialog(int type, Integer bgColor) {
        return showDialog(type, bgColor, null);
    }

    public DialogUtil showDialog(int type) {
        return showDialog(type, null, null, null);
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    private View createChoosePhotoDialog(final String authorities) {
        AlertDialog.Builder mBuild = new AlertDialog.Builder(mContext, R.style.Theme_Transparent);
        mDialog = mBuild.create();
        View view = LayoutInflater.from(mContext).inflate(R.layout.photo_choose_dialog, null);
        Button takePhotoBtn = view.findViewById(R.id.takePhotoBtn);
        Button albumBtn = view.findViewById(R.id.albumBtn);
        Button dissBtn = view.findViewById(R.id.dissBtn);
        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(StorageUtil.getCacheDir(mContext), "/temp/tmp.jpg");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }
                Uri photoURI = FileProvider.getUriForFile(mContext, authorities, file);
                List<ResolveInfo> resInfoList = mContext.getPackageManager().queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    mContext.grantUriPermission(packageName, photoURI,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                SPUtils.put(mContext, TMP_IMG_PATH, file.getAbsolutePath());
                ((Activity) mContext).startActivityForResult(cameraIntent, CAMERA_REQUEST);
                mDialog.dismiss();
            }
        });
        albumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                ((Activity) mContext).startActivityForResult(intent, IMAGE_REQUEST);
                mDialog.dismiss();
            }
        });
        dissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        return view;
    }

    public void show(View view) {
        mDialog.setView(view);
        mDialog.show();
        WindowManager m = mDialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();  //获取对话框当前的参数值、
        params.width = d.getWidth();    //宽度设置全屏宽度
        mDialog.getWindow().setGravity(Gravity.BOTTOM);
        mDialog.getWindow().setAttributes(params);     //设置生效
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    /*
     * 图片裁剪
     */
    public Uri startPhotoCrop(Uri uri, View view) {
        if (uri == null) {
            SnackbarUtil.showLong("选择图片出错！", view);
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // 设置裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        ((Activity) mContext).startActivityForResult(intent, IMAGE_CROP_REQUEST);
        return uri;
    }
}
