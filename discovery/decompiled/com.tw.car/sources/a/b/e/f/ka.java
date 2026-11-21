package a.b.e.f;

import android.support.v7.widget.Toolbar;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class ka implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Toolbar f349a;

    public ka(Toolbar toolbar) {
        this.f349a = toolbar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.f349a.b();
    }
}
