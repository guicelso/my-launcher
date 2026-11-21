package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: TypeTable.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class TypeTable {

    @NotNull
    private final List<ProtoBuf.Type> types;

    public TypeTable(@NotNull ProtoBuf.TypeTable typeTable) {
        List<ProtoBuf.Type> list;
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        TypeTable $receiver = this;
        List<ProtoBuf.Type> originalTypes = typeTable.getTypeList();
        if (typeTable.hasFirstNullable()) {
            int firstNullable = typeTable.getFirstNullable();
            Iterable typeList = typeTable.getTypeList();
            Intrinsics.checkExpressionValueIsNotNull(typeList, "typeTable.typeList");
            Iterable $receiver$iv = typeList;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
            int index$iv$iv = 0;
            for (Object item$iv$iv : $receiver$iv) {
                int index$iv$iv2 = index$iv$iv + 1;
                if (index$iv$iv < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                ProtoBuf.Type type = (ProtoBuf.Type) item$iv$iv;
                destination$iv$iv.add(index$iv$iv >= firstNullable ? type.toBuilder().setNullable(true).build() : type);
                index$iv$iv = index$iv$iv2;
                $receiver = $receiver;
            }
            list = (List) destination$iv$iv;
        } else {
            Intrinsics.checkExpressionValueIsNotNull(originalTypes, "originalTypes");
            list = originalTypes;
        }
        this.types = list;
    }

    @NotNull
    public final ProtoBuf.Type get(int index) {
        return this.types.get(index);
    }
}
