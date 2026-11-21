package com.sprd.engineermode.telephony;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TextInfoActivity extends Activity {
    private static final int APN_QUERY = 4;
    private static final int FPLMN = 1;
    private static final int NETINFOSTATIS = 3;
    private static final int SIM_INFO = 5;
    static final String TAG = "TextInfoActivity";
    private String mATResponse;
    private String mATResponse1;
    private String mATline;
    private String mAnalysisResponse;
    private int mSocketID;
    private int mStartN;
    private TextView mTextView;
    private TextinfoHandler textinfoHandler;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private String mStrTmp = null;
    private String mStrDisplay = null;
    private Handler mUiThread = new Handler();
    private String mInfo = null;

    static /* synthetic */ String access$384(TextInfoActivity x0, Object x1) {
        String str = x0.mInfo + x1;
        x0.mInfo = str;
        return str;
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textinfo);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.textinfoHandler = new TextinfoHandler(ht.getLooper());
        this.mTextView = (TextView) findViewById(R.id.text_view);
        Intent intent = getIntent();
        this.mStartN = intent.getIntExtra("text_info", 0);
        int i = this.mStartN;
        if (i == 1) {
            setTitle(R.string.sim_forbid_plmn);
            Message m = this.textinfoHandler.obtainMessage(1);
            this.textinfoHandler.sendMessage(m);
            return;
        }
        if (i == 2) {
            setTitle(R.string.sim_equal_plmn);
            new Thread(new Runnable() { // from class: com.sprd.engineermode.telephony.TextInfoActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    TextInfoActivity.this.requestEvent(engconstents.ENG_AT_COPS, "atchannel0");
                }
            }).start();
            return;
        }
        if (i == 3) {
            setTitle("NetInfo Statistics");
            Message m2 = this.textinfoHandler.obtainMessage(3);
            this.textinfoHandler.sendMessage(m2);
        } else if (i == 4) {
            setTitle("APN Query");
            Message m3 = this.textinfoHandler.obtainMessage(4);
            this.textinfoHandler.sendMessage(m3);
        } else if (i == 5) {
            setTitle("SIM Info");
            Message m4 = this.textinfoHandler.obtainMessage(5);
            this.textinfoHandler.sendMessage(m4);
        } else {
            Log.e(TAG, "mStartN:" + this.mStartN);
        }
    }

    void myParseAtVersion(engconstents mAtResponse, String AT, Runnable runner) {
        mAtResponse.ParseAtResponse(AT);
        this.mUiThread.post(runner);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestEvent(String cmd, String serverName) {
        this.mStrTmp = IATUtils.sendATCmd(cmd, serverName);
        myParseAtVersion(new engconstents() { // from class: com.sprd.engineermode.telephony.TextInfoActivity.2
            @Override // com.unisoc.engineermode.core.common.engconstents
            public void ParseAtResponse(String response) {
                Log.d("TAG", "reponse:" + response);
                if (!response.contains("OK")) {
                    TextInfoActivity.this.mStrDisplay = response;
                    return;
                }
                String[] strs = response.split("\n");
                TextInfoActivity.this.mStrDisplay = strs[0];
            }
        }, this.mStrTmp, new Runnable() { // from class: com.sprd.engineermode.telephony.TextInfoActivity.3
            @Override // java.lang.Runnable
            public void run() {
                TextInfoActivity.this.mTextView.setText(TextInfoActivity.this.mStrDisplay);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String paserSimInfo(String atRSP) {
        if (atRSP.contains("OK")) {
            String[] imsi = atRSP.split("\n");
            String infoPasered = "IMSI:" + imsi[0] + "\n";
            String tempStrmcc = atRSP.substring(0, 3);
            String tempStrmnc = atRSP.substring(3, 5);
            return (((infoPasered + "MCC:") + tempStrmcc + "\n") + "MNC:") + tempStrmnc + "\n";
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String paserRPLMN(String atRSP) {
        if (!atRSP.contains("OK")) {
            return "\n";
        }
        String[] tmpStr = atRSP.split("\n");
        String[] tmpStr1 = tmpStr[0].split(":");
        String infoPasered = "RPLMN:" + tmpStr1[1];
        return infoPasered + "\n";
    }

    private String paserFPLMN(String atRSP) {
        String infoPasered = "";
        if (atRSP.contains("OK")) {
            String[] str = atRSP.split(",");
            for (int i = 0; i < 4; i++) {
                int start = i * 6;
                if (str[2].substring(start + 1, start + 2).compareTo("F") != 0) {
                    String infoPasered2 = (((((infoPasered + "MCC:") + str[2].substring(start + 1, start + 2)) + str[2].substring(start + 0, start + 1)) + str[2].substring(start + 3, start + 4)) + " ") + "MNC:";
                    if (str[2].substring(start + 2, start + 3).compareTo("F") != 0) {
                        infoPasered2 = infoPasered2 + str[2].substring(start + 2, start + 3);
                    }
                    infoPasered = ((infoPasered2 + str[2].substring(start + 5, start + 6)) + str[2].substring(start + 4, start + 5)) + "\n";
                }
            }
            return infoPasered;
        }
        return "Error";
    }

    private boolean isUsim(int simIndex) {
        this.mATResponse = IATUtils.sendATCmd("AT+CRSM=192,28539,0,0,15,0,\"3F007FFF\"", "atchannel" + simIndex);
        Log.d(TAG, this.mATResponse);
        if (this.mATResponse.contains("OK")) {
            String[] str = this.mATResponse.split(",");
            if (str[2].substring(0, 1).compareTo("0") == 0 && str[2].substring(1, 2).compareTo("0") == 0 && str[2].substring(2, 3).compareTo("0") == 0) {
                return true;
            }
        }
        return false;
    }

    class TextinfoHandler extends Handler {
        public TextinfoHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) throws Exception {
            int i = msg.what;
            if (i == 1) {
                TextInfoActivity.this.mInfo = "";
                for (int simIndex = 0; simIndex < TelephonyManagerProxy.getPhoneCount(); simIndex++) {
                    try {
                        String plmnInfo = TextInfoActivity.this.teleApi.simFunc().readPlmn(simIndex);
                        TextInfoActivity.access$384(TextInfoActivity.this, String.format("SIM%d:\n%s", Integer.valueOf(simIndex + 1), plmnInfo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (TextInfoActivity.this.mInfo.length() == 0) {
                    TextInfoActivity.this.mInfo = "No sim";
                }
                TextInfoActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TextInfoActivity.TextinfoHandler.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TextInfoActivity.this.mTextView.setText(TextInfoActivity.this.mInfo);
                    }
                });
                return;
            }
            if (i == 3) {
                String plmn = "";
                try {
                    plmn = TextInfoActivity.this.teleApi.netInfo().getPlmn();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                final String plmnInfo2 = plmn;
                TextInfoActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TextInfoActivity.TextinfoHandler.2
                    @Override // java.lang.Runnable
                    public void run() {
                        TextInfoActivity.this.mTextView.setText(plmnInfo2);
                    }
                });
                return;
            }
            if (i == 4) {
                TextInfoActivity.this.mATResponse = IATUtils.sendATCmd(engconstents.ENG_AT_APNQUERY, "atchannel0");
                TextInfoActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TextInfoActivity.TextinfoHandler.3
                    @Override // java.lang.Runnable
                    public void run() {
                        TextInfoActivity.this.mTextView.setText(TextInfoActivity.this.mATResponse);
                    }
                });
                return;
            }
            if (i == 5) {
                TextInfoActivity.this.mInfo = "";
                int i2 = 0;
                int simIndex2 = 1;
                while (i2 < TelephonyManagerProxy.getPhoneCount()) {
                    TextInfoActivity.access$384(TextInfoActivity.this, "SIM" + simIndex2 + " Infos:\n");
                    TextInfoActivity.this.mATResponse = IATUtils.sendATCmd(engconstents.ENG_AT_GETHPLMN, "atchannel" + i2);
                    TextInfoActivity textInfoActivity = TextInfoActivity.this;
                    TextInfoActivity.access$384(textInfoActivity, textInfoActivity.paserSimInfo(textInfoActivity.mATResponse));
                    TextInfoActivity.this.mATResponse1 = IATUtils.sendATCmd(engconstents.ENG_AT_GETRPLMN, "atchannel" + i2);
                    TextInfoActivity textInfoActivity2 = TextInfoActivity.this;
                    TextInfoActivity.access$384(textInfoActivity2, textInfoActivity2.paserRPLMN(textInfoActivity2.mATResponse1));
                    TextInfoActivity.access$384(TextInfoActivity.this, "\n");
                    i2++;
                    simIndex2++;
                }
                TextInfoActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TextInfoActivity.TextinfoHandler.4
                    @Override // java.lang.Runnable
                    public void run() {
                        TextInfoActivity.this.mTextView.setText(TextInfoActivity.this.mInfo);
                    }
                });
            }
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.textinfoHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.textinfoHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
