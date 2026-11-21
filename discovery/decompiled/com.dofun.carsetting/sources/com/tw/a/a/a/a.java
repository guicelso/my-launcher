package com.tw.a.a.a;

import android.content.Context;
import android.net.NetworkTemplate;
import android.telephony.TelephonyManager;
import android.telephony.TelephonyManagerEx;
import com.tw.a.a.b.b;

/* JADX INFO: compiled from: NetworkManager.java */
/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static volatile a f561a;

    private a() {
    }

    public static a a() {
        if (f561a == null) {
            synchronized (a.class) {
                if (f561a == null) {
                    f561a = new a();
                }
            }
        }
        return f561a;
    }

    public String a(Context context) {
        if (context == null) {
            return "";
        }
        com.tw.a.a.b.a.C0025a c0025aA = new com.tw.a.a.b.a(context).a(NetworkTemplate.buildTemplateMobileAll(((TelephonyManager) context.getSystemService(TelephonyManager.class)).getSubscriberId()));
        return c0025aA.b + " " + ((Object) b.a(context, c0025aA.f));
    }

    public void a(Context context, int i, boolean z) {
        TelephonyManagerEx.from(context).setSimEnabled(i, z);
    }

    public boolean a(Context context, int i) {
        return TelephonyManagerEx.from(context).isSimEnabled(i);
    }
}
