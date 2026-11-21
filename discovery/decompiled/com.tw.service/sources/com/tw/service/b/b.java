package com.tw.service.b;

import android.app.ActivityManagerNative;
import android.content.res.Configuration;
import android.os.LocaleList;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
class b implements Runnable {
    final /* synthetic */ String Xk;
    final /* synthetic */ c n;

    b(c cVar, String str) {
        this.n = cVar;
        this.Xk = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            Configuration configuration = ActivityManagerNative.getDefault().getConfiguration();
            Locale localeForLanguageTag = Locale.forLanguageTag(this.Xk);
            LocaleList localeList = new LocaleList(localeForLanguageTag);
            if (configuration.locale.equals(localeForLanguageTag)) {
                return;
            }
            this.n.a(localeList);
        } catch (Exception unused) {
        }
    }
}
