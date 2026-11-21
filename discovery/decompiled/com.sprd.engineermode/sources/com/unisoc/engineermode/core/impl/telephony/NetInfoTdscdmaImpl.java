package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
class NetInfoTdscdmaImpl implements ITelephonyApi.INetInfoTdscdma {
    private static String[][] Description_name = {new String[]{"Not Support", "Support"}, new String[]{"Not Support", "Support"}, new String[]{"Unknown", "GEA1", "GEA2", "GEA3"}, new String[]{"A51", "A52", "A53", "A54", "A55", "A56", "A57"}, new String[]{"Unknown", "UEA0", "UEA1"}, new String[]{"Not support Hsdpa and Hsupa", "Support Hsdpa", "Support Hsupa", "Support Hsdpa and Hsupa"}, new String[]{"Not Support", "Support"}, new String[]{"Not Support", "VAMOS1", "VAMOS2"}, new String[]{"Not Support", "Support"}, new String[]{"Not Support", "Support"}, new String[]{"other", "R8", "R9"}, new String[]{"Not Support", "Support"}, new String[]{"Unknown", "eea0", "eea1", "eea2"}, new String[]{"Unknown", "eia0 ", "eia1", "eia2"}};
    private static final String TAG = "NetInfoTdscdma";

    NetInfoTdscdmaImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoTdscdma
    public void getServingCell(int simIdx, String[] names, String[] values) {
        String str;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,6,1", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,6,1: " + result);
        String str2 = "\n";
        if (result.contains("OK")) {
            String result2 = result.replaceAll(",-", ",+").replaceAll("--", "-+");
            String[] str1 = result2.split("\n");
            String[] str3 = str1[0].split("-");
            int i = 0;
            while (true) {
                String result3 = result2;
                if (i >= str3.length) {
                    break;
                }
                if (i == 0) {
                    values[2] = names[2] + ": " + str3[i].replace("+", "-");
                }
                if (i == 1) {
                    values[3] = names[3] + ": " + str3[i].replace("+", "-");
                }
                if (i == 2) {
                    int temp = Integer.parseInt(str3[i].replace("+", "-").trim()) - 116;
                    values[5] = names[5] + ": " + String.valueOf(temp) + "dBm";
                }
                if (i == 15) {
                    values[4] = names[4] + ": " + str3[i].replace("+", "-");
                }
                if (i == 16) {
                    values[0] = names[0] + ": " + str3[i].replace("+", "-");
                }
                if (i == 17) {
                    values[1] = names[1] + ": " + str3[i].replace("+", "-");
                }
                i++;
                result2 = result3;
                str1 = str1;
                str2 = str2;
            }
            str = str2;
        } else {
            str = "\n";
            for (int i2 = 0; i2 < 6; i2++) {
                values[i2] = names[i2] + ": NA";
            }
        }
        int num = 6;
        String result4 = IATUtils.sendATCmd("AT+SPENGMD=0,0,5", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,5: " + result4);
        if (result4.contains("OK")) {
            String[] str4 = result4.replaceAll(",-", ",+").replaceAll("--", "-+").split(str)[0].split("-");
            if ("".equals(str4[0])) {
                for (int i3 = 1; i3 < str4.length; i3++) {
                    if (i3 == 1) {
                        if (str4[1].contains(",")) {
                            String[] str5 = str4[1].split(",");
                            for (int j = 0; j < 3; j++) {
                                if (j == 0) {
                                    values[num] = names[num] + ": -" + str5[0];
                                } else {
                                    values[num] = names[num] + ": " + str5[j].replace("+", "-");
                                }
                                num++;
                            }
                        } else {
                            for (int j2 = 0; j2 < 3; j2++) {
                                values[num] = names[num] + ": " + str4[1].replace("+", "-");
                                num++;
                            }
                        }
                    } else if (num < names.length) {
                        values[num] = names[num] + ": " + str4[i3].replace("+", "-");
                        num++;
                    }
                }
                return;
            }
            for (int i4 = 0; i4 < str4.length; i4++) {
                if (i4 == 0) {
                    if (str4[0].contains(",")) {
                        String[] str6 = str4[0].split(",");
                        for (int j3 = 0; j3 < 3; j3++) {
                            values[num] = names[num] + ": " + str6[j3].replace("+", "-");
                            num++;
                        }
                    } else {
                        for (int j4 = 0; j4 < 3; j4++) {
                            values[num] = names[num] + ": " + str4[0].replace("+", "-");
                            num++;
                        }
                    }
                } else if (num < names.length) {
                    values[num] = names[num] + ": " + str4[i4].replace("+", "-");
                    num++;
                }
            }
            return;
        }
        for (int i5 = 6; i5 < names.length; i5++) {
            values[i5] = names[i5] + ": NA";
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoTdscdma
    public void getAdjacentCell(int simIdx, String[][] values) {
        int COL = values[0].length;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,6,2", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,6,2: " + result);
        if (result.contains("OK")) {
            String[] str1 = result.replaceAll(",-", ",+").replaceAll("--", "-+").split("\n");
            String[] str2 = str1[0].split("-");
            for (int i = 0; i < 5; i++) {
                if (i < str2.length) {
                    if (str2[i].contains(",")) {
                        String[] str3 = str2[i].split(",");
                        int j = 0;
                        for (int i2 = 3; j < i2; i2 = 3) {
                            if (j == 2) {
                                int temp = Integer.parseInt(str3[j].replace("+", "-").trim()) - 116;
                                values[i][j] = String.valueOf(temp) + "dBm";
                            } else if (-1 == Integer.parseInt(str3[j].replace("+", "-").trim())) {
                                values[i][j] = "NA";
                            } else {
                                values[i][j] = str3[j].replace("+", "-");
                            }
                            j++;
                        }
                    }
                } else {
                    for (int j2 = 0; j2 < 3; j2++) {
                        values[i][j2] = "NA";
                    }
                }
            }
            return;
        }
        for (String[] strArr : values) {
            for (int j3 = 0; j3 < COL; j3++) {
                strArr[j3] = "NA";
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoTdscdma
    public void getBetweenAdjacentCell2G(int simIdx, String[][] values) {
        String[] str2;
        int COL = values[0].length;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,6,4", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,6,4: " + result);
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
                                int temp = Integer.parseInt(str4[j].replace("+", "-").trim()) - 111;
                                if (1936 == temp) {
                                    values[i][j - 1] = "NA";
                                }
                                values[i][j - 1] = String.valueOf(temp) + "dBm";
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

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoTdscdma
    public void getBetweenAdjacentCell4G(int simIdx, String[][] values) {
        String[] str2;
        int temp;
        int COL = values[0].length;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,0,4", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,4: " + result);
        int num_4g = 0;
        if (result.contains("OK")) {
            String[] str1 = result.replaceAll(",-", ",+").replaceAll("--", "-+").split("\n");
            String[] str3 = str1[0].split("-");
            int i = 0;
            while (true) {
                if (i >= str3.length || num_4g >= 5) {
                    break;
                    break;
                }
                if (!str3[i].contains(",")) {
                    str2 = str3;
                } else {
                    String[] str4 = str3[i].split(",");
                    int j = 0;
                    for (int i2 = 4; j < i2; i2 = 4) {
                        if (j == 3 || j == 2) {
                            if (j == 2) {
                                temp = Integer.parseInt(str4[j].replace("+", "-").trim()) - 141;
                            } else {
                                temp = (Integer.parseInt(str4[j].replace("+", "-").trim()) - 40) / 2;
                            }
                            values[num_4g][j] = String.valueOf(temp) + "dBm";
                        } else {
                            values[num_4g][j] = str4[j].replace("+", "-");
                        }
                        j++;
                        str3 = str3;
                    }
                    str2 = str3;
                    num_4g++;
                }
                i++;
                str3 = str2;
            }
            if (num_4g < 5) {
                for (int i3 = num_4g; i3 < 5; i3++) {
                    for (int j2 = 0; j2 < 4; j2++) {
                        values[i3][j2] = "NA";
                    }
                }
                return;
            }
            return;
        }
        for (String[] strArr : values) {
            for (int j3 = 0; j3 < COL; j3++) {
                strArr[j3] = "NA";
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoTdscdma
    public List<String> getOutfieldNetworkInfo(int simIdx, String[] names) {
        int num = 0;
        String result = IATUtils.sendATCmd(engconstents.ENG_AT_SPENGMD_007, simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,7: " + result);
        if (result.contains("OK")) {
            String[] str1 = result.replaceAll("--", "-+").split("\n");
            String[] str2 = str1[0].split("-");
            for (int i = 0; i < str2.length; i++) {
                if ((i == 4 || i == 5 || i == 10) && num < names.length) {
                    names[num] = names[num] + ": " + Description_name[i][Integer.valueOf(str2[i].substring(0, 1)).intValue()];
                    num++;
                }
            }
        } else {
            for (int i2 = 0; i2 < names.length; i2++) {
                names[i2] = names[i2] + ": NA";
            }
        }
        List<String> list = new ArrayList<>();
        for (int i3 = 0; i3 < names.length; i3++) {
            if (!names[i3].contains("R9")) {
                list.add(names[i3]);
            }
        }
        return list;
    }
}
