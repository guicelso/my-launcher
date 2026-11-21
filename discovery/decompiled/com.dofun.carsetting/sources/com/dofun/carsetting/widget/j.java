package com.dofun.carsetting.widget;

import android.content.Context;
import android.support.v4.internal.view.SupportMenu;
import android.view.WindowManager;
import android.widget.TextView;
import com.dofun.carsetting.R;

/* JADX INFO: compiled from: WarningView.java */
/* JADX INFO: loaded from: classes.dex */
public class j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private WindowManager f538a;
    private WindowManager.LayoutParams b;
    private TextView c;
    private boolean d = false;
    private boolean e = false;
    private Context f;

    public j(Context context) {
        this.f = context;
        this.c = new TextView(this.f);
        this.c.setText(this.f.getResources().getString(R.string.warning_reset));
        this.c.setTextColor(SupportMenu.CATEGORY_MASK);
        this.c.setTextSize(1, 20.0f);
        this.c.setBackgroundResource(R.drawable.panel_background);
        Context context2 = this.f;
        Context context3 = this.f;
        this.f538a = (WindowManager) context2.getSystemService("window");
        this.b = new WindowManager.LayoutParams(-2, -2, 0, 0, 2002, 56, 1);
        this.b.gravity = 48;
    }

    public j(Context context, String str) {
        this.f = context;
        this.c = new TextView(this.f);
        this.c.setText(str);
        this.c.setTextColor(SupportMenu.CATEGORY_MASK);
        this.c.setTextSize(1, 20.0f);
        this.c.setBackgroundResource(R.drawable.panel_background);
        Context context2 = this.f;
        Context context3 = this.f;
        this.f538a = (WindowManager) context2.getSystemService("window");
        this.b = new WindowManager.LayoutParams(-2, -2, 0, 0, 2002, 56, 1);
        this.b.gravity = 48;
    }

    public void a() {
        if (this.d) {
            return;
        }
        this.d = true;
        this.f538a.addView(this.c, this.b);
    }
}
