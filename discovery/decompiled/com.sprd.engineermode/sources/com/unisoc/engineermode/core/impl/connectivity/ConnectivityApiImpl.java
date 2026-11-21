package com.unisoc.engineermode.core.impl.connectivity;

import com.unisoc.engineermode.core.intf.IConnectivityApi;

/* JADX INFO: loaded from: classes4.dex */
public class ConnectivityApiImpl implements IConnectivityApi {

    private static class ConnectivityApiImplHolder {
        static final IConnectivityApi INSTANCE = new ConnectivityApiImpl();

        private ConnectivityApiImplHolder() {
        }
    }

    public static IConnectivityApi getInstance() {
        return ConnectivityApiImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi
    public IConnectivityApi.IWifiEut wifiEut() {
        return WifiEUTHelperMarlin.WifiEutMarlinImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi
    public IConnectivityApi.IBtEut btEut() {
        return BTHelper.BtHelperHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.IConnectivityApi
    public IConnectivityApi.IWcndEngControl wcndEngControl() {
        return WcndEngControlImpl.WcndEngControlImplHolder.INSTANCE;
    }
}
