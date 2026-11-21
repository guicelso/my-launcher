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
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class UnlockFreqActivity extends Activity {
    private static final int SETLOCKFRQ = 1;
    private static final String TAG = "UnlockFreqActivity";
    private UnLockHandler lockFreqHandler;
    private String mATResponse;
    private String mAnalysisResponse;
    private Button mButton;
    private Button mButton01;
    public EditText mET;
    private Handler mUiThread = new Handler();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unlockfreq);
        this.mET = (EditText) findViewById(R.id.editText1);
        this.mButton = (Button) findViewById(R.id.lock_button1);
        this.mButton01 = (Button) findViewById(R.id.clear_button1);
        clearEditText();
        this.mButton.setText("Unlock");
        this.mButton01.setText("Clear");
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.lockFreqHandler = new UnLockHandler(ht.getLooper());
        this.mButton.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.telephony.UnlockFreqActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Message m = UnlockFreqActivity.this.lockFreqHandler.obtainMessage(1);
                UnlockFreqActivity.this.lockFreqHandler.sendMessage(m);
            }
        });
        this.mButton01.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.telephony.UnlockFreqActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                UnlockFreqActivity.this.clearEditText();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearEditText() {
        this.mET.setText("0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String analysisResponse(String response, int type) {
        if (response.contains("OK")) {
            return "OK";
        }
        return IATUtils.AT_FAIL;
    }

    private class UnLockHandler extends Handler {
        public UnLockHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                UnlockFreqActivity.this.mATResponse = IATUtils.sendATCmd("AT+SPFRQ=1," + UnlockFreqActivity.this.mET.getEditableText().toString().trim(), "atchannel0");
                UnlockFreqActivity unlockFreqActivity = UnlockFreqActivity.this;
                unlockFreqActivity.mAnalysisResponse = unlockFreqActivity.analysisResponse(unlockFreqActivity.mATResponse, 1);
                if (UnlockFreqActivity.this.mAnalysisResponse.equals("OK")) {
                    UnlockFreqActivity.this.mUiThread.post(UnlockFreqActivity.this.new UnLockFreqATSuccessRunnable());
                } else {
                    UnlockFreqActivity.this.mUiThread.post(UnlockFreqActivity.this.new UnLockFreqATFailRunnable());
                }
            }
        }
    }

    class UnLockFreqATFailRunnable implements Runnable {
        UnLockFreqATFailRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(UnlockFreqActivity.this, "Fail", 0).show();
        }
    }

    class UnLockFreqATSuccessRunnable implements Runnable {
        UnLockFreqATSuccessRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(UnlockFreqActivity.this, "Success", 0).show();
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
