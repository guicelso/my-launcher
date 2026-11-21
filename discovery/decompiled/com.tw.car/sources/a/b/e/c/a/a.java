package a.b.e.c.a;

import a.b.b.a.e;
import a.b.e.f.C0019j;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.util.Xml;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final ThreadLocal<TypedValue> f184a = new ThreadLocal<>();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final WeakHashMap<Context, SparseArray<C0004a>> f185b = new WeakHashMap<>(0);

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final Object f186c = new Object();

    /* JADX INFO: renamed from: a.b.e.c.a.a$a, reason: collision with other inner class name */
    private static class C0004a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final ColorStateList f187a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final Configuration f188b;

        public C0004a(ColorStateList colorStateList, Configuration configuration) {
            this.f187a = colorStateList;
            this.f188b = configuration;
        }
    }

    public static ColorStateList a(Context context, int i) {
        C0004a c0004a;
        synchronized (f186c) {
            SparseArray<C0004a> sparseArray = f185b.get(context);
            if (sparseArray != null && sparseArray.size() > 0 && (c0004a = sparseArray.get(i)) != null) {
                if (c0004a.f188b.equals(context.getResources().getConfiguration())) {
                    return c0004a.f187a;
                }
                sparseArray.remove(i);
            }
            return null;
        }
    }

    public static void a(Context context, int i, ColorStateList colorStateList) {
        synchronized (f186c) {
            SparseArray<C0004a> sparseArray = f185b.get(context);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>();
                f185b.put(context, sparseArray);
            }
            sparseArray.append(i, new C0004a(colorStateList, context.getResources().getConfiguration()));
        }
    }

    public static ColorStateList b(Context context, int i) {
        int next;
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColorStateList(i);
        }
        ColorStateList colorStateListA = a(context, i);
        if (colorStateListA != null) {
            return colorStateListA;
        }
        Resources resources = context.getResources();
        TypedValue typedValue = f184a.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            f184a.set(typedValue);
        }
        resources.getValue(i, typedValue, true);
        int i2 = typedValue.type;
        ColorStateList colorStateListA2 = null;
        if (!(i2 >= 28 && i2 <= 31)) {
            Resources resources2 = context.getResources();
            XmlResourceParser xml = resources2.getXml(i);
            try {
                Resources.Theme theme = context.getTheme();
                AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
                do {
                    next = xml.next();
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                if (next != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                colorStateListA2 = e.a(resources2, xml, attributeSetAsAttributeSet, theme);
            } catch (Exception e) {
                Log.e("AppCompatResources", "Failed to inflate ColorStateList, leaving it to the framework", e);
            }
        }
        if (colorStateListA2 == null) {
            return a.b.d.b.a.a(context, i);
        }
        a(context, i, colorStateListA2);
        return colorStateListA2;
    }

    public static Drawable c(Context context, int i) {
        return C0019j.a().c(context, i);
    }
}
