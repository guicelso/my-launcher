package com.dofun.carsetting.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.dofun.carsetting.b.a;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseActivity extends AppCompatActivity {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public a f34a;
    private Handler b = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.activity.BaseActivity.2
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 65281) {
                return true;
            }
            BaseActivity.this.a();
            return true;
        }
    });

    protected abstract void a();

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        b();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.dofun.carsetting.activity.BaseActivity$1] */
    private void b() {
        this.f34a = a.a();
        new Thread() { // from class: com.dofun.carsetting.activity.BaseActivity.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                BaseActivity.this.f34a.b();
                BaseActivity.this.b.removeMessages(65281);
                BaseActivity.this.b.sendEmptyMessage(65281);
            }
        }.start();
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a(String str) {
        Toast.makeText(this, str, 0).show();
    }
}
