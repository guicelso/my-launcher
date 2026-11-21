package com.dofun.carsetting.widget;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.dofun.carsetting.R;
import com.dofun.carsetting.f.u;

/* JADX INFO: compiled from: Floating360View.java */
/* JADX INFO: loaded from: classes.dex */
public class a implements View.OnClickListener {
    private static a b;
    private Context c;
    private DisplayMetrics d;
    private View e;
    private WindowManager f;
    private WindowManager.LayoutParams g;
    private int h;
    private int i;
    private double j;
    private Button k;
    private ActivityManager w;
    private float l = 0.0f;
    private float m = 0.0f;
    private float n = 0.0f;
    private float o = 0.0f;
    private int p = 0;
    private int q = 0;
    private boolean r = false;
    private boolean s = false;
    private final int t = 999;
    private final int u = 6000;
    private boolean v = false;
    private BroadcastReceiver x = new BroadcastReceiver() { // from class: com.dofun.carsetting.widget.a.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (a.this.s) {
                a.this.d();
            }
            a.this.e = null;
        }
    };
    private Handler y = new Handler() { // from class: com.dofun.carsetting.widget.a.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 153) {
                a.this.c();
                a.this.b();
                return;
            }
            if (i != 999) {
                switch (i) {
                    case MotionEventCompat.ACTION_POINTER_INDEX_MASK /* 65280 */:
                        a.this.f();
                        break;
                    case 65281:
                        a.this.a();
                        break;
                    case 65282:
                        a.this.b();
                        break;
                }
                return;
            }
            a.this.d();
            a.this.b();
        }
    };

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private u f482a = u.a();

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
    }

    private a(DisplayMetrics displayMetrics, Context context) {
        this.w = null;
        this.c = context;
        this.d = displayMetrics;
        this.h = displayMetrics.widthPixels;
        this.i = displayMetrics.heightPixels;
        this.j = Math.ceil(displayMetrics.density * 25.0f);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        context.registerReceiver(this.x, intentFilter);
        e();
        this.w = (ActivityManager) context.getSystemService("activity");
    }

    public static a a(DisplayMetrics displayMetrics, Context context) {
        if (b == null) {
            b = new a(displayMetrics, context);
        }
        return b;
    }

    private void e() {
        this.f = (WindowManager) this.c.getSystemService("window");
        this.g = new WindowManager.LayoutParams();
        this.g.type = 2002;
        this.g.format = -3;
        this.g.flags = 32776;
    }

    public void a() {
        try {
            String packageName = ((ActivityManager) this.c.getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getPackageName();
            String str = SystemProperties.get("sys.tw.forcepip");
            PackageManager packageManager = this.c.getPackageManager();
            new Intent();
            if ((!"com.baony.tw360".equals(packageName) && !"cn.cardoor.zt360".equals(packageName) && !"com.softwinner.dvr".equals(packageName) && !"com.tw.dvr".equals(packageName)) || (!"0".equals(str) && !TextUtils.isEmpty(str))) {
                Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage("com.baony.tw360");
                if (launchIntentForPackage != null) {
                    this.c.startActivity(launchIntentForPackage);
                    return;
                }
                Intent launchIntentForPackage2 = packageManager.getLaunchIntentForPackage("cn.cardoor.zt360");
                if (launchIntentForPackage2 != null) {
                    this.c.startActivity(launchIntentForPackage2);
                    return;
                }
                Intent launchIntentForPackage3 = packageManager.getLaunchIntentForPackage("com.softwinner.dvr");
                if (launchIntentForPackage3 != null) {
                    this.c.startActivity(launchIntentForPackage3);
                    return;
                }
                Intent launchIntentForPackage4 = packageManager.getLaunchIntentForPackage("com.tw.dvr");
                if (launchIntentForPackage4 != null) {
                    this.c.startActivity(launchIntentForPackage4);
                    return;
                }
                this.v = false;
                return;
            }
            this.f482a.write(InputDeviceCompat.SOURCE_DPAD, 1, 18);
        } catch (Exception unused) {
        }
    }

    public void b() {
        if (this.r) {
            return;
        }
        this.s = true;
        this.r = true;
        if (this.k == null) {
            this.k = new Button(this.c);
            this.k.setBackgroundResource(R.drawable.btn_floating360);
            this.k.setOnTouchListener(new View.OnTouchListener() { // from class: com.dofun.carsetting.widget.a.3
                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    float rawX = motionEvent.getRawX();
                    float rawY = (float) (((double) motionEvent.getRawY()) - a.this.j);
                    int i = (int) (rawX - a.this.n);
                    int rawY2 = (int) (motionEvent.getRawY() - a.this.o);
                    switch (motionEvent.getAction()) {
                        case 0:
                            a.this.v = true;
                            a.this.y.removeMessages(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
                            a.this.y.sendEmptyMessageDelayed(MotionEventCompat.ACTION_POINTER_INDEX_MASK, 3500L);
                            a.this.l = motionEvent.getX();
                            a.this.m = motionEvent.getY();
                            a.this.n = motionEvent.getRawX();
                            a.this.o = motionEvent.getRawY();
                            a.this.g.alpha = 1.0f;
                            a.this.f.updateViewLayout(a.this.k, a.this.g);
                            return true;
                        case 1:
                            a.this.g.alpha = 0.6f;
                            a.this.f.updateViewLayout(a.this.k, a.this.g);
                            if (i <= -10 || i >= 10 || rawY2 <= -10 || rawY2 >= 10) {
                                float f = rawX - a.this.l;
                                float f2 = rawY - a.this.m;
                                if (f < a.this.h / 2) {
                                    if (f > f2) {
                                        if (rawY > a.this.k.getHeight() * 2) {
                                            a.this.a(f, f2);
                                        } else {
                                            a.this.a(f, 0.0f);
                                        }
                                    } else if (f > (a.this.i - motionEvent.getRawY()) - 98.0f) {
                                        if (((float) (((((double) a.this.i) - a.this.j) - ((double) f2)) - 98.0d)) > a.this.k.getHeight() * 2) {
                                            a.this.a(f, f2);
                                        } else {
                                            a.this.a(f, (float) ((((double) a.this.i) - a.this.j) - 98.0d));
                                        }
                                    } else if (f > a.this.k.getWidth() * 2) {
                                        a.this.a(f, f2);
                                    } else {
                                        a.this.a(0.0f, f2);
                                    }
                                } else if ((a.this.h - f) - 98.0f > f2) {
                                    if (rawY > a.this.k.getHeight() * 2) {
                                        a.this.a(f, f2);
                                    } else {
                                        a.this.a(f, 0.0f);
                                    }
                                } else if ((a.this.h - f) - 98.0f > (a.this.i - motionEvent.getRawY()) - 98.0f) {
                                    if (((float) (((((double) a.this.i) - a.this.j) - ((double) f2)) - 98.0d)) > a.this.k.getHeight() * 2) {
                                        a.this.a(f, f2);
                                    } else {
                                        a.this.a(f, (float) ((((double) a.this.i) - a.this.j) - 98.0d));
                                    }
                                } else if ((a.this.h - f) - 98.0f > a.this.k.getWidth() * 2) {
                                    a.this.a(f, f2);
                                } else {
                                    a.this.a(a.this.h - 98, f2);
                                }
                            } else if (a.this.v) {
                                a.this.y.removeMessages(65281);
                                a.this.y.sendEmptyMessageDelayed(65281, 500L);
                                a.this.v = false;
                            }
                            a.this.l = 0.0f;
                            a.this.m = 0.0f;
                            a.this.n = 0.0f;
                            a.this.o = 0.0f;
                            return true;
                        case 2:
                            if (i < -10 || i > 10 || rawY2 < -10 || rawY2 > 10) {
                                a.this.a(rawX - a.this.l, rawY - a.this.m);
                                a.this.v = false;
                            }
                            return true;
                        default:
                            return true;
                    }
                }
            });
        }
        this.g.alpha = 0.5f;
        this.g.x = this.p;
        this.g.y = this.q;
        this.g.width = (int) this.c.getResources().getDimension(R.dimen.floating_size);
        this.g.height = (int) this.c.getResources().getDimension(R.dimen.floating_size);
        this.g.gravity = 51;
        this.f.addView(this.k, this.g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(float f, float f2) {
        int i = (int) f;
        this.p = i;
        int i2 = (int) f2;
        this.q = i2;
        this.g.x = i;
        this.g.y = i2;
        this.f.updateViewLayout(this.k, this.g);
    }

    public void c() {
        if (!this.r || this.k == null) {
            return;
        }
        this.f.removeView(this.k);
        this.r = false;
    }

    public void d() {
        if (!this.s || this.e == null) {
            return;
        }
        this.f.removeView(this.e);
        this.s = false;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        view.getId();
        g();
    }

    private void g() {
        this.y.removeMessages(999);
        this.y.sendEmptyMessageDelayed(999, 6000L);
    }

    public static void a(Context context, DisplayMetrics displayMetrics) {
        if (b == null) {
            b = a(displayMetrics, context);
        }
        b.d();
        b.b();
    }

    public static void b(Context context, DisplayMetrics displayMetrics) {
        if (b == null) {
            b = a(displayMetrics, context);
        }
        b.d();
        b.c();
    }
}
