package android.support.v4.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
final class FragmentState implements Parcelable {
    public static final Parcelable.Creator CREATOR = new C();
    final int mA;
    final boolean mB;
    final String mC;
    Bundle mt;
    final Bundle mu;
    final String mv;
    final int mw;
    final boolean mx;
    final int my;
    final boolean mz;

    public FragmentState(Parcel parcel) {
        this.mv = parcel.readString();
        this.mA = parcel.readInt();
        this.mz = parcel.readInt() != 0;
        this.my = parcel.readInt();
        this.mw = parcel.readInt();
        this.mC = parcel.readString();
        this.mB = parcel.readInt() != 0;
        this.mx = parcel.readInt() != 0;
        this.mu = parcel.readBundle();
        this.mt = parcel.readBundle();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mv);
        parcel.writeInt(this.mA);
        parcel.writeInt(this.mz ? 1 : 0);
        parcel.writeInt(this.my);
        parcel.writeInt(this.mw);
        parcel.writeString(this.mC);
        parcel.writeInt(this.mB ? 1 : 0);
        parcel.writeInt(this.mx ? 1 : 0);
        parcel.writeBundle(this.mu);
        parcel.writeBundle(this.mt);
    }
}
