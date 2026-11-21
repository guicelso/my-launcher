package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: Indent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002¢\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002¢\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\u0082\b¢\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002¨\u0006\u0015"}, d2 = {"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"}, k = 5, mv = {1, 1, 13}, xi = 1, xs = "kotlin/text/StringsKt")
class StringsKt__IndentKt {
    @NotNull
    public static /* synthetic */ String trimMargin$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "|";
        }
        return StringsKt.trimMargin(str, str2);
    }

    @NotNull
    public static final String trimMargin(@NotNull String receiver$0, @NotNull String marginPrefix) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(marginPrefix, "marginPrefix");
        return StringsKt.replaceIndentByMargin(receiver$0, "", marginPrefix);
    }

    @NotNull
    public static /* synthetic */ String replaceIndentByMargin$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        if ((i & 2) != 0) {
            str3 = "|";
        }
        return StringsKt.replaceIndentByMargin(str, str2, str3);
    }

    @NotNull
    public static final String replaceIndentByMargin(@NotNull String receiver$0, @NotNull String newIndent, @NotNull String marginPrefix) {
        Collection destination$iv$iv$iv;
        String line;
        String strInvoke;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(newIndent, "newIndent");
        Intrinsics.checkParameterIsNotNull(marginPrefix, "marginPrefix");
        int i = 0;
        if (!(!StringsKt.isBlank(marginPrefix))) {
            throw new IllegalArgumentException("marginPrefix must be non-blank string.".toString());
        }
        List<String> listLines = StringsKt.lines(receiver$0);
        int resultSizeEstimate$iv = receiver$0.length() + (newIndent.length() * listLines.size());
        Function1<String, String> indentFunction$StringsKt__IndentKt = getIndentFunction$StringsKt__IndentKt(newIndent);
        int lastIndex$iv = CollectionsKt.getLastIndex(listLines);
        List<String> $receiver$iv$iv = listLines;
        Collection destination$iv$iv$iv2 = new ArrayList();
        Collection destination$iv$iv$iv3 = destination$iv$iv$iv2;
        int index$iv$iv$iv$iv = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        for (Object item$iv$iv$iv$iv : $receiver$iv$iv) {
            int index$iv$iv$iv$iv2 = index$iv$iv$iv$iv + 1;
            if (index$iv$iv$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            int index$iv$iv$iv = index$iv$iv$iv$iv;
            boolean z5 = z2;
            String value$iv = (String) item$iv$iv$iv$iv;
            boolean z6 = z;
            if ((index$iv$iv$iv != 0 && index$iv$iv$iv != lastIndex$iv) || !StringsKt.isBlank(value$iv)) {
                boolean z7 = z4;
                String $receiver$iv = value$iv;
                boolean z8 = z3;
                int length = $receiver$iv.length();
                int index$iv = i;
                while (true) {
                    if (index$iv < length) {
                        char it = $receiver$iv.charAt(index$iv);
                        if (!CharsKt.isWhitespace(it)) {
                            break;
                        }
                        index$iv++;
                    } else {
                        index$iv = -1;
                        break;
                    }
                }
                if (index$iv == -1) {
                    destination$iv$iv$iv = destination$iv$iv$iv3;
                    line = null;
                } else {
                    int firstNonWhitespaceIndex = index$iv;
                    destination$iv$iv$iv = destination$iv$iv$iv3;
                    if (!StringsKt.startsWith$default(value$iv, marginPrefix, index$iv, false, 4, (Object) null)) {
                        line = null;
                    } else {
                        int length2 = firstNonWhitespaceIndex + marginPrefix.length();
                        if (value$iv == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        line = value$iv.substring(length2);
                        Intrinsics.checkExpressionValueIsNotNull(line, "(this as java.lang.String).substring(startIndex)");
                    }
                }
                if (line == null || (strInvoke = indentFunction$StringsKt__IndentKt.invoke(line)) == null) {
                    z4 = z7;
                    z3 = z8;
                } else {
                    value$iv = strInvoke;
                    z4 = z7;
                    z3 = z8;
                }
            } else {
                destination$iv$iv$iv = destination$iv$iv$iv3;
                value$iv = null;
            }
            if (value$iv != null) {
                destination$iv$iv$iv.add(value$iv);
            }
            destination$iv$iv$iv3 = destination$iv$iv$iv;
            index$iv$iv$iv$iv = index$iv$iv$iv$iv2;
            z2 = z5;
            z = z6;
            i = 0;
        }
        String string = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv$iv3, new StringBuilder(resultSizeEstimate$iv), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return string;
    }

    @NotNull
    public static final String trimIndent(@NotNull String receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return StringsKt.replaceIndent(receiver$0, "");
    }

    @NotNull
    public static /* synthetic */ String replaceIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        return StringsKt.replaceIndent(str, str2);
    }

    @NotNull
    public static final String replaceIndent(@NotNull String receiver$0, @NotNull String newIndent) {
        String strInvoke;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(newIndent, "newIndent");
        List<String> listLines = StringsKt.lines(receiver$0);
        List<String> $receiver$iv = listLines;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            String p1 = (String) element$iv$iv;
            if (!StringsKt.isBlank(p1)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        Iterable $receiver$iv3 = $receiver$iv2;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv3, 10));
        for (Object item$iv$iv : $receiver$iv3) {
            String p2 = (String) item$iv$iv;
            destination$iv$iv2.add(Integer.valueOf(indentWidth$StringsKt__IndentKt(p2)));
        }
        Iterable $receiver$iv4 = (List) destination$iv$iv2;
        Integer num = (Integer) CollectionsKt.min($receiver$iv4);
        int minCommonIndent = num != null ? num.intValue() : 0;
        int resultSizeEstimate$iv = receiver$0.length() + (newIndent.length() * listLines.size());
        Function1<String, String> indentFunction$StringsKt__IndentKt = getIndentFunction$StringsKt__IndentKt(newIndent);
        int lastIndex$iv = CollectionsKt.getLastIndex(listLines);
        List<String> $receiver$iv$iv = listLines;
        Collection destination$iv$iv$iv = new ArrayList();
        int index$iv$iv$iv$iv = 0;
        for (Object item$iv$iv$iv$iv : $receiver$iv$iv) {
            int index$iv$iv$iv$iv2 = index$iv$iv$iv$iv + 1;
            if (index$iv$iv$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String value$iv = (String) item$iv$iv$iv$iv;
            int index$iv = index$iv$iv$iv$iv;
            if ((index$iv == 0 || index$iv == lastIndex$iv) && StringsKt.isBlank(value$iv)) {
                strInvoke = null;
            } else {
                String line = StringsKt.drop(value$iv, minCommonIndent);
                if (line == null || (strInvoke = indentFunction$StringsKt__IndentKt.invoke(line)) == null) {
                    strInvoke = value$iv;
                }
            }
            if (strInvoke != null) {
                destination$iv$iv$iv.add(strInvoke);
            }
            index$iv$iv$iv$iv = index$iv$iv$iv$iv2;
        }
        String string = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv$iv, new StringBuilder(resultSizeEstimate$iv), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return string;
    }

    @NotNull
    public static /* synthetic */ String prependIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "    ";
        }
        return StringsKt.prependIndent(str, str2);
    }

    @NotNull
    public static final String prependIndent(@NotNull String receiver$0, @NotNull final String indent) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(indent, "indent");
        return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence(receiver$0), new Function1<String, String>() { // from class: kotlin.text.StringsKt__IndentKt.prependIndent.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final String invoke(@NotNull String it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                if (StringsKt.isBlank(it)) {
                    return it.length() < indent.length() ? indent : it;
                }
                return indent + it;
            }
        }), "\n", null, null, 0, null, null, 62, null);
    }

    private static final int indentWidth$StringsKt__IndentKt(@NotNull String $receiver) {
        String $receiver$iv = $receiver;
        int index$iv = 0;
        int length = $receiver$iv.length();
        while (true) {
            if (index$iv < length) {
                if (!CharsKt.isWhitespace($receiver$iv.charAt(index$iv))) {
                    break;
                }
                index$iv++;
            } else {
                index$iv = -1;
                break;
            }
        }
        int it = index$iv;
        return it == -1 ? $receiver.length() : it;
    }

    private static final Function1<String, String> getIndentFunction$StringsKt__IndentKt(final String indent) {
        return indent.length() == 0 ? new Function1<String, String>() { // from class: kotlin.text.StringsKt__IndentKt$getIndentFunction$1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final String invoke(@NotNull String line) {
                Intrinsics.checkParameterIsNotNull(line, "line");
                return line;
            }
        } : new Function1<String, String>() { // from class: kotlin.text.StringsKt__IndentKt$getIndentFunction$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final String invoke(@NotNull String line) {
                Intrinsics.checkParameterIsNotNull(line, "line");
                return indent + line;
            }
        };
    }

    private static final String reindent$StringsKt__IndentKt(@NotNull List<String> list, int resultSizeEstimate, Function1<? super String, String> function1, Function1<? super String, String> function2) {
        int lastIndex;
        String strInvoke;
        int $i$f$reindent$StringsKt__IndentKt = 0;
        int lastIndex2 = CollectionsKt.getLastIndex(list);
        List<String> $receiver$iv = list;
        int i = 0;
        Collection destination$iv$iv = new ArrayList();
        int index$iv$iv$iv = 0;
        for (Object item$iv$iv$iv : $receiver$iv) {
            int index$iv$iv$iv2 = index$iv$iv$iv + 1;
            if (index$iv$iv$iv < 0) {
                if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, i)) {
                    throw new ArithmeticException("Index overflow has happened.");
                }
                CollectionsKt.throwIndexOverflow();
            }
            int $i$f$reindent$StringsKt__IndentKt2 = index$iv$iv$iv;
            String value = (String) item$iv$iv$iv;
            if (($i$f$reindent$StringsKt__IndentKt2 == 0 || $i$f$reindent$StringsKt__IndentKt2 == lastIndex2) && StringsKt.isBlank(value)) {
                lastIndex = lastIndex2;
                strInvoke = null;
            } else {
                String strInvoke2 = function2.invoke(value);
                if (strInvoke2 != null) {
                    lastIndex = lastIndex2;
                    strInvoke = function1.invoke(strInvoke2);
                    if (strInvoke == null) {
                    }
                } else {
                    lastIndex = lastIndex2;
                }
                strInvoke = value;
            }
            if (strInvoke != null) {
                destination$iv$iv.add(strInvoke);
            }
            index$iv$iv$iv = index$iv$iv$iv2;
            $i$f$reindent$StringsKt__IndentKt = $i$f$reindent$StringsKt__IndentKt;
            $receiver$iv = $receiver$iv;
            lastIndex2 = lastIndex;
            i = 0;
        }
        String string = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv, new StringBuilder(resultSizeEstimate), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return string;
    }
}
