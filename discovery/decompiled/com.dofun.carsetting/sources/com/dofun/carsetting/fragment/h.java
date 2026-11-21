package com.dofun.carsetting.fragment;

import android.content.ComponentName;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.SystemProperties;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.dofun.carsetting.R;
import com.dofun.carsetting.adapter.AppSelectAdapter;
import com.dofun.carsetting.bean.AppInfo;
import com.dofun.carsetting.presenter.VoicePresenter;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.DividerItemDecoration;
import java.util.List;

/* JADX INFO: compiled from: DefaultAppSelectFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class h extends ad<com.dofun.carsetting.presenter.a> implements View.OnClickListener, com.dofun.carsetting.g.a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private RecyclerView f277a;
    private AppSelectAdapter b;
    private LinearLayout c;
    private String d = "";
    private String e;
    private String f;
    private a g;
    private LinearLayout k;
    private VoicePresenter l;

    /* JADX INFO: compiled from: DefaultAppSelectFragment.java */
    public interface a {
        void a(String str, AppInfo appInfo);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_app_list, (ViewGroup) null);
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.a d() {
        this.l = new VoicePresenter(this.i);
        return new com.dofun.carsetting.presenter.a(this.i);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.f277a = (RecyclerView) view.findViewById(R.id.rv_app_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        this.f277a.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(1);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.i, 1);
        dividerItemDecoration.a(this.i.getResources().getDrawable(R.drawable.tl_list_divider2));
        this.f277a.addItemDecoration(dividerItemDecoration);
        this.b = new AppSelectAdapter();
        this.b.a(true);
        this.f277a.setAdapter(this.b);
        this.b.a(new AppSelectAdapter.b() { // from class: com.dofun.carsetting.fragment.h.1
            @Override // com.dofun.carsetting.adapter.AppSelectAdapter.b
            public void a(AppInfo appInfo, int i) {
                h.this.b.f102a = appInfo.componentName;
                h.this.b.notifyDataSetChanged();
                if (h.this.g != null) {
                    h.this.g.a(h.this.d, appInfo);
                }
                switch (h.this.d) {
                    case "CONNECT":
                        ((com.dofun.carsetting.presenter.a) h.this.h).a(appInfo.componentName.getPackageName());
                        break;
                    case "NAVI":
                        if (h.this.l != null && h.this.l.d != null) {
                            h.this.l.d.mapSoftware = appInfo.componentName.getPackageName();
                            h.this.l.d();
                            break;
                        }
                        break;
                    case "MUSIC":
                        if (h.this.l != null && h.this.l.d != null) {
                            h.this.l.d.musicSoftware = appInfo.componentName.getPackageName();
                            h.this.l.d();
                            break;
                        }
                        break;
                    case "RADIO":
                        if (h.this.l != null && h.this.l.d != null) {
                            h.this.l.d.radioSoftware = appInfo.componentName.getPackageName();
                            h.this.l.d();
                            break;
                        }
                        break;
                }
            }
        });
        this.c = (LinearLayout) view.findViewById(R.id.iv_back);
        this.c.setOnClickListener(this);
        this.k = (LinearLayout) view.findViewById(R.id.ll_phone_connect_tip);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        ResolveInfo resolveInfoH;
        ResolveInfo resolveInfoN;
        this.l.c();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.d = arguments.getString("type");
            this.e = arguments.getString("current_type_app_package");
            this.f = arguments.getString("current_type_app_class");
            if (this.e != null && this.f != null) {
                this.b.f102a = new ComponentName(this.e, this.f);
            }
        }
        ((com.dofun.carsetting.presenter.a) this.h).b(this.d);
        this.k.setVisibility(8);
        switch (this.d) {
            case "CONNECT":
                this.k.setVisibility(0);
                this.b.b(false);
                AppInfo appInfoH = ((com.dofun.carsetting.presenter.a) this.h).h();
                if (appInfoH != null) {
                    this.e = appInfoH.componentName.getPackageName();
                    this.f = appInfoH.componentName.getClassName();
                    this.b.f102a = appInfoH.componentName;
                    SystemProperties.set("persist.phone_connect_app", this.e);
                }
                this.b.notifyDataSetChanged();
                break;
            case "NAVI":
                if (TextUtils.isEmpty(this.e) && (resolveInfoH = this.l.h()) != null && resolveInfoH.activityInfo != null) {
                    this.e = resolveInfoH.activityInfo.packageName;
                    this.f = resolveInfoH.activityInfo.name;
                    if (this.e != null && this.f != null) {
                        this.b.f102a = new ComponentName(this.e, this.f);
                    }
                }
                this.b.b(false);
                break;
            case "MUSIC":
                Log.d("DefaultAppSelectFragment", "TYPE_MUSIC:currentTypeAppPackage:" + this.e);
                if (TextUtils.isEmpty(this.e)) {
                    ResolveInfo resolveInfoM = this.l.m();
                    Log.d("DefaultAppSelectFragment", "TYPE_MUSIC:resolveInfo:" + resolveInfoM);
                    if (resolveInfoM != null && resolveInfoM.activityInfo != null) {
                        this.e = resolveInfoM.activityInfo.packageName;
                        this.f = resolveInfoM.activityInfo.name;
                        if (this.e != null && this.f != null) {
                            this.b.f102a = new ComponentName(this.e, this.f);
                        }
                    }
                }
                this.b.b(false);
                break;
            case "RADIO":
                if (TextUtils.isEmpty(this.e) && (resolveInfoN = this.l.n()) != null && resolveInfoN.activityInfo != null) {
                    this.e = resolveInfoN.activityInfo.packageName;
                    this.f = resolveInfoN.activityInfo.name;
                    if (this.e != null && this.f != null) {
                        this.b.f102a = new ComponentName(this.e, this.f);
                    }
                }
                this.b.b(false);
                break;
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.dofun.carsetting.g.a
    public void a(List<AppInfo> list) {
        this.b.a(list);
        this.b.notifyDataSetChanged();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.iv_back) {
            return;
        }
        ((MainActivity) getActivity()).c();
    }

    public void a(a aVar) {
        this.g = aVar;
    }
}
