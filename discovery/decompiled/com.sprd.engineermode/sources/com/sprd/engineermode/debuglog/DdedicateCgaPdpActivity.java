package com.sprd.engineermode.debuglog;

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
public class DdedicateCgaPdpActivity extends Activity implements View.OnClickListener {
    private static final int SET_CGA_AT = 0;
    private static final String TAG = "DdedicateCgaPdpActivity";
    private Button mActived;
    private CgaPdpHandler mCgaPdpHandler;
    private EditText mCid;
    private EditText mState;
    public String stringState = null;
    public String stringCid = null;
    public String stringCmd = null;
    private Handler mUiThread = new Handler();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dedicate_cga_pdp);
        initUI();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mCgaPdpHandler = new CgaPdpHandler(ht.getLooper());
    }

    private void initUI() {
        this.mState = (EditText) findViewById(R.id.cga_state);
        this.mCid = (EditText) findViewById(R.id.cga_cid);
        this.mState.setText("1");
        this.mCid.setText("7");
        this.mActived = (Button) findViewById(R.id.cga_activate);
        this.mActived.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        getSettingParam();
        if (v == this.mActived) {
            Message cgdMessage = this.mCgaPdpHandler.obtainMessage(0, this.stringCmd);
            this.mCgaPdpHandler.sendMessage(cgdMessage);
        }
    }

    class CgaPdpHandler extends Handler {
        public CgaPdpHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String atCmd = (String) msg.obj;
                Log.d(DdedicateCgaPdpActivity.TAG, "SET_CGA atCmd = AT+CGACT=" + atCmd);
                String atResponse = IATUtils.sendATCmd("AT+CGACT=" + atCmd, "atchannel0");
                Log.d(DdedicateCgaPdpActivity.TAG, "SET_CGA responValue = " + atResponse);
                if (atResponse == null || !atResponse.contains("OK")) {
                    DdedicateCgaPdpActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DdedicateCgaPdpActivity.CgaPdpHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DdedicateCgaPdpActivity.this, "activate cga fail", 0).show();
                        }
                    });
                } else {
                    DdedicateCgaPdpActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DdedicateCgaPdpActivity.CgaPdpHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DdedicateCgaPdpActivity.this, "activate cga success", 0).show();
                        }
                    });
                }
            }
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getSettingParam() {
        this.stringState = this.mState.getText().toString();
        this.stringCid = this.mCid.getText().toString();
        this.stringCmd = this.stringState + "," + this.stringCid;
        Log.d(TAG, "Now testing cga, the setting State is: \n cid " + this.stringState + "\n cid is " + this.stringCid + "\n stringCmd is " + this.stringCmd);
    }
}
