package com.tw.service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class oa extends Handler {
    private WindowManager Hc;
    private final ImageView Sc;
    protected boolean Tc;
    protected Context mContext;
    private final TextView mMessage;
    private final View mView;
    private final SeekBar ya;
    private la pa = la.open();
    private WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(-2, -2, 0, 0, 2008, 40, 1);

    public oa(Context context) {
        this.mContext = context;
        this.mView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.volume_adjust, (ViewGroup) null);
        this.Hc = (WindowManager) this.mContext.getSystemService("window");
        this.mLayoutParams.gravity = 48;
        this.mMessage = (TextView) this.mView.findViewById(R.id.message);
        this.Sc = (ImageView) this.mView.findViewById(R.id.other_stream_icon);
        this.Sc.setOnClickListener(new ma(this));
        this.ya = (SeekBar) this.mView.findViewById(R.id.level);
        this.ya.setOnSeekBarChangeListener(new na(this));
    }

    public void a(int i, int i2) {
        int i3 = i & Integer.MAX_VALUE;
        this.Sc.setVisibility(0);
        this.Sc.setImageResource(this.Tc ? R.drawable.ic_volume_off_small : R.drawable.ic_volume_small);
        String str = "";
        TextView textView = this.mMessage;
        if (i3 != 0) {
            str = "" + i3;
        }
        textView.setText(str);
        if (i2 != this.ya.getMax()) {
            this.ya.setMax(i2);
        }
        this.ya.setProgress(i3);
        if (this.mView.getParent() == null) {
            this.Hc.addView(this.mView, this.mLayoutParams);
            removeMessages(65294);
            sendEmptyMessageDelayed(65294, 2500L);
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (message.what == 65294 && this.mView.getParent() != null) {
            this.Hc.removeView(this.mView);
        }
    }

    public boolean w() {
        View view = this.mView;
        return (view == null || view.getParent() == null) ? false : true;
    }
}
