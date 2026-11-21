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
public class DdedicateCgdPdpActivity extends Activity implements View.OnClickListener {
    private static final int SET_CGD_AT = 0;
    private static final String TAG = "DedicateCgdPdpActivity";
    private Button mActived;
    private CgdPdpHandler mCgdPdpHandler;
    private EditText mCid;
    private EditText mPid;
    public String stringCid = null;
    public String stringPid = null;
    public String stringCmd = null;
    private Handler mUiThread = new Handler();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dedicate_cgd_pdp);
        initUI();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mCgdPdpHandler = new CgdPdpHandler(ht.getLooper());
    }

    private void initUI() {
        this.mCid = (EditText) findViewById(R.id.cgd_cid);
        this.mPid = (EditText) findViewById(R.id.cgd_pid);
        this.mCid.setText("7");
        this.mPid.setText("1");
        this.mActived = (Button) findViewById(R.id.cgd_activate);
        this.mActived.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        getSettingParam();
        if (v == this.mActived) {
            Message cgdMessage = this.mCgdPdpHandler.obtainMessage(0, this.stringCmd);
            this.mCgdPdpHandler.sendMessage(cgdMessage);
        }
    }

    class CgdPdpHandler extends Handler {
        public CgdPdpHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String atCmd = (String) msg.obj;
                Log.d(DdedicateCgdPdpActivity.TAG, "SET_CGD atCmd = AT+CGDSCONT=" + atCmd);
                String atResponse = IATUtils.sendATCmd("AT+CGDSCONT=" + atCmd, "atchannel0");
                Log.d(DdedicateCgdPdpActivity.TAG, "SET_CGD responValue = " + atResponse);
                if (atResponse == null || !atResponse.contains("OK")) {
                    DdedicateCgdPdpActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DdedicateCgdPdpActivity.CgdPdpHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DdedicateCgdPdpActivity.this, "activate cgd fail", 0).show();
                        }
                    });
                } else {
                    DdedicateCgdPdpActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DdedicateCgdPdpActivity.CgdPdpHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DdedicateCgdPdpActivity.this, "activate cgd success", 0).show();
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
        this.stringCid = this.mCid.getText().toString();
        this.stringPid = this.mPid.getText().toString();
        this.stringCmd = this.stringCid + "," + this.stringPid;
        Log.d(TAG, "Now testing cgd, the setting param is: \n cid " + this.stringCid + "\n pid is " + this.stringPid + "\n pid is " + this.stringCmd);
    }
}
