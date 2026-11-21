package com.tw.car;

import a.b.d.a.e;
import a.b.d.a.f;
import a.b.d.a.g;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RemoteViews;
import c.b.a.h;

/* JADX INFO: loaded from: classes.dex */
public class CarService extends Service {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public a f456c;
    public b d;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public h f454a = null;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f455b = 0;
    public Handler e = new c.b.a.a(this);
    public ServiceConnection f = new c.b.a.b(this);
    public int g = 0;

    /* JADX INFO: Access modifiers changed from: private */
    public class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public WindowManager f457a;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public View f459c;
        public Context e;
        public boolean d = false;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public WindowManager.LayoutParams f458b = new WindowManager.LayoutParams();

        public a(Context context) {
            this.e = context;
            this.f457a = (WindowManager) this.e.getSystemService("window");
            WindowManager.LayoutParams layoutParams = this.f458b;
            layoutParams.type = 2010;
            layoutParams.format = -3;
            layoutParams.flags = 1280;
            if (this.f459c == null) {
                this.f459c = ((LayoutInflater) CarService.this.getSystemService("layout_inflater")).inflate(R.layout.warning, (ViewGroup) null);
            }
        }

        public void a(byte[] bArr) {
            View viewFindViewById;
            String str;
            WindowManager.LayoutParams layoutParams;
            if (bArr.length == 1) {
                return;
            }
            if (h.d.equals("ARKANA")) {
                this.f459c.findViewById(R.id.default0).setVisibility(8);
                this.f459c.findViewById(R.id.arkana).setVisibility(8);
                this.f459c.findViewById(R.id.duster).setVisibility(0);
            } else {
                this.f459c.findViewById(R.id.default0).setVisibility(8);
                this.f459c.findViewById(R.id.arkana).setVisibility(0);
                this.f459c.findViewById(R.id.duster).setVisibility(8);
            }
            if ((bArr[0] & 255) == 1 && CarService.this.f455b != 0 && !this.d) {
                this.d = true;
                WindowManager.LayoutParams layoutParams2 = this.f458b;
                layoutParams2.alpha = 1.0f;
                layoutParams2.x = 0;
                layoutParams2.y = 0;
                int i = -1;
                layoutParams2.width = -1;
                String str2 = SystemProperties.get("ro.board.platform");
                int i2 = SystemProperties.getInt("persist.sf.forcerotation", 2);
                Log.e("ls", str2);
                if (str2.equals("sc8830")) {
                    str = "ro.sf.hwrotation";
                } else {
                    str = str2.equals("sp9853i") ? "ro.sf.swrotation" : "ro.sf.rotation";
                }
                int i3 = SystemProperties.getInt(str, 0);
                if (i2 == 1) {
                    layoutParams = this.f458b;
                    i = 450;
                } else {
                    if (i3 == 0 || i3 == 180) {
                        layoutParams = this.f458b;
                    }
                    WindowManager.LayoutParams layoutParams3 = this.f458b;
                    layoutParams3.gravity = 51;
                    layoutParams3.gravity = 51;
                    this.f457a.addView(this.f459c, layoutParams3);
                }
                layoutParams.height = i;
                WindowManager.LayoutParams layoutParams4 = this.f458b;
                layoutParams4.gravity = 51;
                layoutParams4.gravity = 51;
                this.f457a.addView(this.f459c, layoutParams4);
            }
            ((ImageView) this.f459c.findViewById(R.id.top_9b0)).getDrawable().setLevel(0);
            ((ImageView) this.f459c.findViewById(R.id.down_9b0)).getDrawable().setLevel(0);
            ((ImageView) this.f459c.findViewById(R.id.left_9b0)).getDrawable().setLevel(0);
            ((ImageView) this.f459c.findViewById(R.id.right_9b0)).getDrawable().setLevel(0);
            ((ImageView) this.f459c.findViewById(R.id.top_9b1)).getDrawable().setLevel(0);
            ((ImageView) this.f459c.findViewById(R.id.down_9b1)).getDrawable().setLevel(0);
            ((ImageView) this.f459c.findViewById(R.id.left_9b1)).getDrawable().setLevel(0);
            ((ImageView) this.f459c.findViewById(R.id.right_9b1)).getDrawable().setLevel(0);
            ((ImageView) this.f459c.findViewById(R.id.top_9b2)).getDrawable().setLevel(0);
            ((ImageView) this.f459c.findViewById(R.id.down_9b2)).getDrawable().setLevel(0);
            ((ImageView) this.f459c.findViewById(R.id.left_9b2)).getDrawable().setLevel(0);
            ((ImageView) this.f459c.findViewById(R.id.right_9b2)).getDrawable().setLevel(0);
            int i4 = bArr[1] & 255;
            if (i4 == 0) {
                ((ImageView) this.f459c.findViewById(R.id.top_9b0)).getDrawable().setLevel(0);
                ((ImageView) this.f459c.findViewById(R.id.down_9b0)).getDrawable().setLevel(0);
                ((ImageView) this.f459c.findViewById(R.id.left_9b0)).getDrawable().setLevel(0);
                ((ImageView) this.f459c.findViewById(R.id.right_9b0)).getDrawable().setLevel(0);
                ((ImageView) this.f459c.findViewById(R.id.top_9b1)).getDrawable().setLevel(0);
                ((ImageView) this.f459c.findViewById(R.id.down_9b1)).getDrawable().setLevel(0);
                ((ImageView) this.f459c.findViewById(R.id.left_9b1)).getDrawable().setLevel(0);
                ((ImageView) this.f459c.findViewById(R.id.right_9b1)).getDrawable().setLevel(0);
                ((ImageView) this.f459c.findViewById(R.id.top_9b2)).getDrawable().setLevel(0);
                ((ImageView) this.f459c.findViewById(R.id.down_9b2)).getDrawable().setLevel(0);
                ((ImageView) this.f459c.findViewById(R.id.left_9b2)).getDrawable().setLevel(0);
                ((ImageView) this.f459c.findViewById(R.id.right_9b2)).getDrawable().setLevel(0);
                return;
            }
            if (i4 == 1) {
                ((ImageView) this.f459c.findViewById(R.id.down_9b0)).getDrawable().setLevel(1);
                ((ImageView) this.f459c.findViewById(R.id.down_9b1)).getDrawable().setLevel(1);
                viewFindViewById = this.f459c.findViewById(R.id.down_9b2);
            } else if (i4 == 2) {
                ((ImageView) this.f459c.findViewById(R.id.top_9b0)).getDrawable().setLevel(1);
                ((ImageView) this.f459c.findViewById(R.id.top_9b1)).getDrawable().setLevel(1);
                viewFindViewById = this.f459c.findViewById(R.id.top_9b2);
            } else if (i4 == 3) {
                ((ImageView) this.f459c.findViewById(R.id.right_9b0)).getDrawable().setLevel(1);
                ((ImageView) this.f459c.findViewById(R.id.right_9b1)).getDrawable().setLevel(1);
                viewFindViewById = this.f459c.findViewById(R.id.right_9b2);
            } else {
                if (i4 != 4) {
                    return;
                }
                ((ImageView) this.f459c.findViewById(R.id.left_9b0)).getDrawable().setLevel(1);
                ((ImageView) this.f459c.findViewById(R.id.left_9b1)).getDrawable().setLevel(1);
                viewFindViewById = this.f459c.findViewById(R.id.left_9b2);
            }
            ((ImageView) viewFindViewById).getDrawable().setLevel(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public class b implements View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public WindowManager f460a;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public View f462c;
        public Context e;
        public boolean d = false;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public WindowManager.LayoutParams f461b = new WindowManager.LayoutParams();

        public b(Context context) {
            this.e = context;
            this.f460a = (WindowManager) this.e.getSystemService("window");
            WindowManager.LayoutParams layoutParams = this.f461b;
            layoutParams.type = 2003;
            layoutParams.format = -3;
            layoutParams.flags = 32776;
            if (this.f462c == null) {
                this.f462c = ((LayoutInflater) CarService.this.getSystemService("layout_inflater")).inflate(R.layout.warning_9f, (ViewGroup) null);
                ((ImageView) this.f462c.findViewById(R.id.btn_front)).setOnClickListener(this);
                ((ImageView) this.f462c.findViewById(R.id.btn_left)).setOnClickListener(this);
                ((ImageView) this.f462c.findViewById(R.id.btn_right)).setOnClickListener(this);
                ((ImageView) this.f462c.findViewById(R.id.btn_rear)).setOnClickListener(this);
            }
        }

        public void a() {
            if (this.d) {
                this.f460a.removeView(this.f462c);
                this.d = false;
            }
        }

        public void b() {
            if (this.d) {
                return;
            }
            this.d = true;
            WindowManager.LayoutParams layoutParams = this.f461b;
            layoutParams.alpha = 1.0f;
            layoutParams.x = 0;
            layoutParams.y = 0;
            layoutParams.width = 217;
            layoutParams.height = 438;
            layoutParams.gravity = 51;
            layoutParams.gravity = 51;
            this.f460a.addView(this.f462c, layoutParams);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            h hVar;
            int i;
            try {
                switch (view.getId()) {
                    case R.id.btn_front /* 2131230763 */:
                        hVar = CarService.this.f454a;
                        i = 2;
                        break;
                    case R.id.btn_left /* 2131230764 */:
                        CarService.this.f454a.a(198, 4, 4);
                        return;
                    case R.id.btn_rear /* 2131230765 */:
                        hVar = CarService.this.f454a;
                        i = 1;
                        break;
                    case R.id.btn_right /* 2131230766 */:
                        hVar = CarService.this.f454a;
                        i = 3;
                        break;
                    default:
                        return;
                }
                hVar.a(198, 4, i);
            } catch (Exception unused) {
            }
        }
    }

    public final void a() {
        Log.d("CarService", "bindService:com.tw.carinfoservice");
        try {
            Intent intent = new Intent("com.tw.carinfoservice.CarService.Bind");
            intent.setClassName("com.tw.carinfoservice", "com.tw.carinfoservice.CarService");
            if (Build.VERSION.SDK_INT >= 26) {
                getApplication().startForegroundService(intent);
            } else {
                getApplication().startService(intent);
            }
            getApplication().bindService(intent, this.f, 1);
        } catch (Exception e) {
            Log.d("CarService", Log.getStackTraceString(e));
        }
    }

    /* JADX WARN: Code duplicated, block: B:77:0x011b A[PHI: r0
      0x011b: PHI (r0v17 android.app.Notification) = (r0v13 android.app.Notification), (r0v16 android.app.Notification), (r0v19 android.app.Notification) binds: [B:76:0x0119, B:52:0x00d4, B:26:0x008a] A[DONT_GENERATE, DONT_INLINE]] */
    public final Notification b() {
        Notification notificationBuild;
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("notification_channel_id_01", "Foreground Service Notification", 2);
            notificationChannel.setDescription("Channel description");
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        e eVar = new e(this, "notification_channel_id_01");
        eVar.M.icon = R.drawable.ic_app_launcher;
        eVar.M.when = System.currentTimeMillis();
        eVar.f = PendingIntent.getActivity(this, 1, new Intent(this, (Class<?>) CarService.class), 134217728);
        f fVar = new f(eVar);
        e eVar2 = fVar.f49b;
        int i = Build.VERSION.SDK_INT;
        if (i >= 26) {
            notificationBuild = fVar.f48a.build();
        } else if (i >= 24) {
            notificationBuild = fVar.f48a.build();
            if (fVar.g != 0) {
                if (notificationBuild.getGroup() != null && (notificationBuild.flags & 512) != 0 && fVar.g == 2) {
                    fVar.a(notificationBuild);
                }
                if (notificationBuild.getGroup() != null && (notificationBuild.flags & 512) == 0 && fVar.g == 1) {
                    fVar.a(notificationBuild);
                }
            }
        } else if (i >= 21) {
            fVar.f48a.setExtras(fVar.f);
            notificationBuild = fVar.f48a.build();
            RemoteViews remoteViews = fVar.f50c;
            if (remoteViews != null) {
                notificationBuild.contentView = remoteViews;
            }
            RemoteViews remoteViews2 = fVar.d;
            if (remoteViews2 != null) {
                notificationBuild.bigContentView = remoteViews2;
            }
            RemoteViews remoteViews3 = fVar.h;
            if (remoteViews3 != null) {
                notificationBuild.headsUpContentView = remoteViews3;
            }
            if (fVar.g != 0) {
                if (notificationBuild.getGroup() != null && (notificationBuild.flags & 512) != 0 && fVar.g == 2) {
                    fVar.a(notificationBuild);
                }
                if (notificationBuild.getGroup() != null && (notificationBuild.flags & 512) == 0 && fVar.g == 1) {
                    fVar.a(notificationBuild);
                }
            }
        } else if (i >= 20) {
            fVar.f48a.setExtras(fVar.f);
            notificationBuild = fVar.f48a.build();
            RemoteViews remoteViews4 = fVar.f50c;
            if (remoteViews4 != null) {
                notificationBuild.contentView = remoteViews4;
            }
            RemoteViews remoteViews5 = fVar.d;
            if (remoteViews5 != null) {
                notificationBuild.bigContentView = remoteViews5;
            }
            if (fVar.g != 0) {
                if (notificationBuild.getGroup() != null && (notificationBuild.flags & 512) != 0 && fVar.g == 2) {
                    fVar.a(notificationBuild);
                }
                if (notificationBuild.getGroup() != null && (notificationBuild.flags & 512) == 0 && fVar.g == 1) {
                    fVar.a(notificationBuild);
                }
            }
        } else {
            SparseArray<Bundle> sparseArrayA = g.a(fVar.e);
            if (sparseArrayA != null) {
                fVar.f.putSparseParcelableArray("android.support.actionExtras", sparseArrayA);
            }
            fVar.f48a.setExtras(fVar.f);
            notificationBuild = fVar.f48a.build();
            RemoteViews remoteViews6 = fVar.f50c;
            if (remoteViews6 != null) {
                notificationBuild.contentView = remoteViews6;
            }
            RemoteViews remoteViews7 = fVar.d;
            if (remoteViews7 != null) {
                notificationBuild.bigContentView = remoteViews7;
            }
        }
        RemoteViews remoteViews8 = fVar.f49b.E;
        if (remoteViews8 != null) {
            notificationBuild.contentView = remoteViews8;
        }
        int i2 = Build.VERSION.SDK_INT;
        return notificationBuild;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    public void onClick(View view) {
        h hVar;
        int i;
        switch (view.getId()) {
            case R.id.down_9b0 /* 2131230784 */:
            case R.id.down_9b1 /* 2131230785 */:
            case R.id.down_9b2 /* 2131230786 */:
                hVar = this.f454a;
                i = 1;
                break;
            case R.id.left_9b0 /* 2131230812 */:
            case R.id.left_9b1 /* 2131230813 */:
            case R.id.left_9b2 /* 2131230814 */:
                this.f454a.a(198, 4, 4);
                return;
            case R.id.right_9b0 /* 2131230838 */:
            case R.id.right_9b1 /* 2131230839 */:
            case R.id.right_9b2 /* 2131230840 */:
                hVar = this.f454a;
                i = 3;
                break;
            case R.id.top_9b0 /* 2131230890 */:
            case R.id.top_9b1 /* 2131230891 */:
            case R.id.top_9b2 /* 2131230892 */:
                hVar = this.f454a;
                i = 2;
                break;
            default:
                return;
        }
        hVar.a(198, 4, i);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.f454a = h.a();
        h hVar = this.f454a;
        if (hVar != null) {
            hVar.addHandler("CarService", this.e);
            if (h.f453c == -1) {
                this.f454a.write(266, 255);
            }
        }
        this.g = Settings.System.getInt(getApplicationContext().getContentResolver(), "BIND_SERVICE_CONFIG", 0);
        if (this.g != 0) {
            startForeground(1, b());
            a();
        }
        this.f456c = new a(getApplicationContext());
    }

    @Override // android.app.Service
    public void onDestroy() {
        h hVar = this.f454a;
        if (hVar != null) {
            hVar.removeHandler("CarService");
            this.f454a.close();
            this.f454a = null;
        }
        if (this.g != 0) {
            stopForeground(true);
            getApplication().unbindService(this.f);
        }
        super.onDestroy();
    }
}
