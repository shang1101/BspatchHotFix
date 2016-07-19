package com.shang.bspatchhotfix.bspatch;

/**
 * Created by wangyan-pd on 2016/7/18.
 */
public class BsPatch {
    static {
        System.loadLibrary("bspatch");
    }

    public native static int bspatch(String olddex, String newdex, String patch);
}
