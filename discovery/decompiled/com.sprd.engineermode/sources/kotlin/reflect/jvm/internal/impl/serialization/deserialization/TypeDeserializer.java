package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.SuspendFunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeBasedStarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: TypeDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class TypeDeserializer {
    private final DeserializationContext c;
    private final Function1<Integer, ClassDescriptor> classDescriptors;
    private final String debugName;
    private boolean experimentalSuspendFunctionTypeEncountered;
    private final TypeDeserializer parent;
    private final Function1<Integer, ClassifierDescriptor> typeAliasDescriptors;
    private final Map<Integer, TypeParameterDescriptor> typeParameterDescriptors;

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$typeConstructor$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TypeDeserializer.kt */
    static final class C00531 extends Lambda implements Function1<Integer, ClassDescriptor> {
        final /* synthetic */ ProtoBuf.Type $proto;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00531(ProtoBuf.Type type) {
            super(1);
            this.$proto = type;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ ClassDescriptor invoke(Integer num) {
            return invoke(num.intValue());
        }

        @NotNull
        public final ClassDescriptor invoke(int classIdIndex) {
            ClassId classId = NameResolverUtilKt.getClassId(TypeDeserializer.this.c.getNameResolver(), classIdIndex);
            List<Integer> mutableList = SequencesKt.toMutableList(SequencesKt.map(SequencesKt.generateSequence(this.$proto, new Function1<ProtoBuf.Type, ProtoBuf.Type>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$typeConstructor$1$typeParametersCount$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                @Nullable
                public final ProtoBuf.Type invoke(@NotNull ProtoBuf.Type it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    return ProtoTypeTableUtilKt.outerType(it, TypeDeserializer.this.c.getTypeTable());
                }
            }), new Function1<ProtoBuf.Type, Integer>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$typeConstructor$1$typeParametersCount$2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Integer invoke(ProtoBuf.Type type) {
                    return Integer.valueOf(invoke2(type));
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final int invoke2(@NotNull ProtoBuf.Type it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    return it.getArgumentCount();
                }
            }));
            int classNestingLevel = SequencesKt.count(SequencesKt.generateSequence(classId, TypeDeserializer$typeConstructor$1$classNestingLevel$1.INSTANCE));
            while (mutableList.size() < classNestingLevel) {
                mutableList.add(0);
            }
            return TypeDeserializer.this.c.getComponents().getNotFoundClasses().getClass(classId, mutableList);
        }
    }

    public TypeDeserializer(@NotNull DeserializationContext c, @Nullable TypeDeserializer parent, @NotNull List<ProtoBuf.TypeParameter> typeParameterProtos, @NotNull String debugName, boolean experimentalSuspendFunctionTypeEncountered) {
        Map<Integer, TypeParameterDescriptor> mapEmptyMap;
        Intrinsics.checkParameterIsNotNull(c, "c");
        Intrinsics.checkParameterIsNotNull(typeParameterProtos, "typeParameterProtos");
        Intrinsics.checkParameterIsNotNull(debugName, "debugName");
        this.c = c;
        this.parent = parent;
        this.debugName = debugName;
        this.experimentalSuspendFunctionTypeEncountered = experimentalSuspendFunctionTypeEncountered;
        this.classDescriptors = this.c.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<Integer, ClassDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$classDescriptors$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ ClassDescriptor invoke(Integer num) {
                return invoke(num.intValue());
            }

            @Nullable
            public final ClassDescriptor invoke(int fqNameIndex) {
                return this.this$0.computeClassDescriptor(fqNameIndex);
            }
        });
        this.typeAliasDescriptors = this.c.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<Integer, ClassifierDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$typeAliasDescriptors$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ ClassifierDescriptor invoke(Integer num) {
                return invoke(num.intValue());
            }

            @Nullable
            public final ClassifierDescriptor invoke(int fqNameIndex) {
                return this.this$0.computeTypeAliasDescriptor(fqNameIndex);
            }
        });
        if (typeParameterProtos.isEmpty()) {
            mapEmptyMap = MapsKt.emptyMap();
        } else {
            LinkedHashMap result = new LinkedHashMap();
            int index = 0;
            for (ProtoBuf.TypeParameter proto : typeParameterProtos) {
                result.put(Integer.valueOf(proto.getId()), new DeserializedTypeParameterDescriptor(this.c, proto, index));
                index++;
            }
            mapEmptyMap = result;
        }
        this.typeParameterDescriptors = mapEmptyMap;
    }

    public /* synthetic */ TypeDeserializer(DeserializationContext deserializationContext, TypeDeserializer typeDeserializer, List list, String str, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(deserializationContext, typeDeserializer, list, str, (i & 16) != 0 ? false : z);
    }

    public final boolean getExperimentalSuspendFunctionTypeEncountered() {
        return this.experimentalSuspendFunctionTypeEncountered;
    }

    @NotNull
    public final List<TypeParameterDescriptor> getOwnTypeParameters() {
        return CollectionsKt.toList(this.typeParameterDescriptors.values());
    }

    @NotNull
    public final KotlinType type(@NotNull ProtoBuf.Type proto) {
        Intrinsics.checkParameterIsNotNull(proto, "proto");
        if (proto.hasFlexibleTypeCapabilitiesId()) {
            String id = this.c.getNameResolver().getString(proto.getFlexibleTypeCapabilitiesId());
            SimpleType lowerBound = simpleType(proto);
            ProtoBuf.Type typeFlexibleUpperBound = ProtoTypeTableUtilKt.flexibleUpperBound(proto, this.c.getTypeTable());
            if (typeFlexibleUpperBound == null) {
                Intrinsics.throwNpe();
            }
            SimpleType upperBound = simpleType(typeFlexibleUpperBound);
            return this.c.getComponents().getFlexibleTypeDeserializer().create(proto, id, lowerBound, upperBound);
        }
        return simpleType(proto);
    }

    @NotNull
    public final SimpleType simpleType(@NotNull final ProtoBuf.Type proto) {
        SimpleType localClassifierType;
        SimpleType simpleType;
        Intrinsics.checkParameterIsNotNull(proto, "proto");
        if (proto.hasClassName()) {
            localClassifierType = computeLocalClassifierReplacementType(proto.getClassName());
        } else {
            localClassifierType = proto.hasTypeAliasName() ? computeLocalClassifierReplacementType(proto.getTypeAliasName()) : null;
        }
        if (localClassifierType != null) {
            return localClassifierType;
        }
        TypeConstructor constructor = typeConstructor(proto);
        if (ErrorUtils.isError(constructor.mo381getDeclarationDescriptor())) {
            SimpleType simpleTypeCreateErrorTypeWithCustomConstructor = ErrorUtils.createErrorTypeWithCustomConstructor(constructor.toString(), constructor);
            Intrinsics.checkExpressionValueIsNotNull(simpleTypeCreateErrorTypeWithCustomConstructor, "ErrorUtils.createErrorTy….toString(), constructor)");
            return simpleTypeCreateErrorTypeWithCustomConstructor;
        }
        DeserializedAnnotations annotations = new DeserializedAnnotations(this.c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$simpleType$annotations$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends AnnotationDescriptor> invoke() {
                return this.this$0.c.getComponents().getAnnotationAndConstantLoader().loadTypeAnnotations(proto, this.this$0.c.getNameResolver());
            }
        });
        Function1<ProtoBuf.Type, List<? extends ProtoBuf.Type.Argument>> function1 = new Function1<ProtoBuf.Type, List<? extends ProtoBuf.Type.Argument>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer.simpleType.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final List<ProtoBuf.Type.Argument> invoke(@NotNull ProtoBuf.Type receiver$0) {
                Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
                List<ProtoBuf.Type.Argument> argumentList = receiver$0.getArgumentList();
                Intrinsics.checkExpressionValueIsNotNull(argumentList, "argumentList");
                List<ProtoBuf.Type.Argument> list = argumentList;
                ProtoBuf.Type typeOuterType = ProtoTypeTableUtilKt.outerType(receiver$0, TypeDeserializer.this.c.getTypeTable());
                List<ProtoBuf.Type.Argument> listInvoke = typeOuterType != null ? invoke(typeOuterType) : null;
                if (listInvoke == null) {
                    listInvoke = CollectionsKt.emptyList();
                }
                return CollectionsKt.plus((Collection) list, (Iterable) listInvoke);
            }
        };
        Iterable $receiver$iv = function1.invoke(proto);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        int index$iv$iv = 0;
        for (Object item$iv$iv : $receiver$iv) {
            int index$iv$iv2 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            SimpleType localClassifierType2 = localClassifierType;
            ProtoBuf.Type.Argument argumentProto = (ProtoBuf.Type.Argument) item$iv$iv;
            Function1<ProtoBuf.Type, List<? extends ProtoBuf.Type.Argument>> function2 = function1;
            List<TypeParameterDescriptor> parameters = constructor.getParameters();
            Intrinsics.checkExpressionValueIsNotNull(parameters, "constructor.parameters");
            destination$iv$iv.add(typeArgument((TypeParameterDescriptor) CollectionsKt.getOrNull(parameters, index$iv$iv), argumentProto));
            index$iv$iv = index$iv$iv2;
            localClassifierType = localClassifierType2;
            function1 = function2;
            $receiver$iv = $receiver$iv;
        }
        List<? extends TypeProjection> list = CollectionsKt.toList((List) destination$iv$iv);
        Boolean bool = Flags.SUSPEND_TYPE.get(proto.getFlags());
        Intrinsics.checkExpressionValueIsNotNull(bool, "Flags.SUSPEND_TYPE.get(proto.flags)");
        if (bool.booleanValue()) {
            simpleType = createSuspendFunctionType(annotations, constructor, list, proto.getNullable());
        } else {
            simpleType = KotlinTypeFactory.simpleType(annotations, constructor, list, proto.getNullable());
        }
        ProtoBuf.Type abbreviatedTypeProto = ProtoTypeTableUtilKt.abbreviatedType(proto, this.c.getTypeTable());
        if (abbreviatedTypeProto == null) {
            return simpleType;
        }
        return SpecialTypesKt.withAbbreviation(simpleType, simpleType(abbreviatedTypeProto));
    }

    private final TypeConstructor typeConstructor(ProtoBuf.Type proto) {
        Object next;
        TypeConstructor typeConstructor;
        TypeParameterDescriptor it;
        C00531 notFoundClass$ = new C00531(proto);
        if (proto.hasClassName()) {
            ClassDescriptor classDescriptorInvoke = this.classDescriptors.invoke(Integer.valueOf(proto.getClassName()));
            if (classDescriptorInvoke == null) {
                classDescriptorInvoke = notFoundClass$.invoke(proto.getClassName());
            }
            TypeConstructor typeConstructor2 = classDescriptorInvoke.getTypeConstructor();
            Intrinsics.checkExpressionValueIsNotNull(typeConstructor2, "(classDescriptors(proto.…assName)).typeConstructor");
            return typeConstructor2;
        }
        if (proto.hasTypeParameter()) {
            TypeConstructor typeConstructorTypeParameterTypeConstructor = typeParameterTypeConstructor(proto.getTypeParameter());
            if (typeConstructorTypeParameterTypeConstructor != null) {
                return typeConstructorTypeParameterTypeConstructor;
            }
            TypeConstructor typeConstructorCreateErrorTypeConstructor = ErrorUtils.createErrorTypeConstructor("Unknown type parameter " + proto.getTypeParameter());
            Intrinsics.checkExpressionValueIsNotNull(typeConstructorCreateErrorTypeConstructor, "ErrorUtils.createErrorTy… ${proto.typeParameter}\")");
            return typeConstructorCreateErrorTypeConstructor;
        }
        if (proto.hasTypeParameterName()) {
            DeclarationDescriptor container = this.c.getContainingDeclaration();
            String name = this.c.getNameResolver().getString(proto.getTypeParameterName());
            Iterator<T> it2 = getOwnTypeParameters().iterator();
            do {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
                it = (TypeParameterDescriptor) next;
            } while (!Intrinsics.areEqual(it.getName().asString(), name));
            TypeParameterDescriptor parameter = (TypeParameterDescriptor) next;
            if (parameter != null && (typeConstructor = parameter.getTypeConstructor()) != null) {
                return typeConstructor;
            }
            TypeConstructor typeConstructorCreateErrorTypeConstructor2 = ErrorUtils.createErrorTypeConstructor("Deserialized type parameter " + name + " in " + container);
            Intrinsics.checkExpressionValueIsNotNull(typeConstructorCreateErrorTypeConstructor2, "ErrorUtils.createErrorTy…ter $name in $container\")");
            return typeConstructorCreateErrorTypeConstructor2;
        }
        if (proto.hasTypeAliasName()) {
            ClassDescriptor classDescriptorInvoke2 = this.typeAliasDescriptors.invoke(Integer.valueOf(proto.getTypeAliasName()));
            if (classDescriptorInvoke2 == null) {
                classDescriptorInvoke2 = notFoundClass$.invoke(proto.getTypeAliasName());
            }
            TypeConstructor typeConstructor3 = classDescriptorInvoke2.getTypeConstructor();
            Intrinsics.checkExpressionValueIsNotNull(typeConstructor3, "(typeAliasDescriptors(pr…iasName)).typeConstructor");
            return typeConstructor3;
        }
        TypeConstructor typeConstructorCreateErrorTypeConstructor3 = ErrorUtils.createErrorTypeConstructor("Unknown type");
        Intrinsics.checkExpressionValueIsNotNull(typeConstructorCreateErrorTypeConstructor3, "ErrorUtils.createErrorTy…nstructor(\"Unknown type\")");
        return typeConstructorCreateErrorTypeConstructor3;
    }

    private final SimpleType createSuspendFunctionType(Annotations annotations, TypeConstructor functionTypeConstructor, List<? extends TypeProjection> list, boolean nullable) {
        int arity;
        int size = functionTypeConstructor.getParameters().size() - list.size();
        SimpleType simpleTypeCreateSuspendFunctionTypeForBasicCase = null;
        if (size == 0) {
            simpleTypeCreateSuspendFunctionTypeForBasicCase = createSuspendFunctionTypeForBasicCase(annotations, functionTypeConstructor, list, nullable);
        } else if (size == 1 && (arity = list.size() - 1) >= 0) {
            ClassDescriptor suspendFunction = functionTypeConstructor.getBuiltIns().getSuspendFunction(arity);
            Intrinsics.checkExpressionValueIsNotNull(suspendFunction, "functionTypeConstructor.…getSuspendFunction(arity)");
            TypeConstructor typeConstructor = suspendFunction.getTypeConstructor();
            Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "functionTypeConstructor.…on(arity).typeConstructor");
            simpleTypeCreateSuspendFunctionTypeForBasicCase = KotlinTypeFactory.simpleType(annotations, typeConstructor, list, nullable);
        }
        SimpleType result = simpleTypeCreateSuspendFunctionTypeForBasicCase;
        if (result != null) {
            return result;
        }
        SimpleType simpleTypeCreateErrorTypeWithArguments = ErrorUtils.createErrorTypeWithArguments("Bad suspend function in metadata with constructor: " + functionTypeConstructor, list);
        Intrinsics.checkExpressionValueIsNotNull(simpleTypeCreateErrorTypeWithArguments, "ErrorUtils.createErrorTy…      arguments\n        )");
        return simpleTypeCreateErrorTypeWithArguments;
    }

    private final SimpleType createSuspendFunctionTypeForBasicCase(Annotations annotations, TypeConstructor functionTypeConstructor, List<? extends TypeProjection> list, boolean nullable) {
        SimpleType functionType = KotlinTypeFactory.simpleType(annotations, functionTypeConstructor, list, nullable);
        if (FunctionTypesKt.isFunctionType(functionType)) {
            return transformRuntimeFunctionTypeToSuspendFunction(functionType);
        }
        return null;
    }

    private final SimpleType transformRuntimeFunctionTypeToSuspendFunction(KotlinType funType) {
        KotlinType continuationArgumentType;
        boolean isReleaseCoroutines = this.c.getComponents().getConfiguration().getReleaseCoroutines();
        TypeProjection typeProjection = (TypeProjection) CollectionsKt.lastOrNull((List) FunctionTypesKt.getValueParameterTypesFromFunctionType(funType));
        if (typeProjection == null || (continuationArgumentType = typeProjection.getType()) == null) {
            return null;
        }
        Intrinsics.checkExpressionValueIsNotNull(continuationArgumentType, "funType.getValueParamete…ll()?.type ?: return null");
        ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = continuationArgumentType.getConstructor().mo381getDeclarationDescriptor();
        FqName continuationArgumentFqName = classifierDescriptorMo381getDeclarationDescriptor != null ? DescriptorUtilsKt.getFqNameSafe(classifierDescriptorMo381getDeclarationDescriptor) : null;
        boolean z = true;
        if (continuationArgumentType.getArguments().size() != 1 || (!SuspendFunctionTypesKt.isContinuation(continuationArgumentFqName, true) && !SuspendFunctionTypesKt.isContinuation(continuationArgumentFqName, false))) {
            return (SimpleType) funType;
        }
        KotlinType suspendReturnType = ((TypeProjection) CollectionsKt.single((List) continuationArgumentType.getArguments())).getType();
        Intrinsics.checkExpressionValueIsNotNull(suspendReturnType, "continuationArgumentType.arguments.single().type");
        Object $receiver$iv = this.c.getContainingDeclaration();
        CallableDescriptor callableDescriptor = (CallableDescriptor) (!($receiver$iv instanceof CallableDescriptor) ? null : $receiver$iv);
        if (Intrinsics.areEqual(callableDescriptor != null ? DescriptorUtilsKt.fqNameOrNull(callableDescriptor) : null, SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME)) {
            return createSimpleSuspendFunctionType(funType, suspendReturnType);
        }
        if (!this.experimentalSuspendFunctionTypeEncountered && (!isReleaseCoroutines || !SuspendFunctionTypesKt.isContinuation(continuationArgumentFqName, !isReleaseCoroutines))) {
            z = false;
        }
        this.experimentalSuspendFunctionTypeEncountered = z;
        return createSimpleSuspendFunctionType(funType, suspendReturnType);
    }

    private final SimpleType createSimpleSuspendFunctionType(KotlinType funType, KotlinType suspendReturnType) {
        KotlinBuiltIns builtIns = TypeUtilsKt.getBuiltIns(funType);
        Annotations annotations = funType.getAnnotations();
        KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(funType);
        Iterable $receiver$iv = CollectionsKt.dropLast(FunctionTypesKt.getValueParameterTypesFromFunctionType(funType), 1);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            TypeProjection p1 = (TypeProjection) item$iv$iv;
            destination$iv$iv.add(p1.getType());
        }
        return FunctionTypesKt.createFunctionType(builtIns, annotations, receiverTypeFromFunctionType, (List) destination$iv$iv, null, suspendReturnType, true).makeNullableAsSpecified(funType.isMarkedNullable());
    }

    private final TypeConstructor typeParameterTypeConstructor(int typeParameterId) {
        TypeConstructor typeConstructor;
        TypeParameterDescriptor typeParameterDescriptor = this.typeParameterDescriptors.get(Integer.valueOf(typeParameterId));
        if (typeParameterDescriptor != null && (typeConstructor = typeParameterDescriptor.getTypeConstructor()) != null) {
            return typeConstructor;
        }
        TypeDeserializer typeDeserializer = this.parent;
        if (typeDeserializer != null) {
            return typeDeserializer.typeParameterTypeConstructor(typeParameterId);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor computeClassDescriptor(int fqNameIndex) {
        ClassId id = NameResolverUtilKt.getClassId(this.c.getNameResolver(), fqNameIndex);
        if (id.isLocal()) {
            return this.c.getComponents().deserializeClass(id);
        }
        return FindClassInModuleKt.findClassAcrossModuleDependencies(this.c.getComponents().getModuleDescriptor(), id);
    }

    private final SimpleType computeLocalClassifierReplacementType(int className) {
        if (NameResolverUtilKt.getClassId(this.c.getNameResolver(), className).isLocal()) {
            return this.c.getComponents().getLocalClassifierTypeSettings().getReplacementTypeForLocalClassifiers();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassifierDescriptor computeTypeAliasDescriptor(int fqNameIndex) {
        ClassId id = NameResolverUtilKt.getClassId(this.c.getNameResolver(), fqNameIndex);
        if (id.isLocal()) {
            return null;
        }
        return FindClassInModuleKt.findTypeAliasAcrossModuleDependencies(this.c.getComponents().getModuleDescriptor(), id);
    }

    private final TypeProjection typeArgument(TypeParameterDescriptor parameter, ProtoBuf.Type.Argument typeArgumentProto) {
        if (typeArgumentProto.getProjection() == ProtoBuf.Type.Argument.Projection.STAR) {
            if (parameter == null) {
                SimpleType nullableAnyType = this.c.getComponents().getModuleDescriptor().getBuiltIns().getNullableAnyType();
                Intrinsics.checkExpressionValueIsNotNull(nullableAnyType, "c.components.moduleDescr….builtIns.nullableAnyType");
                return new TypeBasedStarProjectionImpl(nullableAnyType);
            }
            return new StarProjectionImpl(parameter);
        }
        ProtoEnumFlags protoEnumFlags = ProtoEnumFlags.INSTANCE;
        ProtoBuf.Type.Argument.Projection projection = typeArgumentProto.getProjection();
        Intrinsics.checkExpressionValueIsNotNull(projection, "typeArgumentProto.projection");
        Variance projection2 = protoEnumFlags.variance(projection);
        ProtoBuf.Type type = ProtoTypeTableUtilKt.type(typeArgumentProto, this.c.getTypeTable());
        if (type == null) {
            return new TypeProjectionImpl(ErrorUtils.createErrorType("No type recorded"));
        }
        return new TypeProjectionImpl(projection2, type(type));
    }

    @NotNull
    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(this.debugName);
        if (this.parent == null) {
            str = "";
        } else {
            str = ". Child of " + this.parent.debugName;
        }
        sb.append(str);
        return sb.toString();
    }
}
