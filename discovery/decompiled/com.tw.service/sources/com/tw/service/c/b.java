package com.tw.service.c;

import android.content.Context;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;
import com.tw.service.R;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private WindowManager Hc;
    private Handler mHandler = new a(this);
    private WindowManager.LayoutParams mLayoutParams;
    private TextView mView;

    public b(Context context) {
        this.mView = new TextView(context);
        this.mView.setTextColor(-16777216);
        this.mView.setPadding(25, 15, 15, 25);
        this.mView.setBackgroundResource(R.drawable.toast_view_shape);
        this.mView.setTextSize(30.0f);
        this.Hc = (WindowManager) context.getSystemService("window");
        this.mLayoutParams = new WindowManager.LayoutParams(-2, -2, 0, 0, 2002, 56, 1);
        WindowManager.LayoutParams layoutParams = this.mLayoutParams;
        layoutParams.gravity = 80;
        layoutParams.y = 25;
    }

    public void h(String str) {
        TextView textView = this.mView;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void hide() {
        if (this.mView.getParent() != null) {
            this.Hc.removeView(this.mView);
        }
    }

    public void show() {
        if (this.mView.getParent() == null) {
            this.Hc.addView(this.mView, this.mLayoutParams);
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageDelayed(1, 2000L);
        }
    }
}
