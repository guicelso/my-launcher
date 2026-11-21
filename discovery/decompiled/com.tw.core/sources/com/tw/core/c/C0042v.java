package com.tw.core.c;

import android.content.Context;
import com.tw.core.utils.C0047a;
import com.tw.core.utils.C0064r;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.v, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0042v extends com.tw.core.i.b implements com.tw.core.h.d {
    private Context ff;

    public C0042v(Context context) {
        this.ff = context;
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void jE(boolean z) {
        if (z) {
            if (C0064r.by(this.ff, "car_init_state", false)) {
                com.tw.core.utils.B.ci("CarInitManager", "car is init", new Object[0]);
            } else {
                C0047a.l(this.ff);
            }
        }
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("CarInitManager", "emqMessageChanged %s", eVar);
        try {
            if ("0".equals(new JSONObject(eVar.jk).optString("status"))) {
                C0047a.l(this.ff);
            }
        } catch (JSONException e) {
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
        return "0505";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return null;
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
