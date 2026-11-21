package com.dofun.carsetting.tw.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.support.v4.view.MotionEventCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.dofun.carsetting.R;
import com.dofun.carsetting.activity.BaseActivity;
import com.dofun.carsetting.f.b;
import com.dofun.carsetting.f.u;

/* JADX INFO: loaded from: classes.dex */
public class OpenPIPActivity extends BaseActivity {
    public u b;
    private ImageView d;
    private ImageView e;
    private int c = 5;
    private Handler f = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.tw.activity.OpenPIPActivity.4
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == 65280) {
                if (OpenPIPActivity.this.c > 0) {
                    OpenPIPActivity.this.d.getDrawable().setLevel(OpenPIPActivity.this.c);
                    OpenPIPActivity.this.f.sendEmptyMessageDelayed(MotionEventCompat.ACTION_POINTER_INDEX_MASK, 1000L);
                } else {
                    OpenPIPActivity.this.b.write(514, 1, 0);
                }
                OpenPIPActivity.e(OpenPIPActivity.this);
            }
            return true;
        }
    });

    @Override // com.dofun.carsetting.activity.BaseActivity
    protected void a() {
    }

    static /* synthetic */ int e(OpenPIPActivity openPIPActivity) {
        int i = openPIPActivity.c;
        openPIPActivity.c = i - 1;
        return i;
    }

    @Override // com.dofun.carsetting.activity.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_open_pip);
        this.b = u.a();
        b();
    }

    public void b() {
        this.d = (ImageView) findViewById(R.id.iv_time_number);
        this.e = (ImageView) findViewById(R.id.iv_time_bg);
        Button button = (Button) findViewById(R.id.cancel);
        Button button2 = (Button) findViewById(R.id.confirm);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.tw.activity.OpenPIPActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                OpenPIPActivity.this.f.removeMessages(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
                OpenPIPActivity.this.finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.tw.activity.OpenPIPActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                OpenPIPActivity.this.b.write(514, 1, 0);
                OpenPIPActivity.this.finish();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.dofun.carsetting.tw.activity.OpenPIPActivity$3] */
    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.c = 5;
        a(this.e);
        this.d.getDrawable().setLevel(this.c);
        this.f.sendEmptyMessage(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        new Thread() { // from class: com.dofun.carsetting.tw.activity.OpenPIPActivity.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                OpenPIPActivity.this.c();
            }
        }.start();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // com.dofun.carsetting.activity.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.b != null) {
            this.b.close();
        }
        this.f.removeCallbacksAndMessages(null);
    }

    private void a(ImageView imageView) {
        ((AnimationDrawable) imageView.getDrawable()).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        SystemProperties.set("persist.tw.forcepip", "1");
        if (b.p() || b.F() || b.G() || b.I() || b.J()) {
            this.b.write(40730, 1, 0, "mount -o remount,rw /system_tw");
        } else {
            this.b.write(40730, 1, 0, "mount -o remount,rw /");
        }
        this.b.write(40730, 1, 0, "cp -rf " + this.f34a.S + "theme/sfp/theme.apk " + this.f34a.S + "theme/");
        u uVar = this.b;
        StringBuilder sb = new StringBuilder();
        sb.append("cp -rf ");
        sb.append(this.f34a.S);
        sb.append("theme/sfp/theme.apk /data/tw/theme/");
        uVar.write(40730, 1, 0, sb.toString());
        this.b.write(40730, 3, 0, "chmod 755 " + this.f34a.S + "theme/theme.apk");
        this.b.write(40730, 3, 0, "chmod -R 777 /data/tw/theme");
        if (b.p() || b.F() || b.G() || b.I() || b.J()) {
            this.b.write(40730, 3, 0, "mount -o remount,ro /system_tw");
        } else {
            this.b.write(40730, 3, 0, "mount -o remount,ro /");
        }
        this.b.write(40730, 3, 0, "sync");
    }
}
