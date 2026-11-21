package com.sprd.engineermode.telephony;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.text.InputFilter;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.widget.EditText;
import com.sprd.engineermode.R;

/* JADX INFO: loaded from: classes4.dex */
public class FDActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private SharedPreferences.Editor editor;
    private Context mContext;
    private SharedPreferences preferences;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_fd);
        this.mContext = this;
        this.preferences = this.mContext.getSharedPreferences("fd", 0);
        this.editor = this.preferences.edit();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return true;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            this.editor.putString("key_back", "true");
            this.editor.commit();
            finish();
            return true;
        }
        return true;
    }

    @Override // android.preference.PreferenceActivity
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        if (key.equals("default_value")) {
            this.editor.putString("default", "true");
            this.editor.commit();
            finish();
            return true;
        }
        if (key.equals("self_defining_value")) {
            this.editor.putString("default", "false");
            final EditText inputServer = new EditText(this.mContext);
            inputServer.setKeyListener(new NumberKeyListener() { // from class: com.sprd.engineermode.telephony.FDActivity.1
                private char[] numberChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

                @Override // android.text.method.KeyListener
                public int getInputType() {
                    return 3;
                }

                @Override // android.text.method.NumberKeyListener
                protected char[] getAcceptedChars() {
                    return this.numberChars;
                }
            });
            String value = this.preferences.getString("summary", "");
            inputServer.setHint(value);
            InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(5);
            InputFilter[] inputFilter = {lengthFilter};
            inputServer.setFilters(inputFilter);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Input time(s) for dormancy");
            builder.setView(inputServer);
            builder.setCancelable(false);
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.FDActivity.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.FDActivity.3
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    FDActivity.this.editor.putString("definite_value", inputServer.getText().toString());
                    FDActivity.this.editor.commit();
                    FDActivity.this.finish();
                }
            });
            builder.show();
            return true;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
