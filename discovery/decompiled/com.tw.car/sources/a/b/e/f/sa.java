package a.b.e.f;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class sa extends Resources {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static boolean f378a = false;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final WeakReference<Context> f379b;

    public sa(Context context, Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.f379b = new WeakReference<>(context);
    }

    public static boolean a() {
        return f378a && Build.VERSION.SDK_INT <= 20;
    }

    public final Drawable a(int i) {
        return super.getDrawable(i);
    }

    @Override // android.content.res.Resources
    public Drawable getDrawable(int i) {
        Context context = this.f379b.get();
        return context != null ? C0019j.a().a(context, this, i) : super.getDrawable(i);
    }
}
