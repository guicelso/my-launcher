package a.b.e.d.a;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.StateSet;

/* JADX INFO: loaded from: classes.dex */
public class f extends d {
    public a m;
    public boolean n;

    static class a extends d.b {
        public int[][] J;

        public a(a aVar, f fVar, Resources resources) {
            super(aVar, fVar, resources);
            this.J = aVar != null ? aVar.J : new int[this.g.length][];
        }

        public int a(int[] iArr) {
            int[][] iArr2 = this.J;
            int i = this.h;
            for (int i2 = 0; i2 < i; i2++) {
                if (StateSet.stateSetMatches(iArr2[i2], iArr)) {
                    return i2;
                }
            }
            return -1;
        }

        @Override // a.b.e.d.a.d.b
        public void d() {
            int[][] iArr = this.J;
            int[][] iArr2 = new int[iArr.length][];
            for (int length = iArr.length - 1; length >= 0; length--) {
                int[][] iArr3 = this.J;
                iArr2[length] = iArr3[length] != null ? (int[]) iArr3[length].clone() : null;
            }
            this.J = iArr2;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new f(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new f(this, resources);
        }
    }

    public f(a aVar) {
        if (aVar != null) {
            a(aVar);
        }
    }

    public f(a aVar, Resources resources) {
        a(new a(aVar, this, resources));
        onStateChange(getState());
    }

    @Override // a.b.e.d.a.d
    public a a() {
        return new a(this.m, this, null);
    }

    @Override // a.b.e.d.a.d
    public void a(d.b bVar) {
        this.f197a = bVar;
        int i = this.g;
        if (i >= 0) {
            this.f199c = bVar.a(i);
            Drawable drawable = this.f199c;
            if (drawable != null) {
                a(drawable);
            }
        }
        this.d = null;
        if (bVar instanceof a) {
            this.m = (a) bVar;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        this.f197a.a(theme);
        onStateChange(getState());
    }

    public void clearMutated() {
        this.f197a.y = false;
        this.h = false;
        this.n = false;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // a.b.e.d.a.d, android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.n) {
            super.mutate();
            if (this == this) {
                this.m.d();
                this.n = true;
            }
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        Drawable drawable = this.d;
        boolean state = (drawable == null && (drawable = this.f199c) == null) ? false : drawable.setState(iArr);
        int iA = this.m.a(iArr);
        if (iA < 0) {
            iA = this.m.a(StateSet.WILD_CARD);
        }
        return a(iA) || state;
    }
}
