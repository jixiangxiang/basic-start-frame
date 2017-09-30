package com.eric.self.photolibrary.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by eric on 2017/8/14.
 */

public class StorageUtil {
    /**
     * Check if the primary "external" storage device is available.
     *
     * @return
     */
    private static boolean hasSDCardMounted() {
        String state = Environment.getExternalStorageState();
        if (state != null && state.equals(Environment.MEDIA_MOUNTED)) {//有外部存储
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取缓存路径
     *
     * @param context
     * @return
     */
    public static File getCacheDir(Context context) {
        // 获取保存的文件夹路径
        File file;
        if (hasSDCardMounted()) {
            file = context.getExternalCacheDir();
        } else {
            file = context.getCacheDir();
        }
        return file;
    }
}
