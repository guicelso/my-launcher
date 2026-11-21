package a.b.d.e;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class d implements f.c<e.d> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f90a;

    public d(String str) {
        this.f90a = str;
    }

    @Override // a.b.d.e.f.c
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(e.d dVar) {
        synchronized (e.f93c) {
            a.b.d.g.f<String, ArrayList<f.c<e.d>>> fVar = e.d;
            ArrayList<f.c<e.d>> arrayList = fVar.get(this.f90a);
            if (arrayList == null) {
                return;
            }
            fVar.remove(this.f90a);
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList.get(i).a(dVar);
            }
        }
    }
}
