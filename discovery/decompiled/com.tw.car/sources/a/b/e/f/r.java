package a.b.e.f;

import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: loaded from: classes.dex */
public class r implements AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0026q.b f373a;

    public r(C0026q.b bVar, C0026q c0026q) {
        this.f373a = bVar;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        C0026q.this.setSelection(i);
        if (C0026q.this.getOnItemClickListener() != null) {
            C0026q.b bVar = this.f373a;
            C0026q.this.performItemClick(view, i, bVar.J.getItemId(i));
        }
        this.f373a.dismiss();
    }
}
