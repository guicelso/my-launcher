package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class G implements InterfaceC0019t, InterfaceC0018s {
    private Notification.Builder mq;
    private List mr = new ArrayList();
    private final Bundle ms;

    public G(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, int i4, CharSequence charSequence4, boolean z3, Bundle bundle, String str, boolean z4, String str2) {
        this.mq = new Notification.Builder(context).setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent2, (notification.flags & 128) != 0).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(z2).setPriority(i4).setProgress(i2, i3, z);
        this.ms = new Bundle();
        if (bundle != null) {
            this.ms.putAll(bundle);
        }
        if (z3) {
            this.ms.putBoolean("android.support.localOnly", true);
        }
        if (str != null) {
            this.ms.putString("android.support.groupKey", str);
            if (z4) {
                this.ms.putBoolean("android.support.isGroupSummary", true);
            } else {
                this.ms.putBoolean("android.support.useSideChannel", true);
            }
        }
        if (str2 != null) {
            this.ms.putString("android.support.sortKey", str2);
        }
    }

    @Override // android.support.v4.app.InterfaceC0018s
    public void qP(v vVar) {
        this.mr.add(B.rc(this.mq, vVar));
    }

    @Override // android.support.v4.app.InterfaceC0019t
    public Notification qQ() {
        Notification notificationBuild = this.mq.build();
        Bundle bundleRb = B.rb(notificationBuild);
        Bundle bundle = new Bundle(this.ms);
        for (String str : this.ms.keySet()) {
            if (bundleRb.containsKey(str)) {
                bundle.remove(str);
            }
        }
        bundleRb.putAll(bundle);
        SparseArray<? extends Parcelable> sparseArrayRa = B.ra(this.mr);
        if (sparseArrayRa != null) {
            B.rb(notificationBuild).putSparseParcelableArray("android.support.actionExtras", sparseArrayRa);
        }
        return notificationBuild;
    }

    @Override // android.support.v4.app.InterfaceC0019t
    public Notification.Builder qR() {
        return this.mq;
    }
}
