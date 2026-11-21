package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: typeParameterUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class TypeParameterUtilsKt {
    @NotNull
    public static final List<TypeParameterDescriptor> computeConstructorTypeParameters(@NotNull ClassifierDescriptorWithTypeParameters receiver$0) {
        List<TypeParameterDescriptor> listEmptyList;
        Object element$iv;
        TypeConstructor typeConstructor;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        List<TypeParameterDescriptor> declaredTypeParameters = receiver$0.getDeclaredTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(declaredTypeParameters, "declaredTypeParameters");
        if (!receiver$0.isInner() && !(receiver$0.getContainingDeclaration() instanceof CallableDescriptor)) {
            return declaredTypeParameters;
        }
        List parametersFromContainingFunctions = SequencesKt.toList(SequencesKt.flatMap(SequencesKt.takeWhile(DescriptorUtilsKt.getParents(receiver$0), new Function1<DeclarationDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt$computeConstructorTypeParameters$parametersFromContainingFunctions$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(DeclarationDescriptor declarationDescriptor) {
                return Boolean.valueOf(invoke2(declarationDescriptor));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(@NotNull DeclarationDescriptor it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it instanceof CallableDescriptor;
            }
        }), new Function1<DeclarationDescriptor, Sequence<? extends TypeParameterDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt$computeConstructorTypeParameters$parametersFromContainingFunctions$2
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Sequence<TypeParameterDescriptor> invoke(@NotNull DeclarationDescriptor it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                List<TypeParameterDescriptor> typeParameters = ((CallableDescriptor) it).getTypeParameters();
                Intrinsics.checkExpressionValueIsNotNull(typeParameters, "(it as CallableDescriptor).typeParameters");
                return CollectionsKt.asSequence(typeParameters);
            }
        }));
        Iterator<DeclarationDescriptor> it = DescriptorUtilsKt.getParents(receiver$0).iterator();
        do {
            listEmptyList = null;
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
        } while (!(element$iv instanceof ClassDescriptor));
        ClassDescriptor classDescriptor = (ClassDescriptor) element$iv;
        if (classDescriptor != null && (typeConstructor = classDescriptor.getTypeConstructor()) != null) {
            listEmptyList = typeConstructor.getParameters();
        }
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List<TypeParameterDescriptor> list = listEmptyList;
        if (parametersFromContainingFunctions.isEmpty() && list.isEmpty()) {
            List<TypeParameterDescriptor> declaredTypeParameters2 = receiver$0.getDeclaredTypeParameters();
            Intrinsics.checkExpressionValueIsNotNull(declaredTypeParameters2, "declaredTypeParameters");
            return declaredTypeParameters2;
        }
        Iterable $receiver$iv = CollectionsKt.plus((Collection) parametersFromContainingFunctions, (Iterable) list);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            TypeParameterDescriptor it2 = (TypeParameterDescriptor) item$iv$iv;
            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
            destination$iv$iv.add(capturedCopyForInnerDeclaration(it2, receiver$0, declaredTypeParameters.size()));
        }
        List additional = (List) destination$iv$iv;
        return CollectionsKt.plus((Collection) declaredTypeParameters, (Iterable) additional);
    }

    private static final CapturedTypeParameterDescriptor capturedCopyForInnerDeclaration(@NotNull TypeParameterDescriptor $receiver, DeclarationDescriptor declarationDescriptor, int declaredTypeParametersCount) {
        return new CapturedTypeParameterDescriptor($receiver, declarationDescriptor, declaredTypeParametersCount);
    }

    @Nullable
    public static final PossiblyInnerType buildPossiblyInnerType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = receiver$0.getConstructor().mo381getDeclarationDescriptor();
        if (!(classifierDescriptorMo381getDeclarationDescriptor instanceof ClassifierDescriptorWithTypeParameters)) {
            classifierDescriptorMo381getDeclarationDescriptor = null;
        }
        return buildPossiblyInnerType(receiver$0, (ClassifierDescriptorWithTypeParameters) classifierDescriptorMo381getDeclarationDescriptor, 0);
    }

    private static final PossiblyInnerType buildPossiblyInnerType(@NotNull KotlinType $receiver, ClassifierDescriptorWithTypeParameters classifierDescriptor, int index) {
        if (classifierDescriptor == null || ErrorUtils.isError(classifierDescriptor)) {
            return null;
        }
        int toIndex = classifierDescriptor.getDeclaredTypeParameters().size() + index;
        if (!classifierDescriptor.isInner()) {
            boolean z = toIndex == $receiver.getArguments().size() || DescriptorUtils.isLocal(classifierDescriptor);
            if (_Assertions.ENABLED && !z) {
                throw new AssertionError(($receiver.getArguments().size() - toIndex) + " trailing arguments were found in " + $receiver + " type");
            }
            return new PossiblyInnerType(classifierDescriptor, $receiver.getArguments().subList(index, $receiver.getArguments().size()), null);
        }
        List<TypeProjection> listSubList = $receiver.getArguments().subList(index, toIndex);
        DeclarationDescriptor containingDeclaration = classifierDescriptor.getContainingDeclaration();
        return new PossiblyInnerType(classifierDescriptor, listSubList, buildPossiblyInnerType($receiver, (ClassifierDescriptorWithTypeParameters) (containingDeclaration instanceof ClassifierDescriptorWithTypeParameters ? containingDeclaration : null), toIndex));
    }
}
