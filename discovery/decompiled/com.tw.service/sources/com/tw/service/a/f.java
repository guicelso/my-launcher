package com.tw.service.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import android.util.Log;
import com.tw.service.la;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class f {
    private static f Wk;
    private boolean Qk;
    private e Rk;
    private Messenger Sk;
    private String Uk;
    private String Vk;
    private Context mContext;
    private Messenger mService;
    private la pa;
    private ArrayList Tk = new ArrayList();
    private Handler mHandler = new b(this);
    private ServiceConnection mConnection = new c(this);

    private f() {
    }

    static /* synthetic */ String F() {
        return "f";
    }

    static /* synthetic */ Resources a(Context context, File file) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resources resources = context.getResources();
        AssetManager assetManager = (AssetManager) AssetManager.class.newInstance();
        Object[] objArr = {file.getAbsolutePath()};
        Class[] clsArr = new Class[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            clsArr[i] = objArr[i].getClass();
        }
        Method declaredMethod = AssetManager.class.getDeclaredMethod("addAssetPath", clsArr);
        declaredMethod.setAccessible(true);
        declaredMethod.invoke(assetManager, objArr);
        return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i, String str) throws Throwable {
        BufferedWriter bufferedWriter;
        File file;
        boolean z;
        BufferedWriter bufferedWriter2 = null;
        try {
            try {
                try {
                    if (new File("/twdataconfig").exists()) {
                        file = new File("/twdataconfig/UIAuthorization");
                    } else {
                        File file2 = new File("/sdcard/.Tcfg");
                        if (!file2.exists()) {
                            file2.mkdir();
                        }
                        file = new File("/sdcard/.Tcfg/UIAuthorization");
                    }
                    bufferedWriter = new BufferedWriter(new FileWriter(file));
                    try {
                        try {
                            Log.e("HYH", "UIAuthorizationCheck->saveAuthorizationStation:authorizationList=" + this.Tk.size());
                            int i2 = 0;
                            while (true) {
                                if (i2 >= this.Tk.size()) {
                                    z = false;
                                    break;
                                }
                                String str2 = (String) this.Tk.get(i2);
                                String strOptString = "";
                                try {
                                    JSONObject jSONObject = new JSONObject(!TextUtils.isEmpty(str2) ? a.b.a.a.decrypt(str2) : "");
                                    if (jSONObject.has("softwareType")) {
                                        strOptString = jSONObject.optString("softwareType");
                                    } else {
                                        this.Tk.remove(i2);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    this.Tk.remove(i2);
                                }
                                if (TextUtils.equals(strOptString, this.Uk)) {
                                    if (i != 1) {
                                        this.Tk.remove(i2);
                                    }
                                    z = true;
                                    break;
                                }
                                i2++;
                            }
                            if (!z && !TextUtils.isEmpty(str)) {
                                this.Tk.add(str);
                            }
                            for (int i3 = 0; i3 < this.Tk.size(); i3++) {
                                bufferedWriter.write((String) this.Tk.get(i3));
                                bufferedWriter.write("\n");
                            }
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (Exception e2) {
                            e = e2;
                            bufferedWriter2 = bufferedWriter;
                            Log.e("HYH", "UIAuthorizationCheck->saveAuthorizationStation:" + e.toString());
                            e.printStackTrace();
                            if (bufferedWriter2 != null) {
                                bufferedWriter2.close();
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedWriter = null;
                }
            } catch (Exception e3) {
                e = e3;
            }
            FileUtils.setPermissions("/twdataconfig/UIAuthorization", 438, -1, -1);
            this.pa.write(40730, 1, 0, "sync");
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    public static f getInstance() {
        if (Wk == null) {
            Wk = new f();
        }
        return Wk;
    }

    public void a(a aVar) {
        new d(this, aVar).start();
    }

    public void a(e eVar) {
        this.Rk = eVar;
    }

    public void pa() {
        Log.e("f", "=bind====================================");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.tw.net", "com.tw.net.uiauthorize.AuthorizationService"));
        intent.setAction("android.intent.action.tw.uiauthorize");
        intent.putExtra("softwareType", this.Uk);
        this.mContext.startService(intent);
        this.mContext.bindService(intent, this.mConnection, 1);
    }

    public boolean qa() throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                File file = new File("/twdataconfig").exists() ? new File("/twdataconfig/UIAuthorization") : new File("/sdcard/.Tcfg/UIAuthorization");
                bufferedReader = new BufferedReader(new FileReader(file));
                try {
                    this.Tk.clear();
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (!TextUtils.isEmpty(line)) {
                            this.Tk.add(line);
                        }
                    }
                    Log.e("HYH", "UIAuthorizationCheck->getAuthorizationStation:" + file.canRead() + "," + this.Tk.size());
                    Iterator it = this.Tk.iterator();
                    while (it.hasNext()) {
                        if (a.b.a.a.a(a.b.a.a.decrypt((String) it.next()), this.Uk, this.Vk)) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return true;
                        }
                    }
                    try {
                        bufferedReader.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    return false;
                } catch (Exception e3) {
                    e = e3;
                    bufferedReader2 = bufferedReader;
                    Log.e("HYH", "UIAuthorizationCheck->getAuthorizationStation:" + e.toString());
                    e.printStackTrace();
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = bufferedReader2;
        }
    }

    public void ra() {
        if (this.mService != null) {
            Message messageObtain = Message.obtain(this.mHandler, 65281, 0, 0);
            messageObtain.replyTo = this.Sk;
            try {
                this.mService.send(messageObtain);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.Qk = true;
        pa();
    }

    public void a(Context context, la laVar, String str) throws Throwable {
        String line;
        this.pa = laVar;
        this.mContext = context;
        this.Uk = str;
        this.Sk = new Messenger(this.mHandler);
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/sys/class/block/mmcblk0/device/cid"));
                try {
                    line = bufferedReader2.readLine();
                    BufferedReader bufferedReader3 = new BufferedReader(new FileReader("/sys/firmware/devicetree/base/serial-number"));
                    try {
                        String line2 = bufferedReader3.readLine();
                        String strSubstring = line2.length() > 64 ? line2.substring(0, 64) : line2.substring(0, line2.length() - 1);
                        if (TextUtils.equals(line, strSubstring.substring(0, 32))) {
                            bufferedReader3.close();
                        } else {
                            bufferedReader3.close();
                            line = strSubstring;
                        }
                    } catch (Exception unused) {
                        bufferedReader = bufferedReader3;
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        line = "00000000000000000000000000000000";
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader3;
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        throw th;
                    }
                } catch (Exception unused2) {
                    bufferedReader = bufferedReader2;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                }
            } catch (Exception unused3) {
                line = "00000000000000000000000000000000";
            }
        } catch (Exception unused4) {
        } catch (Throwable th3) {
            th = th3;
        }
        this.Vk = line;
    }
}
