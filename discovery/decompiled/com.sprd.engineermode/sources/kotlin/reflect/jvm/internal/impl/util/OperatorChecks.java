package kotlin.reflect.jvm.internal.impl.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: modifierChecks.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class OperatorChecks extends AbstractModifierChecks {
    public static final OperatorChecks INSTANCE = new OperatorChecks();

    @NotNull
    private static final List<Checks> checks = CollectionsKt.listOf((Object[]) new Checks[]{new Checks(OperatorNameConventions.GET, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, new ValueParameterCountCheck.AtLeast(1)}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.SET, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, new ValueParameterCountCheck.AtLeast(2)}, new Function1<FunctionDescriptor, String>() { // from class: kotlin.reflect.jvm.internal.impl.util.OperatorChecks$checks$1
        /* JADX WARN: Code duplicated, block: B:12:0x002b  */
        @Override // kotlin.jvm.functions.Function1
        @Nullable
        public final String invoke(@NotNull FunctionDescriptor receiver$0) {
            boolean z;
            Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
            List<ValueParameterDescriptor> valueParameters = receiver$0.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters, "valueParameters");
            ValueParameterDescriptor it = (ValueParameterDescriptor) CollectionsKt.lastOrNull((List) valueParameters);
            if (it != null) {
                z = !DescriptorUtilsKt.declaresOrInheritsDefaultValue(it) && it.getVarargElementType() == null;
            }
            boolean lastIsOk = z;
            OperatorChecks operatorChecks = OperatorChecks.INSTANCE;
            if (lastIsOk) {
                return null;
            }
            return "last parameter should not have a default value or be a vararg";
        }
    }), new Checks(OperatorNameConventions.GET_VALUE, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, NoDefaultAndVarargsCheck.INSTANCE, new ValueParameterCountCheck.AtLeast(2), IsKPropertyCheck.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.SET_VALUE, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, NoDefaultAndVarargsCheck.INSTANCE, new ValueParameterCountCheck.AtLeast(3), IsKPropertyCheck.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.PROVIDE_DELEGATE, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, NoDefaultAndVarargsCheck.INSTANCE, new ValueParameterCountCheck.Equals(2), IsKPropertyCheck.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.INVOKE, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.CONTAINS, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, ValueParameterCountCheck.SingleValueParameter.INSTANCE, NoDefaultAndVarargsCheck.INSTANCE, ReturnsCheck.ReturnsBoolean.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.ITERATOR, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, ValueParameterCountCheck.NoValueParameters.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.NEXT, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, ValueParameterCountCheck.NoValueParameters.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.HAS_NEXT, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, ValueParameterCountCheck.NoValueParameters.INSTANCE, ReturnsCheck.ReturnsBoolean.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.RANGE_TO, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, ValueParameterCountCheck.SingleValueParameter.INSTANCE, NoDefaultAndVarargsCheck.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.EQUALS, new Check[]{MemberKindCheck.Member.INSTANCE}, new Function1<FunctionDescriptor, String>() { // from class: kotlin.reflect.jvm.internal.impl.util.OperatorChecks$checks$2

        /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.util.OperatorChecks$checks$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: modifierChecks.kt */
        static final class AnonymousClass1 extends Lambda implements Function1<DeclarationDescriptor, Boolean> {
            public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

            AnonymousClass1() {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(DeclarationDescriptor declarationDescriptor) {
                return Boolean.valueOf(invoke2(declarationDescriptor));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(@NotNull DeclarationDescriptor receiver$0) {
                Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
                return (receiver$0 instanceof ClassDescriptor) && KotlinBuiltIns.isAny((ClassDescriptor) receiver$0);
            }
        }

        @Override // kotlin.jvm.functions.Function1
        @Nullable
        public final String invoke(@NotNull FunctionDescriptor receiver$0) {
            boolean z;
            Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
            AnonymousClass1 isAny$ = AnonymousClass1.INSTANCE;
            OperatorChecks operatorChecks = OperatorChecks.INSTANCE;
            DeclarationDescriptor containingDeclaration = receiver$0.getContainingDeclaration();
            Intrinsics.checkExpressionValueIsNotNull(containingDeclaration, "containingDeclaration");
            boolean z2 = true;
            if (!isAny$.invoke2(containingDeclaration)) {
                Iterable overriddenDescriptors = receiver$0.getOverriddenDescriptors();
                Intrinsics.checkExpressionValueIsNotNull(overriddenDescriptors, "overriddenDescriptors");
                Iterable $receiver$iv = overriddenDescriptors;
                if (!($receiver$iv instanceof Collection) || !((Collection) $receiver$iv).isEmpty()) {
                    Iterator it = $receiver$iv.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = false;
                            break;
                        }
                        Object element$iv = it.next();
                        FunctionDescriptor it2 = (FunctionDescriptor) element$iv;
                        AnonymousClass1 anonymousClass1 = AnonymousClass1.INSTANCE;
                        Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                        DeclarationDescriptor containingDeclaration2 = it2.getContainingDeclaration();
                        Intrinsics.checkExpressionValueIsNotNull(containingDeclaration2, "it.containingDeclaration");
                        if (anonymousClass1.invoke2(containingDeclaration2)) {
                            z = true;
                            break;
                        }
                    }
                } else {
                    z = false;
                }
                if (!z) {
                    z2 = false;
                }
            }
            boolean cond$iv = z2;
            if (cond$iv) {
                return null;
            }
            return "must override ''equals()'' in Any";
        }
    }), new Checks(OperatorNameConventions.COMPARE_TO, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, ReturnsCheck.ReturnsInt.INSTANCE, ValueParameterCountCheck.SingleValueParameter.INSTANCE, NoDefaultAndVarargsCheck.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.BINARY_OPERATION_NAMES, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, ValueParameterCountCheck.SingleValueParameter.INSTANCE, NoDefaultAndVarargsCheck.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.SIMPLE_UNARY_OPERATION_NAMES, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, ValueParameterCountCheck.NoValueParameters.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(CollectionsKt.listOf((Object[]) new Name[]{OperatorNameConventions.INC, OperatorNameConventions.DEC}), new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE}, new Function1<FunctionDescriptor, String>() { // from class: kotlin.reflect.jvm.internal.impl.util.OperatorChecks$checks$3
        @Override // kotlin.jvm.functions.Function1
        @Nullable
        public final String invoke(@NotNull FunctionDescriptor receiver$0) {
            boolean zIsSubtypeOf;
            Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
            ReceiverParameterDescriptor receiver = receiver$0.getDispatchReceiverParameter();
            if (receiver == null) {
                receiver = receiver$0.getExtensionReceiverParameter();
            }
            OperatorChecks operatorChecks = OperatorChecks.INSTANCE;
            boolean cond$iv = false;
            if (receiver != null) {
                KotlinType returnType = receiver$0.getReturnType();
                if (returnType != null) {
                    KotlinType type = receiver.getType();
                    Intrinsics.checkExpressionValueIsNotNull(type, "receiver.type");
                    zIsSubtypeOf = TypeUtilsKt.isSubtypeOf(returnType, type);
                } else {
                    zIsSubtypeOf = false;
                }
                if (zIsSubtypeOf) {
                    cond$iv = true;
                }
            }
            if (cond$iv) {
                return null;
            }
            return "receiver must be a supertype of the return type";
        }
    }), new Checks(OperatorNameConventions.ASSIGNMENT_OPERATIONS, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, ReturnsCheck.ReturnsUnit.INSTANCE, ValueParameterCountCheck.SingleValueParameter.INSTANCE, NoDefaultAndVarargsCheck.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.COMPONENT_REGEX, new Check[]{MemberKindCheck.MemberOrExtension.INSTANCE, ValueParameterCountCheck.NoValueParameters.INSTANCE}, (Function1) null, 4, (DefaultConstructorMarker) null)});

    private OperatorChecks() {
    }

    @Override // kotlin.reflect.jvm.internal.impl.util.AbstractModifierChecks
    @NotNull
    public List<Checks> getChecks$descriptors() {
        return checks;
    }
}
