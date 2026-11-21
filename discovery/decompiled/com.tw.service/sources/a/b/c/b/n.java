package a.b.c.b;

/* JADX INFO: loaded from: classes.dex */
public class n extends m {
    private final Object mLock;

    public n(int i) {
        super(i);
        this.mLock = new Object();
    }

    @Override // a.b.c.b.m
    public Object acquire() {
        Object objAcquire;
        synchronized (this.mLock) {
            objAcquire = super.acquire();
        }
        return objAcquire;
    }

    @Override // a.b.c.b.m
    public boolean release(Object obj) {
        boolean zRelease;
        synchronized (this.mLock) {
            zRelease = super.release(obj);
        }
        return zRelease;
    }
}
