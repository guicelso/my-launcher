package com.android.ex.editstyledtext;

import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

/* JADX INFO: loaded from: classes.dex */
class a {
    private int kA;
    private EditStyledText$SoftKeyReceiver kB;
    private boolean kC;
    private int kD;
    private boolean kE;
    private boolean kF;
    final /* synthetic */ d kG;
    private j kp;
    private int kq;
    private int kr;
    private BackgroundColorSpan ks;
    private SpannableStringBuilder kt;
    private int ku;
    private int kv;
    private d kw;
    private boolean kx;
    private boolean ky;
    private int kz;

    private void oU() {
        Log.d("EditStyledText.EditorManager", "--- handleCancel");
        this.kz = 0;
        this.kD = 0;
        this.kx = false;
        this.kr = 16777215;
        this.kA = 0;
        this.kF = false;
        this.kC = false;
        this.ky = false;
        this.kE = false;
        pt();
        this.kw.setOnClickListener(null);
        ps();
    }

    private int oV(Editable editable, int i) {
        int i2 = i;
        while (i2 < editable.length()) {
            if (editable.charAt(i2) == '\n') {
                i2++;
                break;
            }
            i2++;
        }
        Log.d("EditStyledText.EditorManager", "--- findLineEnd:" + i + "," + editable.length() + "," + i2);
        return i2;
    }

    private int oW(Editable editable, int i) {
        int i2 = i;
        while (i2 > 0 && editable.charAt(i2 - 1) != '\n') {
            i2--;
        }
        Log.d("EditStyledText.EditorManager", "--- findLineStart:" + i + "," + editable.length() + "," + i2);
        return i2;
    }

    private void oX() {
        Log.d("EditStyledText.EditorManager", "--- handleComplete:" + this.kv + "," + this.ku);
        if (this.kx) {
            if (this.kv == this.ku) {
                Log.d("EditStyledText.EditorManager", "--- cancel handle complete:" + this.kv);
                pp();
            } else {
                if (this.kD == 2) {
                    this.kD = 3;
                }
                this.kp.qk(this.kz);
                d.pV(this.kw, this.kw.getText());
            }
        }
    }

    private void pa() {
        if (this.kx) {
            this.kp.qm(11);
        }
    }

    private SpannableStringBuilder po(SpannableStringBuilder spannableStringBuilder) {
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(spannableStringBuilder);
        for (DynamicDrawableSpan dynamicDrawableSpan : (DynamicDrawableSpan[]) spannableStringBuilder2.getSpans(0, spannableStringBuilder2.length(), DynamicDrawableSpan.class)) {
            if ((dynamicDrawableSpan instanceof b) || (dynamicDrawableSpan instanceof f)) {
                spannableStringBuilder2.replace(spannableStringBuilder2.getSpanStart(dynamicDrawableSpan), spannableStringBuilder2.getSpanEnd(dynamicDrawableSpan), (CharSequence) "");
            }
        }
        return spannableStringBuilder2;
    }

    private void pp() {
        oU();
        this.kx = true;
        this.kw.pI(this.kz, this.kD);
    }

    private void pt() {
        Log.d("EditStyledText.EditorManager", "--- offSelect");
        d.pV(this.kw, this.kw.getText());
        int selectionStart = this.kw.getSelectionStart();
        this.kw.setSelection(selectionStart, selectionStart);
        this.kD = 0;
    }

    public void oS() {
        Log.d("EditStyledText.EditorManager", "--- blockSoftKey:");
        pb();
        this.kC = true;
    }

    public boolean oT() {
        return this.kt != null && this.kt.length() > 0 && po(this.kt).length() == 0;
    }

    public int oY() {
        return this.kq;
    }

    public int oZ() {
        return this.kD;
    }

    public void pb() {
        Log.d("EditStyledText.EditorManager", "--- hidesoftkey");
        if (this.kw.isFocused()) {
            this.kB.kT = Selection.getSelectionStart(this.kw.getText());
            this.kB.kS = Selection.getSelectionEnd(this.kw.getText());
            ((InputMethodManager) this.kw.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.kw.getWindowToken(), 0, this.kB);
        }
    }

    public boolean pc() {
        return this.kx;
    }

    public boolean pd() {
        return this.kC;
    }

    public boolean pe() {
        Editable text = this.kw.getText();
        int length = text.length();
        return ((ParagraphStyle[]) text.getSpans(0, length, ParagraphStyle.class)).length > 0 || ((QuoteSpan[]) text.getSpans(0, length, QuoteSpan.class)).length > 0 || ((CharacterStyle[]) text.getSpans(0, length, CharacterStyle.class)).length > 0 || this.kq != 16777215;
    }

    public boolean pf() {
        return this.kF;
    }

    public void pg(int i) {
        ph(i, true);
    }

    public void ph(int i, boolean z) {
        this.kp.qm(i);
        if (z) {
            this.kw.pI(this.kz, this.kD);
        }
    }

    public void pi() {
        this.kp.qm(14);
    }

    public void pj() {
        Log.d("EditStyledText.EditorManager", "--- onClickView");
        if (this.kD == 1 || this.kD == 2) {
            this.kp.qo();
            this.kw.pI(this.kz, this.kD);
        }
    }

    public void pk() {
        Log.d("EditStyledText.EditorManager", "--- onFixSelectedItem");
        oX();
        this.kw.pI(this.kz, this.kD);
    }

    public void pl() {
        Log.d("EditStyledText.EditorManager", "--- onRefreshStyles");
        Editable text = this.kw.getText();
        int length = text.length();
        int width = this.kw.getWidth();
        b[] bVarArr = (b[]) text.getSpans(0, length, b.class);
        for (b bVar : bVarArr) {
            bVar.px(width);
        }
        k[] kVarArr = (k[]) text.getSpans(0, length, k.class);
        for (k kVar : kVarArr) {
            kVar.qq(this.kw.pB());
        }
        if (bVarArr.length > 0) {
            text.replace(0, 1, "" + text.charAt(0));
        }
    }

