package a.b.e.f;

import android.support.v7.widget.Toolbar;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class h0 implements View.OnClickListener {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Toolbar f282b;

    public h0(Toolbar toolbar) {
        this.f282b = toolbar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Toolbar.c cVar = this.f282b.K;
        a.b.e.e.c.h hVar = cVar == null ? null : cVar.f385c;
        if (hVar != null) {
            hVar.collapseActionView();
        }
    }
}
