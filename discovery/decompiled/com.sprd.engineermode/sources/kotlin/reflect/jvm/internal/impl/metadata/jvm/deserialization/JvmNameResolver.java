package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: JvmNameResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class JvmNameResolver implements NameResolver {
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final List<String> PREDEFINED_STRINGS = CollectionsKt.listOf((Object[]) new String[]{"kotlin/Any", "kotlin/Nothing", "kotlin/Unit", "kotlin/Throwable", "kotlin/Number", "kotlin/Byte", "kotlin/Double", "kotlin/Float", "kotlin/Int", "kotlin/Long", "kotlin/Short", "kotlin/Boolean", "kotlin/Char", "kotlin/CharSequence", "kotlin/String", "kotlin/Comparable", "kotlin/Enum", "kotlin/Array", "kotlin/ByteArray", "kotlin/DoubleArray", "kotlin/FloatArray", "kotlin/IntArray", "kotlin/LongArray", "kotlin/ShortArray", "kotlin/BooleanArray", "kotlin/CharArray", "kotlin/Cloneable", "kotlin/Annotation", "kotlin/collections/Iterable", "kotlin/collections/MutableIterable", "kotlin/collections/Collection", "kotlin/collections/MutableCollection", "kotlin/collections/List", "kotlin/collections/MutableList", "kotlin/collections/Set", "kotlin/collections/MutableSet", "kotlin/collections/Map", "kotlin/collections/MutableMap", "kotlin/collections/Map.Entry", "kotlin/collections/MutableMap.MutableEntry", "kotlin/collections/Iterator", "kotlin/collections/MutableIterator", "kotlin/collections/ListIterator", "kotlin/collections/MutableListIterator"});
    private static final Map<String, Integer> PREDEFINED_STRINGS_MAP;
    private final Set<Integer> localNameIndices;

    @NotNull
    private final List<JvmProtoBuf.StringTableTypes.Record> records;

    @NotNull
    private final String[] strings;

    @NotNull
    private final JvmProtoBuf.StringTableTypes types;

    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[JvmProtoBuf.StringTableTypes.Record.Operation.values().length];

        static {
            $EnumSwitchMapping$0[JvmProtoBuf.StringTableTypes.Record.Operation.NONE.ordinal()] = 1;
            $EnumSwitchMapping$0[JvmProtoBuf.StringTableTypes.Record.Operation.INTERNAL_TO_CLASS_ID.ordinal()] = 2;
            $EnumSwitchMapping$0[JvmProtoBuf.StringTableTypes.Record.Operation.DESC_TO_CLASS_ID.ordinal()] = 3;
        }
    }

    public JvmNameResolver(@NotNull JvmProtoBuf.StringTableTypes types, @NotNull String[] strings) {
        Intrinsics.checkParameterIsNotNull(types, "types");
        Intrinsics.checkParameterIsNotNull(strings, "strings");
        this.types = types;
        this.strings = strings;
        List<Integer> localNameList = this.types.getLocalNameList();
        this.localNameIndices = localNameList.isEmpty() ? SetsKt.emptySet() : CollectionsKt.toSet(localNameList);
        ArrayList $receiver = new ArrayList();
        List<JvmProtoBuf.StringTableTypes.Record> recordList = this.types.getRecordList();
        $receiver.ensureCapacity(recordList.size());
        boolean z = false;
        for (JvmProtoBuf.StringTableTypes.Record record : recordList) {
            Intrinsics.checkExpressionValueIsNotNull(record, "record");
            int range = record.getRange();
            boolean z2 = z;
            for (int i = 0; i < range; i++) {
                $receiver.add(record);
            }
            z = z2;
        }
        $receiver.trimToSize();
        this.records = $receiver;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x003c  */
    @Override // kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver
    @NotNull
    public String getString(int index) {
        String string;
        JvmProtoBuf.StringTableTypes.Record record = this.records.get(index);
        if (record.hasString()) {
            string = record.getString();
        } else if (record.hasPredefinedIndex()) {
            int size = PREDEFINED_STRINGS.size();
            int predefinedIndex = record.getPredefinedIndex();
            if (predefinedIndex >= 0 && size > predefinedIndex) {
                string = PREDEFINED_STRINGS.get(record.getPredefinedIndex());
            } else {
                string = this.strings[index];
            }
        } else {
            string = this.strings[index];
        }
        if (record.getSubstringIndexCount() >= 2) {
            List<Integer> substringIndexList = record.getSubstringIndexList();
            Integer begin = substringIndexList.get(0);
            Integer end = substringIndexList.get(1);
            Intrinsics.checkExpressionValueIsNotNull(begin, "begin");
            if (Intrinsics.compare(0, begin.intValue()) <= 0) {
                int iIntValue = begin.intValue();
                Intrinsics.checkExpressionValueIsNotNull(end, "end");
                if (Intrinsics.compare(iIntValue, end.intValue()) <= 0 && Intrinsics.compare(end.intValue(), string.length()) <= 0) {
                    Intrinsics.checkExpressionValueIsNotNull(string, "string");
                    String strSubstring = string.substring(begin.intValue(), end.intValue());
                    Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    string = strSubstring;
                }
            }
        }
        if (record.getReplaceCharCount() >= 2) {
            List<Integer> replaceCharList = record.getReplaceCharList();
            Integer from = replaceCharList.get(0);
            Integer to = replaceCharList.get(1);
            Intrinsics.checkExpressionValueIsNotNull(string, "string");
            string = StringsKt.replace$default(string, (char) from.intValue(), (char) to.intValue(), false, 4, (Object) null);
        }
        JvmProtoBuf.StringTableTypes.Record.Operation operation = record.getOperation();
        if (operation == null) {
            operation = JvmProtoBuf.StringTableTypes.Record.Operation.NONE;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[operation.ordinal()];
        if (i != 1) {
            if (i == 2) {
                Intrinsics.checkExpressionValueIsNotNull(string, "string");
                string = StringsKt.replace$default(string, Typography.dollar, '.', false, 4, (Object) null);
            } else if (i == 3) {
                if (string.length() >= 2) {
                    Intrinsics.checkExpressionValueIsNotNull(string, "string");
                    String strSubstring2 = string.substring(1, string.length() - 1);
                    Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    string = strSubstring2;
                }
                Intrinsics.checkExpressionValueIsNotNull(string, "string");
                string = StringsKt.replace$default(string, Typography.dollar, '.', false, 4, (Object) null);
            }
        }
        Intrinsics.checkExpressionValueIsNotNull(string, "string");
        return string;
    }

    @Override // kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver
    @NotNull
    public String getQualifiedClassName(int index) {
        return getString(index);
    }

    @Override // kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver
    public boolean isLocalClassName(int index) {
        return this.localNameIndices.contains(Integer.valueOf(index));
    }

    /* JADX INFO: compiled from: JvmNameResolver.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        Iterable $receiver$iv = CollectionsKt.withIndex(PREDEFINED_STRINGS);
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)), 16);
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        for (Object element$iv$iv : $receiver$iv) {
            IndexedValue it = (IndexedValue) element$iv$iv;
            String str = (String) it.getValue();
            IndexedValue it2 = (IndexedValue) element$iv$iv;
            destination$iv$iv.put(str, Integer.valueOf(it2.getIndex()));
        }
        PREDEFINED_STRINGS_MAP = destination$iv$iv;
    }
}
