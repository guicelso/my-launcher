package com.tw.core.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import java.io.File;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class ai implements Runnable {
    final /* synthetic */ Context bw;

    ai(Context context) {
        this.bw = context;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        List<PackageInfo> installedPackages = this.bw.getPackageManager().getInstalledPackages(0);
        StringBuffer stringBuffer = new StringBuffer("");
        int size = installedPackages.size();
        for (int i = 0; i < size; i++) {
            PackageInfo packageInfo = installedPackages.get(i);
            String str = packageInfo.packageName;
            String str2 = packageInfo.versionName;
            int i2 = packageInfo.versionCode;
            stringBuffer.append(str);
            stringBuffer.append(",");
            stringBuffer.append(str2);
            stringBuffer.append(",");
            stringBuffer.append(i2);
            if (i < size - 1) {
                stringBuffer.append("\n");
            }
        }
        E.cD(E.cz() + File.separator + "apk.df", stringBuffer.toString(), false);
    }
}
