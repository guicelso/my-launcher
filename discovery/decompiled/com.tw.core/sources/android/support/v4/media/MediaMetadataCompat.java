package android.support.v4.media;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class MediaMetadataCompat implements Parcelable {
    public static final Parcelable.Creator CREATOR;
    private static final android.support.v4.n.e nN = new android.support.v4.n.e();
    private static final String[] nO;
    private static final String[] nP;
    private static final String[] nQ;
    private final Bundle nR;

    static {
        nN.put("android.media.metadata.TITLE", 1);
        nN.put("android.media.metadata.ARTIST", 1);
        nN.put("android.media.metadata.DURATION", 0);
        nN.put("android.media.metadata.ALBUM", 1);
        nN.put("android.media.metadata.AUTHOR", 1);
        nN.put("android.media.metadata.WRITER", 1);
        nN.put("android.media.metadata.COMPOSER", 1);
        nN.put("android.media.metadata.COMPILATION", 1);
        nN.put("android.media.metadata.DATE", 1);
        nN.put("android.media.metadata.YEAR", 0);
        nN.put("android.media.metadata.GENRE", 1);
        nN.put("android.media.metadata.TRACK_NUMBER", 0);
        nN.put("android.media.metadata.NUM_TRACKS", 0);
        nN.put("android.media.metadata.DISC_NUMBER", 0);
        nN.put("android.media.metadata.ALBUM_ARTIST", 1);
        nN.put("android.media.metadata.ART", 2);
        nN.put("android.media.metadata.ART_URI", 1);
        nN.put("android.media.metadata.ALBUM_ART", 2);
        nN.put("android.media.metadata.ALBUM_ART_URI", 1);
        nN.put("android.media.metadata.USER_RATING", 3);
        nN.put("android.media.metadata.RATING", 3);
        nN.put("android.media.metadata.DISPLAY_TITLE", 1);
        nN.put("android.media.metadata.DISPLAY_SUBTITLE", 1);
        nN.put("android.media.metadata.DISPLAY_DESCRIPTION", 1);
        nN.put("android.media.metadata.DISPLAY_ICON", 2);
        nN.put("android.media.metadata.DISPLAY_ICON_URI", 1);
        nN.put("android.media.metadata.MEDIA_ID", 1);
        nP = new String[]{"android.media.metadata.TITLE", "android.media.metadata.ARTIST", "android.media.metadata.ALBUM", "android.media.metadata.ALBUM_ARTIST", "android.media.metadata.WRITER", "android.media.metadata.AUTHOR", "android.media.metadata.COMPOSER"};
        nO = new String[]{"android.media.metadata.DISPLAY_ICON", "android.media.metadata.ART", "android.media.metadata.ALBUM_ART"};
        nQ = new String[]{"android.media.metadata.DISPLAY_ICON_URI", "android.media.metadata.ART_URI", "android.media.metadata.ALBUM_ART_URI"};
        CREATOR = new h();
    }

    private MediaMetadataCompat(Parcel parcel) {
        this.nR = parcel.readBundle();
    }

    /* synthetic */ MediaMetadataCompat(Parcel parcel, MediaMetadataCompat mediaMetadataCompat) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.nR);
    }
}
