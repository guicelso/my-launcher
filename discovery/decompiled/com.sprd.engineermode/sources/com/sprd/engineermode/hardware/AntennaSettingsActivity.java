package com.sprd.engineermode.hardware;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.exception.EmException;
import com.unisoc.engineermode.core.intf.IAntenna;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes4.dex */
public class AntennaSettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final int DELAY_TIME_POWEROFF = 3000;
    private static final int GET_ANTENNA_STATE = 0;
    private static final String KEY_C2K_SET = "c2k_set";
    private static final String KEY_GSM_SET = "gsm_set";
    private static final String KEY_LTE_SET = "lte_set";
    private static final String KEY_WCDMA_SET = "wcdma_set";
    private static final int SET_ANTENNA = 1;
    private static final String TAG = "AntennaSettingsActivity";
    private AnSetHandler mAnSetHandler;
    private Handler mUiThread = new Handler();
    private IAntenna antenna = CoreApi.getHardwareApi().antennaApi();
    private IAntenna.AntennaState[] gsmListPrefValues = {IAntenna.AntennaState.PRIMARY_DIVERSITY, IAntenna.AntennaState.PRIMARY, IAntenna.AntennaState.DIVERSITY};
    private IAntenna.AntennaState[] wcdmaListPrefValues = {IAntenna.AntennaState.PRIMARY_DIVERSITY, IAntenna.AntennaState.PRIMARY, IAntenna.AntennaState.DIVERSITY};
    private IAntenna.AntennaState[] lteListPrefValues = {IAntenna.AntennaState.PRIMARY_DIVERSITY, IAntenna.AntennaState.PRIMARY, IAntenna.AntennaState.DIVERSITY};
    private IAntenna.AntennaState[] c2kListPrefValues = {IAntenna.AntennaState.PRIMARY_DIVERSITY, IAntenna.AntennaState.PRIMARY, IAntenna.AntennaState.DIVERSITY, IAntenna.AntennaState.PRIMARY_DIVERSITY_DYNAMIC};
    private List<StatusData> statusData = new ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    enum NetworkType {
        GSM(0),
        WCDMA(1),
        LTE(2),
        C2K(3);

        private int value;

        NetworkType(int value) {
            this.value = value;
        }

        int getValue() {
            return this.value;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code duplicated, block: B:17:0x0033  */
        static NetworkType of(String key) {
            byte b;
            switch (key) {
                case "wcdma_set":
                    b = 1;
                    break;
                case "c2k_set":
                    b = 3;
                    break;
                case "gsm_set":
                    b = 0;
                    break;
                case "lte_set":
                    b = 2;
                    break;
                default:
                    b = -1;
                    break;
            }
            if (b == 0) {
                return GSM;
            }
            if (b == 1) {
                return WCDMA;
            }
            if (b == 2) {
                return LTE;
            }
            if (b == 3) {
                return C2K;
            }
            throw new RuntimeException("invalid key");
        }

        static NetworkType of(int value) {
            for (NetworkType t : values()) {
                if (t.getValue() == value) {
                    return t;
                }
            }
            throw new RuntimeException("invalid value");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class StatusData {
        IAntenna.AntennaState[] listPrefValues;
        ListPreference pref;
        NetworkType type;

        StatusData(NetworkType type, ListPreference pref, IAntenna.AntennaState[] statusValues) {
            this.type = type;
            this.pref = pref;
            this.listPrefValues = statusValues;
        }
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_antenna_set);
        ListPreference lteList = (ListPreference) findPreference(KEY_LTE_SET);
        ListPreference wcdmaList = (ListPreference) findPreference(KEY_WCDMA_SET);
        ListPreference gsmList = (ListPreference) findPreference(KEY_GSM_SET);
        lteList.setOnPreferenceChangeListener(this);
        wcdmaList.setOnPreferenceChangeListener(this);
        gsmList.setOnPreferenceChangeListener(this);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mAnSetHandler = new AnSetHandler(ht.getLooper());
        this.statusData.add(new StatusData(NetworkType.GSM, gsmList, this.gsmListPrefValues));
        this.statusData.add(new StatusData(NetworkType.WCDMA, wcdmaList, this.wcdmaListPrefValues));
        this.statusData.add(new StatusData(NetworkType.LTE, lteList, this.lteListPrefValues));
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        Message mGetAntennaState = this.mAnSetHandler.obtainMessage(0);
        this.mAnSetHandler.sendMessage(mGetAntennaState);
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        AnSetHandler anSetHandler = this.mAnSetHandler;
        if (anSetHandler != null) {
            anSetHandler.getLooper().quit();
        }
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        int value = Integer.parseInt(newValue.toString());
        Log.d(TAG, "onPreferenceChange = " + value);
        if (!(preference instanceof ListPreference)) {
            return false;
        }
        ListPreference listPreference = (ListPreference) preference;
        NetworkType type = NetworkType.of(listPreference.getKey());
        if (type == NetworkType.LTE) {
            createDialog(NetworkType.LTE, value);
            return false;
        }
        Message mSetAntenna = this.mAnSetHandler.obtainMessage(1, type.getValue(), value);
        this.mAnSetHandler.sendMessage(mSetAntenna);
        return false;
    }

    void createDialog(final NetworkType type, final int value) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.antenna_set)).setMessage(getString(R.string.mode_switch_waring)).setCancelable(false).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.hardware.-$$Lambda$AntennaSettingsActivity$Jy1j8KPaXsTSMylDhRsIG6pGOHw
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$createDialog$0$AntennaSettingsActivity(type, value, dialogInterface, i);
            }
        }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.hardware.-$$Lambda$AntennaSettingsActivity$bA-NG_rGBsO7kpJbPz6FottncyY
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                AntennaSettingsActivity.lambda$createDialog$1(dialogInterface, i);
            }
        }).create();
        alertDialog.show();
    }

    public /* synthetic */ void lambda$createDialog$0$AntennaSettingsActivity(NetworkType type, int value, DialogInterface dialog, int which) {
        AnSetHandler anSetHandler = this.mAnSetHandler;
        anSetHandler.sendMessage(anSetHandler.obtainMessage(1, type.getValue(), value));
    }

    static /* synthetic */ void lambda$createDialog$1(DialogInterface dialog, int which) {
    }

    private static int indexOf(IAntenna.AntennaState state, IAntenna.AntennaState[] arr) {
        return Arrays.asList(arr).indexOf(state);
    }

    private void setPref(final ListPreference pref, final int index) {
        if (index == -1) {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.hardware.-$$Lambda$AntennaSettingsActivity$GqHyvuI3NRL_vjYc6dqJJ4Pj55I
                @Override // java.lang.Runnable
                public final void run() {
                    AntennaSettingsActivity.lambda$setPref$2(pref);
                }
            });
        } else {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.hardware.-$$Lambda$AntennaSettingsActivity$i5-83zztqGU56W-rZE0_HQqxvTI
                @Override // java.lang.Runnable
                public final void run() {
                    AntennaSettingsActivity.lambda$setPref$3(pref, index);
                }
            });
        }
    }

    static /* synthetic */ void lambda$setPref$2(ListPreference pref) {
        pref.setEnabled(false);
        pref.setSummary(R.string.feature_not_support);
    }

    static /* synthetic */ void lambda$setPref$3(ListPreference pref, int index) {
        pref.setValueIndex(index);
        pref.setSummary(pref.getEntry());
    }

    private IAntenna.AntennaState[] getStatesArr() {
        IAntenna.AllState currentStatus = new IAntenna.AllState();
        try {
            currentStatus = this.antenna.getAllStates();
        } catch (EmException e) {
            e.printStackTrace();
        }
        IAntenna.AntennaState[] states = new IAntenna.AntennaState[NetworkType.values().length];
        states[NetworkType.GSM.getValue()] = currentStatus.getGsm();
        states[NetworkType.WCDMA.getValue()] = currentStatus.getWcdma();
        states[NetworkType.LTE.getValue()] = currentStatus.getLte();
        return states;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update() {
        IAntenna.AntennaState[] stateArr = getStatesArr();
        for (StatusData data : this.statusData) {
            setPref(data.pref, indexOf(stateArr[data.type.getValue()], data.listPrefValues));
        }
    }

    private StatusData getStatusData(final NetworkType type) {
        StatusData data = this.statusData.stream().filter(new Predicate() { // from class: com.sprd.engineermode.hardware.-$$Lambda$AntennaSettingsActivity$H3eLrhlWTDwYqC-R1UN67nxmM4A
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AntennaSettingsActivity.lambda$getStatusData$4(type, (AntennaSettingsActivity.StatusData) obj);
            }
        }).findAny().orElse(null);
        if (data == null) {
            throw new RuntimeException("type is wrong");
        }
        return data;
    }

    static /* synthetic */ boolean lambda$getStatusData$4(NetworkType type, StatusData d) {
        return d.type == type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void set(NetworkType type, int value) {
        StatusData data = getStatusData(type);
        IAntenna.AntennaState state = data.listPrefValues[value];
        try {
            int i = AnonymousClass1.$SwitchMap$com$sprd$engineermode$hardware$AntennaSettingsActivity$NetworkType[type.ordinal()];
            if (i == 1) {
                this.antenna.setGsm(state);
            } else if (i == 2) {
                this.antenna.setWcdma(state);
            } else if (i == 3) {
                this.antenna.setLte(state);
            } else if (i != 4) {
            }
            update();
            if (type == NetworkType.LTE) {
                powerOff();
            }
        } catch (EmException e) {
            e.printStackTrace();
            showFailToast();
        }
    }

    /* JADX INFO: renamed from: com.sprd.engineermode.hardware.AntennaSettingsActivity$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$sprd$engineermode$hardware$AntennaSettingsActivity$NetworkType = new int[NetworkType.values().length];

        static {
            try {
                $SwitchMap$com$sprd$engineermode$hardware$AntennaSettingsActivity$NetworkType[NetworkType.GSM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sprd$engineermode$hardware$AntennaSettingsActivity$NetworkType[NetworkType.WCDMA.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sprd$engineermode$hardware$AntennaSettingsActivity$NetworkType[NetworkType.LTE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sprd$engineermode$hardware$AntennaSettingsActivity$NetworkType[NetworkType.C2K.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void powerOff() {
        this.mAnSetHandler.postDelayed(new Runnable() { // from class: com.sprd.engineermode.hardware.-$$Lambda$AntennaSettingsActivity$MFTRGY2NE698R8ULAL6qrl9shkU
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$powerOff$5$AntennaSettingsActivity();
            }
        }, 3000L);
    }

    public /* synthetic */ void lambda$powerOff$5$AntennaSettingsActivity() {
        PowerManager pm = (PowerManager) getSystemService("power");
        pm.reboot(TAG);
    }

    class AnSetHandler extends Handler {
        AnSetHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                AntennaSettingsActivity.this.update();
            } else if (i == 1) {
                AntennaSettingsActivity.this.set(NetworkType.of(msg.arg1), msg.arg2);
            }
        }
    }

    private void showFailToast() {
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.hardware.-$$Lambda$AntennaSettingsActivity$tB_K3DA3OM_c_vG94Cyt3W5yr2c
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showFailToast$6$AntennaSettingsActivity();
            }
        });
    }

    public /* synthetic */ void lambda$showFailToast$6$AntennaSettingsActivity() {
        Toast.makeText(this, "Fail", 0).show();
    }
}
