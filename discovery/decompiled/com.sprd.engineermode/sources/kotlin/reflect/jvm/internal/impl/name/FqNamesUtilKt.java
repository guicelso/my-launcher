package kotlin.reflect.jvm.internal.impl.name;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: FqNamesUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class FqNamesUtilKt {

    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[State.values().length];

        static {
            $EnumSwitchMapping$0[State.BEGINNING.ordinal()] = 1;
            $EnumSwitchMapping$0[State.AFTER_DOT.ordinal()] = 2;
            $EnumSwitchMapping$0[State.MIDDLE.ordinal()] = 3;
        }
    }

    public static final boolean isSubpackageOf(@NotNull FqName receiver$0, @NotNull FqName packageName) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(packageName, "packageName");
        if (Intrinsics.areEqual(receiver$0, packageName) || packageName.isRoot()) {
            return true;
        }
        String strAsString = receiver$0.asString();
        Intrinsics.checkExpressionValueIsNotNull(strAsString, "this.asString()");
        String strAsString2 = packageName.asString();
        Intrinsics.checkExpressionValueIsNotNull(strAsString2, "packageName.asString()");
        return isSubpackageOf(strAsString, strAsString2);
    }

    private static final boolean isSubpackageOf(String subpackageNameStr, String packageNameStr) {
        return StringsKt.startsWith$default(subpackageNameStr, packageNameStr, false, 2, (Object) null) && subpackageNameStr.charAt(packageNameStr.length()) == '.';
    }

    @NotNull
    public static final FqName tail(@NotNull FqName receiver$0, @NotNull FqName prefix) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        if (!isSubpackageOf(receiver$0, prefix) || prefix.isRoot()) {
            return receiver$0;
        }
        if (Intrinsics.areEqual(receiver$0, prefix)) {
            FqName fqName = FqName.ROOT;
            Intrinsics.checkExpressionValueIsNotNull(fqName, "FqName.ROOT");
            return fqName;
        }
        String strAsString = receiver$0.asString();
        Intrinsics.checkExpressionValueIsNotNull(strAsString, "asString()");
        int length = prefix.asString().length() + 1;
        if (strAsString == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String strSubstring = strAsString.substring(length);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
        return new FqName(strSubstring);
    }

    public static final boolean isValidJavaFqName(@Nullable String qualifiedName) {
        if (qualifiedName == null) {
            return false;
        }
        State state = State.BEGINNING;
        int length = qualifiedName.length();
        State state2 = state;
        for (int i = 0; i < length; i++) {
            char c = qualifiedName.charAt(i);
            int i2 = WhenMappings.$EnumSwitchMapping$0[state2.ordinal()];
            if (i2 == 1 || i2 == 2) {
                if (!Character.isJavaIdentifierPart(c)) {
                    return false;
                }
                state2 = State.MIDDLE;
            } else if (i2 != 3) {
                continue;
            } else if (c == '.') {
                state2 = State.AFTER_DOT;
            } else if (!Character.isJavaIdentifierPart(c)) {
                return false;
            }
        }
        return state2 != State.AFTER_DOT;
    }
}
