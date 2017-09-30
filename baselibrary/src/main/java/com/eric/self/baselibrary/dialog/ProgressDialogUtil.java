package com.eric.self.baselibrary.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.eric.self.baselibrary.R;

/**
 * Created by eric on 2017/9/27.
 */

public class ProgressDialogUtil {

    public static ProgressDialog createProgressDialog(Context context) {
        return new CustomProgressDialog(context, R.style.ProgressDialog);
    }
}
