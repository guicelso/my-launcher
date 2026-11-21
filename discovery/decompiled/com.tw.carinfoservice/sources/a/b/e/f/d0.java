package a.b.e.f;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class d0 extends ContextWrapper {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final Object f264b = new Object();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static ArrayList<WeakReference<d0>> f265c;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Resources f266a;

    public d0(Context context) {
        super(context);
        this.f266a = new f0(this, context.getResources());
    }

    public static Context a(Context context) {
        boolean z = false;
        if (!(context instanceof d0) && !(context.getResources() instanceof f0)) {
            context.getResources();
            if (Build.VERSION.SDK_INT < 21) {
                z = true;
            }
        }
        if (!z) {
            return context;
        }
        synchronized (f264b) {
            ArrayList<WeakReference<d0>> arrayList = f265c;
            if (arrayList == null) {
                f265c = new ArrayList<>();
            } else {
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    WeakReference<d0> weakReference = f265c.get(size);
                    if (weakReference == null || weakReference.get() == null) {
                        f265c.remove(size);
                    }
                }
                for (int size2 = f265c.size() - 1; size2 >= 0; size2--) {
                    WeakReference<d0> weakReference2 = f265c.get(size2);
                    d0 d0Var = weakReference2 != null ? weakReference2.get() : null;
                    if (d0Var != null && d0Var.getBaseContext() == context) {
                        return d0Var;
                    }
                }
            }
            d0 d0Var2 = new d0(context);
            f265c.add(new WeakReference<>(d0Var2));
            return d0Var2;
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public AssetManager getAssets() {
        return this.f266a.getAssets();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        return this.f266a;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources.Theme getTheme() {
        return super.getTheme();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void setTheme(int i) {
        super.setTheme(i);
    }
}
