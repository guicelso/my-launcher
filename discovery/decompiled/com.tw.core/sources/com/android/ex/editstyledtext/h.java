package com.android.ex.editstyledtext;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class h extends ShapeDrawable {
    private static boolean lc = false;
    private Spannable ld;
    private int le;

    private b qg() {
        Spannable spannable = this.ld;
        b[] bVarArr = (b[]) spannable.getSpans(0, spannable.length(), b.class);
        if (bVarArr.length > 0) {
            for (b bVar : bVarArr) {
                if (bVar.getDrawable() == this) {
                    return bVar;
                }
            }
        }
        Log.e("EditStyledTextSpan", "---renewBounds: Couldn't find");
        return null;
    }

    private void qi() {
        b bVarQg = qg();
        Spannable spannable = this.ld;
        ForegroundColorSpan[] foregroundColorSpanArr = (ForegroundColorSpan[]) spannable.getSpans(spannable.getSpanStart(bVarQg), spannable.getSpanEnd(bVarQg), ForegroundColorSpan.class);
        if (lc) {
            Log.d("EditStyledTextSpan", "--- renewColor:" + foregroundColorSpanArr.length);
        }
        if (foregroundColorSpanArr.length > 0) {
            qj(foregroundColorSpanArr[foregroundColorSpanArr.length - 1].getForegroundColor());
        }
    }

    private void qj(int i) {
        if (lc) {
            Log.d("EditStyledTextSpan", "--- renewColor:" + i);
        }
        getPaint().setColor(i);
    }

    @Override // android.graphics.drawable.ShapeDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        qi();
        canvas.drawRect(new Rect(0, 9, this.le, 11), getPaint());
    }

    public void qh(int i) {
        if (lc) {
            Log.d("EditStyledTextSpan", "--- renewBounds:" + i);
        }
        if (i > 20) {
            i -= 20;
        }
        this.le = i;
        setBounds(0, 0, i, 20);
    }
}
