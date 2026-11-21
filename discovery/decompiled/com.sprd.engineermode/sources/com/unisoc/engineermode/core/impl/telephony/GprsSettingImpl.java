package com.unisoc.engineermode.core.impl.telephony;

import android.text.TextUtils;
import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class GprsSettingImpl implements ITelephonyApi.IGprsSetting {
    private static final String TAG = "GPRSOPER";

    GprsSettingImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IGprsSetting
    public void attach(int simIdx) throws Exception {
        String values = IATUtils.sendATCmd("AT+CGATT=1", simIdx);
        Log.d(TAG, "<0> ATCMD is AT+CGATT=1 GPRS_ATTACHED Result is " + values);
        if (!values.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, values);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IGprsSetting
    public void detach(int simIdx) throws Exception {
        String values = IATUtils.sendATCmd("AT+CGATT=0", simIdx);
        Log.d(TAG, "<0> ATCMD is AT+CGATT=0 GPRS_DETACHED Result is " + values);
        if (!values.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, values);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IGprsSetting
    public void setAlwaysAttach(int simIdx) throws Exception {
        String values = IATUtils.sendATCmd("AT+SAUTOATT=1", simIdx);
        Log.d(TAG, "<0> ATCMD is AT+SAUTOATT=1 GPRS_ALWAYS_ATTACH Result is " + values);
        if (!values.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, values);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IGprsSetting
    public void setNeededAttach(int simIdx) throws Exception {
        String values = IATUtils.sendATCmd("AT+SAUTOATT=0", simIdx);
        Log.d(TAG, "<0> ATCMD is AT+SAUTOATT=0 GPRS_WHEN_NEEDED_ATTACH Result is " + values);
        if (!values.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, values);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IGprsSetting
    public boolean[] getPdpContextState(int simIdx) throws Exception {
        String result = IATUtils.sendATCmd(engconstents.ENG_AT_GETPDPACTIVE1, simIdx);
        if (!result.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
        int pdpCtxCount = TelephonyManagerSprd.getModemType() == 2 ? 3 : 6;
        boolean[] phaseResult = new boolean[pdpCtxCount];
        String[] line = result.split("\n");
        for (int i = 0; i < phaseResult.length; i++) {
            Log.d(TAG, "line" + i + "=" + line[i]);
            String[] pdpContextInfo = line[i].split(",");
            if (pdpContextInfo[1] != null && pdpContextInfo[1].contains("1")) {
                phaseResult[i] = true;
            } else {
                phaseResult[i] = false;
            }
        }
        return phaseResult;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IGprsSetting
    public boolean activateFirstPdp(int simIdx, int trafficClass, int pdpType) {
        String apn = getApn(simIdx, pdpType);
        Log.d(TAG, "apn = " + apn);
        if (apn.equals("")) {
            return false;
        }
        String atCmd = "AT+CGDCONT=" + pdpType + ",\"IPV4V6\",\"" + apn + "\",\"\",0,0";
        String apnValues = IATUtils.sendATCmd(atCmd, simIdx);
        Log.d(TAG, "<0> ATCMD is " + atCmd + " AT+CGDCONT Result is " + apnValues);
        String atCmd2 = "AT+CGEQREQ=" + pdpType + "," + trafficClass + ",0,0,0,0,2,0,\"1e4\",\"0e0\",3,0,0";
        String tvalues = IATUtils.sendATCmd(atCmd2, simIdx);
        Log.d(TAG, "<0> ATCMD is " + atCmd2 + " AT+CGEQREQ Result is " + tvalues);
        StringBuilder sb = new StringBuilder();
        sb.append("AT+CGDATA=\"M-ETHER\",");
        sb.append(pdpType);
        String atCmd3 = sb.toString();
        String values = IATUtils.sendATCmd(atCmd3, "atchannel" + simIdx);
        Log.d(TAG, "<0> ATCMD is " + atCmd3 + " GPRS_ACTIVATE_PDP Result is " + values);
        return values.contains(IATUtils.AT_CONNECT) && tvalues.contains("OK") && apnValues.contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IGprsSetting
    public boolean activateSecondPdp(int simIdx, int pdpType) {
        String resp = IATUtils.sendATCmd("AT+CGACT=1," + pdpType, simIdx);
        if (resp.contains("OK")) {
            return true;
        }
        return false;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IGprsSetting
    public boolean deactivatePdp(int simIdx, int pdpType) {
        String atCmd = "AT+CGACT=0," + pdpType;
        String values = IATUtils.sendATCmd(atCmd, simIdx);
        Log.d(TAG, "<0> ATCMD is " + atCmd + " GPRS_DEACTIVATE_PDP Result is " + values);
        return values.contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IGprsSetting
    public boolean sendData(int simIdx, int pdpType, String dataLength, String data) {
        String atCmd;
        if (data.equals("")) {
            atCmd = engconstents.ENG_AT_SGPRSDATA1 + dataLength + "," + pdpType;
        } else {
            atCmd = engconstents.ENG_AT_SGPRSDATA1 + dataLength + "," + pdpType + ",\"" + data + "\"";
        }
        String values = IATUtils.sendATCmd(atCmd, simIdx);
        Log.d(TAG, "<0> ATCMD is " + atCmd + " GPRS_SEND_DATA Result is " + values);
        return values.contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IGprsSetting
    public boolean getAutoAttachState(int simIdx) throws Exception {
        String values = IATUtils.sendATCmd(engconstents.ENG_AT_GETAUTOATT, simIdx);
        Log.d(TAG, "<0> ATCMD is " + engconstents.ENG_AT_GETAUTOATT + " GET_AUTOATT_STATUS Result is " + values);
        if (values.contains("OK")) {
            if (values.contains("1")) {
                return true;
            }
            if (values.contains("0")) {
                return false;
            }
            throw new OperationFailedException(ErrorCode.AT_RETURN_PARSE_ERROR, values);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, values);
    }

    private String getApn(int simIdx, int pdpType) {
        String tem2;
        int dstEnd;
        String responValue = IATUtils.sendATCmd(engconstents.ENG_AT_APNQUERY, "atchannel" + simIdx);
        Log.d(TAG, "AT+CGDCONT? " + responValue);
        if (TextUtils.isEmpty(responValue) || !responValue.startsWith("+CGDCONT")) {
            return "";
        }
        String dstPrefix = "CGDCONT:" + pdpType + ",";
        int prefixStart = responValue.indexOf(dstPrefix);
        int prefixLen = dstPrefix.length();
        if (prefixStart == -1) {
            return "";
        }
        String tem = responValue.substring(prefixStart + prefixLen);
        int prefixLen2 = ",".length();
        int prefixStart2 = tem.indexOf(",");
        if (prefixStart2 == -1 || (dstEnd = (tem2 = tem.substring(prefixStart2 + prefixLen2)).indexOf(",")) == 0) {
            return "";
        }
        String dst = tem2.substring(0, dstEnd);
        Log.d("TAG", "default bearer net access dst=" + dst);
        return dst;
    }
}
