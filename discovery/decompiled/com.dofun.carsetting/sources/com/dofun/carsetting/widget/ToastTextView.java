package com.dofun.carsetting.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"AppCompatCustomView"})
public class ToastTextView extends TextView {
    public ToastTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(Color.argb(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 60, 60, 60));
    }

    public ToastTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(Color.argb(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 60, 60, 60));
    }

    public ToastTextView(Context context) {
        super(context);
        a(Color.argb(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 60, 60, 60));
    }

    @SuppressLint({"NewApi"})
    private void a(int i) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(i);
        gradientDrawable.setCornerRadius(8.0f);
        setBackground(gradientDrawable);
        setPadding(15, 15, 15, 15);
        setTextColor(-1);
    }
}
