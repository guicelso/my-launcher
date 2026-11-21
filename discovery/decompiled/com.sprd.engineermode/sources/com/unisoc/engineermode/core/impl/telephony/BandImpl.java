package com.unisoc.engineermode.core.impl.telephony;

import android.support.v4.internal.view.SupportMenu;
import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
class BandImpl implements ITelephonyApi.IBand {
    private static final int DCS1800_BIT = 2;
    private static final int GSM850_BIT = 8;
    private static final int GSM900_BIT = 1;
    private static final int PCS1900_BIT = 4;
    private static final String TAG = "EN-BAND";
    private static final int[] gsmBandMap = {1, 2, 4, 8, 3, 9, 10, 12, 5, 11, 13, 6, 14, 7, 15};

    BandImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IBand
    public List<ITelephonyApi.IBand.GsmBand> getGsmBand(int simIdx) throws Exception {
        String resp = IATUtils.sendATCmd(engconstents.ENG_AT_CURRENT_GSMBAND, simIdx);
        if (!resp.contains("OK")) {
            throw new OperationFailedException("AT returns: " + resp);
        }
        String[] str = resp.split(":|\n|,");
        Log.d(TAG, "str:" + Arrays.toString(str));
        int bandValue = Integer.valueOf(str[2].trim()).intValue();
        List<ITelephonyApi.IBand.GsmBand> bandList = new ArrayList<>();
        for (ITelephonyApi.IBand.GsmBand band : ITelephonyApi.IBand.GsmBand.values()) {
            int i = AnonymousClass1.$SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IBand$GsmBand[band.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        if ((gsmBandMap[bandValue] & 4) != 0) {
                            bandList.add(band);
                        }
                    } else if (i == 4 && (gsmBandMap[bandValue] & 8) != 0) {
                        bandList.add(band);
                    }
                } else if ((gsmBandMap[bandValue] & 2) != 0) {
                    bandList.add(band);
                }
            } else if ((gsmBandMap[bandValue] & 1) != 0) {
                bandList.add(band);
            }
        }
        return bandList;
    }

    /* JADX INFO: renamed from: com.unisoc.engineermode.core.impl.telephony.BandImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IBand$GsmBand = new int[ITelephonyApi.IBand.GsmBand.values().length];

        static {
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IBand$GsmBand[ITelephonyApi.IBand.GsmBand.GSM900.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IBand$GsmBand[ITelephonyApi.IBand.GsmBand.DCS1800.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IBand$GsmBand[ITelephonyApi.IBand.GsmBand.PCS1900.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IBand$GsmBand[ITelephonyApi.IBand.GsmBand.GSM850.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IBand
    public void setGsmBand(int simIdx, List<ITelephonyApi.IBand.GsmBand> bands) throws Exception {
        if (bands.isEmpty()) {
            throw new OperationFailedException("at least one band is needed");
        }
        int bandValue = 0;
        for (ITelephonyApi.IBand.GsmBand band : bands) {
            int i = AnonymousClass1.$SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IBand$GsmBand[band.ordinal()];
            if (i == 1) {
                bandValue |= 1;
            } else if (i == 2) {
                bandValue |= 2;
            } else if (i == 3) {
                bandValue |= 4;
            } else if (i == 4) {
                bandValue |= 8;
            }
        }
        int setValue = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = gsmBandMap;
            if (i2 >= iArr.length) {
                break;
            }
            if (iArr[i2] != bandValue) {
                i2++;
            } else {
                setValue = i2;
                break;
            }
        }
        String resp = IATUtils.sendATCmd(engconstents.ENG_AT_SELECT_GSMBAND + setValue, simIdx);
        if (!resp.contains("OK")) {
            throw new OperationFailedException("AT returns: " + resp);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IBand
    public ITelephonyApi.IBand.TdBand getTdBand(int simIdx) throws Exception {
        String resp = IATUtils.sendATCmd(engconstents.ENG_AT_TD_LOCKED_BAND, simIdx);
        if (!resp.contains("OK")) {
            throw new OperationFailedException("AT returns: " + resp);
        }
        ITelephonyApi.IBand.TdBand band = new ITelephonyApi.IBand.TdBand();
        band.band_a = false;
        band.band_f = false;
        String[] str = resp.split(":|\n|,");
        Log.d(TAG, "str:" + Arrays.toString(str));
        String bandstr = str[1].trim();
        if (bandstr.startsWith("A ")) {
            band.band_a = true;
        } else if (bandstr.startsWith("F ")) {
            band.band_f = true;
        } else if (bandstr.startsWith("A+F ")) {
            band.band_a = true;
            band.band_f = true;
        }
        return band;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IBand
    public void setTdBand(int simIdx, ITelephonyApi.IBand.TdBand bands) throws Exception {
        String para = "";
        if (bands.band_a && bands.band_f) {
            para = "A+F";
        } else if (bands.band_a) {
            para = "A";
        } else if (bands.band_f) {
            para = "F";
        }
        String atCmd = String.format("%s\"%s\"", engconstents.ENG_AT_TD_SET_BAND, para);
        String resp = IATUtils.sendATCmd(atCmd, simIdx);
        if (!resp.contains("OK")) {
            throw new OperationFailedException("AT failed: " + resp);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IBand
    public List<ITelephonyApi.IBand.WcdmaBand> getWcdmaBand(int simIdx) throws Exception {
        List<ITelephonyApi.IBand.WcdmaBand> bandList = new ArrayList<>();
        for (ITelephonyApi.IBand.WcdmaBand band : ITelephonyApi.IBand.WcdmaBand.values()) {
            if (isWcdmaBandOpen(simIdx, band)) {
                bandList.add(band);
            }
        }
        return bandList;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IBand
    public void setWcdmaBand(int simIdx, List<ITelephonyApi.IBand.WcdmaBand> enabledBands, List<ITelephonyApi.IBand.WcdmaBand> disabledBands) throws Exception {
        if (enabledBands.isEmpty()) {
            throw new OperationFailedException("at least one enabled band is needed");
        }
        for (ITelephonyApi.IBand.WcdmaBand band : enabledBands) {
            setWcdmaBandState(simIdx, band, true);
        }
        for (ITelephonyApi.IBand.WcdmaBand band2 : disabledBands) {
            setWcdmaBandState(simIdx, band2, false);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IBand
    public boolean isWcdmaBandOpen(int simIdx, ITelephonyApi.IBand.WcdmaBand band) throws Exception {
        String resp = IATUtils.sendATCmd(engconstents.ENG_AT_W_LOCKED_BAND + band.getValue(), simIdx);
        Log.d(TAG, "isWcdmaBandOpen() resp:" + resp);
        if (resp.contains("OK")) {
            String[] str = resp.split(":|\n|,");
            return str.length >= 3 && str[3].trim().equals("1");
        }
        throw new OperationFailedException("AT failed: " + resp);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IBand
    public List<ITelephonyApi.IBand.CDMA2000Band> getCDMA2000Band(int simIdx) throws Exception {
        List<ITelephonyApi.IBand.CDMA2000Band> bandList = new ArrayList<>();
        for (ITelephonyApi.IBand.CDMA2000Band band : ITelephonyApi.IBand.CDMA2000Band.values()) {
            if (isCDMA2000BandOpen(simIdx, band)) {
                bandList.add(band);
            }
        }
        return bandList;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IBand
    public void setCDMA2000Band(int simIdx, List<ITelephonyApi.IBand.CDMA2000Band> enabledBands, List<ITelephonyApi.IBand.CDMA2000Band> disabledBands) throws Exception {
        if (enabledBands.isEmpty()) {
            throw new OperationFailedException("at least one enabled band is needed");
        }
        for (ITelephonyApi.IBand.CDMA2000Band band : enabledBands) {
            setCDMA2000BandState(simIdx, band, true);
        }
        for (ITelephonyApi.IBand.CDMA2000Band band2 : disabledBands) {
            setCDMA2000BandState(simIdx, band2, false);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IBand
    public boolean isCDMA2000BandOpen(int simIdx, ITelephonyApi.IBand.CDMA2000Band band) throws Exception {
        String resp = IATUtils.sendATCmd(engconstents.ENG_AT_W_LOCKED_BAND + band.getValue(), simIdx);
        Log.d(TAG, "isCDMA2000BandOpen() resp:" + resp);
        if (resp.contains("OK")) {
            String[] str = resp.split(":|\n|,");
            return str.length >= 3 && str[3].trim().equals("1");
        }
        throw new OperationFailedException("AT failed: " + resp);
    }

    private void setCDMA2000BandState(int i, ITelephonyApi.IBand.CDMA2000Band cDMA2000Band, boolean z) throws Exception {
        String strSendATCmd = IATUtils.sendATCmd(String.format("%s%d,%d", engconstents.ENG_AT_W_LOCK_BAND, Integer.valueOf(cDMA2000Band.getValue()), Integer.valueOf(z ? 1 : 0)), i);
        if (!strSendATCmd.contains("OK")) {
            throw new OperationFailedException("AT failed: " + strSendATCmd);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IBand
    public ITelephonyApi.IBand.LteBand getLteBand(int simIdx) throws Exception {
        String resp = IATUtils.sendATCmd(engconstents.ENG_AT_GET_LTE_BAND, simIdx);
        if (!resp.contains("OK")) {
            throw new OperationFailedException("AT returns: " + resp);
        }
        String[] result = resp.split("\n");
        String[] result2 = result[0].split("\\:");
        String[] result3 = result2[1].split(",");
        int tdd = ((Integer.valueOf(result3[0].trim()).intValue() << 16) & SupportMenu.CATEGORY_MASK) + Integer.valueOf(result3[1].trim()).intValue();
        int fdd = ((-65536) & (Integer.valueOf(result3[2].trim()).intValue() << 16)) + Integer.valueOf(result3[3].trim()).intValue();
        ITelephonyApi.IBand.LteBand band = new ITelephonyApi.IBand.LteBand();
        for (int i = 0; i < 32; i++) {
            if (((tdd >> i) & 1) == 1) {
                band.tddBands[i] = 1;
            }
            if (((fdd >> i) & 1) == 1) {
                band.fddBands[i] = 1;
            }
        }
        return band;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IBand
    public void setLteBand(int simIdx, ITelephonyApi.IBand.LteBand bands) throws Exception {
        int tdd = 0;
        int fdd = 0;
        for (int i = 0; i < 32; i++) {
            if (bands.tddBands[i] == 1) {
                tdd |= 1 << i;
            }
            if (bands.fddBands[i] == 1) {
                fdd |= 1 << i;
            }
        }
        int i2 = tdd >> 16;
        int tdd_h = i2 & SupportMenu.USER_MASK;
        int tdd_l = tdd & SupportMenu.USER_MASK;
        int fdd_h = (fdd >> 16) & SupportMenu.USER_MASK;
        int fdd_l = 65535 & fdd;
        String atCmd = String.format("%s%d,%d,%d,%d", engconstents.ENG_AT_SET_LTE_BAND, Integer.valueOf(tdd_h), Integer.valueOf(tdd_l), Integer.valueOf(fdd_h), Integer.valueOf(fdd_l));
        String resp = IATUtils.sendATCmd(atCmd, simIdx);
        if (!resp.contains("OK")) {
            throw new OperationFailedException("AT failed: " + resp);
        }
    }

    private void setWcdmaBandState(int i, ITelephonyApi.IBand.WcdmaBand wcdmaBand, boolean z) throws Exception {
        String strSendATCmd = IATUtils.sendATCmd(String.format("%s%d,%d", engconstents.ENG_AT_W_LOCK_BAND, Integer.valueOf(wcdmaBand.getValue()), Integer.valueOf(z ? 1 : 0)), i);
        if (!strSendATCmd.contains("OK")) {
            throw new OperationFailedException("AT failed: " + strSendATCmd);
        }
    }
}
