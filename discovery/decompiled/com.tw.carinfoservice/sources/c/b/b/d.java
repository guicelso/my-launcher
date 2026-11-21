package c.b.b;

import android.view.SurfaceHolder;
import com.tw.qcarcamlib.QCarCamManager;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class d extends e {
    public QCarCamManager e;

    public class a implements QCarCamManager.OnCameraStateListener {
        public a() {
        }

        @Override // com.tw.qcarcamlib.QCarCamManager.OnCameraStateListener
        public void onCameraState(int i, int i2) {
            Objects.requireNonNull(d.this);
        }
    }

    public d() {
        QCarCamManager qCarCamManager = QCarCamManager.getInstance();
        this.e = qCarCamManager;
        qCarCamManager.registerCameraStateCallback("", new a());
    }

    @Override // c.b.b.e
    public void a() {
        this.e.closeCamera();
    }

    @Override // c.b.b.e
    public void b() {
    }

    @Override // c.b.b.e
    public void c(List<SurfaceHolder> list) {
        this.e.openCamera(QCarCamManager.CAMERA_CHANNEL.CHANNEL_AUX, list);
    }

    @Override // c.b.b.e
    public void e(boolean z) {
    }

    @Override // c.b.b.e
    public void f(SurfaceHolder surfaceHolder) {
    }

    @Override // c.b.b.e
    public void g() {
    }

    @Override // c.b.b.e
    public void h() {
        this.e.closeCamera();
    }
}
