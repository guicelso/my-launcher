package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class ParcelableVolumeInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new p();
    public int ue;
    public int ve;
    public int we;
    public int xe;
    public int ye;

    public ParcelableVolumeInfo(Parcel parcel) {
        this.ue = parcel.readInt();
        this.we = parcel.readInt();
        this.xe = parcel.readInt();
        this.ye = parcel.readInt();
        this.ve = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.ue);
        parcel.writeInt(this.we);
        parcel.writeInt(this.xe);
        parcel.writeInt(this.ye);
        parcel.writeInt(this.ve);
    }
}
