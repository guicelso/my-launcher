package kotlin.reflect.jvm.internal.impl.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class DescriptorSubstitutor {
    @NotNull
    public static TypeSubstitutor substituteTypeParameters(@NotNull List<TypeParameterDescriptor> typeParameters, @NotNull TypeSubstitution originalSubstitution, @NotNull DeclarationDescriptor newContainingDeclaration, @NotNull List<TypeParameterDescriptor> result) {
        TypeSubstitutor substitutor = substituteTypeParameters(typeParameters, originalSubstitution, newContainingDeclaration, result, null);
        if (substitutor == null) {
            throw new AssertionError("Substitution failed");
        }
        return substitutor;
    }

    @Nullable
    public static TypeSubstitutor substituteTypeParameters(@NotNull List<TypeParameterDescriptor> typeParameters, @NotNull TypeSubstitution originalSubstitution, @NotNull DeclarationDescriptor newContainingDeclaration, @NotNull List<TypeParameterDescriptor> result, @Nullable boolean[] wereChanges) {
        Map<TypeConstructor, TypeProjection> mutableSubstitution = new HashMap<>();
        Map<TypeParameterDescriptor, TypeParameterDescriptorImpl> substitutedMap = new HashMap<>();
        int index = 0;
        for (TypeParameterDescriptor descriptor : typeParameters) {
            TypeParameterDescriptorImpl substituted = TypeParameterDescriptorImpl.createForFurtherModification(newContainingDeclaration, descriptor.getAnnotations(), descriptor.isReified(), descriptor.getVariance(), descriptor.getName(), index, SourceElement.NO_SOURCE);
            mutableSubstitution.put(descriptor.getTypeConstructor(), new TypeProjectionImpl(substituted.getDefaultType()));
            substitutedMap.put(descriptor, substituted);
            result.add(substituted);
            index++;
        }
        TypeSubstitutor substitutor = TypeSubstitutor.createChainedSubstitutor(originalSubstitution, TypeConstructorSubstitution.createByConstructorsMap(mutableSubstitution));
        for (TypeParameterDescriptor descriptor2 : typeParameters) {
            TypeParameterDescriptorImpl substituted2 = substitutedMap.get(descriptor2);
            for (KotlinType upperBound : descriptor2.getUpperBounds()) {
                KotlinType substitutedBound = substitutor.substitute(upperBound, Variance.IN_VARIANCE);
                if (substitutedBound == null) {
                    return null;
                }
                if (substitutedBound != upperBound && wereChanges != null) {
                    wereChanges[0] = true;
                }
                substituted2.addUpperBound(substitutedBound);
            }
            substituted2.setInitialized();
        }
        return substitutor;
    }
}
