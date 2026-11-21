package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.exception.UnsupportedFeatureException;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.HidlUtils;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class ManualAssertImpl implements ITelephonyApi.IManualAssert {
    private static final String AT_OPEN_CP2 = "poweron";
    private static final String PROP_CP2_RESET = "persist.sys.sprd.wcnreset";
    private static final String TAG = "MANUALASSERT";

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IManualAssert
    public void assertModem() {
        IATUtils.sendATCmd(engconstents.ENG_AT_SET_MANUAL_ASSERT, "atchannel0");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IManualAssert
    public boolean isModemResetting() {
        return SystemPropertiesProxy.getBoolean("persist.vendor.sys.sprd.modemreset", false);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IManualAssert
    public boolean isCp2Reset() {
        return SystemPropertiesProxy.getBoolean(PROP_CP2_RESET, false);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IManualAssert
    public void enableCp2Reset() throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set(PROP_CP2_RESET, "1");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IManualAssert
    public void disableCp2Reset() throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set(PROP_CP2_RESET, "0");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IManualAssert
    public void powerOnCp2() {
        if (!canAssertCp2()) {
            throw new UnsupportedFeatureException("not support in user");
        }
        String atResponse = HidlUtils.sendCmd("wcnd wcn poweron");
        if (atResponse == null || !atResponse.contains("OK")) {
            Log.d(TAG, "Power on fail");
            throw new OperationFailedException("power on cp2 failed");
        }
        Log.d(TAG, "Power on OK");
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IManualAssert
    public boolean canResetCp2() {
        return Const.isMarlin() && Const.isUser();
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IManualAssert
    public boolean canAssertCp2() {
        return Const.isMarlin() || !Const.isUser();
    }
}
