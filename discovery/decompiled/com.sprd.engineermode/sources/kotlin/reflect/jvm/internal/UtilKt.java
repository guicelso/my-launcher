package kotlin.reflect.jvm.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference;
import kotlin.reflect.KCallable;
import kotlin.reflect.KVisibility;
import kotlin.reflect.jvm.internal.calls.AnnotationConstructorCallerKt;
import kotlin.reflect.jvm.internal.components.ReflectAnnotationSource;
import kotlin.reflect.jvm.internal.components.ReflectJavaClassFinderKt;
import kotlin.reflect.jvm.internal.components.ReflectKotlinClass;
import kotlin.reflect.jvm.internal.components.RuntimeModuleData;
import kotlin.reflect.jvm.internal.components.RuntimeSourceElementFactory;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinarySourceElement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmNameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ClassLiteralValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ErrorValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.NullValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer;
import kotlin.reflect.jvm.internal.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.structure.ReflectJavaAnnotation;
import kotlin.reflect.jvm.internal.structure.ReflectJavaClass;
import kotlin.reflect.jvm.internal.structure.ReflectJavaElement;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: util.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000°\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001an\u0010\u000e\u001a\u0004\u0018\u0001H\u000f\"\b\b\u0000\u0010\u0010*\u00020\u0011\"\b\b\u0001\u0010\u000f*\u00020\u00062\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0014\u001a\u0002H\u00102\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u001d\u0010\u001b\u001a\u0019\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u000f0\u001c¢\u0006\u0002\b\u001eH\u0000¢\u0006\u0002\u0010\u001f\u001a.\u0010 \u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00132\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\n2\u0006\u0010$\u001a\u00020\n2\u0006\u0010%\u001a\u00020&H\u0002\u001a(\u0010 \u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00132\u0006\u0010!\u001a\u00020\"2\u0006\u0010'\u001a\u00020(2\b\b\u0002\u0010%\u001a\u00020&H\u0002\u001a\"\u0010)\u001a\u0002H*\"\u0004\b\u0000\u0010*2\f\u0010+\u001a\b\u0012\u0004\u0012\u0002H*0,H\u0080\b¢\u0006\u0002\u0010-\u001a\u0014\u0010.\u001a\b\u0012\u0002\b\u0003\u0018\u00010/*\u0004\u0018\u000100H\u0000\u001a\u0010\u00101\u001a\u0004\u0018\u000102*\u0004\u0018\u000100H\u0000\u001a\u0014\u00103\u001a\b\u0012\u0002\b\u0003\u0018\u000104*\u0004\u0018\u000100H\u0000\u001a\u0012\u00105\u001a\b\u0012\u0004\u0012\u00020706*\u000208H\u0000\u001a\u000e\u00109\u001a\u0004\u0018\u000107*\u00020:H\u0002\u001a\u0012\u0010;\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0013*\u00020<H\u0000\u001a\u000e\u0010=\u001a\u0004\u0018\u00010>*\u00020?H\u0000\u001a\u001a\u0010@\u001a\u0004\u0018\u000100*\u0006\u0012\u0002\b\u00030A2\u0006\u0010!\u001a\u00020\"H\u0002\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00068@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u001a\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006B"}, d2 = {"JVM_STATIC", "Lkotlin/reflect/jvm/internal/impl/name/FqName;", "getJVM_STATIC", "()Lorg/jetbrains/kotlin/name/FqName;", "instanceReceiverParameter", "Lkotlin/reflect/jvm/internal/impl/descriptors/ReceiverParameterDescriptor;", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableDescriptor;", "getInstanceReceiverParameter", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "packageModuleName", "", "Lkotlin/reflect/jvm/internal/components/ReflectKotlinClass;", "getPackageModuleName", "(Lkotlin/reflect/jvm/internal/components/ReflectKotlinClass;)Ljava/lang/String;", "deserializeToDescriptor", "D", "M", "Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;", "moduleAnchor", "Ljava/lang/Class;", "proto", "nameResolver", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/NameResolver;", "typeTable", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/TypeTable;", "metadataVersion", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/BinaryVersion;", "createDescriptor", "Lkotlin/Function2;", "Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/MemberDeserializer;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Class;Lorg/jetbrains/kotlin/protobuf/MessageLite;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "loadClass", "classLoader", "Ljava/lang/ClassLoader;", "packageName", "className", "arrayDimensions", "", "kotlinClassId", "Lkotlin/reflect/jvm/internal/impl/name/ClassId;", "reflectionCall", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "asKCallableImpl", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "", "asKFunctionImpl", "Lkotlin/reflect/jvm/internal/KFunctionImpl;", "asKPropertyImpl", "Lkotlin/reflect/jvm/internal/KPropertyImpl;", "computeAnnotations", "", "", "Lkotlin/reflect/jvm/internal/impl/descriptors/annotations/Annotated;", "toAnnotationInstance", "Lkotlin/reflect/jvm/internal/impl/descriptors/annotations/AnnotationDescriptor;", "toJavaClass", "Lkotlin/reflect/jvm/internal/impl/descriptors/ClassDescriptor;", "toKVisibility", "Lkotlin/reflect/KVisibility;", "Lkotlin/reflect/jvm/internal/impl/descriptors/Visibility;", "toRuntimeValue", "Lkotlin/reflect/jvm/internal/impl/resolve/constants/ConstantValue;", "kotlin-reflect-api"}, k = 2, mv = {1, 1, 13})
public final class UtilKt {

    @NotNull
    private static final FqName JVM_STATIC = new FqName("kotlin.jvm.JvmStatic");

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 13})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[KotlinClassHeader.Kind.values().length];

        static {
            $EnumSwitchMapping$0[KotlinClassHeader.Kind.FILE_FACADE.ordinal()] = 1;
            $EnumSwitchMapping$0[KotlinClassHeader.Kind.MULTIFILE_CLASS_PART.ordinal()] = 2;
            $EnumSwitchMapping$0[KotlinClassHeader.Kind.MULTIFILE_CLASS.ordinal()] = 3;
        }
    }

    @NotNull
    public static final FqName getJVM_STATIC() {
        return JVM_STATIC;
    }

    @Nullable
    public static final Class<?> toJavaClass(@NotNull ClassDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        SourceElement source = receiver$0.getSource();
        Intrinsics.checkExpressionValueIsNotNull(source, "source");
        if (source instanceof KotlinJvmBinarySourceElement) {
            KotlinJvmBinaryClass binaryClass = ((KotlinJvmBinarySourceElement) source).getBinaryClass();
            if (binaryClass != null) {
                return ((ReflectKotlinClass) binaryClass).getKlass();
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.components.ReflectKotlinClass");
        }
        if (source instanceof RuntimeSourceElementFactory.RuntimeSourceElement) {
            ReflectJavaElement javaElement = ((RuntimeSourceElementFactory.RuntimeSourceElement) source).getJavaElement();
            if (javaElement != null) {
                return ((ReflectJavaClass) javaElement).getElement();
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.structure.ReflectJavaClass");
        }
        ClassId classId = DescriptorUtilsKt.getClassId(receiver$0);
        if (classId == null) {
            return null;
        }
        return loadClass(ReflectClassUtilKt.getSafeClassLoader(receiver$0.getClass()), classId, 0);
    }

    static /* synthetic */ Class loadClass$default(ClassLoader classLoader, ClassId classId, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return loadClass(classLoader, classId, i);
    }

    private static final Class<?> loadClass(ClassLoader classLoader, ClassId kotlinClassId, int arrayDimensions) {
        JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
        FqNameUnsafe unsafe = kotlinClassId.asSingleFqName().toUnsafe();
        Intrinsics.checkExpressionValueIsNotNull(unsafe, "kotlinClassId.asSingleFqName().toUnsafe()");
        ClassId javaClassId = javaToKotlinClassMap.mapKotlinToJava(unsafe);
        if (javaClassId == null) {
            javaClassId = kotlinClassId;
        }
        String strAsString = javaClassId.getPackageFqName().asString();
        Intrinsics.checkExpressionValueIsNotNull(strAsString, "javaClassId.packageFqName.asString()");
        String strAsString2 = javaClassId.getRelativeClassName().asString();
        Intrinsics.checkExpressionValueIsNotNull(strAsString2, "javaClassId.relativeClassName.asString()");
        return loadClass(classLoader, strAsString, strAsString2, arrayDimensions);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static final Class<?> loadClass(ClassLoader classLoader, String packageName, String className, int arrayDimensions) {
        if (Intrinsics.areEqual(packageName, "kotlin")) {
            switch (className.hashCode()) {
                case -901856463:
                    if (className.equals("BooleanArray")) {
                        return boolean[].class;
                    }
                    break;
                case -763279523:
                    if (className.equals("ShortArray")) {
                        return short[].class;
                    }
                    break;
                case -755911549:
                    if (className.equals("CharArray")) {
                        return char[].class;
                    }
                    break;
                case -74930671:
                    if (className.equals("ByteArray")) {
                        return byte[].class;
                    }
                    break;
                case 22374632:
                    if (className.equals("DoubleArray")) {
                        return double[].class;
                    }
                    break;
                case 63537721:
                    if (className.equals("Array")) {
                        return Object[].class;
                    }
                    break;
                case 601811914:
                    if (className.equals("IntArray")) {
                        return int[].class;
                    }
                    break;
                case 948852093:
                    if (className.equals("FloatArray")) {
                        return float[].class;
                    }
                    break;
                case 2104330525:
                    if (className.equals("LongArray")) {
                        return long[].class;
                    }
                    break;
            }
        }
        String str = packageName + '.' + StringsKt.replace$default(className, '.', Typography.dollar, false, 4, (Object) null);
        for (int i = 0; i < arrayDimensions; i++) {
            str = '[' + str;
        }
        return ReflectJavaClassFinderKt.tryLoadClass(classLoader, str);
    }

    @Nullable
    public static final KVisibility toKVisibility(@NotNull Visibility receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (Intrinsics.areEqual(receiver$0, Visibilities.PUBLIC)) {
            return KVisibility.PUBLIC;
        }
        if (Intrinsics.areEqual(receiver$0, Visibilities.PROTECTED)) {
            return KVisibility.PROTECTED;
        }
        if (Intrinsics.areEqual(receiver$0, Visibilities.INTERNAL)) {
            return KVisibility.INTERNAL;
        }
        if (Intrinsics.areEqual(receiver$0, Visibilities.PRIVATE) || Intrinsics.areEqual(receiver$0, Visibilities.PRIVATE_TO_THIS)) {
            return KVisibility.PRIVATE;
        }
        return null;
    }

    @NotNull
    public static final List<Annotation> computeAnnotations(@NotNull Annotated receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = receiver$0.getAnnotations();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $receiver$iv) {
            AnnotationDescriptor it = (AnnotationDescriptor) element$iv$iv$iv;
            SourceElement source = it.getSource();
            Annotation annotationInstance = null;
            if (source instanceof ReflectAnnotationSource) {
                annotationInstance = ((ReflectAnnotationSource) source).getAnnotation();
            } else if (source instanceof RuntimeSourceElementFactory.RuntimeSourceElement) {
                ReflectJavaElement javaElement = ((RuntimeSourceElementFactory.RuntimeSourceElement) source).getJavaElement();
                if (!(javaElement instanceof ReflectJavaAnnotation)) {
                    javaElement = null;
                }
                ReflectJavaAnnotation reflectJavaAnnotation = (ReflectJavaAnnotation) javaElement;
                if (reflectJavaAnnotation != null) {
                    annotationInstance = reflectJavaAnnotation.getAnnotation();
                }
            } else {
                annotationInstance = toAnnotationInstance(it);
            }
            if (annotationInstance != null) {
                destination$iv$iv.add(annotationInstance);
            }
        }
        return (List) destination$iv$iv;
    }

    private static final Annotation toAnnotationInstance(@NotNull AnnotationDescriptor $receiver) {
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass($receiver);
        Class<?> javaClass = annotationClass != null ? toJavaClass(annotationClass) : null;
        if (!(javaClass instanceof Class)) {
            javaClass = null;
        }
        if (javaClass == null) {
            return null;
        }
        Iterable $receiver$iv = $receiver.getAllValueArguments().entrySet();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $receiver$iv) {
            Map.Entry $name_value = (Map.Entry) element$iv$iv$iv;
            Name name = (Name) $name_value.getKey();
            ConstantValue value = (ConstantValue) $name_value.getValue();
            ClassLoader classLoader = javaClass.getClassLoader();
            Iterable $receiver$iv2 = $receiver$iv;
            Intrinsics.checkExpressionValueIsNotNull(classLoader, "annotationClass.classLoader");
            Object p1 = toRuntimeValue(value, classLoader);
            Pair pair = p1 != null ? TuplesKt.to(name.asString(), p1) : null;
            if (pair != null) {
                destination$iv$iv.add(pair);
            }
            $receiver$iv = $receiver$iv2;
        }
        return (Annotation) AnnotationConstructorCallerKt.createAnnotationInstance$default(javaClass, MapsKt.toMap((List) destination$iv$iv), null, 4, null);
    }

    private static final Object toRuntimeValue(@NotNull ConstantValue<?> constantValue, ClassLoader classLoader) {
        if (constantValue instanceof AnnotationValue) {
            return toAnnotationInstance(((AnnotationValue) constantValue).getValue());
        }
        if (!(constantValue instanceof ArrayValue)) {
            if (constantValue instanceof EnumValue) {
                Pair<? extends ClassId, ? extends Name> value = ((EnumValue) constantValue).getValue();
                ClassId enumClassId = value.component1();
                Name entryName = value.component2();
                Class enumClass = loadClass$default(classLoader, enumClassId, 0, 4, null);
                if (enumClass != null) {
                    return Util.getEnumConstantByName(enumClass, entryName.asString());
                }
                return null;
            }
            if (constantValue instanceof KClassValue) {
                ClassLiteralValue value2 = ((KClassValue) constantValue).getValue();
                ClassId classId = value2.component1();
                int arrayDimensions = value2.component2();
                return loadClass(classLoader, classId, arrayDimensions);
            }
            if ((constantValue instanceof ErrorValue) || (constantValue instanceof NullValue)) {
                return null;
            }
            return constantValue.getValue();
        }
        Iterable $receiver$iv = ((ArrayValue) constantValue).getValue();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            ConstantValue it = (ConstantValue) item$iv$iv;
            destination$iv$iv.add(toRuntimeValue(it, classLoader));
        }
        Object[] array = ((List) destination$iv$iv).toArray(new Object[0]);
        if (array != null) {
            return array;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    @Nullable
    public static final KFunctionImpl asKFunctionImpl(@Nullable Object $receiver) {
        KFunctionImpl kFunctionImpl = (KFunctionImpl) (!($receiver instanceof KFunctionImpl) ? null : $receiver);
        if (kFunctionImpl != null) {
            return kFunctionImpl;
        }
        FunctionReference functionReference = (FunctionReference) (!($receiver instanceof FunctionReference) ? null : $receiver);
        KCallable kCallableCompute = functionReference != null ? functionReference.compute() : null;
        if (!(kCallableCompute instanceof KFunctionImpl)) {
            kCallableCompute = null;
        }
        return (KFunctionImpl) kCallableCompute;
    }

    @Nullable
    public static final KPropertyImpl<?> asKPropertyImpl(@Nullable Object $receiver) {
        KPropertyImpl<?> kPropertyImpl = (KPropertyImpl) (!($receiver instanceof KPropertyImpl) ? null : $receiver);
        if (kPropertyImpl != null) {
            return kPropertyImpl;
        }
        PropertyReference propertyReference = (PropertyReference) (!($receiver instanceof PropertyReference) ? null : $receiver);
        KCallable kCallableCompute = propertyReference != null ? propertyReference.compute() : null;
        if (!(kCallableCompute instanceof KPropertyImpl)) {
            kCallableCompute = null;
        }
        return (KPropertyImpl) kCallableCompute;
    }

    @Nullable
    public static final KCallableImpl<?> asKCallableImpl(@Nullable Object $receiver) {
        KFunctionImpl kFunctionImplAsKFunctionImpl = (KCallableImpl) (!($receiver instanceof KCallableImpl) ? null : $receiver);
        if (kFunctionImplAsKFunctionImpl == null) {
            kFunctionImplAsKFunctionImpl = asKFunctionImpl($receiver);
        }
        return kFunctionImplAsKFunctionImpl != null ? kFunctionImplAsKFunctionImpl : asKPropertyImpl($receiver);
    }

    @Nullable
    public static final String getPackageModuleName(@NotNull ReflectKotlinClass receiver$0) throws ClassNotFoundException, InvocationTargetException {
        String partName;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        KotlinClassHeader header = receiver$0.getClassHeader();
        if (!header.getMetadataVersion().isCompatible()) {
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[header.getKind().ordinal()];
        if (i != 1 && i != 2) {
            if (i != 3 || (partName = (String) CollectionsKt.firstOrNull((List) header.getMultifilePartNames())) == null) {
                return null;
            }
            ReflectKotlinClass.Companion companion = ReflectKotlinClass.INSTANCE;
            Class<?> clsLoadClass = receiver$0.getKlass().getClassLoader().loadClass(StringsKt.replace$default(partName, '/', '.', false, 4, (Object) null));
            Intrinsics.checkExpressionValueIsNotNull(clsLoadClass, "klass.classLoader.loadCl…rtName.replace('/', '.'))");
            ReflectKotlinClass reflectKotlinClassCreate = companion.create(clsLoadClass);
            if (reflectKotlinClassCreate != null) {
                return getPackageModuleName(reflectKotlinClassCreate);
            }
            return null;
        }
        String[] data = header.getData();
        if (data == null) {
            Intrinsics.throwNpe();
        }
        String[] strings = header.getStrings();
        if (strings == null) {
            Intrinsics.throwNpe();
        }
        Pair<JvmNameResolver, ProtoBuf.Package> packageDataFrom = JvmProtoBufUtil.readPackageDataFrom(data, strings);
        JvmNameResolver nameResolver = packageDataFrom.component1();
        ProtoBuf.Package proto = packageDataFrom.component2();
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> generatedExtension = JvmProtoBuf.packageModuleName;
        Intrinsics.checkExpressionValueIsNotNull(generatedExtension, "JvmProtoBuf.packageModuleName");
        Integer num = (Integer) ProtoBufUtilKt.getExtensionOrNull(proto, generatedExtension);
        if (num != null) {
            int p1 = num.intValue();
            String string = nameResolver.getString(p1);
            if (string != null) {
                return string;
            }
        }
        return "main";
    }

    @Nullable
    public static final ReceiverParameterDescriptor getInstanceReceiverParameter(@NotNull CallableDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (receiver$0.getDispatchReceiverParameter() == null) {
            return null;
        }
        DeclarationDescriptor containingDeclaration = receiver$0.getContainingDeclaration();
        if (containingDeclaration != null) {
            return ((ClassDescriptor) containingDeclaration).getThisAsReceiverParameter();
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
    }

    @Nullable
    public static final <M extends MessageLite, D extends CallableDescriptor> D deserializeToDescriptor(@NotNull Class<?> moduleAnchor, @NotNull M proto, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable, @NotNull BinaryVersion metadataVersion, @NotNull Function2<? super MemberDeserializer, ? super M, ? extends D> createDescriptor) {
        List<ProtoBuf.TypeParameter> typeParameterList;
        Intrinsics.checkParameterIsNotNull(moduleAnchor, "moduleAnchor");
        Intrinsics.checkParameterIsNotNull(proto, "proto");
        Intrinsics.checkParameterIsNotNull(nameResolver, "nameResolver");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        Intrinsics.checkParameterIsNotNull(metadataVersion, "metadataVersion");
        Intrinsics.checkParameterIsNotNull(createDescriptor, "createDescriptor");
        RuntimeModuleData moduleData = ModuleByClassLoaderKt.getOrCreateModule(moduleAnchor);
        if (proto instanceof ProtoBuf.Function) {
            typeParameterList = ((ProtoBuf.Function) proto).getTypeParameterList();
        } else {
            if (!(proto instanceof ProtoBuf.Property)) {
                throw new IllegalStateException(("Unsupported message: " + proto).toString());
            }
            typeParameterList = ((ProtoBuf.Property) proto).getTypeParameterList();
        }
        List<ProtoBuf.TypeParameter> typeParameters = typeParameterList;
        DeserializationComponents deserialization = moduleData.getDeserialization();
        ModuleDescriptor module = moduleData.getModule();
        VersionRequirementTable empty = VersionRequirementTable.Companion.getEMPTY();
        Intrinsics.checkExpressionValueIsNotNull(typeParameters, "typeParameters");
        DeserializationContext context = new DeserializationContext(deserialization, nameResolver, module, typeTable, empty, metadataVersion, null, null, typeParameters);
        return createDescriptor.invoke(new MemberDeserializer(context), proto);
    }
}
