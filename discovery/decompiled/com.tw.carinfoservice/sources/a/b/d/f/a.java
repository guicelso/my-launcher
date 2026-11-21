package a.b.d.f;

import android.os.Build;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class a implements Spannable {

    /* JADX INFO: renamed from: a.b.d.f.a$a, reason: collision with other inner class name */
    public static final class C0004a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final TextPaint f111a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final TextDirectionHeuristic f112b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final int f113c;
        public final int d;
        public final PrecomputedText.Params e;

        public C0004a(PrecomputedText.Params params) {
            this.f111a = params.getTextPaint();
            this.f112b = params.getTextDirection();
            this.f113c = params.getBreakStrategy();
            this.d = params.getHyphenationFrequency();
            this.e = params;
        }

        public C0004a(TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, int i, int i2) {
            this.e = Build.VERSION.SDK_INT >= 28 ? new PrecomputedText.Params.Builder(textPaint).setBreakStrategy(i).setHyphenationFrequency(i2).setTextDirection(textDirectionHeuristic).build() : null;
            this.f111a = textPaint;
            this.f112b = textDirectionHeuristic;
            this.f113c = i;
            this.d = i2;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof C0004a)) {
                return false;
            }
            C0004a c0004a = (C0004a) obj;
            PrecomputedText.Params params = this.e;
            if (params != null) {
                return params.equals(c0004a.e);
            }
            int i = Build.VERSION.SDK_INT;
            if ((i >= 23 && (this.f113c != c0004a.f113c || this.d != c0004a.d)) || this.f112b != c0004a.f112b || this.f111a.getTextSize() != c0004a.f111a.getTextSize() || this.f111a.getTextScaleX() != c0004a.f111a.getTextScaleX() || this.f111a.getTextSkewX() != c0004a.f111a.getTextSkewX()) {
                return false;
            }
            if ((i >= 21 && (this.f111a.getLetterSpacing() != c0004a.f111a.getLetterSpacing() || !TextUtils.equals(this.f111a.getFontFeatureSettings(), c0004a.f111a.getFontFeatureSettings()))) || this.f111a.getFlags() != c0004a.f111a.getFlags()) {
                return false;
            }
            if (i >= 24) {
                if (!this.f111a.getTextLocales().equals(c0004a.f111a.getTextLocales())) {
                    return false;
                }
            } else if (!this.f111a.getTextLocale().equals(c0004a.f111a.getTextLocale())) {
                return false;
            }
            if (this.f111a.getTypeface() == null) {
                if (c0004a.f111a.getTypeface() != null) {
                    return false;
                }
            } else if (!this.f111a.getTypeface().equals(c0004a.f111a.getTypeface())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = Build.VERSION.SDK_INT;
            if (i >= 24) {
                return Objects.hash(Float.valueOf(this.f111a.getTextSize()), Float.valueOf(this.f111a.getTextScaleX()), Float.valueOf(this.f111a.getTextSkewX()), Float.valueOf(this.f111a.getLetterSpacing()), Integer.valueOf(this.f111a.getFlags()), this.f111a.getTextLocales(), this.f111a.getTypeface(), Boolean.valueOf(this.f111a.isElegantTextHeight()), this.f112b, Integer.valueOf(this.f113c), Integer.valueOf(this.d));
            }
            return i >= 21 ? Objects.hash(Float.valueOf(this.f111a.getTextSize()), Float.valueOf(this.f111a.getTextScaleX()), Float.valueOf(this.f111a.getTextSkewX()), Float.valueOf(this.f111a.getLetterSpacing()), Integer.valueOf(this.f111a.getFlags()), this.f111a.getTextLocale(), this.f111a.getTypeface(), Boolean.valueOf(this.f111a.isElegantTextHeight()), this.f112b, Integer.valueOf(this.f113c), Integer.valueOf(this.d)) : Objects.hash(Float.valueOf(this.f111a.getTextSize()), Float.valueOf(this.f111a.getTextScaleX()), Float.valueOf(this.f111a.getTextSkewX()), Integer.valueOf(this.f111a.getFlags()), this.f111a.getTextLocale(), this.f111a.getTypeface(), this.f112b, Integer.valueOf(this.f113c), Integer.valueOf(this.d));
        }

        public String toString() {
            StringBuilder sbB;
            Object textLocale;
            StringBuilder sb = new StringBuilder("{");
            StringBuilder sbB2 = c.a.a.a.a.b("textSize=");
            sbB2.append(this.f111a.getTextSize());
            sb.append(sbB2.toString());
            sb.append(", textScaleX=" + this.f111a.getTextScaleX());
            sb.append(", textSkewX=" + this.f111a.getTextSkewX());
            int i = Build.VERSION.SDK_INT;
            if (i >= 21) {
                StringBuilder sbB3 = c.a.a.a.a.b(", letterSpacing=");
                sbB3.append(this.f111a.getLetterSpacing());
                sb.append(sbB3.toString());
                sb.append(", elegantTextHeight=" + this.f111a.isElegantTextHeight());
            }
            if (i >= 24) {
                sbB = c.a.a.a.a.b(", textLocale=");
                textLocale = this.f111a.getTextLocales();
            } else {
                sbB = c.a.a.a.a.b(", textLocale=");
                textLocale = this.f111a.getTextLocale();
            }
            sbB.append(textLocale);
            sb.append(sbB.toString());
            StringBuilder sbB4 = c.a.a.a.a.b(", typeface=");
            sbB4.append(this.f111a.getTypeface());
            sb.append(sbB4.toString());
            if (i >= 26) {
                StringBuilder sbB5 = c.a.a.a.a.b(", variationSettings=");
                sbB5.append(this.f111a.getFontVariationSettings());
                sb.append(sbB5.toString());
            }
            StringBuilder sbB6 = c.a.a.a.a.b(", textDir=");
            sbB6.append(this.f112b);
            sb.append(sbB6.toString());
            sb.append(", breakStrategy=" + this.f113c);
            sb.append(", hyphenationFrequency=" + this.d);
            sb.append("}");
            return sb.toString();
        }
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        throw null;
    }

    @Override // android.text.Spanned
    public int getSpanEnd(Object obj) {
        throw null;
    }

    @Override // android.text.Spanned
    public int getSpanFlags(Object obj) {
        throw null;
    }

    @Override // android.text.Spanned
    public int getSpanStart(Object obj) {
        throw null;
    }

    @Override // android.text.Spanned
    public <T> T[] getSpans(int i, int i2, Class<T> cls) {
        int i3 = Build.VERSION.SDK_INT;
        throw null;
    }

    @Override // java.lang.CharSequence
    public int length() {
        throw null;
    }

    @Override // android.text.Spanned
    public int nextSpanTransition(int i, int i2, Class cls) {
        throw null;
    }

    @Override // android.text.Spannable
    public void removeSpan(Object obj) {
        if (obj instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be removed from PrecomputedText.");
        }
        int i = Build.VERSION.SDK_INT;
        throw null;
    }

    @Override // android.text.Spannable
    public void setSpan(Object obj, int i, int i2, int i3) {
        if (obj instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be set to PrecomputedText.");
        }
        int i4 = Build.VERSION.SDK_INT;
        throw null;
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        throw null;
    }

    @Override // java.lang.CharSequence
    public String toString() {
        throw null;
    }
}
