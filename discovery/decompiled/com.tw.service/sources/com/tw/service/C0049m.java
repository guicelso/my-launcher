package com.tw.service;

import android.media.AudioManager;
import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: renamed from: com.tw.service.m, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0049m implements AudioManager.OnAudioFocusChangeListener {
    final /* synthetic */ TWService n;

    C0049m(TWService tWService) {
        this.n = tWService;
    }

    @Override // android.media.AudioManager.OnAudioFocusChangeListener
    public void onAudioFocusChange(int i) throws Throwable {
        int i2;
        int mode = this.n.mAudioManager.getMode();
        Log.i("TWService", "focusChange = " + i + "mode = " + mode + "curAudioFocusName = " + this.n.mAudioManager.getCurrentAudioFocusName());
        if (i == -3) {
            int i3 = this.n.mSource;
            if (i3 == 1) {
                this.n.pa.write(776, 1);
                return;
            }
            if (i3 == 3) {
                this.n.pa.write(40451, 7);
                return;
            }
            if (i3 == 4) {
                this.n.pa.write(40452, 7);
                return;
            } else if (i3 == 8) {
                this.n.pa.write(40456, 7);
                return;
            } else {
                if (i3 != 9) {
                    return;
                }
                this.n.pa.write(40457, 7);
                return;
            }
        }
        if (i == -2) {
            if (mode == 2 || mode == 1) {
                return;
            }
            this.n.p(true);
            this.n.vb = 1;
            return;
        }
        if (i == -1) {
            StringBuilder sbE = b.a.a.a.a.e("mActivity = ");
            sbE.append(this.n.mActivity);
            sbE.append("mSource = ");
            sbE.append(this.n.mSource);
            Log.i("TWService", sbE.toString());
            if ((this.n.mActivity & 143) != this.n.mSource && (i2 = this.n.mSource) != 1) {
                if (i2 == 3) {
                    this.n.pa.write(40451, 6);
                } else if (i2 == 4) {
                    this.n.pa.write(40452, 6);
                } else if (i2 == 8) {
                    this.n.pa.write(40456, 6);
                } else if (i2 == 9) {
                    this.n.pa.write(40457, 6);
                }
            }
            if (TextUtils.equals(this.n.ya(), this.n.mAudioManager.getCurrentAudioFocusName())) {
                this.n.pa.write(769, 192, 12);
            } else {
                this.n.pa.write(769, 192, 0);
            }
            this.n.ub = null;
            return;
        }
        if (i == 1 || i == 2 || i == 3) {
            if (i == 1 && this.n.hc == 1) {
                return;
            }
            this.n.vb = 0;
            this.n.p(false);
            int i4 = this.n.mSource;
            if (i4 == 1) {
                this.n.pa.write(776, 0);
                return;
            }
            if (i4 == 3) {
                this.n.pa.write(40451, 8);
                return;
            }
            if (i4 == 4) {
                this.n.pa.write(40452, 8);
            } else if (i4 == 8) {
                this.n.pa.write(40456, 8);
            } else {
                if (i4 != 9) {
                    return;
                }
                this.n.pa.write(40457, 8);
            }
        }
    }
}
