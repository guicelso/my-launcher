package com.tw.service;

import android.text.TextUtils;
import android.tw.john.TWUtil;

/* JADX INFO: loaded from: classes.dex */
public class la extends TWUtil {
    private static la pa = new la(16);
    private static int mCount = 0;
    public static int mModel = -1;
    public static int Tf = 0;

    public la(int i) {
        super(i);
    }

    public static int d(String str) {
        int i = 0;
        if (!TextUtils.isEmpty(str) && str.length() >= 2) {
            try {
                String upperCase = str.toUpperCase();
                char c = upperCase.toCharArray()[0];
                i = c > 'F' ? ((c - 'G') * 16) + 256 + Integer.parseInt(String.valueOf(upperCase.toCharArray()[1]), 16) : Integer.parseInt(upperCase.substring(0, 2), 16);
            } catch (Exception unused) {
            }
        }
        return i;
    }

    public static la open() {
        int i = mCount;
        mCount = i + 1;
        if (i == 0) {
            if (pa.open(new short[]{267, 528, 516, 258, 260, 262, 266, 267, 524, 274, 513, 514, 515, 517, 523, 769, 770, 773, 774, 1028, 1281, 1285, 1287, 1288, 1289, 1294, -25088, -25071, -24816, -24805, -24804}) != 0) {
                mCount--;
                return null;
            }
            pa.start();
        }
        return pa;
    }

    public int a(int i, int i2, int i3) {
        return write(1281, i, 2, new byte[]{(byte) i2, (byte) i3});
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

    public int g(int i, int i2) {
        return a(i, i2, 0);
    }
}
