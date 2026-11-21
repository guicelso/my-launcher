package com.sprd.engineermode.debuglog;

import android.tw.john.TWUtil;

/* JADX INFO: loaded from: classes4.dex */
public class TWImei extends TWUtil {
    private static TWImei mTW = new TWImei();
    private static int mCount = 0;

    public TWImei() {
    }

    public TWImei(int clazz) {
        super(clazz);
    }

    public static TWImei open() {
        int i = mCount;
        mCount = i + 1;
        if (i == 0) {
            if (mTW.open(new short[0]) != 0) {
                mCount--;
                return null;
            }
            mTW.start();
        }
        return mTW;
    }

    public void close() {
        int i = mCount;
        if (i > 0) {
            int i2 = i - 1;
            mCount = i2;
            if (i2 == 0) {
                stop();
                super.close();
            }
        }
    }
}
