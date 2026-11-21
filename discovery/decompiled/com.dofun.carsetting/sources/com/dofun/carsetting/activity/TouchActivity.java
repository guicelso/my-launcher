package com.dofun.carsetting.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.dofun.carsetting.R;
import com.dofun.carsetting.f.b;
import com.dofun.carsetting.f.f;
import com.dofun.carsetting.f.u;
import com.dofun.carsetting.widget.CalibrationView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class TouchActivity extends Activity implements View.OnLongClickListener {
    static int b;
    static int c;
    private static final int[] m = {R.id.key_1, R.id.key_2, R.id.key_3, R.id.key_4, R.id.key_5, R.id.key_6, R.id.key_7, R.id.key_8, R.id.key_9, R.id.key_10, R.id.key_11, R.id.key_12};
    private AlertDialog w;
    private AlertDialog x;
    private com.dofun.carsetting.b.a d = com.dofun.carsetting.b.a.a();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String f52a = "xcy";
    private int e = 0;
    private Handler f = new Handler() { // from class: com.dofun.carsetting.activity.TouchActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                if (message.what != 65280) {
                    return;
                }
                TouchActivity.this.c();
            } catch (Exception unused) {
            }
        }
    };
    private boolean g = false;
    private boolean h = false;
    private boolean i = false;
    private int[] j = new int[12];
    private int[] k = new int[12];
    private int[] l = new int[12];
    private int n = 0;
    private int o = 0;
    private int p = 0;
    private Dialog q = null;
    private CountDownTimer r = null;
    private CalibrationView s = null;
    private int t = 0;
    private int[] u = new int[5];
    private int[] v = new int[5];

    private int d(int i) {
        if (i > 30) {
            return 30;
        }
        if (i > 25) {
            return 25;
        }
        return i > 20 ? 20 : 15;
    }

    static /* synthetic */ int h(TouchActivity touchActivity) {
        int i = touchActivity.t + 1;
        touchActivity.t = i;
        return i;
    }

    private void a() {
        byte[] bArr = new byte[412];
        d(bArr);
        if (this.g) {
            for (int i = 8; i < 12; i++) {
                findViewById(m[i]).setVisibility(4);
            }
            findViewById(R.id.key_cfg).setVisibility(4);
            findViewById(R.id.key_pos).setVisibility(4);
            if (bArr[248] == 6) {
                for (int i2 = 0; i2 < 4; i2++) {
                    this.j[i2] = bArr[249] == 0 ? (byte) 0 : bArr[(i2 * 4) + ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION];
                    this.j[i2 + 4] = bArr[249] == 0 ? (byte) 0 : bArr[(i2 * 4) + ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION + 1];
                }
            }
        } else if (bArr[266] == 5) {
            for (int i3 = 0; i3 < bArr[267]; i3++) {
                this.j[i3] = bArr[(i3 * 12) + 268];
            }
        }
        if (bArr[242] == 7 && (bArr[243] & 128) == 128) {
            this.p = bArr[243] & 127;
        }
        c();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.tw_touch);
        findViewById(R.id.key_start).setOnLongClickListener(this);
        findViewById(R.id.key_clear).setOnLongClickListener(this);
        this.s = (CalibrationView) findViewById(R.id.calibration);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        b = displayMetrics.widthPixels;
        c = displayMetrics.heightPixels;
        if (b.p() || b.v() || b.F() || b.G() || b.I() || b.J()) {
            this.e = 1;
        }
        this.s.setOnTouchListener(new View.OnTouchListener() { // from class: com.dofun.carsetting.activity.TouchActivity.7
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    int cali = TouchActivity.this.s.getCali();
                    TouchActivity.this.u[cali] = (int) motionEvent.getX();
                    TouchActivity.this.v[cali] = (int) motionEvent.getY();
                    Log.d(TouchActivity.this.f52a, "mCaliX[cali] = " + TouchActivity.this.u[cali] + " mCaliY[cali] = " + TouchActivity.this.v[cali]);
                    if (cali == 4) {
                        if (TouchActivity.this.t != 0) {
                            if (TouchActivity.this.h()) {
                                TouchActivity.this.t = 1;
                                TouchActivity.this.i();
                            } else if (TouchActivity.h(TouchActivity.this) <= 5) {
                                TouchActivity.this.s.b();
                            } else {
                                TouchActivity.this.finish();
                                Toast.makeText(TouchActivity.this.getApplicationContext(), R.string.key_calibration_failed, 0).show();
                            }
                        } else {
                            TouchActivity.this.s.setVisibility(8);
                            if (!TouchActivity.this.h()) {
                                TouchActivity.this.g();
                                Toast.makeText(TouchActivity.this.getApplicationContext(), R.string.key_calibration_failed, 0).show();
                            }
                        }
                    } else {
                        TouchActivity.this.s.b();
                    }
                }
                return true;
            }
        });
    }

    @Override // android.app.Activity
    protected void onResume() {
        Intent intent = getIntent();
        Log.d(this.f52a, "mCalibration = " + this.t);
        if (intent != null) {
            this.g = intent.getIntExtra("key", 0) == 1;
            this.t = intent.getIntExtra("calibration", 0);
            setIntent(null);
        }
        a();
        Log.d(this.f52a, "onResume: mCalibration = " + this.t);
        if (this.t != 0) {
            f();
            this.s.a();
            this.s.setVisibility(0);
            if (this.w != null) {
                this.h = true;
                this.w.dismiss();
            }
            if (this.x != null) {
                this.x.dismiss();
            }
        }
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        setIntent(null);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.r != null) {
            this.r.cancel();
            this.r = null;
        }
        if (this.q != null) {
            this.q.dismiss();
            this.q = null;
        }
        super.onDestroy();
    }

    private void a(byte[] bArr) {
        try {
            FileInputStream fileInputStream = new FileInputStream("/sys/class/tw/gtp/touch");
            try {
                fileInputStream.read(bArr);
            } catch (Exception unused) {
            } finally {
                fileInputStream.close();
            }
        } catch (Exception unused2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        byte[] bArr = new byte[2];
        c(2);
        a(bArr);
        if (bArr[0] == 2) {
            this.j[bArr[1]] = i;
        }
        this.f.sendEmptyMessage(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    private void b() throws Throwable {
        int i = 0;
        while (i < 8 && this.j[i] == 0) {
            i++;
        }
        if (i == 8) {
            byte[] bArr = {6, 0};
            b(bArr);
            a(bArr, 248, 18);
        } else {
            byte[] bArr2 = {6, 8, (byte) this.j[0], (byte) this.j[4], 0, 0, (byte) this.j[1], (byte) this.j[5], 0, 0, (byte) this.j[2], (byte) this.j[6], 0, 0, (byte) this.j[3], (byte) this.j[7], 0, 0};
            b(bArr2);
            a(bArr2, 248, 18);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, int i2) {
        byte[] bArr = new byte[5];
        c(1);
        a(bArr);
        if (bArr[0] == 1) {
            this.j[i2] = i;
            this.k[i2] = (bArr[1] & 255) | ((bArr[2] & 255) << 8);
            this.l[i2] = ((bArr[4] & 255) << 8) | (bArr[3] & 255);
        }
        this.f.sendEmptyMessage(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:13:0x001e  */
    public void b(int i) throws Throwable {
        this.p = i;
        int i2 = 5;
        if (i != 1) {
            if (i != 3 && i != 5) {
                switch (i) {
                    case 7:
                        i2 = 2;
                        break;
                    case 8:
                        i2 = 3;
                        break;
                    case 9:
                        i2 = 4;
                        break;
                    case 10:
                        break;
                    case 11:
                        i2 = 6;
                        break;
                    case 12:
                        i2 = 7;
                        break;
                    default:
                        i2 = 0;
                        break;
                }
            } else {
                i2 = 1;
            }
        } else {
            i2 = 2;
        }
        b(new byte[]{7, (byte) i2});
        a(new byte[]{7, (byte) (i | 128)}, 242, 6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(TextView textView, int i, int i2) {
        if (i == 0) {
            i = R.string.key_reserve;
        }
        textView.setText(i);
        textView.getBackground().setLevel(i2);
    }

    private void a(int i, int i2, int i3) {
        a((TextView) findViewById(i), i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        for (int i = 0; i < 12; i++) {
            a(m[i], f.a(this.j[i]), this.j[i] == 0 ? 0 : 1);
        }
    }

    private int c(int i) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/sys/class/tw/gtp/touch");
            try {
                fileOutputStream.write(new byte[]{(byte) i});
            } catch (Exception unused) {
            } finally {
                fileOutputStream.close();
            }
        } catch (Exception unused2) {
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int b(byte[] bArr) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/sys/class/tw/gtp/touch");
            try {
                fileOutputStream.write(bArr);
            } catch (Exception unused) {
            } finally {
                fileOutputStream.close();
            }
            return 0;
        } catch (Exception unused2) {
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c(byte[] bArr) {
        c(3);
        a(bArr);
        return bArr[0] == 3;
    }

    private class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f72a;
        int b;
        int c;

        public a(int i, int i2, int i3) {
            this.f72a = i;
            this.b = i2;
            this.c = i3;
        }
    }

    private void a(ArrayList<a> arrayList, byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        int i6 = i2;
        Collections.sort(arrayList, new Comparator<a>() { // from class: com.dofun.carsetting.activity.TouchActivity.8
            @Override // java.util.Comparator
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(a aVar, a aVar2) {
                return aVar.b - aVar2.b;
            }
        });
        Iterator<a> it = arrayList.iterator();
        byte b2 = 0;
        int i7 = 0;
        while (it.hasNext()) {
            i7 += it.next().c;
        }
        int size = i7 / arrayList.size();
        int iD = d((int) ((i5 - i3) / 2.5f));
        int i8 = ((size - iD) / 4) * 4;
        if (i8 < i3) {
            i8 = i3;
        }
        int i9 = ((size + iD) / 4) * 4;
        if (i9 > i5) {
            i9 = i5;
        }
        if (arrayList.size() == 1) {
            a aVar = arrayList.get(0);
            int i10 = i + 0;
            bArr[i10 + 0] = (byte) aVar.f72a;
            bArr[i10 + 1] = 0;
            bArr[i10 + 2] = 0;
            bArr[i10 + 3] = 0;
            int iD2 = ((aVar.b - d(aVar.b - i6)) / 4) * 4;
            if (iD2 >= i6) {
                i6 = iD2;
            }
            bArr[i10 + 4] = (byte) (i6 & 255);
            bArr[i10 + 5] = (byte) ((i6 >> 8) & 255);
            int iD3 = ((aVar.b + d((int) ((i4 - aVar.b) / 2.5f))) / 4) * 4;
            if (iD3 > i4) {
                iD3 = i4;
            }
            bArr[i10 + 6] = (byte) (iD3 & 255);
            bArr[i10 + 7] = (byte) ((iD3 >> 8) & 255);
            bArr[i10 + 8] = (byte) (i8 & 255);
            bArr[i10 + 9] = (byte) ((i8 >> 8) & 255);
            bArr[i10 + 10] = (byte) (i9 & 255);
            bArr[i10 + 11] = (byte) ((i9 >> 8) & 255);
            return;
        }
        int i11 = 0;
        while (i11 < 1) {
            a aVar2 = arrayList.get(i11);
            int i12 = i + (i11 * 12);
            bArr[i12 + 0] = (byte) aVar2.f72a;
            bArr[i12 + 1] = b2;
            bArr[i12 + 2] = b2;
            bArr[i12 + 3] = b2;
            int iD4 = ((aVar2.b - d(aVar2.b - i6)) / 4) * 4;
            if (iD4 < i6) {
                iD4 = i6;
            }
            bArr[i12 + 4] = (byte) (iD4 & 255);
            bArr[i12 + 5] = (byte) ((iD4 >> 8) & 255);
            i11++;
            int iD5 = ((aVar2.b + d((int) ((arrayList.get(i11).b - aVar2.b) / 2.5f))) / 4) * 4;
            if (iD5 > i4) {
                iD5 = i4;
            }
            bArr[i12 + 6] = (byte) (iD5 & 255);
            bArr[i12 + 7] = (byte) ((iD5 >> 8) & 255);
            bArr[i12 + 8] = (byte) (i8 & 255);
            bArr[i12 + 9] = (byte) ((i8 >> 8) & 255);
            bArr[i12 + 10] = (byte) (i9 & 255);
            bArr[i12 + 11] = (byte) ((i9 >> 8) & 255);
            b2 = 0;
        }
        while (i11 < arrayList.size() - 1) {
            a aVar3 = arrayList.get(i11);
            int i13 = i + (i11 * 12);
            bArr[i13 + 0] = (byte) aVar3.f72a;
            bArr[i13 + 1] = 0;
            bArr[i13 + 2] = 0;
            bArr[i13 + 3] = 0;
            int iD6 = ((aVar3.b - d(aVar3.b - arrayList.get(i11 - 1).b)) / 4) * 4;
            if (iD6 < i6) {
                iD6 = i6;
            }
            bArr[i13 + 4] = (byte) (iD6 & 255);
            bArr[i13 + 5] = (byte) ((iD6 >> 8) & 255);
            i11++;
            int iD7 = ((aVar3.b + d((int) ((arrayList.get(i11).b - aVar3.b) / 2.5f))) / 4) * 4;
            if (iD7 > i4) {
                iD7 = i4;
            }
            bArr[i13 + 6] = (byte) (iD7 & 255);
            bArr[i13 + 7] = (byte) ((iD7 >> 8) & 255);
            bArr[i13 + 8] = (byte) (i8 & 255);
            bArr[i13 + 9] = (byte) ((i8 >> 8) & 255);
            bArr[i13 + 10] = (byte) (i9 & 255);
            bArr[i13 + 11] = (byte) ((i9 >> 8) & 255);
        }
        while (i11 < arrayList.size()) {
            a aVar4 = arrayList.get(i11);
            int i14 = i + (i11 * 12);
            bArr[i14 + 0] = (byte) aVar4.f72a;
            bArr[i14 + 1] = 0;
            bArr[i14 + 2] = 0;
            bArr[i14 + 3] = 0;
            int iD8 = ((aVar4.b - d(aVar4.b - arrayList.get(i11 - 1).b)) / 4) * 4;
            if (iD8 < i6) {
                iD8 = i6;
            }
            bArr[i14 + 4] = (byte) (iD8 & 255);
            bArr[i14 + 5] = (byte) ((iD8 >> 8) & 255);
            int iD9 = ((aVar4.b + d((int) ((i4 - aVar4.b) / 2.5f))) / 4) * 4;
            if (iD9 > i4) {
                iD9 = i4;
            }
            bArr[i14 + 6] = (byte) (iD9 & 255);
            bArr[i14 + 7] = (byte) ((iD9 >> 8) & 255);
            bArr[i14 + 8] = (byte) (i8 & 255);
            bArr[i14 + 9] = (byte) ((i8 >> 8) & 255);
            bArr[i14 + 10] = (byte) (i9 & 255);
            bArr[i14 + 11] = (byte) ((i9 >> 8) & 255);
            i11++;
        }
    }

    private void b(ArrayList<a> arrayList, byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        Collections.sort(arrayList, new Comparator<a>() { // from class: com.dofun.carsetting.activity.TouchActivity.9
            @Override // java.util.Comparator
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(a aVar, a aVar2) {
                return aVar.c - aVar2.c;
            }
        });
        Iterator<a> it = arrayList.iterator();
        byte b2 = 0;
        int i6 = 0;
        while (it.hasNext()) {
            i6 += it.next().b;
        }
        int size = i6 / arrayList.size();
        int iD = d((int) ((i4 - i2) / 2.5f));
        int i7 = ((size - iD) / 4) * 4;
        if (i7 < i2) {
            i7 = i2;
        }
        int i8 = ((size + iD) / 4) * 4;
        if (i8 > i4) {
            i8 = i4;
        }
        if (arrayList.size() == 1) {
            a aVar = arrayList.get(0);
            int i9 = i + 0;
            bArr[i9 + 0] = (byte) aVar.f72a;
            bArr[i9 + 1] = 0;
            bArr[i9 + 2] = 0;
            bArr[i9 + 3] = 0;
            bArr[i9 + 4] = (byte) (i7 & 255);
            bArr[i9 + 5] = (byte) ((i7 >> 8) & 255);
            bArr[i9 + 6] = (byte) (i8 & 255);
            bArr[i9 + 7] = (byte) ((i8 >> 8) & 255);
            int iD2 = ((aVar.c - d(aVar.c - i3)) / 4) * 4;
            if (iD2 < i3) {
                iD2 = i3;
            }
            bArr[i9 + 8] = (byte) (iD2 & 255);
            bArr[i9 + 9] = (byte) ((iD2 >> 8) & 255);
            int iD3 = ((aVar.c + d((int) ((i5 - aVar.c) / 2.5f))) / 4) * 4;
            if (iD3 > i5) {
                iD3 = i5;
            }
            bArr[i9 + 10] = (byte) (iD3 & 255);
            bArr[i9 + 11] = (byte) ((iD3 >> 8) & 255);
            return;
        }
        int i10 = 0;
        while (i10 < 1) {
            a aVar2 = arrayList.get(i10);
            int i11 = i + (i10 * 12);
            bArr[i11 + 0] = (byte) aVar2.f72a;
            bArr[i11 + 1] = b2;
            bArr[i11 + 2] = b2;
            bArr[i11 + 3] = b2;
            bArr[i11 + 4] = (byte) (i7 & 255);
            bArr[i11 + 5] = (byte) ((i7 >> 8) & 255);
            bArr[i11 + 6] = (byte) (i8 & 255);
            bArr[i11 + 7] = (byte) ((i8 >> 8) & 255);
            int iD4 = ((aVar2.c - d(aVar2.c - i3)) / 4) * 4;
            if (iD4 < i3) {
                iD4 = i3;
            }
            bArr[i11 + 8] = (byte) (iD4 & 255);
            bArr[i11 + 9] = (byte) ((iD4 >> 8) & 255);
            i10++;
            int iD5 = ((aVar2.c + d((int) ((arrayList.get(i10).c - aVar2.c) / 2.5f))) / 4) * 4;
            if (iD5 > i5) {
                iD5 = i5;
            }
            bArr[i11 + 10] = (byte) (iD5 & 255);
            bArr[i11 + 11] = (byte) ((iD5 >> 8) & 255);
            b2 = 0;
        }
        while (i10 < arrayList.size() - 1) {
            a aVar3 = arrayList.get(i10);
            int i12 = i + (i10 * 12);
            bArr[i12 + 0] = (byte) aVar3.f72a;
            bArr[i12 + 1] = 0;
            bArr[i12 + 2] = 0;
            bArr[i12 + 3] = 0;
            bArr[i12 + 4] = (byte) (i7 & 255);
            bArr[i12 + 5] = (byte) ((i7 >> 8) & 255);
            bArr[i12 + 6] = (byte) (i8 & 255);
            bArr[i12 + 7] = (byte) ((i8 >> 8) & 255);
            int iD6 = ((aVar3.c - d(aVar3.c - arrayList.get(i10 - 1).c)) / 4) * 4;
            if (iD6 < i3) {
                iD6 = i3;
            }
            bArr[i12 + 8] = (byte) (iD6 & 255);
            bArr[i12 + 9] = (byte) ((iD6 >> 8) & 255);
            i10++;
            int iD7 = ((aVar3.c + d((int) ((arrayList.get(i10).c - aVar3.c) / 2.5f))) / 4) * 4;
            if (iD7 > i5) {
                iD7 = i5;
            }
            bArr[i12 + 10] = (byte) (iD7 & 255);
            bArr[i12 + 11] = (byte) ((iD7 >> 8) & 255);
        }
        while (i10 < arrayList.size()) {
            a aVar4 = arrayList.get(i10);
            int i13 = i + (i10 * 12);
            bArr[i13 + 0] = (byte) aVar4.f72a;
            bArr[i13 + 1] = 0;
            bArr[i13 + 2] = 0;
            bArr[i13 + 3] = 0;
            bArr[i13 + 4] = (byte) (i7 & 255);
            bArr[i13 + 5] = (byte) ((i7 >> 8) & 255);
            bArr[i13 + 6] = (byte) (i8 & 255);
            bArr[i13 + 7] = (byte) ((i8 >> 8) & 255);
            int iD8 = ((aVar4.c - d(aVar4.c - arrayList.get(i10 - 1).c)) / 4) * 4;
            if (iD8 < i3) {
                iD8 = i3;
            }
            bArr[i13 + 8] = (byte) (iD8 & 255);
            bArr[i13 + 9] = (byte) ((iD8 >> 8) & 255);
            int iD9 = ((aVar4.c + d((int) ((i5 - aVar4.c) / 2.5f))) / 4) * 4;
            if (iD9 > i5) {
                iD9 = i5;
            }
            bArr[i13 + 10] = (byte) (iD9 & 255);
            bArr[i13 + 11] = (byte) ((iD9 >> 8) & 255);
            i10++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:106:0x0179  */
    /* JADX WARN: Code duplicated, block: B:108:0x0188  */
    /* JADX WARN: Code duplicated, block: B:110:0x0197 A[LOOP:2: B:109:0x0195->B:110:0x0197, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:114:0x01b9 A[LOOP:3: B:112:0x01b3->B:114:0x01b9, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:117:0x01d7  */
    /* JADX WARN: Code duplicated, block: B:128:0x01ec  */
    /* JADX WARN: Code duplicated, block: B:130:0x01f0  */
    /* JADX WARN: Code duplicated, block: B:132:0x01f4  */
    /* JADX WARN: Code duplicated, block: B:133:0x01f7  */
    /* JADX WARN: Code duplicated, block: B:135:0x01fb  */
    /* JADX WARN: Code duplicated, block: B:136:0x01fe  */
    /* JADX WARN: Code duplicated, block: B:138:0x0202  */
    /* JADX WARN: Code duplicated, block: B:140:0x0206  */
    /* JADX WARN: Code duplicated, block: B:141:0x0209  */
    /* JADX WARN: Code duplicated, block: B:143:0x020f  */
    /* JADX WARN: Code duplicated, block: B:145:0x0215  */
    /* JADX WARN: Code duplicated, block: B:146:0x0219  */
    /* JADX WARN: Code duplicated, block: B:149:0x021e  */
    /* JADX WARN: Code duplicated, block: B:151:0x0224  */
    /* JADX WARN: Code duplicated, block: B:154:0x0229  */
    /* JADX WARN: Code duplicated, block: B:156:0x022d  */
    /* JADX WARN: Code duplicated, block: B:159:0x0232  */
    /* JADX WARN: Code duplicated, block: B:161:0x0238  */
    /* JADX WARN: Code duplicated, block: B:163:0x023e  */
    /* JADX WARN: Code duplicated, block: B:164:0x0240  */
    /* JADX WARN: Code duplicated, block: B:166:0x0246  */
    /* JADX WARN: Code duplicated, block: B:168:0x024a  */
    /* JADX WARN: Code duplicated, block: B:169:0x024d  */
    /* JADX WARN: Code duplicated, block: B:171:0x0251  */
    /* JADX WARN: Code duplicated, block: B:174:0x0256  */
    /* JADX WARN: Code duplicated, block: B:176:0x025a  */
    /* JADX WARN: Code duplicated, block: B:178:0x0260  */
    /* JADX WARN: Code duplicated, block: B:179:0x0263  */
    /* JADX WARN: Code duplicated, block: B:188:0x027c  */
    /* JADX WARN: Code duplicated, block: B:214:0x02f2  */
    /* JADX WARN: Code duplicated, block: B:217:0x02f9  */
    /* JADX WARN: Code duplicated, block: B:222:0x0304  */
    /* JADX WARN: Code duplicated, block: B:270:0x0379  */
    /* JADX WARN: Code duplicated, block: B:290:0x03b3  */
    /* JADX WARN: Code duplicated, block: B:298:0x03c8  */
    /* JADX WARN: Code duplicated, block: B:306:0x03dc  */
    /* JADX WARN: Code duplicated, block: B:33:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:342:0x0434  */
    /* JADX WARN: Code duplicated, block: B:35:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:46:0x00ed  */
    /* JADX WARN: Code duplicated, block: B:48:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:50:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:51:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:53:0x00fc  */
    /* JADX WARN: Code duplicated, block: B:54:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:56:0x0103  */
    /* JADX WARN: Code duplicated, block: B:58:0x0107  */
    /* JADX WARN: Code duplicated, block: B:59:0x010a  */
    /* JADX WARN: Code duplicated, block: B:61:0x010e  */
    /* JADX WARN: Code duplicated, block: B:63:0x0114  */
    /* JADX WARN: Code duplicated, block: B:64:0x0118  */
    /* JADX WARN: Code duplicated, block: B:67:0x011d  */
    /* JADX WARN: Code duplicated, block: B:69:0x0123  */
    /* JADX WARN: Code duplicated, block: B:72:0x0128  */
    /* JADX WARN: Code duplicated, block: B:74:0x012c  */
    /* JADX WARN: Code duplicated, block: B:77:0x0131  */
    /* JADX WARN: Code duplicated, block: B:79:0x0137  */
    /* JADX WARN: Code duplicated, block: B:81:0x013d  */
    /* JADX WARN: Code duplicated, block: B:82:0x013f  */
    /* JADX WARN: Code duplicated, block: B:84:0x0145  */
    /* JADX WARN: Code duplicated, block: B:86:0x0149  */
    /* JADX WARN: Code duplicated, block: B:87:0x014c  */
    /* JADX WARN: Code duplicated, block: B:89:0x0150  */
    /* JADX WARN: Code duplicated, block: B:92:0x0155  */
    /* JADX WARN: Code duplicated, block: B:94:0x0159  */
    /* JADX WARN: Code duplicated, block: B:96:0x015f  */
    /* JADX WARN: Code duplicated, block: B:97:0x0162  */
    private void d() throws Throwable {
        int i;
        int i2;
        int i3;
        int i4;
        ArrayList<a> arrayList;
        ArrayList<a> arrayList2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        ArrayList<a> arrayList3 = new ArrayList<>();
        for (int i10 = 0; i10 < 12; i10++) {
            if (this.j[i10] != 0) {
                arrayList3.add(new a(this.j[i10], this.k[i10], this.l[i10]));
            }
        }
        if (arrayList3.size() == 0) {
            byte[] bArr = {5, 0};
            b(bArr);
            a(bArr, 266, 146);
            return;
        }
        byte[] bArr2 = new byte[242];
        if (c(bArr2)) {
            this.n = ((bArr2[4] & 255) << 8) | (bArr2[3] & 255);
            this.o = (bArr2[5] & 255) | ((bArr2[6] & 255) << 8);
        }
        byte[] bArr3 = new byte[(arrayList3.size() * 12) + 2];
        bArr3[0] = 5;
        bArr3[1] = (byte) arrayList3.size();
        if (this.e == 1) {
            i = b;
            i2 = c;
        } else {
            i = 0;
            i2 = 0;
        }
        int i11 = this.p;
        int i12 = 1024;
        if (i11 != 12) {
            switch (i11) {
                case 1:
                case 2:
                    if (this.e == 1) {
                        if (i == this.n || i2 == this.o || i == this.o || i2 == this.n || this.o != 600) {
                            i3 = i2;
                        } else {
                            i3 = 600;
                        }
                    } else if (this.n == 800) {
                        if (this.o >= 480) {
                            i3 = 480;
                        } else {
                            i3 = i2;
                        }
                    } else if (this.n == 1024) {
                        if (this.o >= 768) {
                            i3 = 768;
                        } else if (this.o >= 600) {
                            i3 = 600;
                        } else {
                            i3 = i2;
                        }
                    } else if (this.n == 1280) {
                        if (this.o >= 800) {
                            i3 = 800;
                        } else if (this.o >= 720) {
                            i3 = 720;
                        } else if (this.o >= 480) {
                            i3 = 480;
                        } else {
                            i3 = i2;
                        }
                    } else if (this.n == 1920) {
                        if (this.o >= 1080) {
                            i3 = 1080;
                        } else if (this.o >= 720) {
                            i3 = 720;
                        } else {
                            i3 = i2;
                        }
                    } else if (this.n == 1080) {
                        if (this.o >= 1920) {
                            i3 = 1920;
                        } else {
                            i3 = i2;
                        }
                    } else if (this.n == 800) {
                        if (this.o >= 1280) {
                            i3 = 1280;
                        } else {
                            i3 = i2;
                        }
                    } else if (this.n == 768) {
                        if (this.o >= 1024) {
                            i3 = 1024;
                        } else {
                            i3 = i2;
                        }
                    } else if (this.n < 1600 || this.n >= 1920 || this.o < 720 || this.o >= 800) {
                        i3 = i2;
                    } else {
                        i3 = 720;
                    }
                    a(arrayList3, bArr3, 2, 0, i3, this.n, this.o);
                    b(bArr3);
                    a(bArr3, 266, 146);
                    break;
                case 3:
                case 4:
                case 7:
                    if (this.e == 1) {
                        if (i == this.n || i2 == this.o || i == this.o || i2 == this.n || this.o != 600) {
                            i12 = i;
                        } else {
                            i2 = 600;
                        }
                        if (this.n < i12 && i12 > i2) {
                            b(arrayList3, bArr3, 2, 0, i12, this.n, this.o);
                        } else {
                            b(arrayList3, bArr3, 2, i12, 0, this.n, this.o);
                        }
                    } else {
                        if (this.o == 480) {
                            if (this.n >= 1280) {
                                i4 = 1280;
                            } else if (this.n >= 800) {
                                i4 = 800;
                            } else {
                                i4 = i;
                            }
                        } else if (this.o == 600) {
                            if (this.n >= 1024) {
                                i4 = 1024;
                            } else {
                                i4 = i;
                            }
                        } else if (this.o == 720) {
                            if (this.n >= 1920) {
                                i4 = 1920;
                            } else if (this.n >= 1280) {
                                i4 = 1280;
                            } else {
                                i4 = i;
                            }
                        } else {
                            if (this.o == 768) {
                                if (this.n >= 1024) {
                                    i4 = 1024;
                                }
                            } else if (this.o == 800) {
                                if (this.n >= 1280) {
                                    i4 = 1280;
                                }
                            } else if (this.o == 1080) {
                                i4 = 1920;
                                if (this.n < 1920) {
                                }
                            } else if (this.o == 1920) {
                                if (this.n >= 1080) {
                                    i4 = 1080;
                                }
                            } else if (this.o == 1280) {
                                if (this.n >= 800) {
                                    i4 = 800;
                                }
                            } else if (this.o == 1024) {
                                if (this.n >= 768) {
                                    i4 = 768;
                                }
                            } else if (this.o >= 720 && this.o < 800 && this.n >= 1600 && this.n < 1920) {
                                i4 = 1600;
                            }
                            i4 = i;
                        }
                        b(arrayList3, bArr3, 2, i4, 0, this.n, this.o);
                    }
                    b(bArr3);
                    a(bArr3, 266, 146);
                    break;
                case 5:
                case 6:
                    Collections.sort(arrayList3, new Comparator<a>() { // from class: com.dofun.carsetting.activity.TouchActivity.10
                        @Override // java.util.Comparator
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public int compare(a aVar, a aVar2) {
                            return aVar.b - aVar2.b;
                        }
                    });
                    int i13 = 0;
                    while (i13 < arrayList3.size() - 1) {
                        int i14 = i13 + 1;
                        if (arrayList3.get(i14).b - arrayList3.get(i13).b > 15) {
                            if (i13 == arrayList3.size() - 1) {
                                if (this.e == 1) {
                                    if (i != this.n || i2 == this.o || i == this.o || i2 == this.n || this.o != 600) {
                                        i9 = i;
                                    } else {
                                        i9 = 1024;
                                    }
                                } else if (this.o == 480) {
                                    if (this.n >= 1280) {
                                        i9 = 1280;
                                    } else if (this.n >= 800) {
                                        i9 = 800;
                                    } else {
                                        i9 = i;
                                    }
                                } else if (this.o == 600) {
                                    if (this.n >= 1024) {
                                        i9 = 1024;
                                    } else {
                                        i9 = i;
                                    }
                                } else if (this.o == 720) {
                                    if (this.n >= 1920) {
                                        i9 = 1920;
                                    } else if (this.n >= 1280) {
                                        i9 = 1280;
                                    } else {
                                        i9 = i;
                                    }
                                } else {
                                    if (this.o == 768) {
                                        if (this.n >= 1024) {
                                            i9 = 1024;
                                        }
                                    } else if (this.o == 800) {
                                        if (this.n >= 1280) {
                                            i9 = 1280;
                                        }
                                    } else if (this.o == 1080) {
                                        if (this.n >= 1920) {
                                            i9 = 1920;
                                        }
                                    } else if (this.o == 1920) {
                                        if (this.n >= 1080) {
                                            i9 = 1080;
                                        }
                                    } else if (this.o == 1280) {
                                        if (this.n >= 800) {
                                            i9 = 800;
                                        }
                                    } else if (this.o == 1024) {
                                        if (this.n >= 768) {
                                            i9 = 768;
                                        }
                                    } else if (this.o < 720 && this.o < 800 && this.n >= 1600 && this.n < 1920) {
                                        i9 = 1600;
                                    }
                                    i9 = i;
                                }
                                b(arrayList3, bArr3, 2, i9, 0, this.n, this.o);
                            } else {
                                arrayList = new ArrayList<>();
                                arrayList2 = new ArrayList<>();
                                i5 = 0;
                                i6 = 0;
                                while (i5 <= i13) {
                                    a aVar = arrayList3.get(i5);
                                    arrayList.add(aVar);
                                    i6 += aVar.b;
                                    i5++;
                                }
                                int size = i6 / arrayList.size();
                                i7 = 0;
                                while (i5 < arrayList3.size()) {
                                    a aVar2 = arrayList3.get(i5);
                                    arrayList2.add(aVar2);
                                    i7 += aVar2.b;
                                    i5++;
                                }
                                int size2 = (size + (i7 / arrayList2.size())) / 2;
                                if (this.e == 1) {
                                    if (i != this.n || i2 == this.o || i == this.o || i2 == this.n || this.o != 600) {
                                        i8 = i;
                                    } else {
                                        i8 = 1024;
                                    }
                                } else if (this.o == 480) {
                                    if (this.n >= 1280) {
                                        i8 = 1280;
                                    } else if (this.n >= 800) {
                                        i8 = 800;
                                    } else {
                                        i8 = i;
                                    }
                                } else if (this.o == 600) {
                                    if (this.n >= 1024) {
                                        i8 = 1024;
                                    } else {
                                        i8 = i;
                                    }
                                } else if (this.o == 720) {
                                    if (this.n >= 1920) {
                                        i8 = 1920;
                                    } else if (this.n >= 1280) {
                                        i8 = 1280;
                                    } else {
                                        i8 = i;
                                    }
                                } else {
                                    if (this.o == 768) {
                                        if (this.n >= 1024) {
                                            i8 = 1024;
                                        }
                                    } else if (this.o == 800) {
                                        if (this.n >= 1280) {
                                            i8 = 1280;
                                        }
                                    } else if (this.o == 1080) {
                                        if (this.n >= 1920) {
                                            i8 = 1920;
                                        }
                                    } else if (this.o == 1920) {
                                        if (this.n >= 1080) {
                                            i8 = 1080;
                                        }
                                    } else if (this.o == 1280) {
                                        if (this.n >= 800) {
                                            i8 = 800;
                                        }
                                    } else if (this.o == 1024) {
                                        if (this.n >= 768) {
                                            i8 = 768;
                                        }
                                    } else if (this.o < 720 && this.o < 800 && this.n >= 1600 && this.n < 1920) {
                                        i8 = 1600;
                                    }
                                    i8 = i;
                                }
                                b(arrayList, bArr3, 2, i8, 0, size2, this.o);
                                b(arrayList2, bArr3, (arrayList.size() * 12) + 2, size2, 0, this.n, this.o);
                            }
                            b(bArr3);
                            a(bArr3, 266, 146);
                        } else {
                            i13 = i14;
                        }
                        break;
                    }
                    if (i13 == arrayList3.size() - 1) {
                        if (this.e == 1) {
                            if (i != this.n) {
                            }
                            i9 = i;
                        } else if (this.o == 480) {
                            if (this.n >= 1280) {
                                i9 = 1280;
                            } else if (this.n >= 800) {
                                i9 = 800;
                            } else {
                                i9 = i;
                            }
                        } else if (this.o == 600) {
                            if (this.n >= 1024) {
                                i9 = 1024;
                            } else {
                                i9 = i;
                            }
                        } else if (this.o == 720) {
                            if (this.n >= 1920) {
                                i9 = 1920;
                            } else if (this.n >= 1280) {
                                i9 = 1280;
                            } else {
                                i9 = i;
                            }
                        } else {
                            if (this.o == 768) {
                                if (this.n >= 1024) {
                                    i9 = 1024;
                                }
                            } else if (this.o == 800) {
                                if (this.n >= 1280) {
                                    i9 = 1280;
                                }
                            } else if (this.o == 1080) {
                                if (this.n >= 1920) {
                                    i9 = 1920;
                                }
                            } else if (this.o == 1920) {
                                if (this.n >= 1080) {
                                    i9 = 1080;
                                }
                            } else if (this.o == 1280) {
                                if (this.n >= 800) {
                                    i9 = 800;
                                }
                            } else if (this.o == 1024) {
                                if (this.n >= 768) {
                                    i9 = 768;
                                }
                            } else if (this.o < 720) {
                            }
                            i9 = i;
                        }
                        b(arrayList3, bArr3, 2, i9, 0, this.n, this.o);
                    } else {
                        arrayList = new ArrayList<>();
                        arrayList2 = new ArrayList<>();
                        i5 = 0;
                        i6 = 0;
                        while (i5 <= i13) {
                            a aVar3 = arrayList3.get(i5);
                            arrayList.add(aVar3);
                            i6 += aVar3.b;
                            i5++;
                        }
                        int size3 = i6 / arrayList.size();
                        i7 = 0;
                        while (i5 < arrayList3.size()) {
                            a aVar4 = arrayList3.get(i5);
                            arrayList2.add(aVar4);
                            i7 += aVar4.b;
                            i5++;
                        }
                        int size4 = (size3 + (i7 / arrayList2.size())) / 2;
                        if (this.e == 1) {
                            if (i != this.n) {
                            }
                            i8 = i;
                        } else if (this.o == 480) {
                            if (this.n >= 1280) {
                                i8 = 1280;
                            } else if (this.n >= 800) {
                                i8 = 800;
                            } else {
                                i8 = i;
                            }
                        } else if (this.o == 600) {
                            if (this.n >= 1024) {
                                i8 = 1024;
                            } else {
                                i8 = i;
                            }
                        } else if (this.o == 720) {
                            if (this.n >= 1920) {
                                i8 = 1920;
                            } else if (this.n >= 1280) {
                                i8 = 1280;
                            } else {
                                i8 = i;
                            }
                        } else {
                            if (this.o == 768) {
                                if (this.n >= 1024) {
                                    i8 = 1024;
                                }
                            } else if (this.o == 800) {
                                if (this.n >= 1280) {
                                    i8 = 1280;
                                }
                            } else if (this.o == 1080) {
                                if (this.n >= 1920) {
                                    i8 = 1920;
                                }
                            } else if (this.o == 1920) {
                                if (this.n >= 1080) {
                                    i8 = 1080;
                                }
                            } else if (this.o == 1280) {
                                if (this.n >= 800) {
                                    i8 = 800;
                                }
                            } else if (this.o == 1024) {
                                if (this.n >= 768) {
                                    i8 = 768;
                                }
                            } else if (this.o < 720) {
                            }
                            i8 = i;
                        }
                        b(arrayList, bArr3, 2, i8, 0, size4, this.o);
                        b(arrayList2, bArr3, (arrayList.size() * 12) + 2, size4, 0, this.n, this.o);
                    }
                    b(bArr3);
                    a(bArr3, 266, 146);
                    break;
            }
        }
        if (this.e == 1) {
            b(arrayList3, bArr3, 2, (i == this.n || i2 == this.o || i == this.o || i2 == this.n || this.o != 600) ? i : 1024, 0, this.n, this.o);
            b(bArr3);
            a(bArr3, 266, 146);
        }
    }

    private int e(int i) {
        for (int i2 = 0; i2 < 12; i2++) {
            if (i == m[i2]) {
                return i2;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        for (int i = 0; i < 12; i++) {
            this.j[i] = 0;
            this.k[i] = 0;
            this.l[i] = 0;
        }
        c();
    }

    public void onClick(View view) throws Throwable {
        int id = view.getId();
        if (id == R.id.key_start) {
            if (this.i) {
                a(R.id.key_start, R.string.key_start, 0);
                if (this.g) {
                    b();
                } else {
                    d();
                }
                this.i = false;
                return;
            }
            a(R.id.key_start, R.string.key_end, 2);
            this.i = true;
            return;
        }
        if (!this.i) {
            if (id == R.id.key_cfg) {
                new AlertDialog.Builder(this).setTitle(R.string.key_import).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.TouchActivity.13
                    /* JADX WARN: Type inference failed for: r13v8, types: [com.dofun.carsetting.activity.TouchActivity$13$3] */
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FileInputStream fileInputStream;
                        final byte[] bArr = new byte[242];
                        if (TouchActivity.this.c(bArr)) {
                            byte b2 = 0;
                            bArr[0] = 4;
                            bArr[187] = 1;
                            try {
                                fileInputStream = new FileInputStream("/usb/touch.cfg");
                            } catch (Exception unused) {
                                fileInputStream = null;
                            }
                            if (fileInputStream == null) {
                                try {
                                    fileInputStream = new FileInputStream("/usb1/touch.cfg");
                                } catch (Exception unused2) {
                                }
                            }
                            if (fileInputStream == null) {
                                try {
                                    fileInputStream = new FileInputStream("/usb2/touch.cfg");
                                } catch (Exception unused3) {
                                }
                            }
                            if (fileInputStream == null) {
                                try {
                                    fileInputStream = new FileInputStream("/extsd/touch.cfg");
                                } catch (Exception unused4) {
                                }
                            }
                            if (fileInputStream == null) {
                                try {
                                    fileInputStream = new FileInputStream("/extsd1/touch.cfg");
                                } catch (Exception unused5) {
                                }
                            }
                            if (fileInputStream == null) {
                                try {
                                    fileInputStream = new FileInputStream("/sdcard/touch.cfg");
                                } catch (Exception unused6) {
                                }
                            }
                            FileInputStream fileInputStream2 = fileInputStream;
                            try {
                                if (fileInputStream2 != null) {
                                    try {
                                        final byte[] bArr2 = new byte[242];
                                        bArr2[0] = 4;
                                        bArr2[1] = -70;
                                        byte[] bArr3 = new byte[2048];
                                        String[] strArrSplit = new String(bArr3, 0, fileInputStream2.read(bArr3)).split(",");
                                        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                                            int iIndexOf = strArrSplit[i2].indexOf("0x");
                                            bArr2[i2 + 2] = (byte) Integer.parseInt(strArrSplit[i2].substring(iIndexOf + 2, iIndexOf + 4), 16);
                                        }
                                        if ((bArr2[8] & 3) != 0) {
                                            int i3 = 2;
                                            bArr2[2] = 0;
                                            bArr2[8] = (byte) (bArr2[8] & (-4));
                                            while (i3 < 186) {
                                                b2 = (byte) (b2 + bArr2[i3]);
                                                i3++;
                                            }
                                            bArr2[i3] = (byte) ((~b2) + 1);
                                        }
                                        TouchActivity.this.b(bArr2);
                                        TouchActivity.this.q = new AlertDialog.Builder(TouchActivity.this).setTitle(TouchActivity.this.getString(R.string.key_sure) + "(5)").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.TouchActivity.13.1
                                            @Override // android.content.DialogInterface.OnClickListener
                                            public void onClick(DialogInterface dialogInterface2, int i4) throws Throwable {
                                                if (TouchActivity.this.r != null) {
                                                    TouchActivity.this.r.cancel();
                                                    TouchActivity.this.r = null;
                                                }
                                                TouchActivity.this.a(bArr2, 0, 242);
                                            }
                                        }).setNegativeButton(android.R.string.no, (DialogInterface.OnClickListener) null).show();
                                        TouchActivity.this.q.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.activity.TouchActivity.13.2
                                            @Override // android.content.DialogInterface.OnDismissListener
                                            public void onDismiss(DialogInterface dialogInterface2) {
                                                if (TouchActivity.this.r != null) {
                                                    TouchActivity.this.b(bArr);
                                                    TouchActivity.this.r.cancel();
                                                    TouchActivity.this.r = null;
                                                }
                                                TouchActivity.this.q = null;
                                            }
                                        });
                                        TouchActivity.this.r = new CountDownTimer(5000L, 1000L) { // from class: com.dofun.carsetting.activity.TouchActivity.13.3
                                            @Override // android.os.CountDownTimer
                                            public void onTick(long j) {
                                                if (TouchActivity.this.q != null) {
                                                    TouchActivity.this.q.setTitle(TouchActivity.this.getString(R.string.key_sure) + "(" + (j / 1000) + ")");
                                                }
                                            }

                                            @Override // android.os.CountDownTimer
                                            public void onFinish() {
                                                if (TouchActivity.this.q != null) {
                                                    TouchActivity.this.b(bArr);
                                                    TouchActivity.this.q.dismiss();
                                                    TouchActivity.this.q = null;
                                                }
                                                TouchActivity.this.r = null;
                                            }
                                        }.start();
                                    } catch (Exception unused7) {
                                    } finally {
                                        fileInputStream2.close();
                                    }
                                }
                            } catch (Exception unused8) {
                            }
                        }
                    }
                }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
                return;
            } else {
                if (id == R.id.key_pos) {
                    new AlertDialog.Builder(this).setSingleChoiceItems(getResources().getStringArray(R.array.key_pos), this.p, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.TouchActivity.14
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i) throws Throwable {
                            dialogInterface.dismiss();
                            if (i != 9) {
                                TouchActivity.this.b(i);
                                return;
                            }
                            Intent intent = new Intent();
                            intent.setClassName("com.dofun.carsetting", "com.dofun.carsetting.activity.TouchActivity");
                            intent.setFlags(268451840);
                            intent.putExtra("calibration", 1);
                            TouchActivity.this.startActivity(intent);
                        }
                    }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
                    return;
                }
                return;
            }
        }
        if (id == R.id.key_clear) {
            e();
            return;
        }
        if (this.g) {
            final TextView textView = (TextView) view;
            new AlertDialog.Builder(this).setSingleChoiceItems(getResources().getStringArray(R.array.key_all), -1, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.TouchActivity.11
                /* JADX WARN: Type inference failed for: r5v4, types: [com.dofun.carsetting.activity.TouchActivity$11$1] */
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    final int iB = f.b(i);
                    for (int i2 = 0; i2 < 8; i2++) {
                        if (iB == TouchActivity.this.j[i2]) {
                            return;
                        }
                    }
                    TouchActivity.this.a(textView, f.a(iB), 2);
                    new Thread() { // from class: com.dofun.carsetting.activity.TouchActivity.11.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            TouchActivity.this.a(iB);
                        }
                    }.start();
                }
            }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
            return;
        }
        final int iE = e(id);
        if (this.j[iE] == 0) {
            final TextView textView2 = (TextView) view;
            new AlertDialog.Builder(this).setSingleChoiceItems(getResources().getStringArray(R.array.key_all), -1, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.TouchActivity.12
                /* JADX WARN: Type inference failed for: r5v4, types: [com.dofun.carsetting.activity.TouchActivity$12$1] */
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    final int iB = f.b(i);
                    for (int i2 = 0; i2 < 12; i2++) {
                        if (iB == TouchActivity.this.j[i2]) {
                            return;
                        }
                    }
                    TouchActivity.this.a(textView2, f.a(iB), 2);
                    new Thread() { // from class: com.dofun.carsetting.activity.TouchActivity.12.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            TouchActivity.this.a(iB, iE);
                        }
                    }.start();
                }
            }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
        }
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        if (this.i) {
            return true;
        }
        int id = view.getId();
        if (id == R.id.key_clear) {
            new AlertDialog.Builder(this).setSingleChoiceItems(getResources().getStringArray(R.array.key_clear), -1, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.TouchActivity.3
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) throws Throwable {
                    dialogInterface.dismiss();
                    switch (i) {
                        case 0:
                            TouchActivity.this.a((byte[]) null, 0, 412);
                            TouchActivity.this.p = 0;
                            TouchActivity.this.e();
                            new File("/data/tw/touch").delete();
                            break;
                        case 1:
                            TouchActivity.this.a((byte[]) null, 0, 242);
                            break;
                        case 2:
                            TouchActivity.this.a((byte[]) null, 266, 146);
                            if (!TouchActivity.this.g) {
                                TouchActivity.this.e();
                            }
                            break;
                        case 3:
                            TouchActivity.this.a((byte[]) null, 248, 18);
                            if (TouchActivity.this.g) {
                                TouchActivity.this.e();
                            }
                            break;
                    }
                }
            }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
            return true;
        }
        if (id != R.id.key_start) {
            return true;
        }
        new AlertDialog.Builder(this).setSingleChoiceItems(getResources().getStringArray(R.array.key_export), -1, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.TouchActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                byte[] bArr = new byte[412];
                TouchActivity.this.d(bArr);
                u uVarA = u.a();
                if (uVarA.write(65295, 0, i, bArr) == 0) {
                    String str = "";
                    switch (i) {
                        case 0:
                            str = "USB";
                            break;
                        case 1:
                            str = "USB1";
                            break;
                        case 2:
                            str = "USB2";
                            break;
                        case 3:
                            str = "extSD";
                            break;
                        case 4:
                            str = "extSD1";
                            break;
                        case 5:
                            str = "SDCARD";
                            break;
                    }
                    Toast.makeText(TouchActivity.this.getApplicationContext(), TouchActivity.this.getString(R.string.key_complete) + str, 0).show();
                } else {
                    Toast.makeText(TouchActivity.this.getApplicationContext(), R.string.cfg_export_fail, 0).show();
                }
                uVarA.close();
            }
        }).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
        return true;
    }

    private boolean a(String str, int i, byte[] bArr) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            if (i != 0) {
                try {
                    try {
                        fileInputStream.skip(i);
                    } catch (Exception unused) {
                        fileInputStream.close();
                        return false;
                    }
                } finally {
                    fileInputStream.close();
                }
            }
            fileInputStream.read(bArr);
            return true;
        } catch (Exception unused2) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(byte[] bArr) {
        if (a("/data/tw/touch", 0, bArr) || !a("/dev/block/by-name/misc", 4096, bArr)) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(byte[] bArr, int i, int i2) throws Throwable {
        int i3;
        byte[] bArr2 = new byte[412];
        d(bArr2);
        if (bArr != null) {
            i3 = 0;
            while (i3 < bArr.length) {
                bArr2[i + i3] = bArr[i3];
                i3++;
            }
        } else {
            i3 = 0;
        }
        while (i3 < i2) {
            bArr2[i + i3] = 0;
            i3++;
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream("/data/tw/touch");
                    try {
                        fileOutputStream2.write(bArr2);
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                    } catch (Exception unused) {
                        fileOutputStream = fileOutputStream2;
                        new File("/data/tw/touch").delete();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Exception unused2) {
                }
                FileUtils.setPermissions("/data/tw/touch", 438, -1, -1);
                u uVarA = u.a();
                uVarA.write(40730, 3, 0, "dd if=/data/tw/touch of=/dev/block/by-name/misc bs=1024 seek=4");
                uVarA.close();
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused3) {
        }
    }

    private void f() {
        b(new byte[]{7, 0});
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:11:0x0014  */
    public void g() {
        int i;
        int i2 = this.p;
        if (i2 == 1) {
            i = 2;
        } else if (i2 == 3 || i2 == 5) {
            i = 1;
        } else if (i2 != 7) {
            i = 0;
        } else {
            i = 2;
        }
        b(new byte[]{7, (byte) i});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean h() throws Throwable {
        int i;
        int i2 = 4;
        int iAbs = Math.abs(this.u[4] - (this.s.getWidth_x() / 2));
        int iAbs2 = Math.abs(this.v[4] - (this.s.getHeight_x() / 2));
        Log.d(this.f52a, "x_x = " + iAbs + " y_y = " + iAbs2 + " mCView.getWidth = " + this.s.getWidth_x() + " mCView.getHeight = " + this.s.getHeight_x());
        if (iAbs < 50 && iAbs2 < 50) {
            if (this.u[0] > this.u[4] && this.v[0] > this.v[4]) {
                i2 = 8;
                Log.d(this.f52a, "RX_RY");
            } else if (this.u[0] > this.u[4] && this.v[0] < this.v[4]) {
                int i3 = SystemProperties.getInt("ro.sf.swrotation", 0);
                int i4 = SystemProperties.getInt("persist.sf.forcerotation", 2);
                if ((i3 == 90 || i3 == 270) && i4 == 1) {
                    i2 = this.p == 7 ? 7 : 1;
                } else {
                    i2 = this.p == 5 ? 5 : 3;
                }
                Log.d(this.f52a, "R_X");
            } else if (this.u[0] < this.u[4] && this.v[0] > this.v[4]) {
                int i5 = SystemProperties.getInt("ro.sf.swrotation", 0);
                int i6 = SystemProperties.getInt("persist.sf.forcerotation", 2);
                if ((i5 == 90 || i5 == 270) && i6 == 1) {
                    i2 = this.p == 5 ? 5 : 3;
                } else {
                    i2 = this.p == 7 ? 7 : 1;
                }
                Log.d(this.f52a, "R_Y");
            } else if (this.u[0] < this.u[4] && this.v[0] < this.v[4]) {
                if (this.p == 2) {
                    i2 = 2;
                } else if (this.p != 4) {
                    i2 = 0;
                }
            } else {
                Log.d(this.f52a, "checkPos BUGBUG");
                return false;
            }
            Log.d(this.f52a, "pos=" + i2 + " position=" + this.p);
            b(i2);
            return true;
        }
        if (this.u[0] > this.u[4] && this.v[0] > this.v[4]) {
            Log.d(this.f52a, "X_Y RX_RY");
            i = 12;
        } else if (this.v[0] > this.v[4] && this.u[0] < this.u[4]) {
            Log.d(this.f52a, "X_Y R_Y");
            i = 11;
        } else if (this.v[0] < this.v[4] && this.u[0] > this.u[4]) {
            Log.d(this.f52a, "X_Y R_X");
            i = 10;
        } else if (this.u[0] < this.u[4] && this.v[0] < this.v[4]) {
            Log.d(this.f52a, "X_Y X_Y");
            i = 9;
        } else {
            Log.d(this.f52a, "checkPos BUGBUG");
            return false;
        }
        Log.d(this.f52a, "x_y pos=" + i + " position=" + this.p);
        b(i);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        this.w = new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.calibration_remind)).setPositiveButton(getResources().getString(R.string.key_enter), new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.TouchActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                TouchActivity.this.s.setVisibility(8);
                TouchActivity.this.h = true;
                TouchActivity.this.j();
            }
        }).setNegativeButton(getResources().getString(R.string.dlg_cancel), new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.activity.TouchActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                TouchActivity.this.finish();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.activity.TouchActivity.4
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                if (!TouchActivity.this.h) {
                    TouchActivity.this.finish();
                }
                TouchActivity.this.h = false;
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        this.x = new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.touch_selection_reminder)).setPositiveButton(getResources().getString(R.string.key_enter), (DialogInterface.OnClickListener) null).show();
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }
}
