package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class NetInfoC2kImpl implements ITelephonyApi.INetInfoC2k {
    private static final String[][] Description_name = {new String[]{"Not Support", "Support"}, new String[]{"Not Support", "Support"}, new String[]{"Unknown", "GEA1", "GEA2", "GEA3"}, new String[]{"A51", "A52", "A53", "A54", "A55", "A56", "A57"}, new String[]{"Unknown", "UEA0", "UEA1"}, new String[]{"Not support Hsdpa and Hsupa", "Support Hsdpa", "Support Hsupa", "Support Hsdpa and Hsupa"}, new String[]{"Not Support", "Support"}, new String[]{"Not Support", "VAMOS1", "VAMOS2"}, new String[]{"Not Support", "Support"}, new String[]{"Not Support", "Support"}, new String[]{"other", "R8", "R9"}, new String[]{"Not Support", "Support"}, new String[]{"Unknown", "eea0", "eea1", "eea2"}, new String[]{"Unknown", "eia0 ", "eia1", "eia2"}};
    private static final String TAG = "NetInfoC2kImpl";

    /* JADX WARN: Code duplicated, block: B:38:0x012f A[Catch: Exception -> 0x0248, TryCatch #2 {Exception -> 0x0248, blocks: (B:36:0x0116, B:38:0x012f, B:39:0x014f, B:40:0x0157, B:42:0x015a, B:44:0x0162, B:47:0x016d, B:50:0x018e, B:53:0x01af, B:56:0x01d0, B:59:0x01ef, B:62:0x020e, B:65:0x022b), top: B:75:0x0116 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x015a A[Catch: Exception -> 0x0248, TryCatch #2 {Exception -> 0x0248, blocks: (B:36:0x0116, B:38:0x012f, B:39:0x014f, B:40:0x0157, B:42:0x015a, B:44:0x0162, B:47:0x016d, B:50:0x018e, B:53:0x01af, B:56:0x01d0, B:59:0x01ef, B:62:0x020e, B:65:0x022b), top: B:75:0x0116 }] */
    /* JADX WARN: Code duplicated, block: B:44:0x0162 A[Catch: Exception -> 0x0248, TryCatch #2 {Exception -> 0x0248, blocks: (B:36:0x0116, B:38:0x012f, B:39:0x014f, B:40:0x0157, B:42:0x015a, B:44:0x0162, B:47:0x016d, B:50:0x018e, B:53:0x01af, B:56:0x01d0, B:59:0x01ef, B:62:0x020e, B:65:0x022b), top: B:75:0x0116 }] */
    /* JADX WARN: Code duplicated, block: B:47:0x016d A[Catch: Exception -> 0x0248, TryCatch #2 {Exception -> 0x0248, blocks: (B:36:0x0116, B:38:0x012f, B:39:0x014f, B:40:0x0157, B:42:0x015a, B:44:0x0162, B:47:0x016d, B:50:0x018e, B:53:0x01af, B:56:0x01d0, B:59:0x01ef, B:62:0x020e, B:65:0x022b), top: B:75:0x0116 }] */
    /* JADX WARN: Code duplicated, block: B:48:0x018a  */
    /* JADX WARN: Code duplicated, block: B:50:0x018e A[Catch: Exception -> 0x0248, TryCatch #2 {Exception -> 0x0248, blocks: (B:36:0x0116, B:38:0x012f, B:39:0x014f, B:40:0x0157, B:42:0x015a, B:44:0x0162, B:47:0x016d, B:50:0x018e, B:53:0x01af, B:56:0x01d0, B:59:0x01ef, B:62:0x020e, B:65:0x022b), top: B:75:0x0116 }] */
    /* JADX WARN: Code duplicated, block: B:51:0x01ab  */
    /* JADX WARN: Code duplicated, block: B:53:0x01af A[Catch: Exception -> 0x0248, TryCatch #2 {Exception -> 0x0248, blocks: (B:36:0x0116, B:38:0x012f, B:39:0x014f, B:40:0x0157, B:42:0x015a, B:44:0x0162, B:47:0x016d, B:50:0x018e, B:53:0x01af, B:56:0x01d0, B:59:0x01ef, B:62:0x020e, B:65:0x022b), top: B:75:0x0116 }] */
    /* JADX WARN: Code duplicated, block: B:54:0x01cb  */
    /* JADX WARN: Code duplicated, block: B:56:0x01d0 A[Catch: Exception -> 0x0248, TryCatch #2 {Exception -> 0x0248, blocks: (B:36:0x0116, B:38:0x012f, B:39:0x014f, B:40:0x0157, B:42:0x015a, B:44:0x0162, B:47:0x016d, B:50:0x018e, B:53:0x01af, B:56:0x01d0, B:59:0x01ef, B:62:0x020e, B:65:0x022b), top: B:75:0x0116 }] */
    /* JADX WARN: Code duplicated, block: B:57:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:59:0x01ef A[Catch: Exception -> 0x0248, TryCatch #2 {Exception -> 0x0248, blocks: (B:36:0x0116, B:38:0x012f, B:39:0x014f, B:40:0x0157, B:42:0x015a, B:44:0x0162, B:47:0x016d, B:50:0x018e, B:53:0x01af, B:56:0x01d0, B:59:0x01ef, B:62:0x020e, B:65:0x022b), top: B:75:0x0116 }] */
    /* JADX WARN: Code duplicated, block: B:60:0x020a  */
    /* JADX WARN: Code duplicated, block: B:62:0x020e A[Catch: Exception -> 0x0248, TryCatch #2 {Exception -> 0x0248, blocks: (B:36:0x0116, B:38:0x012f, B:39:0x014f, B:40:0x0157, B:42:0x015a, B:44:0x0162, B:47:0x016d, B:50:0x018e, B:53:0x01af, B:56:0x01d0, B:59:0x01ef, B:62:0x020e, B:65:0x022b), top: B:75:0x0116 }] */
    /* JADX WARN: Code duplicated, block: B:63:0x0227  */
    /* JADX WARN: Code duplicated, block: B:65:0x022b A[Catch: Exception -> 0x0248, TRY_LEAVE, TryCatch #2 {Exception -> 0x0248, blocks: (B:36:0x0116, B:38:0x012f, B:39:0x014f, B:40:0x0157, B:42:0x015a, B:44:0x0162, B:47:0x016d, B:50:0x018e, B:53:0x01af, B:56:0x01d0, B:59:0x01ef, B:62:0x020e, B:65:0x022b), top: B:75:0x0116 }] */
    /* JADX WARN: Code duplicated, block: B:75:0x0116 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:87:0x0243 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:92:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:38:0x012f, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:47:0x016d, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:50:0x018e, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:53:0x01af, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:56:0x01d0, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:59:0x01ef, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:62:0x020e, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:65:0x022b, please report this as an issue */
    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoC2k
    public void getServingCell(int simIdx, String[] names, String[] values) {
        String[] str1;
        String result;
        String result2;
        String[] str2;
        String[] str3;
        int i;
        String result3 = IATUtils.sendATCmd("AT+SPENGMD=0,13,0", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,13,0: " + result3);
        if (result3.contains("OK")) {
            try {
                String result4 = result3.replaceAll("--", "-+").replaceAll(",-", ",\\+");
                try {
                    String[] str4 = result4.split("\n");
                    if (str4[0].startsWith("-")) {
                        str4[0] = str4[0].replaceFirst("-", "+");
                        Log.d(TAG, "after convert  AT+SPENGMD=0,12,2: " + str4[0]);
                    }
                    String[] str5 = str4[0].split("-");
                    int i2 = 0;
                    while (i2 < str5.length) {
                        if (str5[i2].contains("+")) {
                            str5[i2] = str5[i2].replace("+", "-");
                        }
                        if (i2 == 1) {
                            StringBuilder sb = new StringBuilder();
                            str1 = str4;
                            sb.append(names[0]);
                            sb.append(": ");
                            sb.append(str5[i2]);
                            values[0] = sb.toString();
                            result = result4;
                        } else {
                            str1 = str4;
                            if (i2 != 4) {
                                result = result4;
                            } else {
                                StringBuilder sb2 = new StringBuilder();
                                result = result4;
                                try {
                                    sb2.append(names[1]);
                                    sb2.append(": ");
                                    sb2.append(str5[i2]);
                                    values[1] = sb2.toString();
                                } catch (Exception e) {
                                    e = e;
                                    Log.d(TAG, "" + e);
                                    result2 = IATUtils.sendATCmd("AT+SPENGMD=0,13,2", simIdx);
                                    Log.d(TAG, "AT+SPENGMD=0,13,2: " + result2);
                                    if (result2.contains("OK")) {
                                        try {
                                            str2 = result2.replaceAll("--", "-+").replaceAll(",-", ",\\+").split("\n");
                                            if (str2[0].startsWith("-")) {
                                                str2[0] = str2[0].replaceFirst("-", "+");
                                                Log.d(TAG, "after convert  AT+SPENGMD=0,13,2: " + str2[0]);
                                            }
                                            str3 = str2[0].split("-");
                                            for (i = 0; i < str3.length; i++) {
                                                if (str3[i].contains("+")) {
                                                    str3[i] = str3[i].replace("+", "-");
                                                }
                                                if (i == 7) {
                                                    values[2] = names[2] + ": " + str3[i];
                                                } else if (i == 8) {
                                                    values[3] = names[3] + ": " + str3[i];
                                                } else if (i == 10) {
                                                    values[4] = names[4] + ": " + str3[i];
                                                } else if (i == 11) {
                                                    values[5] = names[5] + ": " + str3[i];
                                                } else if (i == 12) {
                                                    values[6] = names[6] + ": " + str3[i];
                                                } else if (i == 14) {
                                                    values[7] = names[7] + ": " + str3[i];
                                                } else if (i == 15) {
                                                    values[8] = names[8] + ": " + str3[i];
                                                }
                                            }
                                        } catch (Exception e2) {
                                            Log.d(TAG, "" + e2);
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                        i2++;
                        str4 = str1;
                        result4 = result;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Exception e4) {
                e = e4;
            }
        }
        result2 = IATUtils.sendATCmd("AT+SPENGMD=0,13,2", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,13,2: " + result2);
        if (result2.contains("OK")) {
            str2 = result2.replaceAll("--", "-+").replaceAll(",-", ",\\+").split("\n");
            if (str2[0].startsWith("-")) {
                str2[0] = str2[0].replaceFirst("-", "+");
                Log.d(TAG, "after convert  AT+SPENGMD=0,13,2: " + str2[0]);
            }
            str3 = str2[0].split("-");
            while (i < str3.length) {
                if (str3[i].contains("+")) {
                    str3[i] = str3[i].replace("+", "-");
                }
                if (i == 7) {
                    values[2] = names[2] + ": " + str3[i];
                } else if (i == 8) {
                    values[3] = names[3] + ": " + str3[i];
                } else if (i == 10) {
                    values[4] = names[4] + ": " + str3[i];
                } else if (i == 11) {
                    values[5] = names[5] + ": " + str3[i];
                } else if (i == 12) {
                    values[6] = names[6] + ": " + str3[i];
                } else if (i == 14) {
                    values[7] = names[7] + ": " + str3[i];
                } else if (i == 15) {
                    values[8] = names[8] + ": " + str3[i];
                }
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoC2k
    public void getAdjacentCell(int simIdx, String[][] values) {
        int COL = values[0].length;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,13,3", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,13,3: " + result);
        if (result.contains("OK")) {
            try {
                String[] str1 = result.replaceAll("--", "-+").replaceAll(",-", ",\\+").split("\n");
                if (str1[0].startsWith("-")) {
                    str1[0] = str1[0].replaceFirst("-", "+");
                    Log.d(TAG, "after convert  AT+SPENGMD=0,13,2: " + str1[0]);
                }
                String[] str2 = str1[0].split("-");
                Log.d(TAG, "str2.length: " + str2.length);
                int row = 0;
                for (int i = 12; i < str2.length; i++) {
                    if (str2[i].contains("+")) {
                        str2[i] = str2[i].replace("+", "-");
                    }
                    Log.d(TAG, "str2[i]: " + str2[i]);
                    String[] str3 = str2[i].split(",");
                    for (int j = 0; j < str3.length; j++) {
                        int temp = Integer.parseInt(str3[j].trim());
                        values[row][j] = String.valueOf(temp);
                    }
                    row++;
                }
                return;
            } catch (Exception e) {
                Log.d(TAG, "" + e);
                return;
            }
        }
        for (String[] strArr : values) {
            for (int j2 = 0; j2 < COL; j2++) {
                strArr[j2] = "NA";
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoC2k
    public void getBetweenAdjacentCell2G(int simIdx, String[][] values) {
        String[] str2;
        int COL = values[0].length;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,12,4", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,12,4: " + result);
        if (result.contains("OK")) {
            String[] str1 = result.replaceAll(",-", ",+").replaceAll("--", "-+").split("\n");
            String[] str3 = str1[0].split("-");
            int i = 0;
            while (i < 5) {
                int i2 = 3;
                if (i < str3.length - 3) {
                    if (!str3[i + 3].contains(",")) {
                        str2 = str3;
                    } else {
                        String[] str4 = str3[i + 3].split(",");
                        int j = 1;
                        while (j < 4) {
                            if (j != i2) {
                                String[] str5 = values[i];
                                str5[j - 1] = str4[j].replace("+", "-");
                            } else {
                                values[i][j - 1] = str4[j].replace("+", "-") + "dBm";
                            }
                            j++;
                            str3 = str3;
                            i2 = 3;
                        }
                        str2 = str3;
                    }
                } else {
                    str2 = str3;
                    for (int j2 = 0; j2 < 3; j2++) {
                        values[i][j2] = "NA";
                    }
                }
                i++;
                str3 = str2;
            }
            return;
        }
        for (String[] strArr : values) {
            for (int j3 = 0; j3 < COL; j3++) {
                strArr[j3] = "NA";
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoC2k
    public void getBetweenAdjacentCell4G(int simIdx, String[][] values) {
        int COL = values[0].length;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,13,4", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,13,4: " + result);
        if (result.contains("OK")) {
            try {
                String[] str1 = result.replaceAll("--", "-+").replaceAll(",-", ",\\+").split("\n");
                if (str1[0].startsWith("-")) {
                    str1[0] = str1[0].replaceFirst("-", "+");
                    Log.d(TAG, "after convert  AT+SPENGMD=0,13,4: " + str1[0]);
                }
                String[] str2 = str1[0].split("-");
                Log.d(TAG, "str2.length: " + str2.length);
                int row = 0;
                for (int i = 12; i < str2.length; i++) {
                    if (str2[i].contains("+")) {
                        str2[i] = str2[i].replace("+", "-");
                    }
                    Log.d(TAG, "str2[i]: " + str2[i]);
                    String[] str3 = str2[i].split(",");
                    for (int j = 0; j < str3.length; j++) {
                        int temp = Integer.parseInt(str3[j].trim());
                        values[row][j] = String.valueOf(temp);
                    }
                    row++;
                }
                return;
            } catch (Exception e) {
                Log.d(TAG, "" + e);
                return;
            }
        }
        for (String[] strArr : values) {
            for (int j2 = 0; j2 < COL; j2++) {
                strArr[j2] = "NA";
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoC2k
    public List<String> getOutfieldNetworkInfo(int simIdx, String[] names) {
        int num = 0;
        String result = IATUtils.sendATCmd(engconstents.ENG_AT_SPENGMD_007, simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,7: " + result);
        if (result.contains("OK")) {
            String[] str1 = result.replaceAll("--", "-+").split("\n");
            String[] str2 = str1[0].split("-");
            for (int i = 0; i < str2.length; i++) {
                if ((i == 4 || i == 5 || i == 6 || i == 8 || i == 10 || i == 11) && num < names.length) {
                    names[num] = names[num] + ": " + Description_name[i][Integer.valueOf(str2[i].substring(0, 1)).intValue()];
                    num++;
                }
            }
        } else {
            for (int i2 = 0; i2 < names.length; i2++) {
                names[i2] = names[i2] + ": NA";
            }
        }
        int num2 = 6;
        List<String> nwCapValues = getNwCap(simIdx);
        if (nwCapValues != null) {
            for (int i3 = 0; i3 < nwCapValues.size(); i3++) {
                if (i3 == nwCapValues.size() - 1) {
                    names[num2] = names[num2] + ": " + nwCapValues.get(i3);
                } else {
                    String isSupport = nwCapValues.get(i3).equals("1") ? "support" : "not support";
                    names[num2] = names[num2] + ": " + isSupport;
                }
                num2++;
            }
        } else {
            for (int i4 = 6; i4 < names.length; i4++) {
                names[i4] = names[i4] + ": NA";
            }
        }
        List<String> list = new ArrayList<>();
        for (int i5 = 0; i5 < names.length; i5++) {
            if (!names[i5].contains("R9")) {
                list.add(names[i5]);
            }
        }
        return list;
    }

    private List<String> getNwCap(int simIdx) {
        return NetInfoWcdma.getNwCap(simIdx);
    }
}
