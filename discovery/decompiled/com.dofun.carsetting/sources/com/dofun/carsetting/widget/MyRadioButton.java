package com.dofun.carsetting.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;
import com.dofun.carsetting.R;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"AppCompatCustomView"})
public class MyRadioButton extends RadioButton {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private float f469a;
    private float b;

    public MyRadioButton(Context context) {
        this(context, null);
    }

    public MyRadioButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MyRadioButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f469a = -2.0f;
        this.b = -2.0f;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MyRadioButton, i, 0);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            switch (index) {
                case 0:
                    this.b = typedArrayObtainStyledAttributes.getLayoutDimension(index, -2);
                    break;
                case 1:
                    this.f469a = typedArrayObtainStyledAttributes.getLayoutDimension(index, -2);
                    break;
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        Drawable drawable = getCompoundDrawables()[0];
        Drawable drawable2 = getCompoundDrawables()[1];
        Drawable drawable3 = getCompoundDrawables()[2];
        Drawable drawable4 = getCompoundDrawables()[3];
        setBounds(drawable);
        setBounds(drawable2);
        setBounds(drawable3);
        setBounds(drawable4);
        setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
    }

    private void setBounds(Drawable drawable) {
        if (drawable == null || this.b == -2.0f || this.f469a == -2.0f) {
            return;
        }
        drawable.setBounds(0, 0, (int) this.f469a, (int) this.b);
    }
}
