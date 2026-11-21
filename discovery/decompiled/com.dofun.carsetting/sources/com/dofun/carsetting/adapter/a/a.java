package com.dofun.carsetting.adapter.a;

import java.util.ArrayList;

/* JADX INFO: compiled from: ReverseTypeBean.java */
/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f120a;
    private int b;
    private int c;
    private ArrayList<C0012a> d;

    public a(String str, int i) {
        b(i);
        a(str);
    }

    public int a() {
        return this.c;
    }

    public void a(int i) {
        this.c = i;
    }

    public ArrayList<C0012a> b() {
        return this.d;
    }

    public void a(ArrayList<C0012a> arrayList) {
        this.d = arrayList;
    }

    public String c() {
        return this.f120a;
    }

    public void a(String str) {
        this.f120a = str;
    }

    public void b(int i) {
        this.b = i;
    }

    /* JADX INFO: renamed from: com.dofun.carsetting.adapter.a.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: ReverseTypeBean.java */
    public static class C0012a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public String f121a;
        public int b;

        public C0012a(String str, int i) {
            this.f121a = str;
            this.b = i;
        }
    }
}
