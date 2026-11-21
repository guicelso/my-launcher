package a.b.e.e.a;

import a.b.e.f.ia;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
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
public class k implements a.b.d.d.a.a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final int[] f228a = {1, 4, 5, 3, 2, 0};

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Context f229b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Resources f230c;
    public boolean d;
    public boolean e;
    public a f;
    public ContextMenu.ContextMenuInfo n;
    public CharSequence o;
    public n w;
    public boolean y;
    public int m = 0;
    public boolean p = false;
    public boolean q = false;
    public boolean r = false;
    public boolean s = false;
    public boolean t = false;
    public ArrayList<n> u = new ArrayList<>();
    public CopyOnWriteArrayList<WeakReference<t>> v = new CopyOnWriteArrayList<>();
    public boolean x = false;
    public ArrayList<n> g = new ArrayList<>();
    public ArrayList<n> h = new ArrayList<>();
    public boolean i = true;
    public ArrayList<n> j = new ArrayList<>();
    public ArrayList<n> k = new ArrayList<>();
    public boolean l = true;

    public interface a {
    }

    public interface b {
        boolean a(n nVar);
    }

    public k(Context context) {
        this.f229b = context;
        this.f230c = context.getResources();
        this.e = this.f230c.getConfiguration().keyboard != 1 && a.b.d.h.o.a(ViewConfiguration.get(this.f229b), this.f229b);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public MenuItem a(int i, int i2, int i3, CharSequence charSequence) {
        int i4;
        int i5 = ((-65536) & i3) >> 16;
        if (i5 >= 0) {
            int[] iArr = f228a;
            if (i5 < iArr.length) {
                int i6 = (iArr[i5] << 16) | (65535 & i3);
                n nVar = new n(this, i, i2, i3, i6, charSequence, this.m);
                ContextMenu.ContextMenuInfo contextMenuInfo = this.n;
                if (contextMenuInfo != null) {
                    nVar.E = contextMenuInfo;
                }
                ArrayList<n> arrayList = this.g;
                int size = arrayList.size();
                do {
                    size--;
                    if (size < 0) {
                        i4 = 0;
                    }
                    arrayList.add(i4, nVar);
                    b(true);
                    return nVar;
                } while (arrayList.get(size).d > i6);
                i4 = size + 1;
                arrayList.add(i4, nVar);
                b(true);
                return nVar;
            }
        }
        throw new IllegalArgumentException("order does not contain a valid category.");
    }

    public void a() {
        ArrayList<n> arrayListC = c();
        if (this.l) {
            boolean zFlagActionItems = false;
            for (WeakReference<t> weakReference : this.v) {
                t tVar = weakReference.get();
                if (tVar == null) {
                    this.v.remove(weakReference);
                } else {
                    zFlagActionItems |= tVar.flagActionItems();
                }
            }
            if (zFlagActionItems) {
                this.j.clear();
                this.k.clear();
                int size = arrayListC.size();
                for (int i = 0; i < size; i++) {
                    n nVar = arrayListC.get(i);
                    (nVar.d() ? this.j : this.k).add(nVar);
                }
            } else {
                this.j.clear();
                this.k.clear();
                this.k.addAll(c());
            }
            this.l = false;
        }
    }

    public final void a(int i, boolean z) {
        if (i < 0 || i >= this.g.size()) {
            return;
        }
        this.g.remove(i);
        if (z) {
            b(true);
        }
    }

    public void a(a aVar) {
        this.f = aVar;
    }

    public void a(t tVar, Context context) {
        this.v.add(new WeakReference<>(tVar));
        tVar.a(context, this);
        this.l = true;
    }

    public final void a(boolean z) {
        if (this.t) {
            return;
        }
        this.t = true;
        for (WeakReference<t> weakReference : this.v) {
            t tVar = weakReference.get();
            if (tVar == null) {
                this.v.remove(weakReference);
            } else {
                tVar.a(this, z);
            }
        }
        this.t = false;
    }

    public boolean a(n nVar) {
        boolean zB = false;
        if (!this.v.isEmpty() && this.w == nVar) {
            h();
            for (WeakReference<t> weakReference : this.v) {
                t tVar = weakReference.get();
                if (tVar != null) {
                    zB = tVar.b(this, nVar);
                    if (zB) {
                        break;
                    }
                } else {
                    this.v.remove(weakReference);
                }
            }
            g();
            if (zB) {
                this.w = null;
            }
        }
        return zB;
    }

    public boolean a(MenuItem menuItem, int i) {
        return a(menuItem, (t) null, i);
    }

    @Override // android.view.Menu
    public MenuItem add(int i) {
        return a(0, 0, 0, this.f230c.getString(i));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, int i4) {
        return a(i, i2, i3, this.f230c.getString(i4));
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
        PackageManager packageManager = this.f229b.getPackageManager();
        List<ResolveInfo> listQueryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = listQueryIntentActivityOptions != null ? listQueryIntentActivityOptions.size() : 0;
        if ((i4 & 1) == 0) {
            int size2 = size();
            int i6 = 0;
            while (true) {
                if (i6 >= size2) {
                    i6 = -1;
                    break;
                }
                if (this.g.get(i6).f233b == i) {
                    break;
                }
                i6++;
            }
            if (i6 >= 0) {
                int size3 = this.g.size() - i6;
                int i7 = 0;
                while (true) {
                    int i8 = i7 + 1;
                    if (i7 >= size3 || this.g.get(i6).f233b != i) {
                        break;
                    }
                    a(i6, false);
                    i7 = i8;
                }
                b(true);
            }
        }
        for (int i9 = 0; i9 < size; i9++) {
            ResolveInfo resolveInfo = listQueryIntentActivityOptions.get(i9);
            int i10 = resolveInfo.specificIndex;
            Intent intent2 = new Intent(i10 < 0 ? intent : intentArr[i10]);
            intent2.setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
            MenuItem intent3 = a(i, i2, i3, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent(intent2);
            if (menuItemArr != null && (i5 = resolveInfo.specificIndex) >= 0) {
                menuItemArr[i5] = intent3;
            }
        }
        return size;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i) {
        return addSubMenu(0, 0, 0, this.f230c.getString(i));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return addSubMenu(i, i2, i3, this.f230c.getString(i4));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        n nVar = (n) a(i, i2, i3, charSequence);
        A a2 = new A(this.f229b, this, nVar);
        nVar.o = a2;
        a2.setHeaderTitle(nVar.e);
        return a2;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    public k b() {
        return this;
    }

    public void b(boolean z) {
        if (this.p) {
            this.q = true;
            if (z) {
                this.r = true;
                return;
            }
            return;
        }
        if (z) {
            this.i = true;
            this.l = true;
        }
        if (this.v.isEmpty()) {
            return;
        }
        h();
        for (WeakReference<t> weakReference : this.v) {
            t tVar = weakReference.get();
            if (tVar == null) {
                this.v.remove(weakReference);
            } else {
                tVar.updateMenuView(z);
            }
        }
        g();
    }

    public boolean b(n nVar) {
        boolean zA = false;
        if (this.v.isEmpty()) {
            return false;
        }
        h();
        for (WeakReference<t> weakReference : this.v) {
            t tVar = weakReference.get();
            if (tVar != null) {
                zA = tVar.a(this, nVar);
                if (zA) {
                    break;
                }
            } else {
                this.v.remove(weakReference);
            }
        }
        g();
        if (zA) {
            this.w = nVar;
        }
        return zA;
    }

    public ArrayList<n> c() {
        if (!this.i) {
            return this.h;
        }
        this.h.clear();
        int size = this.g.size();
        for (int i = 0; i < size; i++) {
            n nVar = this.g.get(i);
            if (nVar.isVisible()) {
                this.h.add(nVar);
            }
        }
        this.i = false;
        this.l = true;
        return this.h;
    }

    public void c(n nVar) {
        this.i = true;
        b(true);
    }

    @Override // android.view.Menu
    public void clear() {
        n nVar = this.w;
        if (nVar != null) {
            a(nVar);
        }
        this.g.clear();
        b(true);
    }

    public void clearHeader() {
        this.o = null;
        b(false);
    }

    @Override // android.view.Menu
    public void close() {
        a(true);
    }

    public boolean d() {
        return this.x;
    }

    public boolean e() {
        return this.d;
    }

    public boolean f() {
        return this.e;
    }

    @Override // android.view.Menu
    public MenuItem findItem(int i) {
        MenuItem menuItemFindItem;
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            n nVar = this.g.get(i2);
            if (nVar.f232a == i) {
                return nVar;
            }
            if (nVar.hasSubMenu() && (menuItemFindItem = nVar.o.findItem(i)) != null) {
                return menuItemFindItem;
            }
        }
        return null;
    }

    public void g() {
        this.p = false;
        if (this.q) {
            this.q = false;
            b(this.r);
        }
    }

    @Override // android.view.Menu
    public MenuItem getItem(int i) {
        return this.g.get(i);
    }

    public void h() {
        if (this.p) {
            return;
        }
        this.p = true;
        this.q = false;
        this.r = false;
    }

    @Override // android.view.Menu
    public boolean hasVisibleItems() {
        if (this.y) {
            return true;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.g.get(i).isVisible()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.Menu
    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return a(i, keyEvent) != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.view.MenuItem] */
    /* JADX WARN: Type inference failed for: r2v4, types: [a.b.e.e.a.n] */
    /* JADX WARN: Type inference failed for: r2v6, types: [android.view.MenuItem] */
    /* JADX WARN: Type inference failed for: r4v0, types: [a.b.e.e.a.k] */
    @Override // android.view.Menu
    public boolean performIdentifierAction(int i, int i2) {
        n nVarFindItem;
        int size = size();
        for (int i3 = 0; i3 < size; i3++) {
            nVarFindItem = this.g.get(i3);
            if (nVarFindItem.f232a == i || (nVarFindItem.hasSubMenu() && (nVarFindItem = nVarFindItem.o.findItem(i)) != 0)) {
                return a(nVarFindItem, i2);
            }
        }
        nVarFindItem = 0;
        return a(nVarFindItem, i2);
    }

    @Override // android.view.Menu
    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        n nVarA = a(i, keyEvent);
        boolean zA = nVarA != null ? a(nVarA, (t) null, i2) : false;
        if ((i2 & 2) != 0) {
            a(true);
        }
        return zA;
    }

    @Override // android.view.Menu
    public void removeGroup(int i) {
        int size = size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            } else if (this.g.get(i2).f233b == i) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 >= 0) {
            int size2 = this.g.size() - i2;
            int i3 = 0;
            while (true) {
                int i4 = i3 + 1;
                if (i3 >= size2 || this.g.get(i2).f233b != i) {
                    break;
                }
                a(i2, false);
                i3 = i4;
            }
            b(true);
        }
    }

    @Override // android.view.Menu
    public void removeItem(int i) {
        int size = size();
        int i2 = 0;
        while (i2 < size) {
            if (this.g.get(i2).f232a == i) {
                a(i2, true);
            }
            i2++;
        }
        i2 = -1;
        a(i2, true);
    }

    @Override // android.view.Menu
    public void setGroupCheckable(int i, boolean z, boolean z2) {
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            n nVar = this.g.get(i2);
            if (nVar.f233b == i) {
                nVar.y = (nVar.y & (-5)) | (z2 ? 4 : 0);
                int i3 = nVar.y;
                nVar.y = ((i3 & (-2)) | (z ? 1 : 0)) == true ? 1 : 0;
                if (i3 != nVar.y) {
                    nVar.n.b(false);
                }
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupEnabled(int i, boolean z) {
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            n nVar = this.g.get(i2);
            if (nVar.f233b == i) {
                nVar.y = z ? nVar.y | 16 : nVar.y & (-17);
                nVar.n.b(false);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupVisible(int i, boolean z) {
        int size = this.g.size();
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            n nVar = this.g.get(i2);
            if (nVar.f233b == i && nVar.d(z)) {
                z2 = true;
            }
        }
        if (z2) {
            b(true);
        }
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z) {
        this.d = z;
        b(false);
    }

    @Override // android.view.Menu
    public int size() {
        return this.g.size();
    }

    public boolean a(k kVar, MenuItem menuItem) {
        ActionMenuView.e eVar;
        a aVar = this.f;
        if (aVar == null || (eVar = ActionMenuView.this.z) == null) {
            return false;
        }
        Toolbar toolbar = ((ia) eVar).f342a;
        return false;
    }

    public n a(int i, KeyEvent keyEvent) {
        char c2;
        ArrayList<n> arrayList = this.u;
        arrayList.clear();
        a(arrayList, i, keyEvent);
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
        boolean zE = e();
        for (int i2 = 0; i2 < size; i2++) {
            n nVar = arrayList.get(i2);
            if (zE) {
                c2 = nVar.j;
            } else {
                c2 = nVar.h;
            }
            if ((c2 == keyData.meta[0] && (metaState & 2) == 0) || ((c2 == keyData.meta[2] && (metaState & 2) != 0) || (zE && c2 == '\b' && i == 67))) {
                return nVar;
            }
        }
        return null;
    }

    public void a(List<n> list, int i, KeyEvent keyEvent) {
        char c2;
        int i2;
        boolean zE = e();
        int modifiers = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (keyEvent.getKeyData(keyData) || i == 67) {
            int size = this.g.size();
            for (int i3 = 0; i3 < size; i3++) {
                n nVar = this.g.get(i3);
                if (nVar.hasSubMenu()) {
                    nVar.o.a(list, i, keyEvent);
                }
                if (zE) {
                    c2 = nVar.j;
                } else {
                    c2 = nVar.h;
                }
                if (zE) {
                    i2 = nVar.k;
                } else {
                    i2 = nVar.i;
                }
                if (((modifiers & 69647) == (i2 & 69647)) && c2 != 0) {
                    char[] cArr = keyData.meta;
                    if ((c2 == cArr[0] || c2 == cArr[2] || (zE && c2 == '\b' && i == 67)) && nVar.isEnabled()) {
                        list.add(nVar);
                    }
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:28:0x004c  */
    /* JADX WARN: Code duplicated, block: B:35:0x0060  */
    /* JADX WARN: Code duplicated, block: B:38:0x0067  */
    /* JADX WARN: Code duplicated, block: B:49:0x0083  */
    /* JADX WARN: Code duplicated, block: B:56:0x0091  */
    /* JADX WARN: Code duplicated, block: B:58:0x0095  */
    /* JADX WARN: Code duplicated, block: B:61:0x009e  */
    /* JADX WARN: Code duplicated, block: B:64:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:68:0x00c6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:69:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:73:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:80:0x00f6 A[PHI: r1
      0x00f6: PHI (r1v14 boolean) = (r1v13 boolean), (r1v12 boolean), (r1v16 boolean) binds: [B:79:0x00f4, B:54:0x008e, B:47:0x007f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:86:0x00ec A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:87:0x00e6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:88:0x00ee A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:90:0x00d2 A[SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:28:0x004c, please report this as an issue */
    public boolean a(MenuItem menuItem, t tVar, int i) {
        boolean z;
        a.b.d.h.e eVar;
        boolean z2;
        A a2;
        t tVar2;
        MenuItem.OnActionExpandListener onActionExpandListener;
        n nVar = (n) menuItem;
        boolean zA = false;
        if (nVar == null || !nVar.isEnabled()) {
            return false;
        }
        MenuItem.OnMenuItemClickListener onMenuItemClickListener = nVar.q;
        if (onMenuItemClickListener == null || !onMenuItemClickListener.onMenuItemClick(nVar)) {
            k kVar = nVar.n;
            if (!kVar.a(kVar, nVar)) {
                Runnable runnable = nVar.p;
                if (runnable != null) {
                    runnable.run();
                } else {
                    Intent intent = nVar.g;
                    if (intent != null) {
                        try {
                            nVar.n.f229b.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Log.e("MenuItemImpl", "Can't find activity to handle intent; ignoring", e);
                            a.b.d.h.e eVar2 = nVar.B;
                            if (eVar2 == null) {
                            }
                            eVar = nVar.B;
                            if (eVar == null) {
                                z2 = false;
                            } else {
                                z2 = false;
                            }
                            if (nVar.c()) {
                                if (nVar.c()) {
                                    zA = nVar.n.b(nVar);
                                }
                                z |= zA;
                                if (z) {
                                    a(true);
                                }
                            } else if (nVar.hasSubMenu()) {
                                if ((i & 4) == 0) {
                                    a(false);
                                }
                                if (!nVar.hasSubMenu()) {
                                    A a3 = new A(this.f229b, this, nVar);
                                    nVar.o = a3;
                                    a3.setHeaderTitle(nVar.e);
                                }
                                a2 = nVar.o;
                                if (z2) {
                                    o.a aVar = (o.a) eVar;
                                    aVar.f235b.onPrepareSubMenu(o.this.a(a2));
                                }
                                if (!this.v.isEmpty()) {
                                    zA = tVar != null ? tVar.a(a2) : false;
                                    for (WeakReference<t> weakReference : this.v) {
                                        tVar2 = weakReference.get();
                                        if (tVar2 == null) {
                                            this.v.remove(weakReference);
                                        } else if (!zA) {
                                            zA = tVar2.a(a2);
                                        }
                                    }
                                }
                                z |= zA;
                                if (!z) {
                                    a(true);
                                }
                            } else {
                                if ((i & 4) == 0) {
                                    a(false);
                                }
                                if (!nVar.hasSubMenu()) {
                                    A a4 = new A(this.f229b, this, nVar);
                                    nVar.o = a4;
                                    a4.setHeaderTitle(nVar.e);
                                }
                                a2 = nVar.o;
                                if (z2) {
                                    o.a aVar2 = (o.a) eVar;
                                    aVar2.f235b.onPrepareSubMenu(o.this.a(a2));
                                }
                                if (!this.v.isEmpty()) {
                                    if (tVar != null) {
                                    }
                                    while (r8.hasNext()) {
                                        tVar2 = weakReference.get();
                                        if (tVar2 == null) {
                                            this.v.remove(weakReference);
                                        } else if (!zA) {
                                            zA = tVar2.a(a2);
                                        }
                                    }
                                }
                                z |= zA;
                                if (!z) {
                                    a(true);
                                }
                            }
                            return z;
                        }
                    } else {
                        a.b.d.h.e eVar3 = nVar.B;
                        z = eVar3 == null && ((o.a) eVar3).f235b.onPerformDefaultAction();
                    }
                }
            }
        }
        eVar = nVar.B;
        if (eVar == null && ((o.a) eVar).f235b.hasSubMenu()) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (nVar.c()) {
            if (nVar.c() && ((onActionExpandListener = nVar.C) == null || onActionExpandListener.onMenuItemActionExpand(nVar))) {
                zA = nVar.n.b(nVar);
            }
            z |= zA;
            if (z) {
                a(true);
            }
        } else if (!nVar.hasSubMenu() || z2) {
            if ((i & 4) == 0) {
                a(false);
            }
            if (!nVar.hasSubMenu()) {
                A a5 = new A(this.f229b, this, nVar);
                nVar.o = a5;
                a5.setHeaderTitle(nVar.e);
            }
            a2 = nVar.o;
            if (z2) {
                o.a aVar3 = (o.a) eVar;
                aVar3.f235b.onPrepareSubMenu(o.this.a(a2));
            }
            if (!this.v.isEmpty()) {
                if (tVar != null) {
                }
                while (r8.hasNext()) {
                    tVar2 = weakReference.get();
                    if (tVar2 == null) {
                        this.v.remove(weakReference);
                    } else if (!zA) {
                        zA = tVar2.a(a2);
                    }
                }
            }
            z |= zA;
            if (!z) {
                a(true);
            }
        } else if ((i & 1) == 0) {
            a(true);
        }
        return z;
    }

    public void a(MenuItem menuItem) {
        int groupId = menuItem.getGroupId();
        int size = this.g.size();
        h();
        for (int i = 0; i < size; i++) {
            n nVar = this.g.get(i);
            if (nVar.f233b == groupId && nVar.e()) {
                if ((nVar.y & 1) == 1) {
                    nVar.b(nVar == menuItem);
                }
            }
        }
        g();
    }

    public final void a(int i, CharSequence charSequence, int i2, Drawable drawable, View view) {
        Resources resources = this.f230c;
        if (view != null) {
            this.o = null;
        } else {
            if (i > 0) {
                this.o = resources.getText(i);
            } else if (charSequence != null) {
                this.o = charSequence;
            }
            if (i2 > 0) {
                a.b.d.b.a.b(this.f229b, i2);
            }
        }
        b(false);
    }
}
