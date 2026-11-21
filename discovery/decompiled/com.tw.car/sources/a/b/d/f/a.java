package a.b.d.f;

import a.b.b.a.e;
import android.os.Build;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;

/* JADX INFO: loaded from: classes.dex */
public class a implements Spannable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Spannable f116a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final C0002a f117b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final PrecomputedText f118c;

    /* JADX INFO: renamed from: a.b.d.f.a$a, reason: collision with other inner class name */
    public static final class C0002a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final TextPaint f119a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final TextDirectionHeuristic f120b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final int f121c;
        public final int d;
        public final PrecomputedText.Params e;

        public C0002a(PrecomputedText.Params params) {
            this.f119a = params.getTextPaint();
            this.f120b = params.getTextDirection();
            this.f121c = params.getBreakStrategy();
            this.d = params.getHyphenationFrequency();
            this.e = params;
        }

        public C0002a(TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, int i, int i2) {
            this.e = Build.VERSION.SDK_INT >= 28 ? new PrecomputedText.Params.Builder(textPaint).setBreakStrategy(i).setHyphenationFrequency(i2).setTextDirection(textDirectionHeuristic).build() : null;
            this.f119a = textPaint;
            this.f120b = textDirectionHeuristic;
            this.f121c = i;
            this.d = i2;
        }

        public int a() {
            return this.f121c;
        }

        public int b() {
            return this.d;
        }

        public TextDirectionHeuristic c() {
            return this.f120b;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof C0002a)) {
                return false;
            }
            C0002a c0002a = (C0002a) obj;
            PrecomputedText.Params params = this.e;
            if (params != null) {
                return params.equals(c0002a.e);
            }
            if (Build.VERSION.SDK_INT >= 23 && (this.f121c != c0002a.f121c || this.d != c0002a.d)) {
                return false;
            }
            int i = Build.VERSION.SDK_INT;
            if (this.f120b != c0002a.f120b || this.f119a.getTextSize() != c0002a.f119a.getTextSize() || this.f119a.getTextScaleX() != c0002a.f119a.getTextScaleX() || this.f119a.getTextSkewX() != c0002a.f119a.getTextSkewX()) {
                return false;
            }
            if ((Build.VERSION.SDK_INT >= 21 && (this.f119a.getLetterSpacing() != c0002a.f119a.getLetterSpacing() || !TextUtils.equals(this.f119a.getFontFeatureSettings(), c0002a.f119a.getFontFeatureSettings()))) || this.f119a.getFlags() != c0002a.f119a.getFlags()) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= 24) {
                if (!this.f119a.getTextLocales().equals(c0002a.f119a.getTextLocales())) {
                    return false;
                }
            } else if (!this.f119a.getTextLocale().equals(c0002a.f119a.getTextLocale())) {
                return false;
            }
            if (this.f119a.getTypeface() == null) {
                if (c0002a.f119a.getTypeface() != null) {
                    return false;
                }
            } else if (!this.f119a.getTypeface().equals(c0002a.f119a.getTypeface())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            Object[] objArr;
            int i = Build.VERSION.SDK_INT;
            if (i >= 24) {
                objArr = new Object[]{Float.valueOf(this.f119a.getTextSize()), Float.valueOf(this.f119a.getTextScaleX()), Float.valueOf(this.f119a.getTextSkewX()), Float.valueOf(this.f119a.getLetterSpacing()), Integer.valueOf(this.f119a.getFlags()), this.f119a.getTextLocales(), this.f119a.getTypeface(), Boolean.valueOf(this.f119a.isElegantTextHeight()), this.f120b, Integer.valueOf(this.f121c), Integer.valueOf(this.d)};
            } else {
                objArr = i >= 21 ? new Object[]{Float.valueOf(this.f119a.getTextSize()), Float.valueOf(this.f119a.getTextScaleX()), Float.valueOf(this.f119a.getTextSkewX()), Float.valueOf(this.f119a.getLetterSpacing()), Integer.valueOf(this.f119a.getFlags()), this.f119a.getTextLocale(), this.f119a.getTypeface(), Boolean.valueOf(this.f119a.isElegantTextHeight()), this.f120b, Integer.valueOf(this.f121c), Integer.valueOf(this.d)} : new Object[]{Float.valueOf(this.f119a.getTextSize()), Float.valueOf(this.f119a.getTextScaleX()), Float.valueOf(this.f119a.getTextSkewX()), Integer.valueOf(this.f119a.getFlags()), this.f119a.getTextLocale(), this.f119a.getTypeface(), this.f120b, Integer.valueOf(this.f121c), Integer.valueOf(this.d)};
            }
            return e.a(objArr);
        }

        public String toString() {
            StringBuilder sbA;
            Object textLocale;
            StringBuilder sb = new StringBuilder("{");
            StringBuilder sbA2 = c.a.a.a.a.a("textSize=");
            sbA2.append(this.f119a.getTextSize());
            sb.append(sbA2.toString());
            sb.append(", textScaleX=" + this.f119a.getTextScaleX());
            sb.append(", textSkewX=" + this.f119a.getTextSkewX());
            if (Build.VERSION.SDK_INT >= 21) {
                StringBuilder sbA3 = c.a.a.a.a.a(", letterSpacing=");
                sbA3.append(this.f119a.getLetterSpacing());
                sb.append(sbA3.toString());
                sb.append(", elegantTextHeight=" + this.f119a.isElegantTextHeight());
            }
            if (Build.VERSION.SDK_INT >= 24) {
                sbA = c.a.a.a.a.a(", textLocale=");
                textLocale = this.f119a.getTextLocales();
            } else {
                sbA = c.a.a.a.a.a(", textLocale=");
                textLocale = this.f119a.getTextLocale();
            }
            sbA.append(textLocale);
            sb.append(sbA.toString());
            StringBuilder sbA4 = c.a.a.a.a.a(", typeface=");
            sbA4.append(this.f119a.getTypeface());
            sb.append(sbA4.toString());
            if (Build.VERSION.SDK_INT >= 26) {
                StringBuilder sbA5 = c.a.a.a.a.a(", variationSettings=");
                sbA5.append(this.f119a.getFontVariationSettings());
                sb.append(sbA5.toString());
            }
            StringBuilder sbA6 = c.a.a.a.a.a(", textDir=");
            sbA6.append(this.f120b);
            sb.append(sbA6.toString());
            sb.append(", breakStrategy=" + this.f121c);
            sb.append(", hyphenationFrequency=" + this.d);
            sb.append("}");
            return sb.toString();
        }
    }

    static {
        new Object();
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return this.f116a.charAt(i);
    }

    @Override // android.text.Spanned
    public int getSpanEnd(Object obj) {
        return this.f116a.getSpanEnd(obj);
    }

    @Override // android.text.Spanned
    public int getSpanFlags(Object obj) {
        return this.f116a.getSpanFlags(obj);
    }

    @Override // android.text.Spanned
    public int getSpanStart(Object obj) {
        return this.f116a.getSpanStart(obj);
    }

    @Override // android.text.Spanned
    public <T> T[] getSpans(int i, int i2, Class<T> cls) {
        return Build.VERSION.SDK_INT >= 28 ? (T[]) this.f118c.getSpans(i, i2, cls) : (T[]) this.f116a.getSpans(i, i2, cls);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.f116a.length();
    }

    @Override // android.text.Spanned
    public int nextSpanTransition(int i, int i2, Class cls) {
        return this.f116a.nextSpanTransition(i, i2, cls);
    }

    @Override // android.text.Spannable
    public void removeSpan(Object obj) {
        if (obj instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be removed from PrecomputedText.");
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.f118c.removeSpan(obj);
        } else {
            this.f116a.removeSpan(obj);
        }
    }

    @Override // android.text.Spannable
    public void setSpan(Object obj, int i, int i2, int i3) {
        if (obj instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be set to PrecomputedText.");
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.f118c.setSpan(obj, i, i2, i3);
        } else {
            this.f116a.setSpan(obj, i, i2, i3);
        }
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return this.f116a.subSequence(i, i2);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return this.f116a.toString();
    }
}
