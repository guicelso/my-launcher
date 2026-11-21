package com.unisoc.engineermode.core.impl.connectivity;

import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.IConnectivityApi;

/* JADX INFO: loaded from: classes4.dex */
class WcndEngControlImpl implements IConnectivityApi.IWcndEngControl {
    private static final String TAG = "EM-WcndEngControlImpl";
    private static final String WCND_ENG_ENABLE = "persist.vendor.wcnd.eng.enable";

    public static class WcndEngControlImplHolder {
        public static IConnectivityApi.IWcndEngControl INSTANCE = new WcndEngControlImpl();
    }

    WcndEngControlImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWcndEngControl
    public boolean isWcndEngRunning() throws Exception {
        return SystemPropertiesProxy.get(WCND_ENG_ENABLE).equals("1");
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWcndEngControl
    public void startWcndEng() throws Exception {
        SystemPropertiesProxy.set(WCND_ENG_ENABLE, "1");
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi.IWcndEngControl
    public void stopWcndEng() throws Exception {
        SystemPropertiesProxy.set(WCND_ENG_ENABLE, "0");
    }
}
