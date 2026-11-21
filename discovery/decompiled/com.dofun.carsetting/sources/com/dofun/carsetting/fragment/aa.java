package com.dofun.carsetting.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.view.MotionEventCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.dofun.carsetting.R;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.widget.EditTextPreference;
import com.dofun.carsetting.widget.ImportExportExtensionSettings;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.TwPreference;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/* JADX INFO: compiled from: SystemFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class aa extends ad<com.dofun.carsetting.presenter.m> implements com.dofun.carsetting.g.g, com.dofun.carsetting.g.j, com.dofun.carsetting.g.s {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    com.dofun.carsetting.c.e f242a;
    com.dofun.carsetting.c.f b;
    com.dofun.carsetting.c.j c;
    private InfoPreference f;
    private InfoPreference g;
    private InfoPreference k;
    private InfoPreference l;
    private InfoPreference m;
    private EditTextPreference n;
    private EditTextPreference o;
    private com.dofun.carsetting.f.u p;
    private ProgressDialog r;
    private String t;
    private ImportExportExtensionSettings u;
    private com.dofun.carsetting.b.a q = com.dofun.carsetting.b.a.a();
    private int s = -1;
    public boolean d = false;
    public boolean e = false;

    @SuppressLint({"HandlerLeak"})
    private Handler v = new Handler() { // from class: com.dofun.carsetting.fragment.aa.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                if (message.what != 65280) {
                    return;
                }
                if (aa.this.r != null) {
                    aa.this.r.dismiss();
                }
                Toast.makeText(aa.this.getActivity(), message.arg1 == 0 ? aa.this.getString(R.string.complete_backup) : aa.this.getString(R.string.complete_removal_of), 1).show();
            } catch (Exception unused) {
            }
        }
    };

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_system, (ViewGroup) null);
        this.p = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.m d() {
        return new com.dofun.carsetting.presenter.m(getActivity());
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.f = (InfoPreference) view.findViewById(R.id.time_and_date);
        this.f.setOnPreferenceChangeListener(this);
        this.g = (InfoPreference) view.findViewById(R.id.backup_apk);
        this.g.setOnPreferenceChangeListener(this);
        this.g.setSaveValue(false);
        this.k = (InfoPreference) view.findViewById(R.id.about_the_machine);
        this.k.setOnPreferenceChangeListener(this);
        this.l = (InfoPreference) view.findViewById(R.id.mcu_update);
        this.l.setOnPreferenceChangeListener(this);
        this.m = (InfoPreference) view.findViewById(R.id.system_update);
        this.m.setOnPreferenceChangeListener(this);
        this.m.setSaveValue(false);
        this.n = (EditTextPreference) view.findViewById(R.id.restore_factory_settings);
        this.n.a((com.dofun.carsetting.g.g) this);
        this.o = (EditTextPreference) view.findViewById(R.id.developer_options);
        this.o.a((com.dofun.carsetting.g.g) this);
        this.u = new ImportExportExtensionSettings();
        this.u.a();
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        if (!this.q.I) {
            String[] stringArray = getActivity().getResources().getStringArray(R.array.system_update_string_entries_no_online);
            String[] stringArray2 = getActivity().getResources().getStringArray(R.array.close_open_entries_value);
            Log.d("SystemFragment", "initSystemUpdate:" + Arrays.toString(stringArray) + " values:" + Arrays.toString(stringArray2));
            this.m.a(stringArray, stringArray2);
        }
        if (Settings.Global.getInt(this.i.getContentResolver(), "gat_SystemUpdatePolicies", -1) == 0 || com.dofun.carsetting.f.b.aw()) {
            this.m.setVisibility(8);
        }
        if (this.q.N) {
            this.n.a(getString(R.string.enter_your_pin), 129, true);
            this.o.a(getString(R.string.enter_your_pin), 129, true);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.p != null) {
            this.p.addHandler("SystemFragment", this.v);
        }
        Log.d("SystemFragment", "onResume:isAutomatic:" + this.u.i() + " cmd:" + this.s);
        if (this.u.i()) {
            return;
        }
        if (this.s != -1) {
            this.u.a(getActivity(), this.s, this.t, this.d, this.e);
        }
        switch (this.s) {
            case 6:
                if (!TextUtils.isEmpty(this.t) && new File(this.t).canRead()) {
                    this.u.d(this.t);
                }
                break;
        }
        this.s = -1;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        switch (twPreference.getId()) {
            case R.id.about_the_machine /* 2131230729 */:
                try {
                    Intent intent = new Intent("android.settings.DEVICE_INFO_SETTINGS");
                    intent.setFlags(268435456);
                    this.i.startActivity(intent);
                    break;
                } catch (Exception unused) {
                    Toast.makeText(this.i, getResources().getString(R.string.apk_noinstall), 1).show();
                }
                return false;
            case R.id.backup_apk /* 2131230767 */:
                int i = Integer.parseInt((String) obj);
                if (i == 0) {
                    this.r = a(getActivity(), getResources().getString(R.string.backup_apk));
                    this.r.show();
                }
                a(i);
                this.g.c();
                this.g.setSaveValue(false);
                return false;
            case R.id.mcu_update /* 2131231059 */:
                if (com.dofun.carsetting.f.b.l()) {
                    this.b = new com.dofun.carsetting.c.f(getActivity());
                    this.b.show();
                } else {
                    this.f242a = new com.dofun.carsetting.c.e(getActivity());
                    this.f242a.show();
                }
                return false;
            case R.id.system_update /* 2131231246 */:
                if (obj.equals("0")) {
                    this.c = new com.dofun.carsetting.c.j(getActivity());
                    this.c.show();
                } else {
                    try {
                        Intent intent2 = new Intent("android.intent.action.MAIN");
                        intent2.setClassName("com.abupdate.fota_demo_iot", "com.abupdate.fota_demo_iot.view.activity.MainActivity");
                        startActivity(intent2);
                    } catch (Exception unused2) {
                        Toast.makeText(this.i, getResources().getString(R.string.apk_noinstall), 1).show();
                    }
                    break;
                }
                this.m.c();
                this.m.setAllSaveValue(false);
                return false;
            case R.id.time_and_date /* 2131231261 */:
                try {
                    Intent intent3 = new Intent("android.settings.DATE_SETTINGS");
                    intent3.setFlags(268435456);
                    this.i.startActivity(intent3);
                    break;
                } catch (Exception unused3) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.apk_noinstall), 1).show();
                }
                return false;
            default:
                return false;
        }
    }

    @Override // com.dofun.carsetting.g.g
    public void a(EditTextPreference editTextPreference, Editable editable) {
        int id = editTextPreference.getId();
        if (id != R.id.developer_options) {
            if (id != R.id.restore_factory_settings) {
                return;
            }
            a(editTextPreference, editable.toString());
        } else {
            if (b(editTextPreference, editable.toString())) {
                Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                intent.setFlags(268435456);
                this.i.startActivity(intent);
                return;
            }
            Toast.makeText(this.i, getResources().getString(R.string.password_mistake), 1).show();
        }
    }

    private void a(final int i) {
        new Thread(new Runnable() { // from class: com.dofun.carsetting.fragment.aa.2
            @Override // java.lang.Runnable
            public void run() {
                Message messageObtainMessage = aa.this.v.obtainMessage();
                messageObtainMessage.what = MotionEventCompat.ACTION_POINTER_INDEX_MASK;
                messageObtainMessage.arg1 = i;
                aa.this.p.write(40730, 2, 0, "rm -r /sdcard/.apk");
                if (i == 0) {
                    aa.this.p.write(40730, 2, 0, "mkdir /sdcard/.apk");
                    File[] fileArrListFiles = new File("/data/app").listFiles(new FileFilter() { // from class: com.dofun.carsetting.fragment.aa.2.1
                        @Override // java.io.FileFilter
                        public boolean accept(File file) {
                            return (file.isHidden() || !file.isDirectory() || file.getName().startsWith("vmd")) ? false : true;
                        }
                    });
                    if (fileArrListFiles != null) {
                        for (File file : fileArrListFiles) {
                            aa.this.p.write(40730, 2, 0, "cp " + file.getAbsolutePath() + "/base.apk /sdcard/.apk/" + file.getName() + ".apk");
                        }
                    }
                }
                aa.this.v.sendMessage(messageObtainMessage);
            }
        }).start();
        Toast.makeText(getActivity(), getString(i == 0 ? R.string.complete_backup : R.string.complete_removal_of), 1).show();
    }

    private void a(EditTextPreference editTextPreference, String str) {
        String string = Settings.System.getString(MyApp.a().getContentResolver(), "RESET_FACTORY");
        if (TextUtils.isEmpty(string)) {
            string = "7890";
        }
        if (editTextPreference.getNeedPassword()) {
            string = editTextPreference.getPassword();
        }
        if (str != null && str.startsWith("7890") && str.length() > 4) {
            String strSubstring = str.substring(4);
            int iWrite = this.p.write(65521);
            Calendar calendar = Calendar.getInstance();
            int i = calendar.get(11);
            int i2 = calendar.get(12);
            if (!String.format(Locale.US, "%02X", Integer.valueOf(iWrite)).equals(strSubstring)) {
                if (String.format(Locale.US, "%02X%02d%02d", Integer.valueOf(iWrite), Integer.valueOf(i), Integer.valueOf(i2)).equals(strSubstring)) {
                    final CharSequence[] charSequenceArr = {"host", "device"};
                    new AlertDialog.Builder(getActivity()).setSingleChoiceItems(charSequenceArr, -1, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.fragment.aa.5
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i3) {
                            dialogInterface.dismiss();
                            if (aa.this.q.o != 1) {
                                if (aa.this.q.o == 0) {
                                    SystemProperties.set("persist.wifi.debug", i3 == 1 ? "1" : "0");
                                    return;
                                }
                                if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F()) {
                                    aa.this.p.write(40730, 1, 0, "rwrite /sys/bus/platform/devices/usbc0/otg_role usb_" + ((Object) charSequenceArr[i3]));
                                    return;
                                }
                                if (com.dofun.carsetting.f.b.G()) {
                                    aa.this.p.write(40730, 1, 0, "rwrite /sys/bus/platform/devices/10.usbc0/otg_role usb_" + ((Object) charSequenceArr[i3]));
                                    return;
                                }
                                aa.this.p.write(40730, 1, 0, "rwrite /sys/bus/platform/devices/usbc0.2/otg_role usb_device" + ((Object) charSequenceArr[i3]));
                                return;
                            }
                            SystemProperties.set("persist.usb.otg", i3 == 1 ? "1" : "0");
                        }
                    }).setNegativeButton(R.string.dlg_cancel, (DialogInterface.OnClickListener) null).show();
                    return;
                }
                return;
            }
            if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.sure_system_update).setPositiveButton(R.string.key_enter, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.fragment.aa.4
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i3) {
                        aa.this.p.write(65282, 0, 0, "efex");
                    }
                }).setNeutralButton(R.string.system_update_title, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.fragment.aa.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i3) {
                        aa.this.p.write(65282, 0, 0, null);
                    }
                }).setNegativeButton(R.string.dlg_cancel, (DialogInterface.OnClickListener) null).show();
                return;
            }
            return;
        }
        if (com.dofun.carsetting.f.b.a(str, 3, string)) {
            a(getActivity(), getString(R.string.reset_factory_title));
            this.p.write(40730, 1, 0, "rmdata");
        } else if (com.dofun.carsetting.f.b.a(str, 3, "11223344")) {
            this.p.write(65282, 0, 0, String.format("run_command\nsunxi_flash boot0 force_dram_update_flag 0", new Object[0]));
        }
    }

    public static ProgressDialog a(Context context, String str) {
        ProgressDialog progressDialog = new ProgressDialog(context, 3);
        progressDialog.setMessage(str);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setOnCancelListener(null);
        return progressDialog;
    }

    private boolean b(EditTextPreference editTextPreference, String str) {
        if (editTextPreference.getNeedPassword()) {
            return !TextUtils.isEmpty(str) && str.equals(editTextPreference.getPassword());
        }
        if (com.dofun.carsetting.f.b.L() || com.dofun.carsetting.f.b.O() || com.dofun.carsetting.f.b.F()) {
            if (str != null && str.startsWith("7890") && str.length() > 4) {
                if (String.format(Locale.US, "%02d", Integer.valueOf(Calendar.getInstance().get(11))).equals(str.substring(4))) {
                    return true;
                }
            }
        } else if (TextUtils.equals(str, "7890")) {
            return true;
        }
        return false;
    }

    public void a(int i, String str, boolean z, boolean z2) {
        Log.d("SystemFragment", "setAutoImportAndUpdateAction:cmd:" + i + " this.cmd:" + this.s + " pathString:" + this.t + " deleteUpdateFile:" + z + " wipeData:" + z2);
        if (i == -1 || this.s == i) {
            return;
        }
        this.s = i;
        this.t = str;
        this.d = z;
        this.e = z2;
    }
}
