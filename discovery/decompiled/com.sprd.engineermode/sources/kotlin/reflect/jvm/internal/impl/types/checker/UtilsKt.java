package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class UtilsKt {
    @Nullable
    public static final KotlinType findCorrespondingSupertype(@NotNull KotlinType subtype, @NotNull KotlinType supertype, @NotNull TypeCheckingProcedureCallbacks typeCheckingProcedureCallbacks) {
        boolean z;
        boolean z2;
        boolean z3;
        KotlinType kotlinTypeSafeSubstitute;
        Intrinsics.checkParameterIsNotNull(subtype, "subtype");
        Intrinsics.checkParameterIsNotNull(supertype, "supertype");
        Intrinsics.checkParameterIsNotNull(typeCheckingProcedureCallbacks, "typeCheckingProcedureCallbacks");
        ArrayDeque queue = new ArrayDeque();
        queue.add(new SubtypePathNode(subtype, null));
        TypeConstructor supertypeConstructor = supertype.getConstructor();
        while (!queue.isEmpty()) {
            SubtypePathNode lastPathNode = (SubtypePathNode) queue.poll();
            KotlinType currentSubtype = lastPathNode.getType();
            TypeConstructor constructor = currentSubtype.getConstructor();
            if (typeCheckingProcedureCallbacks.assertEqualTypeConstructors(constructor, supertypeConstructor)) {
                KotlinType substituted = currentSubtype;
                boolean isAnyMarkedNullable = currentSubtype.isMarkedNullable();
                for (SubtypePathNode currentPathNode = lastPathNode.getPrevious(); currentPathNode != null; currentPathNode = currentPathNode.getPrevious()) {
                    KotlinType currentType = currentPathNode.getType();
                    Iterable $receiver$iv = currentType.getArguments();
                    if (!($receiver$iv instanceof Collection) || !((Collection) $receiver$iv).isEmpty()) {
                        Iterator it = $receiver$iv.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z = false;
                                break;
                            }
                            Object element$iv = it.next();
                            TypeProjection it2 = (TypeProjection) element$iv;
                            if (it2.getProjectionKind() != Variance.INVARIANT) {
                                z = true;
                                break;
                            }
                        }
                    } else {
                        z = false;
                    }
                    if (z) {
                        z2 = false;
                        z3 = true;
                        KotlinType kotlinTypeSafeSubstitute2 = CapturedTypeConstructorKt.wrapWithCapturingSubstitution$default(TypeConstructorSubstitution.Companion.create(currentType), false, 1, null).buildSubstitutor().safeSubstitute(substituted, Variance.INVARIANT);
                        Intrinsics.checkExpressionValueIsNotNull(kotlinTypeSafeSubstitute2, "TypeConstructorSubstitut…uted, Variance.INVARIANT)");
                        kotlinTypeSafeSubstitute = approximate(kotlinTypeSafeSubstitute2);
                    } else {
                        z2 = false;
                        z3 = true;
                        kotlinTypeSafeSubstitute = TypeConstructorSubstitution.Companion.create(currentType).buildSubstitutor().safeSubstitute(substituted, Variance.INVARIANT);
                        Intrinsics.checkExpressionValueIsNotNull(kotlinTypeSafeSubstitute, "TypeConstructorSubstitut…uted, Variance.INVARIANT)");
                    }
                    substituted = kotlinTypeSafeSubstitute;
                    if (!isAnyMarkedNullable && !currentType.isMarkedNullable()) {
                        z3 = z2;
                    }
                    isAnyMarkedNullable = z3;
                }
                TypeConstructor substitutedConstructor = substituted.getConstructor();
                if (typeCheckingProcedureCallbacks.assertEqualTypeConstructors(substitutedConstructor, supertypeConstructor)) {
                    return TypeUtils.makeNullableAsSpecified(substituted, isAnyMarkedNullable);
                }
                throw new AssertionError("Type constructors should be equals!\nsubstitutedSuperType: " + debugInfo(substitutedConstructor) + ", \n\nsupertype: " + debugInfo(supertypeConstructor) + " \n" + typeCheckingProcedureCallbacks.assertEqualTypeConstructors(substitutedConstructor, supertypeConstructor));
            }
            for (KotlinType immediateSupertype : constructor.getSupertypes()) {
                Intrinsics.checkExpressionValueIsNotNull(immediateSupertype, "immediateSupertype");
                queue.add(new SubtypePathNode(immediateSupertype, lastPathNode));
            }
        }
        return null;
    }

    private static final KotlinType approximate(@NotNull KotlinType $receiver) {
        return CapturedTypeApproximationKt.approximateCapturedTypes($receiver).getUpper();
    }

    private static final String debugInfo(@NotNull TypeConstructor $receiver) {
        final StringBuilder $receiver2 = new StringBuilder();
        Function1<String, StringBuilder> function1 = new Function1<String, StringBuilder>() { // from class: kotlin.reflect.jvm.internal.impl.types.checker.UtilsKt$debugInfo$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final StringBuilder invoke(@NotNull String receiver$0) {
                Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
                StringBuilder sb = $receiver2;
                sb.append(receiver$0);
                Intrinsics.checkExpressionValueIsNotNull(sb, "append(value)");
                return StringsKt.appendln(sb);
            }
        };
        function1.invoke("type: " + $receiver);
        function1.invoke("hashCode: " + $receiver.hashCode());
        function1.invoke("javaClass: " + $receiver.getClass().getCanonicalName());
        for (ClassifierDescriptor declarationDescriptor = $receiver.mo381getDeclarationDescriptor(); declarationDescriptor != null; declarationDescriptor = declarationDescriptor.getContainingDeclaration()) {
            function1.invoke("fqName: " + DescriptorRenderer.FQ_NAMES_IN_TYPES.render(declarationDescriptor));
            function1.invoke("javaClass: " + declarationDescriptor.getClass().getCanonicalName());
        }
        String string = $receiver2.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }
}
