package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.BooleanValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ByteValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.CharValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValueFactory;
import kotlin.reflect.jvm.internal.impl.resolve.constants.DoubleValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ErrorValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.FloatValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.LongValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ShortValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UByteValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UIntValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ULongValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UShortValue;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: AnnotationDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class AnnotationDeserializer {
    private final ModuleDescriptor module;
    private final NotFoundClasses notFoundClasses;

    public AnnotationDeserializer(@NotNull ModuleDescriptor module, @NotNull NotFoundClasses notFoundClasses) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        Intrinsics.checkParameterIsNotNull(notFoundClasses, "notFoundClasses");
        this.module = module;
        this.notFoundClasses = notFoundClasses;
    }

    private final KotlinBuiltIns getBuiltIns() {
        return this.module.getBuiltIns();
    }

    @NotNull
    public final AnnotationDescriptor deserializeAnnotation(@NotNull ProtoBuf.Annotation proto, @NotNull NameResolver nameResolver) {
        Map arguments;
        Intrinsics.checkParameterIsNotNull(proto, "proto");
        Intrinsics.checkParameterIsNotNull(nameResolver, "nameResolver");
        ClassDescriptor annotationClass = resolveClass(NameResolverUtilKt.getClassId(nameResolver, proto.getId()));
        Map arguments2 = MapsKt.emptyMap();
        if (proto.getArgumentCount() != 0 && !ErrorUtils.isError(annotationClass) && DescriptorUtils.isAnnotationClass(annotationClass)) {
            Collection<ClassConstructorDescriptor> constructors = annotationClass.getConstructors();
            Intrinsics.checkExpressionValueIsNotNull(constructors, "annotationClass.constructors");
            ClassConstructorDescriptor constructor = (ClassConstructorDescriptor) CollectionsKt.singleOrNull(constructors);
            if (constructor != null) {
                Iterable valueParameters = constructor.getValueParameters();
                Intrinsics.checkExpressionValueIsNotNull(valueParameters, "constructor.valueParameters");
                Iterable $receiver$iv = valueParameters;
                int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)), 16);
                Map destination$iv$iv = new LinkedHashMap(capacity$iv);
                for (Object element$iv$iv : $receiver$iv) {
                    ValueParameterDescriptor it = (ValueParameterDescriptor) element$iv$iv;
                    Intrinsics.checkExpressionValueIsNotNull(it, "it");
                    destination$iv$iv.put(it.getName(), element$iv$iv);
                }
                Iterable $receiver$iv2 = proto.getArgumentList();
                Intrinsics.checkExpressionValueIsNotNull($receiver$iv2, "proto.argumentList");
                Collection destination$iv$iv2 = new ArrayList();
                for (Object element$iv$iv$iv : $receiver$iv2) {
                    Map arguments3 = arguments2;
                    ProtoBuf.Annotation.Argument it2 = (ProtoBuf.Annotation.Argument) element$iv$iv$iv;
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    Pair<Name, ConstantValue<?>> pairResolveArgument = resolveArgument(it2, destination$iv$iv, nameResolver);
                    if (pairResolveArgument != null) {
                        destination$iv$iv2.add(pairResolveArgument);
                    }
                    arguments2 = arguments3;
                }
                arguments = MapsKt.toMap((List) destination$iv$iv2);
            }
            return new AnnotationDescriptorImpl(annotationClass.getDefaultType(), arguments, SourceElement.NO_SOURCE);
        }
        arguments = arguments2;
        return new AnnotationDescriptorImpl(annotationClass.getDefaultType(), arguments, SourceElement.NO_SOURCE);
    }

    private final Pair<Name, ConstantValue<?>> resolveArgument(ProtoBuf.Annotation.Argument proto, Map<Name, ? extends ValueParameterDescriptor> map, NameResolver nameResolver) {
        ValueParameterDescriptor parameter = map.get(NameResolverUtilKt.getName(nameResolver, proto.getNameId()));
        if (parameter == null) {
            return null;
        }
        Name name = NameResolverUtilKt.getName(nameResolver, proto.getNameId());
        KotlinType type = parameter.getType();
        Intrinsics.checkExpressionValueIsNotNull(type, "parameter.type");
        ProtoBuf.Annotation.Argument.Value value = proto.getValue();
        Intrinsics.checkExpressionValueIsNotNull(value, "proto.value");
        return new Pair<>(name, resolveValue(type, value, nameResolver));
    }

    @NotNull
    public final ConstantValue<?> resolveValue(@NotNull KotlinType expectedType, @NotNull ProtoBuf.Annotation.Argument.Value value, @NotNull NameResolver nameResolver) {
        ConstantValue<?> uByteValue;
        SimpleType actualArrayType;
        Intrinsics.checkParameterIsNotNull(expectedType, "expectedType");
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull(nameResolver, "nameResolver");
        Boolean bool = Flags.IS_UNSIGNED.get(value.getFlags());
        Intrinsics.checkExpressionValueIsNotNull(bool, "Flags.IS_UNSIGNED.get(value.flags)");
        boolean isUnsigned = bool.booleanValue();
        ProtoBuf.Annotation.Argument.Value.Type type = value.getType();
        if (type != null) {
            switch (type) {
                case BYTE:
                    byte $receiver$iv = (byte) value.getIntValue();
                    uByteValue = isUnsigned ? new UByteValue($receiver$iv) : new ByteValue($receiver$iv);
                    break;
                case CHAR:
                    uByteValue = new CharValue((char) value.getIntValue());
                    break;
                case SHORT:
                    short $receiver$iv2 = (short) value.getIntValue();
                    uByteValue = isUnsigned ? new UShortValue($receiver$iv2) : new ShortValue($receiver$iv2);
                    break;
                case INT:
                    int $receiver$iv3 = (int) value.getIntValue();
                    uByteValue = isUnsigned ? new UIntValue($receiver$iv3) : new IntValue($receiver$iv3);
                    break;
                case LONG:
                    long $receiver$iv4 = value.getIntValue();
                    uByteValue = isUnsigned ? new ULongValue($receiver$iv4) : new LongValue($receiver$iv4);
                    break;
                case FLOAT:
                    uByteValue = new FloatValue(value.getFloatValue());
                    break;
                case DOUBLE:
                    uByteValue = new DoubleValue(value.getDoubleValue());
                    break;
                case BOOLEAN:
                    uByteValue = new BooleanValue(value.getIntValue() != 0);
                    break;
                case STRING:
                    uByteValue = new StringValue(nameResolver.getString(value.getStringValue()));
                    break;
                case CLASS:
                    uByteValue = new KClassValue(NameResolverUtilKt.getClassId(nameResolver, value.getClassId()), value.getArrayDimensionCount());
                    break;
                case ENUM:
                    uByteValue = new EnumValue(NameResolverUtilKt.getClassId(nameResolver, value.getClassId()), NameResolverUtilKt.getName(nameResolver, value.getEnumValueId()));
                    break;
                case ANNOTATION:
                    ProtoBuf.Annotation annotation = value.getAnnotation();
                    Intrinsics.checkExpressionValueIsNotNull(annotation, "value.annotation");
                    uByteValue = new AnnotationValue(deserializeAnnotation(annotation, nameResolver));
                    break;
                case ARRAY:
                    boolean expectedIsArray = KotlinBuiltIns.isArray(expectedType) || KotlinBuiltIns.isPrimitiveArray(expectedType);
                    List<ProtoBuf.Annotation.Argument.Value> arrayElements = value.getArrayElementList();
                    Intrinsics.checkExpressionValueIsNotNull(arrayElements, "arrayElements");
                    if (true ^ arrayElements.isEmpty()) {
                        Object objFirst = CollectionsKt.first((List<? extends Object>) arrayElements);
                        Intrinsics.checkExpressionValueIsNotNull(objFirst, "arrayElements.first()");
                        SimpleType actualElementType = resolveArrayElementType((ProtoBuf.Annotation.Argument.Value) objFirst, nameResolver);
                        SimpleType primitiveArrayKotlinTypeByPrimitiveKotlinType = getBuiltIns().getPrimitiveArrayKotlinTypeByPrimitiveKotlinType(actualElementType);
                        if (primitiveArrayKotlinTypeByPrimitiveKotlinType != null) {
                            actualArrayType = primitiveArrayKotlinTypeByPrimitiveKotlinType;
                        } else {
                            SimpleType actualElementType2 = getBuiltIns().getArrayType(Variance.INVARIANT, actualElementType);
                            Intrinsics.checkExpressionValueIsNotNull(actualElementType2, "builtIns.getArrayType(Va…RIANT, actualElementType)");
                            actualArrayType = actualElementType2;
                        }
                    } else if (expectedIsArray) {
                        actualArrayType = expectedType;
                    } else {
                        SimpleType arrayType = getBuiltIns().getArrayType(Variance.INVARIANT, getBuiltIns().getAnyType());
                        Intrinsics.checkExpressionValueIsNotNull(arrayType, "builtIns.getArrayType(Va…ARIANT, builtIns.anyType)");
                        actualArrayType = arrayType;
                    }
                    KotlinType expectedElementType = getBuiltIns().getArrayElementType(expectedIsArray ? expectedType : actualArrayType);
                    Intrinsics.checkExpressionValueIsNotNull(expectedElementType, "builtIns.getArrayElement…ype else actualArrayType)");
                    ConstantValueFactory constantValueFactory = ConstantValueFactory.INSTANCE;
                    List<ProtoBuf.Annotation.Argument.Value> $receiver$iv5 = arrayElements;
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv5, 10));
                    for (Object item$iv$iv : $receiver$iv5) {
                        ProtoBuf.Annotation.Argument.Value it = (ProtoBuf.Annotation.Argument.Value) item$iv$iv;
                        Intrinsics.checkExpressionValueIsNotNull(it, "it");
                        destination$iv$iv.add(resolveValue(expectedElementType, it, nameResolver));
                    }
                    uByteValue = constantValueFactory.createArrayValue((List<? extends ConstantValue<?>>) destination$iv$iv, actualArrayType);
                    break;
            }
            if (TypeUtilsKt.isSubtypeOf(uByteValue.getType(this.module), expectedType)) {
                return uByteValue;
            }
            return ErrorValue.Companion.create("Unexpected argument value: type " + uByteValue.getType(this.module) + " is not a subtype of " + expectedType + " (value.type = " + value.getType() + ')');
        }
        throw new IllegalStateException(("Unsupported annotation argument type: " + value.getType() + " (expected " + expectedType + ')').toString());
    }

    private final SimpleType resolveArrayElementType(ProtoBuf.Annotation.Argument.Value value, NameResolver nameResolver) {
        KotlinBuiltIns $receiver = getBuiltIns();
        ProtoBuf.Annotation.Argument.Value.Type type = value.getType();
        if (type != null) {
            switch (type) {
                case BYTE:
                    SimpleType byteType = $receiver.getByteType();
                    Intrinsics.checkExpressionValueIsNotNull(byteType, "byteType");
                    return byteType;
                case CHAR:
                    SimpleType charType = $receiver.getCharType();
                    Intrinsics.checkExpressionValueIsNotNull(charType, "charType");
                    return charType;
                case SHORT:
                    SimpleType shortType = $receiver.getShortType();
                    Intrinsics.checkExpressionValueIsNotNull(shortType, "shortType");
                    return shortType;
                case INT:
                    SimpleType intType = $receiver.getIntType();
                    Intrinsics.checkExpressionValueIsNotNull(intType, "intType");
                    return intType;
                case LONG:
                    SimpleType longType = $receiver.getLongType();
                    Intrinsics.checkExpressionValueIsNotNull(longType, "longType");
                    return longType;
                case FLOAT:
                    SimpleType floatType = $receiver.getFloatType();
                    Intrinsics.checkExpressionValueIsNotNull(floatType, "floatType");
                    return floatType;
                case DOUBLE:
                    SimpleType doubleType = $receiver.getDoubleType();
                    Intrinsics.checkExpressionValueIsNotNull(doubleType, "doubleType");
                    return doubleType;
                case BOOLEAN:
                    SimpleType booleanType = $receiver.getBooleanType();
                    Intrinsics.checkExpressionValueIsNotNull(booleanType, "booleanType");
                    return booleanType;
                case STRING:
                    SimpleType stringType = $receiver.getStringType();
                    Intrinsics.checkExpressionValueIsNotNull(stringType, "stringType");
                    return stringType;
                case CLASS:
                    throw new IllegalStateException("Arrays of class literals are not supported yet".toString());
                case ENUM:
                    SimpleType defaultType = resolveClass(NameResolverUtilKt.getClassId(nameResolver, value.getClassId())).getDefaultType();
                    Intrinsics.checkExpressionValueIsNotNull(defaultType, "resolveClass(nameResolve…lue.classId)).defaultType");
                    return defaultType;
                case ANNOTATION:
                    ProtoBuf.Annotation annotation = value.getAnnotation();
                    Intrinsics.checkExpressionValueIsNotNull(annotation, "value.annotation");
                    SimpleType defaultType2 = resolveClass(NameResolverUtilKt.getClassId(nameResolver, annotation.getId())).getDefaultType();
                    Intrinsics.checkExpressionValueIsNotNull(defaultType2, "resolveClass(nameResolve…notation.id)).defaultType");
                    return defaultType2;
                case ARRAY:
                    throw new IllegalStateException("Array of arrays is impossible".toString());
            }
        }
        throw new IllegalStateException(("Unknown type: " + value.getType()).toString());
    }

    private final ClassDescriptor resolveClass(ClassId classId) {
        return FindClassInModuleKt.findNonGenericClassAcrossDependencies(this.module, classId, this.notFoundClasses);
    }
}
