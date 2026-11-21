package c.b.a;

import android.content.DialogInterface;
import com.tw.car.DaciaFuelActivity;

/* JADX INFO: loaded from: classes.dex */
public class e implements DialogInterface.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DaciaFuelActivity f448a;

    public e(DaciaFuelActivity daciaFuelActivity) {
        this.f448a = daciaFuelActivity;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.f448a.f463a.a(144, 2, 0, 0);
    }
}
