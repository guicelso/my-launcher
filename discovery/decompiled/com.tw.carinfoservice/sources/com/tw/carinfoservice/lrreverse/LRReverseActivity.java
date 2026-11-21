package com.tw.carinfoservice.lrreverse;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import c.b.b.b;
import c.b.b.c;
import com.tw.carinfoservice.R;
import com.tw.qcarcamlib.CamParamConstant;

/* JADX INFO: loaded from: classes.dex */
public class LRReverseActivity extends Activity implements SurfaceHolder.Callback, b.c {
    public TextView d;
    public View e;
    public SurfaceHolder h;
    public RelativeLayout i;
    public b j;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public c.b.a.l.b f494b = null;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public SurfaceView f495c = null;
    public int f = 1024;
    public int g = 600;
    public Handler k = new a();

    public class a extends Handler {
        public a() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                int i = message.what;
                if (i != 524) {
                    if (i == 65280) {
                        LRReverseActivity.this.i.setVisibility(8);
                    }
                } else if (c.b.a.l.b.c(message.arg1, 0) != 1 && c.b.a.l.b.c(message.arg1, 2) != 1) {
                    removeMessages(1);
                    LRReverseActivity.this.finish();
                }
            } catch (Exception unused) {
            }
        }
    }

    @Override // c.b.b.b.c
    public void a(boolean z) {
        this.d.setVisibility(z ? 8 : 0);
        this.e.setVisibility(z ? 8 : 0);
        this.f495c.setVisibility(z ? 0 : 8);
    }

    public void b(int i) {
        this.f494b.write(40448, i);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= 30) {
            finish();
        }
        super.onBackPressed();
    }

    /* JADX WARN: Code duplicated, block: B:65:0x01eb  */
    public void onClick(View view) {
        int iA;
        int iA2;
        int iA3;
        int i;
        String str;
        int i2;
        String string;
        StringBuilder sb;
        TextView textView;
        int i3;
        float f;
        int id = view.getId();
        if (id == R.id.back) {
            finish();
            return;
        }
        if (id != R.id.camera_settings) {
            return;
        }
        b bVar = this.j;
        bVar.o.removeMessages(65294);
        c cVar = bVar.h;
        boolean z = cVar.d;
        if (z) {
            cVar.a();
            return;
        }
        if (!z) {
            int i4 = Settings.System.getInt(cVar.f.getContentResolver(), "TWCameraSaveType", 0);
            if (i4 != 0) {
                if (i4 == 1) {
                    iA = c.b.b.f.a.a(Settings.System.getString(cVar.f.getContentResolver(), "TWCameraBrightnessSave"), 0, cVar.f);
                    iA2 = c.b.b.f.a.a(Settings.System.getString(cVar.f.getContentResolver(), "TWCameraContrastSave"), 0, cVar.f);
                    iA3 = c.b.b.f.a.a(Settings.System.getString(cVar.f.getContentResolver(), "TWCameraSaturationSave"), 0, cVar.f);
                } else if (i4 != 2) {
                    iA = 0;
                    iA3 = 0;
                    iA2 = 0;
                } else {
                    iA = SystemProperties.getInt(Settings.System.getString(cVar.f.getContentResolver(), "TWCameraBrightnessSave"), 100);
                    i2 = SystemProperties.getInt(Settings.System.getString(cVar.f.getContentResolver(), "TWCameraContrastSave"), 100);
                    string = Settings.System.getString(cVar.f.getContentResolver(), "TWCameraSaturationSave");
                    iA3 = SystemProperties.getInt(string, 100);
                    iA2 = i2;
                }
            } else if (c.b.b.f.a.i() || c.b.b.f.a.f()) {
                iA = c.b.b.f.a.a("brightness", 0, cVar.f);
                iA2 = c.b.b.f.a.a("contrast", 0, cVar.f);
                iA3 = c.b.b.f.a.a("saturation", 0, cVar.f);
            } else {
                if (c.b.b.f.a.g() || c.b.b.f.a.e() || c.b.b.f.a.j() || c.b.b.f.a.h()) {
                    iA = SystemProperties.getInt("persist.camera.brightness2.aux", 100);
                    i = SystemProperties.getInt("persist.camera.contrast2.aux", 100);
                    str = "persist.camera.saturation2.aux";
                } else {
                    iA = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_BRIGHTNESS, 100);
                    i = SystemProperties.getInt(CamParamConstant.AUX.PERSIST_CAM_CONTRAST, 100);
                    str = CamParamConstant.AUX.PERSIST_CAM_SATURATION;
                }
                String str2 = str;
                i2 = i;
                string = str2;
                iA3 = SystemProperties.getInt(string, 100);
                iA2 = i2;
            }
            int i5 = (c.b.b.f.a.i() || c.b.b.f.a.f()) ? 0 : Settings.System.getInt(cVar.f.getContentResolver(), "TWCameraSaveSize", 128);
            int i6 = iA - i5;
            int i7 = iA2 - i5;
            int i8 = iA3 - i5;
            if (cVar.f461c.findViewById(R.id.offset_layout).getVisibility() == 0) {
                int i9 = Settings.System.getInt(cVar.f.getContentResolver(), "TWCameraOffsetType", 0);
                if (i9 != 0) {
                    if (i9 == 1) {
                        int i10 = Settings.System.getInt(cVar.f.getContentResolver(), "TWCameraOffsetType", 0);
                        if (i10 != 0) {
                            if (i10 != 1) {
                                i3 = 0;
                            } else {
                                int i11 = SystemProperties.getInt("persist.camera.lrpos", 128);
                                int i12 = Settings.System.getInt(cVar.f.getContentResolver(), "TWCameraOffsetSize", 138);
                                int i13 = Settings.System.getInt(cVar.f.getContentResolver(), "TWCameraOffsetMAX", 319);
                                int i14 = Settings.System.getInt(cVar.f.getContentResolver(), "TWCameraOffsetMIN", 64);
                                if (i11 > i12) {
                                    double d = i11 - i12;
                                    double d2 = i13 - i12;
                                    Double.isNaN(d2);
                                    Double.isNaN(d2);
                                    Double.isNaN(d2);
                                    Double.isNaN(d);
                                    Double.isNaN(d);
                                    Double.isNaN(d);
                                    f = ((float) (d / (d2 * 0.02d))) - i12;
                                } else if (i11 < i12) {
                                    double d3 = (i11 - i14) / (i12 - i14);
                                    Double.isNaN(d3);
                                    Double.isNaN(d3);
                                    Double.isNaN(d3);
                                    f = ((float) (d3 * 0.02d)) + i14;
                                } else {
                                    i3 = 0;
                                }
                                i3 = (int) f;
                            }
                        } else if (c.b.b.f.a.q()) {
                            i3 = SystemProperties.getInt("persist.camera.lrpos", 128) - Settings.System.getInt(cVar.f.getContentResolver(), "persist.camera.lrpos", 128);
                        } else {
                            i3 = 0;
                        }
                        textView = (TextView) cVar.f461c.findViewById(R.id.offset_title);
                        sb = new StringBuilder();
                        c.a.a.a.a.c(cVar.f, R.string.offset, sb, "(", i3);
                        c.a.a.a.a.d(sb, ")", textView);
                        ((SeekBar) cVar.f461c.findViewById(R.id.offset_level)).setMax(100);
                        ((SeekBar) cVar.f461c.findViewById(R.id.offset_level)).setProgress(i3 + 50);
                    }
                } else if (c.b.b.f.a.q()) {
                    int i15 = SystemProperties.getInt("persist.camera.lrpos", 128) - 128;
                    TextView textView2 = (TextView) cVar.f461c.findViewById(R.id.offset_title);
                    sb = new StringBuilder();
                    textView = textView2;
                    i3 = i15;
                    c.a.a.a.a.c(cVar.f, R.string.offset, sb, "(", i3);
                    c.a.a.a.a.d(sb, ")", textView);
                    ((SeekBar) cVar.f461c.findViewById(R.id.offset_level)).setMax(100);
                    ((SeekBar) cVar.f461c.findViewById(R.id.offset_level)).setProgress(i3 + 50);
                }
            }
            TextView textView3 = (TextView) cVar.f461c.findViewById(R.id.brightness_title);
            StringBuilder sb2 = new StringBuilder();
            c.a.a.a.a.c(cVar.f, R.string.brightness, sb2, "(", i6);
            c.a.a.a.a.d(sb2, ")", textView3);
            ((SeekBar) cVar.f461c.findViewById(R.id.brightness_level)).setMax(100);
            ((SeekBar) cVar.f461c.findViewById(R.id.brightness_level)).setProgress(i6 + 50);
            TextView textView4 = (TextView) cVar.f461c.findViewById(R.id.contrast_title);
            StringBuilder sb3 = new StringBuilder();
            c.a.a.a.a.c(cVar.f, R.string.contrast, sb3, "(", i7);
            c.a.a.a.a.d(sb3, ")", textView4);
            ((SeekBar) cVar.f461c.findViewById(R.id.contrast_level)).setMax(100);
            ((SeekBar) cVar.f461c.findViewById(R.id.contrast_level)).setProgress(i7 + 50);
            TextView textView5 = (TextView) cVar.f461c.findViewById(R.id.saturation_title);
            StringBuilder sb4 = new StringBuilder();
            c.a.a.a.a.c(cVar.f, R.string.saturation, sb4, "(", i8);
            c.a.a.a.a.d(sb4, ")", textView5);
            ((SeekBar) cVar.f461c.findViewById(R.id.saturation_level)).setMax(100);
            ((SeekBar) cVar.f461c.findViewById(R.id.saturation_level)).setProgress(i8 + 50);
            ((TextView) cVar.f461c.findViewById(R.id.tv_layout_frame)).setTextColor(-1);
            ((TextView) cVar.f461c.findViewById(R.id.tv_layout_camera)).setTextColor(-7829368);
            ((TextView) cVar.f461c.findViewById(R.id.tv_layout_frame)).getCompoundDrawables()[3].setLevel(1);
            ((TextView) cVar.f461c.findViewById(R.id.tv_layout_camera)).getCompoundDrawables()[3].setLevel(0);
            cVar.f461c.findViewById(R.id.scrollViewFrame).setVisibility(0);
            cVar.f461c.findViewById(R.id.scrollViewCamera).setVisibility(8);
            cVar.d = true;
            cVar.f459a.addView(cVar.f461c, cVar.f460b);
        }
        bVar.o.sendEmptyMessageDelayed(65294, 5000L);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Settings.System.putInt(getContentResolver(), "Right_View", 1);
        int i = SystemProperties.getInt("persist.sf.forcerotation", 2);
        int i2 = SystemProperties.getInt("ro.sf.hwrotation", 0);
        setContentView((i != 1 && (i2 == 0 || i2 == 180)) ? R.layout.lrreverse : R.layout.lrreverse_720);
        this.e = (RelativeLayout) findViewById(R.id.warning_layout);
        this.d = (TextView) findViewById(R.id.warning_no_signal);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        this.f495c = surfaceView;
        SurfaceHolder holder = surfaceView.getHolder();
        this.h = holder;
        holder.addCallback(this);
        this.h.setType(3);
        if (b.q == null) {
            b.q = new b((Activity) this);
        }
        b bVar = b.q;
        this.j = bVar;
        bVar.d.add(this.h);
        this.i = (RelativeLayout) findViewById(R.id.hb);
        c.b.a.l.b bVarF = c.b.a.l.b.f();
        this.f494b = bVarF;
        bVarF.addHandler("AuxInActivity", this.k);
    }

    @Override // android.app.Activity
    public void onDestroy() {
        c.b.a.l.b bVar = this.f494b;
        if (bVar != null) {
            bVar.removeHandler("AuxInActivity");
            this.f494b.close();
            this.f494b = null;
        }
        Settings.System.putInt(getContentResolver(), "Right_View", 0);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onPause() {
        this.k.removeMessages(65294);
        this.k.sendEmptyMessage(65294);
        b(134);
        this.e.setVisibility(8);
        this.j.i(this);
        this.j.c();
        super.onPause();
    }

    @Override // android.app.Activity
    public void onResume() throws Throwable {
        super.onResume();
        b(6);
        b bVar = this.j;
        bVar.p.add(this);
        c cVar = bVar.h;
        if (cVar != null) {
            cVar.k.add(bVar);
        }
        this.j.d();
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        b(134);
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            this.f494b.write(2050, (((int) motionEvent.getX()) * 255) / (this.f - 1), (((int) motionEvent.getY()) * 255) / (this.g - 1));
            if (this.i.getVisibility() != 0) {
                this.i.setVisibility(0);
                this.k.removeMessages(65280);
                this.k.sendEmptyMessageDelayed(65280, 2000L);
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.f = i2;
        this.g = i3;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.j.g(surfaceHolder);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.j.h();
    }
}
