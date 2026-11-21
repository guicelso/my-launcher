package com.sprd.engineermode.debuglog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.MotionEventCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManagerGlobal;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class ImeiActivity extends Activity {
    public static final String TAG = "ImeiActivity";
    private Button btn_change_imei;
    private EditText edt_imei_0;
    private EditText edt_imei_1;
    private LinearLayout ll_result;
    private TextView tv_imei_0_result;
    private TextView tv_imei_1_result;
    private TextView tv_tip;
    private TWImei mTW = null;
    private Handler mHandler = new Handler() { // from class: com.sprd.engineermode.debuglog.ImeiActivity.5
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case MotionEventCompat.ACTION_POINTER_INDEX_MASK /* 65280 */:
                        ImeiActivity.this.showWaitingText();
                        break;
                    case 65281:
                        ImeiActivity.this.mTW.write(514, 1, 0);
                        break;
                    case 65282:
                        ImeiActivity.this.btn_change_imei.setText(R.string.quick_writing_restart_1);
                        break;
                    case 65283:
                        ImeiActivity.this.btn_change_imei.setText(R.string.quick_writing_restart_2);
                        break;
                }
            } catch (Exception e) {
            }
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imei);
        this.mTW = TWImei.open();
        this.edt_imei_0 = (EditText) findViewById(R.id.edt_imei_0);
        this.edt_imei_1 = (EditText) findViewById(R.id.edt_imei_1);
        this.tv_tip = (TextView) findViewById(R.id.tv_tip);
        this.btn_change_imei = (Button) findViewById(R.id.btn_change_imei);
        this.ll_result = (LinearLayout) findViewById(R.id.ll_result);
        this.tv_imei_0_result = (TextView) findViewById(R.id.tv_imei_0_result);
        this.tv_imei_1_result = (TextView) findViewById(R.id.tv_imei_1_result);
        this.edt_imei_0.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.debuglog.ImeiActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ImeiActivity.this.tv_tip.setVisibility(0);
                ImeiActivity.this.ll_result.setVisibility(8);
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }
        });
        this.edt_imei_1.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.debuglog.ImeiActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ImeiActivity.this.tv_tip.setVisibility(0);
                ImeiActivity.this.ll_result.setVisibility(8);
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_change_imei) {
            String s0 = this.edt_imei_0.getText().toString();
            String s1 = this.edt_imei_1.getText().toString();
            boolean imei0Success = false;
            boolean imei1Success = false;
            if (s0.length() == 15) {
                String result = IATUtils.sendAt("AT+SPIMEI=0,\"" + s0 + "\"", "atchannel0");
                if (!TextUtils.equals(result, IATUtils.AT_FAIL)) {
                    imei0Success = true;
                }
            }
            if (s1.length() == 15) {
                String result2 = IATUtils.sendAt("AT+SPIMEI=1,\"" + s1 + "\"", "atchannel1");
                if (!TextUtils.equals(result2, IATUtils.AT_FAIL)) {
                    imei1Success = true;
                }
            }
            this.tv_tip.setVisibility(8);
            this.ll_result.setVisibility(0);
            if (imei0Success) {
                this.tv_imei_0_result.setTextColor(-16711936);
                this.tv_imei_0_result.setText(R.string.imei0_correct);
            } else {
                this.tv_imei_0_result.setTextColor(SupportMenu.CATEGORY_MASK);
                this.tv_imei_0_result.setText(R.string.imei0_error);
            }
            if (imei1Success) {
                this.tv_imei_1_result.setTextColor(-16711936);
                this.tv_imei_1_result.setText(R.string.imei1_correct);
            } else {
                this.tv_imei_1_result.setTextColor(SupportMenu.CATEGORY_MASK);
                this.tv_imei_1_result.setText(R.string.imei1_error);
            }
            if (imei0Success || imei1Success) {
                this.btn_change_imei.setEnabled(false);
                this.mHandler.removeMessages(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
                this.mHandler.sendEmptyMessageDelayed(MotionEventCompat.ACTION_POINTER_INDEX_MASK, 500L);
                this.mHandler.removeMessages(65281);
                this.mHandler.sendEmptyMessageDelayed(65281, 8000L);
            }
        }
    }

    private void showRebootDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.quick_settings_reset_ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.ImeiActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                ImeiActivity.this.mTW.write(514, 1, 0);
            }
        });
        builder.setNegativeButton(R.string.quick_settings_reset_cancel, (DialogInterface.OnClickListener) null);
        builder.setMessage(R.string.quick_settings_reset_sure);
        builder.setTitle(R.string.quick_qs_reset_label);
        builder.setCancelable(true);
        Dialog dialog = builder.create();
        dialog.getWindow().setType(2003);
        try {
            WindowManagerGlobal.getWindowManagerService();
        } catch (Exception e) {
        }
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showWaitingText() {
        new Thread(new Runnable() { // from class: com.sprd.engineermode.debuglog.ImeiActivity.4
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < 20; i++) {
                    ImeiActivity.this.mHandler.sendEmptyMessageDelayed(65282, 0L);
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                    }
                    ImeiActivity.this.mHandler.sendEmptyMessageDelayed(65283, 0L);
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e2) {
                    }
                }
            }
        }).start();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        try {
            this.mHandler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            Log.d(TAG, "onDestroy:" + e.getMessage());
        }
        super.onDestroy();
    }
}
