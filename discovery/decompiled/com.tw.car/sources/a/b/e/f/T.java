package a.b.e.f;

import android.support.v7.widget.SearchView;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class T implements View.OnLayoutChangeListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SearchView f301a;

    public T(SearchView searchView) {
        this.f301a = searchView;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.f301a.a();
    }
}
