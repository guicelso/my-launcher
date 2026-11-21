package com.sprd.engineermode.telephony;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.HandlerThread;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class IotAndPdpActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String IOT = "key_iot";
    private static final String PDPONE = "key_pdp_one";
    private static final String PDPTWO = "key_pdp_two";
    private static final String PDP_NORETRY = "key_pdp_noretry";
    private static final String REUSE_DEFAULT_PDN = "key_reuse_default_pdn";
    private static final String TAG = "IotAndPdpActivity";
    private EMSwitchPreference mReusePdnPreference = null;
    private EMSwitchPreference mIotPreference = null;
    private EMSwitchPreference mPdpOnePreference = null;
    private EMSwitchPreference mPdpTwoPreference = null;
    private EMSwitchPreference mIotNoRetryPreference = null;
    private TelephonyManager mTm = null;
    private ConnectivityManager mCm = null;
    private ConnectivityManager.NetworkCallback mNetworkCallbackFirst = null;
    private ConnectivityManager.NetworkCallback mNetworkCallbackSecond = null;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        addPreferencesFromResource(R.xml.pref_iot_pdp);
        this.mIotPreference = (EMSwitchPreference) findPreference(IOT);
        this.mIotPreference.setOnPreferenceChangeListener(this);
        this.mPdpOnePreference = (EMSwitchPreference) findPreference(PDPONE);
        this.mPdpOnePreference.setOnPreferenceChangeListener(this);
        this.mPdpTwoPreference = (EMSwitchPreference) findPreference(PDPTWO);
        this.mPdpTwoPreference.setOnPreferenceChangeListener(this);
        this.mIotNoRetryPreference = (EMSwitchPreference) findPreference(PDP_NORETRY);
        this.mIotNoRetryPreference.setOnPreferenceChangeListener(this);
        this.mReusePdnPreference = (EMSwitchPreference) findPreference(REUSE_DEFAULT_PDN);
        this.mReusePdnPreference.setOnPreferenceChangeListener(this);
        this.mTm = (TelephonyManager) getSystemService("phone");
        this.mCm = (ConnectivityManager) getSystemService("connectivity");
        init();
    }

    @Override // android.app.Activity
    public void onStart() throws IllegalAccessException, InvocationTargetException {
        super.onStart();
        if (this.mIotPreference != null) {
            boolean isIot = SystemPropertiesProxy.getBoolean("persist.sys.volte.iot", false);
            Log.d(TAG, "Iot: " + isIot);
            if (isIot) {
                this.mIotPreference.setChecked(true);
            } else {
                this.mIotPreference.setChecked(false);
            }
        }
        if (this.mIotNoRetryPreference != null) {
            boolean isNoretry = SystemPropertiesProxy.getBoolean("persist.sys.pdp.noretry", false);
            Log.d(TAG, "isNoretry: " + isNoretry);
            if (isNoretry) {
                this.mIotNoRetryPreference.setChecked(true);
            } else {
                this.mIotNoRetryPreference.setChecked(false);
            }
        }
        if (this.mReusePdnPreference != null) {
            boolean ReusePdn = SystemPropertiesProxy.getBoolean("persist.vendor.sys.pdp.reuse", false);
            Log.d(TAG, "ReusePdn: " + ReusePdn);
            if (ReusePdn) {
                this.mReusePdnPreference.setChecked(true);
            } else {
                this.mReusePdnPreference.setChecked(false);
            }
        }
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) throws IllegalAccessException, InvocationTargetException {
        if (!(preference instanceof EMSwitchPreference)) {
            return false;
        }
        String key = preference.getKey();
        if (key.equals(IOT)) {
            if (!this.mIotPreference.isChecked()) {
                Log.d(TAG, "Open Iot Switch");
                SystemPropertiesProxy.set("persist.sys.volte.iot", "1");
                this.mIotPreference.setChecked(true);
            } else {
                Log.d(TAG, "Close Iot Switch");
                SystemPropertiesProxy.set("persist.sys.volte.iot", "0");
                this.mIotPreference.setChecked(false);
            }
        } else if (key.equals(PDP_NORETRY)) {
            if (!this.mIotNoRetryPreference.isChecked()) {
                Log.d(TAG, "Open pdp noretry Switch");
                SystemPropertiesProxy.set("persist.sys.pdp.noretry", "1");
                this.mIotNoRetryPreference.setChecked(true);
            } else {
                Log.d(TAG, "Close pdp noretry Switch");
                SystemPropertiesProxy.set("persist.sys.pdp.noretry", "0");
                this.mIotNoRetryPreference.setChecked(false);
            }
        } else if (key.equals(PDPONE)) {
            if (!this.mPdpOnePreference.isChecked()) {
                Log.d(TAG, "activate 1 pdp");
                NetworkRequest request = buildRequest(1);
                this.mCm.requestNetwork(request, this.mNetworkCallbackFirst);
                this.mPdpOnePreference.setChecked(true);
            } else {
                try {
                    this.mCm.unregisterNetworkCallback(this.mNetworkCallbackFirst);
                    this.mPdpTwoPreference.setChecked(false);
                } catch (IllegalArgumentException iea) {
                    iea.printStackTrace();
                }
            }
        } else if (key.equals(PDPTWO)) {
            if (!this.mPdpTwoPreference.isChecked()) {
                Log.d(TAG, "activate 2 pdp");
                NetworkRequest request2 = buildRequest(0);
                this.mCm.requestNetwork(request2, this.mNetworkCallbackSecond);
                this.mPdpTwoPreference.setChecked(true);
            } else {
                try {
                    this.mCm.unregisterNetworkCallback(this.mNetworkCallbackSecond);
                    this.mPdpTwoPreference.setChecked(false);
                } catch (IllegalArgumentException iea2) {
                    iea2.printStackTrace();
                }
            }
        } else if (key.equals(REUSE_DEFAULT_PDN)) {
            if (!this.mReusePdnPreference.isChecked()) {
                Log.d(TAG, "Open reuse default pdn Switch");
                SystemPropertiesProxy.set("persist.vendor.sys.pdp.reuse", "1");
                this.mReusePdnPreference.setChecked(true);
            } else {
                Log.d(TAG, "Close reuse default pdn Switch");
                SystemPropertiesProxy.set("persist.vendor.sys.pdp.reuse", "0");
                this.mReusePdnPreference.setChecked(false);
            }
        }
        return true;
    }

    private NetworkRequest buildRequest(int type) {
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addCapability(type);
        builder.addTransportType(0);
        builder.setNetworkSpecifier("");
        NetworkRequest request = builder.build();
        return request;
    }

    private void init() {
        this.mNetworkCallbackSecond = new ConnectivityManager.NetworkCallback() { // from class: com.sprd.engineermode.telephony.IotAndPdpActivity.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                super.onAvailable(network);
                Log.d(IotAndPdpActivity.TAG, "network2 onAvailable");
                Toast.makeText(IotAndPdpActivity.this, "success", 0).show();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                super.onLost(network);
                Log.d(IotAndPdpActivity.TAG, "network2 onLost");
            }
        };
        this.mNetworkCallbackFirst = new ConnectivityManager.NetworkCallback() { // from class: com.sprd.engineermode.telephony.IotAndPdpActivity.2
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                super.onAvailable(network);
                Log.d(IotAndPdpActivity.TAG, "network1 onAvailable");
                Toast.makeText(IotAndPdpActivity.this, "success", 0).show();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                super.onLost(network);
                Log.d(IotAndPdpActivity.TAG, "network1 onLost");
            }
        };
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }
}
