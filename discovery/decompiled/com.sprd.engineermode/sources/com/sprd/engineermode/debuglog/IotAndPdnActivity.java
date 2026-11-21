package com.sprd.engineermode.debuglog;

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

/* JADX INFO: loaded from: classes4.dex */
public class IotAndPdnActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String IOT = "key_iot";
    private static final String PDPONE = "key_pdp_one";
    private static final String PDPTWO = "key_pdp_two";
    private static final String TAG = "IotAndPdnActivity";
    private EMSwitchPreference mPdpOnePreference = null;
    private EMSwitchPreference mPdpTwoPreference = null;
    private TelephonyManager mTm = null;
    private ConnectivityManager mCm = null;
    private ConnectivityManager.NetworkCallback mNetworkCallbackFirst = null;
    private ConnectivityManager.NetworkCallback mNetworkCallbackSecond = null;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        addPreferencesFromResource(R.xml.pref_iot_pdn);
        this.mPdpOnePreference = (EMSwitchPreference) findPreference(PDPONE);
        this.mPdpOnePreference.setOnPreferenceChangeListener(this);
        this.mPdpTwoPreference = (EMSwitchPreference) findPreference(PDPTWO);
        this.mPdpTwoPreference.setOnPreferenceChangeListener(this);
        this.mTm = (TelephonyManager) getSystemService("phone");
        this.mCm = (ConnectivityManager) getSystemService("connectivity");
        init();
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (!(preference instanceof EMSwitchPreference)) {
            return false;
        }
        String key = preference.getKey();
        if (key.equals(PDPTWO)) {
            if (!this.mPdpTwoPreference.isChecked()) {
                Log.d(TAG, "activate NET_CAPABILITY_SUPL pdp");
                NetworkRequest request = buildRequest(1);
                this.mCm.registerNetworkCallback(request, this.mNetworkCallbackFirst);
                this.mCm.requestNetwork(request, this.mNetworkCallbackFirst);
                this.mPdpTwoPreference.setChecked(true);
            } else {
                try {
                    this.mCm.unregisterNetworkCallback(this.mNetworkCallbackFirst);
                    this.mPdpTwoPreference.setChecked(false);
                } catch (IllegalArgumentException iea) {
                    iea.printStackTrace();
                }
            }
        } else if (key.equals(PDPONE)) {
            if (!this.mPdpOnePreference.isChecked()) {
                Log.d(TAG, "activate NET_CAPABILITY_MMS pdp");
                NetworkRequest request2 = buildRequest(0);
                this.mCm.registerNetworkCallback(request2, this.mNetworkCallbackSecond);
                this.mCm.requestNetwork(request2, this.mNetworkCallbackSecond);
                this.mPdpOnePreference.setChecked(true);
            } else {
                try {
                    this.mCm.unregisterNetworkCallback(this.mNetworkCallbackSecond);
                    this.mPdpOnePreference.setChecked(false);
                } catch (IllegalArgumentException iea2) {
                    iea2.printStackTrace();
                }
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
        this.mNetworkCallbackSecond = new ConnectivityManager.NetworkCallback() { // from class: com.sprd.engineermode.debuglog.IotAndPdnActivity.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                super.onAvailable(network);
                Log.d(IotAndPdnActivity.TAG, "network2 onAvailable");
                Toast.makeText(IotAndPdnActivity.this, "success", 0).show();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                super.onLost(network);
                Log.d(IotAndPdnActivity.TAG, "network2 onLost");
            }
        };
        this.mNetworkCallbackFirst = new ConnectivityManager.NetworkCallback() { // from class: com.sprd.engineermode.debuglog.IotAndPdnActivity.2
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                super.onAvailable(network);
                Log.d(IotAndPdnActivity.TAG, "network1 onAvailable");
                Toast.makeText(IotAndPdnActivity.this, "success", 0).show();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                super.onLost(network);
                Log.d(IotAndPdnActivity.TAG, "network1 onLost");
            }
        };
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }
}
