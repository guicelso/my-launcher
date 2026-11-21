package a.b.d.a;

import android.app.Notification;
import android.app.RemoteInput;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class f implements c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Notification.Builder f48a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final e f49b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public RemoteViews f50c;
    public RemoteViews d;
    public final List<Bundle> e = new ArrayList();
    public final Bundle f = new Bundle();
    public int g;
    public RemoteViews h;

    public f(e eVar) {
        ArrayList<String> arrayList;
        Bundle bundle;
        String str;
        this.f49b = eVar;
        this.f48a = Build.VERSION.SDK_INT >= 26 ? new Notification.Builder(eVar.f45a, eVar.H) : new Notification.Builder(eVar.f45a);
        Notification notification = eVar.M;
        this.f48a.setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, eVar.h).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(eVar.d).setContentText(eVar.e).setContentInfo(eVar.j).setContentIntent(eVar.f).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(eVar.g, (notification.flags & 128) != 0).setLargeIcon(eVar.i).setNumber(eVar.k).setProgress(eVar.q, eVar.r, eVar.s);
        if (Build.VERSION.SDK_INT < 21) {
            this.f48a.setSound(notification.sound, notification.audioStreamType);
        }
        int i = Build.VERSION.SDK_INT;
        this.f48a.setSubText(eVar.o).setUsesChronometer(eVar.n).setPriority(eVar.l);
        Iterator<d> it = eVar.f46b.iterator();
        while (it.hasNext()) {
            a(it.next());
        }
        Bundle bundle2 = eVar.A;
        if (bundle2 != null) {
            this.f.putAll(bundle2);
        }
        if (Build.VERSION.SDK_INT < 20) {
            if (eVar.w) {
                this.f.putBoolean("android.support.localOnly", true);
            }
            String str2 = eVar.t;
            if (str2 != null) {
                this.f.putString("android.support.groupKey", str2);
                if (eVar.u) {
                    bundle = this.f;
                    str = "android.support.isGroupSummary";
                } else {
                    bundle = this.f;
                    str = "android.support.useSideChannel";
                }
                bundle.putBoolean(str, true);
            }
            String str3 = eVar.v;
            if (str3 != null) {
                this.f.putString("android.support.sortKey", str3);
            }
        }
        this.f50c = eVar.E;
        this.d = eVar.F;
        int i2 = Build.VERSION.SDK_INT;
        this.f48a.setShowWhen(eVar.m);
        if (Build.VERSION.SDK_INT < 21 && (arrayList = eVar.N) != null && !arrayList.isEmpty()) {
            Bundle bundle3 = this.f;
            ArrayList<String> arrayList2 = eVar.N;
            bundle3.putStringArray("android.people", (String[]) arrayList2.toArray(new String[arrayList2.size()]));
        }
        if (Build.VERSION.SDK_INT >= 20) {
            this.f48a.setLocalOnly(eVar.w).setGroup(eVar.t).setGroupSummary(eVar.u).setSortKey(eVar.v);
            this.g = eVar.L;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.f48a.setCategory(eVar.z).setColor(eVar.B).setVisibility(eVar.C).setPublicVersion(eVar.D).setSound(notification.sound, notification.audioAttributes);
            Iterator<String> it2 = eVar.N.iterator();
            while (it2.hasNext()) {
                this.f48a.addPerson(it2.next());
            }
            this.h = eVar.G;
            if (eVar.f47c.size() > 0) {
                if (eVar.A == null) {
                    eVar.A = new Bundle();
                }
                Bundle bundle4 = eVar.A.getBundle("android.car.EXTENSIONS");
                bundle4 = bundle4 == null ? new Bundle() : bundle4;
                Bundle bundle5 = new Bundle();
                for (int i3 = 0; i3 < eVar.f47c.size(); i3++) {
                    bundle5.putBundle(Integer.toString(i3), g.a(eVar.f47c.get(i3)));
                }
                bundle4.putBundle("invisible_actions", bundle5);
                if (eVar.A == null) {
                    eVar.A = new Bundle();
                }
                eVar.A.putBundle("android.car.EXTENSIONS", bundle4);
                this.f.putBundle("android.car.EXTENSIONS", bundle4);
            }
        }
        if (Build.VERSION.SDK_INT >= 24) {
            this.f48a.setExtras(eVar.A).setRemoteInputHistory(eVar.p);
            RemoteViews remoteViews = eVar.E;
            if (remoteViews != null) {
                this.f48a.setCustomContentView(remoteViews);
            }
            RemoteViews remoteViews2 = eVar.F;
            if (remoteViews2 != null) {
                this.f48a.setCustomBigContentView(remoteViews2);
            }
            RemoteViews remoteViews3 = eVar.G;
            if (remoteViews3 != null) {
                this.f48a.setCustomHeadsUpContentView(remoteViews3);
            }
        }
        if (Build.VERSION.SDK_INT >= 26) {
            this.f48a.setBadgeIconType(eVar.I).setShortcutId(eVar.J).setTimeoutAfter(eVar.K).setGroupAlertBehavior(eVar.L);
            if (eVar.y) {
                this.f48a.setColorized(eVar.x);
            }
            if (TextUtils.isEmpty(eVar.H)) {
                return;
            }
            this.f48a.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
        }
    }

    public final void a(d dVar) {
        if (Build.VERSION.SDK_INT < 20) {
            this.e.add(g.a(this.f48a, dVar));
            return;
        }
        Notification.Action.Builder builder = new Notification.Action.Builder(dVar.g, dVar.h, dVar.i);
        h[] hVarArr = dVar.f43b;
        if (hVarArr != null) {
            RemoteInput[] remoteInputArr = new RemoteInput[hVarArr.length];
            if (hVarArr.length > 0) {
                h hVar = hVarArr[0];
                throw null;
            }
            for (RemoteInput remoteInput : remoteInputArr) {
                builder.addRemoteInput(remoteInput);
            }
        }
        Bundle bundle = dVar.f42a;
        Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
        bundle2.putBoolean("android.support.allowGeneratedReplies", dVar.d);
        if (Build.VERSION.SDK_INT >= 24) {
            builder.setAllowGeneratedReplies(dVar.d);
        }
        bundle2.putInt("android.support.action.semanticAction", dVar.f);
        if (Build.VERSION.SDK_INT >= 28) {
            builder.setSemanticAction(dVar.f);
        }
        bundle2.putBoolean("android.support.action.showsUserInterface", dVar.e);
        builder.addExtras(bundle2);
        this.f48a.addAction(builder.build());
    }

    public final void a(Notification notification) {
        notification.sound = null;
        notification.vibrate = null;
        notification.defaults &= -2;
        notification.defaults &= -3;
    }
}
