package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: RenderingUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class RenderingUtilsKt {
    @NotNull
    public static final String render(@NotNull Name receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (!shouldBeEscaped(receiver$0)) {
            String strAsString = receiver$0.asString();
            Intrinsics.checkExpressionValueIsNotNull(strAsString, "asString()");
            return strAsString;
        }
        StringBuilder sb = new StringBuilder();
        String strAsString2 = receiver$0.asString();
        Intrinsics.checkExpressionValueIsNotNull(strAsString2, "asString()");
        sb.append(String.valueOf('`') + strAsString2);
        sb.append('`');
        return sb.toString();
    }

    private static final boolean shouldBeEscaped(@NotNull Name $receiver) {
        boolean z;
        if ($receiver.isSpecial()) {
            return false;
        }
        CharSequence string = $receiver.asString();
        Intrinsics.checkExpressionValueIsNotNull(string, "asString()");
        if (!KeywordStringsGenerated.KEYWORDS.contains(string)) {
            CharSequence $receiver$iv = string;
            int i = 0;
            while (true) {
                if (i < $receiver$iv.length()) {
                    char element$iv = $receiver$iv.charAt(i);
                    char it = (Character.isLetterOrDigit(element$iv) || element$iv == '_') ? (char) 0 : (char) 1;
                    if (it != 0) {
                        z = true;
                        break;
                    }
                    i++;
                } else {
                    z = false;
                    break;
                }
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public static final String render(@NotNull FqNameUnsafe receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        List<Name> listPathSegments = receiver$0.pathSegments();
        Intrinsics.checkExpressionValueIsNotNull(listPathSegments, "pathSegments()");
        return renderFqName(listPathSegments);
    }

    @NotNull
    public static final String renderFqName(@NotNull List<Name> pathSegments) {
        Intrinsics.checkParameterIsNotNull(pathSegments, "pathSegments");
        StringBuilder $receiver = new StringBuilder();
        for (Name element : pathSegments) {
            if ($receiver.length() > 0) {
                $receiver.append(".");
            }
            $receiver.append(render(element));
        }
        String string = $receiver.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }
}
