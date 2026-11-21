package a.b.e.e.a;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class p extends o {

    class a extends o.a implements ActionProvider.VisibilityListener {
        public a.b.d.h.e.b d;

        public a(p pVar, Context context, ActionProvider actionProvider) {
            super(context, actionProvider);
        }

        @Override // a.b.d.h.e
        public View a(MenuItem menuItem) {
            return this.f235b.onCreateActionView(menuItem);
        }

        @Override // a.b.d.h.e
        public void a(a.b.d.h.e.b bVar) {
            this.d = bVar;
            this.f235b.setVisibilityListener(bVar != null ? this : null);
        }

        @Override // a.b.d.h.e
        public boolean a() {
            return this.f235b.isVisible();
        }

        @Override // a.b.d.h.e
        public boolean b() {
            return this.f235b.overridesItemVisibility();
        }

        @Override // android.view.ActionProvider.VisibilityListener
        public void onActionProviderVisibilityChanged(boolean z) {
            a.b.d.h.e.b bVar = this.d;
            if (bVar != null) {
                n nVar = ((m) bVar).f231a;
                nVar.n.c(nVar);
            }
        }
    }

    public p(Context context, a.b.d.d.a.b bVar) {
        super(context, bVar);
    }

    @Override // a.b.e.e.a.o
    public o.a a(ActionProvider actionProvider) {
        return new a(this, this.f211b, actionProvider);
    }
}
