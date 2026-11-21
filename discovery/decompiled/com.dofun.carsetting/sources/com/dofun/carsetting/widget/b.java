package com.dofun.carsetting.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.dofun.carsetting.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: GestureControlDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class b extends Dialog implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private ToggleButton f486a;
    private ImageView b;
    private ImageView c;
    private ImageView d;
    private LinearLayout e;
    private TextView f;
    private Button g;
    private List<a> h;
    private int i;
    private Context j;

    public b(Context context) {
        super(context, R.style.custom_dialog);
        this.h = new ArrayList();
        this.i = 0;
        this.j = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a();
        b();
        c();
        b(this.i);
    }

    public void a() {
        setContentView(LayoutInflater.from(this.j).inflate(R.layout.layout_gestrue_tip_dialog, (ViewGroup) null));
        this.f486a = (ToggleButton) findViewById(R.id.tg_enable_gesture);
        this.b = (ImageView) findViewById(R.id.iv_prev_tip);
        this.c = (ImageView) findViewById(R.id.iv_tip_icon);
        this.d = (ImageView) findViewById(R.id.iv_next_tip);
        this.e = (LinearLayout) findViewById(R.id.ll_tip_indicate);
        this.f = (TextView) findViewById(R.id.tv_tip);
        this.g = (Button) findViewById(R.id.btn_close);
        this.f486a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.dofun.carsetting.widget.b.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (compoundButton.isPressed() || compoundButton.isFocused()) {
                    SystemProperties.set("persist.sys.tw.multPointMotion", z ? "true" : "false");
                }
            }
        });
        this.b.setOnClickListener(this);
        this.d.setOnClickListener(this);
        this.g.setOnClickListener(this);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = (int) this.j.getResources().getDimension(R.dimen.dialog_gesture_tip_width);
            attributes.height = (int) this.j.getResources().getDimension(R.dimen.dialog_gesture_tip_height);
            window.setAttributes(attributes);
        }
        setCanceledOnTouchOutside(true);
    }

    private void b() {
        this.h.add(new a(R.drawable.ic_gesture_0, R.string.multi_touch_action_tip_1));
        this.h.add(new a(R.drawable.ic_gesture_1, R.string.multi_touch_action_tip_2));
        this.h.add(new a(R.drawable.ic_gesture_2, R.string.multi_touch_action_tip_3));
        this.h.add(new a(R.drawable.ic_gesture_3, R.string.multi_touch_action_tip_4));
        this.h.add(new a(R.drawable.ic_gesture_4, R.string.multi_touch_action_tip_5));
        this.f486a.setChecked(SystemProperties.getBoolean("persist.sys.tw.multPointMotion", false));
    }

    private void c() {
        for (int i = 0; i < this.h.size(); i++) {
            View view = new View(this.j);
            view.setBackgroundResource(R.drawable.shape_gesture_tip_indicate);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(a(8), a(8));
            layoutParams.leftMargin = a(8);
            layoutParams.rightMargin = a(8);
            this.e.addView(view, layoutParams);
        }
        this.e.getChildAt(0).setSelected(true);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.btn_close) {
            switch (id) {
                case R.id.iv_next_tip /* 2131230958 */:
                    this.i++;
                    if (this.i > this.h.size() - 1) {
                        this.i = 0;
                    }
                    b(this.i);
                    break;
                case R.id.iv_prev_tip /* 2131230959 */:
                    this.i--;
                    if (this.i < 0) {
                        this.i = this.h.size() - 1;
                    }
                    b(this.i);
                    break;
            }
        }
        dismiss();
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
    }

    @Override // android.app.Dialog
    public void hide() {
        super.hide();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
    }

    /* JADX INFO: compiled from: GestureControlDialog.java */
    private class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f488a;
        int b;

        public a(int i, int i2) {
            this.f488a = i;
            this.b = i2;
        }
    }

    private void b(int i) {
        a aVar;
        if (i < 0 || i > this.h.size() - 1 || (aVar = this.h.get(i)) == null) {
            return;
        }
        this.c.setImageResource(aVar.f488a);
        this.f.setText(aVar.b);
        for (int i2 = 0; i2 < this.e.getChildCount(); i2++) {
            if (i2 == i) {
                this.e.getChildAt(i2).setSelected(true);
            } else {
                this.e.getChildAt(i2).setSelected(false);
            }
        }
    }

    protected int a(int i) {
        return (int) TypedValue.applyDimension(1, i, this.j.getResources().getDisplayMetrics());
    }
}
