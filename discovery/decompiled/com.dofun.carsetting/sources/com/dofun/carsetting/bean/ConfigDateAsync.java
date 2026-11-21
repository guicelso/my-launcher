package com.dofun.carsetting.bean;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.dofun.carsetting.b.a;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ConfigDateAsync extends AsyncTask<String, Integer, ArrayList<ConfigBean>> {
    private static final String path_backups = a.a().S + "CarSettingConfig.json";
    private OnCallBack callBack;
    private final Context mContext;

    public interface OnCallBack {
        void onKeyDate(ArrayList<ConfigBean> arrayList);
    }

    public void setOnCallBack(OnCallBack onCallBack) {
        this.callBack = onCallBack;
    }

    public ConfigDateAsync(Context context) {
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public ArrayList<ConfigBean> doInBackground(String... strArr) {
        return paserJson(getJson(this.mContext, strArr[0]));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onProgressUpdate(Integer... numArr) {
        super.onProgressUpdate((Object[]) numArr);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(ArrayList<ConfigBean> arrayList) {
        super.onPostExecute(arrayList);
        if (this.callBack != null) {
            this.callBack.onKeyDate(arrayList);
        }
    }

    public static String getJson(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(str);
            if (!file.exists()) {
                Log.d("HYH", "getJson: CarSettingConfig.json no exists:" + str);
                file = new File(path_backups);
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("HYH", "getJson: CarSettingConfig.json no exists:" + path_backups);
        }
        return sb.toString();
    }

    public ArrayList<ConfigBean> paserJson(String str) {
        ArrayList<ConfigBean> arrayList = new ArrayList<>();
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                ConfigBean configBean = new ConfigBean();
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                configBean.mId = (String) jSONObject.opt("ID");
                configBean.visibility = (String) jSONObject.opt("visibility");
                configBean.isNeedPwd = ((Boolean) jSONObject.opt("isNeedPwd")).booleanValue();
                configBean.strPwd = (String) jSONObject.opt("strPwd");
                configBean.strPwd2 = (String) jSONObject.opt("strPwd2");
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("items");
                ArrayList arrayList2 = new ArrayList();
                configBean.items = arrayList2;
                for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                    ConfigBean configBean2 = new ConfigBean();
                    JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i2);
                    configBean2.mId = (String) jSONObject2.opt("ID");
                    configBean2.visibility = (String) jSONObject2.opt("visibility");
                    configBean2.isNeedPwd = jSONObject2.optBoolean("isNeedPwd");
                    configBean2.strPwd = (String) jSONObject2.opt("strPwd");
                    configBean2.strPwd2 = (String) jSONObject2.opt("strPwd2");
                    arrayList2.add(configBean2);
                }
                arrayList.add(configBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
