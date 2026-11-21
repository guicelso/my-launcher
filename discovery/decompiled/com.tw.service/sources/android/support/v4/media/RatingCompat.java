package android.support.v4.media;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class RatingCompat implements Parcelable {
    public static final Parcelable.Creator CREATOR = new n();
    private final int ke;
    private final float le;

    RatingCompat(int i, float f) {
        this.ke = i;
        this.le = f;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.ke;
    }

    public String toString() {
        StringBuilder sbE = b.a.a.a.a.e("Rating:style=");
        sbE.append(this.ke);
        sbE.append(" rating=");
        float f = this.le;
        sbE.append(f < 0.0f ? "unrated" : String.valueOf(f));
        return sbE.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.ke);
        parcel.writeFloat(this.le);
    }
}
