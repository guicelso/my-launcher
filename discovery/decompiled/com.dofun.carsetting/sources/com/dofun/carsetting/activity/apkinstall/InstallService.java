package com.dofun.carsetting.activity.apkinstall;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.dofun.carsetting.R;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class InstallService extends Service {
    private static c i;
    private b g;
    private final IBinder b = new a();
    private final int c = 1;
    private final int d = 2;
    private final int e = 3;
    private final int f = 4;
    private Handler h = new Handler() { // from class: com.dofun.carsetting.activity.apkinstall.InstallService.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 1:
                        Log.d("LZP", "开始安装  ");
                        Toast.makeText(InstallService.this.getApplicationContext(), InstallService.this.f92a + " " + InstallService.this.getString(R.string.make_install), 1).show();
                        break;
                    case 2:
                        Toast.makeText(InstallService.this.getApplicationContext(), InstallService.this.getString(R.string.install_success), 0).show();
                        c unused = InstallService.i = null;
                        break;
                    case 3:
                        Log.d("LZP", "取消安装");
                        Toast.makeText(InstallService.this.getApplicationContext(), InstallService.this.getString(R.string.cancel_install), 0).show();
                        break;
                    case 4:
                        Log.d("LZP", "安装完成");
                        String string = message.getData().getString("onProgressUpdate");
                        if (!TextUtils.isEmpty(string)) {
                            Toast.makeText(InstallService.this.getApplicationContext(), InstallService.this.f92a + " " + InstallService.this.getString(R.string.install_complete), 0).show();
                            StringBuilder sb = new StringBuilder();
                            sb.append("value= ");
                            sb.append(string);
                            Log.d("LZP", sb.toString());
                            if (InstallService.i != null) {
                                InstallService.i.a(string);
                            }
                        }
                        break;
                }
            } catch (Exception unused2) {
            }
        }
    };

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String f92a = "";

    interface c {
        void a(String str);
    }

    public class a extends Binder {
        public a() {
        }

        public InstallService a() {
            return InstallService.this;
        }
    }

    private class b extends AsyncTask<File, String, String> {
        private b() {
        }

        @Override // android.os.AsyncTask
        protected void onCancelled() {
            super.onCancelled();
            InstallService.this.h.removeMessages(3);
            InstallService.this.h.sendEmptyMessage(3);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            InstallService.this.h.removeMessages(2);
            InstallService.this.h.sendEmptyMessage(2);
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            InstallService.this.h.removeMessages(1);
            InstallService.this.h.sendEmptyMessage(1);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onProgressUpdate(String... strArr) {
            super.onProgressUpdate(strArr);
            InstallService.this.h.removeMessages(4);
            Message messageObtainMessage = InstallService.this.h.obtainMessage();
            messageObtainMessage.what = 4;
            Bundle bundle = new Bundle();
            bundle.putString("onProgressUpdate", strArr[0]);
            messageObtainMessage.setData(bundle);
            InstallService.this.h.sendMessage(messageObtainMessage);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public String doInBackground(File... fileArr) throws Throwable {
            if (fileArr == null) {
                return "install All End";
            }
            for (File file : fileArr) {
                if (isCancelled()) {
                    return "install All End";
                }
                String strA = InstallService.this.a(file.getAbsolutePath());
                if (strA.contains("Success=")) {
                    publishProgress(strA.split("=")[1]);
                } else {
                    publishProgress("");
                }
            }
            return "install All End";
        }
    }

    public void a(File[] fileArr, c cVar) {
        if (fileArr == null || fileArr.length == 0) {
            return;
        }
        a(cVar);
        if (this.g == null) {
            this.g = (b) new b().execute(fileArr);
        } else {
            this.g.cancel(true);
            this.g = (b) new b().execute(fileArr);
        }
    }

    public static void a(c cVar) {
        i = cVar;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.b;
    }

    /* JADX WARN: Code duplicated, block: B:102:0x0140  */
    /* JADX WARN: Code duplicated, block: B:105:0x012f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:125:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:64:0x00d4 A[Catch: IOException -> 0x00d0, TRY_LEAVE, TryCatch #6 {IOException -> 0x00d0, blocks: (B:60:0x00cc, B:64:0x00d4), top: B:110:0x00cc }] */
    /* JADX WARN: Code duplicated, block: B:77:0x00ee A[Catch: IOException -> 0x00ea, TRY_LEAVE, TryCatch #7 {IOException -> 0x00ea, blocks: (B:73:0x00e6, B:77:0x00ee), top: B:112:0x00e6 }] */
    /* JADX WARN: Code duplicated, block: B:81:0x00f7 A[PHI: r3 r4 r7
      0x00f7: PHI (r3v7 ??) = (r3v4 ??), (r3v5 ??), (r3v11 ??) binds: [B:67:0x00db, B:80:0x00f5, B:32:0x009f] A[DONT_GENERATE, DONT_INLINE]
      0x00f7: PHI (r4v10 ??) = (r4v7 ??), (r4v8 ??), (r4v18 ??) binds: [B:67:0x00db, B:80:0x00f5, B:32:0x009f] A[DONT_GENERATE, DONT_INLINE]
      0x00f7: PHI (r7v18 ??) = (r7v8 ??), (r7v9 ??), (r7v19 ??) binds: [B:67:0x00db, B:80:0x00f5, B:32:0x009f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:84:0x0106  */
    /* JADX WARN: Code duplicated, block: B:97:0x0135 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:98:0x0137 A[Catch: IOException -> 0x0133, TRY_LEAVE, TryCatch #2 {IOException -> 0x0133, blocks: (B:94:0x012f, B:98:0x0137), top: B:105:0x012f }] */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x009f, code lost:
    
        if (r7 != 0) goto L81;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r2v10, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v11, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v8, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.lang.ProcessBuilder] */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v18, types: [java.lang.Process] */
    /* JADX WARN: Type inference failed for: r7v19, types: [java.lang.Process] */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v20 */
    /* JADX WARN: Type inference failed for: r7v21 */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v23 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6, types: [java.lang.Process] */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String a(java.lang.String r8) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 325
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.dofun.carsetting.activity.apkinstall.InstallService.a(java.lang.String):java.lang.String");
    }
}
