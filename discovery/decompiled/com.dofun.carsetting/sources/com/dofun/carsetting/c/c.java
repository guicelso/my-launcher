package com.dofun.carsetting.c;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dofun.carsetting.R;

/* JADX INFO: compiled from: EditTextCompositeDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class c extends Dialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f143a;
    private String b;
    private int c;
    private boolean d;
    private com.dofun.carsetting.g.f e;
    private TextView f;
    private EditText g;
    private LinearLayout h;
    private LinearLayout i;
    private LinearLayout j;
    private LinearLayout k;
    private Button l;
    private Button m;
    private Button n;
    private Button o;
    private Button p;
    private Button q;
    private Button r;
    private Button s;
    private Button t;

    public c(Context context) {
        super(context, R.style.SeekBarDialogStyle);
        this.b = "";
        this.c = 0;
        this.d = false;
        this.f143a = context;
    }

    public c a(String str) {
        this.b = str;
        return this;
    }

    public c a(int i, boolean z) {
        this.c = i;
        this.d = z;
        return this;
    }

    public c a(com.dofun.carsetting.g.f fVar) {
        this.e = fVar;
        return this;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a();
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.f143a).inflate(R.layout.dialog_composite_edittext, (ViewGroup) null);
        setContentView(viewInflate);
        a(viewInflate);
        c();
        d();
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) this.f143a.getResources().getDimension(R.dimen.dialog_edit_width);
        attributes.height = (int) this.f143a.getResources().getDimension(R.dimen.dialog_edit_height);
        Log.d("EditTextCompositeDialog", "lp.width:" + attributes.width);
        window.setAttributes(attributes);
    }

    private void a(View view) {
        this.f = (TextView) view.findViewById(R.id.tv_dialog_title);
        this.g = (EditText) view.findViewById(R.id.edittext);
        this.h = (LinearLayout) view.findViewById(R.id.ll_bottom);
        this.l = (Button) view.findViewById(R.id.cancel);
        this.m = (Button) view.findViewById(R.id.change);
        this.n = (Button) view.findViewById(R.id.confirm);
        this.i = (LinearLayout) view.findViewById(R.id.ll_old_bottom);
        this.o = (Button) view.findViewById(R.id.old_cancel);
        this.p = (Button) view.findViewById(R.id.old_confirm);
        this.j = (LinearLayout) view.findViewById(R.id.ll_new_bottom);
        this.q = (Button) view.findViewById(R.id.new_cancel);
        this.r = (Button) view.findViewById(R.id.new_confirm);
        this.k = (LinearLayout) view.findViewById(R.id.ll_bottom2);
        this.s = (Button) view.findViewById(R.id.cancel2);
        this.t = (Button) view.findViewById(R.id.confirm2);
    }

    private void c() {
        this.l.setOnClickListener(new a());
        this.m.setOnClickListener(new a());
        this.n.setOnClickListener(new a());
        this.o.setOnClickListener(new a());
        this.p.setOnClickListener(new a());
        this.q.setOnClickListener(new a());
        this.r.setOnClickListener(new a());
        this.s.setOnClickListener(new a());
        this.t.setOnClickListener(new a());
    }

    private void d() {
        if (this.d) {
            this.g.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            this.g.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        this.g.setInputType(this.c);
        this.g.setHintTextColor(this.f143a.getResources().getColor(R.color.edit_text_hint_color));
        this.f.setText(this.b);
        if (com.dofun.carsetting.f.b.aA()) {
            a(0);
        } else {
            a(3);
        }
    }

    /* JADX INFO: compiled from: EditTextCompositeDialog.java */
    private class a implements View.OnClickListener {
        private a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cancel /* 2131230792 */:
                case R.id.cancel2 /* 2131230793 */:
                    c.this.cancel();
                    break;
                case R.id.change /* 2131230800 */:
                    c.this.a(1);
                    break;
                case R.id.confirm /* 2131230833 */:
                case R.id.confirm2 /* 2131230834 */:
                    c.this.e.a(c.this.g.getText());
                    break;
                case R.id.new_cancel /* 2131231078 */:
                    c.this.a(0);
                    break;
                case R.id.new_confirm /* 2131231079 */:
                    c.this.e.c(c.this.g.getText());
                    break;
                case R.id.old_cancel /* 2131231087 */:
                    c.this.a(0);
                    break;
                case R.id.old_confirm /* 2131231088 */:
                    c.this.e.b(c.this.g.getText());
                    break;
            }
        }
    }

    public void b() {
        this.g.setText("");
    }

    public void a(int i) {
        switch (i) {
            case 1:
                this.h.setVisibility(8);
                this.i.setVisibility(0);
                this.j.setVisibility(8);
                this.k.setVisibility(8);
                this.g.setHint(this.f143a.getString(R.string.enter_your_old_pin));
                break;
            case 2:
                this.h.setVisibility(8);
                this.i.setVisibility(8);
                this.j.setVisibility(0);
                this.k.setVisibility(0);
                this.g.setHint(this.f143a.getString(R.string.enter_your_new_pin));
                break;
            case 3:
                this.h.setVisibility(8);
                this.i.setVisibility(8);
                this.j.setVisibility(8);
                this.k.setVisibility(0);
                this.g.setHint(this.f143a.getString(R.string.enter_your_pin));
                break;
            default:
                this.h.setVisibility(0);
                this.i.setVisibility(8);
                this.j.setVisibility(8);
                this.k.setVisibility(8);
                this.g.setHint(this.f143a.getString(R.string.enter_your_pin));
                break;
        }
        b();
    }
}