    public void pm(boolean z) {
        Log.d("EditStyledText.EditorManager", "--- onClickSelect");
        this.kz = 5;
        if (this.kD == 0) {
            this.kp.qo();
        } else {
            pt();
            this.kp.qo();
        }
        if (z) {
            this.kw.pI(this.kz, this.kD);
        }
    }

    public void pn(boolean z) {
        Log.d("EditStyledText.EditorManager", "--- onClickSelectAll");
        pa();
        if (z) {
            this.kw.pI(this.kz, this.kD);
        }
    }

    public void pq(int i, int i2) {
        Log.d("EditStyledText", "--- setTextComposingMask:" + i + "," + i2);
        int iMin = Math.min(i, i2);
        int iMax = Math.max(i, i2);
        int iPC = (!pf() || this.kr == 16777215) ? this.kw.pC(iMin) : this.kr;
        int iPB = this.kw.pB();
        Log.d("EditStyledText", "--- fg:" + Integer.toHexString(iPC) + ",bg:" + Integer.toHexString(iPB) + "," + pf() + ",," + this.kz);
        if (iPC == iPB) {
            int i3 = (~((-16777216) | iPB)) | Integer.MIN_VALUE;
            if (this.ks == null || this.ks.getBackgroundColor() != i3) {
                this.ks = new BackgroundColorSpan(i3);
            }
            this.kw.getText().setSpan(this.ks, iMin, iMax, 33);
        }
    }

    public void pr(int i, int i2) {
        Log.d("EditStyledText.EditorManager", "--- showsoftkey");
        if (!this.kw.isFocused() || pd()) {
            return;
        }
        this.kB.kT = Selection.getSelectionStart(this.kw.getText());
        this.kB.kS = Selection.getSelectionEnd(this.kw.getText());
        if (!((InputMethodManager) this.kG.getContext().getSystemService("input_method")).showSoftInput(this.kw, 0, this.kB) || this.kB == null) {
            return;
        }
        Selection.setSelection(this.kG.getText(), i, i2);
    }

    public void ps() {
        Log.d("EditStyledText.EditorManager", "--- unblockSoftKey:");
        this.kC = false;
    }

    public void pu() {
        Log.d("EditStyledText", "--- unsetTextComposingMask");
        if (this.ks != null) {
            this.kw.getText().removeSpan(this.ks);
            this.ks = null;
        }
    }

    public void pv(Editable editable, int i, int i2, int i3) {
        Log.d("EditStyledText.EditorManager", "updateSpanNext:" + i + "," + i2 + "," + i3);
        int i4 = i + i3;
        int iMin = Math.min(i, i4);
        int iMax = Math.max(i, i4);
        Object[] spans = editable.getSpans(iMax, iMax, Object.class);
        for (Object obj : spans) {
            if ((obj instanceof k) || (obj instanceof AlignmentSpan)) {
                int spanStart = editable.getSpanStart(obj);
                int spanEnd = editable.getSpanEnd(obj);
                Log.d("EditStyledText.EditorManager", "spantype:" + obj.getClass() + "," + spanEnd);
                if ((((obj instanceof k) || (obj instanceof AlignmentSpan)) ? oW(this.kw.getText(), iMin) : iMin) < spanStart && i2 > i3) {
                    editable.removeSpan(obj);
                } else if (spanStart > iMin) {
                    editable.setSpan(obj, iMin, spanEnd, 33);
                }
            } else if ((obj instanceof b) && editable.getSpanStart(obj) == i4 && i4 > 0 && this.kw.getText().charAt(i4 - 1) != '\n') {
                this.kw.getText().insert(i4, "\n");
                this.kw.setSelection(i4);
            }
        }
    }

    public void pw(Editable editable, int i, int i2, int i3) {
        Log.d("EditStyledText.EditorManager", "updateSpanPrevious:" + i + "," + i2 + "," + i3);
        int i4 = i + i3;
        int iMin = Math.min(i, i4);
        int iMax = Math.max(i, i4);
        for (Object obj : editable.getSpans(iMin, iMin, Object.class)) {
            if ((obj instanceof ForegroundColorSpan) || (obj instanceof AbsoluteSizeSpan) || (obj instanceof k) || (obj instanceof AlignmentSpan)) {
                int spanStart = editable.getSpanStart(obj);
                int spanEnd = editable.getSpanEnd(obj);
                Log.d("EditStyledText.EditorManager", "spantype:" + obj.getClass() + "," + spanStart);
                int iOV = ((obj instanceof k) || (obj instanceof AlignmentSpan)) ? oV(this.kw.getText(), iMax) : this.ky ? spanEnd : iMax;
                if (spanEnd < iOV) {
                    Log.d("EditStyledText.EditorManager", "updateSpanPrevious: extend span");
                    editable.setSpan(obj, spanStart, iOV, 33);
                }
            } else if (obj instanceof b) {
                int spanStart2 = editable.getSpanStart(obj);
                int spanEnd2 = editable.getSpanEnd(obj);
                if (i2 > i3) {
                    editable.replace(spanStart2, spanEnd2, "");
                    editable.removeSpan(obj);
                } else if (spanEnd2 == i4 && i4 < editable.length() && this.kw.getText().charAt(i4) != '\n') {
                    this.kw.getText().insert(i4, "\n");
                }
            }
        }
    }

    public void setBackgroundColor(int i) {
        this.kq = i;
    }
}
