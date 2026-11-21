package com.tw.core.c;

import android.content.Context;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import java.io.File;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.q, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0037q extends com.tw.core.i.b implements com.tw.core.h.d {
    private Context eR;

    public C0037q(Context context) {
        this.eR = context;
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("LogStatusManager", "emqMessageChanged %s", eVar);
        try {
            boolean zEquals = "1".equals(new JSONObject(eVar.jk).optString("status"));
            com.tw.core.f.h.nq(eVar.jl, zEquals);
            C0064r.bA(this.eR, "key_log_status", zEquals);
            if (!zEquals) {
                File file = new File(com.tw.core.utils.E.cz() + "/log/twcore.df");
                if (file.exists()) {
                    com.tw.core.utils.B.ci("LogStatusManager", "日志存在 删除 %s", Boolean.valueOf(file.delete()));
                }
            }
            C0065s.aa = zEquals;
            com.tw.core.utils.B.ao = zEquals;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return "0700";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return "";
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
