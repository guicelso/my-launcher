package com.dofun.carsetting.presenter;

import android.content.Context;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import com.dofun.carsetting.g.r;

/* JADX INFO: compiled from: SimInfoPresenter.java */
/* JADX INFO: loaded from: classes.dex */
public class l extends b<r, com.dofun.carsetting.d.l> implements r {
    public l(Context context) {
        super(context);
        ((com.dofun.carsetting.d.l) this.b).a(context);
    }

    @Override // com.dofun.carsetting.presenter.b
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.d.l i() {
        return com.dofun.carsetting.d.l.a();
    }

    public void a(String str) {
        com.dofun.carsetting.d.l.a().a(str, this);
    }

    public void b(String str) {
        ((com.dofun.carsetting.d.l) this.b).a(str);
    }

    public void c(String str) {
        if (this.b != 0) {
            ((com.dofun.carsetting.d.l) this.b).b(str);
        }
    }

    @Override // com.dofun.carsetting.g.r
    public void a(SubscriptionInfo subscriptionInfo, SubscriptionInfo subscriptionInfo2) {
        if (j() != null) {
            j().a(subscriptionInfo, subscriptionInfo2);
        }
    }

    @Override // com.dofun.carsetting.g.r
    public void a(boolean z) {
        if (j() != null) {
            j().a(z);
        }
    }

    @Override // com.dofun.carsetting.g.r
    public void a(int i, int i2, String str) {
        if (j() != null) {
            j().a(i, i2, str);
        }
    }

    @Override // com.dofun.carsetting.g.r
    public void a(int i, SignalStrength signalStrength) {
        if (j() != null) {
            j().a(i, signalStrength);
        }
    }

    @Override // com.dofun.carsetting.g.r
    public void a(int i, ServiceState serviceState) {
        if (j() != null) {
            j().a(i, serviceState);
        }
    }
}
