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
public class LteShowActivity extends Activity {
    private static final int GET_ADJACENT = 1;
    private static final int GET_BETWEEN_ADJACENT_2G = 2;
    private static final int GET_BETWEEN_ADJACENT_3G = 3;
    private static final int GET_OUTFIELD_NETWORK = 4;
    private static final int GET_SERVING = 0;
    private static final int KEY_ADJACENT = 1;
    private static final int KEY_BETWEEN_ADJACENT_2G = 2;
    private static final int KEY_BETWEEN_ADJACENT_3G = 3;
    private static final int KEY_OUTFIELD_NETWORK = 4;
    private static final int KEY_SERVING = 0;
    private static final String KEY_SIM_INDEX = "simindex";
    private static final String LTE_INDEX = "PrefenceIndex";
    private static final int NETWORK_GSM = 1;
    private static final int NETWORK_LTE = 4;
    private static final String NETWORK_STAT_CHANGE = "com.sprd.network.NETWORK_STAT_CHANGE";
    private static final int NETWORK_TDSCDMA = 2;
    private static final String NETWORK_TYPE = "NetWorkType";
    private static final int NETWORK_UNKNOW = 0;
    private static final int NETWORK_WCDMA = 3;
    private static final String TAG = "LteShowActivity";
    private static final Object mLock = new Object();
    public int COL;
    public int ROW;
    public boolean isRoaming;
    private ListView listView;
    private int mCheckId;
    private LteHandler mLteHandler;
    public String mNetworkOperator;
    public String mNetworkOperatorName;
    private int mSimIndex;
    private int mSubId;
    private String[][] mTextValue;
    private Timer mTimer;
    private ListView mlistviewOperators;
    TextView[][] mtextView;
    private String[] values;
    private int[][] viewID;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private String SaveName = "atchannel";
    private Handler mUiThread = new Handler();
    ArrayAdapter<String> adapter = null;
    private int[] activityLab = {R.string.netinfo_server_cell, R.string.netinfo_neighbour_cell, R.string.netinfo_adjacent_cell_2g, R.string.netinfo_adjacent_cell_3g, R.string.netinfo_outfield_information};
    private BroadcastReceiver mNetWorkReceiver = new BroadcastReceiver() { // from class: com.sprd.engineermode.telephony.netinfo.LteShowActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String mIntentAction;
            int netWorkStat = intent.getIntExtra(LteShowActivity.NETWORK_TYPE, 0);
            int phoneId = intent.getIntExtra("simindex", -1);
            Log.d(LteShowActivity.TAG, "NetWork state is " + netWorkStat);
            synchronized (LteShowActivity.mLock) {
                if (netWorkStat != 4) {
                    if (phoneId == LteShowActivity.this.mSimIndex) {
                        if (netWorkStat == 1) {
                            mIntentAction = "android.engineermode.action.GSMSHOW";
                        } else if (netWorkStat == 2) {
                            mIntentAction = "android.engineermode.action.TDSCDMASHOW";
                        } else if (netWorkStat != 3) {
                            return;
                        } else {
                            mIntentAction = "android.engineermode.action.WCDMASHOW";
                        }
                        LteShowActivity.this.unregisterReceiver();
                        if (LteShowActivity.this.mTimer != null) {
                            LteShowActivity.this.mTimer.cancel();
                        }
                        Bundle data = new Bundle();
                        data.putInt(LteShowActivity.LTE_INDEX, LteShowActivity.this.mCheckId);
                        data.putInt("SubId", LteShowActivity.this.mSubId);
                        data.putInt("simindex", LteShowActivity.this.mSimIndex);
                        Intent mIntent = new Intent(mIntentAction);
                        mIntent.putExtras(data);
                        LteShowActivity.this.startActivity(mIntent);
                        LteShowActivity.this.finish();
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
            this.mCheckId = data.getInt(LTE_INDEX, -1);
            this.mSimIndex = data.getInt("simindex", -1);
            this.mSubId = data.getInt("SubId", -1);
            Log.d(TAG, "mSimIndex is: " + this.mSimIndex + "mCheckId is: " + this.mCheckId + "SubId: " + this.mSubId);
            HandlerThread ht = new HandlerThread(TAG);
            ht.start();
            this.mLteHandler = new LteHandler(ht.getLooper());
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
                    this.listView.setItemsCanFocus(true);
                    this.values = getResources().getStringArray(R.array.Lte_sering_name);
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
                    Message serving = this.mLteHandler.obtainMessage(0);
                    this.mLteHandler.sendMessage(serving);
                } else if (i == 1) {
                    setContentView(R.layout.netinfo_between_adjacent_4g);
                    TextView mTextViewPsc = (TextView) findViewById(R.id.netinfo_adjacent_4g_label02);
                    mTextViewPsc.setText(R.string.netinfo_pci);
                    this.ROW = 5;
                    this.COL = 4;
                    this.mtextView = (TextView[][]) Array.newInstance((Class<?>) TextView.class, this.ROW, this.COL);
                    this.mTextValue = (String[][]) Array.newInstance((Class<?>) String.class, this.ROW, this.COL);
                    this.viewID = (int[][]) Array.newInstance((Class<?>) int.class, this.ROW, this.COL);
                    int[][] iArr = this.viewID;
                    iArr[0][0] = R.id.netinfo_adjacent_4g_label11;
                    iArr[0][1] = R.id.netinfo_adjacent_4g_label12;
                    iArr[0][2] = R.id.netinfo_adjacent_4g_label13;
                    iArr[0][3] = R.id.netinfo_adjacent_4g_label14;
                    iArr[1][0] = R.id.netinfo_adjacent_4g_label21;
                    iArr[1][1] = R.id.netinfo_adjacent_4g_label22;
                    iArr[1][2] = R.id.netinfo_adjacent_4g_label23;
                    iArr[1][3] = R.id.netinfo_adjacent_4g_label24;
                    iArr[2][0] = R.id.netinfo_adjacent_4g_label31;
                    iArr[2][1] = R.id.netinfo_adjacent_4g_label32;
                    iArr[2][2] = R.id.netinfo_adjacent_4g_label33;
                    iArr[2][3] = R.id.netinfo_adjacent_4g_label34;
                    iArr[3][0] = R.id.netinfo_adjacent_4g_label41;
                    iArr[3][1] = R.id.netinfo_adjacent_4g_label42;
                    iArr[3][2] = R.id.netinfo_adjacent_4g_label43;
                    iArr[3][3] = R.id.netinfo_adjacent_4g_label44;
                    iArr[4][0] = R.id.netinfo_adjacent_4g_label51;
                    iArr[4][1] = R.id.netinfo_adjacent_4g_label52;
                    iArr[4][2] = R.id.netinfo_adjacent_4g_label53;
                    iArr[4][3] = R.id.netinfo_adjacent_4g_label54;
                    for (int i2 = 0; i2 < this.ROW; i2++) {
                        for (int j = 0; j < this.COL; j++) {
                            this.mtextView[i2][j] = (TextView) findViewById(this.viewID[i2][j]);
                        }
                    }
                    Message adjacent = this.mLteHandler.obtainMessage(1);
                    this.mLteHandler.sendMessage(adjacent);
                } else if (i == 2) {
                    setContentView(R.layout.netinfo_between_adjacent_3g);
                    TextView mTextViewPsc_2g = (TextView) findViewById(R.id.netinfo_adjacent_3g_label02);
                    mTextViewPsc_2g.setText(R.string.netinfo_pci);
                    this.ROW = 5;
                    this.COL = 3;
                    this.mtextView = (TextView[][]) Array.newInstance((Class<?>) TextView.class, this.ROW, this.COL);
                    this.mTextValue = (String[][]) Array.newInstance((Class<?>) String.class, this.ROW, this.COL);
                    this.viewID = (int[][]) Array.newInstance((Class<?>) int.class, this.ROW, this.COL);
                    int[][] iArr2 = this.viewID;
                    iArr2[0][0] = R.id.netinfo_adjacent_3g_label11;
                    iArr2[0][1] = R.id.netinfo_adjacent_3g_label12;
                    iArr2[0][2] = R.id.netinfo_adjacent_3g_label13;
                    iArr2[1][0] = R.id.netinfo_adjacent_3g_label21;
                    iArr2[1][1] = R.id.netinfo_adjacent_3g_label22;
                    iArr2[1][2] = R.id.netinfo_adjacent_3g_label23;
                    iArr2[2][0] = R.id.netinfo_adjacent_3g_label31;
                    iArr2[2][1] = R.id.netinfo_adjacent_3g_label32;
                    iArr2[2][2] = R.id.netinfo_adjacent_3g_label33;
                    iArr2[3][0] = R.id.netinfo_adjacent_3g_label41;
                    iArr2[3][1] = R.id.netinfo_adjacent_3g_label42;
                    iArr2[3][2] = R.id.netinfo_adjacent_3g_label43;
                    iArr2[4][0] = R.id.netinfo_adjacent_3g_label51;
                    iArr2[4][1] = R.id.netinfo_adjacent_3g_label52;
                    iArr2[4][2] = R.id.netinfo_adjacent_3g_label53;
                    for (int i3 = 0; i3 < this.ROW; i3++) {
                        for (int j2 = 0; j2 < this.COL; j2++) {
                            this.mtextView[i3][j2] = (TextView) findViewById(this.viewID[i3][j2]);
                        }
                    }
                    Message adjacent_2g = this.mLteHandler.obtainMessage(2);
                    this.mLteHandler.sendMessage(adjacent_2g);
                } else if (i == 3) {
                    setContentView(R.layout.netinfo_between_adjacent_3g);
                    TextView mTextViewPsc_3g = (TextView) findViewById(R.id.netinfo_adjacent_3g_label02);
                    mTextViewPsc_3g.setText(R.string.netinfo_pci);
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
                    Message adjacent_3g = this.mLteHandler.obtainMessage(3);
                    this.mLteHandler.sendMessage(adjacent_3g);
                } else if (i == 4) {
                    setContentView(R.layout.netinfo_outfield_show);
                    this.listView = (ListView) findViewById(R.id.netinfo_outfield_listview);
                    Message outfieldNetwork = this.mLteHandler.obtainMessage(4);
                    this.mLteHandler.sendMessage(outfieldNetwork);
                }
                this.mTimer = new Timer();
                this.mTimer.schedule(new TimerTask() { // from class: com.sprd.engineermode.telephony.netinfo.LteShowActivity.2
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        int i5 = LteShowActivity.this.mCheckId;
                        if (i5 == 0) {
                            Message serving2 = LteShowActivity.this.mLteHandler.obtainMessage(0);
                            LteShowActivity.this.mLteHandler.sendMessage(serving2);
                            return;
                        }
                        if (i5 == 1) {
                            Message adjacent2 = LteShowActivity.this.mLteHandler.obtainMessage(1);
                            LteShowActivity.this.mLteHandler.sendMessage(adjacent2);
                            return;
                        }
                        if (i5 == 2) {
                            Message adjacent_2g2 = LteShowActivity.this.mLteHandler.obtainMessage(2);
                            LteShowActivity.this.mLteHandler.sendMessage(adjacent_2g2);
                        } else if (i5 == 3) {
                            Message adjacent_3g2 = LteShowActivity.this.mLteHandler.obtainMessage(3);
                            LteShowActivity.this.mLteHandler.sendMessage(adjacent_3g2);
                        } else if (i5 == 4) {
                            Message outfieldNetwork2 = LteShowActivity.this.mLteHandler.obtainMessage(4);
                            LteShowActivity.this.mLteHandler.sendMessage(outfieldNetwork2);
                        }
                    }
                }, 0L, 200L);
                return;
            }
            LteHandler lteHandler = this.mLteHandler;
            if (lteHandler != null) {
                lteHandler.getLooper().quit();
            }
        }
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        registerReceiver();
        Log.d(TAG, "onStart");
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        unregisterReceiver();
        Log.d(TAG, "onStop");
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

