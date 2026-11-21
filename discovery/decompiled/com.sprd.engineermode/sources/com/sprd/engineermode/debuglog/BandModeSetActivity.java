package com.sprd.engineermode.debuglog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.sprd.engineermode.telephony.TelephonyFragment;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class BandModeSetActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {
    public static BandModeSetActivity BandModeSetActivityInstance = null;
    private static final int KEY_SAVE_BAND = 1;
    private static final String TAG = "BandModeSetActivity";
    private BandSelector mBandSelector;
    private Context mContext;
    private FBHandler mFBHandler;
    private boolean[] mIsCardExit;
    private int mPhoneCount;
    private ProgressDialog mProgressDlg;
    private TelephonyManager mTelephonyManager;
    private PreferenceGroup mPreGroup = null;
    private int mPhoneID = -1;
    private Handler mUiThread = new Handler();
    private Runnable mSuccessRunnable = new Runnable() { // from class: com.sprd.engineermode.debuglog.BandModeSetActivity.1
        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(BandModeSetActivity.this.getApplicationContext(), "Modem reset success!", 0).show();
        }
    };
    private Runnable mFailRunnable = new Runnable() { // from class: com.sprd.engineermode.debuglog.BandModeSetActivity.2
        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(BandModeSetActivity.this.getApplicationContext(), "Modem reset failed!", 0).show();
        }
    };

    class FBHandler extends Handler {
        public FBHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Log.d(BandModeSetActivity.TAG, " handleMessage:" + msg.what);
            if (msg.what == 1) {
                BandModeSetActivity.this.showProgressDialog("Saving band");
                BandModeSetActivity.this.mBandSelector.saveBand();
                BandModeSetActivity.this.dismissProgressDialog();
                AlertDialog alertDialog = new AlertDialog.Builder(BandModeSetActivity.this).setTitle("Band Select").setMessage(BandModeSetActivity.this.mBandSelector.getSetInfo()).setPositiveButton(R.string.alertdialog_ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.BandModeSetActivity.FBHandler.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() { // from class: com.sprd.engineermode.debuglog.BandModeSetActivity.FBHandler.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (BandModeSetActivity.this.mPhoneCount == 2) {
                                    if (!BandModeSetActivity.this.mIsCardExit[0] || !BandModeSetActivity.this.mIsCardExit[1]) {
                                        if (!BandModeSetActivity.this.mIsCardExit[0]) {
                                            if (BandModeSetActivity.this.mIsCardExit[1]) {
                                                String response1 = IATUtils.sendATCmd("AT+RESET=0", "atchannel1");
                                                Log.d(BandModeSetActivity.TAG, " response1:" + response1);
                                                if (response1 == null || !response1.contains("OK")) {
                                                    BandModeSetActivity.this.mUiThread.postDelayed(BandModeSetActivity.this.mFailRunnable, 0L);
                                                    return;
                                                } else {
                                                    BandModeSetActivity.this.mUiThread.postDelayed(BandModeSetActivity.this.mSuccessRunnable, 0L);
                                                    return;
                                                }
                                            }
                                            Log.d(BandModeSetActivity.TAG, "mIsCardExit[0]==false && mIsCardExit[1]==false");
                                            return;
                                        }
                                        String response0 = IATUtils.sendATCmd("AT+RESET=0", "atchannel0");
                                        Log.d(BandModeSetActivity.TAG, " response0:" + response0);
                                        if (response0 == null || !response0.contains("OK")) {
                                            BandModeSetActivity.this.mUiThread.postDelayed(BandModeSetActivity.this.mFailRunnable, 0L);
                                            return;
                                        } else {
                                            BandModeSetActivity.this.mUiThread.postDelayed(BandModeSetActivity.this.mSuccessRunnable, 0L);
                                            return;
                                        }
                                    }
                                    String response2 = IATUtils.sendATCmd("AT+RESET=0", "atchannel0");
                                    String response3 = IATUtils.sendATCmd("AT+RESET=0", "atchannel1");
                                    Log.d(BandModeSetActivity.TAG, " response0:" + response2 + "--response1:" + response3);
                                    if (response2 == null || !response2.contains("OK") || response3 == null || !response3.contains("OK")) {
                                        BandModeSetActivity.this.mUiThread.postDelayed(BandModeSetActivity.this.mFailRunnable, 0L);
                                        return;
                                    } else {
                                        BandModeSetActivity.this.mUiThread.postDelayed(BandModeSetActivity.this.mSuccessRunnable, 0L);
                                        return;
                                    }
                                }
                                if (BandModeSetActivity.this.mPhoneCount == 1) {
                                    if (BandModeSetActivity.this.mIsCardExit[0]) {
                                        String response4 = IATUtils.sendATCmd("AT+RESET=0", "atchannel0");
                                        Log.d(BandModeSetActivity.TAG, " response0:" + response4);
                                        if (response4 == null || !response4.contains("OK")) {
                                            BandModeSetActivity.this.mUiThread.postDelayed(BandModeSetActivity.this.mFailRunnable, 0L);
                                            return;
                                        } else {
                                            BandModeSetActivity.this.mUiThread.postDelayed(BandModeSetActivity.this.mSuccessRunnable, 0L);
                                            return;
                                        }
                                    }
                                    Log.d(BandModeSetActivity.TAG, "mIsCardExit[0] == false");
                                }
                            }
                        }).start();
                    }
                }).create();
                alertDialog.show();
            }
        }
    }

    private void getCardExitState() {
        this.mTelephonyManager = TelephonyManagerProxy.getService();
        for (int i = 0; i < this.mPhoneCount; i++) {
            TelephonyManager telephonyManager = this.mTelephonyManager;
            if (telephonyManager != null && telephonyManager.getSimState(i) == 5) {
                this.mIsCardExit[i] = true;
            } else {
                this.mIsCardExit[i] = false;
            }
            Log.d(TAG, "mIsCardExit[" + i + "] = " + this.mIsCardExit[i]);
        }
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mFBHandler = new FBHandler(ht.getLooper());
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        this.mPreGroup = getPreferenceScreen();
        this.mPhoneID = getIntent().getIntExtra(TelephonyFragment.KEY_PHONEID, 0);
        Log.d(TAG, "onCreate mPhoneID:" + this.mPhoneID);
        this.mBandSelector = new BandSelector(this.mPhoneID, this, this.mUiThread);
        BandModeSetActivityInstance = this;
        this.mContext = getApplicationContext();
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        this.mIsCardExit = new boolean[this.mPhoneCount];
        getCardExitState();
    }

    @Override // android.app.Activity
    protected void onStart() {
        this.mBandSelector.initModes(this.mPreGroup);
        this.mBandSelector.loadBands();
        super.onStart();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        FBHandler fBHandler = this.mFBHandler;
        if (fBHandler != null) {
            fBHandler.getLooper().quit();
        }
        BandModeSetActivityInstance = null;
        super.onDestroy();
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.frequency_set, menu);
        MenuItem item = menu.findItem(R.id.frequency_set);
        if (item != null) {
            item.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.frequency_set) {
            if (!this.mBandSelector.isCheckOneOrMore()) {
                Toast.makeText(getApplicationContext(), "Please check at least one every mode!", 0).show();
            } else {
                this.mFBHandler.sendEmptyMessage(1);
            }
        } else {
            Log.i(TAG, "default");
        }
        return super.onOptionsItemSelected(item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showProgressDialog(final String msg) {
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.BandModeSetActivity.3
            @Override // java.lang.Runnable
            public void run() {
                BandModeSetActivity bandModeSetActivity = BandModeSetActivity.this;
                bandModeSetActivity.mProgressDlg = ProgressDialog.show(bandModeSetActivity, msg, "Please wait...", true, false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissProgressDialog() {
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.BandModeSetActivity.4
            @Override // java.lang.Runnable
            public void run() {
                if (BandModeSetActivity.this.mProgressDlg != null) {
                    BandModeSetActivity.this.mProgressDlg.dismiss();
                }
            }
        });
    }
}
