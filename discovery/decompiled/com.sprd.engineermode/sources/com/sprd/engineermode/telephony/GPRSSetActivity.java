package com.sprd.engineermode.telephony;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.SubscriptionManagerProxy;
import com.unisoc.engineermode.core.impl.telephony.TelephonyManagerSprd;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class GPRSSetActivity extends Activity implements View.OnClickListener {
    private static final int GET_AUTOATT_STATUS = 8;
    private static final int GPRS_ACTIVATE_PDP = 5;
    private static final int GPRS_ALWAYS_ATTACH = 3;
    private static final int GPRS_ATTACHED = 1;
    private static final int GPRS_CLEAR_DATA = 9;
    private static final int GPRS_DEACTIVATE_PDP = 6;
    private static final int GPRS_DETACHED = 2;
    private static final int GPRS_SEND_DATA = 7;
    private static final int GPRS_STATE_UPDATE = 0;
    private static final int GPRS_WHEN_NEEDED_ATTACH = 4;
    private static final String KEY_SIM_INDEX = "simindex";
    private static final int MAX_PDP_CONTEXT = 3;
    private static final String SET_SEC_PDP = ",1,1,\"2.2.2.2.255.255.0.0\",48,\"65435.65535\",\"65235.65335\"";
    private static final String TAG = "GPRSSetActivity";
    private String[] PDP_context;
    private Button mActiveatePDP;
    private RadioButton mAlwaysAttach;
    private RadioGroup mAlwaysNeededAttach;
    private Button mAttachBt;
    private boolean mAttached;
    private Button mClearData;
    private final int mContextCount;
    private Button mDeactivatePDP;
    private Button mDetachBt;
    private String mDialogMessage;
    private int mExistPDPType;
    private GPRSHandler mGPRSHandler;
    private EditText mInput1;
    private EditText mInput2;
    private String mInputDataContent;
    private String mInputDataLength;
    private RadioButton mNeedAttach;
    private int mPDPType;
    private ArrayAdapter<String> mPDPTypeAdapter;
    private Spinner mPDPTypeSpinner;
    private boolean[] mPdpContextState;
    private Button mSendData;
    private RadioButton mSetFirPdp;
    private RadioGroup mSetPdp;
    private RadioButton mSetPrim;
    private RadioGroup mSetPrimSec;
    private RadioButton mSetSec;
    private RadioButton mSetSecPdp;
    private int mSimIndex = 0;
    private int mTrafficClass;
    private ArrayAdapter<String> mTrafficClassAdapter;
    private Spinner mTrafficClassSpinner;
    private Handler mUiThread;
    private static ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private static final String[] Traffic_class = {"0", "1", "2", "3", "4"};

    public GPRSSetActivity() {
        this.mContextCount = TelephonyManagerSprd.getModemType() == 2 ? 3 : 6;
        this.PDP_context = new String[this.mContextCount];
        this.mUiThread = new Handler() { // from class: com.sprd.engineermode.telephony.GPRSSetActivity.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    if (GPRSSetActivity.this.mAttached) {
                        GPRSSetActivity.this.mActiveatePDP.setEnabled(true);
                        GPRSSetActivity.this.mDeactivatePDP.setEnabled(true);
                        GPRSSetActivity.this.mSendData.setEnabled(true);
                        GPRSSetActivity.this.mClearData.setEnabled(true);
                        GPRSSetActivity.this.mAttachBt.setEnabled(false);
                        GPRSSetActivity.this.mDetachBt.setEnabled(true);
                        GPRSSetActivity.this.updateAdapter();
                        return;
                    }
                    GPRSSetActivity.this.mActiveatePDP.setEnabled(false);
                    GPRSSetActivity.this.mDeactivatePDP.setEnabled(false);
                    GPRSSetActivity.this.mSendData.setEnabled(false);
                    GPRSSetActivity.this.mClearData.setEnabled(false);
                    GPRSSetActivity.this.mAttachBt.setEnabled(true);
                    GPRSSetActivity.this.mDetachBt.setEnabled(false);
                }
            }
        };
        this.mInputDataContent = "";
        this.mAttached = false;
        this.mPdpContextState = null;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < this.mContextCount; i++) {
            this.PDP_context[i] = "PDP Context " + (i + 1);
        }
        setContentView(R.layout.gprs_set);
        this.mAttachBt = (Button) findViewById(R.id.gprs_attach);
        this.mAttachBt.setOnClickListener(this);
        this.mDetachBt = (Button) findViewById(R.id.gprs_deattach);
        this.mDetachBt.setOnClickListener(this);
        this.mAlwaysNeededAttach = (RadioGroup) findViewById(R.id.always_needed_attach);
        this.mAlwaysAttach = (RadioButton) findViewById(R.id.always);
        this.mAlwaysAttach.setOnClickListener(this);
        this.mNeedAttach = (RadioButton) findViewById(R.id.needed);
        this.mNeedAttach.setOnClickListener(this);
        this.mSetPdp = (RadioGroup) findViewById(R.id.radiogroup_pdp_set);
        this.mSetFirPdp = (RadioButton) this.mSetPdp.findViewById(R.id.fir_pdp);
        this.mSetFirPdp.setOnClickListener(this);
        this.mSetSecPdp = (RadioButton) this.mSetPdp.findViewById(R.id.sec_pdp);
        this.mSetSecPdp.setOnClickListener(this);
        this.mSetPrimSec = (RadioGroup) findViewById(R.id.radiogroup_pdp);
        this.mSetPrim = (RadioButton) this.mSetPrimSec.findViewById(R.id.as_prm);
        this.mSetSec = (RadioButton) this.mSetPrimSec.findViewById(R.id.as_sec);
        this.mPDPTypeSpinner = (Spinner) findViewById(R.id.pdp_spinner);
        this.mPDPTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, this.PDP_context);
        this.mPDPTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mPDPTypeSpinner.setAdapter((SpinnerAdapter) this.mPDPTypeAdapter);
        this.mPDPTypeSpinner.setOnItemSelectedListener(new PDPSelectedListener());
        this.mPDPTypeSpinner.setVisibility(0);
        this.mTrafficClassSpinner = (Spinner) findViewById(R.id.traffic_class_spinner);
        this.mTrafficClassAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Traffic_class);
        this.mTrafficClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mTrafficClassSpinner.setAdapter((SpinnerAdapter) this.mTrafficClassAdapter);
        this.mTrafficClassSpinner.setOnItemSelectedListener(new TrafficClassSelectedListener());
        this.mTrafficClassSpinner.setVisibility(0);
        this.mActiveatePDP = (Button) findViewById(R.id.activate_pdp);
        this.mActiveatePDP.setOnClickListener(this);
        this.mDeactivatePDP = (Button) findViewById(R.id.deactivate_pap);
        this.mDeactivatePDP.setOnClickListener(this);
        this.mInput1 = (EditText) findViewById(R.id.edit_data1);
        this.mInput2 = (EditText) findViewById(R.id.edit_data2);
        this.mSendData = (Button) findViewById(R.id.send_data);
        this.mSendData.setOnClickListener(this);
        this.mClearData = (Button) findViewById(R.id.clear_data);
        this.mClearData.setOnClickListener(this);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mGPRSHandler = new GPRSHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    protected void onStart() {
        this.mSimIndex = SubscriptionManagerProxy.getDefaultDataPhoneId();
        if (this.mSimIndex == -1) {
            Toast.makeText(this, "The Wrong simindex " + this.mSimIndex, 0).show();
            finish();
        }
        setTitle(getResources().getString(R.string.gprs_sim_title) + this.mSimIndex);
        Log.d(TAG, "current simindex = " + this.mSimIndex);
        Message getAutoATTStatus = this.mGPRSHandler.obtainMessage(8);
        this.mGPRSHandler.sendMessage(getAutoATTStatus);
        super.onStart();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.mGPRSHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mGPRSHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int pdpContextCount() {
        int count = 0;
        if (this.mPdpContextState != null) {
            int i = 0;
            while (true) {
                boolean[] zArr = this.mPdpContextState;
                if (i >= zArr.length) {
                    break;
                }
                if (zArr[i]) {
                    count++;
                }
                i++;
            }
        }
        return count;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean[] phasePdpContextState(String atRsp) {
        boolean[] phaseResult = new boolean[this.PDP_context.length];
        if (atRsp.contains("OK")) {
            String[] line = atRsp.split("\n");
            for (int i = 0; i < phaseResult.length; i++) {
                Log.d(TAG, "line" + i + "=" + line[i]);
                String[] pdpContextInfo = line[i].split(",");
                if (pdpContextInfo[1] != null && pdpContextInfo[1].contains("1")) {
                    phaseResult[i] = true;
                } else {
                    phaseResult[i] = false;
                }
            }
        }
        return phaseResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateGprsStatus(boolean isAttached, boolean[] isPdpContextActive) {
        this.mAttached = isAttached;
        this.mPdpContextState = isPdpContextActive;
        this.mUiThread.sendEmptyMessage(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateGprsStatus(int position, boolean isActive) {
        boolean[] zArr = this.mPdpContextState;
        if (zArr != null) {
            zArr[position] = isActive;
        }
        this.mUiThread.sendEmptyMessage(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAdapter() {
        int backup = this.mPDPTypeSpinner.getSelectedItemPosition();
        if (this.mPdpContextState == null) {
            this.mPDPTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, this.PDP_context);
            this.mPDPTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.mPDPTypeSpinner.setAdapter((SpinnerAdapter) this.mPDPTypeAdapter);
            this.mPDPTypeSpinner.setSelection(backup);
            return;
        }
        String[] PdpContextWithState = new String[this.PDP_context.length];
        int i = 0;
        while (true) {
            String[] strArr = this.PDP_context;
            if (i < strArr.length) {
                if (this.mPdpContextState[i]) {
                    PdpContextWithState[i] = this.PDP_context[i] + "    Actived";
                } else {
                    PdpContextWithState[i] = strArr[i];
                }
                i++;
            } else {
                this.mPDPTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PdpContextWithState);
                this.mPDPTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.mPDPTypeSpinner.setAdapter((SpinnerAdapter) this.mPDPTypeAdapter);
                this.mPDPTypeSpinner.setSelection(backup);
                return;
            }
        }
    }

    class PDPSelectedListener implements AdapterView.OnItemSelectedListener {
        PDPSelectedListener() {
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            GPRSSetActivity.this.mPDPType = arg2 + 1;
            Log.d(GPRSSetActivity.TAG, "mPDPType is " + GPRSSetActivity.this.mPDPType);
            if (GPRSSetActivity.this.mAttached) {
                if (GPRSSetActivity.this.mPdpContextState[arg2]) {
                    GPRSSetActivity.this.mActiveatePDP.setEnabled(false);
                    GPRSSetActivity.this.mDeactivatePDP.setEnabled(true);
                    GPRSSetActivity.this.mSendData.setEnabled(true);
                    GPRSSetActivity.this.mClearData.setEnabled(true);
                    return;
                }
                GPRSSetActivity.this.mActiveatePDP.setEnabled(true);
                GPRSSetActivity.this.mDeactivatePDP.setEnabled(false);
                GPRSSetActivity.this.mSendData.setEnabled(false);
                GPRSSetActivity.this.mClearData.setEnabled(false);
            }
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    class TrafficClassSelectedListener implements AdapterView.OnItemSelectedListener {
        TrafficClassSelectedListener() {
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            GPRSSetActivity.this.mTrafficClass = arg2;
            Log.d(GPRSSetActivity.TAG, "mTrafficClass is " + GPRSSetActivity.this.mTrafficClass);
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v.equals(this.mAttachBt)) {
            Message setAttach = this.mGPRSHandler.obtainMessage(1);
            this.mGPRSHandler.sendMessage(setAttach);
            return;
        }
        if (v.equals(this.mAlwaysAttach)) {
            Message setAlways = this.mGPRSHandler.obtainMessage(3);
            this.mGPRSHandler.sendMessage(setAlways);
            return;
        }
        if (v.equals(this.mNeedAttach)) {
            Message setNeeded = this.mGPRSHandler.obtainMessage(4);
            this.mGPRSHandler.sendMessage(setNeeded);
            return;
        }
        if (v.equals(this.mDetachBt)) {
            Message setDetach = this.mGPRSHandler.obtainMessage(2);
            this.mGPRSHandler.sendMessage(setDetach);
            return;
        }
        if (v.equals(this.mSetFirPdp)) {
            this.mSetPrim.setEnabled(false);
            this.mSetSec.setEnabled(false);
            return;
        }
        if (v.equals(this.mSetSecPdp)) {
            this.mSetPrim.setEnabled(true);
            this.mSetPrim.setChecked(true);
            this.mSetSec.setEnabled(true);
            return;
        }
        if (v.equals(this.mActiveatePDP)) {
            this.mActiveatePDP.setEnabled(false);
            Message activatePDP = this.mGPRSHandler.obtainMessage(5);
            this.mGPRSHandler.sendMessage(activatePDP);
            return;
        }
        if (v.equals(this.mDeactivatePDP)) {
            this.mDeactivatePDP.setEnabled(false);
            Message deActivatePDP = this.mGPRSHandler.obtainMessage(6);
            this.mGPRSHandler.sendMessage(deActivatePDP);
            return;
        }
        if (v.equals(this.mSendData)) {
            if (this.mInput1.getText().toString().equals("")) {
                Toast.makeText(this, "please input data length", 0).show();
            } else if (Integer.valueOf(this.mInput1.getText().toString()).intValue() >= 1 && Integer.valueOf(this.mInput1.getText().toString()).intValue() <= 16000) {
                Message sendData = this.mGPRSHandler.obtainMessage(7);
                this.mGPRSHandler.sendMessage(sendData);
            } else {
                Toast.makeText(this, "please input 1 ~ 16000", 0).show();
            }
        }
        if (v.equals(this.mClearData)) {
            Message clearData = this.mGPRSHandler.obtainMessage(9);
            this.mGPRSHandler.sendMessage(clearData);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearEditText() {
        this.mInput1.setText("");
        this.mInput2.setText("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showResult(final String message) {
        if (message != null) {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GPRSSetActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(GPRSSetActivity.this, message, 0).show();
                }
            });
        }
    }

    private boolean isPDPExist(int i) {
        return this.mPdpContextState[i];
    }

    private boolean isValidPDP(int setPDP) {
        if (setPDP != this.mExistPDPType) {
            return true;
        }
        return false;
    }

    class GPRSHandler extends Handler {
        public GPRSHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        GPRSSetActivity.teleApi.gprsOper().attach(GPRSSetActivity.this.mSimIndex);
                        GPRSSetActivity.this.mDialogMessage = "GPRS Attached Success";
                        GPRSSetActivity.this.updateGprsStatus(true, GPRSSetActivity.teleApi.gprsOper().getPdpContextState(GPRSSetActivity.this.mSimIndex));
                        break;
                    } catch (Exception e) {
                        GPRSSetActivity.this.mDialogMessage = "GPRS Attached Failed";
                    }
                    GPRSSetActivity gPRSSetActivity = GPRSSetActivity.this;
                    gPRSSetActivity.showResult(gPRSSetActivity.mDialogMessage);
                    return;
                case 2:
                    try {
                        GPRSSetActivity.teleApi.gprsOper().detach(GPRSSetActivity.this.mSimIndex);
                        GPRSSetActivity.this.mDialogMessage = "GPRS Detached Success";
                        GPRSSetActivity.this.updateGprsStatus(false, GPRSSetActivity.teleApi.gprsOper().getPdpContextState(GPRSSetActivity.this.mSimIndex));
                        break;
                    } catch (Exception e2) {
                        GPRSSetActivity.this.mDialogMessage = "GPRS Detached Failed";
                    }
                    GPRSSetActivity gPRSSetActivity2 = GPRSSetActivity.this;
                    gPRSSetActivity2.showResult(gPRSSetActivity2.mDialogMessage);
                    return;
                case 3:
                    try {
                        GPRSSetActivity.teleApi.gprsOper().setAlwaysAttach(GPRSSetActivity.this.mSimIndex);
                        GPRSSetActivity.this.mDialogMessage = "GPRS Always Attach Success";
                        GPRSSetActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GPRSSetActivity.GPRSHandler.1
                            @Override // java.lang.Runnable
                            public void run() {
                                GPRSSetActivity.this.mAttachBt.setEnabled(false);
                                GPRSSetActivity.this.mDetachBt.setEnabled(false);
                            }
                        });
                        GPRSSetActivity.this.updateGprsStatus(true, GPRSSetActivity.teleApi.gprsOper().getPdpContextState(GPRSSetActivity.this.mSimIndex));
                        break;
                    } catch (Exception e3) {
                        GPRSSetActivity.this.mDialogMessage = "GPRS Always Attach Failed";
                    }
                    GPRSSetActivity gPRSSetActivity3 = GPRSSetActivity.this;
                    gPRSSetActivity3.showResult(gPRSSetActivity3.mDialogMessage);
                    return;
                case 4:
                    try {
                        GPRSSetActivity.teleApi.gprsOper().setNeededAttach(GPRSSetActivity.this.mSimIndex);
                        GPRSSetActivity.this.mDialogMessage = "GPRS When Needed Attach Success";
                        GPRSSetActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GPRSSetActivity.GPRSHandler.2
                            @Override // java.lang.Runnable
                            public void run() {
                                GPRSSetActivity.this.mAttachBt.setEnabled(false);
                                GPRSSetActivity.this.mDetachBt.setEnabled(false);
                            }
                        });
                        GPRSSetActivity.this.updateGprsStatus(true, GPRSSetActivity.teleApi.gprsOper().getPdpContextState(GPRSSetActivity.this.mSimIndex));
                        break;
                    } catch (Exception e4) {
                        GPRSSetActivity.this.mDialogMessage = "GPRS When Needed Attach Failed";
                    }
                    GPRSSetActivity gPRSSetActivity4 = GPRSSetActivity.this;
                    gPRSSetActivity4.showResult(gPRSSetActivity4.mDialogMessage);
                    return;
                case 5:
                    if (GPRSSetActivity.this.pdpContextCount() >= 3) {
                        GPRSSetActivity.this.mDialogMessage = "Reach the Max! Cannot active!";
                        GPRSSetActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GPRSSetActivity.GPRSHandler.3
                            @Override // java.lang.Runnable
                            public void run() {
                                GPRSSetActivity.this.mActiveatePDP.setEnabled(true);
                            }
                        });
                    } else if (GPRSSetActivity.this.mPdpContextState[GPRSSetActivity.this.mPDPType - 1]) {
                        GPRSSetActivity.this.mDialogMessage = "This same CID PDP exist";
                    } else if (GPRSSetActivity.this.mSetFirPdp.isChecked() || (GPRSSetActivity.this.mSetSecPdp.isChecked() && GPRSSetActivity.this.mSetPrim.isChecked())) {
                        if (GPRSSetActivity.teleApi.gprsOper().activateFirstPdp(GPRSSetActivity.this.mSimIndex, GPRSSetActivity.this.mTrafficClass, GPRSSetActivity.this.mPDPType)) {
                            GPRSSetActivity.this.mDialogMessage = "GPRS Activate PDP Success";
                            GPRSSetActivity gPRSSetActivity5 = GPRSSetActivity.this;
                            gPRSSetActivity5.updateGprsStatus(gPRSSetActivity5.mPDPType - 1, true);
                        } else {
                            GPRSSetActivity.this.mDialogMessage = "GPRS Activate PDP Failed";
                            GPRSSetActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GPRSSetActivity.GPRSHandler.4
                                @Override // java.lang.Runnable
                                public void run() {
                                    GPRSSetActivity.this.mActiveatePDP.setEnabled(true);
                                }
                            });
                        }
                    } else if (GPRSSetActivity.this.mSetSecPdp.isChecked() && GPRSSetActivity.this.mSetSec.isChecked()) {
                        if (GPRSSetActivity.this.pdpContextCount() != 0) {
                            if (GPRSSetActivity.teleApi.gprsOper().activateSecondPdp(GPRSSetActivity.this.mSimIndex, GPRSSetActivity.this.mPDPType)) {
                                GPRSSetActivity gPRSSetActivity6 = GPRSSetActivity.this;
                                gPRSSetActivity6.updateGprsStatus(gPRSSetActivity6.mPDPType - 1, true);
                            }
                        } else {
                            GPRSSetActivity.this.mDialogMessage = "NO PDP Activiated";
                            GPRSSetActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GPRSSetActivity.GPRSHandler.5
                                @Override // java.lang.Runnable
                                public void run() {
                                    GPRSSetActivity.this.mActiveatePDP.setEnabled(true);
                                }
                            });
                        }
                    }
                    GPRSSetActivity gPRSSetActivity7 = GPRSSetActivity.this;
                    gPRSSetActivity7.showResult(gPRSSetActivity7.mDialogMessage);
                    return;
                case 6:
                    if (GPRSSetActivity.teleApi.gprsOper().deactivatePdp(GPRSSetActivity.this.mSimIndex, GPRSSetActivity.this.mPDPType)) {
                        GPRSSetActivity.this.mDialogMessage = "GPRS DeActivate PDP Success";
                        GPRSSetActivity gPRSSetActivity8 = GPRSSetActivity.this;
                        gPRSSetActivity8.updateGprsStatus(gPRSSetActivity8.mPDPType - 1, false);
                    } else {
                        GPRSSetActivity.this.mDialogMessage = "GPRS DeActivate PDP Failed";
                        GPRSSetActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GPRSSetActivity.GPRSHandler.6
                            @Override // java.lang.Runnable
                            public void run() {
                                GPRSSetActivity.this.mDeactivatePDP.setEnabled(true);
                            }
                        });
                    }
                    GPRSSetActivity gPRSSetActivity9 = GPRSSetActivity.this;
                    gPRSSetActivity9.showResult(gPRSSetActivity9.mDialogMessage);
                    return;
                case 7:
                    GPRSSetActivity gPRSSetActivity10 = GPRSSetActivity.this;
                    gPRSSetActivity10.mInputDataLength = gPRSSetActivity10.mInput1.getText().toString().trim();
                    GPRSSetActivity gPRSSetActivity11 = GPRSSetActivity.this;
                    gPRSSetActivity11.mInputDataContent = gPRSSetActivity11.mInput2.getText().toString();
                    if (GPRSSetActivity.teleApi.gprsOper().sendData(GPRSSetActivity.this.mSimIndex, GPRSSetActivity.this.mPDPType, GPRSSetActivity.this.mInputDataLength, GPRSSetActivity.this.mInputDataContent)) {
                        GPRSSetActivity.this.mDialogMessage = "GPRS Send Data Success";
                    } else {
                        GPRSSetActivity.this.mDialogMessage = "GPRS Send Data Failed";
                    }
                    GPRSSetActivity gPRSSetActivity12 = GPRSSetActivity.this;
                    gPRSSetActivity12.showResult(gPRSSetActivity12.mDialogMessage);
                    return;
                case 8:
                    break;
                case 9:
                    GPRSSetActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GPRSSetActivity.GPRSHandler.7
                        @Override // java.lang.Runnable
                        public void run() {
                            GPRSSetActivity.this.clearEditText();
                        }
                    });
                    break;
                default:
                    return;
            }
            try {
                boolean isAutoAttach = GPRSSetActivity.teleApi.gprsOper().getAutoAttachState(GPRSSetActivity.this.mSimIndex);
                if (isAutoAttach) {
                    GPRSSetActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GPRSSetActivity.GPRSHandler.9
                        @Override // java.lang.Runnable
                        public void run() {
                            GPRSSetActivity.this.mAlwaysAttach.setChecked(true);
                            GPRSSetActivity.this.mNeedAttach.setChecked(false);
                        }
                    });
                    GPRSSetActivity gPRSSetActivity13 = GPRSSetActivity.this;
                    gPRSSetActivity13.updateGprsStatus(true, gPRSSetActivity13.phasePdpContextState(IATUtils.sendATCmd(engconstents.ENG_AT_GETPDPACTIVE1, "atchannel" + GPRSSetActivity.this.mSimIndex)));
                    return;
                }
                GPRSSetActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GPRSSetActivity.GPRSHandler.10
                    @Override // java.lang.Runnable
                    public void run() {
                        GPRSSetActivity.this.mAlwaysAttach.setChecked(false);
                        GPRSSetActivity.this.mNeedAttach.setChecked(true);
                    }
                });
                GPRSSetActivity gPRSSetActivity14 = GPRSSetActivity.this;
                gPRSSetActivity14.updateGprsStatus(true, gPRSSetActivity14.phasePdpContextState(IATUtils.sendATCmd(engconstents.ENG_AT_GETPDPACTIVE1, "atchannel" + GPRSSetActivity.this.mSimIndex)));
            } catch (Exception e5) {
                e5.printStackTrace();
                GPRSSetActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.GPRSSetActivity.GPRSHandler.8
                    @Override // java.lang.Runnable
                    public void run() {
                        GPRSSetActivity.this.mAlwaysAttach.setChecked(true);
                        GPRSSetActivity.this.mNeedAttach.setChecked(false);
                    }
                });
            }
        }
    }
}
