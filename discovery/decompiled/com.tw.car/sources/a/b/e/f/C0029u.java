package a.b.e.f;

import android.graphics.Typeface;
import android.widget.TextView;
import java.lang.ref.WeakReference;

/* JADX INFO: renamed from: a.b.e.f.u, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0029u extends a.b.d.b.a.j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ WeakReference f383a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0030v f384b;

    public C0029u(C0030v c0030v, WeakReference weakReference) {
        this.f384b = c0030v;
        this.f383a = weakReference;
    }

    @Override // a.b.d.b.a.j
    public void a(int i) {
    }

    @Override // a.b.d.b.a.j
    public void a(Typeface typeface) {
        C0030v c0030v = this.f384b;
        WeakReference weakReference = this.f383a;
        if (c0030v.k) {
            c0030v.j = typeface;
            TextView textView = (TextView) weakReference.get();
            if (textView != null) {
                textView.setTypeface(typeface, c0030v.i);
            }
        }
    }
}
