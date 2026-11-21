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
import android.telephony.TelephonyManager;
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
public class CdmaDoShowActivity extends Activity {
    private static final int GET_ADJACENT = 1;
    private static final int GET_BETWEEN_ADJACENT_2G = 2;
    private static final int GET_BETWEEN_ADJACENT_4G = 3;
    private static final int GET_OUTFIELD_NETWORK = 4;
    private static final int GET_SERVING = 0;
    private static final int KEY_ADJACENT = 1;
    private static final int KEY_BETWEEN_ADJACENT_2G = 9;
    private static final int KEY_BETWEEN_ADJACENT_4G = 2;
    private static final int KEY_OUTFIELD_NETWORK = 3;
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
    private static final String TAG = "CdmaDoShowActivity";
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
    private TelephonyManager mTelephonyManager;
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
    private int[] activityLab = {R.string.netinfo_server_cell, R.string.netinfo_neighbour_cell, R.string.netinfo_adjacent_cell_4g};
    private BroadcastReceiver mNetWorkReceiver = new BroadcastReceiver() { // from class: com.sprd.engineermode.telephony.netinfo.CdmaDoShowActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String mIntentAction;
            int netWorkStat = intent.getIntExtra(CdmaDoShowActivity.NETWORK_TYPE, 0);
            int phoneId = intent.getIntExtra("simindex", -1);
            Log.d(CdmaDoShowActivity.TAG, "NetWork state is " + netWorkStat);
            synchronized (CdmaDoShowActivity.mLock) {
                if (netWorkStat != 3) {
                    if (phoneId == CdmaDoShowActivity.this.mSimIndex) {
                        if (netWorkStat == 1) {
                            mIntentAction = "android.engineermode.action.GSMSHOW";
                        } else if (netWorkStat == 2) {
                            mIntentAction = "android.engineermode.action.TDSCDMASHOW";
                        } else if (netWorkStat != 4) {
                            return;
                        } else {
                            mIntentAction = "android.engineermode.action.LTESHOW";
                        }
                        CdmaDoShowActivity.this.unregisterReceiver();
                        if (CdmaDoShowActivity.this.mTimer != null) {
                            CdmaDoShowActivity.this.mTimer.cancel();
                        }
                        Bundle data = new Bundle();
                        data.putInt(CdmaDoShowActivity.PREF_INDEX, CdmaDoShowActivity.this.mCheckId);
                        data.putInt("SubId", CdmaDoShowActivity.this.mSubId);
                        data.putInt("simindex", CdmaDoShowActivity.this.mSimIndex);
                        Intent mIntent = new Intent(mIntentAction);
                        mIntent.putExtras(data);
                        CdmaDoShowActivity.this.startActivity(mIntent);
                        CdmaDoShowActivity.this.finish();
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
        if (data != null) {
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
                    this.values = getResources().getStringArray(R.array.c2k_do_sering_name);
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
                    setContentView(R.layout.netinfo_adjacent_evdo);
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
                    setContentView(R.layout.netinfo_between_adjacent_4g_evdo);
                    this.ROW = 16;
                    this.COL = 4;
                    this.mtextView = (TextView[][]) Array.newInstance((Class<?>) TextView.class, this.ROW, this.COL);
                    this.mTextValue = (String[][]) Array.newInstance((Class<?>) String.class, this.ROW, this.COL);
                    this.viewID = (int[][]) Array.newInstance((Class<?>) int.class, this.ROW, this.COL);
                    int[][] iArr2 = this.viewID;
                    iArr2[0][0] = R.id.netinfo_adjacent_4g_label11;
                    iArr2[0][1] = R.id.netinfo_adjacent_4g_label12;
                    iArr2[0][2] = R.id.netinfo_adjacent_4g_label13;
                    iArr2[0][3] = R.id.netinfo_adjacent_4g_label14;
                    iArr2[1][0] = R.id.netinfo_adjacent_4g_label21;
                    iArr2[1][1] = R.id.netinfo_adjacent_4g_label22;
                    iArr2[1][2] = R.id.netinfo_adjacent_4g_label23;
                    iArr2[1][3] = R.id.netinfo_adjacent_4g_label24;
                    iArr2[2][0] = R.id.netinfo_adjacent_4g_label31;
                    iArr2[2][1] = R.id.netinfo_adjacent_4g_label32;
                    iArr2[2][2] = R.id.netinfo_adjacent_4g_label33;
                    iArr2[2][3] = R.id.netinfo_adjacent_4g_label34;
                    iArr2[3][0] = R.id.netinfo_adjacent_4g_label41;
                    iArr2[3][1] = R.id.netinfo_adjacent_4g_label42;
                    iArr2[3][2] = R.id.netinfo_adjacent_4g_label43;
                    iArr2[3][3] = R.id.netinfo_adjacent_4g_label44;
                    iArr2[4][0] = R.id.netinfo_adjacent_4g_label51;
                    iArr2[4][1] = R.id.netinfo_adjacent_4g_label52;
                    iArr2[4][2] = R.id.netinfo_adjacent_4g_label53;
                    iArr2[4][3] = R.id.netinfo_adjacent_4g_label54;
                    iArr2[5][0] = R.id.netinfo_adjacent_4g_label61;
                    iArr2[5][1] = R.id.netinfo_adjacent_4g_label62;
                    iArr2[5][2] = R.id.netinfo_adjacent_4g_label63;
                    iArr2[5][3] = R.id.netinfo_adjacent_4g_label64;
                    iArr2[6][0] = R.id.netinfo_adjacent_4g_label71;
                    iArr2[6][1] = R.id.netinfo_adjacent_4g_label72;
                    iArr2[6][2] = R.id.netinfo_adjacent_4g_label73;
                    iArr2[6][3] = R.id.netinfo_adjacent_4g_label74;
                    iArr2[7][0] = R.id.netinfo_adjacent_4g_label81;
                    iArr2[7][1] = R.id.netinfo_adjacent_4g_label82;
                    iArr2[7][2] = R.id.netinfo_adjacent_4g_label83;
                    iArr2[7][3] = R.id.netinfo_adjacent_4g_label84;
                    iArr2[8][0] = R.id.netinfo_adjacent_4g_label91;
                    iArr2[8][1] = R.id.netinfo_adjacent_4g_label92;
                    iArr2[8][2] = R.id.netinfo_adjacent_4g_label93;
                    iArr2[8][3] = R.id.netinfo_adjacent_4g_label94;
                    iArr2[9][0] = R.id.netinfo_adjacent_4g_label101;
                    iArr2[9][1] = R.id.netinfo_adjacent_4g_label102;
                    iArr2[9][2] = R.id.netinfo_adjacent_4g_label103;
                    iArr2[9][3] = R.id.netinfo_adjacent_4g_label104;
                    iArr2[10][0] = R.id.netinfo_adjacent_4g_label111;
                    iArr2[10][1] = R.id.netinfo_adjacent_4g_label112;
                    iArr2[10][2] = R.id.netinfo_adjacent_4g_label113;
                    iArr2[10][3] = R.id.netinfo_adjacent_4g_label114;
                    iArr2[11][0] = R.id.netinfo_adjacent_4g_label121;
                    iArr2[11][1] = R.id.netinfo_adjacent_4g_label122;
                    iArr2[11][2] = R.id.netinfo_adjacent_4g_label123;
                    iArr2[11][3] = R.id.netinfo_adjacent_4g_label124;
                    iArr2[12][0] = R.id.netinfo_adjacent_4g_label131;
                    iArr2[12][1] = R.id.netinfo_adjacent_4g_label132;
                    iArr2[12][2] = R.id.netinfo_adjacent_4g_label133;
                    iArr2[12][3] = R.id.netinfo_adjacent_4g_label134;
                    iArr2[13][0] = R.id.netinfo_adjacent_4g_label141;
                    iArr2[13][1] = R.id.netinfo_adjacent_4g_label142;
                    iArr2[13][2] = R.id.netinfo_adjacent_4g_label143;
                    iArr2[13][3] = R.id.netinfo_adjacent_4g_label144;
                    iArr2[14][0] = R.id.netinfo_adjacent_4g_label151;
                    iArr2[14][1] = R.id.netinfo_adjacent_4g_label152;
                    iArr2[14][2] = R.id.netinfo_adjacent_4g_label153;
                    iArr2[14][3] = R.id.netinfo_adjacent_4g_label154;
                    iArr2[15][0] = R.id.netinfo_adjacent_4g_label161;
                    iArr2[15][1] = R.id.netinfo_adjacent_4g_label162;
                    iArr2[15][2] = R.id.netinfo_adjacent_4g_label163;
                    iArr2[15][3] = R.id.netinfo_adjacent_4g_label164;
                    for (int i3 = 0; i3 < this.ROW; i3++) {
                        for (int j2 = 0; j2 < this.COL; j2++) {
                            this.mtextView[i3][j2] = (TextView) findViewById(this.viewID[i3][j2]);
                        }
                    }
                    Message adjacent_4g = this.mWcdmaHandler.obtainMessage(3);
                    this.mWcdmaHandler.sendMessage(adjacent_4g);
                } else if (i == 3) {
                    setContentView(R.layout.netinfo_outfield_show);
                    this.listView = (ListView) findViewById(R.id.netinfo_outfield_listview);
                    Message outfieldNetwork = this.mWcdmaHandler.obtainMessage(4);
                    this.mWcdmaHandler.sendMessage(outfieldNetwork);
                } else if (i == 9) {
                    setContentView(R.layout.netinfo_between_adjacent_3g);
                    TextView mTextViewPsc_2g = (TextView) findViewById(R.id.netinfo_adjacent_3g_label02);
                    mTextViewPsc_2g.setText(R.string.netinfo_psc);
                    this.ROW = 5;
                    this.COL = 3;
                    this.mtextView = (TextView[][]) Array.newInstance((Class<?>) TextView.class, this.ROW, this.COL);
                    this.mTextValue = (String[][]) Array.newInstance((Class<?>) String.class, this.ROW, this.COL);
                    this.viewID = (int[][]) Array.newInstance((Class<?>) int.class, this.ROW, this.COL);
                    int[][] iArr3 = this.viewID;
                    iArr3[0][0] = R.id.netinfo_adjacent_3g_label11;
                    iArr3[0][1] = R.id.netinfo_adjacent_3g_label12;
                    iArr3[0][2] = R.id.netinfo_adjacent_3g_label13;
                    iArr3[1][0] = R.id.netinfo_adjacent_3g_label21;
                    iArr3[1][1] = R.id.netinfo_adjacent_3g_label22;
                    iArr3[1][2] = R.id.netinfo_adjacent_3g_label23;
                    iArr3[2][0] = R.id.netinfo_adjacent_3g_label31;
                    iArr3[2][1] = R.id.netinfo_adjacent_3g_label32;
                    iArr3[2][2] = R.id.netinfo_adjacent_3g_label33;
                    iArr3[3][0] = R.id.netinfo_adjacent_3g_label41;
                    iArr3[3][1] = R.id.netinfo_adjacent_3g_label42;
                    iArr3[3][2] = R.id.netinfo_adjacent_3g_label43;
                    iArr3[4][0] = R.id.netinfo_adjacent_3g_label51;
                    iArr3[4][1] = R.id.netinfo_adjacent_3g_label52;
                    iArr3[4][2] = R.id.netinfo_adjacent_3g_label53;
                    for (int i4 = 0; i4 < this.ROW; i4++) {
                        for (int j3 = 0; j3 < this.COL; j3++) {
                            this.mtextView[i4][j3] = (TextView) findViewById(this.viewID[i4][j3]);
                        }
                    }
                    Message adjacent_2g = this.mWcdmaHandler.obtainMessage(2);
                    this.mWcdmaHandler.sendMessage(adjacent_2g);
                }
                this.mTimer = new Timer();
                this.mTimer.schedule(new TimerTask() { // from class: com.sprd.engineermode.telephony.netinfo.CdmaDoShowActivity.2
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        int i5 = CdmaDoShowActivity.this.mCheckId;
                        if (i5 == 0) {
                            Message serving2 = CdmaDoShowActivity.this.mWcdmaHandler.obtainMessage(0);
                            CdmaDoShowActivity.this.mWcdmaHandler.sendMessage(serving2);
                            return;
                        }
                        if (i5 == 1) {
                            Message adjacent2 = CdmaDoShowActivity.this.mWcdmaHandler.obtainMessage(1);
                            CdmaDoShowActivity.this.mWcdmaHandler.sendMessage(adjacent2);
                            return;
                        }
                        if (i5 == 2) {
                            Message adjacent_4g2 = CdmaDoShowActivity.this.mWcdmaHandler.obtainMessage(3);
                            CdmaDoShowActivity.this.mWcdmaHandler.sendMessage(adjacent_4g2);
                        } else if (i5 == 3) {
                            Message outfieldNetwork2 = CdmaDoShowActivity.this.mWcdmaHandler.obtainMessage(4);
                            CdmaDoShowActivity.this.mWcdmaHandler.sendMessage(outfieldNetwork2);
                        } else if (i5 == 9) {
                            Message adjacent_2g2 = CdmaDoShowActivity.this.mWcdmaHandler.obtainMessage(2);
                            CdmaDoShowActivity.this.mWcdmaHandler.sendMessage(adjacent_2g2);
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
            this.teleApi.netInfoC2kApi().getAdjacentCell(this.mSimIndex, this.mTextValue);
        } else if (cmdType == 2) {
            this.teleApi.netInfoC2kApi().getBetweenAdjacentCell4G(this.mSimIndex, this.mTextValue);
        } else if (cmdType == 9) {
            this.teleApi.netInfoC2kApi().getBetweenAdjacentCell2G(this.mSimIndex, this.mTextValue);
        }
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.netinfo.CdmaDoShowActivity.3
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < CdmaDoShowActivity.this.ROW; i++) {
                    for (int j = 0; j < CdmaDoShowActivity.this.COL; j++) {
                        CdmaDoShowActivity.this.mtextView[i][j].setText(CdmaDoShowActivity.this.mTextValue[i][j]);
                    }
                }
            }
        });
    }

    public void valuesDisplay(int cmdType) {
        if (cmdType == 0) {
            String[] tmpValues = getResources().getStringArray(R.array.c2k_do_sering_name);
            this.teleApi.netInfoC2kApi().getServingCell(this.mSimIndex, tmpValues, this.values);
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.netinfo.CdmaDoShowActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    CdmaDoShowActivity.this.adapter.notifyDataSetChanged();
                }
            });
        } else if (cmdType == 3) {
            this.values = getResources().getStringArray(R.array.Wcdma_Outfield_Network);
            final List<String> list = this.teleApi.netInfoC2kApi().getOutfieldNetworkInfo(this.mSimIndex, this.values);
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.netinfo.CdmaDoShowActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CdmaDoShowActivity.this, R.layout.array_item, (List<String>) list);
                    CdmaDoShowActivity.this.listView.setAdapter((ListAdapter) adapter);
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
                CdmaDoShowActivity cdmaDoShowActivity = CdmaDoShowActivity.this;
                cdmaDoShowActivity.valuesDisplay(cdmaDoShowActivity.mCheckId);
                return;
            }
            if (i == 1) {
                CdmaDoShowActivity cdmaDoShowActivity2 = CdmaDoShowActivity.this;
                cdmaDoShowActivity2.TextValuesDisplay(cdmaDoShowActivity2.mCheckId);
                return;
            }
            if (i == 2) {
                CdmaDoShowActivity cdmaDoShowActivity3 = CdmaDoShowActivity.this;
                cdmaDoShowActivity3.TextValuesDisplay(cdmaDoShowActivity3.mCheckId);
            } else if (i == 3) {
                CdmaDoShowActivity cdmaDoShowActivity4 = CdmaDoShowActivity.this;
                cdmaDoShowActivity4.TextValuesDisplay(cdmaDoShowActivity4.mCheckId);
            } else if (i == 4) {
                CdmaDoShowActivity cdmaDoShowActivity5 = CdmaDoShowActivity.this;
                cdmaDoShowActivity5.valuesDisplay(cdmaDoShowActivity5.mCheckId);
            }
        }
    }
}
