package kotlin.reflect.jvm.internal.calls;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: AnnotationConstructorCaller.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\u001aI\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00020\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0000¢\u0006\u0002\u0010\u000b\u001a$\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00072\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0002\u001a\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u00022\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0002¨\u0006\u0014²\u0006\u0014\u0010\u0015\u001a\u00020\u000f\"\b\b\u0000\u0010\u0001*\u00020\u0002X\u008a\u0084\u0002²\u0006\u0014\u0010\u0016\u001a\u00020\u0007\"\b\b\u0000\u0010\u0001*\u00020\u0002X\u008a\u0084\u0002"}, d2 = {"createAnnotationInstance", "T", "", "annotationClass", "Ljava/lang/Class;", "values", "", "", "methods", "", "Ljava/lang/reflect/Method;", "(Ljava/lang/Class;Ljava/util/Map;Ljava/util/List;)Ljava/lang/Object;", "throwIllegalArgumentType", "", "index", "", "name", "expectedJvmType", "transformKotlinToJvm", "expectedType", "kotlin-reflect-api", "hashCode", "toString"}, k = 2, mv = {1, 1, 13})
public final class AnnotationConstructorCallerKt {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinPackage(AnnotationConstructorCallerKt.class, "kotlin-reflect-api"), "hashCode", "<v#0>")), Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinPackage(AnnotationConstructorCallerKt.class, "kotlin-reflect-api"), "toString", "<v#1>"))};

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object transformKotlinToJvm(@Nullable Object $receiver, Class<?> cls) {
        Object result;
        if ($receiver instanceof Class) {
            return null;
        }
        if ($receiver instanceof KClass) {
            result = JvmClassMappingKt.getJavaClass((KClass) $receiver);
        } else if (!($receiver instanceof Object[])) {
            result = $receiver;
        } else {
            if (((Object[]) $receiver) instanceof Class[]) {
                return null;
            }
            if (!(((Object[]) $receiver) instanceof KClass[])) {
                result = (Object[]) $receiver;
            } else {
                if ($receiver == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.reflect.KClass<*>>");
                }
                KClass[] kClassArr = (KClass[]) $receiver;
                Collection destination$iv$iv = new ArrayList(kClassArr.length);
                for (KClass kClass : kClassArr) {
                    destination$iv$iv.add(JvmClassMappingKt.getJavaClass(kClass));
                }
                Collection $receiver$iv = (List) destination$iv$iv;
                Object[] array = $receiver$iv.toArray(new Class[0]);
                if (array == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }
                result = array;
            }
        }
        if (cls.isInstance(result)) {
            return result;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Void throwIllegalArgumentType(int index, String name, Class<?> cls) {
        KClass kotlinClass;
        String typeString;
        if (Intrinsics.areEqual(cls, Class.class)) {
            kotlinClass = Reflection.getOrCreateKotlinClass(KClass.class);
        } else if (cls.isArray() && Intrinsics.areEqual(cls.getComponentType(), Class.class)) {
            kotlinClass = Reflection.getOrCreateKotlinClass(KClass[].class);
        } else {
            kotlinClass = JvmClassMappingKt.getKotlinClass(cls);
        }
        if (Intrinsics.areEqual(kotlinClass.getQualifiedName(), Reflection.getOrCreateKotlinClass(Object[].class).getQualifiedName())) {
            StringBuilder sb = new StringBuilder();
            sb.append(kotlinClass.getQualifiedName());
            sb.append(Typography.less);
            Class<?> componentType = JvmClassMappingKt.getJavaClass(kotlinClass).getComponentType();
            Intrinsics.checkExpressionValueIsNotNull(componentType, "kotlinClass.java.componentType");
            sb.append(JvmClassMappingKt.getKotlinClass(componentType).getQualifiedName());
            sb.append(Typography.greater);
            typeString = sb.toString();
        } else {
            typeString = kotlinClass.getQualifiedName();
        }
        throw new IllegalArgumentException("Argument #" + index + ' ' + name + " is not of the required type " + typeString);
    }

    @NotNull
    public static /* synthetic */ Object createAnnotationInstance$default(Class cls, Map map, List list, int i, Object obj) {
        if ((i & 4) != 0) {
            Iterable $receiver$iv = map.keySet();
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
            for (Object item$iv$iv : $receiver$iv) {
                String name = (String) item$iv$iv;
                destination$iv$iv.add(cls.getDeclaredMethod(name, new Class[0]));
            }
            list = (List) destination$iv$iv;
        }
        return createAnnotationInstance(cls, map, list);
    }

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.calls.AnnotationConstructorCallerKt$createAnnotationInstance$2, reason: invalid class name */
    /* JADX INFO: compiled from: AnnotationConstructorCaller.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"equals", "", "T", "", "other", "invoke"}, k = 3, mv = {1, 1, 13})
    static final class AnonymousClass2 extends Lambda implements Function1<Object, Boolean> {
        final /* synthetic */ Class $annotationClass;
        final /* synthetic */ List $methods;
        final /* synthetic */ Map $values;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Class cls, List list, Map map) {
            super(1);
            this.$annotationClass = cls;
            this.$methods = list;
            this.$values = map;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Boolean invoke(Object obj) {
            return Boolean.valueOf(invoke2(obj));
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final boolean invoke2(@Nullable Object other) throws IllegalAccessException, InvocationTargetException {
            boolean z;
            boolean zAreEqual;
            KClass annotationClass;
            Class javaClass = null;
            Annotation annotation = (Annotation) (!(other instanceof Annotation) ? null : other);
            if (annotation != null && (annotationClass = JvmClassMappingKt.getAnnotationClass(annotation)) != null) {
                javaClass = JvmClassMappingKt.getJavaClass(annotationClass);
            }
            if (Intrinsics.areEqual(javaClass, this.$annotationClass)) {
                Iterable $receiver$iv = this.$methods;
                if (!($receiver$iv instanceof Collection) || !((Collection) $receiver$iv).isEmpty()) {
                    Iterator it = $receiver$iv.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Object element$iv = it.next();
                            Method method = (Method) element$iv;
                            Object ours = this.$values.get(method.getName());
                            Object theirs = method.invoke(other, new Object[0]);
                            if (ours instanceof boolean[]) {
                                boolean[] zArr = (boolean[]) ours;
                                if (theirs == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.BooleanArray");
                                }
                                zAreEqual = Arrays.equals(zArr, (boolean[]) theirs);
                            } else if (ours instanceof char[]) {
                                char[] cArr = (char[]) ours;
                                if (theirs == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharArray");
                                }
                                zAreEqual = Arrays.equals(cArr, (char[]) theirs);
                            } else if (ours instanceof byte[]) {
                                byte[] bArr = (byte[]) ours;
                                if (theirs == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.ByteArray");
                                }
                                zAreEqual = Arrays.equals(bArr, (byte[]) theirs);
                            } else if (ours instanceof short[]) {
                                short[] sArr = (short[]) ours;
                                if (theirs == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.ShortArray");
                                }
                                zAreEqual = Arrays.equals(sArr, (short[]) theirs);
                            } else if (ours instanceof int[]) {
                                int[] iArr = (int[]) ours;
                                if (theirs == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.IntArray");
                                }
                                zAreEqual = Arrays.equals(iArr, (int[]) theirs);
                            } else if (ours instanceof float[]) {
                                float[] fArr = (float[]) ours;
                                if (theirs == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.FloatArray");
                                }
                                zAreEqual = Arrays.equals(fArr, (float[]) theirs);
                            } else if (ours instanceof long[]) {
                                long[] jArr = (long[]) ours;
                                if (theirs == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.LongArray");
                                }
                                zAreEqual = Arrays.equals(jArr, (long[]) theirs);
                            } else if (ours instanceof double[]) {
                                double[] dArr = (double[]) ours;
                                if (theirs == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.DoubleArray");
                                }
                                zAreEqual = Arrays.equals(dArr, (double[]) theirs);
                            } else if (ours instanceof Object[]) {
                                Object[] objArr = (Object[]) ours;
                                if (theirs == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<*>");
                                }
                                zAreEqual = Arrays.equals(objArr, (Object[]) theirs);
                            } else {
                                zAreEqual = Intrinsics.areEqual(ours, theirs);
                            }
                            if (!zAreEqual) {
                                z = false;
                                break;
                            }
                        } else {
                            z = true;
                            break;
                        }
                    }
                } else {
                    z = true;
                }
                if (z) {
                    return true;
                }
            }
            return false;
        }
    }

    @NotNull
    public static final <T> T createAnnotationInstance(@NotNull final Class<T> annotationClass, @NotNull final Map<String, ? extends Object> values, @NotNull List<Method> methods) {
        Intrinsics.checkParameterIsNotNull(annotationClass, "annotationClass");
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.checkParameterIsNotNull(methods, "methods");
        final AnonymousClass2 anonymousClass2 = new AnonymousClass2(annotationClass, methods, values);
        final Lazy lazy = LazyKt.lazy(new Function0<Integer>() { // from class: kotlin.reflect.jvm.internal.calls.AnnotationConstructorCallerKt$createAnnotationInstance$hashCode$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Integer invoke() {
                return Integer.valueOf(invoke2());
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final int invoke2() {
                int valueHash;
                Iterable $receiver$iv = values.entrySet();
                int sum$iv = 0;
                for (Object element$iv : $receiver$iv) {
                    Map.Entry entry = (Map.Entry) element$iv;
                    String key = (String) entry.getKey();
                    Object value = entry.getValue();
                    if (value instanceof boolean[]) {
                        valueHash = Arrays.hashCode((boolean[]) value);
                    } else if (value instanceof char[]) {
                        valueHash = Arrays.hashCode((char[]) value);
                    } else if (value instanceof byte[]) {
                        valueHash = Arrays.hashCode((byte[]) value);
                    } else if (value instanceof short[]) {
                        valueHash = Arrays.hashCode((short[]) value);
                    } else if (value instanceof int[]) {
                        valueHash = Arrays.hashCode((int[]) value);
                    } else if (value instanceof float[]) {
                        valueHash = Arrays.hashCode((float[]) value);
                    } else if (value instanceof long[]) {
                        valueHash = Arrays.hashCode((long[]) value);
                    } else if (value instanceof double[]) {
                        valueHash = Arrays.hashCode((double[]) value);
                    } else {
                        valueHash = value instanceof Object[] ? Arrays.hashCode((Object[]) value) : value.hashCode();
                    }
                    sum$iv += (key.hashCode() * 127) ^ valueHash;
                }
                return sum$iv;
            }
        });
        final KProperty kProperty = $$delegatedProperties[0];
        final Lazy lazy2 = LazyKt.lazy(new Function0<String>() { // from class: kotlin.reflect.jvm.internal.calls.AnnotationConstructorCallerKt$createAnnotationInstance$toString$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final String invoke() {
                StringBuilder $receiver = new StringBuilder();
                $receiver.append('@');
                $receiver.append(annotationClass.getCanonicalName());
                CollectionsKt.joinTo$default(values.entrySet(), $receiver, ", ", "(", ")", 0, null, new Function1<Map.Entry<? extends String, ? extends Object>, String>() { // from class: kotlin.reflect.jvm.internal.calls.AnnotationConstructorCallerKt$createAnnotationInstance$toString$2$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ String invoke(Map.Entry<? extends String, ? extends Object> entry) {
                        return invoke2((Map.Entry<String, ? extends Object>) entry);
                    }

                    @NotNull
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final String invoke2(@NotNull Map.Entry<String, ? extends Object> entry) {
                        String valueString;
                        Intrinsics.checkParameterIsNotNull(entry, "entry");
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        if (value instanceof boolean[]) {
                            valueString = Arrays.toString((boolean[]) value);
                        } else if (value instanceof char[]) {
                            valueString = Arrays.toString((char[]) value);
                        } else if (value instanceof byte[]) {
                            valueString = Arrays.toString((byte[]) value);
                        } else if (value instanceof short[]) {
                            valueString = Arrays.toString((short[]) value);
                        } else if (value instanceof int[]) {
                            valueString = Arrays.toString((int[]) value);
                        } else if (value instanceof float[]) {
                            valueString = Arrays.toString((float[]) value);
                        } else if (value instanceof long[]) {
                            valueString = Arrays.toString((long[]) value);
                        } else if (value instanceof double[]) {
                            valueString = Arrays.toString((double[]) value);
                        } else {
                            valueString = value instanceof Object[] ? Arrays.toString((Object[]) value) : value.toString();
                        }
                        return key + '=' + valueString;
                    }
                }, 48, null);
                String string = $receiver.toString();
                Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
                return string;
            }
        });
        final KProperty kProperty2 = $$delegatedProperties[1];
        T t = (T) Proxy.newProxyInstance(annotationClass.getClassLoader(), new Class[]{annotationClass}, new InvocationHandler() { // from class: kotlin.reflect.jvm.internal.calls.AnnotationConstructorCallerKt$createAnnotationInstance$result$1
            @Override // java.lang.reflect.InvocationHandler
            @Nullable
            public final Object invoke(Object $noName_0, Method method, Object[] args) {
                Intrinsics.checkExpressionValueIsNotNull(method, "method");
                String name = method.getName();
                if (name != null) {
                    int iHashCode = name.hashCode();
                    if (iHashCode != -1776922004) {
                        if (iHashCode != 147696667) {
                            if (iHashCode == 1444986633 && name.equals("annotationType")) {
                                return annotationClass;
                            }
                        } else if (name.equals("hashCode")) {
                            Lazy lazy3 = lazy;
                            KProperty kProperty3 = kProperty;
                            return lazy3.getValue();
                        }
                    } else if (name.equals("toString")) {
                        Lazy lazy4 = lazy2;
                        KProperty kProperty4 = kProperty2;
                        return lazy4.getValue();
                    }
                }
                if (Intrinsics.areEqual(name, "equals") && args != null && args.length == 1) {
                    return Boolean.valueOf(anonymousClass2.invoke2(ArraysKt.single(args)));
                }
                if (values.containsKey(name)) {
                    return values.get(name);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Method is not supported: ");
                sb.append(method);
                sb.append(" (args: ");
                sb.append(ArraysKt.toList(args != null ? args : new Object[0]));
                sb.append(')');
                throw new KotlinReflectionInternalError(sb.toString());
            }
        });
        if (t != null) {
            return t;
        }
        throw new TypeCastException("null cannot be cast to non-null type T");
    }
}
