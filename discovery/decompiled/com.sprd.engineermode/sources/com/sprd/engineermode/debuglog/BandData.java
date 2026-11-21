package com.sprd.engineermode.debuglog;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.util.Log;
import java.util.Arrays;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BandData {
    private static final String TAG = "BandData";
    protected Context mContext;
    protected int mPhoneID;
    protected boolean mSetSuccessful = false;
    private boolean mIsChanged = false;
    protected String[] mBandTitle = null;
    protected String[] mBandFrequency = null;
    protected int[] mSelecteds = null;
    protected CheckBoxPreference[] mPrefCheckBox = null;

    protected abstract boolean getSelectedBand();

    public abstract boolean setSelectedBandToModem();

    public BandData(Context context, int phoneID) {
        this.mContext = context;
        this.mPhoneID = phoneID;
    }

    public BandData(Context context, int phoneID, PreferenceCategory pref) {
        this.mContext = context;
        this.mPhoneID = phoneID;
    }

    public boolean init() {
        this.mIsChanged = false;
        if (!getSelectedBand()) {
            return false;
        }
        updateCheckBox();
        return true;
    }

    public void updateCheckBox() {
        int i = 0;
        while (true) {
            CheckBoxPreference[] checkBoxPreferenceArr = this.mPrefCheckBox;
            if (i < checkBoxPreferenceArr.length) {
                if (checkBoxPreferenceArr[i] != null) {
                    checkBoxPreferenceArr[i].setChecked(this.mSelecteds[i] == 1);
                }
                i++;
            } else {
                return;
            }
        }
    }

    public boolean successful() {
        return this.mSetSuccessful;
    }

    protected CheckBoxPreference[] addAvailableBandsToPerference() {
        Log.d(TAG, "getPerference() mSelecteds:" + Arrays.toString(this.mSelecteds));
        for (int i = 0; i < this.mBandTitle.length; i++) {
            CheckBoxPreference[] checkBoxPreferenceArr = this.mPrefCheckBox;
            if (checkBoxPreferenceArr[i] == null) {
                checkBoxPreferenceArr[i] = new CheckBoxPreference(this.mContext);
                this.mPrefCheckBox[i].setTitle(this.mBandTitle[i]);
                this.mPrefCheckBox[i].setKey(this.mBandFrequency[i]);
                this.mPrefCheckBox[i].setOnPreferenceClickListener(new PerferecnerOnClick());
            }
            CheckBoxPreference checkBoxPreference = this.mPrefCheckBox[i];
            boolean z = true;
            if (this.mSelecteds[i] != 1) {
                z = false;
            }
            checkBoxPreference.setChecked(z);
        }
        return this.mPrefCheckBox;
    }

    private class PerferecnerOnClick implements Preference.OnPreferenceClickListener {
        private PerferecnerOnClick() {
        }

        @Override // android.preference.Preference.OnPreferenceClickListener
        public boolean onPreferenceClick(Preference preference) {
            String key = preference.getKey();
            for (int i = 0; i < BandData.this.mBandFrequency.length; i++) {
                if (key.equals(BandData.this.mBandFrequency[i])) {
                    BandData.this.mSelecteds[i] = ((CheckBoxPreference) preference).isChecked() ? 1 : 0;
                    BandData.this.mIsChanged = true;
                    StringBuilder sb = new StringBuilder();
                    sb.append(BandData.this.mBandTitle[i]);
                    sb.append("->");
                    sb.append(BandData.this.mSelecteds[i] == 1 ? "chekced" : "unchecked");
                    Log.d(BandData.TAG, sb.toString());
                    break;
                }
            }
            return true;
        }
    }

    public boolean isChanged() {
        return this.mIsChanged;
    }

    public boolean isCheckOneOrMore() {
        if (this.mSelecteds.length == 0) {
            return true;
        }
        int i = 0;
        while (true) {
            int[] iArr = this.mSelecteds;
            if (i < iArr.length) {
                if (iArr[i] == 1) {
                    return true;
                }
                i++;
            } else {
                return false;
            }
        }
    }
}
