package c.b.a;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.tw.car.CarService;

/* JADX INFO: loaded from: classes.dex */
public class b implements ServiceConnection {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ CarService f445a;

    public b(CarService carService) {
        this.f445a = carService;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        StringBuilder sbA = c.a.a.a.a.a("onServiceConnected:");
        sbA.append(componentName.getPackageName());
        Log.d("CarService", sbA.toString());
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        StringBuilder sbA = c.a.a.a.a.a("onServiceDisconnected:");
        sbA.append(componentName.getPackageName());
        Log.d("CarService", sbA.toString());
        this.f445a.a();
    }
}
