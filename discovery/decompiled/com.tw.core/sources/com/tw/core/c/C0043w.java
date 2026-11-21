package com.tw.core.c;

import android.content.Context;
import android.text.TextUtils;
import com.tw.core.utils.C0048b;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/* JADX INFO: renamed from: com.tw.core.c.w, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0043w extends com.tw.core.i.b implements com.tw.core.h.d {
    private Context fg;
    private String fh;
    private String fi;

    public C0043w(Context context) {
        this.fg = context;
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("GpsPermissionManager", "emqMessageChanged %s", eVar);
        C0048b.p().execute(new ab(this, eVar));
    }

    @Override // com.tw.core.i.b, com.tw.core.h.f
    public void jc(boolean z, String str) {
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return null;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0311";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return "";
    }

    public String ll() {
        if (TextUtils.isEmpty(this.fh)) {
            this.fh = "/system/etc/location.wl";
        }
        return this.fh;
    }

    public String lm() {
        if (TextUtils.isEmpty(this.fi)) {
            this.fi = "/data/tw/location.wl";
        }
        return this.fi;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0051 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public boolean ln() throws Throwable {
        BufferedReader bufferedReader;
        File file = new File(ll());
        if (file.exists()) {
            try {
                bufferedReader = new BufferedReader(new FileReader(file.getPath()));
                try {
                    try {
                        boolean z = bufferedReader.readLine().split(" ").length == 3;
                        if (bufferedReader == null) {
                            return z;
                        }
                        try {
                            bufferedReader.close();
                            return z;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return z;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        e.printStackTrace();
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        return false;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                throw th;
            }
        }
        return false;
    }

    public boolean lo() {
        return new File(ll()).exists();
    }

    public void lp(String str, String str2) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(com.tw.core.utils.E.cM(str));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(str2);
        outputStreamWriter.flush();
        outputStreamWriter.close();
        fileOutputStream.close();
        com.tw.core.utils.M.du("chmod 777 " + str);
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
