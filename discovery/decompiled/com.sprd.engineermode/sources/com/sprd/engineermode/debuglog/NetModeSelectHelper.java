package com.sprd.engineermode.debuglog;

import android.content.Context;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.telephony.TelephonyManagerSprd;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: compiled from: NetModeSelectActivity.java */
/* JADX INFO: loaded from: classes4.dex */
class NetModeSelectHelper {
    private static final String GSM_ONLY = "10";
    public static final int NETMODE_AUTO = 0;
    public static final int NETMODE_GSMONLY = 1;
    public static final int NETMODE_INVALID = -1;
    public static final int NETMODE_TDONLY = 4;
    public static final int NETMODE_TDPREFER = 5;
    public static final int NETMODE_WCDMAONLY = 2;
    public static final int NETMODE_WCDMAPREFER = 3;
    private static final String TD_ONLY = "12";
    private static final String TD_PREFER = "13";
    private static final String WCDMA_ONLY = "11";
    private static final String WCDMA_PREFER = "14";
    private static String netModeSim0;
    private static String netModeSim1;
    public static ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private Context mContext;
    private int mModemType;
    private int mSimIndex;

    public static NetModeSelectHelper getNetModeSelectHelper(int simIndex, Context context) {
        NetModeSelectHelper helper = new NetModeSelectHelper(simIndex, context);
        if (helper.mSimIndex == -1 || helper.mModemType == -1) {
            return null;
        }
        return helper;
    }

    public int getModemType() {
        return this.mModemType;
    }

    public int getNetMode() {
        String result;
        String atRsp = teleApi.netWorkMode().getNetWorkMode(this.mSimIndex);
        if (!atRsp.contains("OK")) {
            return -1;
        }
        String[] str = atRsp.split("\\:");
        String[] str1 = str[1].split("\\,");
        str1[0].trim();
        if (this.mSimIndex == 0) {
            String result2 = str1[0].trim();
            netModeSim0 = result2;
            netModeSim1 = str1[1].trim();
            result = result2;
        } else {
            result = str1[1].trim();
            netModeSim0 = str1[0].trim();
            netModeSim1 = result;
        }
        if (result.contains(WCDMA_ONLY)) {
            return 2;
        }
        if (result.contains(GSM_ONLY)) {
            return 1;
        }
        if (result.contains(WCDMA_PREFER)) {
            return 3;
        }
        if (result.contains("18")) {
            return 2;
        }
        if (result.contains(TD_PREFER)) {
            return 5;
        }
        if (result.contains(TD_ONLY)) {
            return 2;
        }
        if (result.contains("15")) {
            return 1;
        }
        return 3;
    }

    public boolean setNetMode(int netMode) {
        if (!checkNetMode(netMode)) {
            return false;
        }
        String atCmd = null;
        if (netMode != 0) {
            if (netMode != 1) {
                if (netMode != 2) {
                    if (netMode != 3) {
                        if (netMode != 4) {
                            if (netMode == 5) {
                                if (this.mSimIndex == 0) {
                                    atCmd = String.format("%s%s,%s", engconstents.ENG_AT_SET_WCDMA_MODEM, TD_PREFER, netModeSim1);
                                    netModeSim0 = TD_PREFER;
                                } else {
                                    atCmd = String.format("%s%s,%s", engconstents.ENG_AT_SET_WCDMA_MODEM, netModeSim0, TD_PREFER);
                                    netModeSim1 = TD_PREFER;
                                }
                            }
                        } else if (this.mSimIndex == 0) {
                            atCmd = String.format("%s%s,%s", engconstents.ENG_AT_SET_WCDMA_MODEM, TD_ONLY, netModeSim1);
                            netModeSim0 = TD_ONLY;
                        } else {
                            atCmd = String.format("%s%s,%s", engconstents.ENG_AT_SET_WCDMA_MODEM, netModeSim0, TD_ONLY);
                            netModeSim1 = TD_ONLY;
                        }
                    } else if (this.mSimIndex == 0) {
                        atCmd = String.format("%s%s,%s", engconstents.ENG_AT_SET_WCDMA_MODEM, WCDMA_PREFER, netModeSim1);
                        netModeSim0 = WCDMA_PREFER;
                    } else {
                        atCmd = String.format("%s%s,%s", engconstents.ENG_AT_SET_WCDMA_MODEM, netModeSim0, WCDMA_PREFER);
                        netModeSim1 = WCDMA_PREFER;
                    }
                } else if (this.mSimIndex == 0) {
                    atCmd = String.format("%s%s,%s", engconstents.ENG_AT_SET_WCDMA_MODEM, WCDMA_ONLY, netModeSim1);
                    netModeSim0 = WCDMA_ONLY;
                } else {
                    atCmd = String.format("%s%s,%s", engconstents.ENG_AT_SET_WCDMA_MODEM, netModeSim0, WCDMA_ONLY);
                    netModeSim1 = WCDMA_ONLY;
                }
            } else if (this.mSimIndex == 0) {
                atCmd = String.format("%s%s,%s", engconstents.ENG_AT_SET_WCDMA_MODEM, GSM_ONLY, netModeSim1);
                netModeSim0 = GSM_ONLY;
            } else {
                atCmd = String.format("%s%s,%s", engconstents.ENG_AT_SET_WCDMA_MODEM, netModeSim0, GSM_ONLY);
                netModeSim1 = GSM_ONLY;
            }
        } else {
            atCmd = String.format("%s2,0,2,4", engconstents.ENG_AT_NETMODE);
        }
        try {
            teleApi.netWorkMode().setNetWorkMode(atCmd, this.mSimIndex);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private NetModeSelectHelper(int simIndex, Context context) {
        this.mSimIndex = -1;
        this.mModemType = -1;
        this.mContext = null;
        this.mSimIndex = simIndex;
        this.mContext = context;
        TelephonyManagerSprd tm = TelephonyManagerSprd.getInstance();
        int previousSetPhoneId = tm.getPrimaryCard();
        if (TelephonyManagerSprd.getModemType() == 1) {
            if (this.mSimIndex == previousSetPhoneId) {
                this.mModemType = 1;
                return;
            } else {
                this.mModemType = 0;
                return;
            }
        }
        if (TelephonyManagerSprd.getModemType() == 2) {
            if (this.mSimIndex == previousSetPhoneId) {
                this.mModemType = 2;
                return;
            } else if (TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WW) {
                this.mModemType = 2;
                return;
            } else {
                this.mModemType = 0;
                return;
            }
        }
        this.mModemType = 0;
    }

    private boolean checkNetMode(int netMode) {
        if (this.mModemType == 0 && (netMode == 0 || netMode == 1)) {
            return true;
        }
        if (this.mModemType == 2 && (netMode == 0 || netMode == 1 || netMode == 2 || netMode == 3)) {
            return true;
        }
        if (this.mModemType == 1) {
            return netMode == 0 || netMode == 1 || netMode == 4 || netMode == 5;
        }
        return false;
    }
}
