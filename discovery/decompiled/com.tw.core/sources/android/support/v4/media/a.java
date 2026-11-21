package android.support.v4.media;

import android.media.MediaDescription;
import android.net.Uri;

/* JADX INFO: loaded from: classes.dex */
class a extends j {
    a() {
    }

    public static void rl(Object obj, Uri uri) {
        ((MediaDescription.Builder) obj).setMediaUri(uri);
    }
}
