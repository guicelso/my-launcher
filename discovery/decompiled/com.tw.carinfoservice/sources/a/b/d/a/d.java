package a.b.d.a;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class d {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Notification.Builder f45a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final c f46b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final List<Bundle> f47c;
    public final Bundle d;

    public d(c cVar) {
        ArrayList<String> arrayList;
        int i = Build.VERSION.SDK_INT;
        this.f47c = new ArrayList();
        this.d = new Bundle();
        this.f46b = cVar;
        Context context = cVar.f42a;
        Notification.Builder builder = i >= 26 ? new Notification.Builder(context, cVar.g) : new Notification.Builder(context);
        this.f45a = builder;
        Notification notification = cVar.h;
        builder.setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, null).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(null).setContentText(null).setContentInfo(null).setContentIntent(cVar.d).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(null, (notification.flags & 128) != 0).setLargeIcon((Bitmap) null).setNumber(0).setProgress(0, 0, false);
        if (i < 21) {
            builder.setSound(notification.sound, notification.audioStreamType);
        }
        builder.setSubText(null).setUsesChronometer(false).setPriority(0);
        Iterator<b> it = cVar.f43b.iterator();
        while (it.hasNext()) {
            a(it.next());
        }
        Bundle bundle = cVar.f;
        if (bundle != null) {
            this.d.putAll(bundle);
        }
        this.f45a.setShowWhen(cVar.e);
        if (i < 21 && (arrayList = cVar.i) != null && !arrayList.isEmpty()) {
            Bundle bundle2 = this.d;
            ArrayList<String> arrayList2 = cVar.i;
            bundle2.putStringArray("android.people", (String[]) arrayList2.toArray(new String[arrayList2.size()]));
        }
        if (i >= 20) {
            this.f45a.setLocalOnly(false).setGroup(null).setGroupSummary(false).setSortKey(null);
        }
        if (i >= 21) {
            this.f45a.setCategory(null).setColor(0).setVisibility(0).setPublicVersion(null).setSound(notification.sound, notification.audioAttributes);
            Iterator<String> it2 = cVar.i.iterator();
            while (it2.hasNext()) {
                this.f45a.addPerson(it2.next());
            }
            if (cVar.f44c.size() > 0) {
                if (cVar.f == null) {
                    cVar.f = new Bundle();
                }
                Bundle bundle3 = cVar.f.getBundle("android.car.EXTENSIONS");
                bundle3 = bundle3 == null ? new Bundle() : bundle3;
                Bundle bundle4 = new Bundle();
                for (int i2 = 0; i2 < cVar.f44c.size(); i2++) {
                    String string = Integer.toString(i2);
                    b bVar = cVar.f44c.get(i2);
                    Object obj = e.f48a;
                    Bundle bundle5 = new Bundle();
                    Objects.requireNonNull(bVar);
                    bundle5.putInt("icon", 0);
                    bundle5.putCharSequence("title", null);
                    bundle5.putParcelable("actionIntent", null);
                    Bundle bundle6 = new Bundle();
                    bundle6.putBoolean("android.support.allowGeneratedReplies", false);
                    bundle5.putBundle("extras", bundle6);
                    bundle5.putParcelableArray("remoteInputs", e.b(null));
                    bundle5.putBoolean("showsUserInterface", false);
                    bundle5.putInt("semanticAction", 0);
                    bundle4.putBundle(string, bundle5);
                }
                bundle3.putBundle("invisible_actions", bundle4);
                if (cVar.f == null) {
                    cVar.f = new Bundle();
                }
                cVar.f.putBundle("android.car.EXTENSIONS", bundle3);
                this.d.putBundle("android.car.EXTENSIONS", bundle3);
            }
        }
        if (i >= 24) {
            this.f45a.setExtras(cVar.f).setRemoteInputHistory(null);
        }
        if (i >= 26) {
            this.f45a.setBadgeIconType(0).setShortcutId(null).setTimeoutAfter(0L).setGroupAlertBehavior(0);
            if (TextUtils.isEmpty(cVar.g)) {
                return;
            }
            this.f45a.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
        }
    }

    public final void a(b bVar) {
        int i = Build.VERSION.SDK_INT;
        if (i < 20) {
            List<Bundle> list = this.f47c;
            Notification.Builder builder = this.f45a;
            Object obj = e.f48a;
            Objects.requireNonNull(bVar);
            builder.addAction(0, null, null);
            Bundle bundle = new Bundle((Bundle) null);
            bundle.putBoolean("android.support.allowGeneratedReplies", false);
            list.add(bundle);
            return;
        }
        Objects.requireNonNull(bVar);
        Notification.Action.Builder builder2 = new Notification.Action.Builder(0, (CharSequence) null, (PendingIntent) null);
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("android.support.allowGeneratedReplies", false);
        if (i >= 24) {
            builder2.setAllowGeneratedReplies(false);
        }
        bundle2.putInt("android.support.action.semanticAction", 0);
        if (i >= 28) {
            builder2.setSemanticAction(0);
        }
        bundle2.putBoolean("android.support.action.showsUserInterface", false);
        builder2.addExtras(bundle2);
        this.f45a.addAction(builder2.build());
    }
}
