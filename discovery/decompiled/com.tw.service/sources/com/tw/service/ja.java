package com.tw.service;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class ja implements View.OnTouchListener {
    private float Nk = 0.0f;
    private float Ok = 0.0f;

    ja(ka kaVar, TWService tWService) {
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int rawX = (int) (motionEvent.getRawX() - this.Nk);
        int rawY = (int) (motionEvent.getRawY() - this.Ok);
        int action = motionEvent.getAction();
        if (action == 0) {
            Log.i("Log", "Action_Down");
            motionEvent.getX();
            motionEvent.getY();
            this.Nk = motionEvent.getRawX();
            this.Ok = motionEvent.getRawY();
            return false;
        }
        if (action != 1 || rawX <= -10 || rawX >= 10 || rawY <= -10 || rawY >= 10) {
            return false;
        }
        com.tw.service.a.f.getInstance().ra();
        return false;
    }
}
