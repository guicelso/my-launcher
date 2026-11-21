package com.dofun.carsetting.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.dofun.carsetting.R;
import com.dofun.carsetting.adapter.SleepAppSelectAdapter;
import com.dofun.carsetting.bean.AppInfo;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.DividerItemDecoration;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: SleepAppSelectFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class y extends ad<com.dofun.carsetting.presenter.a> implements View.OnClickListener, com.dofun.carsetting.g.a {
    private RecyclerView b;
    private SleepAppSelectAdapter c;
    private LinearLayout d;
    private LinearLayout e;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f382a = "/sdcard/.Tcfg/sleepKillPackage.cfg";
    private List<AppInfo> f = new ArrayList();
    private List<String> g = new ArrayList();

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
        return new com.dofun.carsetting.presenter.a(this.i);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.b = (RecyclerView) view.findViewById(R.id.rv_app_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        this.b.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(1);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.i, 1);
        dividerItemDecoration.a(this.i.getResources().getDrawable(R.drawable.tl_list_divider2));
        this.b.addItemDecoration(dividerItemDecoration);
        this.c = new SleepAppSelectAdapter();
        this.c.a(true);
        this.b.setAdapter(this.c);
        this.c.a(new SleepAppSelectAdapter.b() { // from class: com.dofun.carsetting.fragment.y.1
            @Override // com.dofun.carsetting.adapter.SleepAppSelectAdapter.b
            public void a(AppInfo appInfo, int i) throws Throwable {
                appInfo.selected = !appInfo.selected;
                y.this.c.notifyDataSetChanged();
                ((com.dofun.carsetting.presenter.a) y.this.h).a(y.this.f382a, y.this.f);
            }
        });
        this.d = (LinearLayout) view.findViewById(R.id.iv_back);
        this.d.setOnClickListener(this);
        this.e = (LinearLayout) view.findViewById(R.id.ll_phone_connect_tip);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        getArguments();
        ((com.dofun.carsetting.presenter.a) this.h).c();
        this.e.setVisibility(8);
        Log.d("DefaultAppSelectFragment", "initData: ");
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        Log.d("DefaultAppSelectFragment", "onResume: ");
        this.g = ((com.dofun.carsetting.presenter.a) this.h).d(this.f382a);
        for (int i = 0; i < this.f.size(); i++) {
            for (int i2 = 0; i2 < this.g.size(); i2++) {
                AppInfo appInfo = this.f.get(i);
                if (appInfo.mInfo.activityInfo.packageName.equals(this.g.get(i2))) {
                    appInfo.selected = true;
                }
            }
        }
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
        this.f = list;
        for (int i = 0; i < list.size(); i++) {
            for (int i2 = 0; i2 < this.g.size(); i2++) {
                AppInfo appInfo = list.get(i);
                if (appInfo.mInfo.activityInfo.packageName.equals(this.g.get(i2))) {
                    appInfo.selected = true;
                }
            }
        }
        this.c.a(list);
        this.c.notifyDataSetChanged();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.iv_back) {
            return;
        }
        ((MainActivity) getActivity()).c();
    }
}
