package a.b.e.e;

import a.b.d.h.c;
import a.b.e.e.c.h;
import a.b.e.e.c.i;
import a.b.e.f.p;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class b extends MenuInflater {
    public static final Class<?>[] e;
    public static final Class<?>[] f;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Object[] f197a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Object[] f198b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Context f199c;
    public Object d;

    public static class a implements MenuItem.OnMenuItemClickListener {

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public static final Class<?>[] f200c = {MenuItem.class};

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public Object f201a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public Method f202b;

        public a(Object obj, String str) {
            this.f201a = obj;
            Class<?> cls = obj.getClass();
            try {
                this.f202b = cls.getMethod(str, f200c);
            } catch (Exception e) {
                InflateException inflateException = new InflateException("Couldn't resolve menu item onClick handler " + str + " in class " + cls.getName());
                inflateException.initCause(e);
                throw inflateException;
            }
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.f202b.getReturnType() == Boolean.TYPE) {
                    return ((Boolean) this.f202b.invoke(this.f201a, menuItem)).booleanValue();
                }
                this.f202b.invoke(this.f201a, menuItem);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* JADX INFO: renamed from: a.b.e.e.b$b, reason: collision with other inner class name */
    public class C0012b {
        public c A;
        public CharSequence B;
        public CharSequence C;

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public Menu f203a;
        public boolean h;
        public int i;
        public int j;
        public CharSequence k;
        public CharSequence l;
        public int m;
        public char n;
        public int o;
        public char p;
        public int q;
        public int r;
        public boolean s;
        public boolean t;
        public boolean u;
        public int v;
        public int w;
        public String x;
        public String y;
        public String z;
        public ColorStateList D = null;
        public PorterDuff.Mode E = null;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f204b = 0;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f205c = 0;
        public int d = 0;
        public int e = 0;
        public boolean f = true;
        public boolean g = true;

        public C0012b(Menu menu) {
            this.f203a = menu;
        }

        public SubMenu a() {
            this.h = true;
            SubMenu subMenuAddSubMenu = this.f203a.addSubMenu(this.f204b, this.i, this.j, this.k);
            c(subMenuAddSubMenu.getItem());
            return subMenuAddSubMenu;
        }

        public final <T> T b(String str, Class<?>[] clsArr, Object[] objArr) {
            try {
                Constructor<?> constructor = b.this.f199c.getClassLoader().loadClass(str).getConstructor(clsArr);
                constructor.setAccessible(true);
                return (T) constructor.newInstance(objArr);
            } catch (Exception e) {
                Log.w("SupportMenuInflater", "Cannot instantiate class: " + str, e);
                return null;
            }
        }

        public final void c(MenuItem menuItem) {
            boolean z = false;
            menuItem.setChecked(this.s).setVisible(this.t).setEnabled(this.u).setCheckable(this.r >= 1).setTitleCondensed(this.l).setIcon(this.m);
            int i = this.v;
            if (i >= 0) {
                menuItem.setShowAsAction(i);
            }
            if (this.z != null) {
                if (b.this.f199c.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                b bVar = b.this;
                if (bVar.d == null) {
                    bVar.d = bVar.findRealOwner(bVar.f199c);
                }
                menuItem.setOnMenuItemClickListener(new a(bVar.d, this.z));
            }
            boolean z2 = menuItem instanceof h;
            if (z2) {
            }
            if (this.r >= 2) {
                if (z2) {
                    h hVar = (h) menuItem;
                    hVar.x = (hVar.x & (-5)) | 4;
                } else if (menuItem instanceof i) {
                    i iVar = (i) menuItem;
                    try {
                        if (iVar.e == null) {
                            iVar.e = ((a.b.d.d.a.b) iVar.f213a).getClass().getDeclaredMethod("setExclusiveCheckable", Boolean.TYPE);
                        }
                        iVar.e.invoke(iVar.f213a, Boolean.TRUE);
                    } catch (Exception e) {
                        Log.w("MenuItemWrapper", "Error while calling setExclusiveCheckable", e);
                    }
                }
            }
            String str = this.x;
            if (str != null) {
                menuItem.setActionView((View) b(str, b.e, b.this.f197a));
                z = true;
            }
            int i2 = this.w;
            if (i2 > 0) {
                if (z) {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                } else {
                    menuItem.setActionView(i2);
                }
            }
            c cVar = this.A;
            if (cVar != null) {
                if (menuItem instanceof a.b.d.d.a.b) {
                    ((a.b.d.d.a.b) menuItem).e(cVar);
                } else {
                    Log.w("MenuItemCompat", "setActionProvider: item does not implement SupportMenuItem; ignoring");
                }
            }
            a.b.a.a.G(menuItem, this.B);
            a.b.a.a.V(menuItem, this.C);
            a.b.a.a.E(menuItem, this.n, this.o);
            a.b.a.a.O(menuItem, this.p, this.q);
            PorterDuff.Mode mode = this.E;
            if (mode != null) {
                a.b.a.a.L(menuItem, mode);
            }
            ColorStateList colorStateList = this.D;
            if (colorStateList != null) {
                a.b.a.a.K(menuItem, colorStateList);
            }
        }
    }

    static {
        Class<?>[] clsArr = {Context.class};
        e = clsArr;
        f = clsArr;
    }

    public b(Context context) {
        super(context);
        this.f199c = context;
        Object[] objArr = {context};
        this.f197a = objArr;
        this.f198b = objArr;
    }

    public final Object findRealOwner(Object obj) {
        return (!(obj instanceof Activity) && (obj instanceof ContextWrapper)) ? findRealOwner(((ContextWrapper) obj).getBaseContext()) : obj;
    }

    @Override // android.view.MenuInflater
    public void inflate(int i, Menu menu) {
        if (!(menu instanceof a.b.d.d.a.a)) {
            super.inflate(i, menu);
            return;
        }
        XmlResourceParser layout = null;
        try {
            try {
                try {
                    layout = this.f199c.getResources().getLayout(i);
                    parseMenu(layout, Xml.asAttributeSet(layout), menu);
                    layout.close();
                } catch (XmlPullParserException e2) {
                    throw new InflateException("Error inflating menu XML", e2);
                }
            } catch (IOException e3) {
                throw new InflateException("Error inflating menu XML", e3);
            }
        } catch (Throwable th) {
            if (layout != null) {
                layout.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:45:0x00f3  */
    /* JADX WARN: Multi-variable type inference failed */
    public final void parseMenu(XmlPullParser xmlPullParser, AttributeSet attributeSet, Menu menu) throws XmlPullParserException, IOException {
        c cVar;
        ColorStateList colorStateList;
        C0012b c0012b = new C0012b(menu);
        int eventType = xmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("menu")) {
                    eventType = xmlPullParser.next();
                    break;
                }
                throw new RuntimeException("Expecting menu, got " + name);
            }
            eventType = xmlPullParser.next();
        } while (eventType != 1);
        boolean z = false;
        boolean z2 = false;
        String str = null;
        while (!z) {
            if (eventType == 1) {
                throw new RuntimeException("Unexpected end of document");
            }
            if (eventType != 2) {
                if (eventType == 3) {
                    String name2 = xmlPullParser.getName();
                    if (z2 && name2.equals(str)) {
                        z2 = false;
                        str = null;
                    } else if (name2.equals("group")) {
                        c0012b.f204b = 0;
                        c0012b.f205c = 0;
                        c0012b.d = 0;
                        c0012b.e = 0;
                        c0012b.f = true;
                        c0012b.g = true;
                    } else if (name2.equals("item")) {
                        if (!c0012b.h) {
                            c cVar2 = c0012b.A;
                            if (cVar2 == null || !cVar2.a()) {
                                c0012b.h = true;
                                c0012b.c(c0012b.f203a.add(c0012b.f204b, c0012b.i, c0012b.j, c0012b.k));
                            } else {
                                c0012b.a();
                            }
                        }
                    } else if (name2.equals("menu")) {
                        z = true;
                    }
                }
            } else if (!z2) {
                String name3 = xmlPullParser.getName();
                if (name3.equals("group")) {
                    TypedArray typedArrayObtainStyledAttributes = b.this.f199c.obtainStyledAttributes(attributeSet, a.b.e.b.b.o);
                    c0012b.f204b = typedArrayObtainStyledAttributes.getResourceId(1, 0);
                    c0012b.f205c = typedArrayObtainStyledAttributes.getInt(3, 0);
                    c0012b.d = typedArrayObtainStyledAttributes.getInt(4, 0);
                    c0012b.e = typedArrayObtainStyledAttributes.getInt(5, 0);
                    c0012b.f = typedArrayObtainStyledAttributes.getBoolean(2, true);
                    c0012b.g = typedArrayObtainStyledAttributes.getBoolean(0, true);
                    typedArrayObtainStyledAttributes.recycle();
                } else if (name3.equals("item")) {
                    TypedArray typedArrayObtainStyledAttributes2 = b.this.f199c.obtainStyledAttributes(attributeSet, a.b.e.b.b.p);
                    c0012b.i = typedArrayObtainStyledAttributes2.getResourceId(2, 0);
                    c0012b.j = (typedArrayObtainStyledAttributes2.getInt(5, c0012b.f205c) & (-65536)) | (typedArrayObtainStyledAttributes2.getInt(6, c0012b.d) & 65535);
                    c0012b.k = typedArrayObtainStyledAttributes2.getText(7);
                    c0012b.l = typedArrayObtainStyledAttributes2.getText(8);
                    c0012b.m = typedArrayObtainStyledAttributes2.getResourceId(0, 0);
                    String string = typedArrayObtainStyledAttributes2.getString(9);
                    c0012b.n = string == null ? (char) 0 : string.charAt(0);
                    c0012b.o = typedArrayObtainStyledAttributes2.getInt(16, 4096);
                    String string2 = typedArrayObtainStyledAttributes2.getString(10);
                    c0012b.p = string2 == null ? (char) 0 : string2.charAt(0);
                    c0012b.q = typedArrayObtainStyledAttributes2.getInt(20, 4096);
                    c0012b.r = typedArrayObtainStyledAttributes2.hasValue(11) ? typedArrayObtainStyledAttributes2.getBoolean(11, false) : c0012b.e;
                    c0012b.s = typedArrayObtainStyledAttributes2.getBoolean(3, false);
                    c0012b.t = typedArrayObtainStyledAttributes2.getBoolean(4, c0012b.f);
                    c0012b.u = typedArrayObtainStyledAttributes2.getBoolean(1, c0012b.g);
                    c0012b.v = typedArrayObtainStyledAttributes2.getInt(21, -1);
                    c0012b.z = typedArrayObtainStyledAttributes2.getString(12);
                    c0012b.w = typedArrayObtainStyledAttributes2.getResourceId(13, 0);
                    c0012b.x = typedArrayObtainStyledAttributes2.getString(15);
                    String string3 = typedArrayObtainStyledAttributes2.getString(14);
                    c0012b.y = string3;
                    boolean z3 = string3 != null;
                    if (z3 && c0012b.w == 0 && c0012b.x == null) {
                        cVar = (c) c0012b.b(string3, f, b.this.f198b);
                    } else {
                        if (z3) {
                            Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                        }
                        cVar = null;
                    }
                    c0012b.A = cVar;
                    c0012b.B = typedArrayObtainStyledAttributes2.getText(17);
                    c0012b.C = typedArrayObtainStyledAttributes2.getText(22);
                    if (typedArrayObtainStyledAttributes2.hasValue(19)) {
                        c0012b.E = p.c(typedArrayObtainStyledAttributes2.getInt(19, -1), c0012b.E);
                        colorStateList = null;
                    } else {
                        colorStateList = null;
                        c0012b.E = null;
                    }
                    if (typedArrayObtainStyledAttributes2.hasValue(18)) {
                        c0012b.D = typedArrayObtainStyledAttributes2.getColorStateList(18);
                    } else {
                        c0012b.D = colorStateList;
                    }
                    typedArrayObtainStyledAttributes2.recycle();
                    c0012b.h = false;
                } else if (name3.equals("menu")) {
                    parseMenu(xmlPullParser, attributeSet, c0012b.a());
                } else {
                    str = name3;
                    z2 = true;
                }
            }
            eventType = xmlPullParser.next();
        }
    }
}
