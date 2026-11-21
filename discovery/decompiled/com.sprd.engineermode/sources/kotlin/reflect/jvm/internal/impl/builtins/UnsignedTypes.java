package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: UnsignedType.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class UnsignedTypes {
    public static final UnsignedTypes INSTANCE = new UnsignedTypes();
    private static final HashMap<ClassId, ClassId> arrayClassIdToUnsignedClassId;
    private static final Set<Name> arrayClassesShortNames;
    private static final HashMap<ClassId, ClassId> unsignedClassIdToArrayClassId;
    private static final Set<Name> unsignedTypeNames;

    static {
        UnsignedType[] unsignedTypeArrValues = UnsignedType.values();
        Collection destination$iv$iv = new ArrayList(unsignedTypeArrValues.length);
        for (UnsignedType unsignedType : unsignedTypeArrValues) {
            destination$iv$iv.add(unsignedType.getTypeName());
        }
        unsignedTypeNames = CollectionsKt.toSet((List) destination$iv$iv);
        arrayClassIdToUnsignedClassId = new HashMap<>();
        unsignedClassIdToArrayClassId = new HashMap<>();
        UnsignedType[] unsignedTypeArrValues2 = UnsignedType.values();
        Collection destination$iv = (Set) new LinkedHashSet();
        for (UnsignedType unsignedType2 : unsignedTypeArrValues2) {
            destination$iv.add(unsignedType2.getArrayClassId().getShortClassName());
        }
        arrayClassesShortNames = (Set) destination$iv;
        for (UnsignedType unsignedType3 : UnsignedType.values()) {
            arrayClassIdToUnsignedClassId.put(unsignedType3.getArrayClassId(), unsignedType3.getClassId());
            unsignedClassIdToArrayClassId.put(unsignedType3.getClassId(), unsignedType3.getArrayClassId());
        }
    }

    private UnsignedTypes() {
    }

    public final boolean isShortNameOfUnsignedArray(@NotNull Name name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return arrayClassesShortNames.contains(name);
    }

    @Nullable
    public final ClassId getUnsignedClassIdByArrayClassId(@NotNull ClassId arrayClassId) {
        Intrinsics.checkParameterIsNotNull(arrayClassId, "arrayClassId");
        return arrayClassIdToUnsignedClassId.get(arrayClassId);
    }

    @Nullable
    public final ClassId getUnsignedArrayClassIdByUnsignedClassId(@NotNull ClassId arrayClassId) {
        Intrinsics.checkParameterIsNotNull(arrayClassId, "arrayClassId");
        return unsignedClassIdToArrayClassId.get(arrayClassId);
    }

    public final boolean isUnsignedType(@NotNull KotlinType type) {
        ClassifierDescriptor descriptor;
        Intrinsics.checkParameterIsNotNull(type, "type");
        if (TypeUtils.noExpectedType(type) || (descriptor = type.getConstructor().mo381getDeclarationDescriptor()) == null) {
            return false;
        }
        Intrinsics.checkExpressionValueIsNotNull(descriptor, "type.constructor.declara…escriptor ?: return false");
        return isUnsignedClass(descriptor);
    }

    public final boolean isUnsignedClass(@NotNull DeclarationDescriptor descriptor) {
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        DeclarationDescriptor container = descriptor.getContainingDeclaration();
        return (container instanceof PackageFragmentDescriptor) && Intrinsics.areEqual(((PackageFragmentDescriptor) container).getFqName(), KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME) && unsignedTypeNames.contains(descriptor.getName());
    }
}
