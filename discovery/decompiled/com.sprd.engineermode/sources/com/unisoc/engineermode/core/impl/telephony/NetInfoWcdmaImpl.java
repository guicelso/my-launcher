package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
class NetInfoWcdmaImpl implements ITelephonyApi.INetInfoWcdma {
    private static final String[][] Description_name = {new String[]{"Not Support", "Support"}, new String[]{"Not Support", "Support"}, new String[]{"Unknown", "GEA1", "GEA2", "GEA3"}, new String[]{"A51", "A52", "A53", "A54", "A55", "A56", "A57"}, new String[]{"Unknown", "UEA0", "UEA1"}, new String[]{"Not support Hsdpa and Hsupa", "Support Hsdpa", "Support Hsupa", "Support Hsdpa and Hsupa"}, new String[]{"Not Support", "Support"}, new String[]{"Not Support", "VAMOS1", "VAMOS2"}, new String[]{"Not Support", "Support"}, new String[]{"Not Support", "Support"}, new String[]{"other", "R8", "R9"}, new String[]{"Not Support", "Support"}, new String[]{"Unknown", "eea0", "eea1", "eea2"}, new String[]{"Unknown", "eia0 ", "eia1", "eia2"}};
    private static final String TAG = "NetInfoWcdma";

    NetInfoWcdmaImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdma
    public void getServingCell(int simIdx, String[] names, String[] values) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        int i;
        String str7;
        String str8;
        String str9;
        String str10;
        boolean z;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,6,1", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,6,1: " + result);
        String str11 = "OK";
        String str12 = "-+";
        String str13 = "--";
        String str14 = ",+";
        String str15 = ",-";
        if (result.contains("OK")) {
            String[] str1 = result.replaceAll(",-", ",+").replaceAll("--", "-+").split("\n");
            String[] str16 = str1[0].split("-");
            int i2 = 0;
            while (true) {
                String[] str17 = str1;
                if (i2 >= str16.length) {
                    break;
                }
                if (i2 == 0) {
                    values[2] = names[2] + ": " + str16[i2].replace("+", "-");
                }
                if (i2 == 1) {
                    values[3] = names[3] + ": " + str16[i2].replace("+", "-");
                }
                if (i2 == 2) {
                    int temp = Integer.parseInt(str16[i2].replace("+", "-").trim());
                    values[5] = names[5] + ": " + String.valueOf(temp) + "dBm";
                }
                if (i2 == 15) {
                    values[4] = names[4] + ": " + ((Float.parseFloat(str16[i2].replace("+", "-")) * 10.0f) / 20.0f);
                }
                if (i2 == 16) {
                    values[0] = names[0] + ": " + str16[i2].replace("+", "-");
                }
                if (i2 == 17) {
                    values[1] = names[1] + ": " + str16[i2].replace("+", "-");
                }
                i2++;
                str1 = str17;
                str13 = str13;
                str12 = str12;
            }
            str = str12;
            str2 = str13;
        } else {
            str = "-+";
            str2 = "--";
            for (int i3 = 0; i3 < 6; i3++) {
                values[i3] = names[i3] + ": NA";
            }
        }
        String result2 = IATUtils.sendATCmd("AT+SPENGMD=0,0,5", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,5: " + result2);
        if (!result2.contains("OK")) {
            str3 = "OK";
            str4 = ",+";
            str5 = ",-";
            str6 = str2;
            for (int i4 = 6; i4 < names.length; i4++) {
                values[i4] = names[i4] + ": NA";
            }
        } else {
            String str18 = str2;
            String[] str19 = result2.replaceAll(",-", ",+").replaceAll(str18, str).split("\n");
            String[] str20 = str19[0].split("-");
            if (!"".equals(str20[0]) || str20.length <= 1) {
                str3 = "OK";
                str4 = ",+";
                str5 = ",-";
                str6 = str18;
                i = 6;
                for (int i5 = 0; i5 < str20.length; i5++) {
                    if (i5 == 0) {
                        if (str20[0].contains(",")) {
                            String[] str21 = str20[0].split(",");
                            for (int j = 0; j < 3; j++) {
                                values[i] = names[i] + ": " + str21[j].replace("+", "-");
                                i++;
                            }
                        } else {
                            for (int j2 = 0; j2 < 3; j2++) {
                                values[i] = names[i] + ": " + str20[0].replace("+", "-");
                                i++;
                            }
                        }
                    } else if (i < names.length) {
                        values[i] = names[i] + ": " + str20[i5].replace("+", "-");
                        i++;
                    }
                }
            } else {
                int i6 = 1;
                int num = 6;
                while (i6 < str20.length) {
                    if (i6 == 1) {
                        str10 = str18;
                        if (str20[1].contains(",")) {
                            String[] str22 = str20[1].split(",");
                            int j3 = 0;
                            while (true) {
                                str8 = str14;
                                if (j3 >= 3 || j3 >= str22.length) {
                                    break;
                                }
                                if (j3 == 0) {
                                    values[num] = names[num] + ": -" + str22[0];
                                } else {
                                    values[num] = names[num] + ": " + str22[j3].replace("+", "-");
                                }
                                num++;
                                j3++;
                                str14 = str8;
                                str15 = str15;
                                str11 = str11;
                            }
                            str7 = str11;
                            str9 = str15;
                            z = true;
                        } else {
                            str7 = str11;
                            str8 = str14;
                            str9 = str15;
                            for (int j4 = 0; j4 < 3; j4++) {
                                values[num] = names[num] + ": " + str20[1].replace("+", "-");
                                num++;
                            }
                            z = true;
                        }
                    } else {
                        str7 = str11;
                        str8 = str14;
                        str9 = str15;
                        str10 = str18;
                        z = true;
                        int num2 = num;
                        if (num2 >= names.length) {
                            num = num2;
                        } else {
                            values[num2] = names[num2] + ": " + str20[i6].replace("+", "-");
                            num = num2 + 1;
                        }
                    }
                    i6++;
                    str18 = str10;
                    str14 = str8;
                    str15 = str9;
                    str11 = str7;
                }
                str3 = str11;
                str4 = str14;
                str5 = str15;
                str6 = str18;
                i = num;
            }
        }
        String result3 = IATUtils.sendATCmd(engconstents.ENG_AT_SPENGMD_008, simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,8: " + result3);
        String str23 = str3;
        if (result3.contains(str23)) {
            String[] str24 = result3.split("-");
            if (str24.length < 18) {
                values[11] = names[11] + ": NO";
            } else {
                String[] dcHsdpa = str24[13].trim().split(",");
                if ("1".equals(dcHsdpa[0])) {
                    values[11] = names[11] + ": YES";
                } else {
                    values[11] = names[11] + ": NO";
                }
            }
        } else {
            values[11] = names[11] + ": NO";
        }
        String result4 = IATUtils.sendATCmd("AT+SPENGMD=0,5,2", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,5,2: " + result4);
        if (result4.contains(str23)) {
            String[] str25 = result4.replaceAll(str5, str4).replaceAll(str6, str).split("\n");
            String[] str26 = str25[0].split("-");
            int i7 = 0;
            while (i7 < values.length - 12 && i7 < str26.length) {
                if (i7 == 3 || i7 == 7) {
                    values[i7 + 12] = names[i7 + 12] + ": " + str26[i7].replace("+", "-") + "dBm";
                } else {
                    values[i7 + 12] = names[i7 + 12] + ": " + str26[i7].replace("+", "-");
                }
                i7++;
            }
            while (i7 < names.length - 12) {
                values[i7 + 12] = names[i7 + 12] + ": NA";
                i7++;
            }
            return;
        }
        for (int i8 = 0; i8 < names.length - 12; i8++) {
            values[i8 + 12] = names[i8 + 12] + ": NA";
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdma
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
                                int temp = Integer.parseInt(str3[j].replace("+", "-").trim());
                                values[i][j] = String.valueOf(temp) + "dBm";
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

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdma
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

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdma
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
                                temp = Integer.parseInt(str4[j].replace("+", "-").trim()) - 120;
                            } else {
                                temp = Integer.parseInt(str4[j].replace("+", "-").trim()) - 49;
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

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdma
    public List<String> getOutfieldNetworkInfo(int simIdx, String[] names) {
        int num = 0;
        String result = IATUtils.sendATCmd(engconstents.ENG_AT_SPENGMD_007, simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,7: " + result);
        if (result.contains("OK")) {
            String[] str1 = result.replaceAll("--", "-+").split("\n");
            String[] str2 = str1[0].split("-");
            for (int i = 0; i < str2.length && num < names.length; i++) {
                if (i == 4 || i == 5 || i == 6 || i == 8 || i == 10 || i == 11) {
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
            for (int i3 = 0; i3 < nwCapValues.size() && num2 < names.length; i3++) {
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
