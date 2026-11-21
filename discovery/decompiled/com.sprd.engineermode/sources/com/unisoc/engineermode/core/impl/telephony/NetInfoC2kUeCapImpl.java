package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class NetInfoC2kUeCapImpl implements ITelephonyApi.INetInfoC2kUeCap {
    private static final String TAG = "NetInfoC2kUeCap";

    NetInfoC2kUeCapImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.INetInfoC2kUeCap
    public String getUeCapInfo(int simIdx) {
        return IATUtils.sendATCmd("AT+SPENGMD=0,12,0", simIdx);
    }
}
