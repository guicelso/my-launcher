package c.b.a;

import android.content.DialogInterface;
import com.tw.car.DaciaFuelActivity;

/* JADX INFO: loaded from: classes.dex */
public class d implements DialogInterface.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DaciaFuelActivity f447a;

    public d(DaciaFuelActivity daciaFuelActivity) {
        this.f447a = daciaFuelActivity;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.f447a.f463a.a(144, 2, 1, 0);
    }
}
