package com.eric.self.baselibrary.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by eric on 2017/9/11.
 */

public class SnackbarUtil {

    public static void showLong(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showShort(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void showLong(String message, View view, String action, View.OnClickListener onClickListener) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(action, onClickListener).show();
    }

    public static void showShort(String message, View view, String actino, View.OnClickListener onClickListener) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction(actino, onClickListener);
    }
}
