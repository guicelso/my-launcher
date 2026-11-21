package android.support.v7.widget;

import a.b.e.f.ha;
import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: classes.dex */
public class ActivityChooserView$InnerLayout extends LinearLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final int[] f415a = {R.attr.background};

    public ActivityChooserView$InnerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ha haVarA = ha.a(context, attributeSet, f415a);
        setBackgroundDrawable(haVarA.b(0));
        haVarA.f337b.recycle();
    }
}
