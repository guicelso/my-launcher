package com.dofun.carsetting.fragment;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.adapter.AppSelectAdapter;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.bean.AppInfo;
import com.dofun.carsetting.presenter.VoicePresenter;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.DividerItemDecoration;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.SwitchPreference;
import com.dofun.carsetting.widget.TwPreference;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: VoiceFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class ac extends ad<VoicePresenter> implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, com.dofun.carsetting.g.j, com.dofun.carsetting.g.t {
    private RadioButton A;
    private InfoPreference B;
    private InfoPreference C;
    private InfoPreference D;
    private InfoPreference E;
    private LinearLayout F;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    LinearLayout f252a;
    LinearLayout b;
    LinearLayout c;
    LinearLayout d;
    LinearLayout e;
    LinearLayout f;
    LinearLayout g;
    private RecyclerView k;
    private AppSelectAdapter l;
    private InfoPreference n;
    private InfoPreference o;
    private SwitchPreference p;
    private SwitchPreference q;
    private SwitchPreference r;
    private SwitchPreference s;
    private SeekBar t;
    private TextView u;
    private RadioGroup v;
    private RadioButton w;
    private RadioButton x;
    private RadioButton y;
    private RadioButton z;
    private List<AppInfo> m = new ArrayList();
    private boolean G = false;
    private boolean H = false;
    private int I = 0;

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_voice, (ViewGroup) null);
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public VoicePresenter d() {
        return new VoicePresenter(getActivity());
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.f252a = (LinearLayout) view.findViewById(R.id.ll_wake_up_control);
        this.b = (LinearLayout) view.findViewById(R.id.ll_voice_sensitive);
        this.c = (LinearLayout) view.findViewById(R.id.ll_voice_speed);
        this.d = (LinearLayout) view.findViewById(R.id.ll_default_app);
        this.e = (LinearLayout) view.findViewById(R.id.ll_voice_help);
        this.f = (LinearLayout) view.findViewById(R.id.ll_voice_wakeup_sen);
        this.g = (LinearLayout) view.findViewById(R.id.ll_control_more);
        this.k = (RecyclerView) view.findViewById(R.id.rv_voice_app);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        this.k.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(1);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.i, 1);
        dividerItemDecoration.a(this.i.getResources().getDrawable(R.drawable.tl_list_divider2));
        this.k.addItemDecoration(dividerItemDecoration);
        this.l = new AppSelectAdapter();
        this.l.a(true);
        this.k.setAdapter(this.l);
        this.l.a(new AppSelectAdapter.b() { // from class: com.dofun.carsetting.fragment.ac.1
            @Override // com.dofun.carsetting.adapter.AppSelectAdapter.b
            public void a(AppInfo appInfo, int i) throws Throwable {
                ((VoicePresenter) ac.this.h).a(appInfo.componentName);
                ac.this.l.notifyDataSetChanged();
                ac.this.a(TextUtils.equals(appInfo.componentName.getClassName(), "com.dofun.aios.voice.ui.MainActivity") || TextUtils.equals(ac.this.l.f102a.getClassName(), "com.dofun.bridge.MainActivity"));
            }
        });
        this.n = (InfoPreference) view.findViewById(R.id.voice_btn_wake_up_setting);
        this.o = (InfoPreference) view.findViewById(R.id.ip_float_icon);
        this.p = (SwitchPreference) view.findViewById(R.id.sp_voice_icon);
        this.q = (SwitchPreference) view.findViewById(R.id.sp_voice_wake_up);
        this.r = (SwitchPreference) view.findViewById(R.id.sp_quick_wake_up);
        this.s = (SwitchPreference) view.findViewById(R.id.sp_screen_control);
        this.n.setOnPreferenceChangeListener(this);
        this.o.setOnPreferenceChangeListener(this);
        this.p.setOnPreferenceChangeListener(this);
        this.q.setOnPreferenceChangeListener(this);
        this.r.setOnPreferenceChangeListener(this);
        this.s.setOnPreferenceChangeListener(this);
        this.t = (SeekBar) view.findViewById(R.id.sb_voice_sensitive);
        this.u = (TextView) view.findViewById(R.id.tv_voice_sensitive);
        this.t.setOnSeekBarChangeListener(this);
        this.v = (RadioGroup) view.findViewById(R.id.rg_voice_speed);
        this.w = (RadioButton) view.findViewById(R.id.rb_less_20);
        this.x = (RadioButton) view.findViewById(R.id.rb_less_15);
        this.y = (RadioButton) view.findViewById(R.id.rb_normal);
        this.z = (RadioButton) view.findViewById(R.id.rb_more_15);
        this.A = (RadioButton) view.findViewById(R.id.rb_more_20);
        this.v.setOnCheckedChangeListener(this);
        this.B = (InfoPreference) view.findViewById(R.id.ip_default_navi);
        this.C = (InfoPreference) view.findViewById(R.id.ip_default_music);
        this.D = (InfoPreference) view.findViewById(R.id.ip_default_radio);
        this.E = (InfoPreference) view.findViewById(R.id.voice_wakeup_sen);
        this.B.setOnPreferenceChangeListener(this);
        this.C.setOnPreferenceChangeListener(this);
        this.D.setOnPreferenceChangeListener(this);
        this.F = (LinearLayout) view.findViewById(R.id.iv_back);
        this.F.setOnClickListener(this);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        if (com.dofun.carsetting.f.b.T()) {
            this.f252a.setVisibility(8);
            this.D.setVisibility(8);
            this.r.setVisibility(8);
            this.s.setVisibility(8);
        }
        ((VoicePresenter) this.h).o();
        ((VoicePresenter) this.h).c();
        this.m = ((VoicePresenter) this.h).q();
        this.l.a(this.m);
        this.l.b(false);
        this.l.f102a = ((VoicePresenter) this.h).b();
        this.l.notifyDataSetChanged();
        a(this.l.f102a != null && com.dofun.carsetting.f.a.a().b(this.l.f102a.getPackageName()) && (TextUtils.equals(this.l.f102a.getClassName(), "com.dofun.aios.voice.ui.MainActivity") || TextUtils.equals(this.l.f102a.getClassName(), "com.dofun.bridge.MainActivity")));
        String str = ((VoicePresenter) this.h).d.speed;
        if ("1.7".equals(str)) {
            this.w.setChecked(true);
        } else if ("1.1".equals(str)) {
            this.x.setChecked(true);
        } else if ("0.85".equals(str)) {
            this.y.setChecked(true);
        } else if ("0.65".equals(str)) {
            this.z.setChecked(true);
        } else if ("0.5".equals(str)) {
            this.A.setChecked(true);
        }
        try {
            int iFloatValue = (int) ((Float.valueOf(((VoicePresenter) this.h).d.sensitivity).floatValue() * 10.0f) - 5.0f);
            this.u.setText(((VoicePresenter) this.h).d.sensitivity);
            this.t.setProgress(iFloatValue);
            com.dofun.carsetting.f.g.a().b("VoiceFragment", "thresh value " + iFloatValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Settings.System.getInt(MyApp.a().getContentResolver(), "YZS_HIDE", com.dofun.carsetting.f.b.c() ? 3 : 0);
        this.q.setValue(((VoicePresenter) this.h).d.awakeable);
        this.G = this.q.getValue();
        this.p.setValue(((VoicePresenter) this.h).d.micIconShow);
        this.H = this.p.getValue();
        this.r.setValue(((VoicePresenter) this.h).d.canQuickAwake);
        this.s.setValue(((VoicePresenter) this.h).d.screenControl);
        String str2 = ((VoicePresenter) this.h).d.mapSoftware;
        String str3 = ((VoicePresenter) this.h).d.musicSoftware;
        String str4 = ((VoicePresenter) this.h).d.radioSoftware;
        if (((VoicePresenter) this.h).h() != null) {
            this.B.setValueTip(((VoicePresenter) this.h).d(str2));
            this.B.setValueIcon(((VoicePresenter) this.h).e(str2));
        } else {
            this.B.setValueTip(getString(R.string.no_default_navi_app));
        }
        if (((VoicePresenter) this.h).m() != null) {
            this.C.setValueTip(((VoicePresenter) this.h).d(str3));
            this.C.setValueIcon(((VoicePresenter) this.h).e(str3));
        } else {
            this.C.setValueTip(getString(R.string.guide_no_default_music));
        }
        if (((VoicePresenter) this.h).n() != null) {
            this.D.setValueTip(((VoicePresenter) this.h).d(str4));
            this.D.setValueIcon(((VoicePresenter) this.h).e(str4));
        } else {
            this.D.setValueTip(getString(R.string.no_default_radio_app));
        }
        if ("5".equals(com.dofun.carsetting.f.y.a())) {
            this.E.setValueTip(com.dofun.carsetting.f.s.a(R.string.voice_hello_dongfang));
        } else {
            String strB = com.dofun.carsetting.f.x.a().b();
            if (!TextUtils.isEmpty(strB)) {
                this.E.setValueTip(strB);
            }
        }
        this.n.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "SWC_VOICE_MODE", 0)));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.iv_back) {
            return;
        }
        ((MainActivity) getActivity()).c();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.l == null || this.h == 0) {
            return;
        }
        this.l.f102a = ((VoicePresenter) this.h).b();
        this.l.notifyDataSetChanged();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        ((VoicePresenter) this.h).p();
    }

    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        switch (twPreference.getId()) {
            case R.id.ip_default_music /* 2131230916 */:
                a("MUSIC");
                break;
            case R.id.ip_default_navi /* 2131230917 */:
                a("NAVI");
                break;
            case R.id.ip_default_radio /* 2131230918 */:
                a("RADIO");
                break;
            case R.id.sp_quick_wake_up /* 2131231210 */:
                ((VoicePresenter) this.h).d.canQuickAwake = ((Boolean) obj).booleanValue();
                ((VoicePresenter) this.h).d();
                break;
            case R.id.sp_screen_control /* 2131231213 */:
                ((VoicePresenter) this.h).d.screenControl = ((Boolean) obj).booleanValue();
                ((VoicePresenter) this.h).d();
                break;
            case R.id.sp_voice_icon /* 2131231214 */:
                Boolean bool = (Boolean) obj;
                this.H = bool.booleanValue();
                c();
                ((VoicePresenter) this.h).d.micIconShow = bool.booleanValue();
                ((VoicePresenter) this.h).d();
                break;
            case R.id.sp_voice_wake_up /* 2131231215 */:
                Boolean bool2 = (Boolean) obj;
                this.G = bool2.booleanValue();
                c();
                ((VoicePresenter) this.h).d.awakeable = bool2.booleanValue();
                ((VoicePresenter) this.h).d();
                break;
            case R.id.voice_btn_wake_up_setting /* 2131231387 */:
                Settings.System.putInt(this.i.getContentResolver(), "SWC_VOICE_MODE", Integer.valueOf((String) obj).intValue());
                break;
        }
        return false;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        this.u.setText(String.valueOf(((double) (i + 5)) / 10.0d));
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        double progress = ((double) (seekBar.getProgress() + 5)) / 10.0d;
        ((VoicePresenter) this.h).d.sensitivity = String.valueOf(progress);
        ((VoicePresenter) this.h).d();
        com.dofun.carsetting.f.g.a().b("VoiceFragment", "progress value " + progress);
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton radioButton = (RadioButton) this.v.findViewById(i);
        if (radioButton == null || radioButton.isPressed()) {
            switch (i) {
                case R.id.rb_less_15 /* 2131231118 */:
                    ((VoicePresenter) this.h).d.speed = "1.1";
                    break;
                case R.id.rb_less_20 /* 2131231119 */:
                    ((VoicePresenter) this.h).d.speed = "1.7";
                    break;
                case R.id.rb_more_15 /* 2131231120 */:
                    ((VoicePresenter) this.h).d.speed = "0.65";
                    break;
                case R.id.rb_more_20 /* 2131231121 */:
                    ((VoicePresenter) this.h).d.speed = "0.5";
                    break;
                case R.id.rb_normal /* 2131231122 */:
                    ((VoicePresenter) this.h).d.speed = "0.85";
                    break;
            }
            ((VoicePresenter) this.h).d();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        if (z) {
            this.g.setVisibility(0);
            this.b.setVisibility(0);
            this.d.setVisibility(0);
            this.c.setVisibility(0);
            this.v.setVisibility(0);
            this.f.setVisibility(0);
            this.e.setVisibility(0);
            return;
        }
        this.g.setVisibility(8);
        this.b.setVisibility(8);
        this.c.setVisibility(8);
        this.d.setVisibility(8);
        this.v.setVisibility(8);
        this.f.setVisibility(8);
        this.e.setVisibility(8);
    }

    private void c() {
        int i;
        Log.i("VoiceFragment", "YZS_HIDE value111： voiceIconShow:" + this.H + " voiceWakeUp:" + this.G);
        if (!this.H && !this.G) {
            i = 1;
        } else if (this.H || !this.G) {
            i = (!this.H || this.G) ? 0 : 3;
        } else {
            i = 2;
        }
        Log.i("VoiceFragment", "YZS_HIDE value222： " + i);
        com.dofun.carsetting.f.g.a().b("VoiceFragment", "write YZS_HIDE value " + i);
        Settings.System.putInt(MyApp.a().getContentResolver(), "YZS_HIDE", i);
        this.I = i;
        if (i == 0) {
            com.dofun.carsetting.f.q.a().a("com.unisound.intent.action.DO_SHOW", null, null);
            return;
        }
        if (i == 1) {
            com.dofun.carsetting.f.q.a().a("com.unisound.intent.action.DO_HIDE", null, null);
        } else if (i == 2) {
            com.dofun.carsetting.f.q.a().a("com.unisound.intent.action.DO_HIDE2", null, null);
        } else if (i == 3) {
            com.dofun.carsetting.f.q.a().a("com.unisound.intent.action.DO_HIDE3", null, null);
        }
    }

    /* JADX WARN: Code duplicated, block: B:18:0x003c  */
    private void a(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("type", str);
        switch (str) {
            case "NAVI":
                if (((VoicePresenter) this.h).h() != null) {
                    bundle.putString("current_type_app_package", ((VoicePresenter) this.h).h().activityInfo.packageName);
                    bundle.putString("current_type_app_class", ((VoicePresenter) this.h).h().activityInfo.name);
                    break;
                }
                break;
            case "MUSIC":
                if (((VoicePresenter) this.h).m() != null) {
                    bundle.putString("current_type_app_package", ((VoicePresenter) this.h).m().activityInfo.packageName);
                    bundle.putString("current_type_app_class", ((VoicePresenter) this.h).m().activityInfo.name);
                    break;
                }
                break;
            case "RADIO":
                if (((VoicePresenter) this.h).n() != null) {
                    bundle.putString("current_type_app_package", ((VoicePresenter) this.h).n().activityInfo.packageName);
                    bundle.putString("current_type_app_class", ((VoicePresenter) this.h).n().activityInfo.name);
                    break;
                }
                break;
        }
        h hVar = new h();
        hVar.setArguments(bundle);
        hVar.a(new h.a() { // from class: com.dofun.carsetting.fragment.ac.2
            /* JADX WARN: Code duplicated, block: B:18:0x0052  */
            @Override // com.dofun.carsetting.fragment.h.a
            public void a(String str2, AppInfo appInfo) {
                Log.d("VoiceFragment", "onAppSelect: " + str2 + " " + appInfo.appName);
                switch (str2) {
                    case "NAVI":
                        ((VoicePresenter) ac.this.h).d.mapSoftware = appInfo.componentName.getPackageName();
                        Log.d("VoiceFragment", "getVoiceConfig:mPresenter " + ((VoicePresenter) ac.this.h).d.mapSoftware);
                        break;
                    case "MUSIC":
                        ((VoicePresenter) ac.this.h).d.musicSoftware = appInfo.componentName.getPackageName();
                        break;
                    case "RADIO":
                        ((VoicePresenter) ac.this.h).d.radioSoftware = appInfo.componentName.getPackageName();
                        break;
                }
                ((VoicePresenter) ac.this.h).d();
            }
        });
        ((MainActivity) getActivity()).a(hVar);
    }
}
