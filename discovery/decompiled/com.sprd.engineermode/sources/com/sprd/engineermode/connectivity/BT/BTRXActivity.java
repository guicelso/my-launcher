package com.sprd.engineermode.connectivity.BT;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.impl.connectivity.BTHelper;
import com.unisoc.engineermode.core.intf.IConnectivityApi;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class BTRXActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final int MSG_BT_OFF = 3;
    private static final int MSG_BT_RX_READ = 1;
    private static final int MSG_BT_RX_START = 0;
    private static final int MSG_BT_RX_STOP = 2;
    private static final int RESULT_REFRESH = 0;
    private static final String TAG = "BTRXActivity";
    private EditText mAddr;
    private Button mAuto;
    private BTRXHandler mBTRXHandler;
    private ArrayList<BTRXResult> mBTResult;
    private IConnectivityApi.IBtEut.BTRX mBTRx;
    private EditText mChannel;
    private Button mClear;
    private String mCurBer;
    private String mCurBitCnt;
    private String mCurBitErrCnt;
    private String mCurPer;
    private String mCurPktCnt;
    private String mCurPktErrCnt;
    private String mCurRssi;
    private EditText mGain;
    private Spinner mPacType;
    private Spinner mPattern;
    private Button mRead;
    private int mRefreshTime;
    private ListView mResult;
    private BTRXResultAdapter mResultAdapter;
    private BTRXResult mSingleRes;
    private Button mStart;
    private Button mStop;
    private String mRxResult = null;
    private boolean isNewPer = false;
    private boolean isNewBer = false;
    private IConnectivityApi.IBtEut btEut = CoreApi.getConnectivityApi().btEut();
    private Handler mUiThread = new Handler() { // from class: com.sprd.engineermode.connectivity.BT.BTRXActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg == null) {
                Log.d(BTRXActivity.TAG, "UI Message is null");
            } else if (msg.what == 0) {
                BTRXActivity.this.mResultAdapter.result.add((BTRXResult) msg.obj);
                BTRXActivity.this.mResultAdapter.notifyDataSetChanged();
            }
        }
    };
    private final Runnable mRefreshHandler = new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTRXActivity.5
        @Override // java.lang.Runnable
        public void run() {
            Message autoMessage = BTRXActivity.this.mBTRXHandler.obtainMessage(1);
            BTRXActivity.this.mBTRXHandler.sendMessage(autoMessage);
            BTRXActivity.this.mUiThread.postDelayed(BTRXActivity.this.mRefreshHandler, BTRXActivity.this.mRefreshTime);
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_rx);
        getWindow().setSoftInputMode(32);
        initUI();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mBTRx = new IConnectivityApi.IBtEut.BTRX();
        this.mBTRXHandler = new BTRXHandler(ht.getLooper());
        this.mBTResult = new ArrayList<>();
        this.mResultAdapter = new BTRXResultAdapter(this, this.mBTResult);
        this.mResult.setAdapter((ListAdapter) this.mResultAdapter);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.mStop.isEnabled()) {
            Log.d(TAG, "Stop is Enabled when click back");
            Message doMessage = this.mBTRXHandler.obtainMessage(2);
            this.mBTRXHandler.sendMessage(doMessage);
        }
        if (BTHelper.isBTOn) {
            Log.d(TAG, "BT is On when click back");
            Message doMessage2 = this.mBTRXHandler.obtainMessage(3);
            this.mBTRXHandler.sendMessage(doMessage2);
            return;
        }
        super.onBackPressed();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v == this.mStart) {
            if (!getSettingParam()) {
                return;
            }
            Message doMessage = this.mBTRXHandler.obtainMessage(0);
            this.mBTRXHandler.sendMessage(doMessage);
            return;
        }
        if (v == this.mRead) {
            disableSettingPara();
            this.mUiThread.removeCallbacks(this.mRefreshHandler);
            Message doMessage2 = this.mBTRXHandler.obtainMessage(1);
            this.mBTRXHandler.sendMessage(doMessage2);
            return;
        }
        if (v == this.mAuto) {
            disableSettingPara();
            LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
            final View view = inflater.inflate(R.layout.alert_edittext, (ViewGroup) null);
            new AlertDialog.Builder(this).setTitle(getString(R.string.rx_refresh_time)).setView(view).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.BT.BTRXActivity.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    EditText refreshTime = (EditText) view.findViewById(R.id.bt_rx_refresh_time);
                    if (refreshTime.getText().length() != 0) {
                        BTRXActivity.this.mRefreshTime = Integer.valueOf(refreshTime.getText().toString()).intValue();
                        Log.d(BTRXActivity.TAG, "Refresh time is " + BTRXActivity.this.mRefreshTime);
                        BTRXActivity.this.mBTRXHandler.removeMessages(1);
                        BTRXActivity.this.mUiThread.post(BTRXActivity.this.mRefreshHandler);
                        return;
                    }
                    Toast.makeText(BTRXActivity.this, "no refresh time, do nothing", 0).show();
                }
            }).create().show();
            return;
        }
        if (v == this.mStop) {
            this.mUiThread.removeCallbacks(this.mRefreshHandler);
            this.mBTRXHandler.removeMessages(1);
            if (!getSettingParam()) {
                return;
            }
            enableSettingPara();
            Message doMessage3 = this.mBTRXHandler.obtainMessage(2);
            this.mBTRXHandler.sendMessage(doMessage3);
            return;
        }
        if (v == this.mClear) {
            this.mUiThread.removeCallbacks(this.mRefreshHandler);
            this.mBTRXHandler.removeMessages(1);
            this.mResultAdapter.result.clear();
            this.mResultAdapter.notifyDataSetChanged();
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        IConnectivityApi.IBtEut.BTRX btrx = this.mBTRx;
        if (btrx != null) {
            if (parent == this.mPacType) {
                btrx.pactype = getResources().getStringArray(R.array.bt_pac_type_int)[position];
            } else if (parent == this.mPattern) {
                btrx.pattern = getResources().getStringArray(R.array.bt_rx_pattern_int)[position];
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void initUI() {
        this.mPattern = (Spinner) findViewById(R.id.bt_rx_pattern);
        ArrayAdapter<String> patternAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bt_rx_pattern));
        patternAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mPattern.setAdapter((SpinnerAdapter) patternAdapter);
        this.mPattern.setOnItemSelectedListener(this);
        this.mChannel = (EditText) findViewById(R.id.bt_rx_channel);
        this.mChannel.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.BT.BTRXActivity.3
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (BTRXActivity.this.mChannel.getText().length() != 0) {
                    if (Integer.parseInt(BTRXActivity.this.mChannel.getText().toString()) < 0 || Integer.parseInt(BTRXActivity.this.mChannel.getText().toString()) > 78) {
                        Toast.makeText(BTRXActivity.this, "number between 0 and 78", 0).show();
                    }
                }
            }
        });
        this.mPacType = (Spinner) findViewById(R.id.bt_rx_pac_type);
        ArrayAdapter<String> pactype = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bt_pac_type));
        pactype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mPacType.setAdapter((SpinnerAdapter) pactype);
        this.mPacType.setOnItemSelectedListener(this);
        this.mGain = (EditText) findViewById(R.id.bt_rx_gain);
        this.mGain.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.BT.BTRXActivity.4
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (BTRXActivity.this.mGain.getText().length() != 0) {
                    if (Integer.parseInt(BTRXActivity.this.mGain.getText().toString()) < 0 || Integer.parseInt(BTRXActivity.this.mGain.getText().toString()) > 32) {
                        Toast.makeText(BTRXActivity.this, "number between 0 and 32", 0).show();
                    }
                }
            }
        });
        this.mAddr = (EditText) findViewById(R.id.bt_rx_addr);
        this.mResult = (ListView) findViewById(R.id.rx_result_text);
        this.mStart = (Button) findViewById(R.id.bt_rx_start);
        this.mStart.setOnClickListener(this);
        this.mRead = (Button) findViewById(R.id.bt_rx_read);
        this.mRead.setOnClickListener(this);
        this.mAuto = (Button) findViewById(R.id.bt_rx_auto);
        this.mAuto.setOnClickListener(this);
        this.mClear = (Button) findViewById(R.id.bt_rx_clear);
        this.mClear.setOnClickListener(this);
        this.mStop = (Button) findViewById(R.id.bt_rx_stop);
        this.mStop.setOnClickListener(this);
        this.mStop.setEnabled(false);
    }

    private boolean getSettingParam() {
        if (this.mChannel.getText().length() == 0) {
            Toast.makeText(this, "please input TX Channel", 0).show();
            return false;
        }
        this.mBTRx.channel = this.mChannel.getText().toString();
        if (this.mGain.getText().length() == 0) {
            Toast.makeText(this, "please input RX Gain", 0).show();
            return false;
        }
        this.mBTRx.gain = this.mGain.getText().toString();
        if (this.mAddr.getText().length() == 0) {
            Toast.makeText(this, "please input RX Addr", 0).show();
            return false;
        }
        if (this.mAddr.getText().length() < 12) {
            Toast.makeText(this, "please input 12 bits RX Addr", 0).show();
            return false;
        }
        this.mBTRx.addr = this.mAddr.getText().toString().substring(0, 2) + ":" + this.mAddr.getText().toString().substring(2, 4) + ":" + this.mAddr.getText().toString().substring(4, 6) + ":" + this.mAddr.getText().toString().substring(6, 8) + ":" + this.mAddr.getText().toString().substring(8, 10) + ":" + this.mAddr.getText().toString().substring(10, 12);
        StringBuilder sb = new StringBuilder();
        sb.append("Now BT RX Test\nRX Channel is ");
        sb.append(this.mBTRx.channel);
        sb.append("\nRX Pac Type is ");
        sb.append(this.mBTRx.pactype);
        sb.append("\nRX Gain is ");
        sb.append(this.mBTRx.gain);
        sb.append("\nRX Addr is ");
        sb.append(this.mBTRx.addr);
        Log.d(TAG, sb.toString());
        return true;
    }

    private void disableSettingPara() {
        this.mChannel.setEnabled(false);
        this.mPacType.setEnabled(false);
        this.mGain.setEnabled(false);
        this.mAddr.setEnabled(false);
    }

    private void enableSettingPara() {
        this.mChannel.setEnabled(true);
        this.mPacType.setEnabled(true);
        this.mGain.setEnabled(true);
        this.mAddr.setEnabled(true);
    }

    class BTRXHandler extends Handler {
        public BTRXHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Type inference failed for: r5v0 */
        /* JADX WARN: Type inference failed for: r5v1, types: [boolean] */
        /* JADX WARN: Type inference failed for: r5v9 */
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i;
            int i2 = msg.what;
            if (i2 == 0) {
                if (BTRXActivity.this.btEut.BTRxStart(BTRXActivity.this.mBTRx)) {
                    BTRXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTRXActivity.BTRXHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTRXActivity.this, "RX Start Success", 0).show();
                            BTRXActivity.this.mStart.setEnabled(false);
                            BTRXActivity.this.mRead.setEnabled(true);
                            BTRXActivity.this.mAuto.setEnabled(true);
                            BTRXActivity.this.mStop.setEnabled(true);
                        }
                    });
                    return;
                } else {
                    BTRXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTRXActivity.BTRXHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTRXActivity.this, "RX Start Fail", 0).show();
                        }
                    });
                    return;
                }
            }
            ?? r5 = 1;
            char c = 0;
            if (i2 != 1) {
                if (i2 == 2) {
                    if (BTRXActivity.this.btEut.BTRxStop(BTRXActivity.this.mBTRx)) {
                        BTRXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTRXActivity.BTRXHandler.4
                            @Override // java.lang.Runnable
                            public void run() {
                                Toast.makeText(BTRXActivity.this, "RX Stop Success", 0).show();
                                BTRXActivity.this.mStart.setEnabled(true);
                                BTRXActivity.this.mStop.setEnabled(false);
                            }
                        });
                        return;
                    } else {
                        BTRXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTRXActivity.BTRXHandler.5
                            @Override // java.lang.Runnable
                            public void run() {
                                Toast.makeText(BTRXActivity.this, "RX Stop Fail", 0).show();
                            }
                        });
                        return;
                    }
                }
                if (i2 == 3) {
                    if (BTRXActivity.this.btEut.BTOff()) {
                        Toast.makeText(BTRXActivity.this, "BT Off Success", 0).show();
                    } else {
                        Toast.makeText(BTRXActivity.this, "BT Off Fail", 0).show();
                    }
                    BTRXActivity.this.finish();
                    return;
                }
                return;
            }
            BTRXActivity.this.isNewPer = false;
            BTRXActivity.this.isNewBer = false;
            BTRXActivity bTRXActivity = BTRXActivity.this;
            bTRXActivity.mRxResult = bTRXActivity.btEut.BTRxRead();
            Log.d(BTRXActivity.TAG, "RX Read Result is " + BTRXActivity.this.mRxResult);
            if (BTRXActivity.this.mRxResult != null && BTRXActivity.this.mRxResult.contains("OK")) {
                String res = BTRXActivity.this.mRxResult.substring(3);
                Log.d(BTRXActivity.TAG, "res is: " + res);
                String[] res1 = res.split("\\,");
                int i3 = 0;
                while (i3 < res1.length) {
                    String[] res2 = res1[i3].split("\\:");
                    if (res2[c].contains("rssi")) {
                        BTRXActivity.this.mCurRssi = "-" + res2[r5];
                    } else if (res2[c].contains("per")) {
                        BTRXActivity.this.mCurPer = res2[r5];
                    } else if (res2[c].contains("ber")) {
                        BTRXActivity.this.mCurBer = res2[r5];
                    } else if (res2[c].contains("pkt_cnt")) {
                        BTRXActivity.this.mCurPktCnt = res2[r5];
                    } else if (res2[c].contains("pkt_err_cnt")) {
                        BTRXActivity.this.mCurPktErrCnt = res2[r5];
                        if (!BTRXActivity.this.mCurPktErrCnt.equals("0")) {
                            BTRXActivity.this.isNewPer = r5;
                        } else {
                            BTRXActivity.this.mCurPer = "0/" + BTRXActivity.this.mCurPktCnt + "=0%";
                        }
                    } else if (res2[c].contains("bit_cnt")) {
                        BTRXActivity.this.mCurBitCnt = res2[r5];
                    } else if (res2[c].contains("bit_err_cnt")) {
                        BTRXActivity.this.mCurBitErrCnt = res2[r5];
                        if (BTRXActivity.this.mCurBitErrCnt.contains("\u0000")) {
                            Log.d(BTRXActivity.TAG, "mCurBitErrCnt.contains \u0000");
                            String[] value = BTRXActivity.this.mCurBitErrCnt.split("\u0000");
                            Log.d(BTRXActivity.TAG, "value = " + value[c]);
                            BTRXActivity.this.mCurBitErrCnt = value[c];
                        }
                        if (!BTRXActivity.this.mCurBitErrCnt.equals("0")) {
                            BTRXActivity.this.isNewBer = r5;
                        } else {
                            BTRXActivity.this.mCurBer = "0/" + BTRXActivity.this.mCurBitCnt + "=0%";
                        }
                    }
                    if (BTRXActivity.this.isNewPer) {
                        BTRXActivity.this.mCurPer = BTRXActivity.this.mCurPktErrCnt + "/" + BTRXActivity.this.mCurPktCnt + "=";
                        BTRXActivity bTRXActivity2 = BTRXActivity.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append(Math.floor((double) ((((float) Integer.valueOf(BTRXActivity.this.mCurPktErrCnt).intValue()) / ((float) Integer.valueOf(BTRXActivity.this.mCurPktCnt).intValue())) * 1000000.0f)) / 10000.0d);
                        sb.append("%");
                        bTRXActivity2.mCurPer = sb.toString();
                    }
                    if (BTRXActivity.this.isNewBer) {
                        BTRXActivity.this.mCurBer = BTRXActivity.this.mCurBitErrCnt + "/" + BTRXActivity.this.mCurBitCnt + "=";
                        BTRXActivity bTRXActivity3 = BTRXActivity.this;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(Math.floor((double) ((((float) Integer.valueOf(BTRXActivity.this.mCurBitErrCnt).intValue()) / ((float) Integer.valueOf(BTRXActivity.this.mCurBitCnt).intValue())) * 1000000.0f)) / 10000.0d);
                        sb2.append("%");
                        bTRXActivity3.mCurBer = sb2.toString();
                    }
                    i3++;
                    r5 = 1;
                    c = 0;
                }
                if (BTRXActivity.this.mResultAdapter.result.size() % 2 == 0) {
                    BTRXActivity bTRXActivity4 = BTRXActivity.this;
                    bTRXActivity4.mSingleRes = new BTRXResult(bTRXActivity4.mCurRssi, BTRXActivity.this.mCurPer, BTRXActivity.this.mCurBer, true);
                    i = 0;
                } else {
                    BTRXActivity bTRXActivity5 = BTRXActivity.this;
                    i = 0;
                    bTRXActivity5.mSingleRes = new BTRXResult(bTRXActivity5.mCurRssi, BTRXActivity.this.mCurPer, BTRXActivity.this.mCurBer, false);
                }
                Message uiMessage = BTRXActivity.this.mUiThread.obtainMessage(i, BTRXActivity.this.mSingleRes);
                BTRXActivity.this.mUiThread.sendMessage(uiMessage);
                return;
            }
            BTRXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTRXActivity.BTRXHandler.3
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(BTRXActivity.this, "RX Rec Fail", 0).show();
                }
            });
        }
    }
}
