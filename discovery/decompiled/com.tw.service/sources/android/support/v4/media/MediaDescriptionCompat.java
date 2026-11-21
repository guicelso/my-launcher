package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class MediaDescriptionCompat implements Parcelable {
    public static final Parcelable.Creator CREATOR = new k();
    private final String ee;
    private final CharSequence fe;
    private final Uri ge;
    private final Uri he;
    private Object ie;
    private final CharSequence mDescription;
    private final Bundle mExtras;
    private final Bitmap mIcon;
    private final CharSequence mTitle;

    MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.ee = str;
        this.mTitle = charSequence;
        this.fe = charSequence2;
        this.mDescription = charSequence3;
        this.mIcon = bitmap;
        this.ge = uri;
        this.mExtras = bundle;
        this.he = uri2;
    }

    public static MediaDescriptionCompat e(Object obj) {
        Bundle bundle = null;
        if (obj == null) {
            return null;
        }
        int i = Build.VERSION.SDK_INT;
        l lVar = new l();
        MediaDescription mediaDescription = (MediaDescription) obj;
        lVar.setMediaId(mediaDescription.getMediaId());
        lVar.setTitle(mediaDescription.getTitle());
        lVar.setSubtitle(mediaDescription.getSubtitle());
        lVar.setDescription(mediaDescription.getDescription());
        lVar.setIconBitmap(mediaDescription.getIconBitmap());
        lVar.setIconUri(mediaDescription.getIconUri());
        Bundle extras = mediaDescription.getExtras();
        Uri uri = extras == null ? null : (Uri) extras.getParcelable("android.support.v4.media.description.MEDIA_URI");
        if (uri == null) {
            bundle = extras;
        } else if (!extras.containsKey("android.support.v4.media.description.NULL_BUNDLE_FLAG") || extras.size() != 2) {
            extras.remove("android.support.v4.media.description.MEDIA_URI");
            extras.remove("android.support.v4.media.description.NULL_BUNDLE_FLAG");
            bundle = extras;
        }
        lVar.setExtras(bundle);
        if (uri != null) {
            lVar.setMediaUri(uri);
        } else {
            int i2 = Build.VERSION.SDK_INT;
            lVar.setMediaUri(mediaDescription.getMediaUri());
        }
        MediaDescriptionCompat mediaDescriptionCompatBuild = lVar.build();
        mediaDescriptionCompatBuild.ie = obj;
        return mediaDescriptionCompatBuild;
    }

    public Object H() {
        Object obj = this.ie;
        if (obj != null) {
            return obj;
        }
        int i = Build.VERSION.SDK_INT;
        MediaDescription.Builder builder = new MediaDescription.Builder();
        builder.setMediaId(this.ee);
        builder.setTitle(this.mTitle);
        builder.setSubtitle(this.fe);
        builder.setDescription(this.mDescription);
        builder.setIconBitmap(this.mIcon);
        builder.setIconUri(this.ge);
        Bundle bundle = this.mExtras;
        int i2 = Build.VERSION.SDK_INT;
        builder.setExtras(bundle);
        int i3 = Build.VERSION.SDK_INT;
        builder.setMediaUri(this.he);
        this.ie = builder.build();
        return this.ie;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return ((Object) this.mTitle) + ", " + ((Object) this.fe) + ", " + ((Object) this.mDescription);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int i2 = Build.VERSION.SDK_INT;
        ((MediaDescription) H()).writeToParcel(parcel, i);
    }
}
