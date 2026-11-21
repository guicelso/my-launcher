package androidx.versionedparcelable;

import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public abstract class b {
    private static Class c(Class cls) {
        return Class.forName(String.format("%s.%sParcelizer", cls.getPackage().getName(), cls.getSimpleName()), false, cls.getClassLoader());
    }

    protected void a(d dVar) {
        if (dVar == null) {
            writeString(null);
            return;
        }
        try {
            writeString(c(dVar.getClass()).getName());
            b bVarMa = ma();
            try {
                c(dVar.getClass()).getDeclaredMethod("write", dVar.getClass(), b.class).invoke(null, dVar, bVarMa);
                bVarMa.la();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e2);
            } catch (NoSuchMethodException e3) {
                throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
            } catch (InvocationTargetException e4) {
                if (!(e4.getCause() instanceof RuntimeException)) {
                    throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e4);
                }
                throw ((RuntimeException) e4.getCause());
            }
        } catch (ClassNotFoundException e5) {
            throw new RuntimeException(dVar.getClass().getSimpleName() + " does not have a Parcelizer", e5);
        }
    }

    protected abstract void la();

    protected abstract b ma();

    protected abstract String readString();

    protected abstract void writeString(String str);
}
