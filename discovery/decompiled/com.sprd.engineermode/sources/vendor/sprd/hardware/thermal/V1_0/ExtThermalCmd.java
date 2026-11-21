package vendor.sprd.hardware.thermal.V1_0;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public final class ExtThermalCmd {
    public static final int THMCMD_GET_CHG_STAT = 10;
    public static final int THMCMD_GET_PA_STAT = 7;
    public static final int THMCMD_GET_STAT = 4;
    public static final int THMCMD_SET_CHG_DIS = 9;
    public static final int THMCMD_SET_CHG_EN = 8;
    public static final int THMCMD_SET_DIS = 1;
    public static final int THMCMD_SET_EN = 0;
    public static final int THMCMD_SET_LIM_DIS = 12;
    public static final int THMCMD_SET_LIM_DIS_PRST = 14;
    public static final int THMCMD_SET_LIM_EN = 11;
    public static final int THMCMD_SET_LIM_EN_PRST = 13;
    public static final int THMCMD_SET_PA_DIS = 6;
    public static final int THMCMD_SET_PA_EN = 5;
    public static final int THMCMD_SET_PERF_DIS = 3;
    public static final int THMCMD_SET_PERF_EN = 2;

    public static final String toString(int o) {
        if (o == 0) {
            return "THMCMD_SET_EN";
        }
        if (o == 1) {
            return "THMCMD_SET_DIS";
        }
        if (o == 2) {
            return "THMCMD_SET_PERF_EN";
        }
        if (o == 3) {
            return "THMCMD_SET_PERF_DIS";
        }
        if (o == 4) {
            return "THMCMD_GET_STAT";
        }
        if (o == 5) {
            return "THMCMD_SET_PA_EN";
        }
        if (o == 6) {
            return "THMCMD_SET_PA_DIS";
        }
        if (o == 7) {
            return "THMCMD_GET_PA_STAT";
        }
        if (o == 8) {
            return "THMCMD_SET_CHG_EN";
        }
        if (o == 9) {
            return "THMCMD_SET_CHG_DIS";
        }
        if (o == 10) {
            return "THMCMD_GET_CHG_STAT";
        }
        if (o == 11) {
            return "THMCMD_SET_LIM_EN";
        }
        if (o == 12) {
            return "THMCMD_SET_LIM_DIS";
        }
        if (o == 13) {
            return "THMCMD_SET_LIM_EN_PRST";
        }
        if (o == 14) {
            return "THMCMD_SET_LIM_DIS_PRST";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("THMCMD_SET_EN");
        if ((o & 1) == 1) {
            list.add("THMCMD_SET_DIS");
            flipped = 0 | 1;
        }
        if ((o & 2) == 2) {
            list.add("THMCMD_SET_PERF_EN");
            flipped |= 2;
        }
        if ((o & 3) == 3) {
            list.add("THMCMD_SET_PERF_DIS");
            flipped |= 3;
        }
        if ((o & 4) == 4) {
            list.add("THMCMD_GET_STAT");
            flipped |= 4;
        }
        if ((o & 5) == 5) {
            list.add("THMCMD_SET_PA_EN");
            flipped |= 5;
        }
        if ((o & 6) == 6) {
            list.add("THMCMD_SET_PA_DIS");
            flipped |= 6;
        }
        if ((o & 7) == 7) {
            list.add("THMCMD_GET_PA_STAT");
            flipped |= 7;
        }
        if ((o & 8) == 8) {
            list.add("THMCMD_SET_CHG_EN");
            flipped |= 8;
        }
        if ((o & 9) == 9) {
            list.add("THMCMD_SET_CHG_DIS");
            flipped |= 9;
        }
        if ((o & 10) == 10) {
            list.add("THMCMD_GET_CHG_STAT");
            flipped |= 10;
        }
        if ((o & 11) == 11) {
            list.add("THMCMD_SET_LIM_EN");
            flipped |= 11;
        }
        if ((o & 12) == 12) {
            list.add("THMCMD_SET_LIM_DIS");
            flipped |= 12;
        }
        if ((o & 13) == 13) {
            list.add("THMCMD_SET_LIM_EN_PRST");
            flipped |= 13;
        }
        if ((o & 14) == 14) {
            list.add("THMCMD_SET_LIM_DIS_PRST");
            flipped |= 14;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
