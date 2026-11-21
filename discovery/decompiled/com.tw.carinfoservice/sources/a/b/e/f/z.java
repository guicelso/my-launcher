package a.b.e.f;

/* JADX INFO: loaded from: classes.dex */
public class z {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f334a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f335b = 0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f336c = Integer.MIN_VALUE;
    public int d = Integer.MIN_VALUE;
    public int e = 0;
    public int f = 0;
    public boolean g = false;
    public boolean h = false;

    public void a(int i, int i2) {
        this.f336c = i;
        this.d = i2;
        this.h = true;
        if (this.g) {
            if (i2 != Integer.MIN_VALUE) {
                this.f334a = i2;
            }
            if (i != Integer.MIN_VALUE) {
                this.f335b = i;
                return;
            }
            return;
        }
        if (i != Integer.MIN_VALUE) {
            this.f334a = i;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.f335b = i2;
        }
    }
}
