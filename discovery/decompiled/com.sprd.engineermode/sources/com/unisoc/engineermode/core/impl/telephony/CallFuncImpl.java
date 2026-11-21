package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
class CallFuncImpl implements ITelephonyApi.ICallFunc {
    private static final String AUTO_ANSWER = "persist.radio.call.autoanswer";
    private static final String CFU_CONTROL = "persist.sys.callforwarding";

    public static class CallFuncImplHolder {
        static final ITelephonyApi.ICallFunc INSTANCE = new CallFuncImpl();
    }

    CallFuncImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICallFunc
    public void openAutoAnswer() throws Exception {
        SystemPropertiesProxy.set(AUTO_ANSWER, "1");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICallFunc
    public void closeAutoAnswer() throws Exception {
        SystemPropertiesProxy.set(AUTO_ANSWER, "0");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICallFunc
    public String getCfu() {
        return SystemPropertiesProxy.get(CFU_CONTROL, "0");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.ICallFunc
    public void setCfu(String value) throws Exception {
        SystemPropertiesProxy.set(CFU_CONTROL, value);
    }
}
