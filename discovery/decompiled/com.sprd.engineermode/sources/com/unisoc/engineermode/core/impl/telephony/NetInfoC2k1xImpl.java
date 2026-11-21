package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes4.dex */
public class NetInfoC2k1xImpl implements ITelephonyApi.INetInfoC2k1x {
    private static final String[][] Description_name = {new String[]{"Not Support", "Support"}, new String[]{"Not Support", "Support"}, new String[]{"Unknown", "GEA1", "GEA2", "GEA3"}, new String[]{"A51", "A52", "A53", "A54", "A55", "A56", "A57"}, new String[]{"Unknown", "UEA0", "UEA1"}, new String[]{"Not support Hsdpa and Hsupa", "Support Hsdpa", "Support Hsupa", "Support Hsdpa and Hsupa"}, new String[]{"Not Support", "Support"}, new String[]{"Not Support", "VAMOS1", "VAMOS2"}, new String[]{"Not Support", "Support"}, new String[]{"Not Support", "Support"}, new String[]{"other", "R8", "R9"}, new String[]{"Not Support", "Support"}, new String[]{"Unknown", "eea0", "eea1", "eea2"}, new String[]{"Unknown", "eia0 ", "eia1", "eia2"}};
    private static final String TAG = "NetInfoC2k1xImpl";

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoC2k1x
    public void getServingCell(int simIdx, String[] names, String[] values) {
        String[] str1;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,12,1", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,12,1: " + result);
        char c = 0;
        if (result.contains("OK")) {
            try {
                String result2 = result.replace("--", "-+").replace(",-", ",\\+");
                Log.d(TAG, "after convert AT+SPENGMD=0,12,1: " + result2);
                String[] str2 = result2.split("\n");
                if (str2[0].startsWith("-")) {
                    str2[0] = str2[0].replaceFirst("-", "+");
                    Log.d(TAG, "after convert  AT+SPENGMD=0,12,2: " + str2[0]);
                }
                String[] str3 = str2[0].split("-");
                int i = 0;
                while (i < str3.length) {
                    if (str3[i].contains("+")) {
                        str3[i] = str3[i].replace("+", "-");
                    }
                    if (i == 0) {
                        StringBuilder sb = new StringBuilder();
                        str1 = str2;
                        sb.append(names[c]);
                        sb.append(": ");
                        sb.append(str3[i]);
                        values[c] = sb.toString();
                    } else {
                        str1 = str2;
                        if (i == 1) {
                            values[1] = names[1] + ": " + str3[i];
                        } else if (i == 2) {
                            values[2] = names[2] + ": " + str3[i];
                        } else if (i == 3) {
                            values[3] = names[3] + ": " + str3[i];
                        } else if (i == 4) {
                            values[4] = names[4] + ": " + str3[i];
                        } else if (i == 5) {
                            values[5] = names[5] + ": " + str3[i];
                        } else if (i == 6) {
                            values[6] = names[6] + ": " + str3[i];
                        } else if (i == 13) {
                            values[7] = names[7] + ": " + str3[i];
                        } else if (i == 14) {
                            values[8] = names[8] + ": " + str3[i].split(",")[0];
                        } else if (i == 15) {
                            values[9] = names[9] + ": " + str3[i];
                        }
                    }
                    i++;
                    str2 = str1;
                    c = 0;
                }
            } catch (Exception e) {
                Log.d(TAG, "" + e);
            }
        } else {
            for (int i2 = 0; i2 < names.length; i2++) {
                values[i2] = names[i2] + ": NA";
            }
        }
        String result3 = IATUtils.sendATCmd("AT+SPENGMD=0,12,2", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,12,2: " + result3);
        if (result3.contains("OK")) {
            String[] str4 = result3.replace("--", "-+").replace(",-", ",\\+").split("\n");
            if (str4[0].startsWith("-")) {
                str4[0] = str4[0].replaceFirst("-", "+");
                Log.d(TAG, "after convert  AT+SPENGMD=0,12,2: " + str4[0]);
            }
            String[] str5 = str4[0].split("-");
            for (int i3 = 0; i3 < str5.length; i3++) {
                if (str5[i3].contains("+")) {
                    str5[i3] = str5[i3].replace("+", "-");
                }
                if (i3 == 0) {
                    values[10] = names[10] + ": " + str5[i3];
                } else if (i3 == 1) {
                    values[11] = names[11] + ": " + str5[i3];
                } else if (i3 == 2) {
                    values[12] = names[12] + ": " + str5[i3];
                }
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoC2k1x
    public void getAdjacentCell(int simIdx, String[][] values) {
        int col = values[0].length;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,12,3", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,12,3: " + result);
        if (result.contains("OK")) {
            String[] str1 = result.replace("--", "-+").replace(",-", ",\\+").split("\n");
            if (str1[0].startsWith("-")) {
                str1[0] = str1[0].replaceFirst("-", "+");
                Log.d(TAG, "after convert  AT+SPENGMD=0,12,2: " + str1[0]);
            }
            String[] str2 = str1[0].split("-");
            Log.d(TAG, "str2.length: " + str2.length);
            int lrow = 0;
            for (int i = 12; i < str2.length; i++) {
                if (str2[i].contains("+")) {
                    str2[i] = str2[i].replace("+", "-");
                }
                Log.d(TAG, "str2[i]: " + str2[i]);
                String[] str3 = str2[i].split(",");
                for (int j = 0; j < str3.length; j++) {
                    int temp = Integer.parseInt(str3[j].trim());
                    values[lrow][j] = String.valueOf(temp);
                }
                lrow++;
            }
            return;
        }
        for (String[] strArr : values) {
            for (int j2 = 0; j2 < col; j2++) {
                strArr[j2] = "NA";
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoC2k1x
    public List<String> getOutfieldNetworkInfo(int simIdx, String[] names) {
        int num = 0;
        String result = IATUtils.sendATCmd(engconstents.ENG_AT_SPENGMD_007, simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,7: " + result);
        if (result.contains("OK")) {
            String[] str1 = result.replace("--", "-+").split("\n");
            String[] str2 = str1[0].split("-");
            for (int i = 0; i < str2.length; i++) {
                if (i == 0 || i == 1 || i == 2 || i == 3 || i == 6 || i == 7 || i == 9) {
                    if (i != 3) {
                        names[num] = names[num] + ": " + Description_name[i][Integer.valueOf(str2[i].substring(0, 1)).intValue()];
                    } else {
                        int response = Integer.parseInt(str2[i]);
                        int[] bite = {response % 2, (response % 4) / 2, (response % 8) / 4, (response % 16) / 8, (response % 32) / 16, (response % 64) / 32, (response % 128) / 64};
                        StringBuilder sb = new StringBuilder();
                        int j = 0;
                        for (int i2 = 6; j < i2; i2 = 6) {
                            if (bite[j] == 1) {
                                sb.append(Description_name[3][j]);
                                sb.append(" ");
                            }
                            j++;
                        }
                        names[num] = names[num] + ": " + sb.toString();
                    }
                    num++;
                }
            }
        } else {
            for (int i3 = 0; i3 < names.length; i3++) {
                names[i3] = names[i3] + ": NA";
            }
        }
        return (List) Arrays.stream(names).filter(new Predicate() { // from class: com.unisoc.engineermode.core.impl.telephony.-$$Lambda$NetInfoC2k1xImpl$ZUdTX8Ic9t4jriX62BzjiFRauxY
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return NetInfoC2k1xImpl.lambda$getOutfieldNetworkInfo$0((String) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ boolean lambda$getOutfieldNetworkInfo$0(String n) {
        return !n.contains("R9");
    }
}
