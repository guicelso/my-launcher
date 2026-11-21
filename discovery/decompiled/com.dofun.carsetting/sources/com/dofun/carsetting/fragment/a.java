package com.dofun.carsetting.fragment;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.app.PlatLogoActivity;
import com.dofun.carsetting.R;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.AlignTextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/* JADX INFO: compiled from: AboutFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class a extends ad<com.dofun.carsetting.presenter.c> implements View.OnClickListener, com.dofun.carsetting.g.e {
    private static InterfaceC0017a av;
    private TextView A;
    private TextView B;
    private TextView C;
    private TextView D;
    private LinearLayout E;
    private LinearLayout F;
    private TextView G;
    private TextView H;
    private LinearLayout I;
    private TextView J;
    private TextView K;
    private LinearLayout L;
    private TextView M;
    private TextView N;
    private TextView O;
    private TextView P;
    private TextView Q;
    private TextView R;
    private ImageView S;
    private TextView T;
    private TextView U;
    private TextView V;
    private TextView W;
    private TextView X;
    private TextView Y;
    private TextView Z;
    private TextView aa;
    private TextView ab;
    private AlignTextView ac;
    private TextView ad;
    private TextView ae;
    private LinearLayout af;
    private TextView ag;
    private com.dofun.carsetting.f.u ah;
    private String ai;
    private TextView aj;
    private View am;
    private AlertDialog ar;
    private AlertDialog as;
    String c;
    String d;
    String e;
    String f;
    private TextView k;
    private TextView l;
    private TextView m;
    private TextView n;
    private TextView o;
    private TextView p;
    private LinearLayout r;
    private LinearLayout s;
    private TextView t;
    private TextView u;
    private TextView v;
    private TextView w;
    private TextView x;
    private LinearLayout y;
    private TextView z;
    private String q = "";

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String f235a = "";
    String b = "";
    private String ak = "";
    private String al = "";
    private String[] an = {"MODE_NTSC", "MODE_PAL", "MODE_1080P_30FPS", "MODE_1080P_25FPS", "MODE_720P_30FPS", "MODE_720P_25FPS"};
    private long[] ao = new long[3];
    private long[] ap = new long[3];
    int g = 0;
    private long aq = 0;
    private String at = "";
    private String au = Environment.getExternalStorageDirectory().getAbsolutePath();

    /* JADX INFO: renamed from: com.dofun.carsetting.fragment.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: AboutFragment.java */
    public interface InterfaceC0017a {
        void b();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.i = context;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new com.dofun.carsetting.f.u();
        this.ah = com.dofun.carsetting.f.u.a();
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_about, (ViewGroup) null);
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.c d() {
        return new com.dofun.carsetting.presenter.c(this.i);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) throws Throwable {
        this.k = (TextView) view.findViewById(R.id.tv_platform_info);
        this.l = (TextView) view.findViewById(R.id.tv_iccid);
        this.m = (TextView) view.findViewById(R.id.tv_chipid);
        this.n = (TextView) view.findViewById(R.id.tv_device_num);
        this.o = (TextView) view.findViewById(R.id.tv_imei_num);
        this.p = (TextView) view.findViewById(R.id.tv_imei_num1);
        if (com.dofun.carsetting.f.b.ag()) {
            this.o.setVisibility(8);
            this.p.setVisibility(8);
        }
        this.r = (LinearLayout) view.findViewById(R.id.ll_general);
        this.r.setOnClickListener(this);
        this.s = (LinearLayout) view.findViewById(R.id.ll_moblie_network);
        this.y = (LinearLayout) view.findViewById(R.id.mobile_network_sim1);
        this.E = (LinearLayout) view.findViewById(R.id.mobile_network_sim2);
        this.t = (TextView) view.findViewById(R.id.tv_operator_sim1);
        this.u = (TextView) view.findViewById(R.id.tv_signal_strength_sim1);
        this.v = (TextView) view.findViewById(R.id.tv_mobile_type_sim1);
        this.w = (TextView) view.findViewById(R.id.tv_service_stat_sim1);
        this.x = (TextView) view.findViewById(R.id.tv_mobile_stat_sim1);
        this.z = (TextView) view.findViewById(R.id.tv_operator_sim2);
        this.A = (TextView) view.findViewById(R.id.tv_signal_strength_sim2);
        this.B = (TextView) view.findViewById(R.id.tv_mobile_type_sim2);
        this.C = (TextView) view.findViewById(R.id.tv_service_stat_sim2);
        this.D = (TextView) view.findViewById(R.id.tv_mobile_stat_sim2);
        this.F = (LinearLayout) view.findViewById(R.id.ll_rom);
        this.G = (TextView) view.findViewById(R.id.tv_rom_total);
        this.H = (TextView) view.findViewById(R.id.tv_rom_useful);
        this.I = (LinearLayout) view.findViewById(R.id.ll_ram);
        this.J = (TextView) view.findViewById(R.id.tv_ram_total);
        this.K = (TextView) view.findViewById(R.id.tv_ram_useful);
        this.H.setVisibility(8);
        this.K.setVisibility(8);
        this.M = (TextView) view.findViewById(R.id.tv_can_info);
        this.M.setOnClickListener(this);
        this.N = (TextView) view.findViewById(R.id.tv_mcu_info);
        this.N.setOnClickListener(this);
        this.O = (TextView) view.findViewById(R.id.tv_system_edi);
        this.O.setOnClickListener(this);
        this.P = (TextView) view.findViewById(R.id.tv_display_info);
        this.P.setOnClickListener(this);
        this.Q = (TextView) view.findViewById(R.id.tv_app_info);
        this.Q.setText(getResources().getString(R.string.about_app_info) + a(getActivity()));
        this.Q.setOnClickListener(this);
        this.V = (TextView) view.findViewById(R.id.tv_ts10s_versioncode_info);
        String string = Settings.System.getString(MyApp.a().getContentResolver(), "APP_VERSION_LOCK");
        if (!TextUtils.isEmpty(string)) {
            int iIndexOf = com.dofun.carsetting.f.b.a().indexOf(95);
            if (iIndexOf != -1) {
                string = string + com.dofun.carsetting.f.b.a().substring(iIndexOf);
            }
        } else {
            string = com.dofun.carsetting.f.b.a();
        }
        this.V.setText(getResources().getString(R.string.about_versioncode_info) + string);
        this.R = (TextView) view.findViewById(R.id.tv_android_version_info);
        if (com.dofun.carsetting.f.b.a().contains("ZH55") || com.dofun.carsetting.f.b.a().contains("-FY")) {
            this.R.setVisibility(8);
        }
        this.R.setOnClickListener(this);
        this.S = (ImageView) view.findViewById(R.id.btn_about);
        this.S.setOnClickListener(this);
        this.T = (TextView) view.findViewById(R.id.btn_checkupdate);
        this.T.setOnClickListener(this);
        this.U = (TextView) view.findViewById(R.id.tv_boot_info);
        this.W = (TextView) view.findViewById(R.id.tv_cpu_info);
        this.X = (TextView) view.findViewById(R.id.tv_npu_info);
        this.Y = (TextView) view.findViewById(R.id.tv_gpu_info);
        this.Z = (TextView) view.findViewById(R.id.tv_sound_info);
        this.aa = (TextView) view.findViewById(R.id.tv_radio_info);
        this.ab = (TextView) view.findViewById(R.id.tv_bt_info);
        this.ac = (AlignTextView) view.findViewById(R.id.tv_baseband_info);
        this.aj = (TextView) view.findViewById(R.id.tv_custom_info);
        this.ad = (TextView) view.findViewById(R.id.camera_system_info);
        this.ae = (TextView) view.findViewById(R.id.ic360_info);
        this.ag = (TextView) view.findViewById(R.id.car_info);
        this.am = view.findViewById(R.id.hardware_info_layout);
        ((TextView) view.findViewById(R.id.btn_export_log)).setOnClickListener(this);
        this.af = (LinearLayout) view.findViewById(R.id.ll_cpbox_carinfo);
        if (this.ah != null) {
            this.ai = Integer.toHexString(this.ah.write(65521));
        }
        if (this.j.k) {
            this.Y.setVisibility(0);
            this.Y.setText(getString(R.string.about_gpu_info) + com.dofun.carsetting.f.b.ax());
        }
        if (this.j.l) {
            this.X.setVisibility(0);
            this.X.setText(getString(R.string.about_npu_info) + com.dofun.carsetting.f.b.ay());
        }
        if (i() != null) {
            this.S.setImageBitmap(i());
        } else {
            this.S.setImageDrawable(this.i.getResources().getDrawable(R.drawable.rb_about2_bg));
        }
        if (!c().isEmpty()) {
            this.aj.setText(c());
        }
        f();
    }

    private void f() {
        if (com.dofun.carsetting.f.b.T()) {
            this.M.setVisibility(8);
            this.aa.setVisibility(8);
            this.af.setVisibility(0);
            String str = SystemProperties.get("suding.carplay.reverse.manufacturer", "");
            String str2 = SystemProperties.get("suding.carplay.reverse.model", "");
            String str3 = SystemProperties.get("suding.carplay.reverse.oemIconLabel", "");
            String str4 = SystemProperties.get("suding.carplay.reverse.srcVersion", "");
            String str5 = SystemProperties.get("zj.zsender.version", "");
            StringBuilder sb = new StringBuilder();
            if (!str.isEmpty()) {
                sb.append("Manufacturer： " + str);
            }
            if (!str2.isEmpty()) {
                sb.append("\nModel： " + str2);
            }
            if (!str3.isEmpty()) {
                sb.append("\nOemIconLabel： " + str3);
            }
            if (!str4.isEmpty()) {
                sb.append("\nSrcVersion： " + str4);
            }
            if (!str5.isEmpty()) {
                sb.append("\nProtocolVersion： " + str5);
            }
            this.ag.setText(sb);
        } else if (com.dofun.carsetting.f.b.aK()) {
            this.aa.setVisibility(8);
        }
        this.l.setVisibility(this.j.h ? 0 : 8);
        this.s.setVisibility(this.j.f134a ? 0 : 8);
        if (com.dofun.carsetting.f.b.ag()) {
            this.s.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.I()) {
            this.V.setVisibility(0);
        }
        if (com.dofun.carsetting.f.b.s()) {
            this.I.setVisibility(com.dofun.carsetting.f.o.f().equals("0") ? 0 : 8);
        }
        if (com.dofun.carsetting.f.b.f()) {
            this.ac.setVisibility(8);
            if (com.dofun.carsetting.f.b.U()) {
                this.ae.setVisibility(8);
            } else {
                this.ae.setVisibility(0);
            }
        }
        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.V.setVisibility(0);
            this.ac.setVisibility(8);
            this.ae.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.G()) {
            this.m.setVisibility(0);
            this.m.setText(com.dofun.carsetting.f.b.n(this.i));
        }
        if (com.dofun.carsetting.f.b.a().contains("TPMS") || this.j.P) {
            this.T.setVisibility(8);
        }
        switch (com.dofun.carsetting.f.b.b("/sys/devices/tp9930/id")) {
            case 0:
                this.ae.setText(this.i.getString(R.string.ic360) + "：" + this.i.getString(R.string.device_info_default));
                break;
            case 1:
                this.ae.setText(this.i.getString(R.string.ic360) + "：TP9930");
                break;
            case 2:
                this.ae.setText(this.i.getString(R.string.ic360) + "：TP2856");
                break;
            case 3:
                this.ae.setText(this.i.getString(R.string.ic360) + "：TP2826");
                break;
        }
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        HashMap map = new HashMap();
        map.put(0, "MODE_AHD_720P_25FPS");
        map.put(1, "MODE_AHD_720P_30FPS");
        map.put(4, "MODE_AHD_1080P_25FPS");
        map.put(5, "MODE_AHD_1080P_30FPS");
        map.put(2, "MODE_TVI_720P_25FPS");
        map.put(3, "MODE_TVI_720P_30FPS");
        map.put(6, "MODE_TVI_1080P_25FPS");
        map.put(7, "MODE_TVI_1080P_30FPS");
        ((com.dofun.carsetting.presenter.c) this.h).a((com.dofun.carsetting.g.e) this);
        if (com.dofun.carsetting.f.b.at() && !com.dofun.carsetting.f.b.f()) {
            this.ad.setVisibility(0);
        }
        if (com.dofun.carsetting.f.b.H()) {
            String string = (String) map.get(Integer.valueOf(SystemProperties.getInt("persist.camera.tp2815", 0)));
            if (TextUtils.isEmpty(string)) {
                string = this.i.getString(R.string.camera_system_info_unknown);
            }
            this.ad.setText(this.i.getString(R.string.camera_system_info) + string);
            return;
        }
        if (com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.F()) {
            int iWrite = this.ah.write(65523, 0, 0, "car_reverse\ntvd_resolution");
            ArrayList arrayList = new ArrayList();
            for (CharSequence charSequence : this.i.getResources().getTextArray(R.array.new_reverse_cis1_less1080_entries)) {
                arrayList.add((String) charSequence);
            }
            this.ad.setText(this.i.getString(R.string.camera_system_info) + ((String) arrayList.get(iWrite)));
            return;
        }
        if (com.dofun.carsetting.f.b.ar() >= 2 && com.dofun.carsetting.f.b.ar() < this.an.length) {
            this.ad.setText(this.i.getString(R.string.camera_system_info) + this.an[com.dofun.carsetting.f.b.ar()]);
            return;
        }
        this.ad.setText(this.i.getString(R.string.camera_system_info_unknown));
    }

    /* JADX WARN: Code duplicated, block: B:31:0x00e6 A[Catch: Exception -> 0x00fd, TryCatch #1 {Exception -> 0x00fd, blocks: (B:8:0x0037, B:10:0x003f, B:23:0x008a, B:25:0x00a2, B:32:0x00f9, B:27:0x00a6, B:28:0x00b6, B:29:0x00c6, B:30:0x00d6, B:31:0x00e6, B:11:0x0046, B:18:0x0078, B:20:0x007e, B:22:0x0084, B:17:0x006f, B:14:0x0068), top: B:55:0x0037, inners: #0 }] */
    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws Throwable {
        int i = 0;
        switch (view.getId()) {
            case R.id.btn_about /* 2131230778 */:
                g();
                break;
            case R.id.btn_checkupdate /* 2131230781 */:
                h();
                break;
            case R.id.btn_export_log /* 2131230784 */:
                try {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.tw.bug.report", "com.tw.bug.report.ReportActivity"));
                    intent.setFlags(268435456);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                break;
            case R.id.iv_back /* 2131230953 */:
                ((MainActivity) getActivity()).c();
                break;
            case R.id.ll_general /* 2131231007 */:
                if (System.currentTimeMillis() - this.aq > 3000) {
                    this.aq = System.currentTimeMillis();
                    this.g = 0;
                } else {
                    this.g++;
                }
                if (this.g == 5 && av != null) {
                    av.b();
                    break;
                }
                break;
            case R.id.tv_android_version_info /* 2131231270 */:
                System.arraycopy(this.ao, 1, this.ao, 0, this.ao.length - 1);
                this.ao[this.ao.length - 1] = SystemClock.uptimeMillis();
                if (this.ao[0] >= SystemClock.uptimeMillis() - 1000) {
                    this.ao = new long[3];
                    Intent className = null;
                    try {
                        if (com.dofun.carsetting.f.b.o()) {
                            i = SystemProperties.getInt("persist.android.build.version", 10);
                        } else {
                            String strB = com.dofun.carsetting.f.o.b();
                            Log.d("AboutFragment", "versionStr:" + strB);
                            if (TextUtils.isEmpty(strB)) {
                                strB = "0";
                            }
                            try {
                                i = Integer.parseInt(strB);
                            } catch (Exception e2) {
                                Log.e("AboutFragment", Log.getStackTraceString(e2));
                            }
                            if (com.dofun.carsetting.f.b.s() && !com.dofun.carsetting.f.o.i()) {
                                i = SystemProperties.getInt("persist.android.build.version", 10);
                            }
                        }
                        Log.d("AboutFragment", "versionInt:" + i);
                        if (i != 0) {
                            switch (i) {
                                case 10:
                                    className = new Intent("android.intent.action.MAIN").setClassName("android", PlatLogoActivity.class.getName());
                                    break;
                                case 11:
                                    className = new Intent("android.intent.action.MAIN").setClassName("android", "com.android.internal.app.PlatLogoActivityR");
                                    break;
                                case 12:
                                    className = new Intent("android.intent.action.MAIN").setClassName("android", "com.android.internal.app.PlatLogoActivityS");
                                    break;
                                case 13:
                                    className = new Intent("android.intent.action.MAIN").setClassName("android", "com.android.internal.app.PlatLogoActivityT");
                                    break;
                                case 14:
                                    className = new Intent("android.intent.action.MAIN").setClassName("android", "com.android.internal.app.PlatLogoActivityU");
                                    break;
                            }
                        } else {
                            className = new Intent("android.intent.action.MAIN").setClassName("android", PlatLogoActivity.class.getName());
                        }
                        startActivity(className);
                    } catch (Exception e3) {
                        Log.e("AboutFragment", Log.getStackTraceString(e3));
                        return;
                    }
                }
                break;
        }
    }

    private void g() throws Throwable {
        BufferedReader bufferedReader;
        String str = null;
        View viewInflate = LayoutInflater.from(this.i).inflate(R.layout.dialog_versioninfo, (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.i, R.style.custom_dialog);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_dialog_title);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_dialog_close);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.mcu_info);
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_sn);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ll_qr_imei_1);
        LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(R.id.ll_qr_imei_2);
        textView.setText("MCU");
        textView2.setText(this.at);
        String string = SystemProperties.get("ro.boot.serialno", "");
        StringBuilder sb = new StringBuilder();
        sb.append("SN: ");
        if (TextUtils.isEmpty(string)) {
            string = getResources().getString(R.string.device_info_default);
        }
        sb.append(string);
        textView3.setText(sb.toString());
        ImageView imageView2 = (ImageView) viewInflate.findViewById(R.id.system_info);
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/cid"));
                try {
                    String line = bufferedReader.readLine();
                    try {
                        bufferedReader.close();
                    } catch (Exception unused) {
                    }
                    str = line;
                } catch (Exception unused2) {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception unused3) {
            }
        } catch (Exception unused4) {
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
        }
        imageView2.setImageBitmap(a(str, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
        ImageView imageView3 = (ImageView) viewInflate.findViewById(R.id.iv_imei_1);
        if (!TextUtils.isEmpty(this.ak)) {
            imageView3.setImageBitmap(a(this.ak, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
        } else {
            linearLayout.setVisibility(8);
        }
        ImageView imageView4 = (ImageView) viewInflate.findViewById(R.id.iv_imei_2);
        if (!TextUtils.isEmpty(this.al)) {
            imageView4.setImageBitmap(a(this.al, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
        } else {
            linearLayout2.setVisibility(8);
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.a.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                a.this.ar.dismiss();
            }
        });
        builder.setView(viewInflate);
        this.ar = builder.show();
        Window window = this.ar.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) this.i.getResources().getDimension(R.dimen.dialog_mcu_version_info_width);
        attributes.height = (int) this.i.getResources().getDimension(R.dimen.dialog_mcu_version_info_height);
        Log.d("AboutFragment", "init():lp.width:" + attributes.width + " lp.height:" + attributes.height);
        window.setAttributes(attributes);
    }

    private void h() {
        View viewInflate = LayoutInflater.from(this.i).inflate(R.layout.dialog_check_update, (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.i, R.style.custom_dialog);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_dialog_title);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_dialog_close);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.about_mcu2);
        textView.setText(R.string.about_detection_update);
        textView2.setText(this.c);
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.about_can2);
        if (textView3 != null && this.d != null) {
            textView3.setText(this.d);
        }
        ((TextView) viewInflate.findViewById(R.id.about_system2)).setText(this.e);
        TextView textView4 = (TextView) viewInflate.findViewById(R.id.about_tlink2);
        this.f = SystemProperties.get("zj.zsender.version", "");
        if (textView4 != null && !TextUtils.isEmpty(this.f)) {
            textView4.setText(getResources().getString(R.string.about_tlink_info_t) + this.f);
        }
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ll_online_system_update);
        if (!this.j.I) {
            linearLayout.setVisibility(8);
        }
        this.L = (LinearLayout) viewInflate.findViewById(R.id.tv_can_update);
        if (com.dofun.carsetting.f.b.a().contains("Driving")) {
            this.L.setVisibility(0);
        }
        LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(R.id.ll_tlink_update);
        if (!this.j.K) {
            linearLayout2.setVisibility(8);
        }
        ((TextView) viewInflate.findViewById(R.id.tv_detection_update_mcu)).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.a.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                a.this.c("com.tw.carchoose", "com.tw.carchoose.UpCarMcu");
            }
        });
        ((TextView) viewInflate.findViewById(R.id.tv_detection_update_system)).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.a.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                a.this.c("com.abupdate.fota_demo_iot", "com.abupdate.fota_demo_iot.view.activity.MainActivity");
                a.this.as.dismiss();
            }
        });
        ((TextView) viewInflate.findViewById(R.id.tv_detection_update_can)).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.a.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                a.this.c("com.tw.carchoose", "com.tw.carchoose.UpCanBox");
            }
        });
        ((TextView) viewInflate.findViewById(R.id.tv_detection_update_tlink)).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.a.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new com.dofun.carsetting.c.k(a.this.getActivity()).show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.a.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                a.this.as.dismiss();
            }
        });
        builder.setView(viewInflate);
        this.as = builder.show();
        Window window = this.as.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) this.i.getResources().getDimension(R.dimen.dialog_check_update_info_width);
        attributes.height = (int) this.i.getResources().getDimension(R.dimen.dialog_check_update_info_height);
        Log.d("AboutFragment", "init():lp.width:" + attributes.width + " lp.height:" + attributes.height);
        window.setAttributes(attributes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(String str, String str2) {
        try {
            Intent intent = new Intent();
            intent.setClassName(str, str2);
            intent.setFlags(268435456);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("AboutFragment", e.getMessage());
        }
    }

    private Bitmap a(String str, int i, int i2) {
        if (str != null) {
            try {
                if (!"".equals(str) && str.length() >= 1) {
                    Hashtable hashtable = new Hashtable();
                    hashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
                    com.google.zxing.common.b bVarA = new com.google.zxing.qrcode.a().a(str, BarcodeFormat.QR_CODE, i, i2, hashtable);
                    int[] iArr = new int[i * i2];
                    for (int i3 = 0; i3 < i2; i3++) {
                        for (int i4 = 0; i4 < i; i4++) {
                            if (bVarA.a(i4, i3)) {
                                iArr[(i3 * i) + i4] = -16777216;
                            } else {
                                iArr[(i3 * i) + i4] = -1;
                            }
                        }
                    }
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                    bitmapCreateBitmap.setPixels(iArr, 0, i, 0, 0, i, i2);
                    return bitmapCreateBitmap;
                }
            } catch (Exception unused) {
                return null;
            }
        }
        return null;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        ((com.dofun.carsetting.presenter.c) this.h).b();
        if (this.b == null || this.b.isEmpty()) {
            return;
        }
        this.l.setText(this.i.getString(R.string.about_iccid1) + this.b + this.q);
    }

    @Override // com.dofun.carsetting.g.e
    public void a(boolean z) {
        if (z) {
            this.q = getResources().getString(R.string.about_dofun_card);
        } else {
            this.q = "";
        }
        if (this.b == null || this.b.isEmpty()) {
            return;
        }
        this.l.setText(this.i.getString(R.string.about_iccid1) + this.b + this.q);
    }

    @Override // com.dofun.carsetting.g.e
    public void a(String str) {
        this.M.setText(getResources().getString(R.string.about_can_info_t) + str);
        this.d = getResources().getString(R.string.about_can_info_t) + str;
    }

    @Override // com.dofun.carsetting.g.e
    public void b(String str) {
        this.N.setText(str);
        this.c = str;
    }

    @Override // com.dofun.carsetting.g.e
    public void c(String str) {
        this.k.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void a(String str, String str2) {
        this.G.setText(str);
        if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.v()) {
            if (!com.dofun.carsetting.f.o.g().equals("0")) {
                this.G.setText(com.dofun.carsetting.f.o.h());
            }
        } else if ((com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.s()) && !com.dofun.carsetting.f.o.g().equals("0")) {
            this.G.setText(com.dofun.carsetting.f.o.h());
        }
        this.H.setText(str2);
    }

    @Override // com.dofun.carsetting.g.e
    public void b(String str, String str2) {
        String str3 = (String) com.dofun.carsetting.f.b.a(getActivity()).second;
        this.J.setText(str);
        if (com.dofun.carsetting.f.b.h(getActivity())) {
            this.I.setVisibility(8);
            str3.substring(str3.indexOf("(") + 1, str3.length() - 1);
        } else if (com.dofun.carsetting.f.b.f()) {
            if (com.dofun.carsetting.f.o.c().equals("1") && com.dofun.carsetting.f.b.b(getActivity()) == 1.5f) {
                this.I.setVisibility(8);
            } else if (!com.dofun.carsetting.f.o.c().equals("0")) {
                this.J.setText(com.dofun.carsetting.f.o.d());
            }
            if (com.dofun.carsetting.f.b.T() && com.dofun.carsetting.f.b.b(getActivity()) == 1.5f) {
                this.I.setVisibility(8);
            }
        } else if (com.dofun.carsetting.f.b.v()) {
            if (com.dofun.carsetting.f.b.b(getActivity()) == 1.5f) {
                this.I.setVisibility(8);
            }
        } else if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            if (!com.dofun.carsetting.f.o.c().equals("1") && !com.dofun.carsetting.f.o.c().equals("0")) {
                this.I.setVisibility(0);
                this.J.setText(com.dofun.carsetting.f.o.d());
            } else if (com.dofun.carsetting.f.b.b(getActivity()) == 1.5f || com.dofun.carsetting.f.b.F()) {
                this.I.setVisibility(com.dofun.carsetting.f.o.c().equals("1") ? 8 : 0);
            }
        } else if (com.dofun.carsetting.f.b.s() && !com.dofun.carsetting.f.o.c().equals("0")) {
            this.J.setText(com.dofun.carsetting.f.o.d());
        }
        this.K.setText(str2);
    }

    @Override // com.dofun.carsetting.g.e
    public void d(String str) {
        this.R.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void e(String str) {
        this.n.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void f(String str) {
        this.b = str;
        if (str == null || str.isEmpty()) {
            return;
        }
        this.l.setText(this.i.getString(R.string.about_iccid1) + str + this.q);
    }

    @Override // com.dofun.carsetting.g.e
    public void g(String str) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        if (i >= 1280 && i2 >= 720) {
            if (com.dofun.carsetting.f.b.bb()) {
                this.P.setVisibility(0);
            }
            this.P.setText(getResources().getString(R.string.about_display_info_t) + i + "x" + i2);
            if (j()) {
                this.P.setText(getResources().getString(R.string.about_display_info_t) + "2312x720");
            }
            this.at = str + ", " + i + "x" + i2 + "," + this.ai;
            return;
        }
        this.at = str + "," + this.ai;
    }

    @Override // com.dofun.carsetting.g.e
    public void h(String str) {
        this.t.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void i(String str) {
        this.z.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void j(String str) {
        this.u.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void k(String str) {
        this.A.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void l(String str) {
        this.v.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void m(String str) {
        this.B.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void n(String str) {
        this.w.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void o(String str) {
        this.C.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void p(String str) {
        this.x.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void q(String str) {
        this.D.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void r(String str) {
        this.O.setText(str);
        this.e = str;
    }

    @Override // com.dofun.carsetting.g.e
    public void s(String str) {
        if ("".equals(str)) {
            return;
        }
        this.f235a = "(" + str + ")";
    }

    @Override // com.dofun.carsetting.g.e
    public void t(String str) {
        this.U.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void u(String str) {
        this.W.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void v(String str) {
        this.Z.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void w(String str) {
        this.aa.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void x(String str) {
        this.ab.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void y(String str) {
        this.ac.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void z(String str) {
        this.ak = str;
        this.o.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void A(String str) {
        this.al = str;
        this.p.setText(str);
    }

    @Override // com.dofun.carsetting.g.e
    public void a(boolean z, boolean z2, boolean z3, boolean z4) {
        int i = 8;
        if (!com.dofun.carsetting.f.b.ag()) {
            if (this.s != null) {
                this.s.setVisibility(((z && z2) || (z3 && z4)) ? 0 : 8);
            }
            if (this.y != null) {
                this.y.setVisibility((z && z2) ? 0 : 8);
            }
            if (this.E != null) {
                LinearLayout linearLayout = this.E;
                if (z3 && z4) {
                    i = 0;
                }
                linearLayout.setVisibility(i);
                return;
            }
            return;
        }
        this.s.setVisibility(8);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
        ((com.dofun.carsetting.presenter.c) this.h).c();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() throws Throwable {
        super.onResume();
        ((com.dofun.carsetting.presenter.c) this.h).d();
        this.am.setVisibility(Settings.System.getInt(this.i.getContentResolver(), "hardware_info_visibility", 1) == 1 ? 0 : 8);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        ((com.dofun.carsetting.presenter.c) this.h).l();
        if (this.ah != null) {
            this.ah.close();
        }
    }

    public static synchronized String a(Context context) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
            return context.getResources().getString(R.string.unknown);
        }
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
    }

    private Bitmap i() {
        return BitmapFactory.decodeFile(this.au + "/.ResourcePicture/CarSettings/brands.png");
    }

    public String c() throws IOException {
        BufferedReader bufferedReader;
        String str = "";
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(this.au + "/.ResourcePicture/CarSettings/custom.txt")));
            bufferedInputStream.mark(4);
            byte[] bArr = new byte[3];
            bufferedInputStream.read(bArr);
            bufferedInputStream.reset();
            if (bArr[0] == -17 && bArr[1] == -69 && bArr[2] == -65) {
                bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "utf-8"));
            } else if (bArr[0] == -1 && bArr[1] == -2) {
                bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "unicode"));
            } else if (bArr[0] == -2 && bArr[1] == -1) {
                bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "utf-16be"));
            } else if (bArr[0] == -1 && bArr[1] == -1) {
                bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "utf-16le"));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "utf-8"));
            }
            String line = bufferedReader.readLine();
            while (line != null) {
                String str2 = str + line + "";
                try {
                    line = bufferedReader.readLine();
                    str = str2;
                } catch (FileNotFoundException e) {
                    e = e;
                    str = str2;
                    e.printStackTrace();
                    return str;
                } catch (IOException e2) {
                    e = e2;
                    str = str2;
                    e.printStackTrace();
                    return str;
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e3) {
            e = e3;
        } catch (IOException e4) {
            e = e4;
        }
        return str;
    }

    private boolean j() {
        return (com.dofun.carsetting.f.b.a().contains("ZH49") || com.dofun.carsetting.f.b.a().contains("ZH54")) && SystemProperties.get("ro.boot.lcd_describe").contains("2400x720");
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }
}
