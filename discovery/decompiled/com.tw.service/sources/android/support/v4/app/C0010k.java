package android.support.v4.app;

/* JADX INFO: renamed from: android.support.v4.app.k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0010k implements android.arch.lifecycle.e {
    final /* synthetic */ ComponentCallbacksC0013n n;

    C0010k(ComponentCallbacksC0013n componentCallbacksC0013n) {
        this.n = componentCallbacksC0013n;
    }

    @Override // android.arch.lifecycle.e
    public android.arch.lifecycle.c d() {
        if (this.n.ld == null) {
            ComponentCallbacksC0013n componentCallbacksC0013n = this.n;
            componentCallbacksC0013n.ld = new android.arch.lifecycle.g(componentCallbacksC0013n.md);
        }
        return this.n.ld;
    }
}
