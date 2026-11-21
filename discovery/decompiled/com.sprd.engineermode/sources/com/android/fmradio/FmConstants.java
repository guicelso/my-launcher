package com.android.fmradio;

/* JADX INFO: loaded from: classes4.dex */
public class FmConstants {
    public static final boolean COMBO_SEARCH_MULTI_CHANNEL_DEFAULT = false;
    public static final int DEVICE_OUT_FM_HEADSET = 67108864;
    public static final boolean FM_SOFTMUTE_FEATURE_ENABLED = false;
    public static final int FOR_FM = 6;
    public static final int MAX_VOLUME = 15;
    public static final int MAX_VOLUME_BRCM = 255;
    public static final int MIN_VOLUME = 0;
    public static final int NFL_FINE = 2;
    public static final int NFL_LOW = 0;
    public static final int NFL_MED = 1;
    public static final int RDS_FEATURE_PS = 4;
    public static final int RDS_FEATURE_PTY = 8;
    public static final int RDS_FEATURE_PTYN = 32;
    public static final int RDS_FEATURE_RT = 64;
    public static final int RDS_FEATURE_TP = 16;
    public static final int STREAM_FM = 10;
    public static final int STREAM_MUSIC = 3;

    public enum AudioMode {
        FM_AUDIO_MODE_AUTO,
        FM_AUDIO_MODE_STEREO,
        FM_AUDIO_MODE_MONO,
        FM_AUDIO_MODE_BLEND,
        FM_AUDIO_MODE_UNKNOWN
    }

    public enum AudioPath {
        FM_AUDIO_PATH_NONE,
        FM_AUDIO_PATH_SPEAKER,
        FM_AUDIO_PATH_HEADSET,
        FM_AUDIO_PATH_UNKNOWN
    }

    public enum Band {
        FM_BAND_NA,
        FM_BAND_EU,
        FM_BAND_JP_STD,
        FM_BAND_JP_WIDE,
        FM_BAND_UNKNOWN
    }

    public enum MuteMode {
        FM_MUTE_MODE_UNMUTE,
        FM_MUTE_MODE_MUTE,
        FM_MUTE_MODE_UNKNOWN
    }

    public enum SearchDirection {
        FM_SEARCH_DOWN,
        FM_SEARCH_UP
    }

    public enum StepType {
        FM_STEP_50KHZ,
        FM_STEP_100KHZ,
        FM_STEP_UNKNOWN
    }
}
