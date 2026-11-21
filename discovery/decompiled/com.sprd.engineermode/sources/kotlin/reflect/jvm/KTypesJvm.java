package kotlin.reflect.jvm;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: KTypesJvm.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u001c\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\"\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u0001*\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0003\u0010\b¨\u0006\t"}, d2 = {"jvmErasure", "Lkotlin/reflect/KClass;", "Lkotlin/reflect/KClassifier;", "getJvmErasure", "(Lkotlin/reflect/KClassifier;)Lkotlin/reflect/KClass;", "Lkotlin/reflect/KType;", "jvmErasure$annotations", "(Lkotlin/reflect/KType;)V", "(Lkotlin/reflect/KType;)Lkotlin/reflect/KClass;", "kotlin-reflect-api"}, k = 2, mv = {1, 1, 13})
@JvmName(name = "KTypesJvm")
public final class KTypesJvm {
    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void jvmErasure$annotations(KType kType) {
    }

    @NotNull
    public static final KClass<?> getJvmErasure(@NotNull KType receiver$0) {
        KClass<?> jvmErasure;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        KClassifier classifier = receiver$0.getClassifier();
        if (classifier != null && (jvmErasure = getJvmErasure(classifier)) != null) {
            return jvmErasure;
        }
        throw new KotlinReflectionInternalError("Cannot calculate JVM erasure for type: " + receiver$0);
    }

    @NotNull
    public static final KClass<?> getJvmErasure(@NotNull KClassifier receiver$0) {
        Object element$iv;
        KClass<?> jvmErasure;
        ClassDescriptor classDescriptor;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (receiver$0 instanceof KClass) {
            return (KClass) receiver$0;
        }
        if (receiver$0 instanceof KTypeParameter) {
            List<KType> upperBounds = ((KTypeParameter) receiver$0).getUpperBounds();
            List<KType> $receiver$iv = upperBounds;
            Iterator it = $receiver$iv.iterator();
            do {
                if (it.hasNext()) {
                    element$iv = it.next();
                    KType it2 = (KType) element$iv;
                    if (it2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KTypeImpl");
                    }
                    ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = ((KTypeImpl) it2).getType().getConstructor().mo381getDeclarationDescriptor();
                    classDescriptor = (ClassDescriptor) (classifierDescriptorMo381getDeclarationDescriptor instanceof ClassDescriptor ? classifierDescriptorMo381getDeclarationDescriptor : null);
                } else {
                    element$iv = null;
                    break;
                }
            } while (!((classDescriptor == null || classDescriptor.getKind() == ClassKind.INTERFACE || classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS) ? false : true));
            KType kType = (KType) element$iv;
            if (kType == null) {
                kType = (KType) CollectionsKt.firstOrNull((List) upperBounds);
            }
            KType representativeBound = kType;
            return (representativeBound == null || (jvmErasure = getJvmErasure(representativeBound)) == null) ? Reflection.getOrCreateKotlinClass(Object.class) : jvmErasure;
        }
        throw new KotlinReflectionInternalError("Cannot calculate JVM erasure for type: " + receiver$0);
    }
}
