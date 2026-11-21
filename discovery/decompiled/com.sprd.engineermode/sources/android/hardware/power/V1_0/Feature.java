package android.hardware.power.V1_0;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class Feature {
    public static final int POWER_FEATURE_DOUBLE_TAP_TO_WAKE = 1;

    public static final String toString(int o) {
        if (o == 1) {
            return "POWER_FEATURE_DOUBLE_TAP_TO_WAKE";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & 1) == 1) {
            list.add("POWER_FEATURE_DOUBLE_TAP_TO_WAKE");
            flipped = 0 | 1;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
