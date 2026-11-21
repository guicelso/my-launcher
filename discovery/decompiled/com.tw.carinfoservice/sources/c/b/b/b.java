package c.b.b;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.SystemProperties;
import android.provider.Settings;
import android.view.SurfaceHolder;
import android.view.View;
import com.tw.qcarcamlib.CamParamConstant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class b implements c.b.b.c.g {
    public static b q;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public c.b.b.f.b f454a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f455b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f456c;
    public List<SurfaceHolder> d;
    public boolean e;
    public boolean f;
    public Context g;
    public c.b.b.c h;
    public View i;
    public boolean j;
    public Activity k;
    public e l;
    public boolean m;
    public BroadcastReceiver n;
    public Handler o;
    public ArrayList<c> p;

    public class a extends BroadcastReceiver {
        public a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("LRREVERSE_REPAY".equals(intent.getAction())) {
                b bVar = b.this;
                bVar.o.removeMessages(65289);
                bVar.o.sendEmptyMessageDelayed(65289, 200L);
            }
        }
    }

    /* JADX INFO: renamed from: c.b.b.b$b, reason: collision with other inner class name */
    public class HandlerC0018b extends Handler {
        public HandlerC0018b() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:92:0x01a8, code lost:
        
            if (android.os.SystemProperties.getInt(com.tw.qcarcamlib.CamParamConstant.AUX.PERSIST_CAM_TYPE, 0) != r14) goto L93;
         */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void handleMessage(android.os.Message r14) throws java.lang.Throwable {
            /*
                Method dump skipped, instruction units count: 804
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: c.b.b.b.HandlerC0018b.handleMessage(android.os.Message):void");
        }
    }

    public interface c {
        void a(boolean z);
    }

    public b(Activity activity) {
        this.f455b = 0;
        this.f456c = 0;
        this.d = new ArrayList();
        this.f = false;
        this.j = true;
        this.m = false;
        this.n = new a();
        this.o = new HandlerC0018b();
        this.p = new ArrayList<>();
        this.g = activity.getApplicationContext();
        this.k = activity;
        if (this.f454a == null) {
            this.f454a = c.b.b.f.b.a();
        }
        this.f454a.addHandler("CameraManager", this.o);
        this.l = c.b.b.f.a.n() ? new d() : new e(this.g, this.f454a);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LRREVERSE_REPAY");
        this.g.registerReceiver(this.n, intentFilter);
        this.m = true;
        this.h = new c.b.b.c(this.g, this.f454a, this.l);
    }

    public b(Context context) {
        this.f455b = 0;
        this.f456c = 0;
        this.d = new ArrayList();
        this.f = false;
        this.j = true;
        this.m = false;
        this.n = new a();
        this.o = new HandlerC0018b();
        this.p = new ArrayList<>();
        this.g = context;
        if (this.f454a == null) {
            this.f454a = c.b.b.f.b.a();
        }
        this.f454a.addHandler("CameraManager", this.o);
        if (c.b.b.f.a.n()) {
            this.l = new d();
        } else {
            this.l = new e(context, this.f454a);
        }
    }

    public static /* synthetic */ int b(b bVar) {
        int i = bVar.f456c;
        bVar.f456c = i + 1;
        return i;
    }

    @Override // c.b.b.c.g
    public void a() {
        this.o.removeMessages(65294);
        this.o.sendEmptyMessageDelayed(65294, 5000L);
    }

    public void c() {
        if (Settings.System.getInt(this.g.getContentResolver(), "TWCameraCh", 0) == 1 || c.b.b.f.a.e() || c.b.b.f.a.g() || c.b.b.f.a.i() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
            c.b.b.f.a.C(this.f454a, 0);
        }
        this.l.h();
        this.l.e(false);
        this.o.removeMessages(65292);
        this.o.removeMessages(65281);
        BroadcastReceiver broadcastReceiver = this.n;
        if (broadcastReceiver != null && this.m) {
            this.g.unregisterReceiver(broadcastReceiver);
        }
        this.m = false;
        this.l.a();
        this.f454a.removeHandler("CameraManager");
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001d  */
    /* JADX WARN: Code duplicated, block: B:13:0x0022  */
    /* JADX WARN: Code duplicated, block: B:25:0x005f  */
    /* JADX WARN: Code duplicated, block: B:54:0x00c1  */
    public void d() throws Throwable {
        boolean z;
        int iB;
        int i = SystemProperties.getInt("persist.sf.forcerotation", -1);
        int i2 = SystemProperties.getInt("persist.sf.autorotation", 0);
        if (i == 0) {
            if (i2 == 0 && i2 == 1) {
            }
        } else if (i != 1) {
            if (i != 2 && (i == 3 || i == 4)) {
                z = i2 == 0;
            }
        }
        if (z && this.j) {
            View decorView = this.k.getWindow().getDecorView();
            this.i = decorView;
            decorView.setSystemUiVisibility(5894);
        }
        int i3 = Settings.System.getInt(this.g.getContentResolver(), "TWCameraCh", 0);
        if (i3 == 0) {
            if (c.b.b.f.a.o() || c.b.b.f.a.r() || c.b.b.f.a.q() || c.b.b.f.a.t() || c.b.b.f.a.s()) {
                c.b.b.f.a.A(2);
            }
            if (c.b.b.f.a.i() || c.b.b.f.a.g() || c.b.b.f.a.k() || c.b.b.f.a.e() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
                if (c.b.b.f.a.f480c) {
                    c.b.b.f.a.w(this.f454a, 1);
                    c.b.b.f.a.x(this.f454a, this.g);
                    iB = c.b.b.f.a.c("/sys/devices/tp2815/type");
                } else {
                    c.b.b.f.a.C(this.f454a, 1);
                    c.b.b.f.a.H(this.f454a, this.g);
                    iB = c.b.b.f.a.b();
                }
                c.b.b.a.f453c = iB;
            }
        } else if (i3 == 1) {
            c.b.b.f.a.A(2);
        } else if (i3 == 2) {
            c.b.b.f.a.C(this.f454a, 1);
            c.b.b.f.a.H(this.f454a, this.g);
            iB = c.b.b.f.a.b();
            c.b.b.a.f453c = iB;
        } else if (i3 != 3) {
            c.b.b.f.a.A(Settings.System.getInt(this.g.getContentResolver(), "TWCameraCh", 0));
        } else {
            c.b.b.f.a.w(this.f454a, 1);
            c.b.b.f.a.x(this.f454a, this.g);
            iB = c.b.b.f.a.c("/sys/devices/tp2815/type");
            c.b.b.a.f453c = iB;
        }
        this.f = false;
        this.l.b();
        this.l.e(true);
        this.o.removeMessages(65281);
        this.o.sendEmptyMessageDelayed(65281, 300L);
        f(true);
    }

    /* JADX WARN: Code duplicated, block: B:176:0x03a7  */
    /* JADX WARN: Code duplicated, block: B:197:0x0419  */
    /* JADX WARN: Code duplicated, block: B:202:0x042e  */
    /* JADX WARN: Code duplicated, block: B:204:0x0475  */
    /* JADX WARN: Code duplicated, block: B:206:0x0481  */
    /* JADX WARN: Code duplicated, block: B:208:0x0487  */
    /* JADX WARN: Code duplicated, block: B:211:0x0496  */
    /* JADX WARN: Code duplicated, block: B:222:0x04ce  */
    public void e() {
        ContentResolver contentResolver;
        String str;
        ContentResolver contentResolver2;
        int i;
        ContentResolver contentResolver3;
        int i2;
        ContentResolver contentResolver4;
        String str2;
        Objects.requireNonNull(this.l);
        c.b.b.a.a();
        c.b.b.a.d();
        c.b.b.a.b();
        Settings.System.putInt(this.g.getContentResolver(), "Right_View", 0);
        Settings.System.putInt(this.g.getContentResolver(), "TWCameraCh", 0);
        Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal", 0);
        Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal0", 0);
        Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal1", 0);
        Settings.System.putInt(this.g.getContentResolver(), "TWCameraBrake", 0);
        if (c.b.b.f.a.o() || c.b.b.f.a.r() || c.b.b.f.a.q() || c.b.b.f.a.t() || c.b.b.f.a.p()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraCh", 1);
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraViewAuto", 1);
        }
        if (c.b.b.f.a.i() || c.b.b.f.a.g() || c.b.b.f.a.k() || c.b.b.f.a.e() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraViewAuto", 2);
            if (c.b.b.f.a.f480c) {
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraCh", 3);
            } else {
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraCh", 2);
            }
        }
        Settings.System.putInt(this.g.getContentResolver(), "TWCameraId", c.b.b.a.f451a);
        Settings.System.putInt(this.g.getContentResolver(), "TWCameraW", c.b.b.a.d);
        Settings.System.putInt(this.g.getContentResolver(), "TWCameraH", c.b.b.a.e);
        if (c.b.b.f.a.f() || c.b.b.f.a.k()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal", 1);
        } else if (c.b.b.f.a.u() || c.b.b.f.a.v()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal", 2);
        } else if (c.b.b.f.a.o() || c.b.b.f.a.r() || c.b.b.f.a.t() || c.b.b.f.a.n() || c.b.b.f.a.q() || c.b.b.f.a.p()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal", 3);
        } else if (c.b.b.f.a.i() || c.b.b.f.a.e() || c.b.b.f.a.g() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
            if (c.b.b.f.a.f480c) {
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal", 5);
            } else {
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal", 4);
            }
        }
        if (c.b.b.f.a.e()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal0", 1);
        } else if (c.b.b.f.a.g() || c.b.b.f.a.i() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal0", 2);
        } else if (c.b.b.f.a.f()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal0", 3);
        }
        if (c.b.b.f.a.i() || c.b.b.f.a.e() || c.b.b.f.a.g() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal1", 1);
        } else if (c.b.b.f.a.f()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal1", 2);
        } else if (c.b.b.f.a.o() || c.b.b.f.a.r() || c.b.b.f.a.q() || c.b.b.f.a.s() || c.b.b.f.a.p()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal1", 3);
        } else if (c.b.b.f.a.p()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSignal1", 4);
        }
        if (c.b.b.f.a.f() || c.b.b.f.a.k() || c.b.b.f.a.o() || c.b.b.f.a.r() || c.b.b.f.a.s()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraBrake", 1);
        }
        if (!c.b.b.f.a.s() && !c.b.b.f.a.p()) {
            if (c.b.b.f.a.g() || c.b.b.f.a.e() || c.b.b.f.a.i() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
                if (c.b.b.f.a.f480c) {
                    Settings.System.putString(this.g.getContentResolver(), "TWCameraBrightness", "rwrite /sys/devices/tp2815/brightness ");
                    Settings.System.putString(this.g.getContentResolver(), "TWCameraContrast", "rwrite /sys/devices/tp2815/contrast ");
                    contentResolver = this.g.getContentResolver();
                    str = "rwrite /sys/devices/tp2815/saturation ";
                } else {
                    Settings.System.putString(this.g.getContentResolver(), "TWCameraBrightness", "rwrite /sys/devices/tp2850/brightness ");
                    Settings.System.putString(this.g.getContentResolver(), "TWCameraContrast", "rwrite /sys/devices/tp2850/contrast ");
                    contentResolver = this.g.getContentResolver();
                    str = "rwrite /sys/devices/tp2850/saturation ";
                }
            } else if (c.b.b.f.a.l() || c.b.b.f.a.m()) {
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraType", 3);
            } else {
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraType", 2);
            }
            if (!c.b.b.f.a.i() || c.b.b.f.a.f() || c.b.b.f.a.l() || c.b.b.f.a.m()) {
                Settings.System.putString(this.g.getContentResolver(), "TWCameraBrightnessSave", "brightness");
                Settings.System.putString(this.g.getContentResolver(), "TWCameraContrastSave", "contrast");
                Settings.System.putString(this.g.getContentResolver(), "TWCameraSaturationSave", "saturation");
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraSaveType", 1);
            } else {
                if (c.b.b.f.a.g() || c.b.b.f.a.e() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
                    Settings.System.putString(this.g.getContentResolver(), "TWCameraBrightnessSave", "persist.camera.brightness2.aux");
                    Settings.System.putString(this.g.getContentResolver(), "TWCameraContrastSave", "persist.camera.contrast2.aux");
                    contentResolver4 = this.g.getContentResolver();
                    str2 = "persist.camera.saturation2.aux";
                } else {
                    Settings.System.putString(this.g.getContentResolver(), "TWCameraBrightnessSave", CamParamConstant.AUX.PERSIST_CAM_BRIGHTNESS);
                    Settings.System.putString(this.g.getContentResolver(), "TWCameraContrastSave", CamParamConstant.AUX.PERSIST_CAM_CONTRAST);
                    contentResolver4 = this.g.getContentResolver();
                    str2 = CamParamConstant.AUX.PERSIST_CAM_SATURATION;
                }
                Settings.System.putString(contentResolver4, "TWCameraSaturationSave", str2);
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraSaveType", 2);
            }
            if (!c.b.b.f.a.f() || c.b.b.f.a.l() || c.b.b.f.a.m() || c.b.b.f.a.g() || c.b.b.f.a.e() || c.b.b.f.a.p() || c.b.b.f.a.s()) {
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraSaveSize", 0);
            } else {
                if (c.b.b.f.a.i()) {
                    contentResolver3 = this.g.getContentResolver();
                    i2 = 100;
                } else {
                    contentResolver3 = this.g.getContentResolver();
                    i2 = 128;
                }
                Settings.System.putInt(contentResolver3, "TWCameraSaveSize", i2);
            }
            if (c.b.b.f.a.o() || c.b.b.f.a.r()) {
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraOffsetType", 1);
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraOffsetMin", 64);
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraOffsetMAX", 319);
                Settings.System.putString(this.g.getContentResolver(), "TWCameraOffset", "rwrite /sys/class/sensor_i2c/lrpos ");
                Settings.System.putString(this.g.getContentResolver(), "TWCameraOffsetSave", "persist.camera.lrpos");
                if (c.b.b.f.a.o()) {
                    contentResolver2 = this.g.getContentResolver();
                    i = 185;
                } else if (c.b.b.f.a.r()) {
                    contentResolver2 = this.g.getContentResolver();
                    i = 138;
                }
                Settings.System.putInt(contentResolver2, "TWCameraOffsetSize", i);
            }
            if (c.b.b.f.a.i()) {
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraShowLayoutCamera", 1);
                Settings.System.putInt(this.g.getContentResolver(), "AUX_CAMERA_INDEX", c.b.b.f.a.b());
            }
            if (c.b.b.f.a.k() || c.b.b.f.a.i() || c.b.b.f.a.e() || c.b.b.f.a.g() || c.b.b.f.a.e()) {
                Settings.System.putInt(this.g.getContentResolver(), "TWCameraStartPreview", 1);
            }
            this.f454a.write(40730, 1, 0, "sync");
        }
        Settings.System.putString(this.g.getContentResolver(), "TWCameraBrightness", "rwrite /sys/class/sensor_i2c/brightness ");
        Settings.System.putString(this.g.getContentResolver(), "TWCameraContrast", "rwrite /sys/class/sensor_i2c/contrast ");
        contentResolver = this.g.getContentResolver();
        str = "rwrite /sys/class/sensor_i2c/saturation ";
        Settings.System.putString(contentResolver, "TWCameraSaturation", str);
        Settings.System.putInt(this.g.getContentResolver(), "TWCameraType", 1);
        if (c.b.b.f.a.i()) {
            Settings.System.putString(this.g.getContentResolver(), "TWCameraBrightnessSave", "brightness");
            Settings.System.putString(this.g.getContentResolver(), "TWCameraContrastSave", "contrast");
            Settings.System.putString(this.g.getContentResolver(), "TWCameraSaturationSave", "saturation");
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSaveType", 1);
        } else {
            Settings.System.putString(this.g.getContentResolver(), "TWCameraBrightnessSave", "brightness");
            Settings.System.putString(this.g.getContentResolver(), "TWCameraContrastSave", "contrast");
            Settings.System.putString(this.g.getContentResolver(), "TWCameraSaturationSave", "saturation");
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSaveType", 1);
        }
        if (c.b.b.f.a.f()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSaveSize", 0);
        } else {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraSaveSize", 0);
        }
        if (c.b.b.f.a.o()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraOffsetType", 1);
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraOffsetMin", 64);
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraOffsetMAX", 319);
            Settings.System.putString(this.g.getContentResolver(), "TWCameraOffset", "rwrite /sys/class/sensor_i2c/lrpos ");
            Settings.System.putString(this.g.getContentResolver(), "TWCameraOffsetSave", "persist.camera.lrpos");
            if (c.b.b.f.a.o()) {
                contentResolver2 = this.g.getContentResolver();
                i = 185;
            } else if (c.b.b.f.a.r()) {
                contentResolver2 = this.g.getContentResolver();
                i = 138;
            }
            Settings.System.putInt(contentResolver2, "TWCameraOffsetSize", i);
        } else {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraOffsetType", 1);
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraOffsetMin", 64);
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraOffsetMAX", 319);
            Settings.System.putString(this.g.getContentResolver(), "TWCameraOffset", "rwrite /sys/class/sensor_i2c/lrpos ");
            Settings.System.putString(this.g.getContentResolver(), "TWCameraOffsetSave", "persist.camera.lrpos");
            if (c.b.b.f.a.o()) {
                contentResolver2 = this.g.getContentResolver();
                i = 185;
            } else if (c.b.b.f.a.r()) {
                contentResolver2 = this.g.getContentResolver();
                i = 138;
            }
            Settings.System.putInt(contentResolver2, "TWCameraOffsetSize", i);
        }
        if (c.b.b.f.a.i()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraShowLayoutCamera", 1);
            Settings.System.putInt(this.g.getContentResolver(), "AUX_CAMERA_INDEX", c.b.b.f.a.b());
        }
        if (c.b.b.f.a.k()) {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraStartPreview", 1);
        } else {
            Settings.System.putInt(this.g.getContentResolver(), "TWCameraStartPreview", 1);
        }
        this.f454a.write(40730, 1, 0, "sync");
    }

    public final void f(boolean z) {
        if (z) {
            Iterator<c> it = this.p.iterator();
            while (it.hasNext()) {
                it.next().a(true);
            }
        } else {
            Iterator<c> it2 = this.p.iterator();
            while (it2.hasNext()) {
                it2.next().a(false);
            }
        }
    }

    public void g(SurfaceHolder surfaceHolder) {
        try {
            if (c.b.b.f.a.n()) {
                this.e = true;
                if (c.b.b.f.a.n()) {
                    this.o.removeMessages(65292);
                    this.o.sendEmptyMessageDelayed(65292, 100L);
                    return;
                }
                return;
            }
            e eVar = this.l;
            if (eVar != null) {
                eVar.f(surfaceHolder);
            }
            if (Settings.System.getInt(this.g.getContentResolver(), "TWCameraStartPreview", 0) == 1) {
                this.l.g();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void h() {
        try {
            if (c.b.b.f.a.n()) {
                this.e = true;
            } else {
                e eVar = this.l;
                if (eVar != null) {
                    eVar.h();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void i(c cVar) {
        if (this.p.contains(cVar)) {
            this.p.remove(cVar);
            c.b.b.c cVar2 = this.h;
            if (cVar2 == null || !cVar2.k.contains(this)) {
                return;
            }
            cVar2.a();
            cVar2.k.remove(this);
        }
    }
}
