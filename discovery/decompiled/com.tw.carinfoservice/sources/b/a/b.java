package b.a;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;

/* JADX INFO: loaded from: classes.dex */
public class b extends a {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Parcel f392b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f393c;
    public final int d;
    public final String e;
    public int g;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final SparseIntArray f391a = new SparseIntArray();
    public int f = -1;

    public b(Parcel parcel, int i, int i2, String str) {
        this.g = 0;
        this.f392b = parcel;
        this.f393c = i;
        this.d = i2;
        this.g = i;
        this.e = str;
    }

    @Override // b.a.a
    public byte[] b() {
        int i = this.f392b.readInt();
        if (i < 0) {
            return null;
        }
        byte[] bArr = new byte[i];
        this.f392b.readByteArray(bArr);
        return bArr;
    }

    @Override // b.a.a
    public boolean c(int i) {
        int iDataPosition;
        while (true) {
            int i2 = this.g;
            if (i2 >= this.d) {
                iDataPosition = -1;
                break;
            }
            this.f392b.setDataPosition(i2);
            int i3 = this.f392b.readInt();
            int i4 = this.f392b.readInt();
            this.g += i3;
            if (i4 == i) {
                iDataPosition = this.f392b.dataPosition();
                break;
            }
        }
        if (iDataPosition == -1) {
            return false;
        }
        this.f392b.setDataPosition(iDataPosition);
        return true;
    }

    @Override // b.a.a
    public int d() {
        return this.f392b.readInt();
    }

    @Override // b.a.a
    public <T extends Parcelable> T f() {
        return (T) this.f392b.readParcelable(b.class.getClassLoader());
    }

    @Override // b.a.a
    public String g() {
        return this.f392b.readString();
    }

    @Override // b.a.a
    public void h(int i) {
        m();
        this.f = i;
        this.f391a.put(i, this.f392b.dataPosition());
        this.f392b.writeInt(0);
        this.f392b.writeInt(i);
    }

    @Override // b.a.a
    public void i(byte[] bArr) {
        if (bArr == null) {
            this.f392b.writeInt(-1);
        } else {
            this.f392b.writeInt(bArr.length);
            this.f392b.writeByteArray(bArr);
        }
    }

    @Override // b.a.a
    public void j(int i) {
        this.f392b.writeInt(i);
    }

    @Override // b.a.a
    public void k(Parcelable parcelable) {
        this.f392b.writeParcelable(parcelable, 0);
    }

    @Override // b.a.a
    public void l(String str) {
        this.f392b.writeString(str);
    }

    public void m() {
        int i = this.f;
        if (i >= 0) {
            int i2 = this.f391a.get(i);
            int iDataPosition = this.f392b.dataPosition();
            this.f392b.setDataPosition(i2);
            this.f392b.writeInt(iDataPosition - i2);
            this.f392b.setDataPosition(iDataPosition);
        }
    }
}
