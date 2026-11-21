package com.sprd.engineermode.telephony;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.preference.SwitchPreference;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class Csfb2GsmDelayActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final int KEY_GET_CSFB2GSM_DELAY = 0;
    private static final int KEY_GRRC_RANDOM_CLOSE = 4;
    private static final int KEY_GRRC_RANDOM_OPEN = 3;
    private static final String KEY_GRRC_RANDOM_STATE = "GrrcRandomAccessState";
    private static final int KEY_GRRC_RESIDENT_CLOSE = 2;
    private static final int KEY_GRRC_RESIDENT_OPEN = 1;
    private static final String KEY_GRRC_RESIDENT_STATE = "GrrcResidentState";
    private static final String TAG = "Csfb2GsmDelayActivity";
    private Csfb2GsmDelayHandler mCsfb2GsmDelayHandler;
    private int mPhoneCount;
    private PreferenceCategory mPreferenceCategory;
    private TwoStatePreference[] mRandomState;
    private TwoStatePreference[] mResidentState;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    PreferenceGroup mPreGroup = null;
    private int mSIM = 0;
    private String mResult = null;
    private String mServerName = "atchannel";
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        int i = this.mPhoneCount;
        this.mResidentState = new TwoStatePreference[i];
        this.mRandomState = new TwoStatePreference[i];
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        this.mPreGroup = getPreferenceScreen();
        for (int i2 = 0; i2 < this.mPhoneCount; i2++) {
            this.mPreferenceCategory = new PreferenceCategory(this);
            this.mPreferenceCategory.setTitle("SIM" + i2);
            this.mPreGroup.addPreference(this.mPreferenceCategory);
            this.mResidentState[i2] = new SwitchPreference(this);
            this.mResidentState[i2].setKey(KEY_GRRC_RESIDENT_STATE + i2);
            this.mResidentState[i2].setTitle(R.string.grrc_resident_state);
            this.mResidentState[i2].setChecked(false);
            this.mResidentState[i2].setOnPreferenceChangeListener(this);
            this.mRandomState[i2] = new SwitchPreference(this);
            this.mRandomState[i2].setKey(KEY_GRRC_RANDOM_STATE + i2);
            this.mRandomState[i2].setTitle(R.string.grrc_random_access_state);
            this.mRandomState[i2].setChecked(false);
            this.mRandomState[i2].setOnPreferenceChangeListener(this);
            this.mPreGroup.addPreference(this.mResidentState[i2]);
            this.mPreGroup.addPreference(this.mRandomState[i2]);
        }
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mCsfb2GsmDelayHandler = new Csfb2GsmDelayHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        Message getCsfb2gsm_delay = this.mCsfb2GsmDelayHandler.obtainMessage(0);
        this.mCsfb2GsmDelayHandler.sendMessage(getCsfb2gsm_delay);
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        if (this.mCsfb2GsmDelayHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mCsfb2GsmDelayHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        for (int i = 0; i < this.mPhoneCount; i++) {
            TwoStatePreference[] twoStatePreferenceArr = this.mResidentState;
            if (pref == twoStatePreferenceArr[i]) {
                if (!twoStatePreferenceArr[i].isChecked()) {
                    Message GrrcResidentOpen = this.mCsfb2GsmDelayHandler.obtainMessage(1, Integer.valueOf(i));
                    this.mCsfb2GsmDelayHandler.sendMessage(GrrcResidentOpen);
                } else {
                    Message GrrcResidentClose = this.mCsfb2GsmDelayHandler.obtainMessage(2, Integer.valueOf(i));
                    this.mCsfb2GsmDelayHandler.sendMessage(GrrcResidentClose);
                }
            } else {
                TwoStatePreference[] twoStatePreferenceArr2 = this.mRandomState;
                if (pref == twoStatePreferenceArr2[i]) {
                    if (!twoStatePreferenceArr2[i].isChecked()) {
                        Message GrrcRandomOpen = this.mCsfb2GsmDelayHandler.obtainMessage(3, Integer.valueOf(i));
                        this.mCsfb2GsmDelayHandler.sendMessage(GrrcRandomOpen);
                    } else {
                        Message GrrcRandomClose = this.mCsfb2GsmDelayHandler.obtainMessage(4, Integer.valueOf(i));
                        this.mCsfb2GsmDelayHandler.sendMessage(GrrcRandomClose);
                    }
                }
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getStatus() {
        for (int i = 0; i < this.mPhoneCount; i++) {
            this.mSIM = i;
            final int simIdx = i;
            int[] status = {0, 0};
            if (this.teleApi.csfb2GsmDealyApi().getStatus(i, status)) {
                if (status[0] == 1) {
                    this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.Csfb2GsmDelayActivity.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Csfb2GsmDelayActivity.this.mResidentState[simIdx].setChecked(true);
                            Csfb2GsmDelayActivity.this.mResidentState[simIdx].setEnabled(true);
                            Csfb2GsmDelayActivity.this.mRandomState[simIdx].setEnabled(false);
                            Csfb2GsmDelayActivity.this.mRandomState[simIdx].setChecked(false);
                        }
                    });
                } else if (status[1] == 1) {
                    this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.Csfb2GsmDelayActivity.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Csfb2GsmDelayActivity.this.mResidentState[simIdx].setChecked(false);
                            Csfb2GsmDelayActivity.this.mResidentState[simIdx].setEnabled(false);
                            Csfb2GsmDelayActivity.this.mRandomState[simIdx].setEnabled(true);
                            Csfb2GsmDelayActivity.this.mRandomState[simIdx].setChecked(true);
                        }
                    });
                } else {
                    this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.Csfb2GsmDelayActivity.3
                        @Override // java.lang.Runnable
                        public void run() {
                            Csfb2GsmDelayActivity.this.mResidentState[simIdx].setChecked(false);
                            Csfb2GsmDelayActivity.this.mResidentState[simIdx].setEnabled(true);
                            Csfb2GsmDelayActivity.this.mRandomState[simIdx].setChecked(false);
                            Csfb2GsmDelayActivity.this.mRandomState[simIdx].setEnabled(true);
                        }
                    });
                }
            } else {
                this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.Csfb2GsmDelayActivity.4
                    @Override // java.lang.Runnable
                    public void run() {
                        Csfb2GsmDelayActivity.this.mResidentState[simIdx].setChecked(false);
                        Csfb2GsmDelayActivity.this.mResidentState[simIdx].setEnabled(false);
                        Csfb2GsmDelayActivity.this.mRandomState[simIdx].setEnabled(false);
                        Csfb2GsmDelayActivity.this.mRandomState[simIdx].setChecked(false);
                        Csfb2GsmDelayActivity.this.mResidentState[simIdx].setSummary(R.string.feature_abnormal);
                        Csfb2GsmDelayActivity.this.mRandomState[simIdx].setSummary(R.string.feature_abnormal);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openGrrcResident(final int simIdx) {
        if (this.teleApi.csfb2GsmDealyApi().openGrrcResident(simIdx)) {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.Csfb2GsmDelayActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    Csfb2GsmDelayActivity.this.mResidentState[simIdx].setChecked(true);
                    Csfb2GsmDelayActivity.this.mRandomState[simIdx].setEnabled(false);
                    Toast.makeText(Csfb2GsmDelayActivity.this, "Success", 0).show();
                }
            });
        } else {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.Csfb2GsmDelayActivity.6
                @Override // java.lang.Runnable
                public void run() {
                    Csfb2GsmDelayActivity.this.mResidentState[simIdx].setChecked(false);
                    Toast.makeText(Csfb2GsmDelayActivity.this, "Fail", 0).show();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeGrrcResident(final int simIdx) {
        if (this.teleApi.csfb2GsmDealyApi().closeGrrcResident(simIdx)) {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.Csfb2GsmDelayActivity.7
                @Override // java.lang.Runnable
                public void run() {
                    Csfb2GsmDelayActivity.this.mResidentState[simIdx].setChecked(false);
                    Csfb2GsmDelayActivity.this.mRandomState[simIdx].setEnabled(true);
                    Toast.makeText(Csfb2GsmDelayActivity.this, "Success", 0).show();
                }
            });
        } else {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.Csfb2GsmDelayActivity.8
                @Override // java.lang.Runnable
                public void run() {
                    Csfb2GsmDelayActivity.this.mResidentState[simIdx].setChecked(true);
                    Toast.makeText(Csfb2GsmDelayActivity.this, "Fail", 0).show();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openGrrcRandomAccess(final int simIdx) {
        if (this.teleApi.csfb2GsmDealyApi().openGrrcRandomAccess(simIdx)) {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.Csfb2GsmDelayActivity.9
                @Override // java.lang.Runnable
                public void run() {
                    Csfb2GsmDelayActivity.this.mRandomState[simIdx].setChecked(true);
                    Csfb2GsmDelayActivity.this.mResidentState[simIdx].setEnabled(false);
                    Toast.makeText(Csfb2GsmDelayActivity.this, "Success", 0).show();
                }
            });
        } else {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.Csfb2GsmDelayActivity.10
                @Override // java.lang.Runnable
                public void run() {
                    Csfb2GsmDelayActivity.this.mRandomState[simIdx].setChecked(false);
                    Toast.makeText(Csfb2GsmDelayActivity.this, "Fail", 0).show();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeGrrcRandomAccess(final int simIdx) {
        if (this.teleApi.csfb2GsmDealyApi().closeGrrcRandomAccess(simIdx)) {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.Csfb2GsmDelayActivity.11
                @Override // java.lang.Runnable
                public void run() {
                    Csfb2GsmDelayActivity.this.mRandomState[simIdx].setChecked(false);
                    Csfb2GsmDelayActivity.this.mResidentState[simIdx].setEnabled(true);
                    Toast.makeText(Csfb2GsmDelayActivity.this, "Success", 0).show();
                }
            });
        } else {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.Csfb2GsmDelayActivity.12
                @Override // java.lang.Runnable
                public void run() {
                    Csfb2GsmDelayActivity.this.mRandomState[simIdx].setChecked(true);
                    Toast.makeText(Csfb2GsmDelayActivity.this, "Fail", 0).show();
                }
            });
        }
    }

    class Csfb2GsmDelayHandler extends Handler {
        Csfb2GsmDelayHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                Csfb2GsmDelayActivity.this.getStatus();
                return;
            }
            if (i == 1) {
                Csfb2GsmDelayActivity.this.openGrrcResident(((Integer) msg.obj).intValue());
                return;
            }
            if (i == 2) {
                Csfb2GsmDelayActivity.this.closeGrrcResident(((Integer) msg.obj).intValue());
            } else if (i == 3) {
                Csfb2GsmDelayActivity.this.openGrrcRandomAccess(((Integer) msg.obj).intValue());
            } else if (i == 4) {
                Csfb2GsmDelayActivity.this.closeGrrcRandomAccess(((Integer) msg.obj).intValue());
            }
        }
    }
}
