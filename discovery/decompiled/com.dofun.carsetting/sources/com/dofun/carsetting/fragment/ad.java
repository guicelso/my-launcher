package com.dofun.carsetting.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.dofun.carsetting.bean.ConfigBean;
import com.dofun.carsetting.presenter.b;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.TwPreference;
import java.util.List;

/* JADX INFO: compiled from: XTFragment.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class ad<P extends com.dofun.carsetting.presenter.b> extends Fragment implements com.dofun.carsetting.g.c {
    public P h;
    protected Context i;
    protected com.dofun.carsetting.b.a j;

    public abstract void a(View view);

    public abstract void b();

    public abstract P d();

    public void e() {
    }

    @Override // android.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.i = context.getApplicationContext();
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.j = com.dofun.carsetting.b.a.a();
        a(view);
        if (this.h == null) {
            this.h = (P) d();
            if (this.h != null) {
                this.h.a(this);
            }
        }
        b();
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        b(((MainActivity) getActivity()).c);
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        if (this.h != null) {
            this.h.k();
            this.h.l();
        }
        super.onDestroy();
    }

    public void b(List<ConfigBean> list) {
        if (getActivity() == null || list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ConfigBean configBean = list.get(i);
            View viewFindViewById = getView().findViewById(getResources().getIdentifier(configBean.mId, "id", getActivity().getPackageName()));
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(configBean.visibility.equals("1") ? 0 : 8);
            }
            if (viewFindViewById instanceof TwPreference) {
                TwPreference twPreference = (TwPreference) viewFindViewById;
                twPreference.setNeedPassword(configBean.isNeedPwd);
                twPreference.setPassword(configBean.strPwd);
            }
        }
    }

    @Override // android.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (z) {
            return;
        }
        b(((MainActivity) getActivity()).c);
    }
}
