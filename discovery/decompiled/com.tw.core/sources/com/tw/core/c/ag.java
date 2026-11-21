package com.tw.core.c;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import com.tw.bt.ContactCoordinateHelper;
import com.tw.core.utils.C0060n;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0070x;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class ag implements Runnable {
    final /* synthetic */ C0046z gG;
    final /* synthetic */ Intent gH;

    ag(C0046z c0046z, Intent intent) {
        this.gG = c0046z;
        this.gH = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            String stringExtra = this.gH.getStringExtra("ContactsZipSyncSuccess");
            com.tw.core.utils.B.ci("PhoneContactUpload", "contact %s", stringExtra);
            if (stringExtra != null) {
                try {
                    this.gG.fx = new JSONArray(com.tw.core.utils.N.dx(stringExtra));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            } else {
                ArrayList<ContactCoordinateHelper> parcelableArrayListExtra = this.gH.getParcelableArrayListExtra("ContactsSyncSuccess");
                com.tw.core.utils.B.ci("PhoneContactUpload", "contact %s", parcelableArrayListExtra);
                if (parcelableArrayListExtra != null && parcelableArrayListExtra.size() > 0) {
                    this.gG.fx = new JSONArray();
                    for (ContactCoordinateHelper contactCoordinateHelper : parcelableArrayListExtra) {
                        try {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("name", contactCoordinateHelper.mName);
                            jSONObject.put("phone", contactCoordinateHelper.mNumber);
                            this.gG.fx.put(jSONObject);
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
            if (this.gG.fx == null || this.gG.fx.length() <= 0) {
                return;
            }
            com.tw.core.utils.B.ci("PhoneContactUpload", "联系人同步成功 %s", this.gG.fx);
            try {
                if (TextUtils.equals(C0064r.bz(this.gG.fA, "contact_data", null), this.gG.fx.toString())) {
                    com.tw.core.utils.B.cj("PhoneContactUpload", "no report", new Object[0]);
                    return;
                }
                String strAM = C0060n.aM(com.tw.core.utils.N.dw(this.gG.fx.toString()), C0060n.aL());
                if (strAM != null) {
                    try {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("cid", com.tw.core.b.f.gr());
                        jSONObject2.put("address_book", strAM);
                        jSONObject2.put("key", C0070x.ca(C0060n.aL()));
                        this.gG.oe("0205" + Base64.encodeToString(jSONObject2.toString().getBytes(), 8), new ah(this));
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
