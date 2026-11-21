package com.unisoc.engineermode.core.factory;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: Config.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0017B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004H\u0002J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J\u0012\u0010\u0014\u001a\u00020\u00072\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0004J\u0014\u0010\u0016\u001a\u00020\u00072\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u0018"}, d2 = {"Lcom/unisoc/engineermode/core/factory/Config;", "", "()V", "CONFIG_FILENAME", "", "TAG", "data", "Lcom/unisoc/engineermode/core/factory/Config$ConfigData;", "getData", "()Lcom/unisoc/engineermode/core/factory/Config$ConfigData;", "setData", "(Lcom/unisoc/engineermode/core/factory/Config$ConfigData;)V", "getJSONResource", "Lorg/json/JSONObject;", "jsonFileName", "getRawTextResource", "resId", "", "getTextResource", "fileName", "init", "json", "load", "ConfigData", "name"}, k = 1, mv = {1, 1, 13})
public final class Config {
    private static final String CONFIG_FILENAME = "config.json";
    public static final Config INSTANCE = new Config();
    private static final String TAG = "Config";

    @NotNull
    public static ConfigData data;

    private Config() {
    }

    /* JADX INFO: compiled from: Config.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\"\u0010\u0002\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\u0002\u0010\u0006J%\u0010\n\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004`\u0005HÆ\u0003J/\u0010\u000b\u001a\u00020\u00002$\b\u0002\u0010\u0002\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004`\u0005HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\b\u0010\u0011\u001a\u00020\u0004H\u0016R6\u0010\u0002\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004`\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0006¨\u0006\u0012"}, d2 = {"Lcom/unisoc/engineermode/core/factory/Config$ConfigData;", "", "properties", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "(Ljava/util/HashMap;)V", "getProperties", "()Ljava/util/HashMap;", "setProperties", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "name"}, k = 1, mv = {1, 1, 13})
    public static final /* data */ class ConfigData {

        @NotNull
        private HashMap<String, String> properties;

        /* JADX WARN: Multi-variable type inference failed */
        @NotNull
        public static /* synthetic */ ConfigData copy$default(ConfigData configData, HashMap map, int i, Object obj) {
            if ((i & 1) != 0) {
                map = configData.properties;
            }
            return configData.copy(map);
        }

        @NotNull
        public final HashMap<String, String> component1() {
            return this.properties;
        }

        @NotNull
        public final ConfigData copy(@NotNull HashMap<String, String> properties) {
            Intrinsics.checkParameterIsNotNull(properties, "properties");
            return new ConfigData(properties);
        }

        public boolean equals(@Nullable Object other) {
            if (this != other) {
                return (other instanceof ConfigData) && Intrinsics.areEqual(this.properties, ((ConfigData) other).properties);
            }
            return true;
        }

        public int hashCode() {
            HashMap<String, String> map = this.properties;
            if (map != null) {
                return map.hashCode();
            }
            return 0;
        }

        public ConfigData(@NotNull HashMap<String, String> properties) {
            Intrinsics.checkParameterIsNotNull(properties, "properties");
            this.properties = properties;
        }

        @NotNull
        public final HashMap<String, String> getProperties() {
            return this.properties;
        }

        public final void setProperties(@NotNull HashMap<String, String> map) {
            Intrinsics.checkParameterIsNotNull(map, "<set-?>");
            this.properties = map;
        }

        @NotNull
        public String toString() {
            return "ConfigData(properties=" + this.properties + ')';
        }
    }

    @NotNull
    public final ConfigData getData() {
        ConfigData configData = data;
        if (configData == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
        }
        return configData;
    }

    public final void setData(@NotNull ConfigData configData) {
        Intrinsics.checkParameterIsNotNull(configData, "<set-?>");
        data = configData;
    }

