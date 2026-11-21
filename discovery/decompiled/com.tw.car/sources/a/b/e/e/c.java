package a.b.e.e;

import a.b.d.h.e;
import a.b.e.b.j;
import a.b.e.e.a.n;
import a.b.e.e.a.o;
import a.b.e.f.A;
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
public class c extends MenuInflater {
    public final Object[] mActionProviderConstructorArguments;
    public final Object[] mActionViewConstructorArguments;
    public Context mContext;
    public Object mRealOwner;
    public static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE = {Context.class};
    public static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = ACTION_VIEW_CONSTRUCTOR_SIGNATURE;

    private static class a implements MenuItem.OnMenuItemClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public static final Class<?>[] f252a = {MenuItem.class};

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public Object f253b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public Method f254c;

        public a(Object obj, String str) {
            this.f253b = obj;
            Class<?> cls = obj.getClass();
            try {
                this.f254c = cls.getMethod(str, f252a);
            } catch (Exception e) {
                InflateException inflateException = new InflateException("Couldn't resolve menu item onClick handler " + str + " in class " + cls.getName());
                inflateException.initCause(e);
                throw inflateException;
            }
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.f254c.getReturnType() == Boolean.TYPE) {
                    return ((Boolean) this.f254c.invoke(this.f253b, menuItem)).booleanValue();
                }
                this.f254c.invoke(this.f253b, menuItem);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class b {
        public e A;
        public CharSequence B;
        public CharSequence C;

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public Menu f255a;
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
        public int f256b = 0;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f257c = 0;
        public int d = 0;
        public int e = 0;
        public boolean f = true;
        public boolean g = true;

        public b(Menu menu) {
            this.f255a = menu;
        }

        public SubMenu a() {
            this.h = true;
            SubMenu subMenuAddSubMenu = this.f255a.addSubMenu(this.f256b, this.i, this.j, this.k);
            a(subMenuAddSubMenu.getItem());
            return subMenuAddSubMenu;
        }

        public final <T> T a(String str, Class<?>[] clsArr, Object[] objArr) {
            try {
                Constructor<?> constructor = c.this.mContext.getClassLoader().loadClass(str).getConstructor(clsArr);
                constructor.setAccessible(true);
                return (T) constructor.newInstance(objArr);
            } catch (Exception e) {
                Log.w("SupportMenuInflater", "Cannot instantiate class: " + str, e);
                return null;
            }
        }

        public final void a(MenuItem menuItem) {
            boolean z = false;
            menuItem.setChecked(this.s).setVisible(this.t).setEnabled(this.u).setCheckable(this.r >= 1).setTitleCondensed(this.l).setIcon(this.m);
            int i = this.v;
            if (i >= 0) {
                menuItem.setShowAsAction(i);
            }
            if (this.z != null) {
                if (c.this.mContext.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                c cVar = c.this;
                if (cVar.mRealOwner == null) {
                    cVar.mRealOwner = cVar.findRealOwner(cVar.mContext);
                }
                menuItem.setOnMenuItemClickListener(new a(cVar.mRealOwner, this.z));
            }
            boolean z2 = menuItem instanceof n;
            if (z2) {
            }
            if (this.r >= 2) {
                if (z2) {
                    n nVar = (n) menuItem;
                    nVar.y = (nVar.y & (-5)) | 4;
                } else if (menuItem instanceof o) {
                    o oVar = (o) menuItem;
                    try {
                        if (oVar.e == null) {
                            oVar.e = ((a.b.d.d.a.b) oVar.f213a).getClass().getDeclaredMethod("setExclusiveCheckable", Boolean.TYPE);
                        }
                        oVar.e.invoke(oVar.f213a, true);
                    } catch (Exception e) {
                        Log.w("MenuItemWrapper", "Error while calling setExclusiveCheckable", e);
                    }
                }
            }
            String str = this.x;
            if (str != null) {
                menuItem.setActionView((View) a(str, c.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, c.this.mActionViewConstructorArguments));
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
            e eVar = this.A;
            if (eVar != null) {
                if (menuItem instanceof a.b.d.d.a.b) {
                    ((a.b.d.d.a.b) menuItem).a(eVar);
                } else {
                    Log.w("MenuItemCompat", "setActionProvider: item does not implement SupportMenuItem; ignoring");
                }
            }
            a.b.b.a.e.a(menuItem, this.B);
            a.b.b.a.e.b(menuItem, this.C);
            a.b.b.a.e.a(menuItem, this.n, this.o);
            a.b.b.a.e.b(menuItem, this.p, this.q);
            PorterDuff.Mode mode = this.E;
            if (mode != null) {
                a.b.b.a.e.a(menuItem, mode);
            }
            ColorStateList colorStateList = this.D;
            if (colorStateList != null) {
                a.b.b.a.e.a(menuItem, colorStateList);
            }
        }
    }

    public c(Context context) {
        super(context);
        this.mContext = context;
        this.mActionViewConstructorArguments = new Object[]{context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
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
                    layout = this.mContext.getResources().getLayout(i);
                    parseMenu(layout, Xml.asAttributeSet(layout), menu);
                    layout.close();
                } catch (XmlPullParserException e) {
                    throw new InflateException("Error inflating menu XML", e);
                }
            } catch (IOException e2) {
                throw new InflateException("Error inflating menu XML", e2);
            }
        } catch (Throwable th) {
            if (layout != null) {
                layout.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:82:0x0248  */
    /* JADX WARN: Multi-variable type inference failed */
    public final void parseMenu(XmlPullParser xmlPullParser, AttributeSet attributeSet, Menu menu) throws XmlPullParserException, IOException {
        b bVar = new b(menu);
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
        int next = eventType;
        String str = null;
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            if (next == 1) {
                throw new RuntimeException("Unexpected end of document");
            }
            if (next != 2) {
                if (next == 3) {
                    String name2 = xmlPullParser.getName();
                    if (z2 && name2.equals(str)) {
                        str = null;
                        z2 = false;
                    } else if (name2.equals("group")) {
                        bVar.f256b = 0;
                        bVar.f257c = 0;
                        bVar.d = 0;
                        bVar.e = 0;
                        bVar.f = true;
                        bVar.g = true;
                    } else if (name2.equals("item")) {
                        if (!bVar.h) {
                            e eVar = bVar.A;
                            if (eVar == null || !((o.a) eVar).f235b.hasSubMenu()) {
                                bVar.h = true;
                                bVar.a(bVar.f255a.add(bVar.f256b, bVar.i, bVar.j, bVar.k));
                            } else {
                                bVar.a();
                            }
                        }
                    } else if (name2.equals("menu")) {
                        z = true;
                    }
                }
            } else if (!z2) {
                String name3 = xmlPullParser.getName();
                if (name3.equals("group")) {
                    TypedArray typedArrayObtainStyledAttributes = c.this.mContext.obtainStyledAttributes(attributeSet, j.MenuGroup);
                    bVar.f256b = typedArrayObtainStyledAttributes.getResourceId(j.MenuGroup_android_id, 0);
                    bVar.f257c = typedArrayObtainStyledAttributes.getInt(j.MenuGroup_android_menuCategory, 0);
                    bVar.d = typedArrayObtainStyledAttributes.getInt(j.MenuGroup_android_orderInCategory, 0);
                    bVar.e = typedArrayObtainStyledAttributes.getInt(j.MenuGroup_android_checkableBehavior, 0);
                    bVar.f = typedArrayObtainStyledAttributes.getBoolean(j.MenuGroup_android_visible, true);
                    bVar.g = typedArrayObtainStyledAttributes.getBoolean(j.MenuGroup_android_enabled, true);
                    typedArrayObtainStyledAttributes.recycle();
                } else if (name3.equals("item")) {
                    TypedArray typedArrayObtainStyledAttributes2 = c.this.mContext.obtainStyledAttributes(attributeSet, j.MenuItem);
                    bVar.i = typedArrayObtainStyledAttributes2.getResourceId(j.MenuItem_android_id, 0);
                    bVar.j = (typedArrayObtainStyledAttributes2.getInt(j.MenuItem_android_menuCategory, bVar.f257c) & (-65536)) | (typedArrayObtainStyledAttributes2.getInt(j.MenuItem_android_orderInCategory, bVar.d) & 65535);
                    bVar.k = typedArrayObtainStyledAttributes2.getText(j.MenuItem_android_title);
                    bVar.l = typedArrayObtainStyledAttributes2.getText(j.MenuItem_android_titleCondensed);
                    bVar.m = typedArrayObtainStyledAttributes2.getResourceId(j.MenuItem_android_icon, 0);
                    String string = typedArrayObtainStyledAttributes2.getString(j.MenuItem_android_alphabeticShortcut);
                    bVar.n = string == null ? (char) 0 : string.charAt(0);
                    bVar.o = typedArrayObtainStyledAttributes2.getInt(j.MenuItem_alphabeticModifiers, 4096);
                    String string2 = typedArrayObtainStyledAttributes2.getString(j.MenuItem_android_numericShortcut);
                    bVar.p = string2 == null ? (char) 0 : string2.charAt(0);
                    bVar.q = typedArrayObtainStyledAttributes2.getInt(j.MenuItem_numericModifiers, 4096);
                    bVar.r = typedArrayObtainStyledAttributes2.hasValue(j.MenuItem_android_checkable) ? typedArrayObtainStyledAttributes2.getBoolean(j.MenuItem_android_checkable, false) : bVar.e;
                    bVar.s = typedArrayObtainStyledAttributes2.getBoolean(j.MenuItem_android_checked, false);
                    bVar.t = typedArrayObtainStyledAttributes2.getBoolean(j.MenuItem_android_visible, bVar.f);
                    bVar.u = typedArrayObtainStyledAttributes2.getBoolean(j.MenuItem_android_enabled, bVar.g);
                    bVar.v = typedArrayObtainStyledAttributes2.getInt(j.MenuItem_showAsAction, -1);
                    bVar.z = typedArrayObtainStyledAttributes2.getString(j.MenuItem_android_onClick);
                    bVar.w = typedArrayObtainStyledAttributes2.getResourceId(j.MenuItem_actionLayout, 0);
                    bVar.x = typedArrayObtainStyledAttributes2.getString(j.MenuItem_actionViewClass);
                    bVar.y = typedArrayObtainStyledAttributes2.getString(j.MenuItem_actionProviderClass);
                    boolean z3 = bVar.y != null;
                    if (z3 && bVar.w == 0 && bVar.x == null) {
                        bVar.A = (e) bVar.a(bVar.y, ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, c.this.mActionProviderConstructorArguments);
                    } else {
                        if (z3) {
                            Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                        }
                        bVar.A = null;
                    }
                    bVar.B = typedArrayObtainStyledAttributes2.getText(j.MenuItem_contentDescription);
                    bVar.C = typedArrayObtainStyledAttributes2.getText(j.MenuItem_tooltipText);
                    if (typedArrayObtainStyledAttributes2.hasValue(j.MenuItem_iconTintMode)) {
                        bVar.E = A.a(typedArrayObtainStyledAttributes2.getInt(j.MenuItem_iconTintMode, -1), bVar.E);
                    } else {
                        bVar.E = null;
                    }
                    if (typedArrayObtainStyledAttributes2.hasValue(j.MenuItem_iconTint)) {
                        bVar.D = typedArrayObtainStyledAttributes2.getColorStateList(j.MenuItem_iconTint);
                    } else {
                        bVar.D = null;
                    }
                    typedArrayObtainStyledAttributes2.recycle();
                    bVar.h = false;
                } else if (name3.equals("menu")) {
                    parseMenu(xmlPullParser, attributeSet, bVar.a());
                } else {
                    str = name3;
                    z2 = true;
                }
            }
            next = xmlPullParser.next();
        }
    }
}
