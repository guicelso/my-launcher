package com.sprd.engineermode.telephony;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class CategaryActivity extends Activity {
    private static final int GET_CATEGARY_VALUE = 0;
    private static final String TAG = "CategaryActivity";
    private CategaryHandler mCategaryHandler;
    private TextView txtViewlabel01;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private String cat3Value = "Cat: ";
    private Handler mUiThread = new Handler();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categary_result);
        this.txtViewlabel01 = (TextView) findViewById(R.id.categray_result);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mCategaryHandler = new CategaryHandler(ht.getLooper());
        Message categaryValue = this.mCategaryHandler.obtainMessage(0);
        this.mCategaryHandler.sendMessage(categaryValue);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.mCategaryHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mCategaryHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    class CategaryHandler extends Handler {
        public CategaryHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                try {
                    int cat = CategaryActivity.this.teleApi.radioFunc().getUeCategory();
                    final String catText = "Cat: " + cat;
                    CategaryActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.CategaryActivity.CategaryHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            CategaryActivity.this.txtViewlabel01.setText(catText);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
