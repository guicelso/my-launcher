package com.sprd.engineermode.connectivity.fm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import com.android.fmradio.FmManagerSelect;
import com.sprd.engineermode.R;

/* JADX INFO: loaded from: classes4.dex */
public class FMRdsBlerActivity extends Activity {
    private static final int GET_BLER = 1;
    private static final String TAG = "FMRdsBlerActivity";
    private static final int TIME = 200;
    private Context mContext;
    private FmManagerSelect mFmManager = null;
    private Handler mHandler = new Handler() { // from class: com.sprd.engineermode.connectivity.fm.FMRdsBlerActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Log.d(FMRdsBlerActivity.TAG, "handleMessage");
                FMRdsBlerActivity.this.mRdsBler.setText(FMRdsBlerActivity.this.getResources().getString(R.string.bler) + ":" + FMRdsBlerActivity.this.mFmManager.getFmBler());
                Message m = obtainMessage(1);
                sendMessageDelayed(m, 200L);
            }
        }
    };
    private TextView mRdsBler;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_rds_bler);
        this.mContext = this;
        this.mFmManager = new FmManagerSelect(this.mContext);
        this.mRdsBler = (TextView) findViewById(R.id.rds_bler);
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(1);
            Log.d(TAG, "HandlerThread has quit");
        }
    }
}
