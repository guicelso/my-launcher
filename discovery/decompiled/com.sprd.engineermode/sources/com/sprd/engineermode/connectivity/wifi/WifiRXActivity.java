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
import android.text.TextUtils;
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
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.intf.IConnectivityApi;
import java.text.NumberFormat;

/* JADX INFO: loaded from: classes4.dex */
public class WifiRXActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String BAND_CBW = "rx_band_cbw";
    private static final String BAND_SBW = "rx_band_sbw";
    private static final String CHANNEL = "rx_channel";
    private static final int DEINIT_TEST_STATUS = 1;
    private static final int DEINIT_VIEW = 1;
    private static final int GO_ALERT = 2;
    private static final int INIT_TEST_STATUS = 0;
    private static final int INIT_VIEW = 0;
    private static final String INSMODE_RES = "insmode_result";
    private static final String OFFSET = "cbw_channel_offset";
    private static final String PATH = "rf_rx_path";
    private static final int REFRESH_VIEW = 4;
    private static final String STANDRARD = "rf_standrard";
    private static final int STOP_ALERT = 3;
    private static final String TAG = "WifiRXActivity";
    private static final String TEST_NUM = "rx_test_num";
    private static final int WIFI_RX_GO = 2;
    private static final int WIFI_RX_STOP = 3;
    private AlertDialog mAlertDialog;
    private Spinner mBand;
    private Spinner mBandCBW;
    private int mBandCBWPosition;
    private int mBandPosition;
    private Spinner mBandSBW;
    private int mBandSBWPosition;
    private TextView mBandTitle;
    private Spinner mBandWidth;
    private int mBandWidthPosition;
    private TextView mBandWidthTitle;
    private TableRow mCbwTableRow;
    private Spinner mChannel;
    private int mChannelPosition;
    private AlertDialog mCmdAlertDialog;
    private Button mGo;
    private Spinner mOffset;
    private int mOffsetPosition;
    private TableRow mOffsetTableRow;
    private String mOkResult;
    private TextView mPERResult;
    private Spinner mPath;
    private int mPathPosition;
    private TableRow mPathTableRow;
    private TextView mPathText;
    private SharedPreferences mPref;
    private TextView mRXOkResult;
    private String mRXPerResult;
    private TableRow mSbwTableRow;
    private Spinner mStandrard;
    private int mStandrardPosition;
    private Button mStop;
    private EditText mTestRXNum;
    private IConnectivityApi.IWifiEut.WifiRX mWifiRX;
    private WifiRXHandler mWifiRXHandler;
    private boolean mInsmodeSuccessByEUT = false;
    private boolean mInsmodeSuccessByRX = false;
    ArrayAdapter<String> bandAdapter = null;
    ArrayAdapter<String> channelAdapter = null;
    ArrayAdapter<String> bandWidthAdapter = null;
    ArrayAdapter<String> bandCBWAdapter = null;
    ArrayAdapter<String> bandSBWAdapter = null;
    ArrayAdapter<String> standrardAdapter = null;
    ArrayAdapter<String> pathAdapter = null;
    ArrayAdapter<String> offsetAdapter = null;
    public Handler mHandler = new Handler() { // from class: com.sprd.engineermode.connectivity.wifi.WifiRXActivity.3
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg == null) {
                Log.d(WifiRXActivity.TAG, "UI Message is null");
                return;
            }
            int i = msg.what;
            if (i == 0) {
                if (msg.arg1 != 1) {
                    WifiRXActivity.this.mAlertDialog.setMessage("Wifi Init Fail");
                    if (!WifiRXActivity.this.isFinishing()) {
                        WifiRXActivity.this.mAlertDialog.show();
                        return;
                    }
                    return;
                }
                WifiRXActivity.this.mWifiRX = new IConnectivityApi.IWifiEut.WifiRX();
                WifiRXActivity.this.enabledView();
                WifiRXActivity.this.refreshUI();
                return;
            }
            if (i == 1) {
                WifiRXActivity.this.mAlertDialog.setMessage("Wifi Deinit Fail");
                if (!WifiRXActivity.this.isFinishing()) {
                    WifiRXActivity.this.mAlertDialog.show();
                    return;
                }
                return;
            }
            if (i == 2) {
                if (msg.arg1 == 1) {
                    WifiRXActivity.this.mCmdAlertDialog.setMessage("Wifi EUT RX Go Success");
                    WifiRXActivity.this.mGo.setEnabled(false);
                    WifiRXActivity.this.mStop.setEnabled(true);
                } else {
                    WifiRXActivity.this.mCmdAlertDialog.setMessage("Wifi EUT RX Go Fail");
                }
                if (!WifiRXActivity.this.isFinishing()) {
                    WifiRXActivity.this.mCmdAlertDialog.show();
                    return;
                }
                return;
            }
            if (i != 3) {
                if (i == 4) {
                    WifiRXActivity.this.mRXOkResult.setText(WifiRXActivity.this.mOkResult);
                    WifiRXActivity.this.mPERResult.setText(WifiRXActivity.this.mRXPerResult);
                    return;
                }
                return;
            }
            if (msg.arg1 == 1) {
                WifiRXActivity.this.mCmdAlertDialog.setMessage("Wifi EUT RX Stop Success");
                WifiRXActivity.this.mGo.setEnabled(true);
                WifiRXActivity.this.mStop.setEnabled(false);
            } else {
                WifiRXActivity.this.mCmdAlertDialog.setMessage("Wifi EUT RX Stop Fail");
            }
            Log.d(WifiRXActivity.TAG, "STOP_ALERT");
            if (!WifiRXActivity.this.isFinishing()) {
                WifiRXActivity.this.mCmdAlertDialog.show();
            }
            WifiRXActivity.this.analysisRXResult(WifiEUTHelper.getHelper().rxResult());
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_rx);
        getWindow().setSoftInputMode(32);
        this.mInsmodeSuccessByEUT = getIntent().getBooleanExtra(INSMODE_RES, false);
        initUI();
        if (Const.isMarlin()) {
            initUIMarlin();
        } else {
            Log.d(TAG, "initUIBrcm");
            initUIBrcm();
        }
        this.mPref = PreferenceManager.getDefaultSharedPreferences(this);
        this.mAlertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.alert_wifi)).setMessage((CharSequence) null).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.wifi.WifiRXActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                WifiRXActivity.this.finish();
            }
        }).create();
        this.mCmdAlertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.alert_wifi)).setMessage((CharSequence) null).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.wifi.WifiRXActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mWifiRXHandler = new WifiRXHandler(ht.getLooper());
        this.mWifiRX = new IConnectivityApi.IWifiEut.WifiRX();
        refreshUI();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        if (this.mStop.isEnabled()) {
            Message doMessage = this.mWifiRXHandler.obtainMessage(3);
            this.mWifiRXHandler.sendMessage(doMessage);
        }
        super.onBackPressed();
    }

    @Override // android.app.Activity
    protected void onPause() {
        Log.d(TAG, "onPause");
        if (Const.isMarlin()) {
            if (this.mWifiRX != null) {
                SharedPreferences.Editor editor = this.mPref.edit();
                editor.putInt(CHANNEL, this.mChannelPosition);
                editor.putInt(OFFSET, this.mOffsetPosition);
                editor.putString(TEST_NUM, this.mWifiRX.rxtestnum);
                if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                    editor.putInt(PATH, this.mPathPosition);
                    editor.putInt(BAND_CBW, this.mBandCBWPosition);
                    editor.putInt(STANDRARD, this.mStandrardPosition);
                    editor.putInt(BAND_SBW, this.mBandSBWPosition);
                }
                editor.commit();
            }
            Log.d(TAG, "mChannelOffsetPosition onPause" + this.mOffsetPosition);
        }
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.mWifiRXHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mWifiRXHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Message doMessage = null;
        getSettingParam();
        if (v == this.mGo) {
            doMessage = this.mWifiRXHandler.obtainMessage(2);
        } else if (v == this.mStop) {
            doMessage = this.mWifiRXHandler.obtainMessage(3);
        }
        if (doMessage != null) {
            this.mWifiRXHandler.sendMessage(doMessage);
        }
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
        if (this.mWifiRX == null) {
            return;
        }
        if (parent == this.mChannel) {
            if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                int i = this.mStandrardPosition;
                if (i == 0 || i == 1 || i == 2) {
                    int i2 = this.mBandCBWPosition;
                    if (i2 == 0) {
                        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_0)[position];
                    } else if (i2 == 1) {
                        int i3 = this.mOffsetPosition;
                        if (i3 == 0) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_n10)[position];
                        } else if (i3 == 1) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_p10)[position];
                        }
                    }
                } else if (i == 3) {
                    int i4 = this.mBandCBWPosition;
                    if (i4 == 0) {
                        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_1)[position];
                    } else if (i4 == 1) {
                        int i5 = this.mOffsetPosition;
                        if (i5 == 0) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_n10)[position];
                        } else if (i5 == 1) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_p10)[position];
                        }
                    }
                } else if (i == 4) {
                    int i6 = this.mBandCBWPosition;
                    if (i6 == 0) {
                        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_1)[position];
                    } else if (i6 == 1) {
                        int i7 = this.mOffsetPosition;
                        if (i7 == 0) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_n10)[position];
                        } else if (i7 == 1) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_p10)[position];
                        }
                    } else if (i6 == 2) {
                        int i8 = this.mOffsetPosition;
                        if (i8 == 0) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_n30)[position];
                        } else if (i8 == 1) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_n10)[position];
                        } else if (i8 == 2) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_p10)[position];
                        } else if (i8 == 3) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_p30)[position];
                        }
                    }
                } else if (i == 5) {
                    this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_1)[position];
                }
            } else if (Const.isMarlin2()) {
                this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_0)[position];
            }
            this.mChannelPosition = position;
            return;
        }
        if (parent == this.mOffset) {
            if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                int i9 = this.mStandrardPosition;
                if (i9 == 0 || i9 == 1 || i9 == 2) {
                    int i10 = this.mBandCBWPosition;
                    if (i10 == 0) {
                        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_0)[this.mChannelPosition];
                        return;
                    } else {
                        if (i10 == 1) {
                            if (position == 0) {
                                this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_n10)[this.mChannelPosition];
                                return;
                            } else {
                                if (position == 1) {
                                    this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_p10)[this.mChannelPosition];
                                    return;
                                }
                                return;
                            }
                        }
                        return;
                    }
                }
                if (i9 == 3) {
                    int i11 = this.mBandCBWPosition;
                    if (i11 == 0) {
                        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_1)[this.mChannelPosition];
                        return;
                    } else {
                        if (i11 == 1) {
                            if (position == 0) {
                                this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_n10)[this.mChannelPosition];
                                return;
                            } else {
                                if (position == 1) {
                                    this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_p10)[this.mChannelPosition];
                                    return;
                                }
                                return;
                            }
                        }
                        return;
                    }
                }
                if (i9 == 4) {
                    int i12 = this.mBandCBWPosition;
                    if (i12 == 0) {
                        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_1)[this.mChannelPosition];
                        return;
                    }
                    if (i12 == 1) {
                        if (position == 0) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_n10)[this.mChannelPosition];
                            return;
                        } else {
                            if (position == 1) {
                                this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_p10)[this.mChannelPosition];
                                return;
                            }
                            return;
                        }
                    }
                    if (i12 == 2) {
                        if (position == 0) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_n30)[this.mChannelPosition];
                            return;
                        }
                        if (position == 1) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_n10)[this.mChannelPosition];
                            return;
                        } else if (position == 2) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_p10)[this.mChannelPosition];
                            return;
                        } else {
                            if (position == 3) {
                                this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_p30)[this.mChannelPosition];
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                return;
            }
            return;
        }
        if (parent == this.mBandCBW) {
            if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                int i13 = this.mStandrardPosition;
                if (i13 == 0 || i13 == 1) {
                    this.mWifiRX.bandcbw = getResources().getStringArray(R.array.bandwidth_int_arr_0)[position];
                } else if (i13 == 2 || i13 == 3) {
                    this.mWifiRX.bandcbw = getResources().getStringArray(R.array.bandwidth_int_arr_1)[position];
                } else if (i13 == 4) {
                    this.mWifiRX.bandcbw = getResources().getStringArray(R.array.bandwidth_int_arr_2)[position];
                }
                if (position == 0) {
                    Log.d(TAG, " bandsbw_str_arr_0");
                    this.bandSBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandsbw_str_arr_0));
                    this.mBandSBWPosition = 0;
                    this.offsetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.offset_freqnote_str_arr_0));
                } else if (position == 1) {
                    Log.d(TAG, " bandsbw_str_arr_1");
                    this.bandSBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandsbw_str_arr_1));
                    this.mBandSBWPosition = 1;
                    this.offsetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.offset_freqnote_str_arr_1));
                } else if (position == 2) {
                    Log.d(TAG, " bandsbw_str_arr_2");
                    this.bandSBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandsbw_str_arr_2));
                    this.mBandSBWPosition = 2;
                    this.offsetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.offset_freqnote_str_arr_2));
                } else if (position == 3) {
                    Log.d(TAG, "bandsbw_str_arr_3");
                    this.bandSBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandsbw_str_arr_3));
                    this.mBandSBWPosition = 3;
                    this.offsetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.offset_freqnote_str_arr_2));
                }
                this.bandSBWAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.mBandSBW.setAdapter((SpinnerAdapter) this.bandSBWAdapter);
                this.bandSBWAdapter.notifyDataSetChanged();
                this.mBandSBW.setSelection(this.mBandSBWPosition);
                this.offsetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.mOffset.setAdapter((SpinnerAdapter) this.offsetAdapter);
                this.offsetAdapter.notifyDataSetChanged();
                this.mChannelPosition = 0;
                if (position == 0) {
                    int i14 = this.mStandrardPosition;
                    if (i14 == 0 || i14 == 1 || i14 == 2) {
                        this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_0));
                        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_0)[this.mChannelPosition];
                    } else if (i14 == 3 || i14 == 4) {
                        this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_1));
                        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_1)[this.mChannelPosition];
                    }
                } else if (position == 1) {
                    int i15 = this.mStandrardPosition;
                    if (i15 == 2) {
                        this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr));
                        int i16 = this.mOffsetPosition;
                        if (i16 == 0) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_n10)[this.mChannelPosition];
                        } else if (i16 == 1) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_p10)[this.mChannelPosition];
                        }
                    } else if (i15 == 3 || i15 == 4) {
                        this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_2));
                        int i17 = this.mOffsetPosition;
                        if (i17 == 0) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_n10)[this.mChannelPosition];
                        } else if (i17 == 1) {
                            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_2_p10)[this.mChannelPosition];
                        }
                    }
                } else if (position == 2 && this.mStandrardPosition == 4) {
                    this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_3));
                    int i18 = this.mOffsetPosition;
                    if (i18 == 0) {
                        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_n30)[this.mChannelPosition];
                    } else if (i18 == 1) {
                        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_n10)[this.mChannelPosition];
                    } else if (i18 == 2) {
                        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_p10)[this.mChannelPosition];
                    } else if (i18 == 3) {
                        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_3_p30)[this.mChannelPosition];
                    }
                }
                this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                this.channelAdapter.notifyDataSetChanged();
            }
            this.mBandCBWPosition = position;
            return;
        }
        if (parent == this.mBandSBW) {
            if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                int i19 = this.mBandCBWPosition;
                if (i19 == 0) {
                    this.mWifiRX.bandsbw = getResources().getStringArray(R.array.bandsbw_int_arr_0)[position];
                } else if (i19 == 1) {
                    this.mWifiRX.bandsbw = getResources().getStringArray(R.array.bandsbw_int_arr_1)[position];
                } else if (i19 == 2) {
                    this.mWifiRX.bandsbw = getResources().getStringArray(R.array.bandsbw_int_arr_2)[position];
                } else if (i19 == 3) {
                    this.mWifiRX.bandsbw = getResources().getStringArray(R.array.bandsbw_int_arr_3)[position];
                } else {
                    this.mWifiRX.bandsbw = getResources().getStringArray(R.array.bandsbw_int_arr_0)[position];
                }
                this.mBandSBWPosition = position;
                return;
            }
            return;
        }
        if (parent == this.mPath) {
            if (Const.isMarlin3()) {
                this.mWifiRX.path = getResources().getStringArray(R.array.rf_path_str_arr)[position];
                this.mPathPosition = position;
                return;
            }
            return;
        }
        if (parent == this.mStandrard) {
            if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                if (position == 0 || position == 1 || position == 2) {
                    int i20 = this.mBandCBWPosition;
                    if (i20 == 0) {
                        this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_0));
                    } else if (i20 == 1) {
                        this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr));
                    }
                } else if (position == 3) {
                    int i21 = this.mBandCBWPosition;
                    if (i21 == 0) {
                        this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_1));
                    } else if (i21 == 1) {
                        this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_2));
                    }
                } else if (position == 4) {
                    int i22 = this.mBandCBWPosition;
                    if (i22 == 0) {
                        this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_1));
                    } else if (i22 == 1) {
                        this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_2));
                    } else if (i22 == 2) {
                        this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_3));
                    }
                } else if (position == 5) {
                    this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_freqnote_str_arr_1));
                }
                this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                this.channelAdapter.notifyDataSetChanged();
                if (position == 0 || position == 1) {
                    this.bandCBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_0));
                } else if (position == 2 || position == 3) {
                    this.bandCBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_1));
                } else if (position == 4) {
                    this.bandCBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_2));
                } else if (position == 5) {
                    this.bandCBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_0));
                }
                this.bandCBWAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.mBandCBW.setAdapter((SpinnerAdapter) this.bandCBWAdapter);
                this.bandCBWAdapter.notifyDataSetChanged();
            }
            this.mStandrardPosition = position;
        }
    }

    private void onItemSelectBrcm(AdapterView<?> parent, View view, int position, long id) {
        IConnectivityApi.IWifiEut.WifiRX wifiRX = this.mWifiRX;
        if (wifiRX == null) {
            return;
        }
        if (parent == this.mBand) {
            if (position == 0) {
                Log.d(TAG, "-----band:2.4G-------");
                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_arr_0));
                this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                this.channelAdapter.notifyDataSetChanged();
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
                this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr));
                this.bandWidthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.mBandWidth.setAdapter((SpinnerAdapter) this.bandWidthAdapter);
                this.bandWidthAdapter.notifyDataSetChanged();
            }
            this.mWifiRX.band = getResources().getStringArray(R.array.band_str_arr)[position];
            this.mBandPosition = position;
            return;
        }
        if (parent == this.mBandWidth) {
            if (this.mBandPosition == 0) {
                Log.d(TAG, "-----band:2.4G BandWidth selection-------");
                this.mWifiRX.bandwidth = getResources().getStringArray(R.array.bandwidth_int_arr_0)[position];
                this.mBandWidthPosition = position;
                return;
            }
            Log.d(TAG, "-----band:5G BandWidth selection-------");
            if (position == 2) {
                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_arr_2));
                this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                this.channelAdapter.notifyDataSetChanged();
            } else {
                this.channelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.channel_arr_1));
                this.channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.mChannel.setAdapter((SpinnerAdapter) this.channelAdapter);
                this.channelAdapter.notifyDataSetChanged();
            }
            this.mWifiRX.bandwidth = getResources().getStringArray(R.array.bandwidth_int_arr)[position];
            this.mBandWidthPosition = position;
            return;
        }
        if (parent == this.mChannel) {
            if (this.mBandPosition == 0) {
                wifiRX.channel = getResources().getStringArray(R.array.channel_arr_0)[position];
                this.mChannelPosition = position;
                return;
            }
            if (this.mBandWidthPosition == 2) {
                wifiRX.channel = getResources().getStringArray(R.array.channel_arr_2)[position];
                this.mChannelPosition = position;
            }
            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_arr_1)[position];
            this.mChannelPosition = position;
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    class WifiRXHandler extends Handler {
        public WifiRXHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Message uiMessage;
            Message uiMessage2;
            int i = msg.what;
            if (i == 0) {
                if (!WifiRXActivity.this.mInsmodeSuccessByEUT) {
                    if (WifiEUTHelper.getHelper().insMod()) {
                        WifiRXActivity.this.mInsmodeSuccessByRX = true;
                        Message uiMessage3 = WifiRXActivity.this.mHandler.obtainMessage(0, 1, 0);
                        WifiRXActivity.this.mHandler.sendMessage(uiMessage3);
                        return;
                    } else {
                        Message uiMessage4 = WifiRXActivity.this.mHandler.obtainMessage(0, 0, 0);
                        WifiRXActivity.this.mHandler.sendMessage(uiMessage4);
                        return;
                    }
                }
                Message uiMessage5 = WifiRXActivity.this.mHandler.obtainMessage(0, 1, 0);
                WifiRXActivity.this.mHandler.sendMessage(uiMessage5);
                return;
            }
            if (i == 1) {
                if (!WifiRXActivity.this.mInsmodeSuccessByRX || WifiEUTHelper.getHelper().removeMod()) {
                    WifiRXActivity.this.mInsmodeSuccessByRX = false;
                    WifiRXActivity.this.finish();
                    return;
                } else {
                    Message uiMessage6 = WifiRXActivity.this.mHandler.obtainMessage(1);
                    WifiRXActivity.this.mHandler.sendMessage(uiMessage6);
                    return;
                }
            }
            if (i == 2) {
                if (WifiEUTHelper.getHelper().rxStart(WifiRXActivity.this.mWifiRX)) {
                    uiMessage = WifiRXActivity.this.mHandler.obtainMessage(2, 1, 0);
                } else {
                    uiMessage = WifiRXActivity.this.mHandler.obtainMessage(2, 0, 0);
                }
                WifiRXActivity.this.mHandler.sendMessage(uiMessage);
                return;
            }
            if (i == 3) {
                if (WifiEUTHelper.getHelper().rxStop()) {
                    uiMessage2 = WifiRXActivity.this.mHandler.obtainMessage(3, 1, 0);
                } else {
                    uiMessage2 = WifiRXActivity.this.mHandler.obtainMessage(3, 0, 0);
                }
                WifiRXActivity.this.mHandler.sendMessage(uiMessage2);
            }
        }
    }

    private void initUI() {
        this.mTestRXNum = (EditText) findViewById(R.id.wifi_eut_text_rx_num);
        this.mTestRXNum.addTextChangedListener(new TextWatcher() { // from class: com.sprd.engineermode.connectivity.wifi.WifiRXActivity.4
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (WifiRXActivity.this.mWifiRX != null) {
                    WifiRXActivity.this.mWifiRX.rxtestnum = WifiRXActivity.this.mTestRXNum.getText().toString();
                }
            }
        });
        this.mRXOkResult = (TextView) findViewById(R.id.wifi_eut_rx_ok_result);
        this.mPERResult = (TextView) findViewById(R.id.wifi_eut_per_result);
        this.mBand = (Spinner) findViewById(R.id.wifi_eut_band);
        this.mBandTitle = (TextView) findViewById(R.id.wifi_eut_bandTitle);
        this.mBandWidth = (Spinner) findViewById(R.id.wifi_eut_band_width);
        this.mBandWidthTitle = (TextView) findViewById(R.id.wifi_eut_bandWidthTitle);
        this.mPathText = (TextView) findViewById(R.id.wifi_rx_path_text);
        this.mGo = (Button) findViewById(R.id.wifi_rx_go);
        this.mStop = (Button) findViewById(R.id.wifi_rx_stop);
        this.mGo.setOnClickListener(this);
        this.mStop.setOnClickListener(this);
        this.mStop.setEnabled(false);
    }

    private void initUIMarlin() {
        this.mBand.setVisibility(8);
        this.mBandTitle.setVisibility(8);
        Log.d(TAG, " initUIMarlin");
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
        this.mBandCBW = (Spinner) findViewById(R.id.wifi_eut_band_cbw);
        this.bandCBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr));
        this.bandCBWAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mBandCBW.setAdapter((SpinnerAdapter) this.bandCBWAdapter);
        this.mBandCBW.setOnItemSelectedListener(this);
        this.mBandSBW = (Spinner) findViewById(R.id.wifi_eut_band_sbw);
        this.bandSBWAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandsbw_str_arr));
        this.bandSBWAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mBandSBW.setAdapter((SpinnerAdapter) this.bandSBWAdapter);
        this.mBandSBW.setOnItemSelectedListener(this);
        this.mPath = (Spinner) findViewById(R.id.wifi_rx_path);
        this.pathAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rf_path_str_arr));
        this.pathAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mPath.setAdapter((SpinnerAdapter) this.pathAdapter);
        this.mPath.setOnItemSelectedListener(this);
        this.mStandrard = (Spinner) findViewById(R.id.wifi_eut_standard);
        this.mBandWidth.setVisibility(8);
        this.mBandWidthTitle.setVisibility(8);
        this.mPathTableRow = (TableRow) findViewById(R.id.wifi_eut_path_tr);
        this.mCbwTableRow = (TableRow) findViewById(R.id.wifi_eut_cbw_tr);
        this.mSbwTableRow = (TableRow) findViewById(R.id.wifi_eut_sbw_tr);
        this.mOffsetTableRow = (TableRow) findViewById(R.id.wifi_eut_offset_tr);
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
        Log.d(TAG, "initUIBrcm start");
        Log.d(TAG, "initUIBrcm band");
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
        Log.d(TAG, "initUIBrcm bandwidth");
        this.mBandWidth = (Spinner) findViewById(R.id.wifi_eut_band_width);
        this.bandWidthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bandwidth_str_arr_0));
        this.bandWidthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mBandWidth.setAdapter((SpinnerAdapter) this.bandWidthAdapter);
        this.mBandWidth.setOnItemSelectedListener(this);
        Log.d(TAG, "initUIBrcm end");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enabledView() {
        this.mChannel.setEnabled(true);
        this.mOffset.setEnabled(true);
        if (Const.isMarlin3()) {
            this.mPath.setEnabled(true);
            this.mBandCBW.setEnabled(true);
            this.mBandSBW.setEnabled(true);
            this.mStandrard.setEnabled(true);
        } else if (Const.isMarlin3Lite() || Const.isMarlin3E()) {
            this.mBandCBW.setEnabled(true);
            this.mBandSBW.setEnabled(true);
            this.mStandrard.setEnabled(true);
        }
        this.mTestRXNum.setEnabled(true);
        this.mRXOkResult.setEnabled(true);
        this.mPERResult.setEnabled(true);
        this.mGo.setEnabled(true);
        this.mStop.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshUI() {
        try {
            if (Const.isMarlin()) {
                this.mChannelPosition = this.mPref.getInt(CHANNEL, 0);
                this.mOffsetPosition = this.mPref.getInt(OFFSET, 0);
                this.mTestRXNum.setText(this.mPref.getString(TEST_NUM, "0"));
            }
            if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
                this.mBandSBWPosition = this.mPref.getInt(BAND_SBW, 0);
                this.mBandCBWPosition = this.mPref.getInt(BAND_CBW, 0);
                this.mStandrardPosition = this.mPref.getInt(STANDRARD, 0);
                this.mBandCBW.setSelection(this.mBandCBWPosition);
                this.mBandSBW.setSelection(this.mBandSBWPosition);
                this.mStandrard.setSelection(this.mStandrardPosition);
                this.mPathPosition = this.mPref.getInt(PATH, 0);
                this.mPath.setSelection(this.mPathPosition);
            }
            this.mChannel.setSelection(0);
            this.mOffset.setSelection(0);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        Log.d(TAG, " mBandSBWPosition: " + this.mBandSBWPosition);
        Log.d(TAG, " mBandCBWPosition: " + this.mBandCBWPosition);
        Log.d(TAG, " mStandrardPosition: " + this.mStandrardPosition);
        Log.d(TAG, " mChannelPosition: " + this.mChannelPosition);
        Log.d(TAG, " mOffsetPosition: " + this.mOffsetPosition);
    }

    private void getSettingParam() {
        if (Const.isMarlin()) {
            if (this.mWifiRX.channel == null || this.mWifiRX.channel.trim().equals("")) {
                this.mWifiRX.channel = getResources().getStringArray(R.array.channel_freqnote_int_arr_0)[this.mChannelPosition];
            }
        } else {
            getSettingParamBrcm();
        }
        if (Const.isMarlin3() || Const.isMarlin3Lite() || Const.isMarlin3E()) {
            this.mWifiRX.path = getResources().getStringArray(R.array.rf_path_str_arr)[this.mPathPosition];
        }
        this.mWifiRX.rxtestnum = this.mTestRXNum.getText().toString();
        Log.d(TAG, "Now testing RX in WifiEUT, the setting param is: \n channel " + this.mWifiRX.channel + "\n test_num is " + this.mWifiRX.rxtestnum);
    }

    private void getSettingParamBrcm() {
        IConnectivityApi.IWifiEut.WifiRX wifiRX = this.mWifiRX;
        String[] stringArray = getResources().getStringArray(R.array.band_int_arr);
        int i = this.mBandPosition;
        wifiRX.band = stringArray[i];
        if (i == 0) {
            this.mWifiRX.bandwidth = getResources().getStringArray(R.array.bandwidth_int_arr_0)[this.mBandWidthPosition];
            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_arr_0)[this.mChannelPosition];
            return;
        }
        this.mWifiRX.bandwidth = getResources().getStringArray(R.array.bandwidth_int_arr)[this.mBandWidthPosition];
        this.mWifiRX.channel = getResources().getStringArray(R.array.channel_arr_1)[this.mChannelPosition];
        if (this.mBandWidthPosition == 2) {
            this.mWifiRX.channel = getResources().getStringArray(R.array.channel_arr_2)[this.mChannelPosition];
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void analysisRXResult(String result) {
        Log.d(TAG, "analysisRXResult" + result);
        if (result != null) {
            String[] str = result.split("\\:");
            if (str.length < 3) {
                Log.d(TAG, "AT Result is error");
                this.mOkResult = "error";
                this.mRXPerResult = "error";
            } else {
                String[] str1 = str[2].split("\\ ");
                for (int i = 0; i < str1.length; i++) {
                    Log.d(TAG, "str is " + str1[i]);
                    if (str1[i].contains("rx_end_count")) {
                        String[] str2 = str1[i].split("\\=");
                        this.mOkResult = str2[1];
                        String val1 = this.mTestRXNum.getText().toString();
                        if (TextUtils.isEmpty(val1)) {
                            val1 = "0";
                        }
                        if (TextUtils.isEmpty(this.mOkResult)) {
                            Log.d(TAG, "mOkResult is null");
                            return;
                        }
                        int a = Integer.valueOf(val1).intValue() - Integer.valueOf(this.mOkResult).intValue();
                        int b = Integer.valueOf(val1).intValue();
                        NumberFormat numberFormat = NumberFormat.getInstance();
                        numberFormat.setMaximumFractionDigits(2);
                        this.mRXPerResult = numberFormat.format((a / b) * 100.0f) + "%";
                    }
                }
            }
        } else {
            Log.d(TAG, "AT Result is null");
            this.mOkResult = "null";
            this.mRXPerResult = "null";
        }
        Message uiMessage = this.mHandler.obtainMessage(4);
        this.mHandler.sendMessage(uiMessage);
    }
}
