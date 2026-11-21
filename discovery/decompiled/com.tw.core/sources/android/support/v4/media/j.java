package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class j {
    public static Object rH(Object obj) {
        return ((MediaDescription.Builder) obj).build();
    }

    public static Object rI() {
        return new MediaDescription.Builder();
    }

    public static void rJ(Object obj, CharSequence charSequence) {
        ((MediaDescription.Builder) obj).setDescription(charSequence);
    }

    public static void rK(Object obj, Bundle bundle) {
        ((MediaDescription.Builder) obj).setExtras(bundle);
    }

    public static void rL(Object obj, Bitmap bitmap) {
        ((MediaDescription.Builder) obj).setIconBitmap(bitmap);
    }

    public static void rM(Object obj, Uri uri) {
        ((MediaDescription.Builder) obj).setIconUri(uri);
    }

    public static void rN(Object obj, String str) {
        ((MediaDescription.Builder) obj).setMediaId(str);
    }

    public static void rO(Object obj, CharSequence charSequence) {
        ((MediaDescription.Builder) obj).setSubtitle(charSequence);
    }

    public static void rP(Object obj, CharSequence charSequence) {
        ((MediaDescription.Builder) obj).setTitle(charSequence);
    }
}
