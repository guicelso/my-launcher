package a.b.e.f;

import android.R;
import android.view.View;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class i0 implements View.OnClickListener {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final a.b.e.e.c.a f286b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ j0 f287c;

    public i0(j0 j0Var) {
        this.f287c = j0Var;
        this.f286b = new a.b.e.e.c.a(j0Var.f290a.getContext(), 0, R.id.home, 0, 0, j0Var.h);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        j0 j0Var = this.f287c;
        if (j0Var.k != null) {
            Objects.requireNonNull(j0Var);
        }
    }
}
