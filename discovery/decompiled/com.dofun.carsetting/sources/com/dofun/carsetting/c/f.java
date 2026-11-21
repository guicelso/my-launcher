package com.dofun.carsetting.c;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.f.u;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Locale;

/* JADX INFO: compiled from: McuUpdateTS7.java */
/* JADX INFO: loaded from: classes.dex */
public class f extends Dialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    ListView f152a;
    private Context b;
    private u c;
    private String d;
    private int e;
    private int f;
    private String g;
    private String h;
    private int i;

    @SuppressLint({"HandlerLeak"})
    private Handler j;
    private SeekBar k;
    private TextView l;
    private boolean m;
    private com.dofun.carsetting.adapter.f n;
    private final BroadcastReceiver o;

    public f(Context context) {
        super(context, R.style.SeekBarDialogStyle);
        this.d = null;
        this.e = 0;
        this.f = 0;
        this.g = null;
        this.h = null;
        this.i = 1023;
        this.j = new Handler() { // from class: com.dofun.carsetting.c.f.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                try {
                    int i = message.what;
                    if (i != 266) {
                        if (i != 1539) {
                            if (i != 1541) {
                                return;
                            }
                            f.this.c.write(65281);
                            return;
                        } else {
                            if (f.this.k != null) {
                                f.this.k.setProgress((message.arg1 * 100) / f.this.i);
                                f.this.l.setText(f.this.b.getResources().getString(R.string.mcu_update_summary) + ((message.arg1 * 100) / f.this.i) + "%");
                                return;
                            }
                            return;
                        }
                    }
                    if (f.this.d == null) {
                        f.this.d = (String) message.obj;
                        if (f.this.d == null || f.this.d.length() <= 0) {
                            return;
                        }
                        String str = f.this.d.split("-")[3];
                        f.this.e = Integer.parseInt(str.substring(4, 6), 16);
                        f.this.f = Integer.parseInt(str.substring(0, 2), 16);
                        switch ((f.this.e & 28) >> 2) {
                            case 0:
                                f.this.g = com.dofun.carsetting.b.b.a();
                                f.this.h = com.dofun.carsetting.b.b.p();
                                break;
                            case 1:
                                f.this.g = com.dofun.carsetting.b.b.b();
                                f.this.h = com.dofun.carsetting.b.b.q();
                                break;
                            case 2:
                                f.this.g = com.dofun.carsetting.b.b.c();
                                f.this.h = com.dofun.carsetting.b.b.r();
                                break;
                            case 3:
                                f.this.g = com.dofun.carsetting.b.b.d();
                                f.this.h = com.dofun.carsetting.b.b.s();
                                break;
                            case 4:
                                f.this.g = com.dofun.carsetting.b.b.e();
                                f.this.h = com.dofun.carsetting.b.b.t();
                                break;
                            case 5:
                                f.this.g = com.dofun.carsetting.b.b.f();
                                f.this.h = com.dofun.carsetting.b.b.u();
                                break;
                            case 6:
                                f.this.g = com.dofun.carsetting.b.b.g();
                                f.this.h = com.dofun.carsetting.b.b.q();
                                break;
                            case 7:
                                switch (f.this.f & 15) {
                                    case 0:
                                        f.this.g = com.dofun.carsetting.b.b.h();
                                        f.this.h = com.dofun.carsetting.b.b.q();
                                        break;
                                    case 1:
                                        f.this.g = com.dofun.carsetting.b.b.i();
                                        f.this.h = com.dofun.carsetting.b.b.q();
                                        break;
                                    case 2:
                                        f.this.g = com.dofun.carsetting.b.b.j();
                                        f.this.h = com.dofun.carsetting.b.b.q();
                                        break;
                                    case 3:
                                        f.this.g = com.dofun.carsetting.b.b.k();
                                        f.this.h = com.dofun.carsetting.b.b.q();
                                        break;
                                    case 5:
                                        f.this.g = com.dofun.carsetting.b.b.l();
                                        f.this.h = com.dofun.carsetting.b.b.q();
                                        break;
                                    case 6:
                                        f.this.g = com.dofun.carsetting.b.b.m();
                                        f.this.h = com.dofun.carsetting.b.b.q();
                                        break;
                                    case 7:
                                        f.this.g = com.dofun.carsetting.b.b.n();
                                        f.this.h = com.dofun.carsetting.b.b.q();
                                        break;
                                }
                                break;
                            default:
                                f.this.g = com.dofun.carsetting.b.b.o();
                                f.this.h = com.dofun.carsetting.b.b.v();
                                break;
                        }
                        f.this.c();
                    }
                } catch (Exception unused) {
                }
            }
        };
        this.o = new BroadcastReceiver() { // from class: com.dofun.carsetting.c.f.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                intent.getStringExtra("cmd");
                if ("ACTION_MEDIA_MOUNTED".equals(action)) {
                    f.this.l.setText("");
                    if (f.this.m) {
                        f.this.a("/usb");
                        f.this.m = false;
                        return;
                    }
                    return;
                }
                if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action)) {
                    return;
                }
                "android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action);
            }
        };
        this.b = context;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a();
        this.c = u.a();
        if (this.c != null) {
            this.c.addHandler("McuUpdateDialog", this.j);
            this.c.write(266, 255);
        }
        d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        this.n = new com.dofun.carsetting.adapter.f(this.b, new CharSequence[]{"USB1", "USB2"});
        this.f152a.setAdapter((ListAdapter) this.n);
    }

    public void a() {
        View viewInflate = LayoutInflater.from(this.b).inflate(R.layout.dialog_mcu_update, (ViewGroup) null);
        setContentView(viewInflate);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_dialog_title);
        this.l = (TextView) viewInflate.findViewById(R.id.update_tv);
        this.f152a = (ListView) viewInflate.findViewById(R.id.item_list);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_dialog_close);
        this.k = (SeekBar) viewInflate.findViewById(R.id.update_progress);
        this.k.setVisibility(0);
        this.k.setOnTouchListener(new View.OnTouchListener() { // from class: com.dofun.carsetting.c.f.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        textView.setText(this.b.getResources().getString(R.string.mcu_update));
        setCanceledOnTouchOutside(true);
        this.f152a.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.dofun.carsetting.c.f.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) throws Throwable {
                f.this.n.a(i);
                int iB = f.this.b();
                if (i == iB) {
                    f.this.a("/usb");
                    return;
                }
                int i2 = iB + 1;
                if (i2 > 1) {
                    i2 = 0;
                }
                f.a(i2 + 4);
                f.this.m = true;
            }
        });
        imageView.setOnClickListener(new a());
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (((double) this.b.getResources().getDisplayMetrics().widthPixels) * 0.5d);
        attributes.width = (int) this.b.getResources().getDimension(R.dimen.tw_dp_w500);
        window.setAttributes(attributes);
    }

    /* JADX INFO: compiled from: McuUpdateTS7.java */
    private class a implements View.OnClickListener {
        private a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view.getId() != R.id.iv_dialog_close) {
                return;
            }
            f.this.onBackPressed();
        }
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        if (this.c != null) {
            this.c.removeHandler("McuUpdateDialog");
            this.c.close();
            this.c = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        int iWrite;
        if (this.g != null) {
            File file = new File(str + "/" + this.g);
            if (!file.exists() || (iWrite = this.c.write(MotionEventCompat.ACTION_POINTER_INDEX_MASK, 0, 0, file.getAbsolutePath())) < 0) {
                return;
            }
            this.c.write(40730, 3, 0, "rm /data/tw/config");
            if (iWrite >= 128) {
                this.i = (iWrite / 64) - 1;
            }
        }
    }

    public int b() throws Throwable {
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/sys/class/tw/misc/host"));
                try {
                    int i = Integer.parseInt(bufferedReader2.readLine());
                    bufferedReader2.close();
                    return i;
                } catch (Exception unused) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader == null) {
                        return -1;
                    }
                    bufferedReader.close();
                    return -1;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        } catch (Exception unused2) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void a(int i) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/sys/class/tw/misc/host");
            try {
                fileOutputStream.write(String.format(Locale.US, "%d", Integer.valueOf(i)).getBytes());
            } catch (Exception unused) {
            } finally {
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void d() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        intentFilter.addAction("ACTION_MEDIA_MOUNTED");
        this.b.registerReceiver(this.o, intentFilter);
    }
}
