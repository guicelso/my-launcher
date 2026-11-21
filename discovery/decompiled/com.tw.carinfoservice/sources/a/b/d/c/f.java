package a.b.d.c;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class f extends d {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Class f74a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Constructor f75b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Method f76c;
    public final Method d;
    public final Method e;
    public final Method f;
    public final Method g;

    public f() throws NoSuchMethodException {
        Method methodO;
        Method method;
        Constructor<?> constructor;
        Method methodM;
        Method methodN;
        Method method2;
        Class<?> cls = null;
        try {
            Class<?> cls2 = Class.forName("android.graphics.FontFamily");
            constructor = cls2.getConstructor(new Class[0]);
            methodM = m(cls2);
            methodN = n(cls2);
            method2 = cls2.getMethod("freeze", new Class[0]);
            method = cls2.getMethod("abortCreation", new Class[0]);
            methodO = o(cls2);
            cls = cls2;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            StringBuilder sbB = c.a.a.a.a.b("Unable to collect necessary methods for class ");
            sbB.append(e.getClass().getName());
            Log.e("TypefaceCompatApi26Impl", sbB.toString(), e);
            methodO = null;
            method = null;
            constructor = null;
            methodM = null;
            methodN = null;
            method2 = null;
        }
        this.f74a = cls;
        this.f75b = constructor;
        this.f76c = methodM;
        this.d = methodN;
        this.e = method2;
        this.f = method;
        this.g = methodO;
    }

    @Override // a.b.d.c.j
    public Typeface a(Context context, a.b.d.b.b.c cVar, Resources resources, int i) {
        if (!k()) {
            return super.a(context, cVar, resources, i);
        }
        Object objL = l();
        for (a.b.d.b.b.d dVar : cVar.f53a) {
            if (!h(context, objL, dVar.f54a, dVar.e, dVar.f55b, dVar.f56c ? 1 : 0, FontVariationAxis.fromFontVariationSettings(dVar.d))) {
                g(objL);
                return null;
            }
        }
        if (j(objL)) {
            return i(objL);
        }
        return null;
    }

    @Override // a.b.d.c.d, a.b.d.c.j
    public Typeface b(Context context, CancellationSignal cancellationSignal, a.b.d.e.e.c[] cVarArr, int i) {
        if (cVarArr.length < 1) {
            return null;
        }
        if (!k()) {
            a.b.d.e.e.c cVar = (a.b.d.e.e.c) j.e(cVarArr, i, new h(this));
            try {
                ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(cVar.f96a, "r", cancellationSignal);
                if (parcelFileDescriptorOpenFileDescriptor == null) {
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                        parcelFileDescriptorOpenFileDescriptor.close();
                    }
                    return null;
                }
                try {
                    Typeface typefaceBuild = new Typeface.Builder(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor()).setWeight(cVar.f98c).setItalic(cVar.d).build();
                    parcelFileDescriptorOpenFileDescriptor.close();
                    return typefaceBuild;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        try {
                            parcelFileDescriptorOpenFileDescriptor.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
            } catch (IOException unused) {
                return null;
            }
        }
        a.b.d.g.d<String, Typeface> dVar = a.b.d.e.e.f91a;
        HashMap map = new HashMap();
        for (a.b.d.e.e.c cVar2 : cVarArr) {
            if (cVar2.e == 0) {
                Uri uri = cVar2.f96a;
                if (!map.containsKey(uri)) {
                    map.put(uri, a.b.a.a.x(context, cancellationSignal, uri));
                }
            }
        }
        Map mapUnmodifiableMap = Collections.unmodifiableMap(map);
        Object objL = l();
        boolean z = false;
        for (a.b.d.e.e.c cVar3 : cVarArr) {
            ByteBuffer byteBuffer = (ByteBuffer) mapUnmodifiableMap.get(cVar3.f96a);
            if (byteBuffer != null) {
                try {
                    if (!((Boolean) this.d.invoke(objL, byteBuffer, Integer.valueOf(cVar3.f97b), null, Integer.valueOf(cVar3.f98c), Integer.valueOf(cVar3.d ? 1 : 0))).booleanValue()) {
                        g(objL);
                        return null;
                    }
                    z = true;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (!z) {
            g(objL);
            return null;
        }
        if (j(objL)) {
            return Typeface.create(i(objL), i);
        }
        return null;
    }

    @Override // a.b.d.c.j
    public Typeface d(Context context, Resources resources, int i, String str, int i2) {
        if (!k()) {
            return super.d(context, resources, i, str, i2);
        }
        Object objL = l();
        if (!h(context, objL, str, 0, -1, -1, null)) {
            g(objL);
            return null;
        }
        if (j(objL)) {
            return i(objL);
        }
        return null;
    }

    public final void g(Object obj) {
        try {
            this.f.invoke(obj, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public final boolean h(Context context, Object obj, String str, int i, int i2, int i3, FontVariationAxis[] fontVariationAxisArr) {
        try {
            return ((Boolean) this.f76c.invoke(obj, context.getAssets(), str, 0, Boolean.FALSE, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), fontVariationAxisArr)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Typeface i(Object obj) {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) this.f74a, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) this.g.invoke(null, objNewInstance, -1, -1);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public final boolean j(Object obj) {
        try {
            return ((Boolean) this.e.invoke(obj, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public final boolean k() {
        if (this.f76c == null) {
            Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return this.f76c != null;
    }

    public final Object l() {
        try {
            return this.f75b.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Method m(Class cls) {
        Class<?> cls2 = Integer.TYPE;
        return cls.getMethod("addFontFromAssetManager", AssetManager.class, String.class, cls2, Boolean.TYPE, cls2, cls2, cls2, FontVariationAxis[].class);
    }

    public Method n(Class cls) {
        Class<?> cls2 = Integer.TYPE;
        return cls.getMethod("addFontFromBuffer", ByteBuffer.class, cls2, FontVariationAxis[].class, cls2, cls2);
    }

    public Method o(Class cls) throws NoSuchMethodException {
        Class cls2 = Integer.TYPE;
        Method declaredMethod = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", Array.newInstance((Class<?>) cls, 1).getClass(), cls2, cls2);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }
}
