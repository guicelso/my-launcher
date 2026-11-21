package kotlin.reflect.jvm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KType;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KPackageImpl;
import kotlin.reflect.jvm.internal.KPropertyImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.components.ReflectKotlinClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ReflectJvmMapping.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000J\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010%\u001a\u0004\u0018\u00010&*\u00020'H\u0002\"/\u0010\u0000\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00038F¢\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"\u001b\u0010\b\u001a\u0004\u0018\u00010\t*\u0006\u0012\u0002\b\u00030\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u001b\u0010\r\u001a\u0004\u0018\u00010\u000e*\u0006\u0012\u0002\b\u00030\n8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\"\u001b\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u0006\u0012\u0002\b\u00030\u00038F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\"\u001b\u0010\u0014\u001a\u0004\u0018\u00010\u000e*\u0006\u0012\u0002\b\u00030\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017\"\u0015\u0010\u0018\u001a\u00020\u0019*\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c\"-\u0010\u001d\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0003\"\b\b\u0000\u0010\u0002*\u00020\u001e*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 \"\u001b\u0010\u001d\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003*\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u001f\u0010!\"\u001b\u0010\"\u001a\b\u0012\u0002\b\u0003\u0018\u00010\n*\u00020\t8F¢\u0006\u0006\u001a\u0004\b#\u0010$¨\u0006("}, d2 = {"javaConstructor", "Ljava/lang/reflect/Constructor;", "T", "Lkotlin/reflect/KFunction;", "javaConstructor$annotations", "(Lkotlin/reflect/KFunction;)V", "getJavaConstructor", "(Lkotlin/reflect/KFunction;)Ljava/lang/reflect/Constructor;", "javaField", "Ljava/lang/reflect/Field;", "Lkotlin/reflect/KProperty;", "getJavaField", "(Lkotlin/reflect/KProperty;)Ljava/lang/reflect/Field;", "javaGetter", "Ljava/lang/reflect/Method;", "getJavaGetter", "(Lkotlin/reflect/KProperty;)Ljava/lang/reflect/Method;", "javaMethod", "getJavaMethod", "(Lkotlin/reflect/KFunction;)Ljava/lang/reflect/Method;", "javaSetter", "Lkotlin/reflect/KMutableProperty;", "getJavaSetter", "(Lkotlin/reflect/KMutableProperty;)Ljava/lang/reflect/Method;", "javaType", "Ljava/lang/reflect/Type;", "Lkotlin/reflect/KType;", "getJavaType", "(Lkotlin/reflect/KType;)Ljava/lang/reflect/Type;", "kotlinFunction", "", "getKotlinFunction", "(Ljava/lang/reflect/Constructor;)Lkotlin/reflect/KFunction;", "(Ljava/lang/reflect/Method;)Lkotlin/reflect/KFunction;", "kotlinProperty", "getKotlinProperty", "(Ljava/lang/reflect/Field;)Lkotlin/reflect/KProperty;", "getKPackage", "Lkotlin/reflect/KDeclarationContainer;", "Ljava/lang/reflect/Member;", "kotlin-reflect-api"}, k = 2, mv = {1, 1, 13})
@JvmName(name = "ReflectJvmMapping")
public final class ReflectJvmMapping {

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 13})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[KotlinClassHeader.Kind.values().length];

        static {
            $EnumSwitchMapping$0[KotlinClassHeader.Kind.FILE_FACADE.ordinal()] = 1;
            $EnumSwitchMapping$0[KotlinClassHeader.Kind.MULTIFILE_CLASS.ordinal()] = 2;
            $EnumSwitchMapping$0[KotlinClassHeader.Kind.MULTIFILE_CLASS_PART.ordinal()] = 3;
        }
    }

    public static /* synthetic */ void javaConstructor$annotations(KFunction kFunction) {
    }

    @Nullable
    public static final Field getJavaField(@NotNull KProperty<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        KPropertyImpl<?> kPropertyImplAsKPropertyImpl = UtilKt.asKPropertyImpl(receiver$0);
        if (kPropertyImplAsKPropertyImpl != null) {
            return kPropertyImplAsKPropertyImpl.getJavaField();
        }
        return null;
    }

    @Nullable
    public static final Method getJavaGetter(@NotNull KProperty<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return getJavaMethod(receiver$0.getGetter());
    }

    @Nullable
    public static final Method getJavaSetter(@NotNull KMutableProperty<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return getJavaMethod(receiver$0.getSetter());
    }

    @Nullable
    public static final Method getJavaMethod(@NotNull KFunction<?> receiver$0) {
        Caller<?> caller;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        KCallableImpl<?> kCallableImplAsKCallableImpl = UtilKt.asKCallableImpl(receiver$0);
        Member memberMo371getMember = (kCallableImplAsKCallableImpl == null || (caller = kCallableImplAsKCallableImpl.getCaller()) == null) ? null : caller.mo371getMember();
        if (!(memberMo371getMember instanceof Method)) {
            memberMo371getMember = null;
        }
        return (Method) memberMo371getMember;
    }

    @Nullable
    public static final <T> Constructor<T> getJavaConstructor(@NotNull KFunction<? extends T> receiver$0) {
        Caller<?> caller;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        KCallableImpl<?> kCallableImplAsKCallableImpl = UtilKt.asKCallableImpl(receiver$0);
        Member memberMo371getMember = (kCallableImplAsKCallableImpl == null || (caller = kCallableImplAsKCallableImpl.getCaller()) == null) ? null : caller.mo371getMember();
        if (!(memberMo371getMember instanceof Constructor)) {
            memberMo371getMember = null;
        }
        return (Constructor) memberMo371getMember;
    }

    @NotNull
    public static final Type getJavaType(@NotNull KType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return ((KTypeImpl) receiver$0).getJavaType$kotlin_reflect_api();
    }

    @Nullable
    public static final KProperty<?> getKotlinProperty(@NotNull Field receiver$0) throws InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Object obj = null;
        if (receiver$0.isSynthetic()) {
            return null;
        }
        KDeclarationContainer kotlinPackage = getKPackage(receiver$0);
        if (kotlinPackage != null) {
            Iterable $receiver$iv = kotlinPackage.getMembers();
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $receiver$iv) {
                if (element$iv$iv instanceof KProperty) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            Iterable $receiver$iv2 = (List) destination$iv$iv;
            for (Object element$iv : $receiver$iv2) {
                KProperty it = (KProperty) element$iv;
                if (Intrinsics.areEqual(getJavaField(it), receiver$0)) {
                    obj = element$iv;
                    break;
                }
            }
            return (KProperty) obj;
        }
        Class<?> declaringClass = receiver$0.getDeclaringClass();
        Intrinsics.checkExpressionValueIsNotNull(declaringClass, "declaringClass");
        Iterable $receiver$iv3 = KClasses.getMemberProperties(JvmClassMappingKt.getKotlinClass(declaringClass));
        for (Object element$iv2 : $receiver$iv3) {
            KProperty1 it2 = (KProperty1) element$iv2;
            if (Intrinsics.areEqual(getJavaField(it2), receiver$0)) {
                obj = element$iv2;
                break;
            }
        }
        return (KProperty) obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final KDeclarationContainer getKPackage(@NotNull Member member) throws InvocationTargetException {
        KotlinClassHeader classHeader;
        ReflectKotlinClass.Companion companion = ReflectKotlinClass.INSTANCE;
        Class<?> declaringClass = member.getDeclaringClass();
        Intrinsics.checkExpressionValueIsNotNull(declaringClass, "declaringClass");
        ReflectKotlinClass reflectKotlinClassCreate = companion.create(declaringClass);
        String str = null;
        Object[] objArr = 0;
        KotlinClassHeader.Kind kind = (reflectKotlinClassCreate == null || (classHeader = reflectKotlinClassCreate.getClassHeader()) == null) ? null : classHeader.getKind();
        if (kind == null) {
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
        int i2 = 2;
        if (i != 1 && i != 2 && i != 3) {
            return null;
        }
        Class<?> declaringClass2 = member.getDeclaringClass();
        Intrinsics.checkExpressionValueIsNotNull(declaringClass2, "declaringClass");
        return new KPackageImpl(declaringClass2, str, i2, objArr == true ? 1 : 0);
    }

    /* JADX WARN: Code duplicated, block: B:41:0x00d8  */
    @Nullable
    public static final KFunction<?> getKotlinFunction(@NotNull Method receiver$0) throws InvocationTargetException {
        Object element$iv;
        boolean z;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Object obj = null;
        if (receiver$0.isSynthetic()) {
            return null;
        }
        if (Modifier.isStatic(receiver$0.getModifiers())) {
            KDeclarationContainer kotlinPackage = getKPackage(receiver$0);
            if (kotlinPackage != null) {
                Iterable $receiver$iv = kotlinPackage.getMembers();
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $receiver$iv) {
                    if (element$iv$iv instanceof KFunction) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                Iterable $receiver$iv2 = (List) destination$iv$iv;
                for (Object element$iv2 : $receiver$iv2) {
                    KFunction it = (KFunction) element$iv2;
                    if (Intrinsics.areEqual(getJavaMethod(it), receiver$0)) {
                        obj = element$iv2;
                        break;
                    }
                }
                return (KFunction) obj;
            }
            Class<?> declaringClass = receiver$0.getDeclaringClass();
            Intrinsics.checkExpressionValueIsNotNull(declaringClass, "declaringClass");
            KClass<?> companionObject = KClasses.getCompanionObject(JvmClassMappingKt.getKotlinClass(declaringClass));
            if (companionObject != null) {
                Iterable $receiver$iv3 = KClasses.getFunctions(companionObject);
                Iterator it2 = $receiver$iv3.iterator();
                do {
                    if (it2.hasNext()) {
                        element$iv = it2.next();
                        KFunction it3 = (KFunction) element$iv;
                        Method m = getJavaMethod(it3);
                        if (m == null || !Intrinsics.areEqual(m.getName(), receiver$0.getName())) {
                            z = false;
                        } else {
                            Class<?>[] parameterTypes = m.getParameterTypes();
                            if (parameterTypes == null) {
                                Intrinsics.throwNpe();
                            }
                            Class<?>[] parameterTypes2 = receiver$0.getParameterTypes();
                            Intrinsics.checkExpressionValueIsNotNull(parameterTypes2, "this.parameterTypes");
                            if (Arrays.equals(parameterTypes, parameterTypes2) && Intrinsics.areEqual(m.getReturnType(), receiver$0.getReturnType())) {
                                z = true;
                            } else {
                                z = false;
                            }
                        }
                    } else {
                        element$iv = null;
                        break;
                    }
                } while (!z);
                KFunction<?> kFunction = (KFunction) element$iv;
                if (kFunction != null) {
                    return kFunction;
                }
            }
        }
        Class<?> declaringClass2 = receiver$0.getDeclaringClass();
        Intrinsics.checkExpressionValueIsNotNull(declaringClass2, "declaringClass");
        Iterable $receiver$iv4 = KClasses.getFunctions(JvmClassMappingKt.getKotlinClass(declaringClass2));
        for (Object element$iv3 : $receiver$iv4) {
            KFunction it4 = (KFunction) element$iv3;
            if (Intrinsics.areEqual(getJavaMethod(it4), receiver$0)) {
                obj = element$iv3;
                break;
            }
        }
        return (KFunction) obj;
    }

    @Nullable
    public static final <T> KFunction<T> getKotlinFunction(@NotNull Constructor<T> receiver$0) {
        Object element$iv;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Class<T> declaringClass = receiver$0.getDeclaringClass();
        Intrinsics.checkExpressionValueIsNotNull(declaringClass, "declaringClass");
        Iterable $receiver$iv = JvmClassMappingKt.getKotlinClass(declaringClass).getConstructors();
        Iterator<T> it = $receiver$iv.iterator();
        while (it.hasNext()) {
            element$iv = it.next();
            KFunction it2 = (KFunction) element$iv;
            if (Intrinsics.areEqual(getJavaConstructor(it2), receiver$0)) {
                return (KFunction) element$iv;
            }
        }
        element$iv = null;
        return (KFunction) element$iv;
    }
}
