package com.eric.self.photolibrary;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.eric.self.photolibrary.util.StorageUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.eric.self.photolibrary.test", appContext.getPackageName());
        Log.e("/data", Environment.getDataDirectory().getPath());
        Log.e("/storage",Environment.getExternalStorageDirectory().getPath());
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = StorageUtil.getCacheDir(appContext);
        Log.e("/file",file.getAbsolutePath());
        Log.e("/path",path);
    }
}
