package com.android.fmradio;

import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;

/* JADX INFO: loaded from: classes4.dex */
public class FmUtils {
    public static final int CONVERT_RATE;
    private static final int HIGHEST_STATION;
    public static final boolean support50ksearch = "1".equals(SystemPropertiesProxy.get("persist.support.50ksearch", "0"));

    static {
        HIGHEST_STATION = support50ksearch ? 10800 : 1080;
        CONVERT_RATE = support50ksearch ? 100 : 10;
    }

    public static float getHighestFrequency() {
        return computeFrequency(HIGHEST_STATION);
    }

    public static float computeFrequency(int station) {
        return station / CONVERT_RATE;
    }

    public static int computeStation(float frequency) {
        return (int) (CONVERT_RATE * frequency);
    }
}
