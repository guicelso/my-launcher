package com.sprd.engineermode.hardware;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class CameraFPSActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private static final String KEY_PREVIEW = "preview";
    private static final String KEY_RECORDING = "recording";
    private static final String KEY_RESTORE_DEFAULTS = "restore_defaults";
    private static final String TAG = "CameraFPSActivity";
    public static SharedPreferences.Editor mEditor;
    private AlertDialog dialog;
    private Preference mRestoreDefaults;
    private Preference mpreview;
    private Preference mrecording;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_camera_fps);
        this.mpreview = findPreference(KEY_PREVIEW);
        this.mpreview.setOnPreferenceClickListener(this);
        this.mrecording = findPreference(KEY_RECORDING);
        this.mrecording.setOnPreferenceClickListener(this);
        this.mRestoreDefaults = findPreference(KEY_RESTORE_DEFAULTS);
        this.mRestoreDefaults.setOnPreferenceClickListener(this);
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        this.mpreview.setSummary(SystemPropertiesProxy.get("persist.vendor.cam.preview.fps", "0"));
        this.mrecording.setSummary(SystemPropertiesProxy.get("persist.vendor.cam.record.fps", "0"));
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.dialog.dismiss();
        }
        this.dialog = null;
        super.onDestroy();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object objValue) {
        Log.d(TAG, "pref =" + pref.getKey());
        return true;
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        Log.d(TAG, "key=" + pref.getKey());
        if (pref.getKey().equals(KEY_PREVIEW)) {
            showSetDialog("Preview");
            return true;
        }
        if (pref.getKey().equals(KEY_RECORDING)) {
            showSetDialog("Recording");
            return true;
        }
        if (pref.getKey().equals(KEY_RESTORE_DEFAULTS)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("Warning !").setMessage("All operations will restore default values.").setCancelable(false).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.hardware.CameraFPSActivity.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) throws IllegalAccessException, InvocationTargetException {
                    CameraFPSActivity.this.restoreAllDefault();
                }
            }).setNegativeButton(getString(R.string.alertdialog_cancel), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.hardware.CameraFPSActivity.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create();
            alertDialog.show();
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreAllDefault() throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set("persist.vendor.cam.preview.fps", "0");
        SystemPropertiesProxy.set("persist.vendor.cam.record.fps", "0");
        this.mpreview.setEnabled(true);
        this.mpreview.setSummary("0");
        this.mrecording.setEnabled(true);
        this.mrecording.setSummary("0");
        clearReservedProps();
    }

    private void clearReservedProps() throws IllegalAccessException, InvocationTargetException {
        SharedPreferences sp = getSharedPreferences("prop_vaule", 0);
        int propCount = sp.getInt("prop_count", 0);
        Log.d(TAG, "propCount=" + propCount);
        if (propCount == 0) {
            return;
        }
        mEditor = sp.edit();
        for (int i = 1; i <= propCount; i++) {
            String key = "info_" + Integer.toString(i);
            String keyProp = key + "_prop";
            if (sp.getString(keyProp, null) != null) {
                SystemPropertiesProxy.set(sp.getString(keyProp, null), null);
            }
        }
        mEditor.clear();
        mEditor.apply();
    }

    private void showSetDialog(final String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        this.dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_fps, null);
        this.dialog.setView(view, 0, 0, 0, 0);
        final EditText setMax = (EditText) view.findViewById(R.id.max_vaule);
        final EditText setMin = (EditText) view.findViewById(R.id.min_vaule);
        TextView title = (TextView) view.findViewById(R.id.textView1);
        title.setText(type);
        Button btnOK = (Button) view.findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.hardware.CameraFPSActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) throws IllegalAccessException, InvocationTargetException {
                String maxVaule = setMax.getText().toString();
                String minVaule = setMin.getText().toString();
                if (TextUtils.isEmpty(maxVaule) || TextUtils.isEmpty(minVaule)) {
                    Toast.makeText(CameraFPSActivity.this, "empty input!", 0).show();
                    return;
                }
                if (Integer.parseInt(maxVaule) < Integer.parseInt(minVaule)) {
                    Toast.makeText(CameraFPSActivity.this, "the minvaule is bigger than the maxvaule!", 0).show();
                    return;
                }
                if (Integer.parseInt(maxVaule) > 30 || Integer.parseInt(maxVaule) < 5 || Integer.parseInt(minVaule) > 30 || Integer.parseInt(minVaule) < 5) {
                    Toast.makeText(CameraFPSActivity.this, "please input vaule 5~30", 0).show();
                    return;
                }
                if (maxVaule.length() == 1) {
                    maxVaule = "0" + maxVaule;
                }
                if (minVaule.length() == 1) {
                    minVaule = "0" + minVaule;
                }
                String range = maxVaule + minVaule;
                if (type.equals("Preview")) {
                    SystemPropertiesProxy.set("persist.vendor.cam.preview.fps", range);
                    CameraFPSActivity.this.mpreview.setSummary(range);
                    Log.d(CameraFPSActivity.TAG, "Preview=" + SystemPropertiesProxy.get("persist.vendor.cam.preview.fps"));
                } else {
                    SystemPropertiesProxy.set("persist.vendor.cam.record.fps", range);
                    CameraFPSActivity.this.mrecording.setSummary(range);
                    Log.d(CameraFPSActivity.TAG, "record=" + SystemPropertiesProxy.get("persist.vendor.cam.record.fps"));
                }
                CameraFPSActivity.this.dialog.dismiss();
            }
        });
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.sprd.engineermode.hardware.CameraFPSActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CameraFPSActivity.this.dialog.dismiss();
            }
        });
        this.dialog.show();
    }
}
