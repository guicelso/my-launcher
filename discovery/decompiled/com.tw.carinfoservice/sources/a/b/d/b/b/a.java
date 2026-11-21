package a.b.d.b.b;

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
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Shader f50a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final ColorStateList f51b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f52c;

    public a(Shader shader, ColorStateList colorStateList, int i) {
        this.f50a = shader;
        this.f51b = colorStateList;
        this.f52c = i;
    }

    public static a a(Resources resources, int i, Resources.Theme theme) {
        int next;
        float f;
        float f2;
        Shader radialGradient;
        Shader.TileMode tileMode;
        Shader.TileMode tileMode2;
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
        name.hashCode();
        if (!name.equals("gradient")) {
            if (name.equals("selector")) {
                ColorStateList colorStateListH = a.b.a.a.h(resources, xml, attributeSetAsAttributeSet, theme);
                return new a(null, colorStateListH, colorStateListH.getDefaultColor());
            }
            throw new XmlPullParserException(xml.getPositionDescription() + ": unsupported complex color tag " + name);
        }
        String name2 = xml.getName();
        if (!name2.equals("gradient")) {
            throw new XmlPullParserException(xml.getPositionDescription() + ": invalid gradient color tag " + name2);
        }
        TypedArray typedArrayY = a.b.a.a.y(resources, theme, attributeSetAsAttributeSet, a.b.a.b.d);
        float f3 = !a.b.a.a.s(xml, "startX") ? 0.0f : typedArrayY.getFloat(8, 0.0f);
        float f4 = !a.b.a.a.s(xml, "startY") ? 0.0f : typedArrayY.getFloat(9, 0.0f);
        float f5 = !a.b.a.a.s(xml, "endX") ? 0.0f : typedArrayY.getFloat(10, 0.0f);
        float f6 = !a.b.a.a.s(xml, "endY") ? 0.0f : typedArrayY.getFloat(11, 0.0f);
        float f7 = !a.b.a.a.s(xml, "centerX") ? 0.0f : typedArrayY.getFloat(3, 0.0f);
        float f8 = !a.b.a.a.s(xml, "centerY") ? 0.0f : typedArrayY.getFloat(4, 0.0f);
        int i2 = !a.b.a.a.s(xml, "type") ? 0 : typedArrayY.getInt(2, 0);
        int color = !a.b.a.a.s(xml, "startColor") ? 0 : typedArrayY.getColor(0, 0);
        boolean zS = a.b.a.a.s(xml, "centerColor");
        int color2 = !a.b.a.a.s(xml, "centerColor") ? 0 : typedArrayY.getColor(7, 0);
        int color3 = !a.b.a.a.s(xml, "endColor") ? 0 : typedArrayY.getColor(1, 0);
        float f9 = f7;
        int i3 = a.b.a.a.s(xml, "tileMode") ? typedArrayY.getInt(6, 0) : 0;
        float f10 = !a.b.a.a.s(xml, "gradientRadius") ? 0.0f : typedArrayY.getFloat(5, 0.0f);
        typedArrayY.recycle();
        int depth = xml.getDepth() + 1;
        float f11 = f10;
        ArrayList arrayList = new ArrayList(20);
        float f12 = f6;
        ArrayList arrayList2 = new ArrayList(20);
        while (true) {
            int next2 = xml.next();
            f = f5;
            if (next2 == 1) {
                f2 = f4;
                break;
            }
            int depth2 = xml.getDepth();
            f2 = f4;
            if (depth2 < depth && next2 == 3) {
                break;
            }
            if (next2 == 2 && depth2 <= depth && xml.getName().equals("item")) {
                TypedArray typedArrayY2 = a.b.a.a.y(resources, theme, attributeSetAsAttributeSet, a.b.a.b.e);
                boolean zHasValue = typedArrayY2.hasValue(0);
                boolean zHasValue2 = typedArrayY2.hasValue(1);
                if (!zHasValue || !zHasValue2) {
                    throw new XmlPullParserException(xml.getPositionDescription() + ": <item> tag requires a 'color' attribute and a 'offset' attribute!");
                }
                int color4 = typedArrayY2.getColor(0, 0);
                float f13 = typedArrayY2.getFloat(1, 0.0f);
                typedArrayY2.recycle();
                arrayList2.add(Integer.valueOf(color4));
                arrayList.add(Float.valueOf(f13));
            }
            f5 = f;
            f4 = f2;
        }
        f fVar = arrayList2.size() > 0 ? new f(arrayList2, arrayList) : null;
        if (fVar == null) {
            fVar = zS ? new f(color, color2, color3) : new f(color, color3);
        }
        if (i2 == 1) {
            float f14 = f8;
            if (f11 <= 0.0f) {
                throw new XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type");
            }
            int[] iArr = fVar.f60a;
            float[] fArr = fVar.f61b;
            if (i3 != 1) {
                tileMode = i3 != 2 ? Shader.TileMode.CLAMP : Shader.TileMode.MIRROR;
            } else {
                tileMode = Shader.TileMode.REPEAT;
            }
            radialGradient = new RadialGradient(f9, f14, f11, iArr, fArr, tileMode);
        } else if (i2 != 2) {
            int[] iArr2 = fVar.f60a;
            float[] fArr2 = fVar.f61b;
            if (i3 != 1) {
                tileMode2 = i3 != 2 ? Shader.TileMode.CLAMP : Shader.TileMode.MIRROR;
            } else {
                tileMode2 = Shader.TileMode.REPEAT;
            }
            radialGradient = new LinearGradient(f3, f2, f, f12, iArr2, fArr2, tileMode2);
        } else {
            radialGradient = new SweepGradient(f9, f8, fVar.f60a, fVar.f61b);
        }
        return new a(radialGradient, null, 0);
    }

    public boolean b() {
        return this.f50a != null;
    }

    public boolean c() {
        ColorStateList colorStateList;
        return this.f50a == null && (colorStateList = this.f51b) != null && colorStateList.isStateful();
    }

    public boolean d(int[] iArr) {
        if (c()) {
            ColorStateList colorStateList = this.f51b;
            int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
            if (colorForState != this.f52c) {
                this.f52c = colorForState;
                return true;
            }
        }
        return false;
    }
}
