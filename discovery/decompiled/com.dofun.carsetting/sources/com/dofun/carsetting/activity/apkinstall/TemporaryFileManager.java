package com.dofun.carsetting.activity.apkinstall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class TemporaryFileManager extends BroadcastReceiver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final String f96a = "TemporaryFileManager";

    @RequiresApi(api = 21)
    public static File a(Context context) {
        return new File(context.getNoBackupFilesDir(), "install_results.xml");
    }

    @Override // android.content.BroadcastReceiver
    @RequiresApi(api = 21)
    public void onReceive(Context context, Intent intent) {
        long jCurrentTimeMillis = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        File[] fileArrListFiles = context.getNoBackupFilesDir().listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        for (File file : fileArrListFiles) {
            if (jCurrentTimeMillis > file.lastModified()) {
                if (!file.delete()) {
                    Log.w(f96a, "Could not delete " + file.getName() + " onBoot");
                }
            } else {
                Log.w(f96a, file.getName() + " was created before onBoot broadcast was received");
            }
        }
    }
}
