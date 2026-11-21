package com.dofun.carsetting.c;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.dofun.carsetting.R;

/* JADX INFO: compiled from: AutoConfirmDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class a extends Dialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f135a;
    private String b;
    private int c;
    private int d;
    private ImageView e;
    private ImageView f;
    private TextView g;
    private InterfaceC0013a h;
    private boolean i;
    private Handler j;

    /* JADX INFO: renamed from: com.dofun.carsetting.c.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: AutoConfirmDialog.java */
    public interface InterfaceC0013a {
        void a();

        void b();

        void c();
    }

    static /* synthetic */ int f(a aVar) {
        int i = aVar.d;
        aVar.d = i - 1;
        return i;
    }

    public a(Context context) {
        super(context, R.style.SeekBarDialogStyle);
        this.b = "";
        this.c = 0;
        this.d = 5;
        this.i = true;
        this.j = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.c.a.3
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.what != 65280) {
                    return true;
                }
                if (a.this.d > 0) {
                    a.this.e.getDrawable().setLevel(a.this.d);
                    a.this.j.sendEmptyMessageDelayed(MotionEventCompat.ACTION_POINTER_INDEX_MASK, 1000L);
                } else {
                    a.this.dismiss();
                    if (a.this.h != null) {
                        a.this.h.c();
                    }
                }
                a.f(a.this);
                return true;
            }
        });
        this.f135a = context;
    }

    public a(Context context, boolean z) {
        super(context, R.style.SeekBarDialogStyle);
        this.b = "";
        this.c = 0;
        this.d = 5;
        this.i = true;
        this.j = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.c.a.3
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.what != 65280) {
                    return true;
                }
                if (a.this.d > 0) {
                    a.this.e.getDrawable().setLevel(a.this.d);
                    a.this.j.sendEmptyMessageDelayed(MotionEventCompat.ACTION_POINTER_INDEX_MASK, 1000L);
                } else {
                    a.this.dismiss();
                    if (a.this.h != null) {
                        a.this.h.c();
                    }
                }
                a.f(a.this);
                return true;
            }
        });
        this.f135a = context;
        this.i = z;
    }

    public void a(InterfaceC0013a interfaceC0013a) {
        this.h = interfaceC0013a;
    }

    public a a(String str) {
        this.b = str;
        if (this.g != null) {
            this.g.setText(str);
        }
        return this;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a();
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.f135a).inflate(R.layout.dialog_auto_confirm, (ViewGroup) null);
        setContentView(viewInflate);
        this.e = (ImageView) viewInflate.findViewById(R.id.iv_time_number);
        this.f = (ImageView) viewInflate.findViewById(R.id.iv_time_bg);
        this.g = (TextView) viewInflate.findViewById(R.id.tv_dialog_message);
        Button button = (Button) viewInflate.findViewById(R.id.cancel);
        Button button2 = (Button) viewInflate.findViewById(R.id.confirm);
        if (!this.i) {
            button.setTextColor(this.f135a.getResources().getColor(R.color.gray));
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        }
        this.g.setText(this.b);
        setCanceledOnTouchOutside(false);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.c.a.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (a.this.i) {
                    a.this.dismiss();
                    if (a.this.h != null) {
                        a.this.h.b();
                    }
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.c.a.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                a.this.dismiss();
                if (a.this.h != null) {
                    a.this.h.a();
                }
            }
        });
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = (int) this.f135a.getResources().getDimension(R.dimen.dialog_auto_confirm_width);
            attributes.height = (int) this.f135a.getResources().getDimension(R.dimen.dialog_auto_confirm_height);
            window.setAttributes(attributes);
        }
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        this.d = 5;
        a(this.f);
        this.e.getDrawable().setLevel(this.d);
        this.j.sendEmptyMessageDelayed(MotionEventCompat.ACTION_POINTER_INDEX_MASK, 1000L);
    }

    @Override // android.app.Dialog
    public void hide() {
        this.j.removeCallbacksAndMessages(null);
        super.hide();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        this.j.removeCallbacksAndMessages(null);
        super.dismiss();
    }

    private void a(ImageView imageView) {
        ((AnimationDrawable) imageView.getDrawable()).start();
    }
}
