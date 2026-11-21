package android.arch.lifecycle;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class l {
    private static final Object NOT_SET = new Object();
    private volatile Object ja;
    private boolean ka;
    private boolean la;
    private volatile Object mData;
    private int mVersion;
    private final Object ia = new Object();
    private a.a.a.b.i mObservers = new a.a.a.b.i();
    private int mActiveCount = 0;

    public l() {
        Object obj = NOT_SET;
        this.mData = obj;
        this.ja = obj;
        this.mVersion = -1;
        new i(this);
    }

    private void a(j jVar) {
        if (jVar.mActive) {
            if (!jVar.i()) {
                jVar.b(false);
                return;
            }
            int i = jVar.ha;
            int i2 = this.mVersion;
            if (i >= i2) {
                return;
            }
            jVar.ha = i2;
            jVar.mObserver.a(this.mData);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(j jVar) {
        if (this.ka) {
            this.la = true;
            return;
        }
        this.ka = true;
        do {
            this.la = false;
            if (jVar != null) {
                a(jVar);
                jVar = null;
            } else {
                a.a.a.b.f fVarF = this.mObservers.f();
                while (fVarF.hasNext()) {
                    a((j) ((Map.Entry) fVarF.next()).getValue());
                    if (this.la) {
                        break;
                    }
                }
            }
        } while (this.la);
        this.ka = false;
    }

    private static void i(String str) {
        if (a.a.a.a.c.getInstance().e()) {
            return;
        }
        throw new IllegalStateException("Cannot invoke " + str + " on a background thread");
    }

    public void a(m mVar) {
        i("removeObserver");
        j jVar = (j) this.mObservers.remove(null);
        if (jVar == null) {
            return;
        }
        jVar.h();
        jVar.b(false);
    }

    protected void k() {
    }

    protected void onActive() {
    }

    public void setValue(Object obj) {
        i("setValue");
        this.mVersion++;
        this.mData = obj;
        b((j) null);
    }
}
