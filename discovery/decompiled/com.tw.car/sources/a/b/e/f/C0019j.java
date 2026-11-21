package a.b.e.f;

import android.R;
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
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: a.b.e.f.j, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0019j {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static C0019j f344b;
    public WeakHashMap<Context, a.b.d.g.h<ColorStateList>> j;
    public a.b.d.g.b<String, d> k;
    public a.b.d.g.h<String> l;
    public final WeakHashMap<Context, a.b.d.g.d<WeakReference<Drawable.ConstantState>>> m = new WeakHashMap<>(0);
    public TypedValue n;
    public boolean o;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final PorterDuff.Mode f343a = PorterDuff.Mode.SRC_IN;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final c f345c = new c(6);
    public static final int[] d = {a.b.e.b.e.abc_textfield_search_default_mtrl_alpha, a.b.e.b.e.abc_textfield_default_mtrl_alpha, a.b.e.b.e.abc_ab_share_pack_mtrl_alpha};
    public static final int[] e = {a.b.e.b.e.abc_ic_commit_search_api_mtrl_alpha, a.b.e.b.e.abc_seekbar_tick_mark_material, a.b.e.b.e.abc_ic_menu_share_mtrl_alpha, a.b.e.b.e.abc_ic_menu_copy_mtrl_am_alpha, a.b.e.b.e.abc_ic_menu_cut_mtrl_alpha, a.b.e.b.e.abc_ic_menu_selectall_mtrl_alpha, a.b.e.b.e.abc_ic_menu_paste_mtrl_am_alpha};
    public static final int[] f = {a.b.e.b.e.abc_textfield_activated_mtrl_alpha, a.b.e.b.e.abc_textfield_search_activated_mtrl_alpha, a.b.e.b.e.abc_cab_background_top_mtrl_alpha, a.b.e.b.e.abc_text_cursor_material, a.b.e.b.e.abc_text_select_handle_left_mtrl_dark, a.b.e.b.e.abc_text_select_handle_middle_mtrl_dark, a.b.e.b.e.abc_text_select_handle_right_mtrl_dark, a.b.e.b.e.abc_text_select_handle_left_mtrl_light, a.b.e.b.e.abc_text_select_handle_middle_mtrl_light, a.b.e.b.e.abc_text_select_handle_right_mtrl_light};
    public static final int[] g = {a.b.e.b.e.abc_popup_background_mtrl_mult, a.b.e.b.e.abc_cab_background_internal_bg, a.b.e.b.e.abc_menu_hardkey_panel_mtrl_mult};
    public static final int[] h = {a.b.e.b.e.abc_tab_indicator_material, a.b.e.b.e.abc_textfield_search_material};
    public static final int[] i = {a.b.e.b.e.abc_btn_check_material, a.b.e.b.e.abc_btn_radio_material};

    /* JADX INFO: renamed from: a.b.e.f.j$a */
    static class a implements d {
        @Override // a.b.e.f.C0019j.d
        public Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return a.b.e.d.a.b.a(context, context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("AsldcInflateDelegate", "Exception while inflating <animated-selector>", e);
                return null;
            }
        }
    }

    /* JADX INFO: renamed from: a.b.e.f.j$b */
    private static class b implements d {
        @Override // a.b.e.f.C0019j.d
        public Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                Resources resources = context.getResources();
                a.b.b.a.d dVar = new a.b.b.a.d(context, null, null);
                dVar.inflate(resources, xmlPullParser, attributeSet, theme);
                return dVar;
            } catch (Exception e) {
                Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", e);
                return null;
            }
        }
    }

    /* JADX INFO: renamed from: a.b.e.f.j$d */
    private interface d {
        Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme);
    }

    /* JADX INFO: renamed from: a.b.e.f.j$e */
    private static class e implements d {
        @Override // a.b.e.f.C0019j.d
        public Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return a.b.b.a.k.createFromXmlInner(context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("VdcInflateDelegate", "Exception while inflating <vector>", e);
                return null;
            }
        }
    }

    public static synchronized C0019j a() {
        if (f344b == null) {
            f344b = new C0019j();
            C0019j c0019j = f344b;
            if (Build.VERSION.SDK_INT < 24) {
                c0019j.a("vector", new e());
                c0019j.a("animated-vector", new b());
                c0019j.a("animated-selector", new a());
            }
        }
        return f344b;
    }

    public static synchronized PorterDuffColorFilter a(int i2, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilterA;
        porterDuffColorFilterA = f345c.a(i2, mode);
        if (porterDuffColorFilterA == null) {
            porterDuffColorFilterA = new PorterDuffColorFilter(i2, mode);
            f345c.a(i2, mode, porterDuffColorFilterA);
        }
        return porterDuffColorFilterA;
    }

    public static void a(Drawable drawable, int i2, PorterDuff.Mode mode) {
        if (A.a(drawable)) {
            drawable = drawable.mutate();
        }
        if (mode == null) {
            mode = f343a;
        }
        drawable.setColorFilter(a(i2, mode));
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0046  */
    /* JADX WARN: Code duplicated, block: B:22:0x004c  */
    /* JADX WARN: Code duplicated, block: B:25:0x005d  */
    /* JADX WARN: Code duplicated, block: B:27:0x0061 A[RETURN] */
    public static boolean a(Context context, int i2, Drawable drawable) {
        boolean z;
        int iRound;
        PorterDuff.Mode mode = f343a;
        boolean zA = a(d, i2);
        int i3 = R.attr.colorBackground;
        if (zA) {
            i3 = a.b.e.b.a.colorControlNormal;
        } else if (a(f, i2)) {
            i3 = a.b.e.b.a.colorControlActivated;
        } else {
            if (!a(g, i2)) {
                if (i2 == a.b.e.b.e.abc_list_divider_mtrl_alpha) {
                    i3 = R.attr.colorForeground;
                    iRound = Math.round(40.8f);
                    z = true;
                } else if (i2 != a.b.e.b.e.abc_dialog_material_background) {
                    z = false;
                    iRound = -1;
                    i3 = 0;
                }
                if (z) {
                    return false;
                }
                if (A.a(drawable)) {
                    drawable = drawable.mutate();
                }
                drawable.setColorFilter(a(ca.b(context, i3), mode));
                if (iRound != -1) {
                    drawable.setAlpha(iRound);
                }
                return true;
            }
            mode = PorterDuff.Mode.MULTIPLY;
        }
        z = true;
        iRound = -1;
        if (z) {
            return false;
        }
        if (A.a(drawable)) {
            drawable = drawable.mutate();
        }
        drawable.setColorFilter(a(ca.b(context, i3), mode));
        if (iRound != -1) {
            drawable.setAlpha(iRound);
        }
        return true;
    }

    public static boolean a(int[] iArr, int i2) {
        for (int i3 : iArr) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    public final ColorStateList a(Context context) {
        int[][] iArr = new int[3][];
        int[] iArr2 = new int[3];
        ColorStateList colorStateListC = ca.c(context, a.b.e.b.a.colorSwitchThumbNormal);
        if (colorStateListC == null || !colorStateListC.isStateful()) {
            iArr[0] = ca.f317b;
            iArr2[0] = ca.a(context, a.b.e.b.a.colorSwitchThumbNormal);
            iArr[1] = ca.e;
            iArr2[1] = ca.b(context, a.b.e.b.a.colorControlActivated);
            iArr[2] = ca.f;
            iArr2[2] = ca.b(context, a.b.e.b.a.colorSwitchThumbNormal);
        } else {
            iArr[0] = ca.f317b;
            iArr2[0] = colorStateListC.getColorForState(iArr[0], 0);
            iArr[1] = ca.e;
            iArr2[1] = ca.b(context, a.b.e.b.a.colorControlActivated);
            iArr[2] = ca.f;
            iArr2[2] = colorStateListC.getDefaultColor();
        }
        return new ColorStateList(iArr, iArr2);
    }

    public final ColorStateList a(Context context, int i2) {
        int iB = ca.b(context, a.b.e.b.a.colorControlHighlight);
        return new ColorStateList(new int[][]{ca.f317b, ca.d, ca.f318c, ca.f}, new int[]{ca.a(context, a.b.e.b.a.colorButtonNormal), a.b.d.c.a.a(iB, i2), a.b.d.c.a.a(iB, i2), i2});
    }

    public synchronized Drawable a(Context context, sa saVar, int i2) {
        Drawable drawableE = e(context, i2);
        if (drawableE == null) {
            drawableE = saVar.a(i2);
        }
        if (drawableE == null) {
            return null;
        }
        return a(context, i2, false, drawableE);
    }

    public final void a(String str, d dVar) {
        if (this.k == null) {
            this.k = new a.b.d.g.b<>();
        }
        this.k.put(str, dVar);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final synchronized boolean a(Context context, long j, Drawable drawable) {
        boolean z;
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState != null) {
            a.b.d.g.d<WeakReference<Drawable.ConstantState>> dVar = this.m.get(context);
            if (dVar == null) {
                dVar = new a.b.d.g.d<>();
                this.m.put(context, dVar);
            }
            dVar.c(j, new WeakReference<>(constantState));
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public final Drawable b(Context context, int i2) {
        if (this.n == null) {
            this.n = new TypedValue();
        }
        TypedValue typedValue = this.n;
        context.getResources().getValue(i2, typedValue, true);
        long j = (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
        Drawable drawableA = a(context, j);
        if (drawableA != null) {
            return drawableA;
        }
        if (i2 == a.b.e.b.e.abc_cab_background_top_material) {
            drawableA = new LayerDrawable(new Drawable[]{c(context, a.b.e.b.e.abc_cab_background_internal_bg), c(context, a.b.e.b.e.abc_cab_background_top_mtrl_alpha)});
        }
        if (drawableA != null) {
            drawableA.setChangingConfigurations(typedValue.changingConfigurations);
            a(context, j, drawableA);
        }
        return drawableA;
    }

    public synchronized Drawable c(Context context, int i2) {
        return a(context, i2, false);
    }

    /* JADX WARN: Code duplicated, block: B:47:0x0095 A[Catch: all -> 0x00b8, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0006, B:7:0x000e, B:9:0x0017, B:11:0x001b, B:12:0x001d, B:47:0x0095, B:49:0x0099, B:50:0x00a0, B:52:0x00aa, B:53:0x00b4, B:13:0x0023, B:15:0x0027, B:16:0x002a, B:18:0x002e, B:19:0x0033, B:21:0x0037, B:22:0x0042, B:24:0x0046, B:25:0x004c, B:27:0x0050, B:28:0x005b, B:30:0x005f, B:33:0x0064, B:35:0x006c, B:36:0x0073, B:38:0x007b, B:39:0x007e, B:41:0x0086, B:42:0x0089, B:44:0x008d, B:45:0x0090), top: B:62:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x0099 A[Catch: all -> 0x00b8, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0006, B:7:0x000e, B:9:0x0017, B:11:0x001b, B:12:0x001d, B:47:0x0095, B:49:0x0099, B:50:0x00a0, B:52:0x00aa, B:53:0x00b4, B:13:0x0023, B:15:0x0027, B:16:0x002a, B:18:0x002e, B:19:0x0033, B:21:0x0037, B:22:0x0042, B:24:0x0046, B:25:0x004c, B:27:0x0050, B:28:0x005b, B:30:0x005f, B:33:0x0064, B:35:0x006c, B:36:0x0073, B:38:0x007b, B:39:0x007e, B:41:0x0086, B:42:0x0089, B:44:0x008d, B:45:0x0090), top: B:62:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:52:0x00aa A[Catch: all -> 0x00b8, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0006, B:7:0x000e, B:9:0x0017, B:11:0x001b, B:12:0x001d, B:47:0x0095, B:49:0x0099, B:50:0x00a0, B:52:0x00aa, B:53:0x00b4, B:13:0x0023, B:15:0x0027, B:16:0x002a, B:18:0x002e, B:19:0x0033, B:21:0x0037, B:22:0x0042, B:24:0x0046, B:25:0x004c, B:27:0x0050, B:28:0x005b, B:30:0x005f, B:33:0x0064, B:35:0x006c, B:36:0x0073, B:38:0x007b, B:39:0x007e, B:41:0x0086, B:42:0x0089, B:44:0x008d, B:45:0x0090), top: B:62:0x0001 }] */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public synchronized ColorStateList d(Context context, int i2) {
        ColorStateList colorStateListC;
        int i3;
        a.b.d.g.h<ColorStateList> hVar;
        a.b.d.g.h<ColorStateList> hVar2;
        WeakHashMap<Context, a.b.d.g.h<ColorStateList>> weakHashMap = this.j;
        colorStateListC = null;
        if (weakHashMap != null && (hVar2 = weakHashMap.get(context)) != null) {
            colorStateListC = hVar2.a(i2);
        }
        if (colorStateListC == null) {
            if (i2 == a.b.e.b.e.abc_edit_text_material) {
                i3 = a.b.e.b.c.abc_tint_edittext;
            } else if (i2 == a.b.e.b.e.abc_switch_track_mtrl_alpha) {
                i3 = a.b.e.b.c.abc_tint_switch_track;
            } else {
                if (i2 == a.b.e.b.e.abc_switch_thumb_material) {
                    colorStateListC = a(context);
                } else if (i2 == a.b.e.b.e.abc_btn_default_mtrl_shape) {
                    colorStateListC = a(context, ca.b(context, a.b.e.b.a.colorButtonNormal));
                } else if (i2 == a.b.e.b.e.abc_btn_borderless_material) {
                    colorStateListC = a(context, 0);
                } else if (i2 == a.b.e.b.e.abc_btn_colored_material) {
                    colorStateListC = a(context, ca.b(context, a.b.e.b.a.colorAccent));
                } else if (i2 == a.b.e.b.e.abc_spinner_mtrl_am_alpha || i2 == a.b.e.b.e.abc_spinner_textfield_background_material) {
                    i3 = a.b.e.b.c.abc_tint_spinner;
                } else if (a(e, i2)) {
                    colorStateListC = ca.c(context, a.b.e.b.a.colorControlNormal);
                } else if (a(h, i2)) {
                    i3 = a.b.e.b.c.abc_tint_default;
                } else if (a(i, i2)) {
                    i3 = a.b.e.b.c.abc_tint_btn_checkable;
                } else if (i2 == a.b.e.b.e.abc_seekbar_thumb_material) {
                    i3 = a.b.e.b.c.abc_tint_seek_thumb;
                }
                if (colorStateListC != null) {
                    if (this.j == null) {
                        this.j = new WeakHashMap<>();
                    }
                    hVar = this.j.get(context);
                    if (hVar == null) {
                        hVar = new a.b.d.g.h<>();
                        this.j.put(context, hVar);
                    }
                    hVar.a(i2, colorStateListC);
                }
            }
            colorStateListC = a.b.e.c.a.a.b(context, i3);
            if (colorStateListC != null) {
                if (this.j == null) {
                    this.j = new WeakHashMap<>();
                }
                hVar = this.j.get(context);
                if (hVar == null) {
                    hVar = new a.b.d.g.h<>();
                    this.j.put(context, hVar);
                }
                hVar.a(i2, colorStateListC);
            }
        }
        return colorStateListC;
    }

    public final Drawable e(Context context, int i2) {
        int next;
        a.b.d.g.b<String, d> bVar = this.k;
        if (bVar != null) {
            if (!(bVar.g <= 0)) {
                a.b.d.g.h<String> hVar = this.l;
                if (hVar != null) {
                    String strB = hVar.b(i2, null);
                    if ("appcompat_skip_skip".equals(strB) || (strB != null && this.k.get(strB) == null)) {
                        return null;
                    }
                } else {
                    this.l = new a.b.d.g.h<>();
                }
                if (this.n == null) {
                    this.n = new TypedValue();
                }
                TypedValue typedValue = this.n;
                Resources resources = context.getResources();
                resources.getValue(i2, typedValue, true);
                long j = (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
                Drawable drawableA = a(context, j);
                if (drawableA != null) {
                    return drawableA;
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
                        this.l.a(i2, name);
                        d dVar = this.k.get(name);
                        if (dVar != null) {
                            drawableA = dVar.a(context, xml, attributeSetAsAttributeSet, context.getTheme());
                        }
                        if (drawableA != null) {
                            drawableA.setChangingConfigurations(typedValue.changingConfigurations);
                            a(context, j, drawableA);
                        }
                    } catch (Exception e2) {
                        Log.e("AppCompatDrawableManag", "Exception while inflating drawable", e2);
                    }
                }
                if (drawableA == null) {
                    this.l.a(i2, "appcompat_skip_skip");
                }
                return drawableA;
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: a.b.e.f.j$c */
    private static class c extends a.b.d.g.e<Integer, PorterDuffColorFilter> {
        public c(int i) {
            super(i);
        }

        public PorterDuffColorFilter a(int i, PorterDuff.Mode mode) {
            return a(Integer.valueOf(mode.hashCode() + ((i + 31) * 31)));
        }

        public PorterDuffColorFilter a(int i, PorterDuff.Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return a(Integer.valueOf(mode.hashCode() + ((i + 31) * 31)), porterDuffColorFilter);
        }
    }

    public final synchronized Drawable a(Context context, long j) {
        a.b.d.g.d<WeakReference<Drawable.ConstantState>> dVar = this.m.get(context);
        if (dVar == null) {
            return null;
        }
        WeakReference<Drawable.ConstantState> weakReferenceB = dVar.b(j, null);
        if (weakReferenceB != null) {
            Drawable.ConstantState constantState = weakReferenceB.get();
            if (constantState != null) {
                return constantState.newDrawable(context.getResources());
            }
            int iA = a.b.d.g.c.a(dVar.f127c, dVar.e, j);
            if (iA >= 0) {
                Object[] objArr = dVar.d;
                Object obj = objArr[iA];
                Object obj2 = a.b.d.g.d.f125a;
                if (obj != obj2) {
                    objArr[iA] = obj2;
                    dVar.f126b = true;
                }
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
    
        if (r0 != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized android.graphics.drawable.Drawable a(android.content.Context r5, int r6, boolean r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.o     // Catch: java.lang.Throwable -> L47
            if (r0 == 0) goto L6
            goto L2a
        L6:
            r0 = 1
            r4.o = r0     // Catch: java.lang.Throwable -> L47
            int r1 = a.b.e.b.e.abc_vector_test     // Catch: java.lang.Throwable -> L47
            android.graphics.drawable.Drawable r1 = r4.c(r5, r1)     // Catch: java.lang.Throwable -> L47
            r2 = 0
            if (r1 == 0) goto L49
            boolean r3 = r1 instanceof a.b.b.a.k     // Catch: java.lang.Throwable -> L47
            if (r3 != 0) goto L28
            java.lang.Class r1 = r1.getClass()     // Catch: java.lang.Throwable -> L47
            java.lang.String r1 = r1.getName()     // Catch: java.lang.Throwable -> L47
            java.lang.String r3 = "android.graphics.drawable.VectorDrawable"
            boolean r1 = r3.equals(r1)     // Catch: java.lang.Throwable -> L47
            if (r1 == 0) goto L27
            goto L28
        L27:
            r0 = 0
        L28:
            if (r0 == 0) goto L49
        L2a:
            android.graphics.drawable.Drawable r0 = r4.e(r5, r6)     // Catch: java.lang.Throwable -> L47
            if (r0 != 0) goto L34
            android.graphics.drawable.Drawable r0 = r4.b(r5, r6)     // Catch: java.lang.Throwable -> L47
        L34:
            if (r0 != 0) goto L3a
            android.graphics.drawable.Drawable r0 = a.b.d.b.a.b(r5, r6)     // Catch: java.lang.Throwable -> L47
        L3a:
            if (r0 == 0) goto L40
            android.graphics.drawable.Drawable r0 = r4.a(r5, r6, r7, r0)     // Catch: java.lang.Throwable -> L47
        L40:
            if (r0 == 0) goto L45
            a.b.e.f.A.b(r0)     // Catch: java.lang.Throwable -> L47
        L45:
            monitor-exit(r4)
            return r0
        L47:
            r5 = move-exception
            goto L53
        L49:
            r4.o = r2     // Catch: java.lang.Throwable -> L47
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L47
            java.lang.String r6 = "This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat."
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L47
            throw r5     // Catch: java.lang.Throwable -> L47
        L53:
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: a.b.e.f.C0019j.a(android.content.Context, int, boolean):android.graphics.drawable.Drawable");
    }

    public final Drawable a(Context context, int i2, boolean z, Drawable drawable) {
        LayerDrawable layerDrawable;
        Drawable drawableFindDrawableByLayerId;
        int i3;
        ColorStateList colorStateListD = d(context, i2);
        if (colorStateListD != null) {
            if (A.a(drawable)) {
                drawable = drawable.mutate();
            }
            Drawable drawableB = a.b.b.a.e.b(drawable);
            a.b.b.a.e.a(drawableB, colorStateListD);
            PorterDuff.Mode mode = i2 == a.b.e.b.e.abc_switch_thumb_material ? PorterDuff.Mode.MULTIPLY : null;
            if (mode == null) {
                return drawableB;
            }
            a.b.b.a.e.a(drawableB, mode);
            return drawableB;
        }
        if (i2 == a.b.e.b.e.abc_seekbar_track_material) {
            layerDrawable = (LayerDrawable) drawable;
            a(layerDrawable.findDrawableByLayerId(R.id.background), ca.b(context, a.b.e.b.a.colorControlNormal), f343a);
            drawableFindDrawableByLayerId = layerDrawable.findDrawableByLayerId(R.id.secondaryProgress);
            i3 = a.b.e.b.a.colorControlNormal;
        } else {
            if (i2 != a.b.e.b.e.abc_ratingbar_material && i2 != a.b.e.b.e.abc_ratingbar_indicator_material && i2 != a.b.e.b.e.abc_ratingbar_small_material) {
                if (a(context, i2, drawable) || !z) {
                    return drawable;
                }
                return null;
            }
            layerDrawable = (LayerDrawable) drawable;
            a(layerDrawable.findDrawableByLayerId(R.id.background), ca.a(context, a.b.e.b.a.colorControlNormal), f343a);
            drawableFindDrawableByLayerId = layerDrawable.findDrawableByLayerId(R.id.secondaryProgress);
            i3 = a.b.e.b.a.colorControlActivated;
        }
        a(drawableFindDrawableByLayerId, ca.b(context, i3), f343a);
        a(layerDrawable.findDrawableByLayerId(R.id.progress), ca.b(context, a.b.e.b.a.colorControlActivated), f343a);
        return drawable;
    }

    public static void a(Drawable drawable, fa faVar, int[] iArr) {
        if (A.a(drawable) && drawable.mutate() != drawable) {
            Log.d("AppCompatDrawableManag", "Mutated drawable is not the same instance as the input.");
            return;
        }
        if (faVar.d || faVar.f331c) {
            PorterDuffColorFilter porterDuffColorFilterA = null;
            ColorStateList colorStateList = faVar.d ? faVar.f329a : null;
            PorterDuff.Mode mode = faVar.f331c ? faVar.f330b : f343a;
            if (colorStateList != null && mode != null) {
                porterDuffColorFilterA = a(colorStateList.getColorForState(iArr, 0), mode);
            }
            drawable.setColorFilter(porterDuffColorFilterA);
        } else {
            drawable.clearColorFilter();
        }
        if (Build.VERSION.SDK_INT <= 23) {
            drawable.invalidateSelf();
        }
    }
}
