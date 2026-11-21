package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: methodSignatureBuilding.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class SignatureBuildingComponents {
    public static final SignatureBuildingComponents INSTANCE = new SignatureBuildingComponents();

    private SignatureBuildingComponents() {
    }

    @NotNull
    public final String javaLang(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return "java/lang/" + name;
    }

    @NotNull
    public final String javaUtil(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return "java/util/" + name;
    }

    @NotNull
    public final String javaFunction(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return "java/util/function/" + name;
    }

    @NotNull
    public final String[] constructors(@NotNull String... signatures) {
        Intrinsics.checkParameterIsNotNull(signatures, "signatures");
        Collection destination$iv$iv = new ArrayList(signatures.length);
        for (String str : signatures) {
            String it = "<init>(" + str + ")V";
            destination$iv$iv.add(it);
        }
        Collection $receiver$iv = (List) destination$iv$iv;
        Object[] array = $receiver$iv.toArray(new String[0]);
        if (array != null) {
            return (String[]) array;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    @NotNull
    public final LinkedHashSet<String> inJavaLang(@NotNull String name, @NotNull String... signatures) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(signatures, "signatures");
        return inClass(javaLang(name), (String[]) Arrays.copyOf(signatures, signatures.length));
    }

    @NotNull
    public final LinkedHashSet<String> inJavaUtil(@NotNull String name, @NotNull String... signatures) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(signatures, "signatures");
        return inClass(javaUtil(name), (String[]) Arrays.copyOf(signatures, signatures.length));
    }

    @NotNull
    public final LinkedHashSet<String> inClass(@NotNull String internalName, @NotNull String... signatures) {
        Intrinsics.checkParameterIsNotNull(internalName, "internalName");
        Intrinsics.checkParameterIsNotNull(signatures, "signatures");
        Collection destination$iv = new LinkedHashSet();
        for (String str : signatures) {
            String it = internalName + "." + str;
            destination$iv.add(it);
        }
        return (LinkedHashSet) destination$iv;
    }

    @NotNull
    public final String signature(@NotNull ClassDescriptor classDescriptor, @NotNull String jvmDescriptor) {
        Intrinsics.checkParameterIsNotNull(classDescriptor, "classDescriptor");
        Intrinsics.checkParameterIsNotNull(jvmDescriptor, "jvmDescriptor");
        return signature(MethodSignatureMappingKt.getInternalName(classDescriptor), jvmDescriptor);
    }

    @NotNull
    public final String signature(@NotNull String internalName, @NotNull String jvmDescriptor) {
        Intrinsics.checkParameterIsNotNull(internalName, "internalName");
        Intrinsics.checkParameterIsNotNull(jvmDescriptor, "jvmDescriptor");
        return internalName + "." + jvmDescriptor;
    }

    @NotNull
    public final String jvmDescriptor(@NotNull String name, @NotNull List<String> parameters, @NotNull String ret) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(parameters, "parameters");
        Intrinsics.checkParameterIsNotNull(ret, "ret");
        return name + '(' + CollectionsKt.joinToString$default(parameters, "", null, null, 0, null, new Function1<String, String>() { // from class: kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents.jvmDescriptor.1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final String invoke(@NotNull String it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return SignatureBuildingComponents.INSTANCE.escapeClassName(it);
            }
        }, 30, null) + ')' + escapeClassName(ret);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String escapeClassName(String internalName) {
        if (internalName.length() <= 1) {
            return internalName;
        }
        return 'L' + internalName + ';';
    }
}
