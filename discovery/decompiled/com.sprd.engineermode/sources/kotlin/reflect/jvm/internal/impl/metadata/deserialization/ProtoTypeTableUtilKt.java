package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: protoTypeTableUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ProtoTypeTableUtilKt {
    @NotNull
    public static final List<ProtoBuf.Type> supertypes(@NotNull ProtoBuf.Class receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        List<ProtoBuf.Type> supertypeList = receiver$0.getSupertypeList();
        List<ProtoBuf.Type> p1 = supertypeList;
        if (!(!p1.isEmpty())) {
            supertypeList = null;
        }
        if (supertypeList != null) {
            return supertypeList;
        }
        Iterable supertypeIdList = receiver$0.getSupertypeIdList();
        Intrinsics.checkExpressionValueIsNotNull(supertypeIdList, "supertypeIdList");
        Iterable $receiver$iv = supertypeIdList;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            Integer it = (Integer) item$iv$iv;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            destination$iv$iv.add(typeTable.get(it.intValue()));
        }
        return (List) destination$iv$iv;
    }

    @Nullable
    public static final ProtoBuf.Type type(@NotNull ProtoBuf.Type.Argument receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        if (receiver$0.hasType()) {
            return receiver$0.getType();
        }
        if (receiver$0.hasTypeId()) {
            return typeTable.get(receiver$0.getTypeId());
        }
        return null;
    }

    @Nullable
    public static final ProtoBuf.Type flexibleUpperBound(@NotNull ProtoBuf.Type receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        if (receiver$0.hasFlexibleUpperBound()) {
            return receiver$0.getFlexibleUpperBound();
        }
        if (receiver$0.hasFlexibleUpperBoundId()) {
            return typeTable.get(receiver$0.getFlexibleUpperBoundId());
        }
        return null;
    }

    @NotNull
    public static final List<ProtoBuf.Type> upperBounds(@NotNull ProtoBuf.TypeParameter receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        List<ProtoBuf.Type> upperBoundList = receiver$0.getUpperBoundList();
        List<ProtoBuf.Type> p1 = upperBoundList;
        if (!(!p1.isEmpty())) {
            upperBoundList = null;
        }
        if (upperBoundList != null) {
            return upperBoundList;
        }
        Iterable upperBoundIdList = receiver$0.getUpperBoundIdList();
        Intrinsics.checkExpressionValueIsNotNull(upperBoundIdList, "upperBoundIdList");
        Iterable $receiver$iv = upperBoundIdList;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            Integer it = (Integer) item$iv$iv;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            destination$iv$iv.add(typeTable.get(it.intValue()));
        }
        return (List) destination$iv$iv;
    }

    @NotNull
    public static final ProtoBuf.Type returnType(@NotNull ProtoBuf.Function receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        if (!receiver$0.hasReturnType()) {
            if (receiver$0.hasReturnTypeId()) {
                return typeTable.get(receiver$0.getReturnTypeId());
            }
            throw new IllegalStateException("No returnType in ProtoBuf.Function".toString());
        }
        ProtoBuf.Type returnType = receiver$0.getReturnType();
        Intrinsics.checkExpressionValueIsNotNull(returnType, "returnType");
        return returnType;
    }

    public static final boolean hasReceiver(@NotNull ProtoBuf.Function receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.hasReceiverType() || receiver$0.hasReceiverTypeId();
    }

    @Nullable
    public static final ProtoBuf.Type receiverType(@NotNull ProtoBuf.Function receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        if (receiver$0.hasReceiverType()) {
            return receiver$0.getReceiverType();
        }
        if (receiver$0.hasReceiverTypeId()) {
            return typeTable.get(receiver$0.getReceiverTypeId());
        }
        return null;
    }

    @NotNull
    public static final ProtoBuf.Type returnType(@NotNull ProtoBuf.Property receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        if (!receiver$0.hasReturnType()) {
            if (receiver$0.hasReturnTypeId()) {
                return typeTable.get(receiver$0.getReturnTypeId());
            }
            throw new IllegalStateException("No returnType in ProtoBuf.Property".toString());
        }
        ProtoBuf.Type returnType = receiver$0.getReturnType();
        Intrinsics.checkExpressionValueIsNotNull(returnType, "returnType");
        return returnType;
    }

    public static final boolean hasReceiver(@NotNull ProtoBuf.Property receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.hasReceiverType() || receiver$0.hasReceiverTypeId();
    }

    @Nullable
    public static final ProtoBuf.Type receiverType(@NotNull ProtoBuf.Property receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        if (receiver$0.hasReceiverType()) {
            return receiver$0.getReceiverType();
        }
        if (receiver$0.hasReceiverTypeId()) {
            return typeTable.get(receiver$0.getReceiverTypeId());
        }
        return null;
    }

    @NotNull
    public static final ProtoBuf.Type type(@NotNull ProtoBuf.ValueParameter receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        if (!receiver$0.hasType()) {
            if (receiver$0.hasTypeId()) {
                return typeTable.get(receiver$0.getTypeId());
            }
            throw new IllegalStateException("No type in ProtoBuf.ValueParameter".toString());
        }
        ProtoBuf.Type type = receiver$0.getType();
        Intrinsics.checkExpressionValueIsNotNull(type, "type");
        return type;
    }

    @Nullable
    public static final ProtoBuf.Type varargElementType(@NotNull ProtoBuf.ValueParameter receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        if (receiver$0.hasVarargElementType()) {
            return receiver$0.getVarargElementType();
        }
        if (receiver$0.hasVarargElementTypeId()) {
            return typeTable.get(receiver$0.getVarargElementTypeId());
        }
        return null;
    }

    @Nullable
    public static final ProtoBuf.Type outerType(@NotNull ProtoBuf.Type receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        if (receiver$0.hasOuterType()) {
            return receiver$0.getOuterType();
        }
        if (receiver$0.hasOuterTypeId()) {
            return typeTable.get(receiver$0.getOuterTypeId());
        }
        return null;
    }

    @Nullable
    public static final ProtoBuf.Type abbreviatedType(@NotNull ProtoBuf.Type receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        if (receiver$0.hasAbbreviatedType()) {
            return receiver$0.getAbbreviatedType();
        }
        if (receiver$0.hasAbbreviatedTypeId()) {
            return typeTable.get(receiver$0.getAbbreviatedTypeId());
        }
        return null;
    }

    @NotNull
    public static final ProtoBuf.Type underlyingType(@NotNull ProtoBuf.TypeAlias receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        if (!receiver$0.hasUnderlyingType()) {
            if (receiver$0.hasUnderlyingTypeId()) {
                return typeTable.get(receiver$0.getUnderlyingTypeId());
            }
            throw new IllegalStateException("No underlyingType in ProtoBuf.TypeAlias".toString());
        }
        ProtoBuf.Type underlyingType = receiver$0.getUnderlyingType();
        Intrinsics.checkExpressionValueIsNotNull(underlyingType, "underlyingType");
        return underlyingType;
    }

    @NotNull
    public static final ProtoBuf.Type expandedType(@NotNull ProtoBuf.TypeAlias receiver$0, @NotNull TypeTable typeTable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(typeTable, "typeTable");
        if (!receiver$0.hasExpandedType()) {
            if (receiver$0.hasExpandedTypeId()) {
                return typeTable.get(receiver$0.getExpandedTypeId());
            }
            throw new IllegalStateException("No expandedType in ProtoBuf.TypeAlias".toString());
        }
        ProtoBuf.Type expandedType = receiver$0.getExpandedType();
        Intrinsics.checkExpressionValueIsNotNull(expandedType, "expandedType");
        return expandedType;
    }
}
