package com.dofun.carsetting.f;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: compiled from: LongClickUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class h {
    public static void a(final Handler handler, final View view, final long j, final View.OnLongClickListener onLongClickListener) {
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.dofun.carsetting.f.h.1
            private int f;
            private int g;
            private int e = 50;
            private Runnable h = new Runnable() { // from class: com.dofun.carsetting.f.h.1.1
                @Override // java.lang.Runnable
                public void run() {
                    if (onLongClickListener != null) {
                        onLongClickListener.onLongClick(view);
                    }
                }
            };

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                switch (motionEvent.getAction()) {
                    case 0:
                        handler.removeCallbacks(this.h);
                        this.f = x;
                        this.g = y;
                        handler.postDelayed(this.h, j);
                        break;
                    case 1:
                        handler.removeCallbacks(this.h);
                        break;
                    case 2:
                        if (Math.abs(this.f - x) > this.e || Math.abs(this.g - y) > this.e) {
                            handler.removeCallbacks(this.h);
                        }
                        break;
                }
                return true;
            }
        });
    }
}
