package com.dofun.carsetting.config;

import android.text.TextUtils;

/* JADX INFO: compiled from: ConfigInfo.java */
/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public String f186a;
    public String b;

    public a() {
    }

    public a(String str, String str2) {
        this.f186a = str;
        this.b = str2;
    }

    public boolean equals(Object obj) {
        return (obj instanceof a) && TextUtils.equals(((a) obj).f186a, this.f186a);
    }
}
