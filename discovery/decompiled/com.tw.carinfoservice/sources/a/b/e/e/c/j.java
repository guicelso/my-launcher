package a.b.e.e.c;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class j extends i {

    public class a extends i.a implements ActionProvider.VisibilityListener {
        public a.b.d.h.c.a d;

        public a(j jVar, Context context, ActionProvider actionProvider) {
            super(context, actionProvider);
        }

        @Override // a.b.d.h.c
        public boolean b() {
            return this.f232b.isVisible();
        }

        @Override // a.b.d.h.c
        public View d(MenuItem menuItem) {
            return this.f232b.onCreateActionView(menuItem);
        }

        @Override // a.b.d.h.c
        public boolean g() {
            return this.f232b.overridesItemVisibility();
        }

        @Override // a.b.d.h.c
        public void h(a.b.d.h.c.a aVar) {
            this.d = aVar;
            this.f232b.setVisibilityListener(this);
        }

        @Override // android.view.ActionProvider.VisibilityListener
        public void onActionProviderVisibilityChanged(boolean z) {
            a.b.d.h.c.a aVar = this.d;
            if (aVar != null) {
                g gVar = h.this.n;
                gVar.h = true;
                gVar.p(true);
            }
        }
    }

    public j(Context context, a.b.d.d.a.b bVar) {
        super(context, bVar);
    }

    @Override // a.b.e.e.c.i
    public i.a k(ActionProvider actionProvider) {
        return new a(this, this.f211b, actionProvider);
    }
}
