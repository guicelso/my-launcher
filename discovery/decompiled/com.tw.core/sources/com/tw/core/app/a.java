package com.tw.core.app;

import com.tw.core.b.e;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static String iC;
    public static String iD;
    public static String iE;
    public static String iF;
    public static String iG;
    public static String iH;

    static {
        if (e.gh()) {
            iC = "dofun";
        } else {
            iC = "overseas";
        }
        iD = iC + "/cloud/collect/gps";
        iE = iC + "/car/config/";
        iF = iC + "/car/config/#";
        iG = iC + "/car/private/";
        iH = iC + "/cloud/request/baidu/serverid";
    }
}
