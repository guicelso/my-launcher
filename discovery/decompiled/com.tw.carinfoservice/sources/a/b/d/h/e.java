package a.b.d.h;

import android.view.View;
import android.view.ViewParent;

/* JADX INFO: loaded from: classes.dex */
public class e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public ViewParent f147a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ViewParent f148b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final View f149c;
    public boolean d;
    public int[] e;

    public e(View view) {
        this.f149c = view;
    }

    public final ViewParent a(int i) {
        if (i == 0) {
            return this.f147a;
        }
        if (i != 1) {
            return null;
        }
        return this.f148b;
    }
}
