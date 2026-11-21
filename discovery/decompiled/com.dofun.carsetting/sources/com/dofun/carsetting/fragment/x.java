package com.dofun.carsetting.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.ui.MainActivity;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: SimFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class x extends ad<com.dofun.carsetting.presenter.l> implements View.OnClickListener, com.dofun.carsetting.g.r {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private LinearLayout f380a;
    private LinearLayout b;
    private TextView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView k;
    private TextView l;
    private TextView m;
    private TextView n;
    private TextView o;
    private TextView p;
    private TextView q;
    private LinearLayout r;
    private SubscriptionInfo s;
    private SubscriptionInfo t;
    private TelephonyManager y;
    private int u = 1;
    private int v = 1;
    private String w = "";
    private String x = "";
    private BroadcastReceiver z = new BroadcastReceiver() { // from class: com.dofun.carsetting.fragment.x.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction() != null && intent.getAction().equals("dofun.intent.receive.topway.card") && intent.getIntExtra("DoFunCard", 0) == 1) {
                    com.dofun.carsetting.f.b.g(MyApp.a()).intValue();
                }
            } catch (Exception unused) {
                ((com.dofun.carsetting.presenter.l) x.this.h).c(x.this.w);
            }
        }
    };

    @Override // com.dofun.carsetting.g.r
    public void a(boolean z) {
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onAttach(Context context) {
        this.i = context;
        super.onAttach(context);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActivity().registerReceiver(this.z, new IntentFilter("dofun.intent.receive.topway.card"));
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_sim, (ViewGroup) null);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ((com.dofun.carsetting.presenter.l) this.h).a("AboutFragment");
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        this.w = com.dofun.carsetting.f.b.d(getActivity());
        if (TextUtils.isEmpty(this.w) || this.w.length() <= 10) {
            return;
        }
        getActivity().sendBroadcast(new Intent("topway.intent.receive.dofun.card.info"));
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        b();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        try {
            getActivity().unregisterReceiver(this.z);
        } catch (Exception e) {
            com.dofun.carsetting.f.g.a().a("AboutFragment", e.getMessage());
        }
        if (this.h != 0) {
            ((com.dofun.carsetting.presenter.l) this.h).b("AboutFragment");
        }
        super.onDestroy();
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.l d() {
        return new com.dofun.carsetting.presenter.l(this.i);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.f380a = (LinearLayout) view.findViewById(R.id.ll_sim1);
        this.c = (TextView) view.findViewById(R.id.ll_sim1_text);
        this.e = (TextView) view.findViewById(R.id.tv_operator_sim1);
        this.g = (TextView) view.findViewById(R.id.tv_signal_strength_sim1);
        this.l = (TextView) view.findViewById(R.id.tv_mobile_type_sim1);
        this.n = (TextView) view.findViewById(R.id.tv_service_stat_sim1);
        this.p = (TextView) view.findViewById(R.id.tv_mobile_stat_sim1);
        this.b = (LinearLayout) view.findViewById(R.id.ll_sim2);
        this.d = (TextView) view.findViewById(R.id.ll_sim2_text);
        this.f = (TextView) view.findViewById(R.id.tv_operator_sim2);
        this.k = (TextView) view.findViewById(R.id.tv_signal_strength_sim2);
        this.m = (TextView) view.findViewById(R.id.tv_mobile_type_sim2);
        this.o = (TextView) view.findViewById(R.id.tv_service_stat_sim2);
        this.q = (TextView) view.findViewById(R.id.tv_mobile_stat_sim2);
        if (this.s != null) {
            this.f380a.setVisibility(0);
        }
        if (this.t != null) {
            this.b.setVisibility(0);
        }
        this.r = (LinearLayout) view.findViewById(R.id.iv_back);
        this.r.setOnClickListener(this);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        this.w = com.dofun.carsetting.f.b.d(getActivity());
        if (!TextUtils.isEmpty(this.w) && this.w.length() > 10) {
            getActivity().sendBroadcast(new Intent("topway.intent.receive.dofun.card.info"));
            if (com.dofun.carsetting.f.k.b(getActivity(), "isDoFunCard", 0) != 1) {
                getActivity().sendBroadcast(new Intent("topway.intent.receive.dofun.card.info"));
            }
        }
        if (this.s != null) {
            this.e.setText(String.format("%s%s", getResources().getString(R.string.about_operator_t), com.dofun.carsetting.f.b.a(getActivity(), this.s)));
        }
        if (this.t != null) {
            this.f.setText(String.format("%s%s", getResources().getString(R.string.about_operator_t), com.dofun.carsetting.f.b.a(getActivity(), this.t)));
        }
        c();
    }

    @Override // com.dofun.carsetting.g.r
    public void a(SubscriptionInfo subscriptionInfo, SubscriptionInfo subscriptionInfo2) {
        this.s = subscriptionInfo;
        this.t = subscriptionInfo2;
    }

    @Override // com.dofun.carsetting.g.r
    public void a(int i, int i2, String str) {
        String string = EnvironmentCompat.MEDIA_UNKNOWN;
        switch (i2) {
            case 0:
                string = this.i.getResources().getString(R.string.radioInfo_data_disconnected);
                break;
            case 1:
                string = this.i.getResources().getString(R.string.radioInfo_data_connecting);
                break;
            case 2:
                string = this.i.getResources().getString(R.string.radioInfo_data_connected);
                break;
            case 3:
                string = this.i.getResources().getString(R.string.radioInfo_data_suspended);
                break;
        }
        if (this.s != null && i == this.s.getSubscriptionId()) {
            this.p.setText(String.format("%s%s", getResources().getString(R.string.about_mobile_stat_t), string));
            this.l.setText(String.format("%s%s", getResources().getString(R.string.about_mobile_type_t), str));
        }
        if (this.t != null && i == this.t.getSubscriptionId()) {
            this.q.setText(String.format("%s%s", getResources().getString(R.string.about_mobile_stat_t), string));
            this.m.setText(String.format("%s%s", getResources().getString(R.string.about_mobile_type_t), str));
        }
        c();
    }

    @Override // com.dofun.carsetting.g.r
    public void a(int i, SignalStrength signalStrength) {
        try {
            Method declaredMethod = signalStrength.getClass().getDeclaredMethod("getDbm", new Class[0]);
            Method declaredMethod2 = signalStrength.getClass().getDeclaredMethod("getAsuLevel", new Class[0]);
            int iIntValue = ((Integer) declaredMethod.invoke(signalStrength, new Object[0])).intValue();
            int iIntValue2 = ((Integer) declaredMethod2.invoke(signalStrength, new Object[0])).intValue();
            com.dofun.carsetting.f.g.a().a("tags", "signalDbm===" + iIntValue + "   signalAsu====" + iIntValue2);
            if (this.u == 0) {
                this.g.setText(getResources().getString(R.string.about_signal_strength_nosignal));
            } else if (this.s != null && i == this.s.getSubscriptionId()) {
                this.x = a(iIntValue);
                this.g.setText(getResources().getString(R.string.about_signal_strength_t) + this.x + " (" + iIntValue + "dBm" + iIntValue2 + "asu)");
            }
            if (this.v == 0) {
                this.k.setText(getResources().getString(R.string.about_signal_strength_nosignal));
                return;
            }
            if (this.t == null || i != this.t.getSubscriptionId()) {
                return;
            }
            this.x = a(iIntValue);
            this.k.setText(getResources().getString(R.string.about_signal_strength_t) + this.x + " (" + iIntValue + "dBm" + iIntValue2 + "asu)");
        } catch (Exception e) {
            com.dofun.carsetting.f.g.a().c("AboutFragment", e.getLocalizedMessage());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.dofun.carsetting.g.r
    public void a(int i, ServiceState serviceState) {
        String string;
        String string2 = this.i.getResources().getString(R.string.about_service_stat_t);
        int i2 = 0;
        switch (serviceState.getState()) {
            case 0:
                string2 = string2 + this.i.getResources().getString(R.string.radioInfo_service_in);
                i2 = 1;
                break;
            case 1:
                string2 = string2 + this.i.getResources().getString(R.string.radioInfo_service_out);
                break;
            case 2:
                i2 = 1;
                string2 = string2 + this.i.getResources().getString(R.string.radioInfo_service_out);
                break;
            case 3:
                string2 = string2 + this.i.getResources().getString(R.string.radioInfo_service_off);
                break;
            default:
                i2 = 1;
                break;
        }
        try {
            if (serviceState.getOperatorAlphaLong() != null && !serviceState.getOperatorAlphaLong().equals("")) {
                string = serviceState.getOperatorAlphaLong();
            } else {
                string = getActivity().getResources().getString(R.string.device_info_default);
            }
            com.dofun.carsetting.f.g.a().c("AboutFragment", "onGetServiceStatChanged() oprator " + string);
            if (this.s != null && i == this.s.getSubscriptionId()) {
                this.e.setText(getResources().getString(R.string.about_operator_t) + com.dofun.carsetting.f.b.a(getActivity(), this.s));
                this.n.setText(string2);
                this.u = i2;
                if (i2 == 0) {
                    this.g.setText(getResources().getString(R.string.about_signal_strength_nosignal));
                }
            }
            if (this.t == null || i != this.t.getSubscriptionId()) {
                return;
            }
            this.f.setText(getResources().getString(R.string.about_operator_t) + com.dofun.carsetting.f.b.a(getActivity(), this.t));
            this.o.setText(string2);
            this.v = i2;
            if (i2 == 0) {
                this.k.setText(getResources().getString(R.string.about_signal_strength_nosignal));
            }
        } catch (Exception e) {
            com.dofun.carsetting.f.g.a().c("AboutFragment", e.getLocalizedMessage());
        }
    }

    private String a(int i) {
        if (i >= -70) {
            return getResources().getString(R.string.about_signal_strength_h);
        }
        if (i >= -95) {
            return getResources().getString(R.string.about_signal_strength_c);
        }
        return getResources().getString(R.string.about_signal_strength_l);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.iv_back) {
            return;
        }
        ((MainActivity) getActivity()).c();
    }

    private void c() {
        if (this.i != null) {
            this.y = (TelephonyManager) this.i.getSystemService("phone");
        }
        if (this.y == null) {
            return;
        }
        TelephonyManager telephonyManager = this.y;
        boolean z = !TextUtils.equals(TelephonyManager.getTelephonyProperty(0, "persist.radio.sim_enabled", "1"), "0");
        TelephonyManager telephonyManager2 = this.y;
        a(this.s != null, z, this.t != null, !TextUtils.equals(TelephonyManager.getTelephonyProperty(1, "persist.radio.sim_enabled", "1"), "0"));
    }

    public void a(boolean z, boolean z2, boolean z3, boolean z4) {
        this.f380a.setVisibility((z && z2) ? 0 : 8);
        this.c.setVisibility((z && z2) ? 0 : 8);
        this.b.setVisibility((z3 && z4) ? 0 : 8);
        this.d.setVisibility((z3 && z4) ? 0 : 8);
    }
}
