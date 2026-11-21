package a.b.e.a;

import a.b.e.b.j;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public class a extends ViewGroup.MarginLayoutParams {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f183a;

    public a(int i, int i2) {
        super(i, i2);
        this.f183a = 0;
        this.f183a = 8388627;
    }

    public a(a aVar) {
        super((ViewGroup.MarginLayoutParams) aVar);
        this.f183a = 0;
        this.f183a = aVar.f183a;
    }

    public a(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f183a = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, j.ActionBarLayout);
        this.f183a = typedArrayObtainStyledAttributes.getInt(j.ActionBarLayout_android_layout_gravity, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    public a(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.f183a = 0;
    }
}
