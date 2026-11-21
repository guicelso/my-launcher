package android.support.v4.media.session;

import android.media.AudioAttributes;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.Bundle;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class l extends MediaController.Callback {
    protected final k mCallback;

    public l(k kVar) {
        this.mCallback = kVar;
    }

    @Override // android.media.session.MediaController.Callback
    public void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
        int i;
        int i2;
        k kVar = this.mCallback;
        int playbackType = playbackInfo.getPlaybackType();
        AudioAttributes audioAttributes = playbackInfo.getAudioAttributes();
        if ((audioAttributes.getFlags() & 1) == 1) {
            i2 = 7;
        } else {
            if ((audioAttributes.getFlags() & 4) != 4) {
                i = 3;
                switch (audioAttributes.getUsage()) {
                    case 2:
                        i2 = 0;
                        break;
                    case 3:
                        i2 = 8;
                        break;
                    case 4:
                        i = 4;
                        break;
                    case 5:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        i2 = 5;
                        break;
                    case 6:
                        i2 = 2;
                        break;
                    case 13:
                        i = 1;
                        break;
                }
                ((f) kVar).a(playbackType, i, playbackInfo.getVolumeControl(), playbackInfo.getMaxVolume(), playbackInfo.getCurrentVolume());
            }
            i2 = 6;
        }
        i = i2;
        ((f) kVar).a(playbackType, i, playbackInfo.getVolumeControl(), playbackInfo.getMaxVolume(), playbackInfo.getCurrentVolume());
    }

    @Override // android.media.session.MediaController.Callback
    public void onExtrasChanged(Bundle bundle) {
        ((f) this.mCallback).onExtrasChanged(bundle);
    }

    @Override // android.media.session.MediaController.Callback
    public void onMetadataChanged(MediaMetadata mediaMetadata) {
        ((f) this.mCallback).g(mediaMetadata);
    }

    @Override // android.media.session.MediaController.Callback
    public void onPlaybackStateChanged(PlaybackState playbackState) {
        ((f) this.mCallback).h(playbackState);
    }

    @Override // android.media.session.MediaController.Callback
    public void onQueueChanged(List list) {
        ((f) this.mCallback).onQueueChanged(list);
    }

    @Override // android.media.session.MediaController.Callback
    public void onQueueTitleChanged(CharSequence charSequence) {
        ((f) this.mCallback).onQueueTitleChanged(charSequence);
    }

    @Override // android.media.session.MediaController.Callback
    public void onSessionDestroyed() {
        ((f) this.mCallback).onSessionDestroyed();
    }

    @Override // android.media.session.MediaController.Callback
    public void onSessionEvent(String str, Bundle bundle) {
        ((f) this.mCallback).onSessionEvent(str, bundle);
    }
}
