package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: methodSignatureMapping.kt */
/* JADX INFO: loaded from: classes4.dex */
final class JvmTypeFactoryImpl implements JvmTypeFactory<JvmType> {
    public static final JvmTypeFactoryImpl INSTANCE = new JvmTypeFactoryImpl();

    private JvmTypeFactoryImpl() {
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory
    @NotNull
    public JvmType boxType(@NotNull JvmType possiblyPrimitiveType) {
        Intrinsics.checkParameterIsNotNull(possiblyPrimitiveType, "possiblyPrimitiveType");
        if ((possiblyPrimitiveType instanceof JvmType.Primitive) && ((JvmType.Primitive) possiblyPrimitiveType).getJvmPrimitiveType() != null) {
            JvmClassName jvmClassNameByFqNameWithoutInnerClasses = JvmClassName.byFqNameWithoutInnerClasses(((JvmType.Primitive) possiblyPrimitiveType).getJvmPrimitiveType().getWrapperFqName());
            Intrinsics.checkExpressionValueIsNotNull(jvmClassNameByFqNameWithoutInnerClasses, "JvmClassName.byFqNameWit…mitiveType.wrapperFqName)");
            String internalName = jvmClassNameByFqNameWithoutInnerClasses.getInternalName();
            Intrinsics.checkExpressionValueIsNotNull(internalName, "JvmClassName.byFqNameWit…apperFqName).internalName");
            return createObjectType(internalName);
        }
        return possiblyPrimitiveType;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory
    @NotNull
    public JvmType createFromString(@NotNull String representation) {
        JvmPrimitiveType jvmPrimitiveType;
        Intrinsics.checkParameterIsNotNull(representation, "representation");
        boolean z = representation.length() > 0;
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("empty string as JvmType");
        }
        char firstChar = representation.charAt(0);
        JvmPrimitiveType[] jvmPrimitiveTypeArrValues = JvmPrimitiveType.values();
        int length = jvmPrimitiveTypeArrValues.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                jvmPrimitiveType = null;
                break;
            }
            jvmPrimitiveType = jvmPrimitiveTypeArrValues[i];
            if (jvmPrimitiveType.getDesc().charAt(0) == firstChar) {
                break;
            }
            i++;
        }
        if (jvmPrimitiveType != null) {
            JvmPrimitiveType it = jvmPrimitiveType;
            return new JvmType.Primitive(it);
        }
        if (firstChar == 'V') {
            return new JvmType.Primitive(null);
        }
        if (firstChar == '[') {
            String strSubstring = representation.substring(1);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
            return new JvmType.Array(createFromString(strSubstring));
        }
        boolean z2 = firstChar == 'L' && StringsKt.endsWith$default((CharSequence) representation, ';', false, 2, (Object) null);
        if (!_Assertions.ENABLED || z2) {
            String strSubstring2 = representation.substring(1, representation.length() - 1);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return new JvmType.Object(strSubstring2);
        }
        throw new AssertionError("Type that is not primitive nor array should be Object, but '" + representation + "' was found");
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory
    @NotNull
    public JvmType createObjectType(@NotNull String internalName) {
        Intrinsics.checkParameterIsNotNull(internalName, "internalName");
        return new JvmType.Object(internalName);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory
    @NotNull
    public String toString(@NotNull JvmType type) {
        String desc;
        Intrinsics.checkParameterIsNotNull(type, "type");
        if (type instanceof JvmType.Array) {
            return "[" + toString(((JvmType.Array) type).getElementType());
        }
        if (type instanceof JvmType.Primitive) {
            JvmPrimitiveType jvmPrimitiveType = ((JvmType.Primitive) type).getJvmPrimitiveType();
            return (jvmPrimitiveType == null || (desc = jvmPrimitiveType.getDesc()) == null) ? "V" : desc;
        }
        if (!(type instanceof JvmType.Object)) {
            throw new NoWhenBranchMatchedException();
        }
        return "L" + ((JvmType.Object) type).getInternalName() + ";";
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory
    @NotNull
    public JvmType getJavaLangClassType() {
        return createObjectType("java/lang/Class");
    }
}
