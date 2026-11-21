package com.unisoc.engineermode.core.impl.hardware;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.IAntenna;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class AntennaImpl implements IAntenna {
    private static final int SIM0 = 0;
    private static final String TAG = "ANTENNAIMPL";
    private Map<IAntenna.AntennaState, Integer> gsmStateMap = new HashMap();
    private Map<IAntenna.AntennaState, Integer> wcdmaStateMap = new HashMap();
    private Map<IAntenna.AntennaState, Integer> lteStateMap = new HashMap();
    private Map<IAntenna.AntennaState, Integer> c2kStateMap = new HashMap();
    private Pattern pattern = Pattern.compile("\\+SPDUALRFSEL: ([0-9,]+)\r\nOK\r\n");

    public AntennaImpl() {
        this.gsmStateMap.put(IAntenna.AntennaState.PRIMARY_DIVERSITY, 1);
        this.gsmStateMap.put(IAntenna.AntennaState.PRIMARY, 0);
        this.gsmStateMap.put(IAntenna.AntennaState.DIVERSITY, 3);
        this.wcdmaStateMap.put(IAntenna.AntennaState.PRIMARY_DIVERSITY, 1);
        this.wcdmaStateMap.put(IAntenna.AntennaState.PRIMARY, 0);
        this.wcdmaStateMap.put(IAntenna.AntennaState.DIVERSITY, 17);
        this.lteStateMap.put(IAntenna.AntennaState.PRIMARY_DIVERSITY, 0);
        this.lteStateMap.put(IAntenna.AntennaState.PRIMARY, 1);
        this.lteStateMap.put(IAntenna.AntennaState.DIVERSITY, 2);
        this.c2kStateMap.put(IAntenna.AntennaState.PRIMARY_DIVERSITY, 4);
        this.c2kStateMap.put(IAntenna.AntennaState.PRIMARY, 2);
        this.c2kStateMap.put(IAntenna.AntennaState.DIVERSITY, 3);
        this.c2kStateMap.put(IAntenna.AntennaState.PRIMARY_DIVERSITY_DYNAMIC, 5);
    }

    @Override // com.unisoc.engineermode.core.intf.IAntenna
    public IAntenna.AllState getAllStates() {
        String result = IATUtils.sendATCmd(engconstents.ENG_GET_ANTENNA_STATE, 0);
        if (result == null || !result.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
        Matcher matcher = this.pattern.matcher(result);
        if (!matcher.find() || matcher.group(1) == null) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
        String[] statusArr = matcher.group(1).split(",");
        IAntenna.AllState status = new IAntenna.AllState();
        if (statusArr.length == 2) {
            status.setLte(getStateByValue(this.lteStateMap, Integer.parseInt(statusArr[0].trim())));
            status.setWcdma(getStateByValue(this.wcdmaStateMap, Integer.parseInt(statusArr[1].trim())));
        } else if (statusArr.length >= 3) {
            status.setLte(getStateByValue(this.lteStateMap, Integer.parseInt(statusArr[0].trim())));
            status.setWcdma(getStateByValue(this.wcdmaStateMap, Integer.parseInt(statusArr[1].trim())));
            status.setGsm(getStateByValue(this.gsmStateMap, Integer.parseInt(statusArr[2].trim())));
        } else {
            status.setWcdma(getStateByValue(this.wcdmaStateMap, Integer.parseInt(statusArr[0].trim())));
        }
        Log.d(TAG, "status: " + status);
        return status;
    }

    private IAntenna.AntennaState getC2kStatus() {
        String result = IATUtils.sendATCmd("At+SPWASDUMMY=\"get C2K antenna\"", 0);
        if (result == null || !result.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
        String[] c2kStr = result.split(",");
        if (c2kStr.length > 3) {
            return getStateByValue(this.c2kStateMap, Integer.parseInt(c2kStr[3]));
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
    }

    @Override // com.unisoc.engineermode.core.intf.IAntenna
    public void setLte(IAntenna.AntennaState status) {
        String atCmd = engconstents.ENG_SET_ANTENNA + this.lteStateMap.get(status);
        String atResult = IATUtils.sendATCmd(atCmd, 0);
        judgeAtResponse(atResult);
    }

    @Override // com.unisoc.engineermode.core.intf.IAntenna
    public void setWcdma(IAntenna.AntennaState status) {
        String atCmd = String.format("%s,%d", engconstents.ENG_SET_ANTENNA, this.wcdmaStateMap.get(status));
        String atResult = IATUtils.sendATCmd(atCmd, 0);
        judgeAtResponse(atResult);
    }

    @Override // com.unisoc.engineermode.core.intf.IAntenna
    public void setGsm(IAntenna.AntennaState status) {
        String atCmd = String.format("%s,,%d", engconstents.ENG_SET_ANTENNA, this.gsmStateMap.get(status));
        String atResult = IATUtils.sendATCmd(atCmd, 0);
        judgeAtResponse(atResult);
    }

    @Override // com.unisoc.engineermode.core.intf.IAntenna
    public void setC2k(IAntenna.AntennaState status) {
        String atCmd = String.format("%s\"set C2K antenna\",1,%d,0,0,0,0", engconstents.ENG_AT_SET_WASDUMMY_PARAM, this.c2kStateMap.get(status));
        String atResult = IATUtils.sendATCmd(atCmd, 0);
        judgeAtResponse(atResult);
    }

    private IAntenna.AntennaState getStateByValue(Map<IAntenna.AntennaState, Integer> statusMap, int value) {
        for (Map.Entry<IAntenna.AntennaState, Integer> e : statusMap.entrySet()) {
            if (e.getValue().intValue() == value) {
                return e.getKey();
            }
        }
        JSONObject json = new JSONObject();
        try {
            json.put("statusMap", statusMap);
        } catch (Exception e2) {
            Log.e(TAG, Log.getStackTraceString(e2));
        }
        throw new RuntimeException(String.format("can't find value %d in %s", Integer.valueOf(value), json.toString()));
    }

    private void judgeAtResponse(String atRsp) {
        if (!atRsp.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRsp);
        }
    }
}
