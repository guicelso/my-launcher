package com.tw.core.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import java.io.File;
import java.util.TreeMap;

/* JADX INFO: renamed from: com.tw.core.utils.q, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0063q extends Thread {
    private static final String V = C0063q.class.getSimpleName();
    private String W = "/download/";
    private String X = ".apk";
    private Context Y;

    public C0063q(Context context) {
        this.Y = context;
    }

    private void bs(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        TreeMap treeMap = new TreeMap();
        treeMap.put("appId", str);
        treeMap.put("cid", com.tw.core.b.f.gr());
        treeMap.put("strategyId", C0061o.M);
        C0058l.an(treeMap, com.tw.core.d.a.hd, new af(this));
    }

    private boolean bt(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String str2 = E.cz() + this.W + "twcore" + this.X;
        File file = new File(str2);
        if (!file.exists() || !file.isFile()) {
            C0065s.bK(V, "不存在");
            return false;
        }
        C0065s.bK(V, "存在");
        PackageInfo packageArchiveInfo = this.Y.getPackageManager().getPackageArchiveInfo(str2, 1);
        if (packageArchiveInfo == null) {
            file.delete();
            return false;
        }
        String str3 = packageArchiveInfo.packageName;
        String str4 = packageArchiveInfo.versionName;
        int i = packageArchiveInfo.versionCode;
        C0065s.bK(V, "packageName=" + str3 + "  apkVersionName=" + str4);
        if (str3.equals(this.Y.getPackageName()) && C0062p.br(str) == i) {
            C0065s.bK(V, "本地有服务器版本");
            return true;
        }
        C0065s.bK(V, "本地有版本，但是是旧版本！");
        file.delete();
        return false;
    }

    protected void bu(com.tw.core.a.a aVar) {
        try {
            if (aVar == null) {
                C0065s.bK(V, "解析为空");
                return;
            }
            int iBk = C0062p.bk(this.Y);
            String strDY = aVar.dY();
            C0065s.bK(V, "当前版本:" + iBk + "服务器版本:" + strDY);
            if (C0062p.br(strDY) <= iBk) {
                C0065s.bK(V, "当前程序为最新版本");
                return;
            }
            C0065s.bK(V, "为旧版本");
            if (bt(strDY)) {
                C0065s.bK(V, "存在已经下载的apk");
                bv();
            } else {
                if (TextUtils.isEmpty(aVar.dW())) {
                    return;
                }
                C0065s.bK(V, "开始下载");
                boolean zAt = C0058l.at(aVar.dW(), this.W, "twcore" + this.X);
                C0065s.bK(V, "downloadResult:" + zAt);
                if (zAt) {
                    bs(aVar.dX());
                }
            }
        } catch (Exception e) {
            C0065s.bL(V, e);
        }
    }

    protected void bv() {
        String str = E.cz() + this.W + "twcore" + this.X;
        C0065s.bK(V, "安装路径：" + str);
        if (new File(str).exists()) {
            C0067u.bQ(this.Y, str);
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        super.run();
        if (com.tw.core.b.a.fE(this.Y)) {
            C0061o.aQ().aR(this.Y, C0062p.bk(this.Y) + "", com.tw.core.b.b.fK(), new ae(this));
        }
    }
}
