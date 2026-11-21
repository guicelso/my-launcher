package a.b.e.f;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class ga extends K {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final WeakReference<Context> f332b;

    public ga(Context context, Resources resources) {
        super(resources);
        this.f332b = new WeakReference<>(context);
    }

    @Override // android.content.res.Resources
    public Drawable getDrawable(int i) {
        Drawable drawable = this.f283a.getDrawable(i);
        Context context = this.f332b.get();
        if (drawable != null && context != null) {
            C0019j.a();
            C0019j.a(context, i, drawable);
        }
        return drawable;
    }
}
