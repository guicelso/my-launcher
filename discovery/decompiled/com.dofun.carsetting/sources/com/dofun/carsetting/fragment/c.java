package com.dofun.carsetting.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.dofun.carsetting.R;
import com.dofun.carsetting.adapter.AutoPlayAppAdapter;
import com.dofun.carsetting.bean.AppInfo;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.DividerItemDecoration;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: AutoPlayAppFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class c extends ad<com.dofun.carsetting.presenter.a> implements View.OnClickListener, com.dofun.carsetting.g.a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private RecyclerView f258a;
    private AutoPlayAppAdapter b;
    private LinearLayout c;
    private LinearLayout d;
    private com.dofun.carsetting.f.n k;
    private List<AppInfo> e = new ArrayList();
    private List<String> f = new ArrayList();
    private PackageManager g = null;
    private Handler l = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.fragment.c.3
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case MotionEventCompat.ACTION_POINTER_INDEX_MASK /* 65280 */:
                case 65281:
                    c.this.f();
                    break;
                case 65282:
                    c.this.g();
                    break;
            }
            return true;
        }
    });

    @Override // com.dofun.carsetting.g.a
    public void a(List<AppInfo> list) {
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
        return new com.dofun.carsetting.presenter.a(this.i);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.f258a = (RecyclerView) view.findViewById(R.id.rv_app_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        this.f258a.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(1);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.i, 1);
        dividerItemDecoration.a(this.i.getResources().getDrawable(R.drawable.tl_list_divider2));
        this.f258a.addItemDecoration(dividerItemDecoration);
        this.b = new AutoPlayAppAdapter();
        this.b.a(true);
        this.f258a.setAdapter(this.b);
        this.b.a(new AutoPlayAppAdapter.b() { // from class: com.dofun.carsetting.fragment.c.1
            @Override // com.dofun.carsetting.adapter.AutoPlayAppAdapter.b
            public void a(AppInfo appInfo, int i) {
                appInfo.selected = !appInfo.selected;
                String packageName = appInfo.componentName.getPackageName();
                if (!TextUtils.isEmpty(packageName)) {
                    if (c.this.f.contains(packageName)) {
                        c.this.f.remove(packageName);
                    } else {
                        c.this.f.add(packageName);
                    }
                    c.this.k.a(c.this.f);
                    c.this.l.sendEmptyMessageDelayed(65282, 100L);
                }
                c.this.b.notifyDataSetChanged();
            }
        });
        this.c = (LinearLayout) view.findViewById(R.id.iv_back);
        this.c.setOnClickListener(this);
        this.d = (LinearLayout) view.findViewById(R.id.ll_phone_connect_tip);
        this.d.setVisibility(8);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        c();
        this.k = new com.dofun.carsetting.f.n(new com.dofun.carsetting.f.n.a() { // from class: com.dofun.carsetting.fragment.c.2
            @Override // com.dofun.carsetting.f.n.a
            public void a(List<String> list) {
                c.this.f.clear();
                c.this.f = list;
                c.this.l.sendEmptyMessage(65281);
            }
        });
        this.k.a();
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

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.iv_back) {
            return;
        }
        ((MainActivity) getActivity()).c();
    }

    public void c() {
        this.g = this.i.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        this.e.clear();
        for (ResolveInfo resolveInfo : this.g.queryIntentActivities(intent, 0)) {
            AppInfo appInfo = new AppInfo(resolveInfo, (String) null, (Drawable) null);
            appInfo.appName = (String) resolveInfo.loadLabel(this.g);
            appInfo.appIcon = resolveInfo.loadIcon(this.g);
            appInfo.componentName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            if (!resolveInfo.activityInfo.packageName.equals("com.tw.music")) {
                this.e.add(appInfo);
            }
        }
        this.b.a(this.e);
        this.b.notifyDataSetChanged();
        this.l.sendEmptyMessage(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        for (int i = 0; i < this.e.size(); i++) {
            AppInfo appInfo = this.e.get(i);
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                if (appInfo != null && TextUtils.equals(appInfo.componentName.getPackageName(), this.f.get(i2))) {
                    appInfo.selected = true;
                    break;
                }
            }
        }
        this.b.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        Intent intent = new Intent("com.android.server.am.REC");
        intent.putExtra("cmd", 301);
        this.i.sendBroadcast(intent);
    }
}
