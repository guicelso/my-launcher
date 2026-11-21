package com.dofun.carsetting.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.TwPreference;
import java.util.ArrayList;

/* JADX INFO: compiled from: ColorDialogFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class f extends ad<com.dofun.carsetting.presenter.f> implements com.dofun.carsetting.g.j {
    private ImageView A;
    private ImageView B;
    private ImageView C;
    private ImageView D;
    private ImageView E;
    private ImageView F;
    private LinearLayout G;
    private LinearLayout H;
    private TextView I;
    private TextView J;
    private TextView K;
    private TextView L;
    private LinearLayout M;
    private LinearLayout N;
    private LinearLayout O;
    private LinearLayout P;
    private ImageView Q;
    private ImageView R;
    private ImageView S;
    private ImageView T;
    private ImageView U;
    private ImageView V;
    private ImageView W;
    private ImageView X;
    private SeekBar Y;
    private SeekBar Z;
    private SeekBar aa;
    private SeekBar ab;
    private int ac;
    private int ad;
    private int ae;
    private int af;
    private int ai;
    private int aj;
    private int ak;
    private int al;
    private int am;
    private View an;
    private Context ao;
    private com.dofun.carsetting.f.u e;
    private int f;
    private int g;
    private int k;
    private boolean l;
    private SeekBar m;
    private SeekBar n;
    private SeekBar o;
    private SeekBar p;
    private TextView q;
    private TextView r;
    private TextView s;
    private TextView t;
    private ImageView u;
    private ImageView v;
    private ImageView w;
    private ViewPager x;
    private ImageView y;
    private ImageView z;
    private int ag = 20;
    private int ah = 80;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    ArrayList<View> f266a = new ArrayList<>();
    String b = "/soc@03000000";
    private Handler ap = new Handler() { // from class: com.dofun.carsetting.fragment.f.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            try {
                int i = message.what;
                if (i != 272) {
                    if (i != 65525) {
                        return;
                    }
                    if (f.this.k >= 13) {
                        f.this.e.write(272, (((f.this.f * 255) & 255) << 16) + ((((int) (((double) f.this.g) * 8.5d)) & 255) << 8) + 255);
                        return;
                    } else {
                        f.this.e.write(272, (((f.this.f * 255) & 255) << 16) + ((((int) (((double) f.this.g) * 8.5d)) & 255) << 8) + ((f.this.k * 20) & 255));
                        return;
                    }
                }
                byte[] bArr = (byte[]) message.obj;
                f.this.f = (bArr[0] & 255) / 255;
                f.this.g = (int) Math.round(((double) (bArr[1] & 255)) / 8.5d);
                f.this.k = bArr[2] & 255;
                if (f.this.k > 240) {
                    f.this.k = 13;
                } else {
                    f.this.k /= 20;
                }
                if (f.this.l) {
                    return;
                }
                f.this.l = true;
                f.this.c();
            } catch (Exception unused) {
            }
        }
    };
    int c = -1;
    int d = -1;

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.f d() {
        return null;
    }

    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        return false;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.ao = context;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_color_dialog, (ViewGroup) null);
        this.e = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.m = (SeekBar) view.findViewById(R.id.color_red);
        this.n = (SeekBar) view.findViewById(R.id.color_bule);
        this.o = (SeekBar) view.findViewById(R.id.color_greed);
        this.p = (SeekBar) view.findViewById(R.id.color_gamma);
        this.q = (TextView) view.findViewById(R.id.txt_red);
        this.r = (TextView) view.findViewById(R.id.txt_greed);
        this.s = (TextView) view.findViewById(R.id.txt_blue);
        this.t = (TextView) view.findViewById(R.id.txt_gamma);
        this.u = (ImageView) view.findViewById(R.id.shape_1);
        this.v = (ImageView) view.findViewById(R.id.shape_2);
        this.w = (ImageView) view.findViewById(R.id.shape_3);
        this.y = (ImageView) view.findViewById(R.id.color_greed_reduce);
        this.z = (ImageView) view.findViewById(R.id.color_greed_add);
        this.A = (ImageView) view.findViewById(R.id.color_bule_reduce);
        this.C = (ImageView) view.findViewById(R.id.color_bule_add);
        this.D = (ImageView) view.findViewById(R.id.color_red_reduce);
        this.E = (ImageView) view.findViewById(R.id.color_red_add);
        this.B = (ImageView) view.findViewById(R.id.color_gamma_reduce);
        this.F = (ImageView) view.findViewById(R.id.color_gamma_add);
        this.G = (LinearLayout) view.findViewById(R.id.iv_back);
        this.H = (LinearLayout) view.findViewById(R.id.lin_gamma);
        this.an = view.findViewById(R.id.tw_color_panle_change);
        this.x = (ViewPager) view.findViewById(R.id.viewpaper);
        getActivity().getLayoutInflater();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getActivity());
        View viewInflate = layoutInflaterFrom.inflate(R.layout.tw_color_panle_view, (ViewGroup) null);
        View viewInflate2 = layoutInflaterFrom.inflate(R.layout.tw_color_panle_view, (ViewGroup) null);
        View viewInflate3 = layoutInflaterFrom.inflate(R.layout.tw_color_panle_view, (ViewGroup) null);
        viewInflate2.setBackgroundResource(R.drawable.tw_wallpaper2);
        viewInflate3.setBackgroundResource(R.drawable.tw_wallpaper3);
        this.f266a.add(viewInflate);
        this.f266a.add(viewInflate2);
        this.f266a.add(viewInflate3);
        this.I = (TextView) view.findViewById(R.id.txt_brightness);
        this.J = (TextView) view.findViewById(R.id.txt_contrast);
        this.K = (TextView) view.findViewById(R.id.txt_saturation);
        this.L = (TextView) view.findViewById(R.id.txt_colortemp);
        this.M = (LinearLayout) view.findViewById(R.id.lin_bright);
        this.N = (LinearLayout) view.findViewById(R.id.lin_contrast);
        this.O = (LinearLayout) view.findViewById(R.id.lin_saturation);
        this.P = (LinearLayout) view.findViewById(R.id.lin_colortemp);
        this.Q = (ImageView) view.findViewById(R.id.color_brightness_reduce);
        this.R = (ImageView) view.findViewById(R.id.color_brightness_add);
        this.S = (ImageView) view.findViewById(R.id.color_contrast_reduce);
        this.T = (ImageView) view.findViewById(R.id.color_contrast_add);
        this.U = (ImageView) view.findViewById(R.id.color_saturation_reduce);
        this.V = (ImageView) view.findViewById(R.id.color_saturation_add);
        this.W = (ImageView) view.findViewById(R.id.color_colortemp_reduce);
        this.X = (ImageView) view.findViewById(R.id.color_colortemp_add);
        this.Y = (SeekBar) view.findViewById(R.id.color_brightness);
        this.Z = (SeekBar) view.findViewById(R.id.color_contrast);
        this.aa = (SeekBar) view.findViewById(R.id.color_saturation);
        this.ab = (SeekBar) view.findViewById(R.id.color_colortemp);
        this.m.setOnSeekBarChangeListener(new b());
        this.n.setOnSeekBarChangeListener(new b());
        this.o.setOnSeekBarChangeListener(new b());
        this.p.setOnSeekBarChangeListener(new b());
        this.Y.setOnSeekBarChangeListener(new b());
        this.Z.setOnSeekBarChangeListener(new b());
        this.aa.setOnSeekBarChangeListener(new b());
        this.ab.setOnSeekBarChangeListener(new b());
        this.y.setOnClickListener(new c());
        this.z.setOnClickListener(new c());
        this.A.setOnClickListener(new c());
        this.C.setOnClickListener(new c());
        this.D.setOnClickListener(new c());
        this.E.setOnClickListener(new c());
        this.B.setOnClickListener(new c());
        this.F.setOnClickListener(new c());
        this.G.setOnClickListener(new c());
        this.Q.setOnClickListener(new c());
        this.R.setOnClickListener(new c());
        this.S.setOnClickListener(new c());
        this.T.setOnClickListener(new c());
        this.U.setOnClickListener(new c());
        this.V.setOnClickListener(new c());
        this.W.setOnClickListener(new c());
        this.X.setOnClickListener(new c());
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        if (com.dofun.carsetting.f.b.G()) {
            this.b = "/soc";
        }
        a(0);
        this.o.setProgress(this.g);
        this.n.setProgress(this.k);
        this.m.setProgress(this.f);
        this.r.setText(this.ao.getResources().getString(R.string.vcom) + ":" + this.g);
        this.s.setText(this.ao.getResources().getString(R.string.avdd) + ":" + this.k);
        this.q.setText(this.ao.getResources().getString(R.string.vabit) + ":" + this.f);
        this.q.setText(this.f == 1 ? "8bit" : "6bit");
        this.x.setAdapter(new a(getActivity()));
        this.x.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.dofun.carsetting.fragment.f.2
            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
                f.this.a(i);
            }
        });
        if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.H.setVisibility(0);
            this.t.setVisibility(0);
            this.d = com.dofun.carsetting.f.b.b("/sys/class/disp/disp/attr/gamma");
            this.c = this.d;
            this.am = this.d;
            this.p.setProgress(this.d);
            this.t.setText("GAMMA:" + this.d);
        }
        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.an.getLayoutParams().height = this.ao.getResources().getDimensionPixelOffset(R.dimen.tw_dp_h480);
            this.an.setLayoutParams(this.an.getLayoutParams());
            this.I.setVisibility(0);
            this.J.setVisibility(0);
            this.K.setVisibility(0);
            this.L.setVisibility(0);
            this.M.setVisibility(0);
            this.N.setVisibility(0);
            this.O.setVisibility(0);
            this.P.setVisibility(0);
        }
        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.ac = com.dofun.carsetting.f.b.aM();
            this.ad = com.dofun.carsetting.f.b.aN();
            this.ae = com.dofun.carsetting.f.b.aO();
            this.af = com.dofun.carsetting.f.b.aP();
        } else {
            this.ac = Settings.System.getInt(this.ao.getContentResolver(), "color_brightness", 50);
            this.ad = Settings.System.getInt(this.ao.getContentResolver(), "color_contrast", 50);
            this.ae = Settings.System.getInt(this.ao.getContentResolver(), "color_saturation", 50);
            this.af = Settings.System.getInt(this.ao.getContentResolver(), "color_temp", 0);
        }
        this.I.setText(getResources().getString(R.string.bright) + ":" + this.ac);
        this.J.setText(getResources().getString(R.string.contrast) + ":" + this.ad);
        this.K.setText(getResources().getString(R.string.saturation) + ":" + this.ae);
        this.L.setText(getResources().getString(R.string.colortemp) + ":" + this.af);
        this.Y.setProgress(this.ac);
        this.Z.setProgress(this.ad);
        this.aa.setProgress(this.ae);
        this.ab.setProgress(this.af);
        this.ai = this.af;
        this.al = ((this.ac * (this.ah - this.ag)) / 100) + this.ag;
        this.ak = ((this.ad * (this.ah - this.ag)) / 100) + this.ag;
        this.aj = ((this.ae * (this.ah - this.ag)) / 100) + this.ag;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.e != null) {
            this.e.addHandler("ColorDialogFragment", this.ap);
            this.e.write(272, ViewCompat.MEASURED_STATE_MASK);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.e != null) {
            this.e.close();
            this.e.removeHandler("ColorDialogFragment");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        com.dofun.carsetting.c.a aVar = new com.dofun.carsetting.c.a(getActivity());
        aVar.a(MyApp.a().getString(R.string.gamma_info1) + "\n" + MyApp.a().getString(R.string.gamma_info2));
        aVar.a(new com.dofun.carsetting.c.a.InterfaceC0013a() { // from class: com.dofun.carsetting.fragment.f.3
            @Override // com.dofun.carsetting.c.a.InterfaceC0013a
            public void a() {
                f.this.e.write(65282, 0, 0, String.format("run_command\nfdt set " + f.this.b + "/disp@01000000 disp_gamma <0x%x>\nfdt save", Integer.valueOf(f.this.d)));
            }

            @Override // com.dofun.carsetting.c.a.InterfaceC0013a
            public void b() {
                ((MainActivity) f.this.getActivity()).c();
            }

            @Override // com.dofun.carsetting.c.a.InterfaceC0013a
            public void c() {
                f.this.e.write(65282, 0, 0, String.format("run_command\nfdt set " + f.this.b + "/disp@01000000 disp_gamma <0x%x>\nfdt save", Integer.valueOf(f.this.d)));
            }
        });
        aVar.show();
    }

    public void a(int i) {
        this.u.setImageLevel(0);
        this.v.setImageLevel(0);
        this.w.setImageLevel(0);
        switch (i) {
            case 0:
                this.u.setImageLevel(1);
                break;
            case 1:
                this.v.setImageLevel(1);
                break;
            case 2:
                this.w.setImageLevel(1);
                break;
        }
    }

    public void c() {
        if (this.l) {
            this.o.setProgress(this.g);
            this.n.setProgress(this.k);
            this.m.setProgress(this.f);
            this.r.setText(this.ao.getResources().getString(R.string.vcom) + ":" + this.g);
            this.s.setText(this.ao.getResources().getString(R.string.avdd) + ":" + this.k);
            this.q.setText(this.f == 1 ? "8bit" : "6bit");
            this.Y.setProgress(this.ac);
            this.Z.setProgress(this.ad);
            this.aa.setProgress(this.ae);
            this.ab.setProgress(this.af);
        }
    }

    /* JADX INFO: compiled from: ColorDialogFragment.java */
    class b implements SeekBar.OnSeekBarChangeListener {
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        b() {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            switch (seekBar.getId()) {
                case R.id.color_brightness /* 2131230808 */:
                    if (f.this.ac != i && f.this.e != null) {
                        int i2 = (((f.this.ah - f.this.ag) * i) / 100) + f.this.ag;
                        Settings.System.putInt(f.this.ao.getContentResolver(), "color_brightness", i);
                        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                            f.this.e.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/enhance_bright_tw " + i2);
                        } else {
                            f.this.e.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/enhance_bright " + i2);
                        }
                        f.this.al = i2;
                        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                            com.dofun.carsetting.f.b.a(f.this.ai, f.this.aj, f.this.ak, f.this.al, f.this.am);
                        }
                    }
                    f.this.ac = i;
                    f.this.I.setText(f.this.getResources().getString(R.string.bright) + ":" + i);
                    break;
                case R.id.color_bule /* 2131230811 */:
                    f.this.k = i;
                    f.this.s.setText(f.this.ao.getResources().getString(R.string.avdd) + ":" + i);
                    break;
                case R.id.color_colortemp /* 2131230814 */:
                    if (f.this.af != i && f.this.e != null) {
                        Settings.System.putInt(f.this.ao.getContentResolver(), "color_temp", i);
                        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                            f.this.e.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/color_temperature_tw " + i);
                        } else {
                            f.this.e.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/color_temperature " + i);
                        }
                        f.this.ai = i;
                        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                            com.dofun.carsetting.f.b.a(f.this.ai, f.this.aj, f.this.ak, f.this.al, f.this.am);
                        }
                    }
                    f.this.af = i;
                    f.this.L.setText(f.this.getResources().getString(R.string.colortemp) + ":" + i);
                    break;
                case R.id.color_contrast /* 2131230817 */:
                    if (f.this.ad != i && f.this.e != null) {
                        int i3 = (((f.this.ah - f.this.ag) * i) / 100) + f.this.ag;
                        Settings.System.putInt(f.this.ao.getContentResolver(), "color_contrast", i);
                        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                            f.this.e.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/enhance_contrast_tw " + i3);
                        } else {
                            f.this.e.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/enhance_contrast " + i3);
                        }
                        f.this.ak = i3;
                        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                            com.dofun.carsetting.f.b.a(f.this.ai, f.this.aj, f.this.ak, f.this.al, f.this.am);
                        }
                    }
                    f.this.ad = i;
                    f.this.J.setText(f.this.getResources().getString(R.string.contrast) + ":" + i);
                    break;
                case R.id.color_gamma /* 2131230820 */:
                    if (z) {
                        f.this.d = i;
                        f.this.t.setText("GAMMA:" + i);
                        f.this.e.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/gamma " + f.this.d);
                        f.this.am = f.this.d;
                        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                            com.dofun.carsetting.f.b.a(f.this.ai, f.this.aj, f.this.ak, f.this.al, f.this.am);
                        }
                    }
                    break;
                case R.id.color_greed /* 2131230823 */:
                    f.this.g = i;
                    f.this.r.setText(f.this.ao.getResources().getString(R.string.vcom) + ":" + i);
                    break;
                case R.id.color_red /* 2131230826 */:
                    f.this.f = i;
                    f.this.q.setText(i == 1 ? "8bit" : "6bit");
                    break;
                case R.id.color_saturation /* 2131230829 */:
                    if (f.this.ae != i && f.this.e != null) {
                        int i4 = (((f.this.ah - f.this.ag) * i) / 100) + f.this.ag;
                        Settings.System.putInt(f.this.ao.getContentResolver(), "color_saturation", i);
                        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                            f.this.e.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/enhance_saturation_tw " + i4);
                        } else {
                            f.this.e.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/enhance_saturation " + i4);
                        }
                        f.this.aj = i4;
                        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                            com.dofun.carsetting.f.b.a(f.this.ai, f.this.aj, f.this.ak, f.this.al, f.this.am);
                        }
                    }
                    f.this.ae = i;
                    f.this.K.setText(f.this.getResources().getString(R.string.saturation) + ":" + i);
                    break;
            }
            f.this.ap.removeMessages(65525);
            f.this.ap.sendEmptyMessageDelayed(65525, 1000L);
        }
    }

    /* JADX INFO: compiled from: ColorDialogFragment.java */
    class c implements View.OnClickListener {
        c() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.color_brightness_add /* 2131230809 */:
                    f.this.Y.setProgress(f.this.Y.getProgress() + 1 > f.this.Y.getMax() ? f.this.Y.getMax() : f.this.Y.getProgress() + 1);
                    break;
                case R.id.color_brightness_reduce /* 2131230810 */:
                    f.this.Y.setProgress(f.this.Y.getProgress() - 1 >= 0 ? f.this.Y.getProgress() - 1 : 0);
                    break;
                case R.id.color_bule_add /* 2131230812 */:
                    f.this.n.setProgress(f.this.n.getProgress() + 1 > f.this.n.getMax() ? f.this.n.getMax() : f.this.n.getProgress() + 1);
                    break;
                case R.id.color_bule_reduce /* 2131230813 */:
                    f.this.n.setProgress(f.this.n.getProgress() - 1 >= 0 ? f.this.n.getProgress() - 1 : 0);
                    break;
                case R.id.color_colortemp_add /* 2131230815 */:
                    f.this.ab.setProgress(f.this.ab.getProgress() + 1 > f.this.ab.getMax() ? f.this.ab.getMax() : f.this.ab.getProgress() + 1);
                    break;
                case R.id.color_colortemp_reduce /* 2131230816 */:
                    f.this.ab.setProgress(f.this.ab.getProgress() - 1 >= 0 ? f.this.ab.getProgress() - 1 : 0);
                    break;
                case R.id.color_contrast_add /* 2131230818 */:
                    f.this.Z.setProgress(f.this.Z.getProgress() + 1 > f.this.Z.getMax() ? f.this.Z.getMax() : f.this.Z.getProgress() + 1);
                    break;
                case R.id.color_contrast_reduce /* 2131230819 */:
                    f.this.Z.setProgress(f.this.Z.getProgress() - 1 >= 0 ? f.this.Z.getProgress() - 1 : 0);
                    break;
                case R.id.color_gamma_add /* 2131230821 */:
                    f.this.p.setProgress(f.this.p.getProgress() + 1 > f.this.p.getMax() ? f.this.p.getMax() : f.this.p.getProgress() + 1);
                    f.this.d = f.this.p.getProgress();
                    f.this.t.setText("GAMMA:" + f.this.d);
                    f.this.e.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/gamma " + f.this.d);
                    f.this.am = f.this.d;
                    if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                        com.dofun.carsetting.f.b.a(f.this.ai, f.this.aj, f.this.ak, f.this.al, f.this.am);
                    }
                    break;
                case R.id.color_gamma_reduce /* 2131230822 */:
                    f.this.p.setProgress(f.this.p.getProgress() - 1 < 0 ? 0 : f.this.p.getProgress() - 1);
                    f.this.d = f.this.p.getProgress();
                    f.this.t.setText("GAMMA:" + f.this.d);
                    f.this.e.write(40730, 1, 0, "rwrite /sys/class/disp/disp/attr/gamma " + f.this.d);
                    f.this.am = f.this.d;
                    if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                        com.dofun.carsetting.f.b.a(f.this.ai, f.this.aj, f.this.ak, f.this.al, f.this.am);
                    }
                    break;
                case R.id.color_greed_add /* 2131230824 */:
                    f.this.o.setProgress(f.this.o.getProgress() + 1 > f.this.o.getMax() ? f.this.o.getMax() : f.this.o.getProgress() + 1);
                    break;
                case R.id.color_greed_reduce /* 2131230825 */:
                    f.this.o.setProgress(f.this.o.getProgress() - 1 >= 0 ? f.this.o.getProgress() - 1 : 0);
                    break;
                case R.id.color_red_add /* 2131230827 */:
                    f.this.m.setProgress(f.this.m.getProgress() + 1 > f.this.m.getMax() ? f.this.m.getMax() : f.this.m.getProgress() + 1);
                    break;
                case R.id.color_red_reduce /* 2131230828 */:
                    f.this.m.setProgress(f.this.m.getProgress() - 1 >= 0 ? f.this.m.getProgress() - 1 : 0);
                    break;
                case R.id.color_saturation_add /* 2131230830 */:
                    f.this.aa.setProgress(f.this.aa.getProgress() + 1 > f.this.aa.getMax() ? f.this.aa.getMax() : f.this.aa.getProgress() + 1);
                    break;
                case R.id.color_saturation_reduce /* 2131230831 */:
                    f.this.aa.setProgress(f.this.aa.getProgress() - 1 >= 0 ? f.this.aa.getProgress() - 1 : 0);
                    break;
                case R.id.iv_back /* 2131230953 */:
                    if (f.this.c != f.this.d && !com.dofun.carsetting.f.b.F() && !com.dofun.carsetting.f.b.G()) {
                        f.this.f();
                    } else {
                        ((MainActivity) f.this.getActivity()).c();
                    }
                    break;
            }
        }
    }

    /* JADX INFO: compiled from: ColorDialogFragment.java */
    class a extends PagerAdapter {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        Context f270a;

        @Override // android.support.v4.view.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        private a(Context context) {
            this.f270a = context;
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return f.this.f266a.size();
        }

        @Override // android.support.v4.view.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView(f.this.f266a.get(i));
        }

        @Override // android.support.v4.view.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            viewGroup.addView(f.this.f266a.get(i), 0);
            return f.this.f266a.get(i);
        }
    }
}