    @Override // android.app.Activity
    protected void onDestroy() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
        }
        LteHandler lteHandler = this.mLteHandler;
        if (lteHandler != null) {
            lteHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    public void TextValuesDisplay(int cmdType) {
        if (cmdType == 1) {
            this.teleApi.netInfoLteApi().getAdjacentCell(this.mSimIndex, this.mTextValue);
        } else if (cmdType == 2) {
            this.teleApi.netInfoLteApi().getBetweenAdjacentCell2G(this.mSimIndex, this.mTextValue);
        } else if (cmdType == 3) {
            this.teleApi.netInfoLteApi().getBetweenAdjacentCell3G(this.mSimIndex, this.mTextValue);
        }
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.netinfo.LteShowActivity.3
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < LteShowActivity.this.ROW; i++) {
                    for (int j = 0; j < LteShowActivity.this.COL; j++) {
                        LteShowActivity.this.mtextView[i][j].setText(LteShowActivity.this.mTextValue[i][j]);
                    }
                }
            }
        });
    }

    public void valuesDisplay(int cmdType) {
        if (cmdType == 0) {
            String[] tmpValues = getResources().getStringArray(R.array.Lte_sering_name);
            this.teleApi.netInfoLteApi().getServingCell(this.mSimIndex, tmpValues, this.values);
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.netinfo.LteShowActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    LteShowActivity.this.adapter.notifyDataSetChanged();
                }
            });
        } else if (cmdType == 4) {
            this.values = getResources().getStringArray(R.array.Lte_Outfield_Network);
            final List<String> list = this.teleApi.netInfoLteApi().getOutfieldNetworkInfo(this.mSimIndex, this.values);
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.netinfo.LteShowActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(LteShowActivity.this, R.layout.array_item, (List<String>) list);
                    LteShowActivity.this.listView.setAdapter((ListAdapter) adapter);
                }
            });
        }
    }

    class LteHandler extends Handler {
        public LteHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                LteShowActivity lteShowActivity = LteShowActivity.this;
                lteShowActivity.valuesDisplay(lteShowActivity.mCheckId);
                return;
            }
            if (i == 1) {
                LteShowActivity lteShowActivity2 = LteShowActivity.this;
                lteShowActivity2.TextValuesDisplay(lteShowActivity2.mCheckId);
                return;
            }
            if (i == 2) {
                LteShowActivity lteShowActivity3 = LteShowActivity.this;
                lteShowActivity3.TextValuesDisplay(lteShowActivity3.mCheckId);
            } else if (i == 3) {
                LteShowActivity lteShowActivity4 = LteShowActivity.this;
                lteShowActivity4.TextValuesDisplay(lteShowActivity4.mCheckId);
            } else if (i == 4) {
                LteShowActivity lteShowActivity5 = LteShowActivity.this;
                lteShowActivity5.valuesDisplay(lteShowActivity5.mCheckId);
            }
        }
    }
}
