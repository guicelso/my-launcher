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
public class LockCellActivity extends Activity {
    private static final int GETLOCKCELL = 1;
    private static final String TAG = "LockCellActivity";
    private static EditText[] mET = new EditText[16];
    private static String[] ss = new String[16];
    private String mATResponse;
    private String mAnalysisResponse;
    private Button mButton;
    private Handler mUiThread = new Handler();
    private GetLockCellHandler unLockCellHandler;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockcell);
        mET[0] = (EditText) findViewById(R.id.editText1);
        mET[1] = (EditText) findViewById(R.id.editText11);
        mET[2] = (EditText) findViewById(R.id.editText12);
        mET[3] = (EditText) findViewById(R.id.editText13);
        mET[4] = (EditText) findViewById(R.id.editText2);
        mET[5] = (EditText) findViewById(R.id.editText21);
        mET[6] = (EditText) findViewById(R.id.editText22);
        mET[7] = (EditText) findViewById(R.id.editText23);
        mET[8] = (EditText) findViewById(R.id.editText3);
        mET[9] = (EditText) findViewById(R.id.editText31);
        mET[10] = (EditText) findViewById(R.id.editText32);
        mET[11] = (EditText) findViewById(R.id.editText33);
        mET[12] = (EditText) findViewById(R.id.editText4);
        mET[13] = (EditText) findViewById(R.id.editText41);
        mET[14] = (EditText) findViewById(R.id.editText42);
        mET[15] = (EditText) findViewById(R.id.editText43);
        this.mButton = (Button) findViewById(R.id.Button_get);
        this.mButton.setText("Get data");
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.unLockCellHandler = new GetLockCellHandler(ht.getLooper());
        this.mButton.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.telephony.LockCellActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Message m = LockCellActivity.this.unLockCellHandler.obtainMessage(1);
                LockCellActivity.this.unLockCellHandler.sendMessage(m);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String analysisResponse(String response, int type) {
        if (response.contains("OK")) {
            String[] str = response.split("\n");
            Log.e(TAG, str[0]);
            String strtemp2 = str[0].replace("+SPFRQ:", ",").substring(1);
            Log.e(TAG, strtemp2);
            ss = strtemp2.split(",");
            return "OK";
        }
        return IATUtils.AT_FAIL;
    }

    private class GetLockCellHandler extends Handler {
        public GetLockCellHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                LockCellActivity.this.mATResponse = IATUtils.sendATCmd("AT+SPFRQ?", "atchannel0");
                LockCellActivity lockCellActivity = LockCellActivity.this;
                lockCellActivity.mAnalysisResponse = lockCellActivity.analysisResponse(lockCellActivity.mATResponse, 1);
                if (LockCellActivity.this.mAnalysisResponse.equals("OK")) {
                    LockCellActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.LockCellActivity.GetLockCellHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            for (int i = 0; i < 16; i++) {
                                LockCellActivity.mET[i].setText(LockCellActivity.ss[i]);
                            }
                        }
                    });
                    LockCellActivity.this.mUiThread.post(LockCellActivity.this.new LockCellATSuccessRunnable());
                } else {
                    LockCellActivity.this.mUiThread.post(LockCellActivity.this.new LockCellATFailRunnable());
                }
            }
        }
    }

    class LockCellATFailRunnable implements Runnable {
        LockCellATFailRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(LockCellActivity.this, "Fail", 0).show();
        }
    }

    class LockCellATSuccessRunnable implements Runnable {
        LockCellATSuccessRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(LockCellActivity.this, "Success", 0).show();
        }
    }
}
