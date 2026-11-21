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
public class e extends i {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Class f79a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final Constructor f80b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final Method f81c;
    public static final Method d;

    static {
        Class<?> cls;
        Method method;
        Method method2;
        Constructor<?> constructor = null;
        try {
            cls = Class.forName("android.graphics.FontFamily");
            Constructor<?> constructor2 = cls.getConstructor(new Class[0]);
            method2 = cls.getMethod("addFontWeightStyle", ByteBuffer.class, Integer.TYPE, List.class, Integer.TYPE, Boolean.TYPE);
            method = Typeface.class.getMethod("createFromFamiliesWithDefault", Array.newInstance(cls, 1).getClass());
            constructor = constructor2;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e("TypefaceCompatApi24Impl", e.getClass().getName(), e);
            cls = null;
            method = null;
            method2 = null;
        }
        f80b = constructor;
        f79a = cls;
        f81c = method2;
        d = method;
    }

    public static Typeface a(Object obj) {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) f79a, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) d.invoke(null, objNewInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object a() {
        try {
            return f80b.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean a(Object obj, ByteBuffer byteBuffer, int i, int i2, boolean z) {
        try {
            return ((Boolean) f81c.invoke(obj, byteBuffer, Integer.valueOf(i), null, Integer.valueOf(i2), Boolean.valueOf(z))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0056 A[Catch: IOException -> 0x005a, all -> 0x0071, TryCatch #1 {IOException -> 0x005a, blocks: (B:12:0x0028, B:14:0x003d, B:30:0x0059, B:29:0x0056, B:28:0x0052), top: B:44:0x0028 }] */
    /* JADX WARN: Code duplicated, block: B:48:0x004c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // a.b.d.c.i
    public Typeface a(Context context, a.b.d.b.a.c cVar, Resources resources, int i) {
        MappedByteBuffer map;
        Throwable th;
        Throwable th2;
        Object objA = a();
        for (a.b.d.b.a.d dVar : cVar.f56a) {
            int i2 = dVar.f;
            File fileA = a.b.b.a.e.a(context);
            if (fileA != null) {
                try {
                    if (a.b.b.a.e.a(fileA, resources, i2)) {
                        try {
                            FileInputStream fileInputStream = new FileInputStream(fileA);
                            try {
                                FileChannel channel = fileInputStream.getChannel();
                                map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                                fileInputStream.close();
                                fileA.delete();
                            } catch (Throwable th3) {
                                th = th3;
                                try {
                                    throw th;
                                } catch (Throwable th4) {
                                    th2 = th4;
                                    if (th != null) {
                                        fileInputStream.close();
                                    } else {
                                        fileInputStream.close();
                                    }
                                    throw th2;
                                }
                            }
                        } catch (IOException unused) {
                            map = null;
                        }
                    } else {
                        fileA.delete();
                    }
                    if (map != null || !a(objA, map, dVar.e, dVar.f58b, dVar.f59c)) {
                        return null;
                    }
                } catch (Throwable th5) {
                    fileA.delete();
                    throw th5;
                }
            }
            map = null;
            if (map != null) {
                return null;
            }
        }
        return a(objA);
    }

    @Override // a.b.d.c.i
    public Typeface a(Context context, CancellationSignal cancellationSignal, a.b.d.e.f.b[] bVarArr, int i) throws Throwable {
        Object objA = a();
        a.b.d.g.g gVar = new a.b.d.g.g();
        for (a.b.d.e.f.b bVar : bVarArr) {
            Uri uri = bVar.f99a;
            ByteBuffer byteBufferA = (ByteBuffer) gVar.get(uri);
            if (byteBufferA == null) {
                byteBufferA = a.b.b.a.e.a(context, cancellationSignal, uri);
                gVar.put(uri, byteBufferA);
            }
            if (!a(objA, byteBufferA, bVar.f100b, bVar.f101c, bVar.d)) {
                return null;
            }
        }
        return Typeface.create(a(objA), i);
    }
}
