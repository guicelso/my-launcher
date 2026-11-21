package com.tw.core.messenger.a;

import android.content.Context;
import android.content.Intent;
import com.tw.core.utils.B;
import com.tw.core.utils.C0049c;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public ConcurrentHashMap d = new ConcurrentHashMap();

    public static b c() {
        return c.e;
    }

    public boolean d(Context context, String str, a aVar) {
        if (this.d.containsKey(str)) {
            B.ci("ServiceBinder", "%s is bind", str);
            if (aVar != null) {
                aVar.b((d) this.d.get(str));
            }
            return true;
        }
        Intent intentT = C0049c.t(context, new Intent(str));
        if (intentT == null || !context.bindService(intentT, new d(context, str, this, aVar), 1)) {
            B.cj("ServiceBinder", "%s bind error", str);
            return false;
        }
        B.ci("ServiceBinder", "%s bind success", str);
        return true;
    }
}
