package a.b.e.c.a;

import a.b.e.f.f;
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
    public static final ThreadLocal<TypedValue> f177a = new ThreadLocal<>();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final WeakHashMap<Context, SparseArray<C0009a>> f178b = new WeakHashMap<>(0);

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final Object f179c = new Object();

    /* JADX INFO: renamed from: a.b.e.c.a.a$a, reason: collision with other inner class name */
    public static class C0009a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final ColorStateList f180a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final Configuration f181b;

        public C0009a(ColorStateList colorStateList, Configuration configuration) {
            this.f180a = colorStateList;
            this.f181b = configuration;
        }
    }

    public static ColorStateList a(Context context, int i) {
        ColorStateList colorStateListH;
        ColorStateList colorStateList;
        int next;
        C0009a c0009a;
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColorStateList(i);
        }
        synchronized (f179c) {
            SparseArray<C0009a> sparseArray = f178b.get(context);
            colorStateListH = null;
            if (sparseArray == null || sparseArray.size() <= 0 || (c0009a = sparseArray.get(i)) == null) {
                colorStateList = null;
            } else if (c0009a.f181b.equals(context.getResources().getConfiguration())) {
                colorStateList = c0009a.f180a;
            } else {
                sparseArray.remove(i);
                colorStateList = null;
            }
        }
        if (colorStateList != null) {
            return colorStateList;
        }
        Resources resources = context.getResources();
        ThreadLocal<TypedValue> threadLocal = f177a;
        TypedValue typedValue = threadLocal.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            threadLocal.set(typedValue);
        }
        resources.getValue(i, typedValue, true);
        int i2 = typedValue.type;
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
                colorStateListH = a.b.a.a.h(resources2, xml, attributeSetAsAttributeSet, theme);
            } catch (Exception e) {
                Log.e("AppCompatResources", "Failed to inflate ColorStateList, leaving it to the framework", e);
            }
        }
        if (colorStateListH == null) {
            return a.b.d.b.a.a(context, i);
        }
        synchronized (f179c) {
            WeakHashMap<Context, SparseArray<C0009a>> weakHashMap = f178b;
            SparseArray<C0009a> sparseArray2 = weakHashMap.get(context);
            if (sparseArray2 == null) {
                sparseArray2 = new SparseArray<>();
                weakHashMap.put(context, sparseArray2);
            }
            sparseArray2.append(i, new C0009a(colorStateListH, context.getResources().getConfiguration()));
        }
        return colorStateListH;
    }

    public static Drawable b(Context context, int i) {
        return f.g().i(context, i);
    }
}
