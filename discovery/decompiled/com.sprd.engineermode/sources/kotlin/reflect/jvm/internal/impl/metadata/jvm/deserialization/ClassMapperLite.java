package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: ClassMapperLite.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ClassMapperLite {
    public static final ClassMapperLite INSTANCE = new ClassMapperLite();
    private static final Map<String, String> map;

    static {
        final Map $receiver = new LinkedHashMap();
        List primitives = CollectionsKt.listOf((Object[]) new String[]{"Boolean", "Z", "Char", "C", "Byte", "B", "Short", "S", "Int", "I", "Float", "F", "Long", "J", "Double", "D"});
        IntProgression intProgressionStep = RangesKt.step(CollectionsKt.getIndices(primitives), 2);
        int i = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step <= 0 ? i >= last : i <= last) {
            while (true) {
                $receiver.put("kotlin/" + ((String) primitives.get(i)), primitives.get(i + 1));
                $receiver.put("kotlin/" + ((String) primitives.get(i)) + "Array", '[' + ((String) primitives.get(i + 1)));
                if (i == last) {
                    break;
                } else {
                    i += step;
                }
            }
        }
        $receiver.put("kotlin/Unit", "V");
        Function2<String, String, Unit> function2 = new Function2<String, String, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.ClassMapperLite$map$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(String str, String str2) {
                invoke2(str, str2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String kotlinSimpleName, @NotNull String javaInternalName) {
                Intrinsics.checkParameterIsNotNull(kotlinSimpleName, "kotlinSimpleName");
                Intrinsics.checkParameterIsNotNull(javaInternalName, "javaInternalName");
                $receiver.put("kotlin/" + kotlinSimpleName, 'L' + javaInternalName + ';');
            }
        };
        function2.invoke2("Any", "java/lang/Object");
        function2.invoke2("Nothing", "java/lang/Void");
        function2.invoke2("Annotation", "java/lang/annotation/Annotation");
        for (String klass : CollectionsKt.listOf((Object[]) new String[]{"String", "CharSequence", "Throwable", "Cloneable", "Number", "Comparable", "Enum"})) {
            function2.invoke2(klass, "java/lang/" + klass);
        }
        for (String klass2 : CollectionsKt.listOf((Object[]) new String[]{"Iterator", "Collection", "List", "Set", "Map", "ListIterator"})) {
            function2.invoke2("collections/" + klass2, "java/util/" + klass2);
            function2.invoke2("collections/Mutable" + klass2, "java/util/" + klass2);
        }
        function2.invoke2("collections/Iterable", "java/lang/Iterable");
        function2.invoke2("collections/MutableIterable", "java/lang/Iterable");
        function2.invoke2("collections/Map.Entry", "java/util/Map$Entry");
        function2.invoke2("collections/MutableMap.MutableEntry", "java/util/Map$Entry");
        for (int i2 = 0; i2 <= 22; i2++) {
            function2.invoke2("Function" + i2, "kotlin/jvm/functions/Function" + i2);
            function2.invoke2("reflect/KFunction" + i2, "kotlin/reflect/KFunction");
        }
        for (String klass3 : CollectionsKt.listOf((Object[]) new String[]{"Char", "Byte", "Short", "Int", "Float", "Long", "Double", "String", "Enum"})) {
            function2.invoke2(klass3 + ".Companion", "kotlin/jvm/internal/" + klass3 + "CompanionObject");
        }
        map = $receiver;
    }

    private ClassMapperLite() {
    }

    @JvmStatic
    @NotNull
    public static final String mapClass(@NotNull String classId) {
        Intrinsics.checkParameterIsNotNull(classId, "classId");
        String str = map.get(classId);
        if (str != null) {
            return str;
        }
        return 'L' + StringsKt.replace$default(classId, '.', Typography.dollar, false, 4, (Object) null) + ';';
    }
}
