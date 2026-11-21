package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: propertiesConventionUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class PropertiesConventionUtilKt {
    @Nullable
    public static final Name propertyNameByGetMethodName(@NotNull Name methodName) {
        Intrinsics.checkParameterIsNotNull(methodName, "methodName");
        Name namePropertyNameFromAccessorMethodName$default = propertyNameFromAccessorMethodName$default(methodName, "get", false, null, 12, null);
        return namePropertyNameFromAccessorMethodName$default != null ? namePropertyNameFromAccessorMethodName$default : propertyNameFromAccessorMethodName$default(methodName, "is", false, null, 8, null);
    }

    @Nullable
    public static final Name propertyNameBySetMethodName(@NotNull Name methodName, boolean withIsPrefix) {
        Intrinsics.checkParameterIsNotNull(methodName, "methodName");
        return propertyNameFromAccessorMethodName$default(methodName, "set", false, withIsPrefix ? "is" : null, 4, null);
    }

    @NotNull
    public static final List<Name> propertyNamesBySetMethodName(@NotNull Name methodName) {
        Intrinsics.checkParameterIsNotNull(methodName, "methodName");
        return CollectionsKt.filterNotNull(CollectionsKt.listOf((Object[]) new Name[]{propertyNameBySetMethodName(methodName, false), propertyNameBySetMethodName(methodName, true)}));
    }

    static /* synthetic */ Name propertyNameFromAccessorMethodName$default(Name name, String str, boolean z, String str2, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        if ((i & 8) != 0) {
            str2 = (String) null;
        }
        return propertyNameFromAccessorMethodName(name, str, z, str2);
    }

    private static final Name propertyNameFromAccessorMethodName(Name methodName, String prefix, boolean removePrefix, String addPrefix) {
        if (methodName.isSpecial()) {
            return null;
        }
        String identifier = methodName.getIdentifier();
        Intrinsics.checkExpressionValueIsNotNull(identifier, "methodName.identifier");
        if (!StringsKt.startsWith$default(identifier, prefix, false, 2, (Object) null) || identifier.length() == prefix.length()) {
            return null;
        }
        char cCharAt = identifier.charAt(prefix.length());
        if ('a' <= cCharAt && 'z' >= cCharAt) {
            return null;
        }
        if (addPrefix != null) {
            if (_Assertions.ENABLED && !removePrefix) {
                throw new AssertionError("Assertion failed");
            }
            return Name.identifier(addPrefix + StringsKt.removePrefix(identifier, (CharSequence) prefix));
        }
        if (!removePrefix) {
            return methodName;
        }
        String name = CapitalizeDecapitalizeKt.decapitalizeSmart(StringsKt.removePrefix(identifier, (CharSequence) prefix), true);
        if (Name.isValidIdentifier(name)) {
            return Name.identifier(name);
        }
        return null;
    }

    @NotNull
    public static final List<Name> getPropertyNamesCandidatesByAccessorName(@NotNull Name name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        String nameAsString = name.asString();
        Intrinsics.checkExpressionValueIsNotNull(nameAsString, "name.asString()");
        if (JvmAbi.isGetterName(nameAsString)) {
            return CollectionsKt.listOfNotNull(propertyNameByGetMethodName(name));
        }
        if (JvmAbi.isSetterName(nameAsString)) {
            return propertyNamesBySetMethodName(name);
        }
        return BuiltinSpecialProperties.INSTANCE.getPropertyNameCandidatesBySpecialGetterName(name);
    }
}
