package com.tw.service.xt;

import android.app.Instrumentation;

/* JADX INFO: loaded from: classes.dex */
class b implements Runnable {
    final /* synthetic */ int m;

    b(CommandService commandService, int i) {
        this.m = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            new Instrumentation().sendKeyDownUpSync(this.m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
