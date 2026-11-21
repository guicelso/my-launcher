package android.arch.lifecycle;

/* JADX INFO: loaded from: classes.dex */
public class q {
    private final p mFactory;
    private final r oa;

    public q(r rVar, p pVar) {
        this.mFactory = pVar;
        this.oa = rVar;
    }

    public o b(Class cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        String strA = b.a.a.a.a.a("android.arch.lifecycle.ViewModelProvider.DefaultKey:", canonicalName);
        o oVar = this.oa.get(strA);
        if (cls.isInstance(oVar)) {
            return oVar;
        }
        o oVarA = this.mFactory.a(cls);
        this.oa.a(strA, oVarA);
        return oVarA;
    }
}
