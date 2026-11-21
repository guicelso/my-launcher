package kotlin.reflect.full;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KType;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KClassImpl;
import kotlin.reflect.jvm.internal.KFunctionImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: KClasses.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000Z\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\r\u001a+\u0010S\u001a\u0002H\u001d\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00022\b\u0010T\u001a\u0004\u0018\u00010\u0010H\u0007¢\u0006\u0002\u0010U\u001a!\u0010V\u001a\u0002H\u001d\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u0002H\u0007¢\u0006\u0002\u0010\u0013\u001a\u001c\u0010W\u001a\u000203*\u0006\u0012\u0002\b\u00030\u00022\n\u0010X\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0007\u001a\u001c\u0010Y\u001a\u000203*\u0006\u0012\u0002\b\u00030\u00022\n\u0010Z\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0007\u001a-\u0010[\u001a\u0004\u0018\u0001H\u001d\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00022\b\u0010T\u001a\u0004\u0018\u00010\u0010H\u0007¢\u0006\u0002\u0010U\",\u0010\u0000\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"(\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\"(\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0002*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\f\u0010\u0004\u001a\u0004\b\r\u0010\u000e\"$\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0011\u0010\u0004\u001a\u0004\b\u0012\u0010\u0013\",\u0010\u0014\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0016\u0010\u0004\u001a\u0004\b\u0017\u0010\u0006\",\u0010\u0018\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0019\u0010\u0004\u001a\u0004\b\u001a\u0010\u0006\"B\u0010\u001b\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u001d\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001c0\u0001\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u001e\u0010\u0004\u001a\u0004\b\u001f\u0010\u0006\",\u0010 \u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b!\u0010\u0004\u001a\u0004\b\"\u0010\u0006\">\u0010#\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002H\u001d\u0012\u0002\b\u00030$0\u0001\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b%\u0010\u0004\u001a\u0004\b&\u0010\u0006\",\u0010'\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030(0\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b)\u0010\u0004\u001a\u0004\b*\u0010\u0006\"\"\u0010+\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b,\u0010\u0004\u001a\u0004\b-\u0010.\",\u0010/\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b0\u0010\u0004\u001a\u0004\b1\u0010\u0006\"\u001c\u00102\u001a\u000203*\u0006\u0012\u0002\b\u0003048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b2\u00105\"\u001c\u00106\u001a\u000203*\u0006\u0012\u0002\b\u0003048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b6\u00105\",\u00107\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b8\u0010\u0004\u001a\u0004\b9\u0010\u0006\"B\u0010:\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u001d\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001c0\u0001\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b;\u0010\u0004\u001a\u0004\b<\u0010\u0006\",\u0010=\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b>\u0010\u0004\u001a\u0004\b?\u0010\u0006\">\u0010@\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002H\u001d\u0012\u0002\b\u00030$0\u0001\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\bA\u0010\u0004\u001a\u0004\bB\u0010\u0006\"6\u0010C\u001a\n\u0012\u0004\u0012\u0002H\u001d\u0018\u00010\u0015\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\bD\u0010\u0004\u001a\u0004\bE\u0010F\",\u0010G\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\bH\u0010\u0004\u001a\u0004\bI\u0010\u0006\",\u0010J\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030K0\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\bL\u0010\u0004\u001a\u0004\bM\u0010\u0006\",\u0010N\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020O*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\bP\u0010\u0004\u001a\u0004\bQ\u0010R¨\u0006\\"}, d2 = {"allSuperclasses", "", "Lkotlin/reflect/KClass;", "allSuperclasses$annotations", "(Lkotlin/reflect/KClass;)V", "getAllSuperclasses", "(Lkotlin/reflect/KClass;)Ljava/util/Collection;", "allSupertypes", "Lkotlin/reflect/KType;", "allSupertypes$annotations", "getAllSupertypes", "companionObject", "companionObject$annotations", "getCompanionObject", "(Lkotlin/reflect/KClass;)Lkotlin/reflect/KClass;", "companionObjectInstance", "", "companionObjectInstance$annotations", "getCompanionObjectInstance", "(Lkotlin/reflect/KClass;)Ljava/lang/Object;", "declaredFunctions", "Lkotlin/reflect/KFunction;", "declaredFunctions$annotations", "getDeclaredFunctions", "declaredMemberExtensionFunctions", "declaredMemberExtensionFunctions$annotations", "getDeclaredMemberExtensionFunctions", "declaredMemberExtensionProperties", "Lkotlin/reflect/KProperty2;", "T", "declaredMemberExtensionProperties$annotations", "getDeclaredMemberExtensionProperties", "declaredMemberFunctions", "declaredMemberFunctions$annotations", "getDeclaredMemberFunctions", "declaredMemberProperties", "Lkotlin/reflect/KProperty1;", "declaredMemberProperties$annotations", "getDeclaredMemberProperties", "declaredMembers", "Lkotlin/reflect/KCallable;", "declaredMembers$annotations", "getDeclaredMembers", "defaultType", "defaultType$annotations", "getDefaultType", "(Lkotlin/reflect/KClass;)Lkotlin/reflect/KType;", "functions", "functions$annotations", "getFunctions", "isExtension", "", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "(Lkotlin/reflect/jvm/internal/KCallableImpl;)Z", "isNotExtension", "memberExtensionFunctions", "memberExtensionFunctions$annotations", "getMemberExtensionFunctions", "memberExtensionProperties", "memberExtensionProperties$annotations", "getMemberExtensionProperties", "memberFunctions", "memberFunctions$annotations", "getMemberFunctions", "memberProperties", "memberProperties$annotations", "getMemberProperties", "primaryConstructor", "primaryConstructor$annotations", "getPrimaryConstructor", "(Lkotlin/reflect/KClass;)Lkotlin/reflect/KFunction;", "staticFunctions", "staticFunctions$annotations", "getStaticFunctions", "staticProperties", "Lkotlin/reflect/KProperty0;", "staticProperties$annotations", "getStaticProperties", "superclasses", "", "superclasses$annotations", "getSuperclasses", "(Lkotlin/reflect/KClass;)Ljava/util/List;", "cast", "value", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)Ljava/lang/Object;", "createInstance", "isSubclassOf", "base", "isSuperclassOf", "derived", "safeCast", "kotlin-reflect-api"}, k = 2, mv = {1, 1, 13})
@JvmName(name = "KClasses")
public final class KClasses {
    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void allSuperclasses$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void allSupertypes$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void companionObject$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void companionObjectInstance$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void declaredFunctions$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void declaredMemberExtensionFunctions$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void declaredMemberExtensionProperties$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void declaredMemberFunctions$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void declaredMemberProperties$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void declaredMembers$annotations(KClass kClass) {
    }

    @Deprecated(message = "This function creates a type which rarely makes sense for generic classes. For example, such type can only be used in signatures of members of that class. Use starProjectedType or createType() for clearer semantics.")
    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void defaultType$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void functions$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void memberExtensionFunctions$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void memberExtensionProperties$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void memberFunctions$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void memberProperties$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void primaryConstructor$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void staticFunctions$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void staticProperties$annotations(KClass kClass) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void superclasses$annotations(KClass kClass) {
    }

    @Nullable
    public static final <T> KFunction<T> getPrimaryConstructor(@NotNull KClass<T> receiver$0) {
        Object element$iv;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = ((KClassImpl) receiver$0).getConstructors();
        Iterator<T> it = $receiver$iv.iterator();
        while (it.hasNext()) {
            element$iv = it.next();
            KFunction it2 = (KFunction) element$iv;
            if (it2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KFunctionImpl");
            }
            FunctionDescriptor descriptor = ((KFunctionImpl) it2).getDescriptor();
            if (descriptor == null) {
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ConstructorDescriptor");
            }
            if (((ConstructorDescriptor) descriptor).isPrimary()) {
                return (KFunction) element$iv;
            }
        }
        element$iv = null;
        return (KFunction) element$iv;
    }

    @Nullable
    public static final KClass<?> getCompanionObject(@NotNull KClass<?> receiver$0) {
        Object element$iv;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = receiver$0.getNestedClasses();
        Iterator it = $receiver$iv.iterator();
        while (it.hasNext()) {
            element$iv = it.next();
            KClass it2 = (KClass) element$iv;
            if (it2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KClassImpl<*>");
            }
            if (((KClassImpl) it2).getDescriptor().isCompanionObject()) {
                return (KClass) element$iv;
            }
        }
        element$iv = null;
        return (KClass) element$iv;
    }

    @Nullable
    public static final Object getCompanionObjectInstance(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        KClass<?> companionObject = getCompanionObject(receiver$0);
        if (companionObject != null) {
            return companionObject.getObjectInstance();
        }
        return null;
    }

    @NotNull
    public static final KType getDefaultType(@NotNull final KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        SimpleType defaultType = ((KClassImpl) receiver$0).getDescriptor().getDefaultType();
        Intrinsics.checkExpressionValueIsNotNull(defaultType, "(this as KClassImpl<*>).descriptor.defaultType");
        return new KTypeImpl(defaultType, new Function0<Class<? extends Object>>() { // from class: kotlin.reflect.full.KClasses$defaultType$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Class<? extends Object> invoke() {
                return ((KClassImpl) receiver$0).getJClass();
            }
        });
    }

    @NotNull
    public static final Collection<KCallable<?>> getDeclaredMembers(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return ((KClassImpl.Data) ((KClassImpl) receiver$0).getData().invoke()).getDeclaredMembers();
    }

    @NotNull
    public static final Collection<KFunction<?>> getFunctions(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = receiver$0.getMembers();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            if (element$iv$iv instanceof KFunction) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    @NotNull
    public static final Collection<KFunction<?>> getStaticFunctions(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = ((KClassImpl.Data) ((KClassImpl) receiver$0).getData().invoke()).getAllStaticMembers();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            if (element$iv$iv instanceof KFunction) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    @NotNull
    public static final Collection<KFunction<?>> getMemberFunctions(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = ((KClassImpl.Data) ((KClassImpl) receiver$0).getData().invoke()).getAllNonStaticMembers();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            KCallableImpl it = (KCallableImpl) element$iv$iv;
            if (isNotExtension(it) && (it instanceof KFunction)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    @NotNull
    public static final Collection<KFunction<?>> getMemberExtensionFunctions(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = ((KClassImpl.Data) ((KClassImpl) receiver$0).getData().invoke()).getAllNonStaticMembers();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            KCallableImpl it = (KCallableImpl) element$iv$iv;
            if (isExtension(it) && (it instanceof KFunction)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    @NotNull
    public static final Collection<KFunction<?>> getDeclaredFunctions(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = ((KClassImpl.Data) ((KClassImpl) receiver$0).getData().invoke()).getDeclaredMembers();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            if (element$iv$iv instanceof KFunction) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    @NotNull
    public static final Collection<KFunction<?>> getDeclaredMemberFunctions(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = ((KClassImpl.Data) ((KClassImpl) receiver$0).getData().invoke()).getDeclaredNonStaticMembers();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            KCallableImpl it = (KCallableImpl) element$iv$iv;
            if (isNotExtension(it) && (it instanceof KFunction)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    @NotNull
    public static final Collection<KFunction<?>> getDeclaredMemberExtensionFunctions(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = ((KClassImpl.Data) ((KClassImpl) receiver$0).getData().invoke()).getDeclaredNonStaticMembers();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            KCallableImpl it = (KCallableImpl) element$iv$iv;
            if (isExtension(it) && (it instanceof KFunction)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    @NotNull
    public static final Collection<KProperty0<?>> getStaticProperties(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = ((KClassImpl.Data) ((KClassImpl) receiver$0).getData().invoke()).getAllStaticMembers();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            KCallableImpl it = (KCallableImpl) element$iv$iv;
            if (isNotExtension(it) && (it instanceof KProperty0)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    @NotNull
    public static final <T> Collection<KProperty1<T, ?>> getMemberProperties(@NotNull KClass<T> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = ((KClassImpl) receiver$0).getData().invoke().getAllNonStaticMembers();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            KCallableImpl it = (KCallableImpl) element$iv$iv;
            if (isNotExtension(it) && (it instanceof KProperty1)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    @NotNull
    public static final <T> Collection<KProperty2<T, ?, ?>> getMemberExtensionProperties(@NotNull KClass<T> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = ((KClassImpl) receiver$0).getData().invoke().getAllNonStaticMembers();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            KCallableImpl it = (KCallableImpl) element$iv$iv;
            if (isExtension(it) && (it instanceof KProperty2)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    @NotNull
    public static final <T> Collection<KProperty1<T, ?>> getDeclaredMemberProperties(@NotNull KClass<T> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = ((KClassImpl) receiver$0).getData().invoke().getDeclaredNonStaticMembers();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            KCallableImpl it = (KCallableImpl) element$iv$iv;
            if (isNotExtension(it) && (it instanceof KProperty1)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    @NotNull
    public static final <T> Collection<KProperty2<T, ?, ?>> getDeclaredMemberExtensionProperties(@NotNull KClass<T> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = ((KClassImpl) receiver$0).getData().invoke().getDeclaredNonStaticMembers();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            KCallableImpl it = (KCallableImpl) element$iv$iv;
            if (isExtension(it) && (it instanceof KProperty2)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    private static final boolean isExtension(@NotNull KCallableImpl<?> kCallableImpl) {
        return kCallableImpl.getDescriptor().getExtensionReceiverParameter() != null;
    }

    private static final boolean isNotExtension(@NotNull KCallableImpl<?> kCallableImpl) {
        return !isExtension(kCallableImpl);
    }

    @NotNull
    public static final List<KClass<?>> getSuperclasses(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = receiver$0.getSupertypes();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $receiver$iv) {
            KType it = (KType) element$iv$iv$iv;
            KClassifier classifier = it.getClassifier();
            if (!(classifier instanceof KClass)) {
                classifier = null;
            }
            KClass kClass = (KClass) classifier;
            if (kClass != null) {
                destination$iv$iv.add(kClass);
            }
        }
        return (List) destination$iv$iv;
    }

    @NotNull
    public static final Collection<KType> getAllSupertypes(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Object objDfs = DFS.dfs(receiver$0.getSupertypes(), new DFS.Neighbors<KType>() { // from class: kotlin.reflect.full.KClasses$allSupertypes$1
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors
            @NotNull
            public final Iterable<KType> getNeighbors(KType current) {
                KClassifier classifier = current.getClassifier();
                if (!(classifier instanceof KClass)) {
                    classifier = null;
                }
                KClass klass = (KClass) classifier;
                if (klass == null) {
                    throw new KotlinReflectionInternalError("Supertype not a class: " + current);
                }
                Iterable supertypes = klass.getSupertypes();
                if (current.getArguments().isEmpty()) {
                    return supertypes;
                }
                TypeSubstitutor substitutor = TypeSubstitutor.create(((KTypeImpl) current).getType());
                Iterable $receiver$iv = supertypes;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
                for (Object item$iv$iv : $receiver$iv) {
                    KType supertype = (KType) item$iv$iv;
                    if (supertype == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KTypeImpl");
                    }
                    KotlinType substituted = substitutor.substitute(((KTypeImpl) supertype).getType(), Variance.INVARIANT);
                    if (substituted != null) {
                        Intrinsics.checkExpressionValueIsNotNull(substituted, "substitutor.substitute((…: $supertype ($current)\")");
                        destination$iv$iv.add(new KTypeImpl(substituted, new Function0() { // from class: kotlin.reflect.full.KClasses$allSupertypes$1$1$1$1
                            @Override // kotlin.jvm.functions.Function0
                            @NotNull
                            public final Void invoke() {
                                throw new NotImplementedError("An operation is not implemented: Java type for supertype");
                            }
                        }));
                    } else {
                        throw new KotlinReflectionInternalError("Type substitution failed: " + supertype + " (" + current + ')');
                    }
                }
                return (List) destination$iv$iv;
            }
        }, new DFS.VisitedWithSet(), new DFS.NodeHandlerWithListResult<KType, KType>() { // from class: kotlin.reflect.full.KClasses$allSupertypes$2
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.AbstractNodeHandler, kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler
            public boolean beforeChildren(@NotNull KType current) {
                Intrinsics.checkParameterIsNotNull(current, "current");
                ((LinkedList) this.result).add(current);
                return true;
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(objDfs, "DFS.dfs(\n            sup…    }\n            }\n    )");
        return (Collection) objDfs;
    }

    @NotNull
    public static final Collection<KClass<?>> getAllSuperclasses(@NotNull KClass<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = getAllSupertypes(receiver$0);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            KType supertype = (KType) item$iv$iv;
            KClassifier classifier = supertype.getClassifier();
            if (!(classifier instanceof KClass)) {
                classifier = null;
            }
            KClass kClass = (KClass) classifier;
            if (kClass == null) {
                throw new KotlinReflectionInternalError("Supertype not a class: " + supertype);
            }
            destination$iv$iv.add(kClass);
        }
        return (Collection) ((List) destination$iv$iv);
    }

    /* JADX INFO: renamed from: kotlin.reflect.full.KClasses$isSubclassOf$1, reason: invalid class name */
    /* JADX INFO: compiled from: KClasses.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 13})
    final class AnonymousClass1 extends PropertyReference1 {
        public static final KProperty1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public String getName() {
            return "superclasses";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(KClasses.class, "kotlin-reflect-api");
        }

        @Override // kotlin.jvm.internal.CallableReference
        public String getSignature() {
            return "getSuperclasses(Lkotlin/reflect/KClass;)Ljava/util/List;";
        }

        @Override // kotlin.reflect.KProperty1
        @Nullable
        public Object get(@Nullable Object receiver) {
            return KClasses.getSuperclasses((KClass) receiver);
        }
    }

    @SinceKotlin(version = "1.1")
    public static final boolean isSubclassOf(@NotNull KClass<?> receiver$0, @NotNull final KClass<?> base) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(base, "base");
        if (!Intrinsics.areEqual(receiver$0, base)) {
            List listListOf = CollectionsKt.listOf(receiver$0);
            final KProperty1 kProperty1 = AnonymousClass1.INSTANCE;
            Object obj = kProperty1;
            if (kProperty1 != null) {
                obj = new DFS.Neighbors() { // from class: kotlin.reflect.full.KClasses$sam$org_jetbrains_kotlin_utils_DFS_Neighbors$0
                    @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors
                    @NotNull
                    public final /* synthetic */ Iterable getNeighbors(Object obj2) {
                        return (Iterable) kProperty1.invoke(obj2);
                    }
                };
            }
            Boolean boolIfAny = DFS.ifAny(listListOf, (DFS.Neighbors) obj, new Function1<KClass<?>, Boolean>() { // from class: kotlin.reflect.full.KClasses.isSubclassOf.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Boolean invoke(KClass<?> kClass) {
                    return Boolean.valueOf(invoke2(kClass));
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final boolean invoke2(KClass<?> kClass) {
                    return Intrinsics.areEqual(kClass, base);
                }
            });
            Intrinsics.checkExpressionValueIsNotNull(boolIfAny, "DFS.ifAny(listOf(this), …erclasses) { it == base }");
            if (!boolIfAny.booleanValue()) {
                return false;
            }
        }
        return true;
    }

    @SinceKotlin(version = "1.1")
    public static final boolean isSuperclassOf(@NotNull KClass<?> receiver$0, @NotNull KClass<?> derived) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(derived, "derived");
        return isSubclassOf(derived, receiver$0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <T> T cast(@NotNull KClass<T> receiver$0, @Nullable Object obj) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (!receiver$0.isInstance(obj)) {
            throw new TypeCastException("Value cannot be cast to " + receiver$0.getQualifiedName());
        }
        if (obj != 0) {
            return obj;
        }
        throw new TypeCastException("null cannot be cast to non-null type T");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.1")
    @Nullable
    public static final <T> T safeCast(@NotNull KClass<T> receiver$0, @Nullable Object obj) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (!receiver$0.isInstance(obj)) {
            return null;
        }
        if (obj != 0) {
            return obj;
        }
        throw new TypeCastException("null cannot be cast to non-null type T");
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <T> T createInstance(@NotNull KClass<T> receiver$0) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Object obj2 = null;
        boolean z = false;
        Iterator<T> it = receiver$0.getConstructors().iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                obj = obj2;
                break;
            }
            T next = it.next();
            List<KParameter> parameters = ((KFunction) next).getParameters();
            boolean z2 = true;
            if (!(parameters instanceof Collection) || !parameters.isEmpty()) {
                Iterator<T> it2 = parameters.iterator();
                while (it2.hasNext()) {
                    if (!((KParameter) it2.next()).isOptional()) {
                        z2 = false;
                        break;
                    }
                }
            }
            if (z2) {
                if (z) {
                    break;
                }
                obj2 = next;
                z = true;
            }
        }
        KFunction kFunction = (KFunction) obj;
        if (kFunction != null) {
            return (T) kFunction.callBy(MapsKt.emptyMap());
        }
        throw new IllegalArgumentException("Class should have a single no-arg constructor: " + receiver$0);
    }
}
