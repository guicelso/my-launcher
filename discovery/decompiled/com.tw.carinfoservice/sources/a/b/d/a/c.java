package a.b.d.a;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Context f42a;
    public PendingIntent d;
    public Bundle f;
    public String g;
    public Notification h;

    @Deprecated
    public ArrayList<String> i;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ArrayList<b> f43b = new ArrayList<>();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public ArrayList<b> f44c = new ArrayList<>();
    public boolean e = true;

    public c(Context context, String str) {
        Notification notification = new Notification();
        this.h = notification;
        this.f42a = context;
        this.g = str;
        notification.when = System.currentTimeMillis();
        this.h.audioStreamType = -1;
        this.i = new ArrayList<>();
    }
}
