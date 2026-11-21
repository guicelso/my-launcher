package com.unisoc.engineermode.core.impl.telephony;

import android.support.v4.media.MediaPlayer2;
import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes4.dex */
public class NetInfoGsmImpl implements ITelephonyApi.INetInfoGsm {
    private static final String[][] Description_name = {new String[]{"Not Support", "Support"}, new String[]{"Not Support", "Support"}, new String[]{"Unknown", "GEA1", "GEA2", "GEA3"}, new String[]{"A51", "A52", "A53", "A54", "A55", "A56", "A57"}, new String[]{"Unknown", "UEA0", "UEA1"}, new String[]{"Not support Hsdpa and Hsupa", "Support Hsdpa", "Support Hsupa", "Support Hsdpa and Hsupa"}, new String[]{"Not Support", "Support"}, new String[]{"Not Support", "VAMOS1", "VAMOS2"}, new String[]{"Not Support", "Support"}, new String[]{"Not Support", "Support"}, new String[]{"other", "R8", "R9"}, new String[]{"Not Support", "Support"}, new String[]{"Unknown", "eea0", "eea1", "eea2"}, new String[]{"Unknown", "eia0 ", "eia1", "eia2"}};
    private static final String TAG = "NetInfoGsm";

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoGsm
    public void getServingCell(int simIdx, String[] names, String[] values) {
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,0,0", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,0: " + result);
        if (result.contains("OK")) {
            String[] str1 = result.replace("--", "-+").split("\n");
            String[] str2 = str1[0].split("-");
            for (int i = 0; i < str2.length; i++) {
                if (i == 0) {
                    values[1] = names[1] + ": " + str2[i].replace("+", "-");
                }
                if (i == 1) {
                    values[2] = names[2] + ": " + str2[i].replace("+", "-");
                }
                if (i == 2) {
                    values[0] = names[0] + ": " + str2[i].replace("+", "-");
                }
                if (i == 3) {
                    values[3] = names[3] + ": " + str2[i].replace("+", "-");
                }
                if (i == 4) {
                    int temp = Integer.parseInt(str2[i].replace("+", "-").trim()) + MediaPlayer2.MEDIA_ERROR_TIMED_OUT;
                    values[4] = names[4] + ": " + temp + "dBm";
                }
            }
            return;
        }
        for (int i2 = 0; i2 < names.length; i2++) {
            values[i2] = names[i2] + ": NA";
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoGsm
    public void getAdjacentCell(int simIdx, String[][] values) {
        String result;
        int i;
        int i2;
        int i3;
        int col = values[0].length;
        String result2 = IATUtils.sendATCmd("AT+SPENGMD=0,0,1", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,1: " + result2);
        if (result2.contains("OK")) {
            String result3 = result2.replace(",-", ",+").replace("--", "-+");
            String[] str1 = result3.split("\n");
            String[] str2 = str1[0].split("-");
            int i4 = 0;
            while (i4 < str2.length) {
                int i5 = 5;
                int i6 = 1;
                if (str2[i4].contains(",")) {
                    String[] str3 = str2[i4].split(",");
                    int j = 0;
                    while (j < i5) {
                        if (i4 == 0) {
                            if (j >= str3.length) {
                                values[j][i6] = "NA";
                            } else {
                                values[j][i6] = str3[j].replace("+", "-");
                            }
                        }
                        if (i4 != i6) {
                            i3 = 2;
                        } else if (j >= str3.length) {
                            i3 = 2;
                            values[j][2] = "NA";
                        } else {
                            i3 = 2;
                            values[j][2] = str3[j].replace("+", "-");
                        }
                        if (i4 == i3) {
                            if (j >= str3.length) {
                                values[j][0] = "NA";
                            } else {
                                values[j][0] = str3[j].replace("+", "-");
                            }
                        }
                        if (i4 == 3) {
                            if (j >= str3.length) {
                                values[j][3] = "NA";
                            } else {
                                values[j][3] = str3[j].replace("+", "-");
                            }
                        }
                        if (i4 == 4) {
                            if (j >= str3.length) {
                                values[j][4] = "NA";
                            } else {
                                int temp = Integer.parseInt(str3[j].replace("+", "-").trim()) + MediaPlayer2.MEDIA_ERROR_TIMED_OUT;
                                values[j][4] = temp + "dBm";
                            }
                        }
                        j++;
                        result3 = result3;
                        i5 = 5;
                        i6 = 1;
                    }
                    result = result3;
                } else {
                    result = result3;
                    for (int j2 = 0; j2 < 5; j2++) {
                        if (i4 != 0) {
                            i = 1;
                        } else if (j2 > 0) {
                            i = 1;
                            values[j2][1] = "NA";
                        } else {
                            i = 1;
                            values[j2][1] = str2[i4].replace("+", "-");
                        }
                        if (i4 != i) {
                            i2 = 2;
                        } else if (j2 > 0) {
                            i2 = 2;
                            values[j2][2] = "NA";
                        } else {
                            i2 = 2;
                            values[j2][2] = str2[i4].replace("+", "-");
                        }
                        if (i4 == i2) {
                            if (j2 > 0) {
                                values[j2][0] = "NA";
                            } else {
                                values[j2][0] = str2[i4].replace("+", "-");
                            }
                        }
                        if (i4 == 3) {
                            if (j2 > 0) {
                                values[j2][3] = "NA";
                            } else {
                                values[j2][3] = str2[i4].replace("+", "-");
                            }
                        }
                        if (i4 == 4) {
                            if (j2 > 0) {
                                values[j2][4] = "NA";
                            } else {
                                int temp2 = Integer.parseInt(str2[i4].replace("+", "-").trim()) + MediaPlayer2.MEDIA_ERROR_TIMED_OUT;
                                values[j2][4] = temp2 + "dBm";
                            }
                        }
                    }
                }
                i4++;
                result3 = result;
            }
            return;
        }
        for (String[] strArr : values) {
            for (int j3 = 0; j3 < col; j3++) {
                strArr[j3] = "NA";
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoGsm
    public void getBetweenAdjacentCell3G(int simIdx, String[][] values) {
        int col = values[0].length;
        String result = IATUtils.sendATCmd("AT+SPENGMD=0,0,2", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,2: " + result);
        if (result.contains("OK")) {
            String[] str1 = result.replace(",-", ",+").replace("--", "-+").split("\n");
            String[] str2 = str1[0].split("-");
            for (int i = 0; i < str2.length; i++) {
                int i2 = 3;
                if (str2[i].contains(",")) {
                    String[] str3 = str2[i].split(",");
                    int j = 0;
                    for (int i3 = 5; j < i3; i3 = 5) {
                        if (i < i2) {
                            if (j >= str3.length) {
                                values[j][i] = "NA";
                            } else if (i == 2) {
                                int temp = Integer.parseInt(str3[j].replace("+", "-").trim()) - 116;
                                values[j][i] = temp + "dBm";
                            } else {
                                values[j][i] = str3[j].replace("+", "-");
                            }
                        }
                        j++;
                        i2 = 3;
                    }
                } else {
                    for (int j2 = 0; j2 < 5; j2++) {
                        if (i < 3) {
                            if (j2 > 0) {
                                values[j2][i] = "NA";
                            } else if (i == 2) {
                                int temp2 = Integer.parseInt(str2[i].replace("+", "-").trim()) - 116;
                                values[j2][i] = temp2 + "dBm";
                            } else {
                                values[j2][i] = str2[i].replace("+", "-");
                            }
                        }
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

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoGsm
    public void getBetweenAdjacentCell4G(int simIdx, String[][] values) {
        String result;
        int col = values[0].length;
        String result2 = IATUtils.sendATCmd("AT+SPENGMD=0,0,3", simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,3: " + result2);
        if (result2.contains("OK")) {
            String result3 = result2.replace(",-", ",+").replace("--", "-+");
            String[] str1 = result3.split("\n");
            String[] str2 = str1[0].split("-");
            int i = 0;
            while (i < 4) {
                int i2 = 2;
                if (str2[i].contains(",")) {
                    String[] str3 = str2[i].split(",");
                    int j = 0;
                    for (int i3 = 5; j < i3; i3 = 5) {
                        if (j >= str3.length) {
                            values[j][i] = "NA";
                        } else if (i == i2 || i == 3) {
                            int temp = Integer.parseInt(str3[j].replace("+", "-").trim()) / 100;
                            values[j][i] = temp + "dBm";
                        } else {
                            values[j][i] = str3[j].replace("+", "-");
                        }
                        j++;
                        result3 = result3;
                        i2 = 2;
                    }
                    result = result3;
                } else {
                    result = result3;
                    for (int j2 = 0; j2 < 5; j2++) {
                        if (j2 > 0) {
                            values[j2][i] = "NA";
                        } else if (i == 2 || i == 3) {
                            int temp2 = Integer.parseInt(str2[i].replace("+", "-").trim()) / 100;
                            values[j2][i] = temp2 + "dBm";
                        } else {
                            values[j2][i] = str2[i].replace("+", "-");
                        }
                    }
                }
                i++;
                result3 = result;
            }
            return;
        }
        for (String[] strArr : values) {
            for (int j3 = 0; j3 < col; j3++) {
                strArr[j3] = "NA";
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoGsm
    public List<String> getOutfieldNetworkInfo(int simIdx, String[] names) {
        boolean z;
        int num = 0;
        String result = IATUtils.sendATCmd(engconstents.ENG_AT_SPENGMD_007, simIdx);
        Log.d(TAG, "AT+SPENGMD=0,0,7: " + result);
        if (result.contains("OK")) {
            String[] str1 = result.replaceAll("--", "-+").split("\n");
            boolean z2 = false;
            String[] str2 = str1[0].split("-");
            int i = 0;
            while (i < str2.length) {
                if (i == 0) {
                    String[] str3 = str2[i].split(",");
                    if (str3.length < 2) {
                        names[num] = names[num] + ": NA";
                    } else {
                        names[num] = names[num] + ": " + Description_name[i][Integer.parseInt(str3[1].trim())];
                    }
                    num++;
                    z = z2;
                } else {
                    if (i == 3) {
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
                        num++;
                    } else if (i == 1 || i == 2 || i == 6 || i == 7 || i == 9) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(names[num]);
                        sb2.append(": ");
                        z = false;
                        sb2.append(Description_name[i][Integer.parseInt(str2[i].substring(0, 1))]);
                        names[num] = sb2.toString();
                        num++;
                    }
                    z = false;
                }
                i++;
                z2 = z;
            }
        } else {
            for (int i3 = 0; i3 < names.length; i3++) {
                names[i3] = names[i3] + ": NA";
            }
        }
        return (List) Arrays.stream(names).filter(new Predicate() { // from class: com.unisoc.engineermode.core.impl.telephony.-$$Lambda$NetInfoGsmImpl$ikI-0parRCGGaM5QxeQLLUS0kLc
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return NetInfoGsmImpl.lambda$getOutfieldNetworkInfo$0((String) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ boolean lambda$getOutfieldNetworkInfo$0(String n) {
        return !n.contains("R9");
    }
}
