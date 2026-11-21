package c.b.a;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import com.tw.car.DaciaFuelActivity;
import com.tw.car.R;

/* JADX INFO: loaded from: classes.dex */
public class c extends Handler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DaciaFuelActivity f446a;

    public c(DaciaFuelActivity daciaFuelActivity) {
        this.f446a = daciaFuelActivity;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x00e7 A[Catch: Exception -> 0x023e, TRY_ENTER, TryCatch #0 {Exception -> 0x023e, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:10:0x0026, B:12:0x0043, B:24:0x00c7, B:27:0x00e7, B:29:0x00f9, B:30:0x0108, B:31:0x0125, B:33:0x0146, B:35:0x0163, B:36:0x0172, B:37:0x018f, B:39:0x01a7, B:41:0x01ba, B:42:0x01c9, B:43:0x01e6, B:45:0x01fe, B:47:0x0211, B:48:0x0220, B:18:0x005f, B:19:0x0075, B:20:0x007c, B:21:0x0093, B:22:0x00a9, B:23:0x00b0, B:11:0x0035), top: B:53:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x00f9 A[Catch: Exception -> 0x023e, TryCatch #0 {Exception -> 0x023e, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:10:0x0026, B:12:0x0043, B:24:0x00c7, B:27:0x00e7, B:29:0x00f9, B:30:0x0108, B:31:0x0125, B:33:0x0146, B:35:0x0163, B:36:0x0172, B:37:0x018f, B:39:0x01a7, B:41:0x01ba, B:42:0x01c9, B:43:0x01e6, B:45:0x01fe, B:47:0x0211, B:48:0x0220, B:18:0x005f, B:19:0x0075, B:20:0x007c, B:21:0x0093, B:22:0x00a9, B:23:0x00b0, B:11:0x0035), top: B:53:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:30:0x0108 A[Catch: Exception -> 0x023e, TryCatch #0 {Exception -> 0x023e, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:10:0x0026, B:12:0x0043, B:24:0x00c7, B:27:0x00e7, B:29:0x00f9, B:30:0x0108, B:31:0x0125, B:33:0x0146, B:35:0x0163, B:36:0x0172, B:37:0x018f, B:39:0x01a7, B:41:0x01ba, B:42:0x01c9, B:43:0x01e6, B:45:0x01fe, B:47:0x0211, B:48:0x0220, B:18:0x005f, B:19:0x0075, B:20:0x007c, B:21:0x0093, B:22:0x00a9, B:23:0x00b0, B:11:0x0035), top: B:53:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x0146 A[Catch: Exception -> 0x023e, TryCatch #0 {Exception -> 0x023e, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:10:0x0026, B:12:0x0043, B:24:0x00c7, B:27:0x00e7, B:29:0x00f9, B:30:0x0108, B:31:0x0125, B:33:0x0146, B:35:0x0163, B:36:0x0172, B:37:0x018f, B:39:0x01a7, B:41:0x01ba, B:42:0x01c9, B:43:0x01e6, B:45:0x01fe, B:47:0x0211, B:48:0x0220, B:18:0x005f, B:19:0x0075, B:20:0x007c, B:21:0x0093, B:22:0x00a9, B:23:0x00b0, B:11:0x0035), top: B:53:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x0163 A[Catch: Exception -> 0x023e, TryCatch #0 {Exception -> 0x023e, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:10:0x0026, B:12:0x0043, B:24:0x00c7, B:27:0x00e7, B:29:0x00f9, B:30:0x0108, B:31:0x0125, B:33:0x0146, B:35:0x0163, B:36:0x0172, B:37:0x018f, B:39:0x01a7, B:41:0x01ba, B:42:0x01c9, B:43:0x01e6, B:45:0x01fe, B:47:0x0211, B:48:0x0220, B:18:0x005f, B:19:0x0075, B:20:0x007c, B:21:0x0093, B:22:0x00a9, B:23:0x00b0, B:11:0x0035), top: B:53:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:36:0x0172 A[Catch: Exception -> 0x023e, TryCatch #0 {Exception -> 0x023e, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:10:0x0026, B:12:0x0043, B:24:0x00c7, B:27:0x00e7, B:29:0x00f9, B:30:0x0108, B:31:0x0125, B:33:0x0146, B:35:0x0163, B:36:0x0172, B:37:0x018f, B:39:0x01a7, B:41:0x01ba, B:42:0x01c9, B:43:0x01e6, B:45:0x01fe, B:47:0x0211, B:48:0x0220, B:18:0x005f, B:19:0x0075, B:20:0x007c, B:21:0x0093, B:22:0x00a9, B:23:0x00b0, B:11:0x0035), top: B:53:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x01a7 A[Catch: Exception -> 0x023e, TryCatch #0 {Exception -> 0x023e, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:10:0x0026, B:12:0x0043, B:24:0x00c7, B:27:0x00e7, B:29:0x00f9, B:30:0x0108, B:31:0x0125, B:33:0x0146, B:35:0x0163, B:36:0x0172, B:37:0x018f, B:39:0x01a7, B:41:0x01ba, B:42:0x01c9, B:43:0x01e6, B:45:0x01fe, B:47:0x0211, B:48:0x0220, B:18:0x005f, B:19:0x0075, B:20:0x007c, B:21:0x0093, B:22:0x00a9, B:23:0x00b0, B:11:0x0035), top: B:53:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:41:0x01ba A[Catch: Exception -> 0x023e, TryCatch #0 {Exception -> 0x023e, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:10:0x0026, B:12:0x0043, B:24:0x00c7, B:27:0x00e7, B:29:0x00f9, B:30:0x0108, B:31:0x0125, B:33:0x0146, B:35:0x0163, B:36:0x0172, B:37:0x018f, B:39:0x01a7, B:41:0x01ba, B:42:0x01c9, B:43:0x01e6, B:45:0x01fe, B:47:0x0211, B:48:0x0220, B:18:0x005f, B:19:0x0075, B:20:0x007c, B:21:0x0093, B:22:0x00a9, B:23:0x00b0, B:11:0x0035), top: B:53:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x01c9 A[Catch: Exception -> 0x023e, TryCatch #0 {Exception -> 0x023e, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:10:0x0026, B:12:0x0043, B:24:0x00c7, B:27:0x00e7, B:29:0x00f9, B:30:0x0108, B:31:0x0125, B:33:0x0146, B:35:0x0163, B:36:0x0172, B:37:0x018f, B:39:0x01a7, B:41:0x01ba, B:42:0x01c9, B:43:0x01e6, B:45:0x01fe, B:47:0x0211, B:48:0x0220, B:18:0x005f, B:19:0x0075, B:20:0x007c, B:21:0x0093, B:22:0x00a9, B:23:0x00b0, B:11:0x0035), top: B:53:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:45:0x01fe A[Catch: Exception -> 0x023e, TryCatch #0 {Exception -> 0x023e, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:10:0x0026, B:12:0x0043, B:24:0x00c7, B:27:0x00e7, B:29:0x00f9, B:30:0x0108, B:31:0x0125, B:33:0x0146, B:35:0x0163, B:36:0x0172, B:37:0x018f, B:39:0x01a7, B:41:0x01ba, B:42:0x01c9, B:43:0x01e6, B:45:0x01fe, B:47:0x0211, B:48:0x0220, B:18:0x005f, B:19:0x0075, B:20:0x007c, B:21:0x0093, B:22:0x00a9, B:23:0x00b0, B:11:0x0035), top: B:53:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:47:0x0211 A[Catch: Exception -> 0x023e, TryCatch #0 {Exception -> 0x023e, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:10:0x0026, B:12:0x0043, B:24:0x00c7, B:27:0x00e7, B:29:0x00f9, B:30:0x0108, B:31:0x0125, B:33:0x0146, B:35:0x0163, B:36:0x0172, B:37:0x018f, B:39:0x01a7, B:41:0x01ba, B:42:0x01c9, B:43:0x01e6, B:45:0x01fe, B:47:0x0211, B:48:0x0220, B:18:0x005f, B:19:0x0075, B:20:0x007c, B:21:0x0093, B:22:0x00a9, B:23:0x00b0, B:11:0x0035), top: B:53:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:48:0x0220 A[Catch: Exception -> 0x023e, TRY_LEAVE, TryCatch #0 {Exception -> 0x023e, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:10:0x0026, B:12:0x0043, B:24:0x00c7, B:27:0x00e7, B:29:0x00f9, B:30:0x0108, B:31:0x0125, B:33:0x0146, B:35:0x0163, B:36:0x0172, B:37:0x018f, B:39:0x01a7, B:41:0x01ba, B:42:0x01c9, B:43:0x01e6, B:45:0x01fe, B:47:0x0211, B:48:0x0220, B:18:0x005f, B:19:0x0075, B:20:0x007c, B:21:0x0093, B:22:0x00a9, B:23:0x00b0, B:11:0x0035), top: B:53:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:56:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:30:0x0108, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:36:0x0172, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:42:0x01c9, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:48:0x0220, please report this as an issue */
    @Override // android.os.Handler
    public void handleMessage(Message message) {
        TextView textView;
        float f;
        float f2;
        float f3;
        float f4;
        TextView textView2;
        try {
            if (message.what != 1281) {
                return;
            }
            byte[] bArr = (byte[]) message.obj;
            if (message.arg1 != 56) {
                return;
            }
            h unused = this.f446a.f463a;
            if (h.a(bArr[0], 3)) {
                ((TextView) this.f446a.findViewById(R.id.range_unit)).setText(R.string.mile);
            } else {
                ((TextView) this.f446a.findViewById(R.id.range_unit)).setText(R.string.km);
            }
            h unused2 = this.f446a.f463a;
            int iA = h.a(bArr[0], 0, 2);
            if (iA != 0) {
                if (iA != 1) {
                    if (iA == 2) {
                        ((TextView) this.f446a.findViewById(R.id.rafe_unit)).setText(R.string.mpg_us);
                        textView2 = (TextView) this.f446a.findViewById(R.id.driving_fuel_unit);
                    } else if (iA == 3) {
                        ((TextView) this.f446a.findViewById(R.id.rafe_unit)).setText(R.string.mpg_uk);
                        textView2 = (TextView) this.f446a.findViewById(R.id.driving_fuel_unit);
                    }
                    textView2.setText(R.string.gal);
                } else {
                    ((TextView) this.f446a.findViewById(R.id.rafe_unit)).setText(R.string.km_l);
                    textView = (TextView) this.f446a.findViewById(R.id.driving_fuel_unit);
                }
                f = (((bArr[1] & 255) * 256) + (bArr[2] & 255)) / 10.0f;
                if (this.f446a.f464b != f) {
                    this.f446a.f464b = f;
                    if (((bArr[1] & 255) * 256) + (bArr[2] & 255) == 65535) {
                        ((TextView) this.f446a.findViewById(R.id.rafe_fuel)).setText("--.-");
                    } else {
                        ((TextView) this.f446a.findViewById(R.id.rafe_fuel)).setText(f + "");
                    }
                }
                f2 = ((((bArr[3] & 255) << 16) | ((bArr[4] & 255) << 8)) | (bArr[5] & 255)) / 10.0f;
                if (this.f446a.f465c != f2) {
                    this.f446a.f465c = f2;
                    if ((((bArr[3] & 255) << 16) | ((bArr[4] & 255) << 8) | (bArr[5] & 255)) == 16777215) {
                        ((TextView) this.f446a.findViewById(R.id.tt_mile)).setText("--.-");
                    } else {
                        ((TextView) this.f446a.findViewById(R.id.tt_mile)).setText("" + f2);
                    }
                }
                f3 = (((bArr[6] & 255) << 8) | (bArr[7] & 255)) / 10.0f;
                if (this.f446a.d != f3) {
                    this.f446a.d = f3;
                    if ((((bArr[6] & 255) << 8) | (bArr[7] & 255)) == 65535) {
                        ((TextView) this.f446a.findViewById(R.id.lift_mile)).setText("--.-");
                    } else {
                        ((TextView) this.f446a.findViewById(R.id.lift_mile)).setText("" + f3);
                    }
                }
                f4 = (((bArr[8] & 255) << 8) | (bArr[9] & 255)) / 10.0f;
                if (this.f446a.e != f4) {
                    this.f446a.e = f4;
                    if (((bArr[9] & 255) | ((bArr[8] & 255) << 8)) == 65535) {
                        ((TextView) this.f446a.findViewById(R.id.xfy_speed)).setText("--.-");
                        return;
                    }
                    ((TextView) this.f446a.findViewById(R.id.xfy_speed)).setText("" + f4);
                }
            }
            ((TextView) this.f446a.findViewById(R.id.rafe_unit)).setText(R.string.l_100km);
            textView = (TextView) this.f446a.findViewById(R.id.driving_fuel_unit);
            textView.setText(R.string.l);
            f = (((bArr[1] & 255) * 256) + (bArr[2] & 255)) / 10.0f;
            if (this.f446a.f464b != f) {
                this.f446a.f464b = f;
                if (((bArr[1] & 255) * 256) + (bArr[2] & 255) == 65535) {
                    ((TextView) this.f446a.findViewById(R.id.rafe_fuel)).setText("--.-");
                } else {
                    ((TextView) this.f446a.findViewById(R.id.rafe_fuel)).setText(f + "");
                }
            }
            f2 = ((((bArr[3] & 255) << 16) | ((bArr[4] & 255) << 8)) | (bArr[5] & 255)) / 10.0f;
            if (this.f446a.f465c != f2) {
                this.f446a.f465c = f2;
                if ((((bArr[3] & 255) << 16) | ((bArr[4] & 255) << 8) | (bArr[5] & 255)) == 16777215) {
                    ((TextView) this.f446a.findViewById(R.id.tt_mile)).setText("--.-");
                } else {
                    ((TextView) this.f446a.findViewById(R.id.tt_mile)).setText("" + f2);
                }
            }
            f3 = (((bArr[6] & 255) << 8) | (bArr[7] & 255)) / 10.0f;
            if (this.f446a.d != f3) {
                this.f446a.d = f3;
                if ((((bArr[6] & 255) << 8) | (bArr[7] & 255)) == 65535) {
                    ((TextView) this.f446a.findViewById(R.id.lift_mile)).setText("--.-");
                } else {
                    ((TextView) this.f446a.findViewById(R.id.lift_mile)).setText("" + f3);
                }
            }
            f4 = (((bArr[8] & 255) << 8) | (bArr[9] & 255)) / 10.0f;
            if (this.f446a.e != f4) {
                this.f446a.e = f4;
                if (((bArr[9] & 255) | ((bArr[8] & 255) << 8)) == 65535) {
                    ((TextView) this.f446a.findViewById(R.id.xfy_speed)).setText("--.-");
                    return;
                }
                ((TextView) this.f446a.findViewById(R.id.xfy_speed)).setText("" + f4);
            }
        } catch (Exception e) {
            Log.e("DaciaFuelActivity", Log.getStackTraceString(e));
        }
    }
}
