package a.b.e.f;

import android.support.v7.widget.SearchView;

/* JADX INFO: loaded from: classes.dex */
public class aa implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SearchView.SearchAutoComplete f310a;

    public aa(SearchView.SearchAutoComplete searchAutoComplete) {
        this.f310a = searchAutoComplete;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f310a.b();
    }
}
