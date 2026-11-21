package a.b.e.e.c;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
public class g implements a.b.d.d.a.a {
    public static final int[] t = {1, 4, 5, 3, 2, 0};

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f225a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Resources f226b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f227c;
    public boolean d;
    public a e;
    public ArrayList<h> f;
    public ArrayList<h> g;
    public boolean h;
    public ArrayList<h> i;
    public ArrayList<h> j;
    public boolean k;
    public CharSequence l;
    public boolean m = false;
    public boolean n = false;
    public boolean o = false;
    public boolean p = false;
    public ArrayList<h> q = new ArrayList<>();
    public CopyOnWriteArrayList<WeakReference<m>> r = new CopyOnWriteArrayList<>();
    public h s;

    public interface a {
    }

    public interface b {
        boolean b(h hVar);
    }

    public g(Context context) {
        boolean z = false;
        this.f225a = context;
        Resources resources = context.getResources();
        this.f226b = resources;
        this.f = new ArrayList<>();
        this.g = new ArrayList<>();
        this.h = true;
        this.i = new ArrayList<>();
        this.j = new ArrayList<>();
        this.k = true;
        if (resources.getConfiguration().keyboard != 1 && a.b.d.h.k.b(ViewConfiguration.get(context), context)) {
            z = true;
        }
        this.d = z;
    }

    public MenuItem a(int i, int i2, int i3, CharSequence charSequence) {
        int i4;
        int i5 = ((-65536) & i3) >> 16;
        if (i5 >= 0) {
            int[] iArr = t;
            if (i5 < iArr.length) {
                int i6 = (iArr[i5] << 16) | (65535 & i3);
                h hVar = new h(this, i, i2, i3, i6, charSequence, 0);
                ArrayList<h> arrayList = this.f;
                int size = arrayList.size();
                do {
                    size--;
                    if (size < 0) {
                        i4 = 0;
                    }
                    arrayList.add(i4, hVar);
                    p(true);
                    return hVar;
                } while (arrayList.get(size).d > i6);
                i4 = size + 1;
                arrayList.add(i4, hVar);
                p(true);
                return hVar;
            }
        }
        throw new IllegalArgumentException("order does not contain a valid category.");
    }

    @Override // android.view.Menu
    public MenuItem add(int i) {
        return a(0, 0, 0, this.f226b.getString(i));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, int i4) {
        return a(i, i2, i3, this.f226b.getString(i4));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return a(i, i2, i3, charSequence);
    }

