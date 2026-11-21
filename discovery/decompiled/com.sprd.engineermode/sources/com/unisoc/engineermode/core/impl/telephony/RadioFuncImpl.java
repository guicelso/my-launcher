package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
class RadioFuncImpl implements ITelephonyApi.IRadioFunc {
    private static final String TAG = "RFPOWER";
    private static final int WCDMA_DIVERSITY_ONLY = 17;
    private static final int WCDMA_PRIMARY_AND_DIVERSITY = 1;
    private static final int WCDMA_PRIMARY_ONLY = 0;

    RadioFuncImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IRadioFunc
    public void setMaxPower(ITelephonyApi.IRadioFunc.Band band, int dbm) throws Exception {
        int freq = 0;
        Log.d(TAG, "setMaxPower: " + band.getValue() + ", dbm: " + dbm);
        switch (band) {
            case TD19:
                freq = 10054;
                break;
            case TD21:
                freq = 9404;
                break;
            case WBAND1:
                freq = 10693;
                break;
            case WBAND2:
                freq = 9875;
                break;
            case WBAND5:
                freq = 4450;
                break;
            case WBAND8:
                freq = 3012;
                break;
        }
        setMaxPowerImpl(band.getValue(), true, dbm, freq, 0, false);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IRadioFunc
    public void clearMaxPower(ITelephonyApi.IRadioFunc.Band band) throws Exception {
        Log.d(TAG, "clearMaxPower: " + band.getValue());
        setMaxPowerImpl(band.getValue(), false, 0, 0, 0, false);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IRadioFunc
    public ITelephonyApi.IRadioFunc.DualRfState getDualRfState() throws Exception {
        String atResponse = IATUtils.sendATCmd(engconstents.ENG_GET_ANTENNA_STATE, "atchannel0");
        if (!atResponse.contains("OK")) {
            throw new OperationFailedException("at return: " + atResponse);
        }
        String[] tempArray = atResponse.split(" ");
        String[] paraArray = tempArray[1].split(",");
        String[] paraArray2 = paraArray[1].trim().split("\n");
        int lteState = Integer.valueOf(paraArray[0].trim()).intValue();
        int wcdmaState = Integer.valueOf(paraArray2[0].trim()).intValue();
        Log.d(TAG, "wcdmaState=" + wcdmaState);
        Log.d(TAG, "lteState=" + lteState);
        ITelephonyApi.IRadioFunc.DualRfState state = new ITelephonyApi.IRadioFunc.DualRfState();
        if (wcdmaState != 0) {
            if (wcdmaState == 1) {
                state.wcdmaState = ITelephonyApi.IRadioFunc.WcdmaDualRfState.PRIMARY_AND_DIVERSITY;
            } else if (wcdmaState == 17) {
                state.wcdmaState = ITelephonyApi.IRadioFunc.WcdmaDualRfState.DIVERSITY_ONLY;
            } else {
                throw new OperationFailedException("wcdma state is wrong.");
            }
        } else {
            state.wcdmaState = ITelephonyApi.IRadioFunc.WcdmaDualRfState.PRIMARY_ONLY;
        }
        if ((lteState & 8) != 0) {
            state.isLteSccTxOpened = true;
        }
        if ((lteState & 16) != 0) {
            state.isLteDiversityRxOpened = true;
        }
        if ((lteState & 32) != 0) {
            state.isLtePrimaryTxOpened = true;
        }
        if ((lteState & 64) != 0) {
            state.isLtePrimaryRxOpened = true;
        }
        return state;
    }

    /* JADX INFO: renamed from: com.unisoc.engineermode.core.impl.telephony.RadioFuncImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$WcdmaDualRfState = new int[ITelephonyApi.IRadioFunc.WcdmaDualRfState.values().length];

        static {
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$WcdmaDualRfState[ITelephonyApi.IRadioFunc.WcdmaDualRfState.PRIMARY_ONLY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$WcdmaDualRfState[ITelephonyApi.IRadioFunc.WcdmaDualRfState.PRIMARY_AND_DIVERSITY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$WcdmaDualRfState[ITelephonyApi.IRadioFunc.WcdmaDualRfState.DIVERSITY_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$Band = new int[ITelephonyApi.IRadioFunc.Band.values().length];
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$Band[ITelephonyApi.IRadioFunc.Band.GSM850.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$Band[ITelephonyApi.IRadioFunc.Band.GSM900.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$Band[ITelephonyApi.IRadioFunc.Band.DCS1800.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$Band[ITelephonyApi.IRadioFunc.Band.DCS1900.ordinal()] = 4;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$Band[ITelephonyApi.IRadioFunc.Band.TD19.ordinal()] = 5;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$Band[ITelephonyApi.IRadioFunc.Band.TD21.ordinal()] = 6;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$Band[ITelephonyApi.IRadioFunc.Band.WBAND1.ordinal()] = 7;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$Band[ITelephonyApi.IRadioFunc.Band.WBAND2.ordinal()] = 8;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$Band[ITelephonyApi.IRadioFunc.Band.WBAND5.ordinal()] = 9;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$Band[ITelephonyApi.IRadioFunc.Band.WBAND8.ordinal()] = 10;
            } catch (NoSuchFieldError e13) {
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IRadioFunc
    public void setDualRfState(ITelephonyApi.IRadioFunc.DualRfState state) throws Exception {
        int wcdma = 0;
        int i = AnonymousClass1.$SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$WcdmaDualRfState[state.wcdmaState.ordinal()];
        if (i == 1) {
            wcdma = 0;
        } else if (i == 2) {
            wcdma = 1;
        } else if (i == 3) {
            wcdma = 17;
        }
        int lte = 0;
        if (state.isLteSccTxOpened) {
            lte = 0 | 8;
        }
        if (state.isLteDiversityRxOpened) {
            lte |= 16;
        }
        if (state.isLtePrimaryTxOpened) {
            lte |= 32;
        }
        if (state.isLtePrimaryRxOpened) {
            lte |= 64;
        }
        String atCmd = engconstents.ENG_SET_ANTENNA + lte + "," + wcdma;
        Log.d(TAG, "mLTESCCTX mATCmd=" + atCmd);
        String atResponse = IATUtils.sendATCmd(atCmd, "atchannel0");
        if (!atResponse.contains("OK")) {
            throw new OperationFailedException("at return: " + atResponse);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IRadioFunc
    public int getUeCategory() throws Exception {
        String result = IATUtils.sendATCmd("AT+SPUECAT=0", "atchannel0");
        Log.d(TAG, "GET_CATEGARY_VALUE >>> AT+SPUECAT=0: " + result);
        try {
            if (!result.contains("OK")) {
                throw new OperationFailedException("AT failed: " + result);
            }
            String[] str1 = result.split("\n");
            String[] str2 = str1[0].split(":");
            return Integer.parseInt(str2[1].trim());
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            e.printStackTrace();
            throw new OperationFailedException("AT failed: " + result);
        }
    }

    private void setMaxPowerImpl(int i, boolean z, int i2, int i3, int i4, boolean z2) throws OperationFailedException {
        String strSendATCmd = IATUtils.sendATCmd(String.format("%s%d,%d,%d,%d,%d,%d", engconstents.ENG_SEND_POWER, Integer.valueOf(i), Integer.valueOf(z ? 1 : 0), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(z2 ? 1 : 0)), "atchannel0");
        if (!strSendATCmd.contains("OK")) {
            throw new OperationFailedException("result: " + strSendATCmd);
        }
    }
}
