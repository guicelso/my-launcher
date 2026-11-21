package android.support.v4.media.session;

import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import java.lang.ref.WeakReference;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class g extends a {
    private final WeakReference mCallback;

    g(h hVar) {
        this.mCallback = new WeakReference(hVar);
    }

    @Override // android.support.v4.media.session.b
    public void a(MediaMetadataCompat mediaMetadataCompat) {
    }

    @Override // android.support.v4.media.session.b
    public void a(ParcelableVolumeInfo parcelableVolumeInfo) {
        if (((h) this.mCallback.get()) == null || parcelableVolumeInfo == null) {
            return;
        }
        int i = parcelableVolumeInfo.ue;
        int i2 = parcelableVolumeInfo.ve;
        int i3 = parcelableVolumeInfo.we;
        int i4 = parcelableVolumeInfo.xe;
        int i5 = parcelableVolumeInfo.ye;
    }

    public void a(PlaybackStateCompat playbackStateCompat) {
    }

    public void f(int i) {
        if (((h) this.mCallback.get()) != null) {
            Integer.valueOf(i);
        }
    }

    public void g(int i) {
        if (((h) this.mCallback.get()) != null) {
            Integer.valueOf(i);
        }
    }

    public void g(boolean z) {
        if (((h) this.mCallback.get()) != null) {
            Boolean.valueOf(z);
        }
    }

    public void onEvent(String str, Bundle bundle) {
    }

    @Override // android.support.v4.media.session.b
    public void onExtrasChanged(Bundle bundle) {
    }

    @Override // android.support.v4.media.session.b
    public void onQueueChanged(List list) {
    }

    @Override // android.support.v4.media.session.b
    public void onQueueTitleChanged(CharSequence charSequence) {
    }

    @Override // android.support.v4.media.session.b
    public void onSessionDestroyed() {
    }

    public void v() {
    }
}
