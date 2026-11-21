package a.b.c.b;

/* JADX INFO: loaded from: classes.dex */
public class m {
    private final Object[] Ge;
    private int He;

    public m(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("The max pool size must be > 0");
        }
        this.Ge = new Object[i];
    }

    public Object acquire() {
        int i = this.He;
        if (i <= 0) {
            return null;
        }
        int i2 = i - 1;
        Object[] objArr = this.Ge;
        Object obj = objArr[i2];
        objArr[i2] = null;
        this.He = i - 1;
        return obj;
    }

    public boolean release(Object obj) {
        boolean z;
        int i = 0;
        while (true) {
            if (i >= this.He) {
                z = false;
                break;
            }
            if (this.Ge[i] == obj) {
                z = true;
                break;
            }
            i++;
        }
        if (z) {
            throw new IllegalStateException("Already in the pool!");
        }
        int i2 = this.He;
        Object[] objArr = this.Ge;
        if (i2 >= objArr.length) {
            return false;
        }
        objArr[i2] = obj;
        this.He = i2 + 1;
        return true;
    }
}
