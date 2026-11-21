package android.support.v4.media;

import android.content.Intent;
import android.os.IBinder;

/* JADX INFO: loaded from: classes3.dex */
class MediaLibraryService2ImplBase extends MediaSessionService2ImplBase {
    MediaLibraryService2ImplBase() {
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0028  */
    @Override // android.support.v4.media.MediaSessionService2ImplBase, android.support.v4.media.MediaSessionService2.SupportLibraryImpl
    public IBinder onBind(Intent intent) {
        byte b;
        String action = intent.getAction();
        int iHashCode = action.hashCode();
        if (iHashCode != 901933117) {
            if (iHashCode == 1665850838 && action.equals(MediaBrowserServiceCompat.SERVICE_INTERFACE)) {
                b = 1;
            } else {
                b = -1;
            }
        } else if (action.equals(MediaLibraryService2.SERVICE_INTERFACE)) {
            b = 0;
        } else {
            b = -1;
        }
        if (b == 0) {
            return getSession().getSessionBinder();
        }
        if (b == 1) {
            return getSession().getImpl().getLegacySessionBinder();
        }
        return super.onBind(intent);
    }

    @Override // android.support.v4.media.MediaSessionService2ImplBase, android.support.v4.media.MediaSessionService2.SupportLibraryImpl
    public MediaLibraryService2.MediaLibrarySession getSession() {
        return (MediaLibraryService2.MediaLibrarySession) super.getSession();
    }

    @Override // android.support.v4.media.MediaSessionService2ImplBase, android.support.v4.media.MediaSessionService2.SupportLibraryImpl
    public int getSessionType() {
        return 2;
    }
}
