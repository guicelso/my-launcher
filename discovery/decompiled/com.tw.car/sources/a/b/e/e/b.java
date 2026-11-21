package a.b.e.e;

import a.b.e.b.i;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;

/* JADX INFO: loaded from: classes.dex */
public class b extends ContextWrapper {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f249a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Resources.Theme f250b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public LayoutInflater f251c;
    public Configuration d;
    public Resources e;

    public b(Context context, int i) {
        super(context);
        this.f249a = i;
    }

    public final void a() {
        if (this.f250b == null) {
            this.f250b = getResources().newTheme();
            Resources.Theme theme = getBaseContext().getTheme();
            if (theme != null) {
                this.f250b.setTo(theme);
            }
        }
        this.f250b.applyStyle(this.f249a, true);
    }

    @Override // android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        Resources resources;
        if (this.e == null) {
            Configuration configuration = this.d;
            if (configuration == null) {
                resources = super.getResources();
            } else {
                int i = Build.VERSION.SDK_INT;
                resources = createConfigurationContext(configuration).getResources();
            }
            this.e = resources;
        }
        return this.e;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String str) {
        if (!"layout_inflater".equals(str)) {
            return getBaseContext().getSystemService(str);
        }
        if (this.f251c == null) {
            this.f251c = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.f251c;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources.Theme getTheme() {
        Resources.Theme theme = this.f250b;
        if (theme != null) {
            return theme;
        }
        if (this.f249a == 0) {
            this.f249a = i.Theme_AppCompat_Light;
        }
        a();
        return this.f250b;
    }

    public int getThemeResId() {
        return this.f249a;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void setTheme(int i) {
        if (this.f249a != i) {
            this.f249a = i;
            a();
        }
    }
}
