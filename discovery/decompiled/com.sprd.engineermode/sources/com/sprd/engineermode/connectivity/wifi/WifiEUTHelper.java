package com.sprd.engineermode.connectivity.wifi;

import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.IConnectivityApi;

/* JADX INFO: loaded from: classes4.dex */
public class WifiEUTHelper {
    public static synchronized IConnectivityApi.IWifiEut getHelper() {
        return CoreApi.getConnectivityApi().wifiEut();
    }
}
