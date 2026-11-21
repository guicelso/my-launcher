package com.dofun.carsetting.f;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.bean.VoiceControlBean;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONArray;

/* JADX INFO: compiled from: VoiceManager.java */
/* JADX INFO: loaded from: classes.dex */
public class x {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final String f233a = "x";
    private Context b;
    private VoiceControlBean c;
    private String d;

    /* JADX INFO: compiled from: VoiceManager.java */
    static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        static x f234a = new x();
    }

    public static x a() {
        return a.f234a;
    }

    private x() {
        this.d = Environment.getExternalStorageDirectory().getAbsolutePath();
        this.b = MyApp.a();
        this.c = new VoiceControlBean();
    }

    public String b() {
        String str = "";
        try {
            JSONArray jSONArray = new JSONArray(c());
            for (int i = 0; i < jSONArray.length(); i++) {
                String str2 = str + jSONArray.optJSONObject(i).optString("name");
                try {
                    str = i < jSONArray.length() - 1 ? str2 + "," : str2;
                } catch (Exception e) {
                    e = e;
                    str = str2;
                    e.printStackTrace();
                    return str;
                }
            }
            a(str);
        } catch (Exception e2) {
            e = e2;
        }
        return str;
    }

    public void a(String str) {
        k.a(this.b, "awakeMainWord", str);
    }

    public String c() {
        File file = new File(this.d + "/.ResourcePicture/CarVoice/awake_word");
        StringBuffer stringBuffer = null;
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                StringBuffer stringBuffer2 = new StringBuffer();
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        stringBuffer2.append(line + "\n");
                    } catch (FileNotFoundException e) {
                        e = e;
                        stringBuffer = stringBuffer2;
                        e.printStackTrace();
                    } catch (IOException e2) {
                        e = e2;
                        stringBuffer = stringBuffer2;
                        e.printStackTrace();
                    }
                }
                bufferedReader.close();
                fileInputStream.close();
                stringBuffer = stringBuffer2;
            } catch (FileNotFoundException e3) {
                e = e3;
            } catch (IOException e4) {
                e = e4;
            }
        }
        return TextUtils.isEmpty(stringBuffer) ? "" : stringBuffer.toString();
    }
}
