package a.b.d.e;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final a.b.d.g.e<String, Typeface> f94a = new a.b.d.g.e<>(16);

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final k f95b = new k("fonts", 10, 10000);

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final Object f96c = new Object();
    public static final a.b.d.g.g<String, ArrayList<k.a<c>>> d = new a.b.d.g.g<>();
    public static final Comparator<byte[]> e = new e();

    public static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final int f97a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final b[] f98b;

        public a(int i, b[] bVarArr) {
            this.f97a = i;
            this.f98b = bVarArr;
        }
    }

    public static class b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Uri f99a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final int f100b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final int f101c;
        public final boolean d;
        public final int e;

        public b(Uri uri, int i, int i2, boolean z, int i3) {
            if (uri == null) {
                throw new NullPointerException();
            }
            this.f99a = uri;
            this.f100b = i;
            this.f101c = i2;
            this.d = z;
            this.e = i3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class c {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Typeface f102a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final int f103b;

        public c(Typeface typeface, int i) {
            this.f102a = typeface;
            this.f103b = i;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static a a(Context context, CancellationSignal cancellationSignal, a.b.d.e.a aVar) throws PackageManager.NameNotFoundException {
        Cursor cursorQuery;
        boolean z;
        PackageManager packageManager = context.getPackageManager();
        Resources resources = context.getResources();
        String str = aVar.f85a;
        ProviderInfo providerInfoResolveContentProvider = packageManager.resolveContentProvider(str, 0);
        if (providerInfoResolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + str);
        }
        if (!providerInfoResolveContentProvider.packageName.equals(aVar.f86b)) {
            throw new PackageManager.NameNotFoundException("Found content provider " + str + ", but package was not " + aVar.f86b);
        }
        Signature[] signatureArr = packageManager.getPackageInfo(providerInfoResolveContentProvider.packageName, 64).signatures;
        ArrayList arrayList = new ArrayList();
        for (Signature signature : signatureArr) {
            arrayList.add(signature.toByteArray());
        }
        Collections.sort(arrayList, e);
        List<List<byte[]>> listA = aVar.d;
        if (listA == null) {
            listA = a.b.b.a.e.a(resources, aVar.e);
        }
        int i = 0;
        while (true) {
            cursorQuery = null;
            if (i >= listA.size()) {
                providerInfoResolveContentProvider = null;
                break;
            }
            ArrayList arrayList2 = new ArrayList(listA.get(i));
            Collections.sort(arrayList2, e);
            if (arrayList.size() != arrayList2.size()) {
                z = false;
                break;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= arrayList.size()) {
                    z = true;
                    break;
                }
                if (!Arrays.equals((byte[]) arrayList.get(i2), (byte[]) arrayList2.get(i2))) {
                    z = false;
                    break;
                }
                i2++;
            }
            if (z) {
                break;
            }
            i++;
        }
        if (providerInfoResolveContentProvider == null) {
            return new a(1, null);
        }
        String str2 = providerInfoResolveContentProvider.authority;
        ArrayList arrayList3 = new ArrayList();
        Uri uriBuild = new Uri.Builder().scheme("content").authority(str2).build();
        Uri uriBuild2 = new Uri.Builder().scheme("content").authority(str2).appendPath("file").build();
        try {
            int i3 = Build.VERSION.SDK_INT;
            cursorQuery = context.getContentResolver().query(uriBuild, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{aVar.f87c}, null, cancellationSignal);
            if (cursorQuery != null && cursorQuery.getCount() > 0) {
                int columnIndex = cursorQuery.getColumnIndex("result_code");
                arrayList3 = new ArrayList();
                int columnIndex2 = cursorQuery.getColumnIndex("_id");
                int columnIndex3 = cursorQuery.getColumnIndex("file_id");
                int columnIndex4 = cursorQuery.getColumnIndex("font_ttc_index");
                int columnIndex5 = cursorQuery.getColumnIndex("font_weight");
                int columnIndex6 = cursorQuery.getColumnIndex("font_italic");
                while (cursorQuery.moveToNext()) {
                    arrayList3.add(new b(columnIndex3 == -1 ? ContentUris.withAppendedId(uriBuild, cursorQuery.getLong(columnIndex2)) : ContentUris.withAppendedId(uriBuild2, cursorQuery.getLong(columnIndex3)), columnIndex4 != -1 ? cursorQuery.getInt(columnIndex4) : 0, columnIndex5 != -1 ? cursorQuery.getInt(columnIndex5) : 400, columnIndex6 != -1 && cursorQuery.getInt(columnIndex6) == 1, columnIndex != -1 ? cursorQuery.getInt(columnIndex) : 0));
                }
            }
            return new a(0, (b[]) arrayList3.toArray(new b[0]));
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public static c a(Context context, a.b.d.e.a aVar, int i) throws Throwable {
        try {
            a aVarA = a(context, (CancellationSignal) null, aVar);
            int i2 = aVarA.f97a;
            if (i2 != 0) {
                return new c(null, i2 == 1 ? -2 : -3);
            }
            Typeface typefaceA = a.b.d.c.c.f77a.a(context, (CancellationSignal) null, aVarA.f98b, i);
            return new c(typefaceA, typefaceA != null ? 0 : -3);
        } catch (PackageManager.NameNotFoundException unused) {
            return new c(null, -1);
        }
    }

    public static Typeface a(Context context, a.b.d.e.a aVar, a.b.d.b.a.j jVar, Handler handler, boolean z, int i, int i2) {
        String str = aVar.f + "-" + i2;
        Typeface typefaceA = f94a.a(str);
        if (typefaceA != null) {
            if (jVar != null) {
                jVar.a(typefaceA);
            }
            return typefaceA;
        }
        if (z && i == -1) {
            c cVarA = a(context, aVar, i2);
            if (jVar != null) {
                int i3 = cVarA.f103b;
                if (i3 == 0) {
                    jVar.a(cVarA.f102a, handler);
                } else {
                    jVar.a(i3, handler);
                }
            }
            return cVarA.f102a;
        }
        a.b.d.e.b bVar = new a.b.d.e.b(context, aVar, i2, str);
        if (z) {
            try {
                return ((c) f95b.a(bVar, i)).f102a;
            } catch (InterruptedException unused) {
                return null;
            }
        }
        a.b.d.e.c cVar = jVar == null ? null : new a.b.d.e.c(jVar, handler);
        synchronized (f96c) {
            if (d.a(str) >= 0) {
                if (cVar != null) {
                    d.get(str).add(cVar);
                }
                return null;
            }
            if (cVar != null) {
                ArrayList<k.a<c>> arrayList = new ArrayList<>();
                arrayList.add(cVar);
                d.put(str, arrayList);
            }
            f95b.a(bVar, new d(str));
            return null;
        }
    }

    public static Map<Uri, ByteBuffer> a(Context context, b[] bVarArr, CancellationSignal cancellationSignal) {
        HashMap map = new HashMap();
        for (b bVar : bVarArr) {
            if (bVar.e == 0) {
                Uri uri = bVar.f99a;
                if (!map.containsKey(uri)) {
                    map.put(uri, a.b.b.a.e.a(context, cancellationSignal, uri));
                }
            }
        }
        return Collections.unmodifiableMap(map);
    }
}
