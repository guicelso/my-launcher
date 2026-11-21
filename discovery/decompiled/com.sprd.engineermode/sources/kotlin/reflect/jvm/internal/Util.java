package kotlin.reflect.jvm.internal;

/* JADX INFO: loaded from: classes4.dex */
class Util {
    public static Object getEnumConstantByName(Class<? extends Enum<?>> enumClass, String name) {
        return Enum.valueOf(enumClass, name);
    }
}
