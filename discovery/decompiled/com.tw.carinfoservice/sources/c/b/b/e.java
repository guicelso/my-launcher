package c.b.b;

import android.content.Context;
import android.hardware.Camera;
import android.os.SystemProperties;
import android.provider.Settings;
import android.view.SurfaceHolder;
import com.tw.qcarcamlib.CamParamConstant;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Camera f475a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f476b = false;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Context f477c;
    public c.b.b.f.b d;

    public e() {
    }

    public e(Context context, c.b.b.f.b bVar) {
        this.f477c = context;
        this.d = bVar;
        a.a();
        a.d();
        a.b();
    }

    public void a() {
        Camera camera = this.f475a;
        if (camera != null) {
            camera.stopPreview();
            this.f475a.release();
            this.f475a = null;
        }
    }

    public void b() {
        try {
            this.f475a = Camera.open(Settings.System.getInt(this.f477c.getContentResolver(), "TWCameraId", a.f451a));
        } catch (Exception e) {
            e.printStackTrace();
            if (c.b.b.f.a.k()) {
                a.f451a = 7;
                this.f475a = Camera.open(7);
            }
        }
    }

    public void c(List<SurfaceHolder> list) {
    }

    public void d(int i, int i2, int i3) {
        Camera camera;
        int i4 = (c.b.b.f.a.f() || c.b.b.f.a.l()) ? 0 : Settings.System.getInt(this.f477c.getContentResolver(), "TWCameraSaveSize", 128);
        int i5 = i + i4;
        int i6 = i2 + i4;
        int i7 = i3 + i4;
        int i8 = Settings.System.getInt(this.f477c.getContentResolver(), "TWCameraType", 0);
        if (i8 != 0) {
            if (i8 == 1) {
                c.b.b.f.a.z(this.d, i5, i6, i7, this.f477c);
                return;
            }
            if (i8 != 2) {
                if (i8 != 3) {
                    return;
                }
                this.d.write(40721, i5);
                this.d.write(40722, i6);
                this.d.write(40723, i7);
                return;
            }
            camera = this.f475a;
            if (camera == null) {
                return;
            }
        } else {
            if (c.b.b.f.a.s() || c.b.b.f.a.p() || c.b.b.f.a.g() || c.b.b.f.a.e() || c.b.b.f.a.i() || c.b.b.f.a.l() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
                c.b.b.f.a.y(this.d, i5, i6, i7);
                return;
            }
            camera = this.f475a;
        }
        Camera.Parameters parameters = camera.getParameters();
        parameters.set("brightness", i5);
        parameters.set("contrast", i6);
        parameters.set("saturation", i7);
        this.f475a.setParameters(parameters);
    }

    public void e(boolean z) {
        int iA;
        int iA2;
        int iA3;
        String string;
        if (!z || this.f476b) {
            if (z || !this.f476b) {
                return;
            }
            Camera camera = this.f475a;
            if (camera != null) {
                camera.stopPreview();
            }
            this.f476b = false;
            return;
        }
        this.f476b = true;
        if (this.f475a != null) {
            int i = (c.b.b.f.a.f() || c.b.b.f.a.l()) ? 0 : Settings.System.getInt(this.f477c.getContentResolver(), "TWCameraSaveSize", 128);
            try {
                Camera.Parameters parameters = this.f475a.getParameters();
                if (Settings.System.getInt(this.f477c.getContentResolver(), "TWCameraW", a.d) != 0 && Settings.System.getInt(this.f477c.getContentResolver(), "TWCameraH", a.e) != 0) {
                    parameters.setPreviewSize(Settings.System.getInt(this.f477c.getContentResolver(), "TWCameraW", a.d), Settings.System.getInt(this.f477c.getContentResolver(), "TWCameraH", a.e));
                }
                int i2 = Settings.System.getInt(this.f477c.getContentResolver(), "TWCameraSaveType", 0);
                if (i2 != 0) {
                    if (i2 == 1) {
                        iA = c.b.b.f.a.a(Settings.System.getString(this.f477c.getContentResolver(), "TWCameraBrightnessSave"), 0, this.f477c);
                        iA2 = c.b.b.f.a.a(Settings.System.getString(this.f477c.getContentResolver(), "TWCameraContrastSave"), 0, this.f477c);
                        iA3 = c.b.b.f.a.a(Settings.System.getString(this.f477c.getContentResolver(), "TWCameraSaturationSave"), 0, this.f477c);
                    } else if (i2 != 2) {
                        iA = 0;
                        iA3 = 0;
                        iA2 = 0;
                    } else {
                        iA = SystemProperties.getInt(Settings.System.getString(this.f477c.getContentResolver(), "TWCameraBrightnessSave"), 100);
                        iA2 = SystemProperties.getInt(Settings.System.getString(this.f477c.getContentResolver(), "TWCameraContrastSave"), 100);
                        string = Settings.System.getString(this.f477c.getContentResolver(), "TWCameraSaturationSave");
                        iA3 = SystemProperties.getInt(string, 100);
                    }
                } else if (c.b.b.f.a.f() || c.b.b.f.a.i()) {
                    iA = c.b.b.f.a.a("brightness", 0, this.f477c);
                    iA2 = c.b.b.f.a.a("contrast", 0, this.f477c);
                    iA3 = c.b.b.f.a.a("saturation", 0, this.f477c);
                } else {
                    if (c.b.b.f.a.g() || c.b.b.f.a.e() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
                        iA = SystemProperties.getInt("persist.camera.brightness2.aux", 100);
                        iA2 = SystemProperties.getInt("persist.camera.contrast2.aux", 100);
                        string = "persist.camera.saturation2.aux";
                    } else {
                        iA = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_BRIGHTNESS, 100);
                        iA2 = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_CONTRAST, 100);
                        string = CamParamConstant.AUX.PERSIST_CAM_SATURATION;
                    }
                    iA3 = SystemProperties.getInt(string, 100);
                }
                int i3 = iA + i;
                int i4 = iA2 + i;
                int i5 = iA3 + i;
                int i6 = Settings.System.getInt(this.f477c.getContentResolver(), "TWCameraType", 0);
                if (i6 != 0) {
                    if (i6 == 1) {
                        c.b.b.f.a.z(this.d, i3, i4, i5, this.f477c);
                    } else if (i6 == 2) {
                        parameters.set("brightness", i3);
                        parameters.set("contrast", i4);
                        parameters.set("saturation", i5);
                    } else if (i6 == 3) {
                        this.d.write(40721, i3);
                        this.d.write(40722, i4);
                        this.d.write(40723, i5);
                    }
                } else if (c.b.b.f.a.s() || c.b.b.f.a.p() || c.b.b.f.a.g() || c.b.b.f.a.e() || c.b.b.f.a.i() || c.b.b.f.a.l() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
                    c.b.b.f.a.y(this.d, i3, i4, i5);
                } else {
                    parameters.set("brightness", i3);
                    parameters.set("contrast", i4);
                    parameters.set("saturation", i5);
                }
                this.f475a.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Settings.System.getInt(this.f477c.getContentResolver(), "TWCameraStartPreview", 0) == 0) {
                this.f475a.startPreview();
            }
        }
    }

    public void f(SurfaceHolder surfaceHolder) {
        try {
            Camera camera = this.f475a;
            if (camera != null) {
                camera.setPreviewDisplay(surfaceHolder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void g() {
        this.f475a.startPreview();
    }

    public void h() {
        Camera camera = this.f475a;
        if (camera != null) {
            camera.stopPreview();
        }
    }
}
