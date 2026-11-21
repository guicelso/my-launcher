package com.tw.core.model;

import android.content.Context;
import com.tw.core.utils.B;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0058l;
import com.tw.core.utils.C0064r;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private static final String hp = b.class.getSimpleName();
    private static b hs;
    private Context hr;
    private boolean hq = false;
    private AtomicBoolean ht = new AtomicBoolean();

    private b(Context context) {
        this.hr = context;
    }

    public static synchronized b ms(Context context) {
        if (hs == null) {
            hs = new b(context);
        }
        return hs;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mu(String str, String str2, long j) {
        HashMap map = new HashMap();
        map.put("cid", com.tw.core.b.f.gr());
        map.put("activateLatitude", str);
        map.put("activateLongitude", str2);
        map.put("activateTime", j + "");
        C0058l.an(map, com.tw.core.d.a.ha, new j(this));
    }

    public void mt() {
        if (this.ht.get()) {
            B.ci(hp, "uploadActivateResult 上传中", new Object[0]);
            return;
        }
        if (!com.tw.core.b.a.fF(this.hr)) {
            B.ci(hp, "uploadActivateResult 没有联网", new Object[0]);
            return;
        }
        if (C0064r.bE(this.hr, "server_activate_time", 0L) != 0) {
            B.ci(hp, "已经请求过激活时间", new Object[0]);
        } else if (com.tw.core.b.e.gh()) {
            C0048b.p().execute(new i(this));
        } else {
            B.ci(hp, "海外不做位移上报", new Object[0]);
        }
    }

    public void onDestroy() {
    }
}
