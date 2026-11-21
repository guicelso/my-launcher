package a.b.e.f;

import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class V implements View.OnKeyListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SearchView f303a;

    public V(SearchView searchView) {
        this.f303a = searchView;
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        SearchView searchView = this.f303a;
        if (searchView.ca == null) {
            return false;
        }
        if (searchView.q.isPopupShowing() && this.f303a.q.getListSelection() != -1) {
            return this.f303a.a(view, i, keyEvent);
        }
        if (this.f303a.q.a() || !keyEvent.hasNoModifiers() || keyEvent.getAction() != 1 || i != 66) {
            return false;
        }
        view.cancelLongPress();
        SearchView searchView2 = this.f303a;
        searchView2.a(0, (String) null, searchView2.q.getText().toString());
        return true;
    }
}
