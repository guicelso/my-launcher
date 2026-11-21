package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public class c {
    public static Object rm(Parcel parcel) {
        return MediaDescription.CREATOR.createFromParcel(parcel);
    }

    public static CharSequence rn(Object obj) {
        return ((MediaDescription) obj).getDescription();
    }

    public static Bundle ro(Object obj) {
        return ((MediaDescription) obj).getExtras();
    }

    public static Bitmap rp(Object obj) {
        return ((MediaDescription) obj).getIconBitmap();
    }

    public static Uri rq(Object obj) {
        return ((MediaDescription) obj).getIconUri();
    }

    public static String rr(Object obj) {
        return ((MediaDescription) obj).getMediaId();
    }

    public static CharSequence rs(Object obj) {
        return ((MediaDescription) obj).getSubtitle();
    }

    public static CharSequence rt(Object obj) {
        return ((MediaDescription) obj).getTitle();
    }

    public static void ru(Object obj, Parcel parcel, int i) {
        ((MediaDescription) obj).writeToParcel(parcel, i);
    }
}
