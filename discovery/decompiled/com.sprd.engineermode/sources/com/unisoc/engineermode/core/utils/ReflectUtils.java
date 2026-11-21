package com.unisoc.engineermode.core.utils;

import android.util.Log;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes4.dex */
public class ReflectUtils {
    private static final String TAG = "REFLECTUTILS";

    public static <T> T getProxyByClassName(String str, Class<T> cls, Object... objArr) {
        try {
            Class<?> cls2 = Class.forName(str);
            try {
                final Object objNewInstance = cls2.getConstructor((Class[]) Arrays.stream(objArr).map(new Function() { // from class: com.unisoc.engineermode.core.utils.-$$Lambda$-MeWj4yjf_ynucI4AO00sz51qRk
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return obj.getClass();
                    }
                }).toArray(new IntFunction() { // from class: com.unisoc.engineermode.core.utils.-$$Lambda$ReflectUtils$ring64AMaYd6gocwrbNfUQbl6qg
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        return ReflectUtils.lambda$getProxyByClassName$0(i);
                    }
                })).newInstance(objArr);
                return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls2}, new InvocationHandler() { // from class: com.unisoc.engineermode.core.utils.-$$Lambda$ReflectUtils$G9zAkt3jRd1cjc4WN7iXn75u88s
                    @Override // java.lang.reflect.InvocationHandler
                    public final Object invoke(Object obj, Method method, Object[] objArr2) {
                        return method.invoke(objNewInstance, objArr2);
                    }
                });
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                Log.e(TAG, String.format("exception when get %s's constructor", str));
                return null;
            }
        } catch (ClassNotFoundException e2) {
            Log.e(TAG, "no such class, " + str);
            return null;
        }
    }

    static /* synthetic */ Class[] lambda$getProxyByClassName$0(int x$0) {
        return new Class[x$0];
    }
}
