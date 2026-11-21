package a.b.e.f;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class ra {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f374a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final View f375b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final TextView f376c;
    public final WindowManager.LayoutParams d = new WindowManager.LayoutParams();
    public final Rect e = new Rect();
    public final int[] f = new int[2];
    public final int[] g = new int[2];

    public ra(Context context) {
        this.f374a = context;
        this.f375b = LayoutInflater.from(this.f374a).inflate(a.b.e.b.g.abc_tooltip, (ViewGroup) null);
        this.f376c = (TextView) this.f375b.findViewById(a.b.e.b.f.message);
        this.d.setTitle(ra.class.getSimpleName());
        this.d.packageName = this.f374a.getPackageName();
        WindowManager.LayoutParams layoutParams = this.d;
        layoutParams.type = 1002;
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = a.b.e.b.i.Animation_AppCompat_Tooltip;
        layoutParams.flags = 24;
    }

    public void a() {
        if (this.f375b.getParent() != null) {
            ((WindowManager) this.f374a.getSystemService("window")).removeView(this.f375b);
        }
    }

    public boolean b() {
        return this.f375b.getParent() != null;
    }
}
