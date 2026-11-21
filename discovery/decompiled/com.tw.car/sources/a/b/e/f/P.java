package a.b.e.f;

import android.support.v7.widget.SearchView;

/* JADX INFO: loaded from: classes.dex */
public class P implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SearchView f298a;

    public P(SearchView searchView) {
        this.f298a = searchView;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f298a.k();
    }
}
