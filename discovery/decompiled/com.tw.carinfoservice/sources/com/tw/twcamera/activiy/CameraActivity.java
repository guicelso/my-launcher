package com.tw.twcamera.activiy;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import c.b.b.b;
import c.b.b.c;

/* JADX INFO: loaded from: classes.dex */
public abstract class CameraActivity extends Activity implements SurfaceHolder.Callback, b.c {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public b f497b;

    @Override // c.b.b.b.c
    public void a(boolean z) {
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        this.f497b.c();
        this.f497b.i(this);
    }

    @Override // android.app.Activity
    public void onResume() throws Throwable {
        if (this.f497b == null) {
            this.f497b = new b((Activity) this);
        }
        this.f497b.d.add(null);
        super.onResume();
        b bVar = this.f497b;
        bVar.p.add(this);
        c cVar = bVar.h;
        if (cVar != null) {
            cVar.k.add(bVar);
        }
        this.f497b.d();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.f497b.g(surfaceHolder);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.f497b.h();
    }
}
