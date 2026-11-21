package com.android.fmradio;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import com.unisoc.engineermode.core.impl.nonpublic.AudioManagerProxy;
import com.unisoc.engineermode.core.impl.nonpublic.AudioSystemProxy;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class FmManagerSelect {
    private static final String LOGTAG = "FmManagerSelect";
    private AudioManager mAudioManager;
    private final Context mContext;

    public FmManagerSelect(Context context) {
        this.mAudioManager = null;
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
    }

    public boolean powerUp(float frequency) {
        return FmNative.powerUp(frequency);
    }

    public boolean powerDown() {
        return FmNative.powerDown(0);
    }

    public boolean openDev() {
        return FmNative.openDev();
    }

    public boolean closeDev() {
        return FmNative.closeDev();
    }

    public boolean tuneRadio(float frequency) {
        return FmNative.tune(frequency);
    }

    public boolean tuneRadioAgain(float frequency) {
        return true;
    }

    public int getFreq() {
        return -1;
    }

    public boolean setVolume(int volume) {
        return false;
    }

    public int setMute(boolean isMute) {
        return FmNative.setMute(isMute);
    }

    public boolean setAudioPathEnable(FmConstants.AudioPath path, boolean enable) throws IllegalAccessException, InvocationTargetException {
        if (enable) {
            Log.d(LOGTAG, "setAudioPathEnable setDeviceConnectionStateForFM + FmConstants.DEVICE_OUT_FM_HEADSET=67108864");
            AudioManagerProxy.setDeviceConnectionStateForFM(AudioManagerProxy.DEVICE_OUT_FM_HEADSET, AudioSystemProxy.DEVICE_STATE_AVAILABLE, "", "");
            return true;
        }
        AudioManagerProxy.setDeviceConnectionStateForFM(AudioManagerProxy.DEVICE_OUT_FM_HEADSET, AudioSystemProxy.DEVICE_STATE_UNAVAILABLE, "", "");
        return true;
    }

    public boolean setSpeakerEnable(FmConstants.AudioPath path, boolean isSpeaker) {
        if (isSpeaker) {
            AudioSystemProxy.setForceUse(AudioSystemProxy.FOR_FM, AudioSystemProxy.FORCE_SPEAKER);
            this.mAudioManager.setSpeakerphoneOn(true);
        } else {
            AudioSystemProxy.setForceUse(AudioSystemProxy.FOR_FM, AudioSystemProxy.FORCE_NONE);
            this.mAudioManager.setSpeakerphoneOn(false);
        }
        return true;
    }

    public int setRdsMode(boolean rdsMode, boolean enableAf) {
        return FmNative.setRds(rdsMode);
    }

    public int isRdsSupported() {
        return FmNative.isRdsSupport();
    }

    public String getFmBler() {
        return String.valueOf(FmNative.getBler()) + "%";
    }

    private int convertAudioPathForSprd(FmConstants.AudioPath path) {
        return (path != FmConstants.AudioPath.FM_AUDIO_PATH_HEADSET && path == FmConstants.AudioPath.FM_AUDIO_PATH_NONE) ? FmConstants.DEVICE_OUT_FM_HEADSET : FmConstants.DEVICE_OUT_FM_HEADSET;
    }

    public boolean isFmOn() {
        return false;
    }

    public boolean setStepType(FmConstants.StepType type) {
        return false;
    }

    public boolean isUseBrcmFmChip() {
        return false;
    }
}
