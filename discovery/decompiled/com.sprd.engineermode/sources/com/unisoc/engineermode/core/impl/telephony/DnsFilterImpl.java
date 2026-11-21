package com.unisoc.engineermode.core.impl.telephony;

import android.os.INetworkAdapterService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class DnsFilterImpl implements ITelephonyApi.IDnsFilter {
    private static final String NETWORKADAPTER_SERVICE = "network_adapter";
    private static final String TAG = "DnsFilterImpl";

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IDnsFilter
    public void set(int type) {
        INetworkAdapterService networkAdapterObject = INetworkAdapterService.Stub.asInterface(ServiceManager.getService(NETWORKADAPTER_SERVICE));
        try {
            networkAdapterObject.setDnsFilterEnable(type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "DnsFilter set ok, " + type);
    }
}
