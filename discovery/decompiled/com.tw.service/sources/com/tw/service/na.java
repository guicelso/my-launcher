package com.tw.service;

import android.widget.SeekBar;

/* JADX INFO: loaded from: classes.dex */
class na implements SeekBar.OnSeekBarChangeListener {
    final /* synthetic */ oa n;

    na(oa oaVar) {
        this.n = oaVar;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            this.n.pa.write(515, 1, i);
        }
        this.n.removeMessages(65294);
        this.n.sendEmptyMessageDelayed(65294, 2500L);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
