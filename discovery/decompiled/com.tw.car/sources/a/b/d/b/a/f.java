package a.b.d.b.a;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int[] f63a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final float[] f64b;

    public f(int i, int i2) {
        this.f63a = new int[]{i, i2};
        this.f64b = new float[]{0.0f, 1.0f};
    }

    public f(int i, int i2, int i3) {
        this.f63a = new int[]{i, i2, i3};
        this.f64b = new float[]{0.0f, 0.5f, 1.0f};
    }

    public f(List<Integer> list, List<Float> list2) {
        int size = list.size();
        this.f63a = new int[size];
        this.f64b = new float[size];
        for (int i = 0; i < size; i++) {
            this.f63a[i] = list.get(i).intValue();
            this.f64b[i] = list2.get(i).floatValue();
        }
    }
}
