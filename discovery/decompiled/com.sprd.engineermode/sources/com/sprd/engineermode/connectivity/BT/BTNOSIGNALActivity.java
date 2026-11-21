package com.sprd.engineermode.connectivity.BT;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.sprd.engineermode.R;

/* JADX INFO: loaded from: classes4.dex */
public class BTNOSIGNALActivity extends PreferenceActivity {
    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_bt_nosignal_test);
    }
}
