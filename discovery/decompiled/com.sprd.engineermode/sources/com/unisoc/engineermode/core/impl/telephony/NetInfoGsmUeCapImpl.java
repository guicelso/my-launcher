package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class NetInfoGsmUeCapImpl implements ITelephonyApi.INetInfoGsmUeCap {
    private static final String CLOSE = "0";
    private static final String OPEN = "1";

    NetInfoGsmUeCapImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoGsmUeCap
    public boolean getVamos(int simIdx) {
        return IATUtils.sendATCmd(engconstents.ENG_AT_GET_VAMOS, simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoGsmUeCap
    public boolean openVamos(int simIdx) {
        return IATUtils.sendATCmd("at+spengmd = 1,0,7,0,1", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoGsmUeCap
    public boolean closeVamos(int simIdx) {
        return IATUtils.sendATCmd("at+spengmd = 1,0,7,0,0", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoGsmUeCap
    public boolean getDiversity(int simIdx) {
        return IATUtils.sendATCmd(engconstents.ENG_AT_GET_DIVERSITY, simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoGsmUeCap
    public boolean openDiversity(int simIdx) {
        return IATUtils.sendATCmd("AT+SPDIVERSITY=1,0,1,70,1,70", simIdx).contains("OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoGsmUeCap
    public boolean closeDiversity(int simIdx) {
        return IATUtils.sendATCmd("AT+SPDIVERSITY=1,0,0,70,0,70", simIdx).contains("OK");
    }
}
