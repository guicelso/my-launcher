package com.tw.core.d;

import com.tw.core.b.e;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.J;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static final String gK;
    public static final String gL;
    public static final String gM;
    public static final String gN;
    public static final String gO;
    public static final String gP;
    public static final String gQ;
    public static String gR;
    public static final String gS;
    public static final String gT;
    public static final String gU;
    public static final String gV;
    public static final String gW;
    public static final String gX;
    public static final String gY;
    public static String gZ;
    public static final String ha;
    public static final String hb;
    public static final String hc;
    public static final String hd;
    public static final String he;

    static {
        if (e.gh()) {
            gZ = J.aI ? "https://test.cardoor.cn/core/api/" : "http://core.car.cardoor.cn/core/api/";
            gR = J.aI ? "tcp://mqtt.car.test.cardoor.cn:1883" : "tcp://mqtt.car.cardoor.cn:1883";
        } else {
            gZ = J.aI ? "https://overseas.dev.cardoor.cn/core/api/" : "http://core.car.dofuncar.com/core/api/";
            gR = J.aI ? "tcp://overseas.dev.cardoor.cn:1883" : "tcp://mqtt.car.dofuncar.com";
        }
        gK = gZ + (C0062p.aU() ? "car/init" : "carsolution/init");
        ha = gZ + "car/activate";
        gN = gZ + "bind/binded?";
        gT = gZ + "flowcard/simplecheck?";
        he = gZ + "upgradeauto/upinfo?";
        hd = gZ + "upgradeauto/upresult";
        gP = gZ + "serviceid/car?";
        gL = gZ + (C0062p.aU() ? "car/baseinfocyele" : "carsolution/baseinfocyele");
        gM = gZ + "car/gpsinfo";
        gQ = gZ + "common/action/parseUid?";
        gO = gZ + "account/car/refreshtoken?";
        hc = gZ + "logsystemcatch/upload";
        gY = gZ + "logsystemcatch/logreceipt";
        hb = gZ + "logappcatch/upload";
        gU = gZ + "logappcatch/logreceipt";
        gV = gZ + "logappcatch/logswitchreceipt";
        gX = gZ + "carcore/logconfig";
        gS = gZ + "carcore/appconfig";
        gW = gZ + "carcollect/acc";
    }
}
