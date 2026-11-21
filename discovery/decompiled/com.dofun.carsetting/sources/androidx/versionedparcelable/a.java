package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.util.SparseIntArray;

/* JADX INFO: compiled from: VersionedParcelParcel.java */
/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY})
class a extends VersionedParcel {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final SparseIntArray f28a;
    private final Parcel b;
    private final int c;
    private final int d;
    private final String e;
    private int f;
    private int g;

    a(Parcel parcel) {
        this(parcel, parcel.dataPosition(), parcel.dataSize(), "");
    }

    a(Parcel parcel, int i, int i2, String str) {
        this.f28a = new SparseIntArray();
        this.f = -1;
        this.g = 0;
        this.b = parcel;
        this.c = i;
        this.d = i2;
        this.g = this.c;
        this.e = str;
    }

    private int d(int i) {
        while (this.g < this.d) {
            this.b.setDataPosition(this.g);
            int i2 = this.b.readInt();
            int i3 = this.b.readInt();
            this.g += i2;
            if (i3 == i) {
                return this.b.dataPosition();
            }
        }
        return -1;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public boolean b(int i) {
        int iD = d(i);
        if (iD == -1) {
            return false;
        }
        this.b.setDataPosition(iD);
        return true;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void c(int i) {
        b();
        this.f = i;
        this.f28a.put(i, this.b.dataPosition());
        a(0);
        a(i);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void b() {
        if (this.f >= 0) {
            int i = this.f28a.get(this.f);
            int iDataPosition = this.b.dataPosition();
            this.b.setDataPosition(i);
            this.b.writeInt(iDataPosition - i);
            this.b.setDataPosition(iDataPosition);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    protected VersionedParcel c() {
        return new a(this.b, this.b.dataPosition(), this.g == this.c ? this.d : this.g, this.e + "  ");
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void a(byte[] bArr) {
        if (bArr != null) {
            this.b.writeInt(bArr.length);
            this.b.writeByteArray(bArr);
        } else {
            this.b.writeInt(-1);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void a(int i) {
        this.b.writeInt(i);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void a(String str) {
        this.b.writeString(str);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void a(Parcelable parcelable) {
        this.b.writeParcelable(parcelable, 0);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public int d() {
        return this.b.readInt();
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public String e() {
        return this.b.readString();
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public byte[] f() {
        int i = this.b.readInt();
        if (i < 0) {
            return null;
        }
        byte[] bArr = new byte[i];
        this.b.readByteArray(bArr);
        return bArr;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public <T extends Parcelable> T g() {
        return (T) this.b.readParcelable(getClass().getClassLoader());
    }
}
