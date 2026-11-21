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
public class J implements InterfaceC0019t, InterfaceC0018s {
    private Notification.Builder mD;
    private List mE = new ArrayList();
    private Bundle mF;

    public J(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, CharSequence charSequence4, boolean z4, ArrayList arrayList, Bundle bundle, String str, boolean z5, String str2) {
        this.mD = new Notification.Builder(context).setWhen(notification.when).setShowWhen(z2).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent2, (notification.flags & 128) != 0).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(z3).setPriority(i4).setProgress(i2, i3, z);
        this.mF = new Bundle();
        if (bundle != null) {
            this.mF.putAll(bundle);
        }
        if (arrayList != null && !arrayList.isEmpty()) {
            this.mF.putStringArray("android.people", (String[]) arrayList.toArray(new String[arrayList.size()]));
        }
        if (z4) {
            this.mF.putBoolean("android.support.localOnly", true);
        }
        if (str != null) {
            this.mF.putString("android.support.groupKey", str);
            if (z5) {
                this.mF.putBoolean("android.support.isGroupSummary", true);
            } else {
                this.mF.putBoolean("android.support.useSideChannel", true);
            }
        }
        if (str2 != null) {
            this.mF.putString("android.support.sortKey", str2);
        }
    }

    @Override // android.support.v4.app.InterfaceC0018s
    public void qP(v vVar) {
        this.mE.add(B.rc(this.mD, vVar));
    }

    @Override // android.support.v4.app.InterfaceC0019t
    public Notification qQ() {
        SparseArray<? extends Parcelable> sparseArrayRa = B.ra(this.mE);
        if (sparseArrayRa != null) {
            this.mF.putSparseParcelableArray("android.support.actionExtras", sparseArrayRa);
        }
        this.mD.setExtras(this.mF);
        return this.mD.build();
    }

    @Override // android.support.v4.app.InterfaceC0019t
    public Notification.Builder qR() {
        return this.mD;
    }
}
