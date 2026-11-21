package a.b.d.b.a;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Xml;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Shader f53a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final ColorStateList f54b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f55c;

    public a(Shader shader, ColorStateList colorStateList, int i) {
        this.f53a = shader;
        this.f54b = colorStateList;
        this.f55c = i;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static a a(Resources resources, int i, Resources.Theme theme) {
        int next;
        float f;
        float f2;
        Shader radialGradient;
        XmlResourceParser xml = resources.getXml(i);
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
        do {
            next = xml.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        String name = xml.getName();
        byte b2 = -1;
        int iHashCode = name.hashCode();
        if (iHashCode != 89650992) {
            if (iHashCode == 1191572447 && name.equals("selector")) {
                b2 = 0;
            }
        } else if (name.equals("gradient")) {
            b2 = 1;
        }
        if (b2 == 0) {
            ColorStateList colorStateListA = a.b.b.a.e.a(resources, xml, attributeSetAsAttributeSet, theme);
            return new a(null, colorStateListA, colorStateListA.getDefaultColor());
        }
        if (b2 != 1) {
            throw new XmlPullParserException(xml.getPositionDescription() + ": unsupported complex color tag " + name);
        }
        String name2 = xml.getName();
        if (!name2.equals("gradient")) {
            throw new XmlPullParserException(xml.getPositionDescription() + ": invalid gradient color tag " + name2);
        }
        TypedArray typedArrayA = a.b.b.a.e.a(resources, theme, attributeSetAsAttributeSet, a.b.a.b.GradientColor);
        float fA = a.b.b.a.e.a(typedArrayA, (XmlPullParser) xml, "startX", a.b.a.b.GradientColor_android_startX, 0.0f);
        float fA2 = a.b.b.a.e.a(typedArrayA, (XmlPullParser) xml, "startY", a.b.a.b.GradientColor_android_startY, 0.0f);
        float fA3 = a.b.b.a.e.a(typedArrayA, (XmlPullParser) xml, "endX", a.b.a.b.GradientColor_android_endX, 0.0f);
        float fA4 = a.b.b.a.e.a(typedArrayA, (XmlPullParser) xml, "endY", a.b.a.b.GradientColor_android_endY, 0.0f);
        float fA5 = a.b.b.a.e.a(typedArrayA, (XmlPullParser) xml, "centerX", a.b.a.b.GradientColor_android_centerX, 0.0f);
        float fA6 = a.b.b.a.e.a(typedArrayA, (XmlPullParser) xml, "centerY", a.b.a.b.GradientColor_android_centerY, 0.0f);
        int iB = a.b.b.a.e.b(typedArrayA, xml, "type", a.b.a.b.GradientColor_android_type, 0);
        int iA = a.b.b.a.e.a(typedArrayA, (XmlPullParser) xml, "startColor", a.b.a.b.GradientColor_android_startColor, 0);
        boolean zA = a.b.b.a.e.a(xml, "centerColor");
        int iA2 = a.b.b.a.e.a(typedArrayA, (XmlPullParser) xml, "centerColor", a.b.a.b.GradientColor_android_centerColor, 0);
        int iA3 = a.b.b.a.e.a(typedArrayA, (XmlPullParser) xml, "endColor", a.b.a.b.GradientColor_android_endColor, 0);
        int iB2 = a.b.b.a.e.b(typedArrayA, xml, "tileMode", a.b.a.b.GradientColor_android_tileMode, 0);
        float fA7 = a.b.b.a.e.a(typedArrayA, (XmlPullParser) xml, "gradientRadius", a.b.a.b.GradientColor_android_gradientRadius, 0.0f);
        typedArrayA.recycle();
        int depth = xml.getDepth() + 1;
        ArrayList arrayList = new ArrayList(20);
        ArrayList arrayList2 = new ArrayList(20);
        Resources resources2 = resources;
        while (true) {
            int next2 = xml.next();
            f = fA3;
            if (next2 == 1) {
                f2 = fA2;
                break;
            }
            int depth2 = xml.getDepth();
            if (depth2 < depth) {
                f2 = fA2;
                if (next2 == 3) {
                    break;
                }
            } else {
                f2 = fA2;
            }
            if (next2 == 2) {
                if (depth2 > depth) {
                    resources2 = resources;
                } else if (xml.getName().equals("item")) {
                    TypedArray typedArrayA2 = a.b.b.a.e.a(resources2, theme, attributeSetAsAttributeSet, a.b.a.b.GradientColorItem);
                    boolean zHasValue = typedArrayA2.hasValue(a.b.a.b.GradientColorItem_android_color);
                    boolean zHasValue2 = typedArrayA2.hasValue(a.b.a.b.GradientColorItem_android_offset);
                    if (!zHasValue || !zHasValue2) {
                        throw new XmlPullParserException(xml.getPositionDescription() + ": <item> tag requires a 'color' attribute and a 'offset' attribute!");
                    }
                    int color = typedArrayA2.getColor(a.b.a.b.GradientColorItem_android_color, 0);
                    float f3 = typedArrayA2.getFloat(a.b.a.b.GradientColorItem_android_offset, 0.0f);
                    typedArrayA2.recycle();
                    arrayList2.add(Integer.valueOf(color));
                    arrayList.add(Float.valueOf(f3));
                    resources2 = resources;
                } else {
                    continue;
                }
            }
            fA3 = f;
            fA2 = f2;
        }
        f fVar = arrayList2.size() > 0 ? new f(arrayList2, arrayList) : null;
        if (fVar == null) {
            fVar = zA ? new f(iA, iA2, iA3) : new f(iA, iA3);
        }
        if (iB != 1) {
            if (iB != 2) {
                radialGradient = new LinearGradient(fA, f2, f, fA4, fVar.f63a, fVar.f64b, a.b.b.a.e.c(iB2));
            } else {
                radialGradient = new SweepGradient(fA5, fA6, fVar.f63a, fVar.f64b);
            }
        } else {
            if (fA7 <= 0.0f) {
                throw new XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type");
            }
            radialGradient = new RadialGradient(fA5, fA6, fA7, fVar.f63a, fVar.f64b, a.b.b.a.e.c(iB2));
        }
        return new a(radialGradient, null, 0);
    }

    public Shader a() {
        return this.f53a;
    }

    public boolean a(int[] iArr) {
        if (c()) {
            ColorStateList colorStateList = this.f54b;
            int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
            if (colorForState != this.f55c) {
                this.f55c = colorForState;
                return true;
            }
        }
        return false;
    }

    public boolean b() {
        return this.f53a != null;
    }

    public boolean c() {
        ColorStateList colorStateList;
        return this.f53a == null && (colorStateList = this.f54b) != null && colorStateList.isStateful();
    }

    public boolean d() {
        return b() || this.f55c != 0;
    }
}
