package com.sprd.engineermode.debuglog;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class CommunityCapActivity extends PreferenceActivity {
    private static final int GET_STATUS = 1;
    private static final String KEY_EDGE = "edge";
    private static final String KEY_HSDPA = "hsdpa";
    private static final String KEY_HSUPA = "hsupa";
    private static final String KEY_MBMS = "mbms";
    private static final String TAG = "CommunityCapActivity";
    private String mATResponse;
    private CCAPHandler mCCAPHandler;
    private Preference mEDGE;
    private Preference mHSDPA;
    private Preference mHSUPA;
    private Preference mMBMS;
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_community_cap);
        this.mMBMS = findPreference(KEY_MBMS);
        this.mHSDPA = findPreference(KEY_HSDPA);
        this.mHSUPA = findPreference(KEY_HSUPA);
        this.mEDGE = findPreference(KEY_EDGE);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mCCAPHandler = new CCAPHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    protected void onStart() {
        Message mStatus = this.mCCAPHandler.obtainMessage(1);
        this.mCCAPHandler.sendMessage(mStatus);
        super.onStart();
    }

    class CCAPHandler extends Handler {
        public CCAPHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String responValue = IATUtils.sendATCmd("AT+SPTEST?", "atchannel0");
                Log.d(CommunityCapActivity.TAG, "GET_STATUS is " + responValue);
                if (responValue != null && responValue.contains("OK")) {
                    String[] str = responValue.split("\\+");
                    String[] str1 = str[8].split("\\:");
                    String[] str2 = str1[1].split("\\,");
                    CommunityCapActivity.this.mATResponse = str2[1].trim();
                    if (CommunityCapActivity.this.mATResponse.equals("1")) {
                        CommunityCapActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.CommunityCapActivity.CCAPHandler.1
                            @Override // java.lang.Runnable
                            public void run() {
                                CommunityCapActivity.this.mMBMS.setSummary(R.string.support);
                            }
                        });
                        return;
                    }
                    if (CommunityCapActivity.this.mATResponse.equals("2")) {
                        CommunityCapActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.CommunityCapActivity.CCAPHandler.2
                            @Override // java.lang.Runnable
                            public void run() {
                                CommunityCapActivity.this.mHSDPA.setSummary(R.string.support);
                            }
                        });
                    } else if (CommunityCapActivity.this.mATResponse.equals("4")) {
                        CommunityCapActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.CommunityCapActivity.CCAPHandler.3
                            @Override // java.lang.Runnable
                            public void run() {
                                CommunityCapActivity.this.mHSUPA.setSummary(R.string.support);
                            }
                        });
                    } else if (CommunityCapActivity.this.mATResponse.equals("8")) {
                        CommunityCapActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.CommunityCapActivity.CCAPHandler.4
                            @Override // java.lang.Runnable
                            public void run() {
                                CommunityCapActivity.this.mEDGE.setSummary(R.string.support);
                            }
                        });
                    }
                }
            }
        }
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        if (this.mCCAPHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mCCAPHandler.getLooper().quit();
        }
        super.onDestroy();
    }
}
