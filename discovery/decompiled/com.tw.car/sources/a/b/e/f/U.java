package a.b.e.f;

import android.support.v7.widget.SearchView;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class U implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SearchView f302a;

    public U(SearchView searchView) {
        this.f302a = searchView;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        SearchView searchView = this.f302a;
        if (view == searchView.u) {
            searchView.f();
            return;
        }
        if (view == searchView.w) {
            searchView.e();
            return;
        }
        if (view == searchView.v) {
            searchView.g();
        } else if (view == searchView.x) {
            searchView.i();
        } else if (view == searchView.q) {
            searchView.b();
        }
    }
}
