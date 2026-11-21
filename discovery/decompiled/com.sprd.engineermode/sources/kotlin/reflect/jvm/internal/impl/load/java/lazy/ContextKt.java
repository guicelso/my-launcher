package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassOrPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameterListOwner;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: context.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ContextKt {
    @NotNull
    public static final LazyJavaResolverContext child(@NotNull LazyJavaResolverContext receiver$0, @NotNull TypeParameterResolver typeParameterResolver) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeParameterResolver, "typeParameterResolver");
        return new LazyJavaResolverContext(receiver$0.getComponents(), typeParameterResolver, receiver$0.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3, types: [java.util.Collection] */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.lang.Object] */
    @Nullable
    public static final JavaTypeQualifiersByElementType computeNewDefaultTypeQualifiers(@NotNull LazyJavaResolverContext receiver$0, @NotNull Annotations additionalAnnotations) {
        EnumMap<AnnotationTypeQualifierResolver.QualifierApplicabilityType, NullabilityQualifierWithMigrationStatus> nullabilityQualifiers$descriptors_jvm;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(additionalAnnotations, "additionalAnnotations");
        if (receiver$0.getComponents().getAnnotationTypeQualifierResolver().getDisabled()) {
            return receiver$0.getDefaultTypeQualifiers();
        }
        Annotations $receiver$iv = additionalAnnotations;
        Collection destination$iv$iv = new ArrayList();
        Iterator<AnnotationDescriptor> it = $receiver$iv.iterator();
        ?? Component2 = 0;
        while (it.hasNext()) {
            Component2 = it.next();
            AnnotationDescriptor p1 = (AnnotationDescriptor) Component2;
            NullabilityQualifierWithApplicability nullabilityQualifierWithApplicabilityExtractDefaultNullabilityQualifier = extractDefaultNullabilityQualifier(receiver$0, p1);
            if (nullabilityQualifierWithApplicabilityExtractDefaultNullabilityQualifier != null) {
                destination$iv$iv.add(nullabilityQualifierWithApplicabilityExtractDefaultNullabilityQualifier);
            }
        }
        List<NullabilityQualifierWithApplicability> nullabilityQualifiersWithApplicability = (List) destination$iv$iv;
        if (nullabilityQualifiersWithApplicability.isEmpty()) {
            return receiver$0.getDefaultTypeQualifiers();
        }
        JavaTypeQualifiersByElementType defaultTypeQualifiers = receiver$0.getDefaultTypeQualifiers();
        EnumMap nullabilityQualifiersByType = (defaultTypeQualifiers == null || (nullabilityQualifiers$descriptors_jvm = defaultTypeQualifiers.getNullabilityQualifiers$descriptors_jvm()) == null) ? new EnumMap(AnnotationTypeQualifierResolver.QualifierApplicabilityType.class) : new EnumMap((EnumMap) nullabilityQualifiers$descriptors_jvm);
        boolean wasUpdate = false;
        for (NullabilityQualifierWithApplicability nullabilityQualifierWithApplicability : nullabilityQualifiersWithApplicability) {
            NullabilityQualifierWithMigrationStatus nullability = nullabilityQualifierWithApplicability.component1();
            Component2 = nullabilityQualifierWithApplicability.component2();
            for (AnnotationTypeQualifierResolver.QualifierApplicabilityType applicabilityType : Component2) {
                nullabilityQualifiersByType.put(applicabilityType, nullability);
                wasUpdate = true;
            }
        }
        return !wasUpdate ? receiver$0.getDefaultTypeQualifiers() : new JavaTypeQualifiersByElementType(nullabilityQualifiersByType);
    }

    private static final NullabilityQualifierWithApplicability extractDefaultNullabilityQualifier(@NotNull LazyJavaResolverContext $receiver, AnnotationDescriptor annotationDescriptor) {
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullability;
        NullabilityQualifierWithMigrationStatus nullabilityQualifier;
        AnnotationTypeQualifierResolver typeQualifierResolver = $receiver.getComponents().getAnnotationTypeQualifierResolver();
        NullabilityQualifierWithApplicability it = typeQualifierResolver.resolveQualifierBuiltInDefaultAnnotation(annotationDescriptor);
        if (it != null) {
            return it;
        }
        AnnotationTypeQualifierResolver.TypeQualifierWithApplicability typeQualifierWithApplicabilityResolveTypeQualifierDefaultAnnotation = typeQualifierResolver.resolveTypeQualifierDefaultAnnotation(annotationDescriptor);
        if (typeQualifierWithApplicabilityResolveTypeQualifierDefaultAnnotation == null) {
            return null;
        }
        AnnotationDescriptor typeQualifier = typeQualifierWithApplicabilityResolveTypeQualifierDefaultAnnotation.component1();
        List<AnnotationTypeQualifierResolver.QualifierApplicabilityType> listComponent2 = typeQualifierWithApplicabilityResolveTypeQualifierDefaultAnnotation.component2();
        ReportLevel jsr305State = typeQualifierResolver.resolveJsr305CustomState(annotationDescriptor);
        if (jsr305State == null) {
            jsr305State = typeQualifierResolver.resolveJsr305AnnotationState(typeQualifier);
        }
        if (jsr305State.isIgnore() || (nullabilityQualifierWithMigrationStatusExtractNullability = $receiver.getComponents().getSignatureEnhancement().extractNullability(typeQualifier)) == null || (nullabilityQualifier = NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatusExtractNullability, null, jsr305State.isWarning(), 1, null)) == null) {
            return null;
        }
        return new NullabilityQualifierWithApplicability(nullabilityQualifier, listComponent2);
    }

    @NotNull
    public static final LazyJavaResolverContext replaceComponents(@NotNull LazyJavaResolverContext receiver$0, @NotNull JavaResolverComponents components) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(components, "components");
        return new LazyJavaResolverContext(components, receiver$0.getTypeParameterResolver(), receiver$0.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
    }

    private static final LazyJavaResolverContext child(@NotNull LazyJavaResolverContext $receiver, DeclarationDescriptor containingDeclaration, JavaTypeParameterListOwner typeParameterOwner, int typeParametersIndexOffset, Lazy<JavaTypeQualifiersByElementType> lazy) {
        return new LazyJavaResolverContext($receiver.getComponents(), typeParameterOwner != null ? new LazyJavaTypeParameterResolver($receiver, containingDeclaration, typeParameterOwner, typeParametersIndexOffset) : $receiver.getTypeParameterResolver(), lazy);
    }

    @NotNull
    public static /* synthetic */ LazyJavaResolverContext childForMethod$default(LazyJavaResolverContext lazyJavaResolverContext, DeclarationDescriptor declarationDescriptor, JavaTypeParameterListOwner javaTypeParameterListOwner, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return childForMethod(lazyJavaResolverContext, declarationDescriptor, javaTypeParameterListOwner, i);
    }

    @NotNull
    public static final LazyJavaResolverContext childForMethod(@NotNull LazyJavaResolverContext receiver$0, @NotNull DeclarationDescriptor containingDeclaration, @NotNull JavaTypeParameterListOwner typeParameterOwner, int typeParametersIndexOffset) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(containingDeclaration, "containingDeclaration");
        Intrinsics.checkParameterIsNotNull(typeParameterOwner, "typeParameterOwner");
        return child(receiver$0, containingDeclaration, typeParameterOwner, typeParametersIndexOffset, receiver$0.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
    }

    @NotNull
    public static /* synthetic */ LazyJavaResolverContext childForClassOrPackage$default(LazyJavaResolverContext lazyJavaResolverContext, ClassOrPackageFragmentDescriptor classOrPackageFragmentDescriptor, JavaTypeParameterListOwner javaTypeParameterListOwner, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            javaTypeParameterListOwner = (JavaTypeParameterListOwner) null;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return childForClassOrPackage(lazyJavaResolverContext, classOrPackageFragmentDescriptor, javaTypeParameterListOwner, i);
    }

    @NotNull
    public static final LazyJavaResolverContext childForClassOrPackage(@NotNull final LazyJavaResolverContext receiver$0, @NotNull final ClassOrPackageFragmentDescriptor containingDeclaration, @Nullable JavaTypeParameterListOwner typeParameterOwner, int typeParametersIndexOffset) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(containingDeclaration, "containingDeclaration");
        return child(receiver$0, containingDeclaration, typeParameterOwner, typeParametersIndexOffset, LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<JavaTypeQualifiersByElementType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt.childForClassOrPackage.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @Nullable
            public final JavaTypeQualifiersByElementType invoke() {
                return ContextKt.computeNewDefaultTypeQualifiers(receiver$0, containingDeclaration.getAnnotations());
            }
        }));
    }

    @NotNull
    public static final LazyJavaResolverContext copyWithNewDefaultTypeQualifiers(@NotNull final LazyJavaResolverContext receiver$0, @NotNull final Annotations additionalAnnotations) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(additionalAnnotations, "additionalAnnotations");
        if (additionalAnnotations.isEmpty()) {
            return receiver$0;
        }
        return new LazyJavaResolverContext(receiver$0.getComponents(), receiver$0.getTypeParameterResolver(), LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<JavaTypeQualifiersByElementType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt.copyWithNewDefaultTypeQualifiers.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @Nullable
            public final JavaTypeQualifiersByElementType invoke() {
                return ContextKt.computeNewDefaultTypeQualifiers(receiver$0, additionalAnnotations);
            }
        }));
    }
}
