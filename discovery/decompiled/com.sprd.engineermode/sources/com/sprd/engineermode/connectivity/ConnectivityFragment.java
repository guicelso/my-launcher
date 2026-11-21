package com.sprd.engineermode.connectivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.sprd.engineermode.R;
import com.sprd.engineermode.connectivity.BT.BTActivity;
import com.sprd.engineermode.connectivity.fm.FMActivity;
import com.sprd.engineermode.connectivity.wifi.WifiEUTActivity;
import com.sprd.engineermode.connectivity.wifi.WifiTestActivity;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.intf.IConnectivityApi;

/* JADX INFO: loaded from: classes4.dex */
public class ConnectivityFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private static final String KEY_BT = "bt";
    private static final String KEY_CERTIFICATION = "wifi certification";
    private static final String KEY_FM = "fm";
    private static final String KEY_WIFI_EUT = "wifi eut";
    private static final String TAG = "ConnectivityFragment";
    private Preference mBtTest;
    private Preference mFmTest;
    private ProgressDialog mProgress;
    private Button mStartService;
    private Button mStopService;
    private Preference mWifiTest;
    private Context mContext = null;
    private IConnectivityApi.IWcndEngControl wcndEngCtl = CoreApi.getConnectivityApi().wcndEngControl();
    private Handler UIHandler = new Handler();
    private Runnable runnable = new Runnable() { // from class: com.sprd.engineermode.connectivity.ConnectivityFragment.1
        @Override // java.lang.Runnable
        public void run() throws Exception {
            boolean mWcndRunning = true;
            try {
                mWcndRunning = ConnectivityFragment.this.wcndEngCtl.isWcndEngRunning();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!mWcndRunning) {
                ConnectivityFragment.this.UIHandler.postDelayed(this, 100L);
                return;
            }
            if (ConnectivityFragment.this.mProgress != null) {
                ConnectivityFragment.this.mProgress.dismiss();
            }
            if (!Const.isUser()) {
                ConnectivityFragment.this.mFmTest.setEnabled(true);
            }
            ConnectivityFragment.this.mStopService.setEnabled(true);
            ConnectivityFragment.this.mStartService.setEnabled(false);
            ConnectivityFragment.this.UIHandler.postDelayed(this, 2000L);
        }
    };

    @Override // android.app.Fragment
    public void onResume() {
        this.UIHandler.postDelayed(this.runnable, 100L);
        super.onResume();
    }

    @Override // android.app.Fragment
    public void onPause() {
        this.UIHandler.removeCallbacks(this.runnable);
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkServiceStatus() {
        this.UIHandler.postDelayed(this.runnable, 100L);
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.connectivity, container, false);
        this.mStartService = (Button) view.findViewById(R.id.start_service);
        this.mStartService.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.connectivity.ConnectivityFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                try {
                    ConnectivityFragment.this.wcndEngCtl.startWcndEng();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(ConnectivityFragment.TAG, "start wcnd_eng service");
                ConnectivityFragment connectivityFragment = ConnectivityFragment.this;
                connectivityFragment.mProgress = ProgressDialog.show(connectivityFragment.mContext, "Start wcnd_eng service", "Please wait...", true, true);
                ConnectivityFragment.this.checkServiceStatus();
            }
        });
        this.mStopService = (Button) view.findViewById(R.id.stop_service);
        this.mStopService.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.connectivity.ConnectivityFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                try {
                    ConnectivityFragment.this.wcndEngCtl.stopWcndEng();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(ConnectivityFragment.TAG, "stop wcnd_eng service");
                ConnectivityFragment.this.mWifiTest.setEnabled(false);
                ConnectivityFragment.this.mBtTest.setEnabled(false);
                ConnectivityFragment.this.mFmTest.setEnabled(false);
                ConnectivityFragment.this.mStopService.setEnabled(false);
                ConnectivityFragment.this.mStartService.setEnabled(true);
                ConnectivityFragment.this.UIHandler.removeCallbacks(ConnectivityFragment.this.runnable);
            }
        });
        return view;
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_connectivitytab);
        this.mContext = getActivity();
        this.mWifiTest = findPreference(KEY_WIFI_EUT);
        this.mWifiTest.setOnPreferenceClickListener(this);
        this.mBtTest = findPreference(KEY_BT);
        this.mBtTest.setOnPreferenceClickListener(this);
        this.mFmTest = findPreference(KEY_FM);
        this.mFmTest.setOnPreferenceClickListener(this);
        findPreference(KEY_CERTIFICATION).setOnPreferenceClickListener(this);
        if (Const.isUser()) {
            this.mFmTest.setEnabled(false);
            this.mFmTest.setSummary(R.string.feature_not_support_by_user_version);
        }
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onStart() throws Exception {
        super.onStart();
        boolean mWcndRunning = true;
        try {
            mWcndRunning = this.wcndEngCtl.isWcndEngRunning();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mWcndRunning) {
            this.mStartService.setEnabled(false);
        }
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        if (pref.getKey().equals(KEY_WIFI_EUT)) {
            if (!isWifiOn()) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.alert_wifi_test)).setMessage(getString(R.string.alert_wifi)).setCancelable(true).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.-$$Lambda$ConnectivityFragment$PkPHC88B9X_2J0PFBhknwKTUmKo
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this.f$0.lambda$onPreferenceClick$0$ConnectivityFragment(dialogInterface, i);
                    }
                }).create();
                alertDialog.show();
            } else {
                AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.alert_wifi_test)).setMessage(getString(R.string.alert_close_wifi)).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.-$$Lambda$ConnectivityFragment$2euIBWEjKInADVxTnnQizyZgYM4
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ConnectivityFragment.lambda$onPreferenceClick$1(dialogInterface, i);
                    }
                }).create();
                alertDialog2.show();
            }
        } else if (pref.getKey().equals(KEY_BT)) {
            if (!isBTOn()) {
                AlertDialog alertDialog3 = new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.alert_bt_test)).setMessage(getString(R.string.alert_bt)).setCancelable(true).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.-$$Lambda$ConnectivityFragment$qRo9UcxqnHnw3L6j3t-58jIjT1E
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this.f$0.lambda$onPreferenceClick$2$ConnectivityFragment(dialogInterface, i);
                    }
                }).create();
                alertDialog3.show();
            } else {
                AlertDialog alertDialog4 = new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.alert_bt_test)).setMessage(getString(R.string.alert_close_bt)).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.-$$Lambda$ConnectivityFragment$39FdizMykBr0PNTt9omqlPvb19w
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ConnectivityFragment.lambda$onPreferenceClick$3(dialogInterface, i);
                    }
                }).create();
                alertDialog4.show();
            }
        } else if (pref.getKey().equals(KEY_FM)) {
            AlertDialog alertDialog5 = new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.alert_fm_test)).setMessage(getString(R.string.alert_fm)).setCancelable(true).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.-$$Lambda$ConnectivityFragment$zKj4gG6dwY-BjG6Rkt7oHcbYLLk
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$onPreferenceClick$4$ConnectivityFragment(dialogInterface, i);
                }
            }).create();
            alertDialog5.show();
        } else if (pref.getKey().equals(KEY_CERTIFICATION)) {
            if (!isWifiOn()) {
                showWarningDialog(getString(R.string.alert_open_wifi_first));
                return true;
            }
            Intent intent = new Intent();
            intent.setFlags(262144);
            intent.setClass(this.mContext, WifiTestActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public /* synthetic */ void lambda$onPreferenceClick$0$ConnectivityFragment(DialogInterface dialog, int which) {
        Intent intent = new Intent();
        intent.setFlags(262144);
        intent.setClass(this.mContext, WifiEUTActivity.class);
        startActivity(intent);
    }

    static /* synthetic */ void lambda$onPreferenceClick$1(DialogInterface dialog, int which) {
    }

    public /* synthetic */ void lambda$onPreferenceClick$2$ConnectivityFragment(DialogInterface dialog, int which) {
        Intent intent = new Intent();
        intent.setFlags(262144);
        intent.setClass(this.mContext, BTActivity.class);
        startActivity(intent);
    }

    static /* synthetic */ void lambda$onPreferenceClick$3(DialogInterface dialog, int which) {
    }

    public /* synthetic */ void lambda$onPreferenceClick$4$ConnectivityFragment(DialogInterface dialog, int which) {
        Intent intent = new Intent();
        intent.setFlags(262144);
        intent.setClass(this.mContext, FMActivity.class);
        startActivity(intent);
    }

    private boolean isWifiOn() {
        WifiManager wifiManager = (WifiManager) getActivity().getSystemService("wifi");
        if (wifiManager.isWifiEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isBTOn() {
        int btState = BluetoothAdapter.getDefaultAdapter().getState();
        Log.d(TAG, "Connectivity BT Status is " + btState);
        if (btState != 12) {
            return false;
        }
        return true;
    }

    private void showWarningDialog(String msg) {
        new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.alert_wifi_test)).setMessage(msg).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.-$$Lambda$ConnectivityFragment$tR1bXZ577gWQV6ISzdBBPikouO0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ConnectivityFragment.lambda$showWarningDialog$5(dialogInterface, i);
            }
        }).create().show();
    }

    static /* synthetic */ void lambda$showWarningDialog$5(DialogInterface dialog, int which) {
    }
}
