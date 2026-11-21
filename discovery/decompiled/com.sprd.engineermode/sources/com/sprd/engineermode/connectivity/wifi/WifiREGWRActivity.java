package com.sprd.engineermode.connectivity.wifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.intf.IConnectivityApi;

/* JADX INFO: loaded from: classes4.dex */
public class WifiREGWRActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String ADDR = "reg_addr";
    private static final int DEINIT_TEST_STATUS = 1;
    private static final int DEINIT_VIEW = 2;
    private static final int DISPLAY_RESULT = 0;
    private static final int INIT_TEST_STATUS = 0;
    private static final int INIT_VIEW = 1;
    private static final String INSMODE_RES = "insmode_result";
    private static final String LENGTH = "reg_length";
    private static final String TAG = "WifiREGWRActivity";
    private static final String TYPE = "reg_type";
    private static final String VALUE = "reg_value";
    private static final int WIFI_REG_READ = 2;
    private static final int WIFI_REG_WRITE = 3;
    private EditText mAddr;
    private AlertDialog mAlertDialog;
    private String mAlertDialogMsg;
    private AlertDialog mCmdAlertDialog;
    private EditText mLength;
    private SharedPreferences mPref;
    private Button mRead;
    private Spinner mType;
    private int mTypePosition;
    private EditText mValue;
    private WifiREGHandler mWifiREGHandler;
    private IConnectivityApi.IWifiEut.WifiREG mWifiReg;
    private Button mWrite;
    private boolean mInsmodeSuccessByEUT = false;
    public Handler mHandler = new Handler() { // from class: com.sprd.engineermode.connectivity.wifi.WifiREGWRActivity.3
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg == null) {
                Log.d(WifiREGWRActivity.TAG, "UI Message is null");
                return;
            }
            int i = msg.what;
            if (i == 0) {
                WifiREGWRActivity.this.mCmdAlertDialog.setMessage(WifiREGWRActivity.this.mAlertDialogMsg);
                if (!WifiREGWRActivity.this.isFinishing()) {
                    WifiREGWRActivity.this.mCmdAlertDialog.show();
                    return;
                }
                return;
            }
            if (i != 1) {
                if (i == 2) {
                    WifiREGWRActivity.this.mAlertDialog.setMessage("Wifi Deinit Fail");
                    if (!WifiREGWRActivity.this.isFinishing()) {
                        WifiREGWRActivity.this.mAlertDialog.show();
                        return;
                    }
                    return;
                }
                return;
            }
            if (msg.arg1 == 1) {
                WifiREGWRActivity.this.enabledView();
                WifiREGWRActivity.this.refreshUI();
            } else {
                WifiREGWRActivity.this.mAlertDialog.setMessage("Wifi Init Fail");
                if (!WifiREGWRActivity.this.isFinishing()) {
                    WifiREGWRActivity.this.mAlertDialog.show();
                }
            }
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_reg_wr);
        getWindow().setSoftInputMode(32);
        this.mInsmodeSuccessByEUT = getIntent().getBooleanExtra(INSMODE_RES, false);
        initUI();
        this.mPref = PreferenceManager.getDefaultSharedPreferences(this);
        this.mAlertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.alert_wifi)).setMessage((CharSequence) null).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.wifi.WifiREGWRActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                WifiREGWRActivity.this.finish();
            }
        }).create();
        this.mCmdAlertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.alert_wifi)).setMessage((CharSequence) null).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.wifi.WifiREGWRActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mWifiREGHandler = new WifiREGHandler(ht.getLooper());
        this.mWifiReg = new IConnectivityApi.IWifiEut.WifiREG();
    }

    @Override // android.app.Activity
    protected void onStart() {
        refreshUI();
        super.onStart();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.mWifiReg != null) {
            SharedPreferences.Editor editor = this.mPref.edit();
            editor.putInt(TYPE, this.mTypePosition);
            editor.putString(ADDR, this.mWifiReg.addr);
            editor.putString(LENGTH, this.mWifiReg.length);
            editor.putString(VALUE, this.mWifiReg.value);
            editor.commit();
        }
        if (this.mWifiREGHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mWifiREGHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        IConnectivityApi.IWifiEut.WifiREG wifiREG = this.mWifiReg;
        if (wifiREG != null && parent == this.mType) {
            wifiREG.type = getResources().getStringArray(R.array.reg_type_str_arr)[position];
            this.mTypePosition = position;
            if (this.mWifiReg.type.equals("mac")) {
                this.mAddr.setHint("please input 0~800");
            } else if (this.mWifiReg.type.equals("phy0") || this.mWifiReg.type.equals("phy1")) {
                this.mAddr.setHint("please input 0~ff");
            } else if (this.mWifiReg.type.equals("rf")) {
                this.mAddr.setHint("please input 0~6ff");
            }
            this.mAddr.setTextColor(-7829368);
        }
    }

    public static boolean isHexNumber(String str) {
        if (str == null) {
            Log.d(TAG, "String is null");
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char cc = str.charAt(i);
            if ((cc < '0' || cc > '9') && ((cc < 'A' || cc > 'F') && (cc < 'a' || cc > 'f'))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            Log.d(TAG, "String is null");
            return false;
        }
        return str.matches("[0-9]+");
    }

    public final boolean isValueBetween(String str) {
        if (str.length() == 0) {
            Log.d(TAG, "string value is empty");
            Toast.makeText(this, "addr can not be empty!", 1).show();
            return false;
        }
        if (str.length() > 5) {
            Log.d(TAG, "string value is too long" + str.length());
            return false;
        }
        if (this.mWifiReg.type.equals("mac")) {
            if (isInteger(str)) {
                Log.d(TAG, "mac is integer");
                int i = Integer.parseInt(str);
                Log.d(TAG, "mac i=" + i);
                if (i >= 0 && i <= 800) {
                    return true;
                }
                Toast.makeText(this, "please input 0~800", 1).show();
                return false;
            }
            Log.d(TAG, "mac is NOT integer");
            return false;
        }
        if (this.mWifiReg.type.equals("phy0") || this.mWifiReg.type.equals("phy1")) {
            if (isHexNumber(str)) {
                Log.d(TAG, "phy is integer");
                int i2 = Integer.parseInt(str, 16);
                Log.d(TAG, "phy i=" + i2);
                if (i2 >= 0 && i2 <= 255) {
                    return true;
                }
                Toast.makeText(this, "please input 0~ff", 1).show();
                return false;
            }
            Log.d(TAG, "phy is NOT integer");
            return false;
        }
        if (!this.mWifiReg.type.equals("rf")) {
            return false;
        }
        if (isHexNumber(str)) {
            Log.d(TAG, "rfis integer");
            int i3 = Integer.parseInt(str, 16);
            Log.d(TAG, "rf i=" + i3);
            if (i3 >= 0 && i3 <= 1791) {
                return true;
            }
            Toast.makeText(this, "please input 0~6ff", 1).show();
            return false;
        }
        Log.d(TAG, "rf is NOT integer");
        return false;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Message doMessage = null;
        getSettingParam();
        if (!isValueBetween(this.mWifiReg.addr)) {
            return;
        }
        if (v == this.mRead) {
            doMessage = this.mWifiREGHandler.obtainMessage(2);
        } else if (v == this.mWrite) {
            doMessage = this.mWifiREGHandler.obtainMessage(3);
        }
        this.mWifiREGHandler.sendMessage(doMessage);
    }

    class WifiREGHandler extends Handler {
        public WifiREGHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                if (!WifiREGWRActivity.this.mInsmodeSuccessByEUT) {
                    WifiREGWRActivity.this.initWifi();
                    return;
                }
                WifiREGWRActivity.this.mWifiReg = new IConnectivityApi.IWifiEut.WifiREG();
                Message uiMessage = WifiREGWRActivity.this.mHandler.obtainMessage(1, 1, 0);
                WifiREGWRActivity.this.mHandler.sendMessage(uiMessage);
                return;
            }
            if (i == 1) {
                if (!WifiEUTHelper.getHelper().removeMod()) {
                    Message uiMessage2 = WifiREGWRActivity.this.mHandler.obtainMessage(2);
                    WifiREGWRActivity.this.mHandler.sendMessage(uiMessage2);
                    return;
                } else {
                    WifiREGWRActivity.this.finish();
                    return;
                }
            }
            if (i == 2) {
                WifiREGWRActivity.this.analysisResult(WifiEUTHelper.getHelper().regr(WifiREGWRActivity.this.mWifiReg));
            } else if (i == 3) {
                WifiREGWRActivity.this.analysisResult(WifiEUTHelper.getHelper().regw(WifiREGWRActivity.this.mWifiReg));
            }
        }
    }

    private void initUI() {
        this.mType = (Spinner) findViewById(R.id.wifi_eut_type);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.reg_type_str_arr));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mType.setAdapter((SpinnerAdapter) typeAdapter);
        this.mType.setOnItemSelectedListener(this);
        this.mAddr = (EditText) findViewById(R.id.wifi_eut_addr);
        this.mAddr.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.wifi.WifiREGWRActivity.4
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (WifiREGWRActivity.this.mWifiReg != null) {
                    WifiREGWRActivity.this.mWifiReg.addr = WifiREGWRActivity.this.mAddr.getText().toString();
                }
            }
        });
        this.mLength = (EditText) findViewById(R.id.wifi_eut_length);
        this.mLength.setText("1");
        this.mLength.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.wifi.WifiREGWRActivity.5
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (WifiREGWRActivity.this.mWifiReg != null) {
                    WifiREGWRActivity.this.mWifiReg.length = WifiREGWRActivity.this.mLength.getText().toString();
                }
            }
        });
        this.mValue = (EditText) findViewById(R.id.wifi_eut_value);
        this.mValue.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.wifi.WifiREGWRActivity.6
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (WifiREGWRActivity.this.mWifiReg != null) {
                    WifiREGWRActivity.this.mWifiReg.value = WifiREGWRActivity.this.mValue.getText().toString();
                }
            }
        });
        this.mRead = (Button) findViewById(R.id.wifi_reg_read);
        this.mRead.setOnClickListener(this);
        this.mWrite = (Button) findViewById(R.id.wifi_reg_write);
        this.mWrite.setOnClickListener(this);
    }

    private void disabledView() {
        this.mType.setEnabled(false);
        this.mAddr.setEnabled(false);
        this.mLength.setEnabled(false);
        this.mValue.setEnabled(false);
        this.mRead.setEnabled(false);
        this.mWrite.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enabledView() {
        this.mType.setEnabled(true);
        this.mAddr.setEnabled(true);
        this.mLength.setEnabled(false);
        this.mValue.setEnabled(true);
        this.mRead.setEnabled(true);
        this.mWrite.setEnabled(true);
    }

    private void getSettingParam() {
        this.mWifiReg.type = getResources().getStringArray(R.array.reg_type_str_arr)[this.mTypePosition];
        this.mWifiReg.addr = this.mAddr.getText().toString();
        this.mWifiReg.length = this.mLength.getText().toString();
        this.mWifiReg.value = this.mValue.getText().toString();
        Log.d(TAG, "Now testing REG in WifiEUT, the setting param is: \n type " + this.mWifiReg.type + "\n addr is " + this.mWifiReg.addr + "\n length is " + this.mWifiReg.length + "\n value is " + this.mWifiReg.value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshUI() {
        this.mTypePosition = this.mPref.getInt(TYPE, 0);
        this.mType.setSelection(this.mTypePosition);
        this.mAddr.setText(this.mPref.getString(ADDR, "0"));
        this.mLength.setText(this.mPref.getString(LENGTH, "1"));
        this.mValue.setText(this.mPref.getString(VALUE, "0"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void analysisResult(String result) {
        this.mAlertDialogMsg = result;
        Message uiMessage = this.mHandler.obtainMessage(0);
        this.mHandler.sendMessage(uiMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initWifi() {
        Message uiMessage;
        if (WifiEUTHelper.getHelper().insMod()) {
            this.mWifiReg = new IConnectivityApi.IWifiEut.WifiREG();
            uiMessage = this.mHandler.obtainMessage(1, 1, 0);
        } else {
            uiMessage = this.mHandler.obtainMessage(1, 0, 0);
        }
        this.mHandler.sendMessage(uiMessage);
    }
}
