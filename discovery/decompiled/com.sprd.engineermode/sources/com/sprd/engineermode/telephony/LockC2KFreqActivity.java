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
public class LockC2KFreqActivity extends Activity {
    private static final int DEFAULT_LOCK_MAX = 4;
    private static final int GETLOCKFRQ = 0;
    private static final int LTE_LOCK_MAX = 2;
    private static final int SETLOCKFRQ = 1;
    private static final int SETUNLOCKFRQ = 2;
    private static final String TAG = "LockC2KFreqActivity";
    private static final int VALUES_COUNT = 4;
    private TextView cell1;
    private TextView cell2;
    private TextView cell3;
    private TextView freq;
    private LockFreqHandler lockFreqHandler;
    private Button mButtonLock;
    private Button mButtonUnlock;
    private boolean isSupportLTE = false;
    private EditText[] mFreqEdit = new EditText[4];
    private EditText[] mCell1Edit = new EditText[4];
    private EditText[] mCell2Edit = new EditText[4];
    private EditText[] mCell3Edit = new EditText[4];
    private Handler mUiThread = new Handler() { // from class: com.sprd.engineermode.telephony.LockC2KFreqActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            int i2 = 2;
            int i3 = 0;
            if (i != 0) {
                if (i == 1) {
                    if (((String) msg.obj).contains("OK")) {
                        Toast.makeText(LockC2KFreqActivity.this, "Lock Success", 0).show();
                        LockC2KFreqActivity.this.mButtonLock.setEnabled(true);
                        LockC2KFreqActivity.this.mButtonUnlock.setEnabled(true);
                        return;
                    }
                    return;
                }
                if (i == 2 && ((String) msg.obj).contains("OK")) {
                    Toast.makeText(LockC2KFreqActivity.this, "Unlock Success", 0).show();
                    Message m = LockC2KFreqActivity.this.lockFreqHandler.obtainMessage(0);
                    LockC2KFreqActivity.this.lockFreqHandler.sendMessage(m);
                    return;
                }
                return;
            }
            String atRsult = (String) msg.obj;
            Log.d(LockC2KFreqActivity.TAG, "atRsult = " + atRsult);
            if (!atRsult.contains("OK")) {
                LockC2KFreqActivity.this.mButtonLock.setEnabled(false);
                LockC2KFreqActivity.this.mButtonUnlock.setEnabled(false);
                return;
            }
            String[] freqs = atRsult.split("\r")[0].split("\\+SPFRQ\\:");
            Log.d(LockC2KFreqActivity.TAG, "freqs line is :" + freqs.length);
            int i4 = 4;
            if (2 == freqs.length - 1) {
                LockC2KFreqActivity.this.isSupportLTE = true;
                LockC2KFreqActivity.this.freq.setVisibility(0);
                LockC2KFreqActivity.this.mFreqEdit[0].setVisibility(0);
                LockC2KFreqActivity.this.mFreqEdit[1].setVisibility(0);
                int length = freqs.length;
                int line = 0;
                int line2 = 0;
                while (line2 < length) {
                    String freq = freqs[line2];
                    Log.d(LockC2KFreqActivity.TAG, "line = " + line + " freq = " + freq);
                    if (line == i2) {
                        break;
                    }
                    String[] value = freq.split(",");
                    if (value.length == i4) {
                        LockC2KFreqActivity.this.mFreqEdit[line].setText(value[0].trim());
                        line++;
                    }
                    line2++;
                    i2 = 2;
                    i4 = 4;
                }
            } else {
                LockC2KFreqActivity.this.isSupportLTE = false;
                LockC2KFreqActivity.this.freq.setVisibility(0);
                LockC2KFreqActivity.this.cell1.setVisibility(0);
                LockC2KFreqActivity.this.cell2.setVisibility(0);
                LockC2KFreqActivity.this.cell3.setVisibility(0);
                int i5 = 0;
                int line3 = 0;
                while (i5 < 4) {
                    LockC2KFreqActivity.this.mFreqEdit[i5].setVisibility(i3);
                    LockC2KFreqActivity.this.mCell1Edit[i5].setVisibility(i3);
                    LockC2KFreqActivity.this.mCell2Edit[i5].setVisibility(i3);
                    LockC2KFreqActivity.this.mCell3Edit[i5].setVisibility(i3);
                    int length2 = freqs.length;
                    for (int i6 = i3; i6 < length2; i6++) {
                        String freq2 = freqs[i6];
                        Log.d(LockC2KFreqActivity.TAG, "line = " + line3 + " freq = " + freq2);
                        if (line3 == 4) {
                            break;
                        }
                        String[] value2 = freq2.split(",");
                        if (value2.length == 4) {
                            LockC2KFreqActivity.this.mFreqEdit[line3].setText(value2[0].trim());
                            LockC2KFreqActivity.this.mCell1Edit[line3].setText(value2[1].trim());
                            LockC2KFreqActivity.this.mCell2Edit[line3].setText(value2[2].trim());
                            LockC2KFreqActivity.this.mCell3Edit[line3].setText(value2[3].trim());
                            line3++;
                        }
                    }
                    i5++;
                    i3 = 0;
                }
            }
            LockC2KFreqActivity.this.mButtonLock.setEnabled(true);
            LockC2KFreqActivity.this.mButtonUnlock.setEnabled(true);
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockfreq);
        this.mButtonLock = (Button) findViewById(R.id.lock_button);
        this.mButtonUnlock = (Button) findViewById(R.id.clear_button);
        this.mButtonLock.setText("Lock");
        this.mButtonUnlock.setText("Unlock");
        initialpara();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.lockFreqHandler = new LockFreqHandler(ht.getLooper());
        Message m = this.lockFreqHandler.obtainMessage(0);
        this.lockFreqHandler.sendMessage(m);
    }

    private void initialpara() {
        this.freq = (TextView) findViewById(R.id.Text1);
        this.cell1 = (TextView) findViewById(R.id.Text2);
        this.cell2 = (TextView) findViewById(R.id.Text3);
        this.cell3 = (TextView) findViewById(R.id.Text4);
        this.freq.setText("Freq:");
        this.cell1.setText("Cell1:");
        this.cell2.setText("Cell2:");
        this.cell3.setText("Cell3:");
        this.mFreqEdit[0] = (EditText) findViewById(R.id.editText11);
        this.mCell1Edit[0] = (EditText) findViewById(R.id.editText12);
        this.mCell2Edit[0] = (EditText) findViewById(R.id.editText13);
        this.mCell3Edit[0] = (EditText) findViewById(R.id.editText14);
        this.mFreqEdit[1] = (EditText) findViewById(R.id.editText21);
        this.mCell1Edit[1] = (EditText) findViewById(R.id.editText22);
        this.mCell2Edit[1] = (EditText) findViewById(R.id.editText23);
        this.mCell3Edit[1] = (EditText) findViewById(R.id.editText24);
        this.mFreqEdit[2] = (EditText) findViewById(R.id.editText31);
        this.mCell1Edit[2] = (EditText) findViewById(R.id.editText32);
        this.mCell2Edit[2] = (EditText) findViewById(R.id.editText33);
        this.mCell3Edit[2] = (EditText) findViewById(R.id.editText34);
        this.mFreqEdit[3] = (EditText) findViewById(R.id.editText41);
        this.mCell1Edit[3] = (EditText) findViewById(R.id.editText42);
        this.mCell2Edit[3] = (EditText) findViewById(R.id.editText43);
        this.mCell3Edit[3] = (EditText) findViewById(R.id.editText44);
        this.freq.setVisibility(8);
        this.cell1.setVisibility(8);
        this.cell2.setVisibility(8);
        this.cell3.setVisibility(8);
        for (int line = 0; line < 4; line++) {
            this.mFreqEdit[line].setVisibility(8);
            this.mCell1Edit[line].setVisibility(8);
            this.mCell2Edit[line].setVisibility(8);
            this.mCell3Edit[line].setVisibility(8);
        }
        this.mButtonLock.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.telephony.LockC2KFreqActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (LockC2KFreqActivity.this.findInvalidText() == null) {
                    LockC2KFreqActivity.this.mButtonLock.setEnabled(false);
                    LockC2KFreqActivity.this.mButtonUnlock.setEnabled(false);
                    Message m = LockC2KFreqActivity.this.lockFreqHandler.obtainMessage(1);
                    LockC2KFreqActivity.this.lockFreqHandler.sendMessage(m);
                }
            }
        });
        this.mButtonUnlock.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.telephony.LockC2KFreqActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                LockC2KFreqActivity.this.mButtonLock.setEnabled(false);
                LockC2KFreqActivity.this.mButtonUnlock.setEnabled(false);
                Message m = LockC2KFreqActivity.this.lockFreqHandler.obtainMessage(2);
                LockC2KFreqActivity.this.lockFreqHandler.sendMessage(m);
            }
        });
    }

    private String analysisResponse(String response, int type) {
        if (response.contains("OK")) {
            return "OK";
        }
        return IATUtils.AT_FAIL;
    }

    class LockFreqHandler extends Handler {
        public LockFreqHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                LockC2KFreqActivity.this.mUiThread.sendMessage(LockC2KFreqActivity.this.mUiThread.obtainMessage(0, IATUtils.sendATCmd(engconstents.ENG_AT_GET_SPC2KFRQ, "atchannel0")));
                return;
            }
            if (i != 1) {
                if (i == 2) {
                    String atResponse = "OK";
                    for (int i2 = 0; i2 < 4; i2++) {
                        atResponse = IATUtils.sendATCmd("AT+SPC2KFRQ=1", "atchannel0");
                        if (!atResponse.contains("OK")) {
                            LockC2KFreqActivity.this.mUiThread.sendMessage(LockC2KFreqActivity.this.mUiThread.obtainMessage(2, atResponse));
                            return;
                        }
                    }
                    LockC2KFreqActivity.this.mUiThread.sendMessage(LockC2KFreqActivity.this.mUiThread.obtainMessage(2, atResponse));
                    return;
                }
                return;
            }
            String atResponse2 = "OK";
            if (!LockC2KFreqActivity.this.isSupportLTE) {
                for (int line = 0; line < 4; line++) {
                    atResponse2 = IATUtils.sendATCmd("AT+SPC2KFRQ=0," + line + "," + LockC2KFreqActivity.this.mFreqEdit[line].getText().toString().trim() + "," + LockC2KFreqActivity.this.mCell1Edit[line].getText().toString().trim() + "," + LockC2KFreqActivity.this.mCell2Edit[line].getText().toString().trim() + "," + LockC2KFreqActivity.this.mCell3Edit[line].getText().toString().trim(), "atchannel0");
                    if (!atResponse2.contains("OK")) {
                        LockC2KFreqActivity.this.mUiThread.sendMessage(LockC2KFreqActivity.this.mUiThread.obtainMessage(1, atResponse2));
                        return;
                    }
                }
            } else {
                for (int line2 = 0; line2 < 2; line2++) {
                    atResponse2 = IATUtils.sendATCmd("AT+SPC2KFRQ=0," + (line2 + 4) + "," + LockC2KFreqActivity.this.mFreqEdit[line2].getText().toString().trim(), "atchannel0");
                    if (!atResponse2.contains("OK")) {
                        LockC2KFreqActivity.this.mUiThread.sendMessage(LockC2KFreqActivity.this.mUiThread.obtainMessage(1, atResponse2));
                        return;
                    }
                }
            }
            LockC2KFreqActivity.this.mUiThread.sendMessage(LockC2KFreqActivity.this.mUiThread.obtainMessage(1, atResponse2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EditText findInvalidText() {
        if (this.isSupportLTE) {
            if (this.mFreqEdit[0].getText().toString().trim() != null && this.mFreqEdit[0].getText().toString().trim().length() != 0 && this.mFreqEdit[1].getText().toString().trim() != null && this.mFreqEdit[1].getText().toString().trim().length() != 0) {
                int freq0 = Integer.parseInt(this.mFreqEdit[0].getText().toString().trim());
                int freq1 = Integer.parseInt(this.mFreqEdit[1].getText().toString().trim());
                if (freq0 < 0 || freq0 > 65535) {
                    Toast.makeText(this, "Para is wrong", 0).show();
                    return this.mFreqEdit[0];
                }
                if (freq1 < 0 || freq1 > 65535) {
                    Toast.makeText(this, "Para is wrong", 0).show();
                    return this.mFreqEdit[1];
                }
                if (freq0 > freq1) {
                    Toast.makeText(this, "The first number must not be greater than the second number", 0).show();
                    return this.mFreqEdit[0];
                }
                return null;
            }
            Toast.makeText(this, "Input is wrong, it can not be empty", 0).show();
            return this.mFreqEdit[0];
        }
        for (int line = 0; line < 4; line++) {
            if (this.mFreqEdit[line].getText().toString().trim() != null && this.mFreqEdit[line].getText().toString().trim().length() != 0 && this.mCell1Edit[line].getText().toString().trim() != null && this.mCell1Edit[line].getText().toString().trim().length() != 0 && this.mCell2Edit[line].getText().toString().trim() != null && this.mCell2Edit[line].getText().toString().trim().length() != 0 && this.mCell3Edit[line].getText().toString().trim() != null && this.mCell3Edit[line].getText().toString().trim().length() != 0) {
                int freq = Integer.parseInt(this.mFreqEdit[line].getText().toString().trim());
                int cell1 = Integer.parseInt(this.mCell1Edit[line].getText().toString().trim());
                int cell2 = Integer.parseInt(this.mCell2Edit[line].getText().toString().trim());
                int cell3 = Integer.parseInt(this.mCell3Edit[line].getText().toString().trim());
                if (freq < 0 || freq > 65535) {
                    Toast.makeText(this, "Para is wrong", 0).show();
                    return this.mFreqEdit[line];
                }
                if (cell1 < 0 || cell1 > 255) {
                    Toast.makeText(this, "Para is wrong", 0).show();
                    return this.mCell1Edit[line];
                }
                if (cell2 < 0 || cell2 > 255) {
                    Toast.makeText(this, "Para is wrong", 0).show();
                    return this.mCell2Edit[line];
                }
                if (cell3 < 0 || cell3 > 255) {
                    Toast.makeText(this, "Para is wrong", 0).show();
                    return this.mCell3Edit[line];
                }
            } else {
                Toast.makeText(this, "Input is wrong, it can not be empty", 0).show();
                return this.mFreqEdit[line];
            }
        }
        return null;
    }

    class LockFreqATFailRunnable implements Runnable {
        LockFreqATFailRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(LockC2KFreqActivity.this, "Fail", 0).show();
        }
    }

    class LockFreqATSuccessRunnable implements Runnable {
        LockFreqATSuccessRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(LockC2KFreqActivity.this, "Success", 0).show();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.lockFreqHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.lockFreqHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
