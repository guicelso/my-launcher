package android.support.v4.view;

import android.os.Build;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
public class D {
    private final Object Ze;

    private D(Object obj) {
        this.Ze = obj;
    }

    static Object a(D d) {
        if (d == null) {
            return null;
        }
        return d.Ze;
    }

    static D wrap(Object obj) {
        if (obj == null) {
            return null;
        }
        return new D(obj);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || D.class != obj.getClass()) {
            return false;
        }
        D d = (D) obj;
        Object obj2 = this.Ze;
        if (obj2 == null) {
            return d.Ze == null;
        }
        return obj2.equals(d.Ze);
    }

    public int getSystemWindowInsetBottom() {
        int i = Build.VERSION.SDK_INT;
        return ((WindowInsets) this.Ze).getSystemWindowInsetBottom();
    }

    public int getSystemWindowInsetLeft() {
        int i = Build.VERSION.SDK_INT;
        return ((WindowInsets) this.Ze).getSystemWindowInsetLeft();
    }

    public int getSystemWindowInsetRight() {
        int i = Build.VERSION.SDK_INT;
        return ((WindowInsets) this.Ze).getSystemWindowInsetRight();
    }

    public int getSystemWindowInsetTop() {
        int i = Build.VERSION.SDK_INT;
        return ((WindowInsets) this.Ze).getSystemWindowInsetTop();
    }

    public int hashCode() {
        Object obj = this.Ze;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public boolean isConsumed() {
        int i = Build.VERSION.SDK_INT;
        return ((WindowInsets) this.Ze).isConsumed();
    }

    public D replaceSystemWindowInsets(int i, int i2, int i3, int i4) {
        int i5 = Build.VERSION.SDK_INT;
        return new D(((WindowInsets) this.Ze).replaceSystemWindowInsets(i, i2, i3, i4));
    }
}
