package c.b.a.h;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.tw.carinfoservice.R;
import com.tw.carinfoservice.activity.MyApplication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class b extends c.b.a.h.a {
    public static boolean e0;
    public static b f0;
    public static Context g0;
    public int A;
    public int B;
    public int C;
    public int D;
    public int E;
    public int F;
    public boolean G;
    public boolean H;
    public int I;
    public boolean J;
    public c.b.a.k.f K;
    public int L;
    public c.b.b.b M;
    public byte[] N;
    public Handler O;
    public BroadcastReceiver P;
    public c.b.a.k.c Q;
    public ArrayList<e> R;
    public c.b.c.b S;
    public f T;
    public boolean U;
    public ServiceConnection V;
    public c.b.a.e.b W;
    public String X;
    public String Y;
    public int Z;
    public int a0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final c.b.a.l.b f412b;
    public boolean b0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public ArrayList<c.b.a.e.a> f413c;
    public boolean c0;
    public int d;
    public h d0;
    public int e;
    public byte[] f;
    public int g;
    public int h;
    public boolean i;
    public boolean j;
    public int k;
    public int l;
    public int m;
    public float n;
    public float o;
    public String p;
    public int q;
    public int r;
    public int s;
    public int t;
    public int u;
    public int v;
    public int w;
    public int x;
    public int y;
    public int z;

    public class a extends Handler {
        public a() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            b bVar;
            String str;
            b bVar2;
            String string;
            Object obj;
            super.handleMessage(message);
            if (c.b.a.g.b.a().f408b == null) {
            }
            try {
                int i = message.what;
                try {
                    switch (i) {
                        case 266:
                            if (message.arg1 == 0) {
                                String[] strArrSplit = ((String) message.obj).split("-");
                                if (c.b.a.l.b.f450c == -1) {
                                    try {
                                        String str2 = strArrSplit[1];
                                        String str3 = strArrSplit[2];
                                        c.b.a.l.b.e = Integer.parseInt(str2.substring(0, str2.length()), 16);
                                        c.b.a.l.b.f450c = Integer.parseInt(str3.substring(0, 2), 16);
                                        if (str3.length() <= 2) {
                                            c.b.a.l.b.g = true;
                                            c.b.a.l.b.d = 0;
                                        } else {
                                            c.b.a.l.b.d = Integer.parseInt(str3.substring(2, 3), 16);
                                        }
                                        c.b.a.l.b.f = strArrSplit.length == 6 ? strArrSplit[5] : " ";
                                        break;
                                    } catch (Exception unused) {
                                    }
                                    if (c.b.a.l.b.f450c == -1) {
                                        c.b.a.l.b.f450c = 0;
                                    }
                                }
                                b bVar3 = b.this;
                                bVar3.C0(bVar3.f413c);
                            }
                            break;
                        case 267:
                            byte[] bArr = (byte[]) message.obj;
                            if (bArr[1] == 0) {
                                b.this.j = false;
                            } else {
                                b.this.j = true;
                            }
                            try {
                                b bVar4 = b.this;
                                bVar4.D0((byte[]) message.obj, bVar4.f413c);
                                break;
                            } catch (Exception unused2) {
                            }
                            try {
                                if (bArr.length > 12) {
                                    c.b.a.l.b.b(bArr[12], 7);
                                    boolean z = b.e0;
                                    if (bArr.length > 20) {
                                        b.E(b.this, bArr[20] & 255);
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("LS123", Log.getStackTraceString(e));
                                return;
                            }
                            break;
                        case 514:
                            if (SystemProperties.get("car.infoservcie.uart").equals("true")) {
                                int i2 = message.arg1;
                                if (i2 == 5 || i2 == 9) {
                                    b.g0.unbindService(b.this.V);
                                    b.v(b.this, null);
                                    b.this.U = false;
                                } else if (i2 == 6 && !b.this.U) {
                                    b.this.U = true;
                                    Settings.System.putInt(b.g0.getContentResolver(), "Right_View", 0);
                                    b.this.f412b.write(40730, 1, 0, "sync");
                                    if (SystemProperties.get("car.infoservcie.uart").equals("true")) {
                                        b.v(b.this, null);
                                        b.this.O.sendEmptyMessageDelayed(65298, 5000L);
                                    }
                                }
                            }
                            if (message.arg1 == 6 && Settings.System.getInt(b.this.f411a.getContentResolver(), "INFO_KILL", 0) == 1) {
                                Settings.System.putInt(b.this.f411a.getContentResolver(), "INFO_KILL", 0);
                                Iterator it = b.this.R.iterator();
                                while (it.hasNext()) {
                                    ((e) it.next()).a();
                                }
                            } else if (message.arg1 == 1) {
                                Settings.System.putInt(b.this.f411a.getContentResolver(), "INFO_KILL", 1);
                            }
                            int i3 = message.arg1;
                            if (i3 == 1 || i3 == 5 || i3 == 6 || i3 == 8 || i3 == 9) {
                                boolean unused3 = b.e0 = false;
                            }
                            break;
                        case 516:
                            int i4 = b.this.k;
                            int i5 = message.arg1;
                            if (i4 != i5) {
                                b.this.k = i5;
                                bVar = b.this;
                                str = "SendHeadlight";
                                bVar.a(str, -1);
                            }
                            break;
                        case 517:
                            if (b.this.j) {
                                int i6 = b.this.l;
                                int i7 = message.arg1;
                                if (i6 != i7) {
                                    b.this.l = i7;
                                    bVar = b.this;
                                    str = "SendHandbrake";
                                    bVar.a(str, -1);
                                }
                            }
                            break;
                        case 524:
                            Log.e("LS123", "handleMessage: " + message.arg1);
                            int i8 = b.this.m;
                            int i9 = message.arg1;
                            if (i8 != i9) {
                                b.this.m = i9;
                                b.this.a("", 8);
                                b.this.a("", 9);
                                b.this.a("", 15);
                                b.this.a("Turn", -1);
                            }
                            break;
                        case 1286:
                        case 34054:
                            if (i == 1286) {
                                b.this.H = true;
                            }
                            if (message.what != 34054 || !b.this.H) {
                                byte[] bArr2 = (byte[]) message.obj;
                                b.this.E0(a.b.e.b.a.p((bArr2[1] << 8) | (bArr2[0] & 255), (bArr2[2] & 255) | (bArr2[3] << 8)));
                            }
                            break;
                        case 1287:
                        case 34055:
                            if (i == 1287) {
                                b.this.G = true;
                            }
                            if (message.what != 34055 || !b.this.G) {
                                b.N(b.this, message);
                            }
                            break;
                        case 1288:
                            byte[] bArr3 = (byte[]) message.obj;
                            if (-40 < bArr3[1] && bArr3[1] < 80) {
                                if (b.this.o != a.b.e.b.a.i(bArr3[1], b.this.f411a) || b.this.p != a.b.e.b.a.j(b.this.f411a)) {
                                    b bVar5 = b.this;
                                    bVar5.o = a.b.e.b.a.i(bArr3[1], bVar5.f411a);
                                    b bVar6 = b.this;
                                    bVar6.p = a.b.e.b.a.j(bVar6.f411a);
                                    b.this.a("", 13);
                                }
                                break;
                            }
                            break;
                        case 1291:
                            if (b.this.g == -1 && b.this.h == -1 && !b.this.J) {
                                b.this.u0(false);
                            }
                            int i10 = message.arg2;
                            if (i10 != 65535 && (i10 - b.this.h >= 50 || message.arg2 - b.this.h <= -50 || message.arg2 == b.this.h)) {
                                b.this.h = message.arg2;
                                b.this.a("Engine", -1);
                                b.this.a("", 11);
                            }
                            if (message.arg1 != 65535) {
                                b.this.i = true;
                                b.this.g = message.arg1;
                                bVar = b.this;
                                str = "Speed";
                                bVar.a(str, -1);
                            } else {
                                b.this.i = false;
                            }
                            break;
                        case 1292:
                            byte[] bArr4 = (byte[]) message.obj;
                            if (message.arg1 == 0 && !c.b.a.l.b.a(bArr4[0], 7)) {
                                float f = b.this.n;
                                Locale locale = Locale.US;
                                Object[] objArr = new Object[1];
                                double d = (c.b.a.l.b.d(bArr4[0], 0, 7) * 256) + (bArr4[1] & 255);
                                Double.isNaN(d);
                                Double.isNaN(d);
                                objArr[0] = Double.valueOf(d * 0.01d);
                                if (f != Float.valueOf(String.format(locale, "%.1f", objArr)).floatValue()) {
                                    b bVar7 = b.this;
                                    Object[] objArr2 = new Object[1];
                                    double d2 = (c.b.a.l.b.d(bArr4[0], 0, 7) * 256) + (bArr4[1] & 255);
                                    Double.isNaN(d2);
                                    Double.isNaN(d2);
                                    objArr2[0] = Double.valueOf(d2 * 0.01d);
                                    bVar7.n = Float.valueOf(String.format(locale, "%.1f", objArr2)).floatValue();
                                    bVar = b.this;
                                    str = "SendElectricVoltage";
                                    bVar.a(str, -1);
                                }
                                break;
                            }
                            break;
                        case 1294:
                            byte[] bArr5 = (byte[]) message.obj;
                            if (b.this.y != b.this.H0(bArr5[0], bArr5[4]) || b.this.z != b.this.H0(bArr5[1], bArr5[4]) || b.this.A != b.this.H0(bArr5[2], bArr5[4]) || b.this.B != b.this.H0(bArr5[3], bArr5[4]) || b.this.C != b.this.H0(bArr5[5], bArr5[9]) || b.this.D != b.this.H0(bArr5[6], bArr5[9]) || b.this.E != b.this.H0(bArr5[7], bArr5[9]) || b.this.F != b.this.H0(bArr5[8], bArr5[9])) {
                                b bVar8 = b.this;
                                bVar8.y = bVar8.H0(bArr5[0], bArr5[4]);
                                b bVar9 = b.this;
                                bVar9.z = bVar9.H0(bArr5[1], bArr5[4]);
                                b bVar10 = b.this;
                                bVar10.A = bVar10.H0(bArr5[2], bArr5[4]);
                                b bVar11 = b.this;
                                bVar11.B = bVar11.H0(bArr5[3], bArr5[4]);
                                b bVar12 = b.this;
                                bVar12.C = bVar12.H0(bArr5[5], bArr5[9]);
                                b bVar13 = b.this;
                                bVar13.D = bVar13.H0(bArr5[6], bArr5[9]);
                                b bVar14 = b.this;
                                bVar14.E = bVar14.H0(bArr5[7], bArr5[9]);
                                b bVar15 = b.this;
                                bVar15.F = bVar15.H0(bArr5[8], bArr5[9]);
                                bVar = b.this;
                                str = "Radar";
                                bVar.a(str, -1);
                            }
                            break;
                        case 1298:
                            b.this.N = (byte[]) message.obj;
                            break;
                        case 34074:
                            break;
                        case 40704:
                            int i11 = message.arg1;
                            if (i11 == 3) {
                                Object obj2 = message.obj;
                                if (obj2 != null) {
                                    if (TextUtils.isEmpty((CharSequence) obj2)) {
                                        bVar2 = b.this;
                                        string = bVar2.f411a.getResources().getString(R.string.unknown);
                                    } else {
                                        bVar2 = b.this;
                                        string = (String) message.obj;
                                    }
                                    bVar2.X = string;
                                    b.this.W.e(b.this.X);
                                    b bVar16 = b.this;
                                    if (TextUtils.equals(bVar16.Y, bVar16.X)) {
                                        b.this.b0 = false;
                                    } else {
                                        b bVar17 = b.this;
                                        bVar17.Y = bVar17.X;
                                        bVar17.b0 = true;
                                    }
                                    b.this.W.f(b.this.b0);
                                    if ((message.arg2 & 128) == 128) {
                                        b.this.Z = 1;
                                    } else {
                                        b.this.Z = 0;
                                    }
                                    b bVar18 = b.this;
                                    int i12 = bVar18.a0;
                                    int i13 = bVar18.Z;
                                    if (i12 != i13) {
                                        bVar18.c0 = true;
                                        bVar18.a0 = i13;
                                    } else {
                                        bVar18.c0 = false;
                                    }
                                    b.h0(bVar18, bVar18.W.a().getBytes("UTF-16LE"), 1);
                                    b.this.W.c(b.this.c0);
                                    b.this.W.b(b.this.Z);
                                    b.this.W.d(message.arg2 & 127);
                                }
                            } else if (i11 == 8 && (obj = message.obj) != null) {
                                b.h0(b.this, ((String) obj).getBytes("UTF-16LE"), 1);
                            }
                            break;
                        case 40732:
                            b.e(b.this, message.arg1);
                            break;
                        case 65283:
                            b.h(b.this);
                            break;
                        case 65289:
                            b.this.L++;
                            b.this.O.sendEmptyMessageDelayed(65289, 1000L);
                            bVar = b.this;
                            str = "TravelTime";
                            bVar.a(str, -1);
                            break;
                        case 65296:
                            if (!b.e0) {
                                b.this.f412b.write(516, 255);
                            }
                            break;
                        case 65297:
                            if (b.this.g == -1 && b.this.h == -1 && !b.this.J) {
                                b.this.u0(false);
                                break;
                            }
                            break;
                        case 65298:
                            if (b.this.S == null) {
                                b.K(b.this);
                                b.this.O.sendEmptyMessageDelayed(65298, 5000L);
                            } else {
                                b.this.O.removeMessages(65298);
                            }
                            break;
                        case 65299:
                            Intent intent = new Intent();
                            intent.setAction("LRREVERSE_REPAY");
                            b.g0.sendBroadcast(intent);
                            break;
                        case 65300:
                            if (b.this.M != null) {
                                b.this.M.e();
                            }
                            break;
                        default:
                            if (i == 1281) {
                                b.i0(b.this, message);
                            }
                            if (c.b.a.k.k.a.d != null) {
                                for (int i14 = 0; i14 < c.b.a.k.k.a.d.size(); i14++) {
                                    int iIntValue = c.b.a.k.k.a.d.get(Integer.valueOf(i14)).intValue();
                                    int i15 = message.what;
                                    if (iIntValue == i15) {
                                        b.this.d = i15;
                                        b.this.e = message.arg1;
                                        b.this.f = (byte[]) message.obj;
                                        b.this.new g().start();
                                    }
                                }
                            }
                            break;
                    }
                } catch (Exception unused4) {
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: renamed from: c.b.a.h.b$b, reason: collision with other inner class name */
    public class C0016b extends BroadcastReceiver {
        public C0016b() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("LRREVERSE_RESET".equals(intent.getAction())) {
                b.this.M.e();
                b.this.O.removeMessages(65299);
                b.this.O.sendEmptyMessageDelayed(65299, 300L);
            }
            if ("AUTONAVI_STANDARD_BROADCAST_SEND".equals(intent.getAction())) {
                try {
                    if (b.this.N == null || b.this.N.length <= 5) {
                        return;
                    }
                    if (intent.getIntExtra("EXTRA_STATE", 0) == 9) {
                        b.this.f412b.write(1298, 0, 1, new byte[]{0});
                    } else if (intent.getIntExtra("EXTRA_STATE", 0) == 8) {
                        b.this.f412b.write(1298, 0, 1, new byte[]{1});
                    }
                    c.b.a.l.b unused = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[0], 0)) {
                        b.this.f412b.write(1298, 1, 1, new byte[]{(byte) intent.getIntExtra("TYPE", 0)});
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused2 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[0], 1)) {
                        String stringExtra = intent.getStringExtra("CUR_ROAD_NAME");
                        if (stringExtra != null && stringExtra.length() > 0) {
                            b.this.f412b.write(1298, 2, c.b.a.l.a.j(stringExtra).length, c.b.a.l.a.j(stringExtra));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused3 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[0], 2)) {
                        String stringExtra2 = intent.getStringExtra("NEXT_ROAD_NAME");
                        if (stringExtra2 != null && stringExtra2.length() > 0) {
                            b.this.f412b.write(1298, 3, c.b.a.l.a.j(stringExtra2).length, c.b.a.l.a.j(stringExtra2));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused4 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[0], 3)) {
                        b.this.f412b.write(1298, 4, 1, c.b.a.l.a.i(intent.getIntExtra("SAPA_DIST", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused5 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[0], 4)) {
                        b.this.f412b.write(1298, 5, 1, new byte[]{(byte) intent.getIntExtra("SAPA_TYPE", 0)});
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused6 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[0], 5)) {
                        b.this.f412b.write(1298, 6, c.b.a.l.a.i(intent.getIntExtra("NEXT_SAPA_DIST", 0)).length, c.b.a.l.a.i(intent.getIntExtra("NEXT_SAPA_DIST", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused7 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[0], 6)) {
                        b.this.f412b.write(1298, 7, 1, new byte[]{(byte) intent.getIntExtra("NEXT_SAPA_TYPE", 0)});
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused8 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[0], 7)) {
                        String stringExtra3 = intent.getStringExtra("NEXT_SAPA_NAME");
                        if (stringExtra3 != null && stringExtra3.length() > 0) {
                            b.this.f412b.write(1298, 8, c.b.a.l.a.j(stringExtra3).length, c.b.a.l.a.j(stringExtra3));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused9 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[1], 0)) {
                        String stringExtra4 = intent.getStringExtra("NEXT_SAPA_DIST_AUTO");
                        if (stringExtra4 != null && stringExtra4.length() > 0) {
                            b.this.f412b.write(1298, 9, c.b.a.l.a.j(stringExtra4).length, c.b.a.l.a.j(stringExtra4));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused10 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[1], 1)) {
                        b.this.f412b.write(1298, 10, c.b.a.l.a.i(intent.getIntExtra("CAMERA_DIST", 0)).length, c.b.a.l.a.i(intent.getIntExtra("CAMERA_DIST", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused11 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[1], 2)) {
                        b.this.f412b.write(1298, 11, 1, new byte[]{(byte) intent.getIntExtra("CAMERA_TYPE", 0)});
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused12 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[1], 3)) {
                        b.this.f412b.write(1298, 12, c.b.a.l.a.i(intent.getIntExtra("CAMERA_SPEED", 0)).length, c.b.a.l.a.i(intent.getIntExtra("CAMERA_SPEED", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused13 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[1], 4)) {
                        b.this.f412b.write(1298, 13, c.b.a.l.a.i(intent.getIntExtra("CAMERA_INDEX", 0)).length, c.b.a.l.a.i(intent.getIntExtra("CAMERA_INDEX", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused14 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[1], 5)) {
                        b.this.f412b.write(1298, 14, 1, new byte[]{(byte) intent.getIntExtra("NEW_ICON", 0)});
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused15 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[1], 6) && intent.getIntExtra("ROUTE_REMAIN_DIS", 0) != 0) {
                        b.this.f412b.write(1298, 15, c.b.a.l.a.i(intent.getIntExtra("ROUTE_REMAIN_DIS", 0)).length, c.b.a.l.a.i(intent.getIntExtra("ROUTE_REMAIN_DIS", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused16 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[1], 7)) {
                        b.this.f412b.write(1298, 16, c.b.a.l.a.i(intent.getIntExtra("ROUTE_REMAIN_TIME", 0)).length, c.b.a.l.a.i(intent.getIntExtra("ROUTE_REMAIN_TIME", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused17 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[2], 0) && intent.getIntExtra("SEG_REMAIN_DIS", 0) != 0) {
                        b.this.f412b.write(1298, 17, c.b.a.l.a.i(intent.getIntExtra("SEG_REMAIN_DIS", 0)).length, c.b.a.l.a.i(intent.getIntExtra("SEG_REMAIN_DIS", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused18 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[2], 1)) {
                        b.this.f412b.write(1298, 18, c.b.a.l.a.i(intent.getIntExtra("SEG_REMAIN_TIME", 0)).length, c.b.a.l.a.i(intent.getIntExtra("SEG_REMAIN_TIME", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused19 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[2], 2)) {
                        b.this.f412b.write(1298, 19, c.b.a.l.a.i(intent.getIntExtra("CUR_SEG_NUM", 0)).length, c.b.a.l.a.i(intent.getIntExtra("CUR_SEG_NUM", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused20 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[2], 3)) {
                        b.this.f412b.write(1298, 20, 1, new byte[]{(byte) intent.getIntExtra("ROUNG_ABOUT_NUM", 0)});
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused21 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[2], 4)) {
                        b.this.f412b.write(1298, 21, 1, new byte[]{(byte) intent.getIntExtra("ROUND_ALL_NUM", 0)});
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused22 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[2], 5)) {
                        b.this.f412b.write(1298, 22, c.b.a.l.a.i(intent.getIntExtra("ROUTE_ALL_DIS", 0)).length, c.b.a.l.a.i(intent.getIntExtra("ROUTE_ALL_DIS", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused23 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[2], 6)) {
                        b.this.f412b.write(1298, 23, c.b.a.l.a.i(intent.getIntExtra("ROUTE_ALL_TIME", 0)).length, c.b.a.l.a.i(intent.getIntExtra("ROUTE_ALL_TIME", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused24 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[2], 7)) {
                        b.this.f412b.write(1298, 24, c.b.a.l.a.i(intent.getIntExtra("CUR_SPEED", 0)).length, c.b.a.l.a.i(intent.getIntExtra("CUR_SPEED", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused25 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[3], 0)) {
                        b.this.f412b.write(1298, 25, c.b.a.l.a.i(intent.getIntExtra("TRAFFIC_LIGHT_NUM", 0)).length, c.b.a.l.a.i(intent.getIntExtra("TRAFFIC_LIGHT_NUM", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused26 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[3], 1)) {
                        b.this.f412b.write(1298, 26, c.b.a.l.a.i(intent.getIntExtra("SAPA_NUM", 0)).length, c.b.a.l.a.i(intent.getIntExtra("SAPA_NUM", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused27 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[3], 2)) {
                        String stringExtra5 = intent.getStringExtra("SAPA_NAME");
                        if (stringExtra5 != null && stringExtra5.length() > 0) {
                            b.this.f412b.write(1298, 27, c.b.a.l.a.j(stringExtra5).length, c.b.a.l.a.j(stringExtra5));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused28 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[3], 3)) {
                        b.this.f412b.write(1298, 28, 1, new byte[]{(byte) intent.getIntExtra("ROAD_TYPE", 0)});
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused29 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[3], 4)) {
                        String stringExtra6 = intent.getStringExtra("ROUTE_REMAIN_TIME_STRING");
                        if (stringExtra6 != null && stringExtra6.length() > 0) {
                            b.this.f412b.write(1298, 29, stringExtra6.length(), c.b.a.l.a.j(stringExtra6));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused30 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[3], 5)) {
                        String stringExtra7 = intent.getStringExtra("NEXT_NEXT_ROAD_NAME");
                        if (stringExtra7 != null && stringExtra7.length() > 0) {
                            b.this.f412b.write(1298, 30, c.b.a.l.a.j(stringExtra7).length, c.b.a.l.a.j(stringExtra7));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused31 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[3], 6)) {
                        b.this.f412b.write(1298, 31, c.b.a.l.a.i(intent.getIntExtra(" NEXT_NEXT_TURN_ICON", 0)).length, c.b.a.l.a.i(intent.getIntExtra(" NEXT_NEXT_TURN_ICON", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused32 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[3], 7)) {
                        b.this.f412b.write(1298, 32, c.b.a.l.a.i(intent.getIntExtra(" NEXT_SEG_REMAIN_DIS", 0)).length, c.b.a.l.a.i(intent.getIntExtra(" NEXT_SEG_REMAIN_DIS", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused33 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[4], 0)) {
                        b.this.f412b.write(1298, 33, c.b.a.l.a.i(intent.getIntExtra(" NEXT_SEG_REMAIN_TIME", 0)).length, c.b.a.l.a.i(intent.getIntExtra(" NEXT_SEG_REMAIN_TIME", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused34 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[4], 1)) {
                        String stringExtra8 = intent.getStringExtra("ROUTE_REMAIN_DIS_AUTO");
                        if (stringExtra8 != null && stringExtra8.length() > 0) {
                            b.this.f412b.write(1298, 34, c.b.a.l.a.j(stringExtra8).length, c.b.a.l.a.j(stringExtra8));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused35 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[4], 2)) {
                        String stringExtra9 = intent.getStringExtra("ROUTE_REMAIN_TIME_AUTO");
                        if (stringExtra9 != null && stringExtra9.length() > 0) {
                            b.this.f412b.write(1298, 35, c.b.a.l.a.j(stringExtra9).length, c.b.a.l.a.j(stringExtra9));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused36 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[4], 3)) {
                        String stringExtra10 = intent.getStringExtra("SAPA_DIST_AUTO");
                        if (stringExtra10 != null && stringExtra10.length() > 0) {
                            b.this.f412b.write(1298, 36, stringExtra10.length(), c.b.a.l.a.j(stringExtra10));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused37 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[4], 4)) {
                        String stringExtra11 = intent.getStringExtra("SEG_REMAIN_DIS_AUTO");
                        if (stringExtra11 != null && stringExtra11.length() > 0) {
                            b.this.f412b.write(1298, 37, c.b.a.l.a.j(stringExtra11).length, c.b.a.l.a.j(stringExtra11));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused38 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[4], 5)) {
                        String stringExtra12 = intent.getStringExtra("EXIT_NAME_INFO");
                        if (stringExtra12 != null && stringExtra12.length() > 0) {
                            b.this.f412b.write(1298, 38, c.b.a.l.a.j(stringExtra12).length, c.b.a.l.a.j(stringExtra12));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused39 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[4], 6)) {
                        String stringExtra13 = intent.getStringExtra("EXIT_DIRECTION_INFO");
                        if (stringExtra13 != null && stringExtra13.length() > 0) {
                            b.this.f412b.write(1298, 39, c.b.a.l.a.j(stringExtra13).length, c.b.a.l.a.j(stringExtra13));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused40 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[4], 7)) {
                        String stringExtra14 = intent.getStringExtra("ETA_TEXT");
                        if (stringExtra14 != null) {
                            b.this.f412b.write(1298, 40, c.b.a.l.a.j(stringExtra14).length, c.b.a.l.a.j(stringExtra14));
                        }
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused41 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[5], 0)) {
                        b.this.f412b.write(1298, 41, c.b.a.l.a.i(intent.getIntExtra(" NEXT_ROAD_PROGRESS_PRECENT", 0)).length, c.b.a.l.a.i(intent.getIntExtra(" NEXT_ROAD_PROGRESS_PRECENT", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused42 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[5], 1)) {
                        b.this.f412b.write(1298, 42, c.b.a.l.a.i(intent.getIntExtra(" ROUNDABOUT_OUT_ANGLE", 0)).length, c.b.a.l.a.i(intent.getIntExtra(" ROUNDABOUT_OUT_ANGLE", 0)));
                        Thread.sleep(30L);
                    }
                    c.b.a.l.b unused43 = b.this.f412b;
                    if (c.b.a.l.b.a(b.this.N[5], 2)) {
                        b.this.f412b.write(1298, 43, c.b.a.l.a.i(intent.getIntExtra(" SEG_ASSISTANT_ACTION", 0)).length, c.b.a.l.a.i(intent.getIntExtra(" SEG_ASSISTANT_ACTION", 0)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("mAmap", e.toString());
                }
            }
        }
    }

    public class c implements c.b.a.k.c {
        public c() {
        }

        @Override // c.b.a.k.c
        public int a() {
            return 0;
        }

        @Override // c.b.a.k.c
        public int b() {
            return 0;
        }

        @Override // c.b.a.k.c
        public void c(c.b.a.k.a aVar) {
            Log.d("LS123", "onThemeSwitchStart: ");
        }

        @Override // c.b.a.k.c
        public boolean d(c.b.a.k.a aVar) {
            Log.d("LS123", "onThemeSwitching: ");
            return false;
        }

        @Override // c.b.a.k.c
        public int e() {
            return 0;
        }

        @Override // c.b.a.k.c
        public void f(c.b.a.k.a aVar, boolean z) {
            Context context = aVar.f429a.f442b;
            b.this.f413c = aVar.f430b;
            if (c.b.a.k.k.a.e == 0) {
                Settings.System.putInt(b.g0.getContentResolver(), "front_Radar_Ioc", 0);
            } else {
                Settings.System.putInt(b.g0.getContentResolver(), "front_Radar_Ioc", 1);
                Settings.System.putInt(b.g0.getContentResolver(), "Front_Radar_Ioc_Send_ID", c.b.a.k.k.a.f);
                Settings.System.putString(b.g0.getContentResolver(), "Front_Radar_Ioc_Send_Data_Down", c.b.a.k.k.a.g);
                Settings.System.putString(b.g0.getContentResolver(), "Front_Radar_Ioc_Send_Data_Up", c.b.a.k.k.a.h);
                Settings.System.putInt(b.g0.getContentResolver(), "Front_Radar_Ioc_Receive_ID", c.b.a.k.k.a.i);
                Settings.System.putInt(b.g0.getContentResolver(), "Front_Radar_Ioc_Receive_Data_ID", c.b.a.k.k.a.j);
                Settings.System.putInt(b.g0.getContentResolver(), "Front_Radar_Ioc_Receive_Data", c.b.a.k.k.a.k);
                Settings.System.putInt(b.g0.getContentResolver(), "Front_Radar_Ioc_Receive_Bit", c.b.a.k.k.a.l);
                Settings.System.putInt(b.g0.getContentResolver(), "Front_Radar_Ioc_Up", c.b.a.k.k.a.m);
            }
            b.this.z0();
        }
    }

    public class d implements ServiceConnection {
        public d() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            c.b.c.b aVar;
            Log.d("LS123", "uart onServiceConnected");
            try {
                b bVar = b.this;
                bVar.U = true;
                if (iBinder == null) {
                    aVar = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.tw.uart.IUartController");
                    aVar = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof c.b.c.b)) ? new c.b.c.a(iBinder) : (c.b.c.b) iInterfaceQueryLocalInterface;
                }
                bVar.S = aVar;
                if (b.this.S != null) {
                    Log.e("LS123", "CarServiceModel: " + SystemProperties.get("persist.tw.uart.ttys"));
                    if (TextUtils.isEmpty(SystemProperties.get("persist.tw.uart.ttys"))) {
                        b.this.S.Q(115200, c.b.a.l.a.d());
                    } else {
                        b.this.S.Q(115200, SystemProperties.get("persist.tw.uart.ttys"));
                    }
                    b bVar2 = b.this;
                    if (bVar2.T == null) {
                        bVar2.T = new f(bVar2);
                    }
                    b bVar3 = b.this;
                    bVar3.S.e0(bVar3.T);
                }
                b bVar4 = b.this;
                Objects.requireNonNull(bVar4);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.tw.launcher.musicinfo");
                intentFilter.addAction("com.tw.music.info");
                intentFilter.addAction("com.tw.launcher.music_progress_duration");
                bVar4.f411a.registerReceiver(bVar4.d0, intentFilter);
            } catch (Exception e) {
                Log.e("LS123", Log.getStackTraceString(e));
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            c.b.c.b bVar;
            Log.d("LS123", "uart onServiceDisconnected");
            try {
                b bVar2 = b.this;
                f fVar = bVar2.T;
                if (fVar != null && (bVar = bVar2.S) != null) {
                    bVar.M(fVar);
                }
                b bVar3 = b.this;
                bVar3.S = null;
                bVar3.f411a.unregisterReceiver(bVar3.d0);
            } catch (Exception e) {
                Log.e("LS123", Log.getStackTraceString(e));
                b.this.S = null;
            }
        }
    }

    public interface e {
        void a();
    }

    public class f extends c.b.c.c.a {
        public f(b bVar) {
        }
    }

    public class g extends Thread {
        public g() {
        }

        /* JADX WARN: Code duplicated, block: B:70:0x00ba A[Catch: all -> 0x0157, TryCatch #0 {, blocks: (B:6:0x0011, B:7:0x0015, B:9:0x001b, B:11:0x0026, B:13:0x002b, B:15:0x0031, B:18:0x0036, B:19:0x0039, B:22:0x003e, B:24:0x0042, B:35:0x0058, B:37:0x005c, B:39:0x0061, B:41:0x0066, B:43:0x006b, B:46:0x0071, B:51:0x0087, B:53:0x008b, B:48:0x0076, B:49:0x007b, B:54:0x0094, B:55:0x009a, B:57:0x009f, B:59:0x00a4, B:70:0x00ba, B:73:0x00cd, B:75:0x00e0, B:77:0x0105, B:80:0x011c, B:85:0x0132, B:87:0x0136, B:83:0x0123, B:84:0x0128, B:62:0x00a9, B:64:0x00ae, B:90:0x0140, B:92:0x0148, B:94:0x014c, B:27:0x0049, B:29:0x004e), top: B:101:0x0011 }] */
        /* JADX WARN: Code duplicated, block: B:77:0x0105 A[Catch: all -> 0x0157, TryCatch #0 {, blocks: (B:6:0x0011, B:7:0x0015, B:9:0x001b, B:11:0x0026, B:13:0x002b, B:15:0x0031, B:18:0x0036, B:19:0x0039, B:22:0x003e, B:24:0x0042, B:35:0x0058, B:37:0x005c, B:39:0x0061, B:41:0x0066, B:43:0x006b, B:46:0x0071, B:51:0x0087, B:53:0x008b, B:48:0x0076, B:49:0x007b, B:54:0x0094, B:55:0x009a, B:57:0x009f, B:59:0x00a4, B:70:0x00ba, B:73:0x00cd, B:75:0x00e0, B:77:0x0105, B:80:0x011c, B:85:0x0132, B:87:0x0136, B:83:0x0123, B:84:0x0128, B:62:0x00a9, B:64:0x00ae, B:90:0x0140, B:92:0x0148, B:94:0x014c, B:27:0x0049, B:29:0x004e), top: B:101:0x0011 }] */
        /* JADX WARN: Code duplicated, block: B:78:0x0119  */
        /* JADX WARN: Code duplicated, block: B:80:0x011c A[Catch: all -> 0x0157, TryCatch #0 {, blocks: (B:6:0x0011, B:7:0x0015, B:9:0x001b, B:11:0x0026, B:13:0x002b, B:15:0x0031, B:18:0x0036, B:19:0x0039, B:22:0x003e, B:24:0x0042, B:35:0x0058, B:37:0x005c, B:39:0x0061, B:41:0x0066, B:43:0x006b, B:46:0x0071, B:51:0x0087, B:53:0x008b, B:48:0x0076, B:49:0x007b, B:54:0x0094, B:55:0x009a, B:57:0x009f, B:59:0x00a4, B:70:0x00ba, B:73:0x00cd, B:75:0x00e0, B:77:0x0105, B:80:0x011c, B:85:0x0132, B:87:0x0136, B:83:0x0123, B:84:0x0128, B:62:0x00a9, B:64:0x00ae, B:90:0x0140, B:92:0x0148, B:94:0x014c, B:27:0x0049, B:29:0x004e), top: B:101:0x0011 }] */
        /* JADX WARN: Code duplicated, block: B:81:0x0120  */
        /* JADX WARN: Code duplicated, block: B:83:0x0123 A[Catch: all -> 0x0157, TryCatch #0 {, blocks: (B:6:0x0011, B:7:0x0015, B:9:0x001b, B:11:0x0026, B:13:0x002b, B:15:0x0031, B:18:0x0036, B:19:0x0039, B:22:0x003e, B:24:0x0042, B:35:0x0058, B:37:0x005c, B:39:0x0061, B:41:0x0066, B:43:0x006b, B:46:0x0071, B:51:0x0087, B:53:0x008b, B:48:0x0076, B:49:0x007b, B:54:0x0094, B:55:0x009a, B:57:0x009f, B:59:0x00a4, B:70:0x00ba, B:73:0x00cd, B:75:0x00e0, B:77:0x0105, B:80:0x011c, B:85:0x0132, B:87:0x0136, B:83:0x0123, B:84:0x0128, B:62:0x00a9, B:64:0x00ae, B:90:0x0140, B:92:0x0148, B:94:0x014c, B:27:0x0049, B:29:0x004e), top: B:101:0x0011 }] */
        /* JADX WARN: Code duplicated, block: B:84:0x0128 A[Catch: all -> 0x0157, TryCatch #0 {, blocks: (B:6:0x0011, B:7:0x0015, B:9:0x001b, B:11:0x0026, B:13:0x002b, B:15:0x0031, B:18:0x0036, B:19:0x0039, B:22:0x003e, B:24:0x0042, B:35:0x0058, B:37:0x005c, B:39:0x0061, B:41:0x0066, B:43:0x006b, B:46:0x0071, B:51:0x0087, B:53:0x008b, B:48:0x0076, B:49:0x007b, B:54:0x0094, B:55:0x009a, B:57:0x009f, B:59:0x00a4, B:70:0x00ba, B:73:0x00cd, B:75:0x00e0, B:77:0x0105, B:80:0x011c, B:85:0x0132, B:87:0x0136, B:83:0x0123, B:84:0x0128, B:62:0x00a9, B:64:0x00ae, B:90:0x0140, B:92:0x0148, B:94:0x014c, B:27:0x0049, B:29:0x004e), top: B:101:0x0011 }] */
        /* JADX WARN: Code duplicated, block: B:86:0x0135 A[PHI: r7
          0x0135: PHI (r7v11 boolean) = (r7v6 boolean), (r7v15 boolean), (r7v16 boolean), (r7v16 boolean), (r7v16 boolean) binds: [B:58:0x00a2, B:67:0x00b4, B:69:0x00b8, B:74:0x00de, B:72:0x00cc] A[DONT_GENERATE, DONT_INLINE]] */
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            int i;
            byte b2;
            int i2;
            int i3;
            int iD;
            int[] iArr;
            int i4;
            int iD2;
            b bVar = b.this;
            byte[] bArr = bVar.f;
            ArrayList<c.b.a.e.a> arrayList = bVar.f413c;
            int i5 = bVar.e;
            int i6 = bVar.d;
            synchronized (bVar) {
                if (arrayList != null && bArr != null) {
                    for (c.b.a.e.a aVar : arrayList) {
                        if (i5 == aVar.f396a) {
                            int length = bArr.length;
                            int i7 = aVar.y;
                            if (length > i7 && aVar.x == bArr[i7] && aVar.G == 1) {
                                bVar.G0(aVar);
                            }
                        }
                        if (aVar.d != null && i5 == aVar.f396a) {
                            int i8 = aVar.t;
                            char c2 = 0;
                            boolean z = i8 == -1 || ((i = aVar.u) < bArr.length && i8 == bArr[i]);
                            if (z && (iArr = aVar.s) != null && (i4 = aVar.r) < bArr.length) {
                                byte b3 = bArr[i4];
                                if (iArr.length > 0) {
                                    int i9 = iArr[0];
                                    int i10 = iArr.length == 2 ? iArr[1] : 0;
                                    iD2 = i9 == -1 ? b3 & 255 : i10 == 0 ? c.b.a.l.b.d(b3, i9, 1) : c.b.a.l.b.d(b3, i9, Math.abs(i10 - i9) + 1);
                                } else {
                                    iD2 = 0;
                                }
                                if (aVar.v != iD2) {
                                    aVar.v = iD2;
                                    c.b.a.g.b.C0015b.f410a.f(arrayList, aVar.f397b);
                                }
                            }
                            int[] iArr2 = new int[aVar.d.length];
                            int i11 = 0;
                            while (true) {
                                int[] iArr3 = aVar.d;
                                if (i11 >= iArr3.length) {
                                    break;
                                }
                                if (iArr3[i11] < bArr.length) {
                                    int i12 = aVar.x;
                                    if (i12 != -1) {
                                        int i13 = aVar.y;
                                        if (i13 >= bArr.length) {
                                            z = false;
                                            if (z) {
                                                b2 = bArr[iArr3[i11]];
                                                if (aVar.e.get(Integer.valueOf(iArr3[i11])) != null && aVar.e.get(Integer.valueOf(aVar.d[i11])).length > 0) {
                                                    i2 = aVar.e.get(Integer.valueOf(aVar.d[i11]))[c2];
                                                    if (aVar.e.get(Integer.valueOf(aVar.d[i11])).length == 2) {
                                                        i3 = aVar.e.get(Integer.valueOf(aVar.d[i11]))[1];
                                                    } else {
                                                        i3 = 0;
                                                    }
                                                    if (i2 == -1) {
                                                        iD = b2 & 255;
                                                    } else if (i3 == 0) {
                                                        iD = c.b.a.l.b.d(b2, i2, 1);
                                                    } else {
                                                        iD = c.b.a.l.b.d(b2, i2, Math.abs(i3 - i2) + 1);
                                                    }
                                                    iArr2[i11] = iD;
                                                }
                                            }
                                        } else if (i12 != bArr[i13]) {
                                            z = false;
                                        }
                                    }
                                    z = true;
                                    if (z) {
                                        b2 = bArr[iArr3[i11]];
                                        if (aVar.e.get(Integer.valueOf(iArr3[i11])) != null) {
                                            i2 = aVar.e.get(Integer.valueOf(aVar.d[i11]))[c2];
                                            if (aVar.e.get(Integer.valueOf(aVar.d[i11])).length == 2) {
                                                i3 = aVar.e.get(Integer.valueOf(aVar.d[i11]))[1];
                                            } else {
                                                i3 = 0;
                                            }
                                            if (i2 == -1) {
                                                iD = b2 & 255;
                                            } else if (i3 == 0) {
                                                iD = c.b.a.l.b.d(b2, i2, 1);
                                            } else {
                                                iD = c.b.a.l.b.d(b2, i2, Math.abs(i3 - i2) + 1);
                                            }
                                            iArr2[i11] = iD;
                                        }
                                    }
                                }
                                i11++;
                                c2 = 0;
                            }
                            if (z && (!Arrays.equals(aVar.f, iArr2) || aVar.f == null)) {
                                aVar.f = iArr2;
                                c.b.a.g.b.C0015b.f410a.f(arrayList, aVar.f397b);
                            }
                        }
                    }
                }
            }
        }
    }

    public class h extends BroadcastReceiver {
        public h(a aVar) {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            byte b2 = -1;
            try {
                int iHashCode = action.hashCode();
                if (iHashCode != -1012023396) {
                    if (iHashCode != 252458357) {
                        if (iHashCode == 1072499747 && action.equals("com.tw.launcher.musicinfo")) {
                            b2 = 0;
                        }
                    } else if (action.equals("com.tw.music.info")) {
                        b2 = 1;
                    }
                } else if (action.equals("com.tw.launcher.music_progress_duration")) {
                    b2 = 2;
                }
                if (b2 == 0) {
                    byte[] byteArrayExtra = intent.getByteArrayExtra("msg_Albumart");
                    Objects.requireNonNull(b.this);
                    b.this.W.d = byteArrayExtra != null ? BitmapFactory.decodeByteArray(byteArrayExtra, 0, byteArrayExtra.length) : null;
                    String stringExtra = intent.getStringExtra("msg_Artist");
                    if (TextUtils.isEmpty(stringExtra)) {
                        return;
                    }
                    b bVar = b.this;
                    bVar.W.f400b = stringExtra;
                    b.h0(bVar, stringExtra.getBytes("UTF-16LE"), 2);
                    return;
                }
                if (b2 != 1) {
                    if (b2 != 2) {
                        return;
                    }
                    intent.getIntExtra("msg_music_progress", 0);
                    intent.getIntExtra("msg_music_duration", 0);
                    return;
                }
                String stringExtra2 = intent.getStringExtra("musicaArtist");
                if (!TextUtils.isEmpty(stringExtra2)) {
                    b bVar2 = b.this;
                    bVar2.W.f400b = stringExtra2;
                    b.h0(bVar2, stringExtra2.getBytes("UTF-16LE"), 2);
                }
                String stringExtra3 = intent.getStringExtra("musicAlbum");
                if (!TextUtils.isEmpty(stringExtra3)) {
                    b bVar3 = b.this;
                    bVar3.W.f400b = stringExtra2;
                    b.h0(bVar3, stringExtra3.getBytes("UTF-16LE"), 3);
                }
                String stringExtra4 = intent.getStringExtra("musicPath");
                if (TextUtils.isEmpty(stringExtra4)) {
                    return;
                }
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(stringExtra4);
                if (mediaMetadataRetriever.extractMetadata(12) != null) {
                    byte[] embeddedPicture = mediaMetadataRetriever.getEmbeddedPicture();
                    Objects.requireNonNull(b.this);
                    b.this.W.d = embeddedPicture != null ? BitmapFactory.decodeByteArray(embeddedPicture, 0, embeddedPicture.length) : null;
                }
            } catch (Exception unused) {
            }
        }
    }

    static {
        new HashMap();
        e0 = true;
    }

    public b(Context context) {
        super(context);
        this.g = -1;
        this.h = -1;
        this.i = false;
        this.j = false;
        this.k = -1;
        this.l = -1;
        this.m = 0;
        this.n = 0.0f;
        this.o = 0.0f;
        this.p = "℃";
        this.q = 0;
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.u = 0;
        this.v = 0;
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.A = 0;
        this.B = 0;
        this.C = 0;
        this.D = 0;
        this.E = 0;
        this.F = 0;
        this.G = false;
        this.H = false;
        this.I = 240;
        this.J = false;
        this.K = null;
        this.L = 0;
        this.O = new a();
        this.P = new C0016b();
        this.Q = new c();
        this.R = new ArrayList<>();
        this.U = true;
        this.V = new d();
        this.X = "";
        this.Y = "";
        this.b0 = false;
        this.c0 = false;
        g0 = context;
        Context applicationContext = context.getApplicationContext();
        if (!TextUtils.isEmpty("com.tw.service.xt")) {
            Iterator<PackageInfo> it = applicationContext.getPackageManager().getInstalledPackages(0).iterator();
            while (it.hasNext() && !"com.tw.service.xt".equals(it.next().packageName)) {
            }
        }
        c.b.a.l.b bVarF = c.b.a.l.b.f();
        this.f412b = bVarF;
        bVarF.addHandler("LS123", this.O);
        this.O.sendEmptyMessageDelayed(65289, 1000L);
        this.O.sendEmptyMessageDelayed(1, 1000L);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("AUTONAVI_STANDARD_BROADCAST_SEND");
        intentFilter.addAction("LRREVERSE_RESET");
        context.registerReceiver(this.P, intentFilter);
        bVarF.write(1298, 255);
        if (SystemProperties.get("car.infoservice.uart").equals("true")) {
            this.W = new c.b.a.e.b();
            this.d0 = new h(null);
            this.O.sendEmptyMessageDelayed(65298, 5000L);
        }
        SystemProperties.get("ro.board.platform");
        this.M = new c.b.b.b(context);
        this.O.sendEmptyMessageDelayed(65300, 3000L);
    }

    public static /* synthetic */ int E(b bVar, int i) {
        Objects.requireNonNull(bVar);
        return i;
    }

    public static void K(b bVar) {
        Objects.requireNonNull(bVar);
        Intent intent = new Intent("com.tw.uart.UartService.Bind");
        intent.setClassName("com.tw.uart", "com.tw.uart.UartService");
        if (MyApplication.f493b == null) {
            MyApplication.f493b = new MyApplication();
        }
        MyApplication.f493b.bindService(intent, bVar.V, 1);
    }

    public static void N(b bVar, Message message) {
        Objects.requireNonNull(bVar);
        Object obj = message.obj;
        if (obj == null) {
            return;
        }
        byte[] bArr = (byte[]) obj;
        bVar.u = bVar.H0(bArr[0], bArr[4]);
        bVar.v = bVar.H0(bArr[1], bArr[4]);
        bVar.w = bVar.H0(bArr[2], bArr[4]);
        bVar.x = bVar.H0(bArr[3], bArr[4]);
        bVar.q = bVar.H0(bArr[5], bArr[9]);
        bVar.r = bVar.H0(bArr[6], bArr[9]);
        bVar.s = bVar.H0(bArr[7], bArr[9]);
        bVar.t = bVar.H0(bArr[8], bArr[9]);
        try {
            bVar.a("Radar", -1);
        } catch (RemoteException e2) {
            e2.printStackTrace();
        }
    }

    public static /* synthetic */ int e(b bVar, int i) {
        Objects.requireNonNull(bVar);
        return i;
    }

    public static void h(b bVar) {
        Objects.requireNonNull(bVar);
        try {
            bVar.z0();
            c.b.a.g.b.C0015b.f410a.g(bVar.n);
            c.b.a.g.b.C0015b.f410a.i(bVar.j, bVar.l == 1, bVar.f413c);
            c.b.a.g.b.C0015b.f410a.j(bVar.k == 1);
            c.b.a.g.b.C0015b.f410a.f(bVar.f413c, -1);
            c.b.a.g.b.C0015b.f410a.l(bVar.m);
            c.b.a.g.b.C0015b.f410a.k(bVar.L);
            bVar.a("TravelTime", -1);
            ArrayList<c.b.a.e.a> arrayList = bVar.f413c;
            if (arrayList != null) {
                for (c.b.a.e.a aVar : arrayList) {
                    if (aVar.U == 1) {
                        int i = aVar.f397b;
                        if (i != 8 && i != 9) {
                            if (i == 13) {
                                c.b.a.g.b.C0015b.f410a.d(bVar.o, bVar.p);
                            } else if (i != 15) {
                                if (i == 58) {
                                    c.b.a.g.b.C0015b.f410a.e(bVar.d, bVar.e, bVar.f);
                                }
                            }
                        }
                        c.b.a.g.b.C0015b.f410a.m(i, bVar.m);
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void h0(b bVar, byte[] bArr, int i) {
        Objects.requireNonNull(bVar);
        byte[] bArr2 = new byte[bArr.length + 8];
        bArr2[0] = -91;
        bArr2[1] = 0;
        bArr2[2] = 0;
        bArr2[3] = 0;
        bArr2[4] = (byte) i;
        bArr2[5] = 35;
        bArr2[6] = (byte) (bArr.length % 256);
        bArr2[7] = (byte) (bArr.length / 256);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr2[i2 + 8] = bArr[i2];
        }
        try {
            bVar.S.X(bArr2);
        } catch (RemoteException e2) {
            throw new RuntimeException(e2);
        }
    }

    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 15421. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:99)
        */
    public static void i0(c.b.a.h.b r16, android.os.Message r17) {
        /*
            Method dump skipped, instruction units count: 1542
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: c.b.a.h.b.i0(c.b.a.h.b, android.os.Message):void");
    }

    public static /* synthetic */ c.b.c.b v(b bVar, c.b.c.b bVar2) {
        bVar.S = null;
        return null;
    }

    public void A0(byte[] bArr, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("sendToMcu: ");
        sb.append(i);
        sb.append(",");
        sb.append(bArr.length);
        sb.append(",,");
        sb.append(this.f412b == null);
        Log.d("LS123", sb.toString());
        c.b.a.l.b bVar = this.f412b;
        Objects.requireNonNull(bVar);
        bVar.write(1281, i, bArr.length, bArr);
    }

    public void B0(int i, int i2) {
        int[] iArr;
        ArrayList<c.b.a.e.a> arrayList = this.f413c;
        if (arrayList == null || arrayList == null) {
            return;
        }
        Log.e("LS123", "sendCarServiceVoiceControl: " + i + "   " + this.f413c.size());
        for (c.b.a.e.a aVar : this.f413c) {
            if (aVar.f397b != -1 || (iArr = aVar.I) == null || aVar.U == 0) {
                return;
            }
            int i3 = aVar.G;
            int i4 = 0;
            if (i3 == 0) {
                if (iArr.length != 2 || iArr[1] != i) {
                    if ((iArr.length == 2 && iArr[0] == i) || (iArr.length == 1 && iArr[0] == i)) {
                        F0(aVar);
                    }
                }
            }
            if (i3 == 1) {
                while (true) {
                    int[] iArr2 = aVar.I;
                    if (i4 < iArr2.length) {
                        if (iArr2[i4] == i) {
                            G0(aVar);
                        }
                        i4++;
                    }
                }
            }
        }
    }

    public void C0(ArrayList<c.b.a.e.a> arrayList) {
        boolean z;
        boolean z2;
        boolean z3;
        if (arrayList != null) {
            for (c.b.a.e.a aVar : arrayList) {
                int[] iArr = aVar.P;
                if (iArr != null || aVar.Q != null || aVar.R != null || aVar.S != -1) {
                    if (aVar.T == null) {
                        aVar.X = true;
                    }
                    int i = 0;
                    aVar.W = false;
                    if (iArr == null || aVar.Q == null || aVar.R == null) {
                        z = true;
                        z2 = true;
                        z3 = true;
                        break;
                    }
                    int i2 = 0;
                    while (true) {
                        int[] iArr2 = aVar.P;
                        if (i2 >= iArr2.length) {
                            z = false;
                            z2 = false;
                            z3 = false;
                            break;
                        }
                        boolean z4 = iArr2[i2] == c.b.a.l.b.f450c;
                        boolean z5 = aVar.Q[i2] == c.b.a.l.b.d;
                        boolean zEquals = aVar.R[i2].equals(c.b.a.l.b.f);
                        if (z4 && z5 && zEquals) {
                            z = true;
                            z2 = true;
                            z3 = true;
                            break;
                        }
                        i2++;
                    }
                    if (aVar.P != null && aVar.Q != null) {
                        int i3 = 0;
                        while (true) {
                            int[] iArr3 = aVar.P;
                            if (i3 >= iArr3.length) {
                                z = false;
                                z2 = false;
                                break;
                            }
                            boolean z6 = iArr3[i3] == c.b.a.l.b.f450c;
                            boolean z7 = aVar.Q[i3] == c.b.a.l.b.d;
                            if (z6 && z7) {
                                z = true;
                                z2 = true;
                                break;
                            }
                            i3++;
                        }
                        z3 = false;
                    }
                    if (aVar.P != null && aVar.R != null) {
                        int i4 = 0;
                        while (true) {
                            int[] iArr4 = aVar.P;
                            if (i4 >= iArr4.length) {
                                z = false;
                                z2 = false;
                                z3 = false;
                                break;
                            }
                            boolean z8 = iArr4[i4] == c.b.a.l.b.f450c;
                            boolean zEquals2 = aVar.R[i4].equals(c.b.a.l.b.f);
                            if (z8 && zEquals2) {
                                z = true;
                                z2 = true;
                                z3 = true;
                                break;
                            }
                            i4++;
                        }
                    }
                    if (aVar.Q != null && aVar.R != null) {
                        int i5 = 0;
                        while (true) {
                            int[] iArr5 = aVar.Q;
                            if (i5 >= iArr5.length) {
                                z2 = false;
                                z3 = false;
                                break;
                            }
                            boolean z9 = iArr5[i5] == c.b.a.l.b.d;
                            boolean zEquals3 = aVar.R[i5].equals(c.b.a.l.b.f);
                            if (z9 && zEquals3) {
                                z2 = true;
                                z3 = true;
                                break;
                            }
                            i5++;
                        }
                    }
                    if (aVar.P != null) {
                        int i6 = 0;
                        while (true) {
                            int[] iArr6 = aVar.P;
                            if (i6 >= iArr6.length) {
                                break;
                            }
                            if (iArr6[i6] == c.b.a.l.b.f450c) {
                                z = true;
                                break;
                            } else {
                                i6++;
                                z = false;
                            }
                        }
                    }
                    if (aVar.Q != null) {
                        int i7 = 0;
                        while (true) {
                            int[] iArr7 = aVar.Q;
                            if (i7 >= iArr7.length) {
                                break;
                            }
                            if (iArr7[i7] == c.b.a.l.b.d) {
                                z2 = true;
                                break;
                            } else {
                                i7++;
                                z2 = false;
                            }
                        }
                    }
                    if (aVar.R != null) {
                        int i8 = 0;
                        while (true) {
                            String[] strArr = aVar.R;
                            if (i8 >= strArr.length) {
                                break;
                            }
                            if (strArr[i8].equals(c.b.a.l.b.f)) {
                                z3 = true;
                                break;
                            } else {
                                i8++;
                                z3 = false;
                            }
                        }
                    }
                    boolean z10 = aVar.S == -1 || c.b.a.l.b.g;
                    if (z && z2 && z3 && z10) {
                        aVar.W = true;
                    }
                    if (!aVar.W || !aVar.X) {
                        i = aVar.V;
                        aVar.U = i;
                    } else if (aVar.V == 0) {
                        aVar.U = 1;
                    } else {
                        aVar.U = i;
                    }
                }
            }
        }
    }

    public void D0(byte[] bArr, ArrayList<c.b.a.e.a> arrayList) {
        if (arrayList != null) {
            for (c.b.a.e.a aVar : arrayList) {
                int[] iArr = aVar.T;
                if (iArr != null) {
                    aVar.X = false;
                    if (aVar.P == null && aVar.Q == null && aVar.R == null && aVar.S == -1) {
                        aVar.W = true;
                    }
                    if (iArr != null) {
                        int i = 0;
                        while (true) {
                            int[] iArr2 = aVar.T;
                            if (i >= iArr2.length) {
                                break;
                            }
                            if (iArr2[i] == (bArr[12] & 127)) {
                                aVar.X = true;
                                break;
                            }
                            i++;
                        }
                    }
                    if (!aVar.W || !aVar.X) {
                        aVar.U = aVar.V;
                    } else if (aVar.V == 0) {
                        aVar.U = 1;
                    } else {
                        aVar.U = 0;
                    }
                }
            }
        }
    }

    public final void E0(int i) {
        if (this.I != i) {
            this.I = i;
            try {
                a("Track", -1);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    public final void F0(c.b.a.e.a aVar) {
        A0(c.b.a.l.a.c(aVar, 0), aVar.z);
        if (aVar.A == null) {
            try {
                Thread.sleep(30L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            A0(c.b.a.l.a.c(aVar, 1), aVar.z);
        }
    }

    public final void G0(c.b.a.e.a aVar) {
        String str = aVar.E;
        String str2 = aVar.F;
        try {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                return;
            }
            Intent intent = new Intent();
            intent.setClassName(str, str2);
            intent.setFlags(268435456);
            String[] strArr = aVar.K;
            int[] iArr = aVar.L;
            String[] strArr2 = aVar.M;
            if (strArr != null) {
                Bundle bundle = new Bundle();
                String str3 = aVar.J;
                int i = 0;
                if (iArr != null && iArr.length == strArr.length) {
                    while (i < strArr.length) {
                        bundle.putInt(strArr[i], iArr[i]);
                        i++;
                    }
                } else if (strArr2 != null && strArr2.length == strArr.length) {
                    while (i < strArr.length) {
                        bundle.putString(strArr[i], strArr2[i]);
                        i++;
                    }
                }
                if (TextUtils.isEmpty(str3)) {
                    intent.putExtras(bundle);
                } else {
                    intent.putExtra(str3, bundle);
                }
            }
            this.f411a.startActivity(intent);
        } catch (Exception e2) {
            StringBuilder sbB = c.a.a.a.a.b("sendCarServiceVoiceControl: ");
            sbB.append(e2.toString());
            Log.e("LS123", sbB.toString());
            e2.printStackTrace();
        }
    }

    public final int H0(int i, int i2) {
        if (i < i2) {
            return ((i * 11) / i2) + 1;
        }
        return 0;
    }

    /* JADX WARN: Code duplicated, block: B:105:0x0252 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Code duplicated, block: B:120:0x024d A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:83:0x01f4  */
    /* JADX WARN: Code duplicated, block: B:86:0x01fe  */
    /* JADX WARN: Code duplicated, block: B:88:0x020a  */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void a(String str, int i) {
        ArrayList<c.b.a.e.a> arrayList;
        int i2;
        if (str.equals("SendElectricVoltage")) {
            c.b.a.g.b.C0015b.f410a.g(this.n);
        } else {
            if (str.equals("SendHandbrake")) {
                c.b.a.g.b.C0015b.f410a.i(this.j, this.l == 1, this.f413c);
            } else if (str.equals("SendHeadlight")) {
                c.b.a.g.b.C0015b.f410a.j(this.k == 1);
            } else if (str.equals("Radar")) {
                c.b.a.g.b bVar = c.b.a.g.b.C0015b.f410a;
                int[] iArr = {this.q, this.r, this.s, this.t, this.C, this.D, this.E, this.F, this.x, this.w, this.v, this.u, this.B, this.A, this.z, this.y};
                Objects.requireNonNull(bVar);
                try {
                    RemoteCallbackList<c.b.a.d> remoteCallbackList = bVar.f408b;
                    if (remoteCallbackList != null) {
                        int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                        for (int i3 = 0; i3 < iBeginBroadcast; i3++) {
                            if (bVar.f408b.getBroadcastItem(i3) != null) {
                                try {
                                    ((c.b.a.d) bVar.f408b.getBroadcastItem(i3)).d0(null);
                                    ((c.b.a.d) bVar.f408b.getBroadcastItem(i3)).d(iArr[0], iArr[1], iArr[2], iArr[3], iArr[4], iArr[5], iArr[6], iArr[7], iArr[8], iArr[9], iArr[10], iArr[11], iArr[12], iArr[13], iArr[14], iArr[15]);
                                } catch (Exception unused) {
                                    RemoteCallbackList<c.b.a.d> remoteCallbackList2 = bVar.f408b;
                                    remoteCallbackList2.unregister(remoteCallbackList2.getBroadcastItem(i3));
                                    bVar.f408b.finishBroadcast();
                                    arrayList = this.f413c;
                                    if (arrayList != null) {
                                        while (r0.hasNext()) {
                                            i2 = aVar.f397b;
                                            if (i2 != i) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        bVar.f408b.finishBroadcast();
                    }
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            } else if (str.equals("Track")) {
                c.b.a.g.b bVar2 = c.b.a.g.b.C0015b.f410a;
                int i4 = this.I;
                Objects.requireNonNull(bVar2);
                try {
                    RemoteCallbackList<c.b.a.d> remoteCallbackList3 = bVar2.f408b;
                    if (remoteCallbackList3 != null) {
                        int iBeginBroadcast2 = remoteCallbackList3.beginBroadcast();
                        for (int i5 = 0; i5 < iBeginBroadcast2; i5++) {
                            if (bVar2.f408b.getBroadcastItem(i5) != null) {
                                try {
                                    ((c.b.a.d) bVar2.f408b.getBroadcastItem(i5)).d0(null);
                                    ((c.b.a.d) bVar2.f408b.getBroadcastItem(i5)).Z(i4);
                                } catch (Exception unused2) {
                                    RemoteCallbackList<c.b.a.d> remoteCallbackList4 = bVar2.f408b;
                                    remoteCallbackList4.unregister(remoteCallbackList4.getBroadcastItem(i5));
                                    bVar2.f408b.finishBroadcast();
                                    arrayList = this.f413c;
                                    if (arrayList != null) {
                                        while (r0.hasNext()) {
                                            i2 = aVar.f397b;
                                            if (i2 != i) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        bVar2.f408b.finishBroadcast();
                    }
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                }
            } else if (str.equals("Turn")) {
                c.b.a.g.b.C0015b.f410a.l(this.m);
            } else if (str.equals("Speed")) {
                c.b.a.g.b bVar3 = c.b.a.g.b.C0015b.f410a;
                boolean z = this.i;
                int i6 = this.g;
                Objects.requireNonNull(bVar3);
                try {
                    RemoteCallbackList<c.b.a.d> remoteCallbackList5 = bVar3.f408b;
                    if (remoteCallbackList5 != null) {
                        int iBeginBroadcast3 = remoteCallbackList5.beginBroadcast();
                        for (int i7 = 0; i7 < iBeginBroadcast3; i7++) {
                            if (bVar3.f408b.getBroadcastItem(i7) != null) {
                                try {
                                    ((c.b.a.d) bVar3.f408b.getBroadcastItem(i7)).d0(null);
                                    if (z) {
                                        ((c.b.a.d) bVar3.f408b.getBroadcastItem(i7)).j0(i6 / 100);
                                    }
                                } catch (Exception unused3) {
                                    RemoteCallbackList<c.b.a.d> remoteCallbackList6 = bVar3.f408b;
                                    remoteCallbackList6.unregister(remoteCallbackList6.getBroadcastItem(i7));
                                    bVar3.f408b.finishBroadcast();
                                    arrayList = this.f413c;
                                    if (arrayList != null) {
                                        while (r0.hasNext()) {
                                            i2 = aVar.f397b;
                                            if (i2 != i) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        bVar3.f408b.finishBroadcast();
                    }
                } catch (RemoteException e4) {
                    e4.printStackTrace();
                }
            } else if (str.equals("TravelTime")) {
                c.b.a.g.b.C0015b.f410a.k(this.L);
            } else if (str.equals("Engine")) {
                c.b.a.g.b.C0015b.f410a.h(this.i, this.h);
            }
        }
        arrayList = this.f413c;
        if (arrayList != null) {
            for (c.b.a.e.a aVar : arrayList) {
                i2 = aVar.f397b;
                if (i2 != i && aVar.U == 1) {
                    if (i2 == 8 || i2 == 9) {
                        c.b.a.g.b.C0015b.f410a.m(i2, this.m);
                    } else if (i2 == 11) {
                        c.b.a.g.b.C0015b.f410a.h(this.i, this.h);
                    } else if (i2 == 13) {
                        c.b.a.g.b.C0015b.f410a.d(this.o, this.p);
                    } else if (i2 == 15) {
                        c.b.a.g.b.C0015b.f410a.m(i2, this.m);
                    } else if (i2 == 58) {
                        c.b.a.g.b.C0015b.f410a.e(this.d, this.e, this.f);
                    }
                }
            }
        }
    }

    public void u0(boolean z) {
        if (z) {
            this.f412b.write(1291, 3, 0);
        } else {
            this.f412b.write(1291, 0, 0);
            this.h = 0;
        }
    }

    public final String v0(c.b.a.e.a aVar) {
        int i = aVar.f397b;
        if (i != 0 && i != 1) {
            if (i != 2 && i != 3) {
                if (i != 12 && i != 14) {
                    if (i == 18) {
                        return (aVar.m == null && aVar.v == -1) ? a.b.e.b.a.c(this.f411a) : a.b.e.b.a.n(aVar);
                    }
                    if (i != 79) {
                        if (i != 89) {
                            switch (i) {
                                case 60:
                                case 61:
                                    return (aVar.m == null && aVar.v == -1) ? a.b.e.b.a.j(this.f411a) : a.b.e.b.a.n(aVar);
                                default:
                                    switch (i) {
                                        case 67:
                                        case 68:
                                        case 69:
                                        case 70:
                                        case 71:
                                        case 72:
                                        case 73:
                                        case 74:
                                        case 75:
                                            break;
                                        case 76:
                                            if (Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MAINTENANCE_FLAG", 0) == 0) {
                                                return "";
                                            }
                                            if ((aVar.f != null ? a.b.e.b.a.f(aVar) : 0.0f) < Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MAINTENANCE_START", -1)) {
                                                Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MAINTENANCE_START", -1);
                                            }
                                            if (Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MAINTENANCE_START", -1) == -1) {
                                                Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MAINTENANCE_MILEAGE", 10000);
                                            } else {
                                                Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MAINTENANCE_MILEAGE", 10000);
                                                Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MAINTENANCE_START", -1);
                                            }
                                            return a.b.e.b.a.d(this.f411a);
                                        default:
                                            switch (i) {
                                                case 84:
                                                case 85:
                                                case 86:
                                                case 87:
                                                    break;
                                                default:
                                                    return "";
                                            }
                                            break;
                                    }
                                case 62:
                                case 63:
                                    return a.b.e.b.a.n(aVar);
                            }
                        }
                    }
                }
                return (aVar.m == null && aVar.v == -1) ? a.b.e.b.a.j(this.f411a) : a.b.e.b.a.n(aVar);
            }
            return a.b.e.b.a.n(aVar);
        }
        return (aVar.m == null && aVar.v == -1) ? a.b.e.b.a.d(this.f411a) : a.b.e.b.a.n(aVar);
    }

    /* JADX WARN: Code duplicated, block: B:38:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:43:0x0109 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:44:0x010a  */
    /* JADX WARN: Code duplicated, block: B:46:0x010e  */
    /* JADX WARN: Code duplicated, block: B:48:0x0113  */
    public final int w0(c.b.a.e.a aVar) {
        int[] iArr;
        float f2;
        float f3;
        int i = aVar.f397b;
        if (i != 12 && i != 14) {
            if (i != 20) {
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        break;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        break;
                    default:
                        switch (i) {
                            case 16:
                            case 18:
                                break;
                            case 17:
                                return aVar.f[0];
                            default:
                                switch (i) {
                                    default:
                                        switch (i) {
                                            case 59:
                                            case 60:
                                            case 61:
                                            case 62:
                                            case 63:
                                            case 67:
                                            case 69:
                                            case 70:
                                            case 71:
                                            case 72:
                                            case 73:
                                            case 74:
                                            case 75:
                                            case 79:
                                                break;
                                            case 64:
                                                return aVar.f[0];
                                            case 65:
                                            case 66:
                                            case 68:
                                            case 78:
                                                break;
                                            case 76:
                                                if (Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MA_SWITCH", 0) != 0) {
                                                    float f4 = aVar.f != null ? a.b.e.b.a.f(aVar) : 0.0f;
                                                    if (f4 < Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MA_OPEN_CURRENT_MILEAGE", -1)) {
                                                        f4 = Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MA_OPEN_CURRENT_MILEAGE", -1);
                                                    }
                                                    if (Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MA_OPEN_CURRENT_MILEAGE", -1) == -1) {
                                                        f2 = Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MA_LEFT_MILEAGE", 10000);
                                                        f3 = f4 - 0.0f;
                                                    } else {
                                                        f2 = Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MA_LEFT_MILEAGE", 10000);
                                                        f3 = f4 - Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MA_OPEN_CURRENT_MILEAGE", -1);
                                                    }
                                                    float f5 = f2 - f3;
                                                    return (int) a.b.e.b.a.e(f5 >= 0.0f ? f5 : 0.0f, this.f411a);
                                                }
                                            case 77:
                                                if (Settings.System.getInt(this.f411a.getApplicationContext().getContentResolver(), "MA_SWITCH", 0) != 0) {
                                                    int[] iArrK = a.b.e.b.a.k((((((a.b.e.b.a.g(this.f411a, "MA_LEFT_TIME", 6L) * 30) * 24) * 3600) * 1000) + a.b.e.b.a.g(this.f411a, "MA_OPEN_CURRENT_TIME", System.currentTimeMillis())) - System.currentTimeMillis());
                                                    return (iArrK[2] * 12) + iArrK[1];
                                                }
                                                return 0;
                                            default:
                                                switch (i) {
                                                    case 84:
                                                    case 85:
                                                    case 86:
                                                    case 87:
                                                    case 89:
                                                        break;
                                                    case 88:
                                                    case 90:
                                                    case 91:
                                                        break;
                                                    default:
                                                        return 0;
                                                }
                                                break;
                                        }
                                    case 54:
                                    case 55:
                                    case 56:
                                    case 57:
                                        iArr = aVar.f;
                                        if (iArr == null) {
                                            return 0;
                                        }
                                        int[] iArr2 = aVar.g;
                                        return iArr2 != null ? iArr2[iArr[0]] : iArr[0];
                                }
                                break;
                        }
                        break;
                }
            }
            iArr = aVar.f;
            if (iArr == null) {
                return 0;
            }
            int[] iArr3 = aVar.g;
            if (iArr3 != null) {
            }
        }
        return (int) a.b.e.b.a.f(aVar);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004f A[Catch: Exception -> 0x00cf, TryCatch #0 {Exception -> 0x00cf, blocks: (B:3:0x0006, B:5:0x000a, B:6:0x000e, B:8:0x0014, B:10:0x001e, B:18:0x0031, B:19:0x0034, B:26:0x0064, B:28:0x0072, B:31:0x007f, B:33:0x008b, B:36:0x0098, B:38:0x00a4, B:41:0x00ae, B:43:0x00bb, B:44:0x00bf, B:46:0x00cb, B:39:0x00a9, B:40:0x00ac, B:20:0x003c, B:21:0x004f, B:22:0x0059, B:24:0x005e, B:25:0x0061), top: B:51:0x0006 }] */
    public float[] x0(int i) {
        float[] fArr = {-1.0f, 0.0f, 0.0f};
        try {
            ArrayList<c.b.a.e.a> arrayList = this.f413c;
            if (arrayList != null) {
                for (c.b.a.e.a aVar : arrayList) {
                    int i2 = aVar.f397b;
                    if (i2 == i && aVar.U == 1) {
                        if (i2 == 11) {
                            int i3 = this.h;
                            if (i3 == -1) {
                                fArr[0] = 0.0f;
                            } else {
                                fArr[0] = i3;
                            }
                        } else if (i2 != 13 && i2 != 14) {
                            switch (i2) {
                                case 80:
                                case 81:
                                case 82:
                                case 83:
                                    fArr[0] = a.b.e.b.a.l(aVar)[0];
                                    fArr[1] = a.b.e.b.a.l(aVar)[1];
                                    break;
                                case 84:
                                case 85:
                                case 86:
                                case 87:
                                    fArr[0] = 0.0f;
                                    fArr[1] = w0(aVar);
                                    break;
                                default:
                                    fArr[0] = w0(aVar);
                                    break;
                            }
                        } else {
                            fArr[0] = 0.0f;
                            fArr[1] = w0(aVar);
                        }
                        if (v0(aVar).equals("KM") || v0(aVar).equals("psi")) {
                            fArr[1] = 0.0f;
                        } else if (v0(aVar).equals("MI") || v0(aVar).equals("kpa")) {
                            fArr[1] = 1.0f;
                        } else if (v0(aVar).equals("bar")) {
                            fArr[1] = 2.0f;
                        }
                        if (v0(aVar).equals("℃")) {
                            fArr[2] = 0.0f;
                        } else if (v0(aVar).equals("℉")) {
                            fArr[2] = 1.0f;
                        }
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return fArr;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:33:0x0058  */
    public float[] y0(String str) {
        switch (str) {
            case "Turn":
                return new float[]{this.m};
            case "Speed":
                this.J = true;
                return new float[]{this.g / 100};
            case "Track":
                int i = this.I;
                return i != -1 ? new float[]{i} : new float[]{-1.0f};
            case "SendHeadlight":
                int i2 = this.k;
                return i2 != -1 ? new float[]{i2} : new float[]{-1.0f};
            case "SendHandbrake":
                int i3 = this.l;
                return i3 != -1 ? new float[]{i3} : new float[]{-1.0f};
            case "SendElectricVoltage":
                float f2 = this.n;
                return f2 != 0.0f ? new float[]{f2} : new float[]{-1.0f};
            case "Engine":
                this.J = true;
                return new float[]{this.h};
            default:
                return new float[]{-1.0f};
        }
    }

    public final void z0() {
        if (this.R != null) {
            try {
                HashMap<Integer, int[]> map = c.b.a.k.k.a.f446c;
                if (map != null && map.size() > 0) {
                    int size = c.b.a.k.k.a.f446c.size();
                    while (true) {
                        size--;
                        if (size <= -1) {
                            break;
                        }
                        Thread.sleep(200L);
                        int length = c.b.a.k.k.a.f446c.get(Integer.valueOf(size)).length;
                        byte[] bArr = new byte[length];
                        for (int i = 0; i < length; i++) {
                            bArr[i] = (byte) c.b.a.k.k.a.f446c.get(Integer.valueOf(size))[i];
                        }
                        A0(bArr, c.b.a.k.k.a.f445b);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.O.sendEmptyMessageDelayed(65288, 2000L);
        }
    }
}
