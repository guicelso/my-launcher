package a.a.a.b;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class a extends i {
    private HashMap J = new HashMap();

    public boolean contains(Object obj) {
        return this.J.containsKey(obj);
    }

    @Override // a.a.a.b.i
    protected e get(Object obj) {
        return (e) this.J.get(obj);
    }

    @Override // a.a.a.b.i
    public Object remove(Object obj) {
        Object objRemove = super.remove(obj);
        this.J.remove(obj);
        return objRemove;
    }
}
