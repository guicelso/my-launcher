package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import b.a.a;
import b.a.b;
import b.a.c;
import b.a.d;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public class ParcelImpl implements Parcelable {
    public static final Parcelable.Creator<ParcelImpl> CREATOR = new a();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final d f440a;

    public ParcelImpl(Parcel parcel) {
        c cVar = new c(parcel, parcel.dataPosition(), parcel.dataSize(), "");
        String string = cVar.f442b.readString();
        d dVar = null;
        if (string != null) {
            try {
                dVar = (d) Class.forName(string, true, b.class.getClassLoader()).getDeclaredMethod("read", b.class).invoke(null, cVar.b());
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
        }
        this.f440a = dVar;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        c cVar = new c(parcel, parcel.dataPosition(), parcel.dataSize(), "");
        d dVar = this.f440a;
        if (dVar == null) {
            cVar.f442b.writeString(null);
            return;
        }
        try {
            cVar.f442b.writeString(b.a((Class<? extends d>) dVar.getClass()).getName());
            b bVarB = cVar.b();
            try {
                b.a((Class<? extends d>) dVar.getClass()).getDeclaredMethod("write", dVar.getClass(), b.class).invoke(null, dVar, bVarB);
                bVarB.a();
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
}
