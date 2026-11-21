package com.dofun.carsetting.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.view.InputDeviceCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class CalibrationView extends View {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    int f422a;
    int b;
    private boolean c;
    private boolean d;
    private int e;

    public CalibrationView(Context context) {
        super(context);
        this.c = false;
        this.f422a = 0;
        this.b = 0;
        this.d = false;
        this.e = 0;
    }

    public CalibrationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = false;
        this.f422a = 0;
        this.b = 0;
        this.d = false;
        this.e = 0;
        if (a(context)) {
            this.f422a = c(context);
            this.b = b(context);
            switch (SystemProperties.getInt("persist.sf.forcerotation", -1)) {
                case 0:
                case 1:
                case 3:
                case 4:
                    this.d = true;
                    break;
            }
        }
    }

    public void a() {
        this.e = 0;
        invalidate();
    }

    public void b() {
        int i = this.e + 1;
        this.e = i;
        if (i > 4) {
            this.e = 0;
        }
        invalidate();
    }

    public int getCali() {
        return this.e;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int width;
        int height;
        int width2;
        int height2;
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(InputDeviceCompat.SOURCE_ANY);
        switch (this.e) {
            case 0:
                width = this.d ? 200 : 50;
                height = width;
                break;
            case 1:
                if (this.d) {
                    width = getWidth() - 200;
                    height = 200;
                } else {
                    width = getWidth() - 100;
                    height = 50;
                }
                break;
            case 2:
                if (this.d) {
                    width2 = getWidth() - 200;
                    height2 = getHeight() - ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                } else {
                    width2 = getWidth() - 100;
                    height2 = getHeight() - 100;
                }
                height = height2;
                width = width2;
                break;
            case 3:
                if (this.d) {
                    height = getHeight() - 200;
                    width = 200;
                } else {
                    height = getHeight() - 100;
                    width = 50;
                }
                break;
            case 4:
                if (this.d) {
                    width2 = getWidth() / 2;
                    height2 = getHeight() / 2;
                } else {
                    width2 = (getWidth() / 2) - 25;
                    height2 = (getHeight() / 2) - 25;
                }
                height = height2;
                width = width2;
                break;
            default:
                width = 0;
                height = width;
                break;
        }
        float f = height + 25;
        canvas.drawLine(width, f, width + 50, f, paint);
        float f2 = width + 25;
        canvas.drawLine(f2, height, f2, height + 50, paint);
    }

    public boolean a(Context context) {
        String str = SystemProperties.get("qemu.hw.mainkeys");
        boolean z = Settings.System.getInt(context.getContentResolver(), "show_navigationbar", 0) != 0;
        if ("1".equals(str)) {
            this.c = false;
        } else if ("0".equals(str) && z) {
            this.c = true;
        }
        return this.c;
    }

    public int getWidth_x() {
        return getWidth();
    }

    public int getHeight_x() {
        if (this.c) {
            return getHeight() + this.f422a + this.b;
        }
        return getHeight();
    }

    private int b(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return -1;
    }

    private int c(Context context) {
        int identifier = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return -1;
    }
}
