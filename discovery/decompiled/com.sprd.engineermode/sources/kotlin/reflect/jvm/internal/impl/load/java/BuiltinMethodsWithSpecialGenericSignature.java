package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: specialBuiltinMembers.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class BuiltinMethodsWithSpecialGenericSignature {

    @NotNull
    private static final List<String> ERASED_COLLECTION_PARAMETER_NAMES;
    private static final List<NameAndSignature> ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES;
    private static final List<String> ERASED_COLLECTION_PARAMETER_SIGNATURES;
    private static final Set<Name> ERASED_VALUE_PARAMETERS_SHORT_NAMES;

    @NotNull
    private static final Set<String> ERASED_VALUE_PARAMETERS_SIGNATURES;
    private static final Map<NameAndSignature, TypeSafeBarrierDescription> GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP;
    public static final BuiltinMethodsWithSpecialGenericSignature INSTANCE = new BuiltinMethodsWithSpecialGenericSignature();
    private static final Map<String, TypeSafeBarrierDescription> SIGNATURE_TO_DEFAULT_VALUES_MAP;

    static {
        Iterable $receiver$iv = SetsKt.setOf((Object[]) new String[]{"containsAll", "removeAll", "retainAll"});
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            String it = (String) item$iv$iv;
            String desc = JvmPrimitiveType.BOOLEAN.getDesc();
            Intrinsics.checkExpressionValueIsNotNull(desc, "JvmPrimitiveType.BOOLEAN.desc");
            destination$iv$iv.add(SpecialBuiltinMembers.method("java/util/Collection", it, "Ljava/util/Collection;", desc));
        }
        ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES = (List) destination$iv$iv;
        Iterable $receiver$iv2 = ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
        for (Object item$iv$iv2 : $receiver$iv2) {
            NameAndSignature it2 = (NameAndSignature) item$iv$iv2;
            destination$iv$iv2.add(it2.getSignature());
        }
        ERASED_COLLECTION_PARAMETER_SIGNATURES = (List) destination$iv$iv2;
        Iterable $receiver$iv3 = ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv3, 10));
        for (Object item$iv$iv3 : $receiver$iv3) {
            NameAndSignature it3 = (NameAndSignature) item$iv$iv3;
            destination$iv$iv3.add(it3.getName().asString());
        }
        ERASED_COLLECTION_PARAMETER_NAMES = (List) destination$iv$iv3;
        SignatureBuildingComponents $receiver = SignatureBuildingComponents.INSTANCE;
        String strJavaUtil = $receiver.javaUtil("Collection");
        String desc2 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkExpressionValueIsNotNull(desc2, "JvmPrimitiveType.BOOLEAN.desc");
        String strJavaUtil2 = $receiver.javaUtil("Collection");
        String desc3 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkExpressionValueIsNotNull(desc3, "JvmPrimitiveType.BOOLEAN.desc");
        String strJavaUtil3 = $receiver.javaUtil("Map");
        String desc4 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkExpressionValueIsNotNull(desc4, "JvmPrimitiveType.BOOLEAN.desc");
        String strJavaUtil4 = $receiver.javaUtil("Map");
        String desc5 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkExpressionValueIsNotNull(desc5, "JvmPrimitiveType.BOOLEAN.desc");
        String strJavaUtil5 = $receiver.javaUtil("Map");
        String desc6 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkExpressionValueIsNotNull(desc6, "JvmPrimitiveType.BOOLEAN.desc");
        String strJavaUtil6 = $receiver.javaUtil("List");
        String desc7 = JvmPrimitiveType.INT.getDesc();
        Intrinsics.checkExpressionValueIsNotNull(desc7, "JvmPrimitiveType.INT.desc");
        String strJavaUtil7 = $receiver.javaUtil("List");
        String desc8 = JvmPrimitiveType.INT.getDesc();
        Intrinsics.checkExpressionValueIsNotNull(desc8, "JvmPrimitiveType.INT.desc");
        GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP = MapsKt.mapOf(TuplesKt.to(SpecialBuiltinMembers.method(strJavaUtil, "contains", "Ljava/lang/Object;", desc2), TypeSafeBarrierDescription.FALSE), TuplesKt.to(SpecialBuiltinMembers.method(strJavaUtil2, "remove", "Ljava/lang/Object;", desc3), TypeSafeBarrierDescription.FALSE), TuplesKt.to(SpecialBuiltinMembers.method(strJavaUtil3, "containsKey", "Ljava/lang/Object;", desc4), TypeSafeBarrierDescription.FALSE), TuplesKt.to(SpecialBuiltinMembers.method(strJavaUtil4, "containsValue", "Ljava/lang/Object;", desc5), TypeSafeBarrierDescription.FALSE), TuplesKt.to(SpecialBuiltinMembers.method(strJavaUtil5, "remove", "Ljava/lang/Object;Ljava/lang/Object;", desc6), TypeSafeBarrierDescription.FALSE), TuplesKt.to(SpecialBuiltinMembers.method($receiver.javaUtil("Map"), "getOrDefault", "Ljava/lang/Object;Ljava/lang/Object;", "Ljava/lang/Object;"), TypeSafeBarrierDescription.MAP_GET_OR_DEFAULT), TuplesKt.to(SpecialBuiltinMembers.method($receiver.javaUtil("Map"), "get", "Ljava/lang/Object;", "Ljava/lang/Object;"), TypeSafeBarrierDescription.NULL), TuplesKt.to(SpecialBuiltinMembers.method($receiver.javaUtil("Map"), "remove", "Ljava/lang/Object;", "Ljava/lang/Object;"), TypeSafeBarrierDescription.NULL), TuplesKt.to(SpecialBuiltinMembers.method(strJavaUtil6, "indexOf", "Ljava/lang/Object;", desc7), TypeSafeBarrierDescription.INDEX), TuplesKt.to(SpecialBuiltinMembers.method(strJavaUtil7, "lastIndexOf", "Ljava/lang/Object;", desc8), TypeSafeBarrierDescription.INDEX));
        Map<NameAndSignature, TypeSafeBarrierDescription> map = GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP;
        Map destination$iv$iv4 = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
        Iterable $receiver$iv$iv$iv = map.entrySet();
        for (Object element$iv$iv$iv : $receiver$iv$iv$iv) {
            Map.Entry it4 = (Map.Entry) element$iv$iv$iv;
            Map.Entry it$iv$iv = (Map.Entry) element$iv$iv$iv;
            destination$iv$iv4.put(((NameAndSignature) it4.getKey()).getSignature(), it$iv$iv.getValue());
        }
        SIGNATURE_TO_DEFAULT_VALUES_MAP = destination$iv$iv4;
        Iterable allMethods = SetsKt.plus((Set) GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP.keySet(), (Iterable) ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES);
        Iterable $receiver$iv4 = allMethods;
        Collection destination$iv$iv5 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv4, 10));
        for (Object item$iv$iv4 : $receiver$iv4) {
            NameAndSignature it5 = (NameAndSignature) item$iv$iv4;
            destination$iv$iv5.add(it5.getName());
        }
        ERASED_VALUE_PARAMETERS_SHORT_NAMES = CollectionsKt.toSet((List) destination$iv$iv5);
        Iterable $receiver$iv5 = allMethods;
        Collection destination$iv$iv6 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv5, 10));
        for (Object item$iv$iv5 : $receiver$iv5) {
            NameAndSignature it6 = (NameAndSignature) item$iv$iv5;
            destination$iv$iv6.add(it6.getSignature());
        }
        ERASED_VALUE_PARAMETERS_SIGNATURES = CollectionsKt.toSet((List) destination$iv$iv6);
    }

    private BuiltinMethodsWithSpecialGenericSignature() {
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: specialBuiltinMembers.kt */
    public static final class TypeSafeBarrierDescription {
        private static final /* synthetic */ TypeSafeBarrierDescription[] $VALUES;
        public static final TypeSafeBarrierDescription FALSE;
        public static final TypeSafeBarrierDescription INDEX;
        public static final TypeSafeBarrierDescription MAP_GET_OR_DEFAULT;
        public static final TypeSafeBarrierDescription NULL;

        @Nullable
        private final Object defaultValue;

        public static TypeSafeBarrierDescription valueOf(String str) {
            return (TypeSafeBarrierDescription) Enum.valueOf(TypeSafeBarrierDescription.class, str);
        }

        public static TypeSafeBarrierDescription[] values() {
            return (TypeSafeBarrierDescription[]) $VALUES.clone();
        }

        private TypeSafeBarrierDescription(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1, Object defaultValue) {
            super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1);
            this.defaultValue = defaultValue;
        }

        public /* synthetic */ TypeSafeBarrierDescription(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1, Object defaultValue, DefaultConstructorMarker $constructor_marker) {
            this($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, defaultValue);
        }

        static {
            TypeSafeBarrierDescription typeSafeBarrierDescription = new TypeSafeBarrierDescription("NULL", 0, null);
            NULL = typeSafeBarrierDescription;
            TypeSafeBarrierDescription typeSafeBarrierDescription2 = new TypeSafeBarrierDescription("INDEX", 1, -1);
            INDEX = typeSafeBarrierDescription2;
            TypeSafeBarrierDescription typeSafeBarrierDescription3 = new TypeSafeBarrierDescription("FALSE", 2, false);
            FALSE = typeSafeBarrierDescription3;
            MAP_GET_OR_DEFAULT map_get_or_default = new MAP_GET_OR_DEFAULT("MAP_GET_OR_DEFAULT", 3);
            MAP_GET_OR_DEFAULT = map_get_or_default;
            $VALUES = new TypeSafeBarrierDescription[]{typeSafeBarrierDescription, typeSafeBarrierDescription2, typeSafeBarrierDescription3, map_get_or_default};
        }

        /* JADX INFO: compiled from: specialBuiltinMembers.kt */
        static final class MAP_GET_OR_DEFAULT extends TypeSafeBarrierDescription {
            /* JADX WARN: Illegal instructions before constructor call */
            MAP_GET_OR_DEFAULT(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                DefaultConstructorMarker defaultConstructorMarker = null;
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, defaultConstructorMarker, defaultConstructorMarker);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getHasErasedValueParametersInJava(@NotNull CallableMemberDescriptor $receiver) {
        return CollectionsKt.contains(ERASED_VALUE_PARAMETERS_SIGNATURES, MethodSignatureMappingKt.computeJvmSignature($receiver));
    }

    @JvmStatic
    @Nullable
    public static final FunctionDescriptor getOverriddenBuiltinFunctionWithErasedValueParametersInJava(@NotNull FunctionDescriptor functionDescriptor) {
        Intrinsics.checkParameterIsNotNull(functionDescriptor, "functionDescriptor");
        BuiltinMethodsWithSpecialGenericSignature builtinMethodsWithSpecialGenericSignature = INSTANCE;
        Name name = functionDescriptor.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "functionDescriptor.name");
        if (builtinMethodsWithSpecialGenericSignature.getSameAsBuiltinMethodWithErasedValueParameters(name)) {
            return (FunctionDescriptor) DescriptorUtilsKt.firstOverridden$default(functionDescriptor, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Boolean invoke(CallableMemberDescriptor callableMemberDescriptor) {
                    return Boolean.valueOf(invoke2(callableMemberDescriptor));
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final boolean invoke2(@NotNull CallableMemberDescriptor it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    return BuiltinMethodsWithSpecialGenericSignature.INSTANCE.getHasErasedValueParametersInJava(it);
                }
            }, 1, null);
        }
        return null;
    }

    public final boolean getSameAsBuiltinMethodWithErasedValueParameters(@NotNull Name receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return ERASED_VALUE_PARAMETERS_SHORT_NAMES.contains(receiver$0);
    }

    /* JADX INFO: compiled from: specialBuiltinMembers.kt */
    public enum SpecialSignatureInfo {
        ONE_COLLECTION_PARAMETER("Ljava/util/Collection<+Ljava/lang/Object;>;", false),
        OBJECT_PARAMETER_NON_GENERIC(null, true),
        OBJECT_PARAMETER_GENERIC("Ljava/lang/Object;", true);

        private final boolean isObjectReplacedWithTypeParameter;

        @Nullable
        private final String valueParametersSignature;

        SpecialSignatureInfo(String valueParametersSignature, boolean isObjectReplacedWithTypeParameter) {
            this.valueParametersSignature = valueParametersSignature;
            this.isObjectReplacedWithTypeParameter = isObjectReplacedWithTypeParameter;
        }
    }

    @JvmStatic
    @Nullable
    public static final SpecialSignatureInfo getSpecialSignatureInfo(@NotNull CallableMemberDescriptor receiver$0) {
        CallableMemberDescriptor callableMemberDescriptorFirstOverridden$default;
        String builtinSignature;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (!ERASED_VALUE_PARAMETERS_SHORT_NAMES.contains(receiver$0.getName()) || (callableMemberDescriptorFirstOverridden$default = DescriptorUtilsKt.firstOverridden$default(receiver$0, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithSpecialGenericSignature$getSpecialSignatureInfo$builtinSignature$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(CallableMemberDescriptor callableMemberDescriptor) {
                return Boolean.valueOf(invoke2(callableMemberDescriptor));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(@NotNull CallableMemberDescriptor it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return (it instanceof FunctionDescriptor) && BuiltinMethodsWithSpecialGenericSignature.INSTANCE.getHasErasedValueParametersInJava(it);
            }
        }, 1, null)) == null || (builtinSignature = MethodSignatureMappingKt.computeJvmSignature(callableMemberDescriptorFirstOverridden$default)) == null) {
            return null;
        }
        if (ERASED_COLLECTION_PARAMETER_SIGNATURES.contains(builtinSignature)) {
            return SpecialSignatureInfo.ONE_COLLECTION_PARAMETER;
        }
        TypeSafeBarrierDescription typeSafeBarrierDescription = SIGNATURE_TO_DEFAULT_VALUES_MAP.get(builtinSignature);
        if (typeSafeBarrierDescription == null) {
            Intrinsics.throwNpe();
        }
        TypeSafeBarrierDescription defaultValue = typeSafeBarrierDescription;
        if (defaultValue == TypeSafeBarrierDescription.NULL) {
            return SpecialSignatureInfo.OBJECT_PARAMETER_GENERIC;
        }
        return SpecialSignatureInfo.OBJECT_PARAMETER_NON_GENERIC;
    }
}
