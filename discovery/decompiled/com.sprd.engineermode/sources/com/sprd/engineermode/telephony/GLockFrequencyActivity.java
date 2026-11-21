package com.sprd.engineermode.telephony;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class GLockFrequencyActivity extends Activity {
    private static final int DEFAULT_LOCK_MAX = 6;
    private static final int GET_GSM_LOCK_FREQ = 0;
    private static final int SET_GSM_LOCK_FREQ = 1;
    private static final int SET_GSM_UNLOCK_FREQ = 2;
    private static final String TAG = "GLockFrequencyActivity";
    private Button mButtonLock;
    private Button mButtonUnlock;
    private GsmLockFreqHandler mGsmLockFreqHandler;
    private TextView[] mFreq = new TextView[6];
    private EditText[] mEditFreq = new EditText[6];
    private String mServerName = "atchannel0";
    private Handler mUiThread = new Handler() { // from class: com.sprd.engineermode.telephony.GLockFrequencyActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String atRsult = (String) msg.obj;
                Log.d(GLockFrequencyActivity.TAG, "atRsult = " + atRsult);
                if (atRsult != null && atRsult.contains("OK")) {
                    GLockFrequencyActivity.this.mButtonLock.setEnabled(true);
                    GLockFrequencyActivity.this.mButtonUnlock.setEnabled(true);
                    String[] str = atRsult.split("\n");
                    String[] str1 = str[0].split("\\:");
                    if (str1[1].contains(",")) {
                        String[] str2 = str1[1].split("\\,");
                        for (int i = 0; i < 6; i++) {
                            if (i < str2.length - 1) {
                                GLockFrequencyActivity.this.mEditFreq[i].setText(str2[i + 1].trim());
                            } else {
                                GLockFrequencyActivity.this.mEditFreq[i].setText("0");
                            }
                        }
                        return;
                    }
                    for (int i2 = 0; i2 < 6; i2++) {
                        GLockFrequencyActivity.this.mEditFreq[i2].setText("0");
                    }
                    return;
                }
                GLockFrequencyActivity.this.mButtonLock.setEnabled(false);
                GLockFrequencyActivity.this.mButtonUnlock.setEnabled(false);
            }
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gsm_lockfreq);
        initialpara();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mGsmLockFreqHandler = new GsmLockFreqHandler(ht.getLooper());
        Message m = this.mGsmLockFreqHandler.obtainMessage(0);
        this.mGsmLockFreqHandler.sendMessage(m);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        GsmLockFreqHandler gsmLockFreqHandler = this.mGsmLockFreqHandler;
        if (gsmLockFreqHandler != null) {
            gsmLockFreqHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    private void initialpara() {
        this.mFreq[0] = (TextView) findViewById(R.id.Text11);
        this.mFreq[1] = (TextView) findViewById(R.id.Text12);
        this.mFreq[2] = (TextView) findViewById(R.id.Text13);
        this.mFreq[3] = (TextView) findViewById(R.id.Text21);
        this.mFreq[4] = (TextView) findViewById(R.id.Text22);
        this.mFreq[5] = (TextView) findViewById(R.id.Text23);
        this.mEditFreq[0] = (EditText) findViewById(R.id.editText11);
        this.mEditFreq[1] = (EditText) findViewById(R.id.editText12);
        this.mEditFreq[2] = (EditText) findViewById(R.id.editText13);
        this.mEditFreq[3] = (EditText) findViewById(R.id.editText21);
        this.mEditFreq[4] = (EditText) findViewById(R.id.editText22);
        this.mEditFreq[5] = (EditText) findViewById(R.id.editText23);
        this.mButtonLock = (Button) findViewById(R.id.lock_button);
        this.mButtonUnlock = (Button) findViewById(R.id.clear_button);
        this.mButtonLock.setText("Lock");
        this.mButtonUnlock.setText("Unlock");
        for (int i = 0; i < 6; i++) {
            this.mFreq[i].setText("Freq" + i);
        }
        this.mButtonLock.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.telephony.GLockFrequencyActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (GLockFrequencyActivity.this.findInvalidText() == null) {
                    GLockFrequencyActivity.this.mButtonLock.setEnabled(false);
                    GLockFrequencyActivity.this.mButtonUnlock.setEnabled(false);
                    Message m = GLockFrequencyActivity.this.mGsmLockFreqHandler.obtainMessage(1);
                    GLockFrequencyActivity.this.mGsmLockFreqHandler.sendMessage(m);
                    return;
                }
                Toast.makeText(GLockFrequencyActivity.this, "Para is wrong", 0).show();
            }
        });
        this.mButtonUnlock.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.telephony.GLockFrequencyActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                GLockFrequencyActivity.this.mButtonLock.setEnabled(false);
                GLockFrequencyActivity.this.mButtonUnlock.setEnabled(false);
                Message m = GLockFrequencyActivity.this.mGsmLockFreqHandler.obtainMessage(2);
                GLockFrequencyActivity.this.mGsmLockFreqHandler.sendMessage(m);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EditText findInvalidText() {
        for (int line = 0; line < 6; line++) {
            if (!this.mEditFreq[line].getText().toString().trim().equals("")) {
                int freq = Integer.parseInt(this.mEditFreq[line].getText().toString().trim());
                if (freq < 0 || freq > 2047) {
                    return this.mEditFreq[line];
                }
            } else {
                return this.mEditFreq[line];
            }
        }
        return null;
    }

    class GsmLockFreqHandler extends Handler {
        public GsmLockFreqHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                GLockFrequencyActivity.this.mUiThread.sendMessage(GLockFrequencyActivity.this.mUiThread.obtainMessage(0, IATUtils.sendATCmd(engconstents.ENG_AT_SPGSMFRQ1, GLockFrequencyActivity.this.mServerName)));
                return;
            }
            if (i != 1) {
                if (i == 2) {
                    String responValue = IATUtils.sendATCmd("AT+SPGSMFRQ=0", GLockFrequencyActivity.this.mServerName);
                    Log.d(GLockFrequencyActivity.TAG, "resluts of AT+SPGSMFRQ=0is :" + responValue);
                    if (responValue == null || !responValue.contains("OK")) {
                        GLockFrequencyActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GLockFrequencyActivity.GsmLockFreqHandler.4
                            @Override // java.lang.Runnable
                            public void run() {
                                GLockFrequencyActivity.this.mButtonLock.setEnabled(false);
                                GLockFrequencyActivity.this.mButtonUnlock.setEnabled(false);
                                Toast.makeText(GLockFrequencyActivity.this, "Fail", 0).show();
                            }
                        });
                        return;
                    } else {
                        GLockFrequencyActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GLockFrequencyActivity.GsmLockFreqHandler.3
                            @Override // java.lang.Runnable
                            public void run() {
                                GLockFrequencyActivity.this.mButtonLock.setEnabled(true);
                                GLockFrequencyActivity.this.mButtonUnlock.setEnabled(true);
                                Message m = GLockFrequencyActivity.this.mGsmLockFreqHandler.obtainMessage(0);
                                GLockFrequencyActivity.this.mGsmLockFreqHandler.sendMessage(m);
                                Toast.makeText(GLockFrequencyActivity.this, "Success", 0).show();
                            }
                        });
                        return;
                    }
                }
                return;
            }
            String atCmd = "AT+SPGSMFRQ=1";
            for (int i2 = 0; i2 < 6; i2++) {
                if (!GLockFrequencyActivity.this.mEditFreq[i2].getText().equals("")) {
                    atCmd = atCmd + "," + GLockFrequencyActivity.this.mEditFreq[i2].getText().toString().trim();
                }
            }
            String responValue2 = IATUtils.sendATCmd(atCmd, GLockFrequencyActivity.this.mServerName);
            Log.d(GLockFrequencyActivity.TAG, "resluts of " + atCmd + "is :" + responValue2);
            if (responValue2 == null || !responValue2.contains("OK")) {
                GLockFrequencyActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GLockFrequencyActivity.GsmLockFreqHandler.2
                    @Override // java.lang.Runnable
                    public void run() {
                        GLockFrequencyActivity.this.mButtonLock.setEnabled(false);
                        GLockFrequencyActivity.this.mButtonUnlock.setEnabled(false);
                        Toast.makeText(GLockFrequencyActivity.this, "Fail", 0).show();
                    }
                });
            } else {
                GLockFrequencyActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GLockFrequencyActivity.GsmLockFreqHandler.1
                    @Override // java.lang.Runnable
                    public void run() {
                        GLockFrequencyActivity.this.mButtonLock.setEnabled(true);
                        GLockFrequencyActivity.this.mButtonUnlock.setEnabled(true);
                        Toast.makeText(GLockFrequencyActivity.this, "Success", 0).show();
                    }
                });
            }
        }
    }
}
