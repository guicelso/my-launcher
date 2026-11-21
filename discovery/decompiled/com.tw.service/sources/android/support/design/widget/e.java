package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public class e extends ViewGroup.MarginLayoutParams {
    CoordinatorLayout.Behavior Lj;
    boolean Mj;
    public int Nj;
    public int Oj;
    int Pj;
    public int Qj;
    public int Rj;
    int Sj;
    int Tj;
    View Uj;
    View Vj;
    private boolean Wj;
    private boolean Xj;
    private boolean Yj;
    private boolean Zj;
    final Rect _j;
    public int gravity;

    public e(int i, int i2) {
        super(i, i2);
        this.Mj = false;
        this.gravity = 0;
        this.Nj = 0;
        this.Oj = -1;
        this.Pj = -1;
        this.Qj = 0;
        this.Rj = 0;
        this._j = new Rect();
    }

    e(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.Mj = false;
        this.gravity = 0;
        this.Nj = 0;
        this.Oj = -1;
        this.Pj = -1;
        this.Qj = 0;
        this.Rj = 0;
        this._j = new Rect();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a.b.b.a.Xc);
        this.gravity = typedArrayObtainStyledAttributes.getInteger(a.b.b.a.Yc, 0);
        this.Pj = typedArrayObtainStyledAttributes.getResourceId(1, -1);
        this.Nj = typedArrayObtainStyledAttributes.getInteger(2, 0);
        this.Oj = typedArrayObtainStyledAttributes.getInteger(6, -1);
        this.Qj = typedArrayObtainStyledAttributes.getInt(5, 0);
        this.Rj = typedArrayObtainStyledAttributes.getInt(4, 0);
        this.Mj = typedArrayObtainStyledAttributes.hasValue(3);
        if (this.Mj) {
            this.Lj = CoordinatorLayout.a(context, attributeSet, typedArrayObtainStyledAttributes.getString(3));
        }
        typedArrayObtainStyledAttributes.recycle();
        CoordinatorLayout.Behavior behavior = this.Lj;
        if (behavior != null) {
            behavior.a(this);
        }
    }

    public e(e eVar) {
        super((ViewGroup.MarginLayoutParams) eVar);
        this.Mj = false;
        this.gravity = 0;
        this.Nj = 0;
        this.Oj = -1;
        this.Pj = -1;
        this.Qj = 0;
        this.Rj = 0;
        this._j = new Rect();
    }

    public e(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.Mj = false;
        this.gravity = 0;
        this.Nj = 0;
        this.Oj = -1;
        this.Pj = -1;
        this.Qj = 0;
        this.Rj = 0;
        this._j = new Rect();
    }

    public e(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
        this.Mj = false;
        this.gravity = 0;
        this.Nj = 0;
        this.Oj = -1;
        this.Pj = -1;
        this.Qj = 0;
        this.Rj = 0;
        this._j = new Rect();
    }

    boolean A(int i) {
        if (i == 0) {
            return this.Xj;
        }
        if (i != 1) {
            return false;
        }
        return this.Yj;
    }

    void a(int i, boolean z) {
        if (i == 0) {
            this.Xj = z;
        } else {
            if (i != 1) {
                return;
            }
            this.Yj = z;
        }
    }

    boolean e(CoordinatorLayout coordinatorLayout, View view) {
        boolean z = this.Wj;
        if (z) {
            return true;
        }
        CoordinatorLayout.Behavior behavior = this.Lj;
        boolean zA = (behavior != null ? behavior.a(coordinatorLayout, view) : false) | z;
        this.Wj = zA;
        return zA;
    }

    boolean ha() {
        if (this.Lj == null) {
            this.Wj = false;
        }
        return this.Wj;
    }

    boolean ia() {
        return this.Zj;
    }

    void ja() {
        this.Zj = false;
    }

    void k(boolean z) {
        this.Zj = z;
    }

    void ka() {
        this.Wj = false;
    }
}
