package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class TelephonyInfoImpl implements ITelephonyApi.ITelephonyInfo {
    private static String PROP_SSDA_MODE = "persist.vendor.radio.modem.config";
    private static String CDMA_FLAG = "C";
    private static String WCDMA_FLAG = "W";
    private static String TDSCDMA_FLAG = "TD";
    private static String TDLTE_FLAG = "TL";
    private static String FDDLTE_FLAG = "LF";
    private static String GSM_FLAG = "G";

    TelephonyInfoImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportWcdma() {
        try {
            String ssdaMode = SystemPropertiesProxy.get(PROP_SSDA_MODE);
            return ssdaMode != null && ssdaMode.contains(WCDMA_FLAG);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportLte() {
        try {
            String ssdaMode = SystemPropertiesProxy.get(PROP_SSDA_MODE);
            if (ssdaMode != null) {
                return ssdaMode.contains(TDLTE_FLAG) || ssdaMode.contains(FDDLTE_FLAG);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportC2k() {
        try {
            String ssdaMode = SystemPropertiesProxy.get(PROP_SSDA_MODE);
            return ssdaMode != null && ssdaMode.contains(CDMA_FLAG);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportCsfb() {
        return TelephonyManagerSprd.RadioCapbility.TDD_CSFB.equals(TelephonyManagerSprd.getRadioCapbility()) || TelephonyManagerSprd.RadioCapbility.FDD_CSFB.equals(TelephonyManagerSprd.getRadioCapbility()) || TelephonyManagerSprd.RadioCapbility.CSFB.equals(TelephonyManagerSprd.getRadioCapbility()) || TelephonyManagerSprd.RadioCapbility.LW.equals(TelephonyManagerSprd.getRadioCapbility());
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportLplusG() {
        return TelephonyManagerSprd.RadioCapbility.FDD_CSFB.equals(TelephonyManagerSprd.getRadioCapbility()) || TelephonyManagerSprd.RadioCapbility.CSFB.equals(TelephonyManagerSprd.getRadioCapbility());
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportLplusW() {
        return TelephonyManagerSprd.RadioCapbility.CSFB.equals(TelephonyManagerSprd.getRadioCapbility()) || TelephonyManagerSprd.RadioCapbility.FLW.equals(TelephonyManagerSprd.getRadioCapbility());
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportLplusL() {
        return TelephonyManagerSprd.RadioCapbility.TDD_TDD.equals(TelephonyManagerSprd.getRadioCapbility()) || TelephonyManagerSprd.RadioCapbility.TD_TD.equals(TelephonyManagerSprd.getRadioCapbility());
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportWplusG() {
        return TelephonyManagerSprd.RadioCapbility.WG.equals(TelephonyManagerSprd.getRadioCapbility());
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportWplusW() {
        return TelephonyManagerSprd.RadioCapbility.WW.equals(TelephonyManagerSprd.getRadioCapbility());
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportNetwork() {
        return SystemPropertiesProxy.get("persist.radio.network.unable").equals("true");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportTdscdma() {
        try {
            return SystemPropertiesProxy.get(PROP_SSDA_MODE).contains(TDSCDMA_FLAG);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportGsm() {
        try {
            return SystemPropertiesProxy.get(PROP_SSDA_MODE).contains(GSM_FLAG);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ITelephonyInfo
    public boolean isSupportAPCFunc() {
        try {
            String mStrTmp = IATUtils.sendATCmd(engconstents.ENG_AT_GET_SPAPC, "atchannel0");
            return (mStrTmp == null || mStrTmp.trim().equalsIgnoreCase("+CME ERROR: 4")) ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
