package com.sprd.engineermode.telephony.netinfo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes4.dex */
public class Cdma1XShowActivity extends Activity {
    private static final int GET_ADJACENT = 1;
    private static final int GET_OUTFIELD_NETWORK = 2;
    private static final int GET_SERVING = 0;
    private static final int KEY_ADJACENT = 1;
    private static final int KEY_OUTFIELD_NETWORK = 2;
    private static final int KEY_SERVING = 0;
    private static final String KEY_SIM_INDEX = "simindex";
    private static final int NETWORK_GSM = 1;
    private static final int NETWORK_LTE = 4;
    private static final String NETWORK_STAT_CHANGE = "com.sprd.network.NETWORK_STAT_CHANGE";
    private static final int NETWORK_TDSCDMA = 2;
    private static final String NETWORK_TYPE = "NetWorkType";
    private static final int NETWORK_UNKNOW = 0;
    private static final int NETWORK_WCDMA = 3;
    private static final String PREF_INDEX = "PrefenceIndex";
    private static final String TAG = "Cdma1XShowActivity";
    private static final int UPDATE_PERIOD = 2000;
    private static final Object mLock = new Object();
    public int COL;
    public int ROW;
    public boolean isRoaming;
    private ListView listView;
    private int mCheckId;
    public String mNetworkOperator;
    public String mNetworkOperatorName;
    private int mSimIndex;
    private int mSubId;
    private String[][] mTextValue;
    private Timer mTimer;
    private WcdmaHandler mWcdmaHandler;
    private ListView mlistviewOperators;
    TextView[][] mtextView;
    private String[] values;
    private int[][] viewID;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private String SaveName = "atchannel";
    private Handler mUiThread = new Handler();
    ArrayAdapter<String> adapter = null;
    private int[] activityLab = {R.string.netinfo_server_cell, R.string.netinfo_neighbour_cell};
    private BroadcastReceiver mNetWorkReceiver = new BroadcastReceiver() { // from class: com.sprd.engineermode.telephony.netinfo.Cdma1XShowActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String mIntentAction;
            int netWorkStat = intent.getIntExtra(Cdma1XShowActivity.NETWORK_TYPE, 0);
            int phoneId = intent.getIntExtra("simindex", -1);
            Log.d(Cdma1XShowActivity.TAG, "NetWork state is " + netWorkStat);
            synchronized (Cdma1XShowActivity.mLock) {
                if (netWorkStat != 3) {
                    if (phoneId == Cdma1XShowActivity.this.mSimIndex) {
                        if (netWorkStat == 1) {
                            mIntentAction = "android.engineermode.action.GSMSHOW";
                        } else if (netWorkStat == 2) {
                            mIntentAction = "android.engineermode.action.TDSCDMASHOW";
                        } else if (netWorkStat != 4) {
                            return;
                        } else {
                            mIntentAction = "android.engineermode.action.LTESHOW";
                        }
                        Cdma1XShowActivity.this.unregisterReceiver();
                        if (Cdma1XShowActivity.this.mTimer != null) {
                            Cdma1XShowActivity.this.mTimer.cancel();
                        }
                        Bundle data = new Bundle();
                        data.putInt(Cdma1XShowActivity.PREF_INDEX, Cdma1XShowActivity.this.mCheckId);
                        data.putInt("SubId", Cdma1XShowActivity.this.mSubId);
                        data.putInt("simindex", Cdma1XShowActivity.this.mSimIndex);
                        Intent mIntent = new Intent(mIntentAction);
                        mIntent.putExtras(data);
                        Cdma1XShowActivity.this.startActivity(mIntent);
                        Cdma1XShowActivity.this.finish();
                    }
                }
            }
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        if (data == null) {
            return;
        }
        this.mCheckId = data.getInt(PREF_INDEX, -1);
        this.mSimIndex = data.getInt("simindex", -1);
        this.mSubId = data.getInt("SubId", -1);
        Log.d(TAG, "mCheckId is: " + this.mCheckId + "mSimIndex is: " + this.mSimIndex + "SubId: " + this.mSubId);
        registerReceiver();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mWcdmaHandler = new WcdmaHandler(ht.getLooper());
        if (this.mCheckId != -1 && this.mSimIndex != -1) {
            this.mNetworkOperatorName = TelephonyManagerProxy.getNetworkOperatorName(this.mSubId);
            this.mNetworkOperator = TelephonyManagerProxy.getNetworkOperatorForPhone(this.mSimIndex);
            this.isRoaming = TelephonyManagerProxy.isNetworkRoaming(this.mSubId);
            Log.d(TAG, "NetworkOperatorName: " + this.mNetworkOperatorName + "NetworkOperator: " + this.mNetworkOperator + "isRoaming: " + this.isRoaming);
            setTitle(this.activityLab[this.mCheckId]);
            StringBuilder sb = new StringBuilder();
            sb.append("atchannel");
            sb.append(this.mSimIndex);
            this.SaveName = sb.toString();
            int i = this.mCheckId;
            if (i == 0) {
                setContentView(R.layout.netinfo_listview_show);
                this.listView = (ListView) findViewById(R.id.netinfo_listview);
                this.values = getResources().getStringArray(R.array.c2k_1x_sering_name);
                this.adapter = new ArrayAdapter<>(this, R.layout.array_item, this.values);
                this.listView.setAdapter((ListAdapter) this.adapter);
                this.mlistviewOperators = (ListView) findViewById(R.id.netinfo_operators_listview);
                String[] values = getResources().getStringArray(R.array.operators_sering_name);
                if (this.mNetworkOperator != null) {
                    values[0] = values[0] + ": " + this.mNetworkOperator;
                } else {
                    values[0] = values[0] + ": NA";
                }
                if (this.mNetworkOperatorName != null) {
                    values[1] = values[1] + ": " + this.mNetworkOperatorName;
                } else {
                    values[1] = values[1] + ": NA";
                }
                if (this.isRoaming) {
                    values[2] = values[2] + ": " + getResources().getString(R.string.is_roaming);
                } else {
                    values[2] = values[2] + ": " + getResources().getString(R.string.non_roaming);
                }
                ArrayAdapter<String> operatorsAdapter = new ArrayAdapter<>(this, R.layout.array_item, values);
                this.mlistviewOperators.setAdapter((ListAdapter) operatorsAdapter);
                Message serving = this.mWcdmaHandler.obtainMessage(0);
                this.mWcdmaHandler.sendMessage(serving);
            } else if (i == 1) {
                setContentView(R.layout.netinfo_adjacent_1x);
                TextView mTextViewPsc = (TextView) findViewById(R.id.netinfo_adjacent_3g_label02);
                mTextViewPsc.setText(R.string.netinfo_psc);
                this.ROW = 10;
                this.COL = 3;
                this.mtextView = (TextView[][]) Array.newInstance((Class<?>) TextView.class, this.ROW, this.COL);
                this.mTextValue = (String[][]) Array.newInstance((Class<?>) String.class, this.ROW, this.COL);
                this.viewID = (int[][]) Array.newInstance((Class<?>) int.class, this.ROW, this.COL);
                int[][] iArr = this.viewID;
                iArr[0][0] = R.id.netinfo_adjacent_3g_label11;
                iArr[0][1] = R.id.netinfo_adjacent_3g_label12;
                iArr[0][2] = R.id.netinfo_adjacent_3g_label13;
                iArr[1][0] = R.id.netinfo_adjacent_3g_label21;
                iArr[1][1] = R.id.netinfo_adjacent_3g_label22;
                iArr[1][2] = R.id.netinfo_adjacent_3g_label23;
                iArr[2][0] = R.id.netinfo_adjacent_3g_label31;
                iArr[2][1] = R.id.netinfo_adjacent_3g_label32;
                iArr[2][2] = R.id.netinfo_adjacent_3g_label33;
                iArr[3][0] = R.id.netinfo_adjacent_3g_label41;
                iArr[3][1] = R.id.netinfo_adjacent_3g_label42;
                iArr[3][2] = R.id.netinfo_adjacent_3g_label43;
                iArr[4][0] = R.id.netinfo_adjacent_3g_label51;
                iArr[4][1] = R.id.netinfo_adjacent_3g_label52;
                iArr[4][2] = R.id.netinfo_adjacent_3g_label53;
                iArr[5][0] = R.id.netinfo_adjacent_3g_label61;
                iArr[5][1] = R.id.netinfo_adjacent_3g_label62;
                iArr[5][2] = R.id.netinfo_adjacent_3g_label63;
                iArr[6][0] = R.id.netinfo_adjacent_3g_label71;
                iArr[6][1] = R.id.netinfo_adjacent_3g_label72;
                iArr[6][2] = R.id.netinfo_adjacent_3g_label73;
                iArr[7][0] = R.id.netinfo_adjacent_3g_label81;
                iArr[7][1] = R.id.netinfo_adjacent_3g_label82;
                iArr[7][2] = R.id.netinfo_adjacent_3g_label83;
                iArr[8][0] = R.id.netinfo_adjacent_3g_label91;
                iArr[8][1] = R.id.netinfo_adjacent_3g_label92;
                iArr[8][2] = R.id.netinfo_adjacent_3g_label93;
                iArr[9][0] = R.id.netinfo_adjacent_3g_label101;
                iArr[9][1] = R.id.netinfo_adjacent_3g_label102;
                iArr[9][2] = R.id.netinfo_adjacent_3g_label103;
                for (int i2 = 0; i2 < this.ROW; i2++) {
                    for (int j = 0; j < this.COL; j++) {
                        this.mtextView[i2][j] = (TextView) findViewById(this.viewID[i2][j]);
                    }
                }
                Message adjacent = this.mWcdmaHandler.obtainMessage(1);
                this.mWcdmaHandler.sendMessage(adjacent);
            } else if (i == 2) {
                setContentView(R.layout.netinfo_outfield_show);
                this.listView = (ListView) findViewById(R.id.netinfo_outfield_listview);
                Message outfieldNetwork = this.mWcdmaHandler.obtainMessage(2);
                this.mWcdmaHandler.sendMessage(outfieldNetwork);
            }
            this.mTimer = new Timer();
            this.mTimer.schedule(new TimerTask() { // from class: com.sprd.engineermode.telephony.netinfo.Cdma1XShowActivity.2
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    int i3 = Cdma1XShowActivity.this.mCheckId;
                    if (i3 == 0) {
                        Message serving2 = Cdma1XShowActivity.this.mWcdmaHandler.obtainMessage(0);
                        Cdma1XShowActivity.this.mWcdmaHandler.sendMessage(serving2);
                    } else if (i3 == 1) {
                        Message adjacent2 = Cdma1XShowActivity.this.mWcdmaHandler.obtainMessage(1);
                        Cdma1XShowActivity.this.mWcdmaHandler.sendMessage(adjacent2);
                    } else if (i3 == 2) {
                        Message outfieldNetwork2 = Cdma1XShowActivity.this.mWcdmaHandler.obtainMessage(2);
                        Cdma1XShowActivity.this.mWcdmaHandler.sendMessage(outfieldNetwork2);
                    }
                }
            }, 0L, 2000L);
            return;
        }
        WcdmaHandler wcdmaHandler = this.mWcdmaHandler;
        if (wcdmaHandler != null) {
            wcdmaHandler.getLooper().quit();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
        }
        WcdmaHandler wcdmaHandler = this.mWcdmaHandler;
        if (wcdmaHandler != null) {
            wcdmaHandler.getLooper().quit();
        }
        unregisterReceiver();
        super.onDestroy();
    }

    public void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(NETWORK_STAT_CHANGE);
        registerReceiver(this.mNetWorkReceiver, filter);
    }

    public void unregisterReceiver() {
        try {
            unregisterReceiver(this.mNetWorkReceiver);
        } catch (IllegalArgumentException e) {
        }
    }

    public void TextValuesDisplay(int cmdType) {
        if (cmdType == 1) {
            this.teleApi.netInfoC2k1xApi().getAdjacentCell(this.mSimIndex, this.mTextValue);
        }
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.netinfo.Cdma1XShowActivity.3
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < Cdma1XShowActivity.this.ROW; i++) {
                    for (int j = 0; j < Cdma1XShowActivity.this.COL; j++) {
                        Cdma1XShowActivity.this.mtextView[i][j].setText(Cdma1XShowActivity.this.mTextValue[i][j]);
                    }
                }
            }
        });
    }

    public void valuesDisplay(int cmdType) {
        if (cmdType == 0) {
            String[] tmpValues = getResources().getStringArray(R.array.c2k_1x_sering_name);
            this.teleApi.netInfoC2k1xApi().getServingCell(this.mSimIndex, tmpValues, this.values);
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.netinfo.Cdma1XShowActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    Cdma1XShowActivity.this.adapter.notifyDataSetChanged();
                }
            });
        } else if (cmdType == 2) {
            this.values = getResources().getStringArray(R.array.Wcdma_Outfield_Network);
            final List<String> list = this.teleApi.netInfoC2k1xApi().getOutfieldNetworkInfo(this.mSimIndex, this.values);
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.netinfo.Cdma1XShowActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Cdma1XShowActivity.this, R.layout.array_item, (List<String>) list);
                    Cdma1XShowActivity.this.listView.setAdapter((ListAdapter) adapter);
                }
            });
        }
    }

    class WcdmaHandler extends Handler {
        public WcdmaHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                Cdma1XShowActivity cdma1XShowActivity = Cdma1XShowActivity.this;
                cdma1XShowActivity.valuesDisplay(cdma1XShowActivity.mCheckId);
            } else if (i == 1) {
                Cdma1XShowActivity cdma1XShowActivity2 = Cdma1XShowActivity.this;
                cdma1XShowActivity2.TextValuesDisplay(cdma1XShowActivity2.mCheckId);
            } else if (i == 2) {
                Cdma1XShowActivity cdma1XShowActivity3 = Cdma1XShowActivity.this;
                cdma1XShowActivity3.valuesDisplay(cdma1XShowActivity3.mCheckId);
            }
        }
    }
}
