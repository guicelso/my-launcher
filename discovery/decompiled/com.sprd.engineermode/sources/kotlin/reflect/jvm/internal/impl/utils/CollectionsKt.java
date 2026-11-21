package kotlin.reflect.jvm.internal.impl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: collections.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class CollectionsKt {
    @NotNull
    public static final <K> Map<K, Integer> mapToIndex(@NotNull Iterable<? extends K> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        LinkedHashMap map = new LinkedHashMap();
        Iterator<? extends K> it = receiver$0.iterator();
        int index = 0;
        while (it.hasNext()) {
            map.put(it.next(), Integer.valueOf(index));
            index++;
        }
        return map;
    }

    public static final <T> void addIfNotNull(@NotNull Collection<T> receiver$0, @Nullable T t) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (t != null) {
            receiver$0.add(t);
        }
    }

    @NotNull
    public static final <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
        return new HashMap<>(capacity(expectedSize));
    }

    @NotNull
    public static final <E> HashSet<E> newHashSetWithExpectedSize(int expectedSize) {
        return new HashSet<>(capacity(expectedSize));
    }

    @NotNull
    public static final <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int expectedSize) {
        return new LinkedHashSet<>(capacity(expectedSize));
    }

    private static final int capacity(int expectedSize) {
        if (expectedSize < 3) {
            return 3;
        }
        return (expectedSize / 3) + expectedSize + 1;
    }

    @NotNull
    public static final <T> List<T> compact(@NotNull ArrayList<T> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        int size = receiver$0.size();
        if (size == 0) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        if (size == 1) {
            return kotlin.collections.CollectionsKt.listOf(kotlin.collections.CollectionsKt.first((List) receiver$0));
        }
        receiver$0.trimToSize();
        return receiver$0;
    }
}
