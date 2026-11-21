package kotlin.reflect.jvm;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.calls.Caller;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: KCallablesJvm.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\",\u0010\u0002\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0002\u0010\u0004\"\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"value", "", "isAccessible", "Lkotlin/reflect/KCallable;", "(Lkotlin/reflect/KCallable;)Z", "setAccessible", "(Lkotlin/reflect/KCallable;Z)V", "kotlin-reflect-api"}, k = 2, mv = {1, 1, 13})
@JvmName(name = "KCallablesJvm")
public final class KCallablesJvm {
    public static final boolean isAccessible(@NotNull KCallable<?> receiver$0) {
        Caller<?> defaultCaller;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (receiver$0 instanceof KMutableProperty) {
            Field javaField = ReflectJvmMapping.getJavaField((KProperty) receiver$0);
            if (!(javaField != null ? javaField.isAccessible() : true)) {
                return false;
            }
            Method javaGetter = ReflectJvmMapping.getJavaGetter((KProperty) receiver$0);
            if (!(javaGetter != null ? javaGetter.isAccessible() : true)) {
                return false;
            }
            Method javaSetter = ReflectJvmMapping.getJavaSetter((KMutableProperty) receiver$0);
            return javaSetter != null ? javaSetter.isAccessible() : true;
        }
        if (receiver$0 instanceof KProperty) {
            Field javaField2 = ReflectJvmMapping.getJavaField((KProperty) receiver$0);
            if (!(javaField2 != null ? javaField2.isAccessible() : true)) {
                return false;
            }
            Method javaGetter2 = ReflectJvmMapping.getJavaGetter((KProperty) receiver$0);
            return javaGetter2 != null ? javaGetter2.isAccessible() : true;
        }
        if (receiver$0 instanceof KProperty.Getter) {
            Field javaField3 = ReflectJvmMapping.getJavaField(((KProperty.Getter) receiver$0).getProperty());
            if (!(javaField3 != null ? javaField3.isAccessible() : true)) {
                return false;
            }
            Method javaMethod = ReflectJvmMapping.getJavaMethod((KFunction) receiver$0);
            return javaMethod != null ? javaMethod.isAccessible() : true;
        }
        if (receiver$0 instanceof KMutableProperty.Setter) {
            Field javaField4 = ReflectJvmMapping.getJavaField(((KMutableProperty.Setter) receiver$0).getProperty());
            if (!(javaField4 != null ? javaField4.isAccessible() : true)) {
                return false;
            }
            Method javaMethod2 = ReflectJvmMapping.getJavaMethod((KFunction) receiver$0);
            return javaMethod2 != null ? javaMethod2.isAccessible() : true;
        }
        if (receiver$0 instanceof KFunction) {
            Method javaMethod3 = ReflectJvmMapping.getJavaMethod((KFunction) receiver$0);
            if (!(javaMethod3 != null ? javaMethod3.isAccessible() : true)) {
                return false;
            }
            KCallableImpl<?> kCallableImplAsKCallableImpl = UtilKt.asKCallableImpl(receiver$0);
            Object objMo371getMember = (kCallableImplAsKCallableImpl == null || (defaultCaller = kCallableImplAsKCallableImpl.getDefaultCaller()) == null) ? null : defaultCaller.mo371getMember();
            if (!(objMo371getMember instanceof AccessibleObject)) {
                objMo371getMember = null;
            }
            AccessibleObject accessibleObject = (AccessibleObject) objMo371getMember;
            if (!(accessibleObject != null ? accessibleObject.isAccessible() : true)) {
                return false;
            }
            Constructor javaConstructor = ReflectJvmMapping.getJavaConstructor((KFunction) receiver$0);
            return javaConstructor != null ? javaConstructor.isAccessible() : true;
        }
        throw new UnsupportedOperationException("Unknown callable: " + receiver$0 + " (" + receiver$0.getClass() + ')');
    }

    public static final void setAccessible(@NotNull KCallable<?> receiver$0, boolean value) {
        Caller<?> defaultCaller;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (receiver$0 instanceof KMutableProperty) {
            Field javaField = ReflectJvmMapping.getJavaField((KProperty) receiver$0);
            if (javaField != null) {
                javaField.setAccessible(value);
            }
            Method javaGetter = ReflectJvmMapping.getJavaGetter((KProperty) receiver$0);
            if (javaGetter != null) {
                javaGetter.setAccessible(value);
            }
            Method javaSetter = ReflectJvmMapping.getJavaSetter((KMutableProperty) receiver$0);
            if (javaSetter != null) {
                javaSetter.setAccessible(value);
                return;
            }
            return;
        }
        if (receiver$0 instanceof KProperty) {
            Field javaField2 = ReflectJvmMapping.getJavaField((KProperty) receiver$0);
            if (javaField2 != null) {
                javaField2.setAccessible(value);
            }
            Method javaGetter2 = ReflectJvmMapping.getJavaGetter((KProperty) receiver$0);
            if (javaGetter2 != null) {
                javaGetter2.setAccessible(value);
                return;
            }
            return;
        }
        if (receiver$0 instanceof KProperty.Getter) {
            Field javaField3 = ReflectJvmMapping.getJavaField(((KProperty.Getter) receiver$0).getProperty());
            if (javaField3 != null) {
                javaField3.setAccessible(value);
            }
            Method javaMethod = ReflectJvmMapping.getJavaMethod((KFunction) receiver$0);
            if (javaMethod != null) {
                javaMethod.setAccessible(value);
                return;
            }
            return;
        }
        if (receiver$0 instanceof KMutableProperty.Setter) {
            Field javaField4 = ReflectJvmMapping.getJavaField(((KMutableProperty.Setter) receiver$0).getProperty());
            if (javaField4 != null) {
                javaField4.setAccessible(value);
            }
            Method javaMethod2 = ReflectJvmMapping.getJavaMethod((KFunction) receiver$0);
            if (javaMethod2 != null) {
                javaMethod2.setAccessible(value);
                return;
            }
            return;
        }
        if (receiver$0 instanceof KFunction) {
            Method javaMethod3 = ReflectJvmMapping.getJavaMethod((KFunction) receiver$0);
            if (javaMethod3 != null) {
                javaMethod3.setAccessible(value);
            }
            KCallableImpl<?> kCallableImplAsKCallableImpl = UtilKt.asKCallableImpl(receiver$0);
            Object objMo371getMember = (kCallableImplAsKCallableImpl == null || (defaultCaller = kCallableImplAsKCallableImpl.getDefaultCaller()) == null) ? null : defaultCaller.mo371getMember();
            if (!(objMo371getMember instanceof AccessibleObject)) {
                objMo371getMember = null;
            }
            AccessibleObject accessibleObject = (AccessibleObject) objMo371getMember;
            if (accessibleObject != null) {
                accessibleObject.setAccessible(true);
            }
            Constructor javaConstructor = ReflectJvmMapping.getJavaConstructor((KFunction) receiver$0);
            if (javaConstructor != null) {
                javaConstructor.setAccessible(value);
                return;
            }
            return;
        }
        throw new UnsupportedOperationException("Unknown callable: " + receiver$0 + " (" + receiver$0.getClass() + ')');
    }
}
