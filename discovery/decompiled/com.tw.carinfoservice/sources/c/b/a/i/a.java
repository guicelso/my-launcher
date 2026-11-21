package c.b.a.i;

import android.content.Intent;
import android.os.FileObserver;
import android.util.Log;
import com.tw.carinfoservice.CarService;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class a extends FileObserver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public String f420a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public InterfaceC0017a f421b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f422c;

    /* JADX INFO: renamed from: c.b.a.i.a$a, reason: collision with other inner class name */
    public interface InterfaceC0017a {
    }

    public a(String str, InterfaceC0017a interfaceC0017a) {
        super(str, 2);
        this.f420a = str;
        this.f421b = interfaceC0017a;
        this.f422c = c.b.b.f.a.c(str);
    }

    @Override // android.os.FileObserver
    public void onEvent(int i, String str) throws Throwable {
        if ((i & 2) != 0) {
            int iC = c.b.b.f.a.c(this.f420a);
            Log.d("NodeFileObserver", "onEvent sysClassNode -> " + iC);
            InterfaceC0017a interfaceC0017a = this.f421b;
            if (interfaceC0017a == null || this.f422c == iC) {
                return;
            }
            this.f422c = iC;
            CarService.c cVar = (CarService.c) interfaceC0017a;
            Objects.requireNonNull(cVar);
            Log.d("CarInfoService", "onNodeValueChanged node change -> " + iC);
            CarService carService = CarService.this;
            if (carService.f != iC) {
                carService.f = iC;
                Intent intent = new Intent();
                intent.setAction("LRREVERSE_RESET");
                carService.sendBroadcast(intent);
            }
        }
    }
}
