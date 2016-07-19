package com.shang.bspatchhotfix.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * Created by wangyan-pd on 2016/7/18.
 */
public class DexInject {

    private static final String TAG = DexInject.class.getName();
    private static final String DEX_DIR = "classes.dex";
    private static String DEX_OPT_DIR ="dex_opt";

    public static void init(Context context) {
        File dexDir = new File(context.getFilesDir(), DEX_DIR);
        loadPatch(context, dexDir.getAbsolutePath());
    }

    private static void loadPatch(Context context, String dexPath) {

        if (context == null) {
            Log.e(TAG, "context is null");
            return;
        }
        if (!new File(dexPath).exists()) {
            Log.e(TAG, dexPath + " is null");
            return;
        }
        File dexOptDir = new File(context.getFilesDir(), DEX_OPT_DIR);
        dexOptDir.mkdir();
        try {
            DexUtils.injectDexAtFirst(dexPath, dexOptDir.getAbsolutePath());
        } catch (Exception e) {
            Log.e(TAG, "inject " + dexPath + " failed");
            e.printStackTrace();
        }
    }
}
