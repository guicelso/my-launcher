package android.hardware.power.V1_0;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class PowerHint {
    public static final int INTERACTION = 2;
    public static final int LAUNCH = 8;
    public static final int LOW_POWER = 5;
    public static final int SUSTAINED_PERFORMANCE = 6;
    public static final int VIDEO_DECODE = 4;
    public static final int VIDEO_ENCODE = 3;
    public static final int VR_MODE = 7;
    public static final int VSYNC = 1;

    public static final String toString(int o) {
        if (o == 1) {
            return "VSYNC";
        }
        if (o == 2) {
            return "INTERACTION";
        }
        if (o == 3) {
            return "VIDEO_ENCODE";
        }
        if (o == 4) {
            return "VIDEO_DECODE";
        }
        if (o == 5) {
            return "LOW_POWER";
        }
        if (o == 6) {
            return "SUSTAINED_PERFORMANCE";
        }
        if (o == 7) {
            return "VR_MODE";
        }
        if (o == 8) {
            return "LAUNCH";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & 1) == 1) {
            list.add("VSYNC");
            flipped = 0 | 1;
        }
        if ((o & 2) == 2) {
            list.add("INTERACTION");
            flipped |= 2;
        }
        if ((o & 3) == 3) {
            list.add("VIDEO_ENCODE");
            flipped |= 3;
        }
        if ((o & 4) == 4) {
            list.add("VIDEO_DECODE");
            flipped |= 4;
        }
        if ((o & 5) == 5) {
            list.add("LOW_POWER");
            flipped |= 5;
        }
        if ((o & 6) == 6) {
            list.add("SUSTAINED_PERFORMANCE");
            flipped |= 6;
        }
        if ((o & 7) == 7) {
            list.add("VR_MODE");
            flipped |= 7;
        }
        if ((o & 8) == 8) {
            list.add("LAUNCH");
            flipped |= 8;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
