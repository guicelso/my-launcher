package com.android.fmradio;

import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public class FmNative {

    public static class FmAudioThresholdParms {
        public int hbound;
        public int lbound;
        public byte phyt;
        public int power_th;
        public byte snr_th;
    }

    public static class FmRegCtlParms {
        public int addr;
        public byte err;
        public byte rw_flag;
        public int val;
    }

    public static class FmSeekCriteriaParms {
        public int freq_offset_th;
        public int noise_power_th;
        public int pilot_power_th;
        public int rssi_th;
        public byte snr_th;
    }

    public static native short activeAf();

    public static native short[] autoScan(int i);

    public static native boolean closeDev();

    public static native int getAudioParm(FmAudioThresholdParms fmAudioThresholdParms);

    public static native int getBler();

    public static native byte[] getLrText();

    public static native byte[] getPs();

    public static native int getRssi();

    public static native int getSnr();

    public static native int getTuneParm(FmSeekCriteriaParms fmSeekCriteriaParms);

    public static native int isRdsSupport();

    public static native boolean openDev();

    public static native boolean powerDown(int i);

    public static native boolean powerUp(float f);

    public static native short readRds();

    public static native int readRegParm(FmRegCtlParms fmRegCtlParms);

    public static native float seek(float f, boolean z);

    public static native int setAudioParm(FmAudioThresholdParms fmAudioThresholdParms);

    public static native int setMute(boolean z);

    public static native int setRds(boolean z);

    public static native int setTuneParm(FmSeekCriteriaParms fmSeekCriteriaParms);

    public static native boolean stopScan();

    public static native int switchAntenna(int i);

    public static native boolean tune(float f);

    public static native int writeRegParm(FmRegCtlParms fmRegCtlParms);

    static {
        try {
            System.loadLibrary("fmjni");
        } catch (UnsatisfiedLinkError e) {
            Log.d("FmNative", " #loadLibrary   fmjni failed  ");
            e.printStackTrace();
        }
    }
}
