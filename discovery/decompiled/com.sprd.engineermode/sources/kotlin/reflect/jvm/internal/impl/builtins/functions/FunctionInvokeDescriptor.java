package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.util.OperatorNameConventions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: FunctionInvokeDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class FunctionInvokeDescriptor extends SimpleFunctionDescriptorImpl {
    public static final Factory Factory = new Factory(null);

    private FunctionInvokeDescriptor(DeclarationDescriptor container, FunctionInvokeDescriptor original, CallableMemberDescriptor.Kind callableKind, boolean isSuspend) {
        super(container, original, Annotations.Companion.getEMPTY(), OperatorNameConventions.INVOKE, callableKind, SourceElement.NO_SOURCE);
        setOperator(true);
        setSuspend(isSuspend);
        setHasStableParameterNames(false);
    }

    public /* synthetic */ FunctionInvokeDescriptor(DeclarationDescriptor container, FunctionInvokeDescriptor original, CallableMemberDescriptor.Kind callableKind, boolean isSuspend, DefaultConstructorMarker $constructor_marker) {
        this(container, original, callableKind, isSuspend);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl
    @Nullable
    protected FunctionDescriptor doSubstitute(@NotNull FunctionDescriptorImpl.CopyConfiguration configuration) {
        Intrinsics.checkParameterIsNotNull(configuration, "configuration");
        FunctionInvokeDescriptor substituted = (FunctionInvokeDescriptor) super.doSubstitute(configuration);
        if (substituted == null) {
            return null;
        }
        Iterable valueParameters = substituted.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters, "substituted.valueParameters");
        Iterable $receiver$iv = valueParameters;
        boolean z = true;
        if (!($receiver$iv instanceof Collection) || !((Collection) $receiver$iv).isEmpty()) {
            for (Object element$iv : $receiver$iv) {
                ValueParameterDescriptor it = (ValueParameterDescriptor) element$iv;
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                KotlinType type = it.getType();
                Intrinsics.checkExpressionValueIsNotNull(type, "it.type");
                if (FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(type) != null) {
                    z = false;
                    break;
                }
            }
        }
        if (z) {
            return substituted;
        }
        Iterable valueParameters2 = substituted.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters2, "substituted.valueParameters");
        Iterable $receiver$iv2 = valueParameters2;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
        for (Object item$iv$iv : $receiver$iv2) {
            ValueParameterDescriptor it2 = (ValueParameterDescriptor) item$iv$iv;
            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
            KotlinType type2 = it2.getType();
            Intrinsics.checkExpressionValueIsNotNull(type2, "it.type");
            destination$iv$iv.add(FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(type2));
        }
        List parameterNames = (List) destination$iv$iv;
        return substituted.replaceParameterNames(parameterNames);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl
    @NotNull
    protected FunctionDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @Nullable FunctionDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, @Nullable Name newName, @NotNull Annotations annotations, @NotNull SourceElement source) {
        Intrinsics.checkParameterIsNotNull(newOwner, "newOwner");
        Intrinsics.checkParameterIsNotNull(kind, "kind");
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        Intrinsics.checkParameterIsNotNull(source, "source");
        return new FunctionInvokeDescriptor(newOwner, (FunctionInvokeDescriptor) original, kind, isSuspend());
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    public boolean isExternal() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor
    public boolean isInline() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor
    public boolean isTailrec() {
        return false;
    }

    private final FunctionDescriptor replaceParameterNames(List<Name> list) {
        Name parameterName;
        int indexShift = getValueParameters().size() - list.size();
        boolean z = indexShift == 0 || indexShift == 1;
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("Assertion failed");
        }
        Iterable valueParameters = getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters, "valueParameters");
        Iterable $receiver$iv = valueParameters;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            ValueParameterDescriptor it = (ValueParameterDescriptor) item$iv$iv;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            Name newName = it.getName();
            Intrinsics.checkExpressionValueIsNotNull(newName, "it.name");
            int parameterIndex = it.getIndex();
            int nameIndex = parameterIndex - indexShift;
            if (nameIndex >= 0 && (parameterName = list.get(nameIndex)) != null) {
                newName = parameterName;
            }
            destination$iv$iv.add(it.copy(this, newName, parameterIndex));
        }
        List newValueParameters = (List) destination$iv$iv;
        FunctionDescriptorImpl.CopyConfiguration copyConfigurationNewCopyBuilder = newCopyBuilder(TypeSubstitutor.EMPTY);
        List<Name> $receiver$iv2 = list;
        boolean z2 = false;
        if (!($receiver$iv2 instanceof Collection) || !$receiver$iv2.isEmpty()) {
            for (Object element$iv : $receiver$iv2) {
                if (((Name) element$iv) == null) {
                    z2 = true;
                    break;
                }
            }
        }
        FunctionDescriptorImpl.CopyConfiguration copyConfiguration = copyConfigurationNewCopyBuilder.setHasSynthesizedParameterNames(z2).setValueParameters((List<ValueParameterDescriptor>) newValueParameters).setOriginal(getOriginal());
        Intrinsics.checkExpressionValueIsNotNull(copyConfiguration, "newCopyBuilder(TypeSubst…   .setOriginal(original)");
        FunctionDescriptor functionDescriptorDoSubstitute = super.doSubstitute(copyConfiguration);
        if (functionDescriptorDoSubstitute == null) {
            Intrinsics.throwNpe();
        }
        return functionDescriptorDoSubstitute;
    }

    /* JADX INFO: compiled from: FunctionInvokeDescriptor.kt */
    public static final class Factory {
        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final FunctionInvokeDescriptor create(@NotNull FunctionClassDescriptor functionClass, boolean isSuspend) {
            Intrinsics.checkParameterIsNotNull(functionClass, "functionClass");
            List<TypeParameterDescriptor> declaredTypeParameters = functionClass.getDeclaredTypeParameters();
            FunctionInvokeDescriptor result = new FunctionInvokeDescriptor(functionClass, null, CallableMemberDescriptor.Kind.DECLARATION, isSuspend, null);
            ReceiverParameterDescriptor thisAsReceiverParameter = functionClass.getThisAsReceiverParameter();
            List listEmptyList = CollectionsKt.emptyList();
            List<TypeParameterDescriptor> $receiver$iv = declaredTypeParameters;
            ArrayList list$iv = new ArrayList();
            for (Object item$iv : $receiver$iv) {
                if (!(((TypeParameterDescriptor) item$iv).getVariance() == Variance.IN_VARIANCE)) {
                    break;
                }
                list$iv.add(item$iv);
            }
            ArrayList $receiver$iv2 = list$iv;
            Iterable $receiver$iv3 = CollectionsKt.withIndex($receiver$iv2);
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv3, 10));
            for (Object item$iv$iv : $receiver$iv3) {
                IndexedValue it = (IndexedValue) item$iv$iv;
                destination$iv$iv.add(FunctionInvokeDescriptor.Factory.createValueParameter(result, it.getIndex(), (TypeParameterDescriptor) it.getValue()));
            }
            result.initialize((ReceiverParameterDescriptor) null, thisAsReceiverParameter, listEmptyList, (List) destination$iv$iv, (KotlinType) ((TypeParameterDescriptor) CollectionsKt.last((List) declaredTypeParameters)).getDefaultType(), Modality.ABSTRACT, Visibilities.PUBLIC);
            result.setHasSynthesizedParameterNames(true);
            return result;
        }

        /* JADX WARN: Code duplicated, block: B:13:0x0030  */
        private final ValueParameterDescriptor createValueParameter(FunctionInvokeDescriptor containingDeclaration, int index, TypeParameterDescriptor typeParameter) {
            String name;
            String typeParameterName = typeParameter.getName().asString();
            Intrinsics.checkExpressionValueIsNotNull(typeParameterName, "typeParameter.name.asString()");
            int iHashCode = typeParameterName.hashCode();
            if (iHashCode != 69) {
                if (iHashCode == 84 && typeParameterName.equals("T")) {
                    name = "instance";
                } else {
                    name = typeParameterName.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(name, "(this as java.lang.String).toLowerCase()");
                }
            } else if (typeParameterName.equals("E")) {
                name = "receiver";
            } else {
                name = typeParameterName.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(name, "(this as java.lang.String).toLowerCase()");
            }
            Annotations empty = Annotations.Companion.getEMPTY();
            Name nameIdentifier = Name.identifier(name);
            Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier(name)");
            SimpleType defaultType = typeParameter.getDefaultType();
            Intrinsics.checkExpressionValueIsNotNull(defaultType, "typeParameter.defaultType");
            SourceElement sourceElement = SourceElement.NO_SOURCE;
            Intrinsics.checkExpressionValueIsNotNull(sourceElement, "SourceElement.NO_SOURCE");
            return new ValueParameterDescriptorImpl(containingDeclaration, null, index, empty, nameIdentifier, defaultType, false, false, false, null, sourceElement);
        }
    }
}
