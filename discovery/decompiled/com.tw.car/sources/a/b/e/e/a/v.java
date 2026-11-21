package a.b.e.e.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class v extends c<a.b.d.d.a.a> implements Menu {
    public v(Context context, a.b.d.d.a.a aVar) {
        super(context, aVar);
    }

    @Override // android.view.Menu
    public MenuItem add(int i) {
        return a(((a.b.d.d.a.a) this.f213a).add(i));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, int i4) {
        return a(((a.b.d.d.a.a) this.f213a).add(i, i2, i3, i4));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return a(((a.b.d.d.a.a) this.f213a).add(i, i2, i3, charSequence));
    }

    @Override // android.view.Menu
    public MenuItem add(CharSequence charSequence) {
        return a(((a.b.d.d.a.a) this.f213a).add(charSequence));
    }

    @Override // android.view.Menu
    public int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        MenuItem[] menuItemArr2 = menuItemArr != null ? new MenuItem[menuItemArr.length] : null;
        int iAddIntentOptions = ((a.b.d.d.a.a) this.f213a).addIntentOptions(i, i2, i3, componentName, intentArr, intent, i4, menuItemArr2);
        if (menuItemArr2 != null) {
            int length = menuItemArr2.length;
            for (int i5 = 0; i5 < length; i5++) {
                menuItemArr[i5] = a(menuItemArr2[i5]);
            }
        }
        return iAddIntentOptions;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i) {
        return a(((a.b.d.d.a.a) this.f213a).addSubMenu(i));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return a(((a.b.d.d.a.a) this.f213a).addSubMenu(i, i2, i3, i4));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        return a(((a.b.d.d.a.a) this.f213a).addSubMenu(i, i2, i3, charSequence));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(CharSequence charSequence) {
        return a(((a.b.d.d.a.a) this.f213a).addSubMenu(charSequence));
    }

    @Override // android.view.Menu
    public void clear() {
        Map<a.b.d.d.a.b, MenuItem> map = this.f212c;
        if (map != null) {
            map.clear();
        }
        Map<a.b.d.d.a.c, SubMenu> map2 = this.d;
        if (map2 != null) {
            map2.clear();
        }
        ((a.b.d.d.a.a) this.f213a).clear();
    }

    @Override // android.view.Menu
    public void close() {
        ((a.b.d.d.a.a) this.f213a).close();
    }

    @Override // android.view.Menu
    public MenuItem findItem(int i) {
        return a(((a.b.d.d.a.a) this.f213a).findItem(i));
    }

    @Override // android.view.Menu
    public MenuItem getItem(int i) {
        return a(((a.b.d.d.a.a) this.f213a).getItem(i));
    }

    @Override // android.view.Menu
    public boolean hasVisibleItems() {
        return ((a.b.d.d.a.a) this.f213a).hasVisibleItems();
    }

    @Override // android.view.Menu
    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return ((a.b.d.d.a.a) this.f213a).isShortcutKey(i, keyEvent);
    }

    @Override // android.view.Menu
    public boolean performIdentifierAction(int i, int i2) {
        return ((a.b.d.d.a.a) this.f213a).performIdentifierAction(i, i2);
    }

    @Override // android.view.Menu
    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        return ((a.b.d.d.a.a) this.f213a).performShortcut(i, keyEvent, i2);
    }

    @Override // android.view.Menu
    public void removeGroup(int i) {
        Map<a.b.d.d.a.b, MenuItem> map = this.f212c;
        if (map != null) {
            Iterator<a.b.d.d.a.b> it = map.keySet().iterator();
            while (it.hasNext()) {
                if (i == it.next().getGroupId()) {
                    it.remove();
                }
            }
        }
        ((a.b.d.d.a.a) this.f213a).removeGroup(i);
    }

    @Override // android.view.Menu
    public void removeItem(int i) {
        Map<a.b.d.d.a.b, MenuItem> map = this.f212c;
        if (map != null) {
            Iterator<a.b.d.d.a.b> it = map.keySet().iterator();
            while (it.hasNext()) {
                if (i == it.next().getItemId()) {
                    it.remove();
                    break;
                }
            }
        }
        ((a.b.d.d.a.a) this.f213a).removeItem(i);
    }

    @Override // android.view.Menu
    public void setGroupCheckable(int i, boolean z, boolean z2) {
        ((a.b.d.d.a.a) this.f213a).setGroupCheckable(i, z, z2);
    }

    @Override // android.view.Menu
    public void setGroupEnabled(int i, boolean z) {
        ((a.b.d.d.a.a) this.f213a).setGroupEnabled(i, z);
    }

    @Override // android.view.Menu
    public void setGroupVisible(int i, boolean z) {
        ((a.b.d.d.a.a) this.f213a).setGroupVisible(i, z);
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z) {
        ((a.b.d.d.a.a) this.f213a).setQwertyMode(z);
    }

    @Override // android.view.Menu
    public int size() {
        return ((a.b.d.d.a.a) this.f213a).size();
    }
}
