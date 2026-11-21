package a.b.b.a;

import a.b.e.f.qa;
import a.b.e.f.ua;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormatSymbols;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.StrictMode;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.InflateException;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static Method f10a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static boolean f11b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static Method f12c;
    public static boolean d;
    public static Field e;
    public static boolean f;

    public static float a(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, float f2) {
        return !a(xmlPullParser, str) ? f2 : typedArray.getFloat(i, f2);
    }

    public static int a(int i) {
        if (i >= 0) {
            return i;
        }
        throw new IllegalArgumentException();
    }

    public static int a(int i, int i2) {
        int i3 = Build.VERSION.SDK_INT;
        return Gravity.getAbsoluteGravity(i, i2);
    }

    public static int a(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, int i2) {
        return !a(xmlPullParser, str) ? i2 : typedArray.getColor(i, i2);
    }

    public static int a(Object... objArr) {
        int i = Build.VERSION.SDK_INT;
        return Objects.hash(objArr);
    }

    public static Keyframe a(Keyframe keyframe, float f2) {
        if (keyframe.getType() == Float.TYPE) {
            return Keyframe.ofFloat(f2);
        }
        return keyframe.getType() == Integer.TYPE ? Keyframe.ofInt(f2) : Keyframe.ofObject(f2);
    }

    public static TypedArray a(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] iArr) {
        return theme == null ? resources.obtainAttributes(attributeSet, iArr) : theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    public static ActionMode.Callback a(TextView textView, ActionMode.Callback callback) {
        int i = Build.VERSION.SDK_INT;
        return (i < 26 || i > 27 || (callback instanceof a.b.d.i.h)) ? callback : new a.b.d.i.h(callback, textView);
    }

    public static Interpolator a(Context context, int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            return AnimationUtils.loadInterpolator(context, i);
        }
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                if (i == 17563663) {
                    return new a.b.d.h.b.a();
                }
                if (i == 17563661) {
                    return new a.b.d.h.b.b();
                }
                if (i == 17563662) {
                    return new a.b.d.h.b.c();
                }
                XmlResourceParser animation = context.getResources().getAnimation(i);
                context.getResources();
                context.getTheme();
                Interpolator interpolatorA = a(context, animation);
                animation.close();
                return interpolatorA;
            } catch (IOException e2) {
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i));
                notFoundException.initCause(e2);
                throw notFoundException;
            } catch (XmlPullParserException e3) {
                Resources.NotFoundException notFoundException2 = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i));
                notFoundException2.initCause(e3);
                throw notFoundException2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    public static Interpolator a(Context context, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        Interpolator accelerateInterpolator;
        int depth = xmlPullParser.getDepth();
        Interpolator linearInterpolator = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                break;
            }
            if (next == 2) {
                AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
                String name = xmlPullParser.getName();
                if (name.equals("linearInterpolator")) {
                    linearInterpolator = new LinearInterpolator();
                } else {
                    if (name.equals("accelerateInterpolator")) {
                        accelerateInterpolator = new AccelerateInterpolator(context, attributeSetAsAttributeSet);
                    } else if (name.equals("decelerateInterpolator")) {
                        accelerateInterpolator = new DecelerateInterpolator(context, attributeSetAsAttributeSet);
                    } else if (name.equals("accelerateDecelerateInterpolator")) {
                        linearInterpolator = new AccelerateDecelerateInterpolator();
                    } else if (name.equals("cycleInterpolator")) {
                        accelerateInterpolator = new CycleInterpolator(context, attributeSetAsAttributeSet);
                    } else if (name.equals("anticipateInterpolator")) {
                        accelerateInterpolator = new AnticipateInterpolator(context, attributeSetAsAttributeSet);
                    } else if (name.equals("overshootInterpolator")) {
                        accelerateInterpolator = new OvershootInterpolator(context, attributeSetAsAttributeSet);
                    } else if (name.equals("anticipateOvershootInterpolator")) {
                        accelerateInterpolator = new AnticipateOvershootInterpolator(context, attributeSetAsAttributeSet);
                    } else if (name.equals("bounceInterpolator")) {
                        linearInterpolator = new BounceInterpolator();
                    } else {
                        if (!name.equals("pathInterpolator")) {
                            StringBuilder sbA = c.a.a.a.a.a("Unknown interpolator name: ");
                            sbA.append(xmlPullParser.getName());
                            throw new RuntimeException(sbA.toString());
                        }
                        accelerateInterpolator = new h(context, attributeSetAsAttributeSet, xmlPullParser);
                    }
                    linearInterpolator = accelerateInterpolator;
                }
            }
        }
        return linearInterpolator;
    }

    public static InputConnection a(InputConnection inputConnection, EditorInfo editorInfo, View view) {
        if (inputConnection != null && editorInfo.hintText == null) {
            for (ViewParent parent = view.getParent(); parent instanceof View; parent = parent.getParent()) {
                if (parent instanceof ua) {
                    editorInfo.hintText = ((ua) parent).a();
                    break;
                }
            }
        }
        return inputConnection;
    }

    public static File a(Context context) {
        StringBuilder sbA = c.a.a.a.a.a(".font");
        sbA.append(Process.myPid());
        sbA.append("-");
        sbA.append(Process.myTid());
        sbA.append("-");
        String string = sbA.toString();
        for (int i = 0; i < 100; i++) {
            File file = new File(context.getCacheDir(), string + i);
            try {
                if (file.createNewFile()) {
                    return file;
                }
            } catch (IOException unused) {
            }
        }
        return null;
    }

    public static String a(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i) {
        if (a(xmlPullParser, str)) {
            return typedArray.getString(i);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0047 A[Catch: all -> 0x004b, Throwable -> 0x004e, TryCatch #3 {Throwable -> 0x004e, blocks: (B:8:0x0013, B:10:0x002c, B:25:0x004a, B:24:0x0047, B:23:0x0043), top: B:44:0x0013 }] */
    /* JADX WARN: Code duplicated, block: B:37:0x005f A[Catch: IOException -> 0x0063, TryCatch #4 {IOException -> 0x0063, blocks: (B:3:0x0005, B:6:0x000f, B:11:0x002f, B:38:0x0062, B:37:0x005f, B:36:0x005b, B:33:0x0056), top: B:45:0x0005, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x0056 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:48:0x003e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static ByteBuffer a(Context context, CancellationSignal cancellationSignal, Uri uri) throws Throwable {
        Throwable th;
        Throwable th2;
        Throwable th3;
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r", cancellationSignal);
            if (parcelFileDescriptorOpenFileDescriptor == null) {
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                }
                return null;
            }
            try {
                try {
                    FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor());
                    try {
                        FileChannel channel = fileInputStream.getChannel();
                        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                        fileInputStream.close();
                        parcelFileDescriptorOpenFileDescriptor.close();
                        return map;
                    } catch (Throwable th4) {
                        try {
                            throw th4;
                        } catch (Throwable th5) {
                            th2 = th4;
                            th3 = th5;
                            if (th2 == null) {
                                fileInputStream.close();
                                throw th3;
                            }
                            fileInputStream.close();
                            throw th3;
                        }
                    }
                } catch (Throwable th6) {
                    try {
                        throw th6;
                    } catch (Throwable th7) {
                        th = th6;
                        th = th7;
                        if (th == null) {
                            parcelFileDescriptorOpenFileDescriptor.close();
                            throw th;
                        }
                        try {
                            parcelFileDescriptorOpenFileDescriptor.close();
                            throw th;
                        } catch (Throwable th8) {
                            th.addSuppressed(th8);
                            throw th;
                        }
                    }
                }
            } catch (Throwable th9) {
                th = th9;
                th = null;
                if (th == null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                    throw th;
                }
                parcelFileDescriptorOpenFileDescriptor.close();
                throw th;
            }
        } catch (IOException unused) {
            return null;
        }
    }

    public static List<byte[]> a(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            arrayList.add(Base64.decode(str, 0));
        }
        return arrayList;
    }

    public static void a(Drawable drawable, float f2, float f3) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setHotspot(f2, f3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void a(Drawable drawable, int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setTint(i);
        } else if (drawable instanceof a.b.d.c.a.a) {
            ((a.b.d.c.a.a) drawable).setTint(i);
        }
    }

    public static void a(Drawable drawable, int i, int i2, int i3, int i4) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setHotspotBounds(i, i2, i3, i4);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void a(Drawable drawable, ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setTintList(colorStateList);
        } else if (drawable instanceof a.b.d.c.a.a) {
            ((a.b.d.c.a.a) drawable).setTintList(colorStateList);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void a(Drawable drawable, PorterDuff.Mode mode) {
        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setTintMode(mode);
        } else if (drawable instanceof a.b.d.c.a.a) {
            ((a.b.d.c.a.a) drawable).setTintMode(mode);
        }
    }

    public static void a(MenuItem menuItem, char c2, int i) {
        if (menuItem instanceof a.b.d.d.a.b) {
            ((a.b.d.d.a.b) menuItem).a(c2, i);
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setAlphabeticShortcut(c2, i);
        }
    }

    public static void a(MenuItem menuItem, ColorStateList colorStateList) {
        if (menuItem instanceof a.b.d.d.a.b) {
            ((a.b.d.d.a.b) menuItem).a(colorStateList);
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setIconTintList(colorStateList);
        }
    }

    public static void a(MenuItem menuItem, PorterDuff.Mode mode) {
        if (menuItem instanceof a.b.d.d.a.b) {
            ((a.b.d.d.a.b) menuItem).a(mode);
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setIconTintMode(mode);
        }
    }

    public static void a(MenuItem menuItem, CharSequence charSequence) {
        if (menuItem instanceof a.b.d.d.a.b) {
            ((a.b.d.d.a.b) menuItem).a(charSequence);
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setContentDescription(charSequence);
        }
    }

    public static void a(PopupWindow popupWindow, boolean z) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 23) {
            popupWindow.setOverlapAnchor(z);
            return;
        }
        if (i >= 21) {
            if (!f) {
                try {
                    e = PopupWindow.class.getDeclaredField("mOverlapAnchor");
                    e.setAccessible(true);
                } catch (NoSuchFieldException e2) {
                    Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", e2);
                }
                f = true;
            }
            Field field = e;
            if (field != null) {
                try {
                    field.set(popupWindow, Boolean.valueOf(z));
                } catch (IllegalAccessException e3) {
                    Log.i("PopupWindowCompatApi21", "Could not set overlap anchor field in PopupWindow", e3);
                }
            }
        }
    }

    public static void a(TextView textView, int i) {
        a(i);
        if (Build.VERSION.SDK_INT >= 28) {
            textView.setFirstBaselineToTopHeight(i);
            return;
        }
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        int i2 = Build.VERSION.SDK_INT;
        int i3 = textView.getIncludeFontPadding() ? fontMetricsInt.top : fontMetricsInt.ascent;
        if (i > Math.abs(i3)) {
            textView.setPadding(textView.getPaddingLeft(), i - (-i3), textView.getPaddingRight(), textView.getPaddingBottom());
        }
    }

    public static void a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int i = 1;
        while (i > 0) {
            int next = xmlPullParser.next();
            if (next == 2) {
                i++;
            } else if (next == 3) {
                i--;
            }
        }
    }

    public static boolean a(Drawable drawable) {
        int i = Build.VERSION.SDK_INT;
        return drawable.isAutoMirrored();
    }

    public static boolean a(File file, Resources resources, int i) throws Throwable {
        InputStream inputStreamOpenRawResource;
        try {
            inputStreamOpenRawResource = resources.openRawResource(i);
            try {
                boolean zA = a(file, inputStreamOpenRawResource);
                if (inputStreamOpenRawResource != null) {
                    try {
                        inputStreamOpenRawResource.close();
                    } catch (IOException unused) {
                    }
                }
                return zA;
            } catch (Throwable th) {
                th = th;
                if (inputStreamOpenRawResource != null) {
                    try {
                        inputStreamOpenRawResource.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            inputStreamOpenRawResource = null;
        }
    }

    public static boolean a(XmlPullParser xmlPullParser, String str) {
        return xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", str) != null;
    }

    public static boolean a(a.b.d.c.b[] bVarArr, a.b.d.c.b[] bVarArr2) {
        if (bVarArr == null || bVarArr2 == null || bVarArr.length != bVarArr2.length) {
            return false;
        }
        for (int i = 0; i < bVarArr.length; i++) {
            if (bVarArr[i].f75a != bVarArr2[i].f75a || bVarArr[i].f76b.length != bVarArr2[i].f76b.length) {
                return false;
            }
        }
        return true;
    }

    public static float[] a(float[] fArr, int i, int i2) {
        if (i > i2) {
            throw new IllegalArgumentException();
        }
        int length = fArr.length;
        if (i < 0 || i > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i3 = i2 - i;
        int iMin = Math.min(i3, length - i);
        float[] fArr2 = new float[i3];
        System.arraycopy(fArr, i, fArr2, 0, iMin);
        return fArr2;
    }

    public static a.b.d.c.b[] a(a.b.d.c.b[] bVarArr) {
        if (bVarArr == null) {
            return null;
        }
        a.b.d.c.b[] bVarArr2 = new a.b.d.c.b[bVarArr.length];
        for (int i = 0; i < bVarArr.length; i++) {
            bVarArr2[i] = new a.b.d.c.b(bVarArr[i]);
        }
        return bVarArr2;
    }

    public static int b(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, int i2) {
        return !a(xmlPullParser, str) ? i2 : typedArray.getInt(i, i2);
    }

    public static Path b(String str) {
        Path path = new Path();
        a.b.d.c.b[] bVarArrA = a(str);
        if (bVarArrA == null) {
            return null;
        }
        try {
            a.b.d.c.b.a(bVarArrA, path);
            return path;
        } catch (RuntimeException e2) {
            throw new RuntimeException("Error in parsing " + str, e2);
        }
    }

    public static Drawable b(Drawable drawable) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 23) {
            return drawable;
        }
        if (i >= 21) {
            return !(drawable instanceof a.b.d.c.a.a) ? new a.b.d.c.a.d(drawable) : drawable;
        }
        return !(drawable instanceof a.b.d.c.a.a) ? new a.b.d.c.a.c(drawable) : drawable;
    }

    public static TypedValue b(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i) {
        if (a(xmlPullParser, str)) {
            return typedArray.peekValue(i);
        }
        return null;
    }

    public static void b(MenuItem menuItem, char c2, int i) {
        if (menuItem instanceof a.b.d.d.a.b) {
            ((a.b.d.d.a.b) menuItem).b(c2, i);
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setNumericShortcut(c2, i);
        }
    }

    public static void b(MenuItem menuItem, CharSequence charSequence) {
        if (menuItem instanceof a.b.d.d.a.b) {
            ((a.b.d.d.a.b) menuItem).b(charSequence);
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setTooltipText(charSequence);
        }
    }

    public static void b(TextView textView, int i) {
        a(i);
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        int i2 = Build.VERSION.SDK_INT;
        int i3 = textView.getIncludeFontPadding() ? fontMetricsInt.bottom : fontMetricsInt.descent;
        if (i > Math.abs(i3)) {
            textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), i - i3);
        }
    }

    public static boolean b(int i) {
        return i >= 28 && i <= 31;
    }

    public static int c(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, int i2) {
        return !a(xmlPullParser, str) ? i2 : typedArray.getResourceId(i, i2);
    }

    public static Shader.TileMode c(int i) {
        if (i != 1) {
            return i != 2 ? Shader.TileMode.CLAMP : Shader.TileMode.MIRROR;
        }
        return Shader.TileMode.REPEAT;
    }

    public static void c(TextView textView, int i) {
        a(i);
        int fontMetricsInt = textView.getPaint().getFontMetricsInt(null);
        if (i != fontMetricsInt) {
            textView.setLineSpacing(i - fontMetricsInt, 1.0f);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static boolean a(File file, InputStream inputStream) throws Throwable {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file, false);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = inputStream.read(bArr);
                        if (i == -1) {
                            break;
                        }
                        fileOutputStream2.write(bArr, 0, i);
                    }
                    try {
                        fileOutputStream2.close();
                    } catch (IOException unused) {
                    }
                    StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskWrites);
                    return true;
                } catch (IOException e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                    Log.e("TypefaceCompatUtil", "Error copying resource contents to temp file: " + e.getMessage());
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskWrites);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused3) {
                        }
                    }
                    StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskWrites);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:154:0x02f7  */
    /* JADX WARN: Code duplicated, block: B:80:0x0185  */
    /* JADX WARN: Code duplicated, block: B:81:0x018f  */
    /* JADX WARN: Code duplicated, block: B:84:0x0196 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:85:0x0198  */
    public static Animator a(Context context, Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet, AnimatorSet animatorSet, int i, float f2) throws XmlPullParserException, IOException {
        int i2;
        int i3;
        PropertyValuesHolder[] propertyValuesHolderArr;
        String str;
        int i4;
        PropertyValuesHolder propertyValuesHolderA;
        int i5;
        int size;
        Keyframe keyframeOfFloat;
        int i6;
        int iC;
        ValueAnimator valueAnimatorA;
        resources = resources;
        theme = theme;
        int depth = xmlPullParser.getDepth();
        Animator animator = null;
        ArrayList arrayList = null;
        while (true) {
            int next = xmlPullParser.next();
            int i7 = 3;
            i2 = 0;
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                break;
            }
            int i8 = 1;
            if (next == 1) {
                break;
            }
            int i9 = 2;
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("objectAnimator")) {
                    valueAnimatorA = new ObjectAnimator();
                    a(context, resources, theme, attributeSet, valueAnimatorA, f2, xmlPullParser);
                } else {
                    if (name.equals("animator")) {
                        valueAnimatorA = a(context, resources, theme, attributeSet, null, f2, xmlPullParser);
                    } else if (name.equals("set")) {
                        AnimatorSet animatorSet2 = new AnimatorSet();
                        TypedArray typedArrayA = a(resources, theme, attributeSet, a.h);
                        a(context, resources, theme, xmlPullParser, attributeSet, animatorSet2, b(typedArrayA, xmlPullParser, "ordering", 0, 0), f2);
                        typedArrayA.recycle();
                        i3 = depth;
                        animator = animatorSet2;
                    } else {
                        String str2 = "propertyValuesHolder";
                        if (!name.equals("propertyValuesHolder")) {
                            StringBuilder sbA = c.a.a.a.a.a("Unknown animator name: ");
                            sbA.append(xmlPullParser.getName());
                            throw new RuntimeException(sbA.toString());
                        }
                        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
                        ArrayList arrayList2 = null;
                        while (true) {
                            int eventType = xmlPullParser.getEventType();
                            if (eventType == i7 || eventType == i8) {
                                break;
                            }
                            if (eventType != i9) {
                                xmlPullParser.next();
                            } else {
                                if (xmlPullParser.getName().equals(str2)) {
                                    TypedArray typedArrayA2 = a(resources, theme, attributeSetAsAttributeSet, a.i);
                                    String strA = a(typedArrayA2, xmlPullParser, "propertyName", i7);
                                    int iB = b(typedArrayA2, xmlPullParser, "valueType", i9, 4);
                                    ArrayList arrayList3 = null;
                                    int i10 = iB;
                                    while (true) {
                                        int next2 = xmlPullParser.next();
                                        if (next2 == i7) {
                                            break;
                                        }
                                        int i11 = i7;
                                        if (next2 == 1) {
                                            break;
                                        }
                                        if (xmlPullParser.getName().equals("keyframe")) {
                                            if (i10 == 4) {
                                                TypedArray typedArrayA3 = a(resources, theme, Xml.asAttributeSet(xmlPullParser), a.j);
                                                TypedValue typedValueB = b(typedArrayA3, xmlPullParser, "value", 0);
                                                i10 = ((typedValueB != null) && b(typedValueB.type)) ? 3 : 0;
                                                typedArrayA3.recycle();
                                            }
                                            String str3 = str2;
                                            TypedArray typedArrayA4 = a(resources, theme, Xml.asAttributeSet(xmlPullParser), a.j);
                                            float fA = a(typedArrayA4, xmlPullParser, "fraction", 3, -1.0f);
                                            TypedValue typedValueB2 = b(typedArrayA4, xmlPullParser, "value", 0);
                                            boolean z = typedValueB2 != null;
                                            int i12 = depth;
                                            int i13 = i10 == 4 ? (z && b(typedValueB2.type)) ? 3 : 0 : i10;
                                            if (z) {
                                                if (i13 == 0) {
                                                    i6 = 0;
                                                    keyframeOfFloat = Keyframe.ofFloat(fA, a(typedArrayA4, xmlPullParser, "value", 0, 0.0f));
                                                } else if (i13 == 1 || i13 == 3) {
                                                    i6 = 0;
                                                    keyframeOfFloat = Keyframe.ofInt(fA, b(typedArrayA4, xmlPullParser, "value", 0, 0));
                                                } else {
                                                    keyframeOfFloat = null;
                                                }
                                                iC = c(typedArrayA4, xmlPullParser, "interpolator", 1, i6);
                                                if (iC > 0) {
                                                    keyframeOfFloat.setInterpolator(a(context, iC));
                                                }
                                                typedArrayA4.recycle();
                                                if (keyframeOfFloat != null) {
                                                    if (arrayList3 == null) {
                                                        arrayList3 = new ArrayList();
                                                    }
                                                    arrayList3.add(keyframeOfFloat);
                                                }
                                                xmlPullParser.next();
                                                i7 = 3;
                                                str2 = str3;
                                                depth = i12;
                                            } else {
                                                keyframeOfFloat = i13 == 0 ? Keyframe.ofFloat(fA) : Keyframe.ofInt(fA);
                                            }
                                            i6 = 0;
                                            iC = c(typedArrayA4, xmlPullParser, "interpolator", 1, i6);
                                            if (iC > 0) {
                                                keyframeOfFloat.setInterpolator(a(context, iC));
                                            }
                                            typedArrayA4.recycle();
                                            if (keyframeOfFloat != null) {
                                                if (arrayList3 == null) {
                                                    arrayList3 = new ArrayList();
                                                }
                                                arrayList3.add(keyframeOfFloat);
                                            }
                                            xmlPullParser.next();
                                            i7 = 3;
                                            str2 = str3;
                                            depth = i12;
                                        } else {
                                            i7 = i11;
                                        }
                                    }
                                    str = str2;
                                    i4 = depth;
                                    if (arrayList3 == null || (size = arrayList3.size()) <= 0) {
                                        i7 = 3;
                                        propertyValuesHolderA = null;
                                    } else {
                                        Keyframe keyframe = (Keyframe) arrayList3.get(0);
                                        Keyframe keyframe2 = (Keyframe) arrayList3.get(size - 1);
                                        float fraction = keyframe2.getFraction();
                                        if (fraction < 1.0f) {
                                            if (fraction < 0.0f) {
                                                keyframe2.setFraction(1.0f);
                                            } else {
                                                arrayList3.add(arrayList3.size(), a(keyframe2, 1.0f));
                                                size++;
                                            }
                                        }
                                        float fraction2 = keyframe.getFraction();
                                        if (fraction2 != 0.0f) {
                                            if (fraction2 < 0.0f) {
                                                keyframe.setFraction(0.0f);
                                            } else {
                                                arrayList3.add(0, a(keyframe, 0.0f));
                                                size++;
                                            }
                                        }
                                        Keyframe[] keyframeArr = new Keyframe[size];
                                        arrayList3.toArray(keyframeArr);
                                        int i14 = 0;
                                        while (i14 < size) {
                                            Keyframe keyframe3 = keyframeArr[i14];
                                            float f3 = 0.0f;
                                            if (keyframe3.getFraction() < 0.0f) {
                                                if (i14 != 0) {
                                                    int i15 = size - 1;
                                                    if (i14 == i15) {
                                                        f3 = 1.0f;
                                                        keyframe3.setFraction(f3);
                                                    } else {
                                                        int i16 = i14;
                                                        for (int i17 = i14 + 1; i17 < i15 && keyframeArr[i17].getFraction() < 0.0f; i17++) {
                                                            i16 = i17;
                                                        }
                                                        float fraction3 = (keyframeArr[i16 + 1].getFraction() - keyframeArr[i14 - 1].getFraction()) / ((i16 - i14) + 2);
                                                        int i18 = i14;
                                                        while (i18 <= i16) {
                                                            keyframeArr[i18].setFraction(keyframeArr[i18 - 1].getFraction() + fraction3);
                                                            i18++;
                                                            size = size;
                                                        }
                                                    }
                                                } else {
                                                    keyframe3.setFraction(f3);
                                                }
                                            }
                                            i14++;
                                            size = size;
                                        }
                                        propertyValuesHolderA = PropertyValuesHolder.ofKeyframe(strA, keyframeArr);
                                        i7 = 3;
                                        if (i10 == 3) {
                                            propertyValuesHolderA.setEvaluator(g.a());
                                        }
                                    }
                                    if (propertyValuesHolderA == null) {
                                        i5 = 0;
                                        propertyValuesHolderA = a(typedArrayA2, iB, 0, 1, strA);
                                    } else {
                                        i5 = 0;
                                    }
                                    if (propertyValuesHolderA != null) {
                                        if (arrayList2 == null) {
                                            arrayList2 = new ArrayList();
                                        }
                                        arrayList2.add(propertyValuesHolderA);
                                    }
                                    typedArrayA2.recycle();
                                    i2 = i5;
                                } else {
                                    str = str2;
                                    i4 = depth;
                                }
                                xmlPullParser.next();
                                i8 = 1;
                                i9 = 2;
                                resources = resources;
                                theme = theme;
                                attributeSetAsAttributeSet = attributeSetAsAttributeSet;
                                str2 = str;
                                depth = i4;
                            }
                        }
                        i3 = depth;
                        if (arrayList2 != null) {
                            int size2 = arrayList2.size();
                            propertyValuesHolderArr = new PropertyValuesHolder[size2];
                            while (i2 < size2) {
                                propertyValuesHolderArr[i2] = (PropertyValuesHolder) arrayList2.get(i2);
                                i2++;
                            }
                        } else {
                            propertyValuesHolderArr = null;
                        }
                        if (propertyValuesHolderArr != null && animator != null && (animator instanceof ValueAnimator)) {
                            ((ValueAnimator) animator).setValues(propertyValuesHolderArr);
                        }
                        i2 = 1;
                    }
                    if (animatorSet != null && i2 == 0) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(animator);
                    }
                    resources = resources;
                    theme = theme;
                    depth = i3;
                }
                animator = valueAnimatorA;
                i3 = depth;
                if (animatorSet != null) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(animator);
                }
                resources = resources;
                theme = theme;
                depth = i3;
            }
        }
        if (animatorSet != null && arrayList != null) {
            Animator[] animatorArr = new Animator[arrayList.size()];
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                animatorArr[i2] = (Animator) it.next();
                i2++;
            }
            if (i == 0) {
                animatorSet.playTogether(animatorArr);
            } else {
                animatorSet.playSequentially(animatorArr);
            }
        }
        return animator;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0083  */
    /* JADX WARN: Code duplicated, block: B:44:0x00be  */
    /* JADX WARN: Code duplicated, block: B:47:0x00c4  */
    /* JADX WARN: Code duplicated, block: B:50:0x00d3  */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static ColorStateList a(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth;
        int i;
        int attributeCount;
        int i2;
        int[] iArrTrimStateSet;
        int i3;
        int attributeNameResource;
        String name = xmlPullParser.getName();
        if (name.equals("selector")) {
            int i4 = 1;
            int depth2 = xmlPullParser.getDepth() + 1;
            Object[] objArr = new int[20][];
            int[] iArr = new int[objArr.length];
            int i5 = 0;
            while (true) {
                int next = xmlPullParser.next();
                if (next == i4 || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                    break;
                }
                if (next == 2 && depth <= depth2 && xmlPullParser.getName().equals("item")) {
                    int[] iArr2 = a.b.a.b.ColorStateListItem;
                    TypedArray typedArrayObtainAttributes = theme == null ? resources.obtainAttributes(attributeSet, iArr2) : theme.obtainStyledAttributes(attributeSet, iArr2, 0, 0);
                    int color = typedArrayObtainAttributes.getColor(a.b.a.b.ColorStateListItem_android_color, -65281);
                    float f2 = 1.0f;
                    if (typedArrayObtainAttributes.hasValue(a.b.a.b.ColorStateListItem_android_alpha)) {
                        i = a.b.a.b.ColorStateListItem_android_alpha;
                    } else {
                        if (typedArrayObtainAttributes.hasValue(a.b.a.b.ColorStateListItem_alpha)) {
                            i = a.b.a.b.ColorStateListItem_alpha;
                        }
                        typedArrayObtainAttributes.recycle();
                        attributeCount = attributeSet.getAttributeCount();
                        int[] iArr3 = new int[attributeCount];
                        int i6 = 0;
                        for (i2 = 0; i2 < attributeCount; i2++) {
                            attributeNameResource = attributeSet.getAttributeNameResource(i2);
                            if (attributeNameResource == 16843173 && attributeNameResource != 16843551 && attributeNameResource != a.b.a.a.alpha) {
                                int i7 = i6 + 1;
                                if (!attributeSet.getAttributeBooleanValue(i2, false)) {
                                    attributeNameResource = -attributeNameResource;
                                }
                                iArr3[i6] = attributeNameResource;
                                i6 = i7;
                            }
                        }
                        iArrTrimStateSet = StateSet.trimStateSet(iArr3, i6);
                        int iRound = (Math.round(Color.alpha(color) * f2) << 24) | (color & 16777215);
                        if (i5 != 0) {
                            int length = iArrTrimStateSet.length;
                        }
                        i3 = i5 + 1;
                        if (i3 > iArr.length) {
                            int[] iArr4 = new int[a.b.d.b.a.g.a(i5)];
                            System.arraycopy(iArr, 0, iArr4, 0, i5);
                            iArr = iArr4;
                        }
                        iArr[i5] = iRound;
                        if (i3 > objArr.length) {
                            Object[] objArr2 = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), a.b.d.b.a.g.a(i5));
                            System.arraycopy(objArr, 0, objArr2, 0, i5);
                            objArr = objArr2;
                        }
                        objArr[i5] = iArrTrimStateSet;
                        objArr = (int[][]) objArr;
                        i5 = i3;
                    }
                    f2 = typedArrayObtainAttributes.getFloat(i, 1.0f);
                    typedArrayObtainAttributes.recycle();
                    attributeCount = attributeSet.getAttributeCount();
                    int[] iArr5 = new int[attributeCount];
                    int i8 = 0;
                    while (i2 < attributeCount) {
                        attributeNameResource = attributeSet.getAttributeNameResource(i2);
                        if (attributeNameResource == 16843173) {
                        }
                    }
                    iArrTrimStateSet = StateSet.trimStateSet(iArr5, i8);
                    int iRound2 = (Math.round(Color.alpha(color) * f2) << 24) | (color & 16777215);
                    if (i5 != 0) {
                        int length2 = iArrTrimStateSet.length;
                    }
                    i3 = i5 + 1;
                    if (i3 > iArr.length) {
                        int[] iArr6 = new int[a.b.d.b.a.g.a(i5)];
                        System.arraycopy(iArr, 0, iArr6, 0, i5);
                        iArr = iArr6;
                    }
                    iArr[i5] = iRound2;
                    if (i3 > objArr.length) {
                        Object[] objArr3 = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), a.b.d.b.a.g.a(i5));
                        System.arraycopy(objArr, 0, objArr3, 0, i5);
                        objArr = objArr3;
                    }
                    objArr[i5] = iArrTrimStateSet;
                    objArr = (int[][]) objArr;
                    i5 = i3;
                }
                i4 = 1;
            }
            int[] iArr7 = new int[i5];
            int[][] iArr8 = new int[i5][];
            System.arraycopy(iArr, 0, iArr7, 0, i5);
            System.arraycopy(objArr, 0, iArr8, 0, i5);
            return new ColorStateList(iArr8, iArr7);
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid color state list tag " + name);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0032  */
    /* JADX WARN: Code duplicated, block: B:21:0x0048  */
    /* JADX WARN: Code duplicated, block: B:43:0x0091 A[PHI: r14
      0x0091: PHI (r14v3 boolean) = (r14v1 boolean), (r14v1 boolean), (r14v6 boolean) binds: [B:32:0x007a, B:35:0x0080, B:41:0x008c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:49:0x009d A[Catch: NumberFormatException -> 0x00b8, TryCatch #0 {NumberFormatException -> 0x00b8, blocks: (B:26:0x005a, B:29:0x006e, B:31:0x0074, B:35:0x0080, B:47:0x0097, B:49:0x009d, B:52:0x00ad, B:54:0x00b2), top: B:69:0x005a }] */
    /* JADX WARN: Code duplicated, block: B:51:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:52:0x00ad A[Catch: NumberFormatException -> 0x00b8, TryCatch #0 {NumberFormatException -> 0x00b8, blocks: (B:26:0x005a, B:29:0x006e, B:31:0x0074, B:35:0x0080, B:47:0x0097, B:49:0x009d, B:52:0x00ad, B:54:0x00b2), top: B:69:0x005a }] */
    /* JADX WARN: Code duplicated, block: B:59:0x00d5  */
    /* JADX WARN: Code duplicated, block: B:73:0x00e3 A[SYNTHETIC] */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x008a, code lost:
    
        if (r16 == false) goto L41;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static a.b.d.c.b[] a(java.lang.String r17) {
        /*
            Method dump skipped, instruction units count: 282
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: a.b.b.a.e.a(java.lang.String):a.b.d.c.b[]");
    }

    public static a.b.d.b.a.a a(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme, String str, int i, int i2) {
        a.b.d.b.a.a aVarA;
        if (a(xmlPullParser, str)) {
            TypedValue typedValue = new TypedValue();
            typedArray.getValue(i, typedValue);
            int i3 = typedValue.type;
            if (i3 >= 28 && i3 <= 31) {
                return new a.b.d.b.a.a(null, null, typedValue.data);
            }
            try {
                aVarA = a.b.d.b.a.a.a(typedArray.getResources(), typedArray.getResourceId(i, 0), theme);
            } catch (Exception e2) {
                Log.e("ComplexColorCompat", "Failed to inflate ComplexColor.", e2);
                aVarA = null;
            }
            if (aVarA != null) {
                return aVarA;
            }
        }
        return new a.b.d.b.a.a(null, null, i2);
    }

    public static PropertyValuesHolder a(TypedArray typedArray, int i, int i2, int i3, String str) {
        int color;
        PropertyValuesHolder propertyValuesHolderOfInt;
        int color2;
        int color3;
        Object[] objArr;
        TypedValue typedValuePeekValue = typedArray.peekValue(i2);
        boolean z = typedValuePeekValue != null;
        int i4 = z ? typedValuePeekValue.type : 0;
        TypedValue typedValuePeekValue2 = typedArray.peekValue(i3);
        boolean z2 = typedValuePeekValue2 != null;
        int i5 = z2 ? typedValuePeekValue2.type : 0;
        if (i == 4) {
            i = ((z && b(i4)) || (z2 && b(i5))) ? 3 : 0;
        }
        boolean z3 = i == 0;
        PropertyValuesHolder propertyValuesHolder = null;
        if (i == 2) {
            String string = typedArray.getString(i2);
            String string2 = typedArray.getString(i3);
            a.b.d.c.b[] bVarArrA = a(string);
            a.b.d.c.b[] bVarArrA2 = a(string2);
            if (bVarArrA == null && bVarArrA2 == null) {
                return null;
            }
            if (bVarArrA == null) {
                if (bVarArrA2 != null) {
                    return PropertyValuesHolder.ofObject(str, new f(), bVarArrA2);
                }
                return null;
            }
            f fVar = new f();
            if (bVarArrA2 == null) {
                objArr = new Object[]{bVarArrA};
            } else {
                if (!a(bVarArrA, bVarArrA2)) {
                    throw new InflateException(" Can't morph from " + string + " to " + string2);
                }
                objArr = new Object[]{bVarArrA, bVarArrA2};
            }
            return PropertyValuesHolder.ofObject(str, fVar, objArr);
        }
        g gVar = i == 3 ? g.f14a : null;
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
                        color = b(i5) ? typedArray.getColor(i3, 0) : typedArray.getInt(i3, 0);
                    }
                    propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, color);
                }
                if (propertyValuesHolder == null && gVar != null) {
                    propertyValuesHolder.setEvaluator(gVar);
                    return propertyValuesHolder;
                }
            }
            if (i4 == 5) {
                color2 = (int) typedArray.getDimension(i2, 0.0f);
            } else {
                color2 = b(i4) ? typedArray.getColor(i2, 0) : typedArray.getInt(i2, 0);
            }
            if (z2) {
                if (i5 == 5) {
                    color3 = (int) typedArray.getDimension(i3, 0.0f);
                } else {
                    color3 = b(i5) ? typedArray.getColor(i3, 0) : typedArray.getInt(i3, 0);
                }
                propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, color2, color3);
            } else {
                propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, color2);
            }
        }
        propertyValuesHolder = propertyValuesHolderOfInt;
        return propertyValuesHolder == null ? propertyValuesHolder : propertyValuesHolder;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0040  */
    /* JADX WARN: Code duplicated, block: B:31:0x007c  */
    /* JADX WARN: Code duplicated, block: B:33:0x0082  */
    /* JADX WARN: Code duplicated, block: B:35:0x0088  */
    public static a.b.d.f.a.C0002a a(TextView textView) {
        int breakStrategy;
        int hyphenationFrequency;
        TextDirectionHeuristic textDirectionHeuristic;
        if (Build.VERSION.SDK_INT >= 28) {
            return new a.b.d.f.a.C0002a(textView.getTextMetricsParams());
        }
        TextPaint textPaint = new TextPaint(textView.getPaint());
        if (Build.VERSION.SDK_INT >= 23) {
            breakStrategy = 1;
            hyphenationFrequency = 1;
        } else {
            breakStrategy = 0;
            hyphenationFrequency = 0;
        }
        int i = Build.VERSION.SDK_INT;
        TextDirectionHeuristic textDirectionHeuristic2 = TextDirectionHeuristics.FIRSTSTRONG_LTR;
        if (Build.VERSION.SDK_INT >= 23) {
            breakStrategy = textView.getBreakStrategy();
            hyphenationFrequency = textView.getHyphenationFrequency();
        }
        int i2 = Build.VERSION.SDK_INT;
        if (textView.getTransformationMethod() instanceof PasswordTransformationMethod) {
            textDirectionHeuristic = TextDirectionHeuristics.LTR;
        } else if (Build.VERSION.SDK_INT < 28 || (textView.getInputType() & 15) != 3) {
            boolean z = textView.getLayoutDirection() == 1;
            switch (textView.getTextDirection()) {
                case 2:
                    textDirectionHeuristic = TextDirectionHeuristics.ANYRTL_LTR;
                    break;
                case 3:
                    textDirectionHeuristic = TextDirectionHeuristics.LTR;
                    break;
                case 4:
                    textDirectionHeuristic = TextDirectionHeuristics.RTL;
                    break;
                case 5:
                    textDirectionHeuristic = TextDirectionHeuristics.LOCALE;
                    break;
                case 6:
                    textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_LTR;
                    break;
                case 7:
                    textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_RTL;
                    break;
                default:
                    if (!z) {
                        textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_LTR;
                    } else {
                        textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_RTL;
                    }
                    break;
            }
        } else {
            byte directionality = Character.getDirectionality(DecimalFormatSymbols.getInstance(textView.getTextLocale()).getDigitStrings()[0].codePointAt(0));
            if (directionality == 1 || directionality == 2) {
                textDirectionHeuristic = TextDirectionHeuristics.RTL;
            } else {
                textDirectionHeuristic = TextDirectionHeuristics.LTR;
            }
        }
        return new a.b.d.f.a.C0002a(textPaint, textDirectionHeuristic, breakStrategy, hyphenationFrequency);
    }

    public static ValueAnimator a(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, ValueAnimator valueAnimator, float f2, XmlPullParser xmlPullParser) {
        ValueAnimator valueAnimator2;
        int resourceId;
        ValueAnimator valueAnimator3;
        TypedArray typedArrayA = a(resources, theme, attributeSet, a.g);
        TypedArray typedArrayA2 = a(resources, theme, attributeSet, a.k);
        ValueAnimator valueAnimator4 = valueAnimator == null ? new ValueAnimator() : valueAnimator;
        long jB = b(typedArrayA, xmlPullParser, "duration", 1, 300);
        int i = 0;
        long jB2 = b(typedArrayA, xmlPullParser, "startOffset", 2, 0);
        int iB = b(typedArrayA, xmlPullParser, "valueType", 7, 4);
        if (a(xmlPullParser, "valueFrom") && a(xmlPullParser, "valueTo")) {
            if (iB == 4) {
                TypedValue typedValuePeekValue = typedArrayA.peekValue(5);
                boolean z = typedValuePeekValue != null;
                int i2 = z ? typedValuePeekValue.type : 0;
                TypedValue typedValuePeekValue2 = typedArrayA.peekValue(6);
                boolean z2 = typedValuePeekValue2 != null;
                iB = ((z && b(i2)) || (z2 && b(z2 ? typedValuePeekValue2.type : 0))) ? 3 : 0;
            }
            PropertyValuesHolder propertyValuesHolderA = a(typedArrayA, iB, 5, 6, "");
            if (propertyValuesHolderA != null) {
                valueAnimator4.setValues(propertyValuesHolderA);
            }
        }
        valueAnimator4.setDuration(jB);
        valueAnimator4.setStartDelay(jB2);
        valueAnimator4.setRepeatCount(b(typedArrayA, xmlPullParser, "repeatCount", 3, 0));
        valueAnimator4.setRepeatMode(b(typedArrayA, xmlPullParser, "repeatMode", 4, 1));
        if (typedArrayA2 != null) {
            ObjectAnimator objectAnimator = (ObjectAnimator) valueAnimator4;
            String strA = a(typedArrayA2, xmlPullParser, "pathData", 1);
            if (strA != null) {
                String strA2 = a(typedArrayA2, xmlPullParser, "propertyXName", 2);
                String strA3 = a(typedArrayA2, xmlPullParser, "propertyYName", 3);
                if (strA2 == null && strA3 == null) {
                    throw new InflateException(typedArrayA2.getPositionDescription() + " propertyXName or propertyYName is needed for PathData");
                }
                Path pathB = b(strA);
                float f3 = 0.5f * f2;
                PathMeasure pathMeasure = new PathMeasure(pathB, false);
                ArrayList arrayList = new ArrayList();
                arrayList.add(Float.valueOf(0.0f));
                float length = 0.0f;
                do {
                    length += pathMeasure.getLength();
                    arrayList.add(Float.valueOf(length));
                } while (pathMeasure.nextContour());
                PathMeasure pathMeasure2 = new PathMeasure(pathB, false);
                int iMin = Math.min(100, ((int) (length / f3)) + 1);
                float[] fArr = new float[iMin];
                float[] fArr2 = new float[iMin];
                float[] fArr3 = new float[2];
                float f4 = length / (iMin - 1);
                valueAnimator2 = valueAnimator4;
                int i3 = 0;
                float f5 = 0.0f;
                while (true) {
                    if (i3 >= iMin) {
                        break;
                    }
                    int i4 = iMin;
                    pathMeasure2.getPosTan(f5 - ((Float) arrayList.get(i)).floatValue(), fArr3, null);
                    fArr[i3] = fArr3[0];
                    fArr2[i3] = fArr3[1];
                    f5 += f4;
                    int i5 = i + 1;
                    if (i5 < arrayList.size() && f5 > ((Float) arrayList.get(i5)).floatValue()) {
                        pathMeasure2.nextContour();
                        i = i5;
                    }
                    i3++;
                    iMin = i4;
                }
                PropertyValuesHolder propertyValuesHolderOfFloat = strA2 != null ? PropertyValuesHolder.ofFloat(strA2, fArr) : null;
                PropertyValuesHolder propertyValuesHolderOfFloat2 = strA3 != null ? PropertyValuesHolder.ofFloat(strA3, fArr2) : null;
                if (propertyValuesHolderOfFloat == null) {
                    resourceId = 0;
                    objectAnimator.setValues(propertyValuesHolderOfFloat2);
                } else {
                    resourceId = 0;
                    objectAnimator.setValues(propertyValuesHolderOfFloat2 == null ? new PropertyValuesHolder[]{propertyValuesHolderOfFloat} : new PropertyValuesHolder[]{propertyValuesHolderOfFloat, propertyValuesHolderOfFloat2});
                }
            } else {
                valueAnimator2 = valueAnimator4;
                resourceId = 0;
                objectAnimator.setPropertyName(a(typedArrayA2, xmlPullParser, "propertyName", 0));
            }
        } else {
            valueAnimator2 = valueAnimator4;
            resourceId = 0;
        }
        if (a(xmlPullParser, "interpolator")) {
            resourceId = typedArrayA.getResourceId(resourceId, resourceId);
        }
        if (resourceId > 0) {
            valueAnimator3 = valueAnimator2;
            valueAnimator3.setInterpolator(a(context, resourceId));
        } else {
            valueAnimator3 = valueAnimator2;
        }
        typedArrayA.recycle();
        if (typedArrayA2 != null) {
            typedArrayA2.recycle();
        }
        return valueAnimator3;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static a.b.d.b.a.b a(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            xmlPullParser.require(2, null, "font-family");
            if (xmlPullParser.getName().equals("font-family")) {
                TypedArray typedArrayObtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), a.b.a.b.FontFamily);
                String string = typedArrayObtainAttributes.getString(a.b.a.b.FontFamily_fontProviderAuthority);
                String string2 = typedArrayObtainAttributes.getString(a.b.a.b.FontFamily_fontProviderPackage);
                String string3 = typedArrayObtainAttributes.getString(a.b.a.b.FontFamily_fontProviderQuery);
                int resourceId = typedArrayObtainAttributes.getResourceId(a.b.a.b.FontFamily_fontProviderCerts, 0);
                int integer = typedArrayObtainAttributes.getInteger(a.b.a.b.FontFamily_fontProviderFetchStrategy, 1);
                int integer2 = typedArrayObtainAttributes.getInteger(a.b.a.b.FontFamily_fontProviderFetchTimeout, 500);
                typedArrayObtainAttributes.recycle();
                if (string != null && string2 != null && string3 != null) {
                    while (xmlPullParser.next() != 3) {
                        a(xmlPullParser);
                    }
                    return new a.b.d.b.a.e(new a.b.d.e.a(string, string2, string3, a(resources, resourceId)), integer, integer2);
                }
                ArrayList arrayList = new ArrayList();
                while (xmlPullParser.next() != 3) {
                    if (xmlPullParser.getEventType() == 2) {
                        if (xmlPullParser.getName().equals("font")) {
                            TypedArray typedArrayObtainAttributes2 = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), a.b.a.b.FontFamilyFont);
                            int i = typedArrayObtainAttributes2.getInt(typedArrayObtainAttributes2.hasValue(a.b.a.b.FontFamilyFont_fontWeight) ? a.b.a.b.FontFamilyFont_fontWeight : a.b.a.b.FontFamilyFont_android_fontWeight, 400);
                            boolean z = 1 == typedArrayObtainAttributes2.getInt(typedArrayObtainAttributes2.hasValue(a.b.a.b.FontFamilyFont_fontStyle) ? a.b.a.b.FontFamilyFont_fontStyle : a.b.a.b.FontFamilyFont_android_fontStyle, 0);
                            int i2 = typedArrayObtainAttributes2.hasValue(a.b.a.b.FontFamilyFont_ttcIndex) ? a.b.a.b.FontFamilyFont_ttcIndex : a.b.a.b.FontFamilyFont_android_ttcIndex;
                            String string4 = typedArrayObtainAttributes2.getString(typedArrayObtainAttributes2.hasValue(a.b.a.b.FontFamilyFont_fontVariationSettings) ? a.b.a.b.FontFamilyFont_fontVariationSettings : a.b.a.b.FontFamilyFont_android_fontVariationSettings);
                            int i3 = typedArrayObtainAttributes2.getInt(i2, 0);
                            int i4 = typedArrayObtainAttributes2.hasValue(a.b.a.b.FontFamilyFont_font) ? a.b.a.b.FontFamilyFont_font : a.b.a.b.FontFamilyFont_android_font;
                            int resourceId2 = typedArrayObtainAttributes2.getResourceId(i4, 0);
                            String string5 = typedArrayObtainAttributes2.getString(i4);
                            typedArrayObtainAttributes2.recycle();
                            while (xmlPullParser.next() != 3) {
                                a(xmlPullParser);
                            }
                            arrayList.add(new a.b.d.b.a.d(string5, i, z, string4, i3, resourceId2));
                        } else {
                            a(xmlPullParser);
                        }
                    }
                }
                if (arrayList.isEmpty()) {
                    return null;
                }
                return new a.b.d.b.a.c((a.b.d.b.a.d[]) arrayList.toArray(new a.b.d.b.a.d[arrayList.size()]));
            }
            a(xmlPullParser);
            return null;
        }
        throw new XmlPullParserException("No start tag found");
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static List<List<byte[]>> a(Resources resources, int i) {
        int type;
        if (i == 0) {
            return Collections.emptyList();
        }
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(i);
        try {
            if (typedArrayObtainTypedArray.length() == 0) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            if (Build.VERSION.SDK_INT >= 21) {
                type = typedArrayObtainTypedArray.getType(0);
            } else {
                TypedValue typedValue = new TypedValue();
                typedArrayObtainTypedArray.getValue(0, typedValue);
                type = typedValue.type;
            }
            if (type == 1) {
                for (int i2 = 0; i2 < typedArrayObtainTypedArray.length(); i2++) {
                    int resourceId = typedArrayObtainTypedArray.getResourceId(i2, 0);
                    if (resourceId != 0) {
                        arrayList.add(a(resources.getStringArray(resourceId)));
                    }
                }
            } else {
                arrayList.add(a(resources.getStringArray(i)));
            }
            return arrayList;
        } finally {
            typedArrayObtainTypedArray.recycle();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void a(TextView textView, a.b.d.f.a aVar) {
        CharSequence charSequence;
        if (Build.VERSION.SDK_INT >= 28) {
            Spannable spannable = aVar.f116a;
            charSequence = spannable instanceof PrecomputedText ? (PrecomputedText) spannable : null;
        } else {
            boolean zEquals = a(textView).equals(aVar.f117b);
            charSequence = aVar;
            if (!zEquals) {
                throw new IllegalArgumentException("Given text can not be applied to TextView.");
            }
        }
        textView.setText(charSequence);
    }

    public static void a(View view, CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setTooltipText(charSequence);
            return;
        }
        qa qaVar = qa.f370a;
        if (qaVar != null && qaVar.f372c == view) {
            qa.a((qa) null);
        }
        if (!TextUtils.isEmpty(charSequence)) {
            new qa(view, charSequence);
            return;
        }
        qa qaVar2 = qa.f371b;
        if (qaVar2 != null && qaVar2.f372c == view) {
            qaVar2.b();
        }
        view.setOnLongClickListener(null);
        view.setLongClickable(false);
        view.setOnHoverListener(null);
    }
}
