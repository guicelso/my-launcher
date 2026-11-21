package c.b.a;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.tw.car.CarService;
import com.tw.car.CarService.b;

/* JADX INFO: loaded from: classes.dex */
public class a extends Handler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ CarService f444a;

    public a(CarService carService) {
        this.f444a = carService;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i = message.what;
        if (i != 266) {
            if (i != 523) {
                if (i != 1281) {
                    if (i != 40732) {
                        return;
                    }
                }
            }
            int i2 = this.f444a.f455b;
            int i3 = message.arg1;
            if (i2 != i3) {
                this.f444a.f455b = i3;
                if (this.f444a.f455b != 0) {
                    Log.e("tjy", "mReverse");
                    if (this.f444a.d == null) {
                        CarService carService = this.f444a;
                        carService.d = carService.new b(carService.getApplicationContext());
                    }
                    this.f444a.d.b();
                }
            }
            if (message.arg1 != 0) {
                this.f444a.f454a.a(144, 148, 0);
                return;
            }
            if (this.f444a.f456c != null) {
                CarService.a aVar = this.f444a.f456c;
                if (aVar.d) {
                    aVar.f457a.removeView(aVar.f459c);
                    aVar.d = false;
                }
            }
            if (this.f444a.d != null) {
                this.f444a.d.a();
                return;
            }
            return;
        }
        if (message.arg1 == 0) {
            String[] strArrSplit = ((String) message.obj).split("-");
            if (h.f453c == -1) {
                try {
                    String str = strArrSplit[1];
                    String str2 = strArrSplit[2];
                    Integer.parseInt(str.substring(0, str.length()), 16);
                    h.f453c = Integer.parseInt(str2.substring(0, 2), 16);
                    if (str2.length() > 2) {
                        Integer.parseInt(str2.substring(2, 3), 16);
                    }
                    h.d = strArrSplit.length == 6 ? strArrSplit[5] : " ";
                } catch (Exception unused) {
                }
                if (h.f453c == -1) {
                    h.f453c = 0;
                    return;
                }
                return;
            }
            return;
        }
        byte[] bArr = (byte[]) message.obj;
        if (message.arg1 != 148 || this.f444a.f455b == 0) {
            return;
        }
        this.f444a.f456c.a(bArr);
    }
}
