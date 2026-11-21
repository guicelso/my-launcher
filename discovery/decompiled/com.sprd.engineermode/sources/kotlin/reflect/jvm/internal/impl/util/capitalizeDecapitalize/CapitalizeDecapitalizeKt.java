package kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: capitalizeDecapitalize.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class CapitalizeDecapitalizeKt {
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt$decapitalizeSmart$1] */
    @NotNull
    public static final String decapitalizeSmart(@NotNull final String receiver$0, final boolean asciiOnly) {
        Object element$iv;
        int it;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ?? r0 = new Function1<Integer, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt.decapitalizeSmart.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Integer num) {
                return Boolean.valueOf(invoke(num.intValue()));
            }

            public final boolean invoke(int index) {
                char c = receiver$0.charAt(index);
                if (asciiOnly) {
                    return 'A' <= c && 'Z' >= c;
                }
                return Character.isUpperCase(c);
            }
        };
        if ((receiver$0.length() == 0) || !r0.invoke(0)) {
            return receiver$0;
        }
        if (receiver$0.length() == 1 || !r0.invoke(1)) {
            return asciiOnly ? decapitalizeAsciiOnly(receiver$0) : StringsKt.decapitalize(receiver$0);
        }
        Function1<String, String> function1 = new Function1<String, String>() { // from class: kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt.decapitalizeSmart.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final String invoke(@NotNull String string) {
                Intrinsics.checkParameterIsNotNull(string, "string");
                if (asciiOnly) {
                    return CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(string);
                }
                String lowerCase = string.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                return lowerCase;
            }
        };
        Iterable $receiver$iv = StringsKt.getIndices(receiver$0);
        Iterator<Integer> it2 = $receiver$iv.iterator();
        do {
            if (!it2.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it2.next();
            it = ((Number) element$iv).intValue();
        } while (!(!r0.invoke(it)));
        Integer num = (Integer) element$iv;
        if (num != null) {
            int secondWordStart = num.intValue() - 1;
            StringBuilder sb = new StringBuilder();
            String strSubstring = receiver$0.substring(0, secondWordStart);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            sb.append(function1.invoke(strSubstring));
            String strSubstring2 = receiver$0.substring(secondWordStart);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.String).substring(startIndex)");
            sb.append(strSubstring2);
            return sb.toString();
        }
        return function1.invoke(receiver$0);
    }

    @NotNull
    public static final String capitalizeAsciiOnly(@NotNull String receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (receiver$0.length() == 0) {
            return receiver$0;
        }
        char c = receiver$0.charAt(0);
        if ('a' <= c && 'z' >= c) {
            char upperCase = Character.toUpperCase(c);
            String strSubstring = receiver$0.substring(1);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
            return String.valueOf(upperCase) + strSubstring;
        }
        return receiver$0;
    }

    @NotNull
    public static final String decapitalizeAsciiOnly(@NotNull String receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (receiver$0.length() == 0) {
            return receiver$0;
        }
        char c = receiver$0.charAt(0);
        if ('A' <= c && 'Z' >= c) {
            char lowerCase = Character.toLowerCase(c);
            String strSubstring = receiver$0.substring(1);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
            return String.valueOf(lowerCase) + strSubstring;
        }
        return receiver$0;
    }

    @NotNull
    public static final String toLowerCaseAsciiOnly(@NotNull String receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        StringBuilder builder = new StringBuilder(receiver$0.length());
        int length = receiver$0.length();
        for (int i = 0; i < length; i++) {
            char c = receiver$0.charAt(i);
            builder.append(('A' <= c && 'Z' >= c) ? Character.toLowerCase(c) : c);
        }
        String string = builder.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "builder.toString()");
        return string;
    }
}
