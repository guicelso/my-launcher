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
public class NetInfoLteImpl implements ITelephonyApi.INetInfoLte {
    private static final String TAG = "NetInfoLte";
    private String[][] Description_name = {new String[]{"Not Support", "Support"}, new String[]{"Not Support", "Support"}, new String[]{"Unknown", "GEA1", "GEA2", "GEA3"}, new String[]{"A51", "A52", "A53", "A54", "A55", "A56", "A57"}, new String[]{"Unknown", "UEA0", "UEA1", "UEA0,UEA1"}, new String[]{"Not support Hsdpa and Hsupa", "Support Hsdpa", "Support Hsupa", "Support Hsdpa and Hsupa"}, new String[]{"Not Support", "Support"}, new String[]{"Not Support", "VAMOS1", "VAMOS2"}, new String[]{"Not Support", "Support"}, new String[]{"Not Support", "Support"}, new String[]{"other", "R8", "R9"}, new String[]{"Not Support", "Support"}, new String[]{"Unknown", "eea0", "eea1", "eea2"}, new String[]{"Unknown", "eia0 ", "eia1", "eia2"}, new String[]{"Not Support", "Support"}};

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoLte
    public void getServingCell(int simIdx, String[] names, String[] values) {
        String str;
        String str2;
        String str3;
        String str4;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,6,0", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,6,0: " + result);
        String str5 = "-+";
        String str6 = "--";
        String str7 = ",+";
        String str8 = ",-";
        if (result.contains("OK")) {
            String[] str1 = result.replace(",-", ",+").replace("--", "-+").split("\n");
            String[] str9 = str1[0].split("-");
            int i = 0;
            while (true) {
                String[] str10 = str1;
                str = str5;
                if (i + 1 >= str9.length) {
                    break;
                }
                if (i == 3 || i == 4) {
                    if (str9[i].contains(",")) {
                        values[i] = names[i] + ": ";
                        String[] str11 = str9[i].split(",");
                        int[] tempArray = new int[str11.length];
                        for (int j = 0; j < str11.length; j++) {
                            tempArray[j] = Integer.parseInt(str11[j].replace("+", "-").trim()) / 100;
                            values[i] = values[i] + tempArray[j] + "dBm";
                            if (j == 0) {
                                values[i] = values[i] + ",";
                            }
                        }
                    } else {
                        int temp = Integer.parseInt(str9[i].replace("+", "-").trim()) / 100;
                        values[i] = names[i] + ": " + temp + "dBm";
                    }
                } else if (i == 7 - 1 || i == 7 - 2) {
                    StringBuilder sb = new StringBuilder();
                    str6 = str6;
                    sb.append(names[i]);
                    sb.append(": ");
                    sb.append(str9[i + 1].replace("+", "-"));
                    values[i] = sb.toString();
                    if (i != 7 - 1) {
                        str7 = str7;
                        str8 = str8;
                    } else {
                        int indexOfColon = values[i].indexOf(58);
                        str7 = str7;
                        str8 = str8;
                        String pBandWidth = values[i].substring(indexOfColon + 2, indexOfColon + 3);
                        Log.d(TAG, "pBandWidth = " + pBandWidth);
                        values[i] = values[i].replaceFirst(pBandWidth, switchToBandWidth(pBandWidth));
                        int indexOfColon2 = values[i].indexOf(44);
                        Log.d(TAG, "values[i] = " + values[i]);
                        if (indexOfColon2 >= 0) {
                            String[] str12 = values[i].split(",");
                            String sBandWidth = str12[str12.length - 1];
                            Log.d(TAG, "sBandWidth = " + sBandWidth);
                            values[i] = values[i].substring(0, indexOfColon2 + 1) + switchToBandWidth(sBandWidth);
                        }
                        Log.d(TAG, "values[i] = " + values[i]);
                    }
                } else if (i >= 14) {
                    str6 = str6;
                    str7 = str7;
                    str8 = str8;
                } else {
                    values[i] = names[i] + ": " + str9[i].replace("+", "-");
                    str6 = str6;
                    str7 = str7;
                    str8 = str8;
                }
                i++;
                str1 = str10;
                str5 = str;
                str6 = str6;
                str7 = str7;
                str8 = str8;
            }
            str2 = str6;
            str3 = str7;
            str4 = str8;
            values[14] = names[14] + ": " + str9[10].replace("+", "-");
            values[15] = names[15] + ": " + str9[11].replace("+", "-");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("values[14] = ");
            sb2.append(values[14]);
            Log.d(TAG, sb2.toString());
            Log.d(TAG, "values[15] = " + values[15]);
        } else {
            str = "-+";
            str2 = "--";
            str3 = ",+";
            str4 = ",-";
            for (int i2 = 0; i2 < 7; i2++) {
                values[i2] = names[i2] + ": NA";
            }
        }
        int num = 7;
        String result2 = IATUtils.sendATCmd("AT+SPENGMD=0,0,6", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,6: " + result2);
        if (result2.contains("OK")) {
            String[] str13 = result2.replace(str4, str3).replace(str2, str).split("\n")[0].split("-");
            for (String s : str13) {
                if (num != 14 && num != 15 && num < names.length) {
                    Log.d(TAG, "i=" + num + " " + s.replace("+", "-"));
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(names[num]);
                    sb3.append(": ");
                    sb3.append(s.replace("+", "-"));
                    values[num] = sb3.toString();
                    num++;
                }
            }
            if (str13.length >= 7) {
                values[5] = names[5] + ": " + str13[7];
                Log.d(TAG, values[5] + " = " + values[5]);
            }
            for (int i3 = num; i3 < names.length; i3++) {
                if (i3 != 14 && i3 != 15) {
                    values[i3] = names[i3] + ":NA";
                }
            }
            return;
        }
        for (int i4 = 7; i4 < names.length; i4++) {
            values[i4] = names[i4] + ": NA";
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoLte
    public void getAdjacentCell(int simIdx, String[][] values) {
        int col = values[0].length;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,6,6", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,6,6: " + result);
        if (result.contains("OK")) {
            String[] str1 = result.replace(",-", ",+").replace("--", "-+").split("\n");
            String[] str2 = str1[0].split("-");
            for (int i = 0; i < 5; i++) {
                if (i < str2.length) {
                    if (str2[i].contains(",")) {
                        String[] str3 = str2[i].split(",");
                        int j = 0;
                        for (int i2 = 4; j < i2; i2 = 4) {
                            if (j == 2 || j == 3) {
                                int temp = Integer.parseInt(str3[j].replace("+", "-").trim()) / 100;
                                values[i][j] = temp + "dBm";
                            } else {
                                values[i][j] = str3[j].replace("+", "-");
                            }
                            j++;
                        }
                    }
                } else {
                    for (int j2 = 0; j2 < 4; j2++) {
                        values[i][j2] = "NA";
                    }
                }
            }
            return;
        }
        for (String[] strArr : values) {
            for (int j3 = 0; j3 < col; j3++) {
                strArr[j3] = "NA";
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoLte
    public void getBetweenAdjacentCell2G(int simIdx, String[][] values) {
        String[] str2;
        int col = values[0].length;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,6,7", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,6,7: " + result);
        int num = 0;
        if (result.contains("OK")) {
            String[] str1 = result.replace(",-", ",+").replace("--", "-+").split("\n");
            String[] str3 = str1[0].split("-");
            int i = 0;
            while (true) {
                if (i >= str3.length || num >= 5) {
                    break;
                    break;
                }
                if (!str3[i].contains(",")) {
                    str2 = str3;
                } else {
                    String[] str4 = str3[i].split(",");
                    int j = 0;
                    for (int i2 = 3; j < i2; i2 = 3) {
                        if (j != 2) {
                            String[] str5 = values[num];
                            str5[j] = str4[j].replace("+", "-");
                        } else {
                            int temp = Integer.parseInt(str4[j].replace("+", "-").trim()) / 100;
                            values[num][j] = temp + "dBm";
                        }
                        j++;
                        str3 = str3;
                    }
                    str2 = str3;
                    num++;
                }
                i++;
                str3 = str2;
            }
            if (num < 5) {
                for (int i3 = num; i3 < 5; i3++) {
                    for (int j2 = 0; j2 < 3; j2++) {
                        values[i3][j2] = "NA";
                    }
                }
                return;
            }
            return;
        }
        for (String[] strArr : values) {
            for (int j3 = 0; j3 < col; j3++) {
                strArr[j3] = "NA";
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoLte
    public void getBetweenAdjacentCell3G(int simIdx, String[][] values) {
        String[] str2;
        int col = values[0].length;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,6,8", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,6,8: " + result);
        int num4G = 0;
        if (result.contains("OK")) {
            String[] str1 = result.replace(",-", ",+").replace("--", "-+").split("\n");
            String[] str3 = str1[0].split("-");
            int i = 0;
            while (true) {
                if (i >= str3.length || num4G >= 5) {
                    break;
                    break;
                }
                if (!str3[i].contains(",")) {
                    str2 = str3;
                } else {
                    String[] str4 = str3[i].split(",");
                    int j = 0;
                    for (int i2 = 3; j < i2; i2 = 3) {
                        if (j != 2) {
                            String[] str5 = values[num4G];
                            str5[j] = str4[j].replace("+", "-");
                        } else {
                            int temp = Integer.parseInt(str4[j].replace("+", "-").trim()) / 100;
                            values[num4G][j] = String.valueOf(temp) + "dBm";
                        }
                        j++;
                        str3 = str3;
                    }
                    str2 = str3;
                    num4G++;
                }
                i++;
                str3 = str2;
            }
            if (num4G < 5) {
                for (int i3 = num4G; i3 < 5; i3++) {
                    for (int j2 = 0; j2 < 3; j2++) {
                        values[i3][j2] = "NA";
                    }
                }
                return;
            }
            return;
        }
        for (String[] strArr : values) {
            for (int j3 = 0; j3 < col; j3++) {
                strArr[j3] = "NA";
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoLte
    public List<String> getOutfieldNetworkInfo(int simIdx, String[] names) {
        int i;
        int index;
        int index2;
        int n;
        char c;
        String[] str2;
        int num = 0;
        String result = IATUtils.sendATCmd(engconstents.ENG_AT_SPENGMD_007, simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,7: " + result);
        if (result.contains("OK")) {
            String result2 = result.replace("--", "-+");
            String[] str1 = result2.split("\n");
            if (str1.length > 3) {
                str2 = str1[2].split("-");
            } else {
                str2 = str1[0].split("-");
            }
            names[0] = names[0] + ": " + this.Description_name[4][Integer.valueOf(str2[12].substring(0, 1)).intValue()];
            num = 0 + 1;
            if (num < names.length) {
                names[num] = names[num] + ": " + this.Description_name[10][Integer.valueOf(str2[10].substring(0, 1)).intValue()];
                num++;
            }
            result = result2;
        } else {
            for (int i2 = 0; i2 < names.length; i2++) {
                names[i2] = names[i2] + ": NA";
            }
        }
        String bsrvcc = IATUtils.sendATCmd("AT+SPENGMDVOLTE=24,0", simIdx);
        if (bsrvcc != null && bsrvcc.contains("OK")) {
            String[] bsrvccString = bsrvcc.split("\n");
            String[] bsrvccString1 = bsrvccString[0].split(",");
            if (bsrvccString1.length >= 3) {
                c = 2;
                if ("1".equals(bsrvccString1[2].trim())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(names[2]);
                    sb.append(": ");
                    String[][] strArr = this.Description_name;
                    sb.append(strArr[strArr[0].length - 1][1]);
                    names[2] = sb.toString();
                }
            } else {
                c = 2;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(names[c]);
            sb2.append(": ");
            String[][] strArr2 = this.Description_name;
            sb2.append(strArr2[strArr2[0].length - 1][0]);
            names[2] = sb2.toString();
        } else {
            names[2] = names[2] + ": NA";
        }
        String result3 = IATUtils.sendATCmd("AT+SPENGMD=0,6,0", simIdx);
        Log.d(TAG, "ATSPENGMD=0,6,0: " + result3);
        if (!result3.contains("OK")) {
            i = 0;
            for (int i3 = 3; i3 < names.length; i3++) {
                names[i3] = names[i3] + ": NA";
            }
        } else {
            String result4 = result3.replace("--", "-").replace(",-", ",+");
            String[] nwCap = result4.split("\n")[0].split("-");
            if (nwCap.length >= 12) {
                String[] uldaca = nwCap[12].split(",");
                int length = uldaca.length;
                n = 3;
                int n2 = 0;
                while (n2 < length) {
                    String result5 = result4;
                    String isSupport = uldaca[n2].equals("1") ? "support" : "not support";
                    names[n] = names[n] + ": " + isSupport;
                    n++;
                    n2++;
                    result4 = result5;
                    bsrvcc = bsrvcc;
                }
            } else {
                n = 3;
            }
            for (int k = 21; k < nwCap.length && k <= 22; k++) {
                String isSupport2 = nwCap[k].substring(0, 1).equals("1") ? "support" : "not support";
                names[n] = names[n] + ": " + isSupport2;
                n++;
            }
            i = 0;
            Log.d(TAG, "nwCap.length = " + nwCap.length);
            if (nwCap.length < 23) {
                for (int i4 = n; i4 < names.length; i4++) {
                    names[i4] = names[i4] + ": NA";
                }
            }
        }
        String result6 = IATUtils.sendATCmd(engconstents.ENG_AT_APNQUERY, simIdx);
        Log.d(TAG, "ENG_AT_APNQUERY: " + result6);
        String[] str3 = result6.split("\n");
        String ipv6Support = "not support";
        int length2 = str3.length;
        while (i < length2) {
            String str = str3[i];
            if (str.contains("+CGDCONT:11") || (!str.contains("V6") && !str.contains("v6"))) {
                i++;
            } else {
                ipv6Support = "support";
                break;
            }
        }
        names[7] = names[7] + ": " + ipv6Support;
        int n3 = 7 + 1;
        String result7 = IATUtils.sendATCmd(engconstents.ENG_AT_GET_ASRVCC, simIdx);
        Log.d(TAG, "ENG_AT_GET_ASRVCC = " + result7);
        String asrvccSupport = "not support";
        if (result7.contains("OK") && (index2 = result7.indexOf("25,0,")) >= 0 && result7.substring("25,0,".length() + index2, "25,0,".length() + index2 + 1).equals("1")) {
            asrvccSupport = "support";
        }
        if (n3 < names.length) {
            names[n3] = names[n3] + ": " + asrvccSupport;
        }
        int n4 = n3 + 1;
        String result8 = IATUtils.sendATCmd("AT+SPENGMDVOLTE=29,0", simIdx);
        Log.d(TAG, "AT+SPENGMDVOLTE=29,0 : " + result8);
        String esrvccSupport = "not support";
        if (result8.contains("OK") && (index = result8.indexOf("29,0,")) >= 0 && result8.substring("29,0,".length() + index, "29,0,".length() + index + 1).equals("1")) {
            esrvccSupport = "support";
        }
        if (n4 < names.length) {
            names[n4] = names[n4] + ": " + esrvccSupport;
        }
        return (List) Arrays.stream(names).filter(new Predicate() { // from class: com.unisoc.engineermode.core.impl.telephony.-$$Lambda$NetInfoLteImpl$VBCKVl7IEgag41dDVfUmAZ6gGOw
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return NetInfoLteImpl.lambda$getOutfieldNetworkInfo$0((String) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ boolean lambda$getOutfieldNetworkInfo$0(String name) {
        return !name.contains("R9");
    }

    private String switchToBandWidth(String colon) {
        int c = Integer.parseInt(colon);
        Log.d(TAG, "c = " + c);
        String bandWidth = "";
        if (c == 0) {
            bandWidth = "1.4M";
        } else if (c == 1) {
            bandWidth = "3M";
        } else if (c == 2) {
            bandWidth = "5M";
        } else if (c == 3) {
            bandWidth = "10M";
        } else if (c == 4) {
            bandWidth = "15M";
        } else if (c == 5) {
            bandWidth = "20M";
        } else if (c == 255) {
            bandWidth = "NA";
        }
        Log.d(TAG, "bandWidth = " + bandWidth);
        return bandWidth;
    }
}
