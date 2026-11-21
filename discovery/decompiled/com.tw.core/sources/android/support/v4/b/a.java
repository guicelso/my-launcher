package android.support.v4.b;

import android.os.Build;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static Parcelable.Creator rj(e eVar) {
        return Build.VERSION.SDK_INT >= 13 ? b.rk(eVar) : new d(eVar);
    }
}
