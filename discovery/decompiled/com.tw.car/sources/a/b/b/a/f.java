package a.b.b.a;

import android.animation.TypeEvaluator;

/* JADX INFO: loaded from: classes.dex */
public class f implements TypeEvaluator<a.b.d.c.b[]> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public a.b.d.c.b[] f13a;

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.animation.TypeEvaluator
    public a.b.d.c.b[] evaluate(float f, a.b.d.c.b[] bVarArr, a.b.d.c.b[] bVarArr2) {
        a.b.d.c.b[] bVarArr3 = bVarArr;
        a.b.d.c.b[] bVarArr4 = bVarArr2;
        if (!e.a(bVarArr3, bVarArr4)) {
            throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
        }
        a.b.d.c.b[] bVarArr5 = this.f13a;
        if (bVarArr5 == null || !e.a(bVarArr5, bVarArr3)) {
            this.f13a = e.a(bVarArr3);
        }
        for (int i = 0; i < bVarArr3.length; i++) {
            this.f13a[i].a(bVarArr3[i], bVarArr4[i], f);
        }
        return this.f13a;
    }
}
