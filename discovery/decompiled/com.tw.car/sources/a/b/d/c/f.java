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
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class f extends d {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Class f82a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Constructor f83b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Method f84c;
    public final Method d;
    public final Method e;
    public final Method f;
    public final Method g;

    public f() throws NoSuchMethodException {
        Class<?> cls;
        Method method;
        Method methodC;
        Method methodA;
        Method methodB;
        Method method2;
        Constructor<?> constructor = null;
        try {
            cls = Class.forName("android.graphics.FontFamily");
            Constructor<?> constructor2 = cls.getConstructor(new Class[0]);
            methodA = a((Class) cls);
            methodB = b((Class) cls);
            method2 = cls.getMethod("freeze", new Class[0]);
            method = cls.getMethod("abortCreation", new Class[0]);
            methodC = c((Class) cls);
            constructor = constructor2;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            StringBuilder sbA = c.a.a.a.a.a("Unable to collect necessary methods for class ");
            sbA.append(e.getClass().getName());
            Log.e("TypefaceCompatApi26Impl", sbA.toString(), e);
            cls = null;
            method = null;
            methodC = null;
            methodA = null;
            methodB = null;
            method2 = null;
        }
        this.f82a = cls;
        this.f83b = constructor;
        this.f84c = methodA;
        this.d = methodB;
        this.e = method2;
        this.f = method;
        this.g = methodC;
    }

    @Override // a.b.d.c.i
    public Typeface a(Context context, a.b.d.b.a.c cVar, Resources resources, int i) {
        if (a()) {
            Object objB = b();
            for (a.b.d.b.a.d dVar : cVar.f56a) {
                if (!a(context, objB, dVar.f57a, dVar.e, dVar.f58b, dVar.f59c ? 1 : 0, FontVariationAxis.fromFontVariationSettings(dVar.d))) {
                    a(objB);
                    return null;
                }
            }
            if (c(objB)) {
                return b(objB);
            }
            return null;
        }
        a.b.d.b.a.d[] dVarArr = cVar.f56a;
        int i2 = (i & 1) == 0 ? 400 : 700;
        boolean z = (i & 2) != 0;
        a.b.d.b.a.d dVar2 = null;
        int i3 = Integer.MAX_VALUE;
        for (a.b.d.b.a.d dVar3 : dVarArr) {
            int iAbs = (Math.abs(dVar3.f58b - i2) * 2) + (dVar3.f59c == z ? 0 : 1);
            if (dVar2 == null || i3 > iAbs) {
                dVar2 = dVar3;
                i3 = iAbs;
            }
        }
        if (dVar2 == null) {
            return null;
        }
        return c.a(context, resources, dVar2.f, dVar2.f57a, i);
    }

    public Method a(Class cls) {
        Class<?> cls2 = Integer.TYPE;
        return cls.getMethod("addFontFromAssetManager", AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, cls2, cls2, cls2, FontVariationAxis[].class);
    }

    public final void a(Object obj) {
        try {
            this.f.invoke(obj, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public final boolean a() {
        if (this.f84c == null) {
            Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return this.f84c != null;
    }

    public final boolean a(Context context, Object obj, String str, int i, int i2, int i3, FontVariationAxis[] fontVariationAxisArr) {
        try {
            return ((Boolean) this.f84c.invoke(obj, context.getAssets(), str, 0, false, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), fontVariationAxisArr)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Typeface b(Object obj) {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) this.f82a, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) this.g.invoke(null, objNewInstance, -1, -1);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public final Object b() {
        try {
            return this.f83b.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Method b(Class cls) {
        Class<?> cls2 = Integer.TYPE;
        return cls.getMethod("addFontFromBuffer", ByteBuffer.class, cls2, FontVariationAxis[].class, cls2, cls2);
    }

    public Method c(Class cls) throws NoSuchMethodException {
        Class cls2 = Integer.TYPE;
        Method declaredMethod = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", Array.newInstance((Class<?>) cls, 1).getClass(), cls2, cls2);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }

    public final boolean c(Object obj) {
        try {
            return ((Boolean) this.e.invoke(obj, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0053 A[Catch: IOException -> 0x0057, TryCatch #1 {IOException -> 0x0057, blocks: (B:8:0x0014, B:11:0x0020, B:14:0x003d, B:27:0x0056, B:26:0x0053, B:25:0x004f, B:22:0x004a), top: B:54:0x0014, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:55:0x004a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // a.b.d.c.d, a.b.d.c.i
    public Typeface a(Context context, CancellationSignal cancellationSignal, a.b.d.e.f.b[] bVarArr, int i) throws Throwable {
        if (bVarArr.length < 1) {
            return null;
        }
        if (a()) {
            Map<Uri, ByteBuffer> mapA = a.b.d.e.f.a(context, bVarArr, cancellationSignal);
            Object objB = b();
            boolean z = false;
            for (a.b.d.e.f.b bVar : bVarArr) {
                ByteBuffer byteBuffer = mapA.get(bVar.f99a);
                if (byteBuffer != null) {
                    try {
                        if (!((Boolean) this.d.invoke(objB, byteBuffer, Integer.valueOf(bVar.f100b), null, Integer.valueOf(bVar.f101c), Integer.valueOf(bVar.d ? 1 : 0))).booleanValue()) {
                            a(objB);
                            return null;
                        }
                        z = true;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (!z) {
                a(objB);
                return null;
            }
            if (c(objB)) {
                return Typeface.create(b(objB), i);
            }
            return null;
        }
        a.b.d.e.f.b bVarA = a(bVarArr, i);
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(bVarA.f99a, "r", cancellationSignal);
            if (parcelFileDescriptorOpenFileDescriptor == null) {
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                }
                return null;
            }
            try {
                Typeface typefaceBuild = new Typeface.Builder(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor()).setWeight(bVarA.f101c).setItalic(bVarA.d).build();
                parcelFileDescriptorOpenFileDescriptor.close();
                return typefaceBuild;
            } catch (Throwable th) {
                th = th;
                try {
                    throw th;
                } catch (Throwable th2) {
                    th = th2;
                    if (th != null) {
                        parcelFileDescriptorOpenFileDescriptor.close();
                    } else {
                        parcelFileDescriptorOpenFileDescriptor.close();
                    }
                    throw th;
                }
            }
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // a.b.d.c.i
    public Typeface a(Context context, Resources resources, int i, String str, int i2) {
        Typeface typefaceCreateFromFile = null;
        if (!a()) {
            File fileA = a.b.b.a.e.a(context);
            if (fileA != null) {
                try {
                    if (a.b.b.a.e.a(fileA, resources, i)) {
                        typefaceCreateFromFile = Typeface.createFromFile(fileA.getPath());
                    }
                } catch (RuntimeException unused) {
                } finally {
                    fileA.delete();
                }
            }
            return typefaceCreateFromFile;
        }
        Object objB = b();
        if (!a(context, objB, str, 0, -1, -1, null)) {
            a(objB);
            return null;
        }
        if (c(objB)) {
            return b(objB);
        }
        return null;
    }
}
