package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.os.Parcelable;
import android.support.v4.graphics.drawable.IconCompat;
import b.a.b;
import b.a.c;

/* JADX INFO: loaded from: classes.dex */
public class IconCompatParcelizer {
    public static IconCompat read(b bVar) {
        IconCompat iconCompat = new IconCompat();
        iconCompat.f395b = bVar.a(iconCompat.f395b, 1);
        byte[] bArr = iconCompat.d;
        if (bVar.a(2)) {
            c cVar = (c) bVar;
            int i = cVar.f442b.readInt();
            if (i < 0) {
                bArr = null;
            } else {
                byte[] bArr2 = new byte[i];
                cVar.f442b.readByteArray(bArr2);
                bArr = bArr2;
            }
        }
        iconCompat.d = bArr;
        iconCompat.e = bVar.a(iconCompat.e, 3);
        iconCompat.f = bVar.a(iconCompat.f, 4);
        iconCompat.g = bVar.a(iconCompat.g, 5);
        iconCompat.h = (ColorStateList) bVar.a(iconCompat.h, 6);
        String strC = iconCompat.j;
        if (bVar.a(7)) {
            strC = bVar.c();
        }
        iconCompat.j = strC;
        iconCompat.c();
        return iconCompat;
    }

    public static void write(IconCompat iconCompat, b bVar) {
        bVar.a(true, true);
        iconCompat.a(false);
        bVar.b(iconCompat.f395b, 1);
        byte[] bArr = iconCompat.d;
        bVar.b(2);
        c cVar = (c) bVar;
        if (bArr != null) {
            cVar.f442b.writeInt(bArr.length);
            cVar.f442b.writeByteArray(bArr);
        } else {
            cVar.f442b.writeInt(-1);
        }
        Parcelable parcelable = iconCompat.e;
        bVar.b(3);
        cVar.f442b.writeParcelable(parcelable, 0);
        bVar.b(iconCompat.f, 4);
        bVar.b(iconCompat.g, 5);
        ColorStateList colorStateList = iconCompat.h;
        bVar.b(6);
        cVar.f442b.writeParcelable(colorStateList, 0);
        String str = iconCompat.j;
        bVar.b(7);
        cVar.f442b.writeString(str);
    }
}
