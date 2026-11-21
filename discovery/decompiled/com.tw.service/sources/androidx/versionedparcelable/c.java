package androidx.versionedparcelable;

import android.os.Parcel;
import android.util.SparseIntArray;

/* JADX INFO: loaded from: classes.dex */
class c extends b {
    private final String lk;
    private final int mEnd;
    private final int mOffset;
    private final Parcel mParcel;
    private int nk;
    private final SparseIntArray kk = new SparseIntArray();
    private int mk = -1;

    c(Parcel parcel, int i, int i2, String str) {
        this.nk = 0;
        this.mParcel = parcel;
        this.mOffset = i;
        this.mEnd = i2;
        this.nk = this.mOffset;
        this.lk = str;
    }

    @Override // androidx.versionedparcelable.b
    public void la() {
        int i = this.mk;
        if (i >= 0) {
            int i2 = this.kk.get(i);
            int iDataPosition = this.mParcel.dataPosition();
            this.mParcel.setDataPosition(i2);
            this.mParcel.writeInt(iDataPosition - i2);
            this.mParcel.setDataPosition(iDataPosition);
        }
    }

    @Override // androidx.versionedparcelable.b
    protected b ma() {
        Parcel parcel = this.mParcel;
        int iDataPosition = parcel.dataPosition();
        int i = this.nk;
        if (i == this.mOffset) {
            i = this.mEnd;
        }
        return new c(parcel, iDataPosition, i, this.lk + "  ");
    }

    @Override // androidx.versionedparcelable.b
    public String readString() {
        return this.mParcel.readString();
    }

    @Override // androidx.versionedparcelable.b
    public void writeString(String str) {
        this.mParcel.writeString(str);
    }
}
