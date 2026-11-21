package com.tw.core.f;

import com.tw.core.utils.B;
import com.tw.core.utils.C0058l;
import com.tw.core.utils.E;
import com.tw.core.utils.N;
import java.io.File;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class g implements Runnable {
    private String in;
    private File io;
    private String ip;
    private String iq;
    private File ir;
    private String is;

    public g(String str, File file) {
        this(str, file, null);
    }

    public g(String str, File file, String str2) {
        this.is = com.tw.core.d.a.hc;
        this.iq = str;
        this.io = file;
        this.in = str2;
        this.ip = file.getName();
    }

    public void nk(String str) {
        this.is = str;
    }

    public void nl() {
        B.ci("LogUploader", "begin upload -- %s", this.iq);
        if (this.iq == null || this.io == null || !this.io.exists()) {
            return;
        }
        String str = this.ip + "__COMPRESS";
        File file = new File(this.io.getParent() + File.separator + str);
        if (!file.exists()) {
            E.cJ(this.io, str);
        }
        if (file.exists()) {
            this.io = file;
            B.ci("LogUploader", "want to compress file path %s", this.io.getAbsolutePath());
            String str2 = this.io.getParent() + File.separator + this.ip + "__UPLOADING.zip";
            this.ir = new File(str2);
            if (!this.ir.exists()) {
                B.ci("LogUploader", "begin compress file", new Object[0]);
                this.ir = N.dv(this.io.getAbsolutePath(), str2);
            }
            if (this.ir != null) {
                B.ci("LogUploader", "compress file path %s", this.ir.getAbsolutePath());
                f.nh().nj(this);
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        B.cj("LogUploader", "system info %s", com.tw.core.b.b.fP());
        B.ci("LogUploader", "begin upload %s", this.ir.getName());
        HashMap map = new HashMap();
        map.put("logId", this.iq);
        HashMap map2 = new HashMap();
        map2.put("logFile", this.ir);
        C0058l.ao(this.is, map, map2, new l(this));
    }
}
