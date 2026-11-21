package com.sprd.engineermode.connectivity.wifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.intf.IConnectivityApi;

/* JADX INFO: loaded from: classes4.dex */
public class WifiTXActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String BAND = "tx_band";
    private static final String BAND_SBW = "tx_sbw";
    private static final String BAND_WIDTH = "tx_bandwidth";
    private static final String CHANNEL = "tx_channel";
    private static final int GO_ALERT = 3;
    private static final String GUARD_INTERVAL = "tx_guardinterval";
    private static final String INSMODE_RES = "insmode_result";
    private static final String MODE = "tx_mode";
    private static final String OFFSET = "cbw_channel_offset";
    private static final String PATH = "rf_tx_path";
    private static final String PKT_CNT = "tx_pktcnt";
    private static final String PKT_LENGTH = "tx_pktlength";
    private static final String POWER_LEVEL = "tx_powerlevel";
    private static final String PREAMBLE = "tx_preamble";
    private static final String RATE = "tx_rate";
    private static final String STANDRARD = "rf_standrard";
    private static final int STOP_ALERT = 4;
    private static final String TAG = "WifiTXActivity";
    private static final int WIFI_TX_GO = 3;
    private static final int WIFI_TX_STOP = 4;
    private AlertDialog mAlertDialog;
    private Spinner mBand;
    private int mBandPosition;
    private Spinner mBandSBW;
    private int mBandSBWPosition;
    private TextView mBandTitle;
    private Spinner mBandWidth;
    private int mBandWidthPosition;
    private TableRow mCbwTableRow;
    private Spinner mChannel;
    private int mChannelPosition;
    private AlertDialog mCmdAlertDialog;
    private Button mGo;
    private Spinner mGuardInterval;
    private int mGuardIntervalPosition;
    private Spinner mMode;
    private int mModePosition;
    private Spinner mOffset;
    private int mOffsetPosition;
    private TableRow mOffsetTableRow;
    private Spinner mPath;
    private int mPathPosition;
    private TableRow mPathTableRow;
    private EditText mPktcnt;
    private EditText mPktlength;
    private EditText mPowerLevel;
    private Spinner mPreamble;
    private int mPreamblePosition;
    private SharedPreferences mPref;
    private Spinner mRate;
    private int mRatePosition;
    private TableRow mSbwTableRow;
    private Spinner mStandrard;
    private int mStandrardPosition;
    private Button mStop;
    private IConnectivityApi.IWifiEut.WifiTX mWifiTX;
    private WifiTXHandler mWifiTXHandler;
    private boolean mInsmodeSuccessByEUT = false;
    private boolean mInsmodeSuccessByTX = false;
    ArrayAdapter<String> bandAdapter = null;
    ArrayAdapter<String> channelAdapter = null;
    ArrayAdapter<String> rateAdapter = null;
    ArrayAdapter<String> preadmbleAdapter = null;
    ArrayAdapter<String> bandWidthAdapter = null;
    ArrayAdapter<String> bandSBWAdapter = null;
    ArrayAdapter<String> pathAdapter = null;
    ArrayAdapter<String> standrardAdapter = null;
    ArrayAdapter<String> offsetAdapter = null;
    private int mPowerLevelMax = 17;
    private boolean mRefreshUI = false;
    public Handler mHandler = new Handler() { // from class: com.sprd.engineermode.connectivity.wifi.WifiTXActivity.3
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg == null) {
                Log.d(WifiTXActivity.TAG, "UI Message is null");
                return;
            }
            int i = msg.what;
            if (i == 3) {
                if (msg.arg1 == 1) {
                    WifiTXActivity.this.mCmdAlertDialog.setMessage("Wifi EUT TX Start Success");
                    WifiTXActivity.this.mGo.setEnabled(false);
                    WifiTXActivity.this.mStop.setEnabled(true);
                } else {
                    WifiTXActivity.this.mCmdAlertDialog.setMessage("Wifi EUT TX Start Fail");
                }
                if (!WifiTXActivity.this.isFinishing()) {
                    WifiTXActivity.this.mCmdAlertDialog.show();
                    return;
                }
                return;
            }
            if (i == 4) {
                if (msg.arg1 == 1) {
                    WifiTXActivity.this.mCmdAlertDialog.setMessage("Wifi EUT TX Stop Success");
                    WifiTXActivity.this.mGo.setEnabled(true);
                    WifiTXActivity.this.mStop.setEnabled(false);
                } else {
                    WifiTXActivity.this.mCmdAlertDialog.setMessage("Wifi EUT TX Stop Fail");
                }
                Log.d(WifiTXActivity.TAG, "STOP_ALERT");
                if (!WifiTXActivity.this.isFinishing()) {
                    WifiTXActivity.this.mCmdAlertDialog.show();
                }
            }
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_tx);
        getWindow().setSoftInputMode(32);
        this.mInsmodeSuccessByEUT = getIntent().getBooleanExtra(INSMODE_RES, false);
        this.mPref = PreferenceManager.getDefaultSharedPreferences(this);
        initUI();
        if (Const.isMarlin()) {
            initUIMarlin();
        } else {
            initUIBrcm();
        }
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        if (this.mWifiTXHandler == null) {
            this.mWifiTXHandler = new WifiTXHandler(ht.getLooper());
        }
        if (this.mWifiTX == null) {
            this.mWifiTX = new IConnectivityApi.IWifiEut.WifiTX();
        }
        this.mAlertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.alert_wifi)).setMessage((CharSequence) null).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.wifi.WifiTXActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                WifiTXActivity.this.finish();
            }
        }).create();
        this.mCmdAlertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.alert_wifi)).setMessage((CharSequence) null).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.wifi.WifiTXActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
    }

    @Override // android.app.Activity
    protected void onResume() {
        refreshUI();
        super.onResume();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.mStop.isEnabled()) {
            Message doMessage = this.mWifiTXHandler.obtainMessage(4);
            this.mWifiTXHandler.sendMessage(doMessage);
        }
        super.onBackPressed();
    }

    @Override // android.app.Activity
    protected void onPause() {
        Log.d(TAG, "onPause start");
        this.mRefreshUI = true;
        if (Const.isMarlin() && this.mWifiTX != null) {
            SharedPreferences.Editor editor = this.mPref.edit();
            editor.putInt(BAND, this.mBandPosition);
            editor.putInt(CHANNEL, this.mChannelPosition);
            editor.putInt(OFFSET, this.mOffsetPosition);
            editor.putString(PKT_LENGTH, this.mWifiTX.pktlength);
            editor.putString(PKT_CNT, this.mWifiTX.pktcnt);
            editor.putString(POWER_LEVEL, this.mWifiTX.powerlevel);
            editor.putInt(RATE, this.mRatePosition);
            editor.putInt(MODE, this.mModePosition);
            editor.putInt(PREAMBLE, this.mPreamblePosition);
            editor.putInt(BAND_WIDTH, this.mBandWidthPosition);
            editor.putInt(GUARD_INTERVAL, this.mGuardIntervalPosition);
            if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                editor.putInt(PATH, this.mPathPosition);
                editor.putInt(STANDRARD, this.mStandrardPosition);
                editor.putInt(BAND_SBW, this.mBandSBWPosition);
            }
            editor.commit();
        }
        super.onPause();
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (this.mWifiTXHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mWifiTXHandler.getLooper().quit();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Message doMessage = null;
        if (v == this.mGo) {
            if (checkParams()) {
                doMessage = this.mWifiTXHandler.obtainMessage(3);
            }
        } else if (v == this.mStop) {
            doMessage = this.mWifiTXHandler.obtainMessage(4);
        }
        if (doMessage != null) {
            this.mWifiTXHandler.sendMessage(doMessage);
        }
    }

    private boolean checkParams() {
        if (this.mPktlength.getText().toString().length() == 0 || Integer.parseInt(this.mPktlength.getText().toString()) < 64 || Integer.parseInt(this.mPktlength.getText().toString()) > 4095) {
            Toast.makeText(this, "Pkt length: number between 64 and 4095", 0).show();
            return false;
        }
        if (this.mPktcnt.getText().toString().length() == 0 || Integer.parseInt(this.mPktcnt.getText().toString()) < 0 || Integer.parseInt(this.mPktcnt.getText().toString()) > 65535) {
            Toast.makeText(this, "Pkt cnt: number between 0 and 65535", 0).show();
            return false;
        }
        if (this.mPowerLevel.getText().toString().length() == 0 || Integer.parseInt(this.mPowerLevel.getText().toString()) < 0 || Integer.parseInt(this.mPowerLevel.getText().toString()) > this.mPowerLevelMax) {
            Toast.makeText(this, "Power Level: number between 0 and " + this.mPowerLevelMax, 0).show();
            return false;
        }
        return true;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (Const.isMarlin()) {
            onItemSelectMarlin(parent, view, position, id);
        } else {
            onItemSelectBrcm(parent, view, position, id);
        }
    }

    private void onItemSelectMarlin(AdapterView<?> parent, View view, int position, long id) {
        IConnectivityApi.IWifiEut.WifiTX wifiTX = this.mWifiTX;
        if (wifiTX != null) {
            try {
                if (parent == this.mBand) {
                    Log.d(TAG, "mBand");
                    this.mWifiTX.band = getResources().getStringArray(R.array.band_str_arr)[position];
                    this.mBandPosition = position;
                    return;
                }
                if (parent == this.mChannel) {
                    if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                        if (this.mStandrardPosition == 0 || this.mStandrardPosition == 1 || this.mStandrardPosition == 2) {
                            if (this.mBandWidthPosition == 0) {
                                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_0)[position];
                            } else if (this.mBandWidthPosition == 1) {
                                if (this.mOffsetPosition == 0) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_n10)[position];
                                } else if (this.mOffsetPosition == 1) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_p10)[position];
                                }
                            }
                        } else if (this.mStandrardPosition == 3) {
                            if (this.mBandWidthPosition == 0) {
                                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_1)[position];
                            } else if (this.mBandWidthPosition == 1) {
                                if (this.mOffsetPosition == 0) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_n10)[position];
                                } else if (this.mOffsetPosition == 1) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_p10)[position];
                                }
                            }
                        } else if (this.mStandrardPosition == 4) {
                            if (this.mBandWidthPosition == 0) {
                                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_1)[position];
                            } else if (this.mBandWidthPosition == 1) {
                                if (this.mOffsetPosition == 0) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_n10)[position];
                                } else if (this.mOffsetPosition == 1) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_p10)[position];
                                }
                            } else if (this.mBandWidthPosition == 2) {
                                if (this.mOffsetPosition == 0) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_n30)[position];
                                } else if (this.mOffsetPosition == 1) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_n10)[position];
                                } else if (this.mOffsetPosition == 2) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_p10)[position];
                                } else if (this.mOffsetPosition == 3) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_p30)[position];
                                }
                            }
                        } else if (this.mStandrardPosition == 5 && this.mBandWidthPosition == 0) {
                            this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_1)[position];
                        }
                    } else if (Const.isMarlin2()) {
                        this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_0)[position];
                    }
                    this.mChannelPosition = position;
                    return;
                }
                if (parent == this.mOffset) {
                    if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                        if (this.mStandrardPosition == 0 || this.mStandrardPosition == 1 || this.mStandrardPosition == 2) {
                            if (this.mBandWidthPosition == 0) {
                                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_0)[this.mChannelPosition];
                            } else if (this.mBandWidthPosition == 1) {
                                if (position == 0) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_n10)[this.mChannelPosition];
                                } else if (position == 1) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_p10)[this.mChannelPosition];
                                }
                            }
                        } else if (this.mStandrardPosition == 3) {
                            if (this.mBandWidthPosition == 0) {
                                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_1)[this.mChannelPosition];
                            } else if (this.mBandWidthPosition == 1) {
                                if (position == 0) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_n10)[this.mChannelPosition];
                                } else if (position == 1) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_p10)[this.mChannelPosition];
                                }
                            }
                        } else if (this.mStandrardPosition == 4 || this.mStandrardPosition == 5) {
                            if (this.mBandWidthPosition == 0) {
                                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_1)[this.mChannelPosition];
                            } else if (this.mBandWidthPosition == 1) {
                                if (position == 0) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_n10)[this.mChannelPosition];
                                } else if (position == 1) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_p10)[this.mChannelPosition];
                                }
                            } else if (this.mBandWidthPosition == 2) {
                                if (position == 0) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_n30)[this.mChannelPosition];
                                } else if (position == 1) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_n10)[this.mChannelPosition];
                                } else if (position == 2) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_p10)[this.mChannelPosition];
                                } else if (position == 3) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_p30)[this.mChannelPosition];
                                }
                            }
                        }
                    } else if (Const.isMarlin2()) {
                        this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_0)[this.mChannelPosition];
                    }
                    this.mOffsetPosition = position;
                    return;
                }
                if (parent == this.mRate) {
                    if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                        if (this.mStandrardPosition == 0) {
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_0)[position];
                        } else if (this.mStandrardPosition == 1) {
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_1)[position];
                        } else if (this.mStandrardPosition == 2 || this.mStandrardPosition == 3) {
                            if (this.mPathPosition == 0 || this.mPathPosition == 1) {
                                this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_2_primary)[position];
                            } else {
                                this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_2_mimo)[position];
                            }
                        } else if (this.mStandrardPosition == 4) {
                            if (this.mPathPosition == 0 || this.mPathPosition == 1) {
                                this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_3_primary)[position];
                            } else {
                                this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_3_mimo)[position];
                            }
                        } else if (this.mStandrardPosition == 5) {
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_802_11a)[position];
                        } else {
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_0)[position];
                        }
                    } else if (Const.isMarlin2()) {
                        if (this.mStandrardPosition == 0) {
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_0_marlin2)[position];
                        } else if (this.mStandrardPosition == 1) {
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_1_marlin2)[position];
                        } else if (this.mStandrardPosition == 2) {
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_2_marlin2)[position];
                        } else {
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_0_marlin2)[position];
                        }
                    } else {
                        this.mWifiTX.rate = getResources().getStringArray(R.array.rate_int_arr)[position];
                    }
                    this.mRatePosition = position;
                    return;
                }
                if (parent == this.mMode) {
                    wifiTX.mode = getResources().getStringArray(R.array.mode_str_arr)[position];
                    this.mModePosition = position;
                    return;
                }
                if (parent == this.mPreamble) {
                    wifiTX.preamble = getResources().getStringArray(R.array.preamble_int_arr_0)[position];
                    this.mPreamblePosition = position;
                    return;
                }
                if (parent == this.mBandWidth) {
                    if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                        if (this.mStandrardPosition == 0 || this.mStandrardPosition == 1) {
                            this.mWifiTX.bandwidth = getResources().getStringArray(R.array.bandwidth_int_arr_0)[position];
                        } else if (this.mStandrardPosition == 2 || this.mStandrardPosition == 3) {
                            this.mWifiTX.bandwidth = getResources().getStringArray(R.array.bandwidth_int_arr_1)[position];
                        } else if (this.mStandrardPosition == 4) {
                            this.mWifiTX.bandwidth = getResources().getStringArray(R.array.bandwidth_int_arr_2)[position];
                        }
                        if (position == 0) {
                            Log.d(TAG, " bandcbw_str_arr_0");
                            this.bandSBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandsbw_str_arr_0));
                            this.mBandSBWPosition = 0;
                            this.offsetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.offset_freqnote_str_arr_0));
                        } else if (position == 1) {
                            Log.d(TAG, " bandcbw_str_arr_1");
                            this.bandSBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandsbw_str_arr_1));
                            this.mBandSBWPosition = 1;
                            this.offsetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.offset_freqnote_str_arr_1));
                        } else if (position == 2) {
                            Log.d(TAG, " bandcbw_str_arr_2");
                            this.bandSBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandsbw_str_arr_2));
                            this.mBandSBWPosition = 2;
                            this.offsetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.offset_freqnote_str_arr_2));
                        } else if (position == 3) {
                            Log.d(TAG, " bandsbw_ctr_arr_3");
                            this.bandSBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandsbw_str_arr_3));
                            this.mBandSBWPosition = 3;
                            this.offsetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.offset_freqnote_str_arr_2));
                        }
                        this.bandSBWAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        this.mBandSBW.setAdapter((SpinnerAdapter) this.bandSBWAdapter);
                        this.bandSBWAdapter.notifyDataSetChanged();
                        this.offsetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        this.mOffset.setAdapter((SpinnerAdapter) this.offsetAdapter);
                        this.offsetAdapter.notifyDataSetChanged();
                        this.mBandSBW.setSelection(this.mBandSBWPosition);
                        this.mChannelPosition = 0;
                        if (position == 0) {
                            if (this.mStandrardPosition == 0 || this.mStandrardPosition == 1 || this.mStandrardPosition == 2) {
                                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_0));
                                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_0)[this.mChannelPosition];
                            } else if (this.mStandrardPosition == 3 || this.mStandrardPosition == 4) {
                                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_1));
                                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_1)[this.mChannelPosition];
                            }
                        } else if (position == 1) {
                            if (this.mStandrardPosition == 2) {
                                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr));
                                if (this.mOffsetPosition == 0) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_n10)[this.mChannelPosition];
                                } else if (this.mOffsetPosition == 1) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_p10)[this.mChannelPosition];
                                }
                            } else if (this.mStandrardPosition == 3 || this.mStandrardPosition == 4) {
                                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_2));
                                if (this.mOffsetPosition == 0) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_n10)[this.mChannelPosition];
                                } else if (this.mOffsetPosition == 1) {
                                    this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_p10)[this.mChannelPosition];
                                }
                            }
                        } else if (position == 2 && this.mStandrardPosition == 4) {
                            this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_3));
                            if (this.mOffsetPosition == 0) {
                                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_n30)[this.mChannelPosition];
                            } else if (this.mOffsetPosition == 1) {
                                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_n10)[this.mChannelPosition];
                            } else if (this.mOffsetPosition == 2) {
                                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_p10)[this.mChannelPosition];
                            } else if (this.mOffsetPosition == 3) {
                                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_p30)[this.mChannelPosition];
                            }
                        }
                        this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                        this.channelAdapter.notifyDataSetChanged();
                    }
                    this.mBandWidthPosition = position;
                    return;
                }
                if (parent == this.mBandSBW) {
                    if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                        if (this.mBandWidthPosition == 0) {
                            this.mWifiTX.bandsbw = getResources().getStringArray(R.array.bandsbw_int_arr_0)[position];
                        } else if (this.mBandWidthPosition == 1) {
                            this.mWifiTX.bandsbw = getResources().getStringArray(R.array.bandsbw_int_arr_1)[position];
                        } else if (this.mBandWidthPosition == 2) {
                            this.mWifiTX.bandsbw = getResources().getStringArray(R.array.bandsbw_int_arr_2)[position];
                        } else if (this.mBandWidthPosition == 3) {
                            this.mWifiTX.bandsbw = getResources().getStringArray(R.array.bandsbw_int_arr_3)[position];
                        } else {
                            this.mWifiTX.bandsbw = getResources().getStringArray(R.array.bandsbw_int_arr_0)[position];
                        }
                        this.mBandSBWPosition = position;
                        return;
                    }
                    return;
                }
                if (parent == this.mGuardInterval) {
                    wifiTX.guardinterval = getResources().getStringArray(R.array.guardinterval_int_arr)[position];
                    this.mGuardIntervalPosition = position;
                    return;
                }
                if (parent == this.mPath) {
                    if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                        this.mWifiTX.path = getResources().getStringArray(R.array.rf_path_str_arr)[position];
                        this.mPathPosition = position;
                        if (position != 0 && position != 1) {
                            if (position == 2) {
                                if (this.mStandrardPosition == 2 || this.mStandrardPosition == 3) {
                                    this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_2_mimo));
                                } else if (this.mStandrardPosition == 4) {
                                    this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_3_mimo));
                                }
                                this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                                this.rateAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        }
                        if (this.mStandrardPosition != 2 && this.mStandrardPosition != 3) {
                            if (this.mStandrardPosition == 4) {
                                this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_3_primary));
                                return;
                            }
                            return;
                        }
                        this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_2_primary));
                        return;
                    }
                    return;
                }
                if (parent == this.mStandrard) {
                    if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                        if (position == 0 || position == 1 || position == 2) {
                            if (this.mBandWidthPosition == 0) {
                                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_0));
                            } else if (this.mBandWidthPosition == 1) {
                                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr));
                            }
                        } else if (position == 3) {
                            if (this.mBandWidthPosition == 0) {
                                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_1));
                            } else if (this.mBandWidthPosition == 1) {
                                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_2));
                            }
                        } else if (position == 4) {
                            if (this.mBandWidthPosition == 0) {
                                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_1));
                            } else if (this.mBandWidthPosition == 1) {
                                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_2));
                            } else if (this.mBandWidthPosition == 2) {
                                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_3));
                            }
                        } else if (position == 5) {
                            this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_1));
                        }
                        this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                        this.channelAdapter.notifyDataSetChanged();
                        if (position == 0) {
                            this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_0));
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_0)[0];
                            this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_0));
                        } else if (position == 1) {
                            this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_1));
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_1)[0];
                            this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_0));
                        } else if (position == 2 || position == 3) {
                            Log.e(TAG, "mPathPosition: " + this.mPathPosition);
                            if (this.mPathPosition == 0 || this.mPathPosition == 1) {
                                this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_2_primary));
                                this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_2_primary)[0];
                            } else {
                                this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_2_mimo));
                                this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_2_mimo)[0];
                            }
                            this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_1));
                        } else if (position == 4) {
                            Log.e(TAG, "mPathPosition: " + this.mPathPosition);
                            if (this.mPathPosition == 0 || this.mPathPosition == 1) {
                                this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_3_primary));
                                this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_3_primary)[0];
                            } else {
                                this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_3_mimo));
                                this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_3_mimo)[0];
                            }
                            this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_2));
                        } else if (position == 5) {
                            this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_802_11a));
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_802_11a)[0];
                            this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_0));
                        }
                        this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                        this.rateAdapter.notifyDataSetChanged();
                        this.bandWidthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        this.mBandWidth.setAdapter((SpinnerAdapter) this.bandWidthAdapter);
                        this.bandWidthAdapter.notifyDataSetChanged();
                    } else if (Const.isMarlin2()) {
                        if (position == 0) {
                            this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_0_marlin2));
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_0_marlin2)[0];
                        } else if (position == 1) {
                            this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_1_marlin2));
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_1_marlin2)[0];
                        } else if (position == 2) {
                            this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr_2_marlin2));
                            this.mWifiTX.rate = getResources().getStringArray(R.array.tx_rate_int_arr_2_marlin2)[0];
                        }
                        this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                        this.rateAdapter.notifyDataSetChanged();
                    }
                    if (position == 0) {
                        Log.d(TAG, "standard 0");
                        this.preadmbleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.preamble_str_arr_0));
                        this.mWifiTX.preamble = getResources().getStringArray(R.array.preamble_int_arr_0)[0];
                        this.mPowerLevel.setHint(getString(R.string.power_level_range2));
                        this.mPowerLevelMax = 17;
                    } else if (position == 1 || position == 5) {
                        Log.d(TAG, "standard 1");
                        this.preadmbleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.preamble_str_arr_3));
                        this.mWifiTX.preamble = getResources().getStringArray(R.array.preamble_int_arr_3)[0];
                        this.mPowerLevel.setHint(getString(R.string.power_level_range1));
                        this.mPowerLevelMax = 15;
                    } else if (position == 2 || position == 3) {
                        Log.d(TAG, "standard 2");
                        this.preadmbleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.preamble_str_arr_1));
                        this.mWifiTX.preamble = getResources().getStringArray(R.array.preamble_int_arr_1)[0];
                        this.mPowerLevel.setHint(getString(R.string.power_level_range1));
                        this.mPowerLevelMax = 15;
                    } else if (position == 4) {
                        Log.d(TAG, "standard 3");
                        this.preadmbleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.preamble_str_arr_2));
                        this.mWifiTX.preamble = getResources().getStringArray(R.array.preamble_int_arr_2)[0];
                        this.mPowerLevel.setHint(getString(R.string.power_level_range1));
                        this.mPowerLevelMax = 15;
                    }
                    this.preadmbleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mPreamble.setAdapter((SpinnerAdapter) this.preadmbleAdapter);
                    this.preadmbleAdapter.notifyDataSetChanged();
                    this.mStandrardPosition = position;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void onItemSelectBrcm(AdapterView<?> parent, View view, int position, long id) {
        IConnectivityApi.IWifiEut.WifiTX wifiTX = this.mWifiTX;
        if (wifiTX != null) {
            if (parent == this.mBand) {
                if (position == 0) {
                    Log.d(TAG, "-----band:2.4G-------");
                    this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_arr_0));
                    this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                    this.channelAdapter.notifyDataSetChanged();
                    this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rate_str_arr_0));
                    this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                    this.rateAdapter.notifyDataSetChanged();
                    this.preadmbleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.preamble_str_arr_0));
                    this.preadmbleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mPreamble.setAdapter((SpinnerAdapter) this.preadmbleAdapter);
                    this.preadmbleAdapter.notifyDataSetChanged();
                    this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_0));
                    this.bandWidthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mBandWidth.setAdapter((SpinnerAdapter) this.bandWidthAdapter);
                    this.bandWidthAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "-----band:5G-------");
                    this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_arr_1));
                    this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                    this.channelAdapter.notifyDataSetChanged();
                    this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rate_str_arr_1));
                    this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                    this.rateAdapter.notifyDataSetChanged();
                    this.preadmbleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.preamble_str_arr_1));
                    this.preadmbleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mPreamble.setAdapter((SpinnerAdapter) this.preadmbleAdapter);
                    this.preadmbleAdapter.notifyDataSetChanged();
                    this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr));
                    this.bandWidthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mBandWidth.setAdapter((SpinnerAdapter) this.bandWidthAdapter);
                    this.bandWidthAdapter.notifyDataSetChanged();
                }
                this.mWifiTX.band = getResources().getStringArray(R.array.band_str_arr)[position];
                this.mBandPosition = position;
                Log.d(TAG, "mBandPosition=" + this.mBandPosition);
                return;
            }
            if (parent == this.mPreamble) {
                if (this.mBandPosition == 0) {
                    Log.d(TAG, "-----band:2.4G preample selection-------");
                    this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_arr_0));
                    this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                    this.channelAdapter.notifyDataSetChanged();
                    this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rate_str_arr_0));
                    this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                    this.rateAdapter.notifyDataSetChanged();
                    this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_0));
                    this.bandWidthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mBandWidth.setAdapter((SpinnerAdapter) this.bandWidthAdapter);
                    this.bandWidthAdapter.notifyDataSetChanged();
                    this.mWifiTX.preamble = getResources().getStringArray(R.array.preamble_int_arr_0)[position];
                    this.mPreamblePosition = position;
                    return;
                }
                Log.d(TAG, "-----band:5G preample selection-------");
                if (position == 2) {
                    Log.d(TAG, "-----band:5G preample:802.11ac-------");
                    this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rate_str_arr_1));
                    this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                    this.rateAdapter.notifyDataSetChanged();
                    this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr));
                    this.bandWidthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mBandWidth.setAdapter((SpinnerAdapter) this.bandWidthAdapter);
                    this.bandWidthAdapter.notifyDataSetChanged();
                    this.mWifiTX.preamble = getResources().getStringArray(R.array.preamble_int_arr_1)[position];
                    this.mPreamblePosition = position;
                } else {
                    Log.d(TAG, "-----band:5G preample:802.11n Mixed Mode || 802.11n Green Field-------");
                    this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rate_str_arr_2));
                    this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                    this.rateAdapter.notifyDataSetChanged();
                    this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_1));
                    this.bandWidthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mBandWidth.setAdapter((SpinnerAdapter) this.bandWidthAdapter);
                    this.bandWidthAdapter.notifyDataSetChanged();
                    this.mWifiTX.preamble = getResources().getStringArray(R.array.preamble_int_arr_1)[position];
                    this.mPreamblePosition = position;
                }
                Log.d(TAG, "mPreamblePosition=" + this.mPreamblePosition);
                return;
            }
            if (parent == this.mBandWidth) {
                if (this.mBandPosition == 0) {
                    Log.d(TAG, "-----band:2.4G BandWidth selection-------");
                    this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rate_str_arr_0));
                    this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                    this.rateAdapter.notifyDataSetChanged();
                    this.mWifiTX.bandwidth = getResources().getStringArray(R.array.bandwidth_int_arr_0)[position];
                    this.mBandWidthPosition = position;
                    return;
                }
                Log.d(TAG, "-----band:5G BandWidth selection-------");
                int i = this.mPreamblePosition;
                if (i == 0 || i == 1) {
                    Log.d(TAG, "-----band:5G preample:802.11n Mixed Mode || 802.11n Green Field BandWidth selection-------");
                    this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rate_str_arr_2));
                    this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                    this.rateAdapter.notifyDataSetChanged();
                    this.mWifiTX.bandwidth = getResources().getStringArray(R.array.bandwidth_int_arr)[position];
                    this.mBandWidthPosition = position;
                    return;
                }
                Log.d(TAG, "-----band:5G preample:802.11ac BandWidth selection-------");
                if (position == 0) {
                    Log.d(TAG, "-----band:5G preample:802.11ac BandWidth:20M-------");
                    this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rate_str_arr_3));
                    this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                    this.rateAdapter.notifyDataSetChanged();
                    this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_arr_1));
                    this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                    this.channelAdapter.notifyDataSetChanged();
                } else if (position == 1) {
                    Log.d(TAG, "-----band:5G preample:802.11ac BandWidth:40M-------");
                    this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rate_str_arr_1));
                    this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                    this.rateAdapter.notifyDataSetChanged();
                    this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_arr_1));
                    this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                    this.channelAdapter.notifyDataSetChanged();
                } else if (position == 2) {
                    Log.d(TAG, "-----band:5G preample:802.11ac BandWidth:80M-------");
                    this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rate_str_arr_1));
                    this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
                    this.rateAdapter.notifyDataSetChanged();
                    this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_arr_2));
                    this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                    this.channelAdapter.notifyDataSetChanged();
                }
                this.mWifiTX.bandwidth = getResources().getStringArray(R.array.bandwidth_int_arr)[position];
                this.mBandWidthPosition = position;
                return;
            }
            if (parent == this.mChannel) {
                if (this.mBandPosition == 0) {
                    wifiTX.channel = getResources().getStringArray(R.array.channel_arr_0)[position];
                    this.mChannelPosition = position;
                    return;
                }
                if (this.mPreamblePosition == 2 && this.mBandWidthPosition == 2) {
                    wifiTX.channel = getResources().getStringArray(R.array.channel_arr_2)[position];
                    this.mChannelPosition = position;
                }
                this.mWifiTX.channel = getResources().getStringArray(R.array.channel_arr_1)[position];
                this.mChannelPosition = position;
                return;
            }
            if (parent == this.mRate) {
                if (this.mBandPosition == 0) {
                    wifiTX.rate = getResources().getStringArray(R.array.rate_str_arr_0)[position];
                    this.mRatePosition = position;
                    return;
                }
                int i2 = this.mPreamblePosition;
                if (i2 == 0 || i2 == 1) {
                    this.mWifiTX.rate = getResources().getStringArray(R.array.rate_str_arr_2)[position];
                    this.mRatePosition = position;
                } else if (i2 == 2 && this.mBandWidthPosition == 0) {
                    wifiTX.rate = getResources().getStringArray(R.array.rate_str_arr_0)[position];
                    this.mRatePosition = position;
                }
                this.mWifiTX.rate = getResources().getStringArray(R.array.rate_str_arr_1)[position];
                this.mRatePosition = position;
                return;
            }
            if (parent == this.mMode) {
                wifiTX.mode = getResources().getStringArray(R.array.mode_str_arr)[position];
                this.mModePosition = position;
            } else if (parent == this.mGuardInterval) {
                wifiTX.guardinterval = getResources().getStringArray(R.array.guardinterval_int_arr)[position];
                this.mGuardIntervalPosition = position;
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    class WifiTXHandler extends Handler {
        public WifiTXHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Message uiMessage;
            Message uiMessage2;
            int i = msg.what;
            if (i != 3) {
                if (i == 4) {
                    if (WifiEUTHelper.getHelper().txStop()) {
                        uiMessage2 = WifiTXActivity.this.mHandler.obtainMessage(4, 1, 0);
                    } else {
                        uiMessage2 = WifiTXActivity.this.mHandler.obtainMessage(4, 0, 0);
                    }
                    WifiTXActivity.this.mHandler.sendMessage(uiMessage2);
                    return;
                }
                return;
            }
            if (WifiTXActivity.this.mModePosition == 1) {
                if (WifiEUTHelper.getHelper().txCw(WifiTXActivity.this.mWifiTX)) {
                    uiMessage = WifiTXActivity.this.mHandler.obtainMessage(3, 1, 0);
                    Log.d(WifiTXActivity.TAG, "WIFI_TX_CW uiMessage=" + uiMessage);
                } else {
                    uiMessage = WifiTXActivity.this.mHandler.obtainMessage(3, 0, 0);
                    Log.d(WifiTXActivity.TAG, "WIFI_TX_CW uiMessage=" + uiMessage);
                }
            } else if (WifiEUTHelper.getHelper().txGo(WifiTXActivity.this.mWifiTX)) {
                uiMessage = WifiTXActivity.this.mHandler.obtainMessage(3, 1, 0);
            } else {
                uiMessage = WifiTXActivity.this.mHandler.obtainMessage(3, 0, 0);
            }
            WifiTXActivity.this.mHandler.sendMessage(uiMessage);
        }
    }

    private void initUI() {
        this.mPktlength = (EditText) findViewById(R.id.wifi_eut_pkt_length);
        this.mPktlength.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.wifi.WifiTXActivity.4
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (WifiTXActivity.this.mPktlength.getText().toString().length() != 0 && Integer.parseInt(WifiTXActivity.this.mPktlength.getText().toString()) >= 64 && Integer.parseInt(WifiTXActivity.this.mPktlength.getText().toString()) <= 4095) {
                    if (WifiTXActivity.this.mWifiTX != null) {
                        WifiTXActivity.this.mWifiTX.pktlength = WifiTXActivity.this.mPktlength.getText().toString();
                        return;
                    }
                    return;
                }
                Toast.makeText(WifiTXActivity.this, "number between 64 and 4095", 0).show();
            }
        });
        this.mPktcnt = (EditText) findViewById(R.id.wifi_eut_pkt_cnt);
        this.mPktcnt.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.wifi.WifiTXActivity.5
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (WifiTXActivity.this.mPktcnt.getText().toString().length() != 0 && Integer.parseInt(WifiTXActivity.this.mPktcnt.getText().toString()) >= 0 && Integer.parseInt(WifiTXActivity.this.mPktcnt.getText().toString()) <= 65535) {
                    if (WifiTXActivity.this.mWifiTX != null) {
                        WifiTXActivity.this.mWifiTX.pktcnt = WifiTXActivity.this.mPktcnt.getText().toString();
                        return;
                    }
                    return;
                }
                Toast.makeText(WifiTXActivity.this, "number between 0 and 65535", 0).show();
            }
        });
        this.mPowerLevel = (EditText) findViewById(R.id.wifi_eut_powerLevel);
        this.mPowerLevel.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.wifi.WifiTXActivity.6
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (WifiTXActivity.this.mPowerLevel.getText().toString().length() != 0 && Integer.parseInt(WifiTXActivity.this.mPowerLevel.getText().toString()) >= 0 && Integer.parseInt(WifiTXActivity.this.mPowerLevel.getText().toString()) <= WifiTXActivity.this.mPowerLevelMax) {
                    if (WifiTXActivity.this.mWifiTX != null) {
                        WifiTXActivity.this.mWifiTX.powerlevel = WifiTXActivity.this.mPowerLevel.getText().toString();
                        return;
                    }
                    return;
                }
                Toast.makeText(WifiTXActivity.this, "number between 0 and " + WifiTXActivity.this.mPowerLevelMax, 0).show();
            }
        });
        this.mMode = (Spinner) findViewById(R.id.wifi_eut_mode);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.mode_str_arr));
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mMode.setAdapter((SpinnerAdapter) modeAdapter);
        this.mMode.setOnItemSelectedListener(this);
        this.mGuardInterval = (Spinner) findViewById(R.id.wifi_eut_guard_interval);
        ArrayAdapter<String> guardintervalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.guardinterval_str_arr));
        guardintervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mGuardInterval.setAdapter((SpinnerAdapter) guardintervalAdapter);
        this.mGuardInterval.setOnItemSelectedListener(this);
        this.mGo = (Button) findViewById(R.id.wifi_tx_go);
        this.mStop = (Button) findViewById(R.id.wifi_tx_stop);
        this.mBand = (Spinner) findViewById(R.id.wifi_eut_band);
        this.mBandTitle = (TextView) findViewById(R.id.wifi_eut_bandTitle);
        this.mGo.setOnClickListener(this);
        this.mStop.setOnClickListener(this);
        this.mStop.setEnabled(false);
    }

    private void initUIMarlin() {
        Log.d(TAG, "initUIMarlin start");
        this.mBand.setVisibility(8);
        this.mBandTitle.setVisibility(8);
        this.mChannel = (Spinner) findViewById(R.id.wifi_eut_channel);
        if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
            this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_3));
        } else {
            this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_0));
        }
        this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
        this.mChannel.setOnItemSelectedListener(this);
        this.mOffset = (Spinner) findViewById(R.id.wifi_eut_offset);
        this.offsetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.offset_freqnote_str_arr_0));
        this.offsetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mOffset.setAdapter((SpinnerAdapter) this.offsetAdapter);
        this.mOffset.setOnItemSelectedListener(this);
        this.mRate = (Spinner) findViewById(R.id.wifi_eut_rate);
        if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
            this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tx_rate_str_arr));
        } else {
            this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rate_str_arr));
        }
        this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
        this.mRate.setOnItemSelectedListener(this);
        this.mPreamble = (Spinner) findViewById(R.id.wifi_eut_preamble);
        this.preadmbleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.preamble_str_arr_0));
        this.preadmbleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mPreamble.setAdapter((SpinnerAdapter) this.preadmbleAdapter);
        this.mPreamble.setOnItemSelectedListener(this);
        this.mBandWidth = (Spinner) findViewById(R.id.wifi_eut_band_width);
        this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr));
        this.bandWidthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mBandWidth.setAdapter((SpinnerAdapter) this.bandWidthAdapter);
        this.mBandWidth.setOnItemSelectedListener(this);
        this.mBandSBW = (Spinner) findViewById(R.id.wifi_eut_band_sbw);
        this.bandSBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandsbw_str_arr));
        this.bandSBWAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mBandSBW.setAdapter((SpinnerAdapter) this.bandSBWAdapter);
        this.mBandSBW.setOnItemSelectedListener(this);
        this.mPath = (Spinner) findViewById(R.id.wifi_eut_path);
        this.pathAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rf_path_str_arr));
        this.pathAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mPath.setAdapter((SpinnerAdapter) this.pathAdapter);
        this.mPath.setOnItemSelectedListener(this);
        this.mPathTableRow = (TableRow) findViewById(R.id.wifi_eut_path_tr);
        this.mCbwTableRow = (TableRow) findViewById(R.id.wifi_eut_cbw_tr);
        this.mSbwTableRow = (TableRow) findViewById(R.id.wifi_eut_sbw_tr);
        this.mOffsetTableRow = (TableRow) findViewById(R.id.wifi_eut_offset_tr);
        this.mStandrard = (Spinner) findViewById(R.id.wifi_eut_standard);
        this.standrardAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rf_standrard_str_arr));
        if (Const.isMarlin2()) {
            this.standrardAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rf_standrard_str_arr_marlin2));
            this.mPathTableRow.setVisibility(8);
            this.mCbwTableRow.setVisibility(8);
            this.mSbwTableRow.setVisibility(8);
            this.mOffsetTableRow.setVisibility(8);
        } else if (Const.isMarlin3Lite()) {
            this.mPathTableRow.setVisibility(8);
        }
        this.standrardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mStandrard.setAdapter((SpinnerAdapter) this.standrardAdapter);
        this.mStandrard.setOnItemSelectedListener(this);
    }

    private void initUIBrcm() {
        this.bandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.band_str_arr));
        this.bandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mBand.setAdapter((SpinnerAdapter) this.bandAdapter);
        this.mBand.setOnItemSelectedListener(this);
        Log.d(TAG, "initUIBrcm Channel");
        this.mChannel = (Spinner) findViewById(R.id.wifi_eut_channel);
        this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_arr_0));
        this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
        this.mChannel.setOnItemSelectedListener(this);
        Log.d(TAG, "initUIBrcm Rate");
        this.mRate = (Spinner) findViewById(R.id.wifi_eut_rate);
        this.rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rate_str_arr_0));
        this.rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mRate.setAdapter((SpinnerAdapter) this.rateAdapter);
        this.mRate.setOnItemSelectedListener(this);
        Log.d(TAG, "initUIBrcm Preamble");
        this.mPreamble = (Spinner) findViewById(R.id.wifi_eut_preamble);
        Log.d(TAG, "onCreate mBandPosition" + this.mBandPosition);
        this.preadmbleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.preamble_str_arr_0));
        this.preadmbleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mPreamble.setAdapter((SpinnerAdapter) this.preadmbleAdapter);
        this.mPreamble.setOnItemSelectedListener(this);
        Log.d(TAG, "initUIBrcm bandwidth");
        this.mBandWidth = (Spinner) findViewById(R.id.wifi_eut_band_width);
        Log.d(TAG, "onCreate mBandPosition" + this.mBandPosition);
        this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_0));
        this.bandWidthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mBandWidth.setAdapter((SpinnerAdapter) this.bandWidthAdapter);
        this.mBandWidth.setOnItemSelectedListener(this);
    }

    private boolean checkPosition(Spinner spinner, int position) {
        Log.d(TAG, "checkPosition spinner:" + spinner.getClass().getName() + ",position=" + position);
        if (spinner == this.mBand) {
            ArrayAdapter<String> arrayAdapter = this.bandAdapter;
            return arrayAdapter != null && position < arrayAdapter.getCount();
        }
        if (spinner == this.mChannel) {
            ArrayAdapter<String> arrayAdapter2 = this.channelAdapter;
            return arrayAdapter2 != null && position < arrayAdapter2.getCount();
        }
        if (spinner == this.mRate) {
            ArrayAdapter<String> arrayAdapter3 = this.rateAdapter;
            return arrayAdapter3 != null && position < arrayAdapter3.getCount();
        }
        if (spinner == this.mBandWidth) {
            ArrayAdapter<String> arrayAdapter4 = this.bandWidthAdapter;
            return arrayAdapter4 != null && position < arrayAdapter4.getCount();
        }
        if (spinner == this.mBandSBW) {
            ArrayAdapter<String> arrayAdapter5 = this.bandSBWAdapter;
            return arrayAdapter5 != null && position < arrayAdapter5.getCount();
        }
        if (spinner == this.mPath) {
            ArrayAdapter<String> arrayAdapter6 = this.pathAdapter;
            return arrayAdapter6 != null && position < arrayAdapter6.getCount();
        }
        if (spinner == this.mStandrard) {
            ArrayAdapter<String> arrayAdapter7 = this.standrardAdapter;
            return arrayAdapter7 != null && position < arrayAdapter7.getCount();
        }
        if (spinner != this.mOffset) {
            return false;
        }
        ArrayAdapter<String> arrayAdapter8 = this.offsetAdapter;
        return arrayAdapter8 != null && position < arrayAdapter8.getCount();
    }

    private void refreshUI() {
        if (this.mRefreshUI) {
            try {
                this.mBandPosition = this.mPref.getInt(BAND, 0);
                this.mChannelPosition = this.mPref.getInt(CHANNEL, 0);
                this.mOffsetPosition = this.mPref.getInt(OFFSET, 0);
                this.mRatePosition = this.mPref.getInt(RATE, 0);
                this.mModePosition = this.mPref.getInt(MODE, 0);
                this.mPreamblePosition = this.mPref.getInt(PREAMBLE, 0);
                this.mBandWidthPosition = this.mPref.getInt(BAND_WIDTH, 0);
                this.mBandSBWPosition = this.mPref.getInt(BAND_SBW, 0);
                this.mGuardIntervalPosition = this.mPref.getInt(GUARD_INTERVAL, 0);
                if (Const.isMarlin3Lite()) {
                    this.mPathPosition = this.mPref.getInt(PATH, 1);
                } else {
                    this.mPathPosition = this.mPref.getInt(PATH, 0);
                }
                this.mStandrardPosition = this.mPref.getInt(STANDRARD, 0);
                this.mPktlength.setText(this.mPref.getString(PKT_LENGTH, ""));
                this.mPktcnt.setText(this.mPref.getString(PKT_CNT, ""));
                this.mBandSBW.setSelection(checkPosition(this.mBandSBW, this.mBandSBWPosition) ? this.mBandSBWPosition : 0);
                this.mPath.setSelection(checkPosition(this.mPath, this.mPathPosition) ? this.mPathPosition : 0);
                this.mStandrard.setSelection(checkPosition(this.mStandrard, this.mStandrardPosition) ? this.mStandrardPosition : 0);
                this.mBand.setSelection(checkPosition(this.mBand, this.mBandPosition) ? this.mBandPosition : 0);
                this.mChannel.setSelection(checkPosition(this.mChannel, this.mChannelPosition) ? this.mChannelPosition : 0);
                this.mOffset.setSelection(checkPosition(this.mOffset, this.mOffsetPosition) ? this.mOffsetPosition : 0);
                this.mRate.setSelection(checkPosition(this.mRate, this.mRatePosition) ? this.mRatePosition : 0);
                this.mBandWidth.setSelection(checkPosition(this.mBandWidth, this.mBandWidthPosition) ? this.mBandWidthPosition : 0);
                this.mGuardInterval.setSelection(this.mGuardIntervalPosition);
                this.mMode.setSelection(this.mModePosition);
                this.mPreamble.setSelection(this.mPreamblePosition);
                Log.d(TAG, "refreshUI mWifiTX.path: " + this.mWifiTX.path);
                Log.d(TAG, "refreshUI mWifiTX.rate: " + this.mWifiTX.rate);
                Log.d(TAG, "refreshUI mWifiTX.pktlength: " + this.mWifiTX.pktlength);
                Log.d(TAG, "refreshUI mWifiTX.pktcnt: " + this.mWifiTX.pktcnt);
                Log.d(TAG, "refreshUI mWifiTX.preamble: " + this.mWifiTX.preamble);
                Log.d(TAG, "refreshUI mWifiTX.bandwidth: " + this.mWifiTX.bandwidth);
                Log.d(TAG, "refreshUI mWifiTX.bandsbw: " + this.mWifiTX.bandsbw);
                Log.d(TAG, "refreshUI mWifiTX.channel: " + this.mWifiTX.channel);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
