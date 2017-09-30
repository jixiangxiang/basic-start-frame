package com.eric.self.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by eric on 2017/9/27.
 */

public class AlertDialogUtil {
    /**
     * @param context
     * @param title
     * @param message
     * @param customVieww
     * @param postiveText
     * @param postiveClick
     * @param negativeText
     * @param negativeClick
     * @param cacelable
     * @return
     */
    private static AlertDialog show(Context context, String title, String message, View customVieww,
                                    String postiveText, DialogInterface.OnClickListener postiveClick,
                                    String negativeText, DialogInterface.OnClickListener negativeClick,
                                    boolean cacelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null)
            builder.setTitle(title);
        if (message != null)
            builder.setMessage(message);
        if (customVieww != null)
            builder.setView(customVieww);
        if (postiveText != null)
            builder.setPositiveButton(postiveText, postiveClick);
        if (negativeText != null)
            builder.setNegativeButton(negativeText, negativeClick);
        builder.setCancelable(cacelable);
        return builder.show();
    }

    public static AlertDialog showAlert(Context context, String message, String postiveText, DialogInterface.OnClickListener postiveClick) {
        return show(context, null, message, null, postiveText, postiveClick, null, null, false);
    }

    public static AlertDialog showAlert(Context context, String title, String message, String postiveText, DialogInterface.OnClickListener postiveClick) {
        return show(context, title, message, null, postiveText, postiveClick, null, null, false);
    }

    public static AlertDialog showAlert(Context context, String title, String message, String postiveText) {
        return show(context, title, message, null, postiveText, null, null, null, false);
    }

    public static AlertDialog showAlert(Context context, String title, String message, View contentView,
                                        String postiveText, DialogInterface.OnClickListener postiveClick) {
        return show(context, title, message, contentView, postiveText, postiveClick, null, null, false);
    }

    public static AlertDialog showConfirm(Context context, String message,
                                          String postiveText, DialogInterface.OnClickListener postiveClick,
                                          String negativeText, DialogInterface.OnClickListener negativeClick) {
        return show(context, null, message, null, postiveText, postiveClick, negativeText, negativeClick, false);
    }

    public static AlertDialog showConfirm(Context context, String title, String message,
                                          String postiveText, DialogInterface.OnClickListener postiveClick,
                                          String negativeText, DialogInterface.OnClickListener negativeClick) {
        return show(context, title, message, null, postiveText, postiveClick, negativeText, negativeClick, false);
    }

    public static AlertDialog showConfirm(Context context, String title, String message, View contentView,
                                          String postiveText, DialogInterface.OnClickListener postiveClick,
                                          String negativeText, DialogInterface.OnClickListener negativeClick) {
        return show(context, title, message, contentView, postiveText, postiveClick, negativeText, negativeClick, false);
    }
}
