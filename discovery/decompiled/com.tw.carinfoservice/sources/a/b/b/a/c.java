package a.b.b.a;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class c {

    public static class a implements TypeEvaluator<a.b.d.c.b[]> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public a.b.d.c.b[] f15a;

        @Override // android.animation.TypeEvaluator
        public a.b.d.c.b[] evaluate(float f, a.b.d.c.b[] bVarArr, a.b.d.c.b[] bVarArr2) {
            a.b.d.c.b[] bVarArr3 = bVarArr;
            a.b.d.c.b[] bVarArr4 = bVarArr2;
            if (!a.b.a.a.c(bVarArr3, bVarArr4)) {
                throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
            }
            a.b.d.c.b[] bVarArr5 = this.f15a;
            if (bVarArr5 == null || !a.b.a.a.c(bVarArr5, bVarArr3)) {
                this.f15a = a.b.a.a.l(bVarArr3);
            }
            for (int i = 0; i < bVarArr3.length; i++) {
                a.b.d.c.b bVar = this.f15a[i];
                a.b.d.c.b bVar2 = bVarArr3[i];
                a.b.d.c.b bVar3 = bVarArr4[i];
                Objects.requireNonNull(bVar);
                int i2 = 0;
                while (true) {
                    float[] fArr = bVar2.f68b;
                    if (i2 < fArr.length) {
                        bVar.f68b[i2] = (bVar3.f68b[i2] * f) + ((1.0f - f) * fArr[i2]);
                        i2++;
                    }
                }
            }
            return this.f15a;
        }
    }

    /* JADX WARN: Code duplicated, block: B:137:0x025a A[PHI: r15
      0x025a: PHI (r15v9 float) = (r15v3 float), (r15v4 float) binds: [B:136:0x0258, B:139:0x0262] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:180:0x0328  */
    public static Animator a(Context context, Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet, AnimatorSet animatorSet, int i, float f) throws XmlPullParserException, IOException {
        int i2;
        PropertyValuesHolder[] propertyValuesHolderArr;
        int i3;
        int i4;
        PropertyValuesHolder propertyValuesHolderC;
        int size;
        Keyframe keyframeOfFloat;
        Resources resources2 = resources;
        Resources.Theme theme2 = theme;
        int depth = xmlPullParser.getDepth();
        Animator animatorE = null;
        ArrayList arrayList = null;
        while (true) {
            int next = xmlPullParser.next();
            int i5 = 3;
            boolean z = false;
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                break;
            }
            int i6 = 1;
            if (next == 1) {
                break;
            }
            int i7 = 2;
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("objectAnimator")) {
                    ObjectAnimator objectAnimator = new ObjectAnimator();
                    e(context, resources, theme, attributeSet, objectAnimator, f, xmlPullParser);
                    animatorE = objectAnimator;
                } else {
                    if (name.equals("animator")) {
                        animatorE = e(context, resources, theme, attributeSet, null, f, xmlPullParser);
                    } else if (name.equals("set")) {
                        AnimatorSet animatorSet2 = new AnimatorSet();
                        TypedArray typedArrayY = a.b.a.a.y(resources2, theme2, attributeSet, a.b.b.a.a.h);
                        a(context, resources, theme, xmlPullParser, attributeSet, animatorSet2, !a.b.a.a.s(xmlPullParser, "ordering") ? 0 : typedArrayY.getInt(0, 0), f);
                        typedArrayY.recycle();
                        i2 = depth;
                        animatorE = animatorSet2;
                    } else {
                        String str = "propertyValuesHolder";
                        if (!name.equals("propertyValuesHolder")) {
                            StringBuilder sbB = c.a.a.a.a.b("Unknown animator name: ");
                            sbB.append(xmlPullParser.getName());
                            throw new RuntimeException(sbB.toString());
                        }
                        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
                        ArrayList arrayList2 = null;
                        while (true) {
                            int eventType = xmlPullParser.getEventType();
                            if (eventType == i5 || eventType == i6) {
                                break;
                            }
                            if (eventType != i7) {
                                xmlPullParser.next();
                            } else {
                                if (xmlPullParser.getName().equals(str)) {
                                    TypedArray typedArrayY2 = a.b.a.a.y(resources2, theme2, attributeSetAsAttributeSet, a.b.b.a.a.i);
                                    String strP = a.b.a.a.p(typedArrayY2, xmlPullParser, "propertyName", i5);
                                    int i8 = !a.b.a.a.s(xmlPullParser, "valueType") ? 4 : typedArrayY2.getInt(i7, 4);
                                    int[] iArr = a.b.b.a.a.j;
                                    ArrayList arrayList3 = null;
                                    int i9 = i8;
                                    while (true) {
                                        int next2 = xmlPullParser.next();
                                        if (next2 == i5 || next2 == 1) {
                                            break;
                                        }
                                        if (xmlPullParser.getName().equals("keyframe")) {
                                            if (i9 == 4) {
                                                TypedArray typedArrayY3 = a.b.a.a.y(resources2, theme2, Xml.asAttributeSet(xmlPullParser), iArr);
                                                TypedValue typedValuePeekValue = !a.b.a.a.s(xmlPullParser, "value") ? null : typedArrayY3.peekValue(0);
                                                int i10 = ((typedValuePeekValue != null) && d(typedValuePeekValue.type)) ? 3 : 0;
                                                typedArrayY3.recycle();
                                                i9 = i10;
                                            }
                                            TypedArray typedArrayY4 = a.b.a.a.y(resources2, theme2, Xml.asAttributeSet(xmlPullParser), iArr);
                                            float f2 = a.b.a.a.s(xmlPullParser, "fraction") ? typedArrayY4.getFloat(3, -1.0f) : -1.0f;
                                            TypedValue typedValuePeekValue2 = !a.b.a.a.s(xmlPullParser, "value") ? null : typedArrayY4.peekValue(0);
                                            boolean z2 = typedValuePeekValue2 != null;
                                            int i11 = i9 == 4 ? (z2 && d(typedValuePeekValue2.type)) ? 3 : 0 : i9;
                                            if (!z2) {
                                                keyframeOfFloat = i11 == 0 ? Keyframe.ofFloat(f2) : Keyframe.ofInt(f2);
                                            } else if (i11 == 0) {
                                                keyframeOfFloat = Keyframe.ofFloat(f2, !a.b.a.a.s(xmlPullParser, "value") ? 0.0f : typedArrayY4.getFloat(0, 0.0f));
                                            } else if (i11 == 1 || i11 == 3) {
                                                keyframeOfFloat = Keyframe.ofInt(f2, !a.b.a.a.s(xmlPullParser, "value") ? 0 : typedArrayY4.getInt(0, 0));
                                            } else {
                                                keyframeOfFloat = null;
                                            }
                                            int resourceId = !a.b.a.a.s(xmlPullParser, "interpolator") ? 0 : typedArrayY4.getResourceId(1, 0);
                                            if (resourceId > 0) {
                                                keyframeOfFloat.setInterpolator(a.b.a.a.w(context, resourceId));
                                            }
                                            typedArrayY4.recycle();
                                            if (keyframeOfFloat != null) {
                                                if (arrayList3 == null) {
                                                    arrayList3 = new ArrayList();
                                                }
                                                arrayList3.add(keyframeOfFloat);
                                            }
                                            xmlPullParser.next();
                                        }
                                        i5 = 3;
                                        resources2 = resources;
                                        theme2 = theme;
                                        depth = depth;
                                    }
                                    i3 = depth;
                                    if (arrayList3 == null || (size = arrayList3.size()) <= 0) {
                                        i4 = 3;
                                        propertyValuesHolderC = null;
                                    } else {
                                        Keyframe keyframe = (Keyframe) arrayList3.get(0);
                                        Keyframe keyframe2 = (Keyframe) arrayList3.get(size - 1);
                                        float fraction = keyframe2.getFraction();
                                        if (fraction < 1.0f) {
                                            if (fraction < 0.0f) {
                                                keyframe2.setFraction(1.0f);
                                            } else {
                                                arrayList3.add(arrayList3.size(), b(keyframe2, 1.0f));
                                                size++;
                                            }
                                        }
                                        float fraction2 = keyframe.getFraction();
                                        if (fraction2 != 0.0f) {
                                            if (fraction2 < 0.0f) {
                                                keyframe.setFraction(0.0f);
                                            } else {
                                                arrayList3.add(0, b(keyframe, 0.0f));
                                                size++;
                                            }
                                        }
                                        Keyframe[] keyframeArr = new Keyframe[size];
                                        arrayList3.toArray(keyframeArr);
                                        int i12 = 0;
                                        while (i12 < size) {
                                            Keyframe keyframe3 = keyframeArr[i12];
                                            float f3 = 0.0f;
                                            if (keyframe3.getFraction() < 0.0f) {
                                                if (i12 == 0) {
                                                    keyframe3.setFraction(f3);
                                                } else {
                                                    int i13 = size - 1;
                                                    f3 = 1.0f;
                                                    if (i12 == i13) {
                                                        keyframe3.setFraction(f3);
                                                    } else {
                                                        int i14 = i12;
                                                        for (int i15 = i12 + 1; i15 < i13 && keyframeArr[i15].getFraction() < 0.0f; i15++) {
                                                            i14 = i15;
                                                        }
                                                        float fraction3 = (keyframeArr[i14 + 1].getFraction() - keyframeArr[i12 - 1].getFraction()) / ((i14 - i12) + 2);
                                                        int i16 = i12;
                                                        while (i16 <= i14) {
                                                            keyframeArr[i16].setFraction(keyframeArr[i16 - 1].getFraction() + fraction3);
                                                            i16++;
                                                            size = size;
                                                        }
                                                    }
                                                }
                                            }
                                            i12++;
                                            size = size;
                                        }
                                        propertyValuesHolderC = PropertyValuesHolder.ofKeyframe(strP, keyframeArr);
                                        i4 = 3;
                                        if (i9 == 3) {
                                            propertyValuesHolderC.setEvaluator(d.f16a);
                                        }
                                    }
                                    if (propertyValuesHolderC == null) {
                                        propertyValuesHolderC = c(typedArrayY2, i8, 0, 1, strP);
                                    }
                                    if (propertyValuesHolderC != null) {
                                        if (arrayList2 == null) {
                                            arrayList2 = new ArrayList();
                                        }
                                        arrayList2.add(propertyValuesHolderC);
                                    }
                                    typedArrayY2.recycle();
                                    i5 = i4;
                                    i6 = 1;
                                } else {
                                    i3 = depth;
                                }
                                xmlPullParser.next();
                                i7 = 2;
                                resources2 = resources;
                                theme2 = theme;
                                attributeSetAsAttributeSet = attributeSetAsAttributeSet;
                                str = str;
                                depth = i3;
                            }
                        }
                        i2 = depth;
                        if (arrayList2 != null) {
                            int size2 = arrayList2.size();
                            propertyValuesHolderArr = new PropertyValuesHolder[size2];
                            for (int i17 = 0; i17 < size2; i17++) {
                                propertyValuesHolderArr[i17] = (PropertyValuesHolder) arrayList2.get(i17);
                            }
                        } else {
                            propertyValuesHolderArr = null;
                        }
                        if (propertyValuesHolderArr != null && animatorE != null && (animatorE instanceof ValueAnimator)) {
                            ((ValueAnimator) animatorE).setValues(propertyValuesHolderArr);
                        }
                        z = true;
                    }
                    if (animatorSet != null && !z) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(animatorE);
                    }
                    resources2 = resources;
                    theme2 = theme;
                    depth = i2;
                }
                i2 = depth;
                if (animatorSet != null) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(animatorE);
                }
                resources2 = resources;
                theme2 = theme;
                depth = i2;
            }
        }
        if (animatorSet != null && arrayList != null) {
            Animator[] animatorArr = new Animator[arrayList.size()];
            Iterator it = arrayList.iterator();
            int i18 = 0;
            while (it.hasNext()) {
                animatorArr[i18] = (Animator) it.next();
                i18++;
            }
            if (i == 0) {
                animatorSet.playTogether(animatorArr);
            } else {
                animatorSet.playSequentially(animatorArr);
            }
        }
        return animatorE;
    }

    public static Keyframe b(Keyframe keyframe, float f) {
        if (keyframe.getType() == Float.TYPE) {
            return Keyframe.ofFloat(f);
        }
        return keyframe.getType() == Integer.TYPE ? Keyframe.ofInt(f) : Keyframe.ofObject(f);
    }

    public static PropertyValuesHolder c(TypedArray typedArray, int i, int i2, int i3, String str) {
        int color;
        PropertyValuesHolder propertyValuesHolderOfInt;
        int color2;
        int color3;
        PropertyValuesHolder propertyValuesHolderOfObject;
        TypedValue typedValuePeekValue = typedArray.peekValue(i2);
        boolean z = typedValuePeekValue != null;
        int i4 = z ? typedValuePeekValue.type : 0;
        TypedValue typedValuePeekValue2 = typedArray.peekValue(i3);
        boolean z2 = typedValuePeekValue2 != null;
        int i5 = z2 ? typedValuePeekValue2.type : 0;
        if (i == 4) {
            i = ((z && d(i4)) || (z2 && d(i5))) ? 3 : 0;
        }
        boolean z3 = i == 0;
        PropertyValuesHolder propertyValuesHolder = null;
        if (i == 2) {
            String string = typedArray.getString(i2);
            String string2 = typedArray.getString(i3);
            a.b.d.c.b[] bVarArrJ = a.b.a.a.j(string);
            a.b.d.c.b[] bVarArrJ2 = a.b.a.a.j(string2);
            if (bVarArrJ == null && bVarArrJ2 == null) {
                return null;
            }
            if (bVarArrJ == null) {
                if (bVarArrJ2 != null) {
                    return PropertyValuesHolder.ofObject(str, new a(), bVarArrJ2);
                }
                return null;
            }
            a aVar = new a();
            if (bVarArrJ2 == null) {
                propertyValuesHolderOfObject = PropertyValuesHolder.ofObject(str, aVar, bVarArrJ);
            } else {
                if (!a.b.a.a.c(bVarArrJ, bVarArrJ2)) {
                    throw new InflateException(" Can't morph from " + string + " to " + string2);
                }
                propertyValuesHolderOfObject = PropertyValuesHolder.ofObject(str, aVar, bVarArrJ, bVarArrJ2);
            }
            return propertyValuesHolderOfObject;
        }
        d dVar = i == 3 ? d.f16a : null;
        if (z3) {
            if (z) {
                float dimension = i4 == 5 ? typedArray.getDimension(i2, 0.0f) : typedArray.getFloat(i2, 0.0f);
                if (z2) {
                    propertyValuesHolderOfInt = PropertyValuesHolder.ofFloat(str, dimension, i5 == 5 ? typedArray.getDimension(i3, 0.0f) : typedArray.getFloat(i3, 0.0f));
                } else {
                    propertyValuesHolderOfInt = PropertyValuesHolder.ofFloat(str, dimension);
                }
            } else {
                propertyValuesHolderOfInt = PropertyValuesHolder.ofFloat(str, i5 == 5 ? typedArray.getDimension(i3, 0.0f) : typedArray.getFloat(i3, 0.0f));
            }
        } else {
            if (!z) {
                if (z2) {
                    if (i5 == 5) {
                        color = (int) typedArray.getDimension(i3, 0.0f);
                    } else {
                        color = d(i5) ? typedArray.getColor(i3, 0) : typedArray.getInt(i3, 0);
                    }
                    propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, color);
                }
                if (propertyValuesHolder == null && dVar != null) {
                    propertyValuesHolder.setEvaluator(dVar);
                    return propertyValuesHolder;
                }
            }
            if (i4 == 5) {
                color2 = (int) typedArray.getDimension(i2, 0.0f);
            } else {
                color2 = d(i4) ? typedArray.getColor(i2, 0) : typedArray.getInt(i2, 0);
            }
            if (z2) {
                if (i5 == 5) {
                    color3 = (int) typedArray.getDimension(i3, 0.0f);
                } else {
                    color3 = d(i5) ? typedArray.getColor(i3, 0) : typedArray.getInt(i3, 0);
                }
                propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, color2, color3);
            } else {
                propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, color2);
            }
        }
        propertyValuesHolder = propertyValuesHolderOfInt;
        return propertyValuesHolder == null ? propertyValuesHolder : propertyValuesHolder;
    }

    public static boolean d(int i) {
        return i >= 28 && i <= 31;
    }

    public static ValueAnimator e(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, ValueAnimator valueAnimator, float f, XmlPullParser xmlPullParser) {
        ValueAnimator valueAnimator2;
        TypedArray typedArray;
        TypedArray typedArray2;
        ValueAnimator valueAnimator3;
        TypedArray typedArrayY = a.b.a.a.y(resources, theme, attributeSet, a.b.b.a.a.g);
        TypedArray typedArrayY2 = a.b.a.a.y(resources, theme, attributeSet, a.b.b.a.a.k);
        ValueAnimator valueAnimator4 = valueAnimator == null ? new ValueAnimator() : valueAnimator;
        long j = a.b.a.a.s(xmlPullParser, "duration") ? typedArrayY.getInt(1, 300) : 300;
        int resourceId = 0;
        long j2 = !a.b.a.a.s(xmlPullParser, "startOffset") ? 0 : typedArrayY.getInt(2, 0);
        int i = !a.b.a.a.s(xmlPullParser, "valueType") ? 4 : typedArrayY.getInt(7, 4);
        if (a.b.a.a.s(xmlPullParser, "valueFrom") && a.b.a.a.s(xmlPullParser, "valueTo")) {
            if (i == 4) {
                TypedValue typedValuePeekValue = typedArrayY.peekValue(5);
                boolean z = typedValuePeekValue != null;
                int i2 = z ? typedValuePeekValue.type : 0;
                TypedValue typedValuePeekValue2 = typedArrayY.peekValue(6);
                boolean z2 = typedValuePeekValue2 != null;
                i = ((z && d(i2)) || (z2 && d(z2 ? typedValuePeekValue2.type : 0))) ? 3 : 0;
            }
            PropertyValuesHolder propertyValuesHolderC = c(typedArrayY, i, 5, 6, "");
            if (propertyValuesHolderC != null) {
                valueAnimator4.setValues(propertyValuesHolderC);
            }
        }
        valueAnimator4.setDuration(j);
        valueAnimator4.setStartDelay(j2);
        valueAnimator4.setRepeatCount(!a.b.a.a.s(xmlPullParser, "repeatCount") ? 0 : typedArrayY.getInt(3, 0));
        valueAnimator4.setRepeatMode(!a.b.a.a.s(xmlPullParser, "repeatMode") ? 1 : typedArrayY.getInt(4, 1));
        if (typedArrayY2 != null) {
            ObjectAnimator objectAnimator = (ObjectAnimator) valueAnimator4;
            String strP = a.b.a.a.p(typedArrayY2, xmlPullParser, "pathData", 1);
            if (strP != null) {
                String strP2 = a.b.a.a.p(typedArrayY2, xmlPullParser, "propertyXName", 2);
                String strP3 = a.b.a.a.p(typedArrayY2, xmlPullParser, "propertyYName", 3);
                if (strP2 == null && strP3 == null) {
                    throw new InflateException(typedArrayY2.getPositionDescription() + " propertyXName or propertyYName is needed for PathData");
                }
                Path pathK = a.b.a.a.k(strP);
                float f2 = 0.5f * f;
                PathMeasure pathMeasure = new PathMeasure(pathK, false);
                ArrayList arrayList = new ArrayList();
                arrayList.add(Float.valueOf(0.0f));
                float length = 0.0f;
                do {
                    length += pathMeasure.getLength();
                    arrayList.add(Float.valueOf(length));
                } while (pathMeasure.nextContour());
                PathMeasure pathMeasure2 = new PathMeasure(pathK, false);
                int iMin = Math.min(100, ((int) (length / f2)) + 1);
                float[] fArr = new float[iMin];
                float[] fArr2 = new float[iMin];
                float[] fArr3 = new float[2];
                float f3 = length / (iMin - 1);
                valueAnimator2 = valueAnimator4;
                typedArray = typedArrayY;
                int i3 = 0;
                float f4 = 0.0f;
                while (true) {
                    if (resourceId >= iMin) {
                        break;
                    }
                    int i4 = iMin;
                    pathMeasure2.getPosTan(f4 - ((Float) arrayList.get(i3)).floatValue(), fArr3, null);
                    fArr[resourceId] = fArr3[0];
                    fArr2[resourceId] = fArr3[1];
                    f4 += f3;
                    int i5 = i3 + 1;
                    if (i5 < arrayList.size() && f4 > ((Float) arrayList.get(i5)).floatValue()) {
                        pathMeasure2.nextContour();
                        i3 = i5;
                    }
                    resourceId++;
                    iMin = i4;
                }
                PropertyValuesHolder propertyValuesHolderOfFloat = strP2 != null ? PropertyValuesHolder.ofFloat(strP2, fArr) : null;
                PropertyValuesHolder propertyValuesHolderOfFloat2 = strP3 != null ? PropertyValuesHolder.ofFloat(strP3, fArr2) : null;
                if (propertyValuesHolderOfFloat == null) {
                    resourceId = 0;
                    objectAnimator.setValues(propertyValuesHolderOfFloat2);
                } else {
                    resourceId = 0;
                    if (propertyValuesHolderOfFloat2 == null) {
                        objectAnimator.setValues(propertyValuesHolderOfFloat);
                    } else {
                        objectAnimator.setValues(propertyValuesHolderOfFloat, propertyValuesHolderOfFloat2);
                    }
                }
            } else {
                valueAnimator2 = valueAnimator4;
                typedArray = typedArrayY;
                objectAnimator.setPropertyName(a.b.a.a.p(typedArrayY2, xmlPullParser, "propertyName", 0));
            }
        } else {
            valueAnimator2 = valueAnimator4;
            typedArray = typedArrayY;
        }
        if (a.b.a.a.s(xmlPullParser, "interpolator")) {
            typedArray2 = typedArray;
            resourceId = typedArray2.getResourceId(resourceId, resourceId);
        } else {
            typedArray2 = typedArray;
        }
        if (resourceId > 0) {
            valueAnimator3 = valueAnimator2;
            valueAnimator3.setInterpolator(a.b.a.a.w(context, resourceId));
        } else {
            valueAnimator3 = valueAnimator2;
        }
        typedArray2.recycle();
        if (typedArrayY2 != null) {
            typedArrayY2.recycle();
        }
        return valueAnimator3;
    }
}
