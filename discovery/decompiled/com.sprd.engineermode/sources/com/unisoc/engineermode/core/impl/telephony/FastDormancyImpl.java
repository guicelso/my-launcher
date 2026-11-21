package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class FastDormancyImpl implements ITelephonyApi.IFastDormancy {
    private static final int FAST_DORMANCY_CLOSE = 0;
    private static final int FAST_DORMANCY_OPEN = 1;
    private static final int SIM0 = 0;
    private static final int SIM1 = 1;
    private int mPhoneCount;

    FastDormancyImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IFastDormancy
    public void close() throws Exception {
        set(0, 0);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IFastDormancy
    public void open(int value) throws Exception {
        set(1, value);
    }

    private void set(int state, int value) throws Exception {
        String cmd = engconstents.ENG_AT_SPSETFDY + state;
        if (value != 0) {
            cmd = cmd + "," + value;
        }
        String mStrTmp = IATUtils.sendATCmd(cmd, 0);
        judgeAtResponse(mStrTmp);
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        if (this.mPhoneCount > 1) {
            String mStrTmp2 = IATUtils.sendATCmd(cmd, 1);
            judgeAtResponse(mStrTmp2);
        }
    }

    private void judgeAtResponse(String atRSP) throws Exception {
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }
}
