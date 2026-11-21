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
public class DdedicateCgtPdpActivity extends Activity implements View.OnClickListener {
    private static final int SET_CGT_AT = 0;
    private static final String TAG = "DdedicateCgtPdpActivity";
    private Button mActived;
    private CgtPdpHandler mCgtPdpHandler;
    private EditText mEtCid;
    private EditText mEtDestination;
    private EditText mEtEvaluation;
    private EditText mEtFlowLabel;
    private EditText mEtIpsecSecurity;
    private EditText mEtPacketFilter;
    private EditText mEtProtocolNumber;
    private EditText mEtSourceAddress;
    private EditText mEtSourcePort;
    private EditText mEtType;
    public String strCid = null;
    public String strPacket = null;
    public String strEva = null;
    public String strSou = null;
    public String strPro = null;
    public String strDes = null;
    public String strSourcePort = null;
    public String strIpsec = null;
    public String strType = null;
    public String strFlow = null;
    public String strCmd = null;
    private Handler mUiThread = new Handler();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dedicate_cgt_pdp);
        initUI();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mCgtPdpHandler = new CgtPdpHandler(ht.getLooper());
    }

    private void initUI() {
        this.mEtCid = (EditText) findViewById(R.id.cgt_cid);
        this.mEtPacketFilter = (EditText) findViewById(R.id.cgt_packet_filter);
        this.mEtEvaluation = (EditText) findViewById(R.id.cgt_evaluation_precedence);
        this.mEtSourceAddress = (EditText) findViewById(R.id.cgt_source_address);
        this.mEtProtocolNumber = (EditText) findViewById(R.id.cgt_protocol_number);
        this.mEtDestination = (EditText) findViewById(R.id.cgt_destination_port_range);
        this.mEtSourcePort = (EditText) findViewById(R.id.cgt_source_port_range);
        this.mEtIpsecSecurity = (EditText) findViewById(R.id.cgt_ipsec_security_parameter);
        this.mEtType = (EditText) findViewById(R.id.cgt_type_of_service);
        this.mEtFlowLabel = (EditText) findViewById(R.id.cgt_flow_label);
        this.mEtCid.setText("7");
        this.mEtPacketFilter.setText("2");
        this.mEtEvaluation.setText("5");
        this.mEtSourceAddress.setText("\"10.19.4.1.255.255.255.255\"");
        this.mEtProtocolNumber.setText("1");
        this.mActived = (Button) findViewById(R.id.cga_activate);
        this.mActived.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        getSettingParam();
        if (v == this.mActived) {
            Message cgtMessage = this.mCgtPdpHandler.obtainMessage(0, this.strCmd);
            this.mCgtPdpHandler.sendMessage(cgtMessage);
        }
    }

    class CgtPdpHandler extends Handler {
        public CgtPdpHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String atCmd = (String) msg.obj;
                Log.d(DdedicateCgtPdpActivity.TAG, "SET_CGT atCmd = AT+CGTFT=" + atCmd);
                String atResponse = IATUtils.sendATCmd("AT+CGTFT=" + atCmd, "atchannel0");
                Log.d(DdedicateCgtPdpActivity.TAG, "SET_CGT responValue = " + atResponse);
                if (atResponse == null || !atResponse.contains("OK")) {
                    DdedicateCgtPdpActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DdedicateCgtPdpActivity.CgtPdpHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DdedicateCgtPdpActivity.this, "activate cgt fail", 0).show();
                        }
                    });
                } else {
                    DdedicateCgtPdpActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DdedicateCgtPdpActivity.CgtPdpHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DdedicateCgtPdpActivity.this, "activate cgt success", 0).show();
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
        this.strCid = this.mEtCid.getText().toString();
        this.strPacket = this.mEtPacketFilter.getText().toString();
        this.strEva = this.mEtEvaluation.getText().toString();
        this.strSou = this.mEtSourceAddress.getText().toString();
        this.strPro = this.mEtProtocolNumber.getText().toString();
        this.strDes = this.mEtDestination.getText().toString();
        this.strSourcePort = this.mEtSourcePort.getText().toString();
        this.strIpsec = this.mEtIpsecSecurity.getText().toString();
        this.strType = this.mEtType.getText().toString();
        this.strFlow = this.mEtFlowLabel.getText().toString();
        this.strCmd = this.strCid + "," + this.strPacket + "," + this.strEva + "," + this.strSou + "," + this.strPro + "," + this.strDes + "," + this.strSourcePort + "," + this.strIpsec + "," + this.strType + "," + this.strFlow;
        Log.d(TAG, "Now testing cgt, the setting Cid is: \n cid " + this.strCid + "\n strPacket is " + this.strPacket + "\n strEva is " + this.strEva + "\n strSou is " + this.strSou + "\n strPro is " + this.strPro + "\n strDes is " + this.strDes + "\n strSourcePort is " + this.strSourcePort + "\n strIpsec is " + this.strIpsec + "\n strType is " + this.strType + "\n strFlow is " + this.strFlow + "\n stringCmd is " + this.strCmd);
    }
}
