package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class VolteTemporarySettingsImpl implements ITelephonyApi.IVolteTemporarySettings {

    public static class VolteTemporarySettingsImplHolder {
        static final ITelephonyApi.IVolteTemporarySettings INSTANCE = new VolteTemporarySettingsImpl();
    }

    VolteTemporarySettingsImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteTemporarySettings
    public void setImpi(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteTemporarySettings
    public void setImpu(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteTemporarySettings
    public void setDomain(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteTemporarySettings
    public void setPcscf(String cmdValue) throws Exception {
        SystemPropertiesProxy.set("persist.sys.volte.pcscf", cmdValue);
    }
}
