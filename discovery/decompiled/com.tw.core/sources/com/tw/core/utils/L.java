package com.tw.core.utils;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.tw.core.app.DoFunApplication;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class L {
    public static int dj(Context context) {
        if (Build.VERSION.SDK_INT < 22) {
            return 0;
        }
        try {
            Class<?> cls = Class.forName("android.telephony.SubscriptionManager");
            Method declaredMethod = cls.getDeclaredMethod("from", Context.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, context);
            Method declaredMethod2 = cls.getDeclaredMethod("getActiveSubscriptionInfoCount", new Class[0]);
            declaredMethod2.setAccessible(true);
            return ((Integer) declaredMethod2.invoke(objInvoke, new Object[0])).intValue();
        } catch (Exception e) {
            C0065s.bL("SimCardUtil", e);
            return 0;
        }
    }

    public static List dk() {
        ArrayList arrayList;
        boolean zBooleanValue;
        Context contextNu = DoFunApplication.nu();
        if (Build.VERSION.SDK_INT < 22) {
            return null;
        }
        try {
            Class<?> cls = Class.forName("android.telephony.SubscriptionManager");
            Method declaredMethod = cls.getDeclaredMethod("from", Context.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, contextNu);
            Method declaredMethod2 = cls.getDeclaredMethod("getActiveSubscriptionInfoList", new Class[0]);
            declaredMethod2.setAccessible(true);
            List list = (List) declaredMethod2.invoke(objInvoke, new Object[0]);
            TelephonyManager telephonyManager = (TelephonyManager) contextNu.getSystemService("phone");
            if (list != null) {
                ArrayList arrayList2 = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    try {
                        Object obj = list.get(i);
                        Method declaredMethod3 = obj.getClass().getDeclaredMethod("getSimSlotIndex", new Class[0]);
                        declaredMethod3.setAccessible(true);
                        int iIntValue = ((Integer) declaredMethod3.invoke(obj, new Object[0])).intValue();
                        Method declaredMethod4 = obj.getClass().getDeclaredMethod("getIccId", new Class[0]);
                        declaredMethod4.setAccessible(true);
                        String str = (String) declaredMethod4.invoke(obj, new Object[0]);
                        Method declaredMethod5 = obj.getClass().getDeclaredMethod("getSubscriptionId", new Class[0]);
                        declaredMethod5.setAccessible(true);
                        int iIntValue2 = ((Integer) declaredMethod5.invoke(obj, new Object[0])).intValue();
                        boolean z = iIntValue2 == m1do(contextNu).intValue();
                        if (Build.VERSION.SDK_INT >= 26) {
                            Method method = TelephonyManager.class.getMethod("getSimState", Integer.TYPE);
                            method.setAccessible(true);
                            zBooleanValue = ((Integer) method.invoke(telephonyManager, Integer.valueOf(iIntValue))).intValue() == 5;
                        } else {
                            Method method2 = TelephonyManager.class.getMethod("isSimStandby", Integer.TYPE);
                            method2.setAccessible(true);
                            zBooleanValue = ((Boolean) method2.invoke(telephonyManager, Integer.valueOf(iIntValue))).booleanValue();
                        }
                        com.tw.core.a.d dVar = new com.tw.core.a.d(str, iIntValue, z);
                        dVar.eJ(zBooleanValue);
                        dVar.eK(iIntValue2);
                        arrayList2.add(dVar);
                    } catch (Exception e) {
                        e = e;
                        arrayList = arrayList2;
                        C0065s.bL("SimCardUtil", e);
                        return arrayList;
                    }
                }
                arrayList = arrayList2;
            } else {
                arrayList = null;
            }
            try {
                B.ci("SimCardUtil", "querySimInfo: %s", arrayList);
                return arrayList;
            } catch (Exception e2) {
                e = e2;
                C0065s.bL("SimCardUtil", e);
                return arrayList;
            }
        } catch (Exception e3) {
            e = e3;
            arrayList = null;
        }
    }

    public static String dl(Context context, String str) {
        if (!com.tw.core.b.a.fE(context)) {
            return null;
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            C0065s.bK("SimCardUtil", "querySimCardType net close");
            return null;
        }
        String strAu = C0058l.au(com.tw.core.d.a.gT + "iccid=" + str, null);
        B.ci("SimCardUtil", "queryDoFunCard response: %s", strAu);
        if (TextUtils.isEmpty(strAu)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(strAu);
            if ("0".equals(jSONObject.optString("code"))) {
                return "DoFunCard";
            }
            if ("-1".equals(jSONObject.optString("code"))) {
                return "UnDoFunCard";
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void dm(Context context, int i, boolean z) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (Build.VERSION.SDK_INT < 26) {
                Method method = TelephonyManager.class.getMethod("setSimStandby", Integer.TYPE, Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(telephonyManager, Integer.valueOf(i), Boolean.valueOf(z));
            }
        } catch (Exception e) {
            C0065s.bL("SimCardUtil", e);
        }
    }

    public static void dn(Context context, int i, int i2) {
        try {
            if (dq(context, i2)) {
                return;
            }
            B.ci("SimCardUtil", "通过切数据方式切卡", new Object[0]);
            Method declaredMethod = Class.forName("android.telephony.SubscriptionManager").getDeclaredMethod("from", Context.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, context);
            objInvoke.getClass().getMethod("setDefaultDataSubId", Integer.TYPE).invoke(objInvoke, Integer.valueOf(i));
            dp(true, context, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: do, reason: not valid java name */
    public static Integer m1do(Context context) {
        int iValueOf = -1;
        try {
            Method declaredMethod = Class.forName("android.telephony.SubscriptionManager").getDeclaredMethod("from", Context.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, context);
            Method method = objInvoke.getClass().getMethod("getDefaultDataSubId", new Class[0]);
            return method != null ? Integer.valueOf(((Integer) method.invoke(objInvoke, new Object[0])).intValue()) : iValueOf;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return iValueOf;
        } catch (NoSuchMethodException e2) {
            try {
                Method declaredMethod2 = Class.forName("android.telephony.SubscriptionManager").getDeclaredMethod("from", Context.class);
                declaredMethod2.setAccessible(true);
                Object objInvoke2 = declaredMethod2.invoke(null, context);
                Method method2 = objInvoke2.getClass().getMethod("getDefaultDataSubscriptionId", new Class[0]);
                return method2 != null ? Integer.valueOf(((Integer) method2.invoke(objInvoke2, new Object[0])).intValue()) : iValueOf;
            } catch (IllegalAccessException e3) {
                e2.printStackTrace();
                return iValueOf;
            } catch (NoSuchMethodException e4) {
                try {
                    Method declaredMethod3 = Class.forName("android.telephony.SubscriptionManager").getDeclaredMethod("from", Context.class);
                    declaredMethod3.setAccessible(true);
                    Object objInvoke3 = declaredMethod3.invoke(null, context);
                    Method method3 = objInvoke3.getClass().getMethod("getDefaultDataPhoneId", new Class[0]);
                    if (method3 == null) {
                        return iValueOf;
                    }
                    iValueOf = Integer.valueOf(((Integer) method3.invoke(objInvoke3, new Object[0])).intValue());
                    B.ci("SimCardUtil", ((Integer) method3.invoke(objInvoke3, new Object[0])).intValue() + "", new Object[0]);
                    return iValueOf;
                } catch (IllegalAccessException e5) {
                    e2.printStackTrace();
                    return iValueOf;
                } catch (NoSuchMethodException e6) {
                    e2.printStackTrace();
                    return iValueOf;
                } catch (InvocationTargetException e7) {
                    e2.printStackTrace();
                    return iValueOf;
                } catch (Exception e8) {
                    e2.printStackTrace();
                    return iValueOf;
                }
            } catch (InvocationTargetException e9) {
                e2.printStackTrace();
                return iValueOf;
            } catch (Exception e10) {
                e2.printStackTrace();
                return iValueOf;
            }
        } catch (InvocationTargetException e11) {
            e11.printStackTrace();
            return iValueOf;
        } catch (Exception e12) {
            e12.printStackTrace();
            return iValueOf;
        }
    }

    public static void dp(boolean z, Context context, int i) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            Method declaredMethod = telephonyManager.getClass().getDeclaredMethod("setDataEnabled", Integer.TYPE, Boolean.TYPE);
            if (declaredMethod != null) {
                declaredMethod.invoke(telephonyManager, Integer.valueOf(i), Boolean.valueOf(z));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean dq(Context context, int i) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        try {
            Method declaredMethod = telephonyManager.getClass().getDeclaredMethod("setPrimaryCard", Integer.TYPE);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(telephonyManager, Integer.valueOf(i));
            B.ci("SimCardUtil", "通过切主卡方式切卡", new Object[0]);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
