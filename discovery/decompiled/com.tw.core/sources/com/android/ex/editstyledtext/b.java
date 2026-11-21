package com.android.ex.editstyledtext;

import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

/* JADX INFO: loaded from: classes.dex */
public class b extends DynamicDrawableSpan {
    h kH;

    @Override // android.text.style.DynamicDrawableSpan
    public Drawable getDrawable() {
        return this.kH;
    }

    public void px(int i) {
        this.kH.qh(i);
    }
}
