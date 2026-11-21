package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty1;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FieldDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertySetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedSimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedTypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.NonEmptyDeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: MemberDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class MemberDeserializer {
    private final AnnotationDeserializer annotationDeserializer;
    private final DeserializationContext c;

    public MemberDeserializer(@NotNull DeserializationContext c) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        this.c = c;
        this.annotationDeserializer = new AnnotationDeserializer(this.c.getComponents().getModuleDescriptor(), this.c.getComponents().getNotFoundClasses());
    }

    @NotNull
    public final PropertyDescriptor loadProperty(@NotNull ProtoBuf.Property proto) {
        final ProtoBuf.Property property;
        Annotations receiverAnnotations;
        PropertyGetterDescriptorImpl getter;
        PropertySetterDescriptorImpl setter;
        PropertyGetterDescriptorImpl propertyGetterDescriptorImplCreateDefaultGetter;
        KotlinType receiverType;
        Intrinsics.checkParameterIsNotNull(proto, "proto");
        int flags = proto.hasFlags() ? proto.getFlags() : loadOldFlags(proto.getOldFlags());
        DeclarationDescriptor containingDeclaration = this.c.getContainingDeclaration();
        Annotations annotations = getAnnotations(proto, flags, AnnotatedCallableKind.PROPERTY);
        Modality modality = ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(flags));
        Visibility visibility = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(flags));
        Boolean bool = Flags.IS_VAR.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool, "Flags.IS_VAR.get(flags)");
        boolean zBooleanValue = bool.booleanValue();
        Name name = NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName());
        CallableMemberDescriptor.Kind kindMemberKind = ProtoEnumFlags.INSTANCE.memberKind(Flags.MEMBER_KIND.get(flags));
        Boolean bool2 = Flags.IS_LATEINIT.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool2, "Flags.IS_LATEINIT.get(flags)");
        boolean zBooleanValue2 = bool2.booleanValue();
        Boolean bool3 = Flags.IS_CONST.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool3, "Flags.IS_CONST.get(flags)");
        boolean zBooleanValue3 = bool3.booleanValue();
        Boolean bool4 = Flags.IS_EXTERNAL_PROPERTY.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool4, "Flags.IS_EXTERNAL_PROPERTY.get(flags)");
        boolean zBooleanValue4 = bool4.booleanValue();
        Boolean bool5 = Flags.IS_DELEGATED.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool5, "Flags.IS_DELEGATED.get(flags)");
        boolean zBooleanValue5 = bool5.booleanValue();
        Boolean bool6 = Flags.IS_EXPECT_PROPERTY.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool6, "Flags.IS_EXPECT_PROPERTY.get(flags)");
        final DeserializedPropertyDescriptor property2 = new DeserializedPropertyDescriptor(containingDeclaration, null, annotations, modality, visibility, zBooleanValue, name, kindMemberKind, zBooleanValue2, zBooleanValue3, zBooleanValue4, zBooleanValue5, bool6.booleanValue(), proto, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource());
        DeserializationContext deserializationContext = this.c;
        List<ProtoBuf.TypeParameter> typeParameterList = proto.getTypeParameterList();
        Intrinsics.checkExpressionValueIsNotNull(typeParameterList, "proto.typeParameterList");
        DeserializationContext local = deserializationContext.childContext(property2, typeParameterList, (60 & 4) != 0 ? deserializationContext.nameResolver : null, (60 & 8) != 0 ? deserializationContext.typeTable : null, (60 & 16) != 0 ? deserializationContext.versionRequirementTable : null, (60 & 32) != 0 ? deserializationContext.metadataVersion : null);
        Boolean bool7 = Flags.HAS_GETTER.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool7, "Flags.HAS_GETTER.get(flags)");
        boolean hasGetter = bool7.booleanValue();
        if (!hasGetter || !ProtoTypeTableUtilKt.hasReceiver(proto)) {
            property = proto;
            receiverAnnotations = Annotations.Companion.getEMPTY();
        } else {
            property = proto;
            receiverAnnotations = getReceiverParameterAnnotations(property, AnnotatedCallableKind.PROPERTY_GETTER);
        }
        KotlinType kotlinTypeType = local.getTypeDeserializer().type(ProtoTypeTableUtilKt.returnType(property, this.c.getTypeTable()));
        List<TypeParameterDescriptor> ownTypeParameters = local.getTypeDeserializer().getOwnTypeParameters();
        ReceiverParameterDescriptor dispatchReceiverParameter = getDispatchReceiverParameter();
        ProtoBuf.Type p1 = ProtoTypeTableUtilKt.receiverType(property, this.c.getTypeTable());
        property2.setType(kotlinTypeType, ownTypeParameters, dispatchReceiverParameter, (p1 == null || (receiverType = local.getTypeDeserializer().type(p1)) == null) ? null : DescriptorFactory.createExtensionReceiverParameterForCallable(property2, receiverType, receiverAnnotations));
        Boolean bool8 = Flags.HAS_ANNOTATIONS.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool8, "Flags.HAS_ANNOTATIONS.get(flags)");
        int defaultAccessorFlags = Flags.getAccessorFlags(bool8.booleanValue(), Flags.VISIBILITY.get(flags), Flags.MODALITY.get(flags), false, false, false);
        if (hasGetter) {
            int getterFlags = proto.hasGetterFlags() ? proto.getGetterFlags() : defaultAccessorFlags;
            Boolean bool9 = Flags.IS_NOT_DEFAULT.get(getterFlags);
            Intrinsics.checkExpressionValueIsNotNull(bool9, "Flags.IS_NOT_DEFAULT.get(getterFlags)");
            boolean isNotDefault = bool9.booleanValue();
            Boolean bool10 = Flags.IS_EXTERNAL_ACCESSOR.get(getterFlags);
            Intrinsics.checkExpressionValueIsNotNull(bool10, "Flags.IS_EXTERNAL_ACCESSOR.get(getterFlags)");
            boolean isExternal = bool10.booleanValue();
            Boolean bool11 = Flags.IS_INLINE_ACCESSOR.get(getterFlags);
            Intrinsics.checkExpressionValueIsNotNull(bool11, "Flags.IS_INLINE_ACCESSOR.get(getterFlags)");
            boolean isInline = bool11.booleanValue();
            Annotations annotations2 = getAnnotations(property, getterFlags, AnnotatedCallableKind.PROPERTY_GETTER);
            if (isNotDefault) {
                propertyGetterDescriptorImplCreateDefaultGetter = new PropertyGetterDescriptorImpl(property2, annotations2, ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(getterFlags)), ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(getterFlags)), !isNotDefault, isExternal, isInline, property2.getKind(), null, SourceElement.NO_SOURCE);
            } else {
                propertyGetterDescriptorImplCreateDefaultGetter = DescriptorFactory.createDefaultGetter(property2, annotations2);
                Intrinsics.checkExpressionValueIsNotNull(propertyGetterDescriptorImplCreateDefaultGetter, "DescriptorFactory.create…er(property, annotations)");
            }
            PropertyGetterDescriptorImpl getter2 = propertyGetterDescriptorImplCreateDefaultGetter;
            getter2.initialize(property2.getReturnType());
            getter = getter2;
        } else {
            getter = null;
        }
        Boolean bool12 = Flags.HAS_SETTER.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool12, "Flags.HAS_SETTER.get(flags)");
        if (bool12.booleanValue()) {
            int setterFlags = proto.hasSetterFlags() ? proto.getSetterFlags() : defaultAccessorFlags;
            Boolean bool13 = Flags.IS_NOT_DEFAULT.get(setterFlags);
            Intrinsics.checkExpressionValueIsNotNull(bool13, "Flags.IS_NOT_DEFAULT.get(setterFlags)");
            boolean isNotDefault2 = bool13.booleanValue();
            Boolean bool14 = Flags.IS_EXTERNAL_ACCESSOR.get(setterFlags);
            Intrinsics.checkExpressionValueIsNotNull(bool14, "Flags.IS_EXTERNAL_ACCESSOR.get(setterFlags)");
            boolean isExternal2 = bool14.booleanValue();
            Boolean bool15 = Flags.IS_INLINE_ACCESSOR.get(setterFlags);
            Intrinsics.checkExpressionValueIsNotNull(bool15, "Flags.IS_INLINE_ACCESSOR.get(setterFlags)");
            boolean isInline2 = bool15.booleanValue();
            Annotations annotations3 = getAnnotations(property, setterFlags, AnnotatedCallableKind.PROPERTY_SETTER);
            if (isNotDefault2) {
                PropertySetterDescriptorImpl setter2 = new PropertySetterDescriptorImpl(property2, annotations3, ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(setterFlags)), ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(setterFlags)), !isNotDefault2, isExternal2, isInline2, property2.getKind(), null, SourceElement.NO_SOURCE);
                DeserializationContext setterLocal = local.childContext(setter2, CollectionsKt.emptyList(), (60 & 4) != 0 ? local.nameResolver : null, (60 & 8) != 0 ? local.typeTable : null, (60 & 16) != 0 ? local.versionRequirementTable : null, (60 & 32) != 0 ? local.metadataVersion : null);
                setter = setter2;
                setter.initialize((ValueParameterDescriptor) CollectionsKt.single((List) setterLocal.getMemberDeserializer().valueParameters(CollectionsKt.listOf(proto.getSetterValueParameter()), property, AnnotatedCallableKind.PROPERTY_SETTER)));
            } else {
                PropertySetterDescriptorImpl propertySetterDescriptorImplCreateDefaultSetter = DescriptorFactory.createDefaultSetter(property2, annotations3, Annotations.Companion.getEMPTY());
                Intrinsics.checkExpressionValueIsNotNull(propertySetterDescriptorImplCreateDefaultSetter, "DescriptorFactory.create…ptor */\n                )");
                setter = propertySetterDescriptorImplCreateDefaultSetter;
            }
        } else {
            setter = null;
        }
        PropertySetterDescriptorImpl setter3 = setter;
        Boolean bool16 = Flags.HAS_CONSTANT.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool16, "Flags.HAS_CONSTANT.get(flags)");
        if (bool16.booleanValue()) {
            property2.setCompileTimeInitializer(this.c.getStorageManager().createNullableLazyValue(new Function0<ConstantValue<?>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer.loadProperty.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ConstantValue<?> invoke() {
                    MemberDeserializer memberDeserializer = MemberDeserializer.this;
                    ProtoContainer container = memberDeserializer.asProtoContainer(memberDeserializer.c.getContainingDeclaration());
                    if (container == null) {
                        Intrinsics.throwNpe();
                    }
                    AnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>> annotationAndConstantLoader = MemberDeserializer.this.c.getComponents().getAnnotationAndConstantLoader();
                    ProtoBuf.Property property3 = property;
                    KotlinType returnType = property2.getReturnType();
                    Intrinsics.checkExpressionValueIsNotNull(returnType, "property.returnType");
                    return annotationAndConstantLoader.loadPropertyConstant(container, property3, returnType);
                }
            }));
        }
        property2.initialize(getter, setter3, new FieldDescriptorImpl(getPropertyFieldAnnotations(property, false), property2), new FieldDescriptorImpl(getPropertyFieldAnnotations(property, true), property2), checkExperimentalCoroutine(property2, local.getTypeDeserializer()));
        return property2;
    }

    private final DeserializedMemberDescriptor.CoroutinesCompatibilityMode checkExperimentalCoroutine(@NotNull DeserializedMemberDescriptor $receiver, TypeDeserializer typeDeserializer) {
        if (!versionAndReleaseCoroutinesMismatch($receiver)) {
            return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
        }
        forceUpperBoundsComputation(typeDeserializer);
        if (typeDeserializer.getExperimentalSuspendFunctionTypeEncountered()) {
            return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
        }
        return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }

    private final void forceUpperBoundsComputation(TypeDeserializer typeDeserializer) {
        Iterable $receiver$iv = typeDeserializer.getOwnTypeParameters();
        for (Object element$iv : $receiver$iv) {
            TypeParameterDescriptor it = (TypeParameterDescriptor) element$iv;
            it.getUpperBounds();
        }
    }

    private final void initializeWithCoroutinesExperimentalityStatus(@NotNull DeserializedSimpleFunctionDescriptor $receiver, ReceiverParameterDescriptor extensionReceiverParameter, ReceiverParameterDescriptor dispatchReceiverParameter, List<? extends TypeParameterDescriptor> list, List<? extends ValueParameterDescriptor> list2, KotlinType unsubstitutedReturnType, Modality modality, Visibility visibility, Map<? extends CallableDescriptor.UserDataKey<?>, ?> map, boolean isSuspend) {
        $receiver.initialize(extensionReceiverParameter, dispatchReceiverParameter, list, list2, unsubstitutedReturnType, modality, visibility, map, computeExperimentalityModeForFunctions($receiver, extensionReceiverParameter, list2, list, unsubstitutedReturnType, isSuspend));
    }

    private final DeserializedMemberDescriptor.CoroutinesCompatibilityMode computeExperimentalityModeForFunctions(@NotNull DeserializedCallableMemberDescriptor $receiver, ReceiverParameterDescriptor extensionReceiverParameter, Collection<? extends ValueParameterDescriptor> collection, Collection<? extends TypeParameterDescriptor> collection2, KotlinType returnType, boolean isSuspend) {
        int $i$a$1$any;
        boolean z;
        List types;
        DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesCompatibilityMode;
        boolean z2;
        if (versionAndReleaseCoroutinesMismatch($receiver) && !Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull($receiver), SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME)) {
            Collection<? extends ValueParameterDescriptor> $receiver$iv = collection;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
            for (Object item$iv$iv : $receiver$iv) {
                destination$iv$iv.add(((ValueParameterDescriptor) item$iv$iv).getType());
            }
            List types2 = CollectionsKt.plus((Collection) ((List) destination$iv$iv), (Iterable) CollectionsKt.listOfNotNull(extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null));
            if (returnType != null && containsSuspendFunctionType(returnType)) {
                return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
            }
            Collection<? extends TypeParameterDescriptor> $receiver$iv2 = collection2;
            if (!($receiver$iv2 instanceof Collection) || !$receiver$iv2.isEmpty()) {
                Iterator it = $receiver$iv2.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        $i$a$1$any = 0;
                        break;
                    }
                    Object element$iv = it.next();
                    TypeParameterDescriptor typeParameter = (TypeParameterDescriptor) element$iv;
                    Iterable upperBounds = typeParameter.getUpperBounds();
                    Intrinsics.checkExpressionValueIsNotNull(upperBounds, "typeParameter.upperBounds");
                    Iterable $receiver$iv3 = upperBounds;
                    if (!($receiver$iv3 instanceof Collection) || !((Collection) $receiver$iv3).isEmpty()) {
                        Iterator it2 = $receiver$iv3.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                z = false;
                                break;
                            }
                            Object element$iv2 = it2.next();
                            KotlinType it3 = (KotlinType) element$iv2;
                            Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                            if (containsSuspendFunctionType(it3)) {
                                z = true;
                                break;
                            }
                        }
                    } else {
                        z = false;
                    }
                    if (z) {
                        $i$a$1$any = 1;
                        break;
                    }
                }
            } else {
                $i$a$1$any = 0;
            }
            if ($i$a$1$any != 0) {
                return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
            }
            List $receiver$iv4 = types2;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv4, 10));
            for (Object item$iv$iv2 : $receiver$iv4) {
                KotlinType type = (KotlinType) item$iv$iv2;
                Intrinsics.checkExpressionValueIsNotNull(type, "type");
                if (!FunctionTypesKt.isSuspendFunctionType(type) || type.getArguments().size() > 3) {
                    types = types2;
                    coroutinesCompatibilityMode = containsSuspendFunctionType(type) ? DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE : DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
                } else {
                    Iterable $receiver$iv5 = type.getArguments();
                    if (($receiver$iv5 instanceof Collection) && ((Collection) $receiver$iv5).isEmpty()) {
                        types = types2;
                        z2 = false;
                    } else {
                        Iterator it4 = $receiver$iv5.iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                types = types2;
                                z2 = false;
                                break;
                            }
                            Object element$iv3 = it4.next();
                            KotlinType type2 = ((TypeProjection) element$iv3).getType();
                            types = types2;
                            Intrinsics.checkExpressionValueIsNotNull(type2, "it.type");
                            if (containsSuspendFunctionType(type2)) {
                                z2 = true;
                                break;
                            }
                            types2 = types;
                        }
                    }
                    coroutinesCompatibilityMode = z2 ? DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE : DeserializedMemberDescriptor.CoroutinesCompatibilityMode.NEEDS_WRAPPER;
                }
                destination$iv$iv2.add(coroutinesCompatibilityMode);
                types2 = types;
            }
            DeserializedMemberDescriptor.CoroutinesCompatibilityMode maxFromParameters = (DeserializedMemberDescriptor.CoroutinesCompatibilityMode) CollectionsKt.max(destination$iv$iv2);
            if (maxFromParameters == null) {
                maxFromParameters = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
            }
            return (DeserializedMemberDescriptor.CoroutinesCompatibilityMode) ComparisonsKt.maxOf(isSuspend ? DeserializedMemberDescriptor.CoroutinesCompatibilityMode.NEEDS_WRAPPER : DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE, maxFromParameters);
        }
        return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer$containsSuspendFunctionType$1, reason: invalid class name */
    /* JADX INFO: compiled from: MemberDeserializer.kt */
    final class AnonymousClass1 extends PropertyReference1 {
        public static final KProperty1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public String getName() {
            return "isSuspendFunctionType";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(FunctionTypesKt.class, "deserialization");
        }

        @Override // kotlin.jvm.internal.CallableReference
        public String getSignature() {
            return "isSuspendFunctionType(Lorg/jetbrains/kotlin/types/KotlinType;)Z";
        }

        @Override // kotlin.reflect.KProperty1
        @Nullable
        public Object get(@Nullable Object receiver) {
            return Boolean.valueOf(FunctionTypesKt.isSuspendFunctionType((UnwrappedType) receiver));
        }
    }

    private final boolean containsSuspendFunctionType(@NotNull KotlinType $receiver) {
        return TypeUtilsKt.contains($receiver, AnonymousClass1.INSTANCE);
    }

    private final boolean versionAndReleaseCoroutinesMismatch(@NotNull DeserializedMemberDescriptor $receiver) {
        boolean z;
        if (!this.c.getComponents().getConfiguration().getReleaseCoroutines()) {
            return false;
        }
        Iterable $receiver$iv = $receiver.getVersionRequirements();
        if (($receiver$iv instanceof Collection) && ((Collection) $receiver$iv).isEmpty()) {
            z = true;
        } else {
            for (Object element$iv : $receiver$iv) {
                VersionRequirement it = (VersionRequirement) element$iv;
                if (Intrinsics.areEqual(it.getVersion(), new VersionRequirement.Version(1, 3, 0, 4, null)) && it.getKind() == ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    private final int loadOldFlags(int oldFlags) {
        int lowSixBits = oldFlags & 63;
        int rest = (oldFlags >> 8) << 6;
        return lowSixBits + rest;
    }

    @NotNull
    public final SimpleFunctionDescriptor loadFunction(@NotNull ProtoBuf.Function proto) {
        Annotations empty;
        VersionRequirementTable versionRequirementTable;
        KotlinType receiverType;
        Intrinsics.checkParameterIsNotNull(proto, "proto");
        int flags = proto.hasFlags() ? proto.getFlags() : loadOldFlags(proto.getOldFlags());
        Annotations annotations = getAnnotations(proto, flags, AnnotatedCallableKind.FUNCTION);
        if (ProtoTypeTableUtilKt.hasReceiver(proto)) {
            empty = getReceiverParameterAnnotations(proto, AnnotatedCallableKind.FUNCTION);
        } else {
            empty = Annotations.Companion.getEMPTY();
        }
        Annotations receiverAnnotations = empty;
        if (Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(this.c.getContainingDeclaration()).child(NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName())), SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME)) {
            versionRequirementTable = VersionRequirementTable.Companion.getEMPTY();
        } else {
            versionRequirementTable = this.c.getVersionRequirementTable();
        }
        DeserializedSimpleFunctionDescriptor function = new DeserializedSimpleFunctionDescriptor(this.c.getContainingDeclaration(), null, annotations, NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName()), ProtoEnumFlags.INSTANCE.memberKind(Flags.MEMBER_KIND.get(flags)), proto, this.c.getNameResolver(), this.c.getTypeTable(), versionRequirementTable, this.c.getContainerSource(), null, 1024, null);
        DeserializationContext deserializationContext = this.c;
        List<ProtoBuf.TypeParameter> typeParameterList = proto.getTypeParameterList();
        Intrinsics.checkExpressionValueIsNotNull(typeParameterList, "proto.typeParameterList");
        DeserializationContext local = deserializationContext.childContext(function, typeParameterList, (60 & 4) != 0 ? deserializationContext.nameResolver : null, (60 & 8) != 0 ? deserializationContext.typeTable : null, (60 & 16) != 0 ? deserializationContext.versionRequirementTable : null, (60 & 32) != 0 ? deserializationContext.metadataVersion : null);
        ProtoBuf.Type p1 = ProtoTypeTableUtilKt.receiverType(proto, this.c.getTypeTable());
        ReceiverParameterDescriptor receiverParameterDescriptorCreateExtensionReceiverParameterForCallable = (p1 == null || (receiverType = local.getTypeDeserializer().type(p1)) == null) ? null : DescriptorFactory.createExtensionReceiverParameterForCallable(function, receiverType, receiverAnnotations);
        ReceiverParameterDescriptor dispatchReceiverParameter = getDispatchReceiverParameter();
        List<TypeParameterDescriptor> ownTypeParameters = local.getTypeDeserializer().getOwnTypeParameters();
        MemberDeserializer memberDeserializer = local.getMemberDeserializer();
        List<ProtoBuf.ValueParameter> valueParameterList = proto.getValueParameterList();
        Intrinsics.checkExpressionValueIsNotNull(valueParameterList, "proto.valueParameterList");
        List<ValueParameterDescriptor> listValueParameters = memberDeserializer.valueParameters(valueParameterList, proto, AnnotatedCallableKind.FUNCTION);
        KotlinType kotlinTypeType = local.getTypeDeserializer().type(ProtoTypeTableUtilKt.returnType(proto, this.c.getTypeTable()));
        Modality modality = ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(flags));
        Visibility visibility = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(flags));
        Map<? extends CallableDescriptor.UserDataKey<?>, ?> mapEmptyMap = MapsKt.emptyMap();
        Boolean bool = Flags.IS_SUSPEND.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool, "Flags.IS_SUSPEND.get(flags)");
        initializeWithCoroutinesExperimentalityStatus(function, receiverParameterDescriptorCreateExtensionReceiverParameterForCallable, dispatchReceiverParameter, ownTypeParameters, listValueParameters, kotlinTypeType, modality, visibility, mapEmptyMap, bool.booleanValue());
        Boolean bool2 = Flags.IS_OPERATOR.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool2, "Flags.IS_OPERATOR.get(flags)");
        function.setOperator(bool2.booleanValue());
        Boolean bool3 = Flags.IS_INFIX.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool3, "Flags.IS_INFIX.get(flags)");
        function.setInfix(bool3.booleanValue());
        Boolean bool4 = Flags.IS_EXTERNAL_FUNCTION.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool4, "Flags.IS_EXTERNAL_FUNCTION.get(flags)");
        function.setExternal(bool4.booleanValue());
        Boolean bool5 = Flags.IS_INLINE.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool5, "Flags.IS_INLINE.get(flags)");
        function.setInline(bool5.booleanValue());
        Boolean bool6 = Flags.IS_TAILREC.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool6, "Flags.IS_TAILREC.get(flags)");
        function.setTailrec(bool6.booleanValue());
        Boolean bool7 = Flags.IS_SUSPEND.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool7, "Flags.IS_SUSPEND.get(flags)");
        function.setSuspend(bool7.booleanValue());
        Boolean bool8 = Flags.IS_EXPECT_FUNCTION.get(flags);
        Intrinsics.checkExpressionValueIsNotNull(bool8, "Flags.IS_EXPECT_FUNCTION.get(flags)");
        function.setExpect(bool8.booleanValue());
        Pair<CallableDescriptor.UserDataKey<?>, Object> pairDeserializeContractFromFunction = this.c.getComponents().getContractDeserializer().deserializeContractFromFunction(proto, function, this.c.getTypeTable(), this.c.getTypeDeserializer());
        if (pairDeserializeContractFromFunction != null) {
            function.putInUserDataMap(pairDeserializeContractFromFunction.getFirst(), pairDeserializeContractFromFunction.getSecond());
        }
        return function;
    }

    @NotNull
    public final TypeAliasDescriptor loadTypeAlias(@NotNull ProtoBuf.TypeAlias proto) {
        Intrinsics.checkParameterIsNotNull(proto, "proto");
        Annotations.Companion companion = Annotations.Companion;
        Iterable annotationList = proto.getAnnotationList();
        Intrinsics.checkExpressionValueIsNotNull(annotationList, "proto.annotationList");
        Iterable $receiver$iv = annotationList;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            ProtoBuf.Annotation it = (ProtoBuf.Annotation) item$iv$iv;
            AnnotationDeserializer annotationDeserializer = this.annotationDeserializer;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            destination$iv$iv.add(annotationDeserializer.deserializeAnnotation(it, this.c.getNameResolver()));
        }
        Annotations annotations = companion.create((List) destination$iv$iv);
        Visibility visibility = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(proto.getFlags()));
        DeserializedTypeAliasDescriptor typeAlias = new DeserializedTypeAliasDescriptor(this.c.getStorageManager(), this.c.getContainingDeclaration(), annotations, NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName()), visibility, proto, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource());
        DeserializationContext deserializationContext = this.c;
        List<ProtoBuf.TypeParameter> typeParameterList = proto.getTypeParameterList();
        Intrinsics.checkExpressionValueIsNotNull(typeParameterList, "proto.typeParameterList");
        DeserializationContext local = deserializationContext.childContext(typeAlias, typeParameterList, (60 & 4) != 0 ? deserializationContext.nameResolver : null, (60 & 8) != 0 ? deserializationContext.typeTable : null, (60 & 16) != 0 ? deserializationContext.versionRequirementTable : null, (60 & 32) != 0 ? deserializationContext.metadataVersion : null);
        typeAlias.initialize(local.getTypeDeserializer().getOwnTypeParameters(), local.getTypeDeserializer().simpleType(ProtoTypeTableUtilKt.underlyingType(proto, this.c.getTypeTable())), local.getTypeDeserializer().simpleType(ProtoTypeTableUtilKt.expandedType(proto, this.c.getTypeTable())), checkExperimentalCoroutine(typeAlias, local.getTypeDeserializer()));
        return typeAlias;
    }

    private final ReceiverParameterDescriptor getDispatchReceiverParameter() {
        DeclarationDescriptor containingDeclaration = this.c.getContainingDeclaration();
        if (!(containingDeclaration instanceof ClassDescriptor)) {
            containingDeclaration = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
        if (classDescriptor != null) {
            return classDescriptor.getThisAsReceiverParameter();
        }
        return null;
    }

    @NotNull
    public final ClassConstructorDescriptor loadConstructor(@NotNull ProtoBuf.Constructor proto, boolean isPrimary) {
        DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesCompatibilityModeComputeExperimentalityModeForFunctions;
        DeserializationContext c;
        TypeDeserializer typeDeserializer;
        Intrinsics.checkParameterIsNotNull(proto, "proto");
        DeclarationDescriptor containingDeclaration = this.c.getContainingDeclaration();
        if (containingDeclaration == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
        DeserializedClassConstructorDescriptor descriptor = new DeserializedClassConstructorDescriptor(classDescriptor, null, getAnnotations(proto, proto.getFlags(), AnnotatedCallableKind.FUNCTION), isPrimary, CallableMemberDescriptor.Kind.DECLARATION, proto, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource(), null, 1024, null);
        DeserializationContext deserializationContext = this.c;
        DeserializationContext local = deserializationContext.childContext(descriptor, CollectionsKt.emptyList(), (60 & 4) != 0 ? deserializationContext.nameResolver : null, (60 & 8) != 0 ? deserializationContext.typeTable : null, (60 & 16) != 0 ? deserializationContext.versionRequirementTable : null, (60 & 32) != 0 ? deserializationContext.metadataVersion : null);
        MemberDeserializer memberDeserializer = local.getMemberDeserializer();
        List<ProtoBuf.ValueParameter> valueParameterList = proto.getValueParameterList();
        Intrinsics.checkExpressionValueIsNotNull(valueParameterList, "proto.valueParameterList");
        descriptor.initialize(memberDeserializer.valueParameters(valueParameterList, proto, AnnotatedCallableKind.FUNCTION), ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(proto.getFlags())));
        descriptor.setReturnType(classDescriptor.getDefaultType());
        DeclarationDescriptor containingDeclaration2 = this.c.getContainingDeclaration();
        if (!(containingDeclaration2 instanceof DeserializedClassDescriptor)) {
            containingDeclaration2 = null;
        }
        DeserializedClassDescriptor deserializedClassDescriptor = (DeserializedClassDescriptor) containingDeclaration2;
        boolean doesClassContainIncompatibility = (deserializedClassDescriptor == null || (c = deserializedClassDescriptor.getC()) == null || (typeDeserializer = c.getTypeDeserializer()) == null || !typeDeserializer.getExperimentalSuspendFunctionTypeEncountered() || !versionAndReleaseCoroutinesMismatch(descriptor)) ? false : true;
        if (doesClassContainIncompatibility) {
            coroutinesCompatibilityModeComputeExperimentalityModeForFunctions = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
        } else {
            DeserializedClassConstructorDescriptor deserializedClassConstructorDescriptor = descriptor;
            List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters, "descriptor.valueParameters");
            List<ValueParameterDescriptor> list = valueParameters;
            List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
            Intrinsics.checkExpressionValueIsNotNull(typeParameters, "descriptor.typeParameters");
            coroutinesCompatibilityModeComputeExperimentalityModeForFunctions = computeExperimentalityModeForFunctions(deserializedClassConstructorDescriptor, null, list, typeParameters, descriptor.getReturnType(), false);
        }
        descriptor.setCoroutinesExperimentalCompatibilityMode$deserialization(coroutinesCompatibilityModeComputeExperimentalityModeForFunctions);
        return descriptor;
    }

    private final Annotations getAnnotations(final MessageLite proto, int flags, final AnnotatedCallableKind kind) {
        if (!Flags.HAS_ANNOTATIONS.get(flags).booleanValue()) {
            return Annotations.Companion.getEMPTY();
        }
        return new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer.getAnnotations.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends AnnotationDescriptor> invoke() {
                MemberDeserializer memberDeserializer = MemberDeserializer.this;
                ProtoContainer it = memberDeserializer.asProtoContainer(memberDeserializer.c.getContainingDeclaration());
                List<? extends AnnotationDescriptor> list = it != null ? CollectionsKt.toList(MemberDeserializer.this.c.getComponents().getAnnotationAndConstantLoader().loadCallableAnnotations(it, proto, kind)) : null;
                return list != null ? list : CollectionsKt.emptyList();
            }
        });
    }

    private final Annotations getPropertyFieldAnnotations(final ProtoBuf.Property proto, final boolean isDelegate) {
        if (!Flags.HAS_ANNOTATIONS.get(proto.getFlags()).booleanValue()) {
            return Annotations.Companion.getEMPTY();
        }
        return new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer.getPropertyFieldAnnotations.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends AnnotationDescriptor> invoke() {
                List<? extends AnnotationDescriptor> list;
                MemberDeserializer memberDeserializer = MemberDeserializer.this;
                ProtoContainer it = memberDeserializer.asProtoContainer(memberDeserializer.c.getContainingDeclaration());
                if (it != null) {
                    list = isDelegate ? CollectionsKt.toList(MemberDeserializer.this.c.getComponents().getAnnotationAndConstantLoader().loadPropertyDelegateFieldAnnotations(it, proto)) : CollectionsKt.toList(MemberDeserializer.this.c.getComponents().getAnnotationAndConstantLoader().loadPropertyBackingFieldAnnotations(it, proto));
                } else {
                    list = null;
                }
                return list != null ? list : CollectionsKt.emptyList();
            }
        });
    }

    private final Annotations getReceiverParameterAnnotations(final MessageLite proto, final AnnotatedCallableKind kind) {
        return new DeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer.getReceiverParameterAnnotations.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends AnnotationDescriptor> invoke() {
                MemberDeserializer memberDeserializer = MemberDeserializer.this;
                ProtoContainer it = memberDeserializer.asProtoContainer(memberDeserializer.c.getContainingDeclaration());
                List<AnnotationDescriptor> listLoadExtensionReceiverParameterAnnotations = it != null ? MemberDeserializer.this.c.getComponents().getAnnotationAndConstantLoader().loadExtensionReceiverParameterAnnotations(it, proto, kind) : null;
                return listLoadExtensionReceiverParameterAnnotations != null ? listLoadExtensionReceiverParameterAnnotations : CollectionsKt.emptyList();
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:19:0x009d  */
    private final List<ValueParameterDescriptor> valueParameters(List<ProtoBuf.ValueParameter> list, final MessageLite callable, final AnnotatedCallableKind kind) {
        Annotations annotations;
        DeclarationDescriptor containingDeclaration = this.c.getContainingDeclaration();
        if (containingDeclaration == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
        }
        final CallableDescriptor callableDescriptor = (CallableDescriptor) containingDeclaration;
        DeclarationDescriptor containingDeclaration2 = callableDescriptor.getContainingDeclaration();
        Intrinsics.checkExpressionValueIsNotNull(containingDeclaration2, "callableDescriptor.containingDeclaration");
        final ProtoContainer containerOfCallable = asProtoContainer(containingDeclaration2);
        List<ProtoBuf.ValueParameter> $receiver$iv = list;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        Collection destination$iv$iv2 = destination$iv$iv;
        final int index$iv$iv = 0;
        int index$iv$iv2 = 0;
        boolean z = false;
        for (Object item$iv$iv : $receiver$iv) {
            int index$iv$iv3 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            final ProtoBuf.ValueParameter proto = (ProtoBuf.ValueParameter) item$iv$iv;
            int i = index$iv$iv2;
            int flags = proto.hasFlags() ? proto.getFlags() : 0;
            if (containerOfCallable != null) {
                Boolean bool = Flags.HAS_ANNOTATIONS.get(flags);
                Intrinsics.checkExpressionValueIsNotNull(bool, "Flags.HAS_ANNOTATIONS.get(flags)");
                if (bool.booleanValue()) {
                    annotations = new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer$valueParameters$$inlined$mapIndexed$lambda$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        @NotNull
                        public final List<? extends AnnotationDescriptor> invoke() {
                            return CollectionsKt.toList(this.c.getComponents().getAnnotationAndConstantLoader().loadValueParameterAnnotations(containerOfCallable, callable, kind, index$iv$iv, proto));
                        }
                    });
                } else {
                    annotations = Annotations.Companion.getEMPTY();
                }
            } else {
                annotations = Annotations.Companion.getEMPTY();
            }
            Name name = NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName());
            KotlinType kotlinTypeType = this.c.getTypeDeserializer().type(ProtoTypeTableUtilKt.type(proto, this.c.getTypeTable()));
            Boolean bool2 = Flags.DECLARES_DEFAULT_VALUE.get(flags);
            Intrinsics.checkExpressionValueIsNotNull(bool2, "Flags.DECLARES_DEFAULT_VALUE.get(flags)");
            boolean zBooleanValue = bool2.booleanValue();
            Boolean bool3 = Flags.IS_CROSSINLINE.get(flags);
            Intrinsics.checkExpressionValueIsNotNull(bool3, "Flags.IS_CROSSINLINE.get(flags)");
            boolean zBooleanValue2 = bool3.booleanValue();
            Boolean bool4 = Flags.IS_NOINLINE.get(flags);
            Intrinsics.checkExpressionValueIsNotNull(bool4, "Flags.IS_NOINLINE.get(flags)");
            boolean zBooleanValue3 = bool4.booleanValue();
            ProtoBuf.Type it = ProtoTypeTableUtilKt.varargElementType(proto, this.c.getTypeTable());
            KotlinType kotlinTypeType2 = it != null ? this.c.getTypeDeserializer().type(it) : null;
            SourceElement sourceElement = SourceElement.NO_SOURCE;
            Intrinsics.checkExpressionValueIsNotNull(sourceElement, "SourceElement.NO_SOURCE");
            Collection destination$iv$iv3 = destination$iv$iv2;
            destination$iv$iv3.add(new ValueParameterDescriptorImpl(callableDescriptor, null, index$iv$iv, annotations, name, kotlinTypeType, zBooleanValue, zBooleanValue2, zBooleanValue3, kotlinTypeType2, sourceElement));
            $receiver$iv = $receiver$iv;
            destination$iv$iv2 = destination$iv$iv3;
            z = z;
            index$iv$iv = index$iv$iv3;
            index$iv$iv2 = i;
        }
        return CollectionsKt.toList((List) destination$iv$iv2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ProtoContainer asProtoContainer(@NotNull DeclarationDescriptor $receiver) {
        if ($receiver instanceof PackageFragmentDescriptor) {
            return new ProtoContainer.Package(((PackageFragmentDescriptor) $receiver).getFqName(), this.c.getNameResolver(), this.c.getTypeTable(), this.c.getContainerSource());
        }
        if ($receiver instanceof DeserializedClassDescriptor) {
            return ((DeserializedClassDescriptor) $receiver).getThisAsProtoContainer$deserialization();
        }
        return null;
    }
}
