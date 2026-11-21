package a.b.d.c;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class e extends j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Class f71a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final Constructor f72b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final Method f73c;
    public static final Method d;

    static {
        Class<?> cls;
        Method method;
        Method method2;
        Constructor<?> constructor = null;
        try {
            cls = Class.forName("android.graphics.FontFamily");
            Constructor<?> constructor2 = cls.getConstructor(new Class[0]);
            Class<?> cls2 = Integer.TYPE;
            method2 = cls.getMethod("addFontWeightStyle", ByteBuffer.class, cls2, List.class, cls2, Boolean.TYPE);
            method = Typeface.class.getMethod("createFromFamiliesWithDefault", Array.newInstance(cls, 1).getClass());
            constructor = constructor2;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e("TypefaceCompatApi24Impl", e.getClass().getName(), e);
            cls = null;
            method = null;
            method2 = null;
        }
        f72b = constructor;
        f71a = cls;
        f73c = method2;
        d = method;
    }

    public static boolean f(Object obj, ByteBuffer byteBuffer, int i, int i2, boolean z) {
        try {
            return ((Boolean) f73c.invoke(obj, byteBuffer, Integer.valueOf(i), null, Integer.valueOf(i2), Boolean.valueOf(z))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Typeface g(Object obj) {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) f71a, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) d.invoke(null, objNewInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object h() {
        try {
            return f72b.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // a.b.d.c.j
    public Typeface a(Context context, a.b.d.b.b.c cVar, Resources resources, int i) {
        MappedByteBuffer map;
        Object objH = h();
        for (a.b.d.b.b.d dVar : cVar.f53a) {
            int i2 = dVar.f;
            File fileQ = a.b.a.a.q(context);
            if (fileQ == null) {
                map = null;
            } else {
                try {
                    if (a.b.a.a.f(fileQ, resources, i2)) {
                        try {
                            FileInputStream fileInputStream = new FileInputStream(fileQ);
                            try {
                                FileChannel channel = fileInputStream.getChannel();
                                map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                                fileInputStream.close();
                                fileQ.delete();
                            } catch (Throwable th) {
                                try {
                                    throw th;
                                } catch (Throwable th2) {
                                    try {
                                        fileInputStream.close();
                                    } catch (Throwable th3) {
                                        th.addSuppressed(th3);
                                    }
                                    throw th2;
                                }
                            }
                        } catch (IOException unused) {
                            map = null;
                        }
                    } else {
                        fileQ.delete();
                        map = null;
                    }
                } catch (Throwable th4) {
                    fileQ.delete();
                    throw th4;
                }
            }
            if (map == null || !f(objH, map, dVar.e, dVar.f55b, dVar.f56c)) {
                return null;
            }
        }
        return g(objH);
    }

    @Override // a.b.d.c.j
    public Typeface b(Context context, CancellationSignal cancellationSignal, a.b.d.e.e.c[] cVarArr, int i) {
        Object objH = h();
        a.b.d.g.f fVar = new a.b.d.g.f();
        for (a.b.d.e.e.c cVar : cVarArr) {
            Uri uri = cVar.f96a;
            ByteBuffer byteBufferX = (ByteBuffer) fVar.get(uri);
            if (byteBufferX == null) {
                byteBufferX = a.b.a.a.x(context, cancellationSignal, uri);
                fVar.put(uri, byteBufferX);
            }
            if (!f(objH, byteBufferX, cVar.f97b, cVar.f98c, cVar.d)) {
                return null;
            }
        }
        return Typeface.create(g(objH), i);
    }
}
