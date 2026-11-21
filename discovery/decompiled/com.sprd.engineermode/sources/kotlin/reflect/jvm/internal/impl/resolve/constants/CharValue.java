package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: constantValues.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class CharValue extends IntegerValueConstant<Character> {
    public CharValue(char value) {
        super(Character.valueOf(value));
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue
    @NotNull
    public SimpleType getType(@NotNull ModuleDescriptor module) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        SimpleType charType = module.getBuiltIns().getCharType();
        Intrinsics.checkExpressionValueIsNotNull(charType, "module.builtIns.charType");
        return charType;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue
    @NotNull
    public String toString() {
        Object[] objArr = {Integer.valueOf(getValue().charValue()), getPrintablePart(getValue().charValue())};
        String str = String.format("\\u%04X ('%s')", Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.String.format(this, *args)");
        return str;
    }

    private final String getPrintablePart(char c) {
        switch (c) {
            case '\b':
                return "\\b";
            case '\t':
                return "\\t";
            case '\n':
                return "\\n";
            case 11:
            default:
                String string = isPrintableUnicode(c) ? Character.toString(c) : "?";
                Intrinsics.checkExpressionValueIsNotNull(string, "if (isPrintableUnicode(c…cter.toString(c) else \"?\"");
                return string;
            case '\f':
                return "\\f";
            case '\r':
                return "\\r";
        }
    }

    private final boolean isPrintableUnicode(char c) {
        byte t = (byte) Character.getType(c);
        return (t == 0 || t == 13 || t == 14 || t == 15 || t == 16 || t == 18 || t == 19) ? false : true;
    }
}
