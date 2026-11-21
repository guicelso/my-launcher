package a.b.d.b.b;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int[] f60a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final float[] f61b;

    public f(int i, int i2) {
        this.f60a = new int[]{i, i2};
        this.f61b = new float[]{0.0f, 1.0f};
    }

    public f(int i, int i2, int i3) {
        this.f60a = new int[]{i, i2, i3};
        this.f61b = new float[]{0.0f, 0.5f, 1.0f};
    }

    public f(List<Integer> list, List<Float> list2) {
        int size = list.size();
        this.f60a = new int[size];
        this.f61b = new float[size];
        for (int i = 0; i < size; i++) {
            this.f60a[i] = list.get(i).intValue();
            this.f61b[i] = list2.get(i).floatValue();
        }
    }
}
