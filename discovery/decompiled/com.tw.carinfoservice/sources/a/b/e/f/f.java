package a.b.e.f;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import com.tw.carinfoservice.R;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public final class f {
    public static f h;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public WeakHashMap<Context, a.b.d.g.g<ColorStateList>> f273a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public a.b.d.g.a<String, d> f274b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public a.b.d.g.g<String> f275c;
    public final WeakHashMap<Context, a.b.d.g.c<WeakReference<Drawable.ConstantState>>> d = new WeakHashMap<>(0);
    public TypedValue e;
    public boolean f;
    public static final PorterDuff.Mode g = PorterDuff.Mode.SRC_IN;
    public static final c i = new c(6);
    public static final int[] j = {R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha};
    public static final int[] k = {R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_seekbar_tick_mark_material, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha};
    public static final int[] l = {R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material, R.drawable.abc_text_select_handle_left_mtrl_dark, R.drawable.abc_text_select_handle_middle_mtrl_dark, R.drawable.abc_text_select_handle_right_mtrl_dark, R.drawable.abc_text_select_handle_left_mtrl_light, R.drawable.abc_text_select_handle_middle_mtrl_light, R.drawable.abc_text_select_handle_right_mtrl_light};
    public static final int[] m = {R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult};
    public static final int[] n = {R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material};
    public static final int[] o = {R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material};

    public static class a implements d {
        @Override // a.b.e.f.f.d
        public Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return a.b.e.d.a.a.g(context, context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("AsldcInflateDelegate", "Exception while inflating <animated-selector>", e);
                return null;
            }
        }
    }

    public static class b implements d {
        @Override // a.b.e.f.f.d
        public Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                Resources resources = context.getResources();
                a.b.b.a.b bVar = new a.b.b.a.b(context, null, null);
                bVar.inflate(resources, xmlPullParser, attributeSet, theme);
                return bVar;
            } catch (Exception e) {
                Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", e);
                return null;
            }
        }
    }

    public static class c extends a.b.d.g.d<Integer, PorterDuffColorFilter> {
        public c(int i) {
            super(i);
        }
    }

    public interface d {
        Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme);
    }

    public static class e implements d {
        @Override // a.b.e.f.f.d
        public Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return a.b.b.a.g.a(context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("VdcInflateDelegate", "Exception while inflating <vector>", e);
                return null;
            }
        }
    }

    public static boolean c(int[] iArr, int i2) {
        for (int i3 : iArr) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    public static synchronized f g() {
        if (h == null) {
            f fVar = new f();
            h = fVar;
            if (Build.VERSION.SDK_INT < 24) {
                fVar.a("vector", new e());
                fVar.a("animated-vector", new b());
                fVar.a("animated-selector", new a());
            }
        }
        return h;
    }

    public static synchronized PorterDuffColorFilter k(int i2, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilterA;
        c cVar = i;
        Objects.requireNonNull(cVar);
        int i3 = (i2 + 31) * 31;
        porterDuffColorFilterA = cVar.a(Integer.valueOf(mode.hashCode() + i3));
        if (porterDuffColorFilterA == null) {
            porterDuffColorFilterA = new PorterDuffColorFilter(i2, mode);
            Objects.requireNonNull(cVar);
            cVar.b(Integer.valueOf(mode.hashCode() + i3), porterDuffColorFilterA);
        }
        return porterDuffColorFilterA;
    }

    public static void n(Drawable drawable, int i2, PorterDuff.Mode mode) {
        if (p.a(drawable)) {
            drawable = drawable.mutate();
        }
        if (mode == null) {
            mode = g;
        }
        drawable.setColorFilter(k(i2, mode));
    }

    public static void p(Drawable drawable, e0 e0Var, int[] iArr) {
        if (p.a(drawable) && drawable.mutate() != drawable) {
            Log.d("AppCompatDrawableManag", "Mutated drawable is not the same instance as the input.");
            return;
        }
        boolean z = e0Var.d;
        if (z || e0Var.f272c) {
            PorterDuffColorFilter porterDuffColorFilterK = null;
            ColorStateList colorStateList = z ? e0Var.f270a : null;
            PorterDuff.Mode mode = e0Var.f272c ? e0Var.f271b : g;
            if (colorStateList != null && mode != null) {
                porterDuffColorFilterK = k(colorStateList.getColorForState(iArr, 0), mode);
            }
            drawable.setColorFilter(porterDuffColorFilterK);
        } else {
            drawable.clearColorFilter();
        }
        if (Build.VERSION.SDK_INT <= 23) {
            drawable.invalidateSelf();
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0048  */
    /* JADX WARN: Code duplicated, block: B:23:0x004e  */
    /* JADX WARN: Code duplicated, block: B:26:0x005f  */
    /* JADX WARN: Code duplicated, block: B:28:0x0063 A[RETURN] */
    public static boolean q(Context context, int i2, Drawable drawable) {
        int iRound;
        boolean z;
        PorterDuff.Mode mode = g;
        boolean zC = c(j, i2);
        int i3 = android.R.attr.colorBackground;
        if (zC) {
            i3 = R.attr.colorControlNormal;
        } else if (c(l, i2)) {
            i3 = R.attr.colorControlActivated;
        } else {
            if (!c(m, i2)) {
                if (i2 == R.drawable.abc_list_divider_mtrl_alpha) {
                    i3 = android.R.attr.colorForeground;
                    iRound = Math.round(40.8f);
                    z = true;
                } else if (i2 != R.drawable.abc_dialog_material_background) {
                    iRound = -1;
                    z = false;
                    i3 = 0;
                }
                if (z) {
                    return false;
                }
                if (p.a(drawable)) {
                    drawable = drawable.mutate();
                }
                drawable.setColorFilter(k(c0.b(context, i3), mode));
                if (iRound != -1) {
                    drawable.setAlpha(iRound);
                }
                return true;
            }
            mode = PorterDuff.Mode.MULTIPLY;
        }
        iRound = -1;
        z = true;
        if (z) {
            return false;
        }
        if (p.a(drawable)) {
            drawable = drawable.mutate();
        }
        drawable.setColorFilter(k(c0.b(context, i3), mode));
        if (iRound != -1) {
            drawable.setAlpha(iRound);
        }
        return true;
    }

    public final void a(String str, d dVar) {
        if (this.f274b == null) {
            this.f274b = new a.b.d.g.a<>();
        }
        this.f274b.put(str, dVar);
    }

    public final synchronized boolean b(Context context, long j2, Drawable drawable) {
        boolean z;
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState != null) {
            a.b.d.g.c<WeakReference<Drawable.ConstantState>> cVar = this.d.get(context);
            if (cVar == null) {
                cVar = new a.b.d.g.c<>();
                this.d.put(context, cVar);
            }
            cVar.e(j2, new WeakReference<>(constantState));
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public final ColorStateList d(Context context, int i2) {
        int iB = c0.b(context, R.attr.colorControlHighlight);
        return new ColorStateList(new int[][]{c0.f260b, c0.d, c0.f261c, c0.f}, new int[]{c0.a(context, R.attr.colorButtonNormal), a.b.d.c.a.a(iB, i2), a.b.d.c.a.a(iB, i2), i2});
    }

    public final Drawable e(Context context, int i2) {
        if (this.e == null) {
            this.e = new TypedValue();
        }
        TypedValue typedValue = this.e;
        context.getResources().getValue(i2, typedValue, true);
        long j2 = (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
        Drawable drawableH = h(context, j2);
        if (drawableH != null) {
            return drawableH;
        }
        if (i2 == R.drawable.abc_cab_background_top_material) {
            drawableH = new LayerDrawable(new Drawable[]{i(context, R.drawable.abc_cab_background_internal_bg), i(context, R.drawable.abc_cab_background_top_mtrl_alpha)});
        }
        if (drawableH != null) {
            drawableH.setChangingConfigurations(typedValue.changingConfigurations);
            b(context, j2, drawableH);
        }
        return drawableH;
    }

    public final ColorStateList f(Context context) {
        int[][] iArr = new int[3][];
        int[] iArr2 = new int[3];
        ColorStateList colorStateListC = c0.c(context, R.attr.colorSwitchThumbNormal);
        if (colorStateListC == null || !colorStateListC.isStateful()) {
            iArr[0] = c0.f260b;
            iArr2[0] = c0.a(context, R.attr.colorSwitchThumbNormal);
            iArr[1] = c0.e;
            iArr2[1] = c0.b(context, R.attr.colorControlActivated);
            iArr[2] = c0.f;
            iArr2[2] = c0.b(context, R.attr.colorSwitchThumbNormal);
        } else {
            iArr[0] = c0.f260b;
            iArr2[0] = colorStateListC.getColorForState(iArr[0], 0);
            iArr[1] = c0.e;
            iArr2[1] = c0.b(context, R.attr.colorControlActivated);
            iArr[2] = c0.f;
            iArr2[2] = colorStateListC.getDefaultColor();
        }
        return new ColorStateList(iArr, iArr2);
    }

    public final synchronized Drawable h(Context context, long j2) {
        a.b.d.g.c<WeakReference<Drawable.ConstantState>> cVar = this.d.get(context);
        if (cVar == null) {
            return null;
        }
        WeakReference<Drawable.ConstantState> weakReferenceD = cVar.d(j2, null);
        if (weakReferenceD != null) {
            Drawable.ConstantState constantState = weakReferenceD.get();
            if (constantState != null) {
                return constantState.newDrawable(context.getResources());
            }
            int iB = a.b.d.g.b.b(cVar.f118b, cVar.d, j2);
            if (iB >= 0) {
                Object[] objArr = cVar.f119c;
                Object obj = objArr[iB];
                Object obj2 = a.b.d.g.c.e;
                if (obj != obj2) {
                    objArr[iB] = obj2;
                    cVar.f117a = true;
                }
            }
        }
        return null;
    }

    public synchronized Drawable i(Context context, int i2) {
        return j(context, i2, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0029, code lost:
    
        if (r0 != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized android.graphics.drawable.Drawable j(android.content.Context r5, int r6, boolean r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.f     // Catch: java.lang.Throwable -> L48
            if (r0 == 0) goto L6
            goto L2b
        L6:
            r0 = 1
            r4.f = r0     // Catch: java.lang.Throwable -> L48
            r1 = 2131165267(0x7f070053, float:1.7944746E38)
            android.graphics.drawable.Drawable r1 = r4.i(r5, r1)     // Catch: java.lang.Throwable -> L48
            r2 = 0
            if (r1 == 0) goto L4a
            boolean r3 = r1 instanceof a.b.b.a.g     // Catch: java.lang.Throwable -> L48
            if (r3 != 0) goto L29
            java.lang.String r3 = "android.graphics.drawable.VectorDrawable"
            java.lang.Class r1 = r1.getClass()     // Catch: java.lang.Throwable -> L48
            java.lang.String r1 = r1.getName()     // Catch: java.lang.Throwable -> L48
            boolean r1 = r3.equals(r1)     // Catch: java.lang.Throwable -> L48
            if (r1 == 0) goto L28
            goto L29
        L28:
            r0 = 0
        L29:
            if (r0 == 0) goto L4a
        L2b:
            android.graphics.drawable.Drawable r0 = r4.m(r5, r6)     // Catch: java.lang.Throwable -> L48
            if (r0 != 0) goto L35
            android.graphics.drawable.Drawable r0 = r4.e(r5, r6)     // Catch: java.lang.Throwable -> L48
        L35:
            if (r0 != 0) goto L3b
            android.graphics.drawable.Drawable r0 = a.b.d.b.a.b(r5, r6)     // Catch: java.lang.Throwable -> L48
        L3b:
            if (r0 == 0) goto L41
            android.graphics.drawable.Drawable r0 = r4.o(r5, r6, r7, r0)     // Catch: java.lang.Throwable -> L48
        L41:
            if (r0 == 0) goto L46
            a.b.e.f.p.b(r0)     // Catch: java.lang.Throwable -> L48
        L46:
            monitor-exit(r4)
            return r0
        L48:
            r5 = move-exception
            goto L54
        L4a:
            r4.f = r2     // Catch: java.lang.Throwable -> L48
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L48
            java.lang.String r6 = "This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat."
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L48
            throw r5     // Catch: java.lang.Throwable -> L48
        L54:
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: a.b.e.f.f.j(android.content.Context, int, boolean):android.graphics.drawable.Drawable");
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00ad A[Catch: all -> 0x0025, TryCatch #0 {all -> 0x0025, blocks: (B:3:0x0001, B:5:0x0006, B:7:0x000e, B:12:0x001f, B:49:0x00ad, B:51:0x00b1, B:52:0x00b8, B:54:0x00c2, B:55:0x00cc, B:20:0x0036, B:23:0x0041, B:26:0x0052, B:29:0x005d, B:35:0x0074, B:37:0x007c, B:38:0x0084, B:41:0x0090), top: B:62:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:51:0x00b1 A[Catch: all -> 0x0025, TryCatch #0 {all -> 0x0025, blocks: (B:3:0x0001, B:5:0x0006, B:7:0x000e, B:12:0x001f, B:49:0x00ad, B:51:0x00b1, B:52:0x00b8, B:54:0x00c2, B:55:0x00cc, B:20:0x0036, B:23:0x0041, B:26:0x0052, B:29:0x005d, B:35:0x0074, B:37:0x007c, B:38:0x0084, B:41:0x0090), top: B:62:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:54:0x00c2 A[Catch: all -> 0x0025, TryCatch #0 {all -> 0x0025, blocks: (B:3:0x0001, B:5:0x0006, B:7:0x000e, B:12:0x001f, B:49:0x00ad, B:51:0x00b1, B:52:0x00b8, B:54:0x00c2, B:55:0x00cc, B:20:0x0036, B:23:0x0041, B:26:0x0052, B:29:0x005d, B:35:0x0074, B:37:0x007c, B:38:0x0084, B:41:0x0090), top: B:62:0x0001 }] */
    public synchronized ColorStateList l(Context context, int i2) {
        ColorStateList colorStateListC;
        int i3;
        a.b.d.g.g<ColorStateList> gVar;
        a.b.d.g.g<ColorStateList> gVar2;
        try {
            WeakHashMap<Context, a.b.d.g.g<ColorStateList>> weakHashMap = this.f273a;
            colorStateListC = null;
            if (weakHashMap != null && (gVar2 = weakHashMap.get(context)) != null) {
                colorStateListC = gVar2.c(i2, null);
            }
            if (colorStateListC == null) {
                if (i2 == R.drawable.abc_edit_text_material) {
                    i3 = R.color.abc_tint_edittext;
                } else if (i2 == R.drawable.abc_switch_track_mtrl_alpha) {
                    i3 = R.color.abc_tint_switch_track;
                } else {
                    if (i2 == R.drawable.abc_switch_thumb_material) {
                        colorStateListC = f(context);
                    } else if (i2 == R.drawable.abc_btn_default_mtrl_shape) {
                        colorStateListC = d(context, c0.b(context, R.attr.colorButtonNormal));
                    } else if (i2 == R.drawable.abc_btn_borderless_material) {
                        colorStateListC = d(context, 0);
                    } else if (i2 == R.drawable.abc_btn_colored_material) {
                        colorStateListC = d(context, c0.b(context, R.attr.colorAccent));
                    } else if (i2 == R.drawable.abc_spinner_mtrl_am_alpha || i2 == R.drawable.abc_spinner_textfield_background_material) {
                        i3 = R.color.abc_tint_spinner;
                    } else if (c(k, i2)) {
                        colorStateListC = c0.c(context, R.attr.colorControlNormal);
                    } else if (c(n, i2)) {
                        i3 = R.color.abc_tint_default;
                    } else if (c(o, i2)) {
                        i3 = R.color.abc_tint_btn_checkable;
                    } else if (i2 == R.drawable.abc_seekbar_thumb_material) {
                        i3 = R.color.abc_tint_seek_thumb;
                    }
                    if (colorStateListC != null) {
                        if (this.f273a == null) {
                            this.f273a = new WeakHashMap<>();
                        }
                        gVar = this.f273a.get(context);
                        if (gVar == null) {
                            gVar = new a.b.d.g.g<>();
                            this.f273a.put(context, gVar);
                        }
                        gVar.a(i2, colorStateListC);
                    }
                }
                colorStateListC = a.b.e.c.a.a.a(context, i3);
                if (colorStateListC != null) {
                    if (this.f273a == null) {
                        this.f273a = new WeakHashMap<>();
                    }
                    gVar = this.f273a.get(context);
                    if (gVar == null) {
                        gVar = new a.b.d.g.g<>();
                        this.f273a.put(context, gVar);
                    }
                    gVar.a(i2, colorStateListC);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return colorStateListC;
    }

    public final Drawable m(Context context, int i2) {
        int next;
        a.b.d.g.a<String, d> aVar = this.f274b;
        if (aVar == null || aVar.isEmpty()) {
            return null;
        }
        a.b.d.g.g<String> gVar = this.f275c;
        if (gVar != null) {
            String strC = gVar.c(i2, null);
            if ("appcompat_skip_skip".equals(strC) || (strC != null && this.f274b.get(strC) == null)) {
                return null;
            }
        } else {
            this.f275c = new a.b.d.g.g<>();
        }
        if (this.e == null) {
            this.e = new TypedValue();
        }
        TypedValue typedValue = this.e;
        Resources resources = context.getResources();
        resources.getValue(i2, typedValue, true);
        long j2 = (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
        Drawable drawableH = h(context, j2);
        if (drawableH != null) {
            return drawableH;
        }
        CharSequence charSequence = typedValue.string;
        if (charSequence != null && charSequence.toString().endsWith(".xml")) {
            try {
                XmlResourceParser xml = resources.getXml(i2);
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
                this.f275c.a(i2, name);
                d dVar = this.f274b.get(name);
                if (dVar != null) {
                    drawableH = dVar.a(context, xml, attributeSetAsAttributeSet, context.getTheme());
                }
                if (drawableH != null) {
                    drawableH.setChangingConfigurations(typedValue.changingConfigurations);
                    b(context, j2, drawableH);
                }
            } catch (Exception e2) {
                Log.e("AppCompatDrawableManag", "Exception while inflating drawable", e2);
            }
        }
        if (drawableH == null) {
            this.f275c.a(i2, "appcompat_skip_skip");
        }
        return drawableH;
    }

    public final Drawable o(Context context, int i2, boolean z, Drawable drawable) {
        LayerDrawable layerDrawable;
        Drawable drawableFindDrawableByLayerId;
        int iB;
        PorterDuff.Mode mode = g;
        ColorStateList colorStateListL = l(context, i2);
        if (colorStateListL != null) {
            if (p.a(drawable)) {
                drawable = drawable.mutate();
            }
            Drawable drawableY = a.b.a.a.Y(drawable);
            a.b.a.a.T(drawableY, colorStateListL);
            PorterDuff.Mode mode2 = i2 == R.drawable.abc_switch_thumb_material ? PorterDuff.Mode.MULTIPLY : null;
            if (mode2 == null) {
                return drawableY;
            }
            a.b.a.a.U(drawableY, mode2);
            return drawableY;
        }
        if (i2 == R.drawable.abc_seekbar_track_material) {
            layerDrawable = (LayerDrawable) drawable;
            n(layerDrawable.findDrawableByLayerId(android.R.id.background), c0.b(context, R.attr.colorControlNormal), mode);
            drawableFindDrawableByLayerId = layerDrawable.findDrawableByLayerId(android.R.id.secondaryProgress);
            iB = c0.b(context, R.attr.colorControlNormal);
        } else {
            if (i2 != R.drawable.abc_ratingbar_material && i2 != R.drawable.abc_ratingbar_indicator_material && i2 != R.drawable.abc_ratingbar_small_material) {
                if (q(context, i2, drawable) || !z) {
                    return drawable;
                }
                return null;
            }
            layerDrawable = (LayerDrawable) drawable;
            n(layerDrawable.findDrawableByLayerId(android.R.id.background), c0.a(context, R.attr.colorControlNormal), mode);
            drawableFindDrawableByLayerId = layerDrawable.findDrawableByLayerId(android.R.id.secondaryProgress);
            iB = c0.b(context, R.attr.colorControlActivated);
        }
        n(drawableFindDrawableByLayerId, iB, mode);
        n(layerDrawable.findDrawableByLayerId(android.R.id.progress), c0.b(context, R.attr.colorControlActivated), mode);
        return drawable;
    }
}
