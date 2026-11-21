package c;

import android.content.Context;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public final class g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Context f21a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public String f22b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Thread f23c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public Process f24d;

    public g(Context context, String str) {
        this.f21a = context;
        this.f22b = d.d.c("CoreLaunchLog") + File.separator + str;
    }
}
