package com.unisoc.engineermode.core.impl.debuglog;

import android.provider.Settings;
import com.unisoc.engineermode.core.common.CommonKt;
import com.unisoc.engineermode.core.intf.IGcfTest;

/* JADX INFO: loaded from: classes4.dex */
public class GcfTestImpl implements IGcfTest {
    private static final String MAX_SEND_RETRIES = "3";
    private static final String MIN_SEND_RETRIES = "0";
    private static final String SEND_RETRIE_TIME = "message_send_retries";

    @Override // com.unisoc.engineermode.core.intf.IGcfTest
    public boolean isGcfTestOpened() {
        String retryKey = Settings.Global.getString(CommonKt.appCtx.getContentResolver(), SEND_RETRIE_TIME);
        return retryKey != null && retryKey.equals(MIN_SEND_RETRIES);
    }

    @Override // com.unisoc.engineermode.core.intf.IGcfTest
    public void openGcfTest() {
        Settings.Global.putString(CommonKt.appCtx.getContentResolver(), SEND_RETRIE_TIME, MIN_SEND_RETRIES);
    }

    @Override // com.unisoc.engineermode.core.intf.IGcfTest
    public void closeGcfTest() {
        Settings.Global.putString(CommonKt.appCtx.getContentResolver(), SEND_RETRIE_TIME, MAX_SEND_RETRIES);
    }
}
