package com.dofun.carsetting.widget;

import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class AlignTextView extends TextView {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f418a;

    public AlignTextView(Context context) {
        super(context);
        this.f418a = true;
    }

    public AlignTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f418a = true;
    }

    public AlignTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f418a = true;
    }

    public void setAutoSplitEnabled(boolean z) {
        this.f418a = z;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) == 1073741824 && View.MeasureSpec.getMode(i2) == 1073741824 && getWidth() > 0 && getHeight() > 0 && this.f418a) {
            String strA = a(this);
            if (!TextUtils.isEmpty(strA)) {
                setText(strA);
            }
        }
        super.onMeasure(i, i2);
    }

    private String a(TextView textView) {
        String string = textView.getText().toString();
        TextPaint paint = textView.getPaint();
        float width = (textView.getWidth() - textView.getPaddingLeft()) - textView.getPaddingRight();
        String[] strArrSplit = string.replaceAll("\r", "").split("\n");
        StringBuilder sb = new StringBuilder();
        for (String str : strArrSplit) {
            if (paint.measureText(str) <= width) {
                sb.append(str);
            } else {
                int i = 0;
                float fMeasureText = 0.0f;
                while (i != str.length()) {
                    char cCharAt = str.charAt(i);
                    fMeasureText += paint.measureText(String.valueOf(cCharAt));
                    if (fMeasureText <= width) {
                        sb.append(cCharAt);
                    } else {
                        sb.append("\n");
                        i--;
                        fMeasureText = 0.0f;
                    }
                    i++;
                }
            }
            sb.append("\n");
        }
        if (!string.endsWith("\n")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
