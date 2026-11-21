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
import android.widget.TableRow;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.impl.connectivity.BTHelper;
import com.unisoc.engineermode.core.intf.IConnectivityApi;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class BTBLERXActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final int MSG_BT_BLE_OFF = 3;
    private static final int MSG_BT_BLE_RX_READ = 1;
    private static final int MSG_BT_BLE_RX_START = 0;
    private static final int MSG_BT_BLE_RX_STOP = 2;
    private static final int RESULT_REFRESH = 0;
    private static final String TAG = "BTBLERXActivity";
    private Button mAuto;
    private BTBLERXHandler mBTBLERXHandler;
    private IConnectivityApi.IBtEut.BTRX mBTBLERx;
    private ArrayList<BTBLERXResult> mBTResult;
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
    private TableRow mLeModTableRow;
    private Spinner mLePhy;
    private TableRow mLePhyTableRow;
    private Spinner mModIndex;
    private TableRow mPacTypeTableRow;
    private Spinner mPattern;
    private Button mRead;
    private int mRefreshTime;
    private ListView mResult;
    private BTBLERXResultAdapter mResultAdapter;
    private BTBLERXResult mSingleRes;
    private Button mStart;
    private Button mStop;
    private String mRxResult = null;
    private boolean isNewPer = false;
    private boolean isNewBer = false;
    private IConnectivityApi.IBtEut btEut = CoreApi.getConnectivityApi().btEut();
    private Handler mUiThread = new Handler() { // from class: com.sprd.engineermode.connectivity.BT.BTBLERXActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg == null) {
                Log.d(BTBLERXActivity.TAG, "UI Message is null");
            } else if (msg.what == 0) {
                BTBLERXActivity.this.mResultAdapter.result.add((BTBLERXResult) msg.obj);
                BTBLERXActivity.this.mResultAdapter.notifyDataSetChanged();
            }
        }
    };
    private final Runnable mRefreshHandler = new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTBLERXActivity.5
        @Override // java.lang.Runnable
        public void run() {
            Message autoMessage = BTBLERXActivity.this.mBTBLERXHandler.obtainMessage(1);
            BTBLERXActivity.this.mBTBLERXHandler.sendMessage(autoMessage);
            BTBLERXActivity.this.mUiThread.postDelayed(BTBLERXActivity.this.mRefreshHandler, BTBLERXActivity.this.mRefreshTime);
        }
    };

    static /* synthetic */ String access$1784(BTBLERXActivity x0, Object x1) {
        String str = x0.mCurPer + x1;
        x0.mCurPer = str;
        return str;
    }

    static /* synthetic */ String access$1884(BTBLERXActivity x0, Object x1) {
        String str = x0.mCurBer + x1;
        x0.mCurBer = str;
        return str;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_ble_rx);
        getWindow().setSoftInputMode(32);
        initUI();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mBTBLERx = new IConnectivityApi.IBtEut.BTRX();
        this.mBTBLERXHandler = new BTBLERXHandler(ht.getLooper());
        this.mBTResult = new ArrayList<>();
        this.mResultAdapter = new BTBLERXResultAdapter(this, this.mBTResult);
        this.mResult.setAdapter((ListAdapter) this.mResultAdapter);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.mStop.isEnabled()) {
            Log.d(TAG, "Stop is Enabled when click back");
            Message doMessage = this.mBTBLERXHandler.obtainMessage(2);
            this.mBTBLERXHandler.sendMessage(doMessage);
        }
        if (BTHelper.isBTOn) {
            Log.d(TAG, "BT is On when click back");
            Message doMessage2 = this.mBTBLERXHandler.obtainMessage(3);
            this.mBTBLERXHandler.sendMessage(doMessage2);
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
            Message doMessage = this.mBTBLERXHandler.obtainMessage(0);
            this.mBTBLERXHandler.sendMessage(doMessage);
            return;
        }
        if (v == this.mRead) {
            disableSettingPara();
            this.mUiThread.removeCallbacks(this.mRefreshHandler);
            Message doMessage2 = this.mBTBLERXHandler.obtainMessage(1);
            this.mBTBLERXHandler.sendMessage(doMessage2);
            return;
        }
        if (v == this.mAuto) {
            disableSettingPara();
            LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
            final View view = inflater.inflate(R.layout.alert_edittext, (ViewGroup) null);
            new AlertDialog.Builder(this).setTitle(getString(R.string.rx_refresh_time)).setView(view).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.BT.BTBLERXActivity.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    EditText refreshTime = (EditText) view.findViewById(R.id.bt_rx_refresh_time);
                    if (refreshTime.getText().length() != 0) {
                        BTBLERXActivity.this.mRefreshTime = Integer.valueOf(refreshTime.getText().toString()).intValue();
                        Log.d(BTBLERXActivity.TAG, "Refresh time is " + BTBLERXActivity.this.mRefreshTime);
                        BTBLERXActivity.this.mBTBLERXHandler.removeMessages(1);
                        BTBLERXActivity.this.mUiThread.post(BTBLERXActivity.this.mRefreshHandler);
                        return;
                    }
                    Toast.makeText(BTBLERXActivity.this, "no refresh time, do nothing", 0).show();
                }
            }).create().show();
            return;
        }
        if (v == this.mStop) {
            this.mUiThread.removeCallbacks(this.mRefreshHandler);
            this.mBTBLERXHandler.removeMessages(1);
            if (!getSettingParam()) {
                return;
            }
            enableSettingPara();
            Message doMessage3 = this.mBTBLERXHandler.obtainMessage(2);
            this.mBTBLERXHandler.sendMessage(doMessage3);
            return;
        }
        if (v == this.mClear) {
            this.mUiThread.removeCallbacks(this.mRefreshHandler);
            this.mBTBLERXHandler.removeMessages(1);
            this.mResultAdapter.result.clear();
            this.mResultAdapter.notifyDataSetChanged();
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        IConnectivityApi.IBtEut.BTRX btrx = this.mBTBLERx;
        if (btrx != null) {
            if (parent == this.mPattern) {
                btrx.pattern = getResources().getStringArray(R.array.bt_ble_rx_pattern_int)[position];
            }
            if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                if (parent == this.mModIndex) {
                    this.mBTBLERx.modindex = getResources().getStringArray(R.array.bt_ble_rx_mod_index_int)[position];
                } else if (parent == this.mLePhy) {
                    this.mBTBLERx.lephy = getResources().getStringArray(R.array.bt_ble_rx_le_phy_int)[position];
                }
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void initUI() {
        this.mChannel = (EditText) findViewById(R.id.bt_ble_rx_channel);
        this.mChannel.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.BT.BTBLERXActivity.3
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (BTBLERXActivity.this.mChannel.getText().length() != 0) {
                    if (Integer.parseInt(BTBLERXActivity.this.mChannel.getText().toString()) < 0 || Integer.parseInt(BTBLERXActivity.this.mChannel.getText().toString()) > 39) {
                        Toast.makeText(BTBLERXActivity.this, "number between 0 and 39", 0).show();
                    }
                }
            }
        });
        this.mPattern = (Spinner) findViewById(R.id.bt_ble_rx_pattern);
        ArrayAdapter<String> patternAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bt_ble_rx_pattern));
        patternAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mPattern.setAdapter((SpinnerAdapter) patternAdapter);
        this.mPattern.setOnItemSelectedListener(this);
        this.mModIndex = (Spinner) findViewById(R.id.bt_ble_rx_mod_index);
        ArrayAdapter<String> mModIndexAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bt_ble_rx_mod_index));
        mModIndexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mModIndex.setAdapter((SpinnerAdapter) mModIndexAdapter);
        this.mModIndex.setOnItemSelectedListener(this);
        this.mLePhy = (Spinner) findViewById(R.id.bt_ble_rx_le_phy);
        ArrayAdapter<String> mLePhyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bt_ble_rx_le_phy));
        mLePhyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mLePhy.setAdapter((SpinnerAdapter) mLePhyAdapter);
        this.mLePhy.setOnItemSelectedListener(this);
        this.mLeModTableRow = (TableRow) findViewById(R.id.bt_ble_rx_mod_tablerow);
        this.mLePhyTableRow = (TableRow) findViewById(R.id.bt_ble_rx_phy_tablerow);
        this.mPacTypeTableRow = (TableRow) findViewById(R.id.bt_ble_rx_pactype_tablerow);
        if (Const.isMarlin2()) {
            this.mLeModTableRow.setVisibility(8);
            this.mLePhyTableRow.setVisibility(8);
        }
        this.mPacTypeTableRow.setVisibility(8);
        this.mGain = (EditText) findViewById(R.id.bt_ble_rx_gain);
        this.mGain.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.BT.BTBLERXActivity.4
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (BTBLERXActivity.this.mGain.getText().length() != 0) {
                    if (Integer.parseInt(BTBLERXActivity.this.mGain.getText().toString()) < 0 || Integer.parseInt(BTBLERXActivity.this.mGain.getText().toString()) > 5) {
                        Toast.makeText(BTBLERXActivity.this, "number between 0 and 5", 0).show();
                    }
                }
            }
        });
        this.mResult = (ListView) findViewById(R.id.ble_rx_result_text);
        this.mStart = (Button) findViewById(R.id.bt_ble_rx_start);
        this.mStart.setOnClickListener(this);
        this.mRead = (Button) findViewById(R.id.bt_ble_rx_read);
        this.mRead.setOnClickListener(this);
        this.mAuto = (Button) findViewById(R.id.bt_ble_rx_auto);
        this.mAuto.setOnClickListener(this);
        this.mClear = (Button) findViewById(R.id.bt_ble_rx_clear);
        this.mClear.setOnClickListener(this);
        this.mStop = (Button) findViewById(R.id.bt_ble_rx_stop);
        this.mStop.setOnClickListener(this);
        this.mStop.setEnabled(false);
    }

    private boolean getSettingParam() {
        if (this.mChannel.getText().length() == 0) {
            Toast.makeText(this, "please input BLE TX Channel", 0).show();
            return false;
        }
        this.mBTBLERx.channel = this.mChannel.getText().toString();
        if (this.mGain.getText().length() == 0) {
            Toast.makeText(this, "please input BLE RX Gain", 0).show();
            return false;
        }
        this.mBTBLERx.gain = this.mGain.getText().toString();
        Log.d(TAG, "Now BT BLE RX Test\nBLE RX modindex is " + this.mBTBLERx.modindex + "\nBLE RX lephy is " + this.mBTBLERx.lephy + "\nBLE RX Channel is " + this.mBTBLERx.channel + "\nBLE RX Pac Type is " + this.mBTBLERx.pactype + "\nBLE RX Gain is " + this.mBTBLERx.gain + "\n");
        return true;
    }

    private void disableSettingPara() {
        this.mChannel.setEnabled(false);
    }

    private void enableSettingPara() {
        this.mChannel.setEnabled(true);
        if (Const.isMarlin2()) {
            this.mGain.setEnabled(true);
        }
        if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
            this.mModIndex.setEnabled(true);
            this.mLePhy.setEnabled(true);
        }
    }

    class BTBLERXHandler extends Handler {
        public BTBLERXHandler(Looper looper) {
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
                if (BTBLERXActivity.this.btEut.BTBLERxStart(BTBLERXActivity.this.mBTBLERx)) {
                    BTBLERXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTBLERXActivity.BTBLERXHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Log.d(BTBLERXActivity.TAG, "BLE RX Start Success");
                            BTBLERXActivity.this.mStart.setEnabled(false);
                            Toast.makeText(BTBLERXActivity.this, "BLE RX Start Success", 0).show();
                            if (Const.isMarlin2()) {
                                BTBLERXActivity.this.mRead.setEnabled(true);
                                BTBLERXActivity.this.mAuto.setEnabled(true);
                            }
                            BTBLERXActivity.this.mStop.setEnabled(true);
                        }
                    });
                    return;
                } else {
                    BTBLERXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTBLERXActivity.BTBLERXHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(BTBLERXActivity.this, "BLE RX Start Fail", 0).show();
                        }
                    });
                    return;
                }
            }
            ?? r5 = 1;
            char c = 0;
            if (i2 != 1) {
                if (i2 == 2) {
                    if (BTBLERXActivity.this.btEut.BTBLERxStop(BTBLERXActivity.this.mBTBLERx)) {
                        BTBLERXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTBLERXActivity.BTBLERXHandler.4
                            @Override // java.lang.Runnable
                            public void run() {
                                Toast.makeText(BTBLERXActivity.this, "BLE RX Stop Success", 0).show();
                                BTBLERXActivity.this.mStart.setEnabled(true);
                                BTBLERXActivity.this.mStop.setEnabled(false);
                            }
                        });
                        return;
                    } else {
                        BTBLERXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTBLERXActivity.BTBLERXHandler.5
                            @Override // java.lang.Runnable
                            public void run() {
                                Toast.makeText(BTBLERXActivity.this, "BLE RX Stop Fail", 0).show();
                            }
                        });
                        return;
                    }
                }
                if (i2 == 3) {
                    if (BTBLERXActivity.this.btEut.BTOff()) {
                        Toast.makeText(BTBLERXActivity.this, "BT Off Success", 0).show();
                    } else {
                        Toast.makeText(BTBLERXActivity.this, "BT Off Fail", 0).show();
                    }
                    BTBLERXActivity.this.finish();
                    return;
                }
                return;
            }
            BTBLERXActivity.this.isNewPer = false;
            BTBLERXActivity.this.isNewBer = false;
            BTBLERXActivity bTBLERXActivity = BTBLERXActivity.this;
            bTBLERXActivity.mRxResult = bTBLERXActivity.btEut.BTBLERxRead();
            Log.d(BTBLERXActivity.TAG, "RX Read Result is " + BTBLERXActivity.this.mRxResult);
            if (BTBLERXActivity.this.mRxResult != null && BTBLERXActivity.this.mRxResult.contains("OK")) {
                String res = BTBLERXActivity.this.mRxResult.substring(3);
                Log.d(BTBLERXActivity.TAG, "res is " + res);
                String[] res1 = res.split("\\,");
                int i3 = 0;
                while (i3 < res1.length) {
                    String[] res2 = res1[i3].split("\\:");
                    if (res2[c].contains("rssi")) {
                        BTBLERXActivity.this.mCurRssi = "-" + res2[r5];
                    } else if (res2[c].contains("per")) {
                        BTBLERXActivity.this.mCurPer = res2[r5];
                    } else if (res2[c].contains("ber")) {
                        BTBLERXActivity.this.mCurBer = res2[r5];
                    } else if (res2[c].contains("pkt_cnt")) {
                        BTBLERXActivity.this.mCurPktCnt = res2[r5];
                    } else if (res2[c].contains("pkt_err_cnt")) {
                        BTBLERXActivity.this.mCurPktErrCnt = res2[r5];
                        if (!BTBLERXActivity.this.mCurPktErrCnt.equals("0")) {
                            BTBLERXActivity.this.isNewPer = r5;
                        } else {
                            BTBLERXActivity.this.mCurPer = "0/" + BTBLERXActivity.this.mCurPktCnt + "=0%";
                        }
                    } else if (res2[c].contains("bit_cnt")) {
                        BTBLERXActivity.this.mCurBitCnt = res2[r5];
                    } else if (res2[c].contains("bit_err_cnt")) {
                        BTBLERXActivity.this.mCurBitErrCnt = res2[r5];
                        if (BTBLERXActivity.this.mCurBitErrCnt.contains("\u0000")) {
                            Log.d(BTBLERXActivity.TAG, "mCurBitErrCnt.contains \u0000");
                            String[] value = BTBLERXActivity.this.mCurBitErrCnt.split("\u0000");
                            Log.d(BTBLERXActivity.TAG, "value = " + value[c]);
                            BTBLERXActivity.this.mCurBitErrCnt = value[c];
                        }
                        if (!BTBLERXActivity.this.mCurBitErrCnt.equals("0")) {
                            BTBLERXActivity.this.isNewBer = r5;
                        } else {
                            BTBLERXActivity.this.mCurBer = "0/" + BTBLERXActivity.this.mCurBitCnt + "=0%";
                        }
                    }
                    if (BTBLERXActivity.this.isNewPer) {
                        BTBLERXActivity.this.mCurPer = BTBLERXActivity.this.mCurPktErrCnt + "/" + BTBLERXActivity.this.mCurPktCnt + "=";
                        BTBLERXActivity bTBLERXActivity2 = BTBLERXActivity.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append(Math.floor((double) ((((float) Integer.valueOf(BTBLERXActivity.this.mCurPktErrCnt).intValue()) / ((float) Integer.valueOf(BTBLERXActivity.this.mCurPktCnt).intValue())) * 1000000.0f)) / 10000.0d);
                        sb.append("%");
                        BTBLERXActivity.access$1784(bTBLERXActivity2, sb.toString());
                    }
                    if (BTBLERXActivity.this.isNewBer) {
                        BTBLERXActivity.this.mCurBer = BTBLERXActivity.this.mCurBitErrCnt + "/" + BTBLERXActivity.this.mCurBitCnt + "=";
                        BTBLERXActivity bTBLERXActivity3 = BTBLERXActivity.this;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(Math.floor((double) ((((float) Integer.valueOf(BTBLERXActivity.this.mCurBitErrCnt).intValue()) / ((float) Integer.valueOf(BTBLERXActivity.this.mCurBitCnt).intValue())) * 1000000.0f)) / 10000.0d);
                        sb2.append("%");
                        BTBLERXActivity.access$1884(bTBLERXActivity3, sb2.toString());
                    }
                    i3++;
                    r5 = 1;
                    c = 0;
                }
                if (BTBLERXActivity.this.mResultAdapter.result.size() % 2 == 0) {
                    BTBLERXActivity bTBLERXActivity4 = BTBLERXActivity.this;
                    bTBLERXActivity4.mSingleRes = new BTBLERXResult(bTBLERXActivity4.mCurRssi, BTBLERXActivity.this.mCurPer, true);
                    i = 0;
                } else {
                    BTBLERXActivity bTBLERXActivity5 = BTBLERXActivity.this;
                    i = 0;
                    bTBLERXActivity5.mSingleRes = new BTBLERXResult(bTBLERXActivity5.mCurRssi, BTBLERXActivity.this.mCurPer, false);
                }
                Message uiMessage = BTBLERXActivity.this.mUiThread.obtainMessage(i, BTBLERXActivity.this.mSingleRes);
                BTBLERXActivity.this.mUiThread.sendMessage(uiMessage);
                return;
            }
            BTBLERXActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTBLERXActivity.BTBLERXHandler.3
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(BTBLERXActivity.this, "BLE RX Rec Fail", 0).show();
                }
            });
        }
    }
}
