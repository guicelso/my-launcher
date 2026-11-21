package android.support.v4.media.session;

import android.media.session.MediaSession;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaDescriptionCompat;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class MediaSessionCompat$QueueItem implements Parcelable {
    public static final Parcelable.Creator CREATOR = new m();
    private final MediaDescriptionCompat mDescription;
    private final long mId;

    MediaSessionCompat$QueueItem(Parcel parcel) {
        this.mDescription = (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readLong();
    }

    private MediaSessionCompat$QueueItem(Object obj, MediaDescriptionCompat mediaDescriptionCompat, long j) {
        if (mediaDescriptionCompat == null) {
            throw new IllegalArgumentException("Description cannot be null.");
        }
        if (j == -1) {
            throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
        }
        this.mDescription = mediaDescriptionCompat;
        this.mId = j;
    }

    public static List b(List list) {
        MediaSessionCompat$QueueItem mediaSessionCompat$QueueItem;
        if (list == null) {
            return null;
        }
        int i = Build.VERSION.SDK_INT;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj != null) {
                int i2 = Build.VERSION.SDK_INT;
                MediaSession.QueueItem queueItem = (MediaSession.QueueItem) obj;
                mediaSessionCompat$QueueItem = new MediaSessionCompat$QueueItem(obj, MediaDescriptionCompat.e(queueItem.getDescription()), queueItem.getQueueId());
            } else {
                mediaSessionCompat$QueueItem = null;
            }
            arrayList.add(mediaSessionCompat$QueueItem);
        }
        return arrayList;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder sbE = b.a.a.a.a.e("MediaSession.QueueItem {Description=");
        sbE.append(this.mDescription);
        sbE.append(", Id=");
        sbE.append(this.mId);
        sbE.append(" }");
        return sbE.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mDescription.writeToParcel(parcel, i);
        parcel.writeLong(this.mId);
    }
}
