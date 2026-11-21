package android.support.v4.app;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

/* JADX INFO: renamed from: android.support.v4.app.g, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0006g {
    public static IBinder a(Bundle bundle, String str) {
        int i = Build.VERSION.SDK_INT;
        return bundle.getBinder(str);
    }
}
