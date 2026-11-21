package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public class ParcelImpl implements Parcelable {
    public static final Parcelable.Creator CREATOR = new a();
    private final d mParcel;

    protected ParcelImpl(Parcel parcel) {
        c cVar = new c(parcel, parcel.dataPosition(), parcel.dataSize(), "");
        String string = cVar.readString();
        d dVar = null;
        if (string != null) {
            try {
                dVar = (d) Class.forName(string, true, b.class.getClassLoader()).getDeclaredMethod("read", b.class).invoke(null, cVar.ma());
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
        this.mParcel = dVar;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        new c(parcel, parcel.dataPosition(), parcel.dataSize(), "").a(this.mParcel);
    }
}
