package a.b.d.e;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class d implements k.a<f.c> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f93a;

    public d(String str) {
        this.f93a = str;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // a.b.d.e.k.a
    public void a(f.c cVar) {
        synchronized (f.f96c) {
            ArrayList<k.a<f.c>> arrayList = f.d.get(this.f93a);
            if (arrayList == null) {
                return;
            }
            a.b.d.g.g<String, ArrayList<k.a<f.c>>> gVar = f.d;
            int iA = gVar.a(this.f93a);
            if (iA >= 0) {
                gVar.c(iA);
            }
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList.get(i).a(cVar);
            }
        }
    }
}
