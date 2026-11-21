package com.sprd.engineermode.telephony;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.text.InputFilter;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class AOCSettingsPrefActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final int AOCSET = 3;
    private static final int GETAOCACTIVE = 1;
    private static final String KEY_AOCACT = "aoc_active";
    private static final String KEY_AOCSET = "aoc_setting";
    private static final String MAX_NUM = "AOC MAX";
    private static final String PIN2 = "2345";
    private static final int SETAOCACTIVE = 2;
    private static final int STATUS_OFF = 0;
    private static final int STATUS_ON = 1;
    private static final String TAG = "AOCSettingsPrefActivity";
    private static String mMaxNum;
    private AOCSettingHandler aocSettingHandler;
    private String mATResponse;
    private String mAnalysisResponse;
    private EMSwitchPreference mPreference01;
    private EditTextPreference mPreference02;
    private int mSetNum;
    private int mSimIndex;
    private Handler mUiThread = new Handler();
    private NumberKeyListener numberKeyListener = new NumberKeyListener() { // from class: com.sprd.engineermode.telephony.AOCSettingsPrefActivity.1
        private char[] numberChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        @Override // android.text.method.KeyListener
        public int getInputType() {
            return 1;
        }

        @Override // android.text.method.NumberKeyListener
        protected char[] getAcceptedChars() {
            return this.numberChars;
        }
    };
    private int state;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_aocsettings);
        this.mSimIndex = getIntent().getIntExtra(SimSelectHelperActivity.INTENT_SIM_INDEX, 0);
        this.mPreference01 = (EMSwitchPreference) findPreference(KEY_AOCACT);
        this.mPreference01.setOnPreferenceChangeListener(this);
        this.mPreference02 = (EditTextPreference) findPreference(KEY_AOCSET);
        this.mPreference02.setOnPreferenceChangeListener(this);
        this.mPreference02.getEditText().setKeyListener(this.numberKeyListener);
        if (savedInstanceState != null) {
            mMaxNum = savedInstanceState.getString(MAX_NUM, null);
        }
        this.mPreference02.setSummary(mMaxNum);
        InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(6);
        InputFilter[] inputFilter = {lengthFilter};
        this.mPreference02.getEditText().setFilters(inputFilter);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.aocSettingHandler = new AOCSettingHandler(ht.getLooper());
        Message aocActiveMessage = this.aocSettingHandler.obtainMessage(1);
        this.aocSettingHandler.sendMessage(aocActiveMessage);
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        if (KEY_AOCSET.equals(key)) {
            final String numToSet = (String) newValue;
            if (numToSet.length() != 6) {
                Toast.makeText(this, "number between 000000 and ffffff", 0).show();
                return false;
            }
            final EditText inputServer = new EditText(this);
            inputServer.setKeyListener(new NumberKeyListener() { // from class: com.sprd.engineermode.telephony.AOCSettingsPrefActivity.2
                private char[] numberChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

                @Override // android.text.method.KeyListener
                public int getInputType() {
                    return 3;
                }

                @Override // android.text.method.NumberKeyListener
                protected char[] getAcceptedChars() {
                    return this.numberChars;
                }
            });
            InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(8);
            InputFilter[] inputFilter = {lengthFilter};
            inputServer.setFilters(inputFilter);
            inputServer.setInputType(16);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Input PIN2");
            builder.setView(inputServer);
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.AOCSettingsPrefActivity.3
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.AOCSettingsPrefActivity.4
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    if (inputServer.getText().toString() != null && inputServer.getText().toString().length() >= 4) {
                        Message m = AOCSettingsPrefActivity.this.aocSettingHandler.obtainMessage(3, Integer.parseInt(numToSet, 16), 0, inputServer.getText().toString());
                        AOCSettingsPrefActivity.this.aocSettingHandler.sendMessage(m);
                    } else {
                        Toast.makeText(AOCSettingsPrefActivity.this, "Please input 4-8 pos", 0).show();
                    }
                }
            });
            builder.show();
            return false;
        }
        if (!KEY_AOCACT.equals(key)) {
            return false;
        }
        int statusWant = 1;
        if (this.mPreference01.isChecked()) {
            statusWant = 0;
        }
        Message m = this.aocSettingHandler.obtainMessage(2, statusWant, 0, this.mPreference01);
        this.aocSettingHandler.sendMessage(m);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String analysisResponse(String response, int type) {
        if (response.contains("OK")) {
            if (type == 1) {
                String[] str = response.split("\n");
                String[] str1 = str[0].split(":");
                Log.d(TAG, type + "  " + str1[1]);
                return str1[1].trim();
            }
            if (type == 2 || type == 3) {
                return "OK";
            }
            return IATUtils.AT_FAIL;
        }
        return IATUtils.AT_FAIL;
    }

    private class AOCSettingHandler extends Handler {
        public AOCSettingHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 1) {
                AOCSettingsPrefActivity.this.mATResponse = IATUtils.sendATCmd("AT+SPTEST?", "atchannel" + AOCSettingsPrefActivity.this.mSimIndex);
                if (AOCSettingsPrefActivity.this.mATResponse.contains("OK")) {
                    if (AOCSettingsPrefActivity.this.mATResponse.contains("3,1")) {
                        AOCSettingsPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.AOCSettingsPrefActivity.AOCSettingHandler.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AOCSettingsPrefActivity.this.mPreference01.setChecked(true);
                            }
                        });
                        return;
                    } else {
                        if (AOCSettingsPrefActivity.this.mATResponse.equals("3,0")) {
                            AOCSettingsPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.AOCSettingsPrefActivity.AOCSettingHandler.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    AOCSettingsPrefActivity.this.mPreference01.setChecked(false);
                                }
                            });
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            if (i == 2) {
                if (msg.arg1 == 1) {
                    AOCSettingsPrefActivity.this.state = 1;
                } else {
                    AOCSettingsPrefActivity.this.state = 0;
                }
                AOCSettingsPrefActivity.this.mATResponse = IATUtils.sendATCmd(engconstents.ENG_AT_CAOCSET1 + AOCSettingsPrefActivity.this.state, "atchannel" + AOCSettingsPrefActivity.this.mSimIndex);
                AOCSettingsPrefActivity aOCSettingsPrefActivity = AOCSettingsPrefActivity.this;
                aOCSettingsPrefActivity.mAnalysisResponse = aOCSettingsPrefActivity.analysisResponse(aOCSettingsPrefActivity.mATResponse, 2);
                if ("OK".equals(AOCSettingsPrefActivity.this.mAnalysisResponse)) {
                    AOCSettingsPrefActivity.this.mUiThread.post(AOCSettingsPrefActivity.this.new AOCATSuccessRunnable());
                    return;
                } else {
                    AOCSettingsPrefActivity.this.mUiThread.post(AOCSettingsPrefActivity.this.new AOCATFailRunnable(msg));
                    return;
                }
            }
            if (i == 3) {
                AOCSettingsPrefActivity.this.mSetNum = msg.arg1;
                final String mSetNumStr = Integer.toHexString(AOCSettingsPrefActivity.this.mSetNum);
                String pin2 = (String) msg.obj;
                AOCSettingsPrefActivity.this.mATResponse = IATUtils.sendATCmd("AT+CAMM=\"" + mSetNumStr + "\",\"" + pin2 + "\"", "atchannel" + AOCSettingsPrefActivity.this.mSimIndex);
                Log.d(AOCSettingsPrefActivity.TAG, "AT+CAMM=\"" + mSetNumStr + "\",\"" + pin2 + "\"");
                AOCSettingsPrefActivity aOCSettingsPrefActivity2 = AOCSettingsPrefActivity.this;
                aOCSettingsPrefActivity2.mAnalysisResponse = aOCSettingsPrefActivity2.analysisResponse(aOCSettingsPrefActivity2.mATResponse, 3);
                Log.d(AOCSettingsPrefActivity.TAG, AOCSettingsPrefActivity.this.mAnalysisResponse);
                if ("OK".equals(AOCSettingsPrefActivity.this.mAnalysisResponse)) {
                    AOCSettingsPrefActivity.this.mUiThread.post(AOCSettingsPrefActivity.this.new AOCATSuccessRunnable());
                    AOCSettingsPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.AOCSettingsPrefActivity.AOCSettingHandler.3
                        @Override // java.lang.Runnable
                        public void run() {
                            AOCSettingsPrefActivity.this.mPreference02.setSummary(mSetNumStr);
                            String unused = AOCSettingsPrefActivity.mMaxNum = mSetNumStr;
                        }
                    });
                } else {
                    AOCSettingsPrefActivity.this.mUiThread.post(AOCSettingsPrefActivity.this.new AOCATFailRunnable(msg));
                }
            }
        }
    }

    class AOCATFailRunnable implements Runnable {
        private int arg1;
        private Object obj;

        public AOCATFailRunnable(Message msg) {
            this.arg1 = 0;
            this.obj = null;
            this.arg1 = msg.arg1;
            this.obj = msg.obj;
        }

        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(AOCSettingsPrefActivity.this, "Fail", 0).show();
            if (this.obj == AOCSettingsPrefActivity.this.mPreference01) {
                AOCSettingsPrefActivity.this.mPreference01.setChecked(this.arg1 == 0);
                AOCSettingsPrefActivity.this.mPreference01.setEnabled(true);
            }
        }
    }

    class AOCATSuccessRunnable implements Runnable {
        AOCATSuccessRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(AOCSettingsPrefActivity.this, "Success", 0).show();
        }
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        if (this.aocSettingHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.aocSettingHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MAX_NUM, mMaxNum);
    }
}
