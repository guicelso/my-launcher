package a.b.d.h;

import android.view.View;
import android.view.ViewParent;

/* JADX INFO: loaded from: classes.dex */
public class h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public ViewParent f162a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ViewParent f163b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final View f164c;
    public boolean d;
    public int[] e;

    public h(View view) {
        this.f164c = view;
    }

    public final ViewParent a(int i) {
        if (i == 0) {
            return this.f162a;
        }
        if (i != 1) {
            return null;
        }
        return this.f163b;
    }

    public final void a(int i, ViewParent viewParent) {
        if (i == 0) {
            this.f162a = viewParent;
        } else {
            if (i != 1) {
                return;
            }
            this.f163b = viewParent;
        }
    }

    public boolean b(int i) {
        return a(i) != null;
    }
}
