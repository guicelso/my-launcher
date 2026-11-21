package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class ParcelableVolumeInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new b();
    public int nv;
    public int nw;
    public int nx;
    public int ny;
    public int nz;

    public ParcelableVolumeInfo(Parcel parcel) {
        this.nv = parcel.readInt();
        this.nx = parcel.readInt();
        this.ny = parcel.readInt();
        this.nz = parcel.readInt();
        this.nw = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.nv);
        parcel.writeInt(this.nx);
        parcel.writeInt(this.ny);
        parcel.writeInt(this.nz);
        parcel.writeInt(this.nw);
    }
}
