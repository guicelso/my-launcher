package com.tw.service;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
final class ga extends Handler {
    final /* synthetic */ TWService n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ga(TWService tWService, Looper looper) {
        super(looper);
        this.n = tWService;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x04c6  */
    /* JADX WARN: Code duplicated, block: B:104:0x04eb  */
    /* JADX WARN: Code duplicated, block: B:125:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:92:0x0473  */
    /* JADX WARN: Code duplicated, block: B:93:0x048c  */
    /* JADX WARN: Code duplicated, block: B:95:0x0496  */
    /* JADX WARN: Code duplicated, block: B:96:0x04a6  */
    /* JADX WARN: Code duplicated, block: B:98:0x04b0  */
    @Override // android.os.Handler
    public void handleMessage(Message message) throws Throwable {
        String str;
        TWService tWService;
        Intent intentA;
        la laVar;
        StringBuilder sb;
        String absolutePath;
        Intent intent = (Intent) message.obj;
        if (intent.getAction().equals("android.intent.action.MEDIA_UNMOUNTED")) {
            Log.i("TWService", "ACTION_MEDIA_UNMOUNTED:usb");
            String path = intent.getData().getPath();
            if (path.equals("/mnt/sdcard")) {
                this.n.pa.write(40479, 3, 0);
                return;
            } else if (path.startsWith("/storage/extsd")) {
                this.n.pa.write(40479, 1, 0, path.substring(9));
                return;
            } else {
                if (path.startsWith("/storage/usb")) {
                    this.n.pa.write(40479, 2, 0, path.substring(9));
                    return;
                }
                return;
            }
        }
        if (intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
            String path2 = intent.getData().getPath();
            if (message.what < 0 || new File(path2).canRead()) {
                if (!path2.equals("/mnt/sdcard")) {
                    if (path2.startsWith("/storage/usb")) {
                        Log.e("TWService", "ACTION_MEDIA_MOUNTED:usb:" + path2);
                        try {
                            File file = new File(path2 + "/ts18-e1a1-x");
                            File file2 = new File(path2 + "/ts18-e1a1-y");
                            File file3 = new File(path2 + "/ts18-e1a1-z");
                            Log.d("TWService", "path:disableAutoUpdateFile:" + file.getAbsolutePath() + " exists:" + file.exists());
                            Log.d("TWService", "path:disableAutoImportFile:" + file2.getAbsolutePath() + " exists:" + file2.exists());
                            Log.d("TWService", "path:autoRebootByUpdateBoot:" + file3.getAbsolutePath() + " exists:" + file3.exists());
                            String strR = TWService.r();
                            String strK = this.n.k(path2 + "/deviceid");
                            Log.d("TWService", "ACTION_MEDIA_MOUNTED:deviceCid:" + strR + " saveCid:" + strK);
                            if (!new File(path2 + "/deviceid").exists() || TextUtils.equals(strR, strK)) {
                                str = "/storage/usb";
                            } else {
                                str = "/storage/usb";
                                try {
                                    this.n.pa.write(40730, 3, 0, "rm " + file.getAbsolutePath());
                                    this.n.pa.write(40730, 3, 0, "rm " + file2.getAbsolutePath());
                                    this.n.pa.write(40730, 3, 0, "rm " + file3.getAbsolutePath());
                                    this.n.pa.write(40730, 3, 0, "rm " + path2 + "/deviceid");
                                } catch (Exception unused) {
                                }
                            }
                            if (file.exists() && file2.exists()) {
                                this.n.pa.write(40730, 3, 0, "rm " + file.getAbsolutePath());
                                laVar = this.n.pa;
                                sb = new StringBuilder();
                                sb.append("rm ");
                                absolutePath = file2.getAbsolutePath();
                            } else if (!file.exists() && file2.exists()) {
                                laVar = this.n.pa;
                                sb = new StringBuilder();
                                sb.append("rm ");
                                absolutePath = file2.getAbsolutePath();
                            } else if (!file.exists() || file2.exists()) {
                                File file4 = new File(path2 + "/ts18-e1a1");
                                Log.d("TWService", "path:cpDirPath:" + file4.getAbsolutePath() + " exists:" + file4.exists());
                                if (file4.exists() && file4.isDirectory() && file4.canRead()) {
                                    try {
                                        this.n.ib = true;
                                        this.n.startActivity(this.n.a(3, path2));
                                    } catch (Exception unused2) {
                                        this.n.ib = true;
                                        intentA = this.n.a(3, path2);
                                        this.n.startActivityAsUser(intentA, UserHandle.CURRENT);
                                    }
                                } else {
                                    File file5 = new File(path2 + "/ts18.cfg");
                                    Log.d("TWService", "path:cfg file path:" + file5.getAbsolutePath() + " exists:" + file5.exists());
                                    if (file5.exists() && file5.isFile() && file5.canRead()) {
                                        try {
                                            this.n.ib = true;
                                            this.n.startActivity(this.n.a(4, path2));
                                        } catch (Exception unused3) {
                                            this.n.ib = true;
                                            tWService = this.n;
                                            intentA = tWService.a(-1, (String) null);
                                            this.n.startActivityAsUser(intentA, UserHandle.CURRENT);
                                        }
                                    }
                                }
                            } else if (file3.exists()) {
                                this.n.pa.write(40730, 3, 0, "rm " + file3.getAbsolutePath());
                                this.n.pa.write(40730, 3, 0, "sync");
                                try {
                                    this.n.ib = true;
                                    this.n.startActivity(this.n.a(5, path2));
                                } catch (Exception e) {
                                    Log.e("TWService", "ACTION_MEDIA_MOUNTED:" + e.getMessage());
                                }
                            } else {
                                File file6 = new File(path2 + "/ts18.cfg");
                                if (file6.exists() && file6.isFile() && file6.canRead()) {
                                    try {
                                        this.n.ib = true;
                                        this.n.startActivity(this.n.a(4, path2));
                                    } catch (Exception unused4) {
                                        this.n.ib = true;
                                        tWService = this.n;
                                        intentA = tWService.a(-1, (String) null);
                                        this.n.startActivityAsUser(intentA, UserHandle.CURRENT);
                                    }
                                } else {
                                    this.n.pa.write(40730, 3, 0, "rm " + file.getAbsolutePath());
                                }
                            }
                            sb.append(absolutePath);
                            laVar.write(40730, 3, 0, sb.toString());
                        } catch (Exception unused5) {
                            str = "/storage/usb";
                        }
                    }
                    new C0039c().g(path2);
                    TWService.Ba(this.n);
                    if (path2.startsWith("/mnt/sdcard")) {
                        this.n.pa.write(40479, 3, 0);
                        this.n.pa.write(40479, 3, 1);
                    } else if (path2.startsWith("/storage/extsd")) {
                        this.n.pa.write(40479, 1, 1, path2.substring(9));
                    } else if (path2.startsWith(str)) {
                        this.n.pa.write(40479, 2, 1, path2.substring(9));
                    }
                    if (this.n.Ca) {
                        this.n.mHandler.removeMessages(65290);
                        this.n.mHandler.sendEmptyMessageDelayed(65290, 5000L);
                    }
                    if (this.n.t().contains("Dat")) {
                        this.n.mHandler.removeMessages(65312);
                        this.n.mHandler.sendEmptyMessageDelayed(65312, 5000L);
                    }
                }
                path2 = b.a.a.a.a.a(path2, "/iNand");
                new File(path2).mkdir();
                str = "/storage/usb";
                new C0039c().g(path2);
                TWService.Ba(this.n);
                if (path2.startsWith("/mnt/sdcard")) {
                    this.n.pa.write(40479, 3, 0);
                    this.n.pa.write(40479, 3, 1);
                } else if (path2.startsWith("/storage/extsd")) {
                    this.n.pa.write(40479, 1, 1, path2.substring(9));
                } else if (path2.startsWith(str)) {
                    this.n.pa.write(40479, 2, 1, path2.substring(9));
                }
                if (this.n.Ca) {
                    this.n.mHandler.removeMessages(65290);
                    this.n.mHandler.sendEmptyMessageDelayed(65290, 5000L);
                }
                if (this.n.t().contains("Dat")) {
                    this.n.mHandler.removeMessages(65312);
                    this.n.mHandler.sendEmptyMessageDelayed(65312, 5000L);
                }
            }
        }
    }
}
