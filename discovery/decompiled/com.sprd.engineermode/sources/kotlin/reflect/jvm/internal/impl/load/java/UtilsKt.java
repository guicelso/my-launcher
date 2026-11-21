package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.NumberWithRadix;
import kotlin.reflect.jvm.internal.impl.utils.NumbersKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class UtilsKt {
    @Nullable
    public static final JavaDefaultValue lexicalCastFrom(@NotNull KotlinType receiver$0, @NotNull String value) {
        Object result;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(value, "value");
        ClassifierDescriptor typeDescriptor = receiver$0.getConstructor().mo381getDeclarationDescriptor();
        if ((typeDescriptor instanceof ClassDescriptor) && ((ClassDescriptor) typeDescriptor).getKind() == ClassKind.ENUM_CLASS) {
            MemberScope unsubstitutedInnerClassesScope = ((ClassDescriptor) typeDescriptor).getUnsubstitutedInnerClassesScope();
            Name nameIdentifier = Name.identifier(value);
            Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier(value)");
            ClassifierDescriptor descriptor = unsubstitutedInnerClassesScope.mo382getContributedClassifier(nameIdentifier, NoLookupLocation.FROM_BACKEND);
            if ((descriptor instanceof ClassDescriptor) && ((ClassDescriptor) descriptor).getKind() == ClassKind.ENUM_ENTRY) {
                return new EnumEntry((ClassDescriptor) descriptor);
            }
            return null;
        }
        KotlinType type = TypeUtilsKt.makeNotNullable(receiver$0);
        NumberWithRadix numberWithRadixExtractRadix = NumbersKt.extractRadix(value);
        String number = numberWithRadixExtractRadix.component1();
        int radix = numberWithRadixExtractRadix.component2();
        try {
            if (KotlinBuiltIns.isBoolean(type)) {
                result = Boolean.valueOf(Boolean.parseBoolean(value));
            } else if (KotlinBuiltIns.isChar(type)) {
                result = StringsKt.singleOrNull(value);
            } else if (KotlinBuiltIns.isByte(type)) {
                result = StringsKt.toByteOrNull(number, radix);
            } else if (KotlinBuiltIns.isShort(type)) {
                result = StringsKt.toShortOrNull(number, radix);
            } else if (KotlinBuiltIns.isInt(type)) {
                result = StringsKt.toIntOrNull(number, radix);
            } else if (KotlinBuiltIns.isLong(type)) {
                result = StringsKt.toLongOrNull(number, radix);
            } else if (KotlinBuiltIns.isFloat(type)) {
                result = StringsKt.toFloatOrNull(value);
            } else if (KotlinBuiltIns.isDouble(type)) {
                result = StringsKt.toDoubleOrNull(value);
            } else {
                result = KotlinBuiltIns.isString(type) ? value : null;
            }
        } catch (IllegalArgumentException e) {
            result = null;
        }
        if (result != null) {
            return new Constant(result);
        }
        return null;
    }
}
