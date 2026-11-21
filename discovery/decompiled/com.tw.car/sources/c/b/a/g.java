package c.b.a;

import android.os.Handler;
import android.os.Message;
import android.preference.ListPreference;
import android.util.Log;
import com.tw.car.RenaultDaciaCapturActivity;

/* JADX INFO: loaded from: classes.dex */
public class g extends Handler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ RenaultDaciaCapturActivity f450a;

    public g(RenaultDaciaCapturActivity renaultDaciaCapturActivity) {
        this.f450a = renaultDaciaCapturActivity;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        ListPreference listPreference;
        String strValueOf;
        try {
            int i = message.what;
            if (i != 266) {
                if (i != 267) {
                    if (i != 1281) {
                        return;
                    }
                    byte[] bArr = (byte[]) message.obj;
                    int i2 = message.arg1;
                    if (i2 == 37) {
                        listPreference = this.f450a.i;
                        strValueOf = String.valueOf(h.b(bArr[0], 1));
                    } else if (i2 == 51) {
                        listPreference = this.f450a.j;
                        strValueOf = String.valueOf(bArr[0] & 255);
                    } else if (i2 == 64) {
                        this.f450a.k.setChecked(h.a(bArr[4], 0));
                        this.f450a.l.setChecked(h.a(bArr[1], 3));
                        this.f450a.m.setChecked(h.a(bArr[1], 4));
                        this.f450a.n.setChecked(h.a(bArr[1], 5));
                    }
                    listPreference.setValue(strValueOf);
                }
                byte[] bArr2 = (byte[]) message.obj;
                if (bArr2.length >= 12) {
                    try {
                        this.f450a.o.setValue(String.valueOf(bArr2[12] & 255));
                        return;
                    } catch (Exception unused) {
                        return;
                    }
                }
                return;
            }
            if (message.arg1 == 0) {
                String[] strArrSplit = ((String) message.obj).split("-");
                if (h.f453c == -1) {
                    try {
                        String str = strArrSplit[1];
                        String str2 = strArrSplit[2];
                        Integer.parseInt(str.substring(0, str.length()), 16);
                        h.f453c = Integer.parseInt(str2.substring(0, 2), 16);
                        if (str2.length() > 2) {
                            Integer.parseInt(str2.substring(2, 3), 16);
                        }
                        h.d = strArrSplit.length == 6 ? strArrSplit[5] : " ";
                    } catch (Exception unused2) {
                    }
                    if (h.f453c == -1) {
                        h.f453c = 0;
                    }
                }
                h unused3 = this.f450a.f469a;
                if (!h.d.equals("DACIA")) {
                    this.f450a.getPreferenceScreen().removePreference(this.f450a.o);
                }
                h unused4 = this.f450a.f469a;
                if (h.d.equals("NEWDUSTER")) {
                    return;
                }
                this.f450a.getPreferenceScreen().removePreference(this.f450a.k);
                this.f450a.getPreferenceScreen().removePreference(this.f450a.l);
                this.f450a.getPreferenceScreen().removePreference(this.f450a.m);
                this.f450a.getPreferenceScreen().removePreference(this.f450a.n);
            }
        } catch (Exception e) {
            Log.e("RenaultDaciaCapturActivity", Log.getStackTraceString(e));
        }
    }
}
