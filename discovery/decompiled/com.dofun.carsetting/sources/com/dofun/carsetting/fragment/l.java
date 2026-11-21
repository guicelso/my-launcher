package com.dofun.carsetting.fragment;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.dofun.carsetting.R;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.SwitchPreference;
import com.dofun.carsetting.widget.TwPreference;

/* JADX INFO: compiled from: InternetFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class l extends d implements View.OnClickListener, com.dofun.carsetting.g.j {
    private LinearLayout b;
    private LinearLayout c;
    private SwitchPreference d;
    private SwitchPreference e;
    private InfoPreference f;
    private InfoPreference g;
    private InfoPreference k;
    private InfoPreference l;
    private InfoPreference m;
    private InfoPreference n;
    private SwitchPreference o;
    private SwitchPreference p;
    private InfoPreference q;
    private String r;
    private int s;
    private boolean t;
    private TelephonyManager u;
    private com.dofun.carsetting.f.u v;
    private Handler w = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.fragment.l.2
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i != R.id.sp_mobile) {
                if (i == R.id.sp_wifi) {
                    if (l.this.t != (message.arg1 == 1)) {
                        ((com.dofun.carsetting.presenter.g) l.this.h).d(message.arg1 == 1);
                    }
                }
            } else {
                if (l.this.u.getSimState() != 5) {
                    Toast.makeText(l.this.getActivity(), R.string.sim_not_disabled, 0).show();
                    l.this.e.setValue(false);
                    return true;
                }
                ((com.dofun.carsetting.presenter.g) l.this.h).e(message.arg1 == 1);
            }
            return true;
        }
    });
    private Handler x = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.fragment.l.3
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 65281) {
                return true;
            }
            String str = (String) message.obj;
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            l.this.k.setSummary(str);
            return true;
        }
    });

    @Override // com.dofun.carsetting.fragment.d
    public String a() {
        return "InternetFragment";
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_internet, (ViewGroup) null);
        this.v = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.b = (LinearLayout) view.findViewById(R.id.ll_mobile);
        this.c = (LinearLayout) view.findViewById(R.id.ll_network_more);
        this.d = (SwitchPreference) view.findViewById(R.id.sp_wifi);
        this.e = (SwitchPreference) view.findViewById(R.id.sp_mobile);
        this.q = (InfoPreference) view.findViewById(R.id.ip_vpn);
        this.d.setOnPreferenceChangeListener(this);
        this.e.setOnPreferenceChangeListener(this);
        this.q.setOnPreferenceChangeListener(this);
        this.f = (InfoPreference) view.findViewById(R.id.ip_wifi_connect_state);
        this.g = (InfoPreference) view.findViewById(R.id.ip_wifi_more_setting);
        this.k = (InfoPreference) view.findViewById(R.id.ip_data_use);
        this.l = (InfoPreference) view.findViewById(R.id.ip_mobile_more_setting);
        this.m = (InfoPreference) view.findViewById(R.id.ip_sim_info);
        this.n = (InfoPreference) view.findViewById(R.id.ip_hotspot);
        this.o = (SwitchPreference) view.findViewById(R.id.sp_airplane_mode);
        this.p = (SwitchPreference) view.findViewById(R.id.pref_usb_net_share);
        this.f.setOnPreferenceChangeListener(this);
        this.g.setOnPreferenceChangeListener(this);
        this.k.setOnPreferenceChangeListener(this);
        this.l.setOnPreferenceChangeListener(this);
        this.m.setOnPreferenceChangeListener(this);
        this.n.setOnPreferenceChangeListener(this);
        this.o.setOnPreferenceChangeListener(this);
        this.p.setOnPreferenceChangeListener(this);
        if (com.dofun.carsetting.f.b.T()) {
            this.p.setVisibility(8);
        }
        if (!this.j.f134a) {
            this.b.setVisibility(8);
            this.m.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.ag() || com.dofun.carsetting.f.b.aw() || (com.dofun.carsetting.f.b.f() && !com.dofun.carsetting.f.b.i(getActivity()))) {
            this.b.setVisibility(8);
        }
        if (this.j.O) {
            this.o.setVisibility(8);
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.dofun.carsetting.fragment.l$1] */
    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        this.u = (TelephonyManager) this.i.getSystemService("phone");
        this.e.setValue(((com.dofun.carsetting.presenter.g) this.h).c());
        new Thread() { // from class: com.dofun.carsetting.fragment.l.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                String strA = com.tw.a.a.a.a.a().a(l.this.getActivity());
                Log.d("InternetFragment", "initData:dataUsage: " + strA);
                Message messageObtainMessage = l.this.x.obtainMessage();
                messageObtainMessage.what = 65281;
                messageObtainMessage.obj = strA;
                l.this.x.sendMessage(messageObtainMessage);
            }
        }.start();
        this.o.setValue(((com.dofun.carsetting.presenter.g) this.h).d());
        this.p.setValue(SystemProperties.getInt("persist.tw.ipheth", 0) == 1);
        this.t = ((com.dofun.carsetting.presenter.g) this.h).b() == 3;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        this.d.setValue(this.t);
        this.e.setValue(((com.dofun.carsetting.presenter.g) this.h).c());
        this.f.setValueTip(this.r);
        Log.d("InternetFragment", "onResume: wifiState：" + this.t + " mobileData:" + ((com.dofun.carsetting.presenter.g) this.h).c());
        this.f.a(this.t);
        this.f.getValueIconView().getDrawable().setLevel(this.s);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
        Log.d("InternetFragment", "onPause: ");
    }

    @Override // com.dofun.carsetting.fragment.d, com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.v != null) {
            this.v.close();
            this.v.removeHandler("InternetFragment");
        }
        Log.d("InternetFragment", "onDestroy: ");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        switch (twPreference.getId()) {
            case R.id.ip_data_use /* 2131230915 */:
                if (this.u.getSimState() != 5) {
                    Toast.makeText(getActivity(), R.string.sim_not_disabled, 0).show();
                    return false;
                }
                Intent intent = new Intent("android.settings.DATA_ROAMING_SETTINGS");
                intent.setFlags(268435456);
                this.i.startActivity(intent);
                return false;
            case R.id.ip_hotspot /* 2131230924 */:
                Intent intent2 = new Intent("android.intent.action.MAIN");
                intent2.putExtra(":settings:show_fragment", "com.android.settings.TetherSettings");
                intent2.setComponent(new ComponentName("com.android.settings", "com.android.settings.SubSettings"));
                startActivity(intent2);
                return false;
            case R.id.ip_mobile_more_setting /* 2131230930 */:
                int simState = this.u.getSimState();
                if (simState != 5) {
                    Toast.makeText(getActivity(), R.string.sim_not_disabled, 0).show();
                    return false;
                }
                Intent intent3 = new Intent("android.settings.DATA_ROAMING_SETTINGS");
                intent3.setFlags(268435456);
                this.i.startActivity(intent3);
                Log.d("InternetFragment", "onPreferenceChange: simState:" + simState);
                return false;
            case R.id.ip_sim_info /* 2131230937 */:
                ((MainActivity) getActivity()).a(new x());
                return false;
            case R.id.ip_vpn /* 2131230942 */:
                Intent intent4 = new Intent("android.settings.VPN_SETTINGS");
                intent4.setFlags(268435456);
                this.i.startActivity(intent4);
                return false;
            case R.id.ip_wifi_connect_state /* 2131230943 */:
                Intent intent5 = new Intent("android.settings.WIFI_SETTINGS");
                intent5.setFlags(268435456);
                this.i.startActivity(intent5);
                return false;
            case R.id.ip_wifi_more_setting /* 2131230944 */:
                Intent intent6 = new Intent("android.settings.WIFI_SETTINGS");
                intent6.setFlags(268435456);
                this.i.startActivity(intent6);
                return false;
            case R.id.pref_usb_net_share /* 2131231098 */:
                Boolean bool = (Boolean) obj;
                SystemProperties.set("persist.tw.ipheth", bool.booleanValue() ? "1" : "0");
                if (bool.booleanValue()) {
                    SystemProperties.set("persist.usb.otg", "0");
                }
                if (!com.dofun.carsetting.f.b.f()) {
                    d(!bool.booleanValue());
                }
                f();
                return false;
            case R.id.sp_airplane_mode /* 2131231205 */:
                ((com.dofun.carsetting.presenter.g) this.h).f(((Boolean) obj).booleanValue());
                return false;
            case R.id.sp_mobile /* 2131231208 */:
                Message messageObtainMessage = this.w.obtainMessage();
                messageObtainMessage.what = R.id.sp_mobile;
                messageObtainMessage.arg1 = ((Boolean) obj).booleanValue() ? 1 : 0;
                this.w.removeMessages(R.id.sp_wifi);
                this.w.sendMessageDelayed(messageObtainMessage, 1000L);
                return false;
            case R.id.sp_wifi /* 2131231216 */:
                Message messageObtainMessage2 = this.w.obtainMessage();
                messageObtainMessage2.what = R.id.sp_wifi;
                messageObtainMessage2.arg1 = ((Boolean) obj).booleanValue() ? 1 : 0;
                this.w.removeMessages(R.id.sp_wifi);
                this.w.sendMessageDelayed(messageObtainMessage2, 1000L);
                return false;
            default:
                return false;
        }
    }

    @Override // com.dofun.carsetting.fragment.d, com.dofun.carsetting.g.k
    public void a(boolean z) {
        boolean z2 = ((com.dofun.carsetting.presenter.g) this.h).b() == 3;
        if (z2 != z) {
            Log.d("InternetFragment", "onWiFiState: state:" + z2 + "  on:" + z);
            this.t = z2;
        } else {
            this.t = z;
        }
        this.d.setValue(z);
        if (z) {
            return;
        }
        this.f.setValueTip("");
        this.f.a(false);
        this.f.setTitle(getString(R.string.wifi_disconnect));
    }

    @Override // com.dofun.carsetting.fragment.d, com.dofun.carsetting.g.k
    public void a(WifiInfo wifiInfo) {
        if (wifiInfo == null) {
            return;
        }
        this.r = wifiInfo.getSSID().replace("\"", "");
        Log.d("InternetFragment", "onWiFiInfo: " + this.r);
        this.f.setValueTip(this.r);
        this.f.setTitle(getString(R.string.wifi_connected));
        this.f.a(true);
    }

    @Override // com.dofun.carsetting.fragment.d, com.dofun.carsetting.g.k
    public void a(String str, int i) {
        this.s = i;
        this.f.getValueIconView().getDrawable().setLevel(i);
    }

    @Override // com.dofun.carsetting.fragment.d, com.dofun.carsetting.g.k
    public void b(boolean z) {
        this.e.setValue(z);
    }

    @Override // com.dofun.carsetting.fragment.d, com.dofun.carsetting.g.k
    public void c(boolean z) {
        this.o.setValue(((com.dofun.carsetting.presenter.g) this.h).d());
    }

    private void d(boolean z) {
        SystemProperties.set("persist.sys.speedplay.cp.wired.disa", z ? "0" : "1");
        SystemProperties.set("persist.sys.zlink.cp.wired.disa", z ? "0" : "1");
        Intent intent = new Intent("com.zjinnova.zlink");
        intent.setPackage("com.zjinnova.zlink");
        int i = SystemProperties.getInt("persist.sys.tw.link", 0);
        Log.d("InternetFragment", "setCarplayUsbEnable: carplayType:" + i);
        if (i == 0) {
            intent.setComponent(new ComponentName("com.zjinnova.zlink", "com.texustek.speedplay.broadcast.VendorBroadcastReceiver"));
        }
        Log.d("InternetFragment", "setCarplayUsbEnable: targetCn:" + intent.getComponent());
        if (z) {
            intent.setAction("com.zjinnova.zlink.action.USB_IDLE");
        } else {
            intent.setAction("com.zjinnova.zlink.action.USB_OCCUPIED");
        }
        this.i.sendBroadcast(intent);
    }

    private void f() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.fragment.l.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (l.this.v != null) {
                    l.this.v.write(40730, 3, 0, "sync");
                    l.this.v.write(514, 1, 0);
                }
            }
        });
        builder.setNegativeButton(R.string.dlg_cancel, (DialogInterface.OnClickListener) null);
        builder.setTitle(R.string.usb_net_share);
        builder.setMessage(R.string.reboot_now);
        builder.setCancelable(true);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.getWindow().setType(2003);
        try {
            WindowManagerGlobal.getWindowManagerService();
        } catch (Exception unused) {
        }
        alertDialogCreate.show();
    }
}
