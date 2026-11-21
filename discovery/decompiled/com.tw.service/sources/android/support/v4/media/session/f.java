package android.support.v4.media.session;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import java.lang.ref.WeakReference;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class f implements k {
    private final WeakReference mCallback;

    f(h hVar) {
        this.mCallback = new WeakReference(hVar);
    }

    public void a(int i, int i2, int i3, int i4, int i5) {
    }

    public void g(Object obj) {
        if (((h) this.mCallback.get()) != null) {
            MediaMetadataCompat.f(obj);
        }
    }

    public void h(Object obj) {
        h hVar = (h) this.mCallback.get();
        if (hVar == null || hVar.oe != null) {
            return;
        }
        PlaybackStateCompat.j(obj);
    }

    public void onExtrasChanged(Bundle bundle) {
    }

    public void onQueueChanged(List list) {
        if (((h) this.mCallback.get()) != null) {
            MediaSessionCompat$QueueItem.b(list);
        }
    }

    public void onQueueTitleChanged(CharSequence charSequence) {
    }

    public void onSessionDestroyed() {
    }

    public void onSessionEvent(String str, Bundle bundle) {
        h hVar = (h) this.mCallback.get();
        if (hVar == null || hVar.oe == null) {
            return;
        }
        int i = Build.VERSION.SDK_INT;
    }
}
