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
import android.os.CancellationSignal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final a.b.d.g.d<String, Typeface> f91a = new a.b.d.g.d<>(16);

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final f f92b = new f("fonts", 10, 10000);

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final Object f93c = new Object();
    public static final a.b.d.g.f<String, ArrayList<f.c<d>>> d = new a.b.d.g.f<>();
    public static final Comparator<byte[]> e = new a();

    public static class a implements Comparator<byte[]> {
        @Override // java.util.Comparator
        public int compare(byte[] bArr, byte[] bArr2) {
            int length;
            int length2;
            byte[] bArr3 = bArr;
            byte[] bArr4 = bArr2;
            if (bArr3.length == bArr4.length) {
                for (int i = 0; i < bArr3.length; i++) {
                    if (bArr3[i] != bArr4[i]) {
                        length = bArr3[i];
                        length2 = bArr4[i];
                    }
                }
                return 0;
            }
            length = bArr3.length;
            length2 = bArr4.length;
            return length - length2;
        }
    }

    public static class b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final int f94a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final c[] f95b;

        public b(int i, c[] cVarArr) {
            this.f94a = i;
            this.f95b = cVarArr;
        }
    }

    public static class c {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Uri f96a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final int f97b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final int f98c;
        public final boolean d;
        public final int e;

        public c(Uri uri, int i, int i2, boolean z, int i3) {
            Objects.requireNonNull(uri);
            this.f96a = uri;
            this.f97b = i;
            this.f98c = i2;
            this.d = z;
            this.e = i3;
        }
    }

    public static final class d {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Typeface f99a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final int f100b;

        public d(Typeface typeface, int i) {
            this.f99a = typeface;
            this.f100b = i;
        }
    }

    public static b a(Context context, CancellationSignal cancellationSignal, a.b.d.e.a aVar) throws PackageManager.NameNotFoundException {
        Cursor cursorQuery;
        boolean z;
        PackageManager packageManager = context.getPackageManager();
        Resources resources = context.getResources();
        String str = aVar.f82a;
        ProviderInfo providerInfoResolveContentProvider = packageManager.resolveContentProvider(str, 0);
        if (providerInfoResolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + str);
        }
        if (!providerInfoResolveContentProvider.packageName.equals(aVar.f83b)) {
            throw new PackageManager.NameNotFoundException("Found content provider " + str + ", but package was not " + aVar.f83b);
        }
        Signature[] signatureArr = packageManager.getPackageInfo(providerInfoResolveContentProvider.packageName, 64).signatures;
        ArrayList arrayList = new ArrayList();
        for (Signature signature : signatureArr) {
            arrayList.add(signature.toByteArray());
        }
        Collections.sort(arrayList, e);
        List<List<byte[]>> listD = aVar.d;
        if (listD == null) {
            listD = a.b.a.a.D(resources, 0);
        }
        int i = 0;
        while (true) {
            cursorQuery = null;
            if (i >= listD.size()) {
                providerInfoResolveContentProvider = null;
                break;
            }
            ArrayList arrayList2 = new ArrayList(listD.get(i));
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
            return new b(1, null);
        }
        String str2 = providerInfoResolveContentProvider.authority;
        ArrayList arrayList3 = new ArrayList();
        Uri uriBuild = new Uri.Builder().scheme("content").authority(str2).build();
        Uri uriBuild2 = new Uri.Builder().scheme("content").authority(str2).appendPath("file").build();
        try {
            cursorQuery = context.getContentResolver().query(uriBuild, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{aVar.f84c}, null, null);
            if (cursorQuery != null && cursorQuery.getCount() > 0) {
                int columnIndex = cursorQuery.getColumnIndex("result_code");
                arrayList3 = new ArrayList();
                int columnIndex2 = cursorQuery.getColumnIndex("_id");
                int columnIndex3 = cursorQuery.getColumnIndex("file_id");
                int columnIndex4 = cursorQuery.getColumnIndex("font_ttc_index");
                int columnIndex5 = cursorQuery.getColumnIndex("font_weight");
                int columnIndex6 = cursorQuery.getColumnIndex("font_italic");
                while (cursorQuery.moveToNext()) {
                    arrayList3.add(new c(columnIndex3 == -1 ? ContentUris.withAppendedId(uriBuild, cursorQuery.getLong(columnIndex2)) : ContentUris.withAppendedId(uriBuild2, cursorQuery.getLong(columnIndex3)), columnIndex4 != -1 ? cursorQuery.getInt(columnIndex4) : 0, columnIndex5 != -1 ? cursorQuery.getInt(columnIndex5) : 400, columnIndex6 != -1 && cursorQuery.getInt(columnIndex6) == 1, columnIndex != -1 ? cursorQuery.getInt(columnIndex) : 0));
                }
            }
            return new b(0, (c[]) arrayList3.toArray(new c[0]));
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public static d b(Context context, a.b.d.e.a aVar, int i) {
        try {
            b bVarA = a(context, null, aVar);
            int i2 = bVarA.f94a;
            if (i2 != 0) {
                return new d(null, i2 == 1 ? -2 : -3);
            }
            Typeface typefaceB = a.b.d.c.c.f69a.b(context, null, bVarA.f95b, i);
            return new d(typefaceB, typefaceB != null ? 0 : -3);
        } catch (PackageManager.NameNotFoundException unused) {
            return new d(null, -1);
        }
    }
}
