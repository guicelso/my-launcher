package com.dofun.carsetting.fragment;

import android.app.UiModeManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IPowerManager;
import android.os.Message;
import android.os.ServiceManager;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.adapter.WallpaperAdapter;
import com.dofun.carsetting.bean.WallpaperInfo;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.SwitchPreference;
import com.dofun.carsetting.widget.TwPreference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: DevicesFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class i extends ad<com.dofun.carsetting.presenter.d> implements View.OnClickListener, com.dofun.carsetting.g.d, com.dofun.carsetting.g.j {
    private RecyclerView A;
    private WallpaperAdapter B;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected boolean f279a;
    protected int b;
    private com.dofun.carsetting.f.u c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView k;
    private InfoPreference l;
    private String n;
    private SeekBar o;
    private TextView p;
    private int q;
    private SeekBar r;
    private SeekBar s;
    private InfoPreference t;
    private InfoPreference u;
    private InfoPreference v;
    private InfoPreference w;
    private SwitchPreference x;
    private InfoPreference y;
    private UiModeManager z;
    private int m = 1;
    private List<WallpaperInfo> C = new ArrayList();
    private Handler D = new Handler() { // from class: com.dofun.carsetting.fragment.i.3
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            try {
                int i = message.what;
                if (i == 258) {
                    i.this.b = message.arg2;
                    i.this.w.setValueTip(i.this.getResources().getStringArray(R.array.brightness_mode_entries)[i.this.b]);
                    i.this.w.setValue(String.valueOf(i.this.b));
                    i.this.c.write(516, 255);
                    return;
                }
                if (i == 260) {
                    byte[] bArr = (byte[]) message.obj;
                    if (Integer.valueOf(bArr[1]).intValue() == 2) {
                        i.this.g.setSelected(true);
                        i.this.k.setSelected(false);
                        i.this.r.setEnabled(true);
                        i.this.s.setEnabled(true);
                    } else {
                        i.this.g.setSelected(false);
                        i.this.k.setSelected(true);
                        i.this.r.setEnabled(false);
                        i.this.s.setEnabled(false);
                    }
                    i.this.a(Integer.valueOf(bArr[3]).intValue());
                    return;
                }
                if (i == 262) {
                    byte[] bArr2 = (byte[]) message.obj;
                    i.this.r.setProgress(bArr2[0]);
                    i.this.s.setProgress(bArr2[1]);
                    return;
                }
                if (i != 516) {
                    return;
                }
                i.this.f279a = message.arg1 == 1;
                int i2 = message.arg2 & 255;
                int i3 = (message.arg2 >> 8) & 255;
                if (com.dofun.carsetting.f.b.K()) {
                    if (i.this.b == 0) {
                        i iVar = i.this;
                        if (!i.this.f279a) {
                            i3 = i2;
                        }
                        iVar.q = i3;
                    } else if (i.this.b == 1) {
                        i.this.q = i2;
                    } else if (i.this.b == 2) {
                        i.this.q = i3;
                    }
                    i.this.o.setProgress(i.this.q);
                    i.this.p.setText(i.this.q + "");
                }
            } catch (Exception unused) {
            }
        }
    };
    private SeekBar.OnSeekBarChangeListener E = new SeekBar.OnSeekBarChangeListener() { // from class: com.dofun.carsetting.fragment.i.4
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                int id = seekBar.getId();
                if (id == R.id.background_sound_seek_bar) {
                    i.this.c.write(262, 1, i);
                } else {
                    if (id != R.id.guide_sound_seek_bar) {
                        return;
                    }
                    i.this.c.write(262, 0, i);
                }
            }
        }
    };

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.c = com.dofun.carsetting.f.u.a();
        return layoutInflater.inflate(R.layout.fragment_devices, (ViewGroup) null);
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.d d() {
        Log.d("DevicesFragment", "createPresenter: DevicesPresenter");
        return new com.dofun.carsetting.presenter.d(getActivity());
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.d = (TextView) view.findViewById(R.id.system_sound_priority);
        this.e = (TextView) view.findViewById(R.id.rd_party_sound_priority);
        this.f = (TextView) view.findViewById(R.id.intelligent_priority);
        this.g = (TextView) view.findViewById(R.id.navi_audio_mixing);
        this.k = (TextView) view.findViewById(R.id.navi_audio_unmixed);
        this.l = (InfoPreference) view.findViewById(R.id.ringtone_title);
        this.l.setOnPreferenceChangeListener(this);
        this.o = (SeekBar) view.findViewById(R.id.light_seek_bar);
        this.p = (TextView) view.findViewById(R.id.tv_light_num);
        this.w = (InfoPreference) view.findViewById(R.id.ip_brightness_mode);
        this.t = (InfoPreference) view.findViewById(R.id.wallpaper);
        this.u = (InfoPreference) view.findViewById(R.id.night_display_title);
        this.v = (InfoPreference) view.findViewById(R.id.title_font_size);
        this.x = (SwitchPreference) view.findViewById(R.id.keywords_systemui_theme);
        this.r = (SeekBar) view.findViewById(R.id.guide_sound_seek_bar);
        this.r.setOnSeekBarChangeListener(this.E);
        this.s = (SeekBar) view.findViewById(R.id.background_sound_seek_bar);
        this.s.setOnSeekBarChangeListener(this.E);
        this.y = (InfoPreference) view.findViewById(R.id.ip_dropdown_menu_background);
        this.d.setOnClickListener(this);
        this.e.setOnClickListener(this);
        this.f.setOnClickListener(this);
        this.g.setOnClickListener(this);
        this.k.setOnClickListener(this);
        this.w.setOnPreferenceChangeListener(this);
        this.t.setOnPreferenceChangeListener(this);
        this.u.setOnPreferenceChangeListener(this);
        this.x.setOnPreferenceChangeListener(this);
        this.v.setOnPreferenceChangeListener(this);
        this.y.setOnPreferenceChangeListener(this);
        this.z = (UiModeManager) this.i.getSystemService(UiModeManager.class);
        if (!this.j.b) {
            this.x.setVisibility(8);
        }
        this.y.setVisibility(com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J() ? 0 : 8);
        this.A = (RecyclerView) view.findViewById(R.id.rv_wallpaper);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(0);
        this.A.setLayoutManager(linearLayoutManager);
        this.B = new WallpaperAdapter();
        this.B.a(new WallpaperAdapter.a() { // from class: com.dofun.carsetting.fragment.i.1
            @Override // com.dofun.carsetting.adapter.WallpaperAdapter.a
            public void a(WallpaperInfo wallpaperInfo, int i) throws Exception {
                Settings.System.putString(i.this.i.getContentResolver(), "MAIN_BG_CHOOSE", wallpaperInfo.thumbnailResId);
                ((MainActivity) i.this.getActivity()).b(Settings.System.getString(i.this.i.getContentResolver(), "MAIN_BG_CHOOSE"));
            }
        });
        this.A.setAdapter(this.B);
        if (this.j.O) {
            this.A.setVisibility(8);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        if (com.dofun.carsetting.f.u.c().contains("-V2")) {
            this.t.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.c()) {
            this.o.setMax(220);
        } else if (com.dofun.carsetting.f.b.K()) {
            this.o.setMax(10);
        } else {
            this.o.setMax(225);
        }
        if (!com.dofun.carsetting.f.b.K()) {
            this.q = Settings.System.getInt(getActivity().getContentResolver(), "screen_brightness", R.styleable.AppCompatTheme_windowNoTitle);
            if (com.dofun.carsetting.f.b.c()) {
                this.q -= 35;
            } else {
                this.q -= 30;
            }
            this.q = this.q < 0 ? 0 : this.q;
            this.q = this.q > this.o.getMax() ? this.o.getMax() : this.q;
            this.o.setProgress(this.q);
            this.p.setText(String.valueOf((this.q * 100) / this.o.getMax()));
        }
        this.o.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.dofun.carsetting.fragment.i.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v0 */
            /* JADX WARN: Type inference failed for: r0v1 */
            /* JADX WARN: Type inference failed for: r0v2, types: [int] */
            /* JADX WARN: Type inference failed for: r0v4 */
            /* JADX WARN: Type inference failed for: r3v3, types: [com.dofun.carsetting.f.u] */
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                i.this.q = i;
                if (!com.dofun.carsetting.f.b.K()) {
                    if (z) {
                        i.this.c();
                        return;
                    }
                    return;
                }
                ?? r0 = 1;
                if (i.this.b == 0) {
                    r0 = i.this.f279a;
                } else if (i.this.b == 1 || i.this.b != 2) {
                    r0 = 0;
                }
                if (z) {
                    i.this.c.write(516, r0, i);
                }
            }
        });
        this.r.setMax(15);
        this.s.setMax(14);
        String string = Settings.System.getString(this.i.getContentResolver(), "MAIN_BG_CHOOSE");
        this.y.setValue(String.valueOf(Settings.Secure.getInt(this.i.getContentResolver(), "Notification_Panel_View_Background_Type", 2)));
        for (Map.Entry<String, String> entry : ((MainActivity) getActivity()).e.entrySet()) {
            this.C.add(new WallpaperInfo(entry.getKey(), entry.getValue()));
        }
        this.B.a(this.C);
        WallpaperInfo wallpaperInfoC = ((MainActivity) getActivity()).c(string);
        if (wallpaperInfoC != null) {
            this.B.a(wallpaperInfoC);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.c != null) {
            this.c.addHandler("DevicesFragment", this.D);
            this.c.write(258, 255);
            this.c.write(260, 255);
            this.c.write(262, 255);
            this.c.write(516, 255);
        }
        this.x.setValue(this.z.getNightMode() == 2);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.c != null) {
            this.c.removeHandler("DevicesFragment");
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.c != null) {
            this.c.close();
            this.c.removeHandler("DevicesFragment");
        }
    }

    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        try {
            Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
            if (uri != null) {
                this.n = uri.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(i, i2, intent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        try {
            switch (twPreference.getId()) {
                case R.id.ip_brightness_mode /* 2131230914 */:
                    this.c.write(258, 1, Integer.valueOf((String) obj).intValue());
                    this.c.write(258, 128);
                    break;
                case R.id.ip_dropdown_menu_background /* 2131230919 */:
                    Settings.Secure.putInt(this.i.getContentResolver(), "Notification_Panel_View_Background_Type", Integer.valueOf((String) obj).intValue());
                    break;
                case R.id.keywords_systemui_theme /* 2131230984 */:
                    this.z.setNightMode(((Boolean) obj).booleanValue() ? 2 : 1);
                    break;
                case R.id.night_display_title /* 2131231080 */:
                    startActivity(new Intent("android.settings.NIGHT_DISPLAY_SETTINGS"));
                    break;
                case R.id.ringtone_title /* 2131231161 */:
                    Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                    intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", false);
                    if (this.n != null) {
                        intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", Uri.parse(this.n));
                    }
                    startActivityForResult(intent, this.m);
                    break;
                case R.id.title_font_size /* 2131231264 */:
                    startActivity(f());
                    break;
                case R.id.wallpaper /* 2131231390 */:
                    Intent intent2 = new Intent("android.intent.action.SET_WALLPAPER");
                    Intent intent3 = new Intent("android.intent.action.CHOOSER");
                    intent3.putExtra("android.intent.extra.INTENT", intent2);
                    intent3.putExtra("android.intent.extra.TITLE", getString(R.string.chooser_wallpaper));
                    startActivity(intent3);
                    break;
            }
        } catch (Exception unused) {
        }
        return false;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.intelligent_priority /* 2131230911 */:
                a(2);
                this.c.write(260, 3, 2);
                break;
            case R.id.navi_audio_mixing /* 2131231071 */:
                this.g.setSelected(true);
                this.k.setSelected(false);
                this.c.write(260, 1, 2);
                break;
            case R.id.navi_audio_unmixed /* 2131231072 */:
                this.g.setSelected(false);
                this.k.setSelected(true);
                this.c.write(260, 1, 3);
                break;
            case R.id.rd_party_sound_priority /* 2131231131 */:
                a(1);
                this.c.write(260, 3, 1);
                break;
            case R.id.system_sound_priority /* 2131231244 */:
                a(0);
                this.c.write(260, 3, 0);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        int i;
        this.p.setText(String.valueOf((this.q * 100) / this.o.getMax()));
        if (com.dofun.carsetting.f.b.c()) {
            i = this.q + 35;
        } else {
            this.q += 30;
            int i2 = this.q > 30 ? this.q : 30;
            i = 255;
            if (i2 <= 255) {
                i = i2;
            }
        }
        try {
            IPowerManager.Stub.asInterface(ServiceManager.getService("power"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Settings.System.putInt(getActivity().getContentResolver(), "screen_brightness", i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        if (i == 0) {
            this.d.setSelected(true);
            this.e.setSelected(false);
            this.f.setSelected(false);
        } else if (i == 1) {
            this.d.setSelected(false);
            this.e.setSelected(true);
            this.f.setSelected(false);
        } else {
            this.d.setSelected(false);
            this.e.setSelected(false);
            this.f.setSelected(true);
        }
    }

    private Intent f() {
        Intent intent = new Intent("android.intent.action.MAIN");
        if (com.dofun.carsetting.f.b.G()) {
            intent.putExtra(":settings:show_fragment", "com.android.settings.display.ToggleFontSizePreferenceFragment");
        } else if (com.dofun.carsetting.f.b.S()) {
            intent.putExtra(":settings:show_fragment", "com.android.settings.accessibility.TextReadingPreferenceFragment");
        } else if (com.dofun.carsetting.f.b.R()) {
            intent.putExtra(":settings:show_fragment", "com.android.settings.display.FontSizePreferenceFragmentForSetupWizard");
        } else {
            intent.putExtra(":settings:show_fragment", "com.android.settings.accessibility.FontSizePreferenceFragmentForSetupWizard");
        }
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.SubSettings"));
        return intent;
    }
}
