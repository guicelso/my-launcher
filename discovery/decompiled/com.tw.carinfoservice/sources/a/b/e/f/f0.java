package a.b.e.f;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class f0 extends y {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final WeakReference<Context> f276b;

    public f0(Context context, Resources resources) {
        super(resources);
        this.f276b = new WeakReference<>(context);
    }

    @Override // android.content.res.Resources
    public Drawable getDrawable(int i) {
        Drawable drawable = this.f333a.getDrawable(i);
        Context context = this.f276b.get();
        if (drawable != null && context != null) {
            f.g();
            f.q(context, i, drawable);
        }
        return drawable;
    }
}
