package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import androidx.versionedparcelable.CustomVersionedParcelable;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public class IconCompat extends CustomVersionedParcelable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final PorterDuff.Mode f394a = PorterDuff.Mode.SRC_IN;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f395b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Object f396c;
    public byte[] d;
    public Parcelable e;
    public int f;
    public int g;
    public ColorStateList h = null;
    public PorterDuff.Mode i = f394a;
    public String j;

    public int a() {
        int i;
        if (this.f395b != -1 || (i = Build.VERSION.SDK_INT) < 23) {
            if (this.f395b == 2) {
                return this.f;
            }
            throw new IllegalStateException("called getResId() on " + this);
        }
        Icon icon = (Icon) this.f396c;
        if (i >= 28) {
            return icon.getResId();
        }
        try {
            return ((Integer) icon.getClass().getMethod("getResId", new Class[0]).invoke(icon, new Object[0])).intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e("IconCompat", "Unable to get icon resource", e);
            return 0;
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x003c  */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void a(boolean z) {
        byte[] byteArray;
        String string;
        this.j = this.i.name();
        int i = this.f395b;
        if (i != -1) {
            if (i != 1) {
                if (i == 2) {
                    string = (String) this.f396c;
                } else if (i == 3) {
                    byteArray = (byte[]) this.f396c;
                } else if (i != 4) {
                    if (i != 5) {
                        return;
                    }
                    if (z) {
                        Bitmap bitmap = (Bitmap) this.f396c;
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
                        byteArray = byteArrayOutputStream.toByteArray();
                    }
                } else {
                    string = this.f396c.toString();
                }
                byteArray = string.getBytes(Charset.forName("UTF-16"));
            } else if (z) {
                Bitmap bitmap2 = (Bitmap) this.f396c;
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream2);
                byteArray = byteArrayOutputStream2.toByteArray();
            }
            this.d = byteArray;
            return;
        }
        if (z) {
            throw new IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
        }
        this.e = (Parcelable) this.f396c;
    }

    public String b() {
        int i;
        if (this.f395b != -1 || (i = Build.VERSION.SDK_INT) < 23) {
            if (this.f395b == 2) {
                return ((String) this.f396c).split(":", -1)[0];
            }
            throw new IllegalStateException("called getResPackage() on " + this);
        }
        Icon icon = (Icon) this.f396c;
        if (i >= 28) {
            return icon.getResPackage();
        }
        try {
            return (String) icon.getClass().getMethod("getResPackage", new Class[0]).invoke(icon, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e("IconCompat", "Unable to get icon package", e);
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0020  */
    /* JADX WARN: Code duplicated, block: B:20:0x0035  */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void c() {
        Object str;
        this.i = PorterDuff.Mode.valueOf(this.j);
        int i = this.f395b;
        if (i == -1) {
            str = this.e;
            if (str == null) {
                throw new IllegalArgumentException("Invalid icon");
            }
        } else if (i == 1) {
            str = this.e;
            if (str == null) {
                byte[] bArr = this.d;
                this.f396c = bArr;
                this.f395b = 3;
                this.f = 0;
                this.g = bArr.length;
                return;
            }
        } else if (i == 2) {
            str = new String(this.d, Charset.forName("UTF-16"));
        } else if (i == 3) {
            str = this.d;
        } else if (i != 4) {
            if (i != 5) {
                return;
            }
            str = this.e;
            if (str == null) {
                byte[] bArr2 = this.d;
                this.f396c = bArr2;
                this.f395b = 3;
                this.f = 0;
                this.g = bArr2.length;
                return;
            }
        } else {
            str = new String(this.d, Charset.forName("UTF-16"));
        }
        this.f396c = str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0042, code lost:
    
        if (r1 != 5) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String toString() {
        /*
            Method dump skipped, instruction units count: 214
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.drawable.IconCompat.toString():java.lang.String");
    }
}
