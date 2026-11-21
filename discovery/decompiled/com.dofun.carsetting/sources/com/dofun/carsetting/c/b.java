package com.dofun.carsetting.c;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.f.u;

/* JADX INFO: compiled from: BacklightEleAjustDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class b extends Dialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f139a;
    private String b;
    private com.dofun.carsetting.g.b c;
    private int d;
    private int e;
    private TextView f;
    private TextView g;
    private SeekBar h;
    private SeekBar i;
    private u j;

    public b(Context context) {
        super(context, R.style.SeekBarDialogStyle);
        this.b = "";
        this.f139a = context;
        this.j = u.a();
    }

    public b a(String str) {
        this.b = str;
        return this;
    }

    public b a(com.dofun.carsetting.g.b bVar) {
        this.c = bVar;
        return this;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a();
    }

    private void a() {
        Log.d("BacklightEleAjustDialog", "initView: ");
        View viewInflate = LayoutInflater.from(this.f139a).inflate(R.layout.background_ele_adjust, (ViewGroup) null);
        setContentView(viewInflate);
        setCanceledOnTouchOutside(true);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_dialog_title);
        ((ImageView) viewInflate.findViewById(R.id.iv_dialog_close)).setOnClickListener(new a());
        textView.setText(this.b);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        this.f139a.getResources().getDisplayMetrics();
        attributes.width = (int) this.f139a.getResources().getDimension(R.dimen.dialog_backlight_adjust_width);
        attributes.height = (int) this.f139a.getResources().getDimension(R.dimen.dialog_backlight_adjust_height);
        Log.d("BacklightEleAjustDialog", "init():lp.width:" + attributes.width + " lp.height:" + attributes.height);
        window.setAttributes(attributes);
        this.f = (TextView) findViewById(R.id.min_text);
        this.g = (TextView) findViewById(R.id.max_text);
        this.h = (SeekBar) findViewById(R.id.min_SeekBar);
        this.i = (SeekBar) findViewById(R.id.max_SeekBar);
        this.h.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.dofun.carsetting.c.b.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                b.this.c.a(null, 0, i, z);
                Log.d("BacklightEleAjustDialog", "onProgressChanged: " + i);
                if (z) {
                    if (i <= b.this.e + 27) {
                        b.this.d = i;
                    }
                    b.this.a(true);
                }
            }
        });
        this.i.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.dofun.carsetting.c.b.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    b.this.c.a(null, 1, i, z);
                    Log.d("BacklightEleAjustDialog", "onProgressChanged:1 " + i);
                    if (z) {
                        if (i >= b.this.d - 27) {
                            b.this.e = i;
                        }
                        b.this.b(true);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        if (this.h != null) {
            this.h.setProgress(this.d);
        }
        if (this.f != null) {
            this.f.setText((this.d + 5) + "");
        }
        if (z) {
            this.j.write(276, 0, this.d + 5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(boolean z) {
        if (this.i != null) {
            this.i.setProgress(this.e);
        }
        if (this.g != null) {
            this.g.setText((this.e + 33) + "");
        }
        if (z) {
            this.j.write(276, 1, this.e + 33);
        }
    }

    /* JADX INFO: compiled from: BacklightEleAjustDialog.java */
    private class a implements View.OnClickListener {
        private a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view.getId() != R.id.iv_dialog_close) {
                return;
            }
            b.this.onBackPressed();
        }
    }

    public void a(boolean z, int i, int i2, int i3) {
        this.d = i2;
        this.e = i3;
        a(z);
        b(z);
    }
}