    @NotNull
    public static /* synthetic */ ConfigData init$default(Config config, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = (String) null;
        }
        return config.init(str);
    }

    @NotNull
    public final ConfigData init(@Nullable String json) {
        data = load(json);
        ConfigData configData = data;
        if (configData == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
        }
        return configData;
    }

    static /* synthetic */ ConfigData load$default(Config config, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = (String) null;
        }
        return config.load(str);
    }

    private final ConfigData load(String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json != null ? json : getRawTextResource(R.raw.config));
        ConfigData data2 = new ConfigData(new HashMap());
        HashMap<String, String> properties = data2.getProperties();
        String str = Build.VERSION.RELEASE;
        Intrinsics.checkExpressionValueIsNotNull(str, "Build.VERSION.RELEASE");
        properties.put(PropertyKeys.ANDROID_VERSION, str);
        HashMap<String, String> properties2 = data2.getProperties();
        String str2 = Build.PRODUCT;
        Intrinsics.checkExpressionValueIsNotNull(str2, "Build.PRODUCT");
        properties2.put(PropertyKeys.PRODUCT, str2);
        HashMap<String, String> properties3 = data2.getProperties();
        String str3 = Build.BOARD;
        Intrinsics.checkExpressionValueIsNotNull(str3, "Build.BOARD");
        properties3.put(PropertyKeys.BOARD, str3);
        HashMap<String, String> properties4 = data2.getProperties();
        String str4 = Build.HARDWARE;
        Intrinsics.checkExpressionValueIsNotNull(str4, "Build.HARDWARE");
        properties4.put(PropertyKeys.HARDWARE, str4);
        Iterator<String> itKeys = jsonObj.keys();
        while (itKeys.hasNext()) {
            String key = itKeys.next();
            Object value = jsonObj.get(key);
            HashMap<String, String> properties5 = data2.getProperties();
            Intrinsics.checkExpressionValueIsNotNull(key, "key");
            properties5.put(key, value.toString());
        }
        Log.d(TAG, "config: " + data2);
        return data2;
    }

    private final JSONObject getJSONResource(String jsonFileName) throws IOException {
        Context context = ImplementationFactory.INSTANCE.getContext();
        if (context == null) {
            Intrinsics.throwNpe();
        }
        InputStream inputStreamOpen = context.getAssets().open(jsonFileName);
        Intrinsics.checkExpressionValueIsNotNull(inputStreamOpen, "ImplementationFactory.co…assets.open(jsonFileName)");
        Reader inputStreamReader = new InputStreamReader(inputStreamOpen, Charsets.UTF_8);
        BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        Throwable th = (Throwable) null;
        try {
            BufferedReader it = bufferedReader;
            String json = TextStreamsKt.readText(it);
            JSONObject jSONObject = new JSONObject(json);
            CloseableKt.closeFinally(bufferedReader, th);
            return jSONObject;
        } catch (Throwable th2) {
            try {
                throw th2;
            } catch (Throwable th3) {
                CloseableKt.closeFinally(bufferedReader, th2);
                throw th3;
            }
        }
    }

    private final String getTextResource(String fileName) throws IOException {
        Context context = ImplementationFactory.INSTANCE.getContext();
        if (context == null) {
            Intrinsics.throwNpe();
        }
        InputStream inputStreamOpen = context.getAssets().open(fileName);
        Intrinsics.checkExpressionValueIsNotNull(inputStreamOpen, "ImplementationFactory.co…t!!.assets.open(fileName)");
        Reader inputStreamReader = new InputStreamReader(inputStreamOpen, Charsets.UTF_8);
        BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        Throwable th = (Throwable) null;
        try {
            BufferedReader it = bufferedReader;
            String text = TextStreamsKt.readText(it);
            CloseableKt.closeFinally(bufferedReader, th);
            return text;
        } catch (Throwable th2) {
            try {
                throw th2;
            } catch (Throwable th3) {
                CloseableKt.closeFinally(bufferedReader, th2);
                throw th3;
            }
        }
    }

    private final String getRawTextResource(int resId) {
        Context context = ImplementationFactory.INSTANCE.getContext();
        if (context == null) {
            Intrinsics.throwNpe();
        }
        InputStream inputStreamOpenRawResource = context.getResources().openRawResource(resId);
        Intrinsics.checkExpressionValueIsNotNull(inputStreamOpenRawResource, "ImplementationFactory.co…es.openRawResource(resId)");
        Reader inputStreamReader = new InputStreamReader(inputStreamOpenRawResource, Charsets.UTF_8);
        BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        Throwable th = (Throwable) null;
        try {
            BufferedReader it = bufferedReader;
            String text = TextStreamsKt.readText(it);
            CloseableKt.closeFinally(bufferedReader, th);
            return text;
        } catch (Throwable th2) {
            try {
                throw th2;
            } catch (Throwable th3) {
                CloseableKt.closeFinally(bufferedReader, th2);
                throw th3;
            }
        }
    }
}
