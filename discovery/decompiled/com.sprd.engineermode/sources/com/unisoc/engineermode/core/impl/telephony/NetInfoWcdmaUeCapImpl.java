package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NetInfoWcdmaUeCapImpl implements ITelephonyApi.INetInfoWcdmaUeCap {
    private static final String CLOSE = "0";
    private static final String OPEN = "1";
    private static final String TAG = "NetInfoWcdmaUeNwCap";

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdmaUeCap
    public ITelephonyApi.INetInfoWcdmaUeCap.UeCap getUeCap(int simIdx) {
        return NetInfoWcdma.getUeCap(simIdx);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdmaUeCap
    public boolean openUl16Qam(int simIdx) {
        return IATUtils.sendATCmd("AT1", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdmaUeCap
    public boolean closeUl16Qam(int simIdx) {
        return IATUtils.sendATCmd("AT0", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdmaUeCap
    public boolean openDbHsdpa(int simIdx) {
        return IATUtils.sendATCmd("AT1", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdmaUeCap
    public boolean closeDbHsdpa(int simIdx) {
        return IATUtils.sendATCmd("AT0", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdmaUeCap
    public boolean openSnow3G(int simIdx) {
        return IATUtils.sendATCmd("AT1", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdmaUeCap
    public boolean closeSnow3G(int simIdx) {
        return IATUtils.sendATCmd("AT0", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdmaUeCap
    public boolean openWDiversity(int simIdx) {
        return IATUtils.sendATCmd("AT+SPDIVERSITY=1", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdmaUeCap
    public boolean closeWDiversity(int simIdx) {
        return IATUtils.sendATCmd("AT+SPDIVERSITY=0", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdmaUeCap
    public boolean openUlHsdpa(int simIdx) {
        return IATUtils.sendATCmd("AT1", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoWcdmaUeCap
    public boolean closeUlHsdpa(int simIdx) {
        return IATUtils.sendATCmd("AT0", simIdx).contains("OK");
    }
}
