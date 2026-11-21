package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: StarProjectionImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class StarProjectionImplKt {
    @NotNull
    public static final KotlinType starProjectionType(@NotNull TypeParameterDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        DeclarationDescriptor containingDeclaration = receiver$0.getContainingDeclaration();
        if (containingDeclaration == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters");
        }
        ClassifierDescriptorWithTypeParameters classDescriptor = (ClassifierDescriptorWithTypeParameters) containingDeclaration;
        TypeConstructor typeConstructor = classDescriptor.getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "classDescriptor.typeConstructor");
        Iterable parameters = typeConstructor.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "classDescriptor.typeConstructor.parameters");
        Iterable $receiver$iv = parameters;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            TypeParameterDescriptor it = (TypeParameterDescriptor) item$iv$iv;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            destination$iv$iv.add(it.getTypeConstructor());
        }
        final List typeParameters = (List) destination$iv$iv;
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(new TypeConstructorSubstitution() { // from class: kotlin.reflect.jvm.internal.impl.types.StarProjectionImplKt.starProjectionType.1
            @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution
            @Nullable
            public TypeProjection get(@NotNull TypeConstructor key) {
                Intrinsics.checkParameterIsNotNull(key, "key");
                if (typeParameters.contains(key)) {
                    ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = key.mo381getDeclarationDescriptor();
                    if (classifierDescriptorMo381getDeclarationDescriptor != null) {
                        return TypeUtils.makeStarProjection((TypeParameterDescriptor) classifierDescriptorMo381getDeclarationDescriptor);
                    }
                    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
                }
                return null;
            }
        });
        List<KotlinType> upperBounds = receiver$0.getUpperBounds();
        Intrinsics.checkExpressionValueIsNotNull(upperBounds, "this.upperBounds");
        KotlinType kotlinTypeSubstitute = typeSubstitutorCreate.substitute((KotlinType) CollectionsKt.first((List) upperBounds), Variance.OUT_VARIANCE);
        if (kotlinTypeSubstitute != null) {
            return kotlinTypeSubstitute;
        }
        SimpleType defaultBound = DescriptorUtilsKt.getBuiltIns(receiver$0).getDefaultBound();
        Intrinsics.checkExpressionValueIsNotNull(defaultBound, "builtIns.defaultBound");
        return defaultBound;
    }
}
