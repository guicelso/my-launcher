package a.b.e.f;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class ha {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f336a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final TypedArray f337b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public TypedValue f338c;

    public ha(Context context, TypedArray typedArray) {
        this.f336a = context;
        this.f337b = typedArray;
    }

    public static ha a(Context context, AttributeSet attributeSet, int[] iArr) {
        return new ha(context, context.obtainStyledAttributes(attributeSet, iArr));
    }

    public static ha a(Context context, AttributeSet attributeSet, int[] iArr, int i, int i2) {
        return new ha(context, context.obtainStyledAttributes(attributeSet, iArr, i, i2));
    }

    public int a(int i, int i2) {
        return this.f337b.getColor(i, i2);
    }

    public ColorStateList a(int i) {
        int resourceId;
        ColorStateList colorStateListB;
        return (!this.f337b.hasValue(i) || (resourceId = this.f337b.getResourceId(i, 0)) == 0 || (colorStateListB = a.b.e.c.a.a.b(this.f336a, resourceId)) == null) ? this.f337b.getColorStateList(i) : colorStateListB;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x00b2  */
    public Typeface a(int i, int i2, a.b.d.b.a.j jVar) {
        StringBuilder sb;
        String str;
        Typeface typefaceA;
        int resourceId = this.f337b.getResourceId(i, 0);
        Typeface typeface = null;
        if (resourceId == 0) {
            return null;
        }
        if (this.f338c == null) {
            this.f338c = new TypedValue();
        }
        Context context = this.f336a;
        TypedValue typedValue = this.f338c;
        if (!context.isRestricted()) {
            Resources resources = context.getResources();
            resources.getValue(resourceId, typedValue, true);
            CharSequence charSequence = typedValue.string;
            if (charSequence == null) {
                StringBuilder sbA = c.a.a.a.a.a("Resource \"");
                sbA.append(resources.getResourceName(resourceId));
                sbA.append("\" (");
                sbA.append(Integer.toHexString(resourceId));
                sbA.append(") is not a Font: ");
                sbA.append(typedValue);
                throw new Resources.NotFoundException(sbA.toString());
            }
            String string = charSequence.toString();
            if (string.startsWith("res/")) {
                Typeface typefaceA2 = a.b.d.c.c.f78b.a(a.b.d.c.c.a(resources, resourceId, i2));
                if (typefaceA2 != null) {
                    if (jVar != null) {
                        jVar.a(typefaceA2, (Handler) null);
                    }
                    typeface = typefaceA2;
                } else {
                    try {
                        if (string.toLowerCase().endsWith(".xml")) {
                            a.b.d.b.a.b bVarA = a.b.b.a.e.a(resources.getXml(resourceId), resources);
                            if (bVarA == null) {
                                Log.e("ResourcesCompat", "Failed to find font-family tag");
                                if (jVar != null) {
                                    jVar.a(-3, (Handler) null);
                                }
                            } else {
                                typefaceA = a.b.d.c.c.a(context, bVarA, resources, resourceId, i2, jVar, null, true);
                            }
                        } else {
                            typefaceA = a.b.d.c.c.a(context, resources, resourceId, string, i2);
                            if (jVar != null) {
                                if (typefaceA != null) {
                                    jVar.a(typefaceA, (Handler) null);
                                } else {
                                    jVar.a(-3, (Handler) null);
                                }
                            }
                        }
                        typeface = typefaceA;
                    } catch (IOException e) {
                        e = e;
                        sb = new StringBuilder();
                        str = "Failed to read xml resource ";
                        sb.append(str);
                        sb.append(string);
                        Log.e("ResourcesCompat", sb.toString(), e);
                        if (jVar != null) {
                            jVar.a(-3, (Handler) null);
                        }
                    } catch (XmlPullParserException e2) {
                        e = e2;
                        sb = new StringBuilder();
                        str = "Failed to parse xml resource ";
                        sb.append(str);
                        sb.append(string);
                        Log.e("ResourcesCompat", sb.toString(), e);
                        if (jVar != null) {
                            jVar.a(-3, (Handler) null);
                        }
                    }
                }
            } else if (jVar != null) {
                jVar.a(-3, (Handler) null);
            }
            if (typeface == null && jVar == null) {
                StringBuilder sbA2 = c.a.a.a.a.a("Font resource ID #0x");
                sbA2.append(Integer.toHexString(resourceId));
                sbA2.append(" could not be retrieved.");
                throw new Resources.NotFoundException(sbA2.toString());
            }
        }
        return typeface;
    }

    public boolean a(int i, boolean z) {
        return this.f337b.getBoolean(i, z);
    }

    public int b(int i, int i2) {
        return this.f337b.getDimensionPixelOffset(i, i2);
    }

    public Drawable b(int i) {
        int resourceId;
        return (!this.f337b.hasValue(i) || (resourceId = this.f337b.getResourceId(i, 0)) == 0) ? this.f337b.getDrawable(i) : a.b.e.c.a.a.c(this.f336a, resourceId);
    }

    public int c(int i, int i2) {
        return this.f337b.getDimensionPixelSize(i, i2);
    }

    public String c(int i) {
        return this.f337b.getString(i);
    }

    public int d(int i, int i2) {
        return this.f337b.getInt(i, i2);
    }

    public CharSequence d(int i) {
        return this.f337b.getText(i);
    }

    public int e(int i, int i2) {
        return this.f337b.getLayoutDimension(i, i2);
    }

    public boolean e(int i) {
        return this.f337b.hasValue(i);
    }

    public int f(int i, int i2) {
        return this.f337b.getResourceId(i, i2);
    }
}
