package a.b.e.f;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class g0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f277a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final TypedArray f278b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public TypedValue f279c;

    public g0(Context context, TypedArray typedArray) {
        this.f277a = context;
        this.f278b = typedArray;
    }

    public static g0 m(Context context, AttributeSet attributeSet, int[] iArr, int i, int i2) {
        return new g0(context, context.obtainStyledAttributes(attributeSet, iArr, i, i2));
    }

    public boolean a(int i, boolean z) {
        return this.f278b.getBoolean(i, z);
    }

    public int b(int i, int i2) {
        return this.f278b.getColor(i, i2);
    }

    public ColorStateList c(int i) {
        int resourceId;
        ColorStateList colorStateListA;
        return (!this.f278b.hasValue(i) || (resourceId = this.f278b.getResourceId(i, 0)) == 0 || (colorStateListA = a.b.e.c.a.a.a(this.f277a, resourceId)) == null) ? this.f278b.getColorStateList(i) : colorStateListA;
    }

    public int d(int i, int i2) {
        return this.f278b.getDimensionPixelOffset(i, i2);
    }

    public int e(int i, int i2) {
        return this.f278b.getDimensionPixelSize(i, i2);
    }

    public Drawable f(int i) {
        int resourceId;
        return (!this.f278b.hasValue(i) || (resourceId = this.f278b.getResourceId(i, 0)) == 0) ? this.f278b.getDrawable(i) : a.b.e.c.a.a.b(this.f277a, resourceId);
    }

    public Typeface g(int i, int i2, a.b.d.b.b.g gVar) {
        StringBuilder sb;
        String str;
        int resourceId = this.f278b.getResourceId(i, 0);
        if (resourceId == 0) {
            return null;
        }
        if (this.f279c == null) {
            this.f279c = new TypedValue();
        }
        Context context = this.f277a;
        TypedValue typedValue = this.f279c;
        if (context.isRestricted()) {
            return null;
        }
        Resources resources = context.getResources();
        resources.getValue(resourceId, typedValue, true);
        CharSequence charSequence = typedValue.string;
        if (charSequence == null) {
            StringBuilder sbB = c.a.a.a.a.b("Resource \"");
            sbB.append(resources.getResourceName(resourceId));
            sbB.append("\" (");
            sbB.append(Integer.toHexString(resourceId));
            sbB.append(") is not a Font: ");
            sbB.append(typedValue);
            throw new Resources.NotFoundException(sbB.toString());
        }
        String string = charSequence.toString();
        if (string.startsWith("res/")) {
            Typeface typefaceA = a.b.d.c.c.f70b.a(a.b.d.c.c.c(resources, resourceId, i2));
            if (typefaceA != null) {
                gVar.b(typefaceA, null);
                return typefaceA;
            }
            try {
                if (!string.toLowerCase().endsWith(".xml")) {
                    Typeface typefaceB = a.b.d.c.c.b(context, resources, resourceId, string, i2);
                    if (typefaceB != null) {
                        gVar.b(typefaceB, null);
                    } else {
                        gVar.a(-3, null);
                    }
                    return typefaceB;
                }
                a.b.d.b.b.b bVarC = a.b.a.a.C(resources.getXml(resourceId), resources);
                if (bVarC != null) {
                    return a.b.d.c.c.a(context, bVarC, resources, resourceId, i2, gVar, null, true);
                }
                Log.e("ResourcesCompat", "Failed to find font-family tag");
                gVar.a(-3, null);
                return null;
            } catch (IOException e) {
                e = e;
                sb = new StringBuilder();
                str = "Failed to read xml resource ";
                sb.append(str);
                sb.append(string);
                Log.e("ResourcesCompat", sb.toString(), e);
                gVar.a(-3, null);
                return null;
            } catch (XmlPullParserException e2) {
                e = e2;
                sb = new StringBuilder();
                str = "Failed to parse xml resource ";
                sb.append(str);
                sb.append(string);
                Log.e("ResourcesCompat", sb.toString(), e);
                gVar.a(-3, null);
                return null;
            }
        }
        gVar.a(-3, null);
        return null;
    }

    public int h(int i, int i2) {
        return this.f278b.getInt(i, i2);
    }

    public int i(int i, int i2) {
        return this.f278b.getLayoutDimension(i, i2);
    }

    public int j(int i, int i2) {
        return this.f278b.getResourceId(i, i2);
    }

    public CharSequence k(int i) {
        return this.f278b.getText(i);
    }

    public boolean l(int i) {
        return this.f278b.hasValue(i);
    }
}
