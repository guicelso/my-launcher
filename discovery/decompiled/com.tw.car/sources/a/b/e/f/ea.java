package a.b.e.f;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ea extends ContextWrapper {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Object f321a = new Object();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static ArrayList<WeakReference<ea>> f322b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Resources f323c;
    public final Resources.Theme d;

    public ea(Context context) {
        super(context);
        if (!(sa.f378a && Build.VERSION.SDK_INT <= 20)) {
            this.f323c = new ga(this, context.getResources());
            this.d = null;
        } else {
            this.f323c = new sa(this, context.getResources());
            this.d = this.f323c.newTheme();
            this.d.setTo(context.getTheme());
        }
    }

    public static Context a(Context context) {
        boolean z = false;
        if (!(context instanceof ea) && !(context.getResources() instanceof ga) && !(context.getResources() instanceof sa) && (Build.VERSION.SDK_INT < 21 || sa.a())) {
            z = true;
        }
        if (!z) {
            return context;
        }
        synchronized (f321a) {
            if (f322b == null) {
                f322b = new ArrayList<>();
            } else {
                for (int size = f322b.size() - 1; size >= 0; size--) {
                    WeakReference<ea> weakReference = f322b.get(size);
                    if (weakReference == null || weakReference.get() == null) {
                        f322b.remove(size);
                    }
                }
                for (int size2 = f322b.size() - 1; size2 >= 0; size2--) {
                    WeakReference<ea> weakReference2 = f322b.get(size2);
                    ea eaVar = weakReference2 != null ? weakReference2.get() : null;
                    if (eaVar != null && eaVar.getBaseContext() == context) {
                        return eaVar;
                    }
                }
            }
            ea eaVar2 = new ea(context);
            f322b.add(new WeakReference<>(eaVar2));
            return eaVar2;
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public AssetManager getAssets() {
        return this.f323c.getAssets();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        return this.f323c;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources.Theme getTheme() {
        Resources.Theme theme = this.d;
        return theme == null ? super.getTheme() : theme;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void setTheme(int i) {
        Resources.Theme theme = this.d;
        if (theme == null) {
            super.setTheme(i);
        } else {
            theme.applyStyle(i, true);
        }
    }
}
