package a.b.e.f;

import a.b.e.e.a.C0008a;
import android.R;
import android.view.View;
import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public class ma implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0008a f355a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ na f356b;

    public ma(na naVar) {
        this.f356b = naVar;
        this.f355a = new C0008a(this.f356b.f359a.getContext(), 0, R.id.home, 0, 0, this.f356b.h);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        na naVar = this.f356b;
        Window.Callback callback = naVar.k;
        if (callback == null || !naVar.l) {
            return;
        }
        callback.onMenuItemSelected(0, this.f355a);
    }
}