    @Override // android.view.Menu
    public MenuItem add(CharSequence charSequence) {
        return a(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        int i5;
        PackageManager packageManager = this.f225a.getPackageManager();
        List<ResolveInfo> listQueryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = listQueryIntentActivityOptions != null ? listQueryIntentActivityOptions.size() : 0;
        if ((i4 & 1) == 0) {
            removeGroup(i);
        }
        for (int i6 = 0; i6 < size; i6++) {
            ResolveInfo resolveInfo = listQueryIntentActivityOptions.get(i6);
            int i7 = resolveInfo.specificIndex;
            Intent intent2 = new Intent(i7 < 0 ? intent : intentArr[i7]);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            intent2.setComponent(new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
            h hVar = (h) a(i, i2, i3, resolveInfo.loadLabel(packageManager));
            hVar.setIcon(resolveInfo.loadIcon(packageManager));
            hVar.setIntent(intent2);
            if (menuItemArr != null && (i5 = resolveInfo.specificIndex) >= 0) {
                menuItemArr[i5] = hVar;
            }
        }
        return size;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i) {
        return addSubMenu(0, 0, 0, this.f226b.getString(i));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return addSubMenu(i, i2, i3, this.f226b.getString(i4));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        h hVar = (h) a(i, i2, i3, charSequence);
        q qVar = new q(this.f225a, this, hVar);
        hVar.o = qVar;
        qVar.setHeaderTitle(hVar.e);
        return qVar;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    public void b(m mVar, Context context) {
        this.r.add(new WeakReference<>(mVar));
        mVar.l(context, this);
        this.k = true;
    }

    public final void c(boolean z) {
        if (this.p) {
            return;
        }
        this.p = true;
        for (WeakReference<m> weakReference : this.r) {
            m mVar = weakReference.get();
            if (mVar == null) {
                this.r.remove(weakReference);
            } else {
                mVar.a(this, z);
            }
        }
        this.p = false;
    }

    @Override // android.view.Menu
    public void clear() {
        h hVar = this.s;
        if (hVar != null) {
            d(hVar);
        }
        this.f.clear();
        p(true);
    }

    public void clearHeader() {
        this.l = null;
        p(false);
    }

    @Override // android.view.Menu
    public void close() {
        c(true);
    }

    public boolean d(h hVar) {
        boolean zH = false;
        if (!this.r.isEmpty() && this.s == hVar) {
            v();
            for (WeakReference<m> weakReference : this.r) {
                m mVar = weakReference.get();
                if (mVar != null) {
                    zH = mVar.h(this, hVar);
                    if (zH) {
                        break;
                    }
                } else {
                    this.r.remove(weakReference);
                }
            }
            u();
            if (zH) {
                this.s = null;
            }
        }
        return zH;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0020  */
    public boolean e(g gVar, MenuItem menuItem) {
        boolean z;
        a aVar = this.e;
        if (aVar != null) {
            ActionMenuView.e eVar = ActionMenuView.this.A;
            if (eVar == null) {
                z = false;
            } else {
                Toolbar.e eVar2 = Toolbar.this.H;
                if (eVar2 != null ? eVar2.onMenuItemClick(menuItem) : false) {
                    z = true;
                } else {
                    z = false;
                }
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public boolean f(h hVar) {
        boolean zE = false;
        if (this.r.isEmpty()) {
            return false;
        }
        v();
        for (WeakReference<m> weakReference : this.r) {
            m mVar = weakReference.get();
            if (mVar != null) {
                zE = mVar.e(this, hVar);
                if (zE) {
                    break;
                }
            } else {
                this.r.remove(weakReference);
            }
        }
        u();
        if (zE) {
            this.s = hVar;
        }
        return zE;
    }

    @Override // android.view.Menu
    public MenuItem findItem(int i) {
        MenuItem menuItemFindItem;
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            h hVar = this.f.get(i2);
            if (hVar.f228a == i) {
                return hVar;
            }
            if (hVar.hasSubMenu() && (menuItemFindItem = hVar.o.findItem(i)) != null) {
                return menuItemFindItem;
            }
        }
        return null;
    }

    public h g(int i, KeyEvent keyEvent) {
        ArrayList<h> arrayList = this.q;
        arrayList.clear();
        h(arrayList, i, keyEvent);
        if (arrayList.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        keyEvent.getKeyData(keyData);
        int size = arrayList.size();
        if (size == 1) {
            return arrayList.get(0);
        }
        boolean zM = m();
        for (int i2 = 0; i2 < size; i2++) {
            h hVar = arrayList.get(i2);
            char c2 = zM ? hVar.j : hVar.h;
            char[] cArr = keyData.meta;
            if ((c2 == cArr[0] && (metaState & 2) == 0) || ((c2 == cArr[2] && (metaState & 2) != 0) || (zM && c2 == '\b' && i == 67))) {
                return hVar;
            }
        }
        return null;
    }

    @Override // android.view.Menu
    public MenuItem getItem(int i) {
        return this.f.get(i);
    }

    public void h(List<h> list, int i, KeyEvent keyEvent) {
        boolean zM = m();
        int modifiers = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (keyEvent.getKeyData(keyData) || i == 67) {
            int size = this.f.size();
            for (int i2 = 0; i2 < size; i2++) {
                h hVar = this.f.get(i2);
                if (hVar.hasSubMenu()) {
                    hVar.o.h(list, i, keyEvent);
                }
                char c2 = zM ? hVar.j : hVar.h;
                if (((modifiers & 69647) == ((zM ? hVar.k : hVar.i) & 69647)) && c2 != 0) {
                    char[] cArr = keyData.meta;
                    if ((c2 == cArr[0] || c2 == cArr[2] || (zM && c2 == '\b' && i == 67)) && hVar.isEnabled()) {
                        list.add(hVar);
                    }
                }
            }
        }
    }

    @Override // android.view.Menu
    public boolean hasVisibleItems() {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.f.get(i).isVisible()) {
                return true;
            }
        }
        return false;
    }

    public void i() {
        ArrayList<h> arrayListK = k();
        if (this.k) {
            boolean zG = false;
            for (WeakReference<m> weakReference : this.r) {
                m mVar = weakReference.get();
                if (mVar == null) {
                    this.r.remove(weakReference);
                } else {
                    zG |= mVar.g();
                }
            }
            if (zG) {
                this.i.clear();
                this.j.clear();
                int size = arrayListK.size();
                for (int i = 0; i < size; i++) {
                    h hVar = arrayListK.get(i);
                    (hVar.m() ? this.i : this.j).add(hVar);
                }
            } else {
                this.i.clear();
                this.j.clear();
                this.j.addAll(k());
            }
            this.k = false;
        }
    }

    @Override // android.view.Menu
    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return g(i, keyEvent) != null;
    }

    public g j() {
        return this;
    }

    public ArrayList<h> k() {
        if (!this.h) {
            return this.g;
        }
        this.g.clear();
        int size = this.f.size();
        for (int i = 0; i < size; i++) {
            h hVar = this.f.get(i);
            if (hVar.isVisible()) {
                this.g.add(hVar);
            }
        }
        this.h = false;
        this.k = true;
        return this.g;
    }

    public boolean l() {
        return false;
    }

    public boolean m() {
        return this.f227c;
    }

    public boolean n() {
        return this.d;
    }

    public void o() {
        this.k = true;
        p(true);
    }

    public void p(boolean z) {
        if (this.m) {
            this.n = true;
            if (z) {
                this.o = true;
                return;
            }
            return;
        }
        if (z) {
            this.h = true;
            this.k = true;
        }
        if (this.r.isEmpty()) {
            return;
        }
        v();
        for (WeakReference<m> weakReference : this.r) {
            m mVar = weakReference.get();
            if (mVar == null) {
                this.r.remove(weakReference);
            } else {
                mVar.j(z);
            }
        }
        u();
    }

    @Override // android.view.Menu
    public boolean performIdentifierAction(int i, int i2) {
        return q(findItem(i), i2);
    }

    @Override // android.view.Menu
    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        h hVarG = g(i, keyEvent);
        boolean zR = hVarG != null ? r(hVarG, null, i2) : false;
        if ((i2 & 2) != 0) {
            c(true);
        }
        return zR;
    }

    public boolean q(MenuItem menuItem, int i) {
        return r(menuItem, null, i);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0040  */
    /* JADX WARN: Code duplicated, block: B:32:0x004f  */
    /* JADX WARN: Code duplicated, block: B:35:0x0056  */
    /* JADX WARN: Code duplicated, block: B:38:0x005f  */
    /* JADX WARN: Code duplicated, block: B:45:0x006d  */
    /* JADX WARN: Code duplicated, block: B:47:0x0071  */
    /* JADX WARN: Code duplicated, block: B:50:0x007a  */
    /* JADX WARN: Code duplicated, block: B:53:0x008c  */
    /* JADX WARN: Code duplicated, block: B:57:0x0098 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:58:0x009a  */
    /* JADX WARN: Code duplicated, block: B:62:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:69:0x00c8 A[PHI: r1
      0x00c8: PHI (r1v11 boolean) = (r1v10 boolean), (r1v9 boolean), (r1v13 boolean) binds: [B:68:0x00c6, B:43:0x006a, B:36:0x005b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:75:0x00be A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:76:0x00c0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:77:0x00b8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:79:0x00a4 A[SYNTHETIC] */
    public boolean r(MenuItem menuItem, m mVar, int i) {
        a.b.d.h.c cVar;
        boolean zExpandActionView;
        a.b.d.h.c cVar2;
        boolean z;
        q qVar;
        m mVar2;
        h hVar = (h) menuItem;
        boolean zB = false;
        if (hVar == null || !hVar.isEnabled()) {
            return false;
        }
        MenuItem.OnMenuItemClickListener onMenuItemClickListener = hVar.p;
        if (onMenuItemClickListener == null || !onMenuItemClickListener.onMenuItemClick(hVar)) {
            g gVar = hVar.n;
            if (gVar.e(gVar, hVar)) {
                zExpandActionView = true;
            } else {
                Intent intent = hVar.g;
                if (intent != null) {
                    try {
                        hVar.n.f225a.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Log.e("MenuItemImpl", "Can't find activity to handle intent; ignoring", e);
                        cVar = hVar.A;
                        if (cVar == null) {
                        }
                        zExpandActionView = false;
                        cVar2 = hVar.A;
                        if (cVar2 == null) {
                            z = false;
                        } else {
                            z = false;
                        }
                        if (hVar.l()) {
                            zExpandActionView |= hVar.expandActionView();
                            if (zExpandActionView) {
                                c(true);
                            }
                        } else if (hVar.hasSubMenu()) {
                            if ((i & 4) == 0) {
                                c(false);
                            }
                            if (!hVar.hasSubMenu()) {
                                q qVar2 = new q(this.f225a, this, hVar);
                                hVar.o = qVar2;
                                qVar2.setHeaderTitle(hVar.e);
                            }
                            qVar = hVar.o;
                            if (z) {
                                cVar2.f(qVar);
                            }
                            if (!this.r.isEmpty()) {
                                if (mVar != null) {
                                }
                                for (WeakReference<m> weakReference : this.r) {
                                    mVar2 = weakReference.get();
                                    if (mVar2 == null) {
                                        this.r.remove(weakReference);
                                    } else if (!zB) {
                                        zB = mVar2.b(qVar);
                                    }
                                }
                            }
                            zExpandActionView |= zB;
                            if (!zExpandActionView) {
                                c(true);
                            }
                        } else {
                            if ((i & 4) == 0) {
                                c(false);
                            }
                            if (!hVar.hasSubMenu()) {
                                q qVar3 = new q(this.f225a, this, hVar);
                                hVar.o = qVar3;
                                qVar3.setHeaderTitle(hVar.e);
                            }
                            qVar = hVar.o;
                            if (z) {
                                cVar2.f(qVar);
                            }
                            if (!this.r.isEmpty()) {
                                zB = mVar != null ? mVar.b(qVar) : false;
                                while (r8.hasNext()) {
                                    mVar2 = weakReference.get();
                                    if (mVar2 == null) {
                                        this.r.remove(weakReference);
                                    } else if (!zB) {
                                        zB = mVar2.b(qVar);
                                    }
                                }
                            }
                            zExpandActionView |= zB;
                            if (!zExpandActionView) {
                                c(true);
                            }
                        }
                        return zExpandActionView;
                    }
                    zExpandActionView = true;
                } else {
                    cVar = hVar.A;
                    if (cVar == null && cVar.e()) {
                        zExpandActionView = true;
                    } else {
                        zExpandActionView = false;
                    }
                }
            }
        } else {
            zExpandActionView = true;
        }
        cVar2 = hVar.A;
        if (cVar2 == null && cVar2.a()) {
            z = true;
        } else {
            z = false;
        }
        if (hVar.l()) {
            zExpandActionView |= hVar.expandActionView();
            if (zExpandActionView) {
                c(true);
            }
        } else if (hVar.hasSubMenu() || z) {
            if ((i & 4) == 0) {
                c(false);
            }
            if (!hVar.hasSubMenu()) {
                q qVar4 = new q(this.f225a, this, hVar);
                hVar.o = qVar4;
                qVar4.setHeaderTitle(hVar.e);
            }
            qVar = hVar.o;
            if (z) {
                cVar2.f(qVar);
            }
            if (!this.r.isEmpty()) {
                if (mVar != null) {
                }
                while (r8.hasNext()) {
                    mVar2 = weakReference.get();
                    if (mVar2 == null) {
                        this.r.remove(weakReference);
                    } else if (!zB) {
                        zB = mVar2.b(qVar);
                    }
                }
            }
            zExpandActionView |= zB;
            if (!zExpandActionView) {
                c(true);
            }
        } else if ((i & 1) == 0) {
            c(true);
        }
        return zExpandActionView;
    }

    @Override // android.view.Menu
    public void removeGroup(int i) {
        int size = size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            } else if (this.f.get(i2).f229b == i) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 >= 0) {
            int size2 = this.f.size() - i2;
            int i3 = 0;
            while (true) {
                int i4 = i3 + 1;
                if (i3 >= size2 || this.f.get(i2).f229b != i) {
                    break;
                }
                s(i2, false);
                i3 = i4;
            }
            p(true);
        }
    }

    @Override // android.view.Menu
    public void removeItem(int i) {
        int size = size();
        int i2 = 0;
        while (i2 < size) {
            if (this.f.get(i2).f228a == i) {
                s(i2, true);
            }
            i2++;
        }
        i2 = -1;
        s(i2, true);
    }

    public final void s(int i, boolean z) {
        if (i < 0 || i >= this.f.size()) {
            return;
        }
        this.f.remove(i);
        if (z) {
            p(true);
        }
    }

    @Override // android.view.Menu
    public void setGroupCheckable(int i, boolean z, boolean z2) {
        int size = this.f.size();
        for (int i2 = 0; i2 < size; i2++) {
            h hVar = this.f.get(i2);
            if (hVar.f229b == i) {
                hVar.x = (hVar.x & (-5)) | (z2 ? 4 : 0);
                hVar.setCheckable(z);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupEnabled(int i, boolean z) {
        int size = this.f.size();
        for (int i2 = 0; i2 < size; i2++) {
            h hVar = this.f.get(i2);
            if (hVar.f229b == i) {
                hVar.setEnabled(z);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupVisible(int i, boolean z) {
        int size = this.f.size();
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            h hVar = this.f.get(i2);
            if (hVar.f229b == i && hVar.r(z)) {
                z2 = true;
            }
        }
        if (z2) {
            p(true);
        }
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z) {
        this.f227c = z;
        p(false);
    }

    @Override // android.view.Menu
    public int size() {
        return this.f.size();
    }

    public final void t(int i, CharSequence charSequence, int i2, Drawable drawable, View view) {
        Resources resources = this.f226b;
        if (view != null) {
            this.l = null;
        } else {
            if (i > 0) {
                this.l = resources.getText(i);
            } else if (charSequence != null) {
                this.l = charSequence;
            }
            if (i2 > 0) {
                a.b.d.b.a.b(this.f225a, i2);
            }
        }
        p(false);
    }

    public void u() {
        this.m = false;
        if (this.n) {
            this.n = false;
            p(this.o);
        }
    }

    public void v() {
        if (this.m) {
            return;
        }
        this.m = true;
        this.n = false;
        this.o = false;
    }
}
