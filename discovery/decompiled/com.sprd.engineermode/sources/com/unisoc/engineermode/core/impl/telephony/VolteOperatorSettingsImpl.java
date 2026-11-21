package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class VolteOperatorSettingsImpl implements ITelephonyApi.IVolteOperatorSettings {
    private static final String TAG = "VolteOperatorSettingsImpl";

    public static class VolteOperatorSettingsImplHolder {
        static final ITelephonyApi.IVolteOperatorSettings INSTANCE = new VolteOperatorSettingsImpl();
    }

    VolteOperatorSettingsImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getIpsecStatus(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=95,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setIpsecStatus(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getRegSubscribe(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=30,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setRegSubscribe(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getMwiEnable(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=33,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setMwiEnable(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getSIPHeaderCompact(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=44,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setSIPHeaderCompact(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getSMSoverIPEnable(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+CASIMS?", simIdx);
        if (mStrTmp.contains("OK")) {
            return mStrTmp.split("\\:")[1].split("\n")[0].trim();
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setSMSoverIPEnable(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getUssiEnable(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=41,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setUssiEnable(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getInitialRegister(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=32,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setInitialRegister(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getIntegrityAlgorithm(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=96,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setIntegrityAlgorithm(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getCipherAlgorithm(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=97,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setCipherAlgorithm(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getRegSubscrib(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=31,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setRegSubscrib(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getMwiSubscrib(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=35,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setMwiSubscrib(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getMtuValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=86,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setMtuValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getPcscfPort(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=87,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setPcscfPort(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getRegExpireValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=29,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setRegExpireValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getTregTimerValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=25,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setTregTimerValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getTemergregValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=26,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setTemergregValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getVoiceCodeType(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=65,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setVoiceCodeType(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getMinBandWidth(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=69,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setMinBandWidth(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getMaxBandWidth(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=70,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setMaxBandWidth(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getMinBitRate(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=71,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setMinBitRate(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getMaxBitRate(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=72,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setMaxBitRate(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getDefaultBitRate(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=77,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setDefaultBitRate(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getEvsRate(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=68,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setEvsRate(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getEvsDefaultBitRate(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=76,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setEvsDefaultBitRate(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getWbRate(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=67,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setWbRate(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getWbDefaultRate(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=75,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setWbDefaultRate(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getNbRate(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=66,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setNbRate(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getNbDefaultRate(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=74,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setNbDefaultRate(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getChannelAwarkMode(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=73,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setChannelAwarkMode(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getMoAmrOa(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=78,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setMoAmrOa(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getNamedTelEvent(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=79,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setNamedTelEvent(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getAudioRTPTimeout(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=45,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setAudioRTPTimeout(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getAudioRTCPTime(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=47,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setAudioRTCPTime(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getAudioRSValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=89,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setAudioRSValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getAudioRRValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=90,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setAudioRRValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getVideoRsValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=91,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setVideoRsValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getVideoRRValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=92,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setVideoRRValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getTcallTimerValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=23,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setTcallTimerValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getRingingTimerValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=48,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setRingingTimerValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getRingbackTimer(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=49,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setRingbackTimer(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getNoAnswerTimer(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=50,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setNoAnswerTimer(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getSessionTimer(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=37,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setSessionTimer(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getMTSessionTimer(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=39,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setMTSessionTimer(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getConferenceURI(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=53,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setConferenceURI(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getVideoCodeType(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=9,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setVideoCodeType(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getReliable180(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=100,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setReliable180(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getDirectAlerting(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=99,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setDirectAlerting(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getResourceAlways(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=102,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setResourceAlways(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getPrecondition(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=93,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setPrecondition(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getVideoUpgrade(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=94,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setVideoUpgrade(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getVideoEarly(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=51,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setVideoEarly(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getAlertingValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=80,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setAlertingValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getMidCallSwitch(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=81,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setMidCallSwitch(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getPreAlerting(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=82,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setPreAlerting(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getRSRVCCSupport(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=83,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setRSRVCCSupport(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getRSRVCCAlert(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=84,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setRSRVCCAlert(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getRSRVCCMid(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=85,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setRSRVCCMid(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getVideoMaxResolution(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=106,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setVideoMaxResolution(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getNationalURL(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=42,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setNationalURL(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getInternationalURL(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=43,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setInternationalURL(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getTimerRefresher(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=38,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setTimerRefresher(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getMTTimerRefresher(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=40,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setMTTimerRefresher(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getVideoAudioMerge(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=54,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setVideoAudioMerge(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getAudioDynamic(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=104,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setAudioDynamic(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getVideoDynamic(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=20,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setVideoDynamic(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getBsfURIValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=60,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setBsfURIValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getBsfPORTValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=61,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setBsfPORTValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getXcapURIValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=62,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setXcapURIValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getXcapPortValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=63,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setXcapPortValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getXcapAuidValue(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=64,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setXcapAuidValue(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getLocalCall(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=55,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setLocalCall(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getDnsSrvEnable(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=56,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setDnsSrvEnable(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getHttpsEnable(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=58,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setHttpsEnable(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getActivateCfnl(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=103,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setActivateCfnl(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getHttpPutMedia(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=59,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setHttpPutMedia(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public String getIPPriority(int simIdx) throws Exception {
        String mStrTmp = IATUtils.sendATCmd("AT+SPVOLTEENG=57,0", simIdx);
        if (mStrTmp.contains("OK")) {
            return anayResult(mStrTmp);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, mStrTmp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteOperatorSettings
    public void setIPPriority(String atCmd, int simIdx) throws Exception {
        String atRSP = IATUtils.sendATCmd(atCmd, simIdx);
        if (!atRSP.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRSP);
        }
    }

    private String anayResult(String result) {
        String res = null;
        try {
            if (!result.contains("OK")) {
                return "FAILED";
            }
            res = result.split("\\:")[1].split("\n")[0].split(",")[1].trim().replace("\"", "");
            Log.d(TAG, "anayResult anayResult is: " + res);
            return res;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return res;
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            return res;
        }
    }
}
