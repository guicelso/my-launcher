package com.sprd.engineermode.debuglog;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.IDebugLogApi;

/* JADX INFO: loaded from: classes4.dex */
public class SmsCenterNumberActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "SmsCenterNumberActivity";
    private IDebugLogApi debuglogApi = CoreApi.getDebugLogApi();
    private RelativeLayout mLayoutSIM1;
    private RelativeLayout mLayoutSIM2;
    private int mPhoneCount;
    private EditText mSMSCentor1;
    private EditText mSMSCentor2;
    private Button mSetBtn1;
    private Button mSetBtn2;
    private SubscriptionManager mSubscriptionManager;
    private TelephonyManager telephonyManager;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_center_number);
        this.mSubscriptionManager = SubscriptionManager.from(this);
        this.telephonyManager = (TelephonyManager) getSystemService("phone");
        this.mPhoneCount = this.telephonyManager.getPhoneCount();
        this.mSMSCentor1 = (EditText) findViewById(R.id.sms_center1);
        this.mSMSCentor2 = (EditText) findViewById(R.id.sms_center2);
        this.mSetBtn1 = (Button) findViewById(R.id.set_btn1);
        this.mSetBtn1.setOnClickListener(this);
        this.mSetBtn2 = (Button) findViewById(R.id.set_btn2);
        this.mSetBtn2.setOnClickListener(this);
        this.mLayoutSIM1 = (RelativeLayout) findViewById(R.id.sim1_layout);
        this.mLayoutSIM2 = (RelativeLayout) findViewById(R.id.sim2_layout);
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        showSMSCentorNumber();
    }

    private void showSMSCentorNumber() {
        Log.d(TAG, "mPhoneCount: " + this.mPhoneCount);
        for (int i = 0; i < this.mPhoneCount; i++) {
            if (this.telephonyManager.getSimState(i) == 5) {
                Log.d(TAG, "SIM " + i + " SIM_STATE_READY");
                if (i == 0) {
                    this.mLayoutSIM1.setVisibility(0);
                    String smsCentorNum1 = this.debuglogApi.smsCenterNumberApi().getSmsCenterNumber(slotIdToSubId(0));
                    this.mSMSCentor1.setText(smsCentorNum1);
                } else {
                    this.mLayoutSIM2.setVisibility(0);
                    String smsCentorNum2 = this.debuglogApi.smsCenterNumberApi().getSmsCenterNumber(slotIdToSubId(1));
                    this.mSMSCentor2.setText(smsCentorNum2);
                }
            } else {
                Log.d(TAG, "SIM " + i + " SIM_STATE_NOT_READY");
                if (i == 0) {
                    this.mLayoutSIM1.setVisibility(8);
                } else {
                    this.mLayoutSIM2.setVisibility(8);
                }
            }
        }
    }

    public int slotIdToSubId(int phoneId) {
        int subId;
        SubscriptionInfo mSubscriptionInfo = this.mSubscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(phoneId);
        if (mSubscriptionInfo != null) {
            subId = mSubscriptionInfo.getSubscriptionId();
        } else {
            subId = SubscriptionManager.getDefaultSubscriptionId();
        }
        Log.d(TAG, "slotIdToSubId subId : " + subId);
        return subId;
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_btn1 /* 2131297332 */:
                String mCurSMSCentorNum1 = this.mSMSCentor1.getText().toString().trim();
                if (this.debuglogApi.smsCenterNumberApi().setSmsCenterNumber(slotIdToSubId(0), mCurSMSCentorNum1)) {
                    Toast.makeText(this, "Set successful !", 0).show();
                } else {
                    Toast.makeText(this, "Set failed !", 0).show();
                }
                break;
            case R.id.set_btn2 /* 2131297333 */:
                String mCurSMSCentorNum2 = this.mSMSCentor2.getText().toString().trim();
                if (this.debuglogApi.smsCenterNumberApi().setSmsCenterNumber(slotIdToSubId(1), mCurSMSCentorNum2)) {
                    Toast.makeText(this, "Set successful !", 0).show();
                } else {
                    Toast.makeText(this, "Set failed !", 0).show();
                }
                break;
        }
    }
}
