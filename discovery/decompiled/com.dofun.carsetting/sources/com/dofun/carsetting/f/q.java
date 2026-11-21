package com.dofun.carsetting.f;

import android.content.Intent;
import android.text.TextUtils;
import com.dofun.carsetting.app.MyApp;

/* JADX INFO: compiled from: SendBroadCastUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class q {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static q f230a;
    private Intent b = new Intent();

    private q() {
    }

    public static synchronized q a() {
        if (f230a == null) {
            f230a = new q();
        }
        return f230a;
    }

    public void a(String str, String str2, String str3) {
        this.b = new Intent();
        this.b.setAction(str);
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            this.b.putExtra(str2, str3);
        }
        g.a().b("SendBroadCastUtil", "sendBroadCast " + str);
        MyApp.a().sendBroadcast(this.b);
    }
}
