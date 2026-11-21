package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Parcelable;
import android.support.v4.graphics.drawable.IconCompat;
import b.a.a;
import java.nio.charset.Charset;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class IconCompatParcelizer {
    /* JADX WARN: Code duplicated, block: B:28:0x008b  */
    /* JADX WARN: Code duplicated, block: B:31:0x0090  */
    public static IconCompat read(a aVar) {
        Object str;
        IconCompat iconCompat = new IconCompat();
        iconCompat.f337a = aVar.e(iconCompat.f337a, 1);
        byte[] bArrB = iconCompat.f339c;
        if (aVar.c(2)) {
            bArrB = aVar.b();
        }
        iconCompat.f339c = bArrB;
        Parcelable parcelableF = iconCompat.d;
        if (aVar.c(3)) {
            parcelableF = aVar.f();
        }
        iconCompat.d = parcelableF;
        iconCompat.e = aVar.e(iconCompat.e, 4);
        iconCompat.f = aVar.e(iconCompat.f, 5);
        Parcelable parcelableF2 = iconCompat.g;
        if (aVar.c(6)) {
            parcelableF2 = aVar.f();
        }
        iconCompat.g = (ColorStateList) parcelableF2;
        String strG = iconCompat.i;
        if (aVar.c(7)) {
            strG = aVar.g();
        }
        iconCompat.i = strG;
        iconCompat.h = PorterDuff.Mode.valueOf(strG);
        int i = iconCompat.f337a;
        if (i != -1) {
            if (i == 1) {
                str = iconCompat.d;
                if (str != null) {
                    byte[] bArr = iconCompat.f339c;
                    iconCompat.f338b = bArr;
                    iconCompat.f337a = 3;
                    iconCompat.e = 0;
                    iconCompat.f = bArr.length;
                }
            } else {
                if (i != 2) {
                    if (i == 3) {
                        iconCompat.f338b = iconCompat.f339c;
                    } else if (i != 4) {
                        if (i == 5) {
                            str = iconCompat.d;
                            if (str != null) {
                                byte[] bArr2 = iconCompat.f339c;
                                iconCompat.f338b = bArr2;
                                iconCompat.f337a = 3;
                                iconCompat.e = 0;
                                iconCompat.f = bArr2.length;
                            }
                        }
                    }
                }
                str = new String(iconCompat.f339c, Charset.forName("UTF-16"));
            }
            return iconCompat;
        }
        str = iconCompat.d;
        if (str == null) {
            throw new IllegalArgumentException("Invalid icon");
        }
        iconCompat.f338b = str;
        return iconCompat;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0049  */
    public static void write(IconCompat iconCompat, a aVar) {
        Objects.requireNonNull(aVar);
        iconCompat.i = iconCompat.h.name();
        int i = iconCompat.f337a;
        if (i == -1 || i == 1) {
            iconCompat.d = (Parcelable) iconCompat.f338b;
        } else if (i == 2) {
            iconCompat.f339c = ((String) iconCompat.f338b).getBytes(Charset.forName("UTF-16"));
        } else if (i == 3) {
            iconCompat.f339c = (byte[]) iconCompat.f338b;
        } else if (i == 4) {
            iconCompat.f339c = iconCompat.f338b.toString().getBytes(Charset.forName("UTF-16"));
        } else if (i == 5) {
            iconCompat.d = (Parcelable) iconCompat.f338b;
        }
        int i2 = iconCompat.f337a;
        aVar.h(1);
        aVar.j(i2);
        byte[] bArr = iconCompat.f339c;
        aVar.h(2);
        aVar.i(bArr);
        Parcelable parcelable = iconCompat.d;
        aVar.h(3);
        aVar.k(parcelable);
        int i3 = iconCompat.e;
        aVar.h(4);
        aVar.j(i3);
        int i4 = iconCompat.f;
        aVar.h(5);
        aVar.j(i4);
        ColorStateList colorStateList = iconCompat.g;
        aVar.h(6);
        aVar.k(colorStateList);
        String str = iconCompat.i;
        aVar.h(7);
        aVar.l(str);
    }
}
