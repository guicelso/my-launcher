package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaDescriptionCompat;

/* JADX INFO: loaded from: classes.dex */
public final class MediaSessionCompat$QueueItem implements Parcelable {
    public static final Parcelable.Creator CREATOR = new d();
    private final MediaDescriptionCompat nj;
    private final long nk;

    private MediaSessionCompat$QueueItem(Parcel parcel) {
        this.nj = (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
        this.nk = parcel.readLong();
    }

    /* synthetic */ MediaSessionCompat$QueueItem(Parcel parcel, MediaSessionCompat$QueueItem mediaSessionCompat$QueueItem) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "MediaSession.QueueItem {Description=" + this.nj + ", Id=" + this.nk + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.nj.writeToParcel(parcel, i);
        parcel.writeLong(this.nk);
    }
}
