package a.b.e.f;

import android.support.v7.widget.SearchView;

/* JADX INFO: loaded from: classes.dex */
public class Q implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SearchView f299a;

    public Q(SearchView searchView) {
        this.f299a = searchView;
    }

    @Override // java.lang.Runnable
    public void run() {
        a.b.d.i.c cVar = this.f299a.O;
        if (cVar == null || !(cVar instanceof ba)) {
            return;
        }
        cVar.changeCursor(null);
    }
}
