package a.b.e.f;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

/* JADX INFO: renamed from: a.b.e.f.o, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0024o extends PopupWindow {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final boolean f362a;
    public boolean mOverlapAnchor;

    static {
        f362a = Build.VERSION.SDK_INT < 21;
    }

    public C0024o(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        ha haVarA = ha.a(context, attributeSet, a.b.e.b.j.PopupWindow, i, i2);
        if (haVarA.e(a.b.e.b.j.PopupWindow_overlapAnchor)) {
            boolean zA = haVarA.a(a.b.e.b.j.PopupWindow_overlapAnchor, false);
            if (f362a) {
                this.mOverlapAnchor = zA;
            } else {
                a.b.b.a.e.a(this, zA);
            }
        }
        setBackgroundDrawable(haVarA.b(a.b.e.b.j.PopupWindow_android_popupBackground));
        haVarA.f337b.recycle();
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view, int i, int i2) {
        if (f362a && this.mOverlapAnchor) {
            i2 -= view.getHeight();
        }
        super.showAsDropDown(view, i, i2);
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view, int i, int i2, int i3) {
        if (f362a && this.mOverlapAnchor) {
            i2 -= view.getHeight();
        }
        super.showAsDropDown(view, i, i2, i3);
    }

    @Override // android.widget.PopupWindow
    public void update(View view, int i, int i2, int i3, int i4) {
        if (f362a && this.mOverlapAnchor) {
            i2 -= view.getHeight();
        }
        super.update(view, i, i2, i3, i4);
    }
}
