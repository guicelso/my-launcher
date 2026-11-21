package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ExtensionReceiver;
import kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class PropertyDescriptorImpl extends VariableDescriptorWithInitializerImpl implements PropertyDescriptor {
    private FieldDescriptor backingField;
    private FieldDescriptor delegateField;
    private ReceiverParameterDescriptor dispatchReceiverParameter;
    private ReceiverParameterDescriptor extensionReceiverParameter;
    private PropertyGetterDescriptorImpl getter;
    private final boolean isActual;
    private final boolean isConst;
    private final boolean isDelegated;
    private final boolean isExpect;
    private final boolean isExternal;
    private final CallableMemberDescriptor.Kind kind;
    private final boolean lateInit;
    private final Modality modality;
    private final PropertyDescriptor original;
    private Collection<? extends PropertyDescriptor> overriddenProperties;
    private PropertySetterDescriptor setter;
    private boolean setterProjectedOut;
    private List<TypeParameterDescriptor> typeParameters;
    private Visibility visibility;

    protected PropertyDescriptorImpl(@NotNull DeclarationDescriptor containingDeclaration, @Nullable PropertyDescriptor original, @NotNull Annotations annotations, @NotNull Modality modality, @NotNull Visibility visibility, boolean isVar, @NotNull Name name, @NotNull CallableMemberDescriptor.Kind kind, @NotNull SourceElement source, boolean lateInit, boolean isConst, boolean isExpect, boolean isActual, boolean isExternal, boolean isDelegated) {
        super(containingDeclaration, annotations, name, null, isVar, source);
        this.overriddenProperties = null;
        this.modality = modality;
        this.visibility = visibility;
        this.original = original == null ? this : original;
        this.kind = kind;
        this.lateInit = lateInit;
        this.isConst = isConst;
        this.isExpect = isExpect;
        this.isActual = isActual;
        this.isExternal = isExternal;
        this.isDelegated = isDelegated;
    }

    @NotNull
    public static PropertyDescriptorImpl create(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations, @NotNull Modality modality, @NotNull Visibility visibility, boolean isVar, @NotNull Name name, @NotNull CallableMemberDescriptor.Kind kind, @NotNull SourceElement source, boolean lateInit, boolean isConst, boolean isExpect, boolean isActual, boolean isExternal, boolean isDelegated) {
        return new PropertyDescriptorImpl(containingDeclaration, null, annotations, modality, visibility, isVar, name, kind, source, lateInit, isConst, isExpect, isActual, isExternal, isDelegated);
    }

    public void setType(@NotNull KotlinType outType, @NotNull List<? extends TypeParameterDescriptor> typeParameters, @Nullable ReceiverParameterDescriptor dispatchReceiverParameter, @Nullable ReceiverParameterDescriptor extensionReceiverParameter) {
        setOutType(outType);
        this.typeParameters = new ArrayList(typeParameters);
        this.extensionReceiverParameter = extensionReceiverParameter;
        this.dispatchReceiverParameter = dispatchReceiverParameter;
    }

    public void initialize(@Nullable PropertyGetterDescriptorImpl getter, @Nullable PropertySetterDescriptor setter) {
        initialize(getter, setter, null, null);
    }

    public void initialize(@Nullable PropertyGetterDescriptorImpl getter, @Nullable PropertySetterDescriptor setter, @Nullable FieldDescriptor backingField, @Nullable FieldDescriptor delegateField) {
        this.getter = getter;
        this.setter = setter;
        this.backingField = backingField;
        this.delegateField = delegateField;
    }

    public void setSetterProjectedOut(boolean setterProjectedOut) {
        this.setterProjectedOut = setterProjectedOut;
    }

    public void setVisibility(@NotNull Visibility visibility) {
        this.visibility = visibility;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.VariableDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor
    @NotNull
    public List<TypeParameterDescriptor> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.VariableDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor
    @Nullable
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        return this.extensionReceiverParameter;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.VariableDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor
    @Nullable
    public ReceiverParameterDescriptor getDispatchReceiverParameter() {
        return this.dispatchReceiverParameter;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.VariableDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor
    @NotNull
    public KotlinType getReturnType() {
        return getType();
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    @NotNull
    public Modality getModality() {
        return this.modality;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility, kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    @NotNull
    public Visibility getVisibility() {
        return this.visibility;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor
    @Nullable
    public PropertyGetterDescriptorImpl getGetter() {
        return this.getter;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor
    @Nullable
    public PropertySetterDescriptor getSetter() {
        return this.setter;
    }

    public boolean isSetterProjectedOut() {
        return this.setterProjectedOut;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor
    public boolean isLateInit() {
        return this.lateInit;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.VariableDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor
    public boolean isConst() {
        return this.isConst;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    public boolean isExternal() {
        return this.isExternal;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptorWithAccessors
    public boolean isDelegated() {
        return this.isDelegated;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor
    @NotNull
    public List<PropertyAccessorDescriptor> getAccessors() {
        List<PropertyAccessorDescriptor> result = new ArrayList<>(2);
        PropertyAccessorDescriptor propertyAccessorDescriptor = this.getter;
        if (propertyAccessorDescriptor != null) {
            result.add(propertyAccessorDescriptor);
        }
        PropertyAccessorDescriptor propertyAccessorDescriptor2 = this.setter;
        if (propertyAccessorDescriptor2 != null) {
            result.add(propertyAccessorDescriptor2);
        }
        return result;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.Substitutable
    public PropertyDescriptor substitute(@NotNull TypeSubstitutor originalSubstitutor) {
        if (originalSubstitutor.isEmpty()) {
            return this;
        }
        return newCopyBuilder().setSubstitution(originalSubstitutor.getSubstitution()).setOriginal(getOriginal()).build();
    }

    public class CopyConfiguration {
        private ReceiverParameterDescriptor dispatchReceiverParameter;
        private CallableMemberDescriptor.Kind kind;
        private Modality modality;
        private Name name;
        private DeclarationDescriptor owner;
        private Visibility visibility;
        private PropertyDescriptor original = null;
        private TypeSubstitution substitution = TypeSubstitution.EMPTY;
        private boolean copyOverrides = true;
        private List<TypeParameterDescriptor> newTypeParameters = null;

        public CopyConfiguration() {
            this.owner = PropertyDescriptorImpl.this.getContainingDeclaration();
            this.modality = PropertyDescriptorImpl.this.getModality();
            this.visibility = PropertyDescriptorImpl.this.getVisibility();
            this.kind = PropertyDescriptorImpl.this.getKind();
            this.dispatchReceiverParameter = PropertyDescriptorImpl.this.dispatchReceiverParameter;
            this.name = PropertyDescriptorImpl.this.getName();
        }

        @NotNull
        public CopyConfiguration setOwner(@NotNull DeclarationDescriptor owner) {
            this.owner = owner;
            return this;
        }

        @NotNull
        public CopyConfiguration setOriginal(@Nullable CallableMemberDescriptor original) {
            this.original = (PropertyDescriptor) original;
            return this;
        }

        @NotNull
        public CopyConfiguration setModality(@NotNull Modality modality) {
            this.modality = modality;
            return this;
        }

        @NotNull
        public CopyConfiguration setVisibility(@NotNull Visibility visibility) {
            this.visibility = visibility;
            return this;
        }

        @NotNull
        public CopyConfiguration setKind(@NotNull CallableMemberDescriptor.Kind kind) {
            this.kind = kind;
            return this;
        }

        @NotNull
        public CopyConfiguration setSubstitution(@NotNull TypeSubstitution substitution) {
            this.substitution = substitution;
            return this;
        }

        @NotNull
        public CopyConfiguration setCopyOverrides(boolean copyOverrides) {
            this.copyOverrides = copyOverrides;
            return this;
        }

        @Nullable
        public PropertyDescriptor build() {
            return PropertyDescriptorImpl.this.doSubstitute(this);
        }
    }

    @NotNull
    public CopyConfiguration newCopyBuilder() {
        return new CopyConfiguration();
    }

    @Nullable
    protected PropertyDescriptor doSubstitute(@NotNull CopyConfiguration copyConfiguration) {
        ReceiverParameterDescriptor substitutedDispatchReceiver;
        ReceiverParameterDescriptor substitutedExtensionReceiver;
        PropertyGetterDescriptorImpl propertyGetterDescriptorImpl;
        PropertyGetterDescriptor getter;
        PropertySetterDescriptorImpl propertySetterDescriptorImpl;
        PropertySetterDescriptor setter;
        PropertyDescriptorImpl substitutedDescriptor = createSubstitutedCopy(copyConfiguration.owner, copyConfiguration.modality, copyConfiguration.visibility, copyConfiguration.original, copyConfiguration.kind, copyConfiguration.name);
        List<TypeParameterDescriptor> originalTypeParameters = copyConfiguration.newTypeParameters == null ? getTypeParameters() : copyConfiguration.newTypeParameters;
        List<TypeParameterDescriptor> substitutedTypeParameters = new ArrayList<>(originalTypeParameters.size());
        TypeSubstitutor substitutor = DescriptorSubstitutor.substituteTypeParameters(originalTypeParameters, copyConfiguration.substitution, substitutedDescriptor, substitutedTypeParameters);
        KotlinType originalOutType = getType();
        KotlinType outType = substitutor.substitute(originalOutType, Variance.OUT_VARIANCE);
        if (outType != null) {
            ReceiverParameterDescriptor dispatchReceiver = copyConfiguration.dispatchReceiverParameter;
            if (dispatchReceiver != null) {
                ReceiverParameterDescriptor substitutedDispatchReceiver2 = dispatchReceiver.substitute(substitutor);
                if (substitutedDispatchReceiver2 == null) {
                    return null;
                }
                substitutedDispatchReceiver = substitutedDispatchReceiver2;
            } else {
                substitutedDispatchReceiver = null;
            }
            ReceiverParameterDescriptor receiverParameterDescriptor = this.extensionReceiverParameter;
            if (receiverParameterDescriptor != null) {
                KotlinType substitutedReceiverType = substitutor.substitute(receiverParameterDescriptor.getType(), Variance.IN_VARIANCE);
                if (substitutedReceiverType == null) {
                    return null;
                }
                ReceiverParameterDescriptor substitutedExtensionReceiver2 = new ReceiverParameterDescriptorImpl(substitutedDescriptor, new ExtensionReceiver(substitutedDescriptor, substitutedReceiverType, this.extensionReceiverParameter.getValue()), this.extensionReceiverParameter.getAnnotations());
                substitutedExtensionReceiver = substitutedExtensionReceiver2;
            } else {
                substitutedExtensionReceiver = null;
            }
            substitutedDescriptor.setType(outType, substitutedTypeParameters, substitutedDispatchReceiver, substitutedExtensionReceiver);
            PropertyGetterDescriptorImpl propertyGetterDescriptorImpl2 = this.getter;
            if (propertyGetterDescriptorImpl2 != null) {
                Annotations annotations = propertyGetterDescriptorImpl2.getAnnotations();
                Modality modality = copyConfiguration.modality;
                Visibility visibilityNormalizeVisibility = normalizeVisibility(this.getter.getVisibility(), copyConfiguration.kind);
                boolean zIsDefault = this.getter.isDefault();
                boolean zIsExternal = this.getter.isExternal();
                boolean zIsInline = this.getter.isInline();
                CallableMemberDescriptor.Kind kind = copyConfiguration.kind;
                if (copyConfiguration.original != null) {
                    getter = copyConfiguration.original.getGetter();
                } else {
                    getter = null;
                }
                propertyGetterDescriptorImpl = new PropertyGetterDescriptorImpl(substitutedDescriptor, annotations, modality, visibilityNormalizeVisibility, zIsDefault, zIsExternal, zIsInline, kind, getter, SourceElement.NO_SOURCE);
            } else {
                propertyGetterDescriptorImpl = null;
            }
            PropertyGetterDescriptorImpl newGetter = propertyGetterDescriptorImpl;
            if (newGetter != null) {
                KotlinType returnType = this.getter.getReturnType();
                newGetter.setInitialSignatureDescriptor(getSubstitutedInitialSignatureDescriptor(substitutor, this.getter));
                newGetter.initialize(returnType != null ? substitutor.substitute(returnType, Variance.OUT_VARIANCE) : null);
            }
            PropertySetterDescriptor propertySetterDescriptor = this.setter;
            if (propertySetterDescriptor != null) {
                Annotations annotations2 = propertySetterDescriptor.getAnnotations();
                Modality modality2 = copyConfiguration.modality;
                Visibility visibilityNormalizeVisibility2 = normalizeVisibility(this.setter.getVisibility(), copyConfiguration.kind);
                boolean zIsDefault2 = this.setter.isDefault();
                boolean zIsExternal2 = this.setter.isExternal();
                boolean zIsInline2 = this.setter.isInline();
                CallableMemberDescriptor.Kind kind2 = copyConfiguration.kind;
                if (copyConfiguration.original != null) {
                    setter = copyConfiguration.original.getSetter();
                } else {
                    setter = null;
                }
                propertySetterDescriptorImpl = new PropertySetterDescriptorImpl(substitutedDescriptor, annotations2, modality2, visibilityNormalizeVisibility2, zIsDefault2, zIsExternal2, zIsInline2, kind2, setter, SourceElement.NO_SOURCE);
            } else {
                propertySetterDescriptorImpl = null;
            }
            PropertySetterDescriptorImpl newSetter = propertySetterDescriptorImpl;
            if (newSetter != null) {
                List<ValueParameterDescriptor> substitutedValueParameters = FunctionDescriptorImpl.getSubstitutedValueParameters(newSetter, this.setter.getValueParameters(), substitutor, false, false, null);
                if (substitutedValueParameters == null) {
                    substitutedDescriptor.setSetterProjectedOut(true);
                    substitutedValueParameters = Collections.singletonList(PropertySetterDescriptorImpl.createSetterParameter(newSetter, DescriptorUtilsKt.getBuiltIns(copyConfiguration.owner).getNothingType(), this.setter.getValueParameters().get(0).getAnnotations()));
                }
                if (substitutedValueParameters.size() != 1) {
                    throw new IllegalStateException();
                }
                newSetter.setInitialSignatureDescriptor(getSubstitutedInitialSignatureDescriptor(substitutor, this.setter));
                newSetter.initialize(substitutedValueParameters.get(0));
            }
            FieldDescriptor fieldDescriptor = this.backingField;
            FieldDescriptor fieldDescriptorImpl = fieldDescriptor == null ? null : new FieldDescriptorImpl(fieldDescriptor.getAnnotations(), substitutedDescriptor);
            FieldDescriptor fieldDescriptor2 = this.delegateField;
            substitutedDescriptor.initialize(newGetter, newSetter, fieldDescriptorImpl, fieldDescriptor2 == null ? null : new FieldDescriptorImpl(fieldDescriptor2.getAnnotations(), substitutedDescriptor));
            if (copyConfiguration.copyOverrides) {
                Collection<CallableMemberDescriptor> overridden = SmartSet.create();
                for (PropertyDescriptor propertyDescriptor : getOverriddenDescriptors()) {
                    overridden.add(propertyDescriptor.substitute(substitutor));
                }
                substitutedDescriptor.setOverriddenDescriptors(overridden);
            }
            if (isConst() && this.compileTimeInitializer != null) {
                substitutedDescriptor.setCompileTimeInitializer(this.compileTimeInitializer);
            }
            return substitutedDescriptor;
        }
        return null;
    }

    private static Visibility normalizeVisibility(Visibility prev, CallableMemberDescriptor.Kind kind) {
        if (kind == CallableMemberDescriptor.Kind.FAKE_OVERRIDE && Visibilities.isPrivate(prev.normalize())) {
            return Visibilities.INVISIBLE_FAKE;
        }
        return prev;
    }

    private static FunctionDescriptor getSubstitutedInitialSignatureDescriptor(@NotNull TypeSubstitutor substitutor, @NotNull PropertyAccessorDescriptor accessorDescriptor) {
        if (accessorDescriptor.getInitialSignatureDescriptor() != null) {
            return accessorDescriptor.getInitialSignatureDescriptor().substitute(substitutor);
        }
        return null;
    }

    @NotNull
    protected PropertyDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @NotNull Modality newModality, @NotNull Visibility newVisibility, @Nullable PropertyDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, @NotNull Name newName) {
        return new PropertyDescriptorImpl(newOwner, original, getAnnotations(), newModality, newVisibility, isVar(), newName, kind, SourceElement.NO_SOURCE, isLateInit(), isConst(), isExpect(), isActual(), isExternal(), isDelegated());
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        return visitor.visitPropertyDescriptor(this, data);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.VariableDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorNonRootImpl, kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    @NotNull
    public PropertyDescriptor getOriginal() {
        PropertyDescriptor propertyDescriptor = this.original;
        return propertyDescriptor == this ? this : propertyDescriptor.getOriginal();
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor
    @NotNull
    public CallableMemberDescriptor.Kind getKind() {
        return this.kind;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    public boolean isExpect() {
        return this.isExpect;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    public boolean isActual() {
        return this.isActual;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor
    @Nullable
    public FieldDescriptor getBackingField() {
        return this.backingField;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor
    @Nullable
    public FieldDescriptor getDelegateField() {
        return this.delegateField;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor
    public void setOverriddenDescriptors(@NotNull Collection<? extends CallableMemberDescriptor> collection) {
        this.overriddenProperties = collection;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.VariableDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor
    @NotNull
    public Collection<? extends PropertyDescriptor> getOverriddenDescriptors() {
        Collection<? extends PropertyDescriptor> collection = this.overriddenProperties;
        return collection != null ? collection : Collections.emptyList();
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor
    @NotNull
    public PropertyDescriptor copy(DeclarationDescriptor newOwner, Modality modality, Visibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        return newCopyBuilder().setOwner(newOwner).setOriginal(null).setModality(modality).setVisibility(visibility).setKind(kind).setCopyOverrides(copyOverrides).build();
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.VariableDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor
    @Nullable
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> key) {
        return null;
    }
}
