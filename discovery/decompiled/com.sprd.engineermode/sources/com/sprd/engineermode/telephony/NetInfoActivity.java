package com.sprd.engineermode.telephony;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NetInfoActivity extends Activity {
    private static final int NCELL = 2;
    private static final int SCELL = 1;
    private static final String TAG = "NetInfoActivity";
    private TextView mCCED1;
    private TextView mCCED2;
    private TextView mNCELL;
    private TextView mSCELL;
    private Handler mUiThread = new Handler();
    private String mStrTmp = null;
    private String mStrCCED1 = null;
    private String mStrCCED2 = null;

    static /* synthetic */ String access$484(NetInfoActivity x0, Object x1) {
        String str = x0.mStrCCED2 + x1;
        x0.mStrCCED2 = str;
        return str;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.netinfo);
        this.mNCELL = (TextView) findViewById(R.id.ncell_title);
        this.mSCELL = (TextView) findViewById(R.id.scell_title);
        this.mCCED1 = (TextView) findViewById(R.id.ncell_value);
        this.mCCED2 = (TextView) findViewById(R.id.scell_value);
        new Thread(new Runnable() { // from class: com.sprd.engineermode.telephony.NetInfoActivity.1
            @Override // java.lang.Runnable
            public void run() {
                if (NetInfoActivity.this.checkCurrentNetworkState()) {
                    NetInfoActivity.this.requestEvent("AT+CCED=0,1", "atchannel0");
                    NetInfoActivity.this.requestEvent("AT+CCED=0,2", "atchannel0");
                } else {
                    NetInfoActivity.this.mSCELL.setText("network is unavailable");
                    Log.d(NetInfoActivity.TAG, "network is unavailable");
                }
            }
        }).start();
        this.mSCELL.setText("SCELL");
        this.mNCELL.setText("NCELL");
        this.mSCELL.setTextSize(20.0f);
        this.mCCED1.setTextSize(20.0f);
        this.mNCELL.setTextSize(20.0f);
        this.mCCED2.setTextSize(20.0f);
    }

    void myParseAtVersion(engconstents mAtResponse, String AT, Runnable runner) {
        mAtResponse.ParseAtResponse(AT);
        this.mUiThread.post(runner);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestEvent(final String cmd, String serverName) {
        this.mStrTmp = IATUtils.sendATCmd(cmd, serverName);
        myParseAtVersion(new engconstents() { // from class: com.sprd.engineermode.telephony.NetInfoActivity.2
            @Override // com.unisoc.engineermode.core.common.engconstents
            public void ParseAtResponse(String response) {
                Log.d(NetInfoActivity.TAG, "reponse:" + response);
                if (cmd.compareTo("AT+CCED=0,1") == 0) {
                    if (response.indexOf(",") == -1) {
                        NetInfoActivity.this.mStrCCED1 = "No Value";
                        return;
                    }
                    String[] strs = response.split(",");
                    String[] strs1 = strs[0].split(":");
                    NetInfoActivity.this.mStrCCED1 = strs1[1] + "," + strs[1] + "," + strs[5] + "," + strs[6];
                    Log.d(NetInfoActivity.TAG, NetInfoActivity.this.mStrCCED1);
                    return;
                }
                if (response.indexOf(",") == -1) {
                    NetInfoActivity.this.mStrCCED2 = "No Value";
                    return;
                }
                String[] str = response.split("\n");
                String[] strs2 = str[0].split(",");
                String[] strs3 = strs2[0].split(":");
                strs2[0] = strs3[1];
                int nums = strs2.length / 7;
                NetInfoActivity.this.mStrCCED2 = "";
                for (int i = 0; i < nums; i++) {
                    NetInfoActivity netInfoActivity = NetInfoActivity.this;
                    StringBuilder sb = new StringBuilder(strs2[i * 7]);
                    sb.append(",");
                    sb.append(strs2[(i * 7) + 1]);
                    sb.append(",");
                    sb.append(strs2[(i * 7) + 5]);
                    sb.append(",");
                    sb.append(strs2[(i * 7) + 6]);
                    NetInfoActivity.access$484(netInfoActivity, sb);
                    NetInfoActivity.access$484(NetInfoActivity.this, "\n");
                    Log.d(NetInfoActivity.TAG, NetInfoActivity.this.mStrCCED2);
                }
            }
        }, this.mStrTmp, new Runnable() { // from class: com.sprd.engineermode.telephony.NetInfoActivity.3
            @Override // java.lang.Runnable
            public void run() {
                if (cmd.compareTo("AT+CCED=0,1") == 0) {
                    NetInfoActivity.this.mCCED1.setText(NetInfoActivity.this.mStrCCED1);
                } else {
                    NetInfoActivity.this.mCCED2.setText(NetInfoActivity.this.mStrCCED2);
                }
            }
        });
    }

    private void updateUi(final TextView tv, final String msg) {
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.NetInfoActivity.4
            @Override // java.lang.Runnable
            public void run() {
                NetInfoActivity.this.mSCELL.setText("SCELL");
                NetInfoActivity.this.mNCELL.setText("NCELL");
                NetInfoActivity.this.mSCELL.setTextSize(20.0f);
                NetInfoActivity.this.mCCED1.setTextSize(20.0f);
                NetInfoActivity.this.mNCELL.setTextSize(20.0f);
                NetInfoActivity.this.mCCED2.setTextSize(20.0f);
                tv.setText(msg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkCurrentNetworkState() {
        ConnectivityManager conMan = (ConnectivityManager) getSystemService("connectivity");
        if (conMan.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return true;
    }
}
