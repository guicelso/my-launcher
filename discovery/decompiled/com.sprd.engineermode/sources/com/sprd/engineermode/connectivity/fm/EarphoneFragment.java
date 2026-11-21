package com.sprd.engineermode.connectivity.fm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.fmradio.FmConstants;
import com.android.fmradio.FmManagerSelect;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.AudioManagerProxy;

/* JADX INFO: loaded from: classes4.dex */
public class EarphoneFragment extends AbsFMFragment implements View.OnClickListener {
    private static final int FM_HEADSET = 0;
    private static final int FM_SPEAKER = 1;
    private static final int HEADSET_DIALOG = 1;
    private static final int HEADSET_PLUG_IN = 1;
    private static final float POWER_UP_START_FREQUENCY = 87.5f;
    private static final int RADIO_AUDIO_DEVICE_WIRED_HEADSET = 0;
    private static final String TAG = "EarphoneFragment";
    public static String mChannelValue;
    private Button mAudioModeButton;
    private EditText mChannelEdit;
    private Button mChannelSet;
    private Context mContext;
    private Button mEarphonePlayButton;
    private Button mExtrovertedPlayButton;
    private Button mNoiseScanButton;
    private Button mRdsBlerButton;
    private Button mRegModeButton;
    private Button mSwitchEarphoneaAndExtroverButton;
    private EditText mSwitchEarphoneaAndExtroverEditText;
    private Button mTuneModeButton;
    public static int FM_MODE = 0;
    private static FmManagerSelect mFmManager = null;
    private int mSwitchCounts = 0;
    private int mValueHeadSetPlug = 1;
    private int mEarphoneVolume = 0;
    private int mSpeakerVolume = 0;
    private AudioManager mAudioManager = null;
    private Dialog mDialog = null;
    private ProgressDialog mSearchStationDialog = null;
    private boolean isFmOn = false;
    private Handler switchEarphoneaAndExtroverHandler = new Handler();
    public Runnable switchEarphoneaAndExtroverRunnable = new Runnable() { // from class: com.sprd.engineermode.connectivity.fm.EarphoneFragment.1
        @Override // java.lang.Runnable
        public void run() {
            if (EarphoneFragment.this.mSwitchCounts >= EarphoneFragment.this.getSwitchCounts() * 2) {
                EarphoneFragment.this.mSwitchCounts = 0;
                EarphoneFragment.this.mSearchStationDialog.cancel();
            } else {
                EarphoneFragment.setFMPlayerRoute(EarphoneFragment.this.mSwitchCounts % 2, EarphoneFragment.mChannelValue);
                EarphoneFragment.access$008(EarphoneFragment.this);
                EarphoneFragment.this.switchEarphoneaAndExtroverHandler.postDelayed(EarphoneFragment.this.switchEarphoneaAndExtroverRunnable, 2000L);
            }
        }
    };
    private BroadcastReceiver earphonePluginReceiver = new BroadcastReceiver() { // from class: com.sprd.engineermode.connectivity.fm.EarphoneFragment.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent earphoneIntent) {
            if (earphoneIntent != null && earphoneIntent.getAction() != null && earphoneIntent.getAction().equalsIgnoreCase("android.intent.action.HEADSET_PLUG")) {
                Log.d(EarphoneFragment.TAG, "earphonePluginReceiver onReceiver");
                EarphoneFragment.this.mValueHeadSetPlug = earphoneIntent.getIntExtra("state", -1) == 1 ? 0 : 1;
                if (EarphoneFragment.this.mValueHeadSetPlug == 0) {
                    if (EarphoneFragment.this.mDialog != null) {
                        EarphoneFragment.this.mDialog.cancel();
                    }
                } else if (EarphoneFragment.this.mValueHeadSetPlug == 1) {
                    if (EarphoneFragment.this.mSearchStationDialog != null) {
                        EarphoneFragment.this.mSearchStationDialog.cancel();
                    }
                    EarphoneFragment.this.mSwitchCounts = 0;
                    EarphoneFragment.this.mExtrovertedPlayButton.setEnabled(true);
                    EarphoneFragment.this.mEarphonePlayButton.setEnabled(true);
                    new Thread(new Runnable() { // from class: com.sprd.engineermode.connectivity.fm.EarphoneFragment.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            EarphoneFragment.this.powerOffFM();
                        }
                    }).start();
                    EarphoneFragment.this.CreateDialog(1).show();
                }
            }
        }
    };
    private BroadcastReceiver volumeReceiver = new BroadcastReceiver() { // from class: com.sprd.engineermode.connectivity.fm.EarphoneFragment.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int streamType;
            int index;
            if (intent.hasExtra(AudioManagerProxy.EXTRA_VOLUME_STREAM_TYPE) && intent.hasExtra(AudioManagerProxy.EXTRA_VOLUME_STREAM_VALUE) && (streamType = intent.getIntExtra(AudioManagerProxy.EXTRA_VOLUME_STREAM_TYPE, -1)) == 3 && (index = intent.getIntExtra(AudioManagerProxy.EXTRA_VOLUME_STREAM_VALUE, -1)) != -1) {
                Log.d(EarphoneFragment.TAG, "stream type " + streamType + "value " + index);
                if (EarphoneFragment.FM_MODE == 0) {
                    EarphoneFragment.this.mEarphoneVolume = index;
                } else {
                    EarphoneFragment.this.mSpeakerVolume = index;
                }
                EarphoneFragment.this.setVolume(index);
            }
        }
    };

    static /* synthetic */ int access$008(EarphoneFragment x0) {
        int i = x0.mSwitchCounts;
        x0.mSwitchCounts = i + 1;
        return i;
    }

    public boolean setVolume(int volume) {
        Log.d(TAG, "setVolume FM_Volume=" + volume);
        if (!this.isFmOn) {
            return false;
        }
        AudioManagerProxy.setParameters("FM_Volume=" + volume);
        if (volume == 0) {
            mFmManager.setMute(true);
        } else {
            mFmManager.setMute(false);
        }
        return true;
    }

    private boolean isHeadSetIn() {
        return this.mValueHeadSetPlug == 0;
    }

    protected Dialog CreateDialog(int id) {
        if (id == 1) {
            if (this.mDialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
                this.mDialog = builder.setTitle(R.string.fm_dialog_tittle).setMessage(R.string.fm_dialog_message).create();
                this.mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.sprd.engineermode.connectivity.fm.EarphoneFragment.4
                    @Override // android.content.DialogInterface.OnKeyListener
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == 4) {
                            if (EarphoneFragment.this.mDialog != null) {
                                EarphoneFragment.this.mDialog.cancel();
                            }
                            return true;
                        }
                        if (keyCode == 84) {
                            return true;
                        }
                        return false;
                    }
                });
            }
            return this.mDialog;
        }
        return null;
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        getActivity().setVolumeControlStream(3);
        mFmManager = new FmManagerSelect(this.mContext);
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.mEarphoneVolume = this.mAudioManager.getStreamVolume(3);
        this.mSpeakerVolume = this.mAudioManager.getStreamVolume(3);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.HEADSET_PLUG");
        this.mContext.registerReceiver(this.earphonePluginReceiver, filter);
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(AudioManagerProxy.VOLUME_CHANGED_ACTION);
        this.mContext.registerReceiver(this.volumeReceiver, filter2);
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (!isHeadSetIn()) {
            CreateDialog(1).show();
        }
        startRender();
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater);
    }

    private View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.earphone_fragment_main, (ViewGroup) null);
        this.mEarphonePlayButton = (Button) view.findViewById(R.id.earphone_play);
        this.mExtrovertedPlayButton = (Button) view.findViewById(R.id.extroverted_play);
        this.mSwitchEarphoneaAndExtroverEditText = (EditText) view.findViewById(R.id.earphone_and_extroverted_switch_edittext);
        this.mEarphonePlayButton.setOnClickListener(this);
        this.mExtrovertedPlayButton.setOnClickListener(this);
        this.mSwitchEarphoneaAndExtroverButton = (Button) view.findViewById(R.id.earphone_and_extroverted_switch_start_switch);
        this.mSwitchEarphoneaAndExtroverButton.setOnClickListener(this);
        this.mRdsBlerButton = (Button) view.findViewById(R.id.rds_bler_btn);
        this.mRdsBlerButton.setOnClickListener(this);
        this.mNoiseScanButton = (Button) view.findViewById(R.id.noise_scan_btn);
        this.mNoiseScanButton.setOnClickListener(this);
        this.mRegModeButton = (Button) view.findViewById(R.id.reg_mode_btn);
        this.mRegModeButton.setOnClickListener(this);
        this.mTuneModeButton = (Button) view.findViewById(R.id.tune_mode_btn);
        this.mTuneModeButton.setOnClickListener(this);
        this.mAudioModeButton = (Button) view.findViewById(R.id.audio_mode_btn);
        this.mAudioModeButton.setOnClickListener(this);
        this.mRdsBlerButton.setEnabled(false);
        this.mNoiseScanButton.setEnabled(false);
        this.mRegModeButton.setEnabled(false);
        this.mTuneModeButton.setEnabled(false);
        this.mAudioModeButton.setEnabled(false);
        this.mChannelEdit = (EditText) view.findViewById(R.id.channel_edittext);
        this.mChannelSet = (Button) view.findViewById(R.id.channel_switch);
        this.mChannelSet.setOnClickListener(this);
        disableControl();
        initProgressDialog();
        return view;
    }

    public void initProgressDialog() {
        this.mSearchStationDialog = new ProgressDialog(getActivity());
        this.mSearchStationDialog.setProgressStyle(0);
        this.mSearchStationDialog.setMessage(getResources().getString(R.string.earphone_and_extroverted_switch_underway));
        this.mSearchStationDialog.setIndeterminate(false);
        this.mSearchStationDialog.setCancelable(false);
        this.mSearchStationDialog.setButton(getResources().getString(R.string.fm_cancel), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.fm.EarphoneFragment.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                EarphoneFragment.this.switchEarphoneaAndExtroverHandler.removeCallbacks(EarphoneFragment.this.switchEarphoneaAndExtroverRunnable);
                EarphoneFragment.this.mSwitchCounts = 0;
                dialog.dismiss();
            }
        });
        this.mSearchStationDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.sprd.engineermode.connectivity.fm.EarphoneFragment.6
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialoge, int keyCode, KeyEvent event) {
                if (84 == keyCode || 3 == keyCode) {
                    return true;
                }
                return false;
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.audio_mode_btn /* 2131296353 */:
                Intent audio_intent = new Intent(this.mContext, (Class<?>) FMAudioActivity.class);
                startActivity(audio_intent);
                break;
            case R.id.channel_switch /* 2131296503 */:
                mChannelValue = this.mChannelEdit.getText().toString().trim();
                String str = mChannelValue;
                if (str == null || str.equals("")) {
                    Toast.makeText(getActivity(), R.string.input_channel_tips, 1).show();
                } else {
                    enableControl();
                    closeFmApk();
                }
                break;
            case R.id.earphone_and_extroverted_switch_start_switch /* 2131296606 */:
                if (!isHeadSetIn()) {
                    CreateDialog(1).show();
                } else if (this.mSwitchEarphoneaAndExtroverEditText.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.earphone_and_extroverted_switch_counts_string), 1).show();
                } else {
                    this.mExtrovertedPlayButton.setEnabled(true);
                    this.mEarphonePlayButton.setEnabled(true);
                    new Thread(new Runnable() { // from class: com.sprd.engineermode.connectivity.fm.EarphoneFragment.9
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!EarphoneFragment.this.isFmOn) {
                                EarphoneFragment.this.powerOnFM();
                            }
                            EarphoneFragment.this.startSwitchEarphoneAndExtroverted();
                        }
                    }).start();
                    if (!this.mSearchStationDialog.isShowing()) {
                        this.mSearchStationDialog.show();
                    }
                }
                break;
            case R.id.earphone_play /* 2131296608 */:
                if (!isHeadSetIn()) {
                    CreateDialog(1).show();
                } else {
                    this.mExtrovertedPlayButton.setEnabled(true);
                    this.mEarphonePlayButton.setEnabled(false);
                    new Thread(new Runnable() { // from class: com.sprd.engineermode.connectivity.fm.EarphoneFragment.7
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!EarphoneFragment.this.isFmOn) {
                                EarphoneFragment.this.powerOnFM();
                            }
                            EarphoneFragment.FM_MODE = 0;
                            EarphoneFragment.setFMPlayerRoute(EarphoneFragment.FM_MODE, EarphoneFragment.mChannelValue);
                            EarphoneFragment earphoneFragment = EarphoneFragment.this;
                            earphoneFragment.setVolume(earphoneFragment.mEarphoneVolume);
                        }
                    }).start();
                    this.mRdsBlerButton.setEnabled(true);
                    this.mNoiseScanButton.setEnabled(true);
                    this.mRegModeButton.setEnabled(true);
                    this.mTuneModeButton.setEnabled(true);
                    this.mAudioModeButton.setEnabled(true);
                }
                break;
            case R.id.extroverted_play /* 2131296662 */:
                if (!isHeadSetIn()) {
                    CreateDialog(1).show();
                } else {
                    this.mEarphonePlayButton.setEnabled(true);
                    this.mExtrovertedPlayButton.setEnabled(false);
                    new Thread(new Runnable() { // from class: com.sprd.engineermode.connectivity.fm.EarphoneFragment.8
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!EarphoneFragment.this.isFmOn) {
                                EarphoneFragment.this.powerOnFM();
                            }
                            EarphoneFragment.FM_MODE = 1;
                            EarphoneFragment.setFMPlayerRoute(EarphoneFragment.FM_MODE, EarphoneFragment.mChannelValue);
                            EarphoneFragment earphoneFragment = EarphoneFragment.this;
                            earphoneFragment.setVolume(earphoneFragment.mSpeakerVolume);
                        }
                    }).start();
                    this.mRdsBlerButton.setEnabled(true);
                    this.mNoiseScanButton.setEnabled(true);
                    this.mRegModeButton.setEnabled(true);
                    this.mTuneModeButton.setEnabled(true);
                    this.mAudioModeButton.setEnabled(true);
                }
                break;
            case R.id.noise_scan_btn /* 2131297121 */:
                Intent intent = new Intent(this.mContext, (Class<?>) FMNoiseScanActivity.class);
                startActivity(intent);
                break;
            case R.id.rds_bler_btn /* 2131297191 */:
                Intent bler_intent = new Intent(this.mContext, (Class<?>) FMRdsBlerActivity.class);
                startActivity(bler_intent);
                break;
            case R.id.reg_mode_btn /* 2131297195 */:
                Intent reg_intent = new Intent(this.mContext, (Class<?>) FMRegModeActivity.class);
                startActivity(reg_intent);
                break;
            case R.id.tune_mode_btn /* 2131297573 */:
                Intent tune_intent = new Intent(this.mContext, (Class<?>) FmTuneActivity.class);
                startActivity(tune_intent);
                break;
        }
    }

    private void closeFmApk() {
        Intent intent = new Intent();
        intent.setAction("com.android.music.musicservicecommand.sprd");
        intent.putExtra("command", "pause");
        this.mContext.sendBroadcast(intent);
    }

    private void disableControl() {
        this.mEarphonePlayButton.setEnabled(false);
        this.mExtrovertedPlayButton.setEnabled(false);
        this.mSwitchEarphoneaAndExtroverEditText.setEnabled(false);
        this.mSwitchEarphoneaAndExtroverButton.setEnabled(false);
        this.mChannelEdit.setEnabled(true);
        this.mChannelSet.setEnabled(true);
    }

    private void enableControl() {
        this.mEarphonePlayButton.setEnabled(true);
        this.mExtrovertedPlayButton.setEnabled(true);
        this.mSwitchEarphoneaAndExtroverEditText.setEnabled(true);
        this.mSwitchEarphoneaAndExtroverButton.setEnabled(true);
        this.mChannelEdit.setEnabled(false);
        this.mChannelSet.setEnabled(false);
    }

    public void startSwitchEarphoneAndExtroverted() {
        this.switchEarphoneaAndExtroverHandler.post(this.switchEarphoneaAndExtroverRunnable);
    }

    public int getSwitchCounts() {
        return Integer.parseInt(this.mSwitchEarphoneaAndExtroverEditText.getText().toString());
    }

    public static void setFMPlayerRoute(int headsetOrSpeaker, String mchanel) {
        Log.d(TAG, "setFMPlayerRoute: " + headsetOrSpeaker);
        FmManagerSelect fmManagerSelect = mFmManager;
        if (fmManagerSelect != null) {
            if (headsetOrSpeaker == 0) {
                fmManagerSelect.setSpeakerEnable(FmConstants.AudioPath.FM_AUDIO_PATH_HEADSET, false);
            } else {
                fmManagerSelect.setSpeakerEnable(FmConstants.AudioPath.FM_AUDIO_PATH_SPEAKER, true);
            }
            mFmManager.setMute(true);
            mFmManager.setRdsMode(true, false);
            Log.d(TAG, "FM channel is:" + mchanel);
            if (mchanel != null && mFmManager.tuneRadio(Float.valueOf(mchanel).floatValue())) {
                startRender();
            }
            mFmManager.setRdsMode(true, true);
            mFmManager.setMute(false);
            return;
        }
        Log.d(TAG, "mFmManager is null");
    }

    public void powerOnFM() {
        Log.d(TAG, "startPowerUp");
        FmManagerSelect fmManagerSelect = mFmManager;
        if (fmManagerSelect == null) {
            Log.d(TAG, "mFmManager is null");
            return;
        }
        boolean value = fmManagerSelect.openDev();
        if (!value) {
            Log.d(TAG, "powerUp fail");
            this.isFmOn = false;
            return;
        }
        this.mAudioManager.setParameters("FM_Volume=0");
        boolean value2 = mFmManager.powerUp(POWER_UP_START_FREQUENCY);
        if (!value2) {
            Log.e(TAG, "powerUp fail ");
            this.isFmOn = false;
        } else {
            this.isFmOn = true;
            Log.d(TAG, "FM open suceess!");
        }
    }

    private static synchronized void startRender() {
        Log.d(TAG, "startRender ");
        if (mFmManager != null) {
            mFmManager.setAudioPathEnable(FmConstants.AudioPath.FM_AUDIO_PATH_HEADSET, true);
        } else {
            Log.d(TAG, "mFmManager is null");
        }
    }

    private synchronized void stopRender() {
        Log.d(TAG, "stopRender");
        if (mFmManager != null) {
            mFmManager.setAudioPathEnable(FmConstants.AudioPath.FM_AUDIO_PATH_NONE, false);
        } else {
            Log.d(TAG, "mFmManager is null");
        }
    }

    public void powerOffFM() {
        Log.d(TAG, "power off fm");
        FmManagerSelect fmManagerSelect = mFmManager;
        if (fmManagerSelect == null) {
            Log.d(TAG, "mFmManager is null");
            return;
        }
        fmManagerSelect.setMute(true);
        mFmManager.setRdsMode(false, false);
        mFmManager.powerDown();
        mFmManager.closeDev();
        this.isFmOn = false;
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
        fragmentTransaction.remove(new FMFragment());
        fragmentTransaction.replace(R.id.fm_framelayout, new EarphoneFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy...");
        stopRender();
        powerOffFM();
        this.switchEarphoneaAndExtroverHandler.removeCallbacks(this.switchEarphoneaAndExtroverRunnable);
        BroadcastReceiver broadcastReceiver = this.earphonePluginReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }
        BroadcastReceiver broadcastReceiver2 = this.volumeReceiver;
        if (broadcastReceiver2 != null) {
            this.mContext.unregisterReceiver(broadcastReceiver2);
        }
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.cancel();
        }
    }
}
