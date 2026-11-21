package com.dofun.carsetting.c;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.dofun.carsetting.R;

/* JADX INFO: compiled from: PasswordDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class h extends Dialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f161a;
    private String b;
    private int c;
    private boolean d;
    private String e;
    private com.dofun.carsetting.g.g f;
    private EditText g;

    public h(Context context) {
        super(context, R.style.SeekBarDialogStyle);
        this.b = "";
        this.c = 0;
        this.d = false;
        this.f161a = context;
    }

    public h a(String str) {
        this.b = str;
        return this;
    }

    public h a(String str, int i, boolean z, int i2) {
        this.e = str;
        this.c = i;
        this.d = z;
        return this;
    }

    public h a(com.dofun.carsetting.g.g gVar) {
        this.f = gVar;
        return this;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a();
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.f161a).inflate(R.layout.dialog_single_edittext, (ViewGroup) null);
        setContentView(viewInflate);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_dialog_title);
        Button button = (Button) viewInflate.findViewById(R.id.cancel);
        Button button2 = (Button) viewInflate.findViewById(R.id.confirm);
        this.g = (EditText) viewInflate.findViewById(R.id.edittext);
        if (this.d) {
            this.g.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            this.g.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        this.g.setInputType(this.c);
        this.g.setHint(this.e);
        this.g.setHintTextColor(this.f161a.getResources().getColor(R.color.edit_text_hint_color));
        textView.setText(this.b);
        setCanceledOnTouchOutside(true);
        button.setOnClickListener(new a());
        button2.setOnClickListener(new a());
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        this.f161a.getResources().getDisplayMetrics();
        attributes.width = (int) this.f161a.getResources().getDimension(R.dimen.dialog_password_width);
        attributes.height = (int) this.f161a.getResources().getDimension(R.dimen.dialog_password_height);
        window.setAttributes(attributes);
    }

    /* JADX INFO: compiled from: PasswordDialog.java */
    private class a implements View.OnClickListener {
        private a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.cancel) {
                h.this.onBackPressed();
            } else {
                if (id != R.id.confirm) {
                    return;
                }
                h.this.f.a(null, h.this.g.getText());
                h.this.g.setText("");
                h.this.onBackPressed();
            }
        }
    }
}
