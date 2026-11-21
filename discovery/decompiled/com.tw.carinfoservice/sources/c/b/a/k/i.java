package c.b.a.k;

import android.content.Context;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
public class i {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Context f437a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Map<String, j> f438b = new ConcurrentHashMap();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public List<c.b.a.k.c> f439c;
    public volatile j d;

    public static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public static i f440a = new i(null);
    }

    public static class b implements Comparator<c.b.a.k.c> {
        public b(g gVar) {
        }

        @Override // java.util.Comparator
        public int compare(c.b.a.k.c cVar, c.b.a.k.c cVar2) {
            return cVar2.b() - cVar.b();
        }
    }

    public static class c implements Comparator<c.b.a.k.c> {
        public c(g gVar) {
        }

        @Override // java.util.Comparator
        public int compare(c.b.a.k.c cVar, c.b.a.k.c cVar2) {
            return cVar2.a() - cVar.a();
        }
    }

    public static class d implements Comparator<c.b.a.k.c> {
        public d(g gVar) {
        }

        @Override // java.util.Comparator
        public int compare(c.b.a.k.c cVar, c.b.a.k.c cVar2) {
            return cVar2.e() - cVar.e();
        }
    }

    public i(g gVar) {
        new CopyOnWriteArrayList();
        this.f439c = new ArrayList();
    }

    public static i a() {
        return a.f440a;
    }

    public void b(Context context) {
        this.f437a = context;
    }
}
