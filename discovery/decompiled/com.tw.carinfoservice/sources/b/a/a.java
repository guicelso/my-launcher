package b.a;

import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {
    public static Class a(Class<? extends c> cls) {
        return Class.forName(String.format("%s.%sParcelizer", cls.getPackage().getName(), cls.getSimpleName()), false, cls.getClassLoader());
    }

    public abstract byte[] b();

    public abstract boolean c(int i);

    public abstract int d();

    public int e(int i, int i2) {
        return !c(i2) ? i : d();
    }

    public abstract <T extends Parcelable> T f();

    public abstract String g();

    public abstract void h(int i);

    public abstract void i(byte[] bArr);

    public abstract void j(int i);

    public abstract void k(Parcelable parcelable);

    public abstract void l(String str);
}
