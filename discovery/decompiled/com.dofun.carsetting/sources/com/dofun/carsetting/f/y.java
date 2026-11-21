package com.dofun.carsetting.f;

import android.tw.john.TWUtil;

/* JADX INFO: compiled from: VoiceUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class y {
    public static String a() {
        try {
            TWUtil tWUtil = new TWUtil();
            if (tWUtil.open((short[]) null) != 0) {
                return "-1";
            }
            String str = String.format("%x", Integer.valueOf(tWUtil.write(65521)));
            tWUtil.close();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }
}
