package android.arch.lifecycle;

/* JADX INFO: loaded from: classes.dex */
class i implements Runnable {
    final /* synthetic */ l n;

    i(l lVar) {
        this.n = lVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Object obj;
        synchronized (this.n.ia) {
            obj = this.n.ja;
            this.n.ja = l.NOT_SET;
        }
        this.n.setValue(obj);
    }
}
