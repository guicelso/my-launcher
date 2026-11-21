package a.a.a.b;

import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
abstract class g implements Iterator, h {
    e G;
    e mNext;

    g(e eVar, e eVar2) {
        this.G = eVar2;
        this.mNext = eVar;
    }

    private e nextNode() {
        e eVar = this.mNext;
        e eVar2 = this.G;
        if (eVar == eVar2 || eVar2 == null) {
            return null;
        }
        return c(eVar);
    }

    @Override // a.a.a.b.h
    public void a(e eVar) {
        e eVarC = null;
        if (this.G == eVar && eVar == this.mNext) {
            this.mNext = null;
            this.G = null;
        }
        e eVar2 = this.G;
        if (eVar2 == eVar) {
            this.G = b(eVar2);
        }
        e eVar3 = this.mNext;
        if (eVar3 == eVar) {
            e eVar4 = this.G;
            if (eVar3 != eVar4 && eVar4 != null) {
                eVarC = c(eVar3);
            }
            this.mNext = eVarC;
        }
    }

    abstract e b(e eVar);

    abstract e c(e eVar);

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.mNext != null;
    }

    @Override // java.util.Iterator
    public Object next() {
        e eVar = this.mNext;
        this.mNext = nextNode();
        return eVar;
    }
}
