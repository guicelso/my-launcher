package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawSubstitution;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ErasedOverridabilityCondition.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ErasedOverridabilityCondition implements ExternalOverridabilityCondition {

    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[OverridingUtil.OverrideCompatibilityInfo.Result.values().length];

        static {
            $EnumSwitchMapping$0[OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE.ordinal()] = 1;
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition
    @NotNull
    public ExternalOverridabilityCondition.Result isOverridable(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor, @Nullable ClassDescriptor subClassDescriptor) {
        boolean z;
        Intrinsics.checkParameterIsNotNull(superDescriptor, "superDescriptor");
        Intrinsics.checkParameterIsNotNull(subDescriptor, "subDescriptor");
        if (subDescriptor instanceof JavaMethodDescriptor) {
            List<TypeParameterDescriptor> typeParameters = ((JavaMethodDescriptor) subDescriptor).getTypeParameters();
            Intrinsics.checkExpressionValueIsNotNull(typeParameters, "subDescriptor.typeParameters");
            if (!(!typeParameters.isEmpty())) {
                OverridingUtil.OverrideCompatibilityInfo basicOverridabilityProblem = OverridingUtil.getBasicOverridabilityProblem(superDescriptor, subDescriptor);
                OverridingUtil.OverrideCompatibilityInfo.Result basicOverridability = basicOverridabilityProblem != null ? basicOverridabilityProblem.getResult() : null;
                if (basicOverridability != null) {
                    return ExternalOverridabilityCondition.Result.UNKNOWN;
                }
                List<ValueParameterDescriptor> valueParameters = ((JavaMethodDescriptor) subDescriptor).getValueParameters();
                Intrinsics.checkExpressionValueIsNotNull(valueParameters, "subDescriptor.valueParameters");
                Sequence map = SequencesKt.map(CollectionsKt.asSequence(valueParameters), new Function1<ValueParameterDescriptor, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.ErasedOverridabilityCondition$isOverridable$signatureTypes$1
                    @Override // kotlin.jvm.functions.Function1
                    @NotNull
                    public final KotlinType invoke(ValueParameterDescriptor it) {
                        Intrinsics.checkExpressionValueIsNotNull(it, "it");
                        return it.getType();
                    }
                });
                KotlinType returnType = ((JavaMethodDescriptor) subDescriptor).getReturnType();
                if (returnType == null) {
                    Intrinsics.throwNpe();
                }
                Sequence sequencePlus = SequencesKt.plus((Sequence<? extends KotlinType>) map, returnType);
                ReceiverParameterDescriptor extensionReceiverParameter = ((JavaMethodDescriptor) subDescriptor).getExtensionReceiverParameter();
                Sequence signatureTypes = SequencesKt.plus(sequencePlus, (Iterable) CollectionsKt.listOfNotNull(extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null));
                Iterator it = signatureTypes.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        KotlinType it2 = (KotlinType) element$iv;
                        if ((it2.getArguments().isEmpty() ^ true) && !(it2.unwrap() instanceof RawTypeImpl)) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    return ExternalOverridabilityCondition.Result.UNKNOWN;
                }
                CallableDescriptor erasedSuper = superDescriptor.substitute(RawSubstitution.INSTANCE.buildSubstitutor());
                if (erasedSuper == null) {
                    return ExternalOverridabilityCondition.Result.UNKNOWN;
                }
                if (erasedSuper instanceof SimpleFunctionDescriptor) {
                    List<TypeParameterDescriptor> typeParameters2 = ((SimpleFunctionDescriptor) erasedSuper).getTypeParameters();
                    Intrinsics.checkExpressionValueIsNotNull(typeParameters2, "erasedSuper.typeParameters");
                    if (!typeParameters2.isEmpty()) {
                        CallableDescriptor erasedSuper2 = ((SimpleFunctionDescriptor) erasedSuper).newCopyBuilder().setTypeParameters(CollectionsKt.emptyList()).build();
                        if (erasedSuper2 == null) {
                            Intrinsics.throwNpe();
                        }
                        erasedSuper = erasedSuper2;
                    }
                }
                OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIsOverridableByWithoutExternalConditions = OverridingUtil.DEFAULT.isOverridableByWithoutExternalConditions(erasedSuper, subDescriptor, false);
                Intrinsics.checkExpressionValueIsNotNull(overrideCompatibilityInfoIsOverridableByWithoutExternalConditions, "OverridingUtil.DEFAULT.i…er, subDescriptor, false)");
                OverridingUtil.OverrideCompatibilityInfo.Result overridabilityResult = overrideCompatibilityInfoIsOverridableByWithoutExternalConditions.getResult();
                Intrinsics.checkExpressionValueIsNotNull(overridabilityResult, "OverridingUtil.DEFAULT.i…Descriptor, false).result");
                if (WhenMappings.$EnumSwitchMapping$0[overridabilityResult.ordinal()] == 1) {
                    return ExternalOverridabilityCondition.Result.OVERRIDABLE;
                }
                return ExternalOverridabilityCondition.Result.UNKNOWN;
            }
        }
        return ExternalOverridabilityCondition.Result.UNKNOWN;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition
    @NotNull
    public ExternalOverridabilityCondition.Contract getContract() {
        return ExternalOverridabilityCondition.Contract.SUCCESS_ONLY;
    }
}
