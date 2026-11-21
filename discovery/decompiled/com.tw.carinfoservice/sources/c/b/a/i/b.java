package c.b.a.i;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import com.tw.carinfoservice.CarService;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class b extends ContentObserver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Context f423a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public String f424b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public a f425c;
    public int d;

    public interface a {
    }

    public b(Context context, String str, Handler handler, a aVar) {
        super(handler);
        this.f423a = context;
        this.f424b = str;
        this.f425c = aVar;
        this.d = Settings.System.getInt(context.getContentResolver(), this.f424b, 0);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        super.onChange(z);
        int i = Settings.System.getInt(this.f423a.getContentResolver(), this.f424b, 0);
        StringBuilder sbB = c.a.a.a.a.b("SystemSettingObserver -> onChange -> mSettingKey: ");
        sbB.append(this.f424b);
        Log.d("SystemSettingObserver", sbB.toString());
        Log.d("SystemSettingObserver", "SystemSettingObserver -> onChange -> value: " + i);
        a aVar = this.f425c;
        if (aVar == null || this.d == i) {
            return;
        }
        this.d = i;
        CarService.b bVar = (CarService.b) aVar;
        Objects.requireNonNull(bVar);
        Log.d("CarInfoService", "onSettingChange");
        CarService carService = CarService.this;
        Objects.requireNonNull(carService);
        Intent intent = new Intent();
        intent.setAction("LRREVERSE_RESET");
        carService.sendBroadcast(intent);
    }
}
