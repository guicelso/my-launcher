package com.unisoc.engineermode.core.impl.hardware;

import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.IAsdiv;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class AsdivImpl implements IAsdiv {
    private static final String TAG = "EM-Asdiv";
    private int SIM0 = 0;

    public static class AsdivImplHolder {
        public static IAsdiv INSTANCE = new AsdivImpl();
    }

    AsdivImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.IAsdiv
    public void setLteToAntenna1() throws Exception {
        String atRSP = IATUtils.sendATCmd("At+SPDPDTENABLE=16,1,1,0", this.SIM0);
        judgeAtResponse(atRSP);
    }

    @Override // com.unisoc.engineermode.core.intf.IAsdiv
    public void setLteToAntenna2() throws Exception {
        String atRSP = IATUtils.sendATCmd("At+SPDPDTENABLE=16,1,1,1", this.SIM0);
        judgeAtResponse(atRSP);
    }

    @Override // com.unisoc.engineermode.core.intf.IAsdiv
    public void setGsmToAntenna1() throws Exception {
        String atRSP = IATUtils.sendATCmd("At+SPDPDTENABLE=0,1,1,0", this.SIM0);
        judgeAtResponse(atRSP);
    }

    @Override // com.unisoc.engineermode.core.intf.IAsdiv
    public void setGsmToAntenna2() throws Exception {
        String atRSP = IATUtils.sendATCmd("At+SPDPDTENABLE=0,1,1,1", this.SIM0);
        judgeAtResponse(atRSP);
    }

    @Override // com.unisoc.engineermode.core.intf.IAsdiv
    public void setWcdmaToAntenna1() throws Exception {
        String atRSP = IATUtils.sendATCmd("At+SPDPDTENABLE=1,1,1,0", this.SIM0);
        judgeAtResponse(atRSP);
    }

    @Override // com.unisoc.engineermode.core.intf.IAsdiv
    public void setWcdmaToAntenna2() throws Exception {
        String atRSP = IATUtils.sendATCmd("At+SPDPDTENABLE=1,1,1,1", this.SIM0);
        judgeAtResponse(atRSP);
    }

    @Override // com.unisoc.engineermode.core.intf.IAsdiv
    public void setC2kToAntenna1() throws Exception {
        String atRSP = IATUtils.sendATCmd("At+SPWASDUMMY=\"set C2K antenna\",1,0,0,0,0,0", this.SIM0);
        judgeAtResponse(atRSP);
    }

    @Override // com.unisoc.engineermode.core.intf.IAsdiv
    public void setC2kToAntenna2() throws Exception {
        String atRSP = IATUtils.sendATCmd("At+SPWASDUMMY=\"set C2K antenna\",1,1,0,0,0,0", this.SIM0);
        judgeAtResponse(atRSP);
    }

    @Override // com.unisoc.engineermode.core.intf.IAsdiv
    public void closeAllAntennas() throws Exception {
        String atRSP = IATUtils.sendATCmd("At+SPDPDTENABLE=16,1,0,0", this.SIM0);
        judgeAtResponse(atRSP);
        String atRSP2 = IATUtils.sendATCmd("At+SPDPDTENABLE=16,1,0,1", this.SIM0);
        judgeAtResponse(atRSP2);
        String atRSP3 = IATUtils.sendATCmd("At+SPDPDTENABLE=0,1,0,0", this.SIM0);
        judgeAtResponse(atRSP3);
        String atRSP4 = IATUtils.sendATCmd("At+SPDPDTENABLE=0,1,0,1", this.SIM0);
        judgeAtResponse(atRSP4);
        String atRSP5 = IATUtils.sendATCmd("At+SPDPDTENABLE=1,1,0,0", this.SIM0);
        judgeAtResponse(atRSP5);
        String atRSP6 = IATUtils.sendATCmd("At+SPDPDTENABLE=1,1,0,1", this.SIM0);
        judgeAtResponse(atRSP6);
    }

    private void judgeAtResponse(String atRSP) throws Exception {
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }
}
