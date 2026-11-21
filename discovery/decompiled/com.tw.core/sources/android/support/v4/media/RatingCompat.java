package android.support.v4.media;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class RatingCompat implements Parcelable {
    public static final Parcelable.Creator CREATOR = new b();
    private final int oa;
    private final float ob;

    private RatingCompat(int i, float f) {
        this.oa = i;
        this.ob = f;
    }

    /* synthetic */ RatingCompat(int i, float f, RatingCompat ratingCompat) {
        this(i, f);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.oa;
    }

    public String toString() {
        return "Rating:style=" + this.oa + " rating=" + (this.ob < 0.0f ? "unrated" : String.valueOf(this.ob));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.oa);
        parcel.writeFloat(this.ob);
    }
}
