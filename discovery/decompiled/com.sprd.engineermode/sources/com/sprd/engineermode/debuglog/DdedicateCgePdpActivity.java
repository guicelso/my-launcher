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
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class DdedicateCgePdpActivity extends Activity implements View.OnClickListener {
    private static final int SET_CGE_AT = 0;
    private static final String TAG = "DdedicateCgePdpActivity";
    private Button mActived;
    private CgePdpHandler mCgePdpHandler;
    private EditText mCid;
    private EditText mDlGbr;
    private EditText mDlMbr;
    private EditText mQci;
    private EditText mUlGbr;
    private EditText mUlMbr;
    public String stringCid = null;
    public String stringQci = null;
    public String stringDlGbr = null;
    public String stringUlGbr = null;
    public String stringDlMbr = null;
    public String stringUlMbr = null;
    public String stringCmd = null;
    private Handler mUiThread = new Handler();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dedicate_cge_pdp);
        initUI();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mCgePdpHandler = new CgePdpHandler(ht.getLooper());
    }

    private void initUI() {
        this.mCid = (EditText) findViewById(R.id.cge_cid);
        this.mQci = (EditText) findViewById(R.id.cge_qci);
        this.mDlGbr = (EditText) findViewById(R.id.cge_dl_gbr);
        this.mUlGbr = (EditText) findViewById(R.id.cge_ul_gbr);
        this.mDlMbr = (EditText) findViewById(R.id.cge_dl_mbr);
        this.mUlMbr = (EditText) findViewById(R.id.cge_ul_mbr);
        this.mCid.setText("7");
        this.mQci.setText("1");
        this.mDlGbr.setText("128");
        this.mUlGbr.setText("128");
        this.mDlMbr.setText("384");
        this.mUlMbr.setText("384");
        this.mCid.setHint("please input 0~800");
        this.mActived = (Button) findViewById(R.id.cga_activate);
        this.mActived.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        getSettingParam();
        if (v == this.mActived) {
            Message cgdMessage = this.mCgePdpHandler.obtainMessage(0, this.stringCmd);
            this.mCgePdpHandler.sendMessage(cgdMessage);
        }
    }

    class CgePdpHandler extends Handler {
        public CgePdpHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String atCmd = (String) msg.obj;
                Log.d(DdedicateCgePdpActivity.TAG, "SET_CGE atCmd = AT+CGEQOS=" + atCmd);
                String atResponse = IATUtils.sendATCmd(engconstents.ENG_AT_SET_CGE + atCmd, "atchannel0");
                Log.d(DdedicateCgePdpActivity.TAG, "SET_CGE responValue = " + atResponse);
                if (atResponse == null || !atResponse.contains("OK")) {
                    DdedicateCgePdpActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DdedicateCgePdpActivity.CgePdpHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DdedicateCgePdpActivity.this, "activate cge fail", 0).show();
                        }
                    });
                } else {
                    DdedicateCgePdpActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DdedicateCgePdpActivity.CgePdpHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DdedicateCgePdpActivity.this, "activate cge success", 0).show();
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
        this.stringQci = this.mQci.getText().toString();
        this.stringDlGbr = this.mDlGbr.getText().toString();
        this.stringUlGbr = this.mUlGbr.getText().toString();
        this.stringDlMbr = this.mDlMbr.getText().toString();
        this.stringUlMbr = this.mUlMbr.getText().toString();
        this.stringCmd = this.stringCid + "," + this.stringQci + "," + this.stringDlGbr + "," + this.stringUlGbr + "," + this.stringDlMbr + "," + this.stringUlMbr;
        Log.d(TAG, "Now testing cge, the setting Cid is: \n cid " + this.stringCid + "\n Qci is " + this.stringQci + "\n DlGbr is " + this.stringDlGbr + "\n UlGbr is " + this.stringUlGbr + "\n DlMbr is " + this.stringDlMbr + "\n UlMbr is " + this.stringUlMbr + "\n stringCmd is " + this.stringCmd);
    }
}
