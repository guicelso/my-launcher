package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import java.util.ArrayList;

/* JADX INFO: renamed from: android.support.v4.app.m, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0012m {
    public int lB;
    public ArrayList lC;
    int lD;
    int lE;
    boolean lF;
    int lG;
    Notification lH;
    String lJ;
    public AbstractC0013n lK;
    public CharSequence lL;
    RemoteViews lM;
    public boolean lN;
    String ln;
    public CharSequence lp;
    PendingIntent lq;
    public CharSequence lr;
    public CharSequence ls;
    public Context lt;
    Bundle lu;
    PendingIntent lv;
    String lw;
    boolean lx;
    public Bitmap ly;
    boolean lI = true;
    public ArrayList lm = new ArrayList();
    boolean lz = false;
    int lo = 0;
    int lO = 0;
    public Notification lA = new Notification();

    public C0012m(Context context) {
        this.lt = context;
        this.lA.when = System.currentTimeMillis();
        this.lA.audioStreamType = -1;
        this.lD = 0;
        this.lC = new ArrayList();
    }

    protected static CharSequence qC(CharSequence charSequence) {
        return (charSequence != null && charSequence.length() > 5120) ? charSequence.subSequence(0, 5120) : charSequence;
    }

    private void qI(int i, boolean z) {
        if (z) {
            this.lA.flags |= i;
        } else {
            this.lA.flags &= ~i;
        }
    }

    public Notification qA() {
        return C0001b.ll.qy(this, qB());
    }

    protected C0003d qB() {
        return new C0003d();
    }

    public C0012m qD(boolean z) {
        qI(16, z);
        return this;
    }

    public C0012m qE(RemoteViews remoteViews) {
        this.lA.contentView = remoteViews;
        return this;
    }

    public C0012m qF(PendingIntent pendingIntent) {
        this.lq = pendingIntent;
        return this;
    }

    public C0012m qG(CharSequence charSequence) {
        this.lr = qC(charSequence);
        return this;
    }

    public C0012m qH(CharSequence charSequence) {
        this.ls = qC(charSequence);
        return this;
    }

    public C0012m qJ(int i) {
        this.lA.icon = i;
        return this;
    }
}
